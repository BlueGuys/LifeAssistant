package com.hongyan.life.activity.translate;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseFragment;
import com.hongyan.life.net.LFHttpRequestUtils;
import com.hongyan.life.net.LFNetworkCallback;
import com.hongyan.life.utils.GsonUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class TranslateFragment extends BaseFragment {

    private View view;
    private EditText etInput;
    private ImageView imageSearch;
    private TextView tvResultA;
    private TextView tvResultA1;
    private TextView tvResultB;
    private TextView tvResultB1;
    private TextView tvResultC1;
    private TextView tvResultD1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_translate, container, false);
            etInput = view.findViewById(R.id.edit_input);
            imageSearch = view.findViewById(R.id.image_search);
            tvResultA = view.findViewById(R.id.tv_result_A);
            tvResultA1 = view.findViewById(R.id.tv_result_A1);
            tvResultB = view.findViewById(R.id.tv_result_B);
            tvResultB1 = view.findViewById(R.id.tv_result_B1);
            tvResultC1 = view.findViewById(R.id.tv_result_C1);
            tvResultD1 = view.findViewById(R.id.tv_result_D1);
            imageSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search(etInput.getText().toString());
                }
            });
            etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId,
                                              KeyEvent event) {
                    if ((actionId == 0 || actionId == 3) && event != null) {
                        search(etInput.getText().toString());
                        return true;
                    }
                    return false;
                }

            });
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
            public void completed(final String response) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TranslateBean bean = GsonUtils.gsonResolve(response, TranslateBean.class);
                            tvResultA.setText(bean.query);
                            tvResultA1.setText(Arrays.toString(bean.translation));
                            tvResultB1.setText(bean.basic.phonetic);
                            tvResultC1.setText(Arrays.toString(bean.basic.explains));

                            StringBuilder builder = new StringBuilder();
                            ArrayList<TranslateBean.Web> web = bean.web;
                            for (TranslateBean.Web w : web) {
                                builder.append("·");
                                builder.append(w.key);
                                builder.append("\n");
                                builder.append(Arrays.toString(w.value));
                                builder.append("\n");
                            }
                            tvResultD1.setText(builder.toString());
                        } catch (Exception e) {
                        }
                    }
                });
            }

            @Override
            public void failed(int httpStatusCode, String error) {
                String errorMsg = "网络错误  [httpStatusCode" + httpStatusCode + "   error:" + error + "]";
            }
        });
    }


}
