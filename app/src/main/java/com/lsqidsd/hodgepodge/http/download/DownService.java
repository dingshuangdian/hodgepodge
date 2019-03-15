package com.lsqidsd.hodgepodge.http.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;

public interface DownService {
    /**
     * 断点续传下载测试
     */
    @Streaming //大文件需要加入这个判断，防止下载过程中写入到内存中
    @GET("16891/A2DDC774F2803187DE39067570A4A13A.apk?fsname=com.tencent.news_5.7.60_5760.apk&csr=1bbd")
    Observable<ResponseBody> download(@Header("RANGE") String start);
}
