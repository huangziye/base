package com.hzy.base.fragment;

import android.os.Handler;

import com.hzy.base.recycler.RecyclerItemCell;
import com.hzy.base.recycler.RecyclerItemEntity;
import com.hzy.views.recycler.base.Cell;
import com.hzy.views.recycler.fragment.AbsBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziye_huang on 2018/8/13.
 */
public class RecyclerFragment extends AbsBaseFragment<RecyclerItemEntity> {

    private int pageIndex = 0;
    private int pageSize = 10;

    @Override
    public void onRecyclerViewInitialized() {
        pageIndex = 0;
        loadData();
    }

    @Override
    public void onPullRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex = 0;
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);

    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex++;
                loadMore();
            }
        }, 3000);

    }

    @Override
    protected List<Cell> getCells(List<RecyclerItemEntity> list) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            cells.add(new RecyclerItemCell(list.get(i)));
        }
        return cells;
    }

    private void loadData() {
        mBaseAdapter.showLoading();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBaseAdapter.hideLoading();
                List<RecyclerItemEntity> data = getData(pageIndex);
                if(null == data || data.isEmpty()) {
                    mBaseAdapter.showEmpty();
                    return;
                }
                mBaseAdapter.addAll(getCells(data));
            }
        }, 3000);

    }

    private void loadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadMore();
                List<RecyclerItemEntity> data = getData(pageIndex);
                if (null == data || data.isEmpty()) {
                    mBaseAdapter.showLoadNoMore();
                    mBaseAdapter.hideLoading();
                    return;
                }
                mBaseAdapter.addAll(getCells(data));
            }
        }, 3000);
    }

    private List<RecyclerItemEntity> getData(int pageIndex) {
        List<RecyclerItemEntity> list = new ArrayList<>();
        if (pageIndex >= 2) {
            return list;
        }
        for (int i = pageIndex * pageSize; i < pageIndex * pageSize + pageSize; i++) {
            list.add(new RecyclerItemEntity("item" + i));
        }
        return list;
    }
}
