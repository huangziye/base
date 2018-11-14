package com.hzy.retrofit.util;

import android.content.Context;
import android.text.TextUtils;

import com.hzy.retrofit.converter.FastJsonConverterFactory;
import com.hzy.retrofit.service.ApiService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by ziye_huang on 2018/11/14.
 */
public class ApiUtil {

    /**
     * 获取ApiService对象
     *
     * @param context 上下文对象
     * @param host    baseUrl
     * @param token   access token
     * @return
     */
    public static ApiService getApiService(Context context, String host, String token) {
        return getInstance(context, host, token);
    }

    /**
     * 获取ApiService实例对象
     *
     * @param context 上下文对象
     * @param host    baseUrl
     * @param token   access token
     * @return
     */
    private static ApiService getInstance(Context context, String host, String token) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host).addConverterFactory(FastJsonConverterFactory.create())
                //适配RxJava2.0,RxJava1.x则为RxJavaCallAdapterFactory.create()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(getOkHttpClient(token)).build();
        return retrofit.create(ApiService.class);
    }

    private static OkHttpClient getOkHttpClient(String token) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /**
         *设置缓存，代码略
         */

        /**
         *  公共参数，代码略
         */

        /**
         * 设置头，代码略
         */
        if (!TextUtils.isEmpty(token)) {
            builder.addInterceptor(setInterceptor(token));
        }
        /**
         * Log信息拦截器，代码略
         */

        /**
         * 设置cookie，代码略
         */

        /**
         * 设置超时和重连，代码略
         */
        //设置超时
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        //以上设置结束，才能build(),不然设置白搭
        return builder.build();
    }

    /**
     * 设置拦截器，在这里主要配置头信息
     *
     * @return
     */
    private static Interceptor setInterceptor(String token) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder().addHeader("Accept-Encoding", "gzip").addHeader("Accept", "application/json").addHeader("Content-Type", "application/json; charset=utf-8").method(originalRequest.method(), originalRequest.body());
                requestBuilder.addHeader("Authorization", "Bearer " + token);//添加请求头信息，服务器进行token有效性验证
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }
}
