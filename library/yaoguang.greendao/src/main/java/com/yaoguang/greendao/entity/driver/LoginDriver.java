package com.yaoguang.greendao.entity.driver;

import android.os.Parcel;
import android.os.Parcelable;

import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.UserDriverCar;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录或者获取个人信息返回的数据
 */
public class LoginDriver implements Parcelable {

    private UserDriverLicence licenceInfo;
    private Driver userInfo;
    // 第一个是牵引车，第二个是半挂车
    private List<UserDriverCar> carInfo;


    public UserDriverLicence getLicenceInfo() {
        return licenceInfo;
    }

    public void setLicenceInfo(UserDriverLicence licenceInfo) {
        this.licenceInfo = licenceInfo;
    }

    public Driver getUserInfo() {
        if (userInfo == null) {
            userInfo = new Driver();
        }

        return userInfo;
    }

    public void setUserInfo(Driver userInfo) {
        this.userInfo = userInfo;
    }

    public List<UserDriverCar> getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(List<UserDriverCar> carInfo) {
        this.carInfo = carInfo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.licenceInfo);
        dest.writeSerializable(this.userInfo);
        dest.writeList(this.carInfo);
    }

    public LoginDriver() {
    }

    protected LoginDriver(Parcel in) {
        this.licenceInfo = (UserDriverLicence) in.readSerializable();
        this.userInfo = (Driver) in.readSerializable();
        this.carInfo = new ArrayList<UserDriverCar>();
        in.readList(this.carInfo, UserDriverCar.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoginDriver> CREATOR = new Parcelable.Creator<LoginDriver>() {
        @Override
        public LoginDriver createFromParcel(Parcel source) {
            return new LoginDriver(source);
        }

        @Override
        public LoginDriver[] newArray(int size) {
            return new LoginDriver[size];
        }
    };
}
