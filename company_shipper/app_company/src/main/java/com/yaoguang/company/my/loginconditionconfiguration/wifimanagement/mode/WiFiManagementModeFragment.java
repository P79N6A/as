package com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.mode;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentWifiManagementModeBinding;
import com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.nearwifimanagement.list.NearWiFiManagementFragment;
import com.yaoguang.datasource.company.MemberDataSource;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowWlan;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginWlan;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserRecentlyUsedWlan;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/12/19.
 */
public class WiFiManagementModeFragment extends BaseFragmentDataBind<FragmentWifiManagementModeBinding> {

    UserLoginAllowWlan mUserLoginAllowWlan;

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    WiFiManagementModeAdapter mWiFiManagementModeAdapter;
    MemberDataSource mMemberDataSource = new MemberDataSource();

    public static WiFiManagementModeFragment newInstance(UserLoginAllowWlan userLoginAllowWlan) {
        WiFiManagementModeFragment wiFiManagementModeFragment = new WiFiManagementModeFragment();
        Bundle bundle = new Bundle();
        if (userLoginAllowWlan != null) {
            bundle.putParcelable("userLoginAllowWlan", userLoginAllowWlan);
        }
        wiFiManagementModeFragment.setArguments(bundle);
        return wiFiManagementModeFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wifi_management_mode;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public void init() {
        // 判断是否有数据
        if (getArguments() != null) {
            mUserLoginAllowWlan = getArguments().getParcelable("userLoginAllowWlan");
            Gson gson = new Gson();
            String json = gson.toJson(mUserLoginAllowWlan);
            mUserLoginAllowWlan = gson.fromJson(json, UserLoginAllowWlan.class);
        }
        if (mDataBinding.toolbarCommon != null) {
            if (mUserLoginAllowWlan != null)
                initToolbarNav(mToolbarCommonBinding.toolbar, "最近连接过的WiFi网络", -1, null);
            else
                initToolbarNav(mToolbarCommonBinding.toolbar, "编辑WiFi网络群组", -1, null);
        }

        initData();
    }

    private void initData() {
        mWiFiManagementModeAdapter = new WiFiManagementModeAdapter();
        RecyclerViewUtils.initPage(mDataBinding.rvView, mWiFiManagementModeAdapter, null, _mActivity.getBaseContext(), true);
        if (mUserLoginAllowWlan != null) {
            mDataBinding.etNameValue.setText(mUserLoginAllowWlan.getGroupName());
            // 显示列表
            mDataBinding.rvView.setVisibility(View.VISIBLE);
            mWiFiManagementModeAdapter.setList(mUserLoginAllowWlan.getWlanList());
            mWiFiManagementModeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initListener() {
        mDataBinding.rlAdd.setOnClickListener(v -> startForResult(NearWiFiManagementFragment.newInstance(), 1));
        mDataBinding.btnComit.setOnClickListener(v -> {
            if (mUserLoginAllowWlan == null) {
                mUserLoginAllowWlan = new UserLoginAllowWlan();
            }
            mUserLoginAllowWlan.setGroupName(mDataBinding.etNameValue.getText().toString());
            mUserLoginAllowWlan.setWlanList(mWiFiManagementModeAdapter.getList());
            mMemberDataSource.wlanSave(mUserLoginAllowWlan)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, WiFiManagementModeFragment.this) {

                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            Toast.makeText(BaseApplication.getInstance(), response.getResult(), Toast.LENGTH_SHORT).show();
                            setFragmentResult(RESULT_OK, null);
                            pop();
                        }

                    });
        });
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    ArrayList<UserRecentlyUsedWlan> userRecentlyUsedWlans = data.getParcelableArrayList("userRecentlyUsedWlans");
                    if (userRecentlyUsedWlans != null) {
                        ArrayList<UserLoginWlan> userLoginWlans = new ArrayList<>();
                        for (UserRecentlyUsedWlan userRecentlyUsedWlan : userRecentlyUsedWlans) {
                            if (mWiFiManagementModeAdapter.getList().size() > 0) {
                                // 检查列表，如果列表有重复的则不添加
                                boolean isRepeat = false;
                                for (UserLoginWlan userLoginWlan : mWiFiManagementModeAdapter.getList()) {
                                    if (userLoginWlan.getAddress().equals(userRecentlyUsedWlan.getAddress()) && userLoginWlan.getName().equals(userRecentlyUsedWlan.getName())) {
                                        isRepeat = true;
                                    }
                                }
                                if (!isRepeat) {
                                    UserLoginWlan userLoginWlanNew = new UserLoginWlan();
                                    userLoginWlanNew.setAddress(userRecentlyUsedWlan.getAddress());
                                    userLoginWlanNew.setName(userRecentlyUsedWlan.getName());
                                    userLoginWlans.add(userLoginWlanNew);
                                }
                            }else{
                                UserLoginWlan userLoginWlanNew = new UserLoginWlan();
                                userLoginWlanNew.setAddress(userRecentlyUsedWlan.getAddress());
                                userLoginWlanNew.setName(userRecentlyUsedWlan.getName());
                                userLoginWlans.add(userLoginWlanNew);
                            }
                        }
                        // 刷新列表
                        mWiFiManagementModeAdapter.appendToList(userLoginWlans);
                        if (mWiFiManagementModeAdapter.getList().size() > 0)
                            // 显示列表
                            mDataBinding.rvView.setVisibility(View.VISIBLE);
                        mWiFiManagementModeAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }
}
