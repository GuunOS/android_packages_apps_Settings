package com.android.settings.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.Preference.OnPreferenceChangeListener;
import android.content.ComponentName;
import android.content.Intent;

import com.android.settings.R;


public class Statusbar extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.statusbar);

        Preference navpreference = getPreferenceManager().findPreference(getString(R.string.statusbar_category));
        navpreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

        	public boolean onPreferenceClick(Preference navpreference) {

		final Intent intent = new Intent(Intent.ACTION_MAIN, null);

		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		final ComponentName cn = new ComponentName("org.cyanogenmod.cmparts", "org.cyanogenmod.cmparts.statusbar.StatusBarSettings");

		intent.setComponent(cn);

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivity( intent);

		return true;
        	}
        });

    Preference network = getPreferenceManager().findPreference("nettraffic_settings");
    network.setOnPreferenceClickListener(new OnPreferenceClickListener() {

      public boolean onPreferenceClick(Preference network) {

    final Intent intent = new Intent(Intent.ACTION_MAIN, null);

    intent.addCategory(Intent.CATEGORY_LAUNCHER);

    final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.fragments.NetworkTrafficSettings");

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
