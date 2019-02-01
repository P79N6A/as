package .contactsearchfragment;

import com.yaoguang.common.base.impl.BaseListConditionPresenter;
import com.yaoguang.common.base.interfaces.BaseListConditionView;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.greendao.entity.AppContactSearchFragmentWrapper;
import com.yaoguang.greendao.entity.ContactSearchFragmentCondition;

import io.reactivex.Observable;

/**
 * @author zhongjh
 * @Package .contactsearchfragment
 * @Description: 关注查找 控制层
 * @date 2018/04/12
 */
public class ContactSearchFragmentPresenter extends BaseListConditionPresenter<ContactSearchFragmentCondition, AppContactSearchFragmentWrapper> implements ContactSearchFragmentContract.Presenter {

    private ContactSearchFragmentContract.View mView;

    ContactSearchFragmentPresenter(ContactSearchFragmentContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    protected BaseListConditionView getBaseListView() {
        return mView;
    }

    @Override
    protected Observable<BaseResponse<PageList<AppContactSearchFragmentWrapper>>> initDatas(ContactSearchFragmentCondition condition, int pageIndex) {
        return null;
    }
}
