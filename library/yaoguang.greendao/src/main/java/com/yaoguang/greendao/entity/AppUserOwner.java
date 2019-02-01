package com.yaoguang.greendao.entity;

/**
 * 自己封装的一个UserOwner对象
 * Created by zhongjh on 2017/12/8.
 */

public class AppUserOwner {

    private UserOwner userOwner;
    private int type;

    public UserOwner getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(UserOwner userOwner) {
        this.userOwner = userOwner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
