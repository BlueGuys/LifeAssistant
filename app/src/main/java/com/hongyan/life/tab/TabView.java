package com.hongyan.life.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.life.R;


public class TabView extends LinearLayout {

    private View view;
    private ImageView iv;
    private TextView tv;

    private int[] icons;
    private String text;

    private boolean isSelected;

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context,  AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.item_tab_layout_custom, this, true);
        iv = view.findViewById(R.id.logo);
        tv = view.findViewById(R.id.tv);
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
        updateView();
    }

    /**
     * 设置资源
     *
     * @param drawable
     * @param text
     */
    public void setResource(int[] drawable, String text) {
        this.icons = drawable;
        this.text = text;
        updateView();
    }

    private void updateView() {
        iv.setImageResource(isSelected ? icons[1] : icons[0]);
        tv.setText(this.text);
    }
}
