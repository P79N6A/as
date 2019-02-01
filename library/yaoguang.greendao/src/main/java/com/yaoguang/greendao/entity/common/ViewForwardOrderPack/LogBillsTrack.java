package com.yaoguang.greendao.entity.common.ViewForwardOrderPack;


import android.os.Parcel;
import android.os.Parcelable;

public class LogBillsTrack implements Parcelable {

	private String id;

	private String billsId;

	private Integer flag;

	private Integer isFirstEta;             // 是否船到
	private String firstEta;                  // 船到日期(0：否 1：是）

	private Integer firstEtaDay;            // 船到天数(货代)

	private Integer isBdDate;               // 是否办单（货代装货/拖车，0：否 1：是）
	private String bdDate;                    // 办单日期（货代装货/拖车)

	private Integer isTruckPlanTime;        // 是否派车(货代/拖车，0：否 1：是)
	private String truckPlanTime;             // 派车日期（货代/拖车）

	private Integer isConfirmDate;          // 是否司机确认（拖车，0：否 1：是）
	private String confirmDate;               // 司机确认日期(拖车)

	private Integer isRelivePlanTime;       // 是否装送货(货代/拖车，0：否 1：是))
	private String relivePlanTime;            // 装送货日期(货代/拖车)

	private Integer isLastLoadDate;         // 是否回单(拖车，0：否 1：是)
	private String lastLoadDate;              // 回单日期(拖车)

	private Integer isMarketCheckDate;      // 是否报柜号(货代/拖车，0：否 1：是)
	private String marketCheckDate;           // 报柜号日期(货代/拖车)

	private Integer isComDate;              // 是否核柜(货代/拖车，0：否 1：是)
	private String comDate;                   // 核柜日期（货代/拖车）

	private String remark;                  // 核柜备注（货代/拖车）

	private Integer isGoodskeepDate;        // 是否扣货(拖车，0：否 1：是)
	private String goodskeepDate;             // 扣货时间(拖车)

	private String goodskeepReason;         // 扣货原因(拖车)

	private Integer isFirstEtd;             // 是否开船(货代/拖车，0：否 1：是)
	private String firstEtd;                  // 开船日期（货代/拖车）

	private Integer isCarryDate;            // 是否提柜(拖车，0：否 1：是)
	private String carryDate;                 // 提柜日期(拖车)

	private Integer isAssembleDate;         // 是否提交调度(拖车，0：否 1：是)
	private String assembleDate;              // 提交调度日期(拖车)

	private Integer isLoadOverDate;         // 是否装货完成(货代，0：否 1：是)
	private String loadOverDate;              // 装货完成日期（货代）

	private Integer isDeliveryOver;         // 是否(货代送货完成/拖车装送货完成，0：否 1：是)
	private String deliveryOverDate;          // (货代送货完成/拖车装送货完成)日期

	private Integer isBillRegisterDate;     // 运单回传(货代，0：否 1：是)
	private String billRegisterDate;          // 运单回传日期（货代）

	private Integer isDestTruckPlanTime;    // 是否送货派车(货代，0：否 1：是)
	private String destTruckPlanTime;         // 送货派车日期（货代）

	private Integer isBdDestDate;           // 是否送货办单(货代)
	private String bdDestDate;                // 送货办单日期(货代)

	private Integer isHcDate;               // 是否回场(拖车，0：否 1：是)
	private String hcDate;                    // 回场日期(拖车)

	private String thId;                    // 提柜单号（货代）

	private Integer isValid;                // 是否取消业务(货代, -1:待确认 0:否 1:是)

	private Integer isFinish;               // 是否提交商务(货代/拖车，0：否 1：是)

	private String finishedOpt;             // 提交商务人员（货代/拖车）
	private String finishedDate;              // 提交商务日期（货代/拖车）

	private Integer auditStatus;            // 拖车业务单审核状态（0:未审核，1:已审核）

	private String auditor;                 // 拖车业务单审核人
	private String auditDate;                 // 拖车业务单审核日期

	private Integer isMtddDate;             // 是否码头打单（拖车，0：否 1：是）
	private String mtddDate;                  // 码头打单日期（拖车）

	private String createdBy;

	private String createdDeptId;

	private String created;

	private String updatedBy;

	private String updatedDeptId;

	private String updated;

	// 订单id集合
	private String[] billsIds;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillsId() {
		return billsId;
	}

