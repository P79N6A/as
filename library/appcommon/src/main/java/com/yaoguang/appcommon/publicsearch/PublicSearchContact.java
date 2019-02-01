package com.yaoguang.appcommon.publicsearch;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.PublicSearchData;
import com.yaoguang.interfaces.BaseListView;
import com.yaoguang.lib.base.interfaces.BasePresenter;

import java.util.List;

import io.reactivex.Observable;

/**
 * 公共的搜索框的接口
 * Created by zhongjh on 2017/6/2.
 */
public class PublicSearchContact {

    public interface CPublicSearchInteractor {

        /**
         * 查询数据库的缓存列表
         *
         * @param query 查找内容
         * @param type  类型
         */
        Observable<List<PublicSearchData>> search(String query, int type);

        /**
         * 根据查询的内容，添加进数据库
         *
         * @param body 查找内容
         * @param type 类型
         */
        Observable<Long> addQuery(String body, int type);

        /**
         * 根据查询的内容，返回数据源
         *
         * @param body     查找内容
         * @param type     查询类型
         * @param codeId   托运人编号
         * @param areaName 区域名称
         */
        Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> initAppPublicInfoWrappers(String body, String areaName,  int type, int pageIndex, String codeId);

    }

    public interface CPublicSearchPresenter extends BasePresenter {

        /**
         * 查找
         *
         * @param query 查找文本
         */
        void onSearchAction(String query);

        /**
         * 添加查找的内容进缓存
         *
         * @param body
         */
        void addQuery(String body);

        /**
         * 刷新数据
         *
         * @param name 搜索内容
         */
        void refreshData(String name);

        /**
         * 刷新列表
         *
         * @param name     搜索条件(可空)
         * @param dataSize 列表数据源长度
         * @param isNext   是否加载下一页的
         */
        void getData(String name,  int dataSize, boolean isNext);

        /**
         * 加载数据
         *
         * @param name 搜索内容
         * @param size 当前显示的内容长度
         */
        void loadMoreData(String name, int size);
    }

    public interface CPublicSearchView extends BaseListView {





        /**
         * @return 获取搜索的名称
         */
        String getName();



        /**
         * 显示错误的信息
         *
         * @param strMessage 错误信息
         */
        void fail(String strMessage);

        /**
         * 填充搜索框下拉列表的数据
         *
         * @param publicSearchDatas 数据源
         */
        void swapSuggestions(List<PublicSearchData> publicSearchDatas);

        /**
         * 搜索框显示加载框
         */
        void showProgress();

        /**
         * 搜索框隐藏加载框
         */
        void hideProgress();
    }

}
