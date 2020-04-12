package com.hongyan.life.activity.shares;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hongyan.life.R;
import com.hongyan.life.activity.BaseFragment;
import com.hongyan.life.bean.WeatherNow;
import com.hongyan.life.net.LFHttpRequestUtils;
import com.hongyan.life.net.LFNetworkCallback;
import com.hongyan.life.utils.GsonUtils;

import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;


public class SharesFragment extends BaseFragment {

    private View view;
    private EditText etInput;
    private ImageView imageSearch;
    private static String baseUrl = "http://hq.sinajs.cn/list=sh";
    private static String fenShiUrl = "http://image.sinajs.cn/newchart/min/n/sh";
    private static String riKUrl = "http://image.sinajs.cn/newchart/daily/n/sh";
    private static String weekKUrl = "http://image.sinajs.cn/newchart/weekly/n/sh";
    private static String monthKUrl = "http://image.sinajs.cn/newchart/monthly/n/sh";

    private TextView title;
    private TextView kaipan;
    private TextView dangqian;
    private TextView today_min;
    private TextView today_max;



    ImageView fenshi;
    ImageView day;
    ImageView week;
    ImageView month;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view==null){
            view = inflater.inflate(R.layout.fragment_shares, container, false);
            etInput = view.findViewById(R.id.edit_input);
            imageSearch = view.findViewById(R.id.image_search);
            title=view.findViewById(R.id.fragment_shares_title);

            kaipan=view.findViewById(R.id.kaipan);
            dangqian=view.findViewById(R.id.dangqian);
            today_min=view.findViewById(R.id.today_min);
            today_max=view.findViewById(R.id.today_max);

            fenshi=view.findViewById(R.id.fenshi_img);
            day=view.findViewById(R.id.day_img);
            week=view.findViewById(R.id.week_img);
            month=view.findViewById(R.id.month_img);

            imageSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search(etInput.getText().toString());
                }
            });
        }
//        search("000001");
        return view;
    }

    private void search(String str) {
        String dataUrl = baseUrl+str;
        Log.e("test", "dataurl:"+ dataUrl);
        Map<String, Object> parameterList = new LinkedHashMap<>();
//        parameterList.put("app_type", 1);
        LFHttpRequestUtils.getSyn(dataUrl, new LFNetworkCallback() {
            @Override
            public void completed(final String response) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String data = response;
//                            data = URLEncoder.encode(data,"GBK");
                            data=data.substring(data.indexOf("\"")+1,data.lastIndexOf("\""));
                            Log.e("test", "DATA:"+ data);
                            if (data.length()<1){
                                Toast.makeText(getActivity(),"请求数据失败，请检查股票代码！",Toast.LENGTH_LONG).show();
                                kaipan.setVisibility(View.GONE);
                                dangqian.setVisibility(View.GONE);
                                today_min.setVisibility(View.GONE);
                                today_max.setVisibility(View.GONE);
                                return;
                            }
                            String[] split = data.split(",");
                            kaipan.setText("今日开盘价："+split[1]);
                            dangqian.setText("当前价格："+split[3]);
                            today_min.setText("今日最低价："+split[5]);
                            today_max.setText("今日最高价："+split[4]);

//                            title.setText(split[0]);


                        } catch (Exception e) {
                        }
                    }
                });
            }

            @Override
            public void failed(final int httpStatusCode, String error) {
                Log.e("test",error);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (httpStatusCode == 404){
                            Toast.makeText(getActivity(),"网络连接失败！",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(),"请求数据失败，请检查股票代码！",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

       Glide.with(view).asGif().load(fenShiUrl+str+".gif").into(fenshi);
       Glide.with(view).asGif().load(riKUrl+str+".gif").into(day);
       Glide.with(view).asGif().load(weekKUrl+str+".gif").into(week);
       Glide.with(view).asGif().load(monthKUrl+str+".gif").into(month);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }







}
