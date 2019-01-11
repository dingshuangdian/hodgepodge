package com.lsqidsd.hodgepodge.rx;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface InterfaceService {

    String URL = "http://www.ifeng.com/";

    @GET
    Observable<Response<List<String>>> getList();

    class Factory {
        public static InterfaceService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(InterfaceService.class);
        }
    }
}
