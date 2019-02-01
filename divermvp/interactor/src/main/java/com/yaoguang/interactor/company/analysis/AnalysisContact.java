package com.yaoguang.interactor.company.analysis;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppPriceAnalysisWrapper;
import com.yaoguang.greendao.entity.PriceAnalysisCondition;
import com.yaoguang.interfaces.BaseListView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * 货代报价查询
 * Created by zhongjh on 2017/6/12.
 */
public class AnalysisContact {

    public interface AnalysisInteractor {

        /**
         * 根据查询的内容，返回数据源
         *
         * @param priceAnalysisCondition 查询条件
         * @param pageIndex              页数
         * @return 返回数据源
         */
        Observable<BaseResponse<PageList<AppPriceAnalysisWrapper>>> initDatas(PriceAnalysisCondition priceAnalysisCondition, int pageIndex);

        /**
         * @return 返回柜型柜量
         */
        Observable<BaseResponse<List<String>>> analysisSonos();
    }

    public interface AnalysisPresenter extends BasePresenter {

        void analysisSonos();

        /**
         * 刷新数据
         *
         * @param condition 条件
         */
        void refreshData(PriceAnalysisCondition condition);

        /**
         * 刷新列表
         *
         * @param condition 条件
         * @param dataSize  列表数据源长度
         * @param isNext    是否加载下一页的
         */
        void getData(PriceAnalysisCondition condition, int dataSize, boolean isNext);

        /**
         * 加载数据
         *
         * @param condition 条件
         * @param size      当前显示的内容长度
         */
        void loadMoreData(PriceAnalysisCondition condition, int size);
    }

    public interface AnalysisView extends BaseView, BaseListView<AppPriceAnalysisWrapper> {

        /**
         * 获取查询条件
         *
         * @param isRegain 是否重新获取查询条件
         * @return 查询对象
         */
        PriceAnalysisCondition getCondition(boolean isRegain);

        /**
         * 每次打开条件框的时候，都会读取缓存
         */
        void setConditionView();

        /**
         * 赋值柜子信息
         *
         * @param values 值
         */
        void setSonos(List<String> values);
    }

}
