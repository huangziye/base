package com.hzy.utils;

import android.util.Base64;

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
}
