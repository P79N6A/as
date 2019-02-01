package com.yaoguang.interactor.common.my.companyoinfo;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

import io.reactivex.Observable;

/**
 * 商户管理
 * Created by zhongjh on 2017/7/13.
 */
public class CompanyInfoContact {

    public interface CompanyInfoInteractor<T> {
        /**
         * 获取当前商户信息
         *
         * @return 实体类
         */
        Observable<BaseResponse<T>> getInfo();

        /**
         * 修改电话
         *
         * @param userApply 实体
         * @param url       值
         * @return 服务器信息
         */
        Observable<BaseResponse<String>> modifyPhoto(T userApply, String url);

        /**
         * 修改Log
         *
         * @param userApply 实体
         * @param url       值
         * @return 服务器信息
         */
        Observable<BaseResponse<String>> modifyLog(T userApply, String url);

        /**
         * 修改商户备注
         *
         * @param model 实体
         * @param value 值
         * @return 服务器信息
         */
        Observable<BaseResponse<String>> modifyShopDetail(T model, String value);
    }


    public interface CompanyInfoPresenter extends BasePresenter {

        /**
         * 初始化数据
         */
        void initData();

        /**
         * 修改电话
         *
         * @param url       值
         */
        void modifyPhoto(String url);

        /**
         * 修改Log
         *
         * @param url       值
         */
        void modifyLog(String url);

        /**
         * 修改商户备注
         *
         * @param value 值
         */
        void modifyRemark(String value);
    }

    public interface CompanyInfoView<T> extends BaseView {
        /**
         * 显示数据
         *
         * @param info
         */
        void showData(T info);
    }

}
