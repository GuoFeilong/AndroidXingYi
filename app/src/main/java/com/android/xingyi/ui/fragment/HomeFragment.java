package com.android.xingyi.ui.fragment;

import android.widget.TextView;

import com.android.xingyi.R;
import com.android.xingyi.ui.fragment.base.BaseAbsBaseFragment;

import butterknife.Bind;

/**
 * Created by feilong.guo on 16/11/14.
 */

public class HomeFragment extends BaseAbsBaseFragment {
    @Bind(R.id.tv_banner)
    TextView homeDesc;

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        homeDesc.setText("我是首页Fragment中的Banner");
    }

    @Override
    protected int initFragmentLayout() {
        return R.layout.fragment_home;
    }
}
