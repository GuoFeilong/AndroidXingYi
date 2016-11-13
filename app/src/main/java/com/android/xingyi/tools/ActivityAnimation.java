package com.android.xingyi.tools;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.xingyi.R;


/**
 * activity专场动画
 * Created by Feilong.Guo on 2016/11/13.
 */
public class ActivityAnimation {
    public static void bottomIn(Activity context) {
        context.overridePendingTransition(R.anim.activity_from_bottom_in, 0);
    }

    public static void topOut(Activity context) {
        context.overridePendingTransition(0, R.anim.activity_from_top_out);
    }

    public static void rightIn(Activity context) {
        context.overridePendingTransition(R.anim.activity_from_right_in, 0);
    }

    public static void leftOut(Activity context) {
        context.overridePendingTransition(0, R.anim.activity_to_right_out);
    }

    public static void rightOut(Activity context) {
        context.overridePendingTransition(0, R.anim.activity_to_left_out);
    }

    public static void leftIn(Activity context) {
        context.overridePendingTransition(R.anim.activity_from_left_in, 0);
    }

    public static void bottomIn(Activity context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.activity_from_bottom_in);
        view.startAnimation(animation);
    }

    public static void topOut(Activity context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.activity_from_top_out);
        view.startAnimation(animation);
    }

    public static void rightIn(Activity context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.activity_from_right_in);
        view.startAnimation(animation);
    }

    public static void leftOut(Activity context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.activity_to_right_out);
        view.startAnimation(animation);
    }

    public static void rightOut(Activity context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.activity_to_left_out);
        view.startAnimation(animation);
    }

    public static void leftIn(Activity context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.activity_from_left_in);
        view.startAnimation(animation);
    }

}
