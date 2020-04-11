package com.hongyan.life.activity.bill;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.life.R;

import androidx.annotation.Nullable;


/**
 * Created by wangning on 2018/7/10.
 */

public class BillHeadView extends LinearLayout {

    private View view;
    private TextView textView01;
    private TextView textView03;

    public BillHeadView(Context context) {
        super(context);
    }

    public BillHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.view_service_headview, this, true);
        initView();
    }

    private void initView() {
        textView01 = view.findViewById(R.id.textView_01);
        textView03 = view.findViewById(R.id.textView_03);
        textView01.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuClickListener != null) {
                    menuClickListener.onSelect(0);
                }
                select(0);
            }
        });
        textView03.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuClickListener != null) {
                    menuClickListener.onSelect(1);
                }
                select(1);
            }
        });
        select(0);
    }

    protected void select(int index) {
        switch (index) {
            case 0:
                textView01.setTextColor(getResources().getColor(R.color.fontPositive));
                textView03.setTextColor(getResources().getColor(R.color.white));
                textView01.setBackgroundResource(R.drawable.bg_left_white);
                textView03.setBackgroundResource(R.drawable.bg_right_white_stroke);
                break;
            case 1:
                textView01.setTextColor(getResources().getColor(R.color.white));
                textView03.setTextColor(getResources().getColor(R.color.fontPositive));
                textView01.setBackgroundResource(R.drawable.bg_left_white_stroke);
                textView03.setBackgroundResource(R.drawable.bg_right_white);
                break;
        }
    }

    private OnMenuClickListener menuClickListener;

    public void setOnMenuClickListener(OnMenuClickListener listener) {
        this.menuClickListener = listener;
    }

    interface OnMenuClickListener {
        void onSelect(int position);
    }
}
