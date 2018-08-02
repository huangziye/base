package com.hzy.views.rolling.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * VerticalRollingTextView的数据适配器
 * Created by ziye_huang on 2018/5/3.
 */

public abstract class VerticalRollingTextAdapter<T> {
    private List<T> data = new ArrayList<>();

    public VerticalRollingTextAdapter() {
    }

    public VerticalRollingTextAdapter(List<T> data) {
        this.data = data;
    }

    /**
     * @param index 当前角标
     * @return 待显示的字符串
     */
    final public CharSequence getText(int index) {
        return text(data.get(index));
    }

    protected abstract CharSequence text(T t);

    public int getItemCount() {
        return null == data || data.isEmpty() ? 0 : data.size();
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isEmpty() {
        return null == data || data.isEmpty();
    }
}
