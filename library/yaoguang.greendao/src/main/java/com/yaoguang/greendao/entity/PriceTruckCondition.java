package com.yaoguang.greendao.entity;

import java.io.Serializable;

/**
 * App端应收拖车费查询条件
 * 
 * @author wangxiaohui
 * @date 2017年6月9日 下午2:08:59
 */
public class PriceTruckCondition implements Serializable {

	private static final long serialVersionUID = -5876293710732286905L;

	// 装卸类型0-装，1-卸
	private Integer serviceType;

	// 码头
	private String port;

	// 地点
	private String address;

	// 拖运人
	private String shipper;

	// 柜型
	private String cont;

	// 应收应付 (0-应收，1-应付)
	private Integer accoAttr;

	// 总公司编号
	private String clientId;

	// 分公司编号
	private String companyId;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public Integer getAccoAttr() {
		return accoAttr;
	}

	public void setAccoAttr(Integer accoAttr) {
		this.accoAttr = accoAttr;
	}

}
