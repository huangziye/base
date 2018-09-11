package com.hzy.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.TextViewCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * 吐司相关工具类
 * Created by ziye_huang on 2018/7/17.
 */
public class ToastUtil {

    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static Toast sToast;
    private static WeakReference<View> sViewWeakReference;
    private static int sLayoutId = -1;
    private static int gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private static int xOffset = 0;
    private static int yOffset = 0;
    private static int bgColor = COLOR_DEFAULT;
    private static int bgResource = -1;
    private static int msgColor = COLOR_DEFAULT;

    private ToastUtil() {
        throw new AssertionError("No Instance.");
    }

    /**
     * 设置吐司位置
     *
     * @param gravity 位置
     * @param xOffset x偏移
     * @param yOffset y偏移
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        ToastUtil.gravity = gravity;
        ToastUtil.xOffset = xOffset;
        ToastUtil.yOffset = yOffset;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor 背景色
     */
    public static void setBgColor(@ColorInt final int backgroundColor) {
        ToastUtil.bgColor = backgroundColor;
    }

    /**
     * 设置背景资源
     *
     * @param bgResource 背景资源
     */
    public static void setBgResource(@DrawableRes final int bgResource) {
        ToastUtil.bgResource = bgResource;
    }

    /**
     * 设置消息颜色
     *
     * @param msgColor 颜色
     */
    public static void setMsgColor(@ColorInt final int msgColor) {
        ToastUtil.msgColor = msgColor;
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShort(Context context, @NonNull final CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShort(Context context, @StringRes final int resId) {
        show(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShort(Context context, @StringRes final int resId, final Object... args) {
        show(context, resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShort(Context context, final String format, final Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    public static void showLong(Context context, @NonNull final CharSequence text) {
        show(context, text, Toast.LENGTH_LONG);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLong(Context context, @StringRes final int resId) {
        show(context, resId, Toast.LENGTH_LONG);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLong(Context context, @StringRes final int resId, final Object... args) {
        show(context, resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLong(Context context, final String format, final Object... args) {
        show(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 安全地显示短时自定义吐司
     */
    public static View showCustomShort(Context context, @LayoutRes final int layoutId) {
        final View view = getView(context, layoutId);
        show(context, view, Toast.LENGTH_SHORT);
        return view;
    }

    /**
     * 安全地显示长时自定义吐司
     */
    public static View showCustomLong(Context context, @LayoutRes final int layoutId) {
        final View view = getView(context, layoutId);
        show(context, view, Toast.LENGTH_LONG);
        return view;
    }

    /**
     * 取消吐司显示
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }

    private static void show(Context context, @StringRes final int resId, final int duration) {
        show(context.getResources().getText(resId).toString(), duration);
    }

    private static void show(Context context, @StringRes final int resId, final int duration, final Object... args) {
        show(String.format(context.getResources().getString(resId), args), duration);
    }

    private static void show(final String format, final int duration, final Object... args) {
        show(String.format(format, args), duration);
    }

    private static void show(final Context context, final CharSequence text, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                sToast = Toast.makeText(context, text, duration);
                // solve the font of toast
                TextView tvMessage = (TextView) sToast.getView().findViewById(android.R.id.message);
                TextViewCompat.setTextAppearance(tvMessage, android.R.style.TextAppearance);
                tvMessage.setTextColor(msgColor);
                setBgAndGravity(context);
                sToast.show();
            }
        });
    }

    private static void show(final Context context, final View view, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                sToast = new Toast(context);
                sToast.setView(view);
                sToast.setDuration(duration);
                setBgAndGravity(context);
                sToast.show();
            }
        });
    }

    private static void setBgAndGravity(Context context) {
        yOffset = (int) (64 * context.getResources().getDisplayMetrics().density + 0.5);
        View toastView = sToast.getView();
        if (bgResource != -1) {
            toastView.setBackgroundResource(bgResource);
        } else if (bgColor != COLOR_DEFAULT) {
            Drawable background = toastView.getBackground();
            background.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));
        }
        sToast.setGravity(gravity, xOffset, yOffset);
    }

    private static View getView(Context context, @LayoutRes final int layoutId) {
        if (sLayoutId == layoutId) {
            if (sViewWeakReference != null) {
                final View toastView = sViewWeakReference.get();
                if (toastView != null) {
                    return toastView;
                }
            }
        }
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View toastView = inflate.inflate(layoutId, null);
        sViewWeakReference = new WeakReference<>(toastView);
        sLayoutId = layoutId;
        return toastView;
    }
}
