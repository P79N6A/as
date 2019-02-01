package com.yaoguang.company.my.usermanagement.mode.time;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentUserManagementTimeBinding;
import com.yaoguang.company.my.loginconditionconfiguration.timemanagement.model.TimeManagementModeFragment;
import com.yaoguang.company.my.usermanagement.mode.time.adapter.TimeManagementAdapter;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTime;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;

/**
 * Created by zhongjh on 2018/12/6.
 */
public class TimeManagementFragment extends BaseFragmentListConditionDataBind<UserLoginTime, String, TimeManagementAdapter, FragmentUserManagementTimeBinding>
        implements TimeManagementContract.View, Toolbar.OnMenuItemClickListener {

    TimeManagementContract.Presenter mPresenter;

    public static TimeManagementFragment newInstance() {
        return new TimeManagementFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = new TimeManagementPresenter(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_management_time;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new TimeManagementAdapter();
    }

    @Override
    public String getCondition(boolean isRegain) {
        return "";
    }

    @Override
    public void setConditionView(String condition) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startForResult(TimeManagementModeFragment.newInstance(null), 1);
        }
        return super.onMenuItemClick(item);
    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "选择登录时间方案", R.menu.menu_time_management, this);
        }
    }

    @Override
    public void initListener() {
        // 确认
        mDataBinding.btnComit.setOnClickListener(v -> {
            // 返回当前选择的数据
            for (int i = 0; i < mBaseLoadMoreRecyclerAdapter.getList().size(); i++) {
                if (mBaseLoadMoreRecyclerAdapter.getList().get(i).isCheck()) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("userLoginTime", mBaseLoadMoreRecyclerAdapter.getList().get(i));
                    setFragmentResult(RESULT_OK, bundle);
                    pop();
                }
            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            refreshDataAnimation();
        }
    }
}
