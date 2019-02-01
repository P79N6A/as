package com.yaoguang.company.businessorder2.common.loadingandunloading.list;

import com.yaoguang.greendao.entity.company.InfoClientPlace;
import com.yaoguang.greendao.entity.company.LoadingAndUnloadingCondition;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;

/**
 * Created by zhongjh on 2018/10/24.
 */

public class LoadingAndUnloadingListContact {

    public interface Presenter extends BasePresenterListCondition<LoadingAndUnloadingCondition> {

        /**
         * 删除
         * @param id id
         * @param codeId 托运人id
         */
        void batchDeletePlace(String id, String codeId);
    }

    public interface View extends BaseView, BaseListConditionView<InfoClientPlace, LoadingAndUnloadingCondition> {

    }

}
