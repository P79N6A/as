package com.yaoguang.company.businessorder.trailer.templist;

import android.os.Bundle;

import com.yaoguang.appcompanyshipper.databinding.FragmentBusinessOrderTrailerTempListBinding;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.business.BaseBusinessOrderFragment;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.templist.BaseBusinessOrderTempListFragment;
import com.yaoguang.company.businessorder.trailer.templist.adapter.BusinessOrderTempListAdapter;
import com.yaoguang.greendao.entity.company.AppTruckOrder;
import com.yaoguang.greendao.entity.company.AppTruckOrderTemplate;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;

/**
 * Created by zhongjh on 2018/9/10.
 */
public class BusinessOrderTempListFragment extends BaseBusinessOrderTempListFragment<AppTruckOrderTemplate,FragmentBusinessOrderTrailerTempListBinding> {

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
            AppTruckOrderTemplate appOwnerTruckOrderTemplate = (AppTruckOrderTemplate) item;
            AppTruckOrder appTruckOrder = new AppTruckOrder();
            appTruckOrder.setAddress(appOwnerTruckOrderTemplate.getAddress());
            appTruckOrder.setUserId(appOwnerTruckOrderTemplate.getUserId());
            appTruckOrder.setGoodsName(appOwnerTruckOrderTemplate.getGoodsName());
            appTruckOrder.setOwner(appOwnerTruckOrderTemplate.getOwner());
            appTruckOrder.setPort(appOwnerTruckOrderTemplate.getPort());
            appTruckOrder.setAddress(appOwnerTruckOrderTemplate.getAddress());
            appTruckOrder.setShipCompany(appOwnerTruckOrderTemplate.getShipCompany());
            appTruckOrder.setDockInfos(appOwnerTruckOrderTemplate.getDockInfos());
            appTruckOrder.setClientId(appOwnerTruckOrderTemplate.getClientId());
            appTruckOrder.setIsValid(appOwnerTruckOrderTemplate.getIsValid());
            bundle.putParcelable(BaseBusinessOrderFragment.BUNDLE_ORDER, appTruckOrder);
            setFragmentResult(RESULT_OK, bundle);
            pop();
        });
    }

}
