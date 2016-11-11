package com.android.commonframe.tools;

import android.app.Activity;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 定位工具类
 * Created by feilong.guo on 16/11/11.
 */
public class LocationUtil {
    private static LocationClient sMLocationClient;
    private static BDLocationListener sBDLocationListener;
    private static LocationClientOption option;

    private LocationUtil() {

    }

    /**
     * DO NOT forget unRegistBDLocationListener in your onDestroy life cycle
     *
     * @param bdLocationLisstener for UI callback to get location
     */
    public static void initClientAndGetLocation(final Activity thisActivity, final BDLocationListener bdLocationLisstener) {
        sMLocationClient = new LocationClient(thisActivity.getApplicationContext());
        sBDLocationListener = bdLocationLisstener;
        sMLocationClient.registerLocationListener(sBDLocationListener);
        LocationClientOption locationOption = getLocationOption();
        sMLocationClient.setLocOption(locationOption);
        sMLocationClient.start();
    }

    private static LocationClientOption getLocationOption() {
        if (option == null) {
            option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//Set location mode
            option.setCoorType("gcj02");//Set positioning result coordinate
            int span = 0;
            option.setScanSpan(span);//set location time,default 0,location only once
            option.setIsNeedAddress(true);//set is need address information
            option.setOpenGps(true);//set is open GRS
            option.setLocationNotify(false);//set location notify
            option.setIsNeedLocationDescribe(true);
            option.setIsNeedLocationPoiList(true);// set POI result
            option.setIgnoreKillProcess(false);//if need stop the SDK service
            option.SetIgnoreCacheException(false);//set if need collect CRASresultsH data
            option.setEnableSimulateGps(false);//Filter GPS simulation
        }
        return option;
    }

    /**
     * on UI destory DO NOT FORGET UnRegist the listener
     */
    public static void unRegistBDLocationListener() {
        if (sMLocationClient != null) {
            sMLocationClient.stop();
            sMLocationClient.unRegisterLocationListener(sBDLocationListener);
            sBDLocationListener = null;
            sMLocationClient = null;
            option = null;
        }
    }

}
