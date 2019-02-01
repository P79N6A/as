package .clientcondition;

import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.common.base.BaseFragmentListConditionDataBind;
import com.yaoguang.common.base.interfaces.BasePresenterListCondition;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentclientConditionBinding;
import com.yaoguang.company.clientcondition.adapter.clientConditionAdapter;
import com.yaoguang.greendao.entity.AppclientConditionWrapper;
import com.yaoguang.greendao.entity.clientConditionCondition;

/**
 * @author zhongjh
 * @Package .clientcondition
 * @Description: 委托人条件筛选 窗体
 * @date 2018/04/09
 */
public class clientConditionFragment extends BaseFragmentListConditionDataBind<AppclientConditionWrapper, clientConditionCondition, FragmentclientConditionBinding> implements clientConditionContract.View{

    clientConditionContract.Presenter mPresenter = new clientConditionPresenter(this);
    clientConditionCondition mclientConditionCondition = new clientConditionCondition();

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_client_condition;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new clientConditionAdapter();
    }

    @Override
    public clientConditionCondition getCondition(boolean isRegain) {
        if (isRegain) {
            if (mViewBinding != null) {
                //mclientConditionCondition.setXXX(mViewBinding.tvValue.getText().toString());
            }
        }
        return mclientConditionCondition;
    }

    @Override
    public void init() {
        mPresenter.subscribe();
        if (mViewBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "委托人条件筛选", R.menu.shipschedule, clientConditionFragment.this);
        }
        //绑定数据
        mViewBinding.setclientConditionCondition(mclientConditionCondition);
    }

    @Override
    public void initListener() {

    }

}
