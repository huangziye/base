package com.hzy.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ziye_huang on 2018/9/10.
 */
public class BitmapUtil {

    /**
     * 根据设置的宽高缩放图片
     *
     * @param context
     * @param resId
     * @param reqWidth  压缩后的宽度
     * @param reqHeight 压缩后的高度
     * @return
     */
    public static Bitmap scaleImage(Context context, @DrawableRes int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //将BitmapFactory.Option的inJustDecodeBound参数设为true，加载图片，这个时候图片并没有加载进内存，仅仅是去解析图片原始宽高信息而已
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        //将BitmapFactory.Option的inJustDecodeBound参数设为false，重新加载图片，这时候图片才真正被载进内存
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }

    /**
     * 根据设置的宽高缩放图片
     *
     * @param pathName  本地图片路径
     * @param reqWidth  压缩后的宽度
     * @param reqHeight 压缩后的高度
     * @return
     */
    public static Bitmap scaleImage(String pathName, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //将BitmapFactory.Option的inJustDecodeBound参数设为true，加载图片，这个时候图片并没有加载进内存，仅仅是去解析图片原始宽高信息而已
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        //将BitmapFactory.Option的inJustDecodeBound参数设为false，重新加载图片，这时候图片才真正被载进内存
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(pathName, options);
    }

    /**
     * 计算采样率（inSampleSize）
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //从BitmapFactory.Option取出图片的原始宽高信息，对应于outWidth，outHeight参数
        //原始图片宽
        int width = options.outWidth;
        //原始图片高
        int height = options.outHeight;
        //采样率
        int inSampleSize = 1;

        //原始的宽比目标宽大，或者原始高比目标高大
        if (width > reqWidth || height > reqHeight) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);
            inSampleSize = Math.max(widthRadio, heightRadio);
        }
        return inSampleSize;
    }

    /**
     * 保存bitmap
     *
     * @param bitmap
     * @param filePath
     * @return
     */
    public static boolean saveBitmap(Bitmap bitmap, String filePath) {
        File file = new File(filePath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
