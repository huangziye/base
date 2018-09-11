package com.hzy.views.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ziye_huang on 2018/9/10.
 */
public class SpeedCardViewHolder extends RecyclerView.ViewHolder {

    public SpeedCardViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public static SpeedCardViewHolder getInstance(Context context, ViewGroup parent, @LayoutRes int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        SpeedCardViewHolder holder = new SpeedCardViewHolder(itemView);
        return holder;
    }

}
