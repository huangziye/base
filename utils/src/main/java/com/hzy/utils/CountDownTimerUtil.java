package com.hzy.utils;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * 倒计时
 * Created by ziye_huang on 2018/7/27.
 */
public final class CountDownTimerUtil extends CountDownTimer {

    private TextView mTextView;
    private String mFinishMsg;
    /**
     * 倒计时进行中文本颜色
     */
    @ColorInt
    private int mTickTextColor = -1;
    /**
     * 倒计时进行中文本背景颜色
     */
    @DrawableRes
    private int mTickBgColor = -1;
    /**
     * 正常状态情况下文本颜色
     */
    @ColorInt
    private int mInitTextColor = -1;
    /**
     * 正常状态情况下文本背景颜色
     */
    @DrawableRes
    private int mInitBgColor = -1;

    public CountDownTimerUtil(long millisInFuture, long countDownInterval, TextView mTextView) {
        super(millisInFuture, countDownInterval);
        this.mTextView = mTextView;
    }


    @Override
    public void onTick(long millisUntilFinished) {
        if (null != mTextView) {
            //设置不可点击
            mTextView.setClickable(false);
            if (-1 != mTickTextColor) {
                mTextView.setTextColor(mTickTextColor);
            }
            if (-1 != mTickBgColor) {
                Drawable drawable = mTextView.getBackground();
                if (null != drawable) {
                    if (drawable instanceof GradientDrawable) {
                        ((GradientDrawable) drawable).setColor(mTickBgColor);
                    } else if (drawable instanceof ColorDrawable) {
                        ((ColorDrawable) drawable).setColor(mTickBgColor);
                    }
                    mTextView.setBackgroundDrawable(drawable);
                } else {
                    mTextView.setBackgroundResource(mTickBgColor);
                }
            }
            //设置倒计时时间
            mTextView.setText(millisUntilFinished / 1000 + "s");
        }
    }

    @Override
    public void onFinish() {
        if (null != mTextView) {
            if (-1 != mInitTextColor) {
                mTextView.setTextColor(mInitTextColor);
            }
            if (-1 != mInitBgColor) {
                Drawable drawable = mTextView.getBackground();
                if (null != drawable) {
                    if (drawable instanceof GradientDrawable) {
                        ((GradientDrawable) drawable).setColor(mInitBgColor);
                    } else if (drawable instanceof ColorDrawable) {
                        ((ColorDrawable) drawable).setColor(mInitBgColor);
                    }
                    mTextView.setBackgroundDrawable(drawable);
                } else {
                    mTextView.setBackgroundResource(mInitBgColor);
                }
            }
            mTextView.setText(!TextUtils.isEmpty(mFinishMsg) ? mFinishMsg : "重新获取");
            mTextView.setClickable(true);//重新获得点击
        }
    }

    public void close() {
        this.cancel();
    }

    public String getFinishMsg() {
        return mFinishMsg;
    }

    public void setFinishMsg(String msg) {
        this.mFinishMsg = msg;
    }

    public int getTickTextColor() {
        return mTickTextColor;
    }

    public void setTickTextColor(int textColor) {
        this.mTickTextColor = textColor;
    }

    public int getInitTextColor() {
        return mInitTextColor;
    }

    public void setInitTextColor(int textColor) {
        this.mInitTextColor = textColor;
    }

    public int getTickBgColor() {
        return mTickBgColor;
    }

    public void setTickBgColor(int mtickBgColor) {
        this.mTickBgColor = mtickBgColor;
    }

    public int getInitBgColor() {
        return mInitBgColor;
    }

    public void setInitBgColor(int initBgColor) {
        this.mInitBgColor = initBgColor;
    }
}
