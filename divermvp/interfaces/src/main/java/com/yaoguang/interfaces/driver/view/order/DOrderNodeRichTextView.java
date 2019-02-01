package com.yaoguang.interfaces.driver.view.order;

import com.yaoguang.greendao.entity.DriverOrderNodeDetailWrapper;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * 富文本形式的提交
 * Created by zhongjh on 2017/5/16.
 */
public interface DOrderNodeRichTextView extends BaseView {


    void initData();

    void refreshPhotograph(String url);

    void setDriverOrderNodeDetailWrapper(DriverOrderNodeDetailWrapper result);

    void success(String result);

    void showContErrorDialog(String strError);
}
