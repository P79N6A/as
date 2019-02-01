package com.yaoguang.greendao.entity.company;

import java.io.Serializable;

/**
 * App企业端业务下单查询条件
 * 
 * @author wangxiaohui
 * @date 2017年6月8日 上午11:19:33
 */
public class AppCompanyOrderCondition implements Serializable {

	private static final long serialVersionUID = -6796072615075414526L;

	// 是否已导单(0-否，1-是)
	private String status;

	public String getOtherService() {
		return otherService;
	}

	public void setOtherService(String otherService) {
		this.otherService = otherService;
	}

	//装卸货0-装，1-卸
	private String otherService;
	
	// 用户id
	private String userId;

	// 业务单号
	private String orderSn;

	// 托运人名称
	private String shipper;

	// 起运地
	private String dockOfLoading;

	// 目的地
	private String finalDestination;

	// 货主
	private String owner;

	// 起运港
	private String portLoading;

	// 目的港
	private String portDelivery;

	// 总公司id
	private String clientId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getDockOfLoading() {
		return dockOfLoading;
	}

	public void setDockOfLoading(String dockOfLoading) {
		this.dockOfLoading = dockOfLoading;
	}

	public String getFinalDestination() {
		return finalDestination;
	}

	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPortLoading() {
		return portLoading;
	}

	public void setPortLoading(String portLoading) {
		this.portLoading = portLoading;
	}

	public String getPortDelivery() {
		return portDelivery;
	}

	public void setPortDelivery(String portDelivery) {
		this.portDelivery = portDelivery;
	}

}
