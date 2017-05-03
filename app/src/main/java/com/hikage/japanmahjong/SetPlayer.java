package com.hikage.japanmahjong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class SetPlayer extends AppCompatActivity {

    SharedPreferences gameLog;
    EditText edtTxt1, edtTxt2, edtTxt3, edtTxt4;
    Intent intent = new Intent();

    int ran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_player);

        getSupportActionBar().hide();

        findView();

        ConstantUtil.initGame();

        gameLog = getSharedPreferences(SettingsFragment.KEY, 0);
        gameLog.edit()
                .putBoolean(SettingsFragment.KEY_PREF_GAME, false)
                .apply();
    }

    private void findView() {
        edtTxt1 = (EditText) findViewById(R.id.edtTxt1);
        edtTxt2 = (EditText) findViewById(R.id.edtTxt2);
        edtTxt3 = (EditText) findViewById(R.id.edtTxt3);
        edtTxt4 = (EditText) findViewById(R.id.edtTxt4);
    }

    public void setGame(View v) {

        ConstantUtil.setPlayer(edtTxt1.getText().toString(), edtTxt2.getText().toString(),
                edtTxt3.getText().toString(), edtTxt4.getText().toString());

        intent.setClass(this, SetGame.class);
        startActivity(intent);
        finish();

    }

    public void autoSetGame(View v) {

        String[] sit = new String[4];
        String[] player = new String[4];
        boolean[] wind = {true, true, true, true};

        player[0] = edtTxt1.getText().toString();
        player[1] = edtTxt2.getText().toString();
        player[2] = edtTxt3.getText().toString();
        player[3] = edtTxt4.getText().toString();

        for (int i = 0; i < 4; i++) {
            ran = (int) (Math.random() * 4);
            if (wind[ran]) {
                sit[i] = player[ran];
                wind[ran] = false;
            } else
                i--;
        }

        ran = (int) (Math.random() * 4);
        int j = 0;
        for (int i = ran; i < ran + 4; i++) {
            int k = i;
            if (k > 3)
                k -= 4;
            player[j] = sit[k];
            j++;
        }

        ConstantUtil.setPlayer(player[0], player[1], player[2], player[3]);

        intent.setClass(this, Game.class);
        startActivity(intent);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onClickCancel(View view) {
        finish();
    }
}
