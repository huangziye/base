package com.hzy.utils;

import static java.util.regex.Pattern.matches;

/**
 * 验证邮编
 * Created by ziye_huang on 2018/7/17.
 */
public final class PostcodeUtil {

    private PostcodeUtil() {
        throw new AssertionError("No Instance.");
    }

    /**
     * 验证邮编
     *
     * @param postcode
     * @return
     */
    public static boolean checkPostcode(String postcode) {
        String reg = "[1-9]\\d{5}";
        return matches(reg, postcode);
    }

}
