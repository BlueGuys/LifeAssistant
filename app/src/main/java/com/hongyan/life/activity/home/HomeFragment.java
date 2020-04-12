package com.hongyan.life.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hongyan.life.MyApplication;
import com.hongyan.life.R;
import com.hongyan.life.activity.BaseFragment;
import com.hongyan.life.activity.device.DeviceInfoActivity;
import com.hongyan.life.bean.Memo;
import com.hongyan.life.bean.MemoDao;
import com.hongyan.life.bean.WeatherNow;
import com.hongyan.life.net.LFHttpRequestUtils;
import com.hongyan.life.net.LFNetworkCallback;
import com.hongyan.life.utils.GsonUtils;
import com.hongyan.life.view.CommonDialog;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityConfig;
import com.lljjcoder.style.cityjd.JDCityPicker;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final String TAG = "HomeFragment";

    private View view;

    RelativeLayout weatherLayout;
    TextView weatherCityName;

    private Button button;
    private ImageView weatherImg;
    private TextView tipsTv, airLevelTv, tempTv, temp12Tv;

    private ImageView memoAdd;
    private ListView memoList;
    private List<Memo> memos;

    private MemoAdapter memoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            initView();
        }
        return view;
    }

    private void initView() {
        initWeather();
        initMemo();
        initTips();
    }

    private void initTips() {
        view.findViewById(R.id.fragment_home_info_tips_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DeviceInfoActivity.class));
            }
        });
    }


    private void initWeather() {
        weatherLayout = view.findViewById(R.id.fragment_home_weather_layout);
        weatherCityName = view.findViewById(R.id.fragment_home_city_name);
        weatherImg = view.findViewById(R.id.fragment_home_weather_img);
        tipsTv = view.findViewById(R.id.fragment_home_weather_tips);
        airLevelTv = view.findViewById(R.id.fragment_home_weather_air_level);
        tempTv = view.findViewById(R.id.fragment_home_weather_temp);
        temp12Tv = view.findViewById(R.id.fragment_home_weather_temp12);

        weatherCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCityPicker();
            }
        });
    }

    private void showCityPicker() {
        JDCityPicker cityPicker = new JDCityPicker();
        JDCityConfig jdCityConfig = new JDCityConfig.Builder().build();

        jdCityConfig.setShowType(JDCityConfig.ShowType.PRO_CITY);
        cityPicker.init(getActivity());
        cityPicker.setConfig(jdCityConfig);
        cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                weatherCityName.setText(city.getName());
                requestWeather(city.getName());
            }

            @Override
            public void onCancel() {
            }
        });
        cityPicker.showCityPicker();
    }


    private void initMemo() {
        memoAdd = view.findViewById(R.id.fragment_home_memo_add);
        memoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMemoActivity.class);
                getActivity().startActivityForResult(intent, 10000);
            }
        });

        memoList = view.findViewById(R.id.fragment_home_memo_list);
        memos = new ArrayList<>();
        memoAdapter = new MemoAdapter(getActivity(), memos);
        memoList.setAdapter(memoAdapter);
        memoList.setOnItemClickListener(this);
        memoList.setOnItemLongClickListener(this);

        getAllMemos();
    }

    private void getAllMemos() {
        MemoDao memoDao = MyApplication.getDaoSession().getMemoDao();
        List<Memo> qmemos = memoDao.loadAll();
        if (qmemos != null) {
            memos.clear();
            memos.addAll(qmemos);
            memoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String name = weatherCityName.getText().toString();
        requestWeather(name);
    }

    private void requestWeather(String name) {
        if (name == null || name.trim().length() == 0) {
            return;
        }
        name = name.replace("市", "");
        String url = "http://www.tianqiapi.com/api?version=v6&appid=45324354&appsecret=lw9iMb8d";
        try {
            url += "&city=" + name;
            Log.e("天气0", url);
        } catch (Exception e) {
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
                            String json = response;

                            WeatherNow weatherNow = GsonUtils.gsonResolve(json, WeatherNow.class);

                            Log.e("天气", response);
                            Log.e("天气1", weatherNow.getCity());

                            String wea_img = weatherNow.getWea_img();
                            weatherImg.setImageResource(getImageIdByWea(wea_img));
                            tipsTv.setText(weatherNow.getAir_tips());
                            airLevelTv.setText(weatherNow.getAir_level());
                            tempTv.setText(weatherNow.getTem());
                            temp12Tv.setText(weatherNow.getTem1() + "/" + weatherNow.getTem2());
                            weatherLayout.setBackgroundColor(getWeatherBackImg(weatherNow.getWea_img()));
                        } catch (Exception e) {
                        }
                    }
                });
            }

            @Override
            public void failed(int httpStatusCode, String error) {
                Log.d(TAG, httpStatusCode + "error :" + error);
            }
        });
    }

    /**
     * xue、lei、shachen、wu、bingbao、yun、yu、yin、qing
     *
     * @param wea_img
     * @return
     */
    private int getImageIdByWea(String wea_img) {
        switch (wea_img) {
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

    /**
     * 背景
     * @param wea_img
     * @return
     */
    private int getWeatherBackImg(String wea_img) {
        switch (wea_img) {
            case "wu":
            case "lei":
            case "bingbao":
            case "yin":
            case "yu":
            case "yun":
            case "xue":
                return R.color.gray_cc;
            case "shachen":
                return R.color.gray_8f;
            case "qing":
                return R.color.red;
            default:
                return R.color.appColorPositive;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), AddMemoActivity.class);
        intent.putExtra(AddMemoActivity.ADD_MEMO_ID_EXTRA, memos.get(position).getId());
        getActivity().startActivityForResult(intent, 10000);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        CommonDialog commonDialog = new CommonDialog(getActivity(), new CommonDialog.OnClickEvent() {
            @Override
            public void onClick(boolean bool) {
                if (bool) {
                    deleteMemo(memos.get(position).getId());
//                    getAllMemos();
                    memos.remove(memos.get(position));
                    memoAdapter.notifyDataSetChanged();
                }
            }
        }
        );
        commonDialog.show();
        commonDialog.setTitle("确定删除该条记录？");
        commonDialog.setDefine("确定");
        return true;
    }

    private void saveMemo(Memo memo) {
        MemoDao memoDao = MyApplication.getDaoSession().getMemoDao();
        memoDao.insertOrReplace(memo);
        getAllMemos();
    }

    private void deleteMemo(long memoId) {
        MemoDao memoDao = MyApplication.getDaoSession().getMemoDao();
        memoDao.deleteByKey(memoId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        if (data == null) {
            return;
        }
        if (requestCode == 10000) {
            if (resultCode == 1) {//添加成功
                long id = data.getLongExtra("id", 0);
                String content = data.getStringExtra("content");
                String title = data.getStringExtra("title");
                Memo memo = new Memo();
                memo.setTitle(title);
                memo.setTimestamp(System.currentTimeMillis());
                if (id > 0) {
                    memo.setId(id);
                }
                memo.setContent(content);
                saveMemo(memo);

            }
        }
    }
}
