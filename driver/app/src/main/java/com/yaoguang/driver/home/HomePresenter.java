package com.yaoguang.driver.home;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.yaoguang.common.common.Constants;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.driver.data.source.AppRepository;
import com.yaoguang.driver.data.source.OrderRepository;
import com.yaoguang.driver.home.bean.HomeBean;
import com.yaoguang.greendao.Injections;
import com.yaoguang.greendao.entity.BannerPic;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.DriverOrderProgressWrapper;
import com.yaoguang.lib.annotation.apt.InstanceFactory;
import com.yaoguang.taobao.push.impl.PushManager;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 作者：韦理英
 * 时间： 2017/5/8 0008.
 * 修改：钟景华
 * 修改内容：添加修改工作状态
 */

@InstanceFactory
public class HomePresenter extends HomeContacts.Presenter {
    private AppRepository mAppRepository;
    private OrderRepository mOrderRepository;

    @Override
    public void subscribe() {
        getBanner();
        getCurrentProgress();
        getDriverLeaveStatus();

//        // 司机状态,进度,广告banner
//        Flowable.zip(mAppRepository.getBannerPic(), mOrderRepository.getOrderCurrentProgress(null), mData.getDriverStatus(), (pageListBaseResponse, driverOrderProgressWrapperBaseResponse, driverStatusSwitchBaseResponse) -> {
//            HomeBean homeBean = new HomeBean();
//            homeBean.setDriverStatusSwitch(driverStatusSwitchBaseResponse);
//            homeBean.setDriverOrderProgressWrapperBase(driverOrderProgressWrapperBaseResponse);
//            homeBean.setBannerPics(pageListBaseResponse);
//            return homeBean;
//        }).subscribe(new Consumer<HomeBean>() {
//
//            @Override
//            @AbnormalLogin
//            public void accept(HomeBean homeBean) throws Exception {
//
//                // 如果有进度，则显示，否则显示地图
//                if (homeBean != null && homeBean.getDriverOrderProgressWrapperBase() != null && homeBean.getDriverOrderProgressWrapperBase().getState().equals("200")
//                        && homeBean.getDriverOrderProgressWrapperBase().getResult() != null
//                        && !TextUtils.isEmpty(homeBean.getDriverOrderProgressWrapperBase().getResult().getOrderSn()))
//                    mView.showOrderProgress(homeBean.getDriverOrderProgressWrapperBase().getResult());
//                else mView.showOrderProgress(null);
//
//                // 显示休假计划
//                if (homeBean != null && homeBean.getDriverStatusSwitch() != null)
//                    mView.showDriverLeaveStatus(homeBean.getDriverStatusSwitch());
//
//                mView.hideSRLLoadingView();
//            }
//        }, throwable -> mView.hideSRLLoadingView());
    }

    @Override
    void getBanner() {
        mAppRepository.getBannerPic().subscribe(new Consumer<BaseResponse<PageList<BannerPic>>>() {
            @Override
            public void accept(BaseResponse<PageList<BannerPic>> pageListBaseResponse) throws Exception {
                // 显示广告
                if (pageListBaseResponse.getState().equals("200") && !pageListBaseResponse.getResult().getResult().isEmpty())
                    mView.showBannerImage(pageListBaseResponse.getResult().getResult());
            }
        }, throwable -> mView.hideSRLLoadingView());
    }

    /**
     * 获取当前进度
     */
    @Override
    public void getCurrentProgress() {
        mOrderRepository.getOrderCurrentProgress(mData.getDriver().getId()).subscribe(new Consumer<BaseResponse<DriverOrderProgressWrapper>>() {
            @Override
            public void accept(BaseResponse<DriverOrderProgressWrapper> driverOrderProgressWrapperBaseResponse) throws Exception {
                // 如果有进度，则显示，否则显示地图
                if (driverOrderProgressWrapperBaseResponse.getState().equals("200") && driverOrderProgressWrapperBaseResponse.getResult() != null)
                    mView.showOrderProgress(driverOrderProgressWrapperBaseResponse.getResult());
                else mView.showOrderProgress(null);
            }
        }, throwable -> mView.hideSRLLoadingView());
    }

    /**
     * 跳转至地图页
     *
     * @param orderId 订单id
     * @param id      id
     */
    @Override
    public void gotoMap(@NonNull String orderId, @NonNull final String id) {
        mOrderRepository.nextNode(checkNotNull(orderId)).subscribe(new Consumer<BaseResponse<List<DriverOrderNode>>>() {
            @Override
            public void accept(BaseResponse<List<DriverOrderNode>> value) throws Exception {
                if (value.getState().equals("200") && value.getResult().isEmpty())
                    mView.showToast("节点已完成，请进行下一个节点,再跳转");
                else //跳转
                    if (value.getState().equals("200"))
                        mView.startOrderNodeMapFragment(value.getResult(), checkNotNull(id));
                    else mView.showToast(value.getMessage());
            }
        }, throwable -> mView.hideSRLLoadingView());
    }

    /**
     * 获取休假计划
     */
    @Override
    public void getDriverLeaveStatus() {
        mData.getDriverStatus().subscribe(driverStatusSwitchBaseResponse -> {
            // 显示休假计划
            mView.showDriverLeaveStatus(driverStatusSwitchBaseResponse);
            mView.hideSRLLoadingView();
        }, throwable -> mView.hideSRLLoadingView());
    }

    @Override
    public void unSubscribe() {
        super.unSubscribe();
    }

    void setAppRepository(AppRepository appRepository) {
        this.mAppRepository = appRepository;
    }

    void setOrderRepository(OrderRepository orderRepository) {
        this.mOrderRepository = orderRepository;
    }
}
