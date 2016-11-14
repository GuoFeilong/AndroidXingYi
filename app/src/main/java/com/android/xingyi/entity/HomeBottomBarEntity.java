package com.android.xingyi.entity;

import android.widget.TextView;

import com.android.xingyi.ui.fragment.base.BaseFragment;

/**
 * Created by Feilong.Guo on 2016/11/13.
 */

public class HomeBottomBarEntity {
    private String bottomBarDesc;
    private int bottomBarTopIcon;
    private BaseFragment bottomBar4BaseFragment;
    private TextView bottomBarTextView;
    private boolean bottomBarSeleced;

    public String getBottomBarDesc() {
        return bottomBarDesc;
    }

    public void setBottomBarDesc(String bottomBarDesc) {
        this.bottomBarDesc = bottomBarDesc;
    }

    public int getBottomBarTopIcon() {
        return bottomBarTopIcon;
    }

    public void setBottomBarTopIcon(int bottomBarTopIcon) {
        this.bottomBarTopIcon = bottomBarTopIcon;
    }

    public BaseFragment getBottomBar4BaseFragment() {
        return bottomBar4BaseFragment;
    }

    public void setBottomBar4BaseFragment(BaseFragment bottomBar4BaseFragment) {
        this.bottomBar4BaseFragment = bottomBar4BaseFragment;
    }

    public TextView getBottomBarTextView() {
        return bottomBarTextView;
    }

    public void setBottomBarTextView(TextView bottomBarTextView) {
        this.bottomBarTextView = bottomBarTextView;
    }

    public boolean isBottomBarSeleced() {
        return bottomBarSeleced;
    }

    public void setBottomBarSeleced(boolean bottomBarSeleced) {
        this.bottomBarSeleced = bottomBarSeleced;
    }


}
