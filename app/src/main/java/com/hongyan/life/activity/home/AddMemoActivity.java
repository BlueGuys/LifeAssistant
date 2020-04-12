package com.hongyan.life.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hongyan.life.MyApplication;
import com.hongyan.life.R;
import com.hongyan.life.bean.Memo;
import com.hongyan.life.bean.MemoDao;

public class AddMemoActivity extends Activity {

    public static final String ADD_MEMO_ID_EXTRA = "AddMemoActivity_extra_id";
    EditText etTitle;
    EditText etContent;

    private long memoId = 0;

    private ImageView imageBack;
    private ImageView imageDone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmemo);
        imageBack = findViewById(R.id.image_back);
        imageDone = findViewById(R.id.image_done);
        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);

        memoId = getIntent().getLongExtra(ADD_MEMO_ID_EXTRA, 0);
        if (memoId > 0) {
            Memo memo = selectMemoById(memoId);
            if (memo != null) {
                etTitle.setText(memo.getTitle());
                etContent.setText(memo.getContent());
            }
        }
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String conent = etContent.getText().toString();

                if (TextUtils.isEmpty(title)) {
                    title = "标题";
                }
                if (TextUtils.isEmpty(conent)) {
                    conent = "内容";
                }

                Intent intent = new Intent();
                intent.putExtra("id", memoId);
                intent.putExtra("title", title);
                intent.putExtra("content", conent);
                setResult(1, intent);
                finish();
            }
        });
    }

    private Memo selectMemoById(long memoId) {
        MemoDao memoDao = MyApplication.getDaoSession().getMemoDao();
        Memo unique = memoDao.queryBuilder().where(MemoDao.Properties.Id.eq(memoId)).unique();
        return unique;
    }


}
