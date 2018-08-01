package com.hzy.utils;

import java.io.File;
import java.text.DecimalFormat;

/**
 * File相关辅助类
 * Created by ziye_huang on 2018/7/17.
 */
public final class FileUtil {

    private FileUtil() {
        throw new AssertionError("No Instance.");
    }

    /**
     * 删除文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        if (file.isFile()) {
            if (file.exists()) {
                return file.delete();
            }
            return false;
        }
        return false;
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     */
    public static long getFileSize(File file) {
        if (file.isFile()) {
            if (file.exists()) {
                return file.length();
            }
            return 0;
        }
        return 0;
    }

    /**
     * 列出目录下所有的文件
     *
     * @param file
     * @return
     */
    public static File[] listFiles(File file) {
        if (file.isDirectory()) {
            if (file.exists()) {
                return file.listFiles();
            }
            return null;
        }
        return null;
    }

    /**
     * 格式化文件大小
     *
     * @param fileSize
     * @return
     */
    public static String formatFileSizeString(long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < (1024 * 1024)) {
            fileSizeString = df.format((double) fileSize / 1024) + "K";
        } else if (fileSize < (1024 * 1024 * 1024)) {
            fileSizeString = df.format((double) fileSize / (1024 * 1024)) + "M";
        } else {
            fileSizeString = df.format((double) fileSize / (1024 * 1024 * 1024)) + "G";
        }
        return fileSizeString;
    }
}