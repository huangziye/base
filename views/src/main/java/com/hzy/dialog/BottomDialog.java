package com.hzy.dialog;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 底部对话框
 * Created by ziye_huang on 2018/7/18.
 */
public abstract class BottomDialog extends BaseDialog {

    @Override
    protected int setWidth() {
        return getScreenWidth(getActivity());
    }

    @Override
    protected int setHeight() {
        return getScreenHeight(getActivity()) * 3 / 5;
    }

    @Override
    protected int setGravity() {
        return Gravity.BOTTOM;
    }

    /**
     * 重写此方法，设置布局文件
     */
    protected abstract View createView(LayoutInflater inflater, ViewGroup container);

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
