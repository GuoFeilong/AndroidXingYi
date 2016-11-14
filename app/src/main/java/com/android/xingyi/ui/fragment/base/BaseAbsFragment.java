package com.android.xingyi.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by feilong.guo on 16/11/14.
 */

public abstract class BaseAbsFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initData();
        initView();
        initEvent();
        return setFragmentContentView(inflater, container, savedInstanceState);
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract int initFragmentLayout();

    protected View setFragmentContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(initFragmentLayout(), container, false);
    }

}
