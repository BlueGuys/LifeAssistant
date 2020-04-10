package com.hongyan.life.activity.translate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseFragment;
import com.hongyan.life.net.LFHttpRequestUtils;
import com.hongyan.life.net.LFNetworkCallback;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class TranslateFragment extends BaseFragment {

    private View view;
    private EditText etInput;
    private ImageView imageSearch;
    private TextView tvResult;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_translate, container, false);
        etInput = view.findViewById(R.id.edit_input);
        imageSearch = view.findViewById(R.id.image_search);
        tvResult = view.findViewById(R.id.tv_result);
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(etInput.getText().toString());
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }


    private void search(String searchKey) {
        String url = null;
        try {
            url = "http://fanyi.youdao.com/openapi.do?keyfrom=wei54544545" + "&key=86156187&type=data&doctype=json&version=1.1&q=" + URLEncoder.encode(searchKey, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, Object> parameterList = new LinkedHashMap<>();
        parameterList.put("app_type", 1);
        LFHttpRequestUtils.postSyn(url, parameterList, new LFNetworkCallback() {
            @Override
            public void completed(String response) {
                tvResult.setText(response);
            }

            @Override
            public void failed(int httpStatusCode, String error) {
                tvResult.setText(error);
                String errorMsg = "网络错误  [httpStatusCode" + httpStatusCode + "   error:" + error + "]";
            }

        });
    }


}
