package com.yaoguang.datasource.api.company;

import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowLocation;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowWlan;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAuthHistory;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTime;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTimeWrapper;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2018/12/6.
 */
public interface MemberApi {

    String MEMBER = "member";

    /**
     * 登录时间方案列表
     *
     * @param name      名称
     * @param pageIndex 页码
     * @return 返回数据源
     */
    @POST(MEMBER + "/userLoginTime/page.do?")
    @Headers("Cache-Control: public, max-age=5")
    Observable<BaseResponse<PageList<UserLoginTime>>> userLoginTimePage(@Query("name") String name, @Query("pageIndex") int pageIndex);

    /**
     * 新增或者编辑
     * @param userLoginTime 实体
     */
    @POST(MEMBER + "/userLoginTime/edit.do?")
    Observable<BaseResponse<String>> userLoginTimeEdit(@Body UserLoginTimeWrapper userLoginTime);

    /**
     * 删除
     * @param ids id
     */
    @POST(MEMBER + "/userLoginTime/delete.do?")
    Observable<BaseResponse<String>> userLoginTimeDelete(@Query("ids") String ids);

    /**
     * 地理位置列表
     * @param pageIndex 页码
     * @return 返回数据源
     */
    @POST(MEMBER + "/login/location/page.do?")
    @Headers("Cache-Control: public, max-age=5")
    Observable<BaseResponse<PageList<UserLoginAllowLocation>>> locationPage(@Query("pageIndex") int pageIndex);

    /**
     * 新增或者编辑
     * @param userLoginAllowLocation 实体
     */
    @POST(MEMBER + "/login/location/save.do?")
    Observable<BaseResponse<String>> locationSave(@Body UserLoginAllowLocation userLoginAllowLocation);

    /**
     * 删除
     * @param id id
     */
    @POST(MEMBER + "/login/location/delete.do?")
    Observable<BaseResponse<String>> locationDelete(@Query("id") String id);

    /**
     * 授权历史
     * @param userId 用户id
     * @param appType 0:Web,1:物流 2:Boss
     */
    @POST(MEMBER + "/user/auth/history.do?")
    Observable<BaseResponse<List<UserLoginAuthHistory>>> authHistory(@Query("userId") String userId, @Query("appType") String appType);

    /**
     * 取消授权
     * @param id id
     */
    @POST(MEMBER + "/user/auth/cancel.do?")
    Observable<BaseResponse<String>> authCancel(@Query("id") String id);

    /**
     * wifi网络群组列表
     */
    @POST(MEMBER + "/login/wlan/page.do?")
    Observable<BaseResponse<PageList<UserLoginAllowWlan>>> loginWlanPage(@Query("pageIndex") int pageIndex);

    /**
     * wifi网络群组列表新增或者编辑
     * @param userLoginAllowWlan 实体
     */
    @POST(MEMBER + "/login/wlan/save.do?")
    Observable<BaseResponse<String>> wlanSave(@Body UserLoginAllowWlan userLoginAllowWlan);

    /**
     * wifi网络群组列表删除
     * @param id id
     */
    @POST(MEMBER + "/login/wlan/delete.do?")
    Observable<BaseResponse<String>> wlanDelete(@Query("id") String id);

}
