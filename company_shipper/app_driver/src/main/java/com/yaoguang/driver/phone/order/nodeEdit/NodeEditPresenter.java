package com.yaoguang.driver.phone.order.nodeEdit;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.driver.DriverDataSource;
import com.yaoguang.datasource.driver.OrderNodeDataSource;
import com.yaoguang.greendao.entity.driver.DriverNodeAddrVo;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeWrapper;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 节点编辑控制器
 * Created by zhongjh on 2018/6/7.
 */
public class NodeEditPresenter extends BasePresenterListCondition<String, DriverOrderNodeWrapper> implements NodeEditContract.Presenter {

    OrderNodeDataSource mOrderNodeDataSource = new OrderNodeDataSource();
    DriverDataSource mDriverDataSource = new DriverDataSource();

    private NodeEditContract.View mView;


    NodeEditPresenter(NodeEditContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    protected Observable<BaseResponse<PageList<DriverOrderNodeWrapper>>> initDatas(String condition, int pageIndex) {
        // 处理数据
        Observable<BaseResponse<ArrayList<DriverOrderNodeWrapper>>> data = mOrderNodeDataSource.listEdit(condition);
        return data.map(driverOrderNodeProgressWrapperBaseResponse -> {
            BaseResponse<PageList<DriverOrderNodeWrapper>> baseResponse = new BaseResponse<>();
            baseResponse.setState(driverOrderNodeProgressWrapperBaseResponse.getState());
            if (baseResponse.getState().equals("200")) {
                baseResponse.setTotalCount(1);
                PageList<DriverOrderNodeWrapper> pageList = new PageList<>();
                pageList.setPageNo(1);
                pageList.setPageSize(driverOrderNodeProgressWrapperBaseResponse.getResult().size());
                pageList.setTotalCount(1);
                pageList.setResult(driverOrderNodeProgressWrapperBaseResponse.getResult());
                baseResponse.setResult(pageList);
            }
            return baseResponse;
        });
    }

    @Override
    public void exchange(String nodeId, String switchToNodeId) {
        mOrderNodeDataSource.nodeSwitch(nodeId, switchToNodeId)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.exchangeSuccess(response.getResult());
                    }
                });
    }

    @Override
    public void update(DriverNodeAddrVo driverNodeAddrVo) {
        mOrderNodeDataSource.update(driverNodeAddrVo)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.detailubmitSuccess(response.getResult());
                    }
                });

    }

    @Override
    public void updatePort(String nodeId, String portName) {
        mDriverDataSource.updatePort(nodeId, portName)
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, mView) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mView.detailubmitSuccess(response.getResult());
                    }
                });
    }

}
