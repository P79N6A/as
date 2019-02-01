package com.yaoguang.driver.order.chooseaddress;

import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.data.source.OrderRepository;
import com.yaoguang.driver.order.child.OrderChildContacts;
import com.yaoguang.greendao.entity.InfoClientPlace;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.Order;
import com.yaoguang.interfaces.BaseListViewV2;

import java.util.List;

/**
 * 控制层与view层
 * Created by wly on 2017/12/28 0028.
 */

public interface PutOrderAddressContacts {

    interface View extends BaseListViewV2<Order> {
        void setData(BaseResponse<List<InfoPutorderPlace>> listBaseResponse);
    }

    abstract class Presenter extends BasePresenter<View, OrderRepository> {
        abstract void getData(String id);
    }

}
