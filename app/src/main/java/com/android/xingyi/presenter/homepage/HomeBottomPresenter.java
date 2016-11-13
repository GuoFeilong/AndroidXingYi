package com.android.xingyi.presenter.homepage;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.commonframe.net.api.APIResponse;
import com.android.commonframe.tools.DensityUtils;
import com.android.xingyi.R;
import com.android.xingyi.entity.HomeBottomBarEntity;
import com.android.xingyi.presenter.BasePresenter;
import com.android.xingyi.view.homepage.HomeBottomBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地数据用MVP模式获取
 * Created by Feilong.Guo on 2016/11/13.
 */

public class HomeBottomPresenter implements BasePresenter<HomeBottomBarView> {
    private HomeBottomBarView homeBottomBarView;

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
        APIResponse<List<HomeBottomBarEntity>> test = new APIResponse<>();
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

        test.setData(mainBottomBarEntityList);
        homeBottomBarView.bindData(test);
    }


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


}
