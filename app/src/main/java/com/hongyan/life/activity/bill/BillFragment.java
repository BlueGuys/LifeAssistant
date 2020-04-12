package com.hongyan.life.activity.bill;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hongyan.life.R;
import com.hongyan.life.activity.BaseFragment;
import com.hongyan.life.utils.BillUtils;
import com.hongyan.life.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BillFragment extends BaseFragment {

    private View view;

    private ListView listView;
    private BillAdapter mAdapter;
    private ImageView btnAdd;
    private ImageView btnAnalysis;
    private LinearLayout layoutTime;
    private TextView tvYear;
    private TextView tvMonth;
    private TextView tvShouru;
    private TextView tvZhichu;

    private static final int REQUEST_CODE = 1000;

    String nowDateStr;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_bill, container, false);

            listView = view.findViewById(R.id.bill_listView);
            btnAdd = view.findViewById(R.id.btn_add);
            btnAnalysis = view.findViewById(R.id.btn_analysis);
            layoutTime = view.findViewById(R.id.layout_time);
            tvYear = view.findViewById(R.id.tv_year);
            tvMonth = view.findViewById(R.id.tv_month);
            tvShouru = view.findViewById(R.id.tv_total_income);
            tvZhichu = view.findViewById(R.id.tv_total_zhichu);

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
                    intent.putExtra(AnalysisActivity.DATE_EXTRA, nowDateStr);
                    getActivity().startActivity(intent);
                }
            });

            layoutTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTimeDialog();
                }
            });

            listView.setAdapter(mAdapter);
            notifyData(new Date());
        }

        {
            try {
                nowDateStr = DateUtils.dateToString(new Date(), DateUtils.YEAR_MONTH);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            record.setTimeStap(System.currentTimeMillis());
            BillUtils.addRecord(record);
            notifyData(new Date());
        }
    }


    private void notifyData(Date date) {
        String monthStr = DateUtils.formatDate(date, DateUtils.YEAR_MONTH);
        ArrayList<Record> records = null;
        try {
            records = (ArrayList<Record>) BillUtils.getRecordListMonth(1, monthStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mAdapter.setData(records);
    }

    private void showTimeDialog() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(2020, 0, 1);
        endDate.set(2030, 11, 31);

        TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                nowDateStr = DateUtils.formatDate(date, DateUtils.YEAR_MONTH);
                notifyData(date);
            }
        }).setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(18)
                .setTitleSize(20)//标题文字大小
                .setTitleText("选择月份")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(R.color.appColorPositive)//确定按钮文字颜色
                .setCancelColor(R.color.appColorPositive)//取消按钮文字颜色
                .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false).build();//是否显示为对话框样式
        pvTime.show();
    }
}