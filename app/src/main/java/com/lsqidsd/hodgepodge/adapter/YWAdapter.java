package com.lsqidsd.hodgepodge.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.databinding.NewsItem01Binding;
import com.lsqidsd.hodgepodge.viewmodel.newsitemmodel.NewsItemModel;

import java.util.List;

public class YWAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private List<NewsItem.DataBean> list;
    private static final int LOAD_MORE = -1;
    private static final int NEWS_ITEM_TYPE_01 = 0;
    private static final int NEWS_ITEM_TYPE_02 = 1;


    public YWAdapter(Context context, List<NewsItem.DataBean> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder myViewHolder = null;
        NewsItem01Binding binding;
        switch (viewType) {
            case LOAD_MORE:
                binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.loadmore, parent, false);
                myViewHolder = new LoadMoreHolder(binding);
                break;
            case NEWS_ITEM_TYPE_01:
                binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.news_item_01, parent, false);
                myViewHolder = new YWViwHolder(binding);
                break;
        }
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case LOAD_MORE:
                LoadMoreHolder loadMoreHolder = (LoadMoreHolder) holder;
            case NEWS_ITEM_TYPE_01:
                YWViwHolder ywViwHolder = (YWViwHolder) holder;
                NewsItem.DataBean dataBean = list.get(position);
                ywViwHolder.bindData(dataBean);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return LOAD_MORE;
        } else {
            return NEWS_ITEM_TYPE_01;
        }
    }

    public class YWViwHolder extends ViewHolder {
        NewsItem01Binding binding;

        public YWViwHolder(@NonNull NewsItem01Binding itemView) {
            super(itemView.view);
            this.binding = itemView;
        }

        public void bindData(NewsItem.DataBean bean) {
            binding.setNewsitem(new NewsItemModel(context, bean));
        }
    }

    public class LoadMoreHolder extends ViewHolder {

        public LoadMoreHolder(@NonNull NewsItem01Binding itemView) {
            super(itemView);
        }
    }
}
