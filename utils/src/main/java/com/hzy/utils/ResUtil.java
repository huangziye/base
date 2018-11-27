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

    /********************************************** 在将自己写的工具打成.jar包的时候,有时候会需要引用到res中的资源,这时候不能将资源一起打包,只能通过反射机制动态的获取资源. ****************************************************/

    /**
     * 获取layout的ID
     *
     * @param context
     * @param resourceName 资源ID名
     * @return
     */
    public static int getLayoutId(Context context, String resourceName) {
        return getResourceId(context, resourceName, "layout");
    }

    /**
     * 获取资源字符串的ID
     *
     * @param context
     * @param resourceName 资源名称
     * @return
     */
    public static int getStringId(Context context, String resourceName) {
        return getResourceId(context, resourceName, "string");
    }

    /**
     * 获取drawable的ID
     *
     * @param context
     * @param resourceName 资源名称
     * @return
     */
    public static int getDrawableId(Context context, String resourceName) {
        return getResourceId(context, resourceName, "drawable");
    }

    /**
     * 获取style的ID
     *
     * @param context
     * @param resourceName 资源名称
     * @return
     */
    public static int getStyleId(Context context, String resourceName) {
        return getResourceId(context, resourceName, "style");
    }

    /**
     * 获取id
     *
     * @param context
     * @param resourceName 资源名称
     * @return
     */
    public static int getId(Context context, String resourceName) {
        return getResourceId(context, resourceName, "id");
    }

    /**
     * 获取color的ID
     *
     * @param context
     * @param resourceName 资源名称
     * @return
     */
    public static int getColorId(Context context, String resourceName) {
        return getResourceId(context, resourceName, "color");
    }

    /**
     * 获取array的ID
     *
     * @param context
     * @param resourceName 资源名称
     * @return
     */
    public static int getArrayId(Context context, String resourceName) {
        return getResourceId(context, resourceName, "array");
    }

    /**
     * @param context
     * @param resourceName 资源ID名
     * @param defType      资源属性的类型
     * @return
     */
    public static int getResourceId(Context context, String resourceName, String defType) {
        return context.getResources().getIdentifier(resourceName, defType, context.getPackageName());
    }

}
