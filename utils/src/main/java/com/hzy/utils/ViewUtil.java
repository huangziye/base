package com.hzy.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.util.Linkify;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by ziye_huang on 2018/8/31.
 */
public final class ViewUtil {

    private ViewUtil() {
        throw new AssertionError("No Instance.");
    }

    /**
     * 设置文本
     *
     * @param view   布局文件 View 对象
     * @param viewId
     * @param text
     */
    public static void setText(@NonNull View view, @IdRes int viewId, CharSequence text) {
        View v = view.findViewById(viewId);
        if (v instanceof TextView) {
            TextView tv = (TextView) v;
            tv.setText(text);
        }
    }

    /**
     * 设置文本
     *
     * @param view
     * @param viewId
     * @param resId
     */
    public static void setText(@NonNull View view, @IdRes int viewId, @StringRes int resId) {
        View v = view.findViewById(viewId);
        if (v instanceof TextView) {
            TextView tv = (TextView) v;
            tv.setText(resId);
        }
    }

    public static void setTextColor(@NonNull View view, @IdRes int viewId, @ColorInt int color) {
        View v = view.findViewById(viewId);
        if (v instanceof TextView) {
            TextView tv = (TextView) v;
            tv.setTextColor(color);
        }
    }

    public static void setTextColorRes(@NonNull View view, @IdRes int viewId, @ColorRes int colorRes) {
        View v = view.findViewById(viewId);
        if (v instanceof TextView) {
            TextView tv = (TextView) v;
            tv.setTextColor(view.getContext().getResources().getColor(colorRes));
        }
    }

    public static void setSelected(@NonNull View view, @IdRes int viewId, boolean selected) {
        View v = view.findViewById(viewId);
        v.setSelected(selected);
    }

    public static void setChecked(@NonNull View view, @IdRes int viewId, boolean checked) {
        View v = view.findViewById(viewId);
        if (v instanceof Checkable) {
            ((Checkable) v).setChecked(checked);
        }
    }

    public static void setImageResource(@NonNull View view, @IdRes int viewId, @DrawableRes int resId) {
        View v = view.findViewById(viewId);
        if (v instanceof ImageView) {
            ImageView iv = (ImageView) v;
            iv.setImageResource(resId);
        }
    }

    public static void setImageBitmap(@NonNull View view, @IdRes int viewId, Bitmap bitmap) {
        View v = view.findViewById(viewId);
        if (v instanceof ImageView) {
            ImageView iv = (ImageView) v;
            iv.setImageBitmap(bitmap);
        }
    }

    public static void setImageDrawable(@NonNull View view, @IdRes int viewId, Drawable drawable) {
        ImageView iv = view.findViewById(viewId);
        iv.setImageDrawable(drawable);
    }

    public static void setBackgroundColor(@NonNull View view, @IdRes int viewId, @ColorInt int color) {
        View v = view.findViewById(viewId);
        v.setBackgroundColor(color);
    }

    public static void setBackgroundResource(@NonNull View view, @IdRes int viewId, @DrawableRes int colorRes) {
        View v = view.findViewById(viewId);
        v.setBackgroundResource(colorRes);
    }

    @TargetApi(16)
    public static void setBackground(@NonNull View view, @IdRes int viewId, Drawable drawable) {
        View v = view.findViewById(viewId);
        v.setBackground(drawable);
    }

    /**
     * 对整个view的透明度（包括它的子view）进行设置
     *
     * @param view
     * @param viewId
     * @param alpha  0 ～1
     *               0 完全透明
     *               1 不透明
     */
    public static void setAlpha(@NonNull View view, @IdRes int viewId, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        View v = view.findViewById(viewId);
        v.setAlpha(alpha);
    }

    /**
     * 对View的背景透明度设置
     *
     * @param view
     * @param viewId
     * @param alpha  0 ～255
     *               0 完全透明
     *               255 不透明
     */
    public static void setBackgroundAlpha(@NonNull View view, @IdRes int viewId, @IntRange(from = 0, to = 255) int alpha) {
        View v = view.findViewById(viewId);
        Drawable background = v.getBackground();
        if (null != background) {
            background.setAlpha(alpha);
        }
    }

