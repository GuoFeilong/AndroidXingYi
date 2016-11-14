package com.android.xingyi.ui.fragment;

import android.widget.TextView;

import com.android.xingyi.R;
import com.android.xingyi.ui.fragment.base.BaseAbsBaseFragment;

import butterknife.Bind;

/**
 * Created by feilong.guo on 16/11/14.
 */

public class MessageFragment extends BaseAbsBaseFragment {
    @Bind(R.id.tv_message_desc)
    TextView messageDesc;

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        messageDesc.setText("消息Fragment");
    }

    @Override
    protected int initFragmentLayout() {
        return R.layout.fragment_message;
    }
}
