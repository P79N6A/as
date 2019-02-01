package com.yaoguang.shipper.businessorder.trailer.templist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.yaoguang.appcompanyshipper.databinding.FragmentBusinessOrderTrailerTempListBinding;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.business.BaseBusinessOrderFragment;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.templist.BaseBusinessOrderTempListFragment;
import com.yaoguang.greendao.entity.shipper.AppOwnerTruckOrder;
import com.yaoguang.greendao.entity.shipper.AppOwnerTruckOrderTemplate;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.shipper.businessorder.trailer.templist.adapter.BusinessOrderTempListAdapter;

/**
 * Created by zhongjh on 2018/9/10.
 */
public class BusinessOrderTempListFragment extends BaseBusinessOrderTempListFragment<AppOwnerTruckOrderTemplate,FragmentBusinessOrderTrailerTempListBinding> {

    public static BusinessOrderTempListFragment newInstance() {
        return new BusinessOrderTempListFragment();
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new BusinessOrderTempListAdapter(mRecycledViewPool,getContext());
    }


    @Override
    public void init() {
        mPresenter = new BusinessOrderTempListPresenter(this);
        super.init();
    }



    @Override
    public void initListener() {
        super.initListener();
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            Bundle bundle = new Bundle();
            AppOwnerTruckOrderTemplate appOwnerTruckOrderTemplate = (AppOwnerTruckOrderTemplate) item;
            AppOwnerTruckOrder appOwnerTruckOrder = new AppOwnerTruckOrder();
            appOwnerTruckOrder.setClientId(appOwnerTruckOrderTemplate.getClientId());
            appOwnerTruckOrder.setCompanyName(appOwnerTruckOrderTemplate.getOfficeName());
            appOwnerTruckOrder.setAddress(appOwnerTruckOrderTemplate.getAddress());
            appOwnerTruckOrder.setUserId(appOwnerTruckOrderTemplate.getUserId());
            appOwnerTruckOrder.setGoodsName(appOwnerTruckOrderTemplate.getGoodsName());
            appOwnerTruckOrder.setOwner(appOwnerTruckOrderTemplate.getOwner());
            appOwnerTruckOrder.setPort(appOwnerTruckOrderTemplate.getPort());
            appOwnerTruckOrder.setAddress(appOwnerTruckOrderTemplate.getAddress());
            appOwnerTruckOrder.setShipCompany(appOwnerTruckOrderTemplate.getShipCompany());
            appOwnerTruckOrder.setDockInfos(appOwnerTruckOrderTemplate.getDockInfos());
            appOwnerTruckOrder.setClientId(appOwnerTruckOrderTemplate.getClientId());
            appOwnerTruckOrder.setIsValid(appOwnerTruckOrderTemplate.getIsValid());
            bundle.putParcelable(BaseBusinessOrderFragment.BUNDLE_ORDER, appOwnerTruckOrder);
            setFragmentResult(RESULT_OK, bundle);
            pop();
        });
    }

}
