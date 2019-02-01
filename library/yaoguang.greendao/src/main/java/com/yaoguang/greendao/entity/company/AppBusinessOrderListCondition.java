package com.yaoguang.greendao.entity.company;

import com.yaoguang.greendao.entity.SysCondition;

import java.util.List;

/**
 * 列表条件
 * Created by zhongjh on 2018/10/31.
 */
public class AppBusinessOrderListCondition {

    private List<SysCondition> date;
    private List<SysCondition> string;
    private List<SysCondition> office;
    private List<SysCondition> status;

    public List<SysCondition> getDate() {
        return date;
    }

    public void setDate(List<SysCondition> date) {
        this.date = date;
    }

    public List<SysCondition> getString() {
        return string;
    }

    public void setString(List<SysCondition> string) {
        this.string = string;
    }

    public List<SysCondition> getOffice() {
        return office;
    }

    public void setOffice(List<SysCondition> office) {
        this.office = office;
    }

    public List<SysCondition> getStatus() {
        return status;
    }

    public void setStatus(List<SysCondition> status) {
        this.status = status;
    }

}
