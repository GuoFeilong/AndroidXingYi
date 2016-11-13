package com.android.commonframe.ui.customview.loading;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.android.commonframe.R;


/**
 * Created by jsion on 16/8/7.
 */
public class CircleDialogLoading extends Dialog {
    private int mLoadingViewLayout;
    private boolean mCancelable;
    private boolean mOutsideCancelable;

    private CircleDialogLoading(Context context) {
        this(context, 0);
    }

    private CircleDialogLoading(Builder builder) {
        this(builder.mContext, 0);
        mLoadingViewLayout = builder.mLoadingViewLayout;
        mCancelable = builder.mCancelable;
        mOutsideCancelable = builder.mOutsideCancelable;
    }

    private CircleDialogLoading(Context context, int themeResId) {
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

        public Builder(Context context) {
            mContext = context;
            mLoadingViewLayout= R.layout.dialog_circle_loading;
        }

        public Builder cancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public Builder outsideCancelable(boolean outsideCancelable) {
            this.mOutsideCancelable = outsideCancelable;
            return this;
        }

        public Builder loadingViewLayout(int loadingViewLayout){
            this.mLoadingViewLayout=loadingViewLayout;
            return this;
        }

        public CircleDialogLoading build() {
            return new CircleDialogLoading(this);
        }
    }

}

