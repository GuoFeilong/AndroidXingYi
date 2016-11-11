package com.android.xingyi;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.android.commonframe.net.RetrofitFactory;
import com.android.commonframe.tools.CollectionUtil;
import com.android.xingyi.api.apiservice.HostWorkUtils;
import com.android.xingyi.api.apiservice.NetworkService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feilong.guo on 16/11/11.
 */

public class MyApplication extends Application {
    private ActivityLifecycleCallbacks lifecycleCallbacks;
    private static List<Activity> runningTasks;
    /**
     * 注意不要写静态的Context在这里,因为会造成内存泄漏,所以声明一个application用他去获取context
     */
    private static MyApplication myApplication;
    private NetworkService networkService;


    /**
     * 获取Application
     *
     * @return
     */
    public static MyApplication getMyApplication() {
        if (myApplication == null) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
        initLifecycleCallback(lifecycleCallbacks);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        runningTasks = new ArrayList<>();
        myApplication = this;
    }


    /**
     * 初始化activity生命周期
     *
     * @param lifecycleCallbacks 生命周期回调
     */
    private void initLifecycleCallback(ActivityLifecycleCallbacks lifecycleCallbacks) {
        lifecycleCallbacks = new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                removeActivity(activity);
            }
        };
        registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }


    private static void addActivity(Activity activity) {
        if (!CollectionUtil.isEmpty(runningTasks)) {
            runningTasks.add(activity);
        }
    }

    private static void removeActivity(Activity activity) {
        if (!CollectionUtil.isEmpty(runningTasks)) {
            runningTasks.remove(activity);
        }
    }

    /**
     * 退出app
     */
    public static void exitApp() {
        if (!CollectionUtil.isEmpty(runningTasks)) {
            for (Activity activity : runningTasks) {
                if (activity != null) {
                    activity.finish();
                }
            }
        }

    }

    /**
     * 实例化app网络请求,MVP中的presenter中会用到,获取不同业务线的数据
     *
     * @return
     */
    public NetworkService getNetworkService() {
        if (null == networkService) {
            networkService = RetrofitFactory.getAPIService(
                    NetworkService.class,
                    HostWorkUtils.getInstance().getHostName(),
                    "",
                    BuildConfig.DEBUG
            );
        }
        return networkService;
    }


}
