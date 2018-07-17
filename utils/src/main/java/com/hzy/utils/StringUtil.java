package com.hzy.utils;

import android.text.TextUtils;

/**
 * 字符串相关工具类
 * Created by ziye_huang on 2018/7/17.
 */
public final class StringUtil {
    private StringUtil() {
        throw new AssertionError("No Instance.");
    }

    /**
     * 安全显示字符串
     *
     * @param message       要显示的内容
     * @param beginIndex    要替换开始下标
     * @param endIndex      要替换结束下标
     * @param replaceSymbol 替换符号
     * @return
     */
    public static String securityDisplayString(String message, int beginIndex, int endIndex, String replaceSymbol) {
        if (TextUtils.isEmpty(message)) {
            return "";
        }
        if (beginIndex > endIndex) {
            return message;
        }
        if (message.length() < beginIndex || message.length() < endIndex) {
            return message;
        }
        return message.replace(message.substring(beginIndex, endIndex), replaceSymbol);
    }

}
