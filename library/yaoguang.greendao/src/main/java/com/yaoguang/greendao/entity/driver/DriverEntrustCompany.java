package com.yaoguang.greendao.entity.driver;


import android.os.Parcel;
import android.os.Parcelable;

public class DriverEntrustCompany implements Parcelable {

    private String id;

    private String driverId;

    private String entrustCompanyId;

    private String entrustCompanyName;

    private String entrustCompanyPhoto;

    private String updated;

    private String created;

    private String isValid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getEntrustCompanyId() {
        return entrustCompanyId;
    }

    public void setEntrustCompanyId(String entrustCompanyId) {
        this.entrustCompanyId = entrustCompanyId;
    }

    public String getEntrustCompanyName() {
        return entrustCompanyName;
    }

    public void setEntrustCompanyName(String entrustCompanyName) {
        this.entrustCompanyName = entrustCompanyName;
    }

    public String getEntrustCompanyPhoto() {
        return entrustCompanyPhoto;
    }

    public void setEntrustCompanyPhoto(String entrustCompanyPhoto) {
        this.entrustCompanyPhoto = entrustCompanyPhoto;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.driverId);
        dest.writeString(this.entrustCompanyId);
        dest.writeString(this.entrustCompanyName);
        dest.writeString(this.entrustCompanyPhoto);
        dest.writeString(this.updated);
        dest.writeString(this.created);
        dest.writeValue(this.isValid);
    }

    public DriverEntrustCompany() {
    }

    protected DriverEntrustCompany(Parcel in) {
        this.id = in.readString();
        this.driverId = in.readString();
        this.entrustCompanyId = in.readString();
        this.entrustCompanyName = in.readString();
        this.entrustCompanyPhoto = in.readString();
        this.updated = in.readString();
        this.created = in.readString();
        this.isValid = in.readString();
    }

    public static final Parcelable.Creator<DriverEntrustCompany> CREATOR = new Parcelable.Creator<DriverEntrustCompany>() {
        @Override
        public DriverEntrustCompany createFromParcel(Parcel source) {
            return new DriverEntrustCompany(source);
        }

        @Override
        public DriverEntrustCompany[] newArray(int size) {
            return new DriverEntrustCompany[size];
        }
    };
}