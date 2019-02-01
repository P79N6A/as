package com.yaoguang.shipper.offer;

import com.yaoguang.greendao.entity.shipper.PriceShipperReceivableRate;
import com.yaoguang.greendao.entity.shipper.PriceShipperReceivableRateCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * 报价查询
 * Created by zhongjh on 2018/8/29.
 */
public class OfferContact {

    public interface Presenter extends BasePresenterListCondition<PriceShipperReceivableRateCondition> {

    }

    public interface View extends BaseView, BaseListConditionView<PriceShipperReceivableRate, PriceShipperReceivableRateCondition> {

    }

}
