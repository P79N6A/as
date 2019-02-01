package com.yaoguang.greendao.entity.driver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 我的 - 我的订单条件
 * Created by zhongjh on 2018/4/4.
 */
public class DriverOrderCondition implements Parcelable {

    // 司机id
    private String driverId;

    // 公司ids
    private String companyIds;

    private String startDate;

    // 结束日期
    private String endDate;

    // 时间范围(0、自定义，1、近1周，2、近1月，3、近3月，4、近6月 5、近1年)
    private String dateScopeType;


    // 起始日期
    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(String companyIds) {
        this.companyIds = companyIds;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDateScopeType() {
        return dateScopeType;
    }

    public void setDateScopeType(String dateScopeType) {
        this.dateScopeType = dateScopeType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.driverId);
        dest.writeString(this.companyIds);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeString(this.dateScopeType);
    }

    public DriverOrderCondition() {
    }

    protected DriverOrderCondition(Parcel in) {
        this.driverId = in.readString();
        this.companyIds = in.readString();
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.dateScopeType = in.readString();
    }

    public static final Parcelable.Creator<DriverOrderCondition> CREATOR = new Parcelable.Creator<DriverOrderCondition>() {
        @Override
        public DriverOrderCondition createFromParcel(Parcel source) {
            return new DriverOrderCondition(source);
        }

        @Override
        public DriverOrderCondition[] newArray(int size) {
            return new DriverOrderCondition[size];
        }
    };
}
