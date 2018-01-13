package com.android.settings.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.Preference.OnPreferenceChangeListener;
import android.content.ComponentName;
import android.content.Intent;

import com.android.internal.logging.MetricsProto.MetricsEvent;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class Buttons extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.buttons);

        Preference navpreference = getPreferenceManager().findPreference(getString(R.string.buttons_category));
        navpreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

        	public boolean onPreferenceClick(Preference navpreference) {

		final Intent intent = new Intent(Intent.ACTION_MAIN, null);

		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		final ComponentName cn = new ComponentName("org.cyanogenmod.cmparts", "org.cyanogenmod.cmparts.input.ButtonSettings");

		intent.setComponent(cn);

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivity( intent);

		return true;
        	}
        });

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return false;
    }
}
