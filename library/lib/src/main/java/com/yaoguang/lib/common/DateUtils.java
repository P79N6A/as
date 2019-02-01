package com.yaoguang.lib.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期的Utils函数集合
 * <p>
 * 提供直接获取date,calendar,field
 */
public class DateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String MM_DD_HH_MM = "MM-dd HH:mm";

    public static final String YEAR_MONTH = "yyyyMM";

    public static final String YEAR_SEPARATE_MONTH = "yyyy-MM";

    public static final String YEAR_MONTH_DAY = "yyyyMMdd";

    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyyMMddHHmmss";

    public DateUtils() {
    }


    /**
     * 按自定义日期模式转换日期字符串并返回String类型.
     *
     * @param dateString 日期字符串
     * @param pattern    日期转换模式,例子:yyyy/MM/dd HH:mm:ss
     * @return String 返回日期(String)类型
     */
    public static String stringToString(String dateString, String pattern) {
        Date date = stringToDate(dateString, YYYY_MM_DD_HH_MM_SS);
        return dateToString(date, pattern);
    }

    /**
     * 按自定义日期模式转换日期字符串并返回Date类型.
     *
     * @param dateString 日期字符串
     * @param pattern    日期转换模式,例子:yyyy/MM/dd HH:mm:ss
     * @return Date 返回日期(Date)类型
     */
    public static Date stringToDate(String dateString, String pattern) {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 按自定义日期模式转换Date类型,并返回日期字符串.
     *
     * @param date    日期(Date)类型
     * @param pattern 日期转换模式,例子:yyyy/MM/dd HH:mm:ss
     * @return String 返回日期字符串
     */
    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 按自定义日期模式转换Date类型,并返回日期字符串.
     *
     * @return String 返回日期字符串
     */
    public static String getDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return dateFormat.format(new Date());
    }

    /**
     * 将Date类型转换为Calendar类型,并返回
     *
     * @param date 日期(Date)类型
     * @return Calendar 返回日历(Calendar)类型
     */
    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 根据日期(Date)类型获得该域的值
     *
     * @param date  日期(Date)类型
     * @param field 日历属性例子:Calendar.YEAR
     * @return int 返回域的值
     */
    public static int get(Date date, int field) {
        Calendar calendar = getCalendar(date);
        return calendar.get(field);
    }

    /**
     * 获取年
     *
     * @param date
     * @return int
     */
    public static int getYear(Date date) {
        return get(date, Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @param date
     * @return int
     */
    public static int getMonth(Date date) {
        return get(date, Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     *
     * @param date
     * @return int
     */
    public static int getDay(Date date) {
        return get(date, Calendar.DATE);
    }

    /**
     * 时间回滚
     *
     * @param date
     * @param field
     * @param amount
     * @return Date
     */
    public static Date rollDateByField(Date date, int field, int amount) {
        Calendar calendar = getCalendar(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 前一年
     *
     * @param date
     * @return Date
     */
    public static Date getPreYear(Date date) {
        return rollDateByField(date, Calendar.YEAR, -1);
    }

    /**
     * 前一个月
     *
     * @param date
     * @return Date
     */
    public static Date getPreMonth(Date date) {
        return rollDateByField(date, Calendar.MONTH, -1);
    }

    /**
     * 前两个月
     *
     * @param date
     * @return
     */
    public static Date getPreTwoMonth(Date date) {
        return rollDateByField(date, Calendar.MONTH, -2);
    }

    /**
     * 前三个月
     *
     * @param date
     * @return
     */
    public static Date getPreThreeMonth(Date date) {
        return rollDateByField(date, Calendar.MONTH, -3);
    }

    /**
     * 前半年
     *
     * @param date
     * @return
     */
    public static Date getPreSixMonth(Date date) {
        return rollDateByField(date, Calendar.MONTH, -6);
    }

    /**
     * 前一个月转换为字符串
     *
     * @param date
     * @param pattern
     * @return String
     */
    public static String getPreMonthToString(Date date, String pattern) {
        Date preDate = getPreMonth(date);
        return dateToString(preDate, pattern);
    }

    /**
     * 前一个月转换为年月字符串
     *
     * @param date
     * @return String
     */
    public static String getPreMonthToString(Date date) {
        Date preDate = getPreMonth(date);
        return dateToString(preDate, DateUtils.YEAR_MONTH);
    }

    /**
     * 前一天
     *
     * @param date
     * @return Date
     */
    public static Date getPreDay(Date date) {
        return rollDateByField(date, Calendar.DATE, -1);
    }

    /**
     * 明天
     *
     * @param date
     * @return Date
     */
    public static Date getNextDay(Date date) {
        return rollDateByField(date, Calendar.DATE, +1);
    }

    /**
     * 前七天
     *
     * @param date
     * @return
     */
    public static Date getPreSevenDay(Date date) {
        return rollDateByField(date, Calendar.DATE, -7);
    }

    /**
     * 前15天
     *
     * @param date
     * @return
     */
    public static Date getPre15Day(Date date) {
        return rollDateByField(date, Calendar.DATE, -15);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss 格式的字符串转换为 yyyyMMdd 格式
     */
    public static String stringToString(String str) {
        SimpleDateFormat dataFormat1 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        SimpleDateFormat dataFormat2 = new SimpleDateFormat(YEAR_MONTH_DAY);
        String dataFormat = "";
        try {
            dataFormat = dataFormat2.format(dataFormat1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataFormat;
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 日期查询转换
     * 将日期设置为一天的开始时间 yyyy-MM-dd 00:00:00
     *
     * @param startDate
     * @return
     */
    public static Date getStartDate(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 日期查询转换
     * 将日期设置为一天的结束时间 yyyy-MM-dd 23:59:59
     *
     * @param endDate
     * @return
     */
    public static Date getEndDate(Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }


    /**
     * 获取指定日期所在月份月初的时间 x月1日 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        date = getStartDate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取指定日期所在月份月初的时间 x月31日 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getMonthLastDay(Date date) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, getYear(date));
        //设置月份
        cal.set(Calendar.MONTH, getMonth(date) - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return cal.getTime();
    }

    //获取当前月的第一天
    public static String getMonthFirst() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return format.format(c.getTime());
    }

    //获取当前月的最后一天
    public static String getMonthLast() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(c.getTime());
        return format.format(c.getTime());
    }

    /**
     * 获取指定日期上一个月月末的时间 x月28/29/30/31日  23：59:59
     *
     * @param date
     * @return
     */
    public static Date getPreMonthLastDay(Date date) {
        Date monthFirstDay = getMonthFirstDay(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(monthFirstDay);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return getEndDate(calendar.getTime());
    }

    public static boolean isToDay(Date originalTime) {
        String time = dateToString(originalTime, YYYY_MM_DD_HH_MM);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (time == null || "".equals(time)) {
            return false;
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        current.setTime(date);

        Calendar tomorrow = Calendar.getInstance();    //明天

        tomorrow.set(Calendar.YEAR, current.get(Calendar.YEAR));
        tomorrow.set(Calendar.MONTH, current.get(Calendar.MONTH));
        tomorrow.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) + 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);

        if (current.after(today) && current.before(tomorrow)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 格式化时间
     *
     * @param originalTime
     * @return
     */
    public static String formatDateTime(Date originalTime) {
        String time = dateToString(originalTime, YYYY_MM_DD_HH_MM);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        Calendar tomorrow = Calendar.getInstance();    //明天

        tomorrow.set(Calendar.YEAR, current.get(Calendar.YEAR));
        tomorrow.set(Calendar.MONTH, current.get(Calendar.MONTH));
        tomorrow.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) + 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);

        Calendar tomorrowTwo = Calendar.getInstance();    //后天

        tomorrowTwo.set(Calendar.YEAR, current.get(Calendar.YEAR));
        tomorrowTwo.set(Calendar.MONTH, current.get(Calendar.MONTH));
        tomorrowTwo.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) + 2);
        tomorrowTwo.set(Calendar.HOUR_OF_DAY, 0);
        tomorrowTwo.set(Calendar.MINUTE, 0);
        tomorrowTwo.set(Calendar.SECOND, 0);

        Calendar tomorrowThree = Calendar.getInstance();    //大后天

        tomorrowThree.set(Calendar.YEAR, current.get(Calendar.YEAR));
        tomorrowThree.set(Calendar.MONTH, current.get(Calendar.MONTH));
        tomorrowThree.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) + 3);
        tomorrowThree.set(Calendar.HOUR_OF_DAY, 0);
        tomorrowThree.set(Calendar.MINUTE, 0);
        tomorrowThree.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today) && current.before(tomorrow)) {
            return "今天 " + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天 " + time.split(" ")[1];
        } else if (current.after(tomorrow) && current.before(tomorrowTwo)) {
            return "明天 " + time.split(" ")[1];
        } else if (current.after(tomorrowTwo) && current.before(tomorrowThree)) {
            return "后天 " + time.split(" ")[1];
        } else {
            int index = time.indexOf("-") + 1;
            return time.substring(index, time.length());
        }
    }

    /**
     * 比较日期
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 如果时间1 小于 时间2就返回true
     * @throws ParseException 转换异常
     */
    public static boolean compare(String time1, String time2) throws ParseException {
        //如果想比较日期则写成"yyyy-MM-dd"就可以了
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //将字符串形式的时间转化为Date类型的时间
        Date a = sdf.parse(time1);
        Date b = sdf.parse(time2);
        if (a.getTime() - b.getTime() < 0)
            return true;
        else
            return false;
    }

    /**
     * 比较时间
     *
     * @param s1 08:00
     * @param s2 08:00
     * @return 如果时间1 > 时间2 就返回true
     */
    public static boolean compTime(String s1, String s2) {
        try {
            if (s1.indexOf(":") < 0 || s1.indexOf(":") < 0) {
                System.out.println("格式不正确");
            } else {
                String[] array1 = s1.split(":");
                int total1 = Integer.valueOf(array1[0]) * 3600 + Integer.valueOf(array1[1]) * 60 + Integer.valueOf(array1[2]);
                String[] array2 = s2.split(":");
                int total2 = Integer.valueOf(array2[0]) * 3600 + Integer.valueOf(array2[1]) * 60 + Integer.valueOf(array2[2]);
                return total1 - total2 > 0 ? true : false;
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            return true;
        }
        return false;

    }

    /**
     * 时间戳比较
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return
     * @throws ParseException
     */
    public static boolean compare(long time1, long time2) throws ParseException {
        if (time1 - time2 < 0)
            return true;
        else
            return false;
    }


    public static void main(String[] args) {
//		String ss = "201608";
//		ss = ss.substring(0, 4) + "-" + ss.substring(4);
//		System.out.println(ss);
//		Date date = stringToDate("2016-08", YEAR_SEPARATE_MONTH);
//		System.out.println(stringToDate("2016-08", YEAR_SEPARATE_MONTH));
//		System.out.println(dateToString(date, YYYY_MM_DD_HH_MM_SS));
//		System.out.println(dateToString(new Date(), YEAR_SEPARATE_MONTH));
//		System.out.println(dateToString(new Date(), YEAR_MONTH));
//		Date date = getPreMonth(demo);
//		Date preThreeMonth = getPreThreeMonth(demo);
//		Date preSevenDay = getPreSevenDay(demo);
//		System.out.println(dateToString(demo, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
//		System.out.println(dateToString(date, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
//		System.out.println(dateToString(preThreeMonth, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
//		System.out.println(dateToString(preSevenDay, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
//		System.out.println(getStartDate(demo));
//		System.out.println(getEndDate(demo));
//		System.out.println("-----------------");
//		Date demo = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(demo);
//		calendar.set(Calendar.YEAR, 2016);
//		calendar.set(Calendar.MONTH, 2);
//		demo = calendar.getTime();
//		System.out.println(getMonthFirstDay(demo).toLocaleString());
//		System.out.println(getPreMonthLastDay(demo).toLocaleString());
        String time = "2017-06-29 12:20:21";
        Date date = stringToDate(time, YYYY_MM_DD_HH_MM_SS);
        System.out.println(formatDateTime(date));
    }


}
