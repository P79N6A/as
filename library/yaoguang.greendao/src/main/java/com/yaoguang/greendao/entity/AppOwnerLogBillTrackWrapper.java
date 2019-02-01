package com.yaoguang.greendao.entity;

/**
 * App订单状态日志包装对象
 * 
 * @author wangxiaohui
 * @date 2017年6月21日 下午2:34:03
 */
public class AppOwnerLogBillTrackWrapper {

	// 主键id
	private String id;

	// 订单编号
	private String billsId;

	// 业务类型 0-货代，1-拖车
	private Integer flag;

	// 是否装货派车 0-否，1-是
	private Integer isTruckPlanTime;

	// 装货派车日期
	private String truckPlanTime;

	// 是否装货完成 0-否，1-是
	private Integer isLoadOverDate;

	// 装货完成日期
	private String loadOverDate;

	// 是否上船 0-否，1-是
	private Integer isFirstEtd;

	// 上船日期
	private String firstEtd;

	// 是否船到 0-否，1-是
	private Integer isFirstEta;

	// 船到日期
	private String firstEta;

	// 是否送货派车 0-否，1-是
	private Integer isDestTruckPlanTime;

	// 送货派车日期
	private String destTruckPlanTime;
	
	// 是否送货完成 0-否，1-是
	private Integer isDeliveryOver;
	
	// 送货完成日期
	private String deliveryOverDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillsId() {
		return billsId;
	}

	public void setBillsId(String billsId) {
		this.billsId = billsId;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getIsTruckPlanTime() {
		return isTruckPlanTime;
	}

	public void setIsTruckPlanTime(Integer isTruckPlanTime) {
		this.isTruckPlanTime = isTruckPlanTime;
	}

	public String getTruckPlanTime() {
		return truckPlanTime;
	}

	public void setTruckPlanTime(String truckPlanTime) {
		this.truckPlanTime = truckPlanTime;
	}

	public Integer getIsLoadOverDate() {
		return isLoadOverDate;
	}

	public void setIsLoadOverDate(Integer isLoadOverDate) {
		this.isLoadOverDate = isLoadOverDate;
	}

	public String getLoadOverDate() {
		return loadOverDate;
	}

	public void setLoadOverDate(String loadOverDate) {
		this.loadOverDate = loadOverDate;
	}

	public Integer getIsFirstEtd() {
		return isFirstEtd;
	}

	public void setIsFirstEtd(Integer isFirstEtd) {
		this.isFirstEtd = isFirstEtd;
	}

	public String getFirstEtd() {
		return firstEtd;
	}

	public void setFirstEtd(String firstEtd) {
		this.firstEtd = firstEtd;
	}

	public Integer getIsFirstEta() {
		return isFirstEta;
	}

	public void setIsFirstEta(Integer isFirstEta) {
		this.isFirstEta = isFirstEta;
	}

	public String getFirstEta() {
		return firstEta;
	}

	public void setFirstEta(String firstEta) {
		this.firstEta = firstEta;
	}

	public Integer getIsDestTruckPlanTime() {
		return isDestTruckPlanTime;
	}

	public void setIsDestTruckPlanTime(Integer isDestTruckPlanTime) {
		this.isDestTruckPlanTime = isDestTruckPlanTime;
	}

	public String getDestTruckPlanTime() {
		return destTruckPlanTime;
	}

	public void setDestTruckPlanTime(String destTruckPlanTime) {
		this.destTruckPlanTime = destTruckPlanTime;
	}

	public Integer getIsDeliveryOver() {
		return isDeliveryOver;
	}

	public void setIsDeliveryOver(Integer isDeliveryOver) {
		this.isDeliveryOver = isDeliveryOver;
	}

	public String getDeliveryOverDate() {
		return deliveryOverDate;
	}

	public void setDeliveryOverDate(String deliveryOverDate) {
		this.deliveryOverDate = deliveryOverDate;
	}
	
	

}