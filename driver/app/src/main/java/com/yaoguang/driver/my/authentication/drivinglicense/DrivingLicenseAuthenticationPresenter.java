package com.yaoguang.driver.my.authentication.drivinglicense;

import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.common.ULog;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;
import com.yaoguang.lib.annotation.apt.InstanceFactory;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/08
 * 描    述：
 * 驾驶证认证控制层
 * =====================================
 */

@InstanceFactory
public class DrivingLicenseAuthenticationPresenter extends DrivingLicenseAuthenticationContacts.Presenter {

    @Override
    public void subscribe() {

    }

    /**
     * 获取数据
     */
    @Override
    void getData() {
        mData.getUserInfo(null).subscribe(driverAuthenticationBaseResponse -> {
            if (driverAuthenticationBaseResponse.getState().equals("200")) {
                if (driverAuthenticationBaseResponse.getResult() != null && driverAuthenticationBaseResponse.getResult().getUserInfo() != null) {
                    mView.refreshData(driverAuthenticationBaseResponse.getResult().getLicenceInfo());
                }
            }
        }, throwable -> ULog.i("加载用户信息失败"+throwable.toString()));
    }

    /**
     * 保存数据
     */
    @Override
    void save(Driver driver) {
        mView.showProgressDialog("正在保存提交，请稍候");
        mData.saveAuthentication(driver).subscribe(driverAuthenticationBaseResponse -> {

            mView.showToast(driverAuthenticationBaseResponse.getResult());
            mView.hideDialog();
        }, throwable -> {
            mView.showToast("保存失败！");
            mView.hideDialog();
        });
    }

    /**
     * 提交数据
     */
    @Override
    void submit(UserDriverLicence driver) {
        mView.showProgressDialog("正在提交，请稍候");
        mData.setAuthentication(driver).subscribe(driverAuthenticationBaseResponse -> {
            if (driverAuthenticationBaseResponse.getState().equals("200")) {
                LoginResult loginResult = Injection.provideDriverRepository(BaseApplication.getInstance()).getLoginResult();
                loginResult.getLicenceInfo().setAuditStatus(1);
                Injection.provideDriverRepository(BaseApplication.getInstance()).saveOrUpdate(loginResult);
                mView.succeed(loginResult);
            } else {
                mView.showToast(driverAuthenticationBaseResponse.getResult());
            }
        }, throwable -> {
            mView.showToast("提交审核失败！");
            mView.hideDialog();
        });
    }
}
