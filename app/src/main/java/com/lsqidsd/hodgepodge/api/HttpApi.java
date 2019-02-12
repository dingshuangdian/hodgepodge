package com.lsqidsd.hodgepodge.api;

import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.bean.NewsTop;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface HttpApi {
    @GET("irs/rcd?cid=108&token=349ee24cdf9327a050ddad8c166bd3e3")
    Observable<NewsItem> getMainNews(@Query("page") int page);

    @GET("om/mediaArticles?mid=5278151&num=15")
    Observable<NewsTop> getTopNews(@Query("page") int page);

    //热点精选
    @GET("irs/rcd?cid=4&token=9513f1a78a663e1d25b46a826f248c3c")
    Observable<NewsHot> getHotNews(@Query("page") int page, @Query("num") int num);
    @GET("vlike/relate?num=15&id=c0029aqw3zm")
    Observable<NewsVideoItem> getVideos(@Query("page") int page);
}
