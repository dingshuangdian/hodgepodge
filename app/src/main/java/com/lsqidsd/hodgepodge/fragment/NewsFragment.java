package com.lsqidsd.hodgepodge.fragment;
import android.support.v4.app.Fragment;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.BaseFragmentAdapter;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.base.BaseLazyFragment;
import com.lsqidsd.hodgepodge.databinding.NewsFragmentBinding;
import com.lsqidsd.hodgepodge.utils.TabDb;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;
import java.util.ArrayList;
import java.util.List;
public class NewsFragment extends BaseLazyFragment implements InterfaceListenter.HasFinish {
    private NewsFragmentBinding fragmentBinding;
    private List<Fragment> fragmentArrayList = new ArrayList<>();
    private List<String> top = new ArrayList<>();
    private BaseFragmentAdapter basePagerAdapter;
    private InterfaceListenter.HasFinish hasFinish;

    @Override
    public int setContentView() {
        return R.layout.fragment_news;
    }

    @Override
    public void lazyLoad() {
       new Thread(() -> HttpModel.getHotKey(hasFinish, top)).start();
    }

    @Override
    public void initFragment() {
        fragmentBinding = (NewsFragmentBinding) setBinding(fragmentBinding);
        fragmentBinding.tabTop.vt.initView(TabDb.getTopsTxt(), 0);
    }

    @Override
    public void initData() {
        hasFinish = this;
        initFlexTitle();
    }

    private void initFlexTitle() {
        fragmentBinding.tabTop.vt.initData(fragmentBinding.viewpager);
        if (fragmentArrayList != null) {
            fragmentArrayList.clear();
        }
        for (int i = 0; i < 9; i++) {
            fragmentArrayList.add(InformationFragment.newInstance(i));
        }
        basePagerAdapter = new BaseFragmentAdapter(getChildFragmentManager(), fragmentArrayList);
        fragmentBinding.viewpager.setOffscreenPageLimit(0);
        fragmentBinding.viewpager.setAdapter(basePagerAdapter);
    }

    @Override
    public void hasFinish(List<String> top) {
        fragmentBinding.searchview.setTopRoll(top);
    }
}
