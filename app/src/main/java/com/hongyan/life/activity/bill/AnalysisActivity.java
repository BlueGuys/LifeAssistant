package com.hongyan.life.activity.bill;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseActivity;

public class AnalysisActivity extends BaseActivity {

    private BillHeadView headView;
    private ImageView btnBack;
    private int currentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_analysis);
        btnBack = findViewById(R.id.image_back);
        headView = findViewById(R.id.headView);
        headView.setOnMenuClickListener(new BillHeadView.OnMenuClickListener() {
            @Override
            public void onSelect(int position) {
                currentType = position;
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}