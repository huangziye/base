package com.hzy.views.stackview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziye_huang on 2018/8/17.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewViewHolder> {

    protected Context mContext;
    protected List<T> mDataList;
    protected int mLayoutId;
    protected ViewGroup mViewGroup;
    protected OnItemClickListener mOnItemClickListener;

    public BaseRecyclerViewAdapter(Context context, List<T> dataList, int layoutId) {
        mContext = context;
        mDataList = dataList;
        mLayoutId = layoutId;
    }

    @NonNull
    @Override
    public BaseRecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseRecyclerViewViewHolder viewHolder = BaseRecyclerViewViewHolder.getInstance(mContext, parent, mLayoutId);
        if (null == mViewGroup) {
            mViewGroup = parent;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewViewHolder viewHolder, int position) {
        setListener(viewHolder, position);
        convert(viewHolder, mDataList.get(position));
    }


    @Override
    public int getItemCount() {
        return null != mDataList ? mDataList.size() : 0;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    protected int getPosition(BaseRecyclerViewViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }


    private void setListener(BaseRecyclerViewViewHolder viewHolder, final int position) {
        if (isEnabled(getItemViewType(position))) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != mOnItemClickListener) {
                        mOnItemClickListener.onItemClick(mViewGroup, view, mDataList.get(position), position);
                    }
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (null != mOnItemClickListener) {
                        return mOnItemClickListener.onItemLongClick(mViewGroup, mDataList.get(position), position);
                    }
                    return false;
                }
            });
        }
    }

    public abstract void convert(BaseRecyclerViewViewHolder viewHolder, T t);

    public void remove(int position) {
        if (null != mDataList && mDataList.size() > position && position > -1) {
            mDataList.remove(position);
            notifyDataSetChanged();
        }
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setData(List<T> list) {
        if (null != list && !list.isEmpty()) {
            if (null != list) {
                List<T> temp = new ArrayList<>();
                temp.addAll(list);
                mDataList.clear();
                mDataList.addAll(temp);
            } else {
                mDataList.clear();
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 追加数据
     *
     * @param list
     */
    public void addData(List<T> list) {
        if (null != list && !list.isEmpty()) {
            if (null != mDataList) {
                List<T> temp = new ArrayList<>();
                temp.addAll(list);
                mDataList.addAll(temp);
            } else {
                mDataList = list;
            }
            notifyDataSetChanged();
        }
    }

    public List<T> getData() {
        return mDataList;
    }

    public T getItem(int position) {
        return position > -1 && null != mDataList && mDataList.size() > position ? mDataList.get(position) : null;
    }

}
