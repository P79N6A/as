package com.yaoguang.greendao.entity.company.userLoginTimeWrapper;


import android.os.Parcel;
import android.os.Parcelable;

public class UserRecentlyUsedWlanLog implements Parcelable {


    private String id; // 主键

    private String wlanId; // 连接的WiFi的ID

    private String userId; // 用户ID

    private String username; // 用户名称

    private String created; // 首次连接时间

    private String lastUsedTime; // 最后一次连接时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWlanId() {
        return wlanId;
    }

    public void setWlanId(String wlanId) {
        this.wlanId = wlanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.wlanId);
        dest.writeString(this.userId);
        dest.writeString(this.username);
        dest.writeString(this.created);
        dest.writeString(this.lastUsedTime);
    }

    public UserRecentlyUsedWlanLog() {
    }

    protected UserRecentlyUsedWlanLog(Parcel in) {
        this.id = in.readString();
        this.wlanId = in.readString();
        this.userId = in.readString();
        this.username = in.readString();
        this.created = in.readString();
        this.lastUsedTime = in.readString();
    }

    public static final Parcelable.Creator<UserRecentlyUsedWlanLog> CREATOR = new Parcelable.Creator<UserRecentlyUsedWlanLog>() {
        @Override
        public UserRecentlyUsedWlanLog createFromParcel(Parcel source) {
            return new UserRecentlyUsedWlanLog(source);
        }

        @Override
        public UserRecentlyUsedWlanLog[] newArray(int size) {
            return new UserRecentlyUsedWlanLog[size];
        }
    };
}