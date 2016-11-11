package com.android.commonframe.ui.customview.loading;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.android.commonframe.R;


/**
 * Created by jsion on 16/8/7.
 */
public class ATCircleDialog extends Dialog {
    private int mLoadingViewLayout;
    private boolean mCancelable;
    private boolean mOutsideCancelable;

    private ATCircleDialog(Context context) {
        this(context, 0);
    }

    private ATCircleDialog(Builder builder) {
        this(builder.mContext, 0);
        mLoadingViewLayout = builder.mLoadingViewLayout;
        mCancelable = builder.mCancelable;
        mOutsideCancelable = builder.mOutsideCancelable;
    }

    private ATCircleDialog(Context context, int themeResId) {
        super(context, R.style.Translucent_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mLoadingViewLayout);
        setCancelable(mCancelable);
        setCanceledOnTouchOutside(mOutsideCancelable);
    }

    public static class Builder {
        private Context mContext;
        private int mLoadingViewLayout;
        private boolean mCancelable;
        private boolean mOutsideCancelable;

        public Builder(Context context, int loadingViewLayout) {
            mContext = context;
            mLoadingViewLayout = loadingViewLayout;
        }

        public Builder cancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public Builder outsideCancelable(boolean outsideCancelable) {
            this.mOutsideCancelable = outsideCancelable;
            return this;
        }

        public ATCircleDialog build() {
            return new ATCircleDialog(this);
        }
    }

}

