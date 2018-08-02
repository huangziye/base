package com.hzy.views.rolling.strategy;

import android.text.TextPaint;
import android.text.TextUtils;

import com.hzy.views.rolling.VerticalRollingTextView;


/**
 * 文字布局策略
 * Created by ziye_huang on 2018/5/3.
 */

public interface IStrategy {
    VerticalRollingTextView.LayoutWithTextSize getLayout(float autoSizeMinTextSizeInPx, float autoSizeMaxTextSizeInPx, float autoSizeStepGranularityInPx, int wantTextSize, int width, int height, TextPaint paint, int maxLines, CharSequence text, TextUtils.TruncateAt mTruncateAt);

    void reset();
}
