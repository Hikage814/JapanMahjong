package com.hikage.japanmahjong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Logs extends AppCompatActivity {

    private ListView lv;
    private GameLogDAO gameLogDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logs);

        getSupportActionBar().hide();
        findView();

        gameLogDAO = new GameLogDAO(getApplicationContext());

        ListAdapter listAdapter = new SimpleAdapter(
                this,
                gameLogDAO.getAll(),
                android.R.layout.simple_list_item_2,
                new String[]{gameLogDAO.INFO, gameLogDAO.DATETIME},
                new int[]{android.R.id.text1, android.R.id.text2});

        lv.setAdapter(listAdapter);
    }

    private void findView() {
        lv = (ListView) findViewById(R.id.lv);
    }

    protected void onStop(){
        super.onStop();
    }
}
