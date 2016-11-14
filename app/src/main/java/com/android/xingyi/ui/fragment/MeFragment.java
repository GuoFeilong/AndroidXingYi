package com.android.xingyi.ui.fragment;

import android.widget.TextView;

import com.android.xingyi.R;
import com.android.xingyi.ui.fragment.base.BaseAbsBaseFragment;

import butterknife.Bind;

/**
 * Created by feilong.guo on 16/11/14.
 */

public class MeFragment extends BaseAbsBaseFragment {
    @Bind(R.id.tv_me_desc)
    TextView meDesc;

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        meDesc.setText("我的Fragment");
    }

    @Override
    protected int initFragmentLayout() {
        return R.layout.fragment_me;
    }
}
