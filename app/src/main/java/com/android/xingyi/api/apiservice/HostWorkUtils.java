package com.android.xingyi.api.apiservice;


import android.text.TextUtils;

import com.android.xingyi.MyApplication;
import com.android.xingyi.R;


/**
 * 配置请求的服务器地址
 */
public class HostWorkUtils {
    public static final String DEVELOP = "http://10.7.2.100:8888";
    public static final String QA_TEST = "http://10.7.2.117:8888";
    public static final String OUTER_NET = "http://36.110.94.19:28888";
    private static String currentHostAddress;

    private HostWorkUtils() {
    }

    public static HostWorkUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void changeHostAddress(String currentHostAdd) {
        currentHostAddress = currentHostAdd;
    }

    public String getHostName() {
        if (!TextUtils.isEmpty(MyApplication.getMyApplication().getString(R.string.build_host))) {
            return MyApplication.getMyApplication().getString(R.string.build_host);
        }
        return QA_TEST;
    }

    private static class SingletonHolder {
        private static final HostWorkUtils INSTANCE = new HostWorkUtils();
    }
}
