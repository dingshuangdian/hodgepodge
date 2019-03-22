package com.lsqidsd.hodgepodge.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.base.BaseLazyFragment;
import com.lsqidsd.hodgepodge.databinding.MineFragmentBinding;
import com.lsqidsd.hodgepodge.http.RxHttpManager;
import com.lsqidsd.hodgepodge.http.download.DaoUtil;
import com.lsqidsd.hodgepodge.http.download.Info;
import com.lsqidsd.hodgepodge.viewmodel.MineViewModule;

import java.io.File;
import java.util.List;

public class MineFragment extends BaseLazyFragment {
    private MineFragmentBinding fragmentBinding;
    private List<Info> infos;
    private DaoUtil daoUtil;
    @Override
    public void initFragment() {
        fragmentBinding = (MineFragmentBinding) setBinding(fragmentBinding);

    }
    @Override
    public void initData() {
        daoUtil = DaoUtil.getInstance();
    }
    @Override
    public int setContentView() {
        return R.layout.fragment_mine;
    }
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void lazyLoad() {
        infos = daoUtil.queryDownAll();
        if (infos.isEmpty()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "01.mp4");
            Info info = new Info(BaseConstant.UPDATA_URL);
            info.setId(1);
            info.setSavePath(file.getAbsolutePath());
            daoUtil.save(info);
            infos = daoUtil.queryDownAll();
        }
        fragmentBinding.setMineview(new MineViewModule(infos.get(0), getContext(), fragmentBinding));
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
}
