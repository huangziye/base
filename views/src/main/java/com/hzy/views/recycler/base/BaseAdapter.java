package com.hzy.views.recycler.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziye_huang on 2018/8/10.
 */
public abstract class BaseAdapter<C extends BaseCell> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<C> mData;

    public BaseAdapter() {
        mData = new ArrayList<>();
    }

    public void setData(List<C> data) {
        addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 创建ViewHolder
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        for (C c : mData) {
            if (c.getItemType() == viewType) {
                return c.onCreateViewHolder(viewGroup, viewType);
            }
        }
        throw new RuntimeException("wrong viewType");
    }

    /**
     * 数据绑定
     *
     * @param baseViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        mData.get(position).onBindViewHolder(baseViewHolder, position);
    }

    /**
     * 释放资源
     *
     * @param holder
     */
    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        int position = holder.getAdapterPosition();
        if (position < 0 || position >= mData.size()) {
            return;
        }
        mData.get(position).releaseResource();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

    /**
     * 添加一个Cell
     *
     * @param cell
     */
    public void add(C cell) {
        mData.add(cell);
        notifyItemChanged(mData.indexOf(cell));
    }

    /**
     * 添加一个Cell
     *
     * @param position
     * @param cell
     */
    public void add(int position, C cell) {
        mData.add(position, cell);
        notifyItemChanged(mData.indexOf(cell));
    }

    /**
     * 添加多个Cell
     *
     * @param list
     */
    public void addAll(List<C> list) {
        if (null == list || list.isEmpty()) {
            return;
        }
        mData.addAll(list);
        notifyItemRangeChanged(mData.size() - list.size(), mData.size());
    }

    /**
     * 在指定位置添加多个Cell
     *
     * @param position
     * @param list
     */
    public void addAll(int position, List<C> list) {
        if (null == list || list.isEmpty()) {
            return;
        }
        if (position > mData.size()) {
            return;
        }
        mData.addAll(position, list);
        notifyItemRangeChanged(position, position + list.size());
    }

    /**
     * 删除Cell
     *
     * @param cell 要删除的Cell
     */
    public void remove(C cell) {
        remove(mData.indexOf(cell));
    }

    /**
     * 删除Cell
     *
     * @param postion 要删除的下标
     */
    public void remove(int postion) {
        mData.remove(postion);
        notifyItemRemoved(postion);
    }

    /**
     * 删除多个Cell
     *
     * @param start 开始下标
     * @param count 要删除的个数
     */
    public void remove(int start, int count) {
        if (start + count > mData.size()) {
            return;
        }

        mData.subList(start, start + count).clear();
        notifyItemRangeRemoved(start, count);
    }

    /**
     * 清空数据
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 如果子类需要在onBindViewHolder 回调的时候做的操作可以在这个方法里做
     *
     * @param holder
     * @param position
     */
    protected abstract void onViewHolderBound(BaseViewHolder holder, int position);
}
