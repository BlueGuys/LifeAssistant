package com.hongyan.life.activity.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hongyan.life.R;
import com.hongyan.life.bean.Memo;

import java.util.List;

public class MemoAdapter extends BaseAdapter {

    private Context context;
    private List<Memo> memos;


    public MemoAdapter(Context context, List<Memo> memos) {
        this.context = context;
        this.memos = memos;
    }

    @Override
    public int getCount() {
        return memos.size();
    }

    @Override
    public Object getItem(int position) {
        return memos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Memo memo = memos.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_memo, parent,false);
            TextView content = convertView.findViewById(R.id.item_memo_content);
            content.setText(memo.getContent());

        }


        return convertView;
    }


}
