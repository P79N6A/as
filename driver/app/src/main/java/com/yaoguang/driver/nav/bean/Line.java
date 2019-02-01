package com.yaoguang.driver.nav.bean;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/24 0024.
 * 版    权：
 */
public class Line {
    // 路线名
    private String lineName;
    // 路线IconType
    private int lineIconType;
    // 路线 roadType
    private int roadType;
    // 红绿灯个数
    private int trafficLightNumber;
    // 路线长
    private long lineLeng;
    // 下一条路名
    private String lineNextName;

    public int getLineIconType() {
        return lineIconType;
    }

    public void setLineIconType(int lineIconType) {
        this.lineIconType = lineIconType;
    }

    public long getLineLeng() {
        return lineLeng;
    }

    public void setLineLeng(long lineLeng) {
        this.lineLeng = lineLeng;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public int getRoadType() {
        return roadType;
    }

    public void setRoadType(int roadType) {
        this.roadType = roadType;
    }

    public int getTrafficLightNumber() {
        return trafficLightNumber;
    }

    public void setTrafficLightNumber(int trafficLightNumber) {
        this.trafficLightNumber = trafficLightNumber;
    }

    public String getLineNextName() {
        return lineNextName;
    }

    public void setLineNextName(String lineNextName) {
        this.lineNextName = lineNextName;
    }
}
