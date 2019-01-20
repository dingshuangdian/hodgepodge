package com.lsqidsd.hodgepodge.viewmodel.newsmodel;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lsqidsd.hodgepodge.base.OnWriteDataFinishListener;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultListener;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultSub;
import com.lsqidsd.hodgepodge.http.RetrofitServiceManager;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class InformationViewModel {
    private String url;
    private Context context;

    public InformationViewModel(Context context, String url) {
        this.url = url;
        this.context = context;

    }

    public void getNewsData(OnWriteDataFinishListener listener, int page) {
        getMainViewData(new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(NewsItem result) {
                if (listener != null) {
                    listener.onSuccess(result.getData());
                }
            }

            @Override
            public void onFault(String errorMsg) {
            }
        }), page);
    }

    public void getMainViewData(DisposableObserver<NewsItem> subscriber, int page) {

        Observable<NewsItem> observable = RetrofitServiceManager.getInstance().getHttpApi().getMainNews(page);
        RetrofitServiceManager.getInstance().toSubscribe(observable, subscriber);
    }

}
