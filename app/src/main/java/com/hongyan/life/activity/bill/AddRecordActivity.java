package com.hongyan.life.activity.bill;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class AddRecordActivity extends BaseActivity {

    private ImageView btnCommit;
    private ImageView btnBack;
    private GridView gridViewIncome;
    private GridView gridViewExpend;
    private CategoryAdapter inComeAdapter;
    private CategoryAdapter expendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        btnCommit = findViewById(R.id.btn_commit);
        btnBack = findViewById(R.id.image_back);
        gridViewIncome = findViewById(R.id.grid_income);
        gridViewExpend = findViewById(R.id.grid_expend);
        inComeAdapter = new CategoryAdapter();
        expendAdapter = new CategoryAdapter();
        gridViewIncome.setAdapter(inComeAdapter);
        gridViewExpend.setAdapter(expendAdapter);

        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<Category> categories = Category.getIncomeCategrays();
        inComeAdapter.setData(categories);
        expendAdapter.setData(categories);
    }

    private void commit() {
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

    class CategoryAdapter extends BaseAdapter {

        private ArrayList<Category> categories = new ArrayList<>();

        public void setData(List<Category> list) {
            categories.clear();
            if (list != null && list.size() > 0) {
                categories.addAll(list);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                final Category item = categories.get(position);
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
            }
            return convertView;
        }
    }

}