package com.android.settings.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.logging.MetricsProto.MetricsEvent;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class DisplayFragment extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "DisplayTweaks";
    private static final String KEY_ANBI = "anbi_enabled";

    private int mDeviceHardwareKeys;

    private SwitchPreference mAnbiPreference;

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.APPLICATION;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.display);

        Resources res = getActivity().getResources();
        PreferenceScreen prefScreen = getPreferenceScreen();

        mDeviceHardwareKeys = res.getInteger(
                                    com.android.internal.R.integer.config_deviceHardwareKeys);

        mAnbiPreference = (SwitchPreference) findPreference(KEY_ANBI);

        if (mDeviceHardwareKeys == 0)
          prefScreen.removePreference(mAnbiPreference);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        // Let the intents be launched by the Preference manager
        return super.onPreferenceTreeClick(preference);
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {

        return true;
    }
}
