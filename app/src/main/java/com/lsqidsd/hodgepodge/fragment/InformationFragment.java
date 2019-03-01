package com.lsqidsd.hodgepodge.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.CategoriesAdapter;
import com.lsqidsd.hodgepodge.adapter.VideoViewAdapter;
import com.lsqidsd.hodgepodge.adapter.YWAdapter;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.base.BaseLazyFragment;
import com.lsqidsd.hodgepodge.bean.Milite;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import com.lsqidsd.hodgepodge.databinding.InformationDataBinding;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;

import java.util.ArrayList;
import java.util.List;

public class InformationFragment extends BaseLazyFragment implements InterfaceListenter.MainNewsDataListener, InterfaceListenter.VideosDataListener, InterfaceListenter.LoadCategoriesNews {
    private InformationDataBinding fragmentBinding;
    private YWAdapter ywAdapter;
    private CategoriesAdapter categoriesAdapter;
    private VideoViewAdapter viewAdapter;
    private List<Milite.DataBean> beans = new ArrayList<>();
    private List<NewsVideoItem.DataBean> videosList;

    public static InformationFragment newInstance(int i) {
        InformationFragment newFragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key", i);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    private void initRefresh() {
        fragmentBinding.refreshLayout.setOnRefreshListener(a -> loadData());
        fragmentBinding.refreshLayout.autoRefresh();
    }

    @Override
    public void initFragment() {
        fragmentBinding = (InformationDataBinding) setBinding(fragmentBinding);
    }

    @Override
    public void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentBinding.recyview.setLayoutManager(linearLayoutManager);
        categoriesAdapter = new CategoriesAdapter(getContext(), fragmentBinding.refreshLayout);
        viewAdapter = new VideoViewAdapter(getContext(), fragmentBinding.refreshLayout);
        ywAdapter = new YWAdapter(getContext(), fragmentBinding.refreshLayout);

    }

    @Override
    public int setContentView() {
        return R.layout.information_fragment;
    }

    @Override
    public void lazyLoad() {
        initRefresh();
    }

    private void loadData() {
        switch (getArguments().getInt("key")) {
            case 0:
                HttpModel.getMainNewData(this::mainDataChange, fragmentBinding.refreshLayout);
                break;
            case 1:
                videosList = new ArrayList<>();
                HttpModel.getVideoList(1, this::videoDataChange, videosList, fragmentBinding.refreshLayout);
                break;
            case 2:
                HttpModel.getCategoriesNews(1, BaseConstant.getRecommend(), this::loadCategoriesNewsFinish, beans, fragmentBinding.refreshLayout, "rec");
                break;
            case 3:
                HttpModel.getCategoriesNews(1, BaseConstant.getEntParams(), this::loadCategoriesNewsFinish, beans, fragmentBinding.refreshLayout, "ent");
                break;
            case 4:
                HttpModel.getCategoriesNews(1, BaseConstant.getMiliteParams(), this::loadCategoriesNewsFinish, beans, fragmentBinding.refreshLayout, "milite");
                break;
            case 5:
                HttpModel.getCategoriesNews(1, BaseConstant.getHistory(), this::loadCategoriesNewsFinish, beans, fragmentBinding.refreshLayout, "history");
                break;
            case 6:
                HttpModel.getCategoriesNews(1, BaseConstant.getWorldParams(), this::loadCategoriesNewsFinish, beans, fragmentBinding.refreshLayout, "world");
                break;
            case 7:

                HttpModel.getCategoriesNews(1, BaseConstant.getFinanceParams(), this::loadCategoriesNewsFinish, beans, fragmentBinding.refreshLayout, "finance");
                break;
            case 8:
                HttpModel.getCategoriesNews(1, BaseConstant.getCul(), this::loadCategoriesNewsFinish, beans, fragmentBinding.refreshLayout, "cul");
                break;

        }
    }

    @Override
    public void mainDataChange(NewsMain dataBeans) {
        ywAdapter.addDatas(dataBeans);
        fragmentBinding.recyview.setAdapter(ywAdapter);
    }

    @Override
    public void videoDataChange(List<NewsVideoItem.DataBean> dataBean) {
        viewAdapter.addVideos(dataBean);
        fragmentBinding.recyview.setAdapter(viewAdapter);
    }

    @Override
    public void loadCategoriesNewsFinish(List<Milite.DataBean> dataBeans, String s) {
        categoriesAdapter.addMilite(dataBeans, s);
        fragmentBinding.recyview.setAdapter(categoriesAdapter);
    }
}
