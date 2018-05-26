package com.android.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.app.IThemeCallback;
import android.app.ThemeManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.provider.Settings.Secure;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SELinux;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceGroup;
import android.telephony.CarrierConfigManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.logging.MetricsProto.MetricsEvent;
import com.android.settings.dashboard.SummaryLoader;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Index;
import com.android.settings.search.Indexable;
import com.android.settingslib.DeviceInfoUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.internal.os.RegionalizationEnvironment;
import com.android.internal.os.IRegionalizationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.android.settingslib.RestrictedLockUtils.EnforcedAdmin;

public class pop extends SettingsPreferenceFragment implements Indexable {

  private static final String LOG_TAG = "PopInfo";

  private static final String KEY_DEVICE_MODEL = "device_model";
  private static final String KEY_DEVICE_NAME = "device_name";
  private static final String KEY_MOD_VERSION = "mod_version";
  private static final String KEY_FIRMWARE_VERSION = "firmware_version";
  private static final String KEY_SECURITY_PATCH = "security_patch";
  private static final String KEY_MOD_BUILD_DATE = "build_date";

  long[] mHits = new long[3];

  private UserManager mUm;

  private EnforcedAdmin mFunDisallowedAdmin;
  private boolean mFunDisallowedBySystem;

  @Override
  public void onCreate(Bundle pinfo) {
      final int accentColor = Secure.getInt(getContentResolver(),
              Secure.THEME_ACCENT_COLOR, 1);
                switch (accentColor) {
                  case 0 :
                    getTheme().applyStyle(R.style.dialog_teal, true);
                    break;
                  case 1 :
                    getTheme().applyStyle(R.style.dialog_green, true);
                    break;
                  case 2 :
                    getTheme().applyStyle(R.style.dialog_cyan, true);
                    break;
                  case 3 :
                    getTheme().applyStyle(R.style.dialog_blue, true);
                    break;
                  case 4 :
                      getTheme().applyStyle(R.style.dialog_yellow, true);
                      break;
                  case 5 :
                      getTheme().applyStyle(R.style.dialog_orange, true);
                      break;
                  case 6 :
                      getTheme().applyStyle(R.style.dialog_red, true);
                      break;
                  case 7 :
                      getTheme().applyStyle(R.style.dialog_pink, true);
                      break;
                  case 8 :
                      getTheme().applyStyle(R.style.dialog_purple, true);
                      break;
                  case 9 :
                      getTheme().applyStyle(R.style.dialog_grey, true);
                      break;
                }
      super.onCreate(pinfo);
      mUm = UserManager.get(getActivity());

      addPreferencesFromResource(R.xml.pop);

      setStringSummary(KEY_FIRMWARE_VERSION, Build.VERSION.RELEASE);
      findPreference(KEY_FIRMWARE_VERSION).setEnabled(true);

      final String patch = DeviceInfoUtils.getSecurityPatch();
      if (!TextUtils.isEmpty(patch)) {
          setStringSummary(KEY_SECURITY_PATCH, patch);
      } else {
          getPreferenceScreen().removePreference(findPreference(KEY_SECURITY_PATCH));
      }

      setStringSummary(KEY_DEVICE_MODEL, Build.MODEL);
      setValueSummary(KEY_MOD_VERSION, "ro.psycho.version");
      setValueSummary(KEY_MOD_BUILD_DATE, "ro.build.date");

      setStringSummary(KEY_DEVICE_NAME, Build.PRODUCT);
      removePreferenceIfBoolFalse(KEY_DEVICE_NAME, R.bool.config_displayDeviceName);

      final Activity act = getActivity();

      PreferenceGroup parentPreference = getPreferenceScreen();

  }

  @Override
  public void onResume() {
      super.onResume();
}

  @Override
  public boolean onPreferenceTreeClick(Preference preference) {
    if (preference.getKey().equals(KEY_FIRMWARE_VERSION)
            || preference.getKey().equals(KEY_MOD_VERSION)) {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length-1);
        mHits[mHits.length-1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis()-500)) {
            if (mUm.hasUserRestriction(UserManager.DISALLOW_FUN)) {
                if (mFunDisallowedAdmin != null && !mFunDisallowedBySystem) {
                    RestrictedLockUtils.sendShowAdminSupportDetailsIntent(getActivity(),
                            mFunDisallowedAdmin);
                }
                Log.d(LOG_TAG, "Sorry, no fun for you!");
                return false;
            }

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.putExtra("is_lineage", preference.getKey().equals(KEY_MOD_VERSION));
            intent.setClassName("android",
                    com.android.internal.app.PlatLogoActivity.class.getName());
            try {
                startActivity(intent);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Unable to start activity " + intent.toString());
            }
        }
    } else if (preference.getKey().equals(KEY_SECURITY_PATCH)) {
        if (getPackageManager().queryIntentActivities(preference.getIntent(), 0).isEmpty()) {
            // Don't send out the intent to stop crash
            Log.w(LOG_TAG, "Stop click action on " + KEY_SECURITY_PATCH + ": "
                    + "queryIntentActivities() returns empty" );
            return true;
        }
    } else if (preference.getKey().equals(KEY_DEVICE_FEEDBACK)) {
        sendFeedback();
    } else if(preference.getKey().equals(KEY_SYSTEM_UPDATE_SETTINGS)) {
        CarrierConfigManager configManager =
                (CarrierConfigManager) getSystemService(Context.CARRIER_CONFIG_SERVICE);
        PersistableBundle b = configManager.getConfig();
        if (b != null && b.getBoolean(CarrierConfigManager.KEY_CI_ACTION_ON_SYS_UPDATE_BOOL)) {
            ciActionOnSysUpdate(b);
        }
    }
    return super.onPreferenceTreeClick(preference);
  }

  private void removePreferenceIfPropertyMissing(PreferenceGroup preferenceGroup,
          String preference, String property ) {
      if (SystemProperties.get(property).equals("")) {
          // Property is missing so remove preference from group
          try {
              preferenceGroup.removePreference(findPreference(preference));
          } catch (RuntimeException e) {
              Log.d(LOG_TAG, "Property '" + property + "' missing and no '"
                      + preference + "' preference");
          }
      }
  }

  private void removePreferenceIfActivityMissing(String preferenceKey, String action) {
      final Intent intent = new Intent(action);
      if (getPackageManager().queryIntentActivities(intent, 0).isEmpty()) {
          Preference pref = findPreference(preferenceKey);
          if (pref != null) {
              getPreferenceScreen().removePreference(pref);
          }
      }
  }

  private void removePreferenceIfBoolFalse(String preference, int resId) {
      if (!getResources().getBoolean(resId)) {
          Preference pref = findPreference(preference);
          if (pref != null) {
              getPreferenceScreen().removePreference(pref);
          }
      }
  }

  private void setStringSummary(String preference, String value) {
      try {
          findPreference(preference).setSummary(value);
      } catch (RuntimeException e) {
          findPreference(preference).setSummary(
              getResources().getString(R.string.device_info_default));
      }
  }

  private void setValueSummary(String preference, String property) {
      try {
          findPreference(preference).setSummary(
                  SystemProperties.get(property,
                          getResources().getString(R.string.device_info_default)));
      } catch (RuntimeException e) {
          // No recovery
      }
  }

  private void setExplicitValueSummary(String preference, String value) {
      try {
          findPreference(preference).setSummary(value);
      } catch (RuntimeException e) {
          // No recovery
      }
  }

  private static class SummaryProvider implements SummaryLoader.SummaryProvider {

      private final Context mContext;
      private final SummaryLoader mSummaryLoader;

      public SummaryProvider(Context context, SummaryLoader summaryLoader) {
          mContext = context;
          mSummaryLoader = summaryLoader;
      }

      @Override
      public void setListening(boolean listening) {
          if (listening) {
              mSummaryLoader.setSummary(this, mContext.getString(R.string.about_summary,
                      Build.VERSION.RELEASE));
          }
      }
  }

  public static final SummaryLoader.SummaryProviderFactory SUMMARY_PROVIDER_FACTORY
          = new SummaryLoader.SummaryProviderFactory() {
      @Override
      public SummaryLoader.SummaryProvider createSummaryProvider(Activity activity,
                                                                 SummaryLoader summaryLoader) {
          return new SummaryProvider(activity, summaryLoader);
      }
  };

  public static final SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
      new BaseSearchIndexProvider() {

          @Override
          public List<SearchIndexableResource> getXmlResourcesToIndex(
                  Context context, boolean enabled) {
              final SearchIndexableResource sir = new SearchIndexableResource(context);
              sir.xmlResId = R.xml.device_info_settings;
              return Arrays.asList(sir);
          }

          private boolean isPropertyMissing(String property) {
              return SystemProperties.get(property).equals("");
          }
      };
    }
