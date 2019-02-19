package com.lsqidsd.hodgepodge.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import com.lsqidsd.hodgepodge.databinding.Loadbinding;
import com.lsqidsd.hodgepodge.databinding.VideosItemBinding;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;
import com.lsqidsd.hodgepodge.viewmodel.VideosViewModule;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class VideoViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NewsVideoItem.DataBean> videos;
    private Context context;
    private LayoutInflater inflate;
    private final int NORMAL_ITEM = 0;
    private int page = 1;
    private final int LOAD_MORE = -1;//上拉加载
    private RefreshLayout refreshLayout;

    public VideoViewAdapter(List<NewsVideoItem.DataBean> videos, Context context, RefreshLayout refreshLayout) {
        this.videos = videos;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
        this.refreshLayout = refreshLayout;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        VideosItemBinding videosItemBinding;
        Loadbinding loadbinding;
        switch (viewType) {
            case NORMAL_ITEM:
                videosItemBinding = DataBindingUtil.inflate(inflate, R.layout.videos_item, parent, false);
                viewHolder = new VideoHolder(videosItemBinding);
                break;
            case LOAD_MORE:
                loadbinding = DataBindingUtil.inflate(inflate, R.layout.loadmore, parent, false);
                viewHolder = new LoadMoreHolder(loadbinding);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VideoHolder) {
            VideoHolder videoHolder = (VideoHolder) holder;
            videoHolder.bindData(videos.get(position));
        }
        if (holder instanceof LoadMoreHolder) {
            LoadMoreHolder moreHolder = (LoadMoreHolder) holder;
            moreHolder.loadMoreData();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return LOAD_MORE;
        } else {
            return NORMAL_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return videos.size() + 1;
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        VideosItemBinding itemBinding;

        public VideoHolder(VideosItemBinding itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
        }

        public void bindData(NewsVideoItem.DataBean video) {
            itemBinding.setVideoitem(new VideosViewModule(video, context));
        }
    }

    public class LoadMoreHolder extends RecyclerView.ViewHolder {
        Loadbinding loadmoreBinding;

        public LoadMoreHolder(@NonNull Loadbinding itemView) {
            super(itemView.getRoot());
            this.loadmoreBinding = itemView;
        }

        public void loadMoreData() {
            refreshLayout.setOnLoadMoreListener(a -> HttpModel.getVideoList(page, b -> page++, videos, refreshLayout));
        }
    }
}
