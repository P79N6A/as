package com.yaoguang.driver.phone.order.chooseaddress;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.model.LatLng;
import com.yaoguang.appcommon.common.eventbus.OrderChildEvent;
import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.SpanUtils;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentChooseOrderAddressBinding;
import com.yaoguang.driver.phone.order.chooseaddress.adapter.PutOrderAddressAdapter;
import com.yaoguang.greendao.entity.InfoPutorderPlace;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static com.yaoguang.lib.common.constants.Constants.FLAG_REFRESH_PROGRESS;

/**
 * 选择订单存放地点
 * Created by wly on 2017/12/28 0028.
 */

public class PutOrderAddressFragment extends BaseFragmentDataBind<FragmentChooseOrderAddressBinding> implements PutOrderAddressContacts.View {

    PutOrderAddressContacts.Presenter mPutOrderAddressPresenter = new PutOrderAddressPresenter(this);

    public static final String ORDER_TYPE = "orderType";
    public static final String DRIVERORDERWRAPPER = "driverOrderWrapper";
    public static final String POSITION = "position";
    public static final String PLACE_ID = "place_id";
    private static String INFO_PUT_ORDER_PLACES = "info_put_order_places";

    PutOrderAddressAdapter mPutOrderAddressAdapter = new PutOrderAddressAdapter();

    private ArrayList<InfoPutorderPlace> mInfoPutorderPlaces;
    private DriverOrderWrapper mDriverOrderWrapper;
    private int mPosition;
    private String mPlaceId;

    MapFragment mMapFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().getParcelable(DRIVERORDERWRAPPER) != null) {
            mDriverOrderWrapper = getArguments().getParcelable(DRIVERORDERWRAPPER);
            mPosition = getArguments().getInt(POSITION);
            mInfoPutorderPlaces = getArguments().getParcelableArrayList(INFO_PUT_ORDER_PLACES);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_choose_order_address;
    }

    @Override
    public void init() {
        RecyclerViewUtils.initPage(mDataBinding.recyclerView, mPutOrderAddressAdapter, null, _mActivity.getBaseContext(), true);

        mToolbarCommonBinding.toolbarTitle.setText("选择放单地点");
        mDataBinding.tvAlert.setText(new SpanUtils().append("*")
                .setForegroundColor(UiUtils.getColor(R.color.orange500)
                ).append(" 选择放单地点后方可完成接单")
                .setForegroundColor(UiUtils.getColor(R.color.black666))
                .create());

        // 加载地图
        mMapFragment = MapFragment.newInstance(false);
        getChildFragmentManager().beginTransaction().add(R.id.fragmentLayout, mMapFragment).show(mMapFragment).commit();

        setData(mInfoPutorderPlaces);
    }

    @Override
    public void initListener() {
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
                mMapFragment.movePoint(latLng,true);
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
            mPutOrderAddressPresenter.accept(mDriverOrderWrapper.getOrderId(), mDriverOrderWrapper, mPosition, "", mPlaceId);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mDriverOrderWrapper = savedInstanceState.getParcelable(DRIVERORDERWRAPPER);
            mPosition = savedInstanceState.getInt(POSITION);
            mInfoPutorderPlaces = savedInstanceState.getParcelableArrayList(INFO_PUT_ORDER_PLACES);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPutOrderAddressPresenter;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DRIVERORDERWRAPPER, mDriverOrderWrapper);
        outState.putParcelableArrayList(INFO_PUT_ORDER_PLACES, mInfoPutorderPlaces);
        outState.putInt(POSITION, mPosition);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapFragment.onDestroyView();
    }

    /**
     * 打开存放点
     *
     * @param driverOrderWrapper 订单
     * @param infoPutorderPlaces 存放点数据
     * @param position           订单列表位置
     * @param orderType          子订单类型
     * @return fragment
     */
    public static PutOrderAddressFragment newInstance(DriverOrderWrapper driverOrderWrapper, ArrayList<InfoPutorderPlace> infoPutorderPlaces, int position, int orderType) {
        Bundle args = new Bundle();
        args.putParcelable(DRIVERORDERWRAPPER, driverOrderWrapper);
        args.putParcelableArrayList(INFO_PUT_ORDER_PLACES, infoPutorderPlaces);
        args.putInt(POSITION, position);
        args.putInt(ORDER_TYPE, orderType);
        PutOrderAddressFragment fragment = new PutOrderAddressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 赋值放单点数据源
     *
     * @param infoPutorderPlaces 数据源
     */
    public void setData(ArrayList<InfoPutorderPlace> infoPutorderPlaces) {
        ULog.d("choose order address set data size = " + infoPutorderPlaces.size());

        mPutOrderAddressAdapter.appendToList(infoPutorderPlaces);
        mPutOrderAddressAdapter.notifyDataSetChanged();

        mDataBinding.tvAlert.setVisibility(View.VISIBLE);
    }

    @Override
    public void acceptSuccess() {
        // 返回订单详情页并刷新订单
        setFragmentResult(RESULT_OK,new Bundle());
        // event通知订单(待接单、已接单)、订单明细全部刷新（为什么用event，因为如果通过详情页进来的，就可以“隔空”刷新订单列表）
        OrderChildEvent orderChildEvent = new OrderChildEvent(FLAG_REFRESH_PROGRESS);
        orderChildEvent.setOrderType(0);
        EventBus.getDefault().post(orderChildEvent);
        OrderChildEvent orderChildEvent2 = new OrderChildEvent(FLAG_REFRESH_PROGRESS);
        orderChildEvent2.setOrderType(1);
        EventBus.getDefault().post(orderChildEvent2);
        pop();
    }
}

















