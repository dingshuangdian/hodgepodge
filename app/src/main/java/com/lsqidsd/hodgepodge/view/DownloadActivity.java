package com.lsqidsd.hodgepodge.view;

import android.support.v7.widget.LinearLayoutManager;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.DownloadAdapter;
import com.lsqidsd.hodgepodge.base.BaseActivity;
import com.lsqidsd.hodgepodge.databinding.ActivityDownloadBinding;
import com.lsqidsd.hodgepodge.http.downserver.OkDownload;
import com.lsqidsd.hodgepodge.http.downserver.task.XExecutor;
import com.lsqidsd.hodgepodge.utils.ToastUtils;

public class DownloadActivity extends BaseActivity implements XExecutor.OnAllTaskEndListener {
    private ActivityDownloadBinding binding;
    private DownloadAdapter adapter;
    private OkDownload okDownload;

    @Override
    public int getLayout() {
        return R.layout.activity_download;
    }

    @Override
    public void initView() {
        binding = getBinding(binding);
        adapter = new DownloadAdapter(this);
        okDownload = OkDownload.getInstance();
        adapter.updateData(DownloadAdapter.TYPE_ALL);
        binding.recyview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyview.setAdapter(adapter);
        okDownload.addOnAllTaskEndListener(this);
    }

    @Override
    public void onAllTaskEnd() {
        ToastUtils.showToast(this, "所有下载任务已结束");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        okDownload.removeOnAllTaskEndListener(this);
        adapter.unRegister();
    }
}
