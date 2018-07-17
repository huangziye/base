package com.hzy.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

/**
 * 资源相关工具类
 * Created by ziye_huang on 2018/7/17.
 */
public final class ResUtil {

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

}
