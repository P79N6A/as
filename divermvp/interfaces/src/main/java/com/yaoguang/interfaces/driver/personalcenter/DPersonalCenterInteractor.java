package com.yaoguang.interfaces.driver.personalcenter;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.UnreadNum;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public interface DPersonalCenterInteractor {
    Driver getLocalDriverInfo();

    Observable<BaseResponse<UnreadNum>> getUnreadNum();
}
