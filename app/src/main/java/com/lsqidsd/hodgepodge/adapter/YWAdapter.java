package com.lsqidsd.hodgepodge.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsTop;
import com.lsqidsd.hodgepodge.databinding.HotBinding;
import com.lsqidsd.hodgepodge.databinding.Loadbinding;
import com.lsqidsd.hodgepodge.databinding.TopBinding;
import com.lsqidsd.hodgepodge.databinding.OtherBinding;
import com.lsqidsd.hodgepodge.viewmodel.newsitemmodel.NewsItemModel;

import java.util.List;

public class YWAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private int page = 1;
    private List<NewsItem.DataBean> dataBeanList;
    private List<NewsTop> newsTopList;
    private final int LOAD_MORE = -1;//上拉加载
    private final int NEWS_ITEM_TYPE_01 = 0;//置顶
    private final int NEWS_ITEM_TYPE_02 = 1;//热点精选
    private final int NEWS_ITEM_TYPE_03 = 2;//列表

    public YWAdapter(Context context, NewsMain list) {
        this.context = context;
        this.dataBeanList = list.getNewsItems();
        this.newsTopList = list.getNewsTops();
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
            NewsItem.DataBean dataBean = dataBeanList.get(position - 2);
            ywViwHolder.bindData(dataBean);
        } else if (holder instanceof TopHolder) {
            TopHolder topHolder = (TopHolder) holder;
            NewsTop newsTop = new NewsTop(dataBeanList.get(0).getComment_num(), dataBeanList.get(0).getSource(), dataBeanList.get(0).getTitle(), dataBeanList.get(0).getPublish_time(), dataBeanList.get(0).getUrl(), dataBeanList.get(0).getBimg());
            topHolder.bindData(newsTop);
        } else if (holder instanceof HotHolder) {
            HotHolder hotHolder = (HotHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size() + 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return LOAD_MORE;
        } else if (position == 0) {
            return NEWS_ITEM_TYPE_01;
        } else if (position == 1) {
            return NEWS_ITEM_TYPE_02;
        } else {
            return NEWS_ITEM_TYPE_03;
        }
    }

    public class YWViwHolder extends ViewHolder {
        OtherBinding otherBinding;

        public YWViwHolder(@NonNull OtherBinding itemView) {
            super(itemView.view);
            this.otherBinding = itemView;
        }

        public void bindData(NewsItem.DataBean bean) {
            otherBinding.setNewsitem(new NewsItemModel(context, bean));
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

        public TopHolder(TopBinding itemView) {
            super(itemView.getRoot());
            topBinding = itemView;
        }

        public void bindData(NewsTop top) {
            topBinding.setNewsitem(new NewsItemModel(context, top));
        }
    }

    public class HotHolder extends ViewHolder {
        HotBinding hotBinding;
        TextView textView;

        public HotHolder(HotBinding itemView) {
            super(itemView.getRoot());
            hotBinding = itemView;
            bindRollView();
        }

        public void bindRollView() {
            for (int i = 0; i < dataBeanList.size(); i++) {
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                textView = new TextView(context);
                textView.setLayoutParams(params);
                textView.setTextColor(Color.parseColor("#2d3444"));
                textView.setTextSize(14);
                textView.setText(dataBeanList.get(i).getTitle());
                hotBinding.vf.addView(textView);
            }
        }
    }

}
