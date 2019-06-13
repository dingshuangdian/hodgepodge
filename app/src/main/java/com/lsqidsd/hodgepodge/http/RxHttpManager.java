package com.lsqidsd.hodgepodge.http;

import com.lsqidsd.hodgepodge.base.BaseApplication;
import com.lsqidsd.hodgepodge.service.Platform;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxHttpManager {
    private final int DEFAULT_TIME_OUT = 10;
    private final String CACHE_NAME = "cache_news";
    private volatile static RxHttpManager instance;
    private Platform mPlatform;
    /**
     * 请求失败重连次数
     */
    private final int RETRY_COUNT = 5;

    private RxHttpManager() {
        mPlatform = Platform.get();
    }

    private OkHttpClient.Builder okhttpSetting(String... sf) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);//错误重连
        if (sf != null) {
            for (String s : sf) {
                if (s.equals(State.CACHE.getState())) {
                    File cacheFile = new File(BaseApplication.getmContext().getExternalCacheDir(), CACHE_NAME);
                    Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
                    Interceptor interceptor = a -> {
                        Request request = a.request();
                        if (!NetUtil.isNetworkConnected()) {
                            request = request.newBuilder()
                                    .cacheControl(CacheControl.FORCE_CACHE)
                                    .build();
                        }
                        Response response = a.proceed(request);
                        if (!NetUtil.isNetworkConnected()) {
                            int maxAge = 0;
                            //有网络时，设置缓存超时时间
                            response.newBuilder()
                                    .header("Cache-Control", "public, max-age=" + maxAge)
                                    .removeHeader(CACHE_NAME)
                                    .build();
                        } else {
                            //无网络
                            int maxStatle = 60 * 60 * 24;
                            response.newBuilder()
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStatle)
                                    .removeHeader(CACHE_NAME)
                                    .build();
                        }
                        return response;
                    };
                    builder.cache(cache).addInterceptor(interceptor);
                }

                if (s.equals(State.HEAD.getState())) {
                    Interceptor headerInterceptor = a -> {
                        Request originalRequest = a.request();
                        Request.Builder requestBuilder = originalRequest.newBuilder()
                                .addHeader("Accept-Encoding", "gzip")
                                .addHeader("Accept", "application/json")
                                .addHeader("Content-Type", "application/json; charset=utf-8")
                                .method(originalRequest.method(), originalRequest.body());
                        Request request = requestBuilder.build();
                        return a.proceed(request);
                    };
                    builder.addInterceptor(headerInterceptor);
                }
                if (s.equals(State.PARAMS.getState())) {
                    //添加公共参数
                    HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
                            .addHeaderParams("platform", "android")
                            .addHeaderParams("userToken", "1234343434dfdfd3434")
                            .addHeaderParams("userId", "123445")
                            .build();
                    builder.addInterceptor(commonInterceptor);
                }
            }
        }
        return builder;
    }

    private Retrofit retrofitSetting(String url, String... sf) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okhttpSetting(sf).build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
        return retrofit;
    }

    /**
     * 获取对应的Service
     *
     * @return
     */
    public <T> T create(Class<T> tService, String url, String... sf) {
        return retrofitSetting(url, sf).create(tService);
    }

    public static RxHttpManager getInstance() {
        if (instance == null) {
            synchronized (RxHttpManager.class) {
                if (instance == null) {
                    instance = new RxHttpManager();
                }
            }
        }
        return instance;
    }

    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public <T> void subscribe(Observable<T> o, DisposableObserver<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(RETRY_COUNT)
                .subscribe(s);
    }
}
