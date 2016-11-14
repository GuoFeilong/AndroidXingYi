package com.android.xingyi.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.commonframe.net.api.APIResponse;
import com.android.commonframe.tools.CollectionUtil;
import com.android.commonframe.tools.LogUtil;
import com.android.commonframe.tools.T;
import com.android.commonframe.ui.customview.loading.CircleDialogLoading;
import com.android.xingyi.R;
import com.android.xingyi.appinterface.NoDoubleClickListener;
import com.android.xingyi.entity.HomeBottomBarEntity;
import com.android.xingyi.presenter.homepage.HomeBottomPresenter;
import com.android.xingyi.ui.activity.base.BaseTitleAndBottomActivity;
import com.android.xingyi.view.homepage.HomeBottomBarView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MainActivity extends BaseTitleAndBottomActivity implements HomeBottomBarView {
    private HomeBottomPresenter homeBottomPresenterImpl;
    private CircleDialogLoading circleDialogLoading;
    private List<HomeBottomBarEntity> homeBottomBarData;


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
        setEnableGesture(false);
        userDefinedLeftContainerVisible(false);
        userDefinedRightContainerVisible(false);
        userDefinedContentDesc("项目名");
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
        if (circleDialogLoading != null && !circleDialogLoading.isShowing()) {
            circleDialogLoading.show();
        }
    }

    @Override
    public void disMissProgress() {
        if (circleDialogLoading != null && circleDialogLoading.isShowing()) {
            circleDialogLoading.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (circleDialogLoading != null && circleDialogLoading.isShowing()) {
            circleDialogLoading.dismiss();
        }
    }

    @Override
    public void bindData(APIResponse<List<HomeBottomBarEntity>> result) {
        if (result != null) {
            homeBottomBarData = result.getData();
            for (HomeBottomBarEntity currentEntity : homeBottomBarData) {
                if (currentEntity != null) {
                    TextView bottomTextView = homeBottomPresenterImpl.initBottomTextView(currentEntity);
                    currentEntity.setBottomBarTextView(bottomTextView);
                    addView2BottomBarContainer(bottomTextView);
                    addClickEventForCurrentView(bottomTextView, currentEntity);
                }
            }
            initTabFragments();
        }
    }

    private void initTabFragments() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_main, homeBottomBarData.get(0).getBottomBar4BaseFragment(), null)
                .add(R.id.content_main, homeBottomBarData.get(1).getBottomBar4BaseFragment(), null)
                .add(R.id.content_main, homeBottomBarData.get(2).getBottomBar4BaseFragment(), null)
                .hide(homeBottomBarData.get(1).getBottomBar4BaseFragment())
                .hide(homeBottomBarData.get(2).getBottomBar4BaseFragment())
                .show(homeBottomBarData.get(0).getBottomBar4BaseFragment())
                .commitAllowingStateLoss();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (homeBottomPresenterImpl != null) {
            homeBottomPresenterImpl.detach2View();
        }
    }


    /**
     * 把当前选中的tab设置成选中状态
     *
     * @param mainBottomView      当前的textview
     * @param mainBottomBarEntity 当前的实体数据
     */
    private void setCurrentTabSeleced(TextView mainBottomView, HomeBottomBarEntity mainBottomBarEntity) {
        mainBottomView.setSelected(true);
        mainBottomBarEntity.setBottomBarSeleced(true);
        mainBottomBarEntity.getBottomBarTextView().setTextColor(ContextCompat.getColor(this, R.color.at_color_default));
        getSupportFragmentManager()
                .beginTransaction()
                .show(mainBottomBarEntity.getBottomBar4BaseFragment())
                .commit();

    }

    /**
     * 重置所有底部tab的状态
     *
     * @param selecedView   当前的textview
     * @param selecedEntity 当前的实体数据
     */
    private void resetTabAndFragment(final TextView selecedView, final HomeBottomBarEntity selecedEntity) {
        if (!CollectionUtil.isEmpty(homeBottomBarData)) {
            Observable.from(homeBottomBarData)
                    .filter(new Func1<HomeBottomBarEntity, Boolean>() {
                        @Override
                        public Boolean call(HomeBottomBarEntity mainBottomBarEntity) {
                            return null != mainBottomBarEntity;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<HomeBottomBarEntity>() {
                        @Override
                        public void onCompleted() {
                            setCurrentTabSeleced(selecedView, selecedEntity);
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.e(e.toString());
                        }

                        @Override
                        public void onNext(HomeBottomBarEntity mainBottomBarEntity) {
                            mainBottomBarEntity.getBottomBarTextView().setSelected(false);
                            mainBottomBarEntity.setBottomBarSeleced(false);
                            mainBottomBarEntity.getBottomBarTextView().setTextColor(ContextCompat.getColor(MainActivity.this, R.color.at_color_captions_text));
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .hide(mainBottomBarEntity.getBottomBar4BaseFragment())
                                    .commit();

                        }
                    });
        }
    }


    public void addClickEventForCurrentView(final TextView selecedView, final HomeBottomBarEntity selecedEntity) {
        selecedView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                resetTabAndFragment(selecedView, selecedEntity);
                T.show(MainActivity.this, "-->点击了" + selecedEntity.getBottomBarDesc(), Toast.LENGTH_SHORT);
            }
        });
    }
}
