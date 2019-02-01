package com.yaoguang.appcommon.address.addressDetail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.basemap.BaseMapDataBindFragment;
import com.yaoguang.appcommon.common.base.basemap.CallBack;
import com.yaoguang.appcommon.databinding.FragmentAddressDetailBinding;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.map.common.AMapUtil;
import com.yaoguang.map.common.ToastUtil;

import java.util.List;

/**
 * 地址详情
 * Created by zhongjh on 2018/12/25.
 */
public class AddressDetailFragment extends BaseMapDataBindFragment<FragmentAddressDetailBinding> {

    String mTitle;
    String mAddress;
    int mRadius;

    @Override
    protected void aMapinit(TextureMapView mapView, Bundle savedInstanceState) {
        isMonitor = false;
        super.aMapinit(mapView, savedInstanceState);
    }

    public static AddressDetailFragment newInstance(LatLng latLng, String title, int radius) {
        AddressDetailFragment addressDetailFragment = new AddressDetailFragment();
        Bundle bundle = new Bundle();
        if (latLng != null) {
            bundle.putParcelable("latLng", latLng);
        }
        if (!TextUtils.isEmpty(title)) {
            bundle.putString("title", title);
        }
        if (radius != 0) {
            bundle.putInt("radius", radius);
        }
        addressDetailFragment.setArguments(bundle);

        return addressDetailFragment;
    }

    public static AddressDetailFragment newInstance(String address, String title, int radius) {
        AddressDetailFragment addressDetailFragment = new AddressDetailFragment();
        Bundle bundle = new Bundle();
        if (address != null) {
            bundle.putString("address", address);
        }
        if (!TextUtils.isEmpty(title)) {
            bundle.putString("title", title);
        }
        if (radius != 0) {
            bundle.putInt("radius", radius);
        }
        addressDetailFragment.setArguments(bundle);

        return addressDetailFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_address_detail;
    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "地理位置详情", -1, null);
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            mLatLng = bundle.getParcelable("latLng");
            mAddress = bundle.getString("address");
            mTitle = bundle.getString("title");
            mRadius = bundle.getInt("radius");
        }

        mDataBinding.tvAddress.setText(mTitle);
        if (mRadius != -1)
            mDataBinding.tvRadius.setText("附近" + mRadius + "m");
    }

    @Override
    public void initListener() {
        // 先判断具体坐标有没有，没有就分析address
        if (mLatLng == null) {
            PoiSearch.Query query = new PoiSearch.Query(mAddress, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
            query.setPageSize(10);// 设置每页最多返回多少条poiitem
            query.setPageNum(0);// 设置查第一页
            PoiSearch poiSearch = new PoiSearch(this.getContext(), query);
            poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                @Override
                public void onPoiSearched(PoiResult poiResult, int rCode) {
                    if (rCode == 1000) {
                        if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                            if (poiResult.getQuery().equals(query)) {// 是否是同一条
                                // 取得搜索到的poiitems有多少页
                                List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                                List<SuggestionCity> suggestionCities = poiResult
                                        .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                                if (poiItems != null && poiItems.size() > 0) {
                                    mLatLng = new LatLng(poiItems.get(0).getLatLonPoint().getLatitude(), poiItems.get(0).getLatLonPoint().getLongitude());
                                    mDataBinding.map.onResume();// 恢复使用地图
                                    initLatLng();
                                } else if (suggestionCities != null
                                        && suggestionCities.size() > 0) {
                                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), R.string.no_result);
                                } else {
                                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), R.string.no_result);
                                }
                            }
                        } else {
                            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), R.string.no_result);
                        }
                    } else {
                        ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "获取地址出错");
                    }
                }

                @Override
                public void onPoiItemSearched(PoiItem poiItem, int i) {

                }
            });
            poiSearch.searchPOIAsyn();
        } else {
            initLatLng();
        }

        mDataBinding.cvAddress.setOnClickListener(new NoDoubleClickListener() {

            @Override
            public void onNoDoubleClick(View v) {
                if (mLatLng != null)
                    AMapUtil.startAMapNavi(getContext(), mLatLng);
            }
        });
    }

    /**
     * 加载地图
     */
    private void initLatLng() {
        mDataBinding.map.getMap().setOnMapLoadedListener(() -> {
            if (mLatLng != null) {
                // 切换到某个镜头
                movePoint(mLatLng, false);
            }
        });

        // 初始加载添加可移动的坐标
        addMarkerInScreenCenter(mLatLng, false);
        if (mRadius != -1)
            // 画圆圈提示范围
            drawCircle(mRadius, mLatLng);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initMapFinish() {

    }

    @Override
    public void onCameraChangeFinish(LatLng latLng) {

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result) {

    }
}
