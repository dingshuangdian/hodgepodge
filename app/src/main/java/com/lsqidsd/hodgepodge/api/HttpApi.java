package com.lsqidsd.hodgepodge.api;

import com.lsqidsd.hodgepodge.bean.DailyVideos;
import com.lsqidsd.hodgepodge.bean.Milite;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.bean.NewsTop;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import java.util.HashMap;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;

public interface HttpApi {
    @GET("irs/rcd?cid=108&token=349ee24cdf9327a050ddad8c166bd3e3")
    Observable<NewsItem> getMainNews(@Query("page") int page);

    @GET("om/mediaArticles?mid=5278151&num=15&page=1")
    Observable<NewsTop> getTopNews();

    @GET("irs/rcd?cid=4&token=9513f1a78a663e1d25b46a826f248c3c")
    Observable<NewsHot> getHotNews(@Query("page") int page, @Query("num") int num);

    @GET("irs/rcd")
    Observable<Milite> getMilite(@QueryMap HashMap<String, String> hashMap, @Query("page") int page);

    @GET("vlike/category?cid=3&num=10?vid=w0837g1qb8p")
    Observable<NewsVideoItem> getVideos(@Query("page") int page);

    /* @GET("api/v4/video/related?id=150606")
     Observable<AdVideos> getVideoList(@Query("page") int page);*/
    @GET("api/v2/feed")
    Observable<DailyVideos> getDailyVideo();
    @Streaming //大文件需要加入这个判断，防止下载过程中写入到内存中
    @GET("16891/A2DDC774F2803187DE39067570A4A13A.apk?fsname=com.tencent.news_5.7.60_5760.apk&csr=1bbd")
    Observable<ResponseBody> download();

}
