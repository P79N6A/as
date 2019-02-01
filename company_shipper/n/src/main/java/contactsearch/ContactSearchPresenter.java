package .contactsearch;

import com.yaoguang.common.base.impl.BaseListConditionPresenter;
import com.yaoguang.common.base.interfaces.BaseListConditionView;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.greendao.entity.AppContactSearchWrapper;
import com.yaoguang.greendao.entity.ContactSearchCondition;

import io.reactivex.Observable;

/**
 * @author zhongjh
 * @Package .contactsearch
 * @Description: 关注查找 控制层
 * @date 2018/04/12
 */
public class ContactSearchPresenter extends BaseListConditionPresenter<ContactSearchCondition, AppContactSearchWrapper> implements ContactSearchContract.Presenter {

    private ContactSearchContract.View mView;

    ContactSearchPresenter(ContactSearchContract.View view) {
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
    protected Observable<BaseResponse<PageList<AppContactSearchWrapper>>> initDatas(ContactSearchCondition condition, int pageIndex) {
        return null;
    }
}
