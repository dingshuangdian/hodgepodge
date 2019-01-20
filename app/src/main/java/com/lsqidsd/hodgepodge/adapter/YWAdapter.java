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
import com.lsqidsd.hodgepodge.databinding.LoadmoreBinding;
import com.lsqidsd.hodgepodge.databinding.LoadmoreBindingImpl;
import com.lsqidsd.hodgepodge.databinding.NewsItem01Binding;
import com.lsqidsd.hodgepodge.viewmodel.UtilsViewModel;
import com.lsqidsd.hodgepodge.viewmodel.newsitemmodel.NewsItemModel;

import java.util.List;

public class YWAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private List<NewsItem.DataBean> list;
    private final int LOAD_MORE = -1;
    private final int NEWS_ITEM_TYPE_01 = 0;
    private final int NEWS_ITEM_TYPE_02 = 1;
    //当前加载状态，默认为加载完成
    private int loadState = 2;
    //正在加载
    public final int LOADING = 3;
    //加载完成
    private final int LOADING_COMPLETE = 4;
    // 加载到底
    public final int LOADING_END = 5;


    public YWAdapter(Context context, List<NewsItem.DataBean> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder myViewHolder = null;
        NewsItem01Binding binding;
        LoadmoreBinding loadmoreBinding;
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
            switch (loadState) {
                case LOADING://正在加载
                    break;
                case LOADING_COMPLETE://加载完成
                    break;
                case LOADING_END://加载到底
                    break;
                default:
                    break;

            }


        } else if (holder instanceof YWViwHolder) {
            YWViwHolder ywViwHolder = (YWViwHolder) holder;
            NewsItem.DataBean dataBean = list.get(position);
            ywViwHolder.bindData(dataBean);
        }
    }

    @Override
    public int getItemCount() {
        Log.e("size", list.size() + "");
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
        LoadmoreBinding loadmoreBinding;

        public LoadMoreHolder(@NonNull LoadmoreBinding itemView) {
            super(itemView.getRoot());
            this.loadmoreBinding = itemView;
        }

        public void loadMoreData() {

        }
    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

}
