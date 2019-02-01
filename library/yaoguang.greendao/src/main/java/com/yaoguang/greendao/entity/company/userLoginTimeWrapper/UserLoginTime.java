package com.yaoguang.greendao.entity.company.userLoginTimeWrapper;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户登录时间限制
 */
public class UserLoginTime implements Parcelable {

    private boolean isCheck;// 是否选择，app自己这边自己制作的一个字段

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isCheck() {
        return isCheck;
    }

    private String id;

    private String name;

    private Integer isMon;

    private String monTime;

    private Integer isTue;

    private String tueTime;

    private Integer isWes;

    private String wesTime;

    private Integer isThi;

    private String thiTime;

    private Integer isFri;

    private String friTime;

    private Integer isSat;

    private String satTime;

    private Integer isSun;

    private String sunTime;

    private String remarks;

    private String createdBy;

    private String clientId;

    private Integer delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getIsMon() {
        return isMon;
    }

    public void setIsMon(Integer isMon) {
        this.isMon = isMon;
    }

    public String getMonTime() {
        return monTime;
    }

    public void setMonTime(String monTime) {
        this.monTime = monTime == null ? null : monTime.trim();
    }

    public Integer getIsTue() {
        return isTue;
    }

    public void setIsTue(Integer isTue) {
        this.isTue = isTue;
    }

    public String getTueTime() {
        return tueTime;
    }

    public void setTueTime(String tueTime) {
        this.tueTime = tueTime == null ? null : tueTime.trim();
    }

    public Integer getIsWes() {
        return isWes;
    }

    public void setIsWes(Integer isWes) {
        this.isWes = isWes;
    }

    public String getWesTime() {
        return wesTime;
    }

    public void setWesTime(String wesTime) {
        this.wesTime = wesTime == null ? null : wesTime.trim();
    }

    public Integer getIsThi() {
        return isThi;
    }

    public void setIsThi(Integer isThi) {
        this.isThi = isThi;
    }

    public String getThiTime() {
        return thiTime;
    }

    public void setThiTime(String thiTime) {
        this.thiTime = thiTime == null ? null : thiTime.trim();
    }

    public Integer getIsFri() {
        return isFri;
    }

    public void setIsFri(Integer isFri) {
        this.isFri = isFri;
    }

    public String getFriTime() {
        return friTime;
    }

    public void setFriTime(String friTime) {
        this.friTime = friTime == null ? null : friTime.trim();
    }

    public Integer getIsSat() {
        return isSat;
    }

    public void setIsSat(Integer isSat) {
        this.isSat = isSat;
    }

    public String getSatTime() {
        return satTime;
    }

    public void setSatTime(String satTime) {
        this.satTime = satTime == null ? null : satTime.trim();
    }

    public Integer getIsSun() {
        return isSun;
    }

    public void setIsSun(Integer isSun) {
        this.isSun = isSun;
    }

    public String getSunTime() {
        return sunTime;
    }

    public void setSunTime(String sunTime) {
        this.sunTime = sunTime == null ? null : sunTime.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }


    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeValue(this.isMon);
        dest.writeString(this.monTime);
        dest.writeValue(this.isTue);
        dest.writeString(this.tueTime);
        dest.writeValue(this.isWes);
        dest.writeString(this.wesTime);
        dest.writeValue(this.isThi);
        dest.writeString(this.thiTime);
        dest.writeValue(this.isFri);
        dest.writeString(this.friTime);
        dest.writeValue(this.isSat);
        dest.writeString(this.satTime);
        dest.writeValue(this.isSun);
        dest.writeString(this.sunTime);
        dest.writeString(this.remarks);
        dest.writeString(this.createdBy);
        dest.writeString(this.clientId);
        dest.writeValue(this.delFlag);
    }

    public UserLoginTime() {
    }

    protected UserLoginTime(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.isMon = (Integer) in.readValue(Integer.class.getClassLoader());
        this.monTime = in.readString();
        this.isTue = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tueTime = in.readString();
        this.isWes = (Integer) in.readValue(Integer.class.getClassLoader());
        this.wesTime = in.readString();
        this.isThi = (Integer) in.readValue(Integer.class.getClassLoader());
        this.thiTime = in.readString();
        this.isFri = (Integer) in.readValue(Integer.class.getClassLoader());
        this.friTime = in.readString();
        this.isSat = (Integer) in.readValue(Integer.class.getClassLoader());
        this.satTime = in.readString();
        this.isSun = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sunTime = in.readString();
        this.remarks = in.readString();
        this.createdBy = in.readString();
        this.clientId = in.readString();
        this.delFlag = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserLoginTime> CREATOR = new Parcelable.Creator<UserLoginTime>() {
        @Override
        public UserLoginTime createFromParcel(Parcel source) {
            return new UserLoginTime(source);
        }

        @Override
        public UserLoginTime[] newArray(int size) {
            return new UserLoginTime[size];
        }
    };
}