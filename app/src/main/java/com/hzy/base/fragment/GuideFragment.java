package com.hzy.base.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzy.base.MainActivity;
import com.hzy.base.R;
import com.hzy.base.adapter.GuideAdapter;
import com.hzy.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页面（以fragment的方式）
 * Created by ziye_huang on 2018/7/23.
 */
public class GuideFragment extends Fragment {

    private TextView mTvExperience;
    private ViewPager mViewPager;
    private LinearLayout mLLGuidePointGroup;
    private View mRedPointView;

    private List<View> mImageViewList;
    // 点与点之间的距离
    private int mPointDistance;

    public GuideFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.include_guide_content, container, false);
        initView(view);
        return view;
    }

    /**
     * 初始化数据
     */
    private void initView(View view) {
        mTvExperience = view.findViewById(R.id.tv_experience);
        mViewPager = view.findViewById(R.id.viewPager);
        mLLGuidePointGroup = view.findViewById(R.id.ll_guide_point_group);
        mRedPointView = view.findViewById(R.id.red_point);

        mTvExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo
                ((MainActivity) getActivity()).guideCallbck();
            }
        });

        //初始化数据
        initPageData();

        mViewPager.setAdapter(new GuideAdapter(mImageViewList));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 当页面滚动时触发此方法
             * @param position 当前正在被选中的position
             * @param positionOffset 页面移动的比值
             * @param positionOffsetPixels 页面移动的像素
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //点的最终距离 = 间距 * 比值
                int leftMargin = (int) (mPointDistance * (position + positionOffset));
                //把间距设置给红色的点
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRedPointView.getLayoutParams();
                layoutParams.leftMargin = leftMargin;
                mRedPointView.setLayoutParams(layoutParams);
            }

            /**
             * 当页面选中时触发此方法
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                if (position == mImageViewList.size() - 1) {
                    mTvExperience.setVisibility(View.VISIBLE);
                } else {
                    mTvExperience.setVisibility(View.GONE);
                }
            }

            /**
             * 当页面滚动状态改变时触发此方法
             * @param state
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //measure -> layout -> draw
        //获得视图树观察者，观察当整个布局的layout开始布局时的事件
        ViewTreeObserver viewTreeObserver = mRedPointView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /**
             * 当全局开始布局layout时回调此方法
             */
            @Override
            public void onGlobalLayout() {
                //此方法只需要执行一次就可以：把当前的监听从视图树中移除掉，以后就不会再回调此事件
                mRedPointView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //点的距离 = 第1个点的左边 - 第0个点的左边
                mPointDistance = mLLGuidePointGroup.getChildAt(1).getLeft() - mLLGuidePointGroup.getChildAt(0).getLeft();
            }
        });

    }

    private void initPageData() {
        int[] imageResIds = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
        mImageViewList = new ArrayList<>();
        ImageView iv;
        for (int i = 0; i < imageResIds.length; i++) {
            iv = new ImageView(getActivity());
            iv.setBackgroundResource(imageResIds[i]);
            mImageViewList.add(iv);

            //向线性布局中添加一个灰色的点
            View view = new View(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dp2px(getActivity(), 10), DensityUtil.dp2px(getActivity(), 10));
            if (0 != i) {
                params.leftMargin = DensityUtil.dp2px(getActivity(), 10);
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.gray_point);
            mLLGuidePointGroup.addView(view);
        }
    }
}
