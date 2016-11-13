package com.android.xingyi.ui.activity.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.android.xingyi.R;
import com.android.xingyi.tools.ActivityAnimation;
import com.android.xingyi.ui.widget.SwipeBackLayout;

/**
 * 支持滑动删除的activity
 * Created by Feilong.Guo on 2016/11/13.
 */
public class SwipBackActivity extends BaseActivity {
    private SwipeBackLayout swipeBackLayout;
    private boolean needAnimation = true;    //默认需要专场动画


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initSlideBack();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initSlideBack();
    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (needAnimation) {
            ActivityAnimation.leftOut(this);
        }
    }

    /**
     * 设置activity是否需要专场动画
     *
     * @param b
     */
    public void setNeedAnimation(boolean b) {
        needAnimation = b;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (needAnimation) {
            ActivityAnimation.rightIn(this);
        }
    }

    /**
     * 设置滑动模式
     *
     * @param mode
     */
    public void setSlidingMode(SwipeBackLayout.Sliding mode) {
        if (swipeBackLayout == null) {
            throw new NullPointerException("ATSwipeBackLayout is null,Please call after the setContentView");
        }
        swipeBackLayout.setSlidingMode(mode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (needAnimation) {
            ActivityAnimation.rightIn(this);
        }
    }

    @SuppressLint("InflateParams")
    private void initSlideBack() {
        swipeBackLayout = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.layout_swipeback, null);
        swipeBackLayout.attachToActivity(this);
    }

    /**
     * 设置activity是否支持滑动删除
     *
     * @param b
     */
    protected void setEnableGesture(boolean b) {
        swipeBackLayout.setEnableGesture(b);
    }
}
