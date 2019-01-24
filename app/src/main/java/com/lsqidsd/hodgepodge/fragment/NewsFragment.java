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
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.bean.CategoriesBean;
import com.lsqidsd.hodgepodge.fragment.news.InformationFragment;
import com.lsqidsd.hodgepodge.databinding.NewsFragmentBinding;
import com.lsqidsd.hodgepodge.utils.CategoriesUtils;
import com.lsqidsd.hodgepodge.viewmodel.NewsViewModule;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements NewsViewModule.HasFinish {
    private NewsFragmentBinding fragmentBinding;
    private List<Fragment> fragmentArrayList = new ArrayList<>();
    private BaseFragmentAdapter basePagerAdapter;
    private NewsViewModule newsViewModule;
    private NewsViewModule.HasFinish hasFinish;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);
        initFlexTitle();
        hasFinish = this;
        return fragmentBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newsViewModule = new NewsViewModule(getActivity());
        fragmentBinding.setNewsview(newsViewModule);
        new Thread(new Runnable() {
            @Override
            public void run() {
                newsViewModule.getHotKey(hasFinish);
            }
        }).start();
    }

    private void initFlexTitle() {
        fragmentBinding.tabTop.vt.initData(CategoriesUtils.getCategories(), fragmentBinding.viewpager, 0);
        if (fragmentArrayList != null) {
            fragmentArrayList.clear();
        }
        fragmentArrayList.add(InformationFragment.getInstance());
        basePagerAdapter = new BaseFragmentAdapter(getChildFragmentManager(), fragmentArrayList);
        fragmentBinding.viewpager.setAdapter(basePagerAdapter);
    }

    @Override
    public void hasFinish(List<String> top) {
        fragmentBinding.searchview.setTopRoll(top);
    }
}
