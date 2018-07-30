package com.hzy.base.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ziye_huang on 2018/7/26.
 */
public class GuideAdapter extends PagerAdapter {

    private List<View> mDataList;

    public GuideAdapter(List<View> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //向ViewPager中添加一个ImageView
        View view = mDataList.get(position);
        container.addView(view);
        //把添加的ImageView返回回去
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
