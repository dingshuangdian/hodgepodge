package com.lsqidsd.hodgepodge.http;
import android.os.Handler;
import android.os.Looper;
import com.lsqidsd.hodgepodge.base.BaseApplication;
import com.lsqidsd.hodgepodge.http.download.DaoUtil;
import com.lsqidsd.hodgepodge.http.download.DownService;
import com.lsqidsd.hodgepodge.http.download.DownSubscriber;
import com.lsqidsd.hodgepodge.http.download.DownloadInterceptor;
import com.lsqidsd.hodgepodge.http.download.Info;
import com.lsqidsd.hodgepodge.http.download.Platform;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
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
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxHttpManager {
    private final int DEFAULT_TIME_OUT = 10;
    private final String CACHE_NAME = "cache_news";
    private volatile static RxHttpManager instance;
    private Platform mPlatform;
    private Handler handler;
    //下载数据
    private Set<Info> downStateSet;
    private DaoUtil daoUtil;
    private HashMap<String, DownSubscriber> subscriberHashMap;

    /**
     * 请求失败重连次数
     */
    private final int RETRY_COUNT = 5;

    private RxHttpManager() {
        mPlatform = Platform.get();
        downStateSet = new HashSet<>();
        daoUtil = DaoUtil.getInstance();
        subscriberHashMap = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
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

    public void down(Info info) {
        if (info == null || subscriberHashMap.get(info.getUrl()) != null) {
            subscriberHashMap.get(info.getUrl()).setInfo(info);
            return;
        }
        DownSubscriber subscriber = new DownSubscriber(info, handler);
        DownService downService;
        if (downStateSet.contains(info)) {
            downService = info.getService();
        } else {

            subscriberHashMap.put(info.getUrl(), subscriber);
            DownloadInterceptor interceptor = new DownloadInterceptor(subscriber);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
            builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
            builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
            builder.retryOnConnectionFailure(true);//错误重连
            builder.addInterceptor(interceptor);
            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(info.getUrl())
                    .build();
            downService = retrofit.create(DownService.class);
            info.setService(downService);
            downStateSet.add(info);
        }
        downService.download("bytes=" + info.getReadLength() + "-")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(a -> {
                    writeCaches(a, new File(info.getSavePath()), info);
                    return info;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);


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

    /**
     * 写入文件
     *
     * @param file
     * @param info
     * @throws IOException
     */
    public void writeCaches(ResponseBody responseBody, File file, Info info) {
        try {
            RandomAccessFile randomAccessFile = null;
            FileChannel channelOut = null;
            InputStream inputStream = null;
            try {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                long allLength = 0 == info.getCountLength() ? responseBody.contentLength() : info.getReadLength() + responseBody
                        .contentLength();

                inputStream = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                        info.getReadLength(), allLength - info.getReadLength());
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    mappedBuffer.put(buffer, 0, len);
                }
            } catch (IOException e) {
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
        } catch (IOException e) {
        }
    }

    /**
     * 移除下载数据
     */
    public void remove(Info info) {
        subscriberHashMap.remove(info.getUrl());
        downStateSet.remove(info);

    }

    /**
     * 返回全部正在下载的数据
     */
    public Set<Info> getDownStateSet() {
        return downStateSet;
    }

    /**
     * 暂停全部下载
     */
    public void pauseAll() {
        for (Info info : downStateSet) {
            pause(info);

        }
        subscriberHashMap.clear();
        downStateSet.clear();
    }

    /**
     * 暂停下载
     */
    public void pause(Info info) {
        if (info == null) return;
        info.setState(com.lsqidsd.hodgepodge.http.download.State.PAUSE);
        info.getListener().onPause();
        daoUtil.updata(info);
    }

}
