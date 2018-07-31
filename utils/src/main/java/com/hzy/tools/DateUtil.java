package com.hzy.tools;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 日期工具类
 * Created by ziye_huang on 2018/7/17.
 */
public final class DateUtil {
    private DateUtil() {
        throw new AssertionError("No Instance.");
    }

    public final static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_DATE = "yyyy-MM-dd";
    public final static String FORMAT_TIME = "HH:mm:ss";
    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public final static String FORMAT_yyyyMMddHHmmssSSS = "YYYYMMddHHmmssSSS";
    public final static String FORMAT_yyyyMMdd = "yyyyMMdd";
    public final static String FORMAT_yyyyMM = "yyyyMM";
    public final static String FORMAT_MONTH = "yyyy-MM";
    public final static String FORMAT_yyyyMMdd_CN = "yyyy年MM月dd日";
    public final static String FORMAT_MMdd_CN = "MM月dd日";

    public static final long HOUR_MILLIS = 1000 * 60 * 60;
    public static final long DAY_MILLIS = HOUR_MILLIS * 24;
    public static final int EIGHT_HOUR_SECOND = 60 * 60 * 8;// 8小时
    public static final long EIGHT_HOUR_MILLI_SECOND = EIGHT_HOUR_SECOND * 1000L;

    /**
     * 字符串日期转Date对象
     *
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static Date stringToDate(String dateStr, String formatStr) {
        try {
            return new SimpleDateFormat(formatStr).parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 时间戳转化时间
     *
     * @param timeStamp 时间戳
     * @return 返回值
     * @title
     */
    public static Date parseDate(Long timeStamp) {
        return parseDate(new SimpleDateFormat(DEFAULT_FORMAT).format(timeStamp));
    }

