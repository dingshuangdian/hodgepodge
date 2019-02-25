package com.lsqidsd.hodgepodge.diyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.api.MyOnPageChangeListener;
import com.lsqidsd.hodgepodge.bean.CategoriesBean;
import com.lsqidsd.hodgepodge.utils.DynamicLine;
import com.lsqidsd.hodgepodge.utils.Tool;

import java.util.ArrayList;
import java.util.List;

import static com.lsqidsd.hodgepodge.utils.Tool.getScreenWidth;
import static com.lsqidsd.hodgepodge.utils.Tool.getTextViewLength;


public class ViewPagerTitle extends HorizontalScrollView {
    private Context context;
    private ArrayList<TextView> textViews = new ArrayList<>();
    private OnTextViewClick onTextViewClick;
    private DynamicLine dynamicLine;
    private ViewPager viewPager;
    private MyOnPageChangeListener onPageChangeListener;
    private int margin;
    private LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams textViewParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private float defaultTextSize;
    private float selectedTextSize;
    private int defaultTextColor;
    private int shaderColorStart;
    private float lineHeight;
    private int shaderColorEnd;
    private boolean titleCenter;
    private float textTopMargins;
    private float textBottomMargins;
    private float lineBottomMargins;
    private boolean lineDrag;
    private float lineMargins;


    public void setMargin(int margin) {
        this.margin = margin;
    }

    public void setDefaultTextSize(float defaultTextSize) {
        this.defaultTextSize = defaultTextSize;
    }

    public void setSelectedTextSize(float selectedTextSize) {
        this.selectedTextSize = selectedTextSize;
    }

    public void setDefaultTextColor(int defaultTextColor) {
        this.defaultTextColor = defaultTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public void setBackgroundContentColor(int backgroundContentColor) {
        this.backgroundColor = backgroundContentColor;
    }

    public void setItemMargins(float itemMargins) {
        this.itemMargins = itemMargins;
    }

    private int selectedTextColor;
    private int backgroundColor;
    private float itemMargins;


    public ViewPagerTitle(Context context) {
        this(context, null);
    }

    public ViewPagerTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttributeSet(context, attrs);
    }

