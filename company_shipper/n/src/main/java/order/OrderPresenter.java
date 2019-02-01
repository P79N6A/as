package .order;

import com.yaoguang.lib.base.impl.BaseListConditionPresenter;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppOrderWrapper;
import com.yaoguang.greendao.entity.OrderCondition;

import io.reactivex.Observable;

/**
 * @author zhongjh
 * @Package .order
 * @Description:  控制层
 * @date 2018/01/05
 */
public class OrderPresenter extends BaseListConditionPresenter<OrderCondition, AppOrderWrapper> implements OrderContract.Presenter {

    private OrderContract.View mView;

    OrderPresenter(OrderContract.View view) {
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
    protected Observable<BaseResponse<PageList<AppOrderWrapper>>> initDatas(OrderCondition condition, int pageIndex) {
        return null;
    }
}
