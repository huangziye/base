package com.hzy.utils;


/**
 * 银行卡号合法性校验
 * <p>
 * 银行卡是由”发卡行标识代码 + 自定义 + 校验码 “等部分组成的。
 * 银联标准卡与以往发行的银行卡最直接的区别就是其卡号前6位数字的不同。
 * 银行卡卡号的前6位是用来表示发卡银行或机构的，称为“发卡行识别码”(Bank Identification Number，缩写为“BIN”)。
 * <p>
 * 校验过程：
 * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
 * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
 * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
 */
public class BankCardUtil {

    private BankCardUtil() {
        throw new AssertionError("No Instance.");
    }

    /**
     * 校验银行卡卡号
     */
    public static boolean checkBankCard(String bankCard) {
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeBankCard
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0 || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

}