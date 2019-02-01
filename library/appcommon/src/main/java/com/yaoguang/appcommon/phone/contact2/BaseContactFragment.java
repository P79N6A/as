package com.yaoguang.appcommon.phone.contact2;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.phone.contact2.adapter.ContactAdapter;
import com.yaoguang.appcommon.phone.contact2.event.RefreshEvent;
import com.yaoguang.appcommon.databinding.FragmentContactBinding;
import com.yaoguang.greendao.entity.common.DriverContactCompany;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author zhongjh
 * @Package com.yaoguang.driver.contact
 * @Description: 我的关联 窗体
 * @date 2018/04/11
 */
public abstract class BaseContactFragment<B extends ViewDataBinding> extends BaseFragmentListConditionDataBind<DriverContactCompany, String, ContactAdapter, FragmentContactBinding> implements ContactContract.View, Toolbar.OnMenuItemClickListener {

    ContactContract.Presenter mPresenter = new ContactPresenter(this);
    String mContactCondition = "";

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new ContactAdapter(BaseContactFragment.this);
    }

    @Override
    public String getCondition(boolean isRegain) {
        return mContactCondition;
    }

    @Override
    public void setConditionView(String condition) {

    }

    @Override
    public void init() {
        mPresenter.subscribe();
        initToolbarNav(mToolbarCommonBinding.toolbar, "我的关联", R.menu.menu_contact, BaseContactFragment.this);

        //注册事件
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onRefreshEvent(RefreshEvent refreshEvent) {
        // 刷新
        refreshData();
    }


    @Override
    public void initListener() {
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }



}
