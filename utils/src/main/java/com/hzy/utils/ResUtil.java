package com.hzy.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * 资源相关工具类
 * Created by ziye_huang on 2018/7/17.
 */
public class ResUtil {

    private ResUtil() {
        throw new AssertionError("No Instance.");
    }

    /**
     * 根据资源ID获取颜色
     *
     * @param colorId
     * @return
     */
    public static int getColor(Context context, @ColorRes int colorId) {
        return context.getResources().getColor(colorId);
    }

    /**
     * 根据资源ID获取字符串
     *
     * @param strId
     * @return
     */
    public static String getString(Context context, @StringRes int strId) {
        return context.getResources().getString(strId);
    }

    /**
     * 根据资源ID获取Drawable
     *
     * @param context
     * @param drawable
     * @return
     */
    public static Drawable getDrawable(Context context, @DrawableRes int drawable) {
        return context.getResources().getDrawable(drawable);
    }

    /**
     * 根据资源ID获取dimen
     *
     * @param context
     * @param dimen
     * @return
     */
    public static int getDimen(Context context, @DimenRes int dimen) {
        return context.getResources().getDimensionPixelSize(dimen);
    }

}
