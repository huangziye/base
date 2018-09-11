package com.hzy.util;

import android.content.Context;

/**
 * Created by ziye_huang on 2018/9/10.
 */
public class ScreenUtil {

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
