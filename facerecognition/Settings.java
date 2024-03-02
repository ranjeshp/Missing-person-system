package com.ranjesh.facerecognition;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);

            // Set listeners or additional logic for preferences here
            SwitchPreferenceCompat notificationsPref = findPreference("notifications");
            if (notificationsPref != null) {
                notificationsPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        // Handle changes to the "Enable Notifications" preference
                        boolean notificationsEnabled = (boolean) newValue;
                        // Implement your logic here
                        return true; // To update the preference's state
                    }
                });
            }

            SwitchPreferenceCompat darkModePref = findPreference("darkMode");
            if (darkModePref != null) {
                darkModePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        // Handle changes to the "Dark Mode" preference
                        boolean darkModeEnabled = (boolean) newValue;
                        // Implement your logic here
                        return true; // To update the preference's state
                    }
                });
            }

            EditTextPreference usernamePref = findPreference("username");
            if (usernamePref != null) {
                usernamePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        // Handle changes to the "Username" preference
                        String newUsername = (String) newValue;
                        // Implement your logic here
                        return true; // To update the preference's state
                    }
                });
            }

            Preference changePasswordPref = findPreference("changePassword");
            if (changePasswordPref != null) {
                changePasswordPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        // Handle the click on the "Change Password" preference
                        // Implement your logic here
                        return true;
                    }
                });
            }
        }
    }
}
