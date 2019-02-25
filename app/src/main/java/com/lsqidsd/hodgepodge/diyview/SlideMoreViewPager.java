package com.lsqidsd.hodgepodge.diyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.OverScroller;

import com.lsqidsd.hodgepodge.R;

import java.lang.reflect.Field;

public class SlideMoreViewPager extends ViewPager {
    private static final String TAG = "SlideMoreViewPager";
    private static final boolean DEBUG = false;
    private int mTouchSlop;
    private int mActivePointerId;
    private float mInitialDownX;
    private float mInitialDownY;
    private float mLastMotionX;
    private boolean mIsDragging = false;

    private int mRightEdgeScrollX = -1;
    private int mPageMargin = 0;
    private int mPagePadding = 0;
    /**
     * 图片宽高比.
     */
    private Aspect mAspect = null;

    private OverScroller mScroller;

    private Paint mPaint;
    private TextPaint mTextPaint;
    private final RectF mTempRect = new RectF();
    /**
     * 绘制贝塞尔曲线.
     */
    private Path mPath = new Path();

    private int mPageMoreBackgroundColor = Color.parseColor("#dfdfdf");
    private int mPageMoreRadius = 10;
    /**
     * 开始拖动查看更多阈值.
     */
    private int mBeginDragThreshold = 30;
    /**
     * 查看更多拖动结束阈值.
     */
    private int mEndDragThreshold = 150;
    /**
     * 显示松开查看阈值.
     */
    private int mLoosenThreshold = 50;
    /**
     * 最大"松开"滑动阈值.
     */
    private int mMaxLoosenThreshold = 180;

    private String mPageMoreStr = "查看更多";
    private String mLoosenStr = "松开查看";

    private float mPageMoreTextSize = 35.0f;
    private int mPageMoreTextColor = Color.parseColor("#222222");

    private static final int sPageMoreTextMargin = 10;

    private OnFindMoreListener mOnFindMoreListener = null;

    public interface OnFindMoreListener {

        void onFindMore();
    }

    public void setOnFindMoreListener(OnFindMoreListener listener) {
        mOnFindMoreListener = listener;
    }

    public SlideMoreViewPager(Context context) {
        super(context);
        init(context, null);
    }

