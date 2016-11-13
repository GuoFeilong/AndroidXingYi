package com.android.xingyi.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.commonframe.tools.CheckNetUtil;

/**
 * 监听网络变化的广播
 * Created by Feilong.Guo on 2016/11/13.
 */
public class NetCheckBroadcastReceiver extends BroadcastReceiver {
    public final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    public interface NetChangedListener {
        void networkError();

        void netConnected();

        void netWifiConnected();

        void net3GConnected();
    }

    private NetChangedListener mNetChangedListener;


    public void setNetChangedListener(NetChangedListener netChangedListener) {
        mNetChangedListener = netChangedListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)) {
            if (CheckNetUtil.isNetworkAvailable(context)) {
                if (mNetChangedListener != null) {
                    mNetChangedListener.netConnected();
                }
                if (CheckNetUtil.isWifiConnected(context)) {
                    if (mNetChangedListener != null) {
                        mNetChangedListener.netWifiConnected();
                    }

                } else {
                    if (mNetChangedListener != null) {
                        mNetChangedListener.net3GConnected();
                    }

                }
                // network error
            } else {
                if (mNetChangedListener != null) {
                    mNetChangedListener.networkError();
                }
            }
        }
    }
}

