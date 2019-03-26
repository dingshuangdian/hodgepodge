package com.lsqidsd.hodgepodge.fragment;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.base.BaseLazyFragment;
import com.lsqidsd.hodgepodge.databinding.MineFragmentBinding;

public class MineFragment extends BaseLazyFragment {
    private MineFragmentBinding fragmentBinding;


    @Override
    public void initFragment() {
        fragmentBinding = (MineFragmentBinding) setBinding(fragmentBinding);

    }

    @Override
    public void initData() {

    }

    @Override
    public int setContentView() {
        return R.layout.fragment_mine;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void lazyLoad() {
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
