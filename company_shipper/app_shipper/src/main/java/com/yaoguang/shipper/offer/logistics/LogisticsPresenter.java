package com.yaoguang.shipper.offer.logistics;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.shipper.OwnerBaseInfoDataSource;
import com.yaoguang.greendao.entity.AppPriceAnalysisWrapper;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.PriceAnalysisCondition;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * 控制层
 * Created by zhongjh on 2017/6/12.
 */
public class LogisticsPresenter extends BasePresenterListCondition<String, AppPublicInfoWrapper> implements LogisticsContact.Presenter {

    private LogisticsContact.View mView;
    private OwnerBaseInfoDataSource mOwnerBaseInfoDataSource = new OwnerBaseInfoDataSource();


    LogisticsPresenter(LogisticsContact.View view) {
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
    protected Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> initDatas(String condition, int pageIndex) {
        return mOwnerBaseInfoDataSource.getContactCompany("");
    }

}
