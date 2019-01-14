package com.lsqidsd.hodgepodge.viewmodel.newsmodel;

import android.util.Log;

import com.lsqidsd.hodgepodge.base.OnWriteDataFinishListener;
import com.lsqidsd.hodgepodge.http.RetrofitServiceManager;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class InformationViewModel {
    private String url;

    public InformationViewModel(String url) {
        this.url = url;
    }

    public void getNewsInformation(DisposableObserver<ResponseBody> subscriber) {
        {
            Observable<ResponseBody> observable = RetrofitServiceManager.getInstance().getHttpApi().getAllNews(url);
            RetrofitServiceManager.getInstance().toSubscribe(observable, subscriber);
        }
    }

    public void getSliderImg(Document document, OnWriteDataFinishListener onWriteDataFinishListener) {
        Log.e("document+++++++",document.outerHtml());
        Elements elements = document.select(".nav-2sgjEBc8").select(".clearfix").select(".nav");
        Log.e("elements+++++++++", elements.toString());


    }
}
