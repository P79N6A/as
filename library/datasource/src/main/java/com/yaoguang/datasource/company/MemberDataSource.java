package com.yaoguang.datasource.company;

import com.yaoguang.datasource.api.company.ManageUserApi;
import com.yaoguang.datasource.api.company.MemberApi;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowLocation;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowWlan;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAuthHistory;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTime;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTimeWrapper;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/12/6.
 */
public class MemberDataSource {

    /**
     * 登录时间方案列表
     *
     * @param name      名称
     * @param pageIndex 页码
     */
    public Observable<BaseResponse<PageList<UserLoginTime>>> userLoginTimePage(String name, int pageIndex){
        MemberApi memberApi = Api.getInstance().retrofit.create(MemberApi.class);
        return memberApi.userLoginTimePage(name, pageIndex);
    }

    /**
     * 登录时间 新增或者编辑
     * @param userLoginTime 实体
     */
    public Observable<BaseResponse<String>> userLoginTimeEdit(UserLoginTime userLoginTime){
        UserLoginTimeWrapper userLoginTimeWrapper = new UserLoginTimeWrapper();
        List<UserLoginTime> userLoginTimes = new ArrayList<>();
        userLoginTimes.add(userLoginTime);
        userLoginTimeWrapper.setUserLoginTimes(userLoginTimes);
        MemberApi memberApi = Api.getInstance().retrofit.create(MemberApi.class);
        return memberApi.userLoginTimeEdit(userLoginTimeWrapper);
    }

    /**
     * 登录时间 删除
     * @param ids
     */
    public Observable<BaseResponse<String>> userLoginTimeDelete(String ids){
        MemberApi memberApi = Api.getInstance().retrofit.create(MemberApi.class);
        return memberApi.userLoginTimeDelete(ids);
    }

    /**
     * 地理位置列表
     *
     * @param pageIndex 页码
     */
    public Observable<BaseResponse<PageList<UserLoginAllowLocation>>> locationPage(int pageIndex){
        MemberApi memberApi = Api.getInstance().retrofit.create(MemberApi.class);
        return memberApi.locationPage(pageIndex);
    }

    /**
     * 地理位置列表 新增或者编辑
     * @param userLoginAllowLocation 实体
     */
    public Observable<BaseResponse<String>> locationSave(UserLoginAllowLocation userLoginAllowLocation){
        MemberApi memberApi = Api.getInstance().retrofit.create(MemberApi.class);
        return memberApi.locationSave(userLoginAllowLocation);
    }

    /**
     * 地理位置列表 删除
     * @param id
     */
    public Observable<BaseResponse<String>> locationDelete(String id){
        MemberApi memberApi = Api.getInstance().retrofit.create(MemberApi.class);
        return memberApi.locationDelete(id);
    }

    /**
     * 授权历史
     *
     * @param appType 0:Web,1:物流 2:Boss
     */
    public Observable<BaseResponse<List<UserLoginAuthHistory>>> authHistory(String userId,String appType) {
        MemberApi memberApi = Api.getInstance().retrofit.create(MemberApi.class);
        return memberApi.authHistory(userId, appType);
    }


    /**
     * 取消授权
     *
     * @param id id
     */
    public Observable<BaseResponse<String>> authCancel(String id) {
        MemberApi memberApi = Api.getInstance().retrofit.create(MemberApi.class);
        return memberApi.authCancel(id);
    }

    /**
     * wifi网络群组列表
     *
     * @param pageIndex 页码
     */
    public Observable<BaseResponse<PageList<UserLoginAllowWlan>>> loginWlanPage(int pageIndex){
        MemberApi memberApi = Api.getInstance().retrofit.create(MemberApi.class);
        return memberApi.loginWlanPage(pageIndex);
    }

    /**
     * wifi网络群组列表 新增或者编辑
     * @param userLoginAllowWlan 实体
     */
    public Observable<BaseResponse<String>> wlanSave(UserLoginAllowWlan userLoginAllowWlan){
        MemberApi memberApi = Api.getInstance().retrofit.create(MemberApi.class);
        return memberApi.wlanSave(userLoginAllowWlan);
    }

    /**
     * wifi网络群组列表 删除
     * @param id
     */
    public Observable<BaseResponse<String>> wlanDelete(String id){
        MemberApi memberApi = Api.getInstance().retrofit.create(MemberApi.class);
        return memberApi.wlanDelete(id);
    }


}
