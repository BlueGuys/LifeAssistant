package com.hongyan.life.activity.bill;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.hongyan.life.R;
import com.hongyan.life.activity.BaseActivity;
import com.hongyan.life.utils.BillUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class AnalysisActivity extends BaseActivity {

    public static final String DATE_EXTRA = "AnalysisActivity_DATA";

    private BillHeadView headView;
    private ImageView btnBack;
    private int currentType=1;


    private LinearLayout type1Layout;
    private LinearLayout type2Layout;

    private LineChart lineChart1;
    private LineChart lineChart2;


    private PieChart pieChart1;
    private PieChart pieChart2;

    private TextView noPieData1;
    private TextView noPieData2;


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

        pieChart1 = findViewById(R.id.activity_bill_analysis_piechart1);
        pieChart2 = findViewById(R.id.activity_bill_analysis_piechart2);

        noPieData1 = findViewById(R.id.activity_bill_analysis_type1_nopiedata);
        noPieData2 = findViewById(R.id.activity_bill_analysis_type2_nopiedata);



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
        initPieChart1();
        initPieChart2();
    }

    private void initLineChart1() throws ParseException {
        LinkedHashMap<Integer, Float> monthInfo = BillUtils.getMonthInfo(1, nowDateStr);

        initLineChartData(lineChart1 , monthInfo);
    }

    private void initLineChart2() throws ParseException {
        LinkedHashMap<Integer, Float> monthInfo = BillUtils.getMonthInfo(2, nowDateStr);

        initLineChartData(lineChart2 , monthInfo);
    }


    private void initLineChartData(LineChart lineChart ,LinkedHashMap<Integer, Float> dataObjects) {
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
//        lineChart1.setOnChartValueSelectedListener(this);
        lineChart.setDrawGridBackground(false);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setNoDataText("暂无数据");
        XAxis xAxis = lineChart.getXAxis();
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
        lineChart.setData(lineData);

    }




    private void initPieChart1() throws ParseException {
        LinkedHashMap<Integer, Float> monthCategoryInfo   = BillUtils.getMonthCategoryInfo(1, nowDateStr);
        if (monthCategoryInfo ==null || monthCategoryInfo.size()<1){
            noPieData1.setVisibility(View.VISIBLE);
            pieChart1.setVisibility(View.GONE);
        }else {
            initPieChar(pieChart1,monthCategoryInfo);
        }
    }

    private void initPieChart2() throws ParseException {
        LinkedHashMap<Integer, Float> monthCategoryInfo   = BillUtils.getMonthCategoryInfo(2, nowDateStr);
        if (monthCategoryInfo ==null || monthCategoryInfo.size()<1){
            noPieData2.setVisibility(View.VISIBLE);
            pieChart2.setVisibility(View.GONE);
        }else{
            initPieChar(pieChart2,monthCategoryInfo);
        }
    }
    private  void  initPieChar(PieChart pieChart , LinkedHashMap<Integer, Float> monthCategoryInfo)  {

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(false);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.setNoDataTextColor(Color.BLACK);
        pieChart.setNoDataText("暂无分类信息");
        pieChart.setNoDataTextTypeface(Typeface.DEFAULT);
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
//        for (int i = 0; i < 5 ; i++) {
//            entries.add(new PieEntry(20,i+""));
//        }
        Set<Integer> integers = monthCategoryInfo.keySet();
        for (int i:integers){
            entries.add(new PieEntry(monthCategoryInfo.get(i), Category.getDescById(i)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);


        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);




        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
//        data.setValueTypeface(tfLight);
        pieChart.setData(data);
        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();


    }
}