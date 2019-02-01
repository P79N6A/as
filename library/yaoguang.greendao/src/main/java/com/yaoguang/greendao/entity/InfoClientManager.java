package com.yaoguang.greendao.entity;

import java.io.Serializable;


/**
 * 基本资料-往来单位主信息实体类
 * 托运人
 *
 * @author ZhangDeQuan
 * @time 2016年11月2日 上午11:16:38
 */
public class InfoClientManager implements Serializable {
    private static final long serialVersionUID = 4226001977353996756L;

    private String id;

    private String codeId;

    private String companyShort;

    private String companyEngShort;

    private String companyChina;

    private String companyEnglish;

    private String addressChina;

    private String addressEnglish;

    private String eMail;

    private String wwwAddress;

    private Integer isShipOwner;

    private Integer isShipCompany;

    private Integer isShipSupply;

    private Integer isCustom;

    private Integer isPortSupply;

    private Integer isDock;

    private Integer isTyr;

    private Integer isShipper;

    private Integer isShr;

    private Integer isFhr;

    private Integer isOther;

    private Integer isForward;

    private Integer isTzr;

    private Integer isTruck;

    private Integer isDestTruck;

    private Integer isLanding;

    private Integer isContainer;

    private Integer isInsurer;

    private Integer isExpress;

    private Integer isMerchant;

    private Integer isHackman;

    private Integer ifAgent;

    private String comPerson;

    private String comTel;

    private String comFax;

    private String personMobile;

    private String personPager;

    private String accountLxr;

    private String accountTel;

    private String accountFax;

    private String bankName;

    private String bankCode;

    private Integer accountDebt;

    private Integer sqDays;

    private String accountCurrency;

    private String accountType;

    private String payKind;

    private String accountDebtDesc;

    private String operator2;

    private String companyTitle;

    private String kmId;

    private String helpcode;

    private String employeeId;

    private Integer isSms;

    private String goods;

    private String deptid;

    private Integer isShare;

    private Integer isOk;

    private Integer isChecked;

    private String checkedOpt;

    private String checkedDtm;

    private String kind1;

    private String kind2;

    private String kind3;

    private String kind4;

    private String kind5;

    private String kind6;

    private String kind7;

    private String kind8;

    private String kind9;

    private String kind10;

    private String clientAttr;

    private Integer isBlacklist;

    private String quotationNo;

    private String clientType;

    private String clientArrear;

    private String clientProfit;

    private String clientId;

    private Integer flag;

    private String remark;

    private String createdBy;

    private String createdDeptId;

    private String created;

    private String updatedBy;

    private String updatedDeptId;

    private String updated;

    // 所属部门名称
    private String officeName;

    // 区域名称
    private String areaName;

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

    public String getCompanyShort() {
        return companyShort;
    }

    public void setCompanyShort(String companyShort) {
        this.companyShort = companyShort == null ? null : companyShort.trim();
    }

    public String getCompanyEngShort() {
        return companyEngShort;
    }

    public void setCompanyEngShort(String companyEngShort) {
        this.companyEngShort = companyEngShort == null ? null : companyEngShort.trim();
    }

    public String getCompanyChina() {
        return companyChina;
    }

    public void setCompanyChina(String companyChina) {
        this.companyChina = companyChina == null ? null : companyChina.trim();
    }

    public String getCompanyEnglish() {
        return companyEnglish;
    }

    public void setCompanyEnglish(String companyEnglish) {
        this.companyEnglish = companyEnglish == null ? null : companyEnglish.trim();
    }

    public String getAddressChina() {
        return addressChina;
    }

    public void setAddressChina(String addressChina) {
        this.addressChina = addressChina == null ? null : addressChina.trim();
    }

    public String getAddressEnglish() {
        return addressEnglish;
    }

    public void setAddressEnglish(String addressEnglish) {
        this.addressEnglish = addressEnglish == null ? null : addressEnglish.trim();
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail == null ? null : eMail.trim();
    }

    public String getWwwAddress() {
        return wwwAddress;
    }

    public void setWwwAddress(String wwwAddress) {
        this.wwwAddress = wwwAddress == null ? null : wwwAddress.trim();
    }

    public Integer getIsShipOwner() {
        return isShipOwner;
    }

    public void setIsShipOwner(Integer isShipOwner) {
        this.isShipOwner = isShipOwner;
    }

    public Integer getIsShipCompany() {
        return isShipCompany;
    }

    public void setIsShipCompany(Integer isShipCompany) {
        this.isShipCompany = isShipCompany;
    }

    public Integer getIsShipSupply() {
        return isShipSupply;
    }

