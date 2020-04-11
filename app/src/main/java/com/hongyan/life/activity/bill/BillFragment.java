package com.hongyan.life.activity.bill;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseFragment;
import com.hongyan.life.utils.BillUtils;

import java.util.ArrayList;

public class BillFragment extends BaseFragment {

    private View view;

    private ListView listView;
    private BillAdapter mAdapter;
    private ImageView btnAdd;
    private ImageView btnAnalysis;

    private static final int REQUEST_CODE = 1000;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_bill, container, false);

            listView = view.findViewById(R.id.bill_listView);
            btnAdd = view.findViewById(R.id.btn_add);
            btnAnalysis = view.findViewById(R.id.btn_analysis);
            mAdapter = new BillAdapter(getContext());

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AddRecordActivity.class);
                    getActivity().startActivityForResult(intent, REQUEST_CODE);
                }
            });
            btnAnalysis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AnalysisActivity.class);
                    getActivity().startActivity(intent);
                }
            });

            listView.setAdapter(mAdapter);
            notifyData();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 300) {
            String amount = data.getStringExtra("amount");
            int category = data.getIntExtra("category", 1);
            String remark = data.getStringExtra("remark");
            int type = data.getIntExtra("type", 1);

            Record record = new Record();
            record.setAmount(Float.parseFloat(amount));
            record.setCategory(category);
            record.setRemark(remark);
            record.setType(type);
            BillUtils.addRecord(record);
            notifyData();
        }
    }

    private void notifyData() {
        ArrayList<Record> records = (ArrayList<Record>) BillUtils.getAll();
        mAdapter.setData(records);
    }
}
