package com.android.commonframe.ui.customview.XRecyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.commonframe.R;
import com.android.commonframe.tools.StringUtil;
import com.android.commonframe.ui.customview.XRecyclerView.progressindicator.AVLoadingIndicatorView;


public class LoadingMoreFooter extends LinearLayout {

    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    public final static int STATE_CLICK_LOAD = 3;
    private SimpleViewSwitcher progressCon;
    private TextView mText;
    private boolean isLoadMoreClickStyle;
    private String clickLoadDesc;


    public LoadingMoreFooter(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView() {
        setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setPadding(0, 40, 0, 40);
        setLayoutParams(lp);
        progressCon = new SimpleViewSwitcher(getContext());
        progressCon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        progressCon.setView(progressView);

        addView(progressCon);
        mText = new TextView(getContext());
        mText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        mText.setText(getResources().getString(R.string.listview_loading));

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.dimens_10_dp), 0, 0, 0);
        mText.setLayoutParams(layoutParams);
        addView(mText);
    }

    public void setProgressStyle(int style) {
        if (style == ProgressStyle.SysProgress) {
            ProgressBar progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleSmall);
            progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.round_progress_bar));
            progressCon.setView(progressBar);
        } else {
            AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(0xffB5B5B5);
            progressView.setIndicatorId(style);
            progressCon.setView(progressView);
        }
    }

    public void setProgressStyle(int style, boolean isLoadMoreClickStyle, String clickLoadDesc) {
        this.clickLoadDesc = clickLoadDesc;
        this.isLoadMoreClickStyle = isLoadMoreClickStyle;
        if (style == ProgressStyle.SysProgress) {
            ProgressBar progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleSmall);
            progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.round_progress_bar));
            progressCon.setView(progressBar);
        } else {
            AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(0xffB5B5B5);
            progressView.setIndicatorId(style);
            progressCon.setView(progressView);
        }
    }


    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                progressCon.setVisibility(View.VISIBLE);
                mText.setText(getResources().getString(R.string.listview_loading));
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                mText.setText(getResources().getString(R.string.nomore_loading));
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mText.setText(getResources().getString(R.string.nomore_loading));
                progressCon.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_CLICK_LOAD:
                if (isLoadMoreClickStyle) {
                    mText.setText(StringUtil.concatString(clickLoadDesc, getResources().getString(R.string.click_more_flag)));
                } else {
                    mText.setText(getResources().getString(R.string.nomore_loading));
                }
                progressCon.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }

    }
}
