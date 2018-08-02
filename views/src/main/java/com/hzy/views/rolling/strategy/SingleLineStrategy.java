package com.hzy.views.rolling.strategy;

import android.text.BoringLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;

import com.hzy.views.rolling.VerticalRollingTextView;


/**
 * 单行显示
 * Created by ziye_huang on 2018/5/3.
 */

public class SingleLineStrategy implements IStrategy {
    @Override
    public VerticalRollingTextView.LayoutWithTextSize getLayout(float autoSizeMinTextSizeInPx, float autoSizeMaxTextSizeInPx, float autoSizeStepGranularityInPx, int wantTextSize, int width, int height, TextPaint paint, int maxLines, CharSequence text, TextUtils.TruncateAt truncateAt) {

        BoringLayout.Metrics metrics = BoringLayout.isBoring(text, paint);

        Layout layout = new BoringLayout(text, paint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, metrics, false, truncateAt, width);

        VerticalRollingTextView.LayoutWithTextSize lt = new VerticalRollingTextView.LayoutWithTextSize();
        lt.layout = layout;
        lt.textSize = wantTextSize;
        return lt;
    }

    @Override
    public void reset() {

    }
}