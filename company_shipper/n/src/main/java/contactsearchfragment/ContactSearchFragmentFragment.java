package .contactsearchfragment;

import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.common.base.BaseFragmentListConditionDataBind;
import com.yaoguang.common.base.interfaces.BasePresenterListCondition;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentContactSearchFragmentBinding;
import com.yaoguang.company.contactsearchfragment.adapter.ContactSearchFragmentAdapter;
import com.yaoguang.greendao.entity.AppContactSearchFragmentWrapper;
import com.yaoguang.greendao.entity.ContactSearchFragmentCondition;

/**
 * @author zhongjh
 * @Package .contactsearchfragment
 * @Description: 关注查找 窗体
 * @date 2018/04/12
 */
public class ContactSearchFragmentFragment extends BaseFragmentListConditionDataBind<AppContactSearchFragmentWrapper, ContactSearchFragmentCondition, FragmentContactSearchFragmentBinding> implements ContactSearchFragmentContract.View{

    ContactSearchFragmentContract.Presenter mPresenter = new ContactSearchFragmentPresenter(this);
    ContactSearchFragmentCondition mContactSearchFragmentCondition = new ContactSearchFragmentCondition();

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_contact_search_fragment;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new ContactSearchFragmentAdapter();
    }

    @Override
    public ContactSearchFragmentCondition getCondition(boolean isRegain) {
        if (isRegain) {
            if (mViewBinding != null) {
                //mContactSearchFragmentCondition.setXXX(mViewBinding.tvValue.getText().toString());
            }
        }
        return mContactSearchFragmentCondition;
    }

    @Override
    public void init() {
        mPresenter.subscribe();
        if (mViewBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "关注查找", R.menu.shipschedule, ContactSearchFragmentFragment.this);
        }
        //绑定数据
        mViewBinding.setContactSearchFragmentCondition(mContactSearchFragmentCondition);
    }

    @Override
    public void initListener() {

    }

}
