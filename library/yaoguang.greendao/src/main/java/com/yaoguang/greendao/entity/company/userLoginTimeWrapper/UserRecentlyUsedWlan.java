package com.yaoguang.greendao.entity.company.userLoginTimeWrapper;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UserRecentlyUsedWlan implements Parcelable {

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    private boolean isCheck;

    private String id; // 主键

    private String name; // WiFi名称

    private String address; // 地址

    private String clientId; //  总公司id

    private String created; // 创建时间

    private String lastUsedTime; // 最后一次使用时间

    private List<UserRecentlyUsedWlanLog> usedLogs; // 用户连接记录

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(String lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public List<UserRecentlyUsedWlanLog> getUsedLogs() {
        return usedLogs;
    }

    public void setUsedLogs(List<UserRecentlyUsedWlanLog> usedLogs) {
        this.usedLogs = usedLogs;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.clientId);
        dest.writeString(this.created);
        dest.writeString(this.lastUsedTime);
        dest.writeTypedList(this.usedLogs);
    }

    public UserRecentlyUsedWlan() {
    }

    protected UserRecentlyUsedWlan(Parcel in) {
        this.isCheck = in.readByte() != 0;
        this.id = in.readString();
        this.name = in.readString();
        this.address = in.readString();
        this.clientId = in.readString();
        this.created = in.readString();
        this.lastUsedTime = in.readString();
        this.usedLogs = in.createTypedArrayList(UserRecentlyUsedWlanLog.CREATOR);
    }

    public static final Parcelable.Creator<UserRecentlyUsedWlan> CREATOR = new Parcelable.Creator<UserRecentlyUsedWlan>() {
        @Override
        public UserRecentlyUsedWlan createFromParcel(Parcel source) {
            return new UserRecentlyUsedWlan(source);
        }

        @Override
        public UserRecentlyUsedWlan[] newArray(int size) {
            return new UserRecentlyUsedWlan[size];
        }
    };
}