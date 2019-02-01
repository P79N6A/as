package com.yaoguang.greendao.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


/**
 * 货主端用户信息对象
 *
 * @author wangxiaohui
 * @date 2017年6月14日 下午5:03:24
 */
@Entity
public class UserOwner implements Parcelable {

    @Id(autoincrement = true)
    private Long lid;

    private int isLogin;

    private String id;

    // 0是企业 1是个人
    private Integer type;

    // 注册手机
    private String phone;

    // 注册密码
    private String password;

    private String companyName;

    private String province;

    private String city;

    private String district;

    private String companyAddress;

    private String name;

    private String mobile;

    private String shopLogo;

    private String shopPhoto;

    private String shopDetail;

    private String companyCode;

    private String licensePhoto;

    private String idNumber;

    private String idPhoto;

    private Integer applyStatus;

    private String remark;

    private String userId;

    private String createdBy;

    private String createdDeptId;

    private String updatedBy;

    private String created;

    private String updatedDeptId;

    private String updated;

    private Integer isValid;

    // 设备token
    private String deviceToken;

    // 是否接受推送消息
    private Integer messageFlag;

    private Integer sex;

    private String email;

    private String qq;

    private String sign;

    private String photo;

    private String backgroundPicture;

    private String auditRemark;

    // token标识，每次重新登录都会重新赋值
    private String singleToken;

    public String getSingleToken() {
        return singleToken;
    }

