package com.lsqidsd.hodgepodge.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.databinding.DownloadItemBinding;

public class DownloadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;

    public DownloadAdapter(Context context) {
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DownloadItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.download_item, parent, false);
        return new DownloadHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class DownloadHolder extends RecyclerView.ViewHolder {
        public DownloadHolder(DownloadItemBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
