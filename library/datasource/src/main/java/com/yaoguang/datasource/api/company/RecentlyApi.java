package com.yaoguang.datasource.api.company;

import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowWlan;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserRecentlyUsedWlan;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2018/12/19.
 */
public interface RecentlyApi {

    String RECENTLY = "recently";


    /**
     * wifi网络群组列表
     *
     * @param username 用户名
     */
    @POST(RECENTLY + "/wlan/log/page.do?")
    Observable<BaseResponse<PageList<UserRecentlyUsedWlan>>> wlanLogPage(@Query("pageIndex") int pageIndex, @Query("username") String username);

    /**
     * wifi网络群组列表新增或者编辑
     *
     * @param SSID  name
     * @param BSSID mac
     */
    @POST(RECENTLY + "/wlan/log/save.do?")
    Observable<BaseResponse<String>> wlanLogSave(@Query("SSID") String SSID, @Query("BSSID") String BSSID);

}
