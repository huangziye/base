package com.hzy.views.recycler.base;

/**
 * Created by ziye_huang on 2018/8/10.
 */
public abstract class BaseCell<T> implements Cell {

    public T mData;

    public BaseCell(T data) {
        this.mData = data;
    }

    /**
     * 释放资源
     */
    @Override
    public void releaseResource() {

    }
}
