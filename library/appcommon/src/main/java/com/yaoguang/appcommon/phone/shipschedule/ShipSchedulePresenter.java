package com.yaoguang.appcommon.phone.shipschedule;

import android.support.annotation.NonNull;

import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.datasource.company.CompanyBaseInfoDataSource;
import com.yaoguang.datasource.shipper.OwnerBaseInfoDataSource;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.InfoVoyageTable;
import com.yaoguang.greendao.entity.InfoVoyageTableCondition;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author zhongjh
 * @Package com.yaoguang.company.shipschedule
 * @Description: 船期查询 控制层
 * @date 2018/01/15
 */
public class ShipSchedulePresenter extends BasePresenterListCondition<InfoVoyageTableCondition, InfoVoyageTable> implements ShipScheduleContract.Presenter {

    private ShipScheduleContract.View mView;
    private CompanyBaseInfoDataSource mCompanyBaseInfoDataSource = new CompanyBaseInfoDataSource();
    private OwnerBaseInfoDataSource mOwnerBaseInfoDataSource = new OwnerBaseInfoDataSource();

    ShipSchedulePresenter(ShipScheduleContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        //解析柜型柜量数据
        analysisSonos();
    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    protected Observable<BaseResponse<PageList<InfoVoyageTable>>> initDatas(InfoVoyageTableCondition condition, int pageIndex) {
        // 判断app类型
        switch (BaseApplication.getAppType()) {
            case Constants.APP_COMPANY:
                return mCompanyBaseInfoDataSource.getInfoVoyageTables(condition, pageIndex);
            case Constants.APP_SHIPPER:
                return mOwnerBaseInfoDataSource.getInfoVoyageTables(condition, pageIndex);
            default:
                return null;
        }
    }

    @Override
    public void analysisSonos() {
        mCompanyBaseInfoDataSource.getConts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<InfoContType>>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<List<InfoContType>> response) {
                        mView.setSonos(response);
                    }

                });
    }

    @Override
    public void analysisContactCompany() {
        mOwnerBaseInfoDataSource.getContactCompany("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<PageList<AppPublicInfoWrapper>>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<PageList<AppPublicInfoWrapper>> response) {
                        if (response.getResult() != null && response.getResult().getResult() != null && response.getResult().getResult().size() > 0) {
                            mView.setContactCompany(response.getResult().getResult().get(0));
                        }
                    }

                });
    }

}
