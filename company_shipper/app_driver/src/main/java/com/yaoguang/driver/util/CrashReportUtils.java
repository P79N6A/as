package com.yaoguang.driver.util;

import com.tencent.bugly.crashreport.CrashReport;
import com.yaoguang.appcommon.push.BasePushApplication;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.greendao.entity.Driver;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class CrashReportUtils {
    public static void reportDriverAccount() {
        Driver driver = DataStatic.getInstance().getDriver();
        if (driver == null) {
            ULog.e("上报司机帐号失败！");
            return;
        }
        CrashReport.setUserId(driver.getId());
        CrashReport.putUserData(BasePushApplication.getInstance(), "Id", driver.getId());
        CrashReport.putUserData(BasePushApplication.getInstance(), "Mobile", driver.getMobile());
        CrashReport.putUserData(BasePushApplication.getInstance(), "DriverName", driver.getDriverName());
        CrashReport.putUserData(BasePushApplication.getInstance(), "PlaceCity", driver.getPlaceCity());
        CrashReport.putUserData(BasePushApplication.getInstance(), "CarNumber", driver.getCarNumber());
//        CrashReport.putUserData(BasePushApplication.getInstance(), "CarRegisterDate", driver.getCarRegisterDate());

        ULog.i("上报司机帐号成功！");
    }
}
