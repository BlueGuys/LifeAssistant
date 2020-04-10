package com.hongyan.life.tab;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hongyan.life.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;


public class TabContainer extends LinearLayout {

    private Context mContext;

    private List<Tab> tabList = new ArrayList<>();
    private OnSelectChangeListener onSelectChangeListener;
    private int currentIndex;

    public TabContainer(Context context) {
        this(context, null);
    }

    public TabContainer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public void addTab(Tab tab) {
        tabList.add(tab);
        notifyDataSetChanged();
    }

    public void selectPage(int position) {
        this.currentIndex = position;
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        removeAllViews();
        for (int i = 0; i < tabList.size(); i++) {
            Tab tab = tabList.get(i);
            if (i == currentIndex) {
                tab.isSelected = true;
            } else {
                tab.isSelected = false;
            }
            LinearLayout tabView = createTabView(tab);
            final int finalI = i;
            tabView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSelectChangeListener != null) {
                        onSelectChangeListener.onChange(finalI);
                    }
                }
            });
            addView(tabView);
        }
    }

    public LinearLayout createTabView(Tab tab) {
        LinearLayout tabView = new LinearLayout(mContext);
        tabView.setOrientation(VERTICAL);
        tabView.setGravity(Gravity.CENTER);
        tabView.setPadding(0, 10, 0, 10);
        tabView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));

        ImageView image = new ImageView(mContext);
        image.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setText(tab.text);
        textView.setTextSize(11);
        textView.setPadding(0, 10, 0, 0);
        textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if (tab.isSelected) {
            image.setImageResource(tab.drawable[0]);
            textView.setTextColor(getResources().getColor(R.color.appColorPositive));
        } else {
            image.setImageResource(tab.drawable[1]);
            textView.setTextColor(getResources().getColor(R.color.black));
        }
        tabView.addView(image);
        tabView.addView(textView);
        return tabView;
    }

    public static final class Tab {
        public int[] drawable;
        public String text;
        boolean isSelected;
    }

    public Tab newTab() {
        return new Tab();
    }

    public void setOnSelectChangeListener(OnSelectChangeListener listener) {
        this.onSelectChangeListener = listener;
    }

    public interface OnSelectChangeListener {
        void onChange(int position);
    }
}
