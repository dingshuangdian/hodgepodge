package com.lsqidsd.hodgepodge.diyview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class SlideViewPager extends ViewPager {

    public SlideViewPager(Context context) {
        super(context);
    }

    public SlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    /**
     * 外层ViewPager复写canScroll方法，防止滑动冲突.这种处理同样适用于ScrollView等其他滑动控件。
     *
     * @param v
     * @param checkV
     * @param dx
     * @param x
     * @param y
     * @return
     */
    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v != this && v instanceof ViewPager) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}