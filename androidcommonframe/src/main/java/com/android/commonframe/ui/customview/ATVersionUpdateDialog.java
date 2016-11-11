package com.android.commonframe.ui.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.commonframe.R;
import com.android.commonframe.tools.CollectionUtil;
import com.android.commonframe.tools.DensityUtils;
import com.android.commonframe.tools.StringUtil;

import java.util.List;


/**
 * Created by jsion on 16/5/5.
 */
public class ATVersionUpdateDialog extends Dialog {
    private TextView actionUpdate;
    private TextView laterUpdate;
    private View vLine;
    private LinearLayout versionDescContainer;
    private TextView newVersionName;
    private TextView newVersionSize;
    private int updateMode;
    private VersionUpdateEntity versionCheckResponse;
    private ATVersionUpdateClickListener versionUpdateClickListener;
    private Context context;
    private RoundProgressbar roundProgressbar;
    private ViewType viewType;
    private TextView progressPercent;


    public ATVersionUpdateDialog(Context context, VersionUpdateEntity atVersionCheckResponse, int updateMode) {
        super(context, R.style.dialog_version_update);
        this.updateMode = updateMode;
        this.context = context;
        this.versionCheckResponse = atVersionCheckResponse;
    }


    public void setVersionUpdateClickListener(ATVersionUpdateClickListener versionUpdateClickListener) {
        this.versionUpdateClickListener = versionUpdateClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_version_update);
        viewType = ViewType.VERSION_UPDATE;
        actionUpdate = (TextView) findViewById(R.id.tv_action_update);
        laterUpdate = (TextView) findViewById(R.id.tv_later_update);
        vLine = findViewById(R.id.view_update_v_line);
        versionDescContainer = (LinearLayout) findViewById(R.id.ll_version_desc_container);
        newVersionName = (TextView) findViewById(R.id.tv_new_version_name);
        newVersionSize = (TextView) findViewById(R.id.tv_new_version_size);
        addChildView();
        initEvent();
    }

    private void addChildView() {
        if (versionCheckResponse != null) {
            String versionName = versionCheckResponse.getVersionName();
            String versionSize = StringUtil.concatString(String.valueOf(versionCheckResponse.getApkSize()), context.getResources().getString(R.string.unit_m));
            if (!TextUtils.isEmpty(versionName)) {
                this.newVersionName.setText(StringUtil.concatString(context.getResources().getString(R.string.new_version), versionName));
            }
            if (!TextUtils.isEmpty(versionSize)) {
                this.newVersionSize.setText(StringUtil.concatString(context.getResources().getString(R.string.new_version_size), versionSize));
            }
            List<String> newVersionDesc = versionCheckResponse.getVersionUpdateDesc();
            if (!CollectionUtil.isEmpty(newVersionDesc)) {
                for (String desc : newVersionDesc) {
                    TextView tempDesc = new TextView(context);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.topMargin = DensityUtils.dp2px(getContext(), 10);
                    tempDesc.setLayoutParams(lp);
                    if (!TextUtils.isEmpty(desc)) {
                        tempDesc.setText(desc);
                    }
                    tempDesc.setTextColor(context.getResources().getColor(R.color.at_color_important_text_for_example_title));
                    versionDescContainer.addView(tempDesc);
                }
            }
        }
    }

    private void initEvent() {
        laterUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (versionUpdateClickListener != null) {
                    versionUpdateClickListener.onLaterUpdateClick();
                }
            }
        });
        actionUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (versionUpdateClickListener != null) {
                    versionUpdateClickListener.onActionUpdateClick();
                }
            }
        });
        switch (updateMode) {
            case UpdateMode.recommendMode:
                laterUpdate.setVisibility(View.VISIBLE);
                actionUpdate.setVisibility(View.VISIBLE);
                vLine.setVisibility(View.VISIBLE);
                break;
            case UpdateMode.forceMode:
                laterUpdate.setVisibility(View.GONE);
                actionUpdate.setVisibility(View.VISIBLE);
                vLine.setVisibility(View.GONE);
                break;
        }
    }

    private enum ViewType {
        VERSION_UPDATE,
        VERSION_UPDATE_PROGRESS

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public static class UpdateMode {
        public static final int recommendMode = 0;
        public static final int forceMode = 1;
        public static final int noReNoForce = 2;
    }

    public void showUpdateProgress(final long total, long currentProgress, RoundProgressbar.OnProgressDoneListener onProgressDoneListener) {
        setContentView(R.layout.dialog_version_update_progress);
        roundProgressbar = (RoundProgressbar) findViewById(R.id.pb_version_progress);
        progressPercent = (TextView) findViewById(R.id.tv_update_percent);
        currentProgress = (long) (total / 100.0 * 4 + currentProgress);
        String percent = StringUtil.getNotiPercent(currentProgress, total);
        progressPercent.setText(percent);
        roundProgressbar.setProgressDoneListener(onProgressDoneListener);
        roundProgressbar.setProgress(total, currentProgress);
    }

    public void showDownLoadProgress(int current) {
        if (viewType != ViewType.VERSION_UPDATE_PROGRESS) {
            setContentView(R.layout.dialog_version_update_progress);
            viewType = ViewType.VERSION_UPDATE_PROGRESS;
        }
        roundProgressbar = (RoundProgressbar) findViewById(R.id.pb_version_progress);
        progressPercent = (TextView) findViewById(R.id.tv_update_percent);
        progressPercent.setText(current + "%");
        roundProgressbar.setProgress(100, current);
    }

    public interface ATVersionUpdateClickListener {
        void onLaterUpdateClick();

        void onActionUpdateClick();
    }


    public static class VersionUpdateEntity {
        private String versionName;
        private List<String> versionUpdateDesc;
        private int apkSize;

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public List<String> getVersionUpdateDesc() {
            return versionUpdateDesc;
        }

        public void setVersionUpdateDesc(List<String> versionUpdateDesc) {
            this.versionUpdateDesc = versionUpdateDesc;
        }

        public long getApkSize() {
            return apkSize;
        }

        public void setApkSize(int apkSize) {
            this.apkSize = apkSize;
        }
    }
}
