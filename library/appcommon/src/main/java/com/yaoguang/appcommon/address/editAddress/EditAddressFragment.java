package com.yaoguang.appcommon.address.editAddress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.basemap.BaseMapDataBindFragment;
import com.yaoguang.appcommon.common.base.basemap.CallBack;
import com.yaoguang.appcommon.databinding.FragmentEditAddressBinding;
import com.yaoguang.appcommon.address.editAddress.adapter.EditAddressAdapter;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.appcommon.widget.head.Pull2Header;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.databinding.LayoutRecyclerviewBinding;
import com.yaoguang.map.common.ToastUtil;
import com.yaoguang.map.location.impl.LocationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongjh on 2018/6/21.
 */
public class EditAddressFragment extends BaseMapDataBindFragment<FragmentEditAddressBinding> implements EditAddressContact.View, TextWatcher, Inputtips.InputtipsListener {

    /**
     * 不包括省市区的地址
     */
    public static final String PARAMETER_SPECIFIC_ADDRESS = "SpecificAddress";
    /**
     * 包括省市区的地址
     */
    public static final String PARAMETER_DOOR_ADDRESS = "DoorAddress";
    /**
     * 包含经纬度的地址
     */
    public static final String PARAMETER_POIITEM = "poiItem";
    /**
     * 自定义的半径
     */
    public static final String PARAMETER_RADIUS = "radius";
    /**
     * 自己自定义的地址
     */
    public static final String PARAMETER_CUSTOM_ADDRESS = "customAddress";


    private EditAddressContact.Presenter mPresenter = new EditAddressPresenter(this);
    private LayoutRecyclerviewBinding mLayoutRecyclerviewBinding;
    private EditAddressAdapter mNodeEditAddressAdapter;// 顶部搜索
    private EditAddressAdapter mNodeEditAddressPOIAdapter; // 周边搜索

    List<Tip> mTips = new ArrayList<>();// 探索数据
    List<PoiItem> mPoiItems = new ArrayList<>();// 周边数据

    LatLng mLatLng;
    String mTitle;
    boolean mIsRadius;
    String mHint;
    boolean mIsEditAddress;

    @Override
    protected void aMapinit(TextureMapView mapView, Bundle savedInstanceState) {
        isMonitor = false;
        super.aMapinit(mapView, savedInstanceState);
    }

    /**
     * @param latLng 精确的地址
     * @param title  顶部的搜索的内容
     */
    public static EditAddressFragment newInstance(LatLng latLng, String title) {
        EditAddressFragment editAddressFragment = new EditAddressFragment();
        Bundle bundle = new Bundle();
        if (latLng != null) {
            bundle.putParcelable("latLng", latLng);
        }
        if (!TextUtils.isEmpty(title)) {
            bundle.putString("title", title.replace(" ", ""));
        }
        editAddressFragment.setArguments(bundle);

        return editAddressFragment;
    }

