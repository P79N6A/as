package com.yaoguang.greendao.entity;


/**
 * App货主端货柜信息查询条件对象
 *
 * @author wangxiaohui
 * @date 2017年6月20日 上午9:43:56
 */
public class AppOwnerSonoCondition {


    // 柜号
    private String contNo;

    // 运单号
    private String mBlno;

    // 工作单号
    private String orderSn;

    // 时间范围
    private String dateRange;

    private String date;

    //0货代 1拖车
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public String getmBlno() {
        return mBlno;
    }

    public void setmBlno(String mBlno) {
        this.mBlno = mBlno;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

}
