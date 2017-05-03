package com.hikage.japanmahjong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Explain extends AppCompatActivity {

    private ListView lVi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explain);

        getSupportActionBar().hide();

        findView();
        initView();
    }

    private void findView() {
        lVi = (ListView) findViewById(R.id.lVi);
    }

    private void initView() {
        //String[] list = {"點選字牌", "點選牌局", "點選骰子", "其他"};
        String[] list = {"點選字牌", "點選牌局", "點選骰子"};
        ListAdapter mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list);
        lVi.setAdapter(mAdapter);
        lVi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent();
                intent.setClass(Explain.this, ExplainInfo.class);
                bundle.putInt("clicked", position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
