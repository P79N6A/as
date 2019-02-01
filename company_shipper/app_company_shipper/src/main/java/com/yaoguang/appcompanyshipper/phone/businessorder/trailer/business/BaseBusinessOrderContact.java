package com.yaoguang.appcompanyshipper.phone.businessorder.trailer.business;

import android.content.Context;

import com.mingle.entity.MenuEntity;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.company.AppTruckOrder;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.lib.entity.ProvinceBeans;

import java.util.List;

import io.reactivex.Observable;

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
         * 提交预订单
         */
        void editTruckOrder(T t);

        /**
         * 删除预订单
         * @param id 订单id
         */
        void cancelOrder(String id);
    }

    public interface View<T> extends BaseView {

        /**
         * 预订单添加成功
         *
         * @param message 信息
         */
        void addContainerSuccess(String message);

        /**
         * 预订单添加失败
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
         * 赋值条款数据
         * @param options1Items
         */
        void setClause(List<MenuEntity> options1Items);
    }

}
