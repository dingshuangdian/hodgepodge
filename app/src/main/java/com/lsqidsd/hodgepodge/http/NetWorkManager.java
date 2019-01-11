package com.lsqidsd.hodgepodge.http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkManager {

    private static NetWorkManager mInstance;
    private static Retrofit retrofit;
    private static volatile RequestApi request = null;

    public static NetWorkManager getmInstance() {
        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init() {
        //初始化okhttp
        OkHttpClient client = new OkHttpClient.Builder().build();
        //初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(RequestApi.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RequestApi getRequest() {
        if (request == null) {
            synchronized (RequestApi.class) {
                request = retrofit.create(RequestApi.class);
            }
        }
        return request;
    }
}
