package com.lsqidsd.hodgepodge.http;

import com.lsqidsd.hodgepodge.bean.HomeBean;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface RequestApi {
    String URL = "http://www.ifeng.com/";

    @GET
    Observable<Response<List<HomeBean>>> getList();
}
