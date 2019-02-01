package com.yaoguang.driver.net.factory;

import com.yaoguang.driver.home.HomePresenter;
import com.yaoguang.driver.home.driverstatus.StatusSwitchPresenter;
import com.yaoguang.driver.main.MainPresenter;
import com.yaoguang.driver.my.authentication.drivinglicense.DrivingLicenseAuthenticationPresenter;
import com.yaoguang.driver.my.authentication.heavyvehicle.HeavyVehicleAuthenticationPresenter;
import com.yaoguang.driver.my.authentication.personal.RealNameAuthenticationPresenter;
import com.yaoguang.driver.my.message.PlatformMessagePresenter;
import com.yaoguang.driver.my.my.MyFragmentPresenter;
import com.yaoguang.driver.my.myorder.MyOrderPresenter;
import com.yaoguang.driver.my.personal.PersonalInformationPresenter;
import com.yaoguang.driver.order.detail.OrderDetailPresenter;
import java.lang.Class;
import java.lang.IllegalAccessException;
import java.lang.InstantiationException;
import java.lang.Object;

/**
 * @ 实例化工厂 此类由apt自动生成 */
public final class InstanceFactory {
  /**
   * @此方法由apt自动生成 */
  public static Object create(Class mClass) throws IllegalAccessException, InstantiationException {
     switch (mClass.getSimpleName()) {
      case "StatusSwitchPresenter": return  new StatusSwitchPresenter();
      case "HomePresenter": return  new HomePresenter();
      case "MainPresenter": return  new MainPresenter();
      case "DrivingLicenseAuthenticationPresenter": return  new DrivingLicenseAuthenticationPresenter();
      case "HeavyVehicleAuthenticationPresenter": return  new HeavyVehicleAuthenticationPresenter();
      case "RealNameAuthenticationPresenter": return  new RealNameAuthenticationPresenter();
      case "PlatformMessagePresenter": return  new PlatformMessagePresenter();
      case "MyFragmentPresenter": return  new MyFragmentPresenter();
      case "MyOrderPresenter": return  new MyOrderPresenter();
      case "PersonalInformationPresenter": return  new PersonalInformationPresenter();
      case "OrderDetailPresenter": return  new OrderDetailPresenter();
      default: return mClass.newInstance();
    }
  }
}
