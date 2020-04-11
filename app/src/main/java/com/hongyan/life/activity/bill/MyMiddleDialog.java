package com.hongyan.life.activity.bill;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hongyan.life.R;

public class MyMiddleDialog extends Dialog {
    private Context context;

    private EditText editText;
    private Button btnConfirm;

    private OnInputListener listener;

    public MyMiddleDialog(Context context) {
        super(context);
    }

    public MyMiddleDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.dialog_add_record, null);
        this.setContentView(rootView);
        editText = rootView.findViewById(R.id.et_amount);
        btnConfirm = rootView.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                if (str.length() > 0) {
                    if (listener != null) {
                        listener.callBack(str);
                    }
                }
            }
        });
    }

    public interface OnInputListener {
        void callBack(String amount);
    }

    public void setListener(OnInputListener listener) {
        this.listener = listener;
    }
}

