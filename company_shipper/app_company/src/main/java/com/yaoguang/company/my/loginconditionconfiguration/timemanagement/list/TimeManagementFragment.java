package com.yaoguang.company.my.loginconditionconfiguration.timemanagement.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentTimeManagementBinding;
import com.yaoguang.company.my.loginconditionconfiguration.timemanagement.list.adapter.TimeManagementAdapter;
import com.yaoguang.company.my.loginconditionconfiguration.timemanagement.model.TimeManagementModeFragment;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTime;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;

/**
 * Created by zhongjh on 2018/12/6.
 */
public class TimeManagementFragment extends BaseFragmentListConditionDataBind<UserLoginTime, String, TimeManagementAdapter, FragmentTimeManagementBinding>
        implements TimeManagementContract.View, Toolbar.OnMenuItemClickListener {

    TimeManagementContract.Presenter mPresenter;
    DialogHelper mDialogHelper;

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
        return R.layout.fragment_time_management;
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
            initToolbarNav(mToolbarCommonBinding.toolbar, "登录时间方案管理", R.menu.menu_time_management, this);
        }
    }

    @Override
    public void initListener() {
        // 跳转
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> startForResult(TimeManagementModeFragment.newInstance((UserLoginTime) item), 1));
        // 删除
        mBaseLoadMoreRecyclerAdapter.setOnItemDeleteClick(new TimeManagementAdapter.OnItemDeleteClick() {
            @Override
            public void onItemDeleteClick(View itemView, UserLoginTime item, int position) {
                if (mDialogHelper == null)
                    mDialogHelper = new DialogHelper(getContext(), "提示", "是否确认删除", "确定", "取消", false, new CommonDialog.Listener() {
                        @Override
                        public void ok(String content) {
                            mPresenter.remove(item.getId());
                            mDialogHelper.hideDialog();
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                mDialogHelper.show();
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
