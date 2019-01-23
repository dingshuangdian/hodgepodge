package com.lsqidsd.hodgepodge.diyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lsqidsd.hodgepodge.R;

public class VerticleScrollView extends View {
    private Paint paint;
    private float x, startY, endY, firstY, nextStartY, secondY;
    private String[] text = {"123456", "78910"};
    private float textWidth, textHeight;
    //滚动速度
    private float speech = 0;
    private static final int CHANGE_SPEECH = 0x01;
    //是否已经在滚动
    private boolean isScroll = false;

    public VerticleScrollView(Context context) {
        this(context, null);
    }

    public VerticleScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticleScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.edit_hint_text));
        paint.setTextSize(30);
        //测量文字的宽高
        Rect rect = new Rect();
        paint.getTextBounds(text[0], 0, text[0].length(), rect);
        textWidth = rect.width();
        textHeight = rect.height();
        //文字开始的x,y坐标
        // 由于文字是以基准线为基线的，文字底部会突出一点，所以向上收5px
        x = getX() + getPaddingLeft();
        startY = getTop() + textHeight + getPaddingTop() - 5;
        //文字结束的x,y坐标
        endY = startY + textHeight + getPaddingBottom();
        //下一个文字滚动开始的y坐标
        //由于文字是以基准线为基线的，文字底部会突出一点，所以向上收5px
        nextStartY = getTop() - 5;
        //记录开始的坐标
        firstY = startY;
        secondY = nextStartY;
    }

    //获取宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = (int) (getPaddingTop() + getPaddingBottom() + textHeight);
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = (int) (getPaddingLeft() + getPaddingRight() + textWidth);
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    /**
     * 通过Handler来改变速度
     * 通过isScroll控制Handler只发送一次
     * 通过invalidate一直重绘两句话的文字
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHANGE_SPEECH:
                    speech = 1f;
                    break;
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //启动滚动
        if (!isScroll) {
            mHandler.sendEmptyMessageDelayed(CHANGE_SPEECH, 2000);
            isScroll = true;
        }
        canvas.drawText(text[0], x, startY, paint);
        canvas.drawText(text[1], x, nextStartY, paint);
        startY += speech;
        nextStartY += speech;
        //超出View的控件时
        if (startY > endY || nextStartY > endY) {
            if (startY > endY) {
                //第一次滚动过后交换值
                startY = secondY;
                nextStartY = firstY;
            } else if (nextStartY > endY) {
                //第二次滚动过后交换值
                startY = firstY;
                nextStartY = secondY;
            }
            speech = 0;
            isScroll = false;
        }
        invalidate();
    }

    /**
     * 监听点击事件
     */
    public OnTouchListener listener;

    public interface OnTouchListener {
        void touchListener(String s);
    }

    public void setListener(OnTouchListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //点击事件
                if (listener != null) {
                    if (startY >= firstY && nextStartY < firstY) {
                        listener.touchListener(text[0]);
                    } else if (nextStartY >= firstY && startY < firstY) {
                        listener.touchListener(text[1]);
                    }
                }
                break;
        }
        return true;
    }
}
