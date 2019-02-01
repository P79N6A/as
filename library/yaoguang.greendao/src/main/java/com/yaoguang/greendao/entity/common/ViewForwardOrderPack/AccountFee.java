package com.yaoguang.greendao.entity.common.ViewForwardOrderPack;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 财务-应收应付
 *
 * @author heyonggang
 * @date 2016年12月5日下午2:42:13
 */

public class AccountFee implements Parcelable{
	private String id;

	private String billsId;

	private Integer billType;

	private String feeNo;

	private Integer sortno;

	private String flow;

	private Integer isOver;

	private Integer accoAttr;

	private String shipperPre;

	private String shipper;

	private String feeId;

	private Double quantity;

	private String unit;

	private Double price;

	private Integer payKind;

	private Double totalup;

	private Integer inOrOut;

	private Integer bookingOr;

	private String optdtm;

	private String remark;

	private String remark2;

	private Integer isChecked;

	private String checkedAuditor;

	private String checkedDate;

	private Integer status;

	private Integer isAudit;

	private Integer isAcco;

	private String serialNumber;

	private String inputMember;

	private Double dealMoney;

	private String dealDeptName;

	private Integer ifGather;

	private String accoDate;

	private String accoNo;

	private String accoOptid;

	private String contNo;

	private String infotype;

	private String infotypeId;

	private String planPayTime;

	private String clientType;

	private String shipperOrder;

	private String auditNo;

	private String auditNoDate;

	private String auditNoOperator;

	private String auditRemark;

	private String auditNote;

	private String gatherNo;

	private String auditDate;

	private Integer isHadGather;

	private Integer isDealAccount;

	private String bank;

	private String statusOperator;

	private String statusDate;

	private String statusNo;

	private String payType;

	private String voucherNo;

	private String hadGatherDate;

	private String statusRemark;

	private String clientId;

	private String createdBy;

	private String createdDeptId;
	private String created;

	private String updatedBy;

	private String dispatch;

	private String updated;

	private Integer isValid;

	// 订单id集合
	private String[] billsIds;

	// 主键集合
	private String[] ids;

	// 剩余金额
	public Double getLeftMoney() {
		Double d = this.totalup == null ? 0d : this.totalup;
		Double d1 = this.dealMoney == null ? 0d : this.dealMoney;
		return d - d1;
	};

	// 费用核销明细
	private List<AccountFeeAduitDetail> accountFeeAduitDetails;

	// 指定货柜ID
	private String assignSonoId;

	//核算对象类型(1-实际核算对象，2-工作单托运人，3-工作单船公司，4-工作单货代公司，5-工作单保险公司，6-工作单起始拖车，7-工作单目的拖车，8-工作单业务员)
	private String shipperType;

	//计费方式(1-每柜单价，2- 柜合计金额，3- 每票单价，4-票合计单价，5-每吨单价，6-平均至每票，7-货柜毛重单价，8-货柜净重单价)
	private String billingWay;

	//用户填写金额(用于费用批量录入)
	private Double userMoney;

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

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public String getFeeNo() {
		return feeNo;
	}

	public void setFeeNo(String feeNo) {
		this.feeNo = feeNo;
	}

	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	public Integer getIsOver() {
		return isOver;
	}

	public void setIsOver(Integer isOver) {
		this.isOver = isOver;
	}

	public Integer getAccoAttr() {
		return accoAttr;
	}

	public void setAccoAttr(Integer accoAttr) {
		this.accoAttr = accoAttr;
	}

	public String getShipperPre() {
		return shipperPre;
	}

