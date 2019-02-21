package com.lsqidsd.hodgepodge.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.VideoListAdapter;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.bean.DailyVideos;
import com.lsqidsd.hodgepodge.databinding.VideosFragmentBinding;
import com.lsqidsd.hodgepodge.utils.ScreenUtils;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;

import java.util.ArrayList;
import java.util.List;
public class VideosFragment extends Fragment implements InterfaceListenter.VideosLoadFinish {
    private VideosFragmentBinding videosFragmentBinding;
    //用户手动点击播放后，自动播放开始，
    // 除非用户手动点击停止，或者视频播放完毕，停止自动播放，
    private boolean isLooper = false;
    private int looperFlag = 0;//0,无自动播放，1.自动播放上一个，2自动播放下一个
    private int position_play = -1;//播放的位置
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        videosFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_videos, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        videosFragmentBinding.recyview.setLayoutManager(linearLayoutManager);
        initRefresh();
        //videosFragmentBinding.recyview.addOnScrollListener(scrollListener);
        return videosFragmentBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initRefresh() {
        videosFragmentBinding.refreshLayout.setOnRefreshListener(a -> loadData());
        videosFragmentBinding.refreshLayout.autoRefresh();
    }
    private void loadData() {
        List<DailyVideos.IssueListBean.ItemListBean> videosList = new ArrayList<>();
        HttpModel.getDailyVideos(videosList, this::videosLoadFinish, videosFragmentBinding.refreshLayout, BaseConstant.VIDEO_URL, "first");
    }
    @Override
    public void videosLoadFinish(List<DailyVideos.IssueListBean.ItemListBean> beans, String url) {
        VideoListAdapter adapter = new VideoListAdapter(beans, getContext(), videosFragmentBinding.refreshLayout, url);
        videosFragmentBinding.recyview.setAdapter(adapter);
    }
    private OnScrollListener scrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && isLooper && looperFlag != 0) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) videosFragmentBinding.recyview.getLayoutManager();
                switch (looperFlag) {
                    case 1:
                        int position_lastVisible = linearLayoutManager.findLastVisibleItemPosition();
                        if (position_lastVisible == position_play) {
                            //自动播放上一个
                            position_play -= 1;
                        } else {
                            //最后一个可见的item和滑出去的上次播放的view隔了N(N>=1)个Item,所以自动播放倒数第2个可见的item
                            position_play = position_lastVisible - 1;
                        }
                        break;

                    case 2:
                        int position_firstVisible = linearLayoutManager.findFirstVisibleItemPosition();
                        if (position_firstVisible == position_play) {
                            //自动播放下一个
                            position_play += 1;

                        } else {
                            //第一个可见的item和滑出去的上次播放的view隔了N(N>=1)个Item,所以自动播放第2个可见的item
                            position_play = position_firstVisible + 1;
                        }
                        break;
                }

                //注意
                looperFlag = 0;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (!isLooper) return;
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) videosFragmentBinding.recyview.getLayoutManager();
            View view = linearLayoutManager.findViewByPosition(position_play);
            //说明播放的view还未完全消失
            if (view != null) {
                int y_t_rv = ScreenUtils.getViewScreenLocation(videosFragmentBinding.recyview)[1];//RV顶部Y坐标
                int y_b_rv = y_t_rv + videosFragmentBinding.recyview.getHeight();//RV底部Y坐标
                int y_t_view = ScreenUtils.getViewScreenLocation(view)[1];//播放的View顶部Y坐标
                int height_view = view.getHeight();
                int y_b_view = y_t_view + height_view;//播放的View底部Y坐标
                //上滑
                if (dy > 0) {
                    //播放的View上滑，消失了一半了，停止播放
                    if ((y_t_rv > y_t_view) && ((y_t_rv - y_t_view) > height_view * 1f / 2)) {
                        looperFlag = 2;//自动播放下一个
                    }
                } else if (dy < 0) {
                    //播放的View下滑，消失了一半了,停止播放
                    if ((y_b_view > y_b_rv) && ((y_b_view - y_b_rv) > height_view * 1f / 2)) {
                        looperFlag = 1;//自动播放上一个
                    }
                }
            }
        }
    };
}
