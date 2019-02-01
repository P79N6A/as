package com.yaoguang.company.my.usermanagement.mode.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.amap.api.maps.model.LatLng;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentUserManagementAddressBinding;
import com.yaoguang.company.my.loginconditionconfiguration.timemanagement.model.TimeManagementModeFragment;
import com.yaoguang.company.my.usermanagement.mode.address.adapter.AddressManagementAdapter;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowLocation;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;

import java.util.ArrayList;

/**
 * Created by zhongjh on 2018/12/6.
 */
public class AddressManagementFragment extends BaseFragmentListConditionDataBind<UserLoginAllowLocation, String, AddressManagementAdapter, FragmentUserManagementAddressBinding>
        implements AddressManagementContract.View, Toolbar.OnMenuItemClickListener {

    AddressManagementContract.Presenter mPresenter;

    public static AddressManagementFragment newInstance() {
        return new AddressManagementFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = new AddressManagementPresenter(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_management_address;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new AddressManagementAdapter();
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
            initToolbarNav(mToolbarCommonBinding.toolbar, "选择地理位置", -1, null);
        }
    }

    @Override
    public void initListener() {
        // 确认
        mDataBinding.btnComit.setOnClickListener(v -> {
            // 返回当前选择的数据
            ArrayList<UserLoginAllowLocation> userLoginAllowLocations = new ArrayList<>();
            for (int i = 0; i < mBaseLoadMoreRecyclerAdapter.getList().size(); i++) {
                if (mBaseLoadMoreRecyclerAdapter.getList().get(i).isCheck()) {
                    userLoginAllowLocations.add(mBaseLoadMoreRecyclerAdapter.getList().get(i));
                }
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("userLoginAllowLocations", userLoginAllowLocations);
            setFragmentResult(RESULT_OK, bundle);
            pop();
        });

        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            LatLng latLng = new LatLng(((UserLoginAllowLocation)item).getLat(), ((UserLoginAllowLocation)item).getLon());
            start(com.yaoguang.appcommon.address.addressDetail.AddressDetailFragment.newInstance(latLng,((UserLoginAllowLocation)item).getAddress(), ((UserLoginAllowLocation)item).getRadius()));
        });

    }
}
