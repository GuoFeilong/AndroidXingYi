package com.android.xingyi.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.xingyi.R;
import com.android.xingyi.ui.activity.base.BaseTitleAndBottomActivity;

import butterknife.Bind;


public class MainActivity extends BaseTitleAndBottomActivity {
    @Bind(R.id.tv_banner)
    TextView banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void clickCommonTitleRight() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        userDefinedLeftContainerVisible(false);
        userDefinedRightContainerVisible(false);
        userDefinedContentDesc("星公益");
        banner.setText("这是首页的BanNer");
    }
}
