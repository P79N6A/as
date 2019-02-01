package com.yaoguang.appcommon.phone.node;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.driver.OrderNodeDataSource;
import com.yaoguang.greendao.entity.driver.DriverOrderMergeNodeStatusWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderMergeNodeWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeList;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/6/15.
 */
public class NodePresenter extends BasePresenterListCondition<String[], DriverOrderMergeNodeWrapper> implements NodeContract.Presenter {

    OrderNodeDataSource mOrderNodeDataSource = new OrderNodeDataSource();
    // 当前状态
    int mOrderStatus;
    // 公司id
    String mClientId;

    private NodeContract.View mView;

    NodePresenter(NodeContract.View view) {
        mView = view;
        // 给这个列表也做别的事
        mOnRecyclerViewCallback = baseResponse -> {

            // 刷新底部
            mView.refreshingFootView(mOrderStatus, mClientId);

            // 隐藏框
            mView.hideProgressDialog();
        };
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void finish(ArrayList<DriverOrderNodeList> driverOrderNodeList) {
        // 处理数据
        Observable<BaseResponse<ArrayList<DriverOrderMergeNodeWrapper>>> data = mOrderNodeDataSource.finishBatch(driverOrderNodeList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        // 调用跟初始化列表一样的ui方法
        showData(data.map(arrayListBaseResponse -> {
            BaseResponse<PageList<DriverOrderMergeNodeWrapper>> baseResponse = new BaseResponse<>();
            baseResponse.setState(arrayListBaseResponse.getState());
            if (baseResponse.getState().equals("200")) {
                baseResponse.setTotalCount(1);
                PageList<DriverOrderMergeNodeWrapper> pageList = new PageList<>();
                pageList.setPageNo(1);
                pageList.setPageSize(arrayListBaseResponse.getResult().size());
                pageList.setTotalCount(1);
                pageList.setResult(arrayListBaseResponse.getResult());

                baseResponse.setResult(pageList);
            }
            return baseResponse;
        }), 0, false);
    }

    @NonNull
    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    protected Observable<BaseResponse<PageList<DriverOrderMergeNodeWrapper>>> initDatas(String[] condition, int pageIndex) {
        // 判断，如果只有一个数量，那就是司机端只传订单id,如果是两个，就是货主端传类型和货柜id
        if (condition.length == 1) {
            Observable<BaseResponse<DriverOrderMergeNodeStatusWrapper>> data = mOrderNodeDataSource.mergeList(condition[0]);
            return data.map(arrayListBaseResponse -> {
                BaseResponse<PageList<DriverOrderMergeNodeWrapper>> baseResponse = new BaseResponse<>();
                baseResponse.setState(arrayListBaseResponse.getState());
                if (baseResponse.getState().equals("200")) {
                    mOrderStatus = arrayListBaseResponse.getResult().getOrderStatus();
                    mClientId = arrayListBaseResponse.getResult().getClientId();

                    baseResponse.setTotalCount(1);
                    PageList<DriverOrderMergeNodeWrapper> pageList = new PageList<>();
                    pageList.setPageNo(1);
                    pageList.setPageSize(arrayListBaseResponse.getResult().getMergeNodes().size());
                    pageList.setTotalCount(1);
                    pageList.setResult(arrayListBaseResponse.getResult().getMergeNodes());

                    baseResponse.setResult(pageList);
                }
                return baseResponse;
            });
        } else {
            Observable<BaseResponse<ArrayList<DriverOrderMergeNodeWrapper>>> data = mOrderNodeDataSource.sonoNews(condition[0], condition[1]);
            return data.map(arrayListBaseResponse -> {
                BaseResponse<PageList<DriverOrderMergeNodeWrapper>> baseResponse = new BaseResponse<>();
                baseResponse.setState(arrayListBaseResponse.getState());
                if (baseResponse.getState().equals("200")) {

                    baseResponse.setTotalCount(1);
                    PageList<DriverOrderMergeNodeWrapper> pageList = new PageList<>();
                    pageList.setPageNo(1);
                    pageList.setPageSize(arrayListBaseResponse.getResult().size());
                    pageList.setTotalCount(1);
                    pageList.setResult(arrayListBaseResponse.getResult());

                    baseResponse.setResult(pageList);
                }
                return baseResponse;
            });
        }


    }

}
