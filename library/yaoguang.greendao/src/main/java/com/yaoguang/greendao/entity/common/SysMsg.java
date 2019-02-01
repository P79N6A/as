package com.yaoguang.greendao.entity.common;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 系统消息
 * 
 * @author heyonggang
 * @date 2017年4月24日下午1:56:01
 */
public class SysMsg implements Parcelable {


	private String id;

	private String platformType;

	private String title;

	private String content;

	private String msgIcon;

	private String msgType;

	private String createdBy;

	private String createdDeptId;

	private String created;

	private String updatedBy;

	private String updatedDeptId;

	private String updated;

	private String isValid;

	private String flag;

	// 平台名称
	private String platformName;

	// 平台logo
	private String platformLogo;

	// 平台联系电话
	private String platformPhone;

	public String getPlatformPhone() {
		return platformPhone;
	}

	public void setPlatformPhone(String platformPhone) {
		this.platformPhone = platformPhone;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformLogo() {
		return platformLogo;
	}

	public void setPlatformLogo(String platformLogo) {
		this.platformLogo = platformLogo;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getMsgIcon() {
		return msgIcon;
	}

	public void setMsgIcon(String msgIcon) {
		this.msgIcon = msgIcon == null ? null : msgIcon.trim();
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
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

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.platformType);
		dest.writeString(this.title);
		dest.writeString(this.content);
		dest.writeString(this.msgIcon);
		dest.writeString(this.msgType);
		dest.writeString(this.createdBy);
		dest.writeString(this.createdDeptId);
		dest.writeString(this.created);
		dest.writeString(this.updatedBy);
		dest.writeString(this.updatedDeptId);
		dest.writeString(this.updated);
		dest.writeString(this.isValid);
		dest.writeString(this.flag);
		dest.writeString(this.platformName);
		dest.writeString(this.platformLogo);
		dest.writeString(this.platformPhone);
	}

	public SysMsg() {
	}

	protected SysMsg(Parcel in) {
		this.id = in.readString();
		this.platformType = in.readString();
		this.title = in.readString();
		this.content = in.readString();
		this.msgIcon = in.readString();
		this.msgType = in.readString();
		this.createdBy = in.readString();
		this.createdDeptId = in.readString();
		this.created = in.readString();
		this.updatedBy = in.readString();
		this.updatedDeptId = in.readString();
		this.updated = in.readString();
		this.isValid = in.readString();
		this.flag = in.readString();
		this.platformName = in.readString();
		this.platformLogo = in.readString();
		this.platformPhone = in.readString();
	}

	public static final Parcelable.Creator<SysMsg> CREATOR = new Parcelable.Creator<SysMsg>() {
		@Override
		public SysMsg createFromParcel(Parcel source) {
			return new SysMsg(source);
		}

		@Override
		public SysMsg[] newArray(int size) {
			return new SysMsg[size];
		}
	};
}