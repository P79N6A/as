package .contactsearch;

import com.yaoguang.common.base.interfaces.BaseListConditionView;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.base.interfaces.BasePresenterListCondition;


import com.yaoguang.common.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.AppContactSearchWrapper;
import com.yaoguang.greendao.entity.ContactSearchCondition;



/**
 * @author zhongjh
 * @Package .contactsearch
 * @Description: 关注查找 关联接口
 * @date 2018/04/12
 */
public interface ContactSearchContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<ContactSearchCondition> {

    }

    interface View extends BaseView, BaseListConditionView<AppContactSearchWrapper, ContactSearchCondition> {


    }
}
