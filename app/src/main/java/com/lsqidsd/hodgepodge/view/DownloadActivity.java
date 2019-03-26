package com.lsqidsd.hodgepodge.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.base.BaseActivity;
import com.lsqidsd.hodgepodge.databinding.ActivityDownloadBinding;
import com.lsqidsd.hodgepodge.http.download.DownloadConstant;
import com.lsqidsd.hodgepodge.http.download.DownloadHelper;
import com.lsqidsd.hodgepodge.http.download.FileInfo;

import java.io.File;

public class DownloadActivity extends BaseActivity {
    private File dir;
    private static final String FIRST_ACTION = "ACTION";
    private DownloadHelper downloadHelper;
    private ActivityDownloadBinding binding;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                switch (intent.getAction()) {
                    case FIRST_ACTION:
                        FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD);
                        float pro = (float) (fileInfo.getDownloadLocation() * 1.0 / fileInfo.getSize());
                        int progress = (int) (pro * 100);
                        float downSize = fileInfo.getDownloadLocation() / 1024.0f / 1024;
                        float totalSize = fileInfo.getSize() / 1024.0f / 1024;
                        Log.e("progress", progress + "");
                        Log.e("downSize", downSize + "");
                        Log.e("totalSize", totalSize + "");

                        break;
                }

            }

        }
    };

    @Override
    public int getLayout() {
        return R.layout.activity_download;
    }

    @Override
    public void initView() {
        String[] url = getIntent().getStringArrayExtra("extra");
        downloadHelper = DownloadHelper.getInstance();
        IntentFilter filter = new IntentFilter();
        filter.addAction(FIRST_ACTION);
        registerReceiver(receiver, filter);
        binding = getBinding(binding);
        Log.e("url", url[0]);
        downloadHelper.addTask(url[0], new File(getDir(), "news.apk"), FIRST_ACTION).submit(this);
        //binding.recyview.setLayoutManager(new LinearLayoutManager(this));
    }

    private File getDir() {
        if (dir != null && dir.exists()) {
            return dir;
        }
        dir = new File(getExternalCacheDir(), "download");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
}
