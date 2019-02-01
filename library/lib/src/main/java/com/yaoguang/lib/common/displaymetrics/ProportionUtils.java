package com.yaoguang.lib.common.displaymetrics;

/**
 * 比例计算
 * Created by zhongjh on 2017/10/25.
 */
public class ProportionUtils {


    /**
     * 根据宽度和比例计算出高度
     *
     * @param oldHeight 比例的高度
     * @param oldWidth  比例的宽度
     * @param width     新宽度
     * @return 新高度
     */
    public static int getHeight(int oldHeight, int oldWidth, int width) {
        //设置图片宽高比
        float scale = (float) oldWidth / (float) oldHeight;
        return Math.round(width / scale);
    }


}
