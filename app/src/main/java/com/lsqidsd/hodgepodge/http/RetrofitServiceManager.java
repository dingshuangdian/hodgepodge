package com.lsqidsd.hodgepodge.http;

import com.lsqidsd.hodgepodge.api.HttpApi;
import com.lsqidsd.hodgepodge.base.BaseApplication;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
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
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 10;
    private static final int DEFAULT_READ_TIME_OUT = 20;
    private Retrofit retrofit;
    private static RetrofitServiceManager instance;
    public static final String CACHE_NAME = "appName";
    private HttpApi httpApi;

    /**
     * 请求失败重连次数
     */
    private int RETRY_COUNT = 0;
    private OkHttpClient.Builder builder;

    //构造方法私有
    private RetrofitServiceManager() {
        //创建OKHttpClient
        builder = new OkHttpClient.Builder();
        /**
         * 设置缓存
         */
        File cacheFile = new File(BaseApplication.getmContext().getExternalCacheDir(), CACHE_NAME);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (!NetUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    //有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader(CACHE_NAME)//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    //无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader(CACHE_NAME)
                            .build();
                }
                return response;
            }
        };
        builder.cache(cache).addInterceptor(interceptor);

        /**
         * 设置头信息
         */
        Interceptor headInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                Request.Builder builder = request.newBuilder()
                        .addHeader("Accept-Encoding", "gzip")
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .method(request.method(), request.body());
                builder.addHeader("Authorization", "Bearer " + BaseConstant.TOKEN);////添加请求头信息，服务器进行token有效性验证
                Request request1 = builder.build();


                return chain.proceed(request1);
            }
        };
        builder.addInterceptor(headInterceptor);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.d(message);

            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        /**
         * 设置超时和重新连接
         */
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

        //错误重连
        builder.retryOnConnectionFailure(true);
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BaseConstant.BASE_URL)
                .build();
        httpApi = retrofit.create(HttpApi.class);


    }

    public static RetrofitServiceManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitServiceManager.class) {
                if (instance == null) {
                    instance = new RetrofitServiceManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取retrofit
     */
    public Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * 获取httpService
     */
    public HttpApi getHttpApi() {
        return httpApi;
    }

    /**
     * 设置订阅和所在的线程环境
     */
    public <T> void toSubscribe(Observable<T> o, DisposableObserver<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(RETRY_COUNT)//失败重连次数
                .subscribe(s);
    }
}
