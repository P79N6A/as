package com.yaoguang.interfaces.driver.view;


import com.yaoguang.greendao.entity.Driver;

/**
 * 注册页显示层
 * 作者：zhongjh
 * 时间：2017-04-05
 */
public interface DRegisterThreeView extends DRegisterView {
    void startShowCountdown();
    void customSetting();
    void nextPage();
    Driver getDriver();
}
