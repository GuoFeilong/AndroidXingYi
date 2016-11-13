package com.android.xingyi.appinterface;

import android.view.View;

import java.util.Calendar;

/**
 * 处理view快速点击的回调
 * Created by Feilong.Guo on 2016/11/13.
 */

public abstract class NoDoubleClickListener implements View.OnClickListener {
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    public static final int NO_DELAY_TIME = 10;
    private long lastClickTime = 0;
    private int useTime;

    public NoDoubleClickListener() {
        useTime = MIN_CLICK_DELAY_TIME;
    }

    public NoDoubleClickListener(int delayTime) {
        useTime = delayTime;
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > useTime) {
            lastClickTime = currentTime;
            onClickNoDouble(v);
        }
    }

    public abstract void onClickNoDouble(View view);
}