    public static Date parseDate(String dateStr, String formatStr) {
        try {
            return new SimpleDateFormat(formatStr).parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat(FORMAT_DATE_TIME).parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 格式化时间
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date, FORMAT_DATE_TIME);
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        try {
            return (!TextUtils.isEmpty(format) ? new SimpleDateFormat(FORMAT_DATE_TIME) : new SimpleDateFormat(format)).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getYmdDate(Date date) {
        return getDate(date);
    }

    public static String getChineseDate(Date date) {
        return new SimpleDateFormat(FORMAT_yyyyMMdd_CN).format(date);
    }

    public static String getChineseMD(Date date) {
        return new SimpleDateFormat(FORMAT_MMdd_CN).format(date);
    }

    public static String getDate(Date date) {
        return new SimpleDateFormat(FORMAT_DATE).format(date);
    }

    /**
     * 获得8位数字的日期，年月日 格式 yyyyMMdd
     *
     * @param date
     * @return
     * @Description 获取时间的前8位字符串
     */
    public static String getNumberDate(Date date) {
        return new SimpleDateFormat(FORMAT_yyyyMMdd).format(date);
    }

    /**
     * 获得时间 格式 hh24:mi:ss
     *
     * @param date
     * @return
     */
    public static String getHmsTime(Date date) {
        return getTime(date);
    }

    /**
     * 获得时间 格式 hh24:mi:ss
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        return new SimpleDateFormat(FORMAT_TIME).format(date);
    }

    /**
     * 获得14位数字的日期与时间， 时间 格式 yyyyMMddHHmmss
     *
     * @param date
     * @return
     */
    public static String geNumbertDateTime(Date date) {
        return new SimpleDateFormat(FORMAT_yyyyMMddHHmmss).format(date);
    }

    /**
     * 获得时间 格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getDateTime(Date date) {
        return new SimpleDateFormat(FORMAT_DATE_TIME).format(date);
    }

    /**
     * 获得时间 格式 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return getDateTime(new Date());
    }

    public static String getWeek(Date date) {
        String week = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK);
        switch (w) {
            case 1:
                week = "星期天";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * 根据pattern判断字符串是否为合法日期
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static boolean isValidDate(String dateStr, String pattern) {
        boolean isValid = false;
        if (pattern == null || pattern.length() < 1) {
            pattern = "yyyy-MM-dd";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String date = sdf.format(sdf.parse(dateStr));
            if (date.equalsIgnoreCase(dateStr)) {
                isValid = true;
            }
        } catch (Exception e) {
            isValid = false;
        }
        // 如果目标格式不正确，判断是否是其它格式的日期
        if (!isValid) {
            isValid = isValidDatePatterns(dateStr, "");
        }
        return isValid;
    }

    public static boolean isValidDatePatterns(String dateStr, String patterns) {
        if (patterns == null || patterns.length() < 1) {
            patterns = "yyyy-MM-dd;yyyy-MM-dd HH:mm:ss;dd/MM/yyyy;yyyy/MM/dd;yyyy/M/d h:mm";
        }
        boolean isValid = false;
        String[] patternArr = patterns.split(";");
        for (int i = 0; i < patternArr.length; i++) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(patternArr[i]);
                String date = sdf.format(sdf.parse(dateStr));
                if (date.equalsIgnoreCase(dateStr)) {
                    isValid = true;
                    break;
                }
            } catch (Exception e) {
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * @param date 要修改的时间
     * @param day  天数
     * @param hour 小时
     * @param min  分钟
     * @param sec  秒
     * @return
     * @Description: 设置指定时间的天时分秒 正数+ 负数-
     */
    public static Date setDayTime(Date date, int day, int hour, int min, int sec) {
        Calendar cal = Calendar.getInstance();
        date = addDate(date, day);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, sec);
        return cal.getTime();
    }

    public static String getFirstDatePerMonth() {
        return new SimpleDateFormat(FORMAT_MONTH).format(new Date()) + "-01";
    }

    public static String getFormatDate(Date date, SimpleDateFormat format) {
        if (format == null) {
            format = new SimpleDateFormat(DEFAULT_FORMAT);
        }
        try {
            return format.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取月份
     */
    public static Integer getMonth(Date date) {
        return Integer.parseInt(formatDate(date, FORMAT_yyyyMM));
    }

    /**
     * 获取指定日期
     *
     * @param specifiedDay
     * @param num
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay, int num) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + num);

        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 日期加减天数得到新的日期
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDate(Date date, int day) {
        Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(date);
        fromCal.add(Calendar.DATE, day);
        return fromCal.getTime();

    }

    /**
     * 当前时间减秒数时间
     *
     * @param date
     * @param seconds
     * @return
     */
    public static Date addSeconds(Date date, int seconds) {
        Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(date);
        fromCal.add(Calendar.SECOND, seconds);
        return fromCal.getTime();

    }

    public static Date addMonths(final Date date, final int amount) {
        Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(date);
        fromCal.add(Calendar.MONTH, amount);
        return fromCal.getTime();
    }

    public static Date addDays(final Date date, final int amount) {
        Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(date);
        fromCal.add(Calendar.DAY_OF_MONTH, amount);
        return fromCal.getTime();
    }

    public static Date addYears(final Date date, final int amount) {
        Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(date);
        fromCal.add(Calendar.YEAR, amount);
        return fromCal.getTime();
    }

    /**
     * 字符串的日期格式的计算 .getTime()
     *
     * @throws ParseException
     */
    public static int daysBetween(String smallDate, String bigDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long time1 = sdf.parse(smallDate).getTime();
        long time2 = sdf.parse(bigDate).getTime();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Date str2Date(String str, String format) {
        return parseDate(str, format);
    }

    public static Date str2Date(String str) {
        return parseDate(str);
    }

    public static String date2Str(Date date) {
        return null == date ? "" : formatDate(date, DEFAULT_FORMAT);
    }

    public static String date2Str(Date date, String format) {
        return null == date ? "" : new SimpleDateFormat(format).format(date);
    }

    public static int monthsBetween(String smallDate, String bigDate) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(DateUtil.str2Date(smallDate, FORMAT_MONTH));
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(DateUtil.str2Date(bigDate, FORMAT_MONTH));

        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        return year * 12 + month;
    }

    /**
     * 系统时间戳
     *
     * @return
     */
    public static String currentTimestamp() {
        return "" + System.currentTimeMillis();
    }

    /**
     * yyyyMMddHHmmss
     *
     * @return
     */
    public static String currentTimeMillis0() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /**
     * yyMMddHHmmss
     *
     * @return
     */
    public static String currentTimeMillis1() {
        return new SimpleDateFormat("yyMMddHHmmss").format(new Date());
    }

    /**
     * yyyy/MM/dd HH:mm:ss
     *
     * @return
     */
    public static String currentTimeMillis2() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }

    /**
     * yy/MM/dd HH:mm:ss
     *
     * @return
     */
    public static String currentTimeMillis3() {
        return new SimpleDateFormat("yy/MM/dd HH:mm:ss").format(new Date());
    }

    /**
     * yyyy/MM/dd HH:mm:ss.sss
     *
     * @return
     */
    public static String currentTimeMillis4() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.sss").format(new Date());
    }

    /**
     * yy/MM/dd HH:mm:ss.sss
     *
     * @return
     */
    public static String currentTimeMillis5() {
        return new SimpleDateFormat("yy/MM/dd HH:mm:ss.sss").format(new Date());
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String currentTimeMillisCN1() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * yy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String currentTimeMillisCN2() {
        return new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * yyyy-MM-dd
     *
     * @return
     */
    public static String currentTimeMillisCN3() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * yy-MM-dd
     *
     * @return
     */
    public static String currentTimeMillisCN4() {
        return new SimpleDateFormat("yy-MM-dd").format(new Date());
    }

    /**
     * yyyy-MM-dd HH:mm:ss.sss
     *
     * @return
     */
    public static String currentTimeMillisCN5() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss").format(new Date());
    }

    /**
     * yy-MM-dd HH:mm:ss.sss
     *
     * @return
     */
    public static String currentTimeMillisCN6() {
        return new SimpleDateFormat("yy-MM-dd HH:mm:ss.sss").format(new Date());
    }

    /**
     * yyMMdd
     *
     * @return
     */
    public static String currentDate1() {
        return new SimpleDateFormat("yyMMdd").format(new Date());
    }

    /**
     * yy/MM/dd
     *
     * @return
     */
    public static String currentDate2() {
        return new SimpleDateFormat("yy/MM/dd").format(new Date());
    }

    /**
     * yyyyMMdd
     *
     * @return
     */
    public static String currentDate3() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    /**
     * yyyy/MM/dd
     *
     * @return
     */
    public static String currentDate4() {
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

    /**
     * HHmmss
     *
     * @return
     */
    public static String currentTime1() {
        return new SimpleDateFormat("HHmmss").format(new Date());
    }

    /**
     * HH:mm:ss
     *
     * @return
     */
    public static String currentTime2() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static Date parseStringToDate(String mobileTime) { // mobileTime
        // 页面传过来的时间戳
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = new Long(mobileTime);
        String d = format.format(time);
        try {
            Date date = format.parse(d);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public static Integer getAgeByBirthday(String birthday) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDay;
        try {
            birthDay = sdf.parse(birthday);
        } catch (ParseException e) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            return null;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 获取一个月的天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断两个日期是否是同一天
     */
    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }

    /*
     * 统计两个日期之间的时间集合
     */
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(beginDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);
        return lDate;
    }

    /**
     * 获取当日凌晨零时时间
     *
     * @return
     */
    public static Date getZeroTimeToday() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 精确到毫秒，外加5为随机数
     *
     * @param date
     * @return
     */
    public static String getOutTradeNo(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyyMMddHHmmssSSS);
        int max = 99999;
        int min = 10000;
        int random = (int) (Math.random() * (max - min) + min);
        String serial = sdf.format(date);
        // 生成订单号
        return serial + random;
    }

    /**
     * 精确到毫秒，外加5为随机数
     *
     * @return
     */
    public static String getSerial() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyyMMddHHmmssSSS);
        int max = 99999;
        int min = 10000;
        int random = (int) (Math.random() * (max - min) + min);
        String serial = sdf.format(date);
        // 生成订单号
        return serial + random;
    }

