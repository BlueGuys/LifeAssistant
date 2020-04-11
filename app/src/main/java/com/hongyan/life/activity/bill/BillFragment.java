package com.hongyan.life.activity.bill;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseFragment;
import com.hongyan.life.utils.BillUtils;

public class BillFragment extends BaseFragment {

    private View view;
    private BillHeadView headView;
    private ListView listView;
    private BillAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bill, container, false);
        headView = view.findViewById(R.id.headView);
        listView = view.findViewById(R.id.bill_listView);
        mAdapter = new BillAdapter(getContext());
        headView.setOnMenuClickListener(new BillHeadView.OnMenuClickListener() {
            @Override
            public void onSelect(int position) {
                Log.e("hhh", "选择了:"+position);
            }
        });
        listView.setAdapter(mAdapter);
        mAdapter.setData(BillUtils.getRecordList(1));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }




}
