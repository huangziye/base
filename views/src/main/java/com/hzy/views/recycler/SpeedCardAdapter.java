package com.hzy.views.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzy.views.stackview.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziye_huang on 2018/9/10.
 */
public abstract class SpeedCardAdapter<T> extends RecyclerView.Adapter<SpeedCardViewHolder> {
    protected Context mContext;
    protected List<T> mDataList;
    protected int mLayoutId;
    protected ViewGroup mViewGroup;
    protected SpeedCardAdapterHelper mSpeedCardAdapterHelper;
    protected OnItemClickListener mOnItemClickListener;

    public SpeedCardAdapter(Context context, List<T> mList, @LayoutRes int layoutId) {
        mContext = context;
        mDataList = mList;
        mLayoutId = layoutId;
        mSpeedCardAdapterHelper = new SpeedCardAdapterHelper();
    }

    @Override
    public SpeedCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        mSpeedCardAdapterHelper.onCreateViewHolder(parent, itemView);
        SpeedCardViewHolder viewHolder = SpeedCardViewHolder.getInstance(mContext, parent, mLayoutId);
        if (null == mViewGroup) {
            mViewGroup = parent;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SpeedCardViewHolder holder, final int position) {
        mSpeedCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        setListener(holder, position);
        convert(holder, mDataList.get(position));
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

    protected int getPosition(SpeedCardViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }


    private void setListener(SpeedCardViewHolder viewHolder, final int position) {
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

    public abstract void convert(SpeedCardViewHolder viewHolder, T t);
}
