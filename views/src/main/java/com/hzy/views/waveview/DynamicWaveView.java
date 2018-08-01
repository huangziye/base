package com.hzy.views.waveview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;

/**
 * Created by ziye_huang on 2018/7/19.
 */
public class DynamicWaveView extends View {
    // 波纹颜色
    private static final int WAVE_PAINT_COLOR = 0xfff79e81;
    // y = Asin(wx+b)+h
    private static final float STRETCH_FACTOR_A = 20;
    private static final int OFFSET_Y = 0;
    //水波移动速度
    private static final int TRANSLATE_X_SPEED_TWO = 5;
    private float mCycleFactorW;
    private int mTotalWidth, mTotalHeight;
    private float[] mYPositions;
    private float[] mResetTwoYPositions;
    private int mXOffsetSpeedTwo;
    private int mXTwoOffset;
    private Paint mWavePaint;
    private DrawFilter mDrawFilter;
    private Handler handler;
    private Bitmap cacheBitmap;
    private Timer timer;
    private Runnable r;
    private boolean isRunable;
    private Canvas c;

    public DynamicWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 将dp转化为px，用于控制不同分辨率上移动速度基本一致
        mXOffsetSpeedTwo = dp2px(context, TRANSLATE_X_SPEED_TWO);
        // 初始绘制波纹的画笔
        mWavePaint = new Paint();
        // 去除画笔锯齿
        mWavePaint.setAntiAlias(true);
        // 设置风格为实线
        mWavePaint.setStyle(Style.FILL);
        // 设置画笔颜色
        mWavePaint.setColor(WAVE_PAINT_COLOR);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        c = new Canvas();
        start();

    }

    //停止水波的绘制
    public void stop() {
        isRunable = false;
    }

    //开始绘制
    public void start() {
        if (!isRunable) {
            isRunable = true;
            if (handler == null) {
                handler = new Handler();
            }
            if (r == null) {
                r = new Runnable() {
                    @Override
                    public void run() {
                        if (isRunable) {
                            invalidate();
                            handler.postDelayed(r, 50);
                        }
                    }
                };
            }
            handler.postDelayed(r, 50);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //销毁上一次的对象
        if (cacheBitmap != null && !cacheBitmap.isRecycled()) {
            cacheBitmap.recycle();
            cacheBitmap = null;
        }
        cacheBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        c.setBitmap(cacheBitmap);
        // 从canvas层面去除绘制时锯齿
        c.setDrawFilter(mDrawFilter);
        mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);
        mWavePaint.setStyle(Paint.Style.FILL);
        //        for (int i = mXTwoOffset; i < mTotalWidth; i++) {
        //            // 减210只是为了控制波纹绘制的y的在屏幕的位置,也可以动态改变这个变量，从而形成波纹上升下降效果
        //            // 绘制水波纹
        //            c.drawLine(i-mXTwoOffset, mTotalHeight -(STRETCH_FACTOR_A * (float)Math.sin(mCycleFactorW * i) + OFFSET_Y) - 210, i-mXTwoOffset,
        //                    mTotalHeight,
        //                    mWavePaint);
        //        }
        //        for (int i = 0; i < mXTwoOffset; i++) {
        //            // 减210只是为了控制波纹绘制的y的在屏幕的位置,也可以动态改变这个变量，从而形成波纹上升下降效果
        //            // 绘制水波纹
        //            c.drawLine(i+mTotalWidth-mXTwoOffset, mTotalHeight -(STRETCH_FACTOR_A * (float)Math.sin(mCycleFactorW * i) + OFFSET_Y) - 210, i+mTotalWidth-mXTwoOffset,
        //                    mTotalHeight,
        //                    mWavePaint);
        //
        //        }
        Path path1 = new Path();//梯形
        path1.moveTo(0, mTotalHeight);//绘画基点
        for (int i = mXTwoOffset; i < mTotalWidth; i++) {
            // 减210只是为了控制波纹绘制的y的在屏幕的位置,也可以动态改变这个变量，从而形成波纹上升下降效果
            // 绘制水波纹
            path1.lineTo(i - mXTwoOffset, mTotalHeight - (STRETCH_FACTOR_A * (float) Math.sin(mCycleFactorW * i) + OFFSET_Y) - 210);
        }
        for (int i = 0; i < mXTwoOffset; i++) {
            // 减210只是为了控制波纹绘制的y的在屏幕的位置,也可以动态改变这个变量，从而形成波纹上升下降效果
            // 绘制水波纹
            path1.lineTo(i + mTotalWidth + -mXTwoOffset, mTotalHeight - (STRETCH_FACTOR_A * (float) Math.sin(mCycleFactorW * i) + OFFSET_Y) - 210);

        }
        path1.lineTo(mTotalWidth, mTotalHeight);
        path1.close();
        // 改变波纹的移动点
        mXTwoOffset += mXOffsetSpeedTwo;
        // 如果已经移动到结尾处，则重头记录
        if (mXTwoOffset > mTotalWidth) {
            mXTwoOffset = 0;
        }
        c.drawPath(path1, mWavePaint);
        Paint bmpPaint = new Paint();
        canvas.drawBitmap(cacheBitmap, 0, 0, bmpPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        //这个得到的不应该叫做密度，应该是密度的一个比例。不是真实的屏幕密度，而是相对于某个值的屏幕密度。
        //也可以说是相对密度
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}