package com.yaoguang.driver.phone.order.nodeEdit.nodeEditPort;

import android.support.annotation.NonNull;

import com.yaoguang.datasource.driver.DriverDataSource;
import com.yaoguang.greendao.entity.common.InfoDock;
import com.yaoguang.lib.base.impl.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * 控制器
 * Created by zhongjh on 2018/7/9.
 */
public class NodeEditPortPresenter extends BasePresenterListCondition<HashMap<String, String>, InfoDock> implements NodeEditPortContract.Presenter {

    DriverDataSource mDriverDataSource = new DriverDataSource();

    private NodeEditPortContract.View mView;


    NodeEditPortPresenter(NodeEditPortContract.View view) {
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
    protected Observable<BaseResponse<PageList<InfoDock>>> initDatas(HashMap<String, String> map, int pageIndex) {
        return mDriverDataSource.infoDocks(map, pageIndex);
    }

}