	public void setBillsId(String billsId) {
		this.billsId = billsId;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getIsFirstEta() {
		return isFirstEta;
	}

	public void setIsFirstEta(Integer isFirstEta) {
		this.isFirstEta = isFirstEta;
	}

	public String getFirstEta() {
		return firstEta;
	}

	public void setFirstEta(String firstEta) {
		this.firstEta = firstEta;
	}

	public Integer getFirstEtaDay() {
		return firstEtaDay;
	}

	public void setFirstEtaDay(Integer firstEtaDay) {
		this.firstEtaDay = firstEtaDay;
	}

	public Integer getIsBdDate() {
		return isBdDate;
	}

	public void setIsBdDate(Integer isBdDate) {
		this.isBdDate = isBdDate;
	}

	public String getBdDate() {
		return bdDate;
	}

	public void setBdDate(String bdDate) {
		this.bdDate = bdDate;
	}

	public Integer getIsTruckPlanTime() {
		return isTruckPlanTime;
	}

	public void setIsTruckPlanTime(Integer isTruckPlanTime) {
		this.isTruckPlanTime = isTruckPlanTime;
	}

	public String getTruckPlanTime() {
		return truckPlanTime;
	}

	public void setTruckPlanTime(String truckPlanTime) {
		this.truckPlanTime = truckPlanTime;
	}

	public Integer getIsConfirmDate() {
		return isConfirmDate;
	}

	public void setIsConfirmDate(Integer isConfirmDate) {
		this.isConfirmDate = isConfirmDate;
	}

	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Integer getIsRelivePlanTime() {
		return isRelivePlanTime;
	}

	public void setIsRelivePlanTime(Integer isRelivePlanTime) {
		this.isRelivePlanTime = isRelivePlanTime;
	}

	public String getRelivePlanTime() {
		return relivePlanTime;
	}

	public void setRelivePlanTime(String relivePlanTime) {
		this.relivePlanTime = relivePlanTime;
	}

	public Integer getIsLastLoadDate() {
		return isLastLoadDate;
	}

	public void setIsLastLoadDate(Integer isLastLoadDate) {
		this.isLastLoadDate = isLastLoadDate;
	}

	public String getLastLoadDate() {
		return lastLoadDate;
	}

	public void setLastLoadDate(String lastLoadDate) {
		this.lastLoadDate = lastLoadDate;
	}

	public Integer getIsMarketCheckDate() {
		return isMarketCheckDate;
	}

	public void setIsMarketCheckDate(Integer isMarketCheckDate) {
		this.isMarketCheckDate = isMarketCheckDate;
	}

	public String getMarketCheckDate() {
		return marketCheckDate;
	}

	public void setMarketCheckDate(String marketCheckDate) {
		this.marketCheckDate = marketCheckDate;
	}

	public Integer getIsComDate() {
		return isComDate;
	}

	public void setIsComDate(Integer isComDate) {
		this.isComDate = isComDate;
	}

	public String getComDate() {
		return comDate;
	}

	public void setComDate(String comDate) {
		this.comDate = comDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsGoodskeepDate() {
		return isGoodskeepDate;
	}

	public void setIsGoodskeepDate(Integer isGoodskeepDate) {
		this.isGoodskeepDate = isGoodskeepDate;
	}

	public String getGoodskeepDate() {
		return goodskeepDate;
	}

	public void setGoodskeepDate(String goodskeepDate) {
		this.goodskeepDate = goodskeepDate;
	}

	public String getGoodskeepReason() {
		return goodskeepReason;
	}

	public void setGoodskeepReason(String goodskeepReason) {
		this.goodskeepReason = goodskeepReason;
	}

	public Integer getIsFirstEtd() {
		return isFirstEtd;
	}

	public void setIsFirstEtd(Integer isFirstEtd) {
		this.isFirstEtd = isFirstEtd;
	}

	public String getFirstEtd() {
		return firstEtd;
	}

	public void setFirstEtd(String firstEtd) {
		this.firstEtd = firstEtd;
	}

	public Integer getIsCarryDate() {
		return isCarryDate;
	}

	public void setIsCarryDate(Integer isCarryDate) {
		this.isCarryDate = isCarryDate;
	}

	public String getCarryDate() {
		return carryDate;
	}

	public void setCarryDate(String carryDate) {
		this.carryDate = carryDate;
	}

	public Integer getIsAssembleDate() {
		return isAssembleDate;
	}

	public void setIsAssembleDate(Integer isAssembleDate) {
		this.isAssembleDate = isAssembleDate;
	}

	public String getAssembleDate() {
		return assembleDate;
	}

	public void setAssembleDate(String assembleDate) {
		this.assembleDate = assembleDate;
	}

	public Integer getIsLoadOverDate() {
		return isLoadOverDate;
	}

	public void setIsLoadOverDate(Integer isLoadOverDate) {
		this.isLoadOverDate = isLoadOverDate;
	}

	public String getLoadOverDate() {
		return loadOverDate;
	}

	public void setLoadOverDate(String loadOverDate) {
		this.loadOverDate = loadOverDate;
	}

	public Integer getIsDeliveryOver() {
		return isDeliveryOver;
	}

	public void setIsDeliveryOver(Integer isDeliveryOver) {
		this.isDeliveryOver = isDeliveryOver;
	}

	public String getDeliveryOverDate() {
		return deliveryOverDate;
	}

	public void setDeliveryOverDate(String deliveryOverDate) {
		this.deliveryOverDate = deliveryOverDate;
	}

	public Integer getIsBillRegisterDate() {
		return isBillRegisterDate;
	}

	public void setIsBillRegisterDate(Integer isBillRegisterDate) {
		this.isBillRegisterDate = isBillRegisterDate;
	}

	public String getBillRegisterDate() {
		return billRegisterDate;
	}

	public void setBillRegisterDate(String billRegisterDate) {
		this.billRegisterDate = billRegisterDate;
	}

	public Integer getIsDestTruckPlanTime() {
		return isDestTruckPlanTime;
	}

	public void setIsDestTruckPlanTime(Integer isDestTruckPlanTime) {
		this.isDestTruckPlanTime = isDestTruckPlanTime;
	}

	public String getDestTruckPlanTime() {
		return destTruckPlanTime;
	}

	public void setDestTruckPlanTime(String destTruckPlanTime) {
		this.destTruckPlanTime = destTruckPlanTime;
	}

	public Integer getIsBdDestDate() {
		return isBdDestDate;
	}

	public void setIsBdDestDate(Integer isBdDestDate) {
		this.isBdDestDate = isBdDestDate;
	}

	public String getBdDestDate() {
		return bdDestDate;
	}

	public void setBdDestDate(String bdDestDate) {
		this.bdDestDate = bdDestDate;
	}

	public Integer getIsHcDate() {
		return isHcDate;
	}

	public void setIsHcDate(Integer isHcDate) {
		this.isHcDate = isHcDate;
	}

	public String getHcDate() {
		return hcDate;
	}

	public void setHcDate(String hcDate) {
		this.hcDate = hcDate;
	}

	public String getThId() {
		return thId;
	}

	public void setThId(String thId) {
		this.thId = thId;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}

	public String getFinishedOpt() {
		return finishedOpt;
	}

	public void setFinishedOpt(String finishedOpt) {
		this.finishedOpt = finishedOpt;
	}

	public String getFinishedDate() {
		return finishedDate;
	}

	public void setFinishedDate(String finishedDate) {
		this.finishedDate = finishedDate;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getIsMtddDate() {
		return isMtddDate;
	}

	public void setIsMtddDate(Integer isMtddDate) {
		this.isMtddDate = isMtddDate;
	}

	public String getMtddDate() {
		return mtddDate;
	}

	public void setMtddDate(String mtddDate) {
		this.mtddDate = mtddDate;
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
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDeptId() {
		return updatedDeptId;
	}

	public void setUpdatedDeptId(String updatedDeptId) {
		this.updatedDeptId = updatedDeptId;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String[] getBillsIds() {
		return billsIds;
	}

	public void setBillsIds(String[] billsIds) {
		this.billsIds = billsIds;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.billsId);
		dest.writeValue(this.flag);
		dest.writeValue(this.isFirstEta);
		dest.writeString(this.firstEta);
		dest.writeValue(this.firstEtaDay);
		dest.writeValue(this.isBdDate);
		dest.writeString(this.bdDate);
		dest.writeValue(this.isTruckPlanTime);
		dest.writeString(this.truckPlanTime);
		dest.writeValue(this.isConfirmDate);
		dest.writeString(this.confirmDate);
		dest.writeValue(this.isRelivePlanTime);
		dest.writeString(this.relivePlanTime);
		dest.writeValue(this.isLastLoadDate);
		dest.writeString(this.lastLoadDate);
		dest.writeValue(this.isMarketCheckDate);
		dest.writeString(this.marketCheckDate);
		dest.writeValue(this.isComDate);
		dest.writeString(this.comDate);
		dest.writeString(this.remark);
		dest.writeValue(this.isGoodskeepDate);
		dest.writeString(this.goodskeepDate);
		dest.writeString(this.goodskeepReason);
		dest.writeValue(this.isFirstEtd);
		dest.writeString(this.firstEtd);
		dest.writeValue(this.isCarryDate);
		dest.writeString(this.carryDate);
		dest.writeValue(this.isAssembleDate);
		dest.writeString(this.assembleDate);
		dest.writeValue(this.isLoadOverDate);
		dest.writeString(this.loadOverDate);
		dest.writeValue(this.isDeliveryOver);
		dest.writeString(this.deliveryOverDate);
		dest.writeValue(this.isBillRegisterDate);
		dest.writeString(this.billRegisterDate);
		dest.writeValue(this.isDestTruckPlanTime);
		dest.writeString(this.destTruckPlanTime);
		dest.writeValue(this.isBdDestDate);
		dest.writeString(this.bdDestDate);
		dest.writeValue(this.isHcDate);
		dest.writeString(this.hcDate);
		dest.writeString(this.thId);
		dest.writeValue(this.isValid);
		dest.writeValue(this.isFinish);
		dest.writeString(this.finishedOpt);
		dest.writeString(this.finishedDate);
		dest.writeValue(this.auditStatus);
		dest.writeString(this.auditor);
		dest.writeString(this.auditDate);
		dest.writeValue(this.isMtddDate);
		dest.writeString(this.mtddDate);
		dest.writeString(this.createdBy);
		dest.writeString(this.createdDeptId);
		dest.writeString(this.created);
		dest.writeString(this.updatedBy);
		dest.writeString(this.updatedDeptId);
		dest.writeString(this.updated);
		dest.writeStringArray(this.billsIds);
	}

	public LogBillsTrack() {
	}

	protected LogBillsTrack(Parcel in) {
		this.id = in.readString();
		this.billsId = in.readString();
		this.flag = (Integer) in.readValue(Integer.class.getClassLoader());
		this.isFirstEta = (Integer) in.readValue(Integer.class.getClassLoader());
		this.firstEta = in.readString();
		this.firstEtaDay = (Integer) in.readValue(Integer.class.getClassLoader());
		this.isBdDate = (Integer) in.readValue(Integer.class.getClassLoader());
		this.bdDate = in.readString();
		this.isTruckPlanTime = (Integer) in.readValue(Integer.class.getClassLoader());
		this.truckPlanTime = in.readString();
		this.isConfirmDate = (Integer) in.readValue(Integer.class.getClassLoader());
		this.confirmDate = in.readString();
		this.isRelivePlanTime = (Integer) in.readValue(Integer.class.getClassLoader());
		this.relivePlanTime = in.readString();
		this.isLastLoadDate = (Integer) in.readValue(Integer.class.getClassLoader());
		this.lastLoadDate = in.readString();
		this.isMarketCheckDate = (Integer) in.readValue(Integer.class.getClassLoader());
		this.marketCheckDate = in.readString();
		this.isComDate = (Integer) in.readValue(Integer.class.getClassLoader());
		this.comDate = in.readString();
		this.remark = in.readString();
		this.isGoodskeepDate = (Integer) in.readValue(Integer.class.getClassLoader());
		this.goodskeepDate = in.readString();
		this.goodskeepReason = in.readString();
		this.isFirstEtd = (Integer) in.readValue(Integer.class.getClassLoader());
		this.firstEtd = in.readString();
		this.isCarryDate = (Integer) in.readValue(Integer.class.getClassLoader());
		this.carryDate = in.readString();
		this.isAssembleDate = (Integer) in.readValue(Integer.class.getClassLoader());
		this.assembleDate = in.readString();
		this.isLoadOverDate = (Integer) in.readValue(Integer.class.getClassLoader());
		this.loadOverDate = in.readString();
		this.isDeliveryOver = (Integer) in.readValue(Integer.class.getClassLoader());
		this.deliveryOverDate = in.readString();
		this.isBillRegisterDate = (Integer) in.readValue(Integer.class.getClassLoader());
		this.billRegisterDate = in.readString();
		this.isDestTruckPlanTime = (Integer) in.readValue(Integer.class.getClassLoader());
		this.destTruckPlanTime = in.readString();
		this.isBdDestDate = (Integer) in.readValue(Integer.class.getClassLoader());
		this.bdDestDate = in.readString();
		this.isHcDate = (Integer) in.readValue(Integer.class.getClassLoader());
		this.hcDate = in.readString();
		this.thId = in.readString();
		this.isValid = (Integer) in.readValue(Integer.class.getClassLoader());
		this.isFinish = (Integer) in.readValue(Integer.class.getClassLoader());
		this.finishedOpt = in.readString();
		this.finishedDate = in.readString();
		this.auditStatus = (Integer) in.readValue(Integer.class.getClassLoader());
		this.auditor = in.readString();
		this.auditDate = in.readString();
		this.isMtddDate = (Integer) in.readValue(Integer.class.getClassLoader());
		this.mtddDate = in.readString();
		this.createdBy = in.readString();
		this.createdDeptId = in.readString();
		this.created = in.readString();
		this.updatedBy = in.readString();
		this.updatedDeptId = in.readString();
		this.updated = in.readString();
		this.billsIds = in.createStringArray();
	}

	public static final Creator<LogBillsTrack> CREATOR = new Creator<LogBillsTrack>() {
		@Override
		public LogBillsTrack createFromParcel(Parcel source) {
			return new LogBillsTrack(source);
		}

		@Override
		public LogBillsTrack[] newArray(int size) {
			return new LogBillsTrack[size];
		}
	};
}