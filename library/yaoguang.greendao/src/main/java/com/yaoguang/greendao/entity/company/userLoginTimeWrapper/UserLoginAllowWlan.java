package com.yaoguang.greendao.entity.company.userLoginTimeWrapper;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class UserLoginAllowWlan implements Parcelable {

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    private boolean isCheck;

    private String id;

    private String groupName; // 群组名称

    private List<UserLoginWlan> wlanList; // MAC地址

    // 应用类型(1:物流 2:BOSS)
    private Integer appType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<UserLoginWlan> getWlanList() {
        return wlanList;
    }

    public void setWlanList(List<UserLoginWlan> wlanList) {
        this.wlanList = wlanList;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.groupName);
        dest.writeList(this.wlanList);
        dest.writeValue(this.appType);
    }

    public UserLoginAllowWlan() {
    }

    protected UserLoginAllowWlan(Parcel in) {
        this.id = in.readString();
        this.groupName = in.readString();
        this.wlanList = new ArrayList<UserLoginWlan>();
        in.readList(this.wlanList, UserLoginWlan.class.getClassLoader());
        this.appType = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserLoginAllowWlan> CREATOR = new Parcelable.Creator<UserLoginAllowWlan>() {
        @Override
        public UserLoginAllowWlan createFromParcel(Parcel source) {
            return new UserLoginAllowWlan(source);
        }

        @Override
        public UserLoginAllowWlan[] newArray(int size) {
            return new UserLoginAllowWlan[size];
        }
    };
}