package com.lsqidsd.hodgepodge.diyview.rfview;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class RefreashRecyclerView extends RecyclerView {
    private String TAG = "RefreashRecyclerView";
    private RefreashAdapter mRefreashAdapter;
    private View mHeaderView;
    private View mFooterView;
    private boolean isFristLayout = true;
    private float mDownX;
    private float mDownY;
    private static int sHeaderViewMeasuredHeight;
    private float mDx;
    private float mDy;
    public RefreashRecyclerView(Context context) {
        this(context, null);
    }
    public RefreashRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public RefreashRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        isFristLayout = true;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG, "onLayout: " + sHeaderViewMeasuredHeight);
        if (isFristLayout) {
            //将sHeaderViewMeasuredHeight设置为static，只要是为了防止页面间切换出现bug
            if (sHeaderViewMeasuredHeight == 0) {//只有当第一次layout时才会为sHeaderViewMeasuredHeight赋值
                sHeaderViewMeasuredHeight = mHeaderView.getMeasuredHeight();//获取HeaderView的高度
            }
            updateMargin(-sHeaderViewMeasuredHeight);//修改HeaderView的TopMargin值使其隐藏
        }
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mRefreashAdapter = new RefreashAdapter(adapter);
        super.setAdapter(mRefreashAdapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //下拉刷新处理
        if (mHeaderView == getChildAt(0)) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = e.getX();
                    mDownY = e.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float moveX = e.getX();
                    float moveY = e.getY();
                    mDx = moveX - mDownX;
                    mDy = moveY - mDownY;
                    if (Math.abs(mDy) > Math.abs(mDx) && mDy > 0) {
                        if (mDy < 2 * sHeaderViewMeasuredHeight) {//小于两倍height时，逐渐修改TopMargin,使HeaderView逐渐显现出来
                            updateMargin(-sHeaderViewMeasuredHeight + (int) mDy);
                            if (mOnPullDownListener != null) {
                                mOnPullDownListener.onPullDownProgress(mDy / sHeaderViewMeasuredHeight);//将占比返回给监听者
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    float upX = e.getX();
                    float upY = e.getY();
                    mDx = upX - mDownX;
                    mDy = upY - mDownY;
                    if (Math.abs(mDy) > Math.abs(mDx) && mDy > 0 && mHeaderView.getTop() > -sHeaderViewMeasuredHeight) {
                        if (mDy < sHeaderViewMeasuredHeight * 2 / 3) {//小于两倍height时，TopMargin置为-sHeaderViewMeasuredHeight，使其隐藏
                            updateMargin(-sHeaderViewMeasuredHeight);
                        } else {//否则使其完全显现
                            updateMargin(0);
                            if (mOnRefreshListener != null) {
                                //下拉释放后开始刷新
                                mOnRefreshListener.onStartRefresh();//通知监听者开始刷新
                            }
                        }
                    }
                    break;
            }
        }

        //上拉加载处理
        if (mFooterView == getChildAt(getChildCount() - 1)) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = e.getX();
                    mDownY = e.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    float upX = e.getX();
                    float upY = e.getY();
                    mDx = upX - mDownX;
                    mDy = upY - mDownY;
                    if (Math.abs(mDy) > Math.abs(mDx) && mDy < -80) {//上拉加载时，mDy < -80可以通过此处调节加载的灵敏度，值越小上拉所需的距离约大
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMoreStart();//通知监听者开始加载
                        }
                    }
                    break;
            }
        }
        return super.onTouchEvent(e);
    }

    //对滑动过程进行监听，防止滑动到header时其显示出来
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == SCROLL_STATE_SETTLING && mHeaderView == getChildAt(0)) {
            updateMargin(-sHeaderViewMeasuredHeight);
        }
    }


    //修改marginTop控制HeaderView的显示
    private void updateMargin(int marginTop) {
        mHeaderView.setTop(marginTop);//由于单独修改margin，top没变，所以top也要修改
        LayoutParams layoutParams = (LayoutParams) mHeaderView.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, marginTop, layoutParams.rightMargin, layoutParams.bottomMargin);
        mHeaderView.setLayoutParams(layoutParams);
        isFristLayout = false;
    }

    //刷新结束后调用该方法隐藏HeaderView
    public void refreshEnd() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            updateMargin(-sHeaderViewMeasuredHeight);
        } else {
            mHeaderView.post(new Runnable() {
                @Override
                public void run() {
                    updateMargin(-sHeaderViewMeasuredHeight);
                }
            });
        }
    }

    //下拉刷新的进程监听，可以获取到下拉过程中下拉的占比
    private OnPullDownListener mOnPullDownListener;

    public void setOnPullDownListener(OnPullDownListener onPullDownListener) {
        mOnPullDownListener = onPullDownListener;
    }

    public interface OnPullDownListener {
        void onPullDownProgress(float progress);
    }

    //下拉刷新的监听
    private OnRefreshListener mOnRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    public interface OnRefreshListener {
        void onStartRefresh();
    }

    //加载更多监听
    private OnLoadMoreListener mOnLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    //如果没有更多的加载项目可以移除OnLoadMoreListener
    public void removeOnLoadMoreListener() {
        mOnLoadMoreListener = null;
    }

    public interface OnLoadMoreListener {
        void onLoadMoreStart();
    }

    //自定义adapter加入header和footer
    private class RefreashAdapter extends RecyclerView.Adapter {
        private final int HEADER_TYPE = 0;
        private final int NORMAL_TYPE = 1;
        private final int FOOTER_TYPE = 2;
        private Adapter mAdapter;

        public RefreashAdapter(Adapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public int getItemViewType(int position) {
            int type = 0;
            if (position == 0) {
                type = HEADER_TYPE;
            } else if (position == getItemCount() - 1) {
                type = FOOTER_TYPE;
            } else {
                type = NORMAL_TYPE;
            }
            return type;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder;
            switch (viewType) {
                case HEADER_TYPE:
                    holder = new HeaderHolder(mHeaderView);
                    break;
                case FOOTER_TYPE:
                    holder = new FooterHolder(mFooterView);
                    break;
                default:
                    holder = mAdapter.onCreateViewHolder(parent, viewType);
                    break;
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == NORMAL_TYPE) {
                mAdapter.onBindViewHolder(holder, position - 1);
            }
        }

        @Override
        public int getItemCount() {
            return mAdapter.getItemCount() + 2;
        }

        class HeaderHolder extends RecyclerView.ViewHolder {
            public HeaderHolder(View itemView) {
                super(itemView);
            }
        }

        class FooterHolder extends RecyclerView.ViewHolder {
            public FooterHolder(View itemView) {
                super(itemView);
            }
        }
    }
}