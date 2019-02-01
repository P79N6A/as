package com.yaoguang.interactor.driver.my.setting;

import com.yaoguang.lib.base.interfaces.BasePresenter;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public interface SettingPresenter extends BasePresenter {
    void pushSetting(boolean isOpen,String codeType);
}
