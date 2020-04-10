package com.hongyan.life.activity.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseFragment;
import com.hongyan.life.activity.translate.TranslateBean;
import com.hongyan.life.bean.WeatherNow;
import com.hongyan.life.net.LFHttpRequestUtils;
import com.hongyan.life.net.LFNetworkCallback;
import com.hongyan.life.utils.GsonUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        roorLayout = view.findViewById(R.id.linearLayout);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searcheCityWeather("北京");
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
        String name = weatherCityName.getText().toString();
        searcheCityWeather(name);
    }

    private void searcheCityWeather(String name) {
        String url = baseWeatherUrl;
        try {
            url = baseWeatherUrl+ URLEncoder.encode(name,"UTF8");
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
            }

            @Override
            public void failed(int httpStatusCode, String error) {
                Log.d(TAG,"Weather info :"+ error);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }


}
