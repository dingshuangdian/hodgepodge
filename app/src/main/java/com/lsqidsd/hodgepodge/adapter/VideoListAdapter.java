package com.lsqidsd.hodgepodge.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.ViewHolder.LoadMoreHolder;
import com.lsqidsd.hodgepodge.bean.DailyVideos;
import com.lsqidsd.hodgepodge.databinding.Loadbinding;
import com.lsqidsd.hodgepodge.databinding.VideosItemBinding;
import com.lsqidsd.hodgepodge.diyview.videoview.Jzvd;
import com.lsqidsd.hodgepodge.diyview.videoview.JzvdStd;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;
import com.lsqidsd.hodgepodge.viewmodel.VideosViewModule;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.squareup.picasso.Picasso;

import java.util.List;


public class VideoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DailyVideos.IssueListBean.ItemListBean> listBeans;
    private Context context;
    private LayoutInflater layoutInflater;
    private RefreshLayout refreshLayout;
    private final int LOAD_MORE = -1;
    private final int NORMAL = 1;
    private String url;

    public VideoListAdapter(List<DailyVideos.IssueListBean.ItemListBean> listBeans, Context context, RefreshLayout refreshLayout, String url) {
        this.listBeans = listBeans;
        this.context = context;
        this.url = url;
        this.refreshLayout = refreshLayout;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        VideosItemBinding videosItemBinding;
        Loadbinding loadbinding;

        switch (viewType) {
            case NORMAL:
                videosItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.videos_item, parent, false);
                viewHolder = new DataViewHolder(videosItemBinding);
                break;
            case LOAD_MORE:
                loadbinding = DataBindingUtil.inflate(layoutInflater, R.layout.loadmore, parent, false);
                viewHolder = new LoadMoreHolder(loadbinding);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadMoreHolder) {
            HttpModel.getDailyVideos(listBeans, (a, b) -> {
                url = b;
                listBeans = a;
            }, refreshLayout, url, "");
        } else if (holder instanceof DataViewHolder) {
            ((DataViewHolder) holder).initData(listBeans.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return LOAD_MORE;
        } else {
            return NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return listBeans.size() + 1;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        VideosItemBinding binding;

        public DataViewHolder(VideosItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void initData(DailyVideos.IssueListBean.ItemListBean bean) {
            JzvdStd jzvdStd = binding.videoItem;
            jzvdStd.setUp(bean.getData().getPlayUrl(), bean.getData().getTitle(), Jzvd.SCREEN_WINDOW_LIST);
            Picasso.get().load(bean.getData().getCover().getFeed()).into(jzvdStd.thumbImageView);
            switch (bean.getState()) {
                case 0:
                    jzvdStd.startVideo();
                    break;
            }
            binding.setVideoitem(new VideosViewModule(bean, context));
        }
    }

}
