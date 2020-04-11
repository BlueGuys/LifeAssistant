package com.hongyan.life.activity.bill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseActivity;
import com.hongyan.life.activity.BaseFragment;
import com.hongyan.life.activity.calc.CalcFragment;
import com.hongyan.life.activity.home.HomeFragment;
import com.hongyan.life.activity.shares.SharesFragment;
import com.hongyan.life.activity.translate.TranslateFragment;
import com.hongyan.life.tab.SubPage;
import com.hongyan.life.tab.TabContainer;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class AddRecordActivity extends BaseActivity {

    private Button btnCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        btnCommit = findViewById(R.id.btn_commit);
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
    }

    private void commit(){
        float amount = 100.00f;
        int category = 1;
        String remark = "猫猫猫";
        int type = 1;

        Intent intent = new Intent();
        intent.putExtra("amount", amount);
        intent.putExtra("category", amount);
        intent.putExtra("remark", remark);
        intent.putExtra("type", type);
        setResult(1002, intent);
        finish();
    }

}