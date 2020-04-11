package com.hongyan.life.activity.bill;

import android.os.Bundle;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseActivity;

public class AnalysisActivity extends BaseActivity {

    private BillHeadView headView;
    private int currentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_analysis);
        headView = findViewById(R.id.headView);
        headView.setOnMenuClickListener(new BillHeadView.OnMenuClickListener() {
            @Override
            public void onSelect(int position) {
                currentType = position;
            }
        });
    }

}