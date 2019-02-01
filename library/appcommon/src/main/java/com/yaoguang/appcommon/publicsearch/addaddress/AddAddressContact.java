package com.yaoguang.appcommon.publicsearch.addaddress;

import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.company.InfoClientPlace;
import com.yaoguang.lib.entity.ProvinceBeans;
import com.yaoguang.lib.base.interfaces.BasePresenter;

import io.reactivex.Observable;

/**
 * 添加地址
 * Created by zhongjh on 2017/6/12.
 */
public class AddAddressContact {

    public interface CAddAddressInteractor {

        /**
         * 添加地址
         *
         * @param infoClientPlace 地址信息
         * @return
         */
        Observable<BaseResponse<String>> addLoadPlace(InfoClientPlace infoClientPlace);
    }

    public interface CAddAddressPresenter extends BasePresenter {

        void analysisProvinceBeansJson();

        /**
         * @param infoClientPlace 地址实体类
         */
        void addLoadPlace(InfoClientPlace infoClientPlace);

        /**
         * 编辑地址
         *
         * @param infoClientPlace 地址实体类
         */
        void updateLoadPlace(InfoClientPlace infoClientPlace);
    }

    public interface CAddAddressView extends BaseView{

        void addSuccess(String result);

        void addFail(String result);

        void setProvinceBeans(ProvinceBeans value);
    }

}
