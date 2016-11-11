package com.android.commonframe.net;


import com.android.commonframe.FrameConstant;
import com.android.commonframe.tools.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import static com.android.commonframe.FrameConstant.ENCRYPTION;
import static com.android.commonframe.FrameConstant.FLAG;
import static com.android.commonframe.FrameConstant.NO_ENCRYPTION;
import static com.android.commonframe.FrameConstant.TOKEN;
import static com.android.commonframe.FrameConstant.USER_INFO;
import static java.text.NumberFormat.Field.SIGN;


/**
 * 网络请求工厂类实例化retrofit工具
 * <p>
 * Created by feilong.guo on 16/11/11.
 */

public class RetrofitFactory {
    private static boolean sIsShowLog;

    private RetrofitFactory() {
    }

    public static <T> T getAPIService(Class<T> apiServiceClass, String baseURL, String userInfoJson, boolean isShowLog) {
        sIsShowLog = isShowLog;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(genericClient(userInfoJson))
                .build();
        return retrofit.create(apiServiceClass);
    }

    /**
     * 实例化retrofit网络请求类的时候,如参数根据实际情况是否传入用户信息的json
     *
     * @param userInfoJson 用户信息的json
     * @return okhttpclient
     */
    private static OkHttpClient genericClient(final String userInfoJson) {
        return new OkHttpClient().newBuilder()
                .connectTimeout(FrameConstant.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(FrameConstant.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(FrameConstant.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        FrameRequestBody atRequestBody = (FrameRequestBody) chain.request().body();
                        builder.header(FrameConstant.SIGN, atRequestBody.getSignValue());
                        builder.header(TOKEN, atRequestBody.getTokenValue());
                        builder.header(FLAG, !sIsShowLog ? ENCRYPTION : NO_ENCRYPTION);
                        builder.header(USER_INFO, userInfoJson);
                        LogUtil.d("request_e_head = " + SIGN + ":" + atRequestBody.getSignValue());
                        LogUtil.d("request_e_head = " + TOKEN + ":" + atRequestBody.getTokenValue());
                        LogUtil.d("request_e_head = " + FLAG + ":" + sIsShowLog);
                        Request request = builder.build();
                        LogUtil.d("request.url = " + request.url().url());
                        return chain.proceed(request);
                    }
                }).build();
    }

    public static boolean issIsShowLog() {
        return sIsShowLog;
    }

}
