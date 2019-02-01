package com.yaoguang.appcommon.phone.order.detail;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

import io.reactivex.Observable;

/**
 * Created by zhongjh on 2017/6/20.
 */
public class DetailContact {

    public interface DetailPresenter extends BasePresenter {

        //加载所有数据
        void loadData();
    }

    public interface DetailView<T> extends BaseView {

        /**
         * 将数据源赋值到View
         * @param data 数据源
         */
        void initDataView(T data);

    }


}
