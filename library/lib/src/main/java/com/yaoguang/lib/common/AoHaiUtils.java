package com.yaoguang.lib.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具栏
 * Created by zhongjh on 2017/10/10.
 */
public class AoHaiUtils {

    /**
     * 柜号
     *
     * @param value
     * @return
     */
    public static boolean checkContNo(String value) {
        //0-Z的对应数值是0到38，11、22、33不能对11取模数，所以要去掉
        String Charcode = "0123456789A?BCDEFGHIJK?LMNOPQRSTU?VWXYZ";
        String regEx = "^[A-Z]{4}[0-9]{7}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(value);
        if (value.length() != 11) {
            return false;
        }
        if (!matcher.matches()) {
            return false;
        }
        //将前10位的代码值乘积累加后对11取模
        double num = 0;
        for (int i = 0; i < 10; i++) {
            //获取对应数值
            int idx = Charcode.indexOf(value.substring(i, i + 1));
            if (idx == -1 || Charcode.substring(idx, idx + 1).equals("?")) {
                break;
            }

            //计算出代码值
            double idxvalue = idx * Math.pow(2, i);
            //累加代码值
            num += idxvalue;
        }
        //将前10位的代码值乘积累加后对11取模
        if (num % 11 == 0) {
            num = 0;
        } else {
            num = (num % 11) % 10;
        }
        if (ObjectUtils.parseInt((int) num) != ObjectUtils.parseInt(value.substring(10))) {
            return false;
        }
        return true;
    }

}
