package com.yaoguang.datasource.api.common;

import com.yaoguang.greendao.entity.common.LoginPic;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.LoginRestrict;
import com.yaoguang.greendao.entity.company.QRCode;
import com.yaoguang.greendao.entity.company.ScanCodeResult;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.BannerPic;
import com.yaoguang.greendao.entity.SpecialMsg;
import com.yaoguang.greendao.entity.Update;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 一些杂项的api
 * Created by zhongjh on 2017/7/11.
 */
public interface AppApi {

    /**
     * 获取服务器公钥
     *
     * @return 公钥
     */
    @GET("rsa/getCodeKey.do?")
    Observable<BaseResponse<String>> getCodeKey();

    /**
     * 首页的Banner获取图片
     *
     * @param platformType 0pc 1司机 2供应链 3货主
     * @return
     */
    @GET("app/bannerPic/list.do?")
    Observable<BaseResponse<PageList<BannerPic>>> getBannerPic(@Query("platformType") String platformType, @Query("pageIndex") int pageIndex, @Query("pageCount") int pageCount);







    /**
     * 特殊公告
     *
     * @param platformType 0pc 1司机 2供应链 3货主
     * @return
     */
    @POST("specialMsg/list.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<List<SpecialMsg>>> specialMsgList(@Query("platformType") String platformType, @Query("pageIndex") int pageIndex, @Query("pageCount") int pageCount);

    /**
     * 检测更新
     *
     * @param appType app 类型 0为司机端
     */
    @GET("app/version/check/android.do?")
    Call<Update> checkUpdate(@Query("appType") String appType);

    /**
     * 获取节日的图片，登录界面之前显示
     *
     * @param type 0代表android 1代表ios多图
     */
    @POST("app/loginShowPic/getLoginPic.do?")
    Observable<BaseResponse<LoginPic>> getPic(@Query("type") String type);

    /**
     * 扫描后获取到的动态地址
     *
     * @param url 动态地址
     */
    @GET
    Observable<BaseResponse<String>> scanCodeLogin(@Url String url);

    /**
     * 获取服务器时间
     */
    @POST("getServerTime.do?")
    Observable<BaseResponse<String>> getServerTime();

    /**
     * 返回二维码
     * @param type 固定1
     * @param userId 用户id
     */
    @POST("getQrCode.do?")
    @Headers("Cache-Control: public, max-age=60")
    Observable<BaseResponse<QRCode>> getQrCode(@Query("type") String type, @Query("userId") String userId, @Query("appType") String appType, @Query("deviceName") String deviceName);

    /**
     * 扫描二维码后，返回的url手机进行授权登录 ("scanCodeAuth.do?")  @param key key
     */
    @GET
    Observable<BaseResponse<ScanCodeResult>> scanCodeAuth(@Url String url);

    /**
     * 循环检查扫码结果
     */
    @POST("checkScanCode.do?")
    Observable<BaseResponse<ScanCodeResult>> checkScanCode(@Query("key") String key);

    /**
     * 获取登录限制的有关配置
     */
    @POST("loginRestrict.do?")
    Observable<BaseResponse<LoginRestrict>> loginRestrict();

}