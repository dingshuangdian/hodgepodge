package com.lsqidsd.hodgepodge.viewmodel.newsitemmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultListener;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultSub;
import com.lsqidsd.hodgepodge.http.RetrofitServiceManager;
import com.lsqidsd.hodgepodge.utils.TimeUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class NewsItemModel {
    private String url;
    private ItemNewsDataListener newsDataListener;
    private Context context;
    public ObservableInt progressVisibility = new ObservableInt(View.VISIBLE);
    public ObservableInt lineVisibility = new ObservableInt(View.GONE);

    private List<NewsItem.DataBean> dataBeans = new ArrayList<>();
    private NewsItem.DataBean dataBean;

    public NewsItemModel(Context context, NewsItem.DataBean dataBean) {
        this.context = context;
        this.dataBean = dataBean;
    }

    public NewsItemModel(Context context, List<NewsItem.DataBean> dataBeans) {
        this.context = context;
        this.dataBeans = dataBeans;
    }

    public NewsItemModel(String url, Context context, ItemNewsDataListener listener) {
        this.newsDataListener = listener;
        this.url = url;
        this.context = context;
    }

    public String getAuthor() {
        return dataBean.getSource();
    }

    public String getTime() {
        return TimeUtil.formatTime(dataBean.getPublish_time());
    }

    @BindingAdapter({"imageUrl"})
    public static void setImageView(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.get().load(imageUrl).into(imageView);
        }

    }

    public String getImageUrl() {
        return dataBean.getBimg();
    }

    public String getTitle() {
        return dataBean.getTitle();
    }

    public void getMoreData(int page, ItemNewsDataListener listener) {
        this.newsDataListener = listener;
        getNewsData(page);
    }

    public void getNewsData(int page) {
        getMainViewData(new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(NewsItem result) {
                if (newsDataListener != null) {
                    if (result.getData().size() > 0) {
                        for (NewsItem.DataBean dataBeann : result.getData()) {
                            dataBeans.add(dataBeann);
                        }
                        newsDataListener.dataBeanChange(dataBeans);
                        progressVisibility.set(View.GONE);
                    } else {
                        lineVisibility.set(View.VISIBLE);
                        progressVisibility.set(View.GONE);
                    }
                }
            }

            @Override
            public void onFault(String errorMsg) {
                progressVisibility.set(View.GONE);
                lineVisibility.set(View.VISIBLE);
            }
        }), page);
    }

    public void getMainViewData(DisposableObserver<NewsItem> subscriber, int page) {
        Observable<NewsItem> observable = RetrofitServiceManager.getInstance().getHttpApi().getMainNews(page);
        RetrofitServiceManager.getInstance().toSubscribe(observable, subscriber);
    }

    public interface ItemNewsDataListener {
        void dataBeanChange(List<NewsItem.DataBean> dataBeans);
    }
}
