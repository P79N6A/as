package com.yaoguang.appcommon.phone.order.orderdetailnode;

import android.support.annotation.NonNull;

import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.LatLons;
import com.yaoguang.greendao.entity.NodesBean;
import com.yaoguang.greendao.entity.OrderDetailNodeList;
import com.yaoguang.lib.base.interfaces.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by 韦理英 on 2017/6/26 0026.
 */

public class OrderDetailNodeContract {
    public interface View extends BaseView {
        void recyclerViewShowError(String strMessage);

        void recyclerViewShowEmpty();

        void showList(List<NodesBean> mDriverOrderNode);

        void showLocalHistoryList(List<LatLons> latLonsBaseResponse);
    }

    public interface Presenter extends BasePresenter {
        void activityData(String driverOrderId, String orderSn,  String loadingType,String pcSonoId);

        void getLatLonHistory(@NonNull String orderSn,int loadingTypeInt);
    }

    public interface Interactor {
        Observable<BaseResponse<OrderDetailNodeList>> requestData(String driverOrderId, String orderSn);

        Observable<BaseResponse<List<LatLons>>> requestLatLonHistory(String orderSn);
    }
}
