package com.yaoguang.greendao.entity.company;

import java.util.ArrayList;
import java.util.List;

public class ViewTransferCancel {

	private String isCheck;

	private String billsId;  // 工作单id
	
	private String orderSn;  // 工作单号
	
	private String sender;  // 发送方（工作单来源公司）名称
	
	private List<ViewTransferCancelDetail> details = new ArrayList<>();
	
	public String getBillsId() {
		return billsId;
	}

	public void setBillsId(String billsId) {
		this.billsId = billsId;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public List<ViewTransferCancelDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ViewTransferCancelDetail> details) {
		this.details = details;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
}
