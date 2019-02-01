package com.yaoguang.interactor.company.pricetruck;

import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppPriceTruckWrapper;
import com.yaoguang.greendao.entity.PriceTruckCondition;
import com.yaoguang.interfaces.BaseListView;
import com.yaoguang.lib.base.interfaces.BasePresenter;

import java.util.List;

import io.reactivex.Observable;

/**
 * 拖车报价查询
 * Created by zhongjh on 2017/6/12.
 */
public class PriceTruckContact {

    public interface PriceTruckInteractor {

        /**
         * 根据查询的内容，返回数据源
         *
         * @param priceTruckCondition 查询条件
         * @param pageIndex           页数
         * @return 返回数据源
         */
        Observable<BaseResponse<PageList<AppPriceTruckWrapper>>> initDatas(PriceTruckCondition priceTruckCondition, int pageIndex);

    }

    public interface PriceTruckPresenter extends BasePresenter {

        void analysisSonos();

        /**
         * 刷新数据
         *
         * @param condition 条件
         */
        void refreshData(PriceTruckCondition condition);

        /**
         * 刷新列表
         *
         * @param condition 条件
         * @param dataSize  列表数据源长度
         * @param isNext    是否加载下一页的
         */
        void getData(PriceTruckCondition condition, int dataSize, boolean isNext);

        /**
         * 加载数据
         *
         * @param condition 条件
         * @param size      当前显示的内容长度
         */
        void loadMoreData(PriceTruckCondition condition, int size);
    }

    public interface PriceTruckView extends BaseListView<AppPriceTruckWrapper>,BaseView
    {

        /**
         * 获取查询条件
         *
         * @param isRegain 是否重新获取查询条件
         * @return 查询对象
         */
        PriceTruckCondition getCondition(boolean isRegain);

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

        void setSonos(List<String> values);
    }

}
