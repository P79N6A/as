package com.yaoguang.company.my.usermanagement.mode;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentUserManagementModeBinding;
import com.yaoguang.company.my.usermanagement.mode.adapter.AddressAdapter;
import com.yaoguang.company.my.usermanagement.mode.adapter.AuthorizationAdapter;
import com.yaoguang.company.my.usermanagement.mode.adapter.TimeAdapter;
import com.yaoguang.company.my.usermanagement.mode.adapter.WiFiAdapter;
import com.yaoguang.company.my.usermanagement.mode.address.AddressManagementFragment;
import com.yaoguang.company.my.usermanagement.mode.authorizedhistory.AuthorizedHistoryFragment;
import com.yaoguang.company.my.usermanagement.mode.time.TimeManagementFragment;
import com.yaoguang.company.my.usermanagement.mode.wifi.WiFiManagementFragment;
import com.yaoguang.greendao.entity.company.user.ViewUserSetting;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowLocation;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAuthDevice;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTime;
import com.yaoguang.lib.adapter.BaseExpandRecyclerAdapter;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.map.common.AMapUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongjh on 2018/12/12.
 */
public class UserManagementModeFragment extends BaseFragmentDataBind<FragmentUserManagementModeBinding>
        implements UserManagementModeContract.View {

    protected UserManagementModeContract.Presenter mPresenter = new UserManagementModePresenter(this);
    String mUserId;

    DialogHelper mDialogHelperRlRoles;

    private final static int REQUEST_WEB_TIME = 1;
    private final static int REQUEST_COMPANY_TIME = 2;
    private final static int REQUEST_COMPANY_ADDRESS = 3;
    private final static int REQUEST_COMPANY_WIFI = 4;
    private final static int REQUEST_BOSS_TIME = 5;
    private final static int REQUEST_BOSS_WIFI = 6;
    private final static int REQUEST_BOSS_ADDRESS = 7;

    ViewUserSetting mViewUserSetting;

    DialogHelper mDialogHeplerWebAuthorization;
    DialogHelper mDialogHeplerCompanyAuthorization;
    DialogHelper mDialogHeplerBossAuthorization;
    AuthorizationAdapter mAuthorizationAdapterWeb = new AuthorizationAdapter();
    AuthorizationAdapter mAuthorizationAdapterCompany = new AuthorizationAdapter();
    AuthorizationAdapter mAuthorizationAdapterBoss = new AuthorizationAdapter();
    AddressAdapter mAddressAdapterCompany = new AddressAdapter();
    AddressAdapter mAddressAdapterBoss = new AddressAdapter();

    /**
     */
    public static UserManagementModeFragment newInstance(String userId) {
        UserManagementModeFragment userManagementModeFragment = new UserManagementModeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        userManagementModeFragment.setArguments(bundle);
        return userManagementModeFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_management_mode;
    }

    @Override
    public void init() {
        mUserId = getArguments().getString("userId");
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "修改用户", -1, null);
        }

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        if (!TextUtils.isEmpty(mUserId)) {
            // 获取数据
            mPresenter.getInfo(mUserId);
        }
    }

    @Override
    public void initListener() {
        // 提交
        mDataBinding.btnComit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewUserSetting.setUsable(mDataBinding.shAvailabilityValue.isChecked() ? 1 : 0); // 账户是否可用
                mViewUserSetting.setLoginFlag(mDataBinding.shIsItLoggedInWebValue.isChecked() ? 1 : 0); // 是否可登录
                mViewUserSetting.setIsOnlyQrCodeLogin(mDataBinding.shScanLoginValue.isChecked() ? 1 : 0);// 只允许扫描登录
                mViewUserSetting.setIsLoginAuthRequire(mDataBinding.shWebAuthorizationWebValue.isChecked() ? 1 : 0); // 浏览器是否需要授权

                mViewUserSetting.setAppLoginFlag(mDataBinding.shIsItLoggedInCompanyValue.isChecked() ? 1 : 0);// 是否可登录
                mViewUserSetting.setIsAppLoginAuthRequire(mDataBinding.shWebAuthorizationCompanyValue.isChecked() ? 1 : 0); // 设备是否需要授权

                mViewUserSetting.setBossLoginFlag(mDataBinding.shIsItLoggedInBossValue.isChecked() ? 1 : 0);// 是否可登录
                mViewUserSetting.setBossLoginAuthRequire(mDataBinding.shWebAuthorizationBossValue.isChecked() ? 1 : 0); // 设备是否需要授权

                mPresenter.comit(mViewUserSetting);
            }
        });

        // 显示具体用户角色
        mDataBinding.rlRoles.setOnClickListener(v -> {
            if (mDialogHelperRlRoles == null)
                mDialogHelperRlRoles = new DialogHelper(UserManagementModeFragment.this.getContext(), "用户角色", mDataBinding.tvRolesValue.getText().toString(), true, new CommonDialog.Listener() {
                    @Override
                    public void ok(String content) {
                        mDialogHelperRlRoles.hideDialog();
                    }

                    @Override
                    public void cancel() {

                    }
                });
            mDialogHelperRlRoles.show();
        });
        // web端的时间设置
        mDataBinding.tvAllowedLogonTimeWebReelect.setOnClickListener(v -> startForResult(TimeManagementFragment.newInstance(), REQUEST_WEB_TIME));
        mDataBinding.flAllowedLogonTimeWebAdd.setOnClickListener(v -> startForResult(TimeManagementFragment.newInstance(), REQUEST_WEB_TIME));
        // web端的授权历史
        mDataBinding.tvAuthorizationWebValue.setOnClickListener(v -> start(AuthorizedHistoryFragment.newInstance(mViewUserSetting.getId(), "0")));

        // 物流端的时间设置
        mDataBinding.tvAllowedLogonTimeCompanyReelect.setOnClickListener(v -> startForResult(TimeManagementFragment.newInstance(), REQUEST_COMPANY_TIME));
        mDataBinding.flAllowedLogonTimeCompanyAdd.setOnClickListener(v -> startForResult(TimeManagementFragment.newInstance(), REQUEST_COMPANY_TIME));
        // 物流端的wifi设置
        mDataBinding.tvNetworkEnvironmentCompanyReelect.setOnClickListener(v -> startForResult(WiFiManagementFragment.newInstance(), REQUEST_COMPANY_WIFI));
        mDataBinding.flNetworkEnvironmentCompanyAdd.setOnClickListener(v -> startForResult(WiFiManagementFragment.newInstance(), REQUEST_COMPANY_WIFI));
        // 物流端的地理设置
        mDataBinding.tvLocationCompanyReelect.setOnClickListener(v -> startForResult(AddressManagementFragment.newInstance(), REQUEST_COMPANY_ADDRESS));
        mDataBinding.flLocationCompanyAdd.setOnClickListener(v -> startForResult(AddressManagementFragment.newInstance(), REQUEST_COMPANY_ADDRESS));
        mAddressAdapterCompany.setOnItemClickListener((itemView, item, position) -> {
            LatLng latLng = new LatLng(((UserLoginAllowLocation) item).getLat(), ((UserLoginAllowLocation) item).getLon());
            start(com.yaoguang.appcommon.address.addressDetail.AddressDetailFragment.newInstance(latLng, ((UserLoginAllowLocation) item).getAddress(), ((UserLoginAllowLocation) item).getRadius()));
        });
        // 删除，并不是长按设置
        mAddressAdapterCompany.setOnItemLongClickListener((itemView, item, position) -> {
            mViewUserSetting.getAppAllowLocations().remove(position);
            if (mViewUserSetting.getAppAllowLocations().size() > 0) {
                mDataBinding.flLocationCompanyAdd.setVisibility(View.GONE);
                mDataBinding.tvLocationCompanyReelect.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.flLocationCompanyAdd.setVisibility(View.VISIBLE);
                mDataBinding.tvLocationCompanyReelect.setVisibility(View.GONE);
            }
        });
        // 物流端的授权历史
        mDataBinding.tvAuthorizationCompanyValue.setOnClickListener(v -> start(AuthorizedHistoryFragment.newInstance(mViewUserSetting.getId(), "1")));

        // Boss端的时间设置
        mDataBinding.tvAllowedLogonTimeBossReelect.setOnClickListener(v -> startForResult(TimeManagementFragment.newInstance(), REQUEST_BOSS_TIME));
        mDataBinding.flAllowedLogonTimeBossAdd.setOnClickListener(v -> startForResult(TimeManagementFragment.newInstance(), REQUEST_BOSS_TIME));
        // Boss端的wifi设置
        mDataBinding.tvNetworkEnvironmentBossReelect.setOnClickListener(v -> startForResult(WiFiManagementFragment.newInstance(), REQUEST_BOSS_WIFI));
        mDataBinding.flNetworkEnvironmentBossAdd.setOnClickListener(v -> startForResult(WiFiManagementFragment.newInstance(), REQUEST_BOSS_WIFI));
        // Boss端的地理设置
        mDataBinding.tvLocationBossReelect.setOnClickListener(v -> startForResult(AddressManagementFragment.newInstance(), REQUEST_BOSS_ADDRESS));
        mDataBinding.flLocationBossAdd.setOnClickListener(v -> startForResult(AddressManagementFragment.newInstance(), REQUEST_BOSS_ADDRESS));
        mAddressAdapterBoss.setOnItemClickListener((itemView, item, position) -> {
            LatLng latLng = new LatLng(((UserLoginAllowLocation) item).getLat(), ((UserLoginAllowLocation) item).getLon());
            start(com.yaoguang.appcommon.address.addressDetail.AddressDetailFragment.newInstance(latLng, ((UserLoginAllowLocation) item).getAddress(), ((UserLoginAllowLocation) item).getRadius()));
        });
        // 删除，并不是长按设置
        mAddressAdapterBoss.setOnItemLongClickListener((itemView, item, position) -> {
            mViewUserSetting.getBossAllowLocations().remove(position);
            if (mViewUserSetting.getBossAllowLocations().size() > 0) {
                mDataBinding.flLocationBossAdd.setVisibility(View.GONE);
                mDataBinding.tvLocationBossReelect.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.flLocationBossAdd.setVisibility(View.VISIBLE);
                mDataBinding.tvLocationBossReelect.setVisibility(View.GONE);
            }
        });
        // Boss端的授权历史
        mDataBinding.tvAuthorizationBossValue.setOnClickListener(v -> start(AuthorizedHistoryFragment.newInstance(mViewUserSetting.getId(), "2")));
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_WEB_TIME:
                    // web端的时间设置
                    mViewUserSetting.setLoginTime(data.getParcelable("userLoginTime"));
                    initWebTime(mViewUserSetting);
                    break;
                case REQUEST_COMPANY_TIME:
                    // 物流端的时间设置
                    mViewUserSetting.setAppLoginTime(data.getParcelable("userLoginTime"));
                    initCompanyTime(mViewUserSetting);
                    break;
                case REQUEST_COMPANY_ADDRESS:
                    // 物流端的地址设置
                    mViewUserSetting.setAppAllowLocations(data.getParcelableArrayList("userLoginAllowLocations"));
                    initCompanyAddress(mViewUserSetting);
                    break;
                case REQUEST_COMPANY_WIFI:
                    // 物流端的WIFI设置 ArrayList<UserLoginAllowWlan> userLoginAllowWlans
                    mViewUserSetting.setAppAllowWlans(data.getParcelableArrayList("userLoginAllowWlans"));
                    initCompanyWiFi(mViewUserSetting);
                    break;
                case REQUEST_BOSS_TIME:
                    // Boss端的时间设置
                    mViewUserSetting.setBossLoginTime(data.getParcelable("userLoginTime"));
                    initBossTime(mViewUserSetting);
                    break;
                case REQUEST_BOSS_WIFI:
                    // Boss端的WiFi设置
                    mViewUserSetting.setBossAllowWlans(data.getParcelableArrayList("userLoginAllowWlans"));
                    initBossWiFi(mViewUserSetting);
                    break;
                case REQUEST_BOSS_ADDRESS:
                    // 物流端的地址设置
                    mViewUserSetting.setBossAllowLocations(data.getParcelableArrayList("userLoginAllowLocations"));
                    initBossAddress(mViewUserSetting);
                    break;

            }
        }
    }

    @Override
    public void setViewUserSetting(ViewUserSetting result) {
        mViewUserSetting = result;
        initEssentialInformation(result);
        initAccountSettings(result);
        initWebSettings(result);
        initWebTime(result);
        initWebAuthorization(result);
        // 物流端
        initCompanySettings(result);
        initCompanyTime(result);
        initCompanyWiFi(result);
        initCompanyAddress(result);
        initCompanyAuthorization(result);
        // Boss端
        initBossSettings(result);
        initBossTime(result);
        initBossWiFi(result);
        initBossAddress(result);
        initBossAuthorization(result);
    }

    @Override
    public void authCancelWebAuthorization(int position) {
        mAuthorizationAdapterWeb.remove(position);
        mAuthorizationAdapterWeb.notifyDataSetChanged();
    }

    @Override
    public void authCancelCompanyAuthorization(int position) {
        mAuthorizationAdapterCompany.remove(position);
        mAuthorizationAdapterCompany.notifyDataSetChanged();
    }

    @Override
    public void authCancelBossAuthorization(int position) {
        mAuthorizationAdapterBoss.remove(position);
        mAuthorizationAdapterBoss.notifyDataSetChanged();
    }

    @Override
    public void comit(String result) {
        Toast.makeText(BaseApplication.getInstance(), result, Toast.LENGTH_SHORT).show();
        pop();
    }

    /**
     * 获取基本信息
     */
    private void initEssentialInformation(ViewUserSetting result) {
        mDataBinding.etNameValue.setText(result.getName());     // 姓名
        mDataBinding.etLoginNameValue.setText(result.getLoginName());   // 登录名字
        // 用户类型
        switch (result.getUserType()) {
            case 0:
                mDataBinding.etTypeValue.setText("总公司用户");
                mDataBinding.etTypeValue.setTag(result.getUserType());
                break;
            case 2:
                mDataBinding.etTypeValue.setText("管理员");
                mDataBinding.etTypeValue.setTag(result.getUserType());
                break;
            case 3:
                mDataBinding.etTypeValue.setText("普通用户");
                mDataBinding.etTypeValue.setTag(result.getUserType());
                break;
        }
        mDataBinding.etMechanismValue.setText(result.getOfficeName());// 归属机构
        mDataBinding.tvRolesValue.setText(result.getRoleName());// 用户角色
    }

    /**
     * 获取账户设置
     */
    private void initAccountSettings(ViewUserSetting result) {
        mDataBinding.shAvailabilityValue.setChecked(result.getUsable() == 1); // 账户是否可用
    }

    /**
     * Web端设置
     */
    private void initWebSettings(ViewUserSetting result) {
        mDataBinding.shIsItLoggedInWebValue.setChecked(result.getLoginFlag() == 1); // 是否可登录
        mDataBinding.shScanLoginValue.setChecked(result.getIsOnlyQrCodeLogin() == 1); // 只允许扫描登录
    }

    /**
     * Web端设置 - 时间
     */
    private void initWebTime(ViewUserSetting result) {
        // 登录时间
        if (result.getLoginTime() != null) {
            ArrayList<String[]> list = new ArrayList<>();
            if (ObjectUtils.parseInt(result.getLoginTime().getIsMon()) == 1) {
                list.add(new String[]{"周一", result.getLoginTime().getMonTime().split(",")[0] + "~" + result.getLoginTime().getMonTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getLoginTime().getIsTue()) == 1) {
                list.add(new String[]{"周二", result.getLoginTime().getTueTime().split(",")[0] + "~" + result.getLoginTime().getTueTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getLoginTime().getIsWes()) == 1) {
                list.add(new String[]{"周三", result.getLoginTime().getWesTime().split(",")[0] + "~" + result.getLoginTime().getWesTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getLoginTime().getIsThi()) == 1) {
                list.add(new String[]{"周四", result.getLoginTime().getThiTime().split(",")[0] + "~" + result.getLoginTime().getThiTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getLoginTime().getIsFri()) == 1) {
                list.add(new String[]{"周五", result.getLoginTime().getFriTime().split(",")[0] + "~" + result.getLoginTime().getFriTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getLoginTime().getIsSat()) == 1) {
                list.add(new String[]{"周六", result.getLoginTime().getSatTime().split(",")[0] + "~" + result.getLoginTime().getSatTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getLoginTime().getIsSun()) == 1) {
                list.add(new String[]{"周日", result.getLoginTime().getSunTime().split(",")[0] + "~" + result.getLoginTime().getSunTime().split(",")[1]});
            }
            mDataBinding.tvAllowedLogonTimeWebDeleteTitle.setText(result.getLoginTime().getName());
            if (list.size() > 0) {
                mDataBinding.llAllowedLogonTimeWebDelete.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.llAllowedLogonTimeWebDelete.setVisibility(View.GONE);
            }
            TimeAdapter timeAdapter = new TimeAdapter();
            // 移除后，显示添加，清空列表数据等等
            mDataBinding.tvAllowedLogonTimeWebDelete.setOnClickListener(v -> {
                // 显示添加
                mDataBinding.flAllowedLogonTimeWebAdd.setVisibility(View.VISIBLE);
                // 隐藏重选
                mDataBinding.tvAllowedLogonTimeWebReelect.setVisibility(View.GONE);
                // 隐藏头部的标题
                mDataBinding.llAllowedLogonTimeWebDelete.setVisibility(View.GONE);
                // 隐藏底部的展开
                mDataBinding.tvExpandAllowedLogonTimeWeb.setVisibility(View.GONE);
                // 删除列表数据
                timeAdapter.clear();
                timeAdapter.notifyDataSetChanged();
                result.setLoginTime(new UserLoginTime());
            });
            initExpandRecyclerView(mDataBinding.rvAllowedLogonTimeWeb, timeAdapter, 2, list, mDataBinding.tvExpandAllowedLogonTimeWeb, mDataBinding.flAllowedLogonTimeWebAdd, mDataBinding.tvAllowedLogonTimeWebReelect);
        }
    }

    /**
     * Web端设置 - web授权
     */
    private void initWebAuthorization(ViewUserSetting result) {
        mDataBinding.shWebAuthorizationWebValue.setChecked(result.getIsLoginAuthRequire() == 1); // 浏览器是否需要授权
        initExpandRecyclerView(mDataBinding.rvAuthorizationWeb, mAuthorizationAdapterWeb, 1, result.getLoginAuthDevices(), null, null, null);
        mAuthorizationAdapterWeb.setOnItemClickListener((itemView, item, position) -> {
            if (mDialogHeplerWebAuthorization == null) {
                mDialogHeplerWebAuthorization = new DialogHelper(UserManagementModeFragment.this.getContext(), "提示", "是否取消授权", new CommonDialog.Listener() {
                    @Override
                    public void ok(String content) {
                        mPresenter.authCancelAuthorization(((UserLoginAuthDevice) item).getId(), position, 0);
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
            mDialogHeplerWebAuthorization.show();
        });
    }

    /**
     * 物流端设置
     */
    private void initCompanySettings(ViewUserSetting result) {
        mDataBinding.shIsItLoggedInCompanyValue.setChecked(result.getIsAppLoginAuthRequire() == 1); // 是否可登录
    }

    /**
     * 物流端设置 - 时间
     */
    private void initCompanyTime(ViewUserSetting result) {
        // 登录时间
        if (result.getAppLoginTime() != null) {
            ArrayList<String[]> list = new ArrayList<>();
            if (ObjectUtils.parseInt(result.getAppLoginTime().getIsMon()) == 1) {
                list.add(new String[]{"周一", result.getAppLoginTime().getMonTime().split(",")[0] + "~" + result.getAppLoginTime().getMonTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getAppLoginTime().getIsTue()) == 1) {
                list.add(new String[]{"周二", result.getAppLoginTime().getTueTime().split(",")[0] + "~" + result.getAppLoginTime().getTueTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getAppLoginTime().getIsWes()) == 1) {
                list.add(new String[]{"周三", result.getAppLoginTime().getWesTime().split(",")[0] + "~" + result.getAppLoginTime().getWesTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getAppLoginTime().getIsThi()) == 1) {
                list.add(new String[]{"周四", result.getAppLoginTime().getThiTime().split(",")[0] + "~" + result.getAppLoginTime().getThiTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getAppLoginTime().getIsFri()) == 1) {
                list.add(new String[]{"周五", result.getAppLoginTime().getFriTime().split(",")[0] + "~" + result.getAppLoginTime().getFriTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getAppLoginTime().getIsSat()) == 1) {
                list.add(new String[]{"周六", result.getAppLoginTime().getSatTime().split(",")[0] + "~" + result.getAppLoginTime().getSatTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getAppLoginTime().getIsSun()) == 1) {
                list.add(new String[]{"周日", result.getAppLoginTime().getSunTime().split(",")[0] + "~" + result.getAppLoginTime().getSunTime().split(",")[1]});
            }
            mDataBinding.tvAllowedLogonTimeCompanyDeleteTitle.setText(result.getAppLoginTime().getName());
            if (list.size() > 0) {
                mDataBinding.llAllowedLogonTimeCompanyDelete.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.llAllowedLogonTimeCompanyDelete.setVisibility(View.GONE);
            }
            TimeAdapter timeAdapter = new TimeAdapter();
            // 移除后，显示添加，清空列表数据等等
            mDataBinding.tvAllowedLogonTimeCompanyDelete.setOnClickListener(v -> {
                // 显示添加
                mDataBinding.flAllowedLogonTimeCompanyAdd.setVisibility(View.VISIBLE);
                // 隐藏重选
                mDataBinding.tvAllowedLogonTimeCompanyReelect.setVisibility(View.GONE);
                // 隐藏头部的标题
                mDataBinding.llAllowedLogonTimeCompanyDelete.setVisibility(View.GONE);
                // 隐藏底部的展开
                mDataBinding.tvExpandAllowedLogonTimeCompany.setVisibility(View.GONE);
                // 删除列表数据
                timeAdapter.clear();
                timeAdapter.notifyDataSetChanged();
                result.setAppLoginTime(new UserLoginTime());
            });
            initExpandRecyclerView(mDataBinding.rvAllowedLogonTimeCompany, timeAdapter, 2, list, mDataBinding.tvExpandAllowedLogonTimeCompany, mDataBinding.flAllowedLogonTimeCompanyAdd, mDataBinding.tvAllowedLogonTimeCompanyReelect);
        }
    }

    /**
     * 物流端设置 - wifi
     */
    private void initCompanyWiFi(ViewUserSetting result) {
        mDataBinding.llNetworkEnvironmentCompany.removeAllViews();
        if (result.getAppAllowWlans() != null) {
            // 循环数据
            for (int i = 0; i < result.getAppAllowWlans().size(); i++) {
                View view = View.inflate(getContext(), R.layout.item_usermanagement_wifis, null);
                mDataBinding.llNetworkEnvironmentCompany.addView(view);
                ((TextView) view.findViewById(R.id.tvTitle)).setText(result.getAppAllowWlans().get(i).getGroupName());
                initExpandRecyclerView(view.findViewById(R.id.rvNetworkEnvironment), new WiFiAdapter(), 2, result.getAppAllowWlans().get(i).getWlanList(), view.findViewById(R.id.tvNetworkEnvironment), null, null);
                view.findViewById(R.id.tvDelete).setTag(result.getAppAllowWlans().get(i));
                view.findViewById(R.id.tvDelete).setOnClickListener(v -> {
                    mDataBinding.llNetworkEnvironmentCompany.removeView(view);
                    result.getAppAllowWlans().remove(view.findViewById(R.id.tvDelete).getTag());
                    // 每次删除检测数据是否还在，不在的话就设置显示隐藏相关布局
                    if (result.getAppAllowWlans().size() > 0) {
                        mDataBinding.flNetworkEnvironmentCompanyAdd.setVisibility(View.GONE);
                        mDataBinding.tvNetworkEnvironmentCompanyReelect.setVisibility(View.VISIBLE);
                    } else {
                        mDataBinding.flNetworkEnvironmentCompanyAdd.setVisibility(View.VISIBLE);
                        mDataBinding.tvNetworkEnvironmentCompanyReelect.setVisibility(View.GONE);
                    }
                });
            }
            if (result.getAppAllowWlans().size() > 0) {
                mDataBinding.flNetworkEnvironmentCompanyAdd.setVisibility(View.GONE);
                mDataBinding.tvNetworkEnvironmentCompanyReelect.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.flNetworkEnvironmentCompanyAdd.setVisibility(View.VISIBLE);
                mDataBinding.tvNetworkEnvironmentCompanyReelect.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 物流端设置 - 地址
     */
    private void initCompanyAddress(ViewUserSetting result) {
        if (result.getAppAllowLocations() != null) {
            initExpandRecyclerView(mDataBinding.rvLocationCompany, mAddressAdapterCompany, 1, result.getAppAllowLocations(), mDataBinding.tvLocationCompany, mDataBinding.flLocationCompanyAdd, mDataBinding.tvLocationCompanyReelect);
        }
    }

    /**
     * 物流端设置 - 授权
     */
    private void initCompanyAuthorization(ViewUserSetting result) {
        mDataBinding.shWebAuthorizationCompanyValue.setChecked(result.getIsAppLoginAuthRequire() == 1); // 设备是否需要授权
        initExpandRecyclerView(mDataBinding.rvAuthorizationCompany, mAuthorizationAdapterCompany, 1, result.getAppAuthDevices(), null, null, null);
        mAuthorizationAdapterCompany.setOnItemClickListener((itemView, item, position) -> {
            if (mDialogHeplerCompanyAuthorization == null) {
                mDialogHeplerCompanyAuthorization = new DialogHelper(UserManagementModeFragment.this.getContext(), "提示", "是否取消授权", new CommonDialog.Listener() {
                    @Override
                    public void ok(String content) {
                        mPresenter.authCancelAuthorization(((UserLoginAuthDevice) item).getId(), position, 1);
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
            mDialogHeplerCompanyAuthorization.show();
        });
    }

    /**
     * Boss端设置
     */
    private void initBossSettings(ViewUserSetting result) {
        mDataBinding.shIsItLoggedInBossValue.setChecked(result.getBossLoginAuthRequire() == 1);
    }

    /**
     * Boss端设置 - 时间
     */
    private void initBossTime(ViewUserSetting result) {
        // 登录时间
        if (result.getBossLoginTime() != null) {
            ArrayList<String[]> list = new ArrayList<>();
            if (ObjectUtils.parseInt(result.getBossLoginTime().getIsMon()) == 1) {
                list.add(new String[]{"周一", result.getBossLoginTime().getMonTime().split(",")[0] + "~" + result.getBossLoginTime().getMonTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getBossLoginTime().getIsTue()) == 1) {
                list.add(new String[]{"周二", result.getBossLoginTime().getTueTime().split(",")[0] + "~" + result.getBossLoginTime().getTueTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getBossLoginTime().getIsWes()) == 1) {
                list.add(new String[]{"周三", result.getBossLoginTime().getWesTime().split(",")[0] + "~" + result.getBossLoginTime().getWesTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getBossLoginTime().getIsThi()) == 1) {
                list.add(new String[]{"周四", result.getBossLoginTime().getThiTime().split(",")[0] + "~" + result.getBossLoginTime().getThiTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getBossLoginTime().getIsFri()) == 1) {
                list.add(new String[]{"周五", result.getBossLoginTime().getFriTime().split(",")[0] + "~" + result.getBossLoginTime().getFriTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getBossLoginTime().getIsSat()) == 1) {
                list.add(new String[]{"周六", result.getBossLoginTime().getSatTime().split(",")[0] + "~" + result.getBossLoginTime().getSatTime().split(",")[1]});
            }
            if (ObjectUtils.parseInt(result.getBossLoginTime().getIsSun()) == 1) {
                list.add(new String[]{"周日", result.getBossLoginTime().getSunTime().split(",")[0] + "~" + result.getBossLoginTime().getSunTime().split(",")[1]});
            }
            mDataBinding.tvAllowedLogonTimeBossDeleteTitle.setText(result.getBossLoginTime().getName());
            if (list.size() > 0) {
                mDataBinding.llAllowedLogonTimeBossDelete.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.llAllowedLogonTimeBossDelete.setVisibility(View.GONE);
            }
            TimeAdapter timeAdapter = new TimeAdapter();
            // 移除后，显示添加，清空列表数据等等
            mDataBinding.tvAllowedLogonTimeBossDelete.setOnClickListener(v -> {
                // 显示添加
                mDataBinding.flAllowedLogonTimeBossAdd.setVisibility(View.VISIBLE);
                // 隐藏重选
                mDataBinding.tvAllowedLogonTimeBossReelect.setVisibility(View.GONE);
                // 隐藏头部的标题
                mDataBinding.llAllowedLogonTimeBossDelete.setVisibility(View.GONE);
                // 隐藏底部的展开
                mDataBinding.tvExpandAllowedLogonTimeBoss.setVisibility(View.GONE);
                // 删除列表数据
                timeAdapter.clear();
                timeAdapter.notifyDataSetChanged();
                result.setBossLoginTime(new UserLoginTime());
            });
            initExpandRecyclerView(mDataBinding.rvAllowedLogonTimeBoss, timeAdapter, 2, list, mDataBinding.tvExpandAllowedLogonTimeBoss, mDataBinding.flAllowedLogonTimeBossAdd, mDataBinding.tvAllowedLogonTimeBossReelect);
        }
    }

    /**
     * Boss端设置 - wifi
     */
    private void initBossWiFi(ViewUserSetting result) {
        mDataBinding.llNetworkEnvironmentBoss.removeAllViews();
        if (result.getBossAllowWlans() != null) {
            // 循环数据
            for (int i = 0; i < result.getBossAllowWlans().size(); i++) {
                View view = View.inflate(getContext(), R.layout.item_usermanagement_wifis, null);
                mDataBinding.llNetworkEnvironmentBoss.addView(view);
                ((TextView) view.findViewById(R.id.tvTitle)).setText(result.getBossAllowWlans().get(i).getGroupName());
                initExpandRecyclerView(view.findViewById(R.id.rvNetworkEnvironment), new WiFiAdapter(), 2, result.getBossAllowWlans().get(i).getWlanList(), view.findViewById(R.id.tvNetworkEnvironment), null, null);
                view.findViewById(R.id.tvDelete).setTag(result.getBossAllowWlans().get(i));
                view.findViewById(R.id.tvDelete).setOnClickListener(v -> {
                    mDataBinding.llNetworkEnvironmentBoss.removeView(view);
                    result.getBossAllowWlans().remove(view.findViewById(R.id.tvDelete).getTag());
                    // 每次删除检测数据是否还在，不在的话就设置显示隐藏相关布局
                    if (result.getBossAllowWlans().size() > 0) {
                        mDataBinding.flNetworkEnvironmentBossAdd.setVisibility(View.GONE);
                        mDataBinding.tvNetworkEnvironmentBossReelect.setVisibility(View.VISIBLE);
                    } else {
                        mDataBinding.flNetworkEnvironmentBossAdd.setVisibility(View.VISIBLE);
                        mDataBinding.tvNetworkEnvironmentBossReelect.setVisibility(View.GONE);
                    }
                });
            }
            if (result.getBossAllowWlans().size() > 0) {
                mDataBinding.flNetworkEnvironmentBossAdd.setVisibility(View.GONE);
                mDataBinding.tvNetworkEnvironmentBossReelect.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.flNetworkEnvironmentBossAdd.setVisibility(View.VISIBLE);
                mDataBinding.tvNetworkEnvironmentBossReelect.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Boss端设置 - 地址
     */
    private void initBossAddress(ViewUserSetting result) {
        if (result.getBossAllowLocations() != null) {
            initExpandRecyclerView(mDataBinding.rvLocationBoss, mAddressAdapterBoss, 1, result.getBossAllowLocations(), mDataBinding.tvLocationBoss, mDataBinding.flLocationBossAdd, mDataBinding.tvLocationBossReelect);
        }
    }

    /**
     * Boss端设置 - 授权
     */
    private void initBossAuthorization(ViewUserSetting result) {
        mDataBinding.shWebAuthorizationBossValue.setChecked(result.getBossLoginAuthRequire() == 1); // 设备是否需要授权
        initExpandRecyclerView(mDataBinding.rvAuthorizationBoss, mAuthorizationAdapterBoss, 1, result.getBossAuthDevices(), null, null, null);
        mAuthorizationAdapterBoss.setOnItemClickListener((itemView, item, position) -> {
            if (mDialogHeplerBossAuthorization == null) {
                mDialogHeplerBossAuthorization = new DialogHelper(UserManagementModeFragment.this.getContext(), "提示", "是否取消授权", new CommonDialog.Listener() {
                    @Override
                    public void ok(String content) {
                        mPresenter.authCancelAuthorization(((UserLoginAuthDevice) item).getId(), position, 2);
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
            mDialogHeplerBossAuthorization.show();
        });

    }

    /**
     * 设置展开的recyclerView
     *
     * @param recyclerView              列表
     * @param baseExpandRecyclerAdapter 适配器
     * @param spanCount                 个数
     * @param list                      数据源
     * @param tvExpand                  展开收缩的textView
     * @param flAdd                     没有数据的时候进行添加
     * @param tvReelect                 重选控件
     */
    private void initExpandRecyclerView(RecyclerView recyclerView, BaseExpandRecyclerAdapter baseExpandRecyclerAdapter, int spanCount, List list, TextView tvExpand, FrameLayout flAdd, TextView tvReelect) {
        baseExpandRecyclerAdapter.appendToList(list);
        baseExpandRecyclerAdapter.setMaxLength(4);
        // 让嵌套滑动更流畅
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(baseExpandRecyclerAdapter);
        // 判断是否显示出来
        if (tvExpand != null)
            if (list.size() <= 4) {
                tvExpand.setVisibility(View.GONE);
            } else {
                tvExpand.setVisibility(View.VISIBLE);
                tvExpand.setOnClickListener(v -> {
                    if (tvExpand.getText().toString().equals("展开  ∨")) {
                        tvExpand.setText("收起  ∧");
                    } else {
                        tvExpand.setText("展开  ∨");
                    }
                    baseExpandRecyclerAdapter.setContractionExpansion();
                });
            }
        // 如果有数据，就不需要显示
        if (flAdd != null)
            if (list.size() > 0) {
                flAdd.setVisibility(View.GONE);
                tvReelect.setVisibility(View.VISIBLE);
            } else {
                flAdd.setVisibility(View.VISIBLE);
                tvReelect.setVisibility(View.GONE);
            }
    }

}
