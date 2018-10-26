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

    /**
     * 转换为%E4%BD%A0形式
     *
     * @param s
     * @return
     */
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) {
                        k += 256;
                    }
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }


    /**
     * 将%E4%BD%A0转换为汉字
     *
     * @param s
     * @return
     */
    public static String unescape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int l = s.length();
        int ch = -1;
        int b, sumb = 0;
        for (int i = 0, more = -1; i < l; i++) {
            /* Get next byte b from URL segment s */
            switch (ch = s.charAt(i)) {
                case '%':
                    ch = s.charAt(++i);
                    int hb = (Character.isDigit((char) ch) ? ch - '0' : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                    ch = s.charAt(++i);
                    int lb = (Character.isDigit((char) ch) ? ch - '0' : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                    b = (hb << 4) | lb;
                    break;
                case '+':
                    b = ' ';
                    break;
                default:
                    b = ch;
            }
            /* Decode byte b as UTF-8, sumb collects incomplete chars */
            //10xxxxxx (continuation byte)
            if ((b & 0xc0) == 0x80) {
                // Add 6 bits to sumb
                sumb = (sumb << 6) | (b & 0x3f);
                if (--more == 0) {
                    // Add char to sbuf
                    sbuf.append((char) sumb);
                }
            } else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)
                // Store in sbuf
                sbuf.append((char) b);
            } else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)
                sumb = b & 0x1f;
                // Expect 1 more byte
                more = 1;
            } else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)
                sumb = b & 0x0f;
                // Expect 2 more bytes
                more = 2;
            } else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)
                sumb = b & 0x07;
                // Expect 3 more bytes
                more = 3;
            } else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)
                sumb = b & 0x03;
                // Expect 4 more bytes
                more = 4;
            } else /*if ((b & 0xfe) == 0xfc)*/ { // 1111110x (yields 1 bit)
                sumb = b & 0x01;
                // Expect 5 more bytes
                more = 5;
            }
            /* We don't test if the UTF-8 encoding is well-formed */
        }
        return sbuf.toString();
    }

}
