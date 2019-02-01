package com.yaoguang.driver.nav.plan.bean;

import java.io.Serializable;

/**
 * 作者：韦理英
 * 时间： 2017/5/16 0016.
 */

public class NavPlan implements Serializable {

    private int id;
    private String name;
    private String dsc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