    public void setSingleToken(String singleToken) {
        this.singleToken = singleToken;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public Long getLid() {
        return this.lid;
    }

    public void setLid(Long lid) {
        this.lid = lid;
    }

    public int getIsLogin() {
        return this.isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCompanyAddress() {
        return this.companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getShopLogo() {
        return this.shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getShopPhoto() {
        return this.shopPhoto;
    }

    public void setShopPhoto(String shopPhoto) {
        this.shopPhoto = shopPhoto;
    }

    public String getShopDetail() {
        return this.shopDetail;
    }

    public void setShopDetail(String shopDetail) {
        this.shopDetail = shopDetail;
    }

    public String getCompanyCode() {
        return this.companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getLicensePhoto() {
        return this.licensePhoto;
    }

    public void setLicensePhoto(String licensePhoto) {
        this.licensePhoto = licensePhoto;
    }

    public String getIdNumber() {
        return this.idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdPhoto() {
        return this.idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public Integer getApplyStatus() {
        return this.applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDeptId() {
        return this.createdDeptId;
    }

    public void setCreatedDeptId(String createdDeptId) {
        this.createdDeptId = createdDeptId;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdatedDeptId() {
        return this.updatedDeptId;
    }

    public void setUpdatedDeptId(String updatedDeptId) {
        this.updatedDeptId = updatedDeptId;
    }

    public String getUpdated() {
        return this.updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Integer getIsValid() {
        return this.isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getDeviceToken() {
        return this.deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Integer getMessageFlag() {
        return this.messageFlag;
    }

    public void setMessageFlag(Integer messageFlag) {
        this.messageFlag = messageFlag;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return this.qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBackgroundPicture() {
        return this.backgroundPicture;
    }

    public void setBackgroundPicture(String backgroundPicture) {
        this.backgroundPicture = backgroundPicture;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    private String headPortrait;

    private String tel;

    @Generated(hash = 700846932)
    public UserOwner(Long lid, int isLogin, String id, Integer type, String phone,
            String password, String companyName, String province, String city,
            String district, String companyAddress, String name, String mobile,
            String shopLogo, String shopPhoto, String shopDetail,
            String companyCode, String licensePhoto, String idNumber,
            String idPhoto, Integer applyStatus, String remark, String userId,
            String createdBy, String createdDeptId, String updatedBy,
            String created, String updatedDeptId, String updated, Integer isValid,
            String deviceToken, Integer messageFlag, Integer sex, String email,
            String qq, String sign, String photo, String backgroundPicture,
            String auditRemark, String singleToken, String headPortrait,
            String tel) {
        this.lid = lid;
        this.isLogin = isLogin;
        this.id = id;
        this.type = type;
        this.phone = phone;
        this.password = password;
        this.companyName = companyName;
        this.province = province;
        this.city = city;
        this.district = district;
        this.companyAddress = companyAddress;
        this.name = name;
        this.mobile = mobile;
        this.shopLogo = shopLogo;
        this.shopPhoto = shopPhoto;
        this.shopDetail = shopDetail;
        this.companyCode = companyCode;
        this.licensePhoto = licensePhoto;
        this.idNumber = idNumber;
        this.idPhoto = idPhoto;
        this.applyStatus = applyStatus;
        this.remark = remark;
        this.userId = userId;
        this.createdBy = createdBy;
        this.createdDeptId = createdDeptId;
        this.updatedBy = updatedBy;
        this.created = created;
        this.updatedDeptId = updatedDeptId;
        this.updated = updated;
        this.isValid = isValid;
        this.deviceToken = deviceToken;
        this.messageFlag = messageFlag;
        this.sex = sex;
        this.email = email;
        this.qq = qq;
        this.sign = sign;
        this.photo = photo;
        this.backgroundPicture = backgroundPicture;
        this.auditRemark = auditRemark;
        this.singleToken = singleToken;
        this.headPortrait = headPortrait;
        this.tel = tel;
    }

    @Generated(hash = 1291740172)
    public UserOwner() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.lid);
        dest.writeInt(this.isLogin);
        dest.writeString(this.id);
        dest.writeValue(this.type);
        dest.writeString(this.phone);
        dest.writeString(this.password);
        dest.writeString(this.companyName);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.district);
        dest.writeString(this.companyAddress);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.shopLogo);
        dest.writeString(this.shopPhoto);
        dest.writeString(this.shopDetail);
        dest.writeString(this.companyCode);
        dest.writeString(this.licensePhoto);
        dest.writeString(this.idNumber);
        dest.writeString(this.idPhoto);
        dest.writeValue(this.applyStatus);
        dest.writeString(this.remark);
        dest.writeString(this.userId);
        dest.writeString(this.createdBy);
        dest.writeString(this.createdDeptId);
        dest.writeString(this.updatedBy);
        dest.writeString(this.created);
        dest.writeString(this.updatedDeptId);
        dest.writeString(this.updated);
        dest.writeValue(this.isValid);
        dest.writeString(this.deviceToken);
        dest.writeValue(this.messageFlag);
        dest.writeValue(this.sex);
        dest.writeString(this.email);
        dest.writeString(this.qq);
        dest.writeString(this.sign);
        dest.writeString(this.photo);
        dest.writeString(this.backgroundPicture);
        dest.writeString(this.auditRemark);
        dest.writeString(this.headPortrait);
        dest.writeString(this.tel);
    }

    protected UserOwner(Parcel in) {
        this.lid = (Long) in.readValue(Long.class.getClassLoader());
        this.isLogin = in.readInt();
        this.id = in.readString();
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.phone = in.readString();
        this.password = in.readString();
        this.companyName = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.district = in.readString();
        this.companyAddress = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
        this.shopLogo = in.readString();
        this.shopPhoto = in.readString();
        this.shopDetail = in.readString();
        this.companyCode = in.readString();
        this.licensePhoto = in.readString();
        this.idNumber = in.readString();
        this.idPhoto = in.readString();
        this.applyStatus = (Integer) in.readValue(Integer.class.getClassLoader());
        this.remark = in.readString();
        this.userId = in.readString();
        this.createdBy = in.readString();
        this.createdDeptId = in.readString();
        this.updatedBy = in.readString();
        this.created = in.readString();
        this.updatedDeptId = in.readString();
        this.updated = in.readString();
        this.isValid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.deviceToken = in.readString();
        this.messageFlag = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sex = (Integer) in.readValue(Integer.class.getClassLoader());
        this.email = in.readString();
        this.qq = in.readString();
        this.sign = in.readString();
        this.photo = in.readString();
        this.backgroundPicture = in.readString();
        this.auditRemark = in.readString();
        this.headPortrait = in.readString();
        this.tel = in.readString();
    }

    public static final Creator<UserOwner> CREATOR = new Creator<UserOwner>() {
        @Override
        public UserOwner createFromParcel(Parcel source) {
            return new UserOwner(source);
        }

        @Override
        public UserOwner[] newArray(int size) {
            return new UserOwner[size];
        }
    };
}