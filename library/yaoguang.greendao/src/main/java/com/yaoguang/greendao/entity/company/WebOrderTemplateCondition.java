package com.yaoguang.greendao.entity.company;

import java.io.Serializable;

/**
 * App企业端业务下单查询条件
 * 
 * @author wangxiaohui
 * @date 2017年6月8日 上午11:19:33
 */
public class WebOrderTemplateCondition implements Serializable {

	private static final long serialVersionUID = -6796072615075414526L;

	// 用户id
	private String userId;

	// 总公司id
	private String clientId;

	// 类型（0-货代，1-拖车）
	private String type;

	// 是否是默认模板（0-否，1-是）
	private String isDefault;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

}
