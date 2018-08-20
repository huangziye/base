package com.hzy.views.recycler.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hzy.views.R;
import com.hzy.views.recycler.base.BaseCell;
import com.hzy.views.recycler.base.BaseViewHolder;

/**
 * Created by ziye_huang on 2018/8/10.
 */
public abstract class AbsStateCell extends BaseCell<Object> {
    protected View mView;
    protected int mHeight = 0;

    public AbsStateCell(Object o) {
        super(o);
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public void setView(View view) {
        mView = view;
    }

    @Override
    public void releaseResource() {
        if (null != mView) {
            mView = null;
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_state_layout, null);
        //如果调用者没有设置显示的View就用默认的View
        if (null == mView) {
            mView = getDefaultView(parent.getContext());
        }
        if (null != mView) {
            LinearLayout container = (LinearLayout) view.findViewById(R.id.rv_cell_state_root_layout);
            container.removeAllViews();
            container.addView(mView);
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (mHeight > 0) {
            params.height = mHeight;
        }
        view.setLayoutParams(params);
        return new BaseViewHolder(view);
    }

    /**
     * 子类提供的默认布局，当没有通过{@link #setView(View)}设置显示的View的时候，就显示默认的View
     *
     * @return 返回默认布局
     */
    protected abstract View getDefaultView(Context context);
}
