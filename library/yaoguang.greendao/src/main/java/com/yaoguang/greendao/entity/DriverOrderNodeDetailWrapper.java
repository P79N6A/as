package com.yaoguang.greendao.entity;

import java.io.Serializable;


/**
 * 明细包装类(提交页面控制)
 * @author liyangbin
 * 
 * @date 2017年7月4日上午9:27:10
 */
public class DriverOrderNodeDetailWrapper implements Serializable {

	private static final long serialVersionUID = -4007264391775627773L;
	// 节点明细
	private DriverOrderNodeDetail driverOrderNodeDetail;
	// 是否必须上传图片
	private boolean photoRequired = true;
	// 货柜数
	private Integer amount;
	// 柜号一是否可填
	private boolean contNoFirstInput;
	// 封号一是否可填
	private boolean sealNoFirstInput;
	// 柜号二是否可填
	private boolean contNoSecondInput;
	// 封号二是否可填
	private boolean sealNoSecondInput;
	// 柜号一是否必填
	private boolean contNoFirstRequired = false;
	// 封号一是否必填
	private boolean sealNoFirstRequired = false;
	// 柜号二是否必填
	private boolean contNoSecondRequired = false;
	// 封 号二是否必填
	private boolean sealNoSecondRequired = false;
	// 提示文字
	private String tips;
	
	public DriverOrderNodeDetail getDriverOrderNodeDetail() {
		return driverOrderNodeDetail;
	}
	public void setDriverOrderNodeDetail(DriverOrderNodeDetail driverOrderNodeDetail) {
		this.driverOrderNodeDetail = driverOrderNodeDetail;
	}
	public boolean isPhotoRequired() {
		return photoRequired;
	}
	public void setPhotoRequired(boolean photoRequired) {
		this.photoRequired = photoRequired;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public boolean isContNoFirstInput() {
		return contNoFirstInput;
	}
	public void setContNoFirstInput(boolean contNoFirstInput) {
		this.contNoFirstInput = contNoFirstInput;
	}
	public boolean isSealNoFirstInput() {
		return sealNoFirstInput;
	}
	public void setSealNoFirstInput(boolean sealNoFirstInput) {
		this.sealNoFirstInput = sealNoFirstInput;
	}
	public boolean isContNoSecondInput() {
		return contNoSecondInput;
	}
	public void setContNoSecondInput(boolean contNoSecondInput) {
		this.contNoSecondInput = contNoSecondInput;
	}
	public boolean isSealNoSecondInput() {
		return sealNoSecondInput;
	}
	public void setSealNoSecondInput(boolean sealNoSecondInput) {
		this.sealNoSecondInput = sealNoSecondInput;
	}
	public boolean isContNoFirstRequired() {
		return contNoFirstRequired;
	}
	public void setContNoFirstRequired(boolean contNoFirstRequired) {
		this.contNoFirstRequired = contNoFirstRequired;
	}
	public boolean isSealNoFirstRequired() {
		return sealNoFirstRequired;
	}
	public void setSealNoFirstRequired(boolean sealNoFirstRequired) {
		this.sealNoFirstRequired = sealNoFirstRequired;
	}
	public boolean isContNoSecondRequired() {
		return contNoSecondRequired;
	}
	public void setContNoSecondRequired(boolean contNoSecondRequired) {
		this.contNoSecondRequired = contNoSecondRequired;
	}
	public boolean isSealNoSecondRequired() {
		return sealNoSecondRequired;
	}
	public void setSealNoSecondRequired(boolean sealNoSecondRequired) {
		this.sealNoSecondRequired = sealNoSecondRequired;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
}
