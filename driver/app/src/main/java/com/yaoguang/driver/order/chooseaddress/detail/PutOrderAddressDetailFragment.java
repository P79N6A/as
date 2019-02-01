package com.yaoguang.driver.order.chooseaddress.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.common.base.BaseFragment;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.ULog;
import com.yaoguang.driver.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.order.map.MapFragment;
import com.yaoguang.greendao.entity.InfoPutorderPlace;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 选择存放订单地址详情
 * Created by wly on 2017/12/28 0028.
 */

public class PutOrderAddressDetailFragment extends BaseFragment {

    private static final String INFO_PUT_ORDER_PLACE = "info_put_order_place";
    @BindView(R.id.toolbar_left)
    ImageView toolbarLeft;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    ImageView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.map)
    TextureMapView map;
    @BindView(R.id.fragmentLayout)
    FrameLayout fragmentLayout;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvMobile)
    TextView tvMobile;
    @BindView(R.id.tvAddress)
    TextView tvAddress;

    private MapFragment mMapFragment;
    private InfoPutorderPlace mInfoPutorderPlace;
    private Unbinder unbinder;

    public static PutOrderAddressDetailFragment newInstance(InfoPutorderPlace place) {
        Bundle args = new Bundle();
        args.putParcelable(INFO_PUT_ORDER_PLACE, place);
        PutOrderAddressDetailFragment fragment = new PutOrderAddressDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().getParcelable(INFO_PUT_ORDER_PLACE) != null)
            mInfoPutorderPlace = getArguments().getParcelable(INFO_PUT_ORDER_PLACE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(INFO_PUT_ORDER_PLACE, mInfoPutorderPlace);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_order_address_detail, null);
        unbinder = ButterKnife.bind(this, view);

        if (savedInstanceState != null)
            mInfoPutorderPlace = savedInstanceState.getParcelable(INFO_PUT_ORDER_PLACE);
        // 加载地图
        mMapFragment = MapFragment.newInstance(false);
        getChildFragmentManager().beginTransaction().add(R.id.fragmentLayout, mMapFragment).show(mMapFragment).commit();

        toolbarTitle.setText("放单地点详情");
        // 名称
        if (!TextUtils.isEmpty(mInfoPutorderPlace.getName()))
            tvName.setText(mInfoPutorderPlace.getName());
        // 地址
        if (!TextUtils.isEmpty(mInfoPutorderPlace.getAddress()))
            tvAddress.setText(mInfoPutorderPlace.getAddress());
        // 电话
        if (!TextUtils.isEmpty(mInfoPutorderPlace.getPhone()))
            tvMobile.setText(mInfoPutorderPlace.getPhone());

        tvMobile.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick() || TextUtils.isEmpty(mInfoPutorderPlace.getPhone())) return;
            // 打电话
            PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{mInfoPutorderPlace.getPhone()});
        });

        // 设置经纬度，地图需要加载，而且在onActivityCreated中初始化
        // 需要初始化完成，才能操作，所有加了半秒的等待
        App.handler.postDelayed(() -> {
            if (mInfoPutorderPlace.getLatitude() != 0 && mInfoPutorderPlace.getLongitude() != 0 ) {
                LatLng latlng = new LatLng(mInfoPutorderPlace.getLatitude(), mInfoPutorderPlace.getLongitude());
                try {
                    mMapFragment.movePoint(latlng);
                    mMapFragment.addMarkersToMap(latlng, mInfoPutorderPlace.getName(), mInfoPutorderPlace.getAddress());
                } catch (Exception e) {
                    ULog.e("PutOrderAddressDetailFragment 等待地图超时！");
                    e.printStackTrace();
                }
            }
        }, 300);

        toolbarLeft.setOnClickListener(v -> pop());
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapFragment.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
