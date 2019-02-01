package com.yaoguang.company.my.loginconditionconfiguration.locationmanagement.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.yaoguang.appcommon.address.editAddress.EditAddressFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentLocationManagementBinding;
import com.yaoguang.company.my.loginconditionconfiguration.locationmanagement.list.adapter.LocationManagementAdapter;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAllowLocation;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.ObjectUtils;


/**
 * Created by zhongjh on 2018/12/10.
 */
public class LocationManagementFragment extends BaseFragmentListConditionDataBind<UserLoginAllowLocation, String, LocationManagementAdapter, FragmentLocationManagementBinding>
        implements LocationManagementContract.View, Toolbar.OnMenuItemClickListener {

    public static final int REQUEST_ADDRESS = 1;

    LocationManagementContract.Presenter mPresenter;

    DialogHelper mDialogHelper;

    public static LocationManagementFragment newInstance() {
        return new LocationManagementFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = new LocationManagementPresenter(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new LocationManagementAdapter();
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
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
            startForResult(EditAddressFragment.newInstance(null, null, "搜索地址", true, true), REQUEST_ADDRESS);
        }
        return super.onMenuItemClick(item);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_location_management;
    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "地理位置管理", R.menu.menu_time_management, this);
        }
    }

    @Override
    public void initListener() {
        // 跳转
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            UserLoginAllowLocation userLoginAllowLocation = (UserLoginAllowLocation) item;
            LatLng latLng = new LatLng(userLoginAllowLocation.getLat(), userLoginAllowLocation.getLon());
            startForResult(EditAddressFragment.newInstance(latLng, null, "搜索地址", true, true), REQUEST_ADDRESS);
        });
        // 删除
        mBaseLoadMoreRecyclerAdapter.setOnItemDeleteClick((itemView, item, position) -> {
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
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADDRESS:
                    // 获取到自定义地址
                    String address = data.getString(EditAddressFragment.PARAMETER_CUSTOM_ADDRESS);
                    // 获取半径
                    String radius = data.getString(EditAddressFragment.PARAMETER_RADIUS);
                    // 添加
                    UserLoginAllowLocation userLoginAllowLocation = new UserLoginAllowLocation();
                    userLoginAllowLocation.setAddress(address);
                    userLoginAllowLocation.setRadius(ObjectUtils.parseInt(radius));
                    PoiItem poiItem = data.getParcelable(EditAddressFragment.PARAMETER_POIITEM);
                    if (poiItem != null) {
                        userLoginAllowLocation.setLat(poiItem.getLatLonPoint().getLatitude());
                        userLoginAllowLocation.setLon(poiItem.getLatLonPoint().getLongitude());
                    }
                    mPresenter.add(userLoginAllowLocation);
                    break;
            }
        }
    }

}
