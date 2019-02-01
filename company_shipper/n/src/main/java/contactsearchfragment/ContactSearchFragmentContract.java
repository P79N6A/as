package .contactsearchfragment;

import com.yaoguang.common.base.interfaces.BaseListConditionView;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.base.interfaces.BasePresenterListCondition;


import com.yaoguang.common.base.interfaces.BaseView;
import com.yaoguang.greendao.entity.AppContactSearchFragmentWrapper;
import com.yaoguang.greendao.entity.ContactSearchFragmentCondition;



/**
 * @author zhongjh
 * @Package .contactsearchfragment
 * @Description: 关注查找 关联接口
 * @date 2018/04/12
 */
public interface ContactSearchFragmentContract {

    interface Presenter extends BasePresenter, BasePresenterListCondition<ContactSearchFragmentCondition> {

    }

    interface View extends BaseView, BaseListConditionView<AppContactSearchFragmentWrapper, ContactSearchFragmentCondition> {


    }
}
