package com.hikage.japanmahjong;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/***********************
 memo

 畫面改版
 ***********************/

public class MainActivity extends AppCompatActivity {

    SharedPreferences settings, gameLog;
    Intent intent;
    boolean game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getSupportActionBar().hide();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);//設定預設值

        intent = new Intent();
    }

    protected void onResume() {
        super.onResume();
        gameLog = getSharedPreferences(SettingsFragment.KEY, 0);
        game = gameLog.getBoolean(SettingsFragment.KEY_PREF_GAME, false);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public void onClickMenuItem(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                if (game)
                    showDialog(AlertDialogFragment.KEY_CONTINUE);
                else {
                    setGame();
                    intent.setClass(this, SetPlayer.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_option:
                if (game)
                    showDialog(AlertDialogFragment.KEY_CHANGESETTING);
                else {
                    intent.setClass(this, Settings.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_explain:
                intent.setClass(this, Explain.class);
                startActivity(intent);
                break;
            case R.id.btn_log:
                intent.setClass(this, Logs.class);
                startActivity(intent);
                break;
        }
    }

    private void setGame() {

        if (game) {
            String player[] = new String[4];
            player[0] = gameLog.getString(SettingsFragment.KEY_PREF_PLAYER0, "");
            player[1] = gameLog.getString(SettingsFragment.KEY_PREF_PLAYER1, "");
            player[2] = gameLog.getString(SettingsFragment.KEY_PREF_PLAYER2, "");
            player[3] = gameLog.getString(SettingsFragment.KEY_PREF_PLAYER3, "");

            int point[] = new int[4];
            point[0] = gameLog.getInt(SettingsFragment.KEY_PREF_POINT0, 0);
            point[1] = gameLog.getInt(SettingsFragment.KEY_PREF_POINT1, 0);
            point[2] = gameLog.getInt(SettingsFragment.KEY_PREF_POINT2, 0);
            point[3] = gameLog.getInt(SettingsFragment.KEY_PREF_POINT3, 0);

            int round = gameLog.getInt(SettingsFragment.KEY_PREF_ROUND, 0);
            int wind = gameLog.getInt(SettingsFragment.KEY_PREF_WIND, 0);
            int richi = gameLog.getInt(SettingsFragment.KEY_PREF_RICHI, 0);
            int renchan = gameLog.getInt(SettingsFragment.KEY_PREF_RENCHAN, 0);

            ConstantUtil.setGame(player, point, round, wind, richi, renchan);
        }

        int setPoint = Integer.valueOf(settings.getString(SettingsFragment.KEY_PREF_SET_POINT, "") + "00");
        int returnPoint = Integer.valueOf(settings.getString(SettingsFragment.KEY_PREF_RETURN_POINT, "") + "00");
        boolean lastWind = settings.getBoolean(SettingsFragment.KEY_PREF_LAST_WIND, false);
        boolean eastRenchan = settings.getBoolean(SettingsFragment.KEY_PREF_EAST_RENCHAN, true);
        boolean southRenchan = settings.getBoolean(SettingsFragment.KEY_PREF_SOUTH_RENCHAN, true);
        int honbaPoint = Integer.valueOf(settings.getString(SettingsFragment.KEY_PREF_HONBA_POINT, "") + "00");

        ConstantUtil.setRule(setPoint, returnPoint, honbaPoint, lastWind, eastRenchan, southRenchan);
    }


    private void showDialog(String key) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null)
            ft.remove(prev);
        ft.addToBackStack(null);
        DialogFragment newFragment = AlertDialogFragment.newInstance(key, null);
        newFragment.show(ft, "dialog");
    }

    public void doPositiveClick(String id) {
        if (id.equals(AlertDialogFragment.KEY_CONTINUE)) {
            setGame();
            intent.setClass(this, Game.class);
            startActivity(intent);
        } else if (id.equals(AlertDialogFragment.KEY_CHANGESETTING)) {
            gameLog.edit()
                    .putBoolean(SettingsFragment.KEY_PREF_GAME, false)
                    .apply();
            intent.setClass(this, Settings.class);
            startActivity(intent);
        }
    }

    public void doNegativeClick(String id) {
        if (id.equals(AlertDialogFragment.KEY_CONTINUE)) {
            intent.setClass(this, SetPlayer.class);
            startActivity(intent);
        }
    }
}

