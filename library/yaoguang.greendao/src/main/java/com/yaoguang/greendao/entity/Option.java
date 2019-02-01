package com.yaoguang.greendao.entity;


/**
 * Created by Li YangBin on 2018/12/13.
 */
public class Option {

    public Option(String showValue, String value) {
        this.showValue = showValue;
        this.value = value;
    }

    private String showValue; // 显示值

    private String value; // 实际值

    public String getShowValue() {
        return showValue;
    }

    public void setShowValue(String showValue) {
        this.showValue = showValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
