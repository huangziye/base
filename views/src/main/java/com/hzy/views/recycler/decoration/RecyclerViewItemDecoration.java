package com.hzy.views.recycler.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hzy.views.R;


/**
 * Created by ziye_huang on 2018/8/2.
 */
public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;     //分割线Drawable
    private int mDividerHeight;  //分割线高度
    // 最后 mInVisibleNums 条分割线不显示
    private int mInVisibleNums = 0;

    /**
     * 使用line_divider中定义好的颜色
     *
     * @param context
     * @param dividerHeight 分割线高度
     */
    public RecyclerViewItemDecoration(Context context, int dividerHeight) {
        init(context, null, dividerHeight, mInVisibleNums);
    }

    /**
     * @param context
     * @param dividerHeight
     * @param lastInVisible 最后多少条分割线不可见
     */
    public RecyclerViewItemDecoration(Context context, int dividerHeight, int lastInVisible) {
        init(context, null, dividerHeight, lastInVisible);
    }

    /**
     * @param context
     * @param divider       分割线Drawable
     * @param dividerHeight 分割线高度
     */
    public RecyclerViewItemDecoration(Context context, Drawable divider, int dividerHeight) {
        init(context, divider, dividerHeight, mInVisibleNums);
    }

    /**
     * @param context
     * @param dividerDrawable 分割线Drawable
     * @param dividerHeight   分割线高度
     * @param lastInVisible   最后多少条分割线不可见
     */
    private void init(Context context, Drawable dividerDrawable, int dividerHeight, int lastInVisible) {
        mDivider = null == dividerDrawable ? ContextCompat.getDrawable(context, R.drawable.item_divider) : dividerDrawable;
        mDividerHeight = dividerHeight;
        mInVisibleNums = lastInVisible;
    }

    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - mInVisibleNums; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
