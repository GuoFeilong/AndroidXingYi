package com.android.xingyi.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by feilong.guo on 16/11/14.
 */

public abstract class BaseAbsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentRootView = setFragmentContentView(inflater, container, savedInstanceState);
        initData();
        initView(fragmentRootView);
        initEvent();
        return fragmentRootView;
    }

    protected abstract void initData();

    protected abstract void initEvent();

    protected abstract int initFragmentLayout();

    protected View setFragmentContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(initFragmentLayout(), container, false);
    }

    protected void initView(View fragmentRootView) {
        ButterKnife.bind(this, fragmentRootView);

    }


}
