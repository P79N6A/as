package com.yaoguang.driver.phone.home;

import com.yaoguang.datasource.common.AppDataSource;
import com.yaoguang.datasource.common.ContactDataSource;
import com.yaoguang.datasource.driver.DriverIndexDataSource;
import com.yaoguang.datasource.driver.OrderMsgDataSource;
import com.yaoguang.datasource.driver.RestPlanDataSource;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.BannerPic;
import com.yaoguang.greendao.entity.DriverStatusSwitch;
import com.yaoguang.greendao.entity.common.DriverContactCompany;
import com.yaoguang.greendao.entity.driver.DriverOrderMsgWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderProgressWrapper;
import com.yaoguang.lib.base.impl.BasePresenter;
import com.yaoguang.lib.common.DateUtils;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.text.ParseException;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.yaoguang.lib.common.DateUtils.YYYY_MM_DD_HH_MM_SS;

/**
 * 作者：韦理英
 * 时间： 2017/5/8 0008.
 * 修改：钟景华
 * 修改内容：添加修改工作状态
 */

public class HomePresenter extends BasePresenter implements HomeContacts.Presenter {

    private HomeContacts.View mView;
    private AppDataSource mAppDataSource = new AppDataSource();
    private DriverIndexDataSource mDriverIndexDataSource = new DriverIndexDataSource();
    private RestPlanDataSource mRestPlanDataSource = new RestPlanDataSource();
    private OrderMsgDataSource mOrderMsgDataSource = new OrderMsgDataSource();
    private ContactDataSource mContactDataSource = new ContactDataSource();

    HomePresenter(HomeContacts.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        getBanner();
        getCurrentProgress();
        getDriverLeaveStatus();
        getMessageList(1);
        mView.initWeather();
        getContactAll();
    }

    @Override
    public void getBanner() {
        mAppDataSource.getBannerPic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<PageList<BannerPic>>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<PageList<BannerPic>> response) {
                        // 显示广告
                        if (!response.getResult().getResult().isEmpty())
                            mView.showBannerImage(response.getResult().getResult());
                        mView.hideSRLLoadingView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.hideSRLLoadingView();
                    }

                    @Override
                    public void onFail(BaseResponse<PageList<BannerPic>> response) {
                        super.onFail(response);
                        mView.hideSRLLoadingView();
                    }
                });
    }

    /**
     * 获取当前进度
     */
    @Override
    public void getCurrentProgress() {
        mDriverIndexDataSource.getOrderCurrentProgress(R.drawable.ic_dc_s02)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<DriverOrderProgressWrapper>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<DriverOrderProgressWrapper> response) {
                        // 如果有进度，则显示，否则显示地图
                        mView.showOrderProgress(response.getResult());
                        mView.hideSRLLoadingView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.hideSRLLoadingView();
                    }

                    @Override
                    public void onFail(BaseResponse<DriverOrderProgressWrapper> response) {
                        super.onFail(response);
                        mView.hideSRLLoadingView();
                    }
                });
    }

    @Override
    public void getMessageList(int pageIndex) {
        mOrderMsgDataSource.getMessageList(pageIndex, true, R.drawable.ic_dc_s02)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<PageList<DriverOrderMsgWrapper>>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<PageList<DriverOrderMsgWrapper>> response) {
                        mView.hideSRLLoadingView();
                        mView.showMessageList(response.getResult(), pageIndex);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.hideSRLLoadingView();
                    }

                    @Override
                    public void onFail(BaseResponse<PageList<DriverOrderMsgWrapper>> response) {
                        super.onFail(response);
                        mView.hideSRLLoadingView();
                    }
                });
    }

    /**
     * 获取休假计划
     */
    @Override
    public void getDriverLeaveStatus() {
        // 获取本地休假计划
        mRestPlanDataSource.getDriverStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<DriverStatusSwitch>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<DriverStatusSwitch> response) {
                        // 显示休假计划
                        mView.showDriverLeaveStatus(response);
                        mView.hideSRLLoadingView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.hideSRLLoadingView();
                    }

                    @Override
                    public void onFail(BaseResponse<DriverStatusSwitch> response) {
                        super.onFail(response);
                        mView.hideSRLLoadingView();
                    }
                });
    }


    @Override
    public void readBatch(String ids) {
        mOrderMsgDataSource.readBatch(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        // 刷新列表
                        getMessageList(1);
                    }
                });
    }

    @Override
    public void unSubscribe() {
        super.unSubscribe();
    }

    @Override
    public void getContactAll() {
        mContactDataSource.getPassListVersion2All(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<PageList<DriverContactCompany>>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<PageList<DriverContactCompany>> response) {
                        if (response.getResult() != null && response.getResult().getResult() != null) {
                            DriverContactCompany item = null;
                            String date = null;
                            // 循环最新关联的事件
                            for (DriverContactCompany driverContactCompany : response.getResult().getResult()) {
                                if (date == null)
                                    date = driverContactCompany.getCreated();
                                // 判断时间大小
                                try {
                                    boolean isDate = DateUtils.compare(driverContactCompany.getCreated(), date);
                                    // 取最大的时间
                                    if (!isDate) {
                                        date = driverContactCompany.getCreated();
                                        item = driverContactCompany;
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            mView.refreshTitle(item);
                        }
                    }
                });
    }

}
