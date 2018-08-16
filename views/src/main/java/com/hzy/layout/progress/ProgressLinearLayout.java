package com.hzy.layout.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hzy.views.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ziye_huang on 2018/8/16.
 */
public class ProgressLinearLayout extends LinearLayout implements ProgressLayout {
    private final String CONTENT = "type_content";
    private final String LOADING = "type_loading";
    private final String EMPTY = "type_empty";
    private final String ERROR = "type_error";

    private LayoutInflater mInflater;
    private int mLoadingProgressBarWidth;
    private int mLoadingProgressBarHeight;
    private int mLoadingProgressBarColor;
    private int mLoadingBackgroundColor;

    private List<View> contentViews = new ArrayList<>();
    private String mState = CONTENT;
    private Drawable defaultBackground;

    private View loadingView;
    private View emptyView;
    private View errorView;

    public ProgressLinearLayout(Context context) {
        super(context);
    }

    public ProgressLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProgressLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressRelativeLayout);
        mLoadingProgressBarWidth = typedArray.getDimensionPixelSize(R.styleable.ProgressRelativeLayout_loadingProgressBarWidth, 108);
        mLoadingProgressBarHeight = typedArray.getDimensionPixelSize(R.styleable.ProgressRelativeLayout_loadingProgressBarHeight, 108);
        // 设置 ProgressBar 颜色
        mLoadingProgressBarColor = typedArray.getColor(R.styleable.ProgressRelativeLayout_loadingProgressBarColor, Color.BLACK);
        mLoadingBackgroundColor = typedArray.getColor(R.styleable.ProgressRelativeLayout_loadingBackgroundColor, Color.TRANSPARENT);

        typedArray.recycle();

        defaultBackground = getBackground();
    }

    @Override
    public void showContent() {
        switchState(CONTENT, null, Collections.<Integer>emptyList());
    }

    @Override
    public void showContent(List<Integer> idsOfViewsNotToHide) {
        switchState(CONTENT, null, idsOfViewsNotToHide);
    }

    @Override
    public void showLoading() {
        switchState(LOADING, null, Collections.<Integer>emptyList());
    }

    @Override
    public void showLoading(List<Integer> idsOfViewsNotToHide) {
        switchState(LOADING, null, idsOfViewsNotToHide);
    }

    @Override
    public void showLoading(View loading) {
        switchState(LOADING, loading, Collections.<Integer>emptyList());
    }

    @Override
    public void showLoading(View loading, List<Integer> idsOfViewsNotToHide) {
        switchState(LOADING, loading, idsOfViewsNotToHide);
    }

    @Override
    public void showEmpty(View empty) {
        switchState(EMPTY, empty, Collections.<Integer>emptyList());
    }

    @Override
    public void showEmpty(View empty, List<Integer> idsOfViewsNotToHide) {
        switchState(EMPTY, empty, idsOfViewsNotToHide);
    }

    @Override
    public void showError(View error) {
        switchState(ERROR, error, Collections.<Integer>emptyList());
    }

    @Override
    public void showError(View error, List<Integer> idsOfViewsNotToHide) {
        switchState(ERROR, null, idsOfViewsNotToHide);
    }

    private void switchState(String state, View view, List<Integer> idsOfViewsNotToHide) {
        mState = state;
        hideAllViews();

        switch (state) {
            case CONTENT:
                setContentVisibility(true, idsOfViewsNotToHide);
                break;
            case LOADING:
                setContentVisibility(false, idsOfViewsNotToHide);
                if (null != view) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.gravity = Gravity.CENTER;
                    addView(view, layoutParams);
                } else {
                    inflateLoadingView();
                }
                break;
            case EMPTY:
                setContentVisibility(false, idsOfViewsNotToHide);
                if (null != view) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.gravity = Gravity.CENTER;
                    addView(view, layoutParams);
                } else {
                    inflateEmptyView();
                }
                break;
            case ERROR:
                setContentVisibility(false, idsOfViewsNotToHide);
                if (null != view) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.gravity = Gravity.CENTER;
                    addView(view, layoutParams);
                } else {
                    inflateErrorView();
                }
                break;
        }
    }

    /**
     * 初始化loading View
     */
    private void inflateLoadingView() {
        if (null == loadingView) {
            View view = mInflater.inflate(R.layout.progress_layout_view_loading, null);
            loadingView = view.findViewById(R.id.layout_loading);
            loadingView.setTag(LOADING);

            ProgressBar progressBar = view.findViewById(R.id.progress_bar_loading);
            progressBar.getLayoutParams().width = mLoadingProgressBarWidth;
            progressBar.getLayoutParams().height = mLoadingProgressBarHeight;
            progressBar.getIndeterminateDrawable().setColorFilter(mLoadingProgressBarColor, PorterDuff.Mode.SRC_IN);
            progressBar.requestLayout();

            setBackgroundColor(mLoadingBackgroundColor);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;

            addView(loadingView, layoutParams);
        } else {
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化Empty View
     */
    private void inflateEmptyView() {
        if (null == emptyView) {
            View view = mInflater.inflate(R.layout.progress_layout_view_empty, null);
            emptyView = view.findViewById(R.id.layout_empty);
            emptyView.setTag(EMPTY);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;

            addView(emptyView, layoutParams);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化Error View
     */
    private void inflateErrorView() {
        if (null == errorView) {
            View view = mInflater.inflate(R.layout.progress_layout_view_error, null);
            errorView = view.findViewById(R.id.layout_error);
            errorView.setTag(ERROR);


            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;

            addView(errorView, layoutParams);
        } else {
            errorView.setVisibility(View.VISIBLE);
        }
    }


    private void setContentVisibility(boolean visible, List<Integer> skipIds) {
        for (View v : contentViews) {
            if (!skipIds.contains(v.getId())) {
                v.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (null == child.getTag() || (!child.getTag().equals(LOADING) && !child.getTag().equals(EMPTY) && !child.getTag().equals(ERROR))) {
            contentViews.add(child);
        }
    }

    /**
     * 重置背景
     */
    private void restoreBackground() {
        setBackgroundDrawable(defaultBackground);
    }

    /**
     * 重置视图
     */
    private void hideAllViews() {
        hideLoadingView();
        hideEmptyView();
        hideErrorView();
        restoreBackground();
    }

    private void hideLoadingView() {
        if (null != loadingView) {
            loadingView.setVisibility(View.GONE);
        }
    }

    private void hideEmptyView() {
        if (null != emptyView) {
            emptyView.setVisibility(View.GONE);
        }
    }

    private void hideErrorView() {
        if (null != errorView) {
            errorView.setVisibility(View.GONE);
        }
    }
}
