package com.android.settings.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.os.Handler;
import android.os.UserHandle;
import android.content.Context;
import android.preference.SwitchPreference;
import android.content.res.Resources;
import android.content.ContentResolver;
import android.provider.Settings;

import com.android.internal.logging.MetricsProto.MetricsEvent;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class DisplayFragment extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "DisplayTweaks";

    private int mDeviceHardwareKeys;
    private static final String KEY_ANBI = "anbi_enabled";

    private SwitchPreference mAnbiPreference;

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

    public boolean onPreferenceChange(Preference preference, Object objValue) {

        return true;
    }
}
