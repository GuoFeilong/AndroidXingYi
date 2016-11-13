package com.android.xingyi.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.commonframe.tools.StringUtil;
import com.android.xingyi.MyApplication;
import com.android.xingyi.R;
import com.android.xingyi.appinterface.NoDoubleClickListener;
import com.android.xingyi.enumerations.ErrorPageType;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通用带标题和底部布局的activity
 * <p>
 * Created by Feilong.Guo on 2016/11/13.
 */

public abstract class BaseTitleAndBottomActivity extends BaseActivity {
    private float xPort;
    private float yPort;

    @Bind(R.id.include_common_title)
    View includeCommonTitle;
    @Bind(R.id.include_common_bottom)
    View includeCommonBottom;
    @Bind(R.id.include_common_failed)
    View includeCommonFaied;
    @Bind(R.id.fl_content_view_container)
    FrameLayout contentViewContainer;
    @Bind(R.id.rl_common_title_left)
    RelativeLayout commontTitleLeftContainer;
    @Bind(R.id.rl_common_title_content)
    RelativeLayout commontTitleContentContainer;
    @Bind(R.id.rl_common_title_right)
    RelativeLayout commontTitleRightContainer;
    @Bind(R.id.iv_common_title_left)
    ImageView commonTitleLeftIcon;
    @Bind(R.id.tv_common_title_left)
    TextView commonTitleLeftDesc;
    @Bind(R.id.iv_common_title_content)
    ImageView commonTitleContentIcon;
    @Bind(R.id.tv_common_title_content)
    TextView commonTitleContentDesc;
    @Bind(R.id.iv_common_title_right)
    ImageView commonTitleRightIcon;
    @Bind(R.id.tv_common_title_right)
    TextView commonTitleRightDesc;
    @Bind(R.id.iv_failed)
    ImageView failedIcon;
    @Bind(R.id.tv_failed_first_text)
    TextView failedFirstDesc;
    @Bind(R.id.tv_failed_second_text)
    TextView failedSeconedDesc;
    @Bind(R.id.tv_click_retry)
    TextView failedClickRetry;

