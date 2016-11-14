package com.android.xingyi.entity;

import android.widget.TextView;

import com.android.xingyi.ui.fragment.base.Fragment;

/**
 * Created by Feilong.Guo on 2016/11/13.
 */

public class HomeBottomBarEntity {
    private String bottomBarDesc;
    private int bottomBarTopIcon;
    private Fragment bottomBar4Fragment;
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

    public Fragment getBottomBar4Fragment() {
        return bottomBar4Fragment;
    }

    public void setBottomBar4Fragment(Fragment bottomBar4Fragment) {
        this.bottomBar4Fragment = bottomBar4Fragment;
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
