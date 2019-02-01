package .shipschedulefragment;

import com.yaoguang.lib.base.impl.BaseListConditionPresenter;
import com.yaoguang.lib.base.interfaces.BaseListConditionView;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.greendao.entity.AppShipScheduleFragmentWrapper;
import com.yaoguang.greendao.entity.ShipScheduleFragmentCondition;

import io.reactivex.Observable;

/**
 * @author zhongjh
 * @Package .shipschedulefragment
 * @Description: 船期查询 控制层
 * @date 2018/01/15
 */
public class ShipScheduleFragmentPresenter extends BaseListConditionPresenter<ShipScheduleFragmentCondition, AppShipScheduleFragmentWrapper> implements ShipScheduleFragmentContract.Presenter {

    private ShipScheduleFragmentContract.View mView;

    ShipScheduleFragmentPresenter(ShipScheduleFragmentContract.View view) {
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
    protected Observable<BaseResponse<PageList<AppShipScheduleFragmentWrapper>>> initDatas(ShipScheduleFragmentCondition condition, int pageIndex) {
        return null;
    }
}
