package com.android.xingyi.presenter.homepage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.commonframe.net.api.APIResponse;
import com.android.commonframe.tools.DensityUtils;
import com.android.commonframe.tools.LogUtil;
import com.android.commonframe.tools.T;
import com.android.xingyi.R;
import com.android.xingyi.appinterface.NoDoubleClickListener;
import com.android.xingyi.entity.HomeBottomBarEntity;
import com.android.xingyi.presenter.BasePresenter;
import com.android.xingyi.view.homepage.HomeBottomBarView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 本地数据用MVP模式获取
 * Created by Feilong.Guo on 2016/11/13.
 */

public class HomeBottomPresenter implements BasePresenter<HomeBottomBarView> {
    private HomeBottomBarView homeBottomBarView;
    private APIResponse<List<HomeBottomBarEntity>> localDada;

    @Override
    public void attach2View(HomeBottomBarView view) {
        this.homeBottomBarView = view;
    }

    @Override
    public void detach2View() {
        if (homeBottomBarView != null) {
            homeBottomBarView = null;
        }
    }

    public void getHomeBottomBarData() {
        // TODO: 2016/11/13 fragment暂时不绑定
        localDada = new APIResponse<>();
        List<HomeBottomBarEntity> mainBottomBarEntityList = new ArrayList<>();
        HomeBottomBarEntity tempEntity = new HomeBottomBarEntity();
        tempEntity.setBottomBarDesc("首页");
        tempEntity.setBottomBarTopIcon(R.drawable.selector_tab_home);
        tempEntity.setBottomBarSeleced(true);
        mainBottomBarEntityList.add(tempEntity);

        tempEntity = new HomeBottomBarEntity();
        tempEntity.setBottomBarDesc("发现");
        tempEntity.setBottomBarTopIcon(R.drawable.selector_tab_destination);
        tempEntity.setBottomBarSeleced(false);
        mainBottomBarEntityList.add(tempEntity);

        tempEntity = new HomeBottomBarEntity();
        tempEntity.setBottomBarDesc("我的");
        tempEntity.setBottomBarTopIcon(R.drawable.selector_tab_me);
        tempEntity.setBottomBarSeleced(false);
        mainBottomBarEntityList.add(tempEntity);

        localDada.setData(mainBottomBarEntityList);
        homeBottomBarView.bindData(localDada);
    }

    /**
     * 实例化一个底部tab点击栏
     *
     * @param mainBottomBarEntity 数据实体
     * @return 填充后的view
     */
    public TextView initBottomTextView(HomeBottomBarEntity mainBottomBarEntity) {
        String desc = null;
        Drawable drawableTop = null;
        boolean isSeleced = false;
        if (null != mainBottomBarEntity) {
            desc = mainBottomBarEntity.getBottomBarDesc();
            isSeleced = mainBottomBarEntity.isBottomBarSeleced();
            drawableTop = ContextCompat.getDrawable(homeBottomBarView.getActivityContext(), mainBottomBarEntity.getBottomBarTopIcon());
            drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());
        }
        TextView mainBottomView = new TextView(homeBottomBarView.getActivityContext());
        mainBottomView.setText(desc);
        mainBottomView.setBackground(null);
        mainBottomView.setGravity(Gravity.CENTER);
        mainBottomView.setSelected(isSeleced);
        int currentTextColor = isSeleced ? R.color.at_color_default : R.color.at_color_captions_text;
        mainBottomView.setTextColor(ContextCompat.getColor(homeBottomBarView.getActivityContext(), currentTextColor));
        mainBottomView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        mainBottomView.setCompoundDrawables(null, drawableTop, null, null);
        mainBottomView.setCompoundDrawablePadding(DensityUtils.dp2px(homeBottomBarView.getActivityContext(), 3.F));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.F);
        lp.topMargin = DensityUtils.dp2px(homeBottomBarView.getActivityContext(), 6.F);
        lp.bottomMargin = DensityUtils.dp2px(homeBottomBarView.getActivityContext(), 4.F);
        mainBottomView.setLayoutParams(lp);
        return mainBottomView;
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
        mainBottomBarEntity.getBottomBarTextView()
                .setTextColor(ContextCompat.getColor(homeBottomBarView.getActivityContext(), R.color.at_color_default));
        // TODO: 2016/11/13 自己绑定fragment和实力类的关系

    }

    /**
     * 重置所有底部tab的状态
     *
     * @param selecedView   当前的textview
     * @param selecedEntity 当前的实体数据
     */
    private void resetTabAndFragment(final TextView selecedView, final HomeBottomBarEntity selecedEntity) {
        Observable.from(localDada.getData())
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
                        mainBottomBarEntity.getBottomBarTextView().setTextColor(ContextCompat.getColor(homeBottomBarView.getActivityContext(), R.color.at_color_captions_text));
                        // TODO: 2016/11/13 自己绑定fragment和实力类的关系
                    }
                });
    }


    public void addClickEventForCurrentView(final TextView selecedView, final HomeBottomBarEntity selecedEntity) {
        selecedView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                resetTabAndFragment(selecedView, selecedEntity);
                T.show(homeBottomBarView.getActivityContext(), "-->点击了" + selecedEntity.getBottomBarDesc(), Toast.LENGTH_SHORT);
            }
        });
    }

}
