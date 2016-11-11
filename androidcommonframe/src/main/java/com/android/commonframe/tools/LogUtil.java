package com.android.commonframe.tools;

import android.text.TextUtils;

import com.android.commonframe.net.RetrofitFactory;
import com.orhanobut.logger.Logger;

/**
 * 日志输出工具,根据DEBUG模式自动控制日志输出
 * Created by feilong.guo on 16/11/11.
 */
public final class LogUtil {

    private LogUtil() {
    }

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        String customTagPrefix = "at_net_log";
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void d(String message, Object... args) {
        if (!RetrofitFactory.issIsShowLog()) return;
        d(String.format(message, args));
    }

    public static void d(String content) {
        if (!RetrofitFactory.issIsShowLog()) return;
        Logger.d(content);
    }

    public static void d(String content, Throwable tr) {
        if (!RetrofitFactory.issIsShowLog()) return;
        Logger.d(content);
    }

    public static void e(String content) {
        if (!RetrofitFactory.issIsShowLog()) return;
        Logger.e(content);
    }

    public static void e(String content, Throwable tr) {
        if (!RetrofitFactory.issIsShowLog()) return;
        Logger.e(content);
    }

    public static void i(String content) {
        if (!RetrofitFactory.issIsShowLog()) return;
        Logger.i(content);
    }

    public static void i(String content, Throwable tr) {
        if (!RetrofitFactory.issIsShowLog()) return;
        Logger.i(content);
    }

    public static void v(String content) {
        if (!RetrofitFactory.issIsShowLog()) return;
        String tag = generateTag();
        Logger.v(content);
    }

    public static void v(String content, Throwable tr) {
        if (!RetrofitFactory.issIsShowLog()) return;
        Logger.v(content);
    }

    public static void w(String content) {
        if (!RetrofitFactory.issIsShowLog()) return;
        Logger.w(content);
    }

    public static void w(String content, Throwable tr) {
        if (!RetrofitFactory.issIsShowLog()) return;
        Logger.w(content);
    }

    public static void w(Throwable tr) {
        if (!RetrofitFactory.issIsShowLog()) return;
        String tag = generateTag();
        Logger.w(tag,tr);
    }


    public static void wtf(String content) {
        if (!RetrofitFactory.issIsShowLog()) return;
        Logger.wtf(content);
    }

    public static void wtf(String content, Throwable tr) {
        if (!RetrofitFactory.issIsShowLog()) return;
        Logger.wtf(content);
    }

    public static void wtf(Throwable tr) {
        if (!RetrofitFactory.issIsShowLog()) return;
        String tag = generateTag();
        Logger.wtf(tag, tr);
    }

    public static void log(String msg) {
        if (RetrofitFactory.issIsShowLog()) {
            Logger.i("asiatravel", msg);
        }
    }

}
