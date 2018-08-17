package com.hzy.views.stackview;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

/**
 * Created by ziye_huang on 2018/8/17.
 */
public class RecyclerViewItemTouchHelperCallback<T> extends ItemTouchHelper.SimpleCallback {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<T> mDataList;

    public RecyclerViewItemTouchHelperCallback(RecyclerView rv, RecyclerView.Adapter adapter, List<T> data) {
        this(0, ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, rv, adapter, data);
    }

    /**
     * @param dragDirs  表示拖拽的方向，有六个类型的值：LEFT、RIGHT、START、END、UP、DOWN, 如果为0，则表示不触发该操作（滑动or拖拽）
     * @param swipeDirs 表示拖拽的方向，有六个类型的值：LEFT、RIGHT、START、END、UP、DOWN, 如果为0，则表示不触发该操作（滑动or拖拽）
     */
    public RecyclerViewItemTouchHelperCallback(int dragDirs, int swipeDirs, RecyclerView rv, RecyclerView.Adapter adapter, List data) {
        super(dragDirs, swipeDirs);
        mRecyclerView = rv;
        mAdapter = adapter;
        mDataList = data;
    }


    @Override

    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = 0;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof RecyclerViewLayoutManager) {
            swipeFlags = ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 还有一点需要注意，前面说过，为了防止第二层和第三层卡片也能滑动，因此我们需要设置 isItemViewSwipeEnabled() 返回 false 。
     *
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }


    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // 得到滑动的阀值
            float ratio = dX / getThreshold(viewHolder);
            // ratio 最大为 1 或 -1
            if (ratio > 1) {
                ratio = 1;
            } else if (ratio < -1) {
                ratio = -1;
            }

            // 默认最大的旋转角度为 15 度
            itemView.setRotation(ratio * Config.DEFAULT_ROTATE_DEGREE);
            int childCount = recyclerView.getChildCount();

            // 当数据源个数大于最大显示数时
            if (childCount > Config.DEFAULT_SHOW_ITEM) {
                for (int position = 1; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    // 和之前 onLayoutChildren 是一个意思，不过是做相反的动画
                    view.setScaleX(1 - index * Config.DEFAULT_SCALE + Math.abs(ratio) * Config.DEFAULT_SCALE);
                    view.setScaleY(1 - index * Config.DEFAULT_SCALE + Math.abs(ratio) * Config.DEFAULT_SCALE);
                    view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / Config.DEFAULT_TRANSLATE_Y);
                }
            } else {

                // 当数据源个数小于或等于最大显示数时
                for (int position = 0; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    view.setScaleX(1 - index * Config.DEFAULT_SCALE + Math.abs(ratio) * Config.DEFAULT_SCALE);
                    view.setScaleY(1 - index * Config.DEFAULT_SCALE + Math.abs(ratio) * Config.DEFAULT_SCALE);
                    view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / Config.DEFAULT_TRANSLATE_Y);
                }

            }
        }
    }

    /**
     * 拖拽时回调的方法
     * <p>
     * 接下来，就是去重写 onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) 和 onSwiped(RecyclerView.ViewHolder viewHolder, int direction) 方法。
     * 但是因为在上面我们对于 dragFlags 配置的是 0 ，所以在 onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) 中直接返回 false 即可
     *
     * @param recyclerView
     * @param viewHolder   拖动的ViewHolder
     * @param viewHolder1  目标位置的ViewHolder
     * @return
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    /**
     * 滑动时回调的方法
     *
     * @param viewHolder 滑动的ViewHolder
     * @param direction  滑动的方向
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // 移除之前设置的 onTouchListener, 否则触摸滑动会乱了
        viewHolder.itemView.setOnTouchListener(null);

        // 删除相对应的数据
        int layoutPosition = viewHolder.getLayoutPosition();
        T remove = mDataList.remove(layoutPosition);
        mDataList.add(mDataList.size(), remove);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 水平方向是否可以被回收掉的阈值
     *
     * @param viewHolder
     * @return
     */
    private float getThreshold(RecyclerView.ViewHolder viewHolder) {
        return mRecyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }

    /**
     * 发现还是有问题，第一层的卡片滑出去之后第二层的就莫名其妙地偏了。这正是因为 Item View 重用机制“捣鬼”。所以我们应该在 clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) 方法中重置一下：
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setRotation(0f);
    }
}
