package com.lsqidsd.hodgepodge.viewmodel;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultListener;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultSub;
import com.lsqidsd.hodgepodge.http.RetrofitServiceManager;
import com.lsqidsd.hodgepodge.utils.JsonUtils;
import com.lsqidsd.hodgepodge.utils.TimeUtil;
import com.lsqidsd.hodgepodge.view.WebViewActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class HotViewModule {
    private Context mContext;
    public ObservableInt progressVisibility = new ObservableInt(View.VISIBLE);
    public ObservableInt lineVisibility = new ObservableInt(View.GONE);
    public ObservableInt imgfVisbility = new ObservableInt(View.VISIBLE);
    public ObservableInt gvVisbility = new ObservableInt(View.GONE);
    public ObservableInt commentVisibility = new ObservableInt(View.GONE);
    private JSONArray jsonArray;
    private ItemNewsDataListener itemNewsDataListener;
    private List<NewsHot.DataBean> hotBeans = new ArrayList<>();
    private NewsHot.DataBean dataBean;

    public HotViewModule(Context mContext, ItemNewsDataListener listener) {
        this.mContext = mContext;
        this.itemNewsDataListener = listener;
    }

    public HotViewModule(Context mContext, List<NewsHot.DataBean> hotBeans) {
        this.mContext = mContext;
        this.hotBeans = hotBeans;
    }

    public HotViewModule(Context mContext, JSONArray jsonArray, NewsHot.DataBean dataBean) {
        this.mContext = mContext;
        this.jsonArray = jsonArray;
        this.dataBean = dataBean;
    }

    public String getTitle() {
        return dataBean.getTitle();
    }

    public String getTime() {
        return TimeUtil.formatTime(dataBean.getPublish_time());
    }

    public String getAuthor() {
        return dataBean.getSource();
    }

    public String getUrl() {
        return dataBean.getUrl();
    }


    public String getComment() {
        if (dataBean.getComment_num() == 0) {
            commentVisibility.set(View.GONE);
        } else {
            commentVisibility.set(View.VISIBLE);
        }
        return dataBean.getComment_num() + "评";
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

    @BindingAdapter({"imageUrl"})
    public static void setImageView(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.get().load(imageUrl).into(imageView);
        }
    }

    public void click(View view) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        switch (view.getId()) {
            case R.id.view_03:
                intent.putExtra("url", getUrl());
                mContext.startActivity(intent);
                break;
        }
    }
    public void getMoreData(int page, ItemNewsDataListener listener) {
        this.itemNewsDataListener = listener;
        getHotNews(page);
    }
    public void getHotNews(int page) {
        Observable<NewsHot> observable = RetrofitServiceManager.getInstance().getHttpApi().getHotNews(page, 15);
        RetrofitServiceManager.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object o) {
                NewsHot newsHot = (NewsHot) o;
                if (newsHot.getData().size() > 0) {
                    for (NewsHot.DataBean hot : newsHot.getData()) {
                        hotBeans.add(hot);
                    }
                    if (itemNewsDataListener != null) {
                        itemNewsDataListener.dataBeanChange(hotBeans);
                    }
                    progressVisibility.set(View.GONE);
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
        }));
    }
    public interface ItemNewsDataListener {
        void dataBeanChange(List<NewsHot.DataBean> dataBeans);
    }
}
