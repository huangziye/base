package com.hzy.views.rolling;

import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;

/**
 * Created by ziye_huang on 2018/5/3.
 */

public class StaticLayoutHelper {
    public static StaticLayout createStaticLayout(CharSequence source, TextPaint paint, int width, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad, TextUtils.TruncateAt ellipsize, int ellipsizedWidth, int maxLines) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder layoutBuilder = StaticLayout.Builder.obtain(source, 0, source.length(), paint, width);

            return layoutBuilder.setAlignment(align).setLineSpacing(spacingadd, spacingmult).setIncludePad(includepad).setMaxLines(maxLines).setEllipsize(ellipsize).setEllipsizedWidth(ellipsizedWidth).setTextDirection(TextDirectionHeuristics.FIRSTSTRONG_LTR).build();
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    return StaticLayout.class.getConstructor(CharSequence.class, int.class, int.class, TextPaint.class, int.class, Layout.Alignment.class, TextDirectionHeuristic.class, float.class, float.class, boolean.class, TextUtils.TruncateAt.class, int.class, int.class).newInstance(source, 0, source.length(), paint, width, align, TextDirectionHeuristics.FIRSTSTRONG_LTR, spacingmult, spacingadd, includepad, ellipsize, ellipsizedWidth, maxLines);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new StaticLayout(source, 0, source.length(), paint, width, align, spacingmult, spacingadd, includepad, ellipsize, ellipsizedWidth);
    }
}
