package com.hongyan.life.activity.shares;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hongyan.life.R;
import com.hongyan.life.activity.BaseFragment;
import com.hongyan.life.bean.WeatherNow;
import com.hongyan.life.net.LFHttpRequestUtils;
import com.hongyan.life.net.LFNetworkCallback;
import com.hongyan.life.utils.GsonUtils;

import java.util.LinkedHashMap;
import java.util.Map;


public class SharesFragment extends BaseFragment {

    private View view;
    private EditText etInput;
    private ImageView imageSearch;
    private static String baseUrl = "http://hq.sinajs.cn/list=sh";
    private static String fenShiUrl = "http://image.sinajs.cn/newchart/min/n/sh";
    private static String riKUrl = "http://image.sinajs.cn/newchart/daily/n/sh";
    private static String weekKUrl = "http://image.sinajs.cn/newchart/weekly/n/";
    private static String monthKUrl = "http://image.sinajs.cn/newchart/monthly/n/";

    private TextView title;
    ImageView fenshi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view==null){
            view = inflater.inflate(R.layout.fragment_shares, container, false);
            etInput = view.findViewById(R.id.edit_input);
            imageSearch = view.findViewById(R.id.image_search);
            title=view.findViewById(R.id.fragment_shares_title);
            fenshi=view.findViewById(R.id.fenshi_img);

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
                            String json = response;
                            Log.e("test",json);
                            title.setText(json);


                        } catch (Exception e) {
                        }
                    }
                });
            }

            @Override
            public void failed(int httpStatusCode, String error) {
                Log.e("test",error);
            }
        });

       Glide.with(view).asGif().load(fenShiUrl+str+".gif").into(fenshi);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }







}
