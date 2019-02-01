package com.yaoguang.company.my.usermanagement.mode;

import com.yaoguang.greendao.entity.company.user.ViewUserSetting;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * Created by zhongjh on 2018/12/12.
 */

public class UserManagementModeContract {

    public interface Presenter extends BasePresenter {

        /**
         * 详情
         *
         * @param userId 用户id
         */
        void getInfo(String userId);

        /**
         * 取消授权
         * @param id 授权id
         * @param type 0:Web,1:物流，2：boss
         */
        void authCancelAuthorization(String id, int position,int type);

        /**
         * 提交
         */
        void comit(ViewUserSetting viewUserSetting);
    }

    public interface View extends BaseView {

        void setViewUserSetting(ViewUserSetting result);

        /**
         * web端取消授权
         */
        void authCancelWebAuthorization(int position);

        /**
         * Company取消授权
         * @param position
         */
        void authCancelCompanyAuthorization(int position);

        /**
         * Boss端取消授权
         * @param position
         */
        void authCancelBossAuthorization(int position);

        /**
         * 保存成功后
         */
        void comit(String result);

    }

}
