package com.android.commonframe.net.api;

import com.android.commonframe.FrameConstant;

/**
 * 服务器回复基础类
 * Created by feilong.guo on 16/11/11.
 */
public class APIResponse<T> {

    protected boolean success;

    protected int code;

    protected String message;

    protected T data;

    public boolean isSuccess() {
        return getCode() != 0 ? getCode() == FrameConstant.RESPONSE_CODE : success;

    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
