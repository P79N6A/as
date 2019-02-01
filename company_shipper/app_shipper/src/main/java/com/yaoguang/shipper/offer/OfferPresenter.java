package com.yaoguang.shipper.offer;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.shipper.OwnerBaseInfoDataSource;
import com.yaoguang.datasource.shipper.OwnerPriceDataSource;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.shipper.PriceShipperReceivableRate;
import com.yaoguang.greendao.entity.shipper.PriceShipperReceivableRateCondition;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * 控制层
 * Created by zhongjh on 2017/6/12.
 */
public class OfferPresenter extends BasePresenterListCondition<PriceShipperReceivableRateCondition, PriceShipperReceivableRate> implements OfferContact.Presenter {

    private OfferContact.View mView;
    private OwnerPriceDataSource mOwnerPriceDataSource = new OwnerPriceDataSource();

    OfferPresenter(OfferContact.View view) {
        mView = view;
    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    protected Observable<BaseResponse<PageList<PriceShipperReceivableRate>>> initDatas(PriceShipperReceivableRateCondition condition, int pageIndex) {
        return mOwnerPriceDataSource.getShipperFee(condition, pageIndex);
    }

}
