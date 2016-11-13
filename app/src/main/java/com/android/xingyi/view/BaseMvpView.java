package com.android.xingyi.view;

import android.content.Context;

import com.android.commonframe.net.api.APIResponse;

/**
 * view接口基础类,
 * Created by Feilong.Guo on 2016/11/13.
 */

public interface BaseMvpView<T> {
    Context getActivityContext();

    void showNetWorkProgress();

    void disMissProgress();

    void onError(Throwable e);

    // 所有API接口的基础response
    void bindData(APIResponse<T> result);
}
