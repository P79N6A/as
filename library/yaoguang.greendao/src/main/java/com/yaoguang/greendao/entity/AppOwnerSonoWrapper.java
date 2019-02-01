package com.yaoguang.greendao.entity;


/**
 * App货主端货柜包装对象
 *
 * @author wangxiaohui
 * @date 2017年6月20日 上午10:09:02
 */
public class AppOwnerSonoWrapper {

    // 主键id
    private String id;

    // 类型（0-货代，1-拖车）
    private Integer type;

    // 订单编号
    private String orderSn;

    // 柜号
    private String contNo;

    // 运单号
    private String mBlno;

    // 业务时间
    private String created;

    // 装货派车日期
    private String truckPlanTime;

    // 上船日期
    private String firstEtd;

    // 装货完成日期
    private String loadOverDate;

    // 船到日期
    private String firstEta;

    // 送货派车日期
    private String destTruckPlanTime;

    // 送货完成日期
    private String deliveryOverDate;

    // 装送货状态（0-装箱，1-拆箱，2-货代）
    private Integer otherService;

    public Integer getOtherService() {
        return otherService;
    }

    public void setOtherService(Integer otherService) {
        this.otherService = otherService;
    }

    public String getTruckPlanTime() {
        return truckPlanTime;
    }

    public void setTruckPlanTime(String truckPlanTime) {
        this.truckPlanTime = truckPlanTime;
    }

    public String getFirstEtd() {
        return firstEtd;
    }

    public void setFirstEtd(String firstEtd) {
        this.firstEtd = firstEtd;
    }

    public String getLoadOverDate() {
        return loadOverDate;
    }

    public void setLoadOverDate(String loadOverDate) {
        this.loadOverDate = loadOverDate;
    }

    public String getFirstEta() {
        return firstEta;
    }

    public void setFirstEta(String firstEta) {
        this.firstEta = firstEta;
    }

    public String getDestTruckPlanTime() {
        return destTruckPlanTime;
    }

    public void setDestTruckPlanTime(String destTruckPlanTime) {
        this.destTruckPlanTime = destTruckPlanTime;
    }

    public String getDeliveryOverDate() {
        return deliveryOverDate;
    }

    public void setDeliveryOverDate(String deliveryOverDate) {
        this.deliveryOverDate = deliveryOverDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}