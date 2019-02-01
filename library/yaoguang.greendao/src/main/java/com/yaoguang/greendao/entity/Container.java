package com.yaoguang.greendao.entity;

/**
 * 柜型柜量
 * Created by zhongjh on 2017/6/1.
 */

public class Container {


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 1*40GP
     */
    private String title;

    /**
     * 数量,上面1*40GP中的1
     */
    private int count;

}
