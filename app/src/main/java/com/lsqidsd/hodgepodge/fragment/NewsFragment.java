package com.lsqidsd.hodgepodge.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.BaseFragmentAdapter;
import com.lsqidsd.hodgepodge.databinding.BaseFragmentBinding;
import com.lsqidsd.hodgepodge.databinding.NewsFragmentBinding;
import com.lsqidsd.hodgepodge.fragment.base.MyBaseFragment;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private NewsFragmentBinding fragmentBinding;
    private String[] topTip = {"要闻", "视频", "推荐", "新时代", "军事", "法制", "国际", "电影"};
    private List<Fragment> fragmentArrayList = new ArrayList<>();
    private BaseFragmentBinding baseFragmentBinding;
    private BaseFragmentAdapter basePagerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);
        initFlexTitle();
        return fragmentBinding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    private void initFlexTitle() {
        fragmentBinding.tabTop.vt.initData(topTip, fragmentBinding.viewpager, 0);
        if (fragmentArrayList != null) {
            fragmentArrayList.clear();
        }
        for (int i = 0; i < topTip.length; i++) {
            fragmentArrayList.add(MyBaseFragment.newInstance(topTip[i]));
        }
        basePagerAdapter = new BaseFragmentAdapter(getChildFragmentManager(), fragmentArrayList);
        fragmentBinding.viewpager.setAdapter(basePagerAdapter);
    }
}
