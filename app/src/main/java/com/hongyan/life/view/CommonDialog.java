package com.hongyan.life.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hongyan.life.R;

public class CommonDialog extends Dialog implements View.OnClickListener{
    private Context mContext;

    private OnClickEvent mOnClickEvent = null;

    private TextView tv_title;
    private TextView tv_commit;

    public CommonDialog(Context context, OnClickEvent onClickEvent) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.mOnClickEvent = onClickEvent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);

        tv_title = findViewById(R.id.tv_title);

        findViewById(R.id.tv_cancel).setOnClickListener(this);
        tv_commit = findViewById(R.id.tv_commit);
        tv_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_commit:
                mOnClickEvent.onClick(true);
                dismiss();
                break;

            case R.id.tv_cancel:
                mOnClickEvent.onClick(false);
                dismiss();
                break;
        }
    }

    public interface OnClickEvent {
        void onClick(boolean bool);
    }


    /**
     * @param
     */
    public void setTitle(String title){
        tv_title.setText(title);
    }

    /**
     * @param
     */
    public void setDefine(String content){
        tv_commit.setText(content);
    }
}
