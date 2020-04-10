package com.hongyan.life.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseFragment;

public class CalculatorFragment extends BaseFragment {

    View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root==null){
            root=inflater.inflate(R.layout.fragment_calculator,container,false);
        }
        return root;
    }
}
