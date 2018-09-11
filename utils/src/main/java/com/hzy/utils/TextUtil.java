package com.hzy.utils;

import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.widget.TextView;

/**
 * Created by ziye_huang on 2018/9/3.
 */
public class TextUtil {

    /**
     * 设置不显示下划线
     *
     * @param textView
     */
    public static void stripUnderlines(TextView textView) {
        Spannable s = (Spannable) textView.getText();
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span : spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            s.setSpan(span, start, end, 0);
        }
        textView.setText(s);
    }

    public static class URLSpanNoUnderline extends URLSpan {

        public URLSpanNoUnderline(String url) {
            super(url);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

}
