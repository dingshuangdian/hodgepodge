package com.lsqidsd.hodgepodge.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.databinding.SearchViewBinding;

public class HodgeSearchView extends ConstraintLayout {
    private int searchIcon = R.mipmap.ic_search_gray;
    private int clearIcon = R.mipmap.ic_clear;
    private boolean showClearButton = true;
    private int searchTextSize = getResources().getDimensionPixelSize(R.dimen.edit_text_size);
    private int searchTextColor = getResources().getColor(R.color.edit_text);
    private String searchHint = getResources().getString(R.string.edit_hint);
    private int searchHintColor = getResources().getColor(R.color.edit_hint_text);
    private int searchViewHeight = getResources().getDimensionPixelSize(R.dimen.search_view_height);
    private int maxSearchLength = -1;//输入内容最大长度（默认不设限制）
    private OnEditChangeListener onEditChangeListener;
    private OnEnterClickListener onEnterClickListener;
    private SearchViewBinding searchViewBinding;

    public HodgeSearchView(Context context) {
        this(context, null);
    }

    public HodgeSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HodgeSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initViews(context);
    }

    //初始化属性
    private void initAttrs(Context context, AttributeSet attributeSet) {

        if (attributeSet != null) {
            Resources resources = getResources();
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.HodgeSearchView);
            searchIcon = typedArray.getResourceId(R.styleable.HodgeSearchView_searchIcon, R.mipmap.ic_search_gray);
            clearIcon = typedArray.getResourceId(R.styleable.HodgeSearchView_clearIcon, R.mipmap.ic_clear);
            searchTextSize = typedArray.getDimensionPixelSize(R.styleable.HodgeSearchView_searchTextSize, resources.getDimensionPixelSize(R.dimen.edit_text_size));
            searchTextColor = typedArray.getColor(R.styleable.HodgeSearchView_searchTextColor, resources.getColor(R.color.edit_text));
            searchHint = getOrDefault(typedArray.getString(R.styleable.HodgeSearchView_searchHint), resources.getString(R.string.edit_hint));
            searchHintColor = typedArray.getColor(R.styleable.HodgeSearchView_searchHintColor, resources.getColor(R.color.edit_hint_text));
            searchViewHeight = typedArray.getDimensionPixelSize(R.styleable.HodgeSearchView_searchViewHeight, resources.getDimensionPixelSize(R.dimen.search_view_height));
            maxSearchLength = typedArray.getInteger(R.styleable.HodgeSearchView_maxSearchLength, -1);//默认不限制

            typedArray.recycle();//回收资源，否则再次使用会出错
        }

    }

    //初始化view
    private void initViews(Context context) {
        searchViewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.search_view, this, true);
        searchViewBinding.editSearch.setTextColor(searchTextColor);
        searchViewBinding.editSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, searchTextSize);
        searchViewBinding.editSearch.setHintTextColor(searchHintColor);
        searchViewBinding.editSearch.setHint(searchHint);
        limitEditLength(maxSearchLength);
        limitSearchViewHeight(searchViewHeight);
        showOrHideClearButton(searchViewBinding.btnClear, showClearButton);
        searchViewBinding.editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable) && showClearButton) {
                    searchViewBinding.btnClear.setVisibility(VISIBLE);
                } else {
                    searchViewBinding.btnClear.setVisibility(GONE);
                }
                if (onEditChangeListener != null) {
                    onEditChangeListener.onEditChanged(editable.toString().trim());
                }
            }
        });
        //监听键盘搜索
        searchViewBinding.editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener()

        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (onEnterClickListener != null && i == EditorInfo.IME_ACTION_SEARCH) {
                    onEnterClickListener.onEnterClick(getSearchText());
                    return true;
                }
                return false;
            }
        });

        searchViewBinding.btnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });


    }

    //如果目标字符串为空，就获取一个默认字符
    private String getOrDefault(String target, String defaultStr) {
        if (TextUtils.isEmpty(target)) {
            return defaultStr;
        }
        return target;
    }

    private void showOrHideClearButton(ImageButton button, boolean isShow) {
        if (isShow && !getSearchText().isEmpty()) {
            button.setVisibility(VISIBLE);
        } else {
            button.setVisibility(GONE);
        }
    }

    private void clear() {
        searchViewBinding.editSearch.setText("");
    }

    private void limitEditLength(int length) {
        if (length > 0) {
            searchViewBinding.editSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxSearchLength)});
        }
    }

    private void limitSearchViewHeight(int height) {
        LayoutParams layoutParams = (LayoutParams) searchViewBinding.searchView.getLayoutParams();
        layoutParams.height = height;
        searchViewBinding.searchView.setLayoutParams(layoutParams);
    }

    public String getSearchText() {
        return searchViewBinding.editSearch.getText().toString().trim();
    }

    /**
     * 设置输入内容的文字大小
     *
     * @param searchTextSize 文字大小（单位为px）
     */
    public void setSearchTextSize(int searchTextSize) {
        this.searchTextSize = searchTextSize;
        searchViewBinding.editSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, searchTextSize);
    }

    public void setSearchTextColor(@ColorInt int searchTextColor) {
        this.searchTextColor = searchTextColor;
        searchViewBinding.editSearch.setTextColor(searchTextColor);
    }

    public void setSearchHint(String searchHint) {
        this.searchHint = searchHint;
        searchViewBinding.editSearch.setHint(searchHint);
    }

    public void setSearchHintColor(int searchHintColor) {
        this.searchHintColor = searchHintColor;
        searchViewBinding.editSearch.setHintTextColor(searchHintColor);
    }

    /**
     * 设置SearchView的高度（单位为px）
     */
    public void setSearchViewHeight(int searchViewHeight) {
        this.searchViewHeight = searchViewHeight;
        limitSearchViewHeight(searchViewHeight);
    }

    public int getMaxSearchLength() {
        return maxSearchLength;
    }

    //限制输入内容的最大长度
    public void setMaxSearchLength(int maxSearchLength) {
        if (maxSearchLength > 0) {
            this.maxSearchLength = maxSearchLength;
            limitEditLength(maxSearchLength);
        }
    }

    public void setSearchIcon(@DrawableRes int icon) {
        this.searchIcon = icon;
        searchViewBinding.editSearch.setCompoundDrawablesWithIntrinsicBounds(searchIcon, 0, 0, 0);
    }

    public void setClearIcon(@DrawableRes int icon) {
        this.clearIcon = icon;
        searchViewBinding.btnClear.setImageResource(clearIcon);
    }


    /*****************************设置监听器**********************/
    public void setOnEditChangeListener(OnEditChangeListener editChangeListener) {
        this.onEditChangeListener = editChangeListener;
    }

    public void setOnEnterClickListener(OnEnterClickListener enterClickListener) {
        this.onEnterClickListener = enterClickListener;
    }


    /**
     * 监听清除按钮点击事件
     */
    public interface OnClearClickListener {
        /**
         * @param oldContent 被删除的输入框内容
         */
        void onClick(String oldContent);
    }

    /**
     * 监听输入框内容变化
     */
    public interface OnEditChangeListener {
        /**
         * @param nowContent 输入框当前的内容
         */
        void onEditChanged(String nowContent);
    }

    /**
     * 监听用户点击了虚拟键盘中右下角的回车/搜索键
     * 此时可以选择执行搜索操作
     */
    public interface OnEnterClickListener {
        /**
         * @param content 输入框中的内容
         */
        void onEnterClick(String content);
    }


}
