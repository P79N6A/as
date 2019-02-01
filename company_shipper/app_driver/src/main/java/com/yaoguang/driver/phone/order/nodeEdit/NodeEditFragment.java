package com.yaoguang.driver.phone.order.nodeEdit;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentNodeEditBinding;
import com.yaoguang.driver.phone.main.MainActivity;
import com.yaoguang.appcommon.phone.node.event.NodeFragmentRefreshEvent;
import com.yaoguang.driver.phone.order.nodeEdit.adapter.NodeEditAdapter;
import com.yaoguang.driver.phone.order.nodeEdit.nodeEditAddress.NodeEditAddress2Fragment;
import com.yaoguang.driver.phone.order.nodeEdit.nodeEditPort.NodeEditPortFragment;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeWrapper;
import com.yaoguang.greendao.entity.driver.Site;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.map.common.AMapUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 路线节点编辑
 * Created by zhongjh on 2018/6/7.
 */
public class NodeEditFragment extends BaseFragmentListConditionDataBind<DriverOrderNodeWrapper, String, NodeEditAdapter, FragmentNodeEditBinding> implements NodeEditContract.View {

    private String mOrderId;
    private String mClientId;
    private NodeEditContract.Presenter mPresenter = new NodeEditPresenter(this);
    DriverOrderNodeWrapper mDriverOrderNodeWrapper;// 待编辑的driverOrderNodeWrapper

    AMapUtil aMapUtil = new AMapUtil(); // 地图工具类

    public static NodeEditFragment newInstance(String orderId, String clientId) {
        NodeEditFragment nodeEditFragment = new NodeEditFragment();
        Bundle bundle = new Bundle();
        bundle.putString("orderId", orderId);
        bundle.putString("clientId", clientId);
        nodeEditFragment.setArguments(bundle);
        return nodeEditFragment;
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    PoiItem poiItem = data.getParcelable("poiItem");
                    if (poiItem != null) {
                        Site site = new Site();
                        site.setLat(poiItem.getLatLonPoint().getLatitude());
                        site.setLon(poiItem.getLatLonPoint().getLongitude());
                        ArrayList<Site> arrayList = new ArrayList<>();
                        arrayList.add(site);
                        mDriverOrderNodeWrapper.getTruckGoodsAddr().setSite(site);
                        mDriverOrderNodeWrapper.getTruckGoodsAddr().setAddress(poiItem.getCityName());
                        mDriverOrderNodeWrapper.getTruckGoodsAddr().setArea(arrayList);
                        // 提交编辑
                        mPresenter.update(mDriverOrderNodeWrapper.getTruckGoodsAddr());
                    }
                    break;
                case 2:
                    // 修改码头
                    String dockId = data.getString("dockId");
                    String nodeId = data.getString("nodeId");
                    mPresenter.updatePort(nodeId, dockId);
                    break;
            }
        }
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_node_edit;
    }

    @Override
    public void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mOrderId = bundle.getString("orderId");
            mClientId = bundle.getString("clientId");
        }
        mPresenter.subscribe();
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "路线", -1, null);
        }

        // 不显示加载完毕的脚步
        mBaseLoadMoreRecyclerAdapter.setHasFooterPriority(false);
        mLayoutRecyclerviewBinding.refreshLayout.finishLoadMore();
    }

    @Override
    public void initListener() {

        // 点击传送路径过去
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            mDriverOrderNodeWrapper = (DriverOrderNodeWrapper) item;
            // 判断是否可编辑
            if (ObjectUtils.parseInt(mDriverOrderNodeWrapper.getIsEditable(),0) == 1) {
                if (mDriverOrderNodeWrapper.getTruckGoodsAddr().getSite() != null) {
                    LatLng latLng = new LatLng(mDriverOrderNodeWrapper.getTruckGoodsAddr().getSite().getLat(), mDriverOrderNodeWrapper.getTruckGoodsAddr().getSite().getLon());
                    startForResult(NodeEditAddress2Fragment.newInstance(latLng), 1);
                } else {
                    startForResult(NodeEditAddress2Fragment.newInstance(null), 1);
                }
            } else {
                Toast.makeText(BaseApplication.getInstance(), "该地址不可编辑", Toast.LENGTH_SHORT).show();
            }
        });

        // 更换路线
        mBaseLoadMoreRecyclerAdapter.setmOnNodeEditListener(new NodeEditAdapter.OnNodeEditListener() {
            @Override
            public void exchange(View view, DriverOrderNodeWrapper driverOrderNodeWrapper, int position, DriverOrderNodeWrapper driverOrderNodeWrapperNew) {
                mPresenter.exchange(driverOrderNodeWrapper.getId(), driverOrderNodeWrapperNew.getId());
            }

            @Override
            public void startMap(View view, DriverOrderNodeWrapper driverOrderNodeWrapper, int position) {
                if (driverOrderNodeWrapper.getTruckGoodsAddr().getSite() != null && driverOrderNodeWrapper.getTruckGoodsAddr().getSite().getLat() != null &&driverOrderNodeWrapper.getTruckGoodsAddr().getSite().getLat() != -1) {
                    LatLng latLng = new LatLng(driverOrderNodeWrapper.getTruckGoodsAddr().getSite().getLat(), driverOrderNodeWrapper.getTruckGoodsAddr().getSite().getLon());
                    AMapUtil.startAMapNavi(getContext(), latLng);
                } else if (!TextUtils.isEmpty(driverOrderNodeWrapper.getTruckGoodsAddr().getAddress())) {
                    // 如果有address，就显示导航
                    aMapUtil.startAMapNavi(_mActivity,getContext(), driverOrderNodeWrapper.getTruckGoodsAddr().getAddress());
                }
            }

            @Override
            public void startEditPort(View view, DriverOrderNodeWrapper driverOrderNodeWrapper, int position) {
                // 判断是否可编辑
                if (ObjectUtils.parseInt(driverOrderNodeWrapper.getIsEditable(),0) == 1) {
                    startForResult(NodeEditPortFragment.newInstance(mClientId, driverOrderNodeWrapper.getId()), 2);
                }else{
                    Toast.makeText(BaseApplication.getInstance(), "该码头不可编辑", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new NodeEditAdapter(true);
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public String getCondition(boolean isRegain) {
        return mOrderId;
    }

    @Override
    public void setConditionView(String condition) {

    }

    @Override
    public void exchangeSuccess(String message) {
        refreshData();
        Toast.makeText(BaseApplication.getInstance(), message, Toast.LENGTH_LONG).show();
        // 刷新 nodeFragment界面
        EventBus.getDefault().post(new NodeFragmentRefreshEvent(false));

        // 如果是跟当前正在运行的订单一样，则刷新订单
        if (((MainActivity) _mActivity).mOrderNo.equals(mOrderId))
            ((MainActivity) _mActivity).initOrder(mOrderId);
    }

    @Override
    public void detailubmitSuccess(String message) {
        refreshData();
        Toast.makeText(BaseApplication.getInstance(), message, Toast.LENGTH_LONG).show();
        // 刷新 nodeFragment界面
        EventBus.getDefault().post(new NodeFragmentRefreshEvent(false));

        // 如果是跟当前正在运行的订单一样，则刷新订单
        if (((MainActivity) _mActivity).mOrderNo.equals(mOrderId))
            ((MainActivity) _mActivity).initOrder(mOrderId);

    }

}
