package com.yaoguang.shipper.businessorder.forwarder.templist;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.yaoguang.appcompanyshipper.databinding.FragmentBusinessOrderTempListBinding;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.templist.BaseBusinessOrderTempListFragment;
import com.yaoguang.greendao.entity.company.AppCompanyOrderCondition;
import com.yaoguang.greendao.entity.shipper.AppOwnerForwardOrder;
import com.yaoguang.greendao.entity.shipper.AppOwnerForwardOrderTemplate;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.shipper.businessorder.forwarder.business.BusinessOrderFragment;
import com.yaoguang.shipper.businessorder.forwarder.templist.adapter.BusinessOrderTempListAdapter;

import static com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.business.BaseBusinessOrderFragment.BUNDLE_ORDER;

/**
 * Created by zhongjh on 2018/9/7.
 */
public class BusinessOrderTempListFragment extends BaseBusinessOrderTempListFragment<AppOwnerForwardOrderTemplate,FragmentBusinessOrderTempListBinding> {

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
        // 应用到新增界面
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            Bundle bundle = new Bundle();
            AppOwnerForwardOrderTemplate appOwnerForwardOrderTemplate = (AppOwnerForwardOrderTemplate)item;
            AppOwnerForwardOrder appOwnerForwardOrder = new AppOwnerForwardOrder();
            // 装卸地址
            Gson gson = new Gson();
            appOwnerForwardOrder.setClientId(appOwnerForwardOrderTemplate.getClientId());
            appOwnerForwardOrder.setCompanyName(appOwnerForwardOrderTemplate.getOfficeName());
            appOwnerForwardOrder.setConsigneeId(gson.toJson(appOwnerForwardOrderTemplate.getConsigneeClientPlaces()));
            appOwnerForwardOrder.setLoadingId(gson.toJson(appOwnerForwardOrderTemplate.getLoadClientPlaces()));
            appOwnerForwardOrder.setCarriageItem(appOwnerForwardOrderTemplate.getCarriageItem());
            appOwnerForwardOrder.setCarriageWay(appOwnerForwardOrderTemplate.getCarriageWay());
            appOwnerForwardOrder.setCreated(appOwnerForwardOrderTemplate.getCreated());
//        appOwnerForwardOrder.setCreatedDeptId(appOwnerForwardOrderTemplate.getC);
            appOwnerForwardOrder.setDockOfLoading(appOwnerForwardOrderTemplate.getDockOfLoading());
//        appOwnerForwardOrder.setFee(appOwnerForwardOrderTemplate.);
            appOwnerForwardOrder.setFinalDestination(appOwnerForwardOrderTemplate.getFinalDestination());
            appOwnerForwardOrder.setGoodsName(appOwnerForwardOrderTemplate.getGoodsName());
//        appOwnerForwardOrder.setImportTime(appOwnerForwardOrderTemplate.getIm);
//        appOwnerForwardOrder.setInsurMoney(appOwnerForwardOrderTemplate.getIn);
//        appOwnerForwardOrder.setInsurRate(appOwnerForwardOrderTemplate.getIn);
//        appOwnerForwardOrder.setInsurValue(appOwnerForwardOrderTemplate.getIn);
            appOwnerForwardOrder.setIsInsurance(appOwnerForwardOrderTemplate.getIsInsurance());
//        appOwnerForwardOrder.setIsValid(appOwnerForwardOrderTemplate.);
//        appOwnerForwardOrder.setLoadDate(appOwnerForwardOrderTemplate.get);
//        appOwnerForwardOrder.setOperator(appOwnerForwardOrderTemplate.get);
//        appOwnerForwardOrder.setOrderSn(appOwnerForwardOrderTemplate.get);
            appOwnerForwardOrder.setOwner(appOwnerForwardOrderTemplate.getOwner());
            appOwnerForwardOrder.setPortDelivery(appOwnerForwardOrderTemplate.getPortDelivery());
            appOwnerForwardOrder.setPortLoading(appOwnerForwardOrderTemplate.getPortLoading());
            appOwnerForwardOrder.setReamrk(appOwnerForwardOrderTemplate.getRemark());
//        appOwnerForwardOrder.setReamrk1(appOwnerForwardOrderTemplate.getRe);
//        appOwnerForwardOrder.setReamrk2(appOwnerForwardOrderTemplate.getRemark);
//        appOwnerForwardOrder.setSonos(appOwnerForwardOrderTemplate.getS);
//        appOwnerForwardOrder.setStatus(appOwnerForwardOrderTemplate.getS);
            appOwnerForwardOrder.setUpdated(appOwnerForwardOrderTemplate.getUpdated());
            bundle.putParcelable(BUNDLE_ORDER,appOwnerForwardOrder);
            setFragmentResult(RESULT_OK,bundle);
            pop();
        });
    }


}
