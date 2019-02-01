package com.yaoguang.datasource.api.company;

import com.yaoguang.greendao.entity.company.user.UserCondition;
import com.yaoguang.greendao.entity.company.user.ViewUser;
import com.yaoguang.greendao.entity.company.user.ViewUserSetting;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAuthHistory;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhongjh on 2018/12/11.
 */

public interface ManageUserApi {

    String MANAGE_USER = "manage/user";

    /**
     * 登录时间方案列表
     *
     * @param userCondition 条件
     * @param pageIndex     页码
     * @return 返回数据源
     */
    @POST(MANAGE_USER + "/page.do?")
    @Headers("Cache-Control: public, max-age=5")
    Observable<BaseResponse<PageList<ViewUser>>> userPage(@Body UserCondition userCondition, @Query("userId") String userId, @Query("pageIndex") int pageIndex);

    /**
     * 详情
     * @param userId 用户id
     * @return 返回数据源
     */
    @POST(MANAGE_USER + "/setting.do?")
    Observable<BaseResponse<ViewUserSetting>> getInfo(@Query("userId") String userId);

    /**
     * 编辑保存
     * @param viewUserSetting 实体
     */
    @POST(MANAGE_USER + "/save.do?")
    Observable<BaseResponse<String>> save(@Body ViewUserSetting viewUserSetting);


}
