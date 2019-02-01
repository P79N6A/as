package com.yaoguang.interactor.common;

import com.yaoguang.greendao.entity.AppUserWrapper;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.UserOwner;

/**
 * Created by zhongjh on 2017/6/5.
 */

public interface DCSBaseInteractor {

    /**
     *
     * @return 物流
     */
    AppUserWrapper getAppUserWrapper();

    /**
     *
     * @return 货主
     */
    UserOwner getUserOwner();

    /**
     *
     * @return 司机
     */
    Driver getDriver();
}
