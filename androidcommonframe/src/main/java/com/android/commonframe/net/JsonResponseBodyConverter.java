package com.android.commonframe.net;


import com.alibaba.fastjson.JSON;
import com.android.commonframe.tools.LogUtil;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private Type type;

    JsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        LogUtil.d("response = " + response);
        return JSON.parseObject(response, type);
    }
}
