package com.yaoguang.appcommon.publicsearch.loadingandunloading;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

import io.reactivex.Observable;

/**
 * 装卸货
 * Created by zhongjh on 2017/8/11.
 */
public class LoadingAndUnloadingContact {

    public interface LoadingAndUnloadingView extends BaseView {

        /**
         * 搜索框显示加载框
         */
        void showProgress();

        /**
         * 搜索框隐藏加载框
         */
        void hideProgress();

        void startRefresh();
    }

    public interface LoadingAndUnloadingPresenter extends BasePresenter {

        /**
         * 删除地址
         *
         * @param ids    地址，可逗号分隔
         * @param codeId 托运人id
         */
        void deleteAddress(String ids, String codeId);
    }

    public interface LoadingAndUnloadingInteractor {

        //删除地址
        Observable<BaseResponse<String>> deleteAddress(String ids, String codeId);


    }
}
