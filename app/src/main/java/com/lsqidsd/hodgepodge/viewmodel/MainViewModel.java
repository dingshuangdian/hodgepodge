package com.lsqidsd.hodgepodge.viewmodel;


import android.util.Log;

import com.lsqidsd.hodgepodge.rx.InterfaceService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainViewModel {

    public void getHtml1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect("http://www.ifeng.com/").get();
                    Log.e("doc", doc.outerHtml());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    public void getHtml() {

        InterfaceService.Factory.create().getList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<List<String>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<List<String>> listResponse) {
                        Log.e("listResponse+++++++", listResponse.toString());

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
