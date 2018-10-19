package com.hzy.utils;

import android.media.ExifInterface;
import android.util.Log;

import java.io.IOException;

/**
 * 可交换图像文件（Exchangeable Image File）
 * <p>
 * String getAttribute(String tag)：获取图片中属性为tag的字符串值。
 * double getAttribute(String tag,double defaultValue)：获取图片中属性为tag的double值。
 * int getAttributeInt(String tag,defaultValue)：获取图片中属性为tag的int值。
 * void setAttribute(String tag,String value)：根据输入参数，设定图片Exif的值。
 * void saveAttrubutes()：把内存中图片的Exif写入到图片中
 * <p>
 * 可以看到，上面大部分方法操作了一个String类型的tag参数，此为Exif的属性，在ExifInterface中定义了一些字符串的静态常量表示这些tag值，常用如下：
 * <p>
 * TAG_APERTURE：光圈值。
 * TAG_DATETIME：拍摄时间，取决于设备设置的时间。
 * TAG_EXPOSURE_TIME：曝光时间。
 * TAG_FLASH：闪光灯。
 * TAG_FOCAL_LENGTH：焦距。
 * TAG_IMAGE_LENGTH：图片高度。
 * TAG_IMAGE_WIDTH：图片宽度。
 * TAG_ISO：ISO。
 * TAG_MAKE：设备品牌。
 * TAG_MODEL：设备型号，整形表示，在ExifInterface中有常量对应表示。
 * TAG_ORIENTATION：旋转角度，整形表示，在ExifInterface中有常量对应表示。
 * <p>
 * Created by ziye_huang on 2018/10/19.
 */
public class ExifUtil {

    private static final String TAG = "ExifUtil";

    /**
     * 获取图片的旋转角度信息(EXIF信息)
     *
     * @param imagePath 图片的sd卡路径
     * @return
     */
    public static int getBitmapDegree(String imagePath) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(imagePath);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * Returns the degrees in clockwise. Values are 0, 90, 180, or 270.
     * 按顺时针方向返回度数。值是0、90、180或270。
     *
     * @param jpeg
     * @return
     */
    public static int getOrientation(byte[] jpeg) {
        if (jpeg == null) {
            return 0;
        }

        int offset = 0;
        int length = 0;

        // ISO/IEC 10918-1:1993(E)
        while (offset + 3 < jpeg.length && (jpeg[offset++] & 0xFF) == 0xFF) {
            int marker = jpeg[offset] & 0xFF;

            // Check if the marker is a padding.
            if (marker == 0xFF) {
                continue;
            }
            offset++;

            // Check if the marker is SOI or TEM.
            if (marker == 0xD8 || marker == 0x01) {
                continue;
            }
            // Check if the marker is EOI or SOS.
            if (marker == 0xD9 || marker == 0xDA) {
                break;
            }

            // Get the length and check if it is reasonable.
            length = pack(jpeg, offset, 2, false);
            if (length < 2 || offset + length > jpeg.length) {
                Log.e(TAG, "Invalid length");
                return 0;
            }

            // Break if the marker is EXIF in APP1.
            if (marker == 0xE1 && length >= 8 && pack(jpeg, offset + 2, 4, false) == 0x45786966 && pack(jpeg, offset + 6, 2, false) == 0) {
                offset += 8;
                length -= 8;
                break;
            }

            // Skip other markers.
            offset += length;
            length = 0;
        }

        // JEITA CP-3451 Exif Version 2.2
        if (length > 8) {
            // Identify the byte order.
            int tag = pack(jpeg, offset, 4, false);
            if (tag != 0x49492A00 && tag != 0x4D4D002A) {
                Log.e(TAG, "Invalid byte order");
                return 0;
            }
            boolean littleEndian = (tag == 0x49492A00);

            // Get the offset and check if it is reasonable.
            int count = pack(jpeg, offset + 4, 4, littleEndian) + 2;
            if (count < 10 || count > length) {
                Log.e(TAG, "Invalid offset");
                return 0;
            }
            offset += count;
            length -= count;

            // Get the count and go through all the elements.
            count = pack(jpeg, offset - 2, 2, littleEndian);
            while (count-- > 0 && length >= 12) {
                // Get the tag and check if it is orientation.
                tag = pack(jpeg, offset, 2, littleEndian);
                if (tag == 0x0112) {
                    // We do not really care about type and count, do we?
                    int orientation = pack(jpeg, offset + 8, 2, littleEndian);
                    switch (orientation) {
                        case 1:
                            return 0;
                        case 3:
                            return 180;
                        case 6:
                            return 90;
                        case 8:
                            return 270;
                        default:
                            return 0;
                    }
                }
                offset += 12;
                length -= 12;
            }
        }

        Log.i(TAG, "Orientation not found");
        return 0;
    }

    private static int pack(byte[] bytes, int offset, int length, boolean littleEndian) {
        int step = 1;
        if (littleEndian) {
            offset += length - 1;
            step = -1;
        }

        int value = 0;
        while (length-- > 0) {
            value = (value << 8) | (bytes[offset] & 0xFF);
            offset += step;
        }
        return value;
    }
}