    public SlideMoreViewPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if(attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlideMoreViewPager);
            mPageMargin = ta.getDimensionPixelSize(R.styleable.SlideMoreViewPager_page_margin, 0);
            mPagePadding = ta.getDimensionPixelSize(R.styleable.SlideMoreViewPager_page_padding, 0);
            int pageWidth = ta.getInt(R.styleable.SlideMoreViewPager_page_width, 0);
            int pageHeight = ta.getInt(R.styleable.SlideMoreViewPager_page_height, 0);
            if(pageWidth != 0 && pageHeight != 0) {
                mAspect = new Aspect();
                mAspect.relativeWidth = pageWidth;
                mAspect.relativeHeight = pageHeight;
            }
            mPageMoreBackgroundColor = ta.getColor(R.styleable.SlideMoreViewPager_page_more_background_color
                    , mPageMoreBackgroundColor);
            mPageMoreRadius = ta.getDimensionPixelSize(R.styleable.SlideMoreViewPager_page_more_radius
                    , mPageMoreRadius);
            mBeginDragThreshold = ta.getDimensionPixelSize(R.styleable.SlideMoreViewPager_page_more_begin_drag_threshold
                    , mBeginDragThreshold);
            mEndDragThreshold = ta.getDimensionPixelSize(R.styleable.SlideMoreViewPager_page_more_end_drag_threshold
                    , mEndDragThreshold);
            mLoosenThreshold = ta.getDimensionPixelSize(R.styleable.SlideMoreViewPager_page_more_loosen_threshold
                    , mLoosenThreshold);
            mMaxLoosenThreshold = ta.getDimensionPixelSize(R.styleable.SlideMoreViewPager_page_more_max_loosen_threshold
                    , mMaxLoosenThreshold);
            mPageMoreTextSize = ta.getDimension(R.styleable.SlideMoreViewPager_page_more_text_size
                    , mPageMoreTextSize);
            mPageMoreTextColor = ta.getColor(R.styleable.SlideMoreViewPager_page_more_text_color
                    , mPageMoreTextColor);
            if(ta.hasValue(R.styleable.SlideMoreViewPager_page_more_drag_text)) {
                mPageMoreStr = ta.getString(R.styleable.SlideMoreViewPager_page_more_drag_text);
            }
            if(ta.hasValue(R.styleable.SlideMoreViewPager_page_more_loosen_text)) {
                mLoosenStr = ta.getString(R.styleable.SlideMoreViewPager_page_more_loosen_text);
            }
            ta.recycle();
        }

        setPageMargin(mPageMargin);
        setPadding(mPagePadding, 0, mPagePadding, 0);
        setClipChildren(false);
        setClipToPadding(false);

        final ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();

        mScroller = new OverScroller(context);

        mPaint = new Paint();
        mPaint.setColor(mPageMoreBackgroundColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mPageMoreTextSize);
        mTextPaint.setColor(mPageMoreTextColor);
    }

    static class Aspect {
        int relativeWidth;
        int relativeHeight;

        int calculateRealHeight(int realWidth) {
            return realWidth * relativeHeight / relativeWidth;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(mAspect != null) {
            // 保持原始图片宽高比.
            final int measuredWidth = getMeasuredWidth();
            final int realPageWidth = measuredWidth
                    - getPaddingLeft() - getPaddingRight() - mPageMargin * 2;
            final int height = mAspect.calculateRealHeight(realPageWidth)
                    + getPaddingTop() + getPaddingBottom();
            final int newHeightMeasureSpec =
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

            super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(DEBUG) {
            Log.d(TAG, "onDraw...");
        }
        final int currentItem = getCurrentItem();
        final PagerAdapter adapter = getAdapter();
        if(adapter == null
                || currentItem < adapter.getCount() - 1) {
            return;
        }
        final int width = getWidth();
        final int height = getHeight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        final int pageExtrude = mPagePadding - mPageMargin;

        final int currScrollX = getScrollX();
        final int rightEdgeScrollX = adapter.getCount() == 1/*if only just one item?*/
                ? 0 : getRightEdgeScrollX();

        canvas.save();
        canvas.translate(currScrollX, 0);
        if(currScrollX >= rightEdgeScrollX) {
            final int distance = currScrollX - rightEdgeScrollX;
            if(distance < mBeginDragThreshold) {
                mTempRect.left = width - pageExtrude;
                mTempRect.top = paddingTop;
                mTempRect.right = width + mPageMoreRadius;
                mTempRect.bottom = height - paddingBottom;
                canvas.clipRect(mTempRect);
                drawRect(canvas, true);
            } else {
                boolean loosen = false;
                final int endDragDistance = mBeginDragThreshold + mEndDragThreshold;
                if (distance < endDragDistance) {// 显示查看更多
                    mTempRect.left = width - pageExtrude - (distance - mBeginDragThreshold);
                    mTempRect.top = paddingTop;
                    mTempRect.right = width + mPageMoreRadius;
                    mTempRect.bottom = height - paddingBottom;
                    canvas.clipRect(mTempRect);
                    drawRect(canvas, true);
                } else {
                    mTempRect.left = width - pageExtrude - mEndDragThreshold;
                    mTempRect.top = paddingTop;
                    mTempRect.right = width + mPageMoreRadius;
                    mTempRect.bottom = height - paddingBottom;
                    drawRect(canvas, false);

                    final int loosenDistance = distance - endDragDistance;
                    mPath.reset();
                    mPath.moveTo(mTempRect.left, mTempRect.top);
                    mPath.quadTo(mTempRect.left - Math.min(mMaxLoosenThreshold, loosenDistance)
                            , mTempRect.centerY(), mTempRect.left, mTempRect.bottom);
                    mPath.lineTo(mTempRect.left, mTempRect.top);
                    canvas.drawPath(mPath, mPaint);

                    if(loosenDistance > mLoosenThreshold) {
                        loosen = true;
                    }
                }

                final float textLeft = mTempRect.left + pageExtrude + sPageMoreTextMargin;
                final float textCenterLine = mTempRect.centerY();
                drawPageMoreText(canvas, textLeft, textCenterLine, loosen);
            }
        } else if(currScrollX >= rightEdgeScrollX - pageExtrude) {
            final int distance = currScrollX - rightEdgeScrollX;
            mTempRect.left = width - (pageExtrude + distance);
            mTempRect.top = paddingTop;
            mTempRect.right = width + mPageMoreRadius;
            mTempRect.bottom = height - paddingBottom;
            canvas.clipRect(mTempRect);
            drawRect(canvas, true);
        }
        canvas.restore();
    }

    private void drawRect(Canvas canvas, boolean round) {
        if(round) {
            canvas.drawRoundRect(mTempRect, mPageMoreRadius, mPageMoreRadius, mPaint);
        } else {
            canvas.drawRect(mTempRect, mPaint);
        }
    }

    private void drawPageMoreText(Canvas canvas, float textLeft, float textCenterLine, boolean loosen) {
        final Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        canvas.drawText(loosen ? mLoosenStr : mPageMoreStr
                , textLeft
                , textCenterLine - metrics.bottom * .5F - metrics.top * .5F
                , mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        int pointerIndex;

        if(action == MotionEvent.ACTION_DOWN) {
            mActivePointerId = ev.getPointerId(0);
            pointerIndex = ev.findPointerIndex(mActivePointerId);
            if (pointerIndex < 0) {
                return false;
            }
            mIsDragging = false;
            if(!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }

            mInitialDownX = mLastMotionX = ev.getX();
            mInitialDownY = ev.getY();
        }

        final int currentItem = getCurrentItem();
        final PagerAdapter adapter = getAdapter();
        if(adapter == null
                || currentItem < adapter.getCount() - 1
                || canScrollHorizontally(1)) {
            return super.onTouchEvent(ev);
        }

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);
                startDragging(x, y);
                if(mIsDragging) {
                    final int dx = (int) (mLastMotionX - x);
                    if(DEBUG) {
                        Log.d(TAG, "start drag ~ dx:" + (x - mLastMotionX));
                    }
                    processMoving(dx);
                }

                mLastMotionX = x;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                onSecondaryPointerDown(ev);
                break;

            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp1(ev);
                break;

            case MotionEvent.ACTION_UP:
                dispatchOnFindMoreIfNeed();
                releaseTouchEvent();
                break;
            case MotionEvent.ACTION_CANCEL:
                releaseTouchEvent();
                break;
        }

        return mIsDragging || super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private void startDragging(float x, float y) {
        if(x < mLastMotionX) {// 左滑.
            final float xDiff = Math.abs(x - mInitialDownX);
            final float yDiff = Math.abs(y - mInitialDownY);
            if (xDiff > yDiff && xDiff > mTouchSlop) {
                requestParentDisallowInterceptTouchEvent();
                mIsDragging = true;
            }
        }
    }

    private void requestParentDisallowInterceptTouchEvent() {
        final ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private void releaseTouchEvent() {
        final int currentScrollX = getScrollX();
        final int rightEdgeScrollX = getRightEdgeScrollX();
        mScroller.startScroll(currentScrollX
                , 0
                , rightEdgeScrollX - currentScrollX
                , 0);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void dispatchOnFindMoreIfNeed() {
        final int distance = getScrollX() - getRightEdgeScrollX();
        if(distance > mBeginDragThreshold + mEndDragThreshold + mLoosenThreshold) {
            if(mOnFindMoreListener != null) {
                mOnFindMoreListener.onFindMore();
            }
        }
    }

    private void onSecondaryPointerDown(MotionEvent ev) {
        final int index = ev.getActionIndex();
        mActivePointerId = ev.getPointerId(index);

        mIsDragging = false;
        if(!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }

        mInitialDownX = mLastMotionX = ev.getX(index);
        mInitialDownY = ev.getY(index);
    }

    private void onSecondaryPointerUp1(MotionEvent ev) {
        final int pointerIndex = ev.getActionIndex();
        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = ev.getPointerId(newPointerIndex);
        }
    }

    private void processMoving(int dx) {
        final int currentScrollX = getScrollX();
        final int rightEdgeScrollX = getRightEdgeScrollX();

        if(dx > 0) {
            final int scrollGap = Math.abs(currentScrollX - rightEdgeScrollX);
            scrollBy(getRestrictedDx(scrollGap, dx), 0);
        } else if(dx < 0) {
            if(currentScrollX <= rightEdgeScrollX) {
                return;
            }
            if(currentScrollX + dx < rightEdgeScrollX) {
                scrollBy(rightEdgeScrollX - currentScrollX, 0);
                return;
            }
            scrollBy(dx, 0);
        }
    }

    private int getRestrictedDx(int scrollGap, int dx) {
        if(scrollGap < mTouchSlop * 4) {
            return dx;
        } else if (scrollGap < mTouchSlop * 8) {
            return (int) (dx * .8F);
        } else if (scrollGap < mTouchSlop * 16) {
            return (int) (dx * .6F);
        } else if (scrollGap < mTouchSlop * 32) {
            return (int) (dx * .4F);
        } else {
            return (int) (dx * .2F);
        }
    }

    /**
     * 获取滑动到最右边界ScrollX.
     */
    private int getRightEdgeScrollX() {
        if(mRightEdgeScrollX == -1) {
            final int clientWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            float lastOffset = -1.F;
            try {
                Field field = ViewPager.class.getDeclaredField("mLastOffset");
                field.setAccessible(true);
                lastOffset = field.getFloat(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(lastOffset >= 0) {
                mRightEdgeScrollX = (int) (clientWidth * lastOffset);
            }
        }

        return mRightEdgeScrollX;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {
            int currX = mScroller.getCurrX();
            scrollTo(currX, 0);

            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if ((mScroller != null) && !mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

}
