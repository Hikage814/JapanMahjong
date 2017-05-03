package com.hikage.japanmahjong;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Tsumo extends AppCompatActivity {

    SeekBar skBar_fu, skBar_han;
    TextView tv_tsumoInfo, tv_fu, tv_han, tv_point, tv_parentPay, tv_otherPay, tv_get;
    int han = 1, fu = 30, parentPay, otherPay, get,
            clickPlayer, round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        clickPlayer = bundle.getInt("clickPlayer");

        rotateView();
        getSupportActionBar().hide();

        findView();

        round = ConstantUtil.getRound();

        initTv();
        countPoint();

    }

    private void rotateView() {
        int orientation;
        switch (clickPlayer) {
            case 0:
                orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                setContentView(R.layout.tsumo_land);
                break;
            case 1:
                orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                setContentView(R.layout.tsumo);
                break;
            case 2:
                orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                setContentView(R.layout.tsumo_land);
                break;
            default:
                orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                setContentView(R.layout.tsumo);
                break;
        }
        setRequestedOrientation(orientation);
    }

    private void findView() {
        tv_tsumoInfo = (TextView) findViewById(R.id.tv_tsumoInfo);
        tv_fu = (TextView) findViewById(R.id.tv_fu);
        tv_han = (TextView) findViewById(R.id.tv_han);
        tv_point = (TextView) findViewById(R.id.tv_point);
        tv_parentPay = (TextView) findViewById(R.id.tv_parentPay);
        tv_otherPay = (TextView) findViewById(R.id.tv_otherPay);
        tv_get = (TextView) findViewById(R.id.tv_get);

        skBar_fu = (SeekBar) findViewById(R.id.skBar_fu);
        skBar_han = (SeekBar) findViewById(R.id.skBar_han);

        skBar_han.setOnSeekBarChangeListener(skBarListener);
        skBar_fu.setOnSeekBarChangeListener(skBarListener);
    }

    private void initTv() {
        if (clickPlayer == round)
            tv_tsumoInfo.setText("親家自摸");
        else
            tv_tsumoInfo.setText("閒家自摸");
    }

    private SeekBar.OnSeekBarChangeListener skBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progres, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.skBar_fu:
                    switch (progres) {
                        case 0:
                            fu = 0;
                            break;
                        case 1:
                            fu = 20;
                            break;
                        case 2:
                            fu = 25;
                            break;
                        default:
                            fu = progres * 10;
                            break;
                    }
                    if (han < 5) {
                        tv_fu.setText(fu + "符");
                    }
                    break;
                case R.id.skBar_han:
                    han = progres;
                    switch (han) {
                        case 5:
                            tv_fu.setText("滿貫");
                        case 6:
                        case 7:
                            tv_fu.setText("跳滿");
                        case 8:
                        case 9:
                        case 10:
                            tv_fu.setText("倍滿");
                        case 11:
                        case 12:
                            tv_fu.setText("三倍滿");
                        case 13:
                            tv_han.setText(han + "飜");
                            tv_fu.setText("役滿");
                            break;
                        case 14:
                            tv_han.setText("役滿");
                            //tv_fu.setText();看不懂
                            break;
                        case 15:
                            tv_han.setText("役滿");
                            //tv_fu.setText();看不懂
                            break;
                        default:
                            tv_han.setText(han + "飜");
                            tv_fu.setText(fu + "符");
                            break;
                    }
                    break;
            }
            countPoint();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void countPoint() {
        double a;
        switch (han) {
            case 15:
                a = 32000;
                break;
            case 14:
                a = 16000;
                break;
            case 13:
                a = 8000;
                break;
            case 12:
            case 11:
                a = 6000;
                break;
            case 10:
            case 9:
            case 8:
                a = 4000;
                break;
            case 7:
            case 6:
                a = 3000;
                break;
            case 5:
                a = 2000;
                break;
            case 0:
                a = 0;
                break;
            default:
                //基本點＝符×2（翻數+2）
                a = fu * Math.pow(2, han + 2);
                if (fu == 25 && han < 3)
                    a = 0;
                if (fu == 20 && han < 2)
                    a = 0;
                break;
        }

        if (round == clickPlayer) {
            parentPay = 0;

            otherPay = (int) a * 2;
            if (otherPay % 100 != 0)
                otherPay = (otherPay / 100 + 1) * 100;
            if (han < 5 && otherPay >= 4000)
                otherPay = 4000;
        } else {
            parentPay = (int) a * 2;
            if (parentPay % 100 != 0)
                parentPay = (parentPay / 100 + 1) * 100;
            if (han < 5 && parentPay >= 4000)
                parentPay = 4000;

            otherPay = (int) a;
            if (otherPay % 100 != 0)
                otherPay = (otherPay / 100 + 1) * 100;
            if (han < 5 && otherPay >= 2000)
                otherPay = 2000;
        }
        int sum;

        if (round == clickPlayer)
            sum = otherPay * 3;
        else
            sum = parentPay + otherPay * 2;

        tv_point.setText("" + sum);

        if (parentPay == 0)
            tv_parentPay.setText("親家支付：----點");
        else
            tv_parentPay.setText("親家支付：" + parentPay + "點");

        tv_otherPay.setText("閒家支付：" + otherPay + "點");

        get = sum + ConstantUtil.getRenchan() * ConstantUtil.getHonbaPoint() + ConstantUtil.getRichi() * 1000;
        tv_get.setText("獲得：" + get + "點");
    }

    public void onClickDone(View v) {
        if (otherPay != 0) {
            switch (clickPlayer) {
                case 0:
                    switch (round) {
                        case 1:
                            ConstantUtil.setPoint(get, -parentPay, -otherPay, -otherPay);
                            break;
                        case 2:
                            ConstantUtil.setPoint(get, -otherPay, -parentPay, -otherPay);
                            break;
                        case 3:
                            ConstantUtil.setPoint(get, -otherPay, -otherPay, -parentPay);
                            break;
                        default:
                            ConstantUtil.setPoint(get, -otherPay, -otherPay, -otherPay);
                    }
                    break;
                case 1:
                    switch (round) {
                        case 0:
                            ConstantUtil.setPoint(-parentPay, get, -otherPay, -otherPay);
                            break;
                        case 2:
                            ConstantUtil.setPoint(-otherPay, get, -parentPay, -otherPay);
                            break;
                        case 3:
                            ConstantUtil.setPoint(-otherPay, get, -otherPay, -parentPay);
                            break;
                        default:
                            ConstantUtil.setPoint(-otherPay, get, -otherPay, -otherPay);
                    }
                    break;
                case 2:
                    switch (round) {
                        case 0:
                            ConstantUtil.setPoint(-parentPay, -otherPay, get, -otherPay);
                            break;
                        case 1:
                            ConstantUtil.setPoint(-otherPay, -parentPay, get, -otherPay);
                            break;
                        case 3:
                            ConstantUtil.setPoint(-otherPay, -otherPay, get, -parentPay);
                            break;
                        default:
                            ConstantUtil.setPoint(-otherPay, -otherPay, get, -otherPay);
                    }
                    break;
                case 3:
                    switch (round) {
                        case 0:
                            ConstantUtil.setPoint(-parentPay, -otherPay, -otherPay, get);
                            break;
                        case 2:
                            ConstantUtil.setPoint(-otherPay, -parentPay, -otherPay, get);
                            break;
                        case 3:
                            ConstantUtil.setPoint(-otherPay, -otherPay, -parentPay, get);
                            break;
                        default:
                            ConstantUtil.setPoint(-otherPay, -otherPay, -otherPay, get);
                    }
                    break;
            }

            if (round == clickPlayer)
                ConstantUtil.setRenchan();
            else {
                ConstantUtil.setRound();
                ConstantUtil.setRenchan(0);
                //deposition
            }
            ConstantUtil.setRichi(0);
            ConstantUtil.setRichiStatus();

            finish();
        } else
            Toast.makeText(v.getContext(), "請選取符胡翻數", Toast.LENGTH_SHORT).show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