    public static void setVisible(@NonNull View view, @IdRes int viewId, boolean visible) {
        View v = view.findViewById(viewId);
        v.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public static void linkify(@NonNull View view, @IdRes int viewId) {
        View v = view.findViewById(viewId);
        if (v instanceof TextView) {
            TextView tv = (TextView) v;
            Linkify.addLinks(tv, Linkify.ALL);
            TextUtil.stripUnderlines(tv);
        }
    }

    /**
     * @param view
     * @param viewId
     * @param linkTextColorRes 超链接字体颜色
     */
    public static void linkify(@NonNull View view, @IdRes int viewId, @ColorInt int linkTextColorRes) {
        View v = view.findViewById(viewId);
        if (v instanceof TextView) {
            TextView tv = (TextView) v;
            tv.setLinkTextColor(linkTextColorRes);
            Linkify.addLinks(tv, Linkify.ALL);
            TextUtil.stripUnderlines(tv);
        }
    }

    /**
     * 设置字体
     *
     * @param view
     * @param viewId
     * @param typeface
     */
    public static void setTypeface(@NonNull View view, @IdRes int viewId, Typeface typeface) {
        View v = view.findViewById(viewId);
        if (v instanceof TextView) {
            ((TextView) v).setTypeface(typeface);
        }
    }

    public static void setProgress(@NonNull View view, @IdRes int viewId, int progress) {
        View v = view.findViewById(viewId);
        if (v instanceof ProgressBar) {
            ((ProgressBar) v).setProgress(progress);
        }
    }

    public static void setProgress(@NonNull View view, @IdRes int viewId, int progress, int max) {
        View v = view.findViewById(viewId);
        if (v instanceof ProgressBar) {
            ProgressBar progressBar = (ProgressBar) v;
            progressBar.setMax(max);
            progressBar.setProgress(progress);
        }
    }

    public static void setRating(@NonNull View view, @IdRes int viewId, int rating) {
        View v = view.findViewById(viewId);
        if (v instanceof RatingBar) {
            ((RatingBar) v).setRating(rating);
        }
    }

    public static void setRating(@NonNull View view, @IdRes int viewId, int rating, int max) {
        View v = view.findViewById(viewId);
        if (v instanceof RatingBar) {
            RatingBar ratingBar = (RatingBar) v;
            ratingBar.setMax(max);
            ratingBar.setRating(rating);
        }
    }

    public static void setTag(@NonNull View view, @IdRes int viewId, Object tag) {
        View v = view.findViewById(viewId);
        v.setTag(tag);
    }

    public static void setTag(@NonNull View view, @IdRes int viewId, int key, Object tag) {
        View v = view.findViewById(viewId);
        v.setTag(key, tag);
    }

    public static void setOnClickListener(@NonNull View view, @IdRes int viewId, View.OnClickListener onClickListener) {
        View v = view.findViewById(viewId);
        v.setOnClickListener(onClickListener);
    }

    public static void setOnLongClickListener(@NonNull View view, @IdRes int viewId, View.OnLongClickListener onLongClickListener) {
        View v = view.findViewById(viewId);
        v.setOnLongClickListener(onLongClickListener);
    }

    public static void setOnTouchListener(@NonNull View view, @IdRes int viewId, View.OnTouchListener onTouchListener) {
        View v = view.findViewById(viewId);
        v.setOnTouchListener(onTouchListener);
    }

    /**
     * 设置透明度（这是窗体本身的透明度，非背景）
     *
     * @param activity
     * @param alpha
     */
    public static void setWindowAlpha(Activity activity, @FloatRange(from = 0.0f, to = 1.0f) float alpha) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }

    /**
     * 设置黑暗度
     *
     * @param activity
     * @param dimAcount 0.0f 完全不暗，1.0f 全暗
     */
    public static void setWindowDimAcount(Activity activity, @FloatRange(from = 0.0f, to = 1.0f) float dimAcount) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.dimAmount = dimAcount;
        window.setAttributes(layoutParams);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 设置背景模糊
     *
     * @param activity
     */
    public static void setWindowBackgroundDim(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }

}
