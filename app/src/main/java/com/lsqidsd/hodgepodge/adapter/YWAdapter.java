package com.lsqidsd.hodgepodge.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.databinding.Loadbinding;
import com.lsqidsd.hodgepodge.databinding.NewsItem01Binding;
import com.lsqidsd.hodgepodge.viewmodel.newsitemmodel.NewsItemModel;

import java.util.List;

public class YWAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private int page = 1;
    private List<NewsItem.DataBean> list;
    private final int LOAD_MORE = -1;
    private final int NEWS_ITEM_TYPE_01 = 0;
    private final int NEWS_ITEM_TYPE_02 = 1;

    public YWAdapter(Context context, List<NewsItem.DataBean> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder myViewHolder = null;
        NewsItem01Binding binding;
        Loadbinding loadmoreBinding;
        switch (viewType) {
            case LOAD_MORE:
                loadmoreBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.loadmore, parent, false);
                myViewHolder = new LoadMoreHolder(loadmoreBinding);
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

        if (holder instanceof LoadMoreHolder) {
            LoadMoreHolder loadMoreHolder = (LoadMoreHolder) holder;
            loadMoreHolder.loadMoreData();
        } else if (holder instanceof YWViwHolder) {
            YWViwHolder ywViwHolder = (YWViwHolder) holder;
            NewsItem.DataBean dataBean = list.get(position);
            ywViwHolder.bindData(dataBean);
        }
    }
    @Override
    public int getItemCount() {
        return list.size() + 1;
    }
    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
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
        Loadbinding loadmoreBinding;
        public LoadMoreHolder(@NonNull Loadbinding itemView) {
            super(itemView.progress);
            this.loadmoreBinding = itemView;
        }

        public void loadMoreData() {
            loadmoreBinding.setLoadview(new NewsItemModel(context, list));
            loadmoreBinding.getLoadview().getMoreData(page, new NewsItemModel.ItemNewsDataListener() {
                @Override
                public void dataBeanChange(List<NewsItem.DataBean> dataBeans) {
                    page++;
                }
            });
        }
    }
}
