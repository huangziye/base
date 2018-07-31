package com.hzy.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzy.base.adapter.GuideAdapter;
import com.hzy.tools.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziye_huang on 2018/7/26.
 */
public class GuideActivity extends AppCompatActivity {

    private TextView mTvExperience;
    private ViewPager mViewPager;
    private LinearLayout mLLGuidePointGroup;
    private View mRedPointView;

    private List<View> mImageViewList;
    // 点与点之间的距离
    private int mPointDistance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        mTvExperience = findViewById(R.id.tv_experience);
        mViewPager = findViewById(R.id.viewPager);
        mLLGuidePointGroup = findViewById(R.id.ll_guide_point_group);
        mRedPointView = findViewById(R.id.red_point);

        mTvExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo
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
            iv = new ImageView(this);
            iv.setBackgroundResource(imageResIds[i]);
            mImageViewList.add(iv);

            //向线性布局中添加一个灰色的点
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dp2px(this, 10), DensityUtil.dp2px(this, 10));
            if (0 != i) {
                params.leftMargin = DensityUtil.dp2px(this, 10);
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.gray_point);
            mLLGuidePointGroup.addView(view);
        }
    }
}
