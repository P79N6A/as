package com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.nearwifimanagement.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentNearWifiManagementBinding;
import com.yaoguang.company.my.loginconditionconfiguration.timemanagement.model.TimeManagementModeFragment;
import com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.nearwifimanagement.list.adapter.NearWiFiManagementAdapter;
import com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.nearwifimanagement.list.submissionuser.SubmissionUserFragment;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserRecentlyUsedWlan;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserRecentlyUsedWlanLog;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongjh on 2018/12/6.
 */
public class NearWiFiManagementFragment extends BaseFragmentListConditionDataBind<UserRecentlyUsedWlan, String, NearWiFiManagementAdapter, FragmentNearWifiManagementBinding>
        implements NearWiFiManagementContract.View, Toolbar.OnMenuItemClickListener {

    NearWiFiManagementContract.Presenter mPresenter;

    public static NearWiFiManagementFragment newInstance() {
        return new NearWiFiManagementFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = new NearWiFiManagementPresenter(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_near_wifi_management;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new NearWiFiManagementAdapter();
    }

    @Override
    public String getCondition(boolean isRegain) {
        return mDataBinding.etSpinnerValue.getText().toString();
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
            initToolbarNav(mToolbarCommonBinding.toolbar, "最近连接过的WiFi网络", -1, null);
        }
    }

    @Override
    public void initListener() {
        // 选择事件
        mBaseLoadMoreRecyclerAdapter.setOnItemCheckClick((itemView, item, position) -> checkisCheck());
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> start(SubmissionUserFragment.newInstance((ArrayList<UserRecentlyUsedWlanLog>) ((UserRecentlyUsedWlan) item).getUsedLogs())));
        // 搜索回车事件
        mDataBinding.etSpinnerValue.setOnKeyListener((v, keyCode, event) -> {
            //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
            if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
                //处理事件
                refreshDataAnimation();
                return true;
            }
            return false;
        });
        // 确认事件
        mDataBinding.btnComit.setOnClickListener(v -> {
            ArrayList<UserRecentlyUsedWlan> userRecentlyUsedWlans = new ArrayList<>();
            for (UserRecentlyUsedWlan userRecentlyUsedWlan : mBaseLoadMoreRecyclerAdapter.getList()) {
                if (userRecentlyUsedWlan.isCheck())
                    userRecentlyUsedWlans.add(userRecentlyUsedWlan);
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("userRecentlyUsedWlans", userRecentlyUsedWlans);
            setFragmentResult(RESULT_OK, bundle);
            pop();
        });
    }

    @Override
    public void refreshAdapter(List<UserRecentlyUsedWlan> list, boolean isHas) {
        super.refreshAdapter(list, isHas);
        checkisCheck();
    }

    @Override
    public void nextAdapter(List<UserRecentlyUsedWlan> list, boolean isHas) {
        super.nextAdapter(list, isHas);
        checkisCheck();
    }

    /**
     * 检查是否勾选
     */
    private void checkisCheck() {
        boolean isAllCheck = true;
        int i = 0;
        for (UserRecentlyUsedWlan userRecentlyUsedWlan : mBaseLoadMoreRecyclerAdapter.getList()) {
            if (!userRecentlyUsedWlan.isCheck()) {
                isAllCheck = false;
            }else{
                i++;
            }
        }
        mDataBinding.tvCount.setText("已选择" + i + "项");
        mDataBinding.cbAll.setOnCheckedChangeListener(null);
        mDataBinding.cbAll.setChecked(isAllCheck);
        mDataBinding.cbAll.setOnCheckedChangeListener(new OnCheckedChangeListenerCustom());
    }

    /**
     * 设置当前列表跟随变化
     */
    public class OnCheckedChangeListenerCustom implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            for (UserRecentlyUsedWlan userRecentlyUsedWlan : mBaseLoadMoreRecyclerAdapter.getList()) {
                userRecentlyUsedWlan.setCheck(isChecked);
            }
            mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();
        }
    }
}