	public void setShipperPre(String shipperPre) {
		this.shipperPre = shipperPre;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getFeeId() {
		return feeId;
	}

	public void setFeeId(String feeId) {
		this.feeId = feeId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getPayKind() {
		return payKind;
	}

	public void setPayKind(Integer payKind) {
		this.payKind = payKind;
	}

	public Double getTotalup() {
		return totalup;
	}

	public void setTotalup(Double totalup) {
		this.totalup = totalup;
	}

	public Integer getInOrOut() {
		return inOrOut;
	}

	public void setInOrOut(Integer inOrOut) {
		this.inOrOut = inOrOut;
	}

	public Integer getBookingOr() {
		return bookingOr;
	}

	public void setBookingOr(Integer bookingOr) {
		this.bookingOr = bookingOr;
	}

	public String getOptdtm() {
		return optdtm;
	}

	public void setOptdtm(String optdtm) {
		this.optdtm = optdtm;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public Integer getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}

	public String getCheckedAuditor() {
		return checkedAuditor;
	}

	public void setCheckedAuditor(String checkedAuditor) {
		this.checkedAuditor = checkedAuditor;
	}

	public String getCheckedDate() {
		return checkedDate;
	}

	public void setCheckedDate(String checkedDate) {
		this.checkedDate = checkedDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}

	public Integer getIsAcco() {
		return isAcco;
	}

	public void setIsAcco(Integer isAcco) {
		this.isAcco = isAcco;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getInputMember() {
		return inputMember;
	}

	public void setInputMember(String inputMember) {
		this.inputMember = inputMember;
	}

	public Double getDealMoney() {
		return dealMoney;
	}

	public void setDealMoney(Double dealMoney) {
		this.dealMoney = dealMoney;
	}

	public String getDealDeptName() {
		return dealDeptName;
	}

	public void setDealDeptName(String dealDeptName) {
		this.dealDeptName = dealDeptName;
	}

	public Integer getIfGather() {
		return ifGather;
	}

	public void setIfGather(Integer ifGather) {
		this.ifGather = ifGather;
	}

	public String getAccoDate() {
		return accoDate;
	}

	public void setAccoDate(String accoDate) {
		this.accoDate = accoDate;
	}

	public String getAccoNo() {
		return accoNo;
	}

	public void setAccoNo(String accoNo) {
		this.accoNo = accoNo;
	}

	public String getAccoOptid() {
		return accoOptid;
	}

	public void setAccoOptid(String accoOptid) {
		this.accoOptid = accoOptid;
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public String getInfotype() {
		return infotype;
	}

	public void setInfotype(String infotype) {
		this.infotype = infotype;
	}

	public String getInfotypeId() {
		return infotypeId;
	}

	public void setInfotypeId(String infotypeId) {
		this.infotypeId = infotypeId;
	}

	public String getPlanPayTime() {
		return planPayTime;
	}

	public void setPlanPayTime(String planPayTime) {
		this.planPayTime = planPayTime;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getShipperOrder() {
		return shipperOrder;
	}

	public void setShipperOrder(String shipperOrder) {
		this.shipperOrder = shipperOrder;
	}

	public String getAuditNo() {
		return auditNo;
	}

	public void setAuditNo(String auditNo) {
		this.auditNo = auditNo;
	}

	public String getAuditNoDate() {
		return auditNoDate;
	}

	public void setAuditNoDate(String auditNoDate) {
		this.auditNoDate = auditNoDate;
	}

	public String getAuditNoOperator() {
		return auditNoOperator;
	}

	public void setAuditNoOperator(String auditNoOperator) {
		this.auditNoOperator = auditNoOperator;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public String getAuditNote() {
		return auditNote;
	}

	public void setAuditNote(String auditNote) {
		this.auditNote = auditNote;
	}

	public String getGatherNo() {
		return gatherNo;
	}

	public void setGatherNo(String gatherNo) {
		this.gatherNo = gatherNo;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getIsHadGather() {
		return isHadGather;
	}

	public void setIsHadGather(Integer isHadGather) {
		this.isHadGather = isHadGather;
	}

	public Integer getIsDealAccount() {
		return isDealAccount;
	}

	public void setIsDealAccount(Integer isDealAccount) {
		this.isDealAccount = isDealAccount;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getStatusOperator() {
		return statusOperator;
	}

	public void setStatusOperator(String statusOperator) {
		this.statusOperator = statusOperator;
	}

	public String getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}

	public String getStatusNo() {
		return statusNo;
	}

	public void setStatusNo(String statusNo) {
		this.statusNo = statusNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getHadGatherDate() {
		return hadGatherDate;
	}

	public void setHadGatherDate(String hadGatherDate) {
		this.hadGatherDate = hadGatherDate;
	}

	public String getStatusRemark() {
		return statusRemark;
	}

	public void setStatusRemark(String statusRemark) {
		this.statusRemark = statusRemark;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getDispatch() {
		return dispatch;
	}

	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String[] getBillsIds() {
		return billsIds;
	}

	public void setBillsIds(String[] billsIds) {
		this.billsIds = billsIds;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public List<AccountFeeAduitDetail> getAccountFeeAduitDetails() {
		return accountFeeAduitDetails;
	}

	public void setAccountFeeAduitDetails(List<AccountFeeAduitDetail> accountFeeAduitDetails) {
		this.accountFeeAduitDetails = accountFeeAduitDetails;
	}

	public String getAssignSonoId() {
		return assignSonoId;
	}

	public void setAssignSonoId(String assignSonoId) {
		this.assignSonoId = assignSonoId;
	}

	public String getShipperType() {
		return shipperType;
	}

	public void setShipperType(String shipperType) {
		this.shipperType = shipperType;
	}

	public String getBillingWay() {
		return billingWay;
	}

	public void setBillingWay(String billingWay) {
		this.billingWay = billingWay;
	}

	public Double getUserMoney() {
		return userMoney;
	}

	public void setUserMoney(Double userMoney) {
		this.userMoney = userMoney;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.billsId);
		dest.writeValue(this.billType);
		dest.writeString(this.feeNo);
		dest.writeValue(this.sortno);
		dest.writeString(this.flow);
		dest.writeValue(this.isOver);
		dest.writeValue(this.accoAttr);
		dest.writeString(this.shipperPre);
		dest.writeString(this.shipper);
		dest.writeString(this.feeId);
		dest.writeValue(this.quantity);
		dest.writeString(this.unit);
		dest.writeValue(this.price);
		dest.writeValue(this.payKind);
		dest.writeValue(this.totalup);
		dest.writeValue(this.inOrOut);
		dest.writeValue(this.bookingOr);
		dest.writeString(this.optdtm);
		dest.writeString(this.remark);
		dest.writeString(this.remark2);
		dest.writeValue(this.isChecked);
		dest.writeString(this.checkedAuditor);
		dest.writeString(this.checkedDate);
		dest.writeValue(this.status);
		dest.writeValue(this.isAudit);
		dest.writeValue(this.isAcco);
		dest.writeString(this.serialNumber);
		dest.writeString(this.inputMember);
		dest.writeValue(this.dealMoney);
		dest.writeString(this.dealDeptName);
		dest.writeValue(this.ifGather);
		dest.writeString(this.accoDate);
		dest.writeString(this.accoNo);
		dest.writeString(this.accoOptid);
		dest.writeString(this.contNo);
		dest.writeString(this.infotype);
		dest.writeString(this.infotypeId);
		dest.writeString(this.planPayTime);
		dest.writeString(this.clientType);
		dest.writeString(this.shipperOrder);
		dest.writeString(this.auditNo);
		dest.writeString(this.auditNoDate);
		dest.writeString(this.auditNoOperator);
		dest.writeString(this.auditRemark);
		dest.writeString(this.auditNote);
		dest.writeString(this.gatherNo);
		dest.writeString(this.auditDate);
		dest.writeValue(this.isHadGather);
		dest.writeValue(this.isDealAccount);
		dest.writeString(this.bank);
		dest.writeString(this.statusOperator);
		dest.writeString(this.statusDate);
		dest.writeString(this.statusNo);
		dest.writeString(this.payType);
		dest.writeString(this.voucherNo);
		dest.writeString(this.hadGatherDate);
		dest.writeString(this.statusRemark);
		dest.writeString(this.clientId);
		dest.writeString(this.createdBy);
		dest.writeString(this.createdDeptId);
		dest.writeString(this.created);
		dest.writeString(this.updatedBy);
		dest.writeString(this.dispatch);
		dest.writeString(this.updated);
		dest.writeValue(this.isValid);
		dest.writeStringArray(this.billsIds);
		dest.writeStringArray(this.ids);
		dest.writeList(this.accountFeeAduitDetails);
		dest.writeString(this.assignSonoId);
		dest.writeString(this.shipperType);
		dest.writeString(this.billingWay);
		dest.writeValue(this.userMoney);
	}

	public AccountFee() {
	}

	protected AccountFee(Parcel in) {
		this.id = in.readString();
		this.billsId = in.readString();
		this.billType = (Integer) in.readValue(Integer.class.getClassLoader());
		this.feeNo = in.readString();
		this.sortno = (Integer) in.readValue(Integer.class.getClassLoader());
		this.flow = in.readString();
		this.isOver = (Integer) in.readValue(Integer.class.getClassLoader());
		this.accoAttr = (Integer) in.readValue(Integer.class.getClassLoader());
		this.shipperPre = in.readString();
		this.shipper = in.readString();
		this.feeId = in.readString();
		this.quantity = (Double) in.readValue(Double.class.getClassLoader());
		this.unit = in.readString();
		this.price = (Double) in.readValue(Double.class.getClassLoader());
		this.payKind = (Integer) in.readValue(Integer.class.getClassLoader());
		this.totalup = (Double) in.readValue(Double.class.getClassLoader());
		this.inOrOut = (Integer) in.readValue(Integer.class.getClassLoader());
		this.bookingOr = (Integer) in.readValue(Integer.class.getClassLoader());
		this.optdtm = in.readString();
		this.remark = in.readString();
		this.remark2 = in.readString();
		this.isChecked = (Integer) in.readValue(Integer.class.getClassLoader());
		this.checkedAuditor = in.readString();
		this.checkedDate = in.readString();
		this.status = (Integer) in.readValue(Integer.class.getClassLoader());
		this.isAudit = (Integer) in.readValue(Integer.class.getClassLoader());
		this.isAcco = (Integer) in.readValue(Integer.class.getClassLoader());
		this.serialNumber = in.readString();
		this.inputMember = in.readString();
		this.dealMoney = (Double) in.readValue(Double.class.getClassLoader());
		this.dealDeptName = in.readString();
		this.ifGather = (Integer) in.readValue(Integer.class.getClassLoader());
		this.accoDate = in.readString();
		this.accoNo = in.readString();
		this.accoOptid = in.readString();
		this.contNo = in.readString();
		this.infotype = in.readString();
		this.infotypeId = in.readString();
		this.planPayTime = in.readString();
		this.clientType = in.readString();
		this.shipperOrder = in.readString();
		this.auditNo = in.readString();
		this.auditNoDate = in.readString();
		this.auditNoOperator = in.readString();
		this.auditRemark = in.readString();
		this.auditNote = in.readString();
		this.gatherNo = in.readString();
		this.auditDate = in.readString();
		this.isHadGather = (Integer) in.readValue(Integer.class.getClassLoader());
		this.isDealAccount = (Integer) in.readValue(Integer.class.getClassLoader());
		this.bank = in.readString();
		this.statusOperator = in.readString();
		this.statusDate = in.readString();
		this.statusNo = in.readString();
		this.payType = in.readString();
		this.voucherNo = in.readString();
		this.hadGatherDate = in.readString();
		this.statusRemark = in.readString();
		this.clientId = in.readString();
		this.createdBy = in.readString();
		this.createdDeptId = in.readString();
		this.created = in.readString();
		this.updatedBy = in.readString();
		this.dispatch = in.readString();
		this.updated = in.readString();
		this.isValid = (Integer) in.readValue(Integer.class.getClassLoader());
		this.billsIds = in.createStringArray();
		this.ids = in.createStringArray();
		this.accountFeeAduitDetails = new ArrayList<AccountFeeAduitDetail>();
		in.readList(this.accountFeeAduitDetails, AccountFeeAduitDetail.class.getClassLoader());
		this.assignSonoId = in.readString();
		this.shipperType = in.readString();
		this.billingWay = in.readString();
		this.userMoney = (Double) in.readValue(Double.class.getClassLoader());
	}

	public static final Creator<AccountFee> CREATOR = new Creator<AccountFee>() {
		@Override
		public AccountFee createFromParcel(Parcel source) {
			return new AccountFee(source);
		}

		@Override
		public AccountFee[] newArray(int size) {
			return new AccountFee[size];
		}
	};
}