package com.yaoguang.datasource.company;

import com.yaoguang.datasource.api.company.ManageUserApi;
import com.yaoguang.datasource.api.company.MemberApi;
import com.yaoguang.datasource.common.DCSBaseDataSource;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.company.user.UserCondition;
import com.yaoguang.greendao.entity.company.user.ViewUser;
import com.yaoguang.greendao.entity.company.user.ViewUserSetting;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAuthHistory;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTime;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2018/12/12.
 */

public class ManageUserDataSource extends DCSBaseDataSource {

    /**
     * 登录时间方案列表
     *
     * @param userCondition 条件
     * @param pageIndex     页码
     */
    public Observable<BaseResponse<PageList<ViewUser>>> userPage(UserCondition userCondition, int pageIndex) {
        ManageUserApi manageUserApi = Api.getInstance().retrofit.create(ManageUserApi.class);
        return manageUserApi.userPage(userCondition, DataStatic.getInstance().getId(), pageIndex);
    }

    /**
     * 详情
     *
     * @param userId 用户id
     * @return 返回数据源
     */
    public Observable<BaseResponse<ViewUserSetting>> getInfo(String userId) {
        ManageUserApi manageUserApi = Api.getInstance().retrofit.create(ManageUserApi.class);
        return manageUserApi.getInfo(userId);
    }

    /**
     * 编辑保存
     *
     * @param viewUserSetting 实体
     */
    public Observable<BaseResponse<String>> save(ViewUserSetting viewUserSetting) {
        ManageUserApi manageUserApi = Api.getInstance().retrofit.create(ManageUserApi.class);
        return manageUserApi.save(viewUserSetting);
    }




}
