package com.hzy.anim;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 顺时针、水平方向的半圆周运动
 * <p>
 * android:duration：设置动画持续时间
 * android:fillAfter：如果fillAfter设为true，则动画执行后，控件将停留在动画结束的状态
 * android:fillBefore：如果fillBefore设为true，则动画执行后，控件将回到动画开始的状态
 * android:startOffset(long startOffset)：设置动画执行之前等待的时间（单位：毫秒）
 * android:repeatCount(int repeatCount)：设置动画重复的次数
 * android:interpolator：设置动画的变化速度，其值： ｛
 * android:interpolator="@android:anim/accelerate_decelerate_interpolator"：先加速，后减速
 * <p>
 * android:interpolator="@android:anim/accelerate_interpolator"：加速
 * <p>
 * android:interpolator="@android:anim/decelerate_interpolator"：减速
 * <p>
 * android:interpolator="@android:anim/cycle_Interpolator"：动画循环播放特定次数，速率改变沿着正弦曲线
 * <p>
 * android:interpolator="@android:anim/linear_Interpolator"：匀速
 * <p>
 * Created by ziye_huang on 2018/7/20.
 */
public class SemicircleAnimation extends Animation {

    private final float mFromXValue, mToXValue;
    private float mRadius;

    public SemicircleAnimation(float fromX, float toX) {
        mFromXValue = fromX;
        mToXValue = toX;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);

        float fromXDelta = resolveSize(Animation.RELATIVE_TO_SELF, mFromXValue, width, parentWidth);
        float toXDelta = resolveSize(Animation.RELATIVE_TO_SELF, mToXValue, width, parentWidth);

        // Calculate the radius of the semicircle motion.
        // Note: Radius can be negative here.
        mRadius = (toXDelta - fromXDelta) / 2;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float dx = 0;
        float dy = 0;

        if (interpolatedTime == 0) {
            t.getMatrix().setTranslate(dx, dy);
            return;
        }

        float angleDeg = (interpolatedTime * 180f) % 360;
        float angleRad = (float) Math.toRadians(angleDeg);

        dx = (float) (mRadius * (1 - Math.cos(angleRad)));
        dy = (float) (-mRadius * Math.sin(angleRad));

        t.getMatrix().setTranslate(dx, dy);
    }

}
