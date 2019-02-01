package com.yaoguang.greendao.entity.common;


import com.yaoguang.greendao.entity.UserOwner;

/**
 * 用户关注公司表
 * 
 * @author heyonggang
 * @String 2017年4月1日下午2:23:22
 */
public class DriverFollowCompany {


	private String id;

	private String type;
	
    private String userDriverId;
    
    private String userCompanyId;

    private String companyId;

    private String companyFlag;

    private String auditRemark;
    
    private String refuseRemark;

    private String infoHackmanId;

    private String createdDeptId;

    private String created;

    private String updatedBy;

    private String updatedDeptId;

    private String updated;

    private String isValid;
    
    private String applyType;
    
    private String status;
    
    //关注机构信息
    private UserOfficeWrapper userOffice;
    
    //物流端申请公司信息
    private UserOffice userCompany;
    
    //货主申请信息
    private UserOwner userOwner;
    
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public String getUserCompanyId() {
		return userCompanyId;
	}

	public void setUserCompanyId(String userCompanyId) {
		this.userCompanyId = userCompanyId;
	}

	public UserOffice getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(UserOffice userCompany) {
		this.userCompany = userCompany;
	}

	public UserOwner getUserOwner() {
		return userOwner;
	}

	public void setUserOwner(UserOwner userOwner) {
		this.userOwner = userOwner;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserOfficeWrapper getUserOffice() {
		return userOffice;
	}

	public void setUserOffice(UserOfficeWrapper userOffice) {
		this.userOffice = userOffice;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserDriverId() {
        return userDriverId;
    }

    public void setUserDriverId(String userDriverId) {
        this.userDriverId = userDriverId == null ? null : userDriverId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyFlag() {
        return companyFlag;
    }

    public void setCompanyFlag(String companyFlag) {
        this.companyFlag = companyFlag;
    }

    public String getRefuseRemark() {
        return refuseRemark;
    }

    public void setRefuseRemark(String refuseRemark) {
        this.refuseRemark = refuseRemark == null ? null : refuseRemark.trim();
    }

    public String getInfoHackmanId() {
        return infoHackmanId;
    }

    public void setInfoHackmanId(String infoHackmanId) {
        this.infoHackmanId = infoHackmanId == null ? null : infoHackmanId.trim();
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
}