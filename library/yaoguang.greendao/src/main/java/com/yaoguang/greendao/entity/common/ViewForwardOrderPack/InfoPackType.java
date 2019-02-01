package com.yaoguang.greendao.entity.common.ViewForwardOrderPack;




/**
 * 基本资料-包装类型
 * 
 * @author heyonggang
 * @date 2016年10月18日下午5:19:12
 */

public class InfoPackType {

	private String id;

	private String typeId;

	private String typeChniese;

	private String typeEnglish;

	private Integer flag;

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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId == null ? null : typeId.trim();
	}

	public String getTypeChniese() {
		return typeChniese;
	}

	public void setTypeChniese(String typeChniese) {
		this.typeChniese = typeChniese == null ? null : typeChniese.trim();
	}

	public String getTypeEnglish() {
		return typeEnglish;
	}

	public void setTypeEnglish(String typeEnglish) {
		this.typeEnglish = typeEnglish == null ? null : typeEnglish.trim();
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
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