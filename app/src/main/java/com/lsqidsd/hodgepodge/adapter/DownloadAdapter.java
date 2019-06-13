package com.lsqidsd.hodgepodge.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.bean.DailyVideos;
import com.lsqidsd.hodgepodge.databinding.DownloadItemBinding;
import com.lsqidsd.hodgepodge.http.downserver.DownloadListener;
import com.lsqidsd.hodgepodge.http.downserver.DownloadTask;
import com.lsqidsd.hodgepodge.http.downserver.OkDownload;
import com.lsqidsd.hodgepodge.http.downserver.db.DownloadManager;
import com.lsqidsd.hodgepodge.http.downserver.model.Progress;
import com.lsqidsd.hodgepodge.service.LogDownloadListener;
import com.lsqidsd.hodgepodge.utils.ApkUtils;

import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.DownloadHolder> {
    private LayoutInflater inflater;
    public static final int TYPE_ALL = 0;
    public static final int TYPE_FINISH = 1;
    public static final int TYPE_ING = 2;
    private List<DownloadTask> values;
    private NumberFormat numberFormat;
    private Context context;
    private int type;

    public DownloadAdapter(Context context) {
        this.context = context;
        numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);
        inflater = LayoutInflater.from(context);

    }

    public void updateData(int type) {
        //这里是将数据库的数据恢复
        this.type = type;
        if (type == TYPE_ALL) values = OkDownload.restore(DownloadManager.getInstance().getAll());
        if (type == TYPE_FINISH)
            values = OkDownload.restore(DownloadManager.getInstance().getFinished());
        if (type == TYPE_ING)
            values = OkDownload.restore(DownloadManager.getInstance().getDownloading());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DownloadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DownloadItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.download_item, parent, false);
        return new DownloadHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadHolder holder, int position) {
        DownloadTask task = values.get(position);
        String tag = createTag(task);
        task.register(new ListDownloadListener(tag, holder))//
                .register(new LogDownloadListener());
        holder.setTag(tag);
        holder.setTask(task);
        holder.bind();
        holder.refresh(task.progress);


    }

    @Override
    public int getItemCount() {
        return values == null ? 0 : values.size();
    }

    public class DownloadHolder extends RecyclerView.ViewHolder {
        private DownloadTask task;
        private String tag;
        private DownloadItemBinding binding;

        public DownloadHolder(DownloadItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void setTask(DownloadTask task) {
            this.task = task;
        }

        public void remove() {
            task.remove(true);
            updateData(type);
        }

        public void restart() {
            task.restart();
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

        public void start() {
            Progress progress = task.progress;
            switch (progress.status) {
                case Progress.PAUSE:
                case Progress.NONE:
                case Progress.ERROR:
                    task.start();
                    break;
                case Progress.LOADING:
                    task.pause();
                    break;
                case Progress.FINISH:
                    if (ApkUtils.isAvailable(context, new File(progress.filePath))) {
                        ApkUtils.uninstall(context, ApkUtils.getPackageName(context, progress.filePath));
                    } else {
                        ApkUtils.install(context, new File(progress.filePath));
                    }
                    break;
            }
            refresh(progress);
        }

        public void bind() {
            Progress progress = task.progress;
            DailyVideos.IssueListBean.ItemListBean.DataBean apk = (DailyVideos.IssueListBean.ItemListBean.DataBean) progress.extra1;
            if (apk != null) {
                Glide.with(context).load(apk.getCover().getFeed()).into(binding.ivImage);
                binding.tvTitle.setText(apk.getTitle());
            }
        }

        public void refresh(Progress progress) {
            String currentSize = Formatter.formatFileSize(context, progress.currentSize);
            String totalSize = Formatter.formatFileSize(context, progress.totalSize);
            binding.tvSize.setText(currentSize + "/" + totalSize);
            switch (progress.status) {
                case Progress.NONE:
                    binding.netSpeed.setText("停止");
                    //download.setText("下载");
                    break;
                case Progress.PAUSE:
                    binding.netSpeed.setText("暂停中");
                    //download.setText("继续");
                    break;
                case Progress.ERROR:
                    binding.netSpeed.setText("下载出错");
                    //download.setText("出错");
                    break;
                case Progress.WAITING:
                    binding.netSpeed.setText("等待中");
                    //download.setText("等待");
                    break;
                case Progress.FINISH:
                    binding.netSpeed.setText("下载完成");
                    //download.setText("完成");
                    break;
                case Progress.LOADING:
                    String speed = Formatter.formatFileSize(context, progress.speed);
                    binding.netSpeed.setText(String.format("%s/s", speed));
                    //download.setText("暂停");
                    break;
            }

            binding.ProgressBar.setMax(10000);
            binding.ProgressBar.setProgress((int) (progress.fraction * 10000));
        }
    }

    public void unRegister() {
        Map<String, DownloadTask> taskMap = OkDownload.getInstance().getTaskMap();
        for (DownloadTask task : taskMap.values()) {
            task.unRegister(createTag(task));
        }
    }

    private String createTag(DownloadTask task) {
        return type + "_" + task.progress.tag;
    }

    private class ListDownloadListener extends DownloadListener {

        private DownloadHolder holder;

        ListDownloadListener(Object tag, DownloadHolder holder) {
            super(tag);
            this.holder = holder;
        }

        @Override
        public void onStart(Progress progress) {
        }

        @Override
        public void onProgress(Progress progress) {
            if (tag == holder.getTag()) {
                holder.refresh(progress);
            }
        }

        @Override
        public void onError(Progress progress) {
            Throwable throwable = progress.exception;
            if (throwable != null) throwable.printStackTrace();
        }

        @Override
        public void onFinish(File file, Progress progress) {
            Toast.makeText(context, "下载完成:" + progress.filePath, Toast.LENGTH_SHORT).show();
            updateData(type);
        }

        @Override
        public void onRemove(Progress progress) {
        }
    }
}
