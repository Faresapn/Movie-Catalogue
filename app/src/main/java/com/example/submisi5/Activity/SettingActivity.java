package com.example.submisi5.Activity;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.submisi5.R;
import com.example.submisi5.alarm.ReminderActivity;
import static com.example.submisi5.alarm.ReminderActivity.IS_DAILY_REMINDER;
import static com.example.submisi5.alarm.ReminderActivity.IS_RELEASE_REMINDER;

public class SettingActivity extends AppCompatActivity  {
    static ReminderActivity reminderActivity,daily_receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        settoolbar();
    }


    private void settoolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_setting);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        });
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        SwitchPreferenceCompat release,daily;
        SharedPreferences SP;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
            SP = getActivity().getPreferences(0);
            release = findPreference(getResources().getString(R.string.rilis_notif));
            daily = findPreference(getResources().getString(R.string.daily_notif));
            release.setOnPreferenceClickListener(preference -> {
                if (release.isChecked()){
                    SharedPreferences.Editor editor =SP.edit();
                    editor.putBoolean(IS_RELEASE_REMINDER,true);
                    editor.apply();
                    reminderActivity = new ReminderActivity();
                    reminderActivity.release_setalarmmanager(getContext());
                }else if (!release.isChecked()){
                    SharedPreferences.Editor editor =SP.edit();
                    editor.putBoolean(IS_RELEASE_REMINDER,false);
                    editor.apply();
                    reminderActivity = new ReminderActivity();
                    reminderActivity.release_setcancel(getContext());
                }
                return false;
            });
            daily.setOnPreferenceClickListener(preference -> {
                if (daily.isChecked()){
                    SharedPreferences.Editor editor =SP.edit();
                    editor.putBoolean(IS_DAILY_REMINDER,true);
                    editor.apply();
                    daily_receiver = new ReminderActivity();
                    daily_receiver.daily_setalarmmanager(getContext());
                }else if (!daily.isChecked()){
                    SharedPreferences.Editor editor =SP.edit();
                    editor.putBoolean(IS_DAILY_REMINDER,false);
                    editor.apply();
                    daily_receiver = new ReminderActivity();
                    daily_receiver.daily_setcancel(getContext());
                }
                return false;
            });

        }
    }

}