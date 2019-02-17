package com.lsqidsd.hodgepodge.diyview.rfview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lsqidsd.hodgepodge.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by txy on 2016/10/19.
 */

public class RefreshRecyclerView extends RecyclerView {
    private boolean isFristLayout = true;
    private final Context mContext;
    private float mDownX;
    private float mDownY;
    private static int sHeaderViewMeasuredHeight;
    private float mDx;
    private float mDy;
    //    顶部视图，下拉刷新控件
    private View headerView;
    //    正在刷新状态的进度条
    private ProgressBar pb_header_refresh;
    //    刷新箭头
    private ImageView iv_header_refresh;
    //    显示刷新状态
    private TextView tv_status;
    //    显示最近一次的刷新时间
    private TextView tv_time;
    //    转到下拉刷新状态时的动画
    private RotateAnimation downAnima;
    //     转到释放刷新状态时的动画
    private RotateAnimation upAnima;
    //触摸事件中按下的Y坐标，初始值为-1，为防止ACTION_DOWN事件被抢占
    private float startY = -1;
    //    下拉刷新控件的高度
    private int pulldownHeight;
    //    刷新状态：下拉刷新
    private final int PULL_DOWN_REFRESH = 0;
    //    刷新状态：释放刷新
    private final int RELEASE_REFRESH = 1;
    //    刷新状态：正常刷新
    private final int REFRESHING = 2;

    //pull-down refresh data callback
    private IPullRefresh mPullRefresh;

    //slide-up loading data callback
    private IPushRefresh mPushRefresh;
    private RefreshRecyclerView recyclerView;
    private int mHeaderHeight = -1;
    //the height of tip view
    private boolean mIsDrag = false;
    private boolean mLoadMore = false;
    private float mInitY = -1f;
    private int mTouchSlop;
    private boolean mRefresh = false;
    private float mCurrentTargetOffsetTop;
    protected int mOriginalOffsetTop;
    //    当前头布局的状态-默认为下拉刷新
    private int currState = PULL_DOWN_REFRESH;
    //
    //    尾部视图
    private View footerView;
    //    尾部试图（上拉加载控件）的高度
    private int footerViewHeight;
    //    判断是否是加载更多
    private boolean isLoadingMore;
    /**
     * 轮播图对象，在
     */
    private boolean isPullRefresh;
//    private View secondHeaderView;
    /**
     * ListView在Y轴上的坐标
     */
    private int mListViewOnScreen = -1;
    public RefreshRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public RefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initHeaderView();
    }
    /**
     * 返回尾部布局，供外部调用
     *
     * @return
     */
    public View getFooterView() {
        return footerView;
    }
    /**
     * 返回头部布局，供外部调用
     *
     * @return
     */
    public View getHeaderView() {
        return headerView;
    }
    /**
     * 通过HeaderAndFooterWrapper对象给RecyclerView添加尾部
     *
     * @param footerView             尾部视图
     * @param headerAndFooterWrapper RecyclerView.Adapter的包装类对象，通过它给RecyclerView添加尾部视图
     */
    public void addFooterView(View footerView, HeaderAndFooterWrapper headerAndFooterWrapper) {
        headerAndFooterWrapper.addFooterView(footerView);
    }
    /**
     * 通过HeaderAndFooterWrapper对象给RecyclerView添加头部部
     *
     * @param headerView             尾部视图
     * @param headerAndFooterWrapper RecyclerView.Adapter的包装类对象，通过它给RecyclerView添加头部视图
     */
    public void addHeaderView(View headerView, HeaderAndFooterWrapper headerAndFooterWrapper) {
        headerAndFooterWrapper.addHeaderView(headerView);
    }
    private void initFooterView() {
        footerView = View.inflate(mContext, R.layout.refresh_recyclerview_footer, null);
        footerView.measure(0, 0);
        //得到控件的高
        footerViewHeight = footerView.getMeasuredHeight();
        //默认隐藏下拉刷新控件
        // View.setPadding(0,-控件高，0,0);//完全隐藏
        //View.setPadding(0, 0，0,0);//完全显示
        footerView.setPadding(0, -footerViewHeight, 0, 0);
//        addFooterView(footerView);
//        自己监听自己
    }
    private void initHeaderView() {
        headerView = View.inflate(mContext, R.layout.refresh_recyclerview_header, null);
        tv_time = headerView.findViewById(R.id.tv_time);
        tv_status = headerView.findViewById(R.id.tv_status);
        iv_header_refresh = headerView.findViewById(R.id.iv_header_refresh);
        pb_header_refresh = headerView.findViewById(R.id.pb_header_refresh);
    }



















}
