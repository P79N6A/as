package com.yaoguang.lib.common;

/**
 * 天气工具
 * Created by zhongjh on 2017/9/7.
 */

public class WeatherUtil {

    /**
     * 数字转换中文数字
     * @param number 英文数字
     * @return
     */
    public static String parstChineseNumeralsN(int number){
        switch (number){
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "日";
        }
        return null;
    }

}
