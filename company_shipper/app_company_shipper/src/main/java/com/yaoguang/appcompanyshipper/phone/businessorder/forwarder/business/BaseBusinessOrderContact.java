package com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.business;

import com.mingle.entity.MenuEntity;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.Container;
import com.yaoguang.greendao.entity.company.AppCompanyOrder;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.entity.ProvinceBeans;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务下单的接口
 * Created by zhongjh on 2017/5/26.
 */
public class BaseBusinessOrderContact {

    public interface Presenter<T> extends BasePresenter {

        /**
         * 解析柜型柜量
         */
        void analysisSonos();

        /**
         * 查询条款
         */
        void analysisClause();

        /**
         * 初始化明细数据
         */
        void initDetail();

        /**
         * 添加柜型柜量
         */
        void addContainer();

        /**
         * 添加下單
         * @param t 实体类
         */
        void addCompanyOrder(T t);

        /**
         * 删除订单
         * @param id id
         */
        void cancelOrder(String id);
    }

    public interface View<T> extends BaseView {

        /**
         * 添加柜型柜量
         *
         * @param container 柜型实体类
         */
        void addContainer(Container container);

        /**
         * 添加成功
         *
         * @param message 信息
         */
        void addContainerSuccess(String message);

        /**
         * 添加失败
         *
         * @param message 信息
         */
        void addContainerFail(String message);

        /**
         * 赋值省市区
         *
         * @param value 对象
         */
        void setProvinceBeans(ProvinceBeans value);

        /**
         * 赋值柜型柜量1，2级
         *
         * @param valueLevel1
         */
        void setSonos(List<MenuEntity> valueLevel1);

        void addCompanyOrder();

        /**
         * 显示明细
         *
         * @param value
         * @param isTemp 是否模版形式
         */
        void showDetail(T value, boolean isTemp);

        /**
         * 初始化装卸货的信息
         *
         * @param appPublicInfoWrappers 返回来的装卸货信息
         * @param type                  装/卸类型
         */
        void initLoading(ArrayList<AppPublicInfoWrapper> appPublicInfoWrappers, int type);

        /**
         * 赋值条款数据
         * @param options1Items
         */
        void setClause(List<MenuEntity> options1Items);
    }

}
