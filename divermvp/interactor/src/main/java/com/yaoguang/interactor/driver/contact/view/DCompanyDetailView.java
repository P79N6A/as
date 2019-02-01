package com.yaoguang.interactor.driver.contact.view;

import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.Contact;
import com.yaoguang.greendao.entity.common.UserOffice;
import com.yaoguang.lib.base.interfaces.BaseView;

import java.util.ArrayList;

/**
 * 企业详情
 * Created by zhongjh on 2017/4/26.
 */
public interface DCompanyDetailView extends BaseView {

    /**
     * 初始化数据源
     * @param result
     */
    void showData(UserOffice result);

    void showAddContactSuccess();

    void initListener();

    void showData(PageList<Contact> contacts);

    void showCancelSuccess();

    void showDeleteSuccess();

    void nextAdapter(ArrayList<Contact> result, boolean hasNextPage);

    void refreshAdapter(ArrayList<Contact> result, boolean hasNextPage);
}
