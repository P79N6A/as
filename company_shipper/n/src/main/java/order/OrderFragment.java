package .order;

import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentOrderBinding;
import com.yaoguang.company.order.adapter.OrderAdapter;
import com.yaoguang.greendao.entity.AppOrderWrapper;
import com.yaoguang.greendao.entity.OrderCondition;

/**
 * @author zhongjh
 * @Package .order
 * @Description:  窗体
 * @date 2018/01/05
 */
public class OrderFragment extends BaseFragmentListConditionDataBind<AppOrderWrapper, OrderCondition, FragmentOrderBinding> implements OrderContract.View{

    OrderContract.Presenter mPresenter = new OrderPresenter(this);
    OrderCondition mOrderCondition = new OrderCondition();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void init() {
        mPresenter.subscribe();
        if (mViewBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "", R.menu.shipschedule, OrderFragment.this);
        }
        //绑定数据
        mViewBinding.setOrderCondition(mOrderCondition);
    }

    @Override
    public void initListener() {

    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new OrderAdapter();
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public OrderCondition getCondition(boolean isRegain) {
        return mOrderCondition;
    }

}
