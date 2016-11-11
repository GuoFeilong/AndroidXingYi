package com.android.commonframe.net.api;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 网络请求基础类
 * Created by feilong.guo on 16/11/11.
 */
public class APIRequest<T> {

    @JSONField(name = "Parameters")
    private T requestObject;

    @JSONField(name = "Code")
    private String code;

    @JSONField(name = "ForeEndType")
    private int frontEndType = FrontEndType.ANDROID.getValue();

    public T getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(T requestObject) {
        this.requestObject = requestObject;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getFrontEndType() {
        return frontEndType;
    }

    public void setFrontEndType(int frontEndType) {
        this.frontEndType = frontEndType;
    }
}
