package com.hongyan.life.activity.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class HomeFragment extends BaseFragment {

    private static final String TAG="HomeFragment";

    private static final String baseWeatherUrl="https://www.tianqiapi.com/api?version=v6&appid=45324354&appsecret=lw9iMb8d";

    private View view;
    private LinearLayout roorLayout;

    LinearLayout weatherCityLayout;
    TextView weatherCityName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        roorLayout = view.findViewById(R.id.linearLayout);
        initView();
        return view;
    }

    private void initView() {
        initWeather();
    }

    private void initWeather() {
        weatherCityLayout=view.findViewById(R.id.fragment_home_city_layout);
        weatherCityName = view.findViewById(R.id.fragment_home_city_name);
        String name = weatherCityName.getText().toString();





        searcheCityWeather(name);

    }

    private void searcheCityWeather(String name) {
        String url = baseWeatherUrl;
//        try {
//            url = baseWeatherUrl+ URLEncoder.encode(name,"UTF8");
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        Log.d(TAG,"url:"+url);
        LFHttpRequestUtils.getSyn(url, new LFNetworkCallback() {
            @Override
            public void completed(String response) {
//                tvResult.setText(response);
                WeatherNow weatherNow = GsonUtils.gsonResolve(response, WeatherNow.class);
                Log.d(TAG,"Weather info :"+weatherNow.toString());
                if (weatherNow==null){
                    return;
                }
                int imgId = getWeatherImg(weatherNow.getWea_img());


            }

            @Override
            public void failed(int httpStatusCode, String error) {
//                tvResult.setText(error);
                String errorMsg = "网络错误  [httpStatusCode" + httpStatusCode + "   error:" + error + "]";
                Log.d(TAG,errorMsg);
            }

        });
    }

    /**
     * xue、lei、shachen、wu、bingbao、yun、yu、yin、qing
     * @param wea_img
     */
    private int getWeatherImg(String wea_img){
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
            default: return R.drawable.qing;
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
