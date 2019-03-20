package com.lsqidsd.hodgepodge.fragment;

import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;

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
    private Info info;
    private DaoUtil daoUtil;
    private MineViewModule module;
    private RxHttpManager rxHttpManager;

    @Override
    public void initFragment() {
        fragmentBinding = (MineFragmentBinding) setBinding(fragmentBinding);

    }

    @Override
    public void initData() {
        daoUtil = DaoUtil.getInstance();
        rxHttpManager = RxHttpManager.getInstance();
        fragmentBinding.setMineview(module);

    }

    @Override
    public int setContentView() {
        return R.layout.fragment_mine;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void lazyLoad() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "01.mp4");
        info = new Info(BaseConstant.UPDATA_URL);
        info.setId(1);
        info.setSavePath(file.getAbsolutePath());
        daoUtil.save(info);
        module = new MineViewModule(info, getContext());
        rxHttpManager.down(info);
    }

    @Override
    public void onStart() {
        super.onStart();

    }


}
