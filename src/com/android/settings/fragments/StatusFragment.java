/*
 * Copyright (C) 2016 CarbonROM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

public class StatusFragment extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "Statusbar";
    private static final String QS_EASY_TOGGLE = "qs_easy_toggle";

    private SwitchPreference mQsEasyToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.statusbar);
        final ContentResolver resolver = getActivity().getContentResolver();

        mQsEasyToggle = (SwitchPreference) findPreference(QS_EASY_TOGGLE);
        mQsEasyToggle.setChecked((Settings.Secure.getInt(resolver,
                Settings.Secure.QS_EASY_TOGGLE, 1) == 1));
        mQsEasyToggle.setOnPreferenceChangeListener(this);
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.APPLICATION;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
      ContentResolver resolver = getActivity().getContentResolver();
      if  (preference == mQsEasyToggle) {
            boolean value = (Boolean) newValue;
            Settings.Secure.putInt(resolver,
                    Settings.Secure.QS_EASY_TOGGLE, value ? 1: 0);
            return true;
        }
      else {
        final String key = preference.getKey();
        return true;
      }
    }

}
