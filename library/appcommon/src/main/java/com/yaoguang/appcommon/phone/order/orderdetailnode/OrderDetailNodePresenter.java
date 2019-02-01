package com.yaoguang.appcommon.phone.order.orderdetailnode;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.net.Api;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.DriverOrderNodeList;
import com.yaoguang.greendao.entity.LatLons;
import com.yaoguang.greendao.entity.NodesBean;
import com.yaoguang.greendao.entity.OrderDetailNodeList;
import com.yaoguang.interactor.driver.BasePresenterImplV2;
import com.yaoguang.datasource.api.driver.DriverOrderApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 韦理英
 * on 2017/6/26 0026.
 */

public class OrderDetailNodePresenter extends BasePresenterImplV2 implements OrderDetailNodeContract.Presenter {
    OrderDetailNodeContract.Interactor interactor = new OrderDetailNodeInteractor();
    OrderDetailNodeContract.View view;

    public OrderDetailNodePresenter(OrderDetailNodeContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void activityData(final String orderId, final String orderSn,  String loadingType,String pcSonoId) {
        int loadingTypeInt = 0;
        if (loadingType.equals("0")) {
            loadingTypeInt = 4;
        } else if (loadingType.equals("1")) {
            loadingTypeInt = 5;
        }
        switch (BaseApplication.getAppType()) {
            case Constants.APP_COMPANY:
                final int finalLoadingTypeInt1 = loadingTypeInt;
                Api.getInstance().retrofit.create(DriverOrderApi.class).orderDetailNote(loadingTypeInt, pcSonoId, 1)
                        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                        .subscribe(new Observer<BaseResponse<OrderDetailNodeList>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                mCompositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(BaseResponse<OrderDetailNodeList> value) {
                                activityData(orderSn, finalLoadingTypeInt1, value);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.recyclerViewShowError(null);
                                ULog.e(e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case Constants.APP_SHIPPER:
                final int finalLoadingTypeInt = loadingTypeInt;
                Api.getInstance().retrofit.create(DriverOrderApi.class).orderDetailNote(loadingTypeInt, pcSonoId, 1)
                        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                        .subscribe(new Observer<BaseResponse<OrderDetailNodeList>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                mCompositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(BaseResponse<OrderDetailNodeList> value) {
                                activityData(orderSn, finalLoadingTypeInt, value);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.recyclerViewShowError(null);
                                ULog.e(e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case Constants.APP_DRIVER:
                interactor.requestData(orderId, orderSn).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                        .subscribe(new Observer<BaseResponse<OrderDetailNodeList>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                mCompositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(BaseResponse<OrderDetailNodeList> value) {
                                activityData(orderId,  -1, value);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.recyclerViewShowError(null);
                                ULog.e(e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
        }


    }

    private void activityData(String orderId, int loadingTypeInt, BaseResponse<OrderDetailNodeList> value) {
        if (value.getResult() == null || value.getResult().getNodeWrappers() == null ||
                value.getResult().getNodeWrappers().size() <= 0) {
            view.recyclerViewShowEmpty();
            return;
        }
        if (value.getState().equals("200")) {
            List<NodesBean> nodesBeans = new ArrayList<>();
            // 是否已出车
            boolean isChuChe = false;    // true 已出车 false 未出车
            // 首选判断是否未出车状态（没有一个isLatestFinish 是true的）
            for (DriverOrderNode driverOrderNode : value.getResult().getNodeWrappers()) {
                for (DriverOrderNodeList driverOrderNodeList : driverOrderNode.getDriverOrderNodeList()) {
                    if (driverOrderNodeList.isLatestFinish()) { //  如果最后完成有true,就是已出车了
                        isChuChe = true;   //
                    }
                }
            }
            // 标记红点
            boolean hongDian = true;
            int size = value.getResult().getNodeWrappers().size();
            for (int j = 0; j < size; j++) {
                DriverOrderNode driverOrderNode = value.getResult().getNodeWrappers().get(j);
                NodesBean tmpBean = new NodesBean();
                tmpBean.setNodeName(driverOrderNode.getNodeTitle());
                tmpBean.setAddress(driverOrderNode.getAddress());
                tmpBean.setUpdated(driverOrderNode.getUpdated());
                tmpBean.setHuangDian(isChuChe == true ? hongDian : false);
                nodesBeans.add(tmpBean);
                for (int i = 0; i < driverOrderNode.getDriverOrderNodeList().size(); i++) {
                    DriverOrderNodeList driverOrderNodeList = driverOrderNode.getDriverOrderNodeList().get(i);
                    NodesBean tmp = new NodesBean();
                    tmp.setNodeName(driverOrderNodeList.getNodeName());
                    tmp.setAddress(driverOrderNodeList.getAddress());
                    tmp.setUpdated(driverOrderNodeList.getUpdated());
                    tmp.setDetailFlag(driverOrderNodeList.getDetailFlag());
                    tmp.setDetailSuccess(driverOrderNodeList.getDetailSuccess());
                    tmp.setHuangDian(isChuChe == true ? hongDian : false);
                    tmp.setId(driverOrderNodeList.getId());
                    nodesBeans.add(tmp);

                    if (driverOrderNodeList.isLatestFinish()) {
                        // 如果driverOrderNode有下一个检测下一个LatestFinish
                        if ((i + 1 < driverOrderNode.getDriverOrderNodeList().size() && !driverOrderNode.getDriverOrderNodeList().get(i + 1).isLatestFinish())) {
                            hongDian = false;
                        } else if ((j + 1 < value.getResult().getNodeWrappers().size() && !value.getResult().getNodeWrappers().get(j + 1).getChildName().contains("完成"))) {
                            // 如果找到最后一个红点，且这个红点下一个不是完成
                            hongDian = false;
                        }
                    }
                }
            }
            if (nodesBeans.size() > 0) {
                nodesBeans.remove(0);
                if (nodesBeans.size() > 0)
                    nodesBeans.remove(nodesBeans.size() - 2);  // 删除倒数第二个： 前往-完成
            }
            // 显示信息
            view.showList(nodesBeans);
            // 请求轨迹
            getLatLonHistory(orderId, loadingTypeInt);
        } else {
            view.showToast(value.getMessage());
        }
    }


    /**
     * 描    述：获取轨迹记录
     * 作    者：韦理英
     * 时    间：
     *
     * @param orderId [订单id]
     */

    @Override
    public void getLatLonHistory(@NonNull String orderId,  int loadingTypeInt) {
        if (TextUtils.isEmpty(orderId)) return;
        switch (BaseApplication.getAppType()) {
            case Constants.APP_COMPANY:
                Api.getInstance().retrofit.create(DriverOrderApi.class).logs(loadingTypeInt, orderId, 2).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<BaseResponse<List<LatLons>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResponse<List<LatLons>> latLonsBaseResponse) {
                        getLatLonHistory(latLonsBaseResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast("轨迹记录加载失败：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;
            case Constants.APP_SHIPPER:
                Api.getInstance().retrofit.create(DriverOrderApi.class).logs(loadingTypeInt, orderId, 2).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<BaseResponse<List<LatLons>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResponse<List<LatLons>> latLonsBaseResponse) {
                        getLatLonHistory(latLonsBaseResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast("轨迹记录加载失败：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;
            case Constants.APP_DRIVER:
                // 获取数据
                interactor.requestLatLonHistory(orderId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<BaseResponse<List<LatLons>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResponse<List<LatLons>> latLonsBaseResponse) {
                        getLatLonHistory(latLonsBaseResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast("轨迹记录加载失败：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;
        }

    }

    private void getLatLonHistory(BaseResponse<List<LatLons>> latLonsBaseResponse) {
        // 显示轨迹
        if (latLonsBaseResponse.getState().equals("200") && !latLonsBaseResponse.getResult().isEmpty()) {
            view.showLocalHistoryList(latLonsBaseResponse.getResult());
        } else if (!latLonsBaseResponse.getState().equals("200")) {
            view.showToast(latLonsBaseResponse.getMessage());
        }
    }
}
