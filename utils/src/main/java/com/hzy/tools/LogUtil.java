package com.hzy.tools;

import android.util.Log;

/**
 * 日志相关工具类
 * Created by ziye_huang on 2018/7/17.
 */
public final class LogUtil {
    //设为false关闭日志
    private static boolean enable = true;

    private LogUtil() {
        throw new AssertionError("No Instance.");
    }

    public static void setEnable(boolean enable) {
        LogUtil.enable = enable;
    }

    public static void i(String tag, String msg) {
        if (enable) {
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (enable) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (enable) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (enable) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (enable) {
            Log.e(tag, msg);
        }
    }
}
