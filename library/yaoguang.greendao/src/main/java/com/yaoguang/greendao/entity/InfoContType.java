package com.yaoguang.greendao.entity;

import java.io.Serializable;

/**
 * 基本资料-柜型(箱体规格)
 * 
 * @author heyonggang
 * @date 2016年10月14日上午10:23:15
 */
public class InfoContType implements Serializable {

	private static final long serialVersionUID = 8312557567937870178L;

	private String id;

	private String contTypeId;

	private String contChina;

	private String contEnglish;

	private Integer contType;

	private Integer contTeu;

	private Integer contWeight;

	private String codeEdi;

	private String contAttr;

	private Integer isvisible;

	private Integer flag;

	private String remark;

	private String createdBy;

	private String createdDeptId;

	private String created;

	private String updatedBy;

	private String updatedDeptId;

	private String updated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getContTypeId() {
		return contTypeId;
	}

	public void setContTypeId(String contTypeId) {
		this.contTypeId = contTypeId == null ? null : contTypeId.trim();
	}

	public String getContChina() {
		return contChina;
	}

	public void setContChina(String contChina) {
		this.contChina = contChina == null ? null : contChina.trim();
	}

	public String getContEnglish() {
		return contEnglish;
	}

	public void setContEnglish(String contEnglish) {
		this.contEnglish = contEnglish == null ? null : contEnglish.trim();
	}

	public Integer getContType() {
		return contType;
	}

	public void setContType(Integer contType) {
		this.contType = contType;
	}

	public Integer getContTeu() {
		return contTeu;
	}

	public void setContTeu(Integer contTeu) {
		this.contTeu = contTeu;
	}

	public Integer getContWeight() {
		return contWeight;
	}

	public void setContWeight(Integer contWeight) {
		this.contWeight = contWeight;
	}

	public String getCodeEdi() {
		return codeEdi;
	}

	public void setCodeEdi(String codeEdi) {
		this.codeEdi = codeEdi == null ? null : codeEdi.trim();
	}

	public String getContAttr() {
		return contAttr;
	}

	public void setContAttr(String contAttr) {
		this.contAttr = contAttr == null ? null : contAttr.trim();
	}

	public Integer getIsvisible() {
		return isvisible;
	}

	public void setIsvisible(Integer isvisible) {
		this.isvisible = isvisible;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy == null ? null : createdBy.trim();
	}

	public String getCreatedDeptId() {
		return createdDeptId;
	}

	public void setCreatedDeptId(String createdDeptId) {
		this.createdDeptId = createdDeptId == null ? null : createdDeptId.trim();
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy == null ? null : updatedBy.trim();
	}

	public String getUpdatedDeptId() {
		return updatedDeptId;
	}

	public void setUpdatedDeptId(String updatedDeptId) {
		this.updatedDeptId = updatedDeptId == null ? null : updatedDeptId.trim();
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}
}