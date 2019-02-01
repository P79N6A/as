package com.yaoguang.appcommon.phone.home.weather;


import com.yaoguang.appcommon.R;

/**
 * Created by zhongjh on 2017/9/7.
 */

public class WeatherUtils {

    public int getImage(String weather, boolean isNight) {

        // 如果是晚上
        if (isNight) {
            switch (weather) {
                case "晴":
                    return R.drawable.ic_yeqingtian_00;
                case "多云":
                    return R.drawable.ic_yeduoyun_01;
            }
        }


        switch (weather) {
            case "晴":
                return R.drawable.ic_qingtian_00;
            case "多云":
                return R.drawable.ic_duoyun_01;
            case "阴":
                return R.drawable.ic_yintian_02;
            case "阵雨":
                return R.drawable.ic_leiyu_03_04;
            case "雷阵雨":
                return R.drawable.ic_leiyu_03_04;
            case "雷阵雨伴有冰雹":
                return R.drawable.ic_bingbao_05;
            case "雨夹雪":
                return R.drawable.ic_yujiaxue_06;
            case "小雨":
                return R.drawable.ic_xiaoyu_07;
            case "中雨":
                return R.drawable.ic_zhongyu_08;
            case "大雨":
                return R.drawable.ic_dayu_09;
            case "暴雨":
                return R.drawable.ic_dabaoyu_10_11_12;
            case "大暴雨":
                return R.drawable.ic_dabaoyu_10_11_12;
            case "特大暴雨":
                return R.drawable.ic_dabaoyu_10_11_12;
            case "阵雪":
                return R.drawable.ic_xiaoxue_14;
            case "小雪":
                return R.drawable.ic_xiaoxue_14;
            case "中雪":
                return R.drawable.ic_xiaxue_15_16;
            case "大雪":
                return R.drawable.ic_xiaxue_15_16;
            case "暴雪":
                return R.drawable.ic_baofengxue_17;
            case "雾":
                return R.drawable.ic_wutian_18;
            case "冻雨":
                return R.drawable.ic_dongyu_19;
            case "沙尘暴":
                return R.drawable.ic_shachengbao_20_31;
            case "小到中雨":
                return R.drawable.ic_xiaoyuzzhongyu_21;
            case "中到大雨":
                return R.drawable.ic_zhongyuzdayu_22;
            case "大到暴雨":
                return R.drawable.ic_dayuzbaoyu_23;
            case "暴雨到大暴雨":
                return R.drawable.ic_baoyuzddabaoyu_24;
            case "大暴雨到特大暴雨":
                return R.drawable.ic_dabaoyuztedabaoyu_25;
            case "小到中雪":
                return R.drawable.ic_xiaodzhongxue_26;
            case "中到大雪":
                return R.drawable.ic_zhongddaxue_27;
            case "大到暴雪":
                return R.drawable.ic_daxueddabaoxue_28;
            case "浮尘":
                return R.drawable.ic_fucheng_29;
            case "扬沙":
                return R.drawable.ic_yangsha_30;
            case "强沙尘暴":
                return R.drawable.ic_shachengbao_20_31;
            case "霾":
                return R.drawable.ic_mai_53;
            case "无":
                return R.drawable.ic_qingtian_00;
            case "雨":
                return R.drawable.ic_xiaoyu_07;
            case "雪":
                return R.drawable.ic_xiaoxue_14;
        }
        return R.drawable.ic_qingtian_00;
    }


}
