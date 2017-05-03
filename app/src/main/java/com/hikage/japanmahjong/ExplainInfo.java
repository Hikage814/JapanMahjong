package com.hikage.japanmahjong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

public class ExplainInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        int clicked = bundle.getInt("clicked");

        getSupportActionBar().hide();

        switch (clicked) {
            case 0:
                setContentView(R.layout.explain_tile);
                break;
            case 1:
                setContentView(R.layout.explain_game_info);
                break;
            case 2:
                setContentView(R.layout.explain_dice);
                break;
            default:
                setContentView(R.layout.explain_other);
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            finish();
        return false;
    }
}
