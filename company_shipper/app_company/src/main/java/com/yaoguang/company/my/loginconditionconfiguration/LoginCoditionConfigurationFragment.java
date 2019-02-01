package com.yaoguang.company.my.loginconditionconfiguration;

import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentLoginConditionConfigurationBinding;
import com.yaoguang.company.my.loginconditionconfiguration.locationmanagement.list.LocationManagementFragment;
import com.yaoguang.company.my.loginconditionconfiguration.timemanagement.list.TimeManagementFragment;
import com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.list.WiFiManagementFragment;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;

/**
 * 登录条件配置
 * Created by zhongjh on 2018/12/6.
 */
public class LoginCoditionConfigurationFragment extends BaseFragmentDataBind<FragmentLoginConditionConfigurationBinding>{

    public static LoginCoditionConfigurationFragment newInstance() {
        return new LoginCoditionConfigurationFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login_condition_configuration;
    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "登录条件配置", -1, null);
        }
    }

    @Override
    public void initListener() {
        // 登录时间方案管理
        mDataBinding.rlTimeManagement.setOnClickListener(v -> start(TimeManagementFragment.newInstance(), SINGLETOP));
        // wifi管理
        mDataBinding.rlWiFiNetworkGroupManagement.setOnClickListener(v -> start(WiFiManagementFragment.newInstance(),SINGLETOP));
        // 地理位置管理
        mDataBinding.rlGeoLocationManagement.setOnClickListener(v -> start(LocationManagementFragment.newInstance(), SINGLETOP));
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

}
