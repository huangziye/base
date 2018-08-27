package com.hzy.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证号工具类
 * Created by ziye_huang on 2018/6/4.
 */

public final class IdCardUtil {

    /**
     * 验证身份证号码是否合法
     */
    public static boolean idCardCode(String idCardCode) {
        boolean ret = false;

        if (!TextUtils.isEmpty(idCardCode) && idCardCode.length() == 18) {
            /*
             * 此校验对整体数字，省份，生日，最后一位校验码进行校验
			 */
            int[] citys = {11, 12, 13, 14, 15, 21, 22, 23, 31, 32, 33, 34, 35, 36, 37, 41, 42, 43, 44, 45, 46, 50, 51, 52, 53, 54, 61, 62, 63, 64, 65, 71, 81, 82, 91};

            char checkId = idCardCode.charAt(17);

            String regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}[X|x|0-9]$";
            boolean rs = match(regex, idCardCode);
            if (rs) {
                int provinceId = Integer.parseInt(idCardCode.substring(0, 2));
                for (int i = 0; i < citys.length; i++) {
                    if (provinceId == citys[i]) {
                        String idCardSub = idCardCode.substring(0, 17);
                        char[] idCardNum = idCardSub.toCharArray();
                        // 身份证校验位生成密钥
                        int[] nums = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                        // 用于结果对比
                        char[] results = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
                        int sum = 0;
                        for (int j = 0; j < idCardNum.length; j++) {
                            int k = idCardNum[j] - '0';
                            sum = sum + k * nums[j];
                        }
                        if (checkId == results[sum % 11] || checkId == ((results[sum % 11] == 'X') ? 'x' : results[sum % 11])) {
                            ret = true;
                        }
                    }
                }
            }
        }
        return ret;
    }

    private static boolean match(String pattern, String target) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(target);
        return matcher.matches();
    }

}
