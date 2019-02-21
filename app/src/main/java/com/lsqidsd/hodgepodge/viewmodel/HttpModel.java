package com.lsqidsd.hodgepodge.viewmodel;

import android.util.Log;

import com.google.gson.Gson;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.bean.DailyVideos;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsTop;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultListener;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultSub;
import com.lsqidsd.hodgepodge.http.RetrofitServiceManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.Call;

public class HttpModel {
    /**
     * 获取视频列表
     *
     * @param page
     * @param dataListener
     */
    public static void getVideoList(int page, InterfaceListenter.VideosDataListener dataListener, List<NewsVideoItem.DataBean> videoBeans, RefreshLayout refreshLayout) {
        Observable<NewsVideoItem> observable = RetrofitServiceManager.getInstance().setUrl(BaseConstant.BASE_URL).getVideos(page);
        RetrofitServiceManager.toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object o) {
                NewsVideoItem newsVideoItem = (NewsVideoItem) o;
                if (newsVideoItem.getData().size() > 0) {
                    for (NewsVideoItem.DataBean video : newsVideoItem.getData()) {
                        videoBeans.add(video);
                    }
                    if (dataListener != null) {
                        dataListener.videoDataChange(videoBeans);
                        if (page > 0) {
                            refreshLayout.finishLoadMore();
                        } else {
                            refreshLayout.finishRefresh();
                            refreshLayout.resetNoMoreData();
                        }
                    }
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onFault(String errorMsg) {
                if (page > 0) {
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishRefresh();
                    refreshLayout.resetNoMoreData();
                }
            }
        }));
    }

    /**
     * 获取热点新闻
     *
     * @param page
     * @param listener
     */
    public static void getHotNews(int page, InterfaceListenter.MainNewsDataListener listener, NewsMain newsMain, RefreshLayout refreshLayout) {
        Observable<NewsHot> observable = RetrofitServiceManager.getInstance().setUrl(BaseConstant.BASE_URL).getHotNews(page, 5);
        RetrofitServiceManager.toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object o) {
                NewsHot newsHot = (NewsHot) o;
                for (NewsHot.DataBean hot : newsHot.getData()) {
                    newsMain.getNewsHot().add(hot);
                }
                getNewsData(0, listener, newsMain, refreshLayout);
            }

            @Override
            public void onFault(String errorMsg) {
                if (page > 0) {
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishRefresh();
                    refreshLayout.resetNoMoreData();
                }
            }
        }));
    }

    public static void getActivityHotNews(int page, InterfaceListenter.HotNewsDataListener listener, List<NewsHot.DataBean> hotBeans, RefreshLayout refreshLayout) {
        Observable<NewsHot> observable = RetrofitServiceManager.getInstance().setUrl(BaseConstant.BASE_URL).getHotNews(page, 15);
        RetrofitServiceManager.toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object o) {
                NewsHot newsHot = (NewsHot) o;
                if (newsHot.getData().size() > 0) {
                    for (NewsHot.DataBean hot : newsHot.getData()) {
                        hotBeans.add(hot);
                    }
                    if (listener != null) {
                        listener.hotDataChange(hotBeans);
                        if (page > 0) {
                            refreshLayout.finishLoadMore();
                        } else {
                            refreshLayout.finishRefresh();
                            refreshLayout.resetNoMoreData();
                        }
                    }
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onFault(String errorMsg) {
                if (page > 0) {
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishRefresh();
                    refreshLayout.resetNoMoreData();
                }
            }
        }));
    }

    /**
     * 获取置顶新闻
     *
     * @param listener
     */
    public static void getTopNews(InterfaceListenter.MainNewsDataListener listener, NewsMain newsMain, RefreshLayout refreshLayout) {
        Observable<NewsTop> observable = RetrofitServiceManager.getInstance().setUrl(BaseConstant.BASE_URL).getTopNews(0);
        RetrofitServiceManager.toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object o) {
                NewsTop newsTop = (NewsTop) o;
                for (NewsTop.DataBean dataBean : newsTop.getData()) {
                    newsMain.getNewsTops().add(dataBean);
                }
                getHotNews(0, listener, newsMain, refreshLayout);
            }

            @Override
            public void onFault(String errorMsg) {
            }
        }));
    }

    /**
     * 获取新闻数据
     *
     * @param page
     * @param listener
     */
    public static void getNewsData(int page, InterfaceListenter.MainNewsDataListener listener, NewsMain newsMain, RefreshLayout refreshLayout) {
        Observable<NewsItem> observable = RetrofitServiceManager.getInstance().setUrl(BaseConstant.BASE_URL).getMainNews(page);
        RetrofitServiceManager.toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object result) {
                NewsItem newsItem = (NewsItem) result;
                if (newsItem.getData().size() > 0) {
                    for (NewsItem.DataBean dataBeann : newsItem.getData()) {
                        newsMain.getNewsItems().add(dataBeann);
                    }
                    if (listener != null) {
                        listener.mainDataChange(newsMain);
                        if (page > 0) {
                            refreshLayout.finishLoadMore();
                        } else {
                            refreshLayout.finishRefresh();
                            refreshLayout.resetNoMoreData();
                        }
                    }
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onFault(String errorMsg) {
                if (page > 0) {
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishRefresh();
                    refreshLayout.resetNoMoreData();
                }
            }
        }));
    }

    public static void getDailyVideos(List<DailyVideos.IssueListBean.ItemListBean> beans, InterfaceListenter.VideosLoadFinish listener, RefreshLayout refreshLayout, String... url) {
        Log.e("url", url[0]);
        OkHttpUtils.get()
                .url(url[0])
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        DailyVideos dailyVideos = gson.fromJson(response, DailyVideos.class);
                        for (DailyVideos.IssueListBean.ItemListBean itemListBean : dailyVideos.getIssueList().get(0).getItemList()) {
                            if (itemListBean.getType().equals("video")) {
                                beans.add(itemListBean);
                            }
                        }
                        if (listener != null) {
                            if (url[1].isEmpty()) {
                                refreshLayout.finishLoadMore();
                            } else {
                                refreshLayout.finishRefresh();
                                refreshLayout.resetNoMoreData();
                            }
                            listener.videosLoadFinish(beans, dailyVideos.getNextPageUrl());
                        }
                    }
                });
    }

    /**
     * 抓取头部热点滚动
     *
     * @param finish
     * @param top
     */
    public static void getHotKey(InterfaceListenter.HasFinish finish, List<String> top) {
        try {
            Document document = Jsoup.connect(BaseConstant.SEARCH_URL).get();
            Elements elements = document.select("td").select("a");
            for (Element element : elements) {
                top.add(element.text());
            }
            if (finish != null) {
                finish.hasFinish(top);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
