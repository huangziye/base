package com.hzy.views.recycler.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.hzy.views.R;
import com.hzy.views.recycler.base.BaseViewHolder;
import com.hzy.views.recycler.base.SimpleAdapter;

/**
 * Created by ziye_huang on 2018/8/10.
 */
public class LoadingCell extends AbsStateCell {
    public LoadingCell(Object o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return SimpleAdapter.LOADING_TYPE;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    protected View getDefaultView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.rv_loading_layout, null);
    }
}
