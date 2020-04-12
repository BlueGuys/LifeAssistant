package com.hongyan.life.activity.bill;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.hongyan.life.R;
import com.hongyan.life.activity.BaseActivity;
import com.hongyan.life.utils.BillUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class AnalysisActivity extends BaseActivity {

    public static final String DATE_EXTRA = "AnalysisActivity_DATA";

    private BillHeadView headView;
    private ImageView btnBack;
    private int currentType=1;


    private LinearLayout type1Layout;
    private LinearLayout type2Layout;

    private LineChart lineChart1;
    private LineChart lineChart2;

    private String nowDateStr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_analysis);
        btnBack = findViewById(R.id.image_back);
        headView = findViewById(R.id.headView);
        headView.setOnMenuClickListener(new BillHeadView.OnMenuClickListener() {
            @Override
            public void onSelect(int position) {
                currentType = position+1;
//                try {
//                    initLineChart1();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                if (currentType==1){
                    type1Layout.setVisibility(View.VISIBLE);
                    type2Layout.setVisibility(View.GONE);
                }else{
                    type1Layout.setVisibility(View.GONE);
                    type2Layout.setVisibility(View.VISIBLE);
                }

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        type1Layout = findViewById(R.id.activity_bill_analysis_type1);
        type2Layout = findViewById(R.id.activity_bill_analysis_type2);

        lineChart1 = findViewById(R.id.activity_bill_analysis_linechart1);
        lineChart2 = findViewById(R.id.activity_bill_analysis_linechart2);
        initView();
    }

    private void initView() {
        try {
            initBundleInfo();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initBundleInfo() throws ParseException {
        nowDateStr = getIntent().getStringExtra(DATE_EXTRA);
        initLineChart1();
        initLineChart2();
    }

    private void initLineChart1() throws ParseException {
        LinkedHashMap<Integer, Float> monthInfo = BillUtils.getMonthInfo(1, nowDateStr);
        initLineChartData1(monthInfo);
    }

    private void initLineChartData1(LinkedHashMap<Integer, Float> dataObjects) {
        lineChart1.setBackgroundColor(Color.WHITE);
        lineChart1.getDescription().setEnabled(false);
        lineChart1.setTouchEnabled(true);
//        lineChart1.setOnChartValueSelectedListener(this);
        lineChart1.setDrawGridBackground(false);
        lineChart1.setDragEnabled(true);
        lineChart1.setScaleEnabled(true);
        lineChart1.setPinchZoom(true);

        XAxis xAxis = lineChart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setSpaceMax(1);
        xAxis.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value+1)+"日";
            }
        });
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataObjects.size(); i++) {
            float data = dataObjects.get(i+1);
            entries.add(new Entry(i, data));
        }
        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setLineWidth(1f);
        LineData lineData = new LineData(dataSet);
        lineChart1.setData(lineData);

    }

    private void initLineChart2() throws ParseException {
        LinkedHashMap<Integer, Float> monthInfo = BillUtils.getMonthInfo(2, nowDateStr);
        initLineChartData2(monthInfo);
    }

    private void initLineChartData2(LinkedHashMap<Integer, Float> dataObjects) {
        lineChart2.setBackgroundColor(Color.WHITE);
        lineChart2.getDescription().setEnabled(false);
        lineChart2.setTouchEnabled(true);
//        lineChart1.setOnChartValueSelectedListener(this);
        lineChart2.setDrawGridBackground(false);
        lineChart2.setDragEnabled(true);
        lineChart2.setScaleEnabled(true);
        lineChart2.setPinchZoom(true);

        XAxis xAxis = lineChart2.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setSpaceMax(1);
        xAxis.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value+1)+"日";
            }
        });
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataObjects.size(); i++) {
            float data = dataObjects.get(i+1);
            entries.add(new Entry(i, data));
        }
        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setLineWidth(1f);
        LineData lineData = new LineData(dataSet);
        lineChart2.setData(lineData);

    }






}