    /**
     * @param latLng        精确的地址
     * @param title         顶部的搜索的内容
     * @param title         顶部的搜索的hint
     * @param isRadius      是否开启半径，并且地址文字可以自己自定义
     * @param isEditAddress 地址文字是否可以自己自定义
     */
    public static EditAddressFragment newInstance(LatLng latLng, String title, String hint, boolean isRadius, boolean isEditAddress) {
        EditAddressFragment editAddressFragment = new EditAddressFragment();
        Bundle bundle = new Bundle();
        if (latLng != null) {
            bundle.putParcelable("latLng", latLng);
        }
        if (!TextUtils.isEmpty(title)) {
            bundle.putString("title", title.replace(" ", ""));
        }
        if (!TextUtils.isEmpty(hint)) {
            bundle.putString("hint", hint);
        }
        if (isRadius) {
            bundle.putBoolean("isRadius", isRadius);
        }
        if (isEditAddress) {
            bundle.putBoolean("isEditAddress", isEditAddress);
        }

        editAddressFragment.setArguments(bundle);

        return editAddressFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_edit_address;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void init() {
        // 不跟随当前位置移动
        isFollowMove = false;
        Bundle bundle = getArguments();
        if (bundle != null) {
            mLatLng = bundle.getParcelable("latLng");
            mTitle = bundle.getString("title");
            mIsRadius = bundle.getBoolean("isRadius");
            mHint = bundle.getString("hint");
            mIsEditAddress = bundle.getBoolean("isEditAddress");
        }

        if (!TextUtils.isEmpty(mHint)) {
            mDataBinding.etSearch.setHint(mHint);
        }

        // 加载搜索列表
        mNodeEditAddressAdapter = new EditAddressAdapter();
        mLayoutRecyclerviewBinding = DataBindingUtil.bind(mDataBinding.getRoot().findViewById(R.id.layoutRecyclerview));
        mLayoutRecyclerviewBinding.rlView.setAdapter(mNodeEditAddressAdapter);
        RecyclerViewUtils.initPage(mLayoutRecyclerviewBinding.rlView, mNodeEditAddressAdapter, null, getContext(), true);
        // 关闭刷新和下一页
        mLayoutRecyclerviewBinding.refreshLayout.setEnableLoadMore(false);
        mLayoutRecyclerviewBinding.refreshLayout.setEnableRefresh(false);

        // 加载探索周围的列表
        mNodeEditAddressPOIAdapter = new EditAddressAdapter();
        mDataBinding.rlPOIView.setAdapter(mNodeEditAddressPOIAdapter);
        mDataBinding.refreshLayout.setRefreshHeader(new Pull2Header(getContext()));// 加载头部
        RecyclerViewUtils.initPage(mDataBinding.rlPOIView, mNodeEditAddressPOIAdapter, null, getContext(), true);

        // 是否显示自定义层
        if (mIsRadius) {
            mDataBinding.rlAddress.setVisibility(View.VISIBLE);
        }
        if (mIsEditAddress) {
            mDataBinding.imgRadius.setVisibility(View.VISIBLE);
            mDataBinding.imgEdit.setVisibility(View.VISIBLE);
            mDataBinding.etAddress.setEnabled(true);
            initSweetSheets(mDataBinding.llRadius.getId(), mDataBinding.flMainMain, "选择半径", R.menu.sheet_radius, (position, menuEntity) -> {
                mDataBinding.tvRadius.setText(menuEntity.title.toString() + "m");
                return true;
            });
        } else {
            // 设置不可编辑
            mDataBinding.imgRadius.setVisibility(View.GONE);
            mDataBinding.imgEdit.setVisibility(View.GONE);
            mDataBinding.etAddress.setEnabled(false);
            mDataBinding.etAddress.setText("有效半径范围");
            mDataBinding.tvRadius.setText("500m");
        }
    }

    @Override
    public void initListener() {

        // 返回
        mDataBinding.imgReturn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                pop();
            }
        });

        mDataBinding.etSearch.addTextChangedListener(this);

        // 查询搜索列表事件
        mNodeEditAddressAdapter.setOnItemClickListener((itemView, item, position) -> {
            if (mTips.get(position).getPoint() != null) {
                // 移动坐标
                LatLng latLng = new LatLng(mTips.get(position).getPoint().getLatitude(), mTips.get(position).getPoint().getLongitude());
                movePoint(latLng, false);

                mDataBinding.refreshLayout.autoRefresh(1, 1, 1.75f);
                doSearchQuery("", latLng);

                // 显示地图
                mLayoutRecyclerviewBinding.rlView.setVisibility(View.GONE);
                mDataBinding.flMain.setVisibility(View.GONE);
                mDataBinding.map.setVisibility(View.VISIBLE);
                mDataBinding.llBottom.setVisibility(View.VISIBLE);

                // 取消的按钮变成确定的按钮
                mDataBinding.tvComit.setVisibility(View.VISIBLE);
                mDataBinding.tvCancel.setVisibility(View.GONE);
            } else {
                Toast.makeText(BaseApplication.getInstance(), "找不到地址", Toast.LENGTH_SHORT).show();
            }
        });

        // 周边搜索 列表点击事件
        mNodeEditAddressPOIAdapter.setOnItemClickListener((itemView, item, position) -> {
            // 移动坐标
            LatLng latLng = new LatLng(mPoiItems.get(position).getLatLonPoint().getLatitude(), mPoiItems.get(position).getLatLonPoint().getLongitude());
            movePoint(latLng, false);
            mNodeEditAddressPOIAdapter.notifyDataSetChanged();

            // 显示自定义地址
            if (mIsRadius) {
                // 判断有没有副标题，没有副标题就取括号里面的
                if (TextUtils.isEmpty(mPoiItems.get(position).getSnippet())) {
                    // 取主题
                    mDataBinding.etAddress.setText(mPoiItems.get(position).getTitle().substring(mPoiItems.get(position).getTitle().indexOf("(") + 1, mPoiItems.get(position).getTitle().indexOf(")")));
                } else {
                    mDataBinding.etAddress.setText(mPoiItems.get(position).getSnippet());
                }
            }

            // 缩小软键盘
            hideKeyboard();

        });

        // 提交
        mDataBinding.tvComit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                Bundle bundle = new Bundle();
                // 返回当前mpoi所选择的item
                for (int i = 0; i < mNodeEditAddressPOIAdapter.isChecks.size(); i++) {
                    if (mNodeEditAddressPOIAdapter.isChecks.get(i)) {
                        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(mPoiItems.get(i).getLatLonPoint(), 200,
                                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                        GeocodeSearch geocoderSearch = new GeocodeSearch(_mActivity);
                        int finalI = i;
                        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {

                            /**
                             * 逆地理编码回调
                             */
                            @Override
                            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                                if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                                    if (result != null && result.getRegeocodeAddress() != null
                                            && result.getRegeocodeAddress().getFormatAddress() != null) {
                                        mPoiItems.get(finalI).setCityName(result.getRegeocodeAddress().getFormatAddress());
                                        bundle.putParcelable(PARAMETER_POIITEM, mPoiItems.get(finalI));
                                        bundle.putString(PARAMETER_SPECIFIC_ADDRESS, result.getRegeocodeAddress().getFormatAddress());
                                        bundle.putString(PARAMETER_DOOR_ADDRESS, result.getRegeocodeAddress().getFormatAddress().
                                                replace(result.getRegeocodeAddress().getProvince(), "").
                                                replace(result.getRegeocodeAddress().getCity(), "").
                                                replace(result.getRegeocodeAddress().getDistrict(), ""));
                                        // 如果是自定义的，传输额外两个值
                                        if (mIsEditAddress) {
                                            bundle.putString(PARAMETER_RADIUS, mDataBinding.tvRadius.getText().toString().replace("m", ""));
                                            bundle.putString(PARAMETER_CUSTOM_ADDRESS, mDataBinding.etAddress.getText().toString());
                                        }
                                        setFragmentResult(RESULT_OK, bundle);
                                        pop();
                                    } else {
                                        ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), com.yaoguang.appcommon.R.string.no_result);
                                    }
                                } else {
                                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), rCode);
                                }
                            }

                            /**
                             * 地理编码查询回调
                             */
                            @Override
                            public void onGeocodeSearched(GeocodeResult result, int rCode) {

                            }
                        });
                        geocoderSearch.getFromLocationAsyn(regeocodeQuery);// 设置异步逆地理编码请求
                        return;
                    }
                }
            }
        });

        // 关闭搜索
        mDataBinding.tvCancel.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                // 显示地图，隐藏搜索列表
                mLayoutRecyclerviewBinding.rlView.setVisibility(View.GONE);
                mDataBinding.flMain.setVisibility(View.GONE);
                mDataBinding.map.setVisibility(View.VISIBLE);
                mDataBinding.llBottom.setVisibility(View.VISIBLE);

                // 取消的按钮变成确定的按钮
                mDataBinding.tvComit.setVisibility(View.VISIBLE);
                mDataBinding.tvCancel.setVisibility(View.GONE);
            }
        });

        // 赋值文本内容
        mDataBinding.etSearch.setText(mTitle);

        // 选择附近范围
        mDataBinding.llRadius.setOnClickListener(v -> showSweetSheets(mDataBinding.llRadius.getId()));
        // 设置点击范围
        mDataBinding.imgEdit.setOnClickListener(v -> {
            mDataBinding.etAddress.setFocusable(true);
            mDataBinding.etAddress.setFocusableInTouchMode(true);
            mDataBinding.etAddress.requestFocus();
            InputMethodManager imm = (InputMethodManager) _mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        });
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    /**
     * 输入提示结果的回调
     *
     * @param tipList 数据源
     * @param rCode   代码
     */
    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            // 隐藏地图，显示搜索列表
            mDataBinding.flMain.setVisibility(View.VISIBLE);
            mDataBinding.map.setVisibility(View.GONE);
            mDataBinding.llBottom.setVisibility(View.GONE);
            mDataBinding.tvComit.setVisibility(View.GONE);
            mDataBinding.tvCancel.setVisibility(View.VISIBLE);
            mLayoutRecyclerviewBinding.loading.showContent();
            if (tipList != null) {
                mNodeEditAddressAdapter.clear();
                mTips = tipList;
                mNodeEditAddressAdapter.appendToList(tipList);
                mNodeEditAddressAdapter.notifyDataSetChanged();
            } else {
                // 显示地图，隐藏搜索列表
                mLayoutRecyclerviewBinding.rlView.setVisibility(View.GONE);
                mDataBinding.flMain.setVisibility(View.GONE);
                mDataBinding.map.setVisibility(View.VISIBLE);
                mDataBinding.llBottom.setVisibility(View.VISIBLE);
                mDataBinding.tvComit.setVisibility(View.VISIBLE);
                mDataBinding.tvCancel.setVisibility(View.GONE);
            }
        } else {
            if (TextUtils.isEmpty(mDataBinding.etSearch.getText().toString())) {
                // 显示地图，隐藏搜索列表
                mLayoutRecyclerviewBinding.rlView.setVisibility(View.GONE);
                mDataBinding.flMain.setVisibility(View.GONE);
                mDataBinding.map.setVisibility(View.VISIBLE);
                mDataBinding.llBottom.setVisibility(View.VISIBLE);
                mDataBinding.tvComit.setVisibility(View.VISIBLE);
                mDataBinding.tvCancel.setVisibility(View.GONE);
            } else {
                // 隐藏地图，显示搜索列表
                mLayoutRecyclerviewBinding.rlView.setVisibility(View.GONE);
                mDataBinding.flMain.setVisibility(View.GONE);
                mDataBinding.tvComit.setVisibility(View.GONE);
                mDataBinding.tvCancel.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        InputtipsQuery inputquery = new InputtipsQuery(newText, "");
        inputquery.setCityLimit(true);
        Inputtips inputTips = new Inputtips(_mActivity, inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void initMapFinish() {
        setZoomControlsEnabled(true);
//                // 设置该地图的缩放最大最小等级
//                mAMap.setMaxZoomLevel(17f);
//                mAMap.setMinZoomLevel(17f);

        mAMap.setOnMapLoadedListener(() -> {
            if (mLatLng == null || (mLatLng.latitude == 0 && mLatLng.longitude == 0)) {
                // 获取当前位置
                LocationManager locationManager = new LocationManager();
                locationManager.init(BaseApplication.getInstance().getBaseContext());
                locationManager.setComeback(location -> {
                    locationManager.destroyLocation();
                    mLatLng = new LatLng(location.getLat(), location.getLon());
                    // 切换到某个镜头
                    movePoint(mLatLng, false);

                    // 初始加载添加可移动的坐标
                    addMarkerInScreenCenter(null, true);

                    // 周边数据获取
                    LatLng latLng = screenMarker.getPosition();
                    mDataBinding.refreshLayout.autoRefresh(1, 1, 1.75f);
                    doSearchQuery("", mLatLng == null ? latLng : mLatLng);
                });
            } else {
                // 切换到某个镜头
                movePoint(mLatLng, false);

                // 初始加载添加可移动的坐标
                addMarkerInScreenCenter(null, true);

                // 周边数据获取
                LatLng latLng = screenMarker.getPosition();
                mDataBinding.refreshLayout.autoRefresh(1, 1, 1.75f);
                doSearchQuery("", mLatLng == null ? latLng : mLatLng);


            }
        });

        // 设置可视范围变化时的回调的接口方法
        mAMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition postion) {
                //屏幕中心的Marker跳动
                startJumpAnimation();
            }
        });
    }

    @Override
    public void onCameraChangeFinish(LatLng latLng) {
        // 判断latlng是否一样，如果一样则不需要再次获取
        if (!(mLatLng.longitude == latLng.longitude && mLatLng.latitude == latLng.latitude)){
            mLatLng = new LatLng(latLng.latitude,latLng.longitude);
            // 周边数据获取
            mDataBinding.refreshLayout.autoRefresh(1, 1, 1.75f);
            doSearchQuery("", latLng);
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result) {
        mDataBinding.refreshLayout.finishRefresh(1);
        mPoiItems = result.getRegeocodeAddress().getPois();
        // 如果result.getRegeocodeAddress().getNeighborhood()没有值，则取周边的第一个
        String title = "";
        if (TextUtils.isEmpty(result.getRegeocodeAddress().getNeighborhood())) {
            if (mPoiItems.size() > 0) {
                title = mPoiItems.get(0).getTitle();
            }
        } else {
            title = result.getRegeocodeAddress().getNeighborhood();
        }
        PoiItem poiItemTop = new PoiItem(""
                , result.getRegeocodeQuery().getPoint(),
                title + "(" + result.getRegeocodeAddress().getStreetNumber().getStreet() + result.getRegeocodeAddress().getStreetNumber().getNumber() + ")", "");
        poiItemTop.setCityName(result.getRegeocodeAddress().getFormatAddress());
        mPoiItems.add(0, poiItemTop);

        ArrayList<Tip> tips = new ArrayList<>();
        for (PoiItem poiItem : result.getRegeocodeAddress().getPois()) {
            Tip tip = new Tip();
            tip.setName(poiItem.getTitle());
            tip.setDistrict(poiItem.getSnippet());
            tips.add(tip);
        }
        mNodeEditAddressPOIAdapter.clear();
        mNodeEditAddressPOIAdapter.appendToList(tips, true);
        mNodeEditAddressPOIAdapter.notifyDataSetChanged();

        // 显示自定义地址
        if (mIsEditAddress) {
            // 判断有没有副标题，没有副标题就取括号里面的
            if (TextUtils.isEmpty(mPoiItems.get(0).getSnippet())) {
                // 取主题
                mDataBinding.etAddress.setText(mPoiItems.get(0).getTitle().substring(mPoiItems.get(0).getTitle().indexOf("(") + 1, mPoiItems.get(0).getTitle().indexOf(")")));
            } else {
                mDataBinding.etAddress.setText(mPoiItems.get(0).getSnippet());
            }
        }

        LatLng latLng = new LatLng(mPoiItems.get(0).getLatLonPoint().getLatitude(), mPoiItems.get(0).getLatLonPoint().getLongitude());
        if (mIsRadius) {
            // 画圆圈提示范围
            drawCircle(ObjectUtils.parseDouble(mDataBinding.tvRadius.getText().toString().replace("m", "")), latLng);
        }
    }
}
