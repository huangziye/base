package com.hzy.utils;

import android.net.Uri;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串相关工具类
 * Created by ziye_huang on 2018/7/17.
 */
public class StringUtil {

    /**
     * 匹配中文
     */
    private static String zhPattern = "[\\u4e00-\\u9fa5]";

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

    /**
     * 对字符串中的中文进行URL编码
     *
     * @param cnString 被替换的字符串
     * @param charset  字符集
     * @return
     * @throws UnsupportedEncodingException 不支持的字符集
     */
    public static String encode(String cnString, String charset) throws UnsupportedEncodingException {
        Pattern p = Pattern.compile(zhPattern);
        Matcher m = p.matcher(cnString);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, URLEncoder.encode(m.group(0), charset));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 使用UTF-8模式将给定字符串中的字符编码为'%'-转义的八位元组。
     * 叶子字母(“a - z”、“a - z”),数字(0 - 9)和无限制的字符(“_ - ! ~()*”)完好无损。
     * 对所有其他字符进行编码，允许参数中指定的字符除外。
     *
     * @param url
     * @return
     */
    public static String encodeUrl(String url) {
        return Uri.encode(url, "-![.:/,%?&=]");
    }

}
