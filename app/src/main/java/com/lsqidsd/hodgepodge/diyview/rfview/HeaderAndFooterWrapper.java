package com.lsqidsd.hodgepodge.diyview.rfview;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.adapter.VideoViewAdapter;
import com.lsqidsd.hodgepodge.adapter.YWAdapter;

public class HeaderAndFooterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int BASE_ITEM_TYPE_HEADER = 10000;
    private static final int BASE_ITEM_TYPE_FOOTER = 20000;
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    private RecyclerView.Adapter mInnerAdapter;
    public HeaderAndFooterWrapper(RecyclerView.Adapter mInnerAdapter) {
        this.mInnerAdapter = mInnerAdapter;
    }
    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }
    private int getHeadersCount() {
        return mHeaderViews.size();
    }

    private int getFootersCount() {
        return mFooterViews.size();
    }
    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }
    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }
    public void addHeaderView(View v) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, v);
    }
    public void addFooterView(View v) {
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, v);
    }
    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderViews.get(viewType) != null) {
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
            return holder;
        } else if (mFooterViews.get(viewType) != null) {
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), mFooterViews.get(viewType));
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        } else if (isFooterViewPos(position)) {
            return;
        }
        if (mInnerAdapter instanceof YWAdapter) {
            switch (position) {
                case 1:
                    mInnerAdapter.onBindViewHolder(holder, 0);
                    break;
                case 2:
                    mInnerAdapter.onBindViewHolder(holder, 1);
                    break;
                case 3:
                    mInnerAdapter.onBindViewHolder(holder, 2);
                    break;
                default:
                    mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount() - 3);
                    break;
            }
        }
        if (mInnerAdapter instanceof VideoViewAdapter) {
            mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
        }
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//      
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int itemViewType = getItemViewType(position);
                if (mHeaderViews.get(itemViewType) != null) {
                    return layoutManager.getSpanCount();
                } else if (mFooterViews.get(itemViewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            WrapperUtils.setFullSpan(holder);
        }
    }
}
