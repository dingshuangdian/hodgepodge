package com.lsqidsd.hodgepodge.fragment.news;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.VideoViewAdapter;
import com.lsqidsd.hodgepodge.adapter.YWAdapter;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import com.lsqidsd.hodgepodge.databinding.InformationDataBinding;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;
import java.util.ArrayList;
import java.util.List;
public class InformationFragment extends Fragment implements InterfaceListenter.MainNewsDataListener, InterfaceListenter.VideosDataListener {
    private InformationDataBinding fragmentBinding;
    private InterfaceListenter.VideosDataListener videosDataListener;
    private InterfaceListenter.MainNewsDataListener newsDataListener;
    private static InformationFragment informationFragment;
    private List<NewsVideoItem.DataBean> videosList = new ArrayList<>();

    public static InformationFragment getInstance(int i) {
        informationFragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag", i);
        informationFragment.setArguments(bundle);
        return informationFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.information_fragment, container, false);
        fragmentBinding.swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#98a2a9"));
        videosDataListener = this;
        newsDataListener = this;
        return fragmentBinding.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        changeFragment();
        fragmentBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentBinding.swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (fragmentBinding.swipeRefreshLayout != null && fragmentBinding.swipeRefreshLayout.isRefreshing()) {
                            fragmentBinding.swipeRefreshLayout.setRefreshing(false);
                        }

                    }
                }, 1000);
            }
        });
    }

    private void changeFragment() {

        Bundle bundle = getArguments();
        switch (bundle.getInt("flag")) {
            case 0:
                HttpModel.getTopNews(newsDataListener, new NewsMain());
                break;
            case 1:
                HttpModel.getVideoList(0, videosDataListener, videosList);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
        }
    }

    @Override
    public void mainDataChange(NewsMain dataBeans) {
        YWAdapter ywAdapter = new YWAdapter(getContext(), dataBeans);
        fragmentBinding.recyview.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentBinding.recyview.setAdapter(ywAdapter);
    }

    @Override
    public void videoDataChange(List<NewsVideoItem.DataBean> dataBean) {
        VideoViewAdapter viewAdapter = new VideoViewAdapter(dataBean, getContext());
        fragmentBinding.recyview.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentBinding.recyview.setAdapter(viewAdapter);

    }
}
