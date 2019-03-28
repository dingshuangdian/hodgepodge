package com.lsqidsd.hodgepodge.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.base.BaseActivity;
import com.lsqidsd.hodgepodge.databinding.ActivityDownloadBinding;
import com.lsqidsd.hodgepodge.http.download.DownloadConstant;
import com.lsqidsd.hodgepodge.http.download.DownloadHelper;
import com.lsqidsd.hodgepodge.http.download.FileInfo;

import java.io.File;

public class DownloadActivity extends BaseActivity {

    private DownloadHelper downloadHelper;
    private ActivityDownloadBinding binding;


    @Override
    public int getLayout() {
        return R.layout.activity_download;
    }

    @Override
    public void initView() {
        String[] url = getIntent().getStringArrayExtra("extra");
        downloadHelper = DownloadHelper.getInstance();
        binding = getBinding(binding);
        //binding.recyview.setLayoutManager(new LinearLayoutManager(this));
    }

}
