package com.lsqidsd.hodgepodge.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT=10;
    private static final int DEFAULT_READ_TIME_OUT=20;
    private Retrofit retrofit;

    private RetrofitServiceManager(){
        //创建OKHttpClient
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT,TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//读操作超时时间

        //添加公共参数拦截器

    }
}
