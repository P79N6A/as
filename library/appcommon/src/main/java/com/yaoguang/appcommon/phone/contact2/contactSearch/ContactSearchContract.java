package com.yaoguang.appcommon.phone.contact2.contactSearch;
import com.yaoguang.greendao.entity.common.UserOfficeWrapper;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.net.bean.PageList;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.contactsearchfragment
 * @Description: 关注查找 关联接口
 * @date 2018/04/12
 */
public interface ContactSearchContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<String> {

    }

    interface View extends BaseView, BaseListConditionView<UserOfficeWrapper, String> {

        // 判断是不是只有一条数据，如果只有一条，就直接跳转
        void isOneStartFragment(PageList<UserOfficeWrapper> result);
    }
}
