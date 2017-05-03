package com.hikage.japanmahjong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SetGame extends AppCompatActivity {

    TextView tv_eastPlayer, tv_southPlayer, tv_westPlayer, tv_northPlayer, tv_cmd;
    LinearLayout lv_tile, lv_dice, lv_startGame;
    ImageView img_tile1, img_tile2, img_tile3, img_tile0, img_dice1, img_dice2;
    String[] player = new String[4];
    String[] sit = new String[4];
    int step = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_game);

        getSupportActionBar().hide();

        findView();

        setPlayerName();
    }

    private void findView(){
        tv_eastPlayer = (TextView) findViewById(R.id.tv_eastPlayer);
        tv_southPlayer = (TextView) findViewById(R.id.tv_southPlayer);
        tv_westPlayer = (TextView) findViewById(R.id.tv_westPlayer);
        tv_northPlayer = (TextView) findViewById(R.id.tv_northPlayer);
        tv_cmd = (TextView) findViewById(R.id.tv_cmd);

        lv_tile = (LinearLayout) findViewById(R.id.lv_tile);
        lv_dice = (LinearLayout) findViewById(R.id.lv_dice);
        lv_startGame = (LinearLayout) findViewById(R.id.lv_startGame);

        img_dice1 = (ImageView) findViewById(R.id.img_dice1);
        img_dice2 = (ImageView) findViewById(R.id.img_dice2);

        img_tile0 = (ImageView) findViewById(R.id.img_tile0);
        img_tile1 = (ImageView) findViewById(R.id.img_tile1);
        img_tile2 = (ImageView) findViewById(R.id.img_tile2);
        img_tile3 = (ImageView) findViewById(R.id.img_tile3);
    }

    private void setPlayerName() {

        for (int i = 0; i < 4; i++)
            player[i] = ConstantUtil.getPlayer(i);

        tv_eastPlayer.setText(player[0]);
        tv_southPlayer.setText(player[1]);
        tv_westPlayer.setText(player[2]);
        tv_northPlayer.setText(player[3]);

        tv_cmd.setText(player[0] + "選牌決定東家");
    }

    private boolean[] tile = {true, true, true, true};
    boolean[] wind = {true, true, true, true};

    public void onClickTile(View v) {

        ImageView img = null;
        boolean flag = false;

        switch (step) {
            case 0:
            case 1:
            case 2:
            case 3:
                switch (v.getId()) {
                    case R.id.img_tile0:
                        img = img_tile0;
                        flag = tile[0];
                        tile[0] = false;
                        break;

                    case R.id.img_tile1:
                        img = img_tile1;
                        flag = tile[1];
                        tile[1] = false;
                        break;

                    case R.id.img_tile2:
                        img = img_tile2;
                        flag = tile[2];
                        tile[2] = false;
                        break;

                    case R.id.img_tile3:
                        img = img_tile3;
                        flag = tile[3];
                        tile[3] = false;
                        break;

                    default:
                        break;
                }
                break;
            default:
                break;
        }

        if (flag) {

            int ran = (int) (Math.random() * 4);

            do {
                if (wind[ran]) {
                    sit[step] = player[ran];
                    wind[ran] = false;
                    flag = false;
                } else
                    ran = (int) (Math.random() * 4);
            } while (flag);

            switch (ran) {
                case 0:
                    img.setImageResource(R.mipmap.tile_e);
                    break;
                case 1:
                    img.setImageResource(R.mipmap.tile_s);
                    break;
                case 2:
                    img.setImageResource(R.mipmap.tile_w);
                    break;
                default:
                    img.setImageResource(R.mipmap.tile_n);
                    break;
            }

            switch (step) {
                case 0:
                    tv_eastPlayer.setText(player[0] + " → " + player[ran]);
                    tv_cmd.setText(player[1] + "選牌決定南家");
                    step++;
                    break;

                case 1:
                    tv_southPlayer.setText(player[1] + " → " + player[ran]);
                    tv_cmd.setText(player[2] + "選牌決定西家");
                    step++;
                    break;

                case 2:
                    tv_westPlayer.setText(player[2] + " → " + player[ran]);
                    tv_cmd.setText(player[3] + "選牌決定北家");
                    step++;
                    break;

                case 3:
                    tv_northPlayer.setText(player[3] + " → " + player[ran]);
                    tv_cmd.setText(sit[0] + "選牌決定臨時親家");
                    step++;

                    for (int i = 0; i < 4; i++)
                        player[i] = sit[i];

                    tv_eastPlayer.setText(sit[0]);
                    tv_southPlayer.setText(sit[1]);
                    tv_westPlayer.setText(sit[2]);
                    tv_northPlayer.setText(sit[3]);

                    lv_dice.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }
    }

    public void onClickDice(View v) {

        boolean flag = false;

        switch (step) {
            case 4:
            case 5:
                flag = true;
                break;
            default:
                break;
        }

        if (flag) {

            int dice1, dice2, count;
            dice1 = (int) (Math.random() * 5 + 1);
            dice2 = (int) (Math.random() * 5 + 1);
            count = (dice1 + dice2) % 4;

            setDice(img_dice1, dice1);
            setDice(img_dice2, dice2);

            switch (count) {
                case 1:
                    tv_cmd.setText(sit[0] + "");
                    break;
                case 2:
                    tv_cmd.setText(sit[1] + "");
                    break;
                case 3:
                    tv_cmd.setText(sit[2] + "");
                    break;
                case 0:
                    tv_cmd.setText(sit[3] + "");
                    count = 4;
                    break;
                default:
                    break;
            }

            switch (step) {
                case 4:
                    tv_cmd.setText(tv_cmd.getText() + "決定起家");
                    lv_tile.setVisibility(View.INVISIBLE);
                    step++;
                    break;
                case 5:
                    tv_cmd.setText(tv_cmd.getText() + "為起家");
                    lv_startGame.setVisibility(View.VISIBLE);

                    int j = 0;
                    for (int i = count - 1; i < count + 3; i++) {
                        int k = i;
                        if (k > 3)
                            k -= 4;
                        sit[j] = player[k];
                        j++;
                    }
                    step++;
                    break;
                default:
                    break;
            }
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
            case 6:
                img.setImageResource(R.mipmap.dice_6);
                break;
            default:
                break;
        }
    }

    public void onClickStartGame(View v) {

        ConstantUtil.setPlayer(sit[0], sit[1], sit[2], sit[3]);

        Intent intent = new Intent();
        intent.setClass(this, Game.class);
        startActivity(intent);
        finish();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(this, SetPlayer.class);
            startActivity(intent);
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
