package com.hongyan.life.activity.bill;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyan.life.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置页适配器
 */

public class BillAdapter extends BaseAdapter {

    private List<Record> mList = new ArrayList<>();

    private Context mContext;

    public BillAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<Record> list) {
        mList.clear();
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (mList.size() > 0) {
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() > 0) {
            return mList.get(position).type;
        }
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Record record = mList.get(position);
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        ImageView imageView = convertView.findViewById(R.id.icon);
        TextView tvTitle = convertView.findViewById(R.id.tv_title);
        TextView tvAmount = convertView.findViewById(R.id.tv_amount);
        imageView.setImageResource(Category.getIconById(record.category));
        tvTitle.setText(Category.getDescById(record.category));
        tvAmount.setText(String.valueOf(record.amount));
        return convertView;
    }
}
