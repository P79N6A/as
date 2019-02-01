package com.yaoguang.greendao.entity;

/**
 * 作    者：韦理英
 * 时    间：2017/9/15 0015.
 * 描    述：XXXXX
 */
public class DriverStatusSwitch {

    UserDriverStatusDetail driverRestPlan;
    private String currentDate;

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public UserDriverStatusDetail getDriverRestPlan() {
        return driverRestPlan;
    }

    public void setDriverRestPlan(UserDriverStatusDetail driverRestPlan) {
        this.driverRestPlan = driverRestPlan;
    }
}
