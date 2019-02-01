package com.yaoguang.shipper.sonos;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppOwnerSonoCondition;
import com.yaoguang.greendao.entity.AppOwnerSonoWrapper;
import com.yaoguang.interfaces.BaseListView;
import com.yaoguang.lib.base.interfaces.BasePresenter;

import io.reactivex.Observable;

/**
 * 货柜查询
 * Created by zhongjh on 2017/6/12.
 */
public class SonosContact {

    public interface SonosInteractor {

        /**
         * 根据查询的内容，返回数据源
         *
         * @param condition 查询条件
         * @param pageIndex 页数
         * @return 返回数据源
         */
        Observable<BaseResponse<PageList<AppOwnerSonoWrapper>>> initDatas(AppOwnerSonoCondition condition, int pageIndex);


    }

    public interface SonosPresenter extends BasePresenter {

        /**
         * 刷新数据
         *
         * @param condition 条件
         */
        void refreshData(AppOwnerSonoCondition condition);

        /**
         * 刷新列表
         *
         * @param condition 条件
         * @param dataSize  列表数据源长度
         * @param isNext    是否加载下一页的
         */
        void getData(AppOwnerSonoCondition condition, int dataSize, boolean isNext);

        /**
         * 加载数据
         *
         * @param condition 条件
         * @param size      当前显示的内容长度
         */
        void loadMoreData(AppOwnerSonoCondition condition, int size);
    }

    public interface SonosView extends BaseListView<AppOwnerSonoWrapper> {

        /**
         * 获取查询条件
         *
         * @param isRegain 是否重新获取查询条件
         * @return 查询对象
         */
        AppOwnerSonoCondition getCondition(boolean isRegain);

        /**
         * 每次点击筛选的时候，设置查询条件缓存
         */
        void setConditionView();

        /**
         * 显示错误的信息
         *
         * @param strMessage 错误信息
         */
        void fail(String strMessage);

        void showProgress();

        void hideProgress();

        /**
         * 显示运单号的等待效果
         */
        void showProgressMBlNos();

        /**
         * 隐藏运单号的等待效果
         */
        void hideProgressMBlNos();

        /**
         * 显示柜号的等待效果
         */
        void showProgressContNos();

        /**
         * 隐藏柜号的等待效果
         */
        void hideProgressContNos();
    }

}
