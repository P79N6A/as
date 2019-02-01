package com.yaoguang.interactor.driver.my.setting;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.Update;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public interface SettingInteractor {
    Observable<BaseResponse<String>> setPush(boolean isOpen, String codeType);

    void setPushInfo(boolean isOpen, String codeType);

    Observable<Update> checkUpdate();
}