    /**
     * @param date
     * @param timeZoneOffset
     * @return String
     * @Description 本地时间转北京时间
     * @throws:
     */
    public static Date getBeijingDate(Date date, int timeZoneOffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, timeZoneOffset);
        return calendar.getTime();
    }

    public static int getDayCount(Date date1, Date date2) {
        int dayCount1 = getDayCount(date1);
        int dayCount2 = getDayCount(date2);
        return (dayCount1 - dayCount2);
    }

    /**
     * 获取天数
     *
     * @param date 日期
     * @return 天数
     */
    public static int getDayCount(final Date date) {
        return getDayCount(date.getTime());
    }

    /**
     * 获取天数
     *
     * @param time 日期
     * @return 天数
     */
    public static int getDayCount(final long time) {
        long daynum = (time + EIGHT_HOUR_MILLI_SECOND) / DAY_MILLIS;
        return (int) daynum;
    }

    /**
     * 获取时
     *
     * @param date 日期
     * @return 时
     */
    public static int getHour(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        return cld.get(Calendar.HOUR_OF_DAY);
    }

    public static Date getBeijingTodayBegin() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String datetime = dateFormat.format(new Date());
        String dateStr = datetime.substring(0, 10);
        try {
            return dateFormat.parse(dateStr + " 00:00:00");
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getBeijingTodayEnd() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String datetime = dateFormat.format(new Date());
        String dateStr = datetime.substring(0, 10);
        try {
            return dateFormat.parse(dateStr + " 23:59:59");
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getCurrTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);
        return s;
    }

    public static Date getAddTime(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);// 今天+1天
        Date time = c.getTime();
        return time;

    }
}
