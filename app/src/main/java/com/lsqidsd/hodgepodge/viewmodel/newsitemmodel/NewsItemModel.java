package com.lsqidsd.hodgepodge.viewmodel.newsitemmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsTop;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultListener;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultSub;
import com.lsqidsd.hodgepodge.http.RetrofitServiceManager;
import com.lsqidsd.hodgepodge.utils.JsonUtils;
import com.lsqidsd.hodgepodge.utils.TimeUtil;
import com.lsqidsd.hodgepodge.view.WebViewActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class NewsItemModel<T> {
    private String url;
    private ItemNewsDataListener newsDataListener;
    private Context context;
    public ObservableInt progressVisibility = new ObservableInt(View.VISIBLE);
    public ObservableInt lineVisibility = new ObservableInt(View.GONE);
    public ObservableInt commentVisibility = new ObservableInt(View.GONE);
    public ObservableInt commentTopVisibility = new ObservableInt(View.GONE);
    public ObservableInt imgVisbility = new ObservableInt(View.VISIBLE);
    public ObservableInt gvVisbility = new ObservableInt(View.GONE);
    public ObservableInt roundVisbility = new ObservableInt(View.GONE);
    public ObservableInt vpVisbility = new ObservableInt(View.GONE);
    public ObservableInt imgfVisbility = new ObservableInt(View.VISIBLE);
    private List<NewsItem.DataBean> dataBeans = new ArrayList<>();
    private List<NewsTop.DataBean> topBeans = new ArrayList<>();
    private List<NewsHot.DataBean> hotBeans = new ArrayList<>();
    private NewsItem.DataBean dataBean;
    private NewsTop.DataBean newsTop;
    private JSONArray jsonArray;
    private int[] radom = {0, 8};
    private NewsMain newsMain = new NewsMain();

    public NewsItemModel(Context context, T t, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
        if (t instanceof NewsTop.DataBean) {
            this.newsTop = (NewsTop.DataBean) t;
        }
        if (t instanceof NewsItem.DataBean) {
            this.dataBean = (NewsItem.DataBean) t;
        }
    }

    public NewsItemModel() {
    }

    public NewsItemModel(Context context, ItemShowListener itemShowListener) {
        Random random = new Random();
        int x = radom[random.nextInt(radom.length)];
        roundVisbility.set(x);
        if (roundVisbility.get() == 0) {
            vpVisbility.set(View.GONE);
        } else {
            vpVisbility.set(View.VISIBLE);
        }
        if (itemShowListener != null) {
            itemShowListener.itemShow(roundVisbility, vpVisbility);
        }
        this.context = context;
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

    public String getTopAuthor() {
        return newsTop.getSource();
    }

    public String getComment() {
        if (dataBean.getComment_num() == 0) {
            commentVisibility.set(View.GONE);
        } else {
            commentVisibility.set(View.VISIBLE);
        }
        return dataBean.getComment_num() + "评";
    }

    public String getTopComment() {
        if (newsTop.getComments() == 0) {
            commentTopVisibility.set(View.GONE);
        } else {
            commentTopVisibility.set(View.VISIBLE);
        }
        return newsTop.getComments() + "评";
    }

    public void click(View view) {
        Intent intent = new Intent(context, WebViewActivity.class);
        switch (view.getId()) {
            case R.id.view_01:
                intent.putExtra("url", getTopUrl());
                context.startActivity(intent);
                break;
            case R.id.view_03:
                intent.putExtra("url", getUrl());
                context.startActivity(intent);
                break;
        }
    }

    public String getTopUrl() {
        return newsTop.getUrl();
    }

    public String getUrl() {
        return dataBean.getUrl();
    }

    public String getTopTitle() {
        return newsTop.getTitle();
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

    @BindingAdapter({"topImageUrl"})
    public static void setTopImageView(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.get().load(imageUrl).into(imageView);
        }
    }

    public String getTopImageUrl() {
        if (!newsTop.getFlag().isEmpty()) {
            imgVisbility.set(View.VISIBLE);
        } else {
            imgVisbility.set(View.GONE);
        }
        return newsTop.getThumbnails().get(0);
    }

    public String getImageUrl() {
        if (jsonArray != null) {
            if (jsonArray.length() == 3) {
                gvVisbility.set(View.VISIBLE);
                imgfVisbility.set(View.GONE);
            } else {
                gvVisbility.set(View.GONE);
                imgfVisbility.set(View.VISIBLE);
            }
        }
        return JsonUtils.jsonKey(dataBean.getImgs(), 0);
    }

    public String getTitle() {
        return dataBean.getTitle();
    }

    public void getMoreData(int page, ItemNewsDataListener listener) {
        this.newsDataListener = listener;
        getNewsData(page);
    }

    private void getNewsData(int page) {
        getMainViewData(new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object result) {
                NewsItem newsItem = (NewsItem) result;
                if (newsItem.getData().size() > 0) {
                    for (NewsItem.DataBean dataBeann : newsItem.getData()) {
                        dataBeans.add(dataBeann);
                    }
                    newsMain.setNewsItems(dataBeans);
                    if (newsDataListener != null) {
                        newsDataListener.dataBeanChange(newsMain);
                    }
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            progressVisibility.set(View.GONE);
                        }
                    }, 1000);
                } else {
                    lineVisibility.set(View.VISIBLE);
                    progressVisibility.set(View.GONE);

                }

            }

            @Override
            public void onFault(String errorMsg) {
                progressVisibility.set(View.GONE);
                lineVisibility.set(View.VISIBLE);
            }
        }), page);
    }

    private void getMainViewData(DisposableObserver<NewsItem> subscriber, int page) {
        Observable<NewsItem> observable = RetrofitServiceManager.getInstance().getHttpApi().getMainNews(page);
        RetrofitServiceManager.getInstance().toSubscribe(observable, subscriber);
    }

    public void getTopNews() {
        Observable<NewsTop> observable = RetrofitServiceManager.getInstance().getHttpApi().getTopNews(0);
        RetrofitServiceManager.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object o) {
                NewsTop newsTop = (NewsTop) o;
                for (NewsTop.DataBean dataBean : newsTop.getData()) {
                    topBeans.add(dataBean);
                }
                newsMain.setNewsTops(topBeans);
                getHotNews(0);
            }

            @Override
            public void onFault(String errorMsg) {
            }
        }));
    }

    public void getHotNews(int page) {
        Observable<NewsHot> observable = RetrofitServiceManager.getInstance().getHttpApi().getHotNews(page, 5);
        RetrofitServiceManager.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object o) {
                NewsHot newsHot = (NewsHot) o;
                for (NewsHot.DataBean hot : newsHot.getData()) {
                    hotBeans.add(hot);
                }
                newsMain.setNewsHot(hotBeans);
                getNewsData(0);
            }

            @Override
            public void onFault(String errorMsg) {
            }
        }));
    }

    public interface ItemNewsDataListener {
        void dataBeanChange(NewsMain dataBeans);
    }

    public interface ItemShowListener {
        void itemShow(ObservableInt... observableInt);
    }
}
