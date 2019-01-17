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
import com.lsqidsd.hodgepodge.databinding.NewsItem01Binding;
import com.lsqidsd.hodgepodge.viewmodel.newsitemmodel.NewsItemModel;

import java.util.List;

public class YWAdapter extends RecyclerView.Adapter<YWAdapter.YWViwHolder> {
    private Context context;
    private List<NewsItem.DataBean> list;

    public YWAdapter(Context context, List<NewsItem.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public YWViwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsItem01Binding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.news_item_01, parent, false);

        return new YWViwHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull YWViwHolder holder, int position) {
        NewsItem.DataBean dataBean = list.get(position);
        holder.bindData(dataBean);
    }
    /**
     * 由于RecyclerView的onBindViewHolder()方法，只有在getItemViewType()返回类型不同时才会调用，这点是跟ListView的getView()方法不同的地方，
     * 所以如果想要每次都调用onBindViewHolder()刷新item数据，就要重写getItemViewType()，让其返回position，否则很容易产生数据错乱的现象。
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class YWViwHolder extends ViewHolder {
        NewsItem01Binding binding;

        public YWViwHolder(@NonNull NewsItem01Binding itemView) {
            super(itemView.newsview);
            this.binding = itemView;
        }
        public void bindData(NewsItem.DataBean bean) {
            if (binding.getNewsitem() == null) {
                binding.setNewsitem(new NewsItemModel(context, bean));
            } else {
                binding.getNewsitem().setDataBean(bean);
            }
        }
    }
}
