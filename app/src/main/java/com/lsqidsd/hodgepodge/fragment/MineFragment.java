package com.lsqidsd.hodgepodge.fragment;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.base.BaseLazyFragment;
import com.lsqidsd.hodgepodge.databinding.MineFragmentBinding;
import com.lsqidsd.hodgepodge.http.download.DaoUtil;
import com.lsqidsd.hodgepodge.http.download.DownloadConstant;
import com.lsqidsd.hodgepodge.http.download.DownloadHelper;
import com.lsqidsd.hodgepodge.http.download.FileInfo;
import com.lsqidsd.hodgepodge.http.download.Info;

import java.io.File;
import java.util.List;

public class MineFragment extends BaseLazyFragment {
    private MineFragmentBinding fragmentBinding;
    private List<Info> infos;
    private static final String FIRST_ACTION = "download_helper_first_action";
    private File dir;
    private DaoUtil daoUtil;
    private DownloadHelper downloadHelper;
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
    public void initFragment() {
        fragmentBinding = (MineFragmentBinding) setBinding(fragmentBinding);

    }

    @Override
    public void initData() {
        daoUtil = DaoUtil.getInstance();
        downloadHelper = DownloadHelper.getInstance();
        IntentFilter filter = new IntentFilter();
        filter.addAction(FIRST_ACTION);
        getContext().registerReceiver(receiver, filter);

    }

    @Override
    public int setContentView() {
        return R.layout.fragment_mine;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void lazyLoad() {
        downloadHelper.addTask(BaseConstant.UPDATA_URL, new File(getDir(), "news.apk"), FIRST_ACTION).submit(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private File getDir() {
        if (dir != null && dir.exists()) {
            return dir;
        }

        dir = new File(getContext().getExternalCacheDir(), "download");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
}
