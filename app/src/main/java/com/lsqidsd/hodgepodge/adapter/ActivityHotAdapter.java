package com.lsqidsd.hodgepodge.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.databinding.Loadmore01Binding;
import com.lsqidsd.hodgepodge.databinding.NewsItemHotBinding;
import com.lsqidsd.hodgepodge.utils.JsonUtils;
import com.lsqidsd.hodgepodge.viewmodel.HotViewModule;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

public class ActivityHotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private int page = 1;
    private LayoutInflater layoutInflater;
    private List<NewsHot.DataBean> hotBeans;
    private final int TYPE_NORMAL = 1;
    private final int LOAD_MORE = -1;//上拉加载

    public ActivityHotAdapter(Context context, List<NewsHot.DataBean> hotBean) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.hotBeans = hotBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        NewsItemHotBinding binding;
        Loadmore01Binding loadbinding;
        switch (viewType) {
            case TYPE_NORMAL:
                binding = DataBindingUtil.inflate(layoutInflater, R.layout.news_item_hot, parent, false);
                viewHolder = new HotViewHolder(binding);
                break;

            case LOAD_MORE:
                loadbinding = DataBindingUtil.inflate(layoutInflater, R.layout.loadmore_01, parent, false);
                viewHolder = new LoadMoreHolder(loadbinding);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HotViewHolder) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.loadData(hotBeans.get(position));
        }
        if (holder instanceof LoadMoreHolder) {
            LoadMoreHolder loadMoreHolder = (LoadMoreHolder) holder;
            loadMoreHolder.loadMoreData();
        }
    }

    @Override
    public int getItemCount() {
        return hotBeans.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return LOAD_MORE;
        } else {
            return TYPE_NORMAL;
        }

    }

    public class HotViewHolder extends RecyclerView.ViewHolder {
        NewsItemHotBinding itemHotBinding;

        public HotViewHolder(NewsItemHotBinding itemView) {
            super(itemView.getRoot());
            itemHotBinding = itemView;

        }
        private void loadData(NewsHot.DataBean bean) {
            JSONObject jsonObject = JsonUtils.toJsonObject(bean.getIrs_imgs());
            JSONArray jsonArray = null;
            if (jsonObject.has("227X148")) {
                jsonArray = (JSONArray) jsonObject.opt("227X148");
                if (jsonArray.length() == 3) {
                    GridViewImgAdapter gridViewImgAdapter;
                    gridViewImgAdapter = new GridViewImgAdapter(jsonArray, bean.getUrl(), context);
                    itemHotBinding.gv.setAdapter(gridViewImgAdapter);
                }
            }
            itemHotBinding.setNewsitem(new HotViewModule(context, jsonArray, bean));

        }
    }

    public class LoadMoreHolder extends RecyclerView.ViewHolder {
        Loadmore01Binding loadmoreBinding;

        public LoadMoreHolder(@NonNull Loadmore01Binding itemView) {
            super(itemView.progress);
            this.loadmoreBinding = itemView;
        }

        public void loadMoreData() {
            loadmoreBinding.setLoadview(new HotViewModule(context, hotBeans));
            loadmoreBinding.getLoadview().getMoreData(page, new HotViewModule.ItemNewsDataListener() {
                @Override
                public void dataBeanChange(List<NewsHot.DataBean> dataBeans) {
                    page++;
                }
            });
        }
    }
}
