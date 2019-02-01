package com.yaoguang.driver.phone.order.feedback.add;

import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.BaseResponse;

/**
 * 关联
 * Created by zhongjh on 2018/5/17.
 */
public class OrderFeedBackAddContacts {

    interface View extends BaseView {
        void success(BaseResponse<String> value);

        void fail(String message);
    }

    interface Presenter extends BasePresenter {
        /**
         * 添加反馈
         * @param driverOrderNodeDetail   反馈内容
         * @param dsc
         * */
        void add(DriverOrderNodeDetail driverOrderNodeDetail, String dsc);
    }

}
