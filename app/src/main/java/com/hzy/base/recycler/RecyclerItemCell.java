package com.hzy.base.recycler;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzy.base.R;
import com.hzy.views.recycler.base.BaseCell;
import com.hzy.views.recycler.base.BaseViewHolder;

/**
 * Created by ziye_huang on 2018/8/13.
 */
public class RecyclerItemCell extends BaseCell<RecyclerItemEntity> {


    public RecyclerItemCell(RecyclerItemEntity data) {
        super(data);
    }

    @Override
    public int getItemType() {
        return 1;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        View view = holder.getView(R.id.tv_name);
        if (view instanceof TextView) {
            ((TextView) view).setText(mData.getName());
        }
    }
}
