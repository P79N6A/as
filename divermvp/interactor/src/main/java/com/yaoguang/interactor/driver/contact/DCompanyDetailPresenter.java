package com.yaoguang.interactor.driver.contact;

import com.yaoguang.lib.base.interfaces.BasePresenter;

/**
 * 公司详情
 * Created by zhongjh on 2017/4/26.
 */
public interface DCompanyDetailPresenter extends BasePresenter {

    void showCompanyInfo(String companyId);

    void handleAddContact(String id, String companyId);

    void handleDeleteContact(String strContactId);

    void handleCancelContact(String strCompanyId);
}
