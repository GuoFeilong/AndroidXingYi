package com.android.commonframe.tools;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MeizuUtil {
    public static void hideTitleBarBeforeSetContentView(Activity activity) {
        if (!isMeizuMx2OrHigher())
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    private static void hideAndroid44TitleBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            ActionBar actionbar = activity.getActionBar();
            if (actionbar == null) {
                return;
            }

            try {
                Method method = Class.forName("android.app.ActionBar").getMethod("setActionBarViewCollapsable",
                        new Class[]{boolean.class});
                try {
                    method.invoke(actionbar, true);
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                }
            } catch (SecurityException e) {
            } catch (NoSuchMethodException e) {
            } catch (ClassNotFoundException e) {
            }
        }
    }

    private static void defaultHideTitleBar(Activity activity) {
        try {
            ViewGroup DecorView = (ViewGroup) activity.getWindow().getDecorView();
            if (DecorView == null)
                return;

            ViewGroup view = (ViewGroup) DecorView.getChildAt(0);
            if (view == null)
                return;

            View tView = view.getChildAt(0);

            if (tView != null) {
                if (tView.getLayoutParams() != null) {
                    tView.getLayoutParams().height = 0;
                }
            }
        } catch (Exception e) {

        }
    }

    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }
    public static boolean isMeizuMx2OrHigher() {
        if (Build.VERSION.SDK_INT < 14)
            return false;

        String model = Build.MODEL;
        return model.equals("M040") || model.equals("M045") || model.startsWith("M35") || model.startsWith("M46")
                || model.equalsIgnoreCase("MX4") || model.equals("MX4 Pro")
                || model.equals("m1 note") || model.equals("m1");
    }

    public static boolean isMeizuMx3OrHigher() {
        if (Build.VERSION.SDK_INT < 16)
            return false;

        String model = Build.MODEL;
        return model.startsWith("M35") || model.startsWith("M46") || model.equalsIgnoreCase("MX4")
                || model.equals("MX4 Pro") || model.equals("m1 note")
                || model.equals("m1");
    }

    public static boolean isMeizuM9() {
        String model = Build.MODEL;
        return model.equals("M9");
    }

    public static boolean isMeizuMx() {
        String model = Build.MODEL;
        return (model.equals("M030") || model.equals("M031") || model.equals("M032"));
    }

    public static boolean isMeizuMx2() {
        if (Build.VERSION.SDK_INT < 14)
            return false;

        String model = Build.MODEL;
        return (model.equals("M040") || model.equals("M045"));
    }

    public static boolean isMeizuMx3() {
        if (Build.VERSION.SDK_INT < 14)
            return false;

        String model = Build.MODEL;
        return model.startsWith("M35");
    }

    public static boolean isMeizuMx4() {
        if (Build.VERSION.SDK_INT < 14)
            return false;

        String model = Build.MODEL;
        return model.startsWith("M46") || model.equalsIgnoreCase("MX4") || model.equals("MX4 Pro")
                || model.equals("m1 note") || model.equals("m1");
    }
}