    private ErrorPageType errorPageType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_title_and_bottom);
        ButterKnife.bind(this);
        abnormalExit(savedInstanceState);
        initBaseEvent();

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        contentViewContainer.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                xPort = event.getX();
                yPort = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(xPort - event.getX()) <= 20 && Math.abs(yPort - event.getY()) <= 20) {// If is move event don't hide the soft keyboard
                    final View view = getCurrentFocus();
                    if (view != null) {
                        final boolean consumed = super.dispatchTouchEvent(event);
                        final View viewTmp = getCurrentFocus();
                        final View viewNew = viewTmp != null ? viewTmp : view;
                        if (viewNew.equals(view)) {
                            final Rect rect = new Rect();
                            final int[] coordinates = new int[2];
                            view.getLocationOnScreen(coordinates);
                            rect.set(coordinates[0], coordinates[1], coordinates[0] + view.getWidth(), coordinates[1] + view.getHeight());
                            final int x = (int) event.getX();
                            final int y = (int) event.getY();
                            if (rect.contains(x, y)) {
                                return consumed;
                            }
                        } else if (viewNew instanceof EditText) {
                            return consumed;
                        }
                        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(viewNew.getWindowToken(), 0);
                        viewNew.clearFocus();
                        return consumed;
                    }
                }
        }
        return super.dispatchTouchEvent(event);
    }

    private void abnormalExit(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            MyApplication.exitApp();
            Intent it = new Intent();
            it.setClass(this, SwipBackActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(it);
        }
    }

    private void initBaseEvent() {
        includeCommonFaied.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {

            }
        });

        commontTitleLeftContainer.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                finish();
            }
        });

        commontTitleRightContainer.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                clickCommonTitleRight();
            }
        });

        initData();
        initView();
        initEvent();
    }

    protected abstract void clickCommonTitleRight();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initEvent();

    /**
     * 根据错误类型做不同的操作
     *
     * @param errorPageType 错误类型
     */
    protected void clickToRetry(ErrorPageType errorPageType) {

    }

    /**
     * 没有网络错误
     */
    protected void showNoNetWork() {
        contentViewContainer.setVisibility(View.GONE);
        includeCommonFaied.setVisibility(View.VISIBLE);
        failedIcon.setImageResource(R.mipmap.failed_wifi_icon);
        failedFirstDesc.setText(getResources().getString(R.string.at_no_network));
        failedSeconedDesc.setVisibility(View.GONE);
        failedClickRetry.setText(getResources().getString(R.string.at_click_wifi_load));
        this.errorPageType = ErrorPageType.WIFI_ERROR;
    }

    /**
     * 没有数据错误
     */
    protected void showNoData() {
        contentViewContainer.setVisibility(View.GONE);
        includeCommonFaied.setVisibility(View.VISIBLE);
        failedIcon.setImageResource(R.mipmap.failed_no_data_icon);
        failedFirstDesc.setText(getResources().getString(R.string.at_no_failed_data));
        failedSeconedDesc.setVisibility(View.GONE);
        failedClickRetry.setText(getResources().getString(R.string.at_click_no_data_load));
        this.errorPageType = ErrorPageType.EMPTY_ERROR;
    }

    /**
     * 服务器错误页面展示
     *
     * @param message 错误信息
     */
    protected void showServerError(String message) {
        contentViewContainer.setVisibility(View.GONE);
        includeCommonFaied.setVisibility(View.VISIBLE);
        failedIcon.setImageResource(R.mipmap.failed_server_icon);
        failedFirstDesc.setText(StringUtil.isEmpty(message) ? (getResources().getString(R.string.at_server_error_first_text)) : message);
        failedSeconedDesc.setText(getResources().getString(R.string.at_server_error_second_text));
        failedClickRetry.setText(getResources().getString(R.string.at_click_wifi_load));
        this.errorPageType = ErrorPageType.SERVER_ERROR;
    }

    /**
     * 隐藏错误页面,显示内容页
     */
    protected void showContentView() {
        contentViewContainer.setVisibility(View.VISIBLE);
        includeCommonFaied.setVisibility(View.GONE);
        this.errorPageType = ErrorPageType.NONE;
    }

    /**
     * 是否隐藏通用标题栏
     *
     * @param visible 是否隐藏
     */
    protected void userDefinedCommonTitleVisible(boolean visible) {
        int commonTitleState = visible ? View.VISIBLE : View.GONE;
        includeCommonTitle.setVisibility(commonTitleState);
    }

    /**
     * 是否隐藏通用bottombar
     *
     * @param visible 是否隐藏
     */
    protected void userDefinedCommonBottomVisible(boolean visible) {
        int commonTitleState = visible ? View.VISIBLE : View.GONE;
        includeCommonBottom.setVisibility(commonTitleState);
    }

    protected void userDefinedLeftIcon(int resID) {
        commonTitleLeftIcon.setVisibility(View.VISIBLE);
        commonTitleLeftIcon.setImageResource(resID);
        commonTitleLeftDesc.setVisibility(View.GONE);
    }

    protected void userDefinedLeftDesc(String desc) {
        commonTitleLeftIcon.setVisibility(View.GONE);
        commonTitleLeftDesc.setText(desc);
        commonTitleLeftDesc.setVisibility(View.VISIBLE);
    }

    protected void userDefinedContentIcon(int resID) {
        commonTitleContentIcon.setVisibility(View.VISIBLE);
        commonTitleContentIcon.setImageResource(resID);
        commonTitleContentDesc.setVisibility(View.GONE);
    }

    protected void userDefinedContentDesc(String desc) {
        commonTitleContentIcon.setVisibility(View.GONE);
        commonTitleContentDesc.setText(desc);
        commonTitleContentDesc.setVisibility(View.VISIBLE);
    }

    protected void userDefinedRightIcon(int resID) {
        commonTitleRightIcon.setVisibility(View.VISIBLE);
        commonTitleRightIcon.setImageResource(resID);
        commonTitleRightDesc.setVisibility(View.GONE);
    }

    protected void userDefinedRightDesc(String desc) {
        commonTitleRightIcon.setVisibility(View.GONE);
        commonTitleRightDesc.setText(desc);
        commonTitleRightDesc.setVisibility(View.VISIBLE);
    }
}
