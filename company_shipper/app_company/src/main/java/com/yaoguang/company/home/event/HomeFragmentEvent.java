package com.yaoguang.company.home.event;

import android.os.Bundle;

/**
 * Created by zhongjh on 2018/11/12.
 */
public class HomeFragmentEvent {

    /**
     *
     * @param type 网络类型，0就是至少是wifi的连接状态
     */
    public HomeFragmentEvent(int type) {
        this.type = type;
    }

    private int type;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
