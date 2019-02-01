package com.yaoguang.company.businessorder.forwarder.business;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.yaoguang.appcommon.publicsearch.BasePublicSearchFragment;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.publicsearch.loadingandunloading.LoadingAndUnloadingFragment;
import com.yaoguang.appcompanyshipper.databinding.FragmentBusinessOrderBinding;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.business.BaseBusinessOrderFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.businessorder.forwarder.templist.BusinessOrderTempListFragment;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.Container;
import com.yaoguang.greendao.entity.company.AppCompanyOrder;
import com.yaoguang.greendao.entity.company.AppInfoClientPlace;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhongjh on 2018/9/5.
 */
public class BusinessOrderFragment extends BaseBusinessOrderFragment<AppCompanyOrder, FragmentBusinessOrderBinding> {

    public static BusinessOrderFragment newInstance(String id) {
        BusinessOrderFragment baseBusinessOrderFragment = new BusinessOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        baseBusinessOrderFragment.setArguments(bundle);
        return baseBusinessOrderFragment;
    }

    @Override
    protected void setOrder(String sonos) {
        if (mOrder == null)
            // 如果为null，就是创建新的单
            mOrder = new AppCompanyOrder();

        // 托运人
        mOrder.setShipperId(mDataBinding.layoutEntrusted.tvValueShipper.getTag().toString());
        mOrder.setShipper(mDataBinding.layoutEntrusted.tvValueShipper.getText().toString());

        // 3 )装卸货信息
        mOrder.setLoadClientPlaces(mAppInfoClientPlaceLoadings);
        mOrder.setConsigneeClientPlaces(mAppInfoClientPlaceUnLoadings);

        mOrder.setDockOfLoading(mDataBinding.layoutEntrusted.tvValueDepartureTitle.getText().toString());
        mOrder.setFinalDestination(mDataBinding.layoutEntrusted.tvValueDestination.getText().toString());
        mOrder.setGoodsName(mDataBinding.layoutEntrusted.tvValueGoodsName.getText().toString());
        mOrder.setCarriageItem(mDataBinding.layoutEntrusted.tvValueOperationClause.getText().toString());
        mOrder.setOwner(mDataBinding.layoutEntrusted.etConsigneeIdonsigneeId.getText().toString());
        mOrder.setPortLoading(mDataBinding.layoutShipping.tvValuePortShipmentTitle.getText().toString());
        mOrder.setPortDelivery(mDataBinding.layoutShipping.tvValuePortDestinationTitle.getText().toString());
        mOrder.setLoadDate(mDataBinding.layoutCostinformation.tvLoadingDate.getText().toString() + " " + mDataBinding.layoutCostinformation.tvLoadingTime.getText().toString() + ":00");
        mOrder.setCarriageWay(mDataBinding.layoutShipping.tvValueTransportationClause.getText().toString());
        mOrder.setIsInsurance(mDataBinding.layoutInsurance.tvIsInsurance.getText().toString().equals("是") ? "1" : "0");
        mOrder.setInsurMoney(mDataBinding.layoutInsurance.etValueInsurMoney.getText().toString());
        mOrder.setInsurRate(mDataBinding.layoutInsurance.etValueInsurRate.getText().toString());
        mOrder.setInsurValue(mDataBinding.layoutInsurance.etValueInsurValue.getText().toString());

        mOrder.setSonos(sonos);

        mOrder.setFee(mDataBinding.layoutCostinformation.etValueReservationFee.getText().toString().replace("¥", ""));
        mOrder.setReamrk(mDataBinding.layoutCostinformation.etValueRemark.getText().toString());
        mOrder.setReamrk1(mDataBinding.layoutCostinformation.etValueRemark1.getText().toString());
        mOrder.setReamrk2(mDataBinding.layoutCostinformation.etValueRemark2.getText().toString());
        if (!TextUtils.isEmpty(mID)) {
            mOrder.setId(mID);
            mToolbarCommonBinding.toolbar.getMenu().clear();
        }

    }

    @Override
    public void init() {
        super.init();
        mPresenter = new BusinessOrderPresenter(this, getContext(), mID, mOrder);
    }

