package com.lsqidsd.hodgepodge.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.databinding.RoItemBinding;
import com.lsqidsd.hodgepodge.databinding.VpItemBinding;
import com.lsqidsd.hodgepodge.utils.Jump;
import com.lsqidsd.hodgepodge.view.HotActivity;
import com.lsqidsd.hodgepodge.viewmodel.NewsItemModel;

import java.util.List;

public class NewsHotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ObservableInt> observableInt;
    private Context context;
    private List<NewsItem.DataBean> dataBeanList;
    private List<NewsHot.DataBean> newsHotList;

    public NewsHotAdapter(List<ObservableInt> observableInt, Context context, List<NewsItem.DataBean> dataBeanList, List<NewsHot.DataBean> newsHotList) {
        this.observableInt = observableInt;
        this.context = context;
        this.dataBeanList = dataBeanList;
        this.newsHotList = newsHotList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        RoItemBinding roItemBinding;
        VpItemBinding vpItemBinding;
        if (observableInt.get(0).get() == 0) {
            roItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.ro_item, parent, false);
            viewHolder = new RolViewHolder(roItemBinding);
        }
        if (observableInt.get(1).get() == 0) {
            vpItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.vp_item, parent, false);
            viewHolder = new VpViewHolder(vpItemBinding);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class RolViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public RolViewHolder(RoItemBinding itemView) {
            super(itemView.getRoot());
            itemView.setRoitem(new NewsItemModel(context));
            loadData(itemView);
        }

        public void loadData(RoItemBinding roItemBinding) {
            for (int i = 0; i < dataBeanList.size(); i++) {
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                textView = new TextView(context);
                textView.setLayoutParams(params);
                textView.setTextColor(Color.parseColor("#2d3444"));
                textView.setTextSize(14);
                textView.setLineSpacing(7, 1f);
                textView.setText(dataBeanList.get(i).getTitle());
                roItemBinding.vf.addView(textView);
            }
        }
    }

    public class VpViewHolder extends RecyclerView.ViewHolder {
        ViewPageAdapter viewPageAdapter;
        VpItemBinding vpItemBinding;

        public VpViewHolder(VpItemBinding itemView) {
            super(itemView.getRoot());
            vpItemBinding = itemView;
            viewPageAdapter = new ViewPageAdapter(context);
            loadData(vpItemBinding);
        }

        public void loadData(VpItemBinding vpItemBinding) {
            viewPageAdapter.addNews(newsHotList);
            vpItemBinding.vp.setAdapter(viewPageAdapter);
            vpItemBinding.vp.setPageMargin((int) (context.getResources().getDisplayMetrics().density * 10));
            vpItemBinding.vp.setOffscreenPageLimit(2);//预加载2个
            vpItemBinding.vp.setOnFindMoreListener(() -> Jump.jumpToNormalActivity(context, HotActivity.class));
        }
    }
}
