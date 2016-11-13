package com.android.xingyi.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.android.commonframe.net.api.APIResponse;
import com.android.commonframe.tools.CollectionUtil;
import com.android.commonframe.ui.customview.loading.CircleDialogLoading;
import com.android.xingyi.R;
import com.android.xingyi.entity.HomeBottomBarEntity;
import com.android.xingyi.presenter.homepage.HomeBottomPresenter;
import com.android.xingyi.ui.activity.base.BaseTitleAndBottomActivity;
import com.android.xingyi.view.homepage.HomeBottomBarView;

import java.util.List;

import butterknife.Bind;


public class MainActivity extends BaseTitleAndBottomActivity implements HomeBottomBarView {
    private HomeBottomPresenter homeBottomPresenterImpl;
    private CircleDialogLoading circleDialogLoading;
    private List<HomeBottomBarEntity> homeBottomBarData;
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
        homeBottomPresenterImpl = new HomeBottomPresenter();
        homeBottomPresenterImpl.attach2View(this);
        circleDialogLoading = new CircleDialogLoading
                .Builder(this)
                .outsideCancelable(false)
                .cancelable(true)
                .build();
    }


    @Override
    protected void initEvent() {
        banner.setText("这是首页的BanNer");
        userDefinedLeftContainerVisible(false);
        userDefinedRightContainerVisible(false);
        userDefinedContentDesc("星公益");
        userDefinedCommonBottomVisible(true);
        userDefinedBottomLineVisible(true);
        homeBottomPresenterImpl.getHomeBottomBarData();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void showNetWorkProgress() {

    }

    @Override
    public void disMissProgress() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void bindData(APIResponse<List<HomeBottomBarEntity>> result) {
        if (result != null) {
            homeBottomBarData = result.getData();
            for (HomeBottomBarEntity currentEntity : homeBottomBarData) {
                if (currentEntity != null) {
                    addView2BottomBarContainer(homeBottomPresenterImpl.initBottomTextView(currentEntity));
                }
            }
        }
    }
}