    @Override
    public void initListener() {
        super.initListener();
        //装货信息
        mDataBinding.layoutLoading.rlLoading.setOnClickListener(v -> {
            if (mDataBinding.layoutEntrusted.tvValueShipper.getText().toString().equals("")) {
                Toast.makeText(BaseApplication.getInstance(), "必须先选择托运人才可以选择装货地区！", Toast.LENGTH_LONG).show();
                return;
            }
            if (mDataBinding.layoutEntrusted.tvValueDepartureTitle.getText().toString().equals("")) {
                Toast.makeText(BaseApplication.getInstance(), "必须先选择起运地才可以选择装货地区！", Toast.LENGTH_LONG).show();
                return;
            }
            //获取托运人id
            BasePublicSearchFragment fragment = LoadingAndUnloadingFragment.newInstance(PublicSearchInteractorImpl.TYPE_LOADPLACES,
                    mDataBinding.layoutEntrusted.tvValueShipper.getTag().toString(), mDataBinding.layoutEntrusted.tvValueDepartureTitle.getText().toString(), mOrder.getLoadingId(), null);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_LOADPLACES));
        });
        //卸货信息
        mDataBinding.layoutUnloading.rlUnloading.setOnClickListener(v -> {
            if (mDataBinding.layoutEntrusted.tvValueShipper.getText().toString().equals("")) {
                Toast.makeText(BaseApplication.getInstance(), "必须先选择托运人才可以选择卸货地区！", Toast.LENGTH_LONG).show();
                return;
            }
            if (mDataBinding.layoutEntrusted.tvValueDestination.getText().toString().equals("")) {
                Toast.makeText(BaseApplication.getInstance(), "必须先选择目的地才可以选择装货地区！", Toast.LENGTH_LONG).show();
                return;
            }
            //获取托运人id
            BasePublicSearchFragment fragment = LoadingAndUnloadingFragment.newInstance(
                    PublicSearchInteractorImpl.TYPE_UNLOADPLACES, mDataBinding.layoutEntrusted.tvValueShipper.getTag().toString(),
                    mDataBinding.layoutEntrusted.tvValueDestination.getText().toString(), mOrder.getConsigneeId(), null);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_UNLOADPLACES));
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_list) {
            startForResult(BusinessOrderTempListFragment.newInstance(), REQUSET_MODEL_TEMP);
        }
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showDetail(AppCompanyOrder value, boolean isTemp) {

        mOrder = value;

        mDataBinding.flMain.setFocusable(true);

        // 判断状态是否已导入
        mIsExport = value.getStatus();

        initNumberInformation(value);
        initEntrusted(value);
        initShipping(value);
        initLoadingAndUnLoading(value);

        //如果是模版的，就直接返回
        if (isTemp)
            return;

        //显示订单id
        mDataBinding.layoutInformation.llNumberInformation.setVisibility(android.view.View.VISIBLE);

        initCostinformation(value);
        initInsurance(value);
        initContainer(value);

        // 隐藏右上角菜单
        mToolbarCommonBinding.toolbar.getMenu().clear();
        if (mIsExport.equals("1")) {
            initIsExport();
        } else if (mIsExport.equals("0")) {
            mDataBinding.btnComit.setText("重新提交");

            ((TextView) mToolbarCommonBinding.toolbar.findViewById(com.yaoguang.appcompanyshipper.R.id.toolbar_title)).setText("修改预订单");
        }
    }

    /**
     * 单号信息
     */
    protected void initNumberInformation(AppCompanyOrder value) {
        // 预定单号 )单号信息
        mDataBinding.layoutInformation.etValueOrderSn.setText(value.getOrderSn());
        // 提交时间
        if (ObjectUtils.parseString(value.getUpdated()).equals("")) {
            mDataBinding.layoutInformation.etValueCreated.setText(value.getCreated());
        } else {
            mDataBinding.layoutInformation.etValueCreated.setText(value.getUpdated());
        }
        // 导入时间
        if (!ObjectUtils.parseString(value.getImportTime()).equals("")) {
            mDataBinding.layoutInformation.etValueImportTime.setText(value.getImportTime());
        }
    }

    /**
     * 委托信息
     */
    protected void initEntrusted(AppCompanyOrder value) {
        // 托运人 )委托信息
        mDataBinding.layoutEntrusted.tvValueShipper.setText(value.getShipper());
        mDataBinding.layoutEntrusted.tvValueShipper.setTag(value.getShipperId());
        // 起运地
        mDataBinding.layoutEntrusted.tvValueDepartureTitle.setText(value.getDockOfLoading());
        // 目的地
        mDataBinding.layoutEntrusted.tvValueDestination.setText(value.getFinalDestination());
        // 货物名称
        mDataBinding.layoutEntrusted.tvValueGoodsName.setText(value.getGoodsName());
        // 操作条款
        mDataBinding.layoutEntrusted.tvValueOperationClause.setText(value.getCarriageItem());
        // 货主姓名
        mDataBinding.layoutEntrusted.etConsigneeIdonsigneeId.setText(value.getOwner());
    }

    /**
     * 船公司信息
     */
    protected void initShipping(AppCompanyOrder value) {
        // 起运港 )船公司信息
        mDataBinding.layoutShipping.tvValuePortShipmentTitle.setText(value.getPortLoading());
        // 目的港
        mDataBinding.layoutShipping.tvValuePortDestinationTitle.setText(value.getPortDelivery());
        // 运输条款
        mDataBinding.layoutShipping.tvValueTransportationClause.setText(value.getCarriageWay());
    }

    /**
     * 装卸货信息
     */
    protected void initLoadingAndUnLoading(AppCompanyOrder value) {
        // 循环装货信息
        ArrayList<AppPublicInfoWrapper> appPublicInfoWrappersLoad = new ArrayList<>();
        if (value.getLoadClientPlaces().size() > 0) {
            if (mIsExport.equals("0")) {
                mDataBinding.layoutLoading.tvLoading.setText("修改");
            } else {
                mDataBinding.layoutLoading.tvLoading.setText("");
            }
        }
        for (AppInfoClientPlace item : value.getLoadClientPlaces()) {
            AppPublicInfoWrapper appPublicInfoWrapper = new AppPublicInfoWrapper();
            appPublicInfoWrapper.setId(item.getId());
            appPublicInfoWrapper.setCheck(true);
            appPublicInfoWrapper.setFullName(item.getRegionid());
            appPublicInfoWrapper.setShortName(item.getAddress());
            appPublicInfoWrapper.setRemark1(item.getLinkman());
            appPublicInfoWrapper.setRemark2(item.getLinkmanMp());
            appPublicInfoWrapper.setRemark3(item.getLinkmanTel());
            appPublicInfoWrapper.setRemark4(item.getRemark());
            appPublicInfoWrappersLoad.add(appPublicInfoWrapper);
        }
        initLoading(appPublicInfoWrappersLoad, PublicSearchInteractorImpl.TYPE_LOADPLACES);

        // 循环卸货信息
        ArrayList<AppPublicInfoWrapper> appPublicInfoWrappersUnLoading = new ArrayList<>();
        if (value.getConsigneeClientPlaces().size() > 0) {
            if (mIsExport.equals("0")) {
                mDataBinding.layoutUnloading.tvUnLoading.setText("修改");
            } else {
                mDataBinding.layoutUnloading.tvUnLoading.setText("");
            }
        }
        for (AppInfoClientPlace item : value.getConsigneeClientPlaces()) {
            AppPublicInfoWrapper appPublicInfoWrapper = new AppPublicInfoWrapper();
            appPublicInfoWrapper.setId(item.getId());
            appPublicInfoWrapper.setCheck(true);
            appPublicInfoWrapper.setFullName(item.getRegionid());
            appPublicInfoWrapper.setShortName(item.getAddress());
            appPublicInfoWrapper.setRemark1(item.getLinkman());
            appPublicInfoWrapper.setRemark2(item.getLinkmanMp());
            appPublicInfoWrapper.setRemark3(item.getLinkmanTel());
            appPublicInfoWrapper.setRemark4(item.getRemark());
            appPublicInfoWrappersUnLoading.add(appPublicInfoWrapper);
        }
        initLoading(appPublicInfoWrappersUnLoading, PublicSearchInteractorImpl.TYPE_UNLOADPLACES);
    }

    /**
     * 其他
     */
    protected void initCostinformation(AppCompanyOrder value) {
        // 装货时间 )其他
        if (value.getLoadDate() != null && value.getLoadDate().length() > 2) {
            String[] loadDates = value.getLoadDate().split(" ");
            mDataBinding.layoutCostinformation.tvLoadingDate.setText(loadDates[0]);
            mDataBinding.layoutCostinformation.tvLoadingTime.setText(loadDates[1].substring(0, loadDates[1].length() - 3));
        }
        // 预定费用
        mDataBinding.layoutCostinformation.etValueReservationFee.setText("¥" + ObjectUtils.formatNumber2(value.getFee(), 0));
        // 备注
        mDataBinding.layoutCostinformation.etValueRemark.setText(value.getReamrk());
        mDataBinding.layoutCostinformation.etValueRemark1.setText(value.getReamrk1());
        mDataBinding.layoutCostinformation.etValueRemark2.setText(value.getReamrk2());
    }

    /**
     * 保险信息
     */
    protected void initInsurance(AppCompanyOrder value) {
        // )保险信息
        if (value.getIsInsurance().equals("1")) {
            mDataBinding.layoutInsurance.tvIsInsurance.setText("是");
        } else {
            mDataBinding.layoutInsurance.tvIsInsurance.setText("否");
        }
        mDataBinding.layoutInsurance.etValueInsurMoney.setText(value.getInsurMoney());
        mDataBinding.layoutInsurance.etValueInsurRate.setText(value.getInsurRate());
        mDataBinding.layoutInsurance.etValueInsurValue.setText(value.getInsurValue());
    }

    /**
     * 货柜信息
     */
    protected void initContainer(AppCompanyOrder value) {
        // )货柜信息
        if (!TextUtils.isEmpty(value.getSonos())){
            List<Container> containers = new ArrayList<>();

            // 先转换ArrayList<String>
           List<String> list = Arrays.asList( value.getSonos().split(","));

            for (int i = 0; i < list.size(); i++) {
                String[] conts = list.get(i).split("\\*");
                if (i == 0) {
                    //因为货柜信息的特殊性，所以直接替换第一个
                    mDataBinding.layoutContainer.etContainer.setText(conts[0]);
                    mDataBinding.layoutContainer.tvCabinetType0.setText(conts[1]);
                } else {
                    Container container = new Container();
                    container.setCount(ObjectUtils.parseInt(conts[0]));
                    container.setTitle(conts[1]);
                    containers.add(container);
                }
            }
            containerAdapter.appendToList(containers);
            containerAdapter.notifyDataSetChanged();
        }

    }

}
