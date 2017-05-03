package com.hikage.japanmahjong;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    SharedPreferences gameLog;
    private TextView tv_no1Name, tv_no2Name, tv_no3Name, tv_no4Name,
            tv_no1Point, tv_no2Point, tv_no3Point, tv_no4Point,
            tv_no1Score, tv_no2Score, tv_no3Score, tv_no4Score,
            tv_retuenPoint;

    private String[] player = new String[4];
    private int[] point = new int[4];
    private String[] score = new String[4];
    private int returnPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        getSupportActionBar().hide();

        findView();

        getInfo();
        insertionSort();
        printInfo();

        gameLog = getSharedPreferences(SettingsFragment.KEY, 0);
        gameLog.edit()
                .putBoolean(SettingsFragment.KEY_PREF_GAME, false)
                .apply();
    }

    protected void onStop() {
        super.onStop();
        GameLogDAO gameLogDAO = new GameLogDAO(getApplicationContext());
        GameLog gameLog = new GameLog(player, score);
        gameLogDAO.insert(gameLog);
    }

    private void findView() {
        tv_retuenPoint = (TextView) findViewById(R.id.tv_retuenPoint);

        tv_no1Name = (TextView) findViewById(R.id.tv_no1Name);
        tv_no2Name = (TextView) findViewById(R.id.tv_no2Name);
        tv_no3Name = (TextView) findViewById(R.id.tv_no3Name);
        tv_no4Name = (TextView) findViewById(R.id.tv_no4Name);

        tv_no1Point = (TextView) findViewById(R.id.tv_no1Point);
        tv_no2Point = (TextView) findViewById(R.id.tv_no2Point);
        tv_no3Point = (TextView) findViewById(R.id.tv_no3Point);
        tv_no4Point = (TextView) findViewById(R.id.tv_no4Point);

        tv_no1Score = (TextView) findViewById(R.id.tv_no1Score);
        tv_no2Score = (TextView) findViewById(R.id.tv_no2Score);
        tv_no3Score = (TextView) findViewById(R.id.tv_no3Score);
        tv_no4Score = (TextView) findViewById(R.id.tv_no4Score);
    }

    private void getInfo() {
        for (int i = 0; i < 4; i++) {
            point[i] = ConstantUtil.getPoint(i);
            player[i] = ConstantUtil.getPlayer(i);
        }

        returnPoint = ConstantUtil.getReturnPoint();
    }

    private void printInfo() {
        for (int i = 0; i < 4; i++) {
            int j = point[i];
            if (j >= returnPoint)
                score[i] = (j / 1000) - (returnPoint / 1000) + "." + (j / 100 % 10);
            else if (j >= 0 && j < returnPoint)
                score[i] = ((j - returnPoint) / 1000) + "." + -((j - 30000) / 100 % 10);
            else {
                j = -j;
                score[i] = "-" + ((j / 1000) + (returnPoint / 1000)) + "." + (j / 100 % 10);
            }
        }

        int[] rank = {1, 2, 3, 4};
        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                if (rank[i] == rank[j])
                    break;
                else if (point[i] == point[j]) {
                    rank[j] = rank[i];
                }
            }
        }

        tv_retuenPoint.setText("返還點數：" + returnPoint);

        tv_no1Name.setText(rank[0] + "位：" + player[0]);
        tv_no1Point.setText("持有點數：" + point[0]);
        tv_no1Score.setText("得點：" + score[0]);

        tv_no2Name.setText(rank[1] + "位：" + player[1]);
        tv_no2Point.setText("持有點數：" + point[1]);
        tv_no2Score.setText("得點：" + score[1]);

        tv_no3Name.setText(rank[2] + "位：" + player[2]);
        tv_no3Point.setText("持有點數：" + point[2]);
        tv_no3Score.setText("得點：" + score[2]);

        tv_no4Name.setText(rank[3] + "位：" + player[3]);
        tv_no4Point.setText("持有點數：" + point[3]);
        tv_no4Score.setText("得點：" + score[3]);
    }

    public void insertionSort() {

        for (int i = 0; i < 4 - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (point[j - 1] <= point[j])
                    break;
                int temp1 = point[j];
                point[j] = point[j - 1];
                point[j - 1] = temp1;

                String temp2 = player[j];
                player[j] = player[j - 1];
                player[j - 1] = temp2;
            }
        }

        for (int i = 0; i < 2; i++) {
            int temp1 = point[i];
            point[i] = point[4 - i - 1];
            point[4 - i - 1] = temp1;

            String temp2 = player[i];
            player[i] = player[4 - i - 1];
            player[4 - i - 1] = temp2;
        }
    }

    public void onClickEnd(View v) {
        finish();
    }

}
