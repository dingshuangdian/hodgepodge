package com.lsqidsd.hodgepodge.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.ViewHolder.LoadMoreHolder;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsTop;
import com.lsqidsd.hodgepodge.databinding.HotBinding;
import com.lsqidsd.hodgepodge.databinding.Loadbinding;
import com.lsqidsd.hodgepodge.databinding.TopBinding;
import com.lsqidsd.hodgepodge.databinding.OtherBinding;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;
import com.lsqidsd.hodgepodge.viewmodel.NewsItemModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class YWAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private int page;
    private GridViewImgAdapter gridViewImgAdapter;
    private List<NewsItem.DataBean> dataBeanList;
    private List<NewsTop.DataBean> newsTopList;
    private List<NewsHot.DataBean> newsHotList;
    private NewsMain newsMain;
    private final int LOAD_MORE = -1;//上拉加载
    private final int NEWS_ITEM_TYPE_01 = 0;//置顶
    private final int NEWS_ITEM_TYPE_02 = 1;//热点精选
    private final int NEWS_ITEM_TYPE_03 = 2;//列表
    private RefreshLayout refreshLayout;

    public YWAdapter(Context context, RefreshLayout refreshLayout) {
        this.context = context;
        this.refreshLayout = refreshLayout;


    }

    public void addDatas(NewsMain t) {
        this.newsMain = t;
        this.dataBeanList = newsMain.getNewsItems();
        this.newsTopList = newsMain.getNewsTops();
        this.newsHotList = newsMain.getNewsHot();
        this.page = 1;

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
            refreshLayout.setOnLoadMoreListener(a -> HttpModel.loadNewsData(context, page, b ->
                            page++
                    , dataBeanList, refreshLayout));
        } else if (holder instanceof YWViwHolder) {
            YWViwHolder ywViwHolder = (YWViwHolder) holder;
            ywViwHolder.bindData(dataBeanList.get(position - 3));
        } else if (holder instanceof TopHolder) {
            TopHolder topHolder = (TopHolder) holder;
            if (!newsTopList.isEmpty()) {
                newsTopList.get(0).setFlag("showImg");
                topHolder.bindData(newsTopList.get(position));
            }
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
            if (bean.getIrs_imgs().get_$227X148() != null && bean.getIrs_imgs().get_$227X148().size() == 3) {
                gridViewImgAdapter = new GridViewImgAdapter(context);
                gridViewImgAdapter.addImgs(bean.getIrs_imgs().get_$227X148(), bean.getUrl(), otherBinding);
                otherBinding.gv.setAdapter(gridViewImgAdapter);
            }
            otherBinding.setNewsitem(new NewsItemModel(context, bean, bean.getIrs_imgs().get_$227X148(), otherBinding));
        }
    }

    public class TopHolder extends ViewHolder {
        TopBinding topBinding;

        public TopHolder(TopBinding itemView) {
            super(itemView.getRoot());
            topBinding = itemView;
        }

        public void bindData(NewsTop.DataBean top) {
            topBinding.setNewsitem(new NewsItemModel(context, top));
        }
    }

    public class HotHolder extends ViewHolder implements InterfaceListenter.ItemShowListener {
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
