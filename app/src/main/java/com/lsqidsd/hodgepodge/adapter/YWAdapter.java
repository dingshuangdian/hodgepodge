package com.lsqidsd.hodgepodge.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.base.BaseApplication;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsTop;
import com.lsqidsd.hodgepodge.databinding.HotBinding;
import com.lsqidsd.hodgepodge.databinding.Loadbinding;
import com.lsqidsd.hodgepodge.databinding.TopBinding;
import com.lsqidsd.hodgepodge.databinding.OtherBinding;
import com.lsqidsd.hodgepodge.utils.JsonUtils;
import com.lsqidsd.hodgepodge.utils.Tool;
import com.lsqidsd.hodgepodge.view.WebViewActivity;
import com.lsqidsd.hodgepodge.viewmodel.newsitemmodel.NewsItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class YWAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private int page = 1;
    private List<NewsItem.DataBean> dataBeanList;
    private List<NewsTop.DataBean> newsTopList;
    private List<NewsHot.DataBean> newsHotList;
    private final int LOAD_MORE = -1;//上拉加载
    private final int NEWS_ITEM_TYPE_01 = 0;//置顶
    private final int NEWS_ITEM_TYPE_02 = 1;//热点精选
    private final int NEWS_ITEM_TYPE_03 = 2;//列表

    public YWAdapter(Context context, NewsMain list, Activity activity) {
        this.context = context;
        this.dataBeanList = list.getNewsItems();
        this.newsTopList = list.getNewsTops();
        this.newsHotList = list.getNewsHot();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder myViewHolder = null;
        TopBinding topBinding;
        HotBinding hotBinding;
        Loadbinding loadmoreBinding;
        OtherBinding otherBinding;
        switch (viewType) {
            case LOAD_MORE:
                loadmoreBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.loadmore, parent, false);
                myViewHolder = new LoadMoreHolder(loadmoreBinding);
                break;
            case NEWS_ITEM_TYPE_01:
                topBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.news_item_01, parent, false);
                myViewHolder = new TopHolder(topBinding);
                break;
            case NEWS_ITEM_TYPE_02:
                hotBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.news_item_02, parent, false);
                myViewHolder = new HotHolder(hotBinding);
                break;
            case NEWS_ITEM_TYPE_03:
                otherBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.news_item_03, parent, false);
                myViewHolder = new YWViwHolder(otherBinding);
                break;
        }
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (holder instanceof LoadMoreHolder) {
            LoadMoreHolder loadMoreHolder = (LoadMoreHolder) holder;
            loadMoreHolder.loadMoreData();
        } else if (holder instanceof YWViwHolder) {
            YWViwHolder ywViwHolder = (YWViwHolder) holder;
            NewsItem.DataBean dataBean = dataBeanList.get(position - 3);
            ywViwHolder.bindData(dataBean);
        } else if (holder instanceof TopHolder) {
            TopHolder topHolder = (TopHolder) holder;
            newsTopList.get(0).setFlag("showImg");
            topHolder.bindData(newsTopList.get(position));
        } else if (holder instanceof HotHolder) {
            HotHolder hotHolder = (HotHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size() + 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return LOAD_MORE;
        } else if (position == 0 || position == 1) {
            return NEWS_ITEM_TYPE_01;
        } else if (position == 2) {
            return NEWS_ITEM_TYPE_02;
        } else {
            return NEWS_ITEM_TYPE_03;
        }
    }

    public class YWViwHolder extends ViewHolder {
        OtherBinding otherBinding;

        public YWViwHolder(@NonNull OtherBinding itemView) {
            super(itemView.getRoot());
            this.otherBinding = itemView;
        }

        public void bindData(NewsItem.DataBean bean) {
            JSONObject jsonObject = JsonUtils.toJsonObject(bean.getIrs_imgs());
            JSONArray jsonArray = null;
            if (jsonObject.has("227X148")) {
                jsonArray = (JSONArray) jsonObject.opt("227X148");
                if (jsonArray.length() == 3) {
                    GridViewImgAdapter gridViewImgAdapter;
                    gridViewImgAdapter = new GridViewImgAdapter(jsonArray, bean.getUrl(), context);
                    otherBinding.gv.setAdapter(gridViewImgAdapter);
                }
            }
            otherBinding.setNewsitem(new NewsItemModel(context, bean, jsonArray));
        }
    }

    public class LoadMoreHolder extends ViewHolder {
        Loadbinding loadmoreBinding;

        public LoadMoreHolder(@NonNull Loadbinding itemView) {
            super(itemView.progress);
            this.loadmoreBinding = itemView;
        }

        public void loadMoreData() {
            loadmoreBinding.setLoadview(new NewsItemModel(context, dataBeanList));
            loadmoreBinding.getLoadview().getMoreData(page, new NewsItemModel.ItemNewsDataListener() {
                @Override
                public void dataBeanChange(NewsMain dataBeans) {
                    page++;
                }
            });
        }
    }

    public class TopHolder extends ViewHolder {
        TopBinding topBinding;
        JSONArray jsonArray = null;

        public TopHolder(TopBinding itemView) {
            super(itemView.getRoot());
            topBinding = itemView;
        }

        public void bindData(NewsTop.DataBean top) {
            topBinding.setNewsitem(new NewsItemModel(context, top, jsonArray));
        }
    }

    public class HotHolder extends ViewHolder implements NewsItemModel.ItemShowListener {
        HotBinding hotBinding;

        public HotHolder(HotBinding itemView) {
            super(itemView.getRoot());
            hotBinding = itemView;
            hotBinding.setNewsitem(new NewsItemModel(context, this));

        }

        @Override
        public void itemShow(ObservableInt... observableInt) {
            List<ObservableInt> observableInts = new ArrayList<>();
            for (ObservableInt obi : observableInt) {
                observableInts.add(obi);
            }
            NewsHotAdapter newsHotAdapter = new NewsHotAdapter(observableInts, context, dataBeanList, newsHotList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            hotBinding.itemRv.setLayoutManager(linearLayoutManager);
            hotBinding.itemRv.setAdapter(newsHotAdapter);

        }
    }
}
