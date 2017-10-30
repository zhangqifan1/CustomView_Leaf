package com.bawei.customview_leaf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    /**
     * 全选
     */
    private CheckBox mCbAll;
    /**
     * 多少钱?
     */
    private TextView mTvAllPrice;
    /**
     * 结算
     */
    private TextView mCountNum;
    private List<HashMap<String, String>> list;
    private Adapter adapter;
    private int totalCount;
    private int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        adapter = new Adapter(list, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setiRefresh(new IRefresh() {
            @Override
            public void refreshPrice(Map<String, Boolean> map) {
                PriceController(map);
            }

            @Override
            public void refreshIsChecked(boolean b) {
                mCbAll.setChecked(b);
            }
        });
        mCbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Boolean> map = adapter.getMap();
                for (Map.Entry<String, Boolean> entry : map.entrySet()) {
                    entry.setValue(mCbAll.isChecked());
                }
            PriceController(map);
                adapter.setMap(map);
                adapter.notifyDataSetChanged();

            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", new Random().nextInt(10000) + "");
            hashMap.put("name", "第" + (i + 1) + "件商品");
            hashMap.put("price", 50 + "");
            hashMap.put("count", 1 + "");
            list.add(hashMap);
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mCbAll = (CheckBox) findViewById(R.id.cbAll);
        mTvAllPrice = (TextView) findViewById(R.id.tvAllPrice);
        mCountNum = (TextView) findViewById(R.id.countNum);
    }

    private void PriceController(Map<String, Boolean> m) {
        totalCount = 0;
        totalPrice = 0;
        for (int i = 0; i < list.size(); i++) {
            if (m.get(list.get(i).get("id"))) {
                totalCount += Integer.parseInt(list.get(i).get("count"));
                totalPrice += Integer.parseInt(list.get(i).get("count")) * Integer.parseInt(list.get(i).get("price"));
            }
            mCountNum.setText("结算:"+totalCount);
            mTvAllPrice.setText(totalPrice+"");
        }
    }
}
