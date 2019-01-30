package com.lsqidsd.hodgepodge.viewmodel;
import android.content.Context;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultListener;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultSub;
import com.lsqidsd.hodgepodge.http.RetrofitServiceManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class HotViewModule {
    private Context context;
    private List<NewsHot.DataBean> hotBeans = new ArrayList<>();

    public HotViewModule(Context context) {
        this.context = context;
    }
    public void getHotNews(int page) {
        Observable<NewsHot> observable = RetrofitServiceManager.getInstance().getHttpApi().getHotNews(page, 10);
        RetrofitServiceManager.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object o) {
                NewsHot newsHot = (NewsHot) o;
                for (NewsHot.DataBean hot : newsHot.getData()) {
                    hotBeans.add(hot);
                }
            }
            @Override
            public void onFault(String errorMsg) {
            }
        }));
    }


}
