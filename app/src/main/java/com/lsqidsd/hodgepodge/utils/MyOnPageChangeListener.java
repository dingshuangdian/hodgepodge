package com.lsqidsd.hodgepodge.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;
import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;
import static com.lsqidsd.hodgepodge.utils.Tool.getScreenWidth;
import static com.lsqidsd.hodgepodge.utils.Tool.getTextViewLength;


/**
 *
 * Created by xiangpan on 2017/8/1.
 */

public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

    private final Context context;
    private ArrayList<TextView> textViews;
    private ViewPagerTitle viewPagerTitle;
    private DynamicLine dynamicLine;
    private float defaultTextSize;//未选择文字大小
    private float selectTextSize;//选择文字大小
    private ViewPager pager;
    private int pagerCount;
    private int screenWidth;
    private int lastPosition;
    private int dis;
    private int[] location = new int[2];
    private float left;
    private float right;
    private boolean titleCenter;
    private boolean hasSeting;
    private boolean lineDrag;
    private float lineMargins;
    private int lineWidth;
    private TextView textView;
    private int lastValue = -1;

    public MyOnPageChangeListener(Context context, ViewPager viewPager, DynamicLine dynamicLine, ViewPagerTitle viewPagerTitle, int margin, float defaultTextSize, float selectTextSize, boolean titleCenter, boolean lineDrag, float lineMargins) {
        this.viewPagerTitle = viewPagerTitle;
        this.pager = viewPager;
        this.dynamicLine = dynamicLine;
        this.context = context;
        this.defaultTextSize = defaultTextSize;
        this.selectTextSize = selectTextSize;
        this.titleCenter = titleCenter;
        this.lineDrag = lineDrag;
        this.lineMargins = lineMargins;
        textViews = viewPagerTitle.getTextView();
        pagerCount = textViews.size();
        screenWidth = getScreenWidth(context);
        dis = margin;
        textView = new TextView(context);
        lineWidth = getDefaultWidth(0);
    }

    private int getDefaultWidth(int i) {
        textView.setTextSize(defaultTextSize);
        if (i >= textViews.size()) {
            i = textViews.size() - 1;
        }
        if (i < 0) {
            i = 0;
        }
        textView.setText(textViews.get(i).getText());
        float defaultTextSize = getTextViewLength(textView);
        return (int) defaultTextSize;
    }

    private int getSelectWidth(int i) {
        textView.setTextSize(defaultTextSize);
        if (i >= textViews.size()) {
            i = textViews.size() - 1;
        }
        if (i < 0) {
            i = 0;
        }
        textView.setText(textViews.get(i).getText());
        float defaultTextSize = getTextViewLength(textView);
        return (int) defaultTextSize;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        int lastWidth;
        int lastDis;
        if (positionOffset > 0) {
            lastWidth = getDefaultWidth(position + 1);
            lastDis = getSelectWidth(position + 1) - getSelectWidth(position);
        } else {
            lastWidth = getDefaultWidth(position);
            lastDis = 0;
        }
        int leftAll = 0;
        int rightAll = 0;
        if (titleCenter) {
            if (lineDrag) {
                if (lastPosition > position) {
                    for (int i = 0; i < position; i++) {
                        leftAll = leftAll + getDefaultWidth(i);
                    }
                    for (int i = 0; i < lastPosition; i++) {
                        rightAll = rightAll + getDefaultWidth(i);
                    }
                    left = leftAll + (position * 2 + 1) * dis + positionOffset * (getDefaultWidth(position) + 2 * dis) + lineMargins;
                    right = rightAll + (lastPosition * 2 + 1) * dis + getDefaultWidth(lastPosition) + lineMargins;
                    dynamicLine.updateView(left, right);
                } else {
                    if (positionOffset > 0.5f) {
                        positionOffset = 0.5f;
                    }
                    for (int i = 0; i < position; i++) {
                        leftAll = leftAll + getDefaultWidth(i);
                    }
                    for (int i = 0; i <= position; i++) {
                        rightAll = rightAll + getDefaultWidth(i);
                    }
                    left = leftAll + (position * 2 + 1) * dis + lineMargins;
                    right = rightAll + (position * 2 + 1) * dis + lineMargins + positionOffset * 2 * (getDefaultWidth(position + 1) + 2 * dis);
                    dynamicLine.updateView(left, right);
                }
            } else {

                if (lastPosition > position) {
                    for (int i = 0; i < position; i++) {
                        leftAll = leftAll + getDefaultWidth(i);
                    }
                    for (int i = 0; i < lastPosition; i++) {
                        rightAll = rightAll + getDefaultWidth(i);
                    }
                    left = leftAll + (position + positionOffset) * 2 * dis + dis + lineMargins + positionOffset * (lastWidth - lastDis);
                    right = rightAll + (position + positionOffset) * 2 * dis + dis + lineMargins + positionOffset * lastWidth;
                    dynamicLine.updateView(left, right);
                } else {
                    for (int i = 0; i < position; i++) {
                        leftAll = leftAll + getDefaultWidth(i);
                    }
                    for (int i = 0; i <= position; i++) {
                        rightAll = rightAll + getDefaultWidth(i);
                    }
                    left = dis + leftAll + position * 2 * dis + lineMargins + positionOffset * 2 * dis + positionOffset * (lastWidth - lastDis);
                    right = dis + rightAll + position * 2 * dis + lineMargins + positionOffset * 2 * dis + positionOffset * lastWidth;
                    dynamicLine.updateView(left, right);
                }
            }

        } else {
            if (lineDrag) {

                if (lastPosition > position) {
                    for (int i = 0; i < position; i++) {
                        leftAll = leftAll + getDefaultWidth(i);
                    }
                    for (int i = 0; i < lastPosition; i++) {
                        rightAll = rightAll + getDefaultWidth(i);
                    }
                    left = leftAll + (position + 1) * dis + positionOffset * (getDefaultWidth(position) + dis) + lineMargins;
                    right = rightAll + (lastPosition + 1) * dis + getDefaultWidth(lastPosition) + lineMargins;
                    dynamicLine.updateView(left, right);
                } else {
                    if (positionOffset > 0.5f) {
                        positionOffset = 0.5f;
                    }
                    for (int i = 0; i < position; i++) {
                        leftAll = leftAll + getDefaultWidth(i);
                    }
                    for (int i = 0; i <= position; i++) {
                        rightAll = rightAll + getDefaultWidth(i);
                    }
                    left = leftAll + (position + 1) * dis + lineMargins;
                    right = rightAll + (position + 1) * dis + lineMargins + positionOffset * 2 * (getDefaultWidth(position + 1) + dis);
                    dynamicLine.updateView(left, right);
                }

            } else {
                if (lastPosition > position) {
                    for (int i = 0; i < position; i++) {
                        leftAll = leftAll + getDefaultWidth(i);
                    }
                    for (int i = 0; i < lastPosition; i++) {
                        rightAll = rightAll + getDefaultWidth(i);
                    }
                    left = leftAll + (position + positionOffset) * dis + dis + lineMargins + positionOffset * (lastWidth - lastDis);
                    right = rightAll + (position + positionOffset) * dis + dis + lineMargins + positionOffset * lastWidth;
                    dynamicLine.updateView(left, right);
                } else {
                    for (int i = 0; i < position; i++) {
                        leftAll = leftAll + getDefaultWidth(i);
                    }
                    for (int i = 0; i <= position; i++) {
                        rightAll = rightAll + getDefaultWidth(i);
                    }
                    left = dis + leftAll + position * dis + lineMargins + positionOffset * dis + positionOffset * (lastWidth - lastDis);
                    right = dis + rightAll + position * dis + lineMargins + positionOffset * dis + positionOffset * lastWidth;
                    dynamicLine.updateView(left, right);
                }
            }

        }

        lastValue = positionOffsetPixels;
    }


    @Override
    public void onPageSelected(int position) {
        viewPagerTitle.setCurrentItem(position);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

        if (state == SCROLL_STATE_SETTLING) {
            hasSeting = true;
            int position = pager.getCurrentItem();
            if (position + 1 < textViews.size() && position - 1 >= 0) {
                textViews.get(position).getLocationOnScreen(location);
                lineWidth = getDefaultWidth(position);
                int x;
                if (position > lastPosition) {
                    x = location[0] - screenWidth / 2 - (int) lineMargins + lineWidth / 2;
                } else {
                    x = location[0] - screenWidth / 2 + (int) lineMargins + lineWidth / 2;
                }

                viewPagerTitle.smoothScrollBy(x, 0);

            } else if (position + 1 == textViews.size()) {
                viewPagerTitle.smoothScrollBy(lineWidth, 0);

            }
            lastPosition = pager.getCurrentItem();

        } else if (state == SCROLL_STATE_IDLE) {
            int position = pager.getCurrentItem();
            if (!hasSeting) {
                if (position + 1 < textViews.size() && position - 1 >= 0) {
                    textViews.get(position).getLocationOnScreen(location);
                    lineWidth = getDefaultWidth(position);
                    int x;
                    if (position > lastPosition) {
                        x = location[0] - screenWidth / 2 - (int) lineMargins + lineWidth / 2;
                    } else {
                        x = location[0] - screenWidth / 2 + (int) lineMargins + lineWidth / 2;
                    }

                    viewPagerTitle.smoothScrollBy(x, 0);

                } else if (position + 1 == textViews.size()) {
                    viewPagerTitle.smoothScrollBy(lineWidth, 0);

                }
            }
            hasSeting = false;

            lastPosition = pager.getCurrentItem();
            int leftAll = 0;
            for (int i = 0; i < lastPosition; i++) {
                leftAll = leftAll + getDefaultWidth(i);
            }
            lineWidth = getDefaultWidth(lastPosition);
            if (titleCenter) {
                float leftS = leftAll + (lastPosition * 2 + 1) * dis + lineMargins;
                float rightS = leftS + lineWidth;
                if (left != leftS || right != rightS) {
                    dynamicLine.updateView(leftS, rightS);
                }
            } else {
                float leftS = leftAll + (lastPosition + 1) * dis + lineMargins;
                float rightS = leftS + lineWidth;
                if (left != leftS || right != rightS) {
                    dynamicLine.updateView(leftS, rightS);
                }
            }

        }

    }

}

