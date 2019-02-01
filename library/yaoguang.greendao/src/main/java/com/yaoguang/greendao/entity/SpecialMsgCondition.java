package com.yaoguang.greendao.entity;


/**
 * 系统消息条件查询
 * 
 * @author heyonggang
 * @date 2017年4月24日下午3:16:11
 */
public class SpecialMsgCondition {

	//平台类型（1：司机端 2：供应链 3：货主）
	private Integer platformType;

	private String title;

	private Integer msgType;

	// 0：未发布 1：已发布
	private Integer isPublish;

	// 发布状态（0：未生效，1：已生效）
	private Integer publishType;

	public Integer getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Integer getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Integer isPublish) {
		this.isPublish = isPublish;
	}

	public Integer getPublishType() {
		return publishType;
	}

	public void setPublishType(Integer publishType) {
		this.publishType = publishType;
	}

}
