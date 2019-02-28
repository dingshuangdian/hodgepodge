package com.lsqidsd.hodgepodge.fragment;

import android.support.v4.app.Fragment;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.BaseFragmentAdapter;
import com.lsqidsd.hodgepodge.base.BaseLazyFragment;
import com.lsqidsd.hodgepodge.databinding.VideosFragmentBinding;
import com.lsqidsd.hodgepodge.utils.TabDb;
import java.util.ArrayList;
import java.util.List;

public class VideosFragment extends BaseLazyFragment {
    private VideosFragmentBinding fragmentBinding;
    private List<Fragment> fragmentArrayList = new ArrayList<>();
    private BaseFragmentAdapter basePagerAdapter;

    @Override
    public void initFragment() {
        fragmentBinding = (VideosFragmentBinding) setBinding(fragmentBinding);
        fragmentBinding.tab.vt.initView(TabDb.getTopsVideoTxt(), 0);
    }

    @Override
    public void initData() {
        initFlexTitle();
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_videos;
    }

    @Override
    public void lazyLoad() {

    }

    private void initFlexTitle() {
        fragmentBinding.tab.vt.initData(fragmentBinding.viewpager);
        if (fragmentArrayList != null) {
            fragmentArrayList.clear();
        }
        for (int i = 0; i < 3; i++) {
            fragmentArrayList.add(VideolistFragment.getInstance(i));
        }
        basePagerAdapter = new BaseFragmentAdapter(getChildFragmentManager(), fragmentArrayList);
        fragmentBinding.viewpager.setOffscreenPageLimit(0);
        fragmentBinding.viewpager.setAdapter(basePagerAdapter);
    }

}
