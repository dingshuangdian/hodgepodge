package com.lsqidsd.hodgepodge.viewmodel;
import com.google.gson.Gson;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.bean.DailyVideos;
import com.lsqidsd.hodgepodge.bean.Milite;
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
import java.util.HashMap;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
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
                if (page == 0) {
                    videoBeans.clear();
                }
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

    public static void getCategoriesNews(int page, HashMap<String, String> params, InterfaceListenter.LoadCategoriesNews listener, List<Milite.DataBean> milites, RefreshLayout refreshLayout, String... s) {
        Observable<Milite> militeObservable = RetrofitServiceManager.getInstance().setUrl(BaseConstant.BASE_URL).getMilite(params, page);
        RetrofitServiceManager.toSubscribe(militeObservable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object o) {
                Milite milite = (Milite) o;
                if (page == 0) {
                    milites.clear();
                }
                if (milite.getData().size() > 0) {
                    for (Milite.DataBean m : milite.getData()) {
                        milites.add(m);
                    }
                    if (listener != null) {
                        if (page > 0) {
                            refreshLayout.finishLoadMore();
                        } else {
                            refreshLayout.finishRefresh();
                            refreshLayout.resetNoMoreData();
                        }
                        listener.loadCategoriesNewsFinish(milites, s[0]);
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

    public static void getActivityHotNews(int page, InterfaceListenter.HotNewsDataListener listener, List<NewsHot.DataBean> hotBeans, RefreshLayout refreshLayout) {
        Observable<NewsHot> observable = RetrofitServiceManager.getInstance().setUrl(BaseConstant.BASE_URL).getHotNews(page, 15);
        RetrofitServiceManager.toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object o) {
                NewsHot newsHot = (NewsHot) o;
                if (page == 0) {
                    hotBeans.clear();
                }
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
     * 加载更多新闻数据
     *
     * @param page
     * @param listener
     */
    public static void getNewsData(int page, InterfaceListenter.NewsItemListener listener, List<NewsItem.DataBean> dataBeanList, RefreshLayout refreshLayout) {
        Observable<NewsItem> observable = RetrofitServiceManager.getInstance().setUrl(BaseConstant.BASE_URL).getMainNews(page);
        RetrofitServiceManager.toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object result) {
                NewsItem newsItem = (NewsItem) result;
                if (newsItem.getData().size() > 0) {
                    for (NewsItem.DataBean dataBeann : newsItem.getData()) {
                        dataBeanList.add(dataBeann);
                    }
                    if (listener != null) {
                        listener.newsItemDataChange(dataBeanList);
                        refreshLayout.finishLoadMore();

                    }
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onFault(String errorMsg) {
                refreshLayout.finishLoadMore();
            }
        }));
    }

    /**
     * 获取视频列表
     *
     * @param beans
     * @param listener
     * @param refreshLayout
     * @param url
     */

    public static void getDailyVideos(List<DailyVideos.IssueListBean.ItemListBean> beans, InterfaceListenter.VideosLoadFinish listener, RefreshLayout refreshLayout, String... url) {
        OkHttpUtils.get()
                .url(url[0])
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        refreshLayout.finishRefresh();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        DailyVideos dailyVideos = gson.fromJson(response, DailyVideos.class);
                        if (!url[1].isEmpty()) {
                            beans.removeAll(beans);
                        }
                        for (DailyVideos.IssueListBean.ItemListBean itemListBean : dailyVideos.getIssueList().get(0).getItemList()) {
                            itemListBean.setState(1);
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
     * zip操作符结合多个接口的数据请求
     */
    public static void getMainNewData(InterfaceListenter.MainNewsDataListener listener, RefreshLayout refreshLayout) {
        Observable<NewsTop> observable = RetrofitServiceManager.getInstance().setUrl(BaseConstant.BASE_URL).getTopNews(0);
        Observable<NewsHot> observable1 = RetrofitServiceManager.getInstance().setUrl(BaseConstant.BASE_URL).getHotNews(0, 5);
        Observable<NewsItem> observable2 = RetrofitServiceManager.getInstance().setUrl(BaseConstant.BASE_URL).getMainNews(0);
        Observable.zip(observable, observable1, observable2, new Function3<NewsTop, NewsHot, NewsItem, NewsMain>() {
            @Override
            public NewsMain apply(NewsTop newsTop, NewsHot newsHot, NewsItem newsItem) throws Exception {
                NewsMain newsMain = new NewsMain();
                for (NewsTop.DataBean dataBean : newsTop.getData()) {
                    newsMain.getNewsTops().add(dataBean);
                }
                for (NewsHot.DataBean hot : newsHot.getData()) {
                    newsMain.getNewsHot().add(hot);
                }
                for (NewsItem.DataBean dataBeann : newsItem.getData()) {
                    newsMain.getNewsItems().add(dataBeann);
                }
                return newsMain;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsMain>() {
                    @Override
                    public void accept(NewsMain newsMain) throws Exception {
                        if (listener != null) {
                            listener.mainDataChange(newsMain);
                            refreshLayout.finishRefresh();
                            refreshLayout.resetNoMoreData();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
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
