package com.hzy.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzy.base.R;
import com.hzy.views.pattern.PatternLockView;
import com.hzy.views.pattern.listener.PatternLockViewListener;
import com.hzy.views.pattern.util.PatternLockUtil;
import com.hzy.views.pattern.util.ResourceUtil;

import java.util.List;

/**
 * Created by ziye_huang on 2018/8/27.
 */
public class PatternLockFragment extends Fragment {

    private TextView profileName;
    /**
     * 第一次创建的手势
     */
    private String firstCreatePattern;
    /**
     * 设置手势错误最大次数
     */
    private int checkTimes = 3;
    /**
     * 保存成功后的手势
     */
    private String locakKey = "1478";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pattern_lock, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        profileName = view.findViewById(R.id.profile_name);

        mPatternLockView = (PatternLockView) view.findViewById(R.id.patter_lock_view);
        //设置横纵坐标点的个数
        mPatternLockView.setDotCount(3);
        //设置未选中点的大小
        mPatternLockView.setDotNormalSize((int) ResourceUtil.getDimensionInPx(getActivity(), R.dimen.pattern_lock_dot_size));
        //设置选中时点的大小
        mPatternLockView.setDotSelectedSize((int) ResourceUtil.getDimensionInPx(getActivity(), R.dimen.pattern_lock_dot_selected_size));
        //设置路径线的宽度
        mPatternLockView.setPathWidth((int) ResourceUtil.getDimensionInPx(getActivity(), R.dimen.pattern_lock_path_width));
        //设置宽高比是否启用
        mPatternLockView.setAspectRatioEnabled(true);
        //设置宽高比
        mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
        //设置View的模式
        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        //设置点动画持续时间
        mPatternLockView.setDotAnimationDuration(150);

        // 设置Pat结束动画持续时间
        mPatternLockView.setPathEndAnimationDuration(100);
        //设置正确的状态颜色
        mPatternLockView.setCorrectStateColor(ResourceUtil.getColor(getActivity(), R.color.white));
        //是否设置为隐身模式
        mPatternLockView.setInStealthMode(false);
        //设置是否启用触觉反馈
        mPatternLockView.setTactileFeedbackEnabled(true);
        //设置输入是否启用
        mPatternLockView.setInputEnabled(true);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);
    }

    /**
     * 创建手势图案
     *
     * @param pattern
     */
    private void createPattern(List<PatternLockView.Dot> pattern) {
        if (pattern.size() < 4) {
            profileName.setText("至少需要4个点，请重试");
            mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
            mPatternLockView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPatternLockView.clearPattern();
                    firstCreatePattern = "";
                    profileName.setText(R.string.draw_to_unlock_again);
                }
            }, 1500);
            return;
        }

        //获取手势
        String patternStr = PatternLockUtil.patternToString(mPatternLockView, pattern);

        if (TextUtils.isEmpty(firstCreatePattern)) {
            firstCreatePattern = patternStr;
            mPatternLockView.clearPattern();
            profileName.setText(R.string.confirm_drawing_the_unlock_pattern);
        } else {
            if (patternStr.equals(firstCreatePattern)) {
                //保存手势
                profileName.setText("手势设置成功");
                mPatternLockView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPatternLockView.clearPattern();
                        firstCreatePattern = "";
                    }
                }, 800);
            } else {
                firstCreatePattern = "";
                profileName.setText(R.string.redraw);
                mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                mPatternLockView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPatternLockView.clearPattern();
                        profileName.setText(R.string.draw_to_unlock_again);
                    }
                }, 1500);
            }
        }
    }

    private void checkPattern(List<PatternLockView.Dot> pattern) {
        String patternStr = PatternLockUtil.patternToString(mPatternLockView, pattern);
        checkTimes--;

        if (0 == checkTimes) {
            profileName.setText("账号已锁定");
            return;
        }


        if (patternStr.equals(locakKey)) {
            profileName.setText("校验成功");
        } else {
            if (checkTimes > 0) {
                profileName.setText("你还有" + checkTimes + "次机会");
                mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                mPatternLockView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPatternLockView.clearPattern();
                    }
                }, 1500);
            } else {
                if (0 == checkTimes) {
                    profileName.setText("账号已锁定");
                    return;
                }

            }

        }
    }

    private PatternLockView mPatternLockView;

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d(getClass().getName(), "Pattern drawing started");
            profileName.setText("绘制解锁图案");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(getClass().getName(), "Pattern progress: " + PatternLockUtil.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            Log.d(getClass().getName(), "Pattern complete: " + PatternLockUtil.patternToString(mPatternLockView, pattern));
            //            createPattern(pattern);
            checkPattern(pattern);
        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };
}
