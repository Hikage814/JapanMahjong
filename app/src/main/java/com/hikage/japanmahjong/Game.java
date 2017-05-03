package com.hikage.japanmahjong;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends AppCompatActivity {

    SharedPreferences gameLog;
    private ImageView img_player0, img_player1, img_player2, img_player3,
            img_arrow0, img_arrow1, img_arrow2, img_arrow3,
            img_richi0, img_richi1, img_richi2, img_richi3,
            img_dice1, img_dice2;
    private TextView tv_gameRound, tv_throwDice, tv_richi, tv_renchan,
            tv_name0, tv_name1, tv_name2, tv_name3,
            tv_point0, tv_point1, tv_point2, tv_point3;
    private Intent intent;
    private static int clickPlayer;
    private boolean startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        getSupportActionBar().hide();
        findView();
        gameLog = getSharedPreferences(SettingsFragment.KEY, 0);
        intent = new Intent();
    }

    protected void onResume() {
        super.onResume();
        checkConfiguration();
        startGame = true;
        checkGameEnd();
        setPointInfo();
        setGameInfo();
    }

    private void findView() {
        img_player0 = (ImageView) findViewById(R.id.img_player0);
        img_player1 = (ImageView) findViewById(R.id.img_player1);
        img_player2 = (ImageView) findViewById(R.id.img_player2);
        img_player3 = (ImageView) findViewById(R.id.img_player3);


        img_arrow0 = (ImageView) findViewById(R.id.img_arrow0);
        img_arrow1 = (ImageView) findViewById(R.id.img_arrow1);
        img_arrow2 = (ImageView) findViewById(R.id.img_arrow2);
        img_arrow3 = (ImageView) findViewById(R.id.img_arrow3);

        img_richi0 = (ImageView) findViewById(R.id.img_richi0);
        img_richi1 = (ImageView) findViewById(R.id.img_richi1);
        img_richi2 = (ImageView) findViewById(R.id.img_richi2);
        img_richi3 = (ImageView) findViewById(R.id.img_richi3);

        img_dice1 = (ImageView) findViewById(R.id.img_dice1);
        img_dice2 = (ImageView) findViewById(R.id.img_dice2);

        tv_gameRound = (TextView) findViewById(R.id.tv_gameRound);
        tv_throwDice = (TextView) findViewById(R.id.tv_throwDice);
        tv_richi = (TextView) findViewById(R.id.tv_richi);
        tv_renchan = (TextView) findViewById(R.id.tv_renchan);

        tv_name0 = (TextView) findViewById(R.id.tv_name0);
        tv_name1 = (TextView) findViewById(R.id.tv_name1);
        tv_name2 = (TextView) findViewById(R.id.tv_name2);
        tv_name3 = (TextView) findViewById(R.id.tv_name3);

        tv_point0 = (TextView) findViewById(R.id.tv_point0);
        tv_point1 = (TextView) findViewById(R.id.tv_point1);
        tv_point2 = (TextView) findViewById(R.id.tv_point2);
        tv_point3 = (TextView) findViewById(R.id.tv_point3);

        registerForContextMenu(img_player0);
        registerForContextMenu(img_player1);
        registerForContextMenu(img_player2);
        registerForContextMenu(img_player3);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_gameRound:
                showDialog(AlertDialogFragment.KEY_DRAWGAME);
                break;
            case R.id.img_player0:
                clickPlayer = 0;
                showDialog(AlertDialogFragment.KEY_CLICKMENU);
                break;
            case R.id.img_player1:
                clickPlayer = 1;
                showDialog(AlertDialogFragment.KEY_CLICKMENU);
                break;
            case R.id.img_player2:
                clickPlayer = 2;
                showDialog(AlertDialogFragment.KEY_CLICKMENU);
                break;
            case R.id.img_player3:
                clickPlayer = 3;
                showDialog(AlertDialogFragment.KEY_CLICKMENU);
                break;
            case R.id.lv_dice:
                int dice1, dice2, count;
                dice1 = (int) (Math.random() * 5 + 1);
                dice2 = (int) (Math.random() * 5 + 1);
                count = (dice1 + dice2 + ConstantUtil.getRound()) % 4;

                setDice(img_dice1, dice1);
                setDice(img_dice2, dice2);

                img_arrow0.setVisibility(View.INVISIBLE);
                img_arrow1.setVisibility(View.INVISIBLE);
                img_arrow2.setVisibility(View.INVISIBLE);
                img_arrow3.setVisibility(View.INVISIBLE);

                switch (count) {
                    case 0:
                        img_arrow3.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        img_arrow0.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        img_arrow1.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        img_arrow2.setVisibility(View.VISIBLE);
                        break;
                }

                if (tv_throwDice.getVisibility() == View.VISIBLE)
                    tv_throwDice.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void checkGameEnd() {

        int lastWind;
        boolean endFlag = false;

        if (ConstantUtil.getLastWind() == false)
            lastWind = 0;
        else
            lastWind = 1;

        for (int i = 0; i < 4; i++) {
            if (ConstantUtil.getPoint(i) < 0 ||
                    (ConstantUtil.getWind() > lastWind && ConstantUtil.getPoint(i) >= ConstantUtil.getReturnPoint()))
                endFlag = true;
        }

        if (endFlag) {
            gameLog.edit()
                    .putBoolean(SettingsFragment.KEY_PREF_GAME, false)
                    .apply();
            intent.setClass(this, Result.class);
            startActivity(intent);
            finish();
        }
    }

    private void setPointInfo() {
        tv_point0.setText("" + ConstantUtil.getPoint(0));
        tv_point1.setText("" + ConstantUtil.getPoint(1));
        tv_point2.setText("" + ConstantUtil.getPoint(2));
        tv_point3.setText("" + ConstantUtil.getPoint(3));
    }

    private void setGameInfo() {
        if (startGame) {
            startGame = false;

            tv_throwDice.setVisibility(View.VISIBLE);

            img_arrow0.setVisibility(View.INVISIBLE);
            img_arrow1.setVisibility(View.INVISIBLE);
            img_arrow2.setVisibility(View.INVISIBLE);
            img_arrow3.setVisibility(View.INVISIBLE);

            tv_name0.setText(ConstantUtil.getPlayer(0));
            tv_name1.setText(ConstantUtil.getPlayer(1));
            tv_name2.setText(ConstantUtil.getPlayer(2));
            tv_name3.setText(ConstantUtil.getPlayer(3));

            tv_name0.setTextColor(Color.WHITE);
            tv_name1.setTextColor(Color.WHITE);
            tv_name2.setTextColor(Color.WHITE);
            tv_name3.setTextColor(Color.WHITE);

            switch (ConstantUtil.getWind() % 4) {
                case 0:
                    tv_gameRound.setText("東");
                    break;
                case 1:
                    tv_gameRound.setText("南");
                    break;
                case 2:
                    tv_gameRound.setText("西");
                    break;
                default:
                    tv_gameRound.setText("北");
                    break;
            }
            switch (ConstantUtil.getRound()) {
                case 0:
                    tv_gameRound.setRotation(90);
                    tv_name0.setTextColor(Color.RED);
                    break;
                case 1:
                    tv_gameRound.setRotation(0);
                    tv_name1.setTextColor(Color.RED);
                    break;
                case 2:
                    tv_gameRound.setRotation(270);
                    tv_name2.setTextColor(Color.RED);
                    break;
                default:
                    tv_gameRound.setRotation(180);
                    tv_name3.setTextColor(Color.RED);
                    break;
            }
/*
            String[] CHTNum = {"十", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
            int round = ConstantUtil.getRound() + 1;
            if (round > 19) {
                tv_gameRound.setText(tv_gameRound.getText() + "" + ConstantUtil.getRound());
            } else if (round > 9) {
                int i = round % 10;
                int j = round / 10;
                tv_gameRound.setText(tv_gameRound.getText() + CHTNum[i] + CHTNum[j]);
            } else
                tv_gameRound.setText(tv_gameRound.getText() + CHTNum[round]);
                tv_gameRound.setText(tv_gameRound.getText() + "局");
*/
            String[] CHTNum = {"一", "二", "三", "四"};
            int round = ConstantUtil.getRound();
            tv_gameRound.setText(tv_gameRound.getText() + CHTNum[round] + "局");

            tv_renchan.setText(" x " + ConstantUtil.getRenchan());
        }
        tv_richi.setText(" x " + ConstantUtil.getRichi());
        for (int i = 0; i < 4; i++) {
            ImageView img;
            switch (i) {
                case 0:
                    img = img_richi0;
                    break;
                case 1:
                    img = img_richi1;
                    break;
                case 2:
                    img = img_richi2;
                    break;
                default:
                    img = img_richi3;
                    break;
            }
            if (ConstantUtil.getRichiStatus(i))
                img.setVisibility(View.VISIBLE);
            else
                img.setVisibility(View.INVISIBLE);

        }

    }

    private void setDice(ImageView img, int dice) {
        switch (dice) {
            case 1:
                img.setImageResource(R.mipmap.dice_1);
                break;
            case 2:
                img.setImageResource(R.mipmap.dice_2);
                break;
            case 3:
                img.setImageResource(R.mipmap.dice_3);
                break;
            case 4:
                img.setImageResource(R.mipmap.dice_4);
                break;
            case 5:
                img.setImageResource(R.mipmap.dice_5);
                break;
            default:
                img.setImageResource(R.mipmap.dice_6);
                break;
        }
    }

    boolean contextSelected = false;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_game, menu);
        switch (v.getId()) {
            case R.id.img_player0:
                menu.setHeaderTitle(ConstantUtil.getPlayer(0));
                clickPlayer = 0;
                break;
            case R.id.img_player1:
                menu.setHeaderTitle(ConstantUtil.getPlayer(1));
                clickPlayer = 1;
                break;
            case R.id.img_player2:
                menu.setHeaderTitle(ConstantUtil.getPlayer(2));
                clickPlayer = 2;
                break;
            default:
                menu.setHeaderTitle(ConstantUtil.getPlayer(3));
                clickPlayer = 3;
                break;
        }
        contextSelected = false;
        rotateView();
    }

    public boolean onContextItemSelected(MenuItem item) {
        contextSelected = true;
        switch (item.getItemId()) {
            case R.id.it_chonbo:
                showDialog(AlertDialogFragment.KEY_CHONBO);
                return true;
            case R.id.it_cancelRichi:
                if (ConstantUtil.getRichiStatus(clickPlayer))
                    showDialog(AlertDialogFragment.KEY_CANCELRICHI);
                else {
                    Toast.makeText(this, "玩家尚未立直", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void onContextMenuClosed(Menu menu) {
        if (!contextSelected)
            checkConfiguration();
    }


    public static String setChonboMessage() {
        int parentPay, otherPay, sum;
        String s[] = new String[5];
        if (clickPlayer == ConstantUtil.getRound()) {
            s[0] = "親家犯規";
            parentPay = 0;
            otherPay = 4000;
            sum = 4000 * 3;
        } else {
            s[0] = "閒家犯規";
            parentPay = 4000;
            otherPay = 2000;
            sum = 4000 + 2000 * 2;
        }

        for (int i = 1; i < 5; i++) {
            String name = ConstantUtil.getPlayer(i - 1);
            int point = ConstantUtil.getPoint(i - 1);
            s[i] = name + ":" + point;
            if (i != clickPlayer + 1) {
                if (i == ConstantUtil.getRound() + 1)
                    s[i] += "+" + parentPay + "→" + (point + parentPay);
                else
                    s[i] += "+" + otherPay + "→" + (point + otherPay);
            } else
                s[i] += "-" + sum + "→" + (point - sum);
        }

        String message = "";
        for (int i = 0; i < 5; i++) {
            message += s[i];
            if (i != 4)
                message += "\n";
        }

        return message;
    }


    private void showDialog(String key) {
        if (key == AlertDialogFragment.KEY_CLICKMENU)
            rotateView();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null)
            ft.remove(prev);
        ft.addToBackStack(null);
        DialogFragment newFragment;
        if (key == AlertDialogFragment.KEY_CLICKMENU)
            newFragment = AlertDialogFragment.newInstance(key, clickPlayer + "");
        else
            newFragment = AlertDialogFragment.newInstance(key, null);
        newFragment.show(ft, "dialog");
    }

    public void rotateView() {
        int orientation;
        switch (clickPlayer) {
            case 0:
                orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                break;
            case 1:
                orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                break;
            case 2:
                orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                break;
            default:
                orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                break;
        }
        if (this.getResources().getConfiguration().orientation != orientation)
            setRequestedOrientation(orientation);
    }

    public void checkConfiguration() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void doPositiveClick(String id) {
        checkConfiguration();
        if (id.equals(AlertDialogFragment.KEY_CANCELRICHI)) {
            ConstantUtil.setRichi(false);
            ConstantUtil.setRichiStatus(clickPlayer, false);
            setGameInfo();
            ConstantUtil.setPoint(clickPlayer, 1000);
            setPointInfo();
        } else if (id.equals(AlertDialogFragment.KEY_DRAWGAME)) {
            intent.setClass(Game.this, Draw.class);
            startActivity(intent);
        } else if (id.equals(AlertDialogFragment.KEY_CHONBO)) {
            int parentPay, otherPay, sum;
            if (clickPlayer == ConstantUtil.getRound()) {
                parentPay = 0;
                otherPay = 4000;
                sum = 4000 * 3;
            } else {
                parentPay = 4000;
                otherPay = 2000;
                sum = 4000 + 2000 * 2;
            }
            for (int i = 0; i < 4; i++) {
                if (i != clickPlayer) {
                    if (i == ConstantUtil.getRound())
                        ConstantUtil.setPoint(i, parentPay);
                    else
                        ConstantUtil.setPoint(i, otherPay);
                } else
                    ConstantUtil.setPoint(i, -sum);
            }

            for (int i = 0; i < 4; i++) {
                if (ConstantUtil.getRichiStatus(i)) {
                    ConstantUtil.setRichi(false);
                    ConstantUtil.setRichiStatus(i, false);
                    ConstantUtil.setPoint(i, 1000);
                }
            }

            onResume();
        } else if (id.equals(AlertDialogFragment.KEY_PAUSEGAME)) {
            finish();
        }
    }

    public void doNegativeClick() {
        checkConfiguration();
    }

    public void doItemClick(int which) {
        Bundle bundle = new Bundle();
        switch (which) {
            case 0:
                intent.setClass(Game.this, Ron.class);
                bundle.putInt("clickPlayer", clickPlayer);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 1:
                intent.setClass(Game.this, Tsumo.class);
                bundle.putInt("clickPlayer", clickPlayer);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                if (ConstantUtil.getRichiStatus(clickPlayer)) {
                    Toast.makeText(this, "玩家已立直", Toast.LENGTH_SHORT).show();
                } else {
                    ConstantUtil.setRichi(true);
                    ConstantUtil.setRichiStatus(clickPlayer, true);
                    setGameInfo();
                    ConstantUtil.setPoint(clickPlayer, -1000);
                    setPointInfo();
                }
                checkConfiguration();
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            showDialog(AlertDialogFragment.KEY_PAUSEGAME);
        return false;
    }

    protected void onPause() {
        super.onPause();
        gameLog.edit()
                .putBoolean(SettingsFragment.KEY_PREF_GAME, true)
                .apply();
    }

    protected void onStop() {
        super.onStop();
        gameLog.edit()
                .putString(SettingsFragment.KEY_PREF_PLAYER0, ConstantUtil.getPlayer(0))
                .putString(SettingsFragment.KEY_PREF_PLAYER1, ConstantUtil.getPlayer(1))
                .putString(SettingsFragment.KEY_PREF_PLAYER2, ConstantUtil.getPlayer(2))
                .putString(SettingsFragment.KEY_PREF_PLAYER3, ConstantUtil.getPlayer(3))
                .putInt(SettingsFragment.KEY_PREF_POINT0, ConstantUtil.getPoint(0))
                .putInt(SettingsFragment.KEY_PREF_POINT1, ConstantUtil.getPoint(1))
                .putInt(SettingsFragment.KEY_PREF_POINT2, ConstantUtil.getPoint(2))
                .putInt(SettingsFragment.KEY_PREF_POINT3, ConstantUtil.getPoint(3))
                .putInt(SettingsFragment.KEY_PREF_ROUND, ConstantUtil.getRound())
                .putInt(SettingsFragment.KEY_PREF_WIND, ConstantUtil.getWind())
                .putInt(SettingsFragment.KEY_PREF_RICHI, ConstantUtil.getRichi())
                .putInt(SettingsFragment.KEY_PREF_RENCHAN, ConstantUtil.getRenchan())
                .apply();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
