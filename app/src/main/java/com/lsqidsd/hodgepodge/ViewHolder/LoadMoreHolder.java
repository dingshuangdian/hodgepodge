package com.lsqidsd.hodgepodge.ViewHolder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class LoadMoreHolder<K extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public LoadMoreHolder(K itemView) {
        super(itemView.getRoot());
    }
}
