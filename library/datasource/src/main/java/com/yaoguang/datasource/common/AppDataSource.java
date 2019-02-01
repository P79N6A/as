package com.yaoguang.datasource.common;

import com.yaoguang.greendao.entity.common.LoginPic;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.LoginRestrict;
import com.yaoguang.greendao.entity.company.QRCode;
import com.yaoguang.greendao.entity.company.ScanCodeResult;
import com.yaoguang.lib.BuildConfig;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.BannerPic;
import com.yaoguang.greendao.entity.SpecialMsg;
import com.yaoguang.datasource.api.common.AppApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * App有关接口
 * Created by zhongjh on 2017/12/26.
 */
public class AppDataSource {

    /**
     * 获取服务器公钥
     */
    public Observable<BaseResponse<String>> getCodeKey() {
        AppApi appApi = Api.getInstance().retrofit.create(AppApi.class);
        return appApi.getCodeKey();
    }

    /**
     * 获取服务器时间
     */
    public Observable<BaseResponse<String>> getServerTime() {
        AppApi appApi = Api.getInstance().retrofit.create(AppApi.class);
        return appApi.getServerTime();
    }

    /**
     * 获取节日的图片，登录界面之前显示
     * @param type     类型（1：司机端 2：物流端 3：货主）
     * @return 返回数据源
     */
    public Observable<BaseResponse<LoginPic>> getPic(String type) {
        AppApi appApi = Api.getInstance().retrofit.create(AppApi.class);
        return appApi.getPic(type);
    }

    /**
     * 获取banner的横幅广告图片
     *
     * @return 返回数据源
     */
    public Observable<BaseResponse<PageList<BannerPic>>> getBannerPic() {
        // platformType 0pc 1司机 2供应链 3货主
        String platformType = "";
        if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            platformType = "2";
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            platformType = "3";
        } else if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            platformType = "1";
        }

        AppApi appApi = Api.getInstance().retrofit.create(AppApi.class);
        return appApi.getBannerPic(platformType, 1, 4);
    }

    /**
     * 特殊公告
     *
     * @return 返回数据源
     */
    public Observable<BaseResponse<List<SpecialMsg>>> specialMsgList() {
        AppApi appApi = Api.getInstance().retrofit.create(AppApi.class);
        // platformType 0pc 1司机 2供应链 3货主
        String platformType = "";
        if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            platformType = "2";
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
            platformType = "3";
        } else if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
            platformType = "1";
        }
        return appApi.specialMsgList(platformType, 1, 3);
    }

    /**
     * 扫描后获取到的动态地址
     *
     * @param url 动态地址
     */
    public Observable<BaseResponse<String>> scanCodeLogin(String url) {
        AppApi appApi = Api.getInstance().retrofit.create(AppApi.class);
        String password = "";
        String username = "";
        if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            password = DataStatic.getInstance().getAppUserWrapper().getPassword();
            username = DataStatic.getInstance().getAppUserWrapper().getLoginName();
        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
//            password = DataStatic.getInstance().getUserOwner().getPassword();
//            username = DataStatic.getInstance().getUserOwner().getName();
        }
        return appApi.scanCodeLogin(BuildConfig.ENDPOINT + url + "&userName=" + username + "&password=" + password);
    }

    /**
     * 获取二维码的base64
     */
    public Observable<BaseResponse<QRCode>> getQrCode(String appVersionName,String userId) {
        AppApi appApi = Api.getInstance().retrofit.create(AppApi.class);
        return appApi.getQrCode("1",userId,"1",android.os.Build.MANUFACTURER + " SDKINT：" + android.os.Build.VERSION.SDK_INT + " RELEASE：" + android.os.Build.VERSION.RELEASE + " appVersionName：" + appVersionName);
    }

    /**
     * 扫描二维码后，手机授权登录
     * @param url 动态地址
     */
    public Observable<BaseResponse<ScanCodeResult>> scanCodeAuth(String url){
        AppApi appApi = Api.getInstance().retrofit.create(AppApi.class);
        return appApi.scanCodeAuth(BuildConfig.ENDPOINT + url  );
    }

    /**
     * 循环检查扫码结果
     */
    public Observable<BaseResponse<ScanCodeResult>> checkScanCode(String key){
        AppApi appApi = Api.getInstance().retrofit.create(AppApi.class);
        return appApi.checkScanCode(key);
    }

    public Observable<BaseResponse<LoginRestrict>> loginRestrict(){
        AppApi appApi = Api.getInstance().retrofit.create(AppApi.class);
        return appApi.loginRestrict();
    }

}
