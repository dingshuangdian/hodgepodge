package com.lsqidsd.hodgepodge.fragment;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.VideoListAdapter;
import com.lsqidsd.hodgepodge.api.FeedScrollListener;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.base.BaseLazyFragment;
import com.lsqidsd.hodgepodge.bean.DailyVideos;
import com.lsqidsd.hodgepodge.databinding.VideosListFragmentBinding;
import com.lsqidsd.hodgepodge.diyview.videoview.JZMediaManager;
import com.lsqidsd.hodgepodge.diyview.videoview.Jzvd;
import com.lsqidsd.hodgepodge.diyview.videoview.JzvdMgr;
import com.lsqidsd.hodgepodge.diyview.videoview.MyJzvdStd;
import com.lsqidsd.hodgepodge.http.downserver.OkDownload;
import com.lsqidsd.hodgepodge.http.downserver.db.DownloadManager;
import com.lsqidsd.hodgepodge.http.downserver.model.Progress;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;
import java.util.ArrayList;
import java.util.List;
public class VideolistFragment extends BaseLazyFragment implements InterfaceListenter.VideosLoadFinish {
    private VideosListFragmentBinding videosFragmentBinding;
    private VideoListAdapter adapter;
    private List<DailyVideos.IssueListBean.ItemListBean> videosList = new ArrayList<>();
    private static VideolistFragment videolistFragment;
    private static final String DOWN_ACTION = "download";

    public static VideolistFragment getInstance(int i) {
        videolistFragment = new VideolistFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag", i);
        videolistFragment.setArguments(bundle);
        return videolistFragment;
    }

    @Override
    public void initFragment() {
        videosFragmentBinding = (VideosListFragmentBinding) setBinding(videosFragmentBinding);
        OkDownload.getInstance().setFolder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/hodge/");
        OkDownload.getInstance().getThreadPool().setCorePoolSize(5);
        //从数据库中恢复数据
        List<Progress> progressList = DownloadManager.getInstance().getAll();
        OkDownload.restore(progressList);
    }

    @Override
    public void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        videosFragmentBinding.recyview.setLayoutManager(linearLayoutManager);
        adapter = new VideoListAdapter(getContext(), videosFragmentBinding.refreshLayout);
        videosFragmentBinding.recyview.addOnScrollListener(new FeedScrollListener());
        videosFragmentBinding.recyview.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                //子view显示界面调用
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                //子view离开界面调用
                Jzvd jzvd = view.findViewById(R.id.video);
                if (jzvd != null && jzvd.jzDataSource.containsTheUrl(JZMediaManager.getCurrentUrl())) {
                    Jzvd currentJzvd = JzvdMgr.getCurrentJzvd();
                    if (currentJzvd != null && currentJzvd.currentScreen != Jzvd.SCREEN_WINDOW_FULLSCREEN) {
                        Jzvd.releaseAllVideos();
                    }
                }
            }
        });
    }

    @Override
    public int setContentView() {
        return R.layout.videolist_fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void lazyLoad() {
        initRefresh();
    }

    private void loadData() {
        Bundle bundle = getArguments();
        switch (bundle.getInt("flag")) {
            case 0:
                HttpModel.getDailyVideos(videosList, this::videosLoadFinish, videosFragmentBinding.refreshLayout, BaseConstant.VIDEO_URL, "refresh");
                break;
            case 1:
                HttpModel.getDailyVideos(videosList, this::videosLoadFinish, videosFragmentBinding.refreshLayout, BaseConstant.VIDEO_URL, "refresh");
                break;
            case 2:
                HttpModel.getDailyVideos(videosList, this::videosLoadFinish, videosFragmentBinding.refreshLayout, BaseConstant.VIDEO_URL, "refresh");
                break;
        }
    }

    @Override
    public void videosLoadFinish(List<DailyVideos.IssueListBean.ItemListBean> beans, String url) {
        videosList = beans;
        adapter.addVideos(beans, url);
        videosFragmentBinding.recyview.setAdapter(adapter);
    }

    private void initRefresh() {
        videosFragmentBinding.refreshLayout.setOnRefreshListener(a -> loadData());
        videosFragmentBinding.refreshLayout.autoRefresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
        MyJzvdStd.AUTOPLAY = false;
    }
}
