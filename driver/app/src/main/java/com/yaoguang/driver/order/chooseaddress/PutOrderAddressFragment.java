package com.yaoguang.driver.order.chooseaddress;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.model.LatLng;
import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.SpanUtils;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.driver.databinding.ToolbarCommonBinding;
import com.yaoguang.driver.R;
import com.yaoguang.driver.base.baseview.BaseFragmentListV2;
import com.yaoguang.driver.databinding.FragmentChooseOrderAddressBinding;
import com.yaoguang.driver.order.chooseaddress.adapter.PutOrderAddressAdapter;
import com.yaoguang.driver.order.map.MapFragment;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.Order;

import java.util.ArrayList;

import static com.yaoguang.driver.order.child.OrderChildFragment.ORDER_ACCEPT;

/**
 * 选择订单存放地点
 * Created by wly on 2017/12/28 0028.
 */

public class PutOrderAddressFragment extends BaseFragmentListV2<InfoPutorderPlace, PutOrderAddressPresenter, FragmentChooseOrderAddressBinding> {
    public static final String ORDER = "order";
    public static final String POSITION = "position";
    public static final String PLACE_ID = "place_id";
    private static String INFO_PUT_ORDER_PLACES = "info_put_order_places";

    PutOrderAddressAdapter mPutOrderAddressAdapter;

    private ArrayList<InfoPutorderPlace> mInfoPutorderPlaces;
    private Order mOrder;
    private int mPosition;
    private String mPlaceId;

    MapFragment mMapFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().getParcelable(ORDER) != null) {
            mOrder = getArguments().getParcelable(ORDER);
            mPosition = getArguments().getInt(POSITION);
            mInfoPutorderPlaces = getArguments().getParcelableArrayList(INFO_PUT_ORDER_PLACES);
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mOrder = savedInstanceState.getParcelable(ORDER);
            mPosition = savedInstanceState.getInt(POSITION);
            mInfoPutorderPlaces = savedInstanceState.getParcelableArrayList(INFO_PUT_ORDER_PLACES);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        mToolbarCommonBinding = DataBindingUtil.bind(view.findViewById(R.id.toolbarCommon));
        mToolbarCommonBinding.toolbarTitle.setText("选择放单地点");
        mDataBinding.tvAlert.setText(new SpanUtils().append("*")
                .setForegroundColor(UiUtils.getColor(R.color.orange500)
                ).append(" 选择放单地点后方可完成接单")
                .setForegroundColor(UiUtils.getColor(R.color.black666))
                .create());

        mPutOrderAddressAdapter = new PutOrderAddressAdapter();
        initSwipeRecyclerView(view, mPutOrderAddressAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        // 加载地图
        mMapFragment = MapFragment.newInstance(false);
        getChildFragmentManager().beginTransaction().add(R.id.fragmentLayout, mMapFragment).show(mMapFragment).commit();

        setData(mInfoPutorderPlaces);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ORDER, mOrder);
        outState.putParcelableArrayList(INFO_PUT_ORDER_PLACES, mInfoPutorderPlaces);
        outState.putInt(POSITION, mPosition);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_choose_order_address;
    }

    @Override
    protected void initListener() {
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());
        // 打开详情 暂时不要
        mPutOrderAddressAdapter.setOnItemClickListener((itemView, item, position) -> {
            mPlaceId = ((InfoPutorderPlace) item).getId();
//                start(PutOrderAddressDetailFragment.newInstance(place));
        });
        // 显示地址地图
        mPutOrderAddressAdapter.setComeback(new PutOrderAddressAdapter.Comeback() {
            @Override
            public void showLocation(LatLng latLng, String name, String address) {
                // 添加图标并移动
                mMapFragment.movePoint(latLng);
                mMapFragment.addMarkersToMap(latLng, name, address);
            }

            @Override
            public void myLocation(InfoPutorderPlace item) {
                // 移到我的位置
                mMapFragment.moveMyLocation();
            }

            // 思明说暂时不用监听电话
            @Override
            public void callMobile(InfoPutorderPlace place) {
                if (AppClickUtil.isDuplicateClick() || TextUtils.isEmpty(place.getPhone()))
                    return;

                // 打电话
                PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{place.getPhone()});
            }
        });
        // 确认存放点
        mDataBinding.btnComplete.setOnClickListener(v -> {
            // 返回订单页并设置接受订单
            Bundle bundle = new Bundle();
            bundle.putParcelable(ORDER, mOrder);
            bundle.putInt(POSITION, mPosition);
            bundle.putString(PLACE_ID, mPlaceId);
            setFragmentResult(ORDER_ACCEPT, bundle);
            pop();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapFragment.onDestroyView();
    }

    /**
     * 打开存放点
     *
     * @param order              订单
     * @param infoPutorderPlaces 存放点数据
     * @param position           订单列表位置
     * @return fragment
     */
    public static PutOrderAddressFragment newInstance(Order order, ArrayList<InfoPutorderPlace> infoPutorderPlaces, int position) {
        Bundle args = new Bundle();
        args.putParcelable(ORDER, order);
        args.putParcelableArrayList(INFO_PUT_ORDER_PLACES, infoPutorderPlaces);
        args.putInt(POSITION, position);
        PutOrderAddressFragment fragment = new PutOrderAddressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setData(ArrayList<InfoPutorderPlace> infoPutorderPlaces) {
        ULog.d("choose order address set data size = " + infoPutorderPlaces.size());

        mPutOrderAddressAdapter.appendToList(infoPutorderPlaces);
        mPutOrderAddressAdapter.notifyDataSetChanged();

        mDataBinding.tvAlert.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshData() {
    }
}

















