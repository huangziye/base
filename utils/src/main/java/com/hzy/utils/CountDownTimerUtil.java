package com.hzy.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * 倒计时
 * Created by ziye_huang on 2018/7/27.
 */
public class CountDownTimerUtil extends CountDownTimer {

    private Context mContext;
    private TextView mTextView;
    private String mFinishMsg;
    /**
     * 倒计时前缀信息
     */
    private String mPrefixMsg = "";
    /**
     * 倒计时进行中文本颜色
     */
    @ColorRes
    private int mTickTextColor = -1;
    /**
     * 倒计时进行中文本背景颜色
     */
    @ColorRes
    private int mTickBgColor = -1;
    /**
     * 正常状态情况下文本颜色
     */
    @ColorRes
    private int mInitTextColor = -1;
    /**
     * 正常状态情况下文本背景颜色
     */
    @ColorRes
    private int mInitBgColor = -1;

    public CountDownTimerUtil(Context mContext, long millisInFuture, long countDownInterval, TextView mTextView) {
        super(millisInFuture, countDownInterval);
        this.mContext = mContext;
        this.mTextView = mTextView;
    }

    public CountDownTimerUtil(Context mContext, long millisInFuture, long countDownInterval, TextView mTextView, String prefixMsg) {
        super(millisInFuture, countDownInterval);
        this.mContext = mContext;
        this.mTextView = mTextView;
        this.mPrefixMsg = prefixMsg;
    }


    @Override
    public void onTick(long millisUntilFinished) {
        if (null != mTextView) {
            //设置不可点击
            mTextView.setClickable(false);
            if (-1 != mTickTextColor) {
                mTextView.setTextColor(mContext.getResources().getColor(mTickTextColor));
            }
            if (-1 != mTickBgColor) {
                Drawable drawable = mTextView.getBackground();
                if (null != drawable && drawable instanceof GradientDrawable) {
                    ((GradientDrawable) drawable).setColor(mContext.getResources().getColor(mTickBgColor));
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
                mTextView.setTextColor(mContext.getResources().getColor(mInitTextColor));
            }
            if (-1 != mInitBgColor) {
                Drawable drawable = mTextView.getBackground();
                if (null != drawable && drawable instanceof GradientDrawable) {
                    ((GradientDrawable) drawable).setColor(mContext.getResources().getColor(mInitBgColor));
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

    public TextView getTextView() {
        return mTextView;
    }

    public void setTextView(TextView textView) {
        mTextView = textView;
    }
}
