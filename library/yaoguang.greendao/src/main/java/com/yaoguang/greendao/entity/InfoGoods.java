package com.yaoguang.greendao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 基本资料-货名
 * 
 * @author heyonggang
 * @date 2016年10月17日上午11:45:32
 */
public class InfoGoods implements Serializable {

	private static final long serialVersionUID = 4611633901880695812L;

	private String id;

	private String goodsId;

	private String goodsChinese;

	private String goodsEnglish;

	private Integer flag;

	private String goodsType;

	private Integer checkWeight;

	private String goodsHl;

	private String clientId;

	private String remark;

	private String createdBy;

	private String createdDeptId;

	private Date created;

	private String updatedBy;

	private String updatedDeptId;

	private Date updated;
	
	private InfoGoodsUser infoGoodsUser;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsChinese() {
		return goodsChinese;
	}

	public void setGoodsChinese(String goodsChinese) {
		this.goodsChinese = goodsChinese;
	}

	public String getGoodsEnglish() {
		return goodsEnglish;
	}

	public void setGoodsEnglish(String goodsEnglish) {
		this.goodsEnglish = goodsEnglish;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public Integer getCheckWeight() {
		return checkWeight;
	}

	public void setCheckWeight(Integer checkWeight) {
		this.checkWeight = checkWeight;
	}

	public String getGoodsHl() {
		return goodsHl;
	}

	public void setGoodsHl(String goodsHl) {
		this.goodsHl = goodsHl;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDeptId() {
		return createdDeptId;
	}

	public void setCreatedDeptId(String createdDeptId) {
		this.createdDeptId = createdDeptId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDeptId() {
		return updatedDeptId;
	}

	public void setUpdatedDeptId(String updatedDeptId) {
		this.updatedDeptId = updatedDeptId;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public InfoGoodsUser getInfoGoodsUser() {
		return infoGoodsUser;
	}

	public void setInfoGoodsUser(InfoGoodsUser infoGoodsUser) {
		this.infoGoodsUser = infoGoodsUser;
	}
	
}