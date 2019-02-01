package com.yaoguang.datasource.company;

import com.yaoguang.datasource.api.company.MemberApi;
import com.yaoguang.datasource.api.company.RecentlyApi;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowWlan;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserRecentlyUsedWlan;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2018/12/19.
 */

public class RecentlyDataSource {

    /**
     * wifi网络群组列表
     *
     * @param pageIndex 页码
     * @param username  用户名
     */
    public Observable<BaseResponse<PageList<UserRecentlyUsedWlan>>> wlanLogPage(int pageIndex, String username) {
        RecentlyApi recentlyApi = Api.getInstance().retrofit.create(RecentlyApi.class);
        return recentlyApi.wlanLogPage(pageIndex, username);
    }

    /**
     * wifi网络群组列表新增或者编辑
     *
     * @param SSID  name
     * @param BSSID mac
     */
    public Observable<BaseResponse<String>> wlanLogSave(String SSID, String BSSID) {
        RecentlyApi recentlyApi = Api.getInstance().retrofit.create(RecentlyApi.class);
        return recentlyApi.wlanLogSave(SSID, BSSID);
    }

}
