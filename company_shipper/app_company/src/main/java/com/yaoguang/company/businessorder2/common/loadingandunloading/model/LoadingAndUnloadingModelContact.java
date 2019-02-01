package com.yaoguang.company.businessorder2.common.loadingandunloading.model;

import com.yaoguang.greendao.entity.company.InfoClientPlace;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * Created by zhongjh on 2018/11/6.
 */

public class LoadingAndUnloadingModelContact {

    public interface Presenter extends BasePresenter {

        /**
         * 添加地址
         * @param infoClientPlace 实体
         */
        void addLoadPlace(InfoClientPlace infoClientPlace);
    }

    public interface View extends BaseView {
        /**
         * 添加地址成功
         */
        void addLoadPlace();
    }

}
