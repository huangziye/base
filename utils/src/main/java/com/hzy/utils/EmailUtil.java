package com.hzy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 验证邮箱
 * Created by ziye_huang on 2018/7/17.
 */
public final class EmailUtil {

    private EmailUtil() {
        throw new AssertionError("No Instance.");
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = compile(check);
            Matcher matcher = regex.matcher(email);
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

}
