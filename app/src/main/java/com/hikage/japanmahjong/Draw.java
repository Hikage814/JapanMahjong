package com.hikage.japanmahjong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Draw extends AppCompatActivity {


    CheckBox chk_player0, chk_player1, chk_player2, chk_player3, chk_abortiveDraws;
    boolean[] chkStatus = new boolean[5];
    String[] player = new String[4];
    int sumChk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw);

        getSupportActionBar().hide();

        findView();

        for (int i = 0; i < 4; i++)
            player[i] = ConstantUtil.getPlayer(i);

        initChk();
    }

    private void findView() {
        chk_player0 = (CheckBox) findViewById(R.id.chk_player0);
        chk_player1 = (CheckBox) findViewById(R.id.chk_player1);
        chk_player2 = (CheckBox) findViewById(R.id.chk_player2);
        chk_player3 = (CheckBox) findViewById(R.id.chk_player3);
        chk_abortiveDraws = (CheckBox) findViewById(R.id.chk_abortiveDraws);

        chk_player0.setOnCheckedChangeListener(chkListener);
        chk_player1.setOnCheckedChangeListener(chkListener);
        chk_player2.setOnCheckedChangeListener(chkListener);
        chk_player3.setOnCheckedChangeListener(chkListener);
        chk_abortiveDraws.setOnCheckedChangeListener(chkListener);
    }

    private void initChk() {
        chk_player0.setText(player[0]);
        chk_player1.setText(player[1]);
        chk_player2.setText(player[2]);
        chk_player3.setText(player[3]);
    }

    private CheckBox.OnCheckedChangeListener chkListener = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int n = 0;
            if (chk_player0.isChecked()) {
                chkStatus[0] = true;
                n++;
            } else
                chkStatus[0] = false;
            if (chk_player1.isChecked()) {
                chkStatus[1] = true;
                n++;
            } else
                chkStatus[1] = false;
            if (chk_player2.isChecked()) {
                chkStatus[2] = true;
                n++;
            } else
                chkStatus[2] = false;
            if (chk_player3.isChecked()) {
                chkStatus[3] = true;
                n++;
            } else
                chkStatus[3] = false;

            if (chk_abortiveDraws.isChecked())
                chkStatus[4] = true;
            else
                chkStatus[4] = false;

            sumChk = n;
        }
    };

    public void onClickDone(View v) {

        boolean renchan = false;

        if (chkStatus[4]) {
            for (int i = 0; i < 4; i++) {
                if (ConstantUtil.getRichiStatus(i)) {
                    ConstantUtil.setRichi(false);
                    ConstantUtil.setRichiStatus(i, false);
                    ConstantUtil.setPoint(i, 1000);
                }
            }
            finish();
        } else {
            switch (sumChk) {
                case 0:
                    break;
                case 1:
                    for (int i = 0; i < 4; i++) {
                        if (chkStatus[i]) {
                            ConstantUtil.setPoint(i, 3000);
                            if (ConstantUtil.getRound() == i)
                                renchan = true;
                        } else
                            ConstantUtil.setPoint(i, -1000);
                    }
                    break;
                case 2:
                    for (int i = 0; i < 4; i++) {
                        if (chkStatus[i]) {
                            ConstantUtil.setPoint(i, 1500);
                            if (ConstantUtil.getRound() == i)
                                renchan = true;
                        } else
                            ConstantUtil.setPoint(i, -1500);
                    }
                    break;
                case 3:
                    for (int i = 0; i < 4; i++) {
                        if (chkStatus[i]) {
                            ConstantUtil.setPoint(i, 1000);
                            if (ConstantUtil.getRound() == i)
                                renchan = true;
                        } else
                            ConstantUtil.setPoint(i, -3000);
                    }
                    break;
                default:
                    renchan = true;
                    break;
            }
        }

        switch (ConstantUtil.getWind()) {
            case 0:
                if (ConstantUtil.getEastRenchan() == false)
                    renchan = false;
                break;
            case 1:
                if (ConstantUtil.getSouthRenchan() == false)
                    renchan = false;
                break;
        }

        if (renchan)
            ConstantUtil.setRenchan();
        else {
            ConstantUtil.setRound();
            ConstantUtil.setRenchan(0);
        }
        ConstantUtil.setRichiStatus();

        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
