package com.hzy.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片辅助类
 * Created by ziye_huang on 2018/7/17.
 */
public class ImageUtil {

    private ImageUtil() {
        throw new AssertionError("No Instance.");
    }

    /**
     * 图片转base64字符串
     *
     * @param imgPath
     * @return
     */
    public static String file2Base64Str(String imgPath) {
        return file2Base64Str(new File(imgPath));
    }

    /**
     * 图片转base64字符串
     *
     * @param file
     * @return
     */
    public static String file2Base64Str(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            fis.read(buffer);
            fis.close();
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * base64字符串转file
     *
     * @param base64Str
     * @param saveFilePath
     * @return
     */
    public static File base64Str2File(String base64Str, String saveFilePath) {
        try {
            byte[] buffer = Base64.decode(base64Str, Base64.DEFAULT);
            FileOutputStream fos = new FileOutputStream(saveFilePath);
            fos.write(buffer);
            fos.close();
            return new File(saveFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将图片base64转换为Bitmap
     *
     * @param image
     * @return
     */
    public static Bitmap base642bitmap(String image) {
        if (TextUtils.isEmpty(image)) {
            return null;
        }
        byte[] bytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 调整图片大小
     *
     * @param bitmap     要调整的图片
     * @param outputFile 调整后的图片
     * @param maxWidth   调整后的图片的最大宽度
     * @param maxHeight  调整后的图片的最大高度
     */
    public static void resize(Bitmap bitmap, File outputFile, int maxWidth, int maxHeight) {
        try {
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();
            // 图片大于最大高宽，按大的值缩放
            if (bitmapWidth > maxHeight || bitmapHeight > maxWidth) {
                float widthScale = maxWidth * 1.0f / bitmapWidth;
                float heightScale = maxHeight * 1.0f / bitmapHeight;

                float scale = Math.min(widthScale, heightScale);
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
            }
            Log.i("APIService", "upload face size" + bitmap.getWidth() + "*" + bitmap.getHeight());
            // save image
            FileOutputStream out = new FileOutputStream(outputFile);
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调整图片的大小
     *
     * @param inputPath  要调整图片的路径
     * @param outputPath 调整后图片的路径
     * @param dstWidth   调整后图片的宽度
     * @param dstHeight  调整后图片的高度
     */
    public static void resize(String inputPath, String outputPath, int dstWidth, int dstHeight) {
        try {
            int inWidth;
            int inHeight;

            // decode image size (decode metadata only, not the whole image)
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(inputPath, options);

            // save width and height
            inWidth = options.outWidth;
            inHeight = options.outHeight;
            Log.i("APIService", "origin " + inWidth + " " + inHeight);

            Matrix m = new Matrix();
            int rotation = ExifUtil.getBitmapDegree(inputPath);
            if (rotation != 0) {
                m.preRotate(rotation);
            }

            int maxPreviewImageSize = Math.max(dstWidth, dstHeight);
            int size = Math.min(options.outWidth, options.outHeight);
            size = Math.min(size, maxPreviewImageSize);

            options = new BitmapFactory.Options();
            options.inSampleSize = ImageUtil.calculateInSampleSize(options, size, size);
            options.inScaled = true;
            options.inDensity = options.outWidth;
            options.inTargetDensity = size * options.inSampleSize;

            Bitmap roughBitmap = BitmapFactory.decodeFile(inputPath, options);
            roughBitmap = Bitmap.createBitmap(roughBitmap, 0, 0, roughBitmap.getWidth(), roughBitmap.getHeight(), m, false);
            // save image
            FileOutputStream out = new FileOutputStream(outputPath);
            try {
                roughBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * 保存Bitmap为文件
     *
     * @param outputPath
     * @param bitmap
     */
    public static void saveBitmap(String outputPath, Bitmap bitmap) {
        // save image
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(outputPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 缩放图片到指定宽高
     *
     * @param bitmap
     * @param dstWidth
     * @param dstHeight
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int dstWidth, int dstHeight) {
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) dstWidth) / width;
        float scaleHeight = ((float) dstHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left;
        float top;
        float right;
        float bottom;
        float dstLeft;
        float dstTop;
        float dstRight;
        float dstBottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dstLeft = 0;
            dstTop = 0;
            dstRight = width;
            dstBottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dstLeft = 0;
            dstTop = 0;
            dstRight = height;
            dstBottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dstLeft, (int) dstTop, (int) dstRight, (int) dstBottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }


    /**
     * 裁剪图片
     *
     * @param bitmap
     * @param needWidth
     * @param needHeight
     * @return
     */
    public static Bitmap cropFaceImg(Bitmap bitmap, int needWidth, int needHeight) {
        int x = bitmap.getWidth();
        int y = bitmap.getHeight();
        int left = (x - needWidth) / 2;
        int top = (y - needHeight) / 2;
        return Bitmap.createBitmap(bitmap, left, top, needWidth, needHeight);
    }
}
