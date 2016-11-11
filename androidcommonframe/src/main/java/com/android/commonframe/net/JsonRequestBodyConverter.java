package com.android.commonframe.net;


import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.android.commonframe.tools.LogUtil;
import com.android.commonframe.tools.MD5Util;
import com.android.commonframe.tools.StringUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

final class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final String TAG = "JsonRequestBodyConverter";

    JsonRequestBodyConverter() {

    }

    @Override
    public RequestBody convert(T value) throws IOException {
        long currentTime = System.currentTimeMillis();
        String jason = null;
        try {
            // TODO: 16/11/11 日后加入请求加密规则,可用JNI加密
            jason =JSON.toJSONString(value);
        } catch (Exception e) {
            LogUtil.e(TAG, e);
        }
        LogUtil.d("request = " + (!RetrofitFactory.issIsShowLog() ? jason : JSON.toJSONString(value)));
        FrameRequestBody frameRequestBody = FrameRequestBody.create(MEDIA_TYPE, !RetrofitFactory.issIsShowLog() ? jason : JSON.toJSONString(value));
        frameRequestBody.setSignValue(getSingValue(JSON.toJSONString(value), currentTime));
        frameRequestBody.setTokenValue(getTokenValue(currentTime));
        return frameRequestBody;
    }

    private String getSingValue(String jason, long currentTime) {
        return MD5Util.encryptToMD5(StringUtil.concatString(String.valueOf(currentTime), jason));
    }

    private String getTokenValue(long currentTime) {
        return Base64.encodeToString(String.valueOf(currentTime).getBytes(), Base64.NO_WRAP);
    }

}
