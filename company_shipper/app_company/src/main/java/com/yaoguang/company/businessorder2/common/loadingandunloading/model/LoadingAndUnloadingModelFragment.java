package com.yaoguang.company.businessorder2.common.loadingandunloading.model;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.yaoguang.appcommon.address.editAddress.EditAddressFragment;
import com.yaoguang.appcommon.common.base.basemap.BaseMapDataBindFragment;
import com.yaoguang.appcommon.common.base.basemap.CallBack;
import com.yaoguang.appcommon.common.view.EditTextFragment;
import com.yaoguang.appcommon.localselect.MyLocalSelectFragment;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search2.SearchFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.loadingandunloading.model.modifyremark.ModifyRemarkFragment;
import com.yaoguang.company.databinding.FragmentLoadingAndUnloadingModelBinding;
import com.yaoguang.greendao.entity.company.InfoClientPlace;
import com.yaoguang.greendao.entity.driver.LocationArea;
import com.yaoguang.greendao.entity.driver.Site;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;

import static com.yaoguang.appcommon.localselect.MyLocalSelectFragment.SELECT_LOCAL_RESULT;

/**
 * Created by zhongjh on 2018/11/6.
 */
public class LoadingAndUnloadingModelFragment extends BaseMapDataBindFragment<FragmentLoadingAndUnloadingModelBinding>
        implements LoadingAndUnloadingModelContact.View {

    public static final int FLAG_HOMETOWN = 1;
    public static final int REQUEST_ADDRESS = 2;
    public static final int REQUEST_REMAR = 3;

    protected LoadingAndUnloadingModelContact.Presenter mPresenter;
    InfoClientPlace infoClientPlace = new InfoClientPlace();
    Site site = new Site();

    public String mCodeId; // 托运人id
    /**
     * 0代表装货，1代表卸货
     */
    public static final String TYPE = "TYPE";
    public int mType;
    /**
     * 从列表传递过来的对象(用于进行修改或者查看)
     */
    public static final String ID = "ID";
    public String mId;
    public String mAreaName;// 区域，起运地或者是目的地

    public String mConsigneeId; // 货主

    public static LoadingAndUnloadingModelFragment newInstance(String id, InfoClientPlace infoClientPlace, String codeId, int type, String areaName, String consigneeId) {
        LoadingAndUnloadingModelFragment loadingAndUnloadingModelFragment = new LoadingAndUnloadingModelFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("infoClientPlace", infoClientPlace);
        bundle.putString(ID, id);
        bundle.putString("codeId", codeId);
        bundle.putInt(TYPE, type);
        bundle.putString("areaName", areaName);
        bundle.putString("consigneeId", consigneeId);
        loadingAndUnloadingModelFragment.setArguments(bundle);
        return loadingAndUnloadingModelFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mType = args.getInt(TYPE);
            mId = args.getString(ID);
            mCodeId = args.getString("codeId");
            mAreaName = args.getString("areaName");
            mConsigneeId = args.getString("consigneeId");
            if (args.getParcelable("infoClientPlace") != null)
                infoClientPlace = args.getParcelable("infoClientPlace");
        }
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void aMapinit(TextureMapView mapView, Bundle savedInstanceState) {
        isMonitor = false;
        super.aMapinit(mapView, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setLocationButton(false);     // 不要定位按钮
        mAMap.setLocationSource(null);// 不设置定位监听
        mAMap.getUiSettings().setScrollGesturesEnabled(false);// 禁止手势滑动
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_loading_and_unloading_model;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void init() {
        mPresenter = new LoadingAndUnloadingModelPresenter(this, getContext(), mId);

        String title;
        if (mType == 0)
            title = "装货";
        else
            title = "卸货";
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "管理" + title + "信息", -1, null);
        }
        mDataBinding.tvRemarkTitle.setText(title + "说明");
        // mCodeId如果为空则进行一些相关隐藏，直接编辑是不需要的
        if (TextUtils.isEmpty(mCodeId)) {
            mDataBinding.rlRegion.setVisibility(View.GONE);     // 地区
            mDataBinding.vRegion.setVisibility(View.GONE);      // 地区
            mDataBinding.rlRegion2.setVisibility(View.GONE);    // 区域
            mDataBinding.vRegion2.setVisibility(View.GONE);     // 区域
            mDataBinding.rlCargoOwner.setVisibility(View.GONE); // 货主
            mDataBinding.vCargoOwner.setVisibility(View.GONE);  // 货主
        }

        initInfoClientPlace();
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        if (infoClientPlace.getLocationArea() != null) {
            mDataBinding.rlMap.setVisibility(View.VISIBLE);
            mDataBinding.map.onResume(); // 恢复使用地图
            mDataBinding.flMapAdd.setVisibility(View.GONE);
        }
    }

    @Override
    public void initListener() {
        // 地区
        mDataBinding.rlRegion.setOnClickListener(v -> startForResult(MyLocalSelectFragment.newInstance(true), FLAG_HOMETOWN));
        // 地址
        mDataBinding.flMapAdd.setOnClickListener(v -> startForResult(EditAddressFragment.newInstance(null, mDataBinding.tvValueRegion.getText().toString() + mDataBinding.etDetailedAddress.getText().toString(), null, true, false), REQUEST_ADDRESS));
        // 区域
        mDataBinding.rlRegion2.setOnClickListener(v -> {
            if (mType == 0) {
                // 起运地
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DEPARTURE222);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DEPARTURE222));
            } else {
                // 目的地
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DESTINATION333);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DESTINATION333));
            }
        });
        // 说明
        mDataBinding.rlRemark.setOnClickListener(v -> {
            String title;
            if (mType == 0)
                title = "装货";
            else
                title = "卸货";
            startForResult(new ModifyRemarkFragment().newInstance(title + "说明", mDataBinding.tvValueRemark.getText().toString()), REQUEST_REMAR);
        });
        // 地图删除
        mDataBinding.imgMapDelete.setOnClickListener(v -> {
            mDataBinding.rlMap.setVisibility(View.GONE);
            mDataBinding.map.onPause(); // 恢复使用地图
            mDataBinding.flMapAdd.setVisibility(View.VISIBLE);
        });
        // 地图重选
        mDataBinding.imgMapReelection.setOnClickListener(v -> {
            LatLng latLng = new LatLng(site.getLat(), site.getLon());
            startForResult(EditAddressFragment.newInstance(latLng, null, null, true, false), REQUEST_ADDRESS);
        });

        // 提交
        mDataBinding.btnComit.setOnClickListener(v -> {
            // 单位名称
            infoClientPlace.setConsigneeCompany(mDataBinding.etUnitName.getText().toString());
            // 地区
            infoClientPlace.setRegionid(mDataBinding.tvValueRegion.getText().toString());
            // 详细地址
            infoClientPlace.setAddr(mDataBinding.etDetailedAddress.getText().toString());
            // 区域
            infoClientPlace.setArea(mDataBinding.tvValueRegion2.getText().toString());
            // 货主
            infoClientPlace.setConsigneeId(mDataBinding.etCargoOwner.getText().toString());
            // 联系人
            infoClientPlace.setLinkman(mDataBinding.etContacts.getText().toString());
            // 手机
            infoClientPlace.setLinkmanMp(mDataBinding.etMobile.getText().toString());
            // 电话
            infoClientPlace.setLinkmanTel(mDataBinding.etPhone.getText().toString());
            // 传真
            infoClientPlace.setConsigneeCompanyFax(mDataBinding.etFax.getText().toString());
            // 说明
            infoClientPlace.setZxRemark(mDataBinding.tvValueRemark.getText().toString());
            // 经纬度
            LocationArea locationArea = new LocationArea();
            locationArea.setSite(site);
            infoClientPlace.setLocationArea(locationArea);
            // 根据mCodeId判断，如果mCodeId为空，就不需要通过服务器更新
            if (TextUtils.isEmpty(mCodeId)) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("infoClientPlace", infoClientPlace);
                setFragmentResult(RESULT_OK, bundle);
                pop();
            } else {
                // codeid
                infoClientPlace.setCodeId(mCodeId);
                mPresenter.addLoadPlace(infoClientPlace);
            }

        });
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case FLAG_HOMETOWN:
                    String[] local = data.getString(SELECT_LOCAL_RESULT, "").split(",");
                    mDataBinding.tvValueRegion.setText(ObjectUtils.parseString(local[0]) + " " + ObjectUtils.parseString(local[1]) + " " + ObjectUtils.parseString(local[2]));
                    break;
                case REQUEST_ADDRESS:
                    PoiItem poiItem = data.getParcelable(EditAddressFragment.PARAMETER_POIITEM);
