package com.lsqidsd.hodgepodge.http.download;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 复写Interceptor，可以将我们的监听回调通过okhttp的client方法addInterceptor自动加载我们的监听回调和ResponseBody
 */

public class DownloadInterceptor implements Interceptor {
    private DownloadProgressListener listener;

    public DownloadInterceptor(DownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(new DownloadResponseBody(response.body(), listener)).build();
    }
}
