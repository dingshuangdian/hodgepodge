package com.lsqidsd.hodgepodge.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface HttpApi {
    @GET()
    Observable<ResponseBody> getAllNews(@Url String url);
    @POST()
    Observable<ResponseBody> getNews(@Url String url);
}
