package com.hzy.tools;

import java.io.Closeable;
import java.io.IOException;

/**
 * 关闭Closeable对象工具类
 * Created by ziye_huang on 2018/7/17.
 */
public final class CloseUtil {

    private CloseUtil() {
        throw new AssertionError("No Instance.");
    }

    /**
     * 关闭Closeable对象
     *
     * @param closeables
     */
    public static void closeQuielty(Closeable... closeables) {
        if (null != closeables) {
            for (Closeable closeable : closeables) {
                try {
                    if (null != closeable) {
                        closeable.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 关闭Closeable对象
     *
     * @param closeables
     * @throws IOException
     */
    public static void close(Closeable... closeables) throws IOException {
        if (null != closeables) {
            for (Closeable closeable : closeables) {
                if (null != closeable) {
                    closeable.close();
                }
            }
        }
    }

}
