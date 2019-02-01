package .contactsearch;

import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.common.base.BaseFragmentListConditionDataBind;
import com.yaoguang.common.base.interfaces.BasePresenterListCondition;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentContactSearchBinding;
import com.yaoguang.company.contactsearch.adapter.ContactSearchAdapter;
import com.yaoguang.greendao.entity.AppContactSearchWrapper;
import com.yaoguang.greendao.entity.ContactSearchCondition;

/**
 * @author zhongjh
 * @Package .contactsearch
 * @Description: 关注查找 窗体
 * @date 2018/04/12
 */
public class ContactSearchFragment extends BaseFragmentListConditionDataBind<AppContactSearchWrapper, ContactSearchCondition, FragmentContactSearchBinding> implements ContactSearchContract.View{

    ContactSearchContract.Presenter mPresenter = new ContactSearchPresenter(this);
    ContactSearchCondition mContactSearchCondition = new ContactSearchCondition();

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_contact_search;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new ContactSearchAdapter();
    }

    @Override
    public ContactSearchCondition getCondition(boolean isRegain) {
        if (isRegain) {
            if (mViewBinding != null) {
                //mContactSearchCondition.setXXX(mViewBinding.tvValue.getText().toString());
            }
        }
        return mContactSearchCondition;
    }

    @Override
    public void init() {
        mPresenter.subscribe();
        if (mViewBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "关注查找", R.menu.shipschedule, ContactSearchFragment.this);
        }
        //绑定数据
        mViewBinding.setContactSearchCondition(mContactSearchCondition);
    }

    @Override
    public void initListener() {

    }

}
