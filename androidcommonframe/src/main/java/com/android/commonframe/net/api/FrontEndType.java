package com.android.commonframe.net.api;

/**
 * 客户端类型标示
 * Created by feilong.guo on 16/11/11.
 */

public enum FrontEndType {
    IOS(1),
    ANDROID(2),
    H5(3);

    private int value;

    private FrontEndType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
