package com.yaoguang.greendao.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 搜索框每次返回的类
 */
public class AppPublicInfoWrapper implements Parcelable {

    private String id;

    //如果是地址，就是省市区
    private String fullName;

    //如果是地址，就是详细地址
    private String shortName;

    //如果是地址，就是联系人
    private String remark1;
    //如果是地址，就是手机号
    private String remark2;
    //如果是地址，就是电话
    private String remark3;
    // 备用字段4
    private String remark4;

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    private boolean isCheck;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = remark4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.fullName);
        dest.writeString(this.shortName);
        dest.writeString(this.remark1);
        dest.writeString(this.remark2);
        dest.writeString(this.remark3);
        dest.writeString(this.remark4);
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
    }

    public AppPublicInfoWrapper() {
    }

    protected AppPublicInfoWrapper(Parcel in) {
        this.id = in.readString();
        this.fullName = in.readString();
        this.shortName = in.readString();
        this.remark1 = in.readString();
        this.remark2 = in.readString();
        this.remark3 = in.readString();
        this.remark4 = in.readString();
        this.isCheck = in.readByte() != 0;
    }

    public static final Creator<AppPublicInfoWrapper> CREATOR = new Creator<AppPublicInfoWrapper>() {
        @Override
        public AppPublicInfoWrapper createFromParcel(Parcel source) {
            return new AppPublicInfoWrapper(source);
        }

        @Override
        public AppPublicInfoWrapper[] newArray(int size) {
            return new AppPublicInfoWrapper[size];
        }
    };
}