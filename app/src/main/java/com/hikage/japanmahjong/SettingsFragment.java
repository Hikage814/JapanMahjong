package com.hikage.japanmahjong;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_PREF_SET_POINT = "pref_setPoint";
    public static final String KEY_PREF_RETURN_POINT = "pref_returnPoint";
    public static final String KEY_PREF_HONBA_POINT = "pref_honbaPoint";
    public static final String KEY_PREF_LAST_WIND = "pref_lastWind";
    public static final String KEY_PREF_EAST_RENCHAN = "pref_eastRenchan";
    public static final String KEY_PREF_SOUTH_RENCHAN = "pref_southRenchan";

    public static final String KEY = "pref_key";
    public static final String KEY_PREF_GAME = "pref_game";
    public static final String KEY_PREF_PLAYER0 = "pref_player0";
    public static final String KEY_PREF_PLAYER1 = "pref_player1";
    public static final String KEY_PREF_PLAYER2 = "pref_player2";
    public static final String KEY_PREF_PLAYER3 = "pref_player3";
    public static final String KEY_PREF_POINT0 = "pref_point0";
    public static final String KEY_PREF_POINT1 = "pref_point1";
    public static final String KEY_PREF_POINT2 = "pref_point2";
    public static final String KEY_PREF_POINT3 = "pref_point3";
    public static final String KEY_PREF_ROUND = "pref_round";
    public static final String KEY_PREF_WIND = "pref_wind";
    public static final String KEY_PREF_RICHI = "pref_richi";
    public static final String KEY_PREF_RENCHAN = "pref_renchan";

    private CheckBoxPreference pref_southRenchan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        findView();
        initView();

    }

    private void findView() {
        pref_southRenchan = (CheckBoxPreference) findPreference(KEY_PREF_SOUTH_RENCHAN);
    }

    private void initView() {
        SharedPreferences settings = getPreferenceScreen().getSharedPreferences();
        if (settings.getBoolean(KEY_PREF_LAST_WIND,false))
            pref_southRenchan.setEnabled(true);
        else
            pref_southRenchan.setEnabled(false);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals(KEY_PREF_LAST_WIND)) {
            if (sharedPreferences.getBoolean(key, false))
                pref_southRenchan.setEnabled(true);
            else {
                pref_southRenchan.setEnabled(false);
                pref_southRenchan.setChecked(false);
            }
        }
    }

    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }
}
