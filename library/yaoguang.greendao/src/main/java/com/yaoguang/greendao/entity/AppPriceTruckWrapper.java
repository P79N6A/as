package com.yaoguang.greendao.entity;

import java.io.Serializable;


/**
 *  APP拖车报价包装类
 * 
 * @author wangxiaohui
 * @date 2017年6月12日 上午11:15:16
 */
public class AppPriceTruckWrapper implements Serializable{
	
	private static final long serialVersionUID = -9009068310041598925L;

	private PriceTruck priceTruck;
	
	//柜型
	private String cont;
	
	//费用
	private Double fee;

	public PriceTruck getPriceTruck() {
		return priceTruck;
	}

	public void setPriceTruck(PriceTruck priceTruck) {
		this.priceTruck = priceTruck;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	
	
}
