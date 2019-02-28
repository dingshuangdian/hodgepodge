package com.lsqidsd.hodgepodge.api;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.diyview.videoview.MyJzvdStd;

public class FeedScrollListener extends RecyclerView.OnScrollListener {
    private int firstVisibleItem = 0;
    private int lastVisibleItem = 0;
    private int visibleCount = 0;

    /**
     * 视频状态标签
     */
    private enum VideoTagEnum {

        /**
         * 自动播放视频
         */
        TAG_AUTO_PLAY_VIDEO,

        /**
         * 暂停视频
         */
        TAG_PAUSE_VIDEO
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                if (MyJzvdStd.AUTOPLAY) {
                    autoPlayVideo(recyclerView, VideoTagEnum.TAG_AUTO_PLAY_VIDEO);
                }
                break;
            default:
                autoPlayVideo(recyclerView, VideoTagEnum.TAG_PAUSE_VIDEO);
                break;
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            firstVisibleItem = linearManager.findFirstVisibleItemPosition();
            lastVisibleItem = linearManager.findLastVisibleItemPosition();
            visibleCount = lastVisibleItem - firstVisibleItem;
        }

    }

    /**
     * 循环遍历 可见区域的播放器
     * 然后通过 getLocalVisibleRect(rect)方法计算出哪个播放器完全显示出来
     * <p>
     * getLocalVisibleRect相关链接：http://www.cnblogs.com/ai-developers/p/4413585.html
     *
     * @param view
     * @param handleVideoTag 视频需要进行状态
     */
    private void autoPlayVideo(RecyclerView view, VideoTagEnum handleVideoTag) {
        for (int i = 0; i < visibleCount; i++) {
            MyJzvdStd homeGSYVideoPlayer = view.getChildAt(i).findViewById(R.id.video);
            Rect rect = new Rect();
            homeGSYVideoPlayer.getLocalVisibleRect(rect);
            int videoheight = homeGSYVideoPlayer.getHeight();
            if (rect.top == 0 && rect.bottom == videoheight) {
                handleVideo(handleVideoTag, homeGSYVideoPlayer);
                break;
            }
        }
    }

    /**
     * 视频状态处理
     *
     * @param handleVideoTag     视频需要进行状态
     * @param homeGSYVideoPlayer JZVideoPlayer播放器
     */
    private void handleVideo(VideoTagEnum handleVideoTag, MyJzvdStd homeGSYVideoPlayer) {
        switch (handleVideoTag) {
            case TAG_AUTO_PLAY_VIDEO:
                if ((homeGSYVideoPlayer.currentState != MyJzvdStd.CURRENT_STATE_PLAYING)) {
                    // 进行播放
                    homeGSYVideoPlayer.startVideo();
                }
                break;
            case TAG_PAUSE_VIDEO:
              /*  if ((homeGSYVideoPlayer.currentState != MyJzvdStd.CURRENT_STATE_PAUSE)) {
                    // 模拟点击播放Button,实现暂停视频
                    homeGSYVideoPlayer.startButton.performClick();
                }*/
                break;
            default:
                break;
        }
    }

}
