package com.hzy.views.stackview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ziye_huang on 2018/8/17.
 */
public interface OnItemClickListener<T> {

    void onItemClick(ViewGroup viewGroup, View view, T t, int position);

    boolean onItemLongClick(ViewGroup viewGroup, T t, int position);
}
