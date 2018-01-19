package com.android.settings.fragments;


import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.Preference.OnPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.preference.SwitchPreference;

import com.android.settings.R;
import com.android.settings.Utils;

public class Statusbar extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "StatusbarTweaks";
    private static final String KEY_SHOW_FOURG = "show_fourg";

    private SwitchPreference mShowFourG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.statusbar);

        PreferenceScreen prefSet = getPreferenceScreen();
        mShowFourG = (SwitchPreference) prefSet.findPreference(KEY_SHOW_FOURG);
            PackageManager pm = getActivity().getPackageManager();
            if (!pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
                prefSet.removePreference(mShowFourG);
            }
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {

        return true;
    }
}
