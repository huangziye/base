package com.hzy.layout;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ziye_huang on 2018/8/15.
 */
public abstract class FlowAdapter<T> {
    private List<T> mList;

    public FlowAdapter(List<T> datas) {
        mList = datas;
    }

    public FlowAdapter(T[] datas) {
        mList = new ArrayList<T>(Arrays.asList(datas));
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    public abstract View getView(int position);
}
