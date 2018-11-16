package com.hzy.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.List;
import java.util.Locale;

/**
 * Intent相关辅助类
 * Created by ziye_huang on 2018/7/17.
 */
public class IntentUtil {

    private IntentUtil() {
        throw new AssertionError("No Instance.");
    }

    /**
     * 打开文件
     *
     * @param context
     * @param filePath
     * @return
     */
    public static Intent openFile(Context context, String filePath) {
        File file = new File(filePath);
        if (!file.exists())
            return null;
        /* 取得扩展名 */
        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase(Locale.getDefault());
        /* 依扩展名的类型决定MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            return getAudioFileIntent(context, filePath);
        } else if (end.equals("3gp") || end.equals("mp4")) {
            return getVideoFileIntent(context, filePath);
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            return getImageFileIntent(context, filePath);
        } else if (end.equals("apk")) {
            return getApkFileIntent(context, filePath);
        } else if (end.equals("ppt")) {
            return getPptFileIntent(context, filePath);
        } else if (end.equals("xls")) {
            return getExcelFileIntent(context, filePath);
        } else if (end.equals("doc")) {
            return getWordFileIntent(context, filePath);
        } else if (end.equals("pdf")) {
            return getPdfFileIntent(context, filePath);
        } else if (end.equals("chm")) {
            return getChmFileIntent(context, filePath);
        } else if (end.equals("txt")) {
            return getTextFileIntent(context, filePath, false);
        } else {
            return getAllIntent(context, filePath);
        }
    }

    /**
     * 获取对应文件的Uri
     *
     * @param intent 相应的Intent
     * @param file   文件对象
     * @return
     */
    private static Uri getUri(Context context, Intent intent, File file) {
        Uri uri = null;
        uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //判断版本是否在7.0以上
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    /**
     * Android获取一个用于打开APK文件的intent
     *
     * @param context
     * @param param
     * @return
     */
    public static Intent getAllIntent(Context context, String param) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = getUri(context, intent, new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    /**
     * Android获取一个用于打开APK文件的intent
     *
     * @param context
     * @param param
     * @return
     */
    public static Intent getApkFileIntent(Context context, String param) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = getUri(context, intent, new File(param));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * Android获取一个用于打开VIDEO文件的intent
     *
     * @param context
     * @param param
     * @return
     */
    public static Intent getVideoFileIntent(Context context, String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = getUri(context, intent, new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    /**
     * Android获取一个用于打开AUDIO文件的intent
     *
     * @param context
     * @param param
     * @return
     */
    public static Intent getAudioFileIntent(Context context, String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = getUri(context, intent, new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    /**
     * Android获取一个用于打开Html文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getHtmlFileIntent(String param) {
        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    /**
     * Android获取一个用于打开图片文件的intent
     *
     * @param context
     * @param param
     * @return
     */
    public static Intent getImageFileIntent(Context context, String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //        Uri uri = Uri.fromFile(new File(param));
        Uri uri = getUri(context, intent, new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    /**
     * Android获取一个用于打开PPT文件的intent
     *
     * @param context
     * @param param
     * @return
     */
    public static Intent getPptFileIntent(Context context, String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getUri(context, intent, new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    /**
     * Android获取一个用于打开Excel文件的intent
     *
     * @param context
     * @param param
     * @return
     */
    public static Intent getExcelFileIntent(Context context, String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getUri(context, intent, new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    /**
     * Android获取一个用于打开Word文件的intent
     *
     * @param context
     * @param param
     * @return
     */
    public static Intent getWordFileIntent(Context context, String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getUri(context, intent, new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    /**
     * Android获取一个用于打开CHM文件的intent
     *
     * @param context
     * @param param
     * @return
     */
    public static Intent getChmFileIntent(Context context, String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getUri(context, intent, new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    /**
     * Android获取一个用于打开文本文件的intent
     *
     * @param context
     * @param param
     * @param paramBoolean
     * @return
     */
    public static Intent getTextFileIntent(Context context, String param, boolean paramBoolean) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = getUri(context, intent, new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

    /**
     * Android获取一个用于打开PDF文件的intent
     *
     * @param context
     * @param param
     * @return
     */
    public static Intent getPdfFileIntent(Context context, String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getUri(context, intent, new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    /**
     * 判断是否有app接收这个intent
     * <p>
     * 没有任何一个app会去接收这个intent，则app会crash。
     *
     * @param context
     * @param intent
     * @return true 有app接收这个intent，否则没有。
     */
    public static boolean intentSafe(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }
}
