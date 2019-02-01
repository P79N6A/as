package com.yaoguang.appcommon.common.widget.chartview;

/**
 * x,y轴
 */
public class ChartItem {
    private String x;
    private float y;

    public ChartItem(String vx, float vy) {
        this.x = vx;
        this.y = vy;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