    public void setIsShipSupply(Integer isShipSupply) {
        this.isShipSupply = isShipSupply;
    }

    public Integer getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(Integer isCustom) {
        this.isCustom = isCustom;
    }

    public Integer getIsPortSupply() {
        return isPortSupply;
    }

    public void setIsPortSupply(Integer isPortSupply) {
        this.isPortSupply = isPortSupply;
    }

    public Integer getIsDock() {
        return isDock;
    }

    public void setIsDock(Integer isDock) {
        this.isDock = isDock;
    }

    public Integer getIsTyr() {
        return isTyr;
    }

    public void setIsTyr(Integer isTyr) {
        this.isTyr = isTyr;
    }

    public Integer getIsShipper() {
        return isShipper;
    }

    public void setIsShipper(Integer isShipper) {
        this.isShipper = isShipper;
    }

    public Integer getIsShr() {
        return isShr;
    }

    public void setIsShr(Integer isShr) {
        this.isShr = isShr;
    }

    public Integer getIsFhr() {
        return isFhr;
    }

    public void setIsFhr(Integer isFhr) {
        this.isFhr = isFhr;
    }

    public Integer getIsOther() {
        return isOther;
    }

    public void setIsOther(Integer isOther) {
        this.isOther = isOther;
    }

    public Integer getIsForward() {
        return isForward;
    }

    public void setIsForward(Integer isForward) {
        this.isForward = isForward;
    }

    public Integer getIsTzr() {
        return isTzr;
    }

    public void setIsTzr(Integer isTzr) {
        this.isTzr = isTzr;
    }

    public Integer getIsTruck() {
        return isTruck;
    }

    public void setIsTruck(Integer isTruck) {
        this.isTruck = isTruck;
    }

    public Integer getIsDestTruck() {
        return isDestTruck;
    }

    public void setIsDestTruck(Integer isDestTruck) {
        this.isDestTruck = isDestTruck;
    }

    public Integer getIsLanding() {
        return isLanding;
    }

    public void setIsLanding(Integer isLanding) {
        this.isLanding = isLanding;
    }

    public Integer getIsContainer() {
        return isContainer;
    }

    public void setIsContainer(Integer isContainer) {
        this.isContainer = isContainer;
    }

    public Integer getIsInsurer() {
        return isInsurer;
    }

    public void setIsInsurer(Integer isInsurer) {
        this.isInsurer = isInsurer;
    }

    public Integer getIsExpress() {
        return isExpress;
    }

    public void setIsExpress(Integer isExpress) {
        this.isExpress = isExpress;
    }

    public Integer getIsMerchant() {
        return isMerchant;
    }

    public void setIsMerchant(Integer isMerchant) {
        this.isMerchant = isMerchant;
    }

    public Integer getIsHackman() {
        return isHackman;
    }

    public void setIsHackman(Integer isHackman) {
        this.isHackman = isHackman;
    }

    public Integer getIfAgent() {
        return ifAgent;
    }

    public void setIfAgent(Integer ifAgent) {
        this.ifAgent = ifAgent;
    }

    public String getComPerson() {
        return comPerson;
    }

    public void setComPerson(String comPerson) {
        this.comPerson = comPerson == null ? null : comPerson.trim();
    }

    public String getComTel() {
        return comTel;
    }

    public void setComTel(String comTel) {
        this.comTel = comTel == null ? null : comTel.trim();
    }

    public String getComFax() {
        return comFax;
    }

    public void setComFax(String comFax) {
        this.comFax = comFax == null ? null : comFax.trim();
    }

    public String getPersonMobile() {
        return personMobile;
    }

    public void setPersonMobile(String personMobile) {
        this.personMobile = personMobile == null ? null : personMobile.trim();
    }

    public String getPersonPager() {
        return personPager;
    }

    public void setPersonPager(String personPager) {
        this.personPager = personPager == null ? null : personPager.trim();
    }

    public String getAccountLxr() {
        return accountLxr;
    }

    public void setAccountLxr(String accountLxr) {
        this.accountLxr = accountLxr == null ? null : accountLxr.trim();
    }

    public String getAccountTel() {
        return accountTel;
    }

    public void setAccountTel(String accountTel) {
        this.accountTel = accountTel == null ? null : accountTel.trim();
    }

    public String getAccountFax() {
        return accountFax;
    }

