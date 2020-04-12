package com.hongyan.life.activity.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hongyan.life.R;
import com.hongyan.life.activity.bill.Record;
import com.hongyan.life.bean.Memo;
import com.hongyan.life.utils.DateUtil;
import com.hongyan.life.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MemoAdapter extends BaseAdapter {

    private Context context;
    private List<Memo> memos;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");

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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_memo, parent, false);
        }
        TextView tvTitle = convertView.findViewById(R.id.item_title);
        TextView timeTv = convertView.findViewById(R.id.item_time);
        TextView tvContent = convertView.findViewById(R.id.item_content);
        tvTitle.setText(memo.getTitle());
        tvContent.setText(memo.getContent());

        long timestamp = memo.getTimestamp();
        Date date = new Date(timestamp);
        String format = null;
        try {
            format = DateUtils.dateToString(date, DateUtils.MONTH_DAY_HHmm);
            timeTv.setText(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }


}
