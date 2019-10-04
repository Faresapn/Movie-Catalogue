package com.example.submisi5.Activity;


import android.content.SharedPreferences;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.submisi5.R;
import com.example.submisi5.alarm.AlarmManager;

import java.util.Objects;

import static com.example.submisi5.alarm.AlarmManager.IS_DAILY_REMINDER;
import static com.example.submisi5.alarm.AlarmManager.IS_RELEASE_REMINDER;

public class SettingsActivity extends AppCompatActivity {
    static AlarmManager release_reciever;
    static AlarmManager daily_receiver;
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


    }


    public static class SettingsFragment extends PreferenceFragmentCompat {
        SwitchPreferenceCompat release,daily;
        SharedPreferences sharedPreferences;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
            sharedPreferences = Objects.requireNonNull(getActivity()).getPreferences(0);
            release = findPreference(getResources().getString(R.string.release_notif));
            daily = findPreference(getResources().getString(R.string.daily_notif));
            release.setOnPreferenceClickListener(preference -> {
                if (release.isChecked()){
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putBoolean(IS_RELEASE_REMINDER,true);
                    editor.apply();
                    release_reciever = new AlarmManager();
                    release_reciever.release_setalarmmanager(getContext());
                }else if (!release.isChecked()){
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putBoolean(IS_RELEASE_REMINDER,false);
                    editor.apply();
                    release_reciever = new AlarmManager();
                    release_reciever.release_setcancel(getContext());
                }
                return false;
            });
            daily.setOnPreferenceClickListener(preference -> {
                if (daily.isChecked()){
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putBoolean(IS_DAILY_REMINDER,true);
                    editor.apply();
                    daily_receiver = new AlarmManager();
                    daily_receiver.daily_setalarmmanager(getContext());
                }else if (!daily.isChecked()){
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putBoolean(IS_DAILY_REMINDER,false);
                    editor.apply();
                    daily_receiver = new AlarmManager();
                    daily_receiver.daily_setcancel(getContext());
                }
                return false;
            });

        }
    }


}