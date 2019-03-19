package com.lsqidsd.hodgepodge.http.download;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

public class DownloadInterceptor implements Interceptor {
    private ProgressListener listener;

    public DownloadInterceptor(ProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder().body(new DownloadResponseBody(originalResponse.body(), listener)).build();
    }
}
