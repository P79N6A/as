package com.yaoguang.appcommon.search;

import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.interfaces.BaseListView;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;

import io.reactivex.Observable;

/**
 * 新版的搜索框
 * Created by zhongjh on 2017/8/24.
 */
public class SearchContact {

    public interface SearchView extends BaseView, BaseListConditionView<AppPublicInfoWrapper, String> {
    }

    public interface SearchPresenter extends BasePresenterListCondition<String> {

        Observable<BaseResponse<PageList<AppPublicInfoWrapper>>> getSearchObservable(String condition);

        void startSearch(String query);
    }


}
