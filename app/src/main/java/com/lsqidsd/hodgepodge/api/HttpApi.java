package com.lsqidsd.hodgepodge.api;

import com.lsqidsd.hodgepodge.bean.NewsItem;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface HttpApi {
    @GET("irs/rcd?cid=108&token=349ee24cdf9327a050ddad8c166bd3e3")
    Observable<NewsItem> getMainNews(@Query("page") int page);

    @POST()
    Observable<ResponseBody> getNews(@Url String url);
}
