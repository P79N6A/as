package com.yaoguang.driver.nav.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/23 0023.
 * 版    权：
 */
public class RoadInfo {
    // 路线名
    private String parentName;
    // 路线IconType
    private int parentIconType;
    // 红绿灯个数
    private int trafficLightNumber;
    // 路线长
    private int parentLeng;

    private List<Line> lines = new ArrayList<>();

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getParentIconType() {
        return parentIconType;
    }

    public void setParentIconType(int parentIconType) {
        this.parentIconType = parentIconType;
    }

    public int getParentLeng() {
        return parentLeng;
    }

    public void setParentLeng(int parentLeng) {
        this.parentLeng = parentLeng;
    }

    public int getTrafficLightNumber() {
        return trafficLightNumber;
    }

    public void setTrafficLightNumber(int trafficLightNumber) {
        this.trafficLightNumber = trafficLightNumber;
    }
}
