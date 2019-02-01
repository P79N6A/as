package com.yaoguang.greendao.entity.driver;

import java.io.Serializable;
import java.util.Date;

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                /**
 * app意见反馈
 * 
 * @author heyonggang
 * @date 2017年4月21日下午4:19:02
 */
public class FeedbackApp implements Serializable{
	
	private static final long serialVersionUID = -768763498549330364L;

	public static final int FEEDBACK_TYPE_BUG = 1 << 0; // 功能故障或不可用
	public static final int FEEDBACK_TYPE_ADVICE = 1 << 1; // 产品建议
	public static final int FEEDBACK_TYPE_OTHER = 1 << 2; // 其他

	private String id;

	// 图片
	private String feedbackPic;

    // 平台类型(0:Android 1:iOS)
	private Integer platformType;

    // 应用类型（1：司机端 2：物流 3：货主）
    private Integer appType;

    // 反馈人
    private String userId;

    // 反馈类型,多选 (功能故障或不可用1,产品建议2,其他4)
    private Integer feedbackType;

    // 反馈内容
    private String advice;

    // 联系方式
    private String contactWay;

    private Date created;

    private Integer isValid;

    public Integer getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(Integer feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getFeedbackPic() {
		return feedbackPic;
	}

	public void setFeedbackPic(String feedbackPic) {
		this.feedbackPic = feedbackPic;
	}

	public Integer getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice == null ? null : advice.trim();
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay == null ? null : contactWay.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}