    public void setAccountFax(String accountFax) {
        this.accountFax = accountFax == null ? null : accountFax.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public Integer getAccountDebt() {
        return accountDebt;
    }

    public void setAccountDebt(Integer accountDebt) {
        this.accountDebt = accountDebt;
    }

    public Integer getSqDays() {
        return sqDays;
    }

    public void setSqDays(Integer sqDays) {
        this.sqDays = sqDays;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency == null ? null : accountCurrency.trim();
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind == null ? null : payKind.trim();
    }

    public String getAccountDebtDesc() {
        return accountDebtDesc;
    }

    public void setAccountDebtDesc(String accountDebtDesc) {
        this.accountDebtDesc = accountDebtDesc == null ? null : accountDebtDesc.trim();
    }

    public String getOperator2() {
        return operator2;
    }

    public void setOperator2(String operator2) {
        this.operator2 = operator2 == null ? null : operator2.trim();
    }

    public String getCompanyTitle() {
        return companyTitle;
    }

    public void setCompanyTitle(String companyTitle) {
        this.companyTitle = companyTitle == null ? null : companyTitle.trim();
    }

    public String getKmId() {
        return kmId;
    }

    public void setKmId(String kmId) {
        this.kmId = kmId == null ? null : kmId.trim();
    }

    public String getHelpcode() {
        return helpcode;
    }

    public void setHelpcode(String helpcode) {
        this.helpcode = helpcode == null ? null : helpcode.trim();
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId == null ? null : employeeId.trim();
    }

    public Integer getIsSms() {
        return isSms;
    }

    public void setIsSms(Integer isSms) {
        this.isSms = isSms;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods == null ? null : goods.trim();
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }

    public Integer getIsShare() {
        return isShare;
    }

    public void setIsShare(Integer isShare) {
        this.isShare = isShare;
    }

    public Integer getIsOk() {
        return isOk;
    }

    public void setIsOk(Integer isOk) {
        this.isOk = isOk;
    }

    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }

    public String getCheckedOpt() {
        return checkedOpt;
    }

    public void setCheckedOpt(String checkedOpt) {
        this.checkedOpt = checkedOpt == null ? null : checkedOpt.trim();
    }

    public String getCheckedDtm() {
        return checkedDtm;
    }

    public void setCheckedDtm(String checkedDtm) {
        this.checkedDtm = checkedDtm;
    }

    public String getKind1() {
        return kind1;
    }

    public void setKind1(String kind1) {
        this.kind1 = kind1 == null ? null : kind1.trim();
    }

    public String getKind2() {
        return kind2;
    }

    public void setKind2(String kind2) {
        this.kind2 = kind2 == null ? null : kind2.trim();
    }

    public String getKind3() {
        return kind3;
    }

    public void setKind3(String kind3) {
        this.kind3 = kind3 == null ? null : kind3.trim();
    }

    public String getKind4() {
        return kind4;
    }

    public void setKind4(String kind4) {
        this.kind4 = kind4 == null ? null : kind4.trim();
    }

    public String getKind5() {
        return kind5;
    }

    public void setKind5(String kind5) {
        this.kind5 = kind5 == null ? null : kind5.trim();
    }

    public String getKind6() {
        return kind6;
    }

    public void setKind6(String kind6) {
        this.kind6 = kind6 == null ? null : kind6.trim();
    }

    public String getKind7() {
        return kind7;
    }

    public void setKind7(String kind7) {
        this.kind7 = kind7 == null ? null : kind7.trim();
    }

    public String getKind8() {
        return kind8;
    }

    public void setKind8(String kind8) {
        this.kind8 = kind8 == null ? null : kind8.trim();
    }

    public String getKind9() {
        return kind9;
    }

    public void setKind9(String kind9) {
        this.kind9 = kind9 == null ? null : kind9.trim();
    }

    public String getKind10() {
        return kind10;
    }

    public void setKind10(String kind10) {
        this.kind10 = kind10 == null ? null : kind10.trim();
    }

    public String getClientAttr() {
        return clientAttr;
    }

    public void setClientAttr(String clientAttr) {
        this.clientAttr = clientAttr == null ? null : clientAttr.trim();
    }

    public Integer getIsBlacklist() {
        return isBlacklist;
    }

    public void setIsBlacklist(Integer isBlacklist) {
        this.isBlacklist = isBlacklist;
    }

    public String getQuotationNo() {
        return quotationNo;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo == null ? null : quotationNo.trim();
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType == null ? null : clientType.trim();
    }

    public String getClientArrear() {
        return clientArrear;
    }

    public void setClientArrear(String clientArrear) {
        this.clientArrear = clientArrear == null ? null : clientArrear.trim();
    }

    public String getClientProfit() {
        return clientProfit;
    }

    public void setClientProfit(String clientProfit) {
        this.clientProfit = clientProfit == null ? null : clientProfit.trim();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
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

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}