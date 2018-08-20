package com.hzy.views.recycler.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.hzy.views.R;
import com.hzy.views.recycler.base.BaseViewHolder;
import com.hzy.views.recycler.base.SimpleAdapter;

/**
 * Created by ziye_huang on 2018/8/15.
 */
public class LoadNoMoreCell extends AbsStateCell {
    public LoadNoMoreCell(Object o) {
        super(o);
    }

    @Override
    protected View getDefaultView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.rv_load_no_more_layout, null);
    }

    @Override
    public int getItemType() {
        return SimpleAdapter.LOAD_NO_MORE_TYPE;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }
}
