package com.yaoguang.company.businessorder2.common.businessmain.business;

import com.mingle.entity.MenuEntity;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.company.UpdateBusinessOrderModel;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.List;

/**
 * Created by zhongjh on 2018/11/15.
 */

public class BaseBusinessOrderContact {

    public interface Presenter<T,AT> extends BasePresenter {

        /**
         * 查询条款
         */
        void analysisClause();

        /**
         * 初始化收款方式
         */
        void searchPaymentMethod();

        /**
         * 获取柜型
         */
        void getConts();

        /**
         * 添加数据
         */
        void addOrder(T t, boolean isCheck);

        /**
         * 修改数据
         */
        void updateOrder(AT at, boolean b);

        /**
         * 删除装货地址
         * @param id
         * @param billsId
         */
        void deleteLoadAddress(String id,String billsId);

        /**
         * 删卸货地址
         * @param id
         * @param billsId
         */
        void deleteUnLoadAddress(String id,String billsId);
    }

    public interface View extends BaseView {

        /**
         * 赋值条款数据
         * @param options1Items 数据源
         */
        void setClause(List<String> options1Items);

        /**
         * 初始化收款方式
         * @param options1Items 数据源
         */
        void searchPaymentMethod(List<String> options1Items);

        /**
         * 初始化柜型
         * @param response 柜子
         */
        void setSonos(BaseResponse<List<InfoContType>> response);

        /**
         * 保存订单后，做相应动作
         */
        void showUpdateFowardOrder(UpdateBusinessOrderModel result);

        /**
         * 删除装货地址
         */
        void deleteLoadAddress();

        /**
         * 删除卸货地址
         */
        void deleteUnLoadAddress();

        /**
         * 删除旧数据的数据源，确保服务器正常
         * @param id
         * @param billsId
         */
        void deleteLoadAddressData(String id, String billsId);

        /**
         * 删除旧数据的数据源，确保服务器正常
         * @param id
         * @param billsId
         */
        void deleteUnLoadAddressData(String id, String billsId);
    }


}
