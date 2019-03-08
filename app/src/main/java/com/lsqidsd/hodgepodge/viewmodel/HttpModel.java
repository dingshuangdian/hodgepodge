package com.lsqidsd.hodgepodge.viewmodel;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lsqidsd.hodgepodge.api.HttpGet;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.bean.DailyVideos;
import com.lsqidsd.hodgepodge.bean.Milite;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import com.lsqidsd.hodgepodge.http.HttpOnNextListener;
import com.lsqidsd.hodgepodge.http.MyDisposableObserver;
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

import okhttp3.Call;


public class HttpModel {

    /**
     * 获取视频列表
     *
     * @param page
     * @param dataListener
     */
    public static void getVideoList(int page, Context context, InterfaceListenter.VideosDataListener dataListener, List<NewsVideoItem.DataBean> videoBeans, RefreshLayout refreshLayout) {
        MyDisposableObserver observer = new MyDisposableObserver(refreshLayout,context, false, new HttpOnNextListener() {
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
                        if (refreshLayout != null) {
                            if (page > 0) {
                                refreshLayout.finishLoadMore();
                            } else {
                                refreshLayout.finishRefresh();
                                refreshLayout.resetNoMoreData();
                            }
                        }
                    }
                } else {
                    if (refreshLayout != null) {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }

            @Override
            public void onFail(String e) {
                Toast.makeText(context, e, Toast.LENGTH_SHORT).show();
            }
        });
        HttpGet.getVideo(observer, page);
    }

    public static void getCategoriesNews(int page, Context context, HashMap<String, String> params, InterfaceListenter.LoadCategoriesNews listener, List<Milite.DataBean> milites, RefreshLayout refreshLayout, String... s) {
        MyDisposableObserver observer = new MyDisposableObserver(refreshLayout,context, false, new HttpOnNextListener() {
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
            public void onFail(String e) {
                Toast.makeText(context, e, Toast.LENGTH_SHORT).show();
            }
        });
        HttpGet.getNews(observer, page, params);
    }


    public static void getActivityHotNews(Context context, int page, InterfaceListenter.HotNewsDataListener listener, List<NewsHot.DataBean> hotBeans, RefreshLayout refreshLayout) {
        MyDisposableObserver observer = new MyDisposableObserver(refreshLayout,context, false, new HttpOnNextListener() {
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
            public void onFail(String e) {
                Toast.makeText(context, e, Toast.LENGTH_SHORT).show();
            }
        });
        HttpGet.getHot(observer, page);

    }

    public static void loadNewsData(Context context, int page, InterfaceListenter.NewsItemListener listener, List<NewsItem.DataBean> dataBeanList, RefreshLayout refreshLayout) {
        MyDisposableObserver observer = new MyDisposableObserver(refreshLayout, context, false, new HttpOnNextListener() {
            @Override
            public void onSuccess(Object o) {
                NewsItem newsItem = (NewsItem) o;
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
            public void onFail(String e) {
                Toast.makeText(context, e, Toast.LENGTH_SHORT).show();

            }
        });
        HttpGet.getloadNewsData(observer, page);
    }

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


    public static void getMainNewData(Context context, InterfaceListenter.MainNewsDataListener listener, RefreshLayout refreshLayout) {
        MyDisposableObserver observer = new MyDisposableObserver(refreshLayout,context, false, new HttpOnNextListener() {
            @Override
            public void onSuccess(Object o) {
                if (listener != null) {
                    listener.mainDataChange((NewsMain) o);
                    refreshLayout.finishRefresh();
                    refreshLayout.resetNoMoreData();
                }
            }

            @Override
            public void onFail(String e) {
                Toast.makeText(context, e, Toast.LENGTH_SHORT).show();

            }
        });
        HttpGet.getMain(observer);


    }


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
