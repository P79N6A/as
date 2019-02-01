package .clientcondition;

import com.yaoguang.common.base.impl.BaseListConditionPresenter;
import com.yaoguang.common.base.interfaces.BaseListConditionView;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.common.net.bean.PageList;
import com.yaoguang.greendao.entity.AppclientConditionWrapper;
import com.yaoguang.greendao.entity.clientConditionCondition;

import io.reactivex.Observable;

/**
 * @author zhongjh
 * @Package .clientcondition
 * @Description: 委托人条件筛选 控制层
 * @date 2018/04/09
 */
public class clientConditionPresenter extends BaseListConditionPresenter<clientConditionCondition, AppclientConditionWrapper> implements clientConditionContract.Presenter {

    private clientConditionContract.View mView;

    clientConditionPresenter(clientConditionContract.View view) {
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
    protected Observable<BaseResponse<PageList<AppclientConditionWrapper>>> initDatas(clientConditionCondition condition, int pageIndex) {
        return null;
    }
}
