package com.yaoguang.appcommon.phone.contact2.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * dri
 * Created by zhongjh on 2018/4/24.
 */
public class Extra implements Parcelable {

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    private String driverID;
    private String txt;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.driverID);
        dest.writeString(this.txt);
    }

    public Extra() {
    }

    protected Extra(Parcel in) {
        this.driverID = in.readString();
        this.txt = in.readString();
    }

    public static final Parcelable.Creator<Extra> CREATOR = new Parcelable.Creator<Extra>() {
        @Override
        public Extra createFromParcel(Parcel source) {
            return new Extra(source);
        }

        @Override
        public Extra[] newArray(int size) {
            return new Extra[size];
        }
    };
}
