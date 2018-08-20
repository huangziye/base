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
public class LoadMoreCell extends AbsStateCell {
    public static final int mDefaultHeight = 45;//dp

    public LoadMoreCell(Object o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return SimpleAdapter.LOAD_MORE_TYPE;
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    protected View getDefaultView(Context context) {
        // 设置LoadMore View显示的默认高度
        setHeight(dp2px(context, mDefaultHeight));
        return LayoutInflater.from(context).inflate(R.layout.rv_load_more_layout, null);
    }

    public static int dp2px(Context context, float dpValue) {
        //这个得到的不应该叫做密度，应该是密度的一个比例。不是真实的屏幕密度，而是相对于某个值的屏幕密度。
        //也可以说是相对密度
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
