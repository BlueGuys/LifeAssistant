package com.hongyan.life.activity.shares;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseFragment;

public class SharesFragment extends BaseFragment {

    private View view;
    private LinearLayout roorLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shares, container, false);
        roorLayout = view.findViewById(R.id.linearLayout);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }


}
