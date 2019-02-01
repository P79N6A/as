package com.yaoguang.greendao.entity.company;

public class ViewTransferCancelDetail  {

	private String toCompanyId;
	
	private String toCompanyName;  // 接收方公司名称
	
	private Integer serviceType; // (0:货代派单 1:装货派单 2:送货派单 3:拖车派单)
	
	private Integer status; // (0:未勾选 1:已勾选)

	private String isCheck;

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getToCompanyId() {
		return toCompanyId;
	}

	public void setToCompanyId(String toCompanyId) {
		this.toCompanyId = toCompanyId;
	}

	public String getToCompanyName() {
		return toCompanyName;
	}

	public void setToCompanyName(String toCompanyName) {
		this.toCompanyName = toCompanyName;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
