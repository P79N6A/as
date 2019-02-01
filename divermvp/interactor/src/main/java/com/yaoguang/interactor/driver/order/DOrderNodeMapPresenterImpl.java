package com.yaoguang.interactor.driver.order;

import android.text.TextUtils;

import com.elvishew.xlog.XLog;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.greendao.Injections;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.DriverOrderNodeList;
import com.yaoguang.greendao.entity.Location;
import com.yaoguang.greendao.entity.NodesBean;
import com.yaoguang.interactor.driver.BasePresenterImplV2;
import com.yaoguang.interfaces.driver.interactor.order.DOrderChildInteractor;
import com.yaoguang.interfaces.driver.interactor.order.DOrderNodeMapInteractor;
import com.yaoguang.interfaces.driver.presenter.order.DOrderNodeMapPresenter;
import com.yaoguang.interfaces.driver.view.order.DOrderNodeMapView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 节点控制层
 * Created by zhongjh on 2017/5/15.
 */
public class DOrderNodeMapPresenterImpl extends BasePresenterImplV2 implements DOrderNodeMapPresenter {

    private DOrderChildInteractor mDOrderChildInteractor;
    private DOrderNodeMapInteractor mDOrderNodeMapInteractor;
    private DOrderNodeMapView mDOrderNodeMapView;

    public DOrderNodeMapPresenterImpl(DOrderNodeMapView dDOrderNodeMapView) {
        this.mDOrderNodeMapView = dDOrderNodeMapView;
        mDOrderNodeMapInteractor = new DOrderNodeMapInteractorImpl();
        mDOrderChildInteractor = new DOrderChildInteractorImpl();
    }

    @Override
    public NodesBean getNodesBeansFirst(List<NodesBean> nodesBeans) {
        return mDOrderNodeMapInteractor.getNodesBeansFirst(nodesBeans);
    }

    @Override
    public NodesBean getNodesBeansNext(List<NodesBean> nodesBeans) {
        return mDOrderNodeMapInteractor.getNodesBeansNext(nodesBeans);
    }

    @Override
    public String getCarNumber() {
        String carNumber = null;
        Driver driver = DataStatic.getInstance().getDriver();
        if (driver != null && !TextUtils.isEmpty(driver.getCarNumber())) {
            carNumber = driver.getCarNumber();
        }
        ULog.i("carNumber"+carNumber);
        return carNumber;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void switchToNode(final List<DriverOrderNode> driverOrderNodes, String id, String switchToNodeId) {
        Observable<BaseResponse<String>> baseResponseObservable = mDOrderNodeMapInteractor.switchToNode(id, switchToNodeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        subscribe(baseResponseObservable,mDOrderNodeMapView,ShowType.PROCESS,new OnCallback() {
            @Override
            public void onNext200(BaseResponse listBaseResponse) {
                mDOrderNodeMapView.switchToNodeSuccess();
                getNodes(driverOrderNodes.get(0).getDriverOrderId());
            }
        });
    }

    @Override
    public void getNodes(String orderId) {
        Observable<BaseResponse<List<DriverOrderNode>>> baseResponseObservable = mDOrderChildInteractor.initNodes(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        subscribe(baseResponseObservable,mDOrderNodeMapView,ShowType.TOAST,new OnCallback() {
            @Override
            public boolean onNext(BaseResponse listBaseResponse) {
                BaseResponse<List<DriverOrderNode>> value = listBaseResponse;
                if (value != null) {
                    if (value.getState().equals("200") && value.getResult().isEmpty()) {
                        mDOrderNodeMapView.orderFinish();
                    } else {
                        mDOrderNodeMapView.clearOldData();
                        mDOrderNodeMapView.setDriverOrderNodes(value.getResult());
                        //跳转
                        mDOrderNodeMapView.refreshData();
                    }
                } else {
                    mDOrderNodeMapView.showToast("未知错误");
                }
                return true;
            }
        });
    }


}
