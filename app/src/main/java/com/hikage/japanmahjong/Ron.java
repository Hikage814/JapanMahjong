package com.hikage.japanmahjong;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class Ron extends AppCompatActivity {

    RadioGroup rdoGroup;
    RadioButton rdoBtn1, rdoBtn2, rdoBtn3;
    SeekBar skBar_fu, skBar_han;
    TextView tv_fu, tv_han, tv_point, tv_pay, tv_get;
    String[] player = new String[4];
    int han = 1, fu = 30, pay, get,
            clickPlayer, round, rdoStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        clickPlayer = bundle.getInt("clickPlayer");

        rotateView();
        getSupportActionBar().hide();
        findView();

        for (int i = 0; i < 4; i++)
            player[i] = ConstantUtil.getPlayer(i);
        round = ConstantUtil.getRound() ;

        initRdoBtn();
        countPoint();

    }

    private void rotateView() {
        int orientation;
        switch (clickPlayer) {
            case 0:
                orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                setContentView(R.layout.ron_land);
                break;
            case 1:
                orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                setContentView(R.layout.ron);
                break;
            case 2:
                orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                setContentView(R.layout.ron_land);
                break;
            default:
                orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                setContentView(R.layout.ron);
                break;
        }
        setRequestedOrientation(orientation);
    }

    private void findView() {
        rdoGroup = (RadioGroup) findViewById(R.id.rdoGroup);
        rdoBtn1 = (RadioButton) findViewById(R.id.rdoBtn1);
        rdoBtn2 = (RadioButton) findViewById(R.id.rdoBtn2);
        rdoBtn3 = (RadioButton) findViewById(R.id.rdoBtn3);

        tv_fu = (TextView) findViewById(R.id.tv_fu);
        tv_han = (TextView) findViewById(R.id.tv_han);
        tv_point = (TextView) findViewById(R.id.tv_point);
        tv_pay = (TextView) findViewById(R.id.tv_pay);
        tv_get = (TextView) findViewById(R.id.tv_get);

        skBar_fu = (SeekBar) findViewById(R.id.skBar_fu);
        skBar_han = (SeekBar) findViewById(R.id.skBar_han);

        skBar_han.setOnSeekBarChangeListener(skBarListener);
        skBar_fu.setOnSeekBarChangeListener(skBarListener);
        rdoGroup.setOnCheckedChangeListener(rdoGroupListener);
    }

    private void initRdoBtn() {

        switch (clickPlayer) {
            case 0:
                rdoBtn1.setText(player[1]);
                rdoBtn2.setText(player[2]);
                rdoBtn3.setText(player[3]);
                break;
            case 1:
                rdoBtn1.setText(player[0]);
                rdoBtn2.setText(player[2]);
                rdoBtn3.setText(player[3]);
                break;
            case 2:
                rdoBtn1.setText(player[0]);
                rdoBtn2.setText(player[1]);
                rdoBtn3.setText(player[3]);
                break;
            default:
                rdoBtn1.setText(player[0]);
                rdoBtn2.setText(player[1]);
                rdoBtn3.setText(player[2]);
                break;
        }
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

    private RadioGroup.OnCheckedChangeListener rdoGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rdoBtn1:
                    rdoStatus = 1;
                    break;
                case R.id.rdoBtn2:
                    rdoStatus = 2;
                    break;
                case R.id.rdoBtn3:
                    rdoStatus = 3;
                    break;

            }
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
                if (fu > 20) {//基本點＝符×2（翻數+2）
                    a = fu * Math.pow(2, han + 2);
                    if (fu == 25 && han < 2)
                        a = 0;
                } else
                    a = 0;
                break;
        }

        if (round == clickPlayer) {
            pay = (int) a * 6;


            if (pay % 100 != 0)
                pay = (pay / 100 + 1) * 100;

            if (han < 5 && pay >= 12000)
                pay = 12000;

        } else {
            pay = (int) a * 4;
            if (pay % 100 != 0)
                pay = (pay / 100 + 1) * 100;
            if (han < 5 && pay >= 8000)
                pay = 8000;
        }

        tv_point.setText("" + pay);

        tv_pay.setText("支付：" + pay + "點");

        get = pay + ConstantUtil.getRenchan() * ConstantUtil.getHonbaPoint() * 100 + ConstantUtil.getRichi() * 1000;
        tv_get.setText("獲得：" + get + "點");
    }

    public void onClickDone(View v) {
        if (rdoStatus != 0) {
            if (pay != 0) {
                switch (clickPlayer) {
                    case 0:
                        switch (rdoStatus) {
                            case 1:
                                ConstantUtil.setPoint(1, -pay);
                                break;
                            case 2:
                                ConstantUtil.setPoint(2, -pay);
                                break;
                            case 3:
                                ConstantUtil.setPoint(3, -pay);
                                break;
                        }
                        ConstantUtil.setPoint(0, get);
                        break;
                    case 1:
                        switch (rdoStatus) {
                            case 1:
                                ConstantUtil.setPoint(0, -pay);
                                break;
                            case 2:
                                ConstantUtil.setPoint(2, -pay);
                                break;
                            case 3:
                                ConstantUtil.setPoint(3, -pay);
                                break;
                        }
                        ConstantUtil.setPoint(1, get);
                        break;
                    case 2:
                        switch (rdoStatus) {
                            case 1:
                                ConstantUtil.setPoint(0, -pay);
                                break;
                            case 2:
                                ConstantUtil.setPoint(1, -pay);
                                break;
                            case 3:
                                ConstantUtil.setPoint(3, -pay);
                                break;
                        }
                        ConstantUtil.setPoint(2, get);
                        break;
                    default:
                        switch (rdoStatus) {
                            case 1:
                                ConstantUtil.setPoint(0, -pay);
                                break;
                            case 2:
                                ConstantUtil.setPoint(1, -pay);
                                break;
                            case 3:
                                ConstantUtil.setPoint(2, -pay);
                                break;
                        }
                        ConstantUtil.setPoint(3, get);
                        break;
                }
                if (round == clickPlayer)
                    ConstantUtil.setRenchan();
                else {
                    ConstantUtil.setRound();
                    ConstantUtil.setRenchan(0);
                }

                ConstantUtil.setRichi(0);
                ConstantUtil.setRichiStatus();

                finish();
            } else
                Toast.makeText(v.getContext(), "請選取符胡翻數", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(v.getContext(), "請選取放槍者", Toast.LENGTH_SHORT).show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}




