package com.yaoguang.map.navi;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/22 0022.
 * 版    权：
 */
public class NavSetting {
    private boolean congestion; //  躲避拥堵
    private boolean cost; // 避免收费
    private boolean avoidHighSpeed;  // 不走高速
    private boolean highSpeed; // 高速优先

    private boolean huoChe; // 货车导航 true 货车导航 false 小车导航

    String chePai;  // 车牌
    private boolean  xianXing;  // 限行
    String huoCheHeight;  // 货车高度
    private boolean  maxChaiZhong;   // 货车最大载重计算
    String weight;    // 货车重量

    public boolean isCongestion() {
        return congestion;
    }

    public void setCongestion(boolean congestion) {
        this.congestion = congestion;
    }

    public boolean isCost() {
        return cost;
    }

    public void setCost(boolean cost) {
        this.cost = cost;
    }

    public boolean isAvoidHighSpeed() {
        return avoidHighSpeed;
    }

    public void setAvoidHighSpeed(boolean avoidHighSpeed) {
        this.avoidHighSpeed = avoidHighSpeed;
    }

    public boolean isHighSpeed() {
        return highSpeed;
    }

    public void setHighSpeed(boolean highSpeed) {
        this.highSpeed = highSpeed;
    }

    public boolean isHuoChe() {
        return huoChe;
    }

    public void setHuoChe(boolean huoChe) {
        this.huoChe = huoChe;
    }

    public String getChePai() {
        return chePai;
    }

    public void setChePai(String chePai) {
        this.chePai = chePai;
    }

    public boolean isXianXing() {
        return xianXing;
    }

    public void setXianXing(boolean xianXing) {
        this.xianXing = xianXing;
    }

    public String getHuoCheHeight() {
        return huoCheHeight;
    }

    public void setHuoCheHeight(String huoCheHeight) {
        this.huoCheHeight = huoCheHeight;
    }

    public boolean isMaxChaiZhong() {
        return maxChaiZhong;
    }

    public void setMaxChaiZhong(boolean maxChaiZhong) {
        this.maxChaiZhong = maxChaiZhong;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "NavSetting{" +
                "躲避拥堵=" + congestion +
                ", 避免收费=" + cost +
                ", 不走高速=" + avoidHighSpeed +
                ", 高速优先=" + highSpeed +
                ", huoChe=" + huoChe +
                ", chePai='" + chePai + '\'' +
                ", xianXing=" + xianXing +
                ", huoCheHeight='" + huoCheHeight + '\'' +
                ", maxChaiZhong=" + maxChaiZhong +
                ", weight='" + weight + '\'' +
                '}';
    }
}
