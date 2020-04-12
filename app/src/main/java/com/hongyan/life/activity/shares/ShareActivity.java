package com.hongyan.life.activity.shares;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.hongyan.life.R;
import com.hongyan.life.activity.BaseActivity;
import com.hongyan.life.net.LFHttpRequestUtils;
import com.hongyan.life.net.LFNetworkCallback;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShareActivity extends BaseActivity {


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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shares);
        etInput = findViewById(R.id.edit_input);
        imageSearch = findViewById(R.id.image_search);
        title=findViewById(R.id.fragment_shares_title);

        kaipan=findViewById(R.id.kaipan);
        dangqian=findViewById(R.id.dangqian);
        today_min=findViewById(R.id.today_min);
        today_max=findViewById(R.id.today_max);

        fenshi=findViewById(R.id.fenshi_img);
        day=findViewById(R.id.day_img);
        week=findViewById(R.id.week_img);
        month=findViewById(R.id.month_img);

        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(etInput.getText().toString());
            }
        });
    }



    private void search(String str) {
        String dataUrl = baseUrl+str;
        Log.e("test", "dataurl:"+ dataUrl);
        Map<String, Object> parameterList = new LinkedHashMap<>();
//        parameterList.put("app_type", 1);
        LFHttpRequestUtils.getSyn(dataUrl, new LFNetworkCallback() {
            @Override
            public void completed(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String data = response;
//                            data = URLEncoder.encode(data,"GBK");
                            data=data.substring(data.indexOf("\"")+1,data.lastIndexOf("\""));
                            Log.e("test", "DATA:"+ data);
                            if (data.length()<1){
                                Toast.makeText(ShareActivity.this,"请求数据失败，请检查股票代码！",Toast.LENGTH_LONG).show();
                                kaipan.setVisibility(View.GONE);
                                dangqian.setVisibility(View.GONE);
                                today_min.setVisibility(View.GONE);
                                today_max.setVisibility(View.GONE);
                                return;
                            }
                            kaipan.setVisibility(View.VISIBLE);
                            dangqian.setVisibility(View.VISIBLE);
                            today_min.setVisibility(View.VISIBLE);
                            today_max.setVisibility(View.VISIBLE);

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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (httpStatusCode == 404){
                            Toast.makeText(ShareActivity.this,"网络连接失败！",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ShareActivity.this,"请求数据失败，请检查股票代码！",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        Glide.with(this).asGif().load(fenShiUrl+str+".gif").into(fenshi);
        Glide.with(this).asGif().load(riKUrl+str+".gif").into(day);
        Glide.with(this).asGif().load(weekKUrl+str+".gif").into(week);
        Glide.with(this).asGif().load(monthKUrl+str+".gif").into(month);
    }
}
