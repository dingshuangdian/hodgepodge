package com.lsqidsd.hodgepodge.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lsqidsd.hodgepodge.http.download.DownloadConstant;
import com.lsqidsd.hodgepodge.http.download.FileInfo;

public class DownBroadcastReceiver extends BroadcastReceiver {

    private static final String DOWN_ACTION = "download";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null != intent) {
            switch (intent.getAction()) {
                case DOWN_ACTION:
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
}
