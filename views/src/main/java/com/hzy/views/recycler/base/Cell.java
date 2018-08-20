package com.hzy.views.recycler.base;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

/**
 * Created by ziye_huang on 2018/8/10.
 */
public interface Cell {

    /**
     * 回收资源
     */
    void releaseResource();

    /**
     * 获取viewType
     *
     * @return
     */
    int getItemType();

    /**
     * 创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    /**
     * 数据绑定
     *
     * @param holder
     * @param position
     */
    void onBindViewHolder(@NonNull BaseViewHolder holder, int position);
}
