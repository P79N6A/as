package com.yaoguang.greendao.entity.driver;

/**
 * 我的tab实体
 * Created by 韦理英 on 2017/11/24 0024.
 */

public class AuthenticationResultItem {
    private String Title;
    // 0 显示正常 1 显示banner
    int type = 0;
    int icon;
    private String value;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
