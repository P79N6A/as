//package com.yaoguang.driver.phone.order2.nodeEdit.nodeEditAddress;
//
//import android.databinding.DataBindingUtil;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.View;
//
//import com.amap.api.maps.AMap;
//import com.amap.api.maps.model.CameraPosition;
//import com.amap.api.maps.model.LatLng;
//import com.amap.api.services.core.AMapException;
//import com.amap.api.services.core.PoiItem;
//import com.amap.api.services.geocoder.RegeocodeResult;
//import com.amap.api.services.help.Inputtips;
//import com.amap.api.services.help.InputtipsQuery;
//import com.amap.api.services.help.Tip;
//import com.yaoguang.appcommon.common.base.basemap.CallBack;
//import com.yaoguang.driver.R;
//import com.yaoguang.driver.databinding.FragmentNodeEditAddressSearchBinding;
//import com.yaoguang.driver.phone.order2.chooseaddress.MapFragment;
//import com.yaoguang.driver.phone.order2.nodeEdit.nodeEditAddress.adapter.EditAddressAdapter;
//import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
//import com.yaoguang.lib.appcommon.widget.head.Pull2Header;
//import com.yaoguang.lib.base.BaseFragmentDataBind;
//import com.yaoguang.lib.base.interfaces.BasePresenter;
//import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
//import com.yaoguang.lib.databinding.LayoutRecyclerviewBinding;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 地址编辑
// * Created by zhongjh on 2018/6/8.
// */
//public class NodeEditAddressFragment extends BaseFragmentDataBind<FragmentNodeEditAddressSearchBinding> implements EditAddressContact.View, TextWatcher, Inputtips.InputtipsListener {
//
//    private EditAddressContact.Presenter mPresenter = new EditAddressPresenter(this);
//    private LayoutRecyclerviewBinding mLayoutRecyclerviewBinding;
//    private EditAddressAdapter mNodeEditAddressAdapter;// 顶部搜索
//    private EditAddressAdapter mNodeEditAddressPOIAdapter; // 周边搜索
//
//    // 地图
//    MapFragment mMapFragment;
//
//    List<Tip> mTips = new ArrayList<>();// 探索数据
//    List<PoiItem> mPoiItems = new ArrayList<>();// 周边数据
//
//    public static NodeEditAddressFragment newInstance() {
//        return new NodeEditAddressFragment();
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_node_edit_address_search;
//    }
//
//    @Override
//    public void init() {
//        // 加载搜索列表
//        mNodeEditAddressAdapter = new EditAddressAdapter();
//        mLayoutRecyclerviewBinding = DataBindingUtil.bind(mDataBinding.getRoot().findViewById(R.id.layoutRecyclerview));
//        mLayoutRecyclerviewBinding.rlView.setAdapter(mNodeEditAddressAdapter);
//        RecyclerViewUtils.initPage(mLayoutRecyclerviewBinding.rlView, mNodeEditAddressAdapter, null, getContext(), true);
//        // 关闭刷新和下一页
//        mLayoutRecyclerviewBinding.trlView.setAutoLoadMore(false);
//        mLayoutRecyclerviewBinding.trlView.setEnableRefresh(false);
//
//        // 加载探索周围的列表
//        mNodeEditAddressPOIAdapter = new EditAddressAdapter();
//        mDataBinding.rlPOIView.setAdapter(mNodeEditAddressPOIAdapter);
//        mDataBinding.refreshLayout.setRefreshHeader(new Pull2Header(getContext()));// 加载头部
//        RecyclerViewUtils.initPage(mDataBinding.rlPOIView, mNodeEditAddressPOIAdapter, null, getContext(), true);
//
//        // 加载地图
//        mMapFragment = MapFragment.newInstance(false);
//        mMapFragment.setCallBack(new CallBack() {
//            @Override
//            public void initMapFinish() {
//                mMapFragment.mAMap.setOnMapLoadedListener(() -> mMapFragment.addMarkerInScreenCenter());
//
//                // 设置可视范围变化时的回调的接口方法
//                mMapFragment.mAMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
//                    @Override
//                    public void onCameraChange(CameraPosition position) {
//
//                    }
//
//                    @Override
//                    public void onCameraChangeFinish(CameraPosition postion) {
//                        //屏幕中心的Marker跳动
//                        mMapFragment.startJumpAnimation();
//                    }
//                });
//
//                // 设置该地图的缩放最大最小等级
//                mMapFragment.mAMap.setMaxZoomLevel(17f);
//                mMapFragment.mAMap.setMinZoomLevel(17f);
//            }
//
//            @Override
//            public void onCameraChangeFinish(LatLng latLng) {
//                mDataBinding.refreshLayout.autoRefresh(1, 1, 1.75f);
//                mMapFragment.doSearchQuery("", latLng);
//            }
//
//            @Override
//            public void onRegeocodeSearched(RegeocodeResult result) {
//                mDataBinding.refreshLayout.finishRefresh(1);
//                mPoiItems = result.getRegeocodeAddress().getPois();
//                // 如果result.getRegeocodeAddress().getNeighborhood()没有值，则取周边的第一个
//                String title = "";
//                if (TextUtils.isEmpty(result.getRegeocodeAddress().getNeighborhood())) {
//                    if (mPoiItems.size() > 0) {
//                        title = mPoiItems.get(0).getTitle();
//                    }
//                } else {
//                    title = result.getRegeocodeAddress().getNeighborhood();
//                }
//                PoiItem poiItemTop = new PoiItem(""
//                        , result.getRegeocodeQuery().getPoint(),
//                        title + "(" + result.getRegeocodeAddress().getStreetNumber().getStreet() + result.getRegeocodeAddress().getStreetNumber().getNumber() + ")", "");
//                mPoiItems.add(0, poiItemTop);
//
//                ArrayList<Tip> tips = new ArrayList<>();
//                for (PoiItem poiItem : result.getRegeocodeAddress().getPois()) {
//                    Tip tip = new Tip();
//                    tip.setName(poiItem.getTitle());
//                    tip.setDistrict(poiItem.getSnippet());
//                    tips.add(tip);
//                }
//                mNodeEditAddressPOIAdapter.clear();
//                mNodeEditAddressPOIAdapter.appendToList(tips, true);
//                mNodeEditAddressPOIAdapter.notifyDataSetChanged();
//            }
//        });
//        getChildFragmentManager().beginTransaction().add(R.id.flMap, mMapFragment).show(mMapFragment).commit();
//
//    }
//
//    @Override
//    public void initListener() {
//        mDataBinding.etSearch.addTextChangedListener(this);
//
//        // 查询搜索列表事件
//        mNodeEditAddressAdapter.setOnItemClickListener((itemView, item, position) -> {
//            // 移动坐标
//            LatLng latLng = new LatLng(mTips.get(position).getPoint().getLatitude(), mTips.get(position).getPoint().getLongitude());
//            mMapFragment.movePoint(latLng, false);
//
//            mDataBinding.refreshLayout.autoRefresh(1, 1, 1.75f);
//            mMapFragment.doSearchQuery("", latLng);
//
//            // 显示地图
//            mLayoutRecyclerviewBinding.rlView.setVisibility(View.GONE);
//            mDataBinding.flMain.setVisibility(View.GONE);
//            mDataBinding.flMap.setVisibility(View.VISIBLE);
//            mDataBinding.llBottom.setVisibility(View.VISIBLE);
//
//        });
//
//        // 周边搜索 列表点击事件
//        mNodeEditAddressPOIAdapter.setOnItemClickListener((itemView, item, position) -> {
//            // 移动坐标
//            LatLng latLng = new LatLng(mPoiItems.get(position).getLatLonPoint().getLatitude(), mPoiItems.get(position).getLatLonPoint().getLongitude());
//            mMapFragment.movePoint(latLng, false);
//            mNodeEditAddressPOIAdapter.notifyDataSetChanged();
//        });
//
//        // 提交
//        mDataBinding.tvComit.setOnClickListener(new NoDoubleClickListener() {
//            @Override
//            public void onNoDoubleClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("poiItem", mPoiItems.size() > 0 ? mPoiItems.get(0) : null);
//                setFragmentResult(RESULT_OK, bundle);
//                pop();
//            }
//        });
//
//        // 关闭搜索
//        mDataBinding.tvCancel.setOnClickListener(new NoDoubleClickListener() {
//            @Override
//            public void onNoDoubleClick(View v) {
//                // 显示地图，隐藏搜索列表
//                mLayoutRecyclerviewBinding.rlView.setVisibility(View.GONE);
//                mDataBinding.flMain.setVisibility(View.GONE);
//                mDataBinding.flMap.setVisibility(View.VISIBLE);
//                mDataBinding.llBottom.setVisibility(View.VISIBLE);
//                mDataBinding.tvComit.setVisibility(View.VISIBLE);
//                mDataBinding.tvCancel.setVisibility(View.GONE);
//            }
//        });
//
//
//    }
//
//    @Override
//    public BasePresenter getPresenter() {
//        return mPresenter;
//    }
//
//    /**
//     * 输入提示结果的回调
//     *
//     * @param tipList 数据源
//     * @param rCode   代码
//     */
//    @Override
//    public void onGetInputtips(List<Tip> tipList, int rCode) {
//        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
//            // 隐藏地图，显示搜索列表
//            mDataBinding.flMain.setVisibility(View.VISIBLE);
//            mDataBinding.flMap.setVisibility(View.GONE);
//            mDataBinding.llBottom.setVisibility(View.GONE);
//            mDataBinding.tvComit.setVisibility(View.GONE);
//            mDataBinding.tvCancel.setVisibility(View.VISIBLE);
//            mLayoutRecyclerviewBinding.rlView.setVisibility(View.VISIBLE);
//            mLayoutRecyclerviewBinding.layoutRecyclerviewEmptyError.rlError.setVisibility(View.GONE);
//            if (tipList != null) {
//                mNodeEditAddressAdapter.clear();
//                mTips = tipList;
//                mNodeEditAddressAdapter.appendToList(tipList);
//                mNodeEditAddressAdapter.notifyDataSetChanged();
//            } else {
//                // 显示地图，隐藏搜索列表
//                mLayoutRecyclerviewBinding.rlView.setVisibility(View.GONE);
//                mDataBinding.flMain.setVisibility(View.GONE);
//                mDataBinding.flMap.setVisibility(View.VISIBLE);
//                mDataBinding.llBottom.setVisibility(View.VISIBLE);
//                mDataBinding.tvComit.setVisibility(View.VISIBLE);
//                mDataBinding.tvCancel.setVisibility(View.GONE);
//            }
//        } else {
//            if (TextUtils.isEmpty(mDataBinding.etSearch.getText().toString())) {
//                // 显示地图，隐藏搜索列表
//                mLayoutRecyclerviewBinding.rlView.setVisibility(View.GONE);
//                mDataBinding.flMain.setVisibility(View.GONE);
//                mDataBinding.flMap.setVisibility(View.VISIBLE);
//                mDataBinding.llBottom.setVisibility(View.VISIBLE);
//                mDataBinding.tvComit.setVisibility(View.VISIBLE);
//                mDataBinding.tvCancel.setVisibility(View.GONE);
//            } else {
//                // 隐藏地图，显示搜索列表
//                mLayoutRecyclerviewBinding.rlView.setVisibility(View.GONE);
//                mDataBinding.flMain.setVisibility(View.GONE);
//                mDataBinding.tvComit.setVisibility(View.GONE);
//                mDataBinding.tvCancel.setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        String newText = s.toString().trim();
//        InputtipsQuery inputquery = new InputtipsQuery(newText, "");
//        inputquery.setCityLimit(true);
//        Inputtips inputTips = new Inputtips(_mActivity, inputquery);
//        inputTips.setInputtipsListener(this);
//        inputTips.requestInputtipsAsyn();
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//
//    }
//
//}
