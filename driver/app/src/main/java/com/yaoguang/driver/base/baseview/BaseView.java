package com.yaoguang.driver.base.baseview;


/**
 * Project Name:driver
 * Created by weiliying
 * on 2017 2017/4/19.16:21
 * Update wly : 删除一些多余的方法
 */

public interface BaseView {

    void showToast(String msg);

    void showProgressDialog(String msg);

    void showProgressDialog(String title, String msg);

    void showProgressDialog();

    void hideDialog();

}
