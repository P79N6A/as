package com.yaoguang.greendao.entity;

import android.view.View;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class BaseShow {
    private String banner;
    private boolean line;
    private String[] keyValue;
    private String[] imageOne;
    private String[] editKeyValue;

    public BaseShow(boolean line, String[] imageOne) {
        this.line = line;
        this.imageOne = imageOne;
    }

    public BaseShow(String[] editKeyValue) {
        this.editKeyValue = editKeyValue;
    }

    public String[] getEditKeyValue() {
        return editKeyValue;
    }

    public boolean isLine() {
        return line;
    }

    public BaseShow(String[] keyValue, boolean line) {
        this.keyValue = keyValue;
        this.line = line;
    }

    public BaseShow(String banner) {
        this.banner = banner;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String[] getImageOne() {
        return imageOne;
    }

    public void setImageOne(String[] imageOne) {
        this.imageOne = imageOne;
    }

    public String[] getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String[] keyValue) {
        this.keyValue = keyValue;
    }
}
