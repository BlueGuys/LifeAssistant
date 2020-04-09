package com.hongyan.life.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.hongyan.life.R;

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        this(context, R.style.loading_a);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_loading);
    }

}
