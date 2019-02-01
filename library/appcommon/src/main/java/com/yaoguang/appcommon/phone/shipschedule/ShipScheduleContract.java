package com.yaoguang.appcommon.phone.shipschedule;

import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;


import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.InfoVoyageTable;
import com.yaoguang.greendao.entity.InfoVoyageTableCondition;

import java.util.List;


/**
 * @author zhongjh
 * @Package com.yaoguang.company.shipschedule
 * @Description: 船期查询 关联接口
 * @date 2018/01/15
 */
public interface ShipScheduleContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<InfoVoyageTableCondition> {

        /**
         * 获取柜型
         */
        void analysisSonos();

        /**
         * 获取关注的公司,只有货主的fragment使用
         */
        void analysisContactCompany();
    }

    interface View extends BaseView, BaseListConditionView<InfoVoyageTable, InfoVoyageTableCondition> {


        /**
         * 赋值柜子
         * @param infoContType 数据源
         */
        void setSonos(BaseResponse<List<InfoContType>> infoContType);

        /**
         * 赋值默认第一个关注的公司名称
         * @param appPublicInfoWrapper 第一个公司
         */
        void setContactCompany(AppPublicInfoWrapper appPublicInfoWrapper);

        /**
         * 时间类型
         */
        void showDataType();

        /**
         * 起始日期
         */
        void showLoadingDate();

        /**
         * 结束日期
         */
        void showEndDateType();
    }
}