    private void initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlexTitle);
        defaultTextColor = array.getColor(R.styleable.FlexTitle_default_text_color, Color.GRAY);
        selectedTextColor = array.getColor(R.styleable.FlexTitle_selected_text_color, Color.BLACK);
        defaultTextSize = Tool.px2sp(context, array.getDimension(R.styleable.FlexTitle_default_text_size, 14));
        selectedTextSize = Tool.px2sp(context, array.getDimension(R.styleable.FlexTitle_selected_text_Size, 14));
        textTopMargins = Tool.px2dip(context, array.getDimension(R.styleable.FlexTitle_item_top_margins, 0));
        textBottomMargins = Tool.px2dip(context, array.getDimension(R.styleable.FlexTitle_item_bottom_margins, 0));
        lineMargins = Tool.px2dip(context, array.getDimension(R.styleable.FlexTitle_line_margins, 0));
        backgroundColor = array.getColor(R.styleable.FlexTitle_background_content_color, Color.WHITE);
        itemMargins = Tool.px2dip(context, array.getDimension(R.styleable.FlexTitle_item_margins, 30));
        titleCenter = array.getBoolean(R.styleable.FlexTitle_title_center, false);
        lineDrag = array.getBoolean(R.styleable.FlexTitle_line_drag, true);
        shaderColorStart = array.getColor(R.styleable.FlexTitle_line_start_color, Color.GREEN);
        shaderColorEnd = array.getColor(R.styleable.FlexTitle_line_end_color, Color.BLUE);
        lineHeight = Tool.px2dip(context, array.getDimension(R.styleable.FlexTitle_line_height, 10));
        lineBottomMargins = Tool.px2dip(context, array.getDimension(R.styleable.FlexTitle_line_bottom_margins, 10));

        array.recycle();
    }
    /**
     * 初始化时，调用这个方法。ViewPager需要设置Adapter，且titles的数据长度需要与Adapter中的数据长度一置。
     *
     * @param
     * @param viewPager
     * @param selectedIndex 默认选择的第几个页面
     */
    public void initData(String[] t, ViewPager viewPager, int selectedIndex) {
        this.viewPager = viewPager;
        createDynamicLine();
        createTextViews(t);
        onPageChangeListener = new MyOnPageChangeListener(getContext(), viewPager, dynamicLine, this, margin, defaultTextSize, selectedTextSize, titleCenter, lineDrag, lineMargins);
        setSelectedIndex(selectedIndex);
        viewPager.addOnPageChangeListener(onPageChangeListener);

    }

    private int getFixLeftDis(List<CategoriesBean> t) {
        TextView textView = new TextView(getContext());
        textView.setTextSize(defaultTextSize);
        textView.setText(t.get(0).getTitle());
        float defaultTextSize = getTextViewLength(textView);
        textView.setTextSize(selectedTextSize);
        float selectTextSize = getTextViewLength(textView);
        return (int) (selectTextSize - defaultTextSize) / 2;
    }
    public ArrayList<TextView> getTextView() {
        return textViews;
    }
    private void createDynamicLine() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dynamicLine = new DynamicLine(getContext(), shaderColorStart, shaderColorEnd, (int) lineHeight, (int) lineBottomMargins);
        dynamicLine.setLayoutParams(params);
    }
    private void createTextViews(String[] titles) {
        LinearLayout contentLl = new LinearLayout(getContext());
        contentLl.setBackgroundColor(backgroundColor);
        contentLl.setLayoutParams(contentParams);
        contentLl.setOrientation(LinearLayout.VERTICAL);
        addView(contentLl);
        LinearLayout textViewLl = new LinearLayout(getContext());
        textViewLl.setLayoutParams(contentParams);
        textViewLl.setOrientation(LinearLayout.HORIZONTAL);
        margin = getTextViewMargins(titles);
        for (int i = 0; i < titles.length; i++) {
            TextView textView = new TextView(getContext());
            textView.setText(titles[i]);
            textView.setTextColor(Color.GRAY);
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textView.setTextSize(defaultTextSize);
            if (titleCenter) {
                textViewParams2.setMargins(margin, (int) textTopMargins, margin, (int) textBottomMargins);
                textView.setLayoutParams(textViewParams2);
            } else {
                if (i == titles.length - 1) {
                    textViewParams2.setMargins(margin, (int) textTopMargins, margin, (int) textBottomMargins);
                    textView.setLayoutParams(textViewParams2);
                } else {
                    textViewParams.setMargins(margin, (int) textTopMargins, 0, (int) textBottomMargins);
                    textView.setLayoutParams(textViewParams);
                }
            }

            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setOnClickListener(onClickListener);
            textView.setTag(i);
            textViews.add(textView);
            textViewLl.addView(textView);
        }
        contentLl.addView(textViewLl);
        contentLl.addView(dynamicLine);
    }

    private int getTextViewMargins(String[] titles) {
        float countLength = 0;
        float textLength = 0;//文字总长度
        TextView textView = new TextView(getContext());
        textView.setTextSize(defaultTextSize);
        TextPaint paint = textView.getPaint();
        TextView textView2 = new TextView(getContext());
        textView2.setTextSize(selectedTextSize);
        TextPaint paint2 = textView2.getPaint();
        if (titleCenter) {
            for (int i = 0; i < titles.length; i++) {
                countLength = countLength + itemMargins + paint.measureText(titles[i] + itemMargins);
                textLength = textLength + paint.measureText(titles[i]);
            }
            countLength = countLength + 2 * itemMargins + paint2.measureText(titles[titles.length-1]);
            textLength = textLength + paint.measureText(titles[titles.length-1]);
            int screenWidth = getScreenWidth(getContext());

            if (countLength <= screenWidth) {
                return (screenWidth - (int) textLength) / (titles.length * 2);
            } else {
                return (int) itemMargins;
            }
        } else {
            for (int i = 0; i < titles.length - 1; i++) {
                countLength = countLength + itemMargins + paint.measureText(titles[i]);
                textLength = textLength + paint.measureText(titles[i]);
            }
            countLength = countLength + 2 * itemMargins + paint2.measureText(titles[titles.length - 1]);
            textLength = textLength + paint.measureText(titles[titles.length - 1]);
            int screenWidth = getScreenWidth(getContext());

            if (countLength <= screenWidth) {
                return (screenWidth - (int) textLength) / (titles.length + 1);
            } else {
                return (int) itemMargins;
            }
        }

    }


    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            setCurrentItem((int) v.getTag());
            viewPager.setCurrentItem((int) v.getTag());
            if (onTextViewClick != null) {
                onTextViewClick.textViewClick((TextView) v, (int) v.getTag());
            }

        }
    };

    public void setSelectedIndex(int index) {
        setCurrentItem(index);
    }

    public void setCurrentItem(int index) {
        for (int i = 0; i < textViews.size(); i++) {
            if (i == index) {
                textViews.get(i).setTextColor(selectedTextColor);
                textViews.get(i).setTextSize(selectedTextSize);
            } else {
                textViews.get(i).setTextColor(defaultTextColor);
                textViews.get(i).setTextSize(defaultTextSize);
            }
        }
    }

    public interface OnTextViewClick {
        void textViewClick(TextView textView, int index);
    }

    public void setOnTextViewClickListener(OnTextViewClick onTextViewClick) {
        this.onTextViewClick = onTextViewClick;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (viewPager != null) {
            viewPager.removeOnPageChangeListener(onPageChangeListener);
        }

    }


}
