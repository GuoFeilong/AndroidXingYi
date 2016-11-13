package com.android.xingyi.presenter;

/**
 * presenter基础类,
 * Created by Feilong.Guo on 2016/11/13.
 */
public interface BasePresenter<V> {
    void attach2View(V view);

    void detach2View();
}
