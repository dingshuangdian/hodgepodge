package com.lsqidsd.hodgepodge.diyview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;

public class RefreshRecyclerView extends RecyclerView implements AbsListView.OnScrollListener {
    private View header;// 顶部布局文件；
    private int headerHeight;// 顶部布局文件的高度；
    private int firstVisibleItem;// 当前第一个可见的item的位置；
    private int scrollState;// listview 当前滚动状态；
    private boolean isRemark;// 标记，当前是在listview最顶端摁下的；
    private int startY;// 摁下时的Y值
    private int state;// 当前的状态；
    private final int NONE = 0;// 正常状态；
    private final int PULL = 1;// 提示下拉状态；
    private final int RELEASE = 2;// 提示释放状态；
    private final int REFRESHING = 3;// 刷新状态；
    private LayoutInflater layoutInflater;

    public RefreshRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        layoutInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {


    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

}
