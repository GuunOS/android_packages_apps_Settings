package com.android.settings.fragments;

import android.content.Context;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.ListPreference;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.provider.Settings.Secure;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.MetricsProto.MetricsEvent;
import com.android.settings.Utils;

public class DisplayFragment extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "DisplayTweaks";
    private static final String ANBI_ENABLED = "anbi_enabled";
    private static final String POCKET_JUDGE = "pocket_judge";
    private static final String THREE_FINGER_GESTURE = "three_finger_gesture";
    private static final String SENSOR_BLOCK = "sensor_block";
    private static final String SYSTEM_DEFAULT_ANIMATION = "system_default_animation";

    private int mDeviceHardwareKeys;
    private SwitchPreference mAnbiPreference;
    private SwitchPreference mPocketJudge;
    private SwitchPreference mThreeFingerGesture;
    private SwitchPreference mSensorBlock;
    private SwitchPreference mSystemDefaultAnimation;

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.APPLICATION;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.display);
        final ContentResolver resolver = getActivity().getContentResolver();
        Resources res = getActivity().getResources();
        PreferenceScreen prefScreen = getPreferenceScreen();

        mDeviceHardwareKeys = res.getInteger(
                                    com.android.internal.R.integer.config_deviceHardwareKeys);

        mAnbiPreference = (SwitchPreference) findPreference(ANBI_ENABLED);
        mAnbiPreference.setChecked((Settings.System.getInt(resolver,
                Settings.System.ANBI_ENABLED, 1) == 1));
        mAnbiPreference.setOnPreferenceChangeListener(this);

        mPocketJudge = (SwitchPreference) findPreference(POCKET_JUDGE);
        mPocketJudge.setChecked((Settings.System.getInt(resolver,
                Settings.System.POCKET_JUDGE, 1) == 1));
        mPocketJudge.setOnPreferenceChangeListener(this);

        mThreeFingerGesture = (SwitchPreference) findPreference(THREE_FINGER_GESTURE);
        mThreeFingerGesture.setChecked((Settings.System.getInt(resolver,
                Settings.System.THREE_FINGER_GESTURE, 1) == 1));
        mThreeFingerGesture.setOnPreferenceChangeListener(this);

        mSensorBlock = (SwitchPreference) findPreference(SENSOR_BLOCK);
        mSensorBlock.setChecked((Settings.System.getInt(resolver,
                Settings.System.SENSOR_BLOCK, 1) == 1));
        mSensorBlock.setOnPreferenceChangeListener(this);

        mSystemDefaultAnimation = (SwitchPreference) findPreference(SYSTEM_DEFAULT_ANIMATION);
        mSystemDefaultAnimation.setChecked((Settings.System.getInt(resolver,
                Settings.System.SYSTEM_DEFAULT_ANIMATION, 1) == 1));
        mSystemDefaultAnimation.setOnPreferenceChangeListener(this);

        if (mDeviceHardwareKeys == 0)
          prefScreen.removePreference(mAnbiPreference);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
      ContentResolver resolver = getActivity().getContentResolver();
      if (preference == mAnbiPreference) {
        boolean value = (Boolean) newValue;
        Settings.System.putInt(resolver,
                Settings.System.ANBI_ENABLED, value ? 1: 0);
        return true;
      } else if (preference == mPocketJudge) {
        boolean value = (Boolean) newValue;
        Settings.System.putInt(resolver,
                Settings.System.POCKET_JUDGE, value ? 1: 0);
        return true;
      } else if (preference == mThreeFingerGesture) {
        boolean value = (Boolean) newValue;
        Settings.System.putInt(resolver,
                Settings.System.THREE_FINGER_GESTURE, value ? 1: 0);
        return true;
      } else if (preference == mSensorBlock) {
        boolean value = (Boolean) newValue;
        Settings.System.putInt(resolver,
                Settings.System.SENSOR_BLOCK, value ? 1: 0);
        return true;
      } else if (preference == mSystemDefaultAnimation) {
        boolean value = (Boolean) newValue;
        Settings.System.putInt(resolver,
                Settings.System.SYSTEM_DEFAULT_ANIMATION, value ? 1: 0);
        return true;
      } else {
        final String key = preference.getKey();
        return true;
      }
    }

  }