//                    String doorAddress = data.getString(EditAddressFragment.PARAMETER_DOOR_ADDRESS);
                    if (poiItem != null) {
                        site.setLat(poiItem.getLatLonPoint().getLatitude());
                        site.setLon(poiItem.getLatLonPoint().getLongitude());
//                        mDataBinding.etDetailedAddress.setText(doorAddress);
                        // 设置地图
                        LatLng latLng = new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
                        movePoint(latLng, false);
                        mDataBinding.rlMap.setVisibility(View.VISIBLE);
                        mDataBinding.map.onResume(); // 恢复使用地图
                        mDataBinding.flMapAdd.setVisibility(View.GONE);

                        // 设置标记
                        addMarkerInScreenCenter(latLng,false);
                    }

                    break;
                case PublicSearchInteractorImpl.TYPE_DEPARTURE222:
                    mDataBinding.tvValueRegion2.setText(data.getString("name"));
                    break;
                case REQUEST_REMAR:
                    mDataBinding.tvValueRemark.setText(data.getString(EditTextFragment.VALUE));
                    break;
            }
        }
    }

    @Override
    public void addLoadPlace() {
        setFragmentResult(RESULT_OK, new Bundle());
        pop();
    }

    /**
     * 初始化
     */
    private void initInfoClientPlace() {
        // 单位名称
        mDataBinding.etUnitName.setText(ObjectUtils.EmptyIf(infoClientPlace.getConsigneeCompany(), "未知"));
        // 地区
        mDataBinding.tvValueRegion.setText(ObjectUtils.EmptyIf(infoClientPlace.getRegionid(), "未知"));
        // 详细地址
        mDataBinding.etDetailedAddress.setText(ObjectUtils.EmptyIf(infoClientPlace.getAddr(), "未知"));
        // 区域
        if (!TextUtils.isEmpty(infoClientPlace.getArea())) {
            mDataBinding.tvValueRegion2.setText(ObjectUtils.EmptyIf(infoClientPlace.getArea(), "未知"));
        } else {
            if (!TextUtils.isEmpty(mAreaName))
                mDataBinding.tvValueRegion2.setText(ObjectUtils.EmptyIf(mAreaName, "未知")); // 区域
        }
        // 货主
        if (!TextUtils.isEmpty(infoClientPlace.getConsigneeId())) {
            mDataBinding.etCargoOwner.setText(ObjectUtils.EmptyIf(infoClientPlace.getConsigneeId(), "未知"));
        } else {
            if (!TextUtils.isEmpty(mConsigneeId))
                mDataBinding.etCargoOwner.setText(ObjectUtils.EmptyIf(mConsigneeId, "未知"));
        }
        // 联系人
        mDataBinding.etContacts.setText(ObjectUtils.EmptyIf(infoClientPlace.getLinkman(), "未知"));
        // 手机
        mDataBinding.etMobile.setText(ObjectUtils.EmptyIf(infoClientPlace.getLinkmanMp(), "未知"));
        // 电话
        mDataBinding.etPhone.setText(ObjectUtils.EmptyIf(infoClientPlace.getLinkmanTel(), "未知"));
        // 传真
        mDataBinding.etFax.setText(ObjectUtils.EmptyIf(infoClientPlace.getConsigneeCompanyFax(), "未知"));
        // 说明
        mDataBinding.tvValueRemark.setText(ObjectUtils.EmptyIf(infoClientPlace.getZxRemark(), "未知"));

        // codeid
        if (!TextUtils.isEmpty(infoClientPlace.getCodeId())) {
            mCodeId = infoClientPlace.getCodeId();
        }

    }

    @Override
    public void initMapFinish() {
        mAMap.setOnMapLoadedListener(() -> {
            if (infoClientPlace.getLocationArea() != null) {
                site = infoClientPlace.getLocationArea().getSite();
                if (site != null && site.getLat() != null && site.getLon() != null) {
                    // 设置地图
                    LatLng latLng = new LatLng(site.getLat(), site.getLon());
                    movePoint(latLng, false);
                    // 设置标记
                    addMarkerInScreenCenter(latLng,false);
                }
            }
        });
    }

    @Override
    public void onCameraChangeFinish(LatLng latLng) {

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result) {

    }
}
