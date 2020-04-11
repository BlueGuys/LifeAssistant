package com.hongyan.life.activity.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseFragment;
import com.hongyan.life.bean.WeatherNow;
import com.hongyan.life.net.LFHttpRequestUtils;
import com.hongyan.life.net.LFNetworkCallback;
import com.hongyan.life.utils.GsonUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class HomeFragment extends BaseFragment {

    private static final String TAG="HomeFragment";

    private static final String baseWeatherUrl="http://www.tianqiapi.com/api?version=v6&appid=45324354&appsecret=lw9iMb8d";

    private View view;
    private LinearLayout roorLayout;

    LinearLayout weatherCityLayout;
    TextView weatherCityName;

    private Button button;
    private ImageView weatherImg;
    private TextView tipsTv,airLevelTv,tempTv,temp12Tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        roorLayout = view.findViewById(R.id.linearLayout);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestWeather("北京");
            }
        });
        initView();
        return view;
    }

    private void initView() {
        initWeather();
    }

    private void initWeather() {
        weatherCityLayout=view.findViewById(R.id.fragment_home_city_layout);
        weatherCityName = view.findViewById(R.id.fragment_home_city_name);
        weatherImg=view.findViewById(R.id.fragment_home_weather_img);
        tipsTv=view.findViewById(R.id.fragment_home_weather_tips);
        airLevelTv=view.findViewById(R.id.fragment_home_weather_air_level);
        tempTv=view.findViewById(R.id.fragment_home_weather_temp);
        temp12Tv=view.findViewById(R.id.fragment_home_weather_temp12);
        String name = weatherCityName.getText().toString();
        requestWeather(name);
    }


    private void requestWeather(String name) {
        String url = baseWeatherUrl;
        try {
            url = baseWeatherUrl+"&city="+ URLEncoder.encode(name,"UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"url:"+url);
        Map<String, Object> parameterList = new LinkedHashMap<>();
        parameterList.put("app_type", 1);

        LFHttpRequestUtils.postSyn(url, parameterList, new LFNetworkCallback() {
            @Override
            public void completed(String response) {
                Log.d(TAG,"Weather info :"+ response);
                try{
                    String json = response;
                    WeatherNow weatherNow = GsonUtils.gsonResolve(json, WeatherNow.class);
                    String wea_img = weatherNow.getWea_img();
                    weatherImg.setImageResource(getImageIdByWea(wea_img));
                    tipsTv.setText(weatherNow.getAir_tips());
                    airLevelTv.setText(weatherNow.getAir_level());
                    tempTv.setText(weatherNow.getTem()+"℃");
                    temp12Tv.setText(weatherNow.getTem1()+" / "+weatherNow.getTem2());
                }catch (Exception e){
                }
            }

            @Override
            public void failed(int httpStatusCode, String error) {
                Log.d(TAG,httpStatusCode+"error :"+ error);
            }
        });
    }

    /**
     * xue、lei、shachen、wu、bingbao、yun、yu、yin、qing
     * @param wea_img
     * @return
     */
    private int getImageIdByWea(String wea_img) {
        switch (wea_img){
            case "xue":
                return R.drawable.xue;
            case "lei":
                return R.drawable.lei;
            case "shachen":
                return R.drawable.shachen;
            case "wu":
                return R.drawable.wu;
            case "bingbao":
                return R.drawable.bingbao;
            case "yun":
                return R.drawable.yun;
            case "yu":
                return R.drawable.yu;
            case "yin":
                return R.drawable.yin;
            case "qing":
                return R.drawable.qing;
           default:
                return R.drawable.qing;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

}
