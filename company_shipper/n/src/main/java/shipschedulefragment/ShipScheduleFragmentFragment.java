package .shipschedulefragment;

import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentShipScheduleFragmentBinding;
import com.yaoguang.company.shipschedulefragment.adapter.ShipScheduleFragmentAdapter;
import com.yaoguang.greendao.entity.AppShipScheduleFragmentWrapper;
import com.yaoguang.greendao.entity.ShipScheduleFragmentCondition;

/**
 * @author zhongjh
 * @Package .shipschedulefragment
 * @Description: 船期查询 窗体
 * @date 2018/01/15
 */
public class ShipScheduleFragmentFragment extends BaseFragmentListConditionDataBind<AppShipScheduleFragmentWrapper, ShipScheduleFragmentCondition, FragmentShipScheduleFragmentBinding> implements ShipScheduleFragmentContract.View{

    ShipScheduleFragmentContract.Presenter mPresenter = new ShipScheduleFragmentPresenter(this);
    ShipScheduleFragmentCondition mShipScheduleFragmentCondition = new ShipScheduleFragmentCondition();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ship_schedule_fragment;
    }

    @Override
    public void init() {
        mPresenter.subscribe();
        if (mViewBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "船期查询", R.menu.shipschedule, ShipScheduleFragmentFragment.this);
        }
        //绑定数据
        mViewBinding.setShipScheduleFragmentCondition(mShipScheduleFragmentCondition);
    }

    @Override
    public void initListener() {

    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new ShipScheduleFragmentAdapter();
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public ShipScheduleFragmentCondition getCondition(boolean isRegain) {
        return mShipScheduleFragmentCondition;
    }

}
