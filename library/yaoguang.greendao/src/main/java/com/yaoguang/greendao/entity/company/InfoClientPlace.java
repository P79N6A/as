package com.yaoguang.greendao.entity.company;

import android.os.Parcel;
import android.os.Parcelable;

import com.yaoguang.greendao.entity.driver.LocationArea;

import java.io.Serializable;
import java.util.Date;


/**
 * 基本资料-往来单位-装卸货地点信息实体类
 * 
 * @author ZhangDeQuan
 * @time 2016年11月2日 上午11:19:10
 */
public class InfoClientPlace implements Parcelable {

	private String id;

	private String codeId;

	private Integer sortIndex;

	private String consigneeId;

	private String area;

	private String regionid;

	private String regionfullname;

	private String addr;

	private String place;

	private String zxRemark;

	private String consigneeCompany;

	/**
	 * 联系人
	 */
	private String linkman;

	/**
	 * 联系人手机
	 */
	private String linkmanMp;

	/**
	 * 联系人电话
	 */
	private String linkmanTel;

	private String consigneeCompanyFax;

	private Integer isSms;

	private String clientId;

	private String remark;

	private String createdBy;

	private String createdDeptId;

	private String created;

	private String updatedBy;

	private String updatedDeptId;

	private String updated;

	private LocationArea locationArea;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId == null ? null : codeId.trim();
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getConsigneeId() {
		return consigneeId;
	}

	public void setConsigneeId(String consigneeId) {
		this.consigneeId = consigneeId == null ? null : consigneeId.trim();
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area == null ? null : area.trim();
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid == null ? null : regionid.trim();
	}

	public String getRegionfullname() {
		return regionfullname;
	}

	public void setRegionfullname(String regionfullname) {
		this.regionfullname = regionfullname == null ? null : regionfullname.trim();
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr == null ? null : addr.trim();
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place == null ? null : place.trim();
	}

	public String getZxRemark() {
		return zxRemark;
	}

	public void setZxRemark(String zxRemark) {
		this.zxRemark = zxRemark == null ? null : zxRemark.trim();
	}

	public String getConsigneeCompany() {
		return consigneeCompany;
	}

	public void setConsigneeCompany(String consigneeCompany) {
		this.consigneeCompany = consigneeCompany == null ? null : consigneeCompany.trim();
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman == null ? null : linkman.trim();
	}

	public String getLinkmanMp() {
		return linkmanMp;
	}

	public void setLinkmanMp(String linkmanMp) {
		this.linkmanMp = linkmanMp == null ? null : linkmanMp.trim();
	}

	public String getLinkmanTel() {
		return linkmanTel;
	}

	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel == null ? null : linkmanTel.trim();
	}

	public String getConsigneeCompanyFax() {
		return consigneeCompanyFax;
	}

	public void setConsigneeCompanyFax(String consigneeCompanyFax) {
		this.consigneeCompanyFax = consigneeCompanyFax == null ? null : consigneeCompanyFax.trim();
	}

	public Integer getIsSms() {
		return isSms;
	}

	public void setIsSms(Integer isSms) {
		this.isSms = isSms;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId == null ? null : clientId.trim();
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

	public LocationArea getLocationArea() {
		return locationArea;
	}

	public void setLocationArea(LocationArea locationArea) {
		this.locationArea = locationArea;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.codeId);
		dest.writeValue(this.sortIndex);
		dest.writeString(this.consigneeId);
		dest.writeString(this.area);
		dest.writeString(this.regionid);
		dest.writeString(this.regionfullname);
		dest.writeString(this.addr);
		dest.writeString(this.place);
		dest.writeString(this.zxRemark);
		dest.writeString(this.consigneeCompany);
		dest.writeString(this.linkman);
		dest.writeString(this.linkmanMp);
		dest.writeString(this.linkmanTel);
		dest.writeString(this.consigneeCompanyFax);
		dest.writeValue(this.isSms);
		dest.writeString(this.clientId);
		dest.writeString(this.remark);
		dest.writeString(this.createdBy);
		dest.writeString(this.createdDeptId);
		dest.writeString(this.created);
		dest.writeString(this.updatedBy);
		dest.writeString(this.updatedDeptId);
		dest.writeString(this.updated);
		dest.writeParcelable(this.locationArea, flags);
	}

	public InfoClientPlace() {
	}

	protected InfoClientPlace(Parcel in) {
		this.id = in.readString();
		this.codeId = in.readString();
		this.sortIndex = (Integer) in.readValue(Integer.class.getClassLoader());
		this.consigneeId = in.readString();
		this.area = in.readString();
		this.regionid = in.readString();
		this.regionfullname = in.readString();
		this.addr = in.readString();
		this.place = in.readString();
		this.zxRemark = in.readString();
		this.consigneeCompany = in.readString();
		this.linkman = in.readString();
		this.linkmanMp = in.readString();
		this.linkmanTel = in.readString();
		this.consigneeCompanyFax = in.readString();
		this.isSms = (Integer) in.readValue(Integer.class.getClassLoader());
		this.clientId = in.readString();
		this.remark = in.readString();
		this.createdBy = in.readString();
		this.createdDeptId = in.readString();
		this.created = in.readString();
		this.updatedBy = in.readString();
		this.updatedDeptId = in.readString();
		this.updated = in.readString();
		this.locationArea = in.readParcelable(LocationArea.class.getClassLoader());
	}

	public static final Parcelable.Creator<InfoClientPlace> CREATOR = new Parcelable.Creator<InfoClientPlace>() {
		@Override
		public InfoClientPlace createFromParcel(Parcel source) {
			return new InfoClientPlace(source);
		}

		@Override
		public InfoClientPlace[] newArray(int size) {
			return new InfoClientPlace[size];
		}
	};
}