package com.yaoguang.company.my.usermanagement.mode.wifi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentUserManagementWifiBinding;
import com.yaoguang.company.databinding.FragmentWifiManagementBinding;
import com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.mode.WiFiManagementModeFragment;
import com.yaoguang.company.my.usermanagement.mode.wifi.adapter.WiFiManagementAdapter;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowWlan;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;

import java.util.ArrayList;

/**
 * Created by zhongjh on 2018/12/6.
 */
public class WiFiManagementFragment extends BaseFragmentListConditionDataBind<UserLoginAllowWlan, String, WiFiManagementAdapter, FragmentUserManagementWifiBinding>
        implements WiFiManagementContract.View, Toolbar.OnMenuItemClickListener {

    WiFiManagementContract.Presenter mPresenter;
    DialogHelper mDialogHelper;

    // 其实RecycledViewPool的内部维护了一个Map，里面以不同的viewType为Key存储了各自对应的ViewHolder集合，所以这边设置了常量防止父适配器和子适配器的ViewType冲突
    public static final int PARENT_VIEW_TYPE = 0;
    public static final int CHILD_VIEW_TYPE = 1;

    public static WiFiManagementFragment newInstance() {
        return new WiFiManagementFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = new WiFiManagementPresenter(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_management_wifi;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new WiFiManagementAdapter();
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
            startForResult(WiFiManagementModeFragment.newInstance(null), 1);
        }
        return super.onMenuItemClick(item);
    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "选择WiFi网络群组管理", R.menu.menu_time_management, this);
        }
    }

    @Override
    protected void initRecyclerView() {
        // 创建 ViewHolder的缓存共享池
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        LinearLayoutManager layoutManager = new LinearLayoutManager(WiFiManagementFragment.this.getContext());
        // 需要注意：要使用RecycledViewPool的话,如果使用的LayoutManager是LinearLayoutManager或其子类（如GridLayoutManager），需要手动开启这个特性
        layoutManager.setRecycleChildrenOnDetach(true);
        mLayoutRecyclerviewBinding.rlView.setLayoutManager(layoutManager);
        mLayoutRecyclerviewBinding.rlView.setRecycledViewPool(recycledViewPool);
        // 传递RecycledViewPool共享池进父适配器，让父适配器里面的子适配器也共用同一个共享池
        mBaseLoadMoreRecyclerAdapter.setRecycledViewPool(WiFiManagementFragment.this.getContext(),recycledViewPool);
        mLayoutRecyclerviewBinding.rlView.setAdapter(mBaseLoadMoreRecyclerAdapter);
    }

    @Override
    public void initListener() {
//        // 跳转
//        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> startForResult(WiFiManagementModeFragment.newInstance((UserLoginAllowWlan) item), 1));
        // 确定
        mDataBinding.btnComit.setOnClickListener(v -> {
            // 返回当前选择的数据
            ArrayList<UserLoginAllowWlan> userLoginAllowWlans = new ArrayList<>();
            for (int i = 0; i < mBaseLoadMoreRecyclerAdapter.getList().size(); i++) {
                if (mBaseLoadMoreRecyclerAdapter.getList().get(i).isCheck()) {
                    userLoginAllowWlans.add(mBaseLoadMoreRecyclerAdapter.getList().get(i));
                }
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("userLoginAllowWlans", userLoginAllowWlans);
            setFragmentResult(RESULT_OK, bundle);
            pop();
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
