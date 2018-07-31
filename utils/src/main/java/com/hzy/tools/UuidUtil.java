package com.hzy.tools;

import java.util.Random;
import java.util.UUID;

/**
 * UUID工具类
 * Created by ziye_huang on 2018/7/17.
 */
public final class UuidUtil {

    private UuidUtil() {
        throw new AssertionError("No Instance.");
    }

    public static String getUuid() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        uuidStr = uuidStr.replace("-", "");
        return uuidStr;
    }

    public static String getStr(String name) {
        return name.replace("\\", "");
    }

    public static String getPid() {
        String time = System.currentTimeMillis() + UuidUtil.getRandomNumber(3);
        return String.valueOf(time.substring(0, 5) + time.substring(time.length() - 5, time.length()));
    }

    public static String getId() {
        Random random = new Random();
        int r = random.nextInt(8999) + 1000;
        return String.valueOf(System.currentTimeMillis() / 1000) + r;
    }

    /**
     * 获得0-9,a-z,A-Z范围的随机数
     *
     * @param length 随机数长度
     * @return String
     */
    public static String getRandomChar(int length) {
        char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(chr[random.nextInt(62)]);
        }
        return buffer.toString();
    }

    /**
     * 随机获取6为字符串作为盐值加密
     *
     * @return
     */
    public static String getRandomChar() {
        return getRandomChar(6);
    }


    public static String getRandomNumber(int digCount) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(digCount);
        for (int i = 0; i < digCount; i++)
            sb.append((char) ('0' + rnd.nextInt(10)));
        return sb.toString();
    }
}
