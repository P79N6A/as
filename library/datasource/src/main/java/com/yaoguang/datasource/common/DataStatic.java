package com.yaoguang.datasource.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.greendao.entity.driver.UserDriverCar;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.greendao.entity.AppUserWrapper;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.constants.Constants;

import java.util.List;

/**
 * 数据的全局变量
 * Created by zhongjh on 2017/12/4.
 */
public class DataStatic {

    private String id;// 动态根据不同app获取不同的id

    private static DataStatic instance;
    private AppUserWrapper appUserWrapper; // 物流的登录数据
    private UserOwner userOwner; // 货主的登录数据

    private LoginDriver loginDriver;// 这个类同时获取了下面下个
    private Driver driver;// 司机的登录数据
    private List<UserDriverCar> userDriverCars; // 司机的车牌列表
    private UserDriverLicence userDriverLicence; // 驾驶证

    private String rongCloudToken;// 融云token


    public static DataStatic getInstance() {
        if (instance == null) {
            synchronized (DataStatic.class) {
                if (instance == null) {
                    instance = new DataStatic();
                }
            }
        }
        return instance;
    }

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    /**
     * SPUtils构造函数
     * <p>在Application中初始化</p>
     *
     * @param spName spName
     */
    public void init(String spName) {
        sp = BaseApplication.getInstance().getBaseContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.apply();
    }

    public String getId() {
        if (id == null)
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                id = DataStatic.getInstance().getAppUserWrapper().getId();
            } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                id = DataStatic.getInstance().getUserOwner().getId();
            } else if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
                id = DataStatic.getInstance().getDriver().getId();
            }
        return id;
    }

    public AppUserWrapper getAppUserWrapper() {
        if (appUserWrapper == null) {
            SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("AppUserWrapper", Context.MODE_PRIVATE);
            String json = sp.getString("AppUserWrapper", null);
            if (!TextUtils.isEmpty(json)) {
                AppUserWrapper appUserWrapper = new Gson().fromJson(json, AppUserWrapper.class);
                this.appUserWrapper = appUserWrapper;
                return appUserWrapper;
            }
        }
        return appUserWrapper;
    }

    public void setAppUserWrapper(AppUserWrapper appUserWrapper) {
        if (appUserWrapper != null) {
            SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("AppUserWrapper", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            String json = new Gson().toJson(appUserWrapper);
            edit.putString("AppUserWrapper", json);
            edit.apply();
        }
        this.appUserWrapper = appUserWrapper;
    }

    public UserOwner getUserOwner() {
        if (userOwner == null) {
            SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("UserOwner", Context.MODE_PRIVATE);
            String json = sp.getString("UserOwner", null);
            if (!TextUtils.isEmpty(json)) {
                UserOwner userOwner = new Gson().fromJson(json, UserOwner.class);
                this.userOwner = userOwner;
                return userOwner;
            }
        }
        return userOwner;
    }

    public void setUserOwner(UserOwner userOwner) {
        if (userOwner != null) {
            SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("UserOwner", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            String json = new Gson().toJson(userOwner);
            edit.putString("UserOwner", json);
            edit.apply();
        }
        this.userOwner = userOwner;
    }

    public Driver getDriver() {
        if (driver == null) {
            SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("Driver", Context.MODE_PRIVATE);
            String json = sp.getString("Driver", null);
            if (!TextUtils.isEmpty(json)) {
                Driver driver = new Gson().fromJson(json, Driver.class);
                this.driver = driver;
                return driver;
            }
        }
        return driver;
    }

    public void setDriver(Driver driver) {
        if (driver != null) {
            SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("Driver", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            String json = new Gson().toJson(driver);
            edit.putString("Driver", json);
            edit.apply();
        }
        this.driver = driver;
    }

    public List<UserDriverCar> getUserDriverCars() {
        if (this.userDriverCars == null) {
            SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("Driver", Context.MODE_PRIVATE);
            String json = sp.getString("UserDriverCars", null);
            if (!TextUtils.isEmpty(json)) {
                List<UserDriverCar> userDriverCars = new Gson().fromJson(json, new TypeToken<List<UserDriverCar>>() {
                }.getType());
                this.userDriverCars = userDriverCars;
                return userDriverCars;
            }
        }
        return userDriverCars;
    }

    public void setUserDriverCars(List<UserDriverCar> userDriverCars) {
        if (userDriverCars != null) {
            SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("Driver", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            String json = new Gson().toJson(userDriverCars);
            edit.putString("UserDriverCars", json);
            edit.apply();
        }
        this.userDriverCars = userDriverCars;
    }

    public UserDriverLicence getUserDriverLicence() {
        if (this.userDriverLicence == null) {
            SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("Driver", Context.MODE_PRIVATE);
            String json = sp.getString("userDriverLicence", null);
            if (!TextUtils.isEmpty(json)) {
                UserDriverLicence userDriverLicence = new Gson().fromJson(json, UserDriverLicence.class);
                this.userDriverLicence = userDriverLicence;
                return userDriverLicence;
            }
        }
        return userDriverLicence;
    }

    public void setUserDriverLicence(UserDriverLicence userDriverLicence) {
        if (userDriverLicence != null) {
            SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("Driver", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            String json = new Gson().toJson(userDriverLicence);
            edit.putString("userDriverLicence", json);
            edit.apply();
        }
        this.userDriverLicence = userDriverLicence;
    }

    public LoginDriver getLoginDriver() {
        if (this.loginDriver == null) {
            SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("Driver", Context.MODE_PRIVATE);

            String jsonUserDriverLicence = sp.getString("userDriverLicence", null);
            if (!TextUtils.isEmpty(jsonUserDriverLicence)) {
                this.userDriverLicence = new Gson().fromJson(jsonUserDriverLicence, UserDriverLicence.class);
            }

            String jsonDriver = sp.getString("Driver", null);
            if (!TextUtils.isEmpty(jsonDriver)) {
                this.driver = new Gson().fromJson(jsonDriver, Driver.class);
            }

            String jsonUserDriverCars = sp.getString("UserDriverCars", null);
            if (!TextUtils.isEmpty(jsonUserDriverCars)) {
                this.userDriverCars = new Gson().fromJson(jsonUserDriverCars, new TypeToken<List<UserDriverCar>>() {
                }.getType());
            }

            LoginDriver loginDriver = new LoginDriver();
            loginDriver.setUserInfo(driver);
            loginDriver.setCarInfo(userDriverCars);
            loginDriver.setLicenceInfo(userDriverLicence);
            return loginDriver;
        }
        return loginDriver;
    }

    public String getRongCloudToken() {
        if (this.rongCloudToken == null) {
            SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("rongCloudToken", Context.MODE_PRIVATE);
            String json = sp.getString("rongCloudToken", null);
            if (!TextUtils.isEmpty(json)) {
                this.rongCloudToken = json;
                return json;
            }
        }
        return rongCloudToken;
    }

    public void setRongCloudToken(String rongCloudToken) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("rongCloudToken", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("rongCloudToken", rongCloudToken);
        edit.apply();
        this.rongCloudToken = rongCloudToken;
    }


}
