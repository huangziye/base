package com.hzy.base.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hzy.base.R;
import com.hzy.utils.BackgroundSwitchUtil;
import com.hzy.utils.BlurBitmapUtil;
import com.hzy.utils.ViewUtil;
import com.hzy.views.recycler.SpeedCardAdapter;
import com.hzy.views.recycler.SpeedCardScaleHelper;
import com.hzy.views.recycler.SpeedCardViewHolder;
import com.hzy.views.recycler.SpeedRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziye_huang on 2018/9/10.
 */
public class RecyclerViewCardGalleryFragment extends Fragment {

    List<Integer> mList = new ArrayList<>();
    SpeedRecyclerView mRecyclerView;
    ImageView mBlurView;
    Runnable mBlurRunnable;
    SpeedCardScaleHelper mSpeedCardScaleHelper;
    private int mLastPos = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_card_gallery, container, false);
        mRecyclerView = (SpeedRecyclerView) ViewUtil.getView(view, R.id.speedRecyclerView);
        mBlurView = (ImageView) ViewUtil.getView(view, R.id.blurView);
        init();
        return view;
    }


    private void init() {
        for (int i = 0; i < 10; i++) {
            mList.add(R.drawable.pic4);
            mList.add(R.drawable.pic5);
            mList.add(R.drawable.pic6);
        }

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new SpeedCardAdapter(getActivity(), mList, R.layout.item_card_gallery) {

            @Override
            public void convert(SpeedCardViewHolder viewHolder, Object o) {
                ViewUtil.setImageResource(viewHolder.itemView,R.id.imageView, (Integer) o);
            }
        });
        // mRecyclerView绑定scale效果
        mSpeedCardScaleHelper = new SpeedCardScaleHelper();
        mSpeedCardScaleHelper.setCurrentItemPos(2);
        mSpeedCardScaleHelper.attachToRecyclerView(mRecyclerView);

        initBlurBackground();
    }

    private void initBlurBackground() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    notifyBackgroundChange();
                }
            }
        });

        notifyBackgroundChange();
    }

    private void notifyBackgroundChange() {
        if (mLastPos == mSpeedCardScaleHelper.getCurrentItemPos())
            return;
        mLastPos = mSpeedCardScaleHelper.getCurrentItemPos();
        final int resId = mList.get(mSpeedCardScaleHelper.getCurrentItemPos());
        mBlurView.removeCallbacks(mBlurRunnable);
        mBlurRunnable = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
                if (null != bitmap) {
                    BackgroundSwitchUtil.startSwitchBackgroundAnim(mBlurView, BlurBitmapUtil.getBlurBitmap(mBlurView.getContext(), bitmap, 15));
                }
            }
        };
        mBlurView.postDelayed(mBlurRunnable, 500);
    }
}
