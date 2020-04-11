package com.hongyan.life.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hongyan.life.MyApplication;
import com.hongyan.life.R;
import com.hongyan.life.bean.Memo;
import com.hongyan.life.bean.MemoDao;

public class AddMemoActivity extends Activity {

    public static final String ADD_MEMO_ID_EXTRA = "AddMemoActivity_extra_id";
    Button complete;
    EditText editText;

    private long memoId=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmemo);
        complete = findViewById(R.id.activity_addmemo_complete);
        editText = findViewById(R.id.activity_addmemo_edit);

        memoId = getIntent().getLongExtra(ADD_MEMO_ID_EXTRA, 0);
        if (memoId > 0) {
            Memo memo = selectMemoById(memoId);
            if (memo != null) {
                editText.setText(memo.getContent());
            }
        }

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(AddMemoActivity.this, "输入内容为空", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("id",memoId);
                    intent.putExtra("content",s);
                    setResult(1,intent);
                    finish();
                }
            }
        });
    }

    private Memo selectMemoById(long memoId) {
        MemoDao memoDao = MyApplication.getDaoSession().getMemoDao();
        Memo unique = memoDao.queryBuilder().where(MemoDao.Properties.Id.eq(memoId)).unique();
        return unique;
    }


}
