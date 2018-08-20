package com.hzy.views.recycler.base;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ziye_huang on 2018/8/10.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mItemView;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        mItemView = itemView;
    }

    /**
     * 获取ItemView
     *
     * @return
     */
    public View getItemView() {
        return mItemView;
    }

    /**
     * 获取View
     *
     * @param resId
     * @return
     */
    public View getView(@IdRes int resId) {
        return retrieveView(resId);
    }

    /**
     * 设置文本信息
     *
     * @param resId
     * @param text
     */
    public void setText(@IdRes int resId, CharSequence text) {
        View view = getView(resId);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    /**
     * 设置文本信息
     *
     * @param resId
     * @param strId
     */
    public void setText(@IdRes int resId, @StringRes int strId) {
        View view = getView(resId);
        if (view instanceof TextView) {
            ((TextView) view).setText(strId);
        }
    }

    /**
     * 根据viewId获取View
     *
     * @param viewId
     * @param <V>
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <V extends View> V retrieveView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (null == view) {
            view = mItemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (V) view;
    }
}
