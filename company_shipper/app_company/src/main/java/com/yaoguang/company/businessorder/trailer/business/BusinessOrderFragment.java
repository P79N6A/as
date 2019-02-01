package com.yaoguang.company.businessorder.trailer.business;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yaoguang.appcommon.publicsearch.BasePublicSearchFragment;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.publicsearch.loadingandunloading.LoadingAndUnloadingFragment;
import com.yaoguang.appcompanyshipper.databinding.FragmentBusinessOrderTrailerBinding;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.business.BaseBusinessOrderFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.businessorder.forwarder.templist.BusinessOrderTempListFragment;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.AppTruckSono;
import com.yaoguang.greendao.entity.company.AppInfoClientPlace;
import com.yaoguang.greendao.entity.company.AppTruckOrder;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务下单、编辑
 * Created by zhongjh on 2018/9/5.
 */
public class BusinessOrderFragment extends BaseBusinessOrderFragment<AppTruckOrder,FragmentBusinessOrderTrailerBinding> {

    public static BusinessOrderFragment newInstance(String id) {
        BusinessOrderFragment businessOrderFragment = new BusinessOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        businessOrderFragment.setArguments(bundle);
        return businessOrderFragment;
    }

    @Override
    protected void setOrder(String sonos) {
        if (mOrder == null)
            // 如果为null，就是创建新的单
            mOrder = new AppTruckOrder();

        // 判断装货还是卸货 1 ) 委托信息 物流
        if (mDataBinding.layoutEntrusted.tvValueLoadingAndUnloading.getText().equals(getResources().getString(R.string.loading_goods))) {
            mOrder.setOtherService("0");
        } else {
            mOrder.setOtherService("1");
        }
        //货物名称
        mOrder.setGoodsName(mDataBinding.layoutEntrusted.tvValueGoodsName.getText().toString());
        //货主
        mOrder.setOwner(mDataBinding.layoutEntrusted.etConsigneeIdonsigneeId.getText().toString());
        //港口
        mOrder.setPort(mDataBinding.layoutEntrusted.tvValuePort.getText().toString());
        //地区
        mOrder.setAddress(mDataBinding.layoutEntrusted.tvValueRegion.getText().toString());
        //船公司 2 ) 航次信息
        mOrder.setShipCompany(mDataBinding.layoutShipping.tvValueShippingCompany.getText().toString());
        //运单号
        mOrder.setmBlNo(mDataBinding.layoutShipping.etPortDestination.getText().toString());
        //预到船期
        if (!TextUtils.isEmpty(mDataBinding.layoutShipping.tvAdvanceShipmentDate.getText().toString()))
            mOrder.setEtaPlan(mDataBinding.layoutShipping.tvAdvanceShipmentDate.getText().toString());
        // 3 )装卸货信息
        mOrder.setAppInfoClientPlaces(mAppInfoClientPlaceLoadings);

        // 4 ) 货柜信息
        ArrayList<AppTruckSono> appTruckSonos = new ArrayList<>();
        for (AppTruckSono appTruckSono : containerAdapter.getList()) {
            appTruckSonos.add(appTruckSono);
        }
        mOrder.setAppTruckSonos(appTruckSonos);

        //裝/卸货时间 5 ) 其他
        mOrder.setLoadDate(mDataBinding.layoutCostinformation.tvLoadingDate.getText().toString() + " " + mDataBinding.layoutCostinformation.tvLoadingTime.getText().toString() + ":00");
        mOrder.setIsFeeCollect(mDataBinding.layoutCostinformation.cbValueIsFeeCollect.isChecked() ? 1 : 0);
        mOrder.setFee(ObjectUtils.parseDouble(mDataBinding.layoutCostinformation.etValueReservationFee.getText().toString().replace("¥", "")));
        mOrder.setRemark(mDataBinding.layoutCostinformation.etValueRemark.getText().toString());
        mOrder.setRemark1(mDataBinding.layoutCostinformation.etValueRemark1.getText().toString());
        mOrder.setRemark2(mDataBinding.layoutCostinformation.etValueRemark2.getText().toString());

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
//         装货信息 - 物流端
        mDataBinding.layoutLoading.rlLoading.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mDataBinding.layoutEntrusted.tvValueLoadingAndUnloading.getText().equals(getResources().getString(R.string.loading_goods))) {
                    if (mDataBinding.layoutEntrusted.tvValueShipper.getText().toString().equals("")) {
                        Toast.makeText(BaseApplication.getInstance(), "必须先选择托运人才可以选择装货地区！", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (mDataBinding.layoutEntrusted.tvValueRegion.getText().toString().equals("")) {
                        Toast.makeText(BaseApplication.getInstance(), "必须先选择地区才可以选择装货地区！", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    if (mDataBinding.layoutEntrusted.tvValueShipper.getText().toString().equals("")) {
                        Toast.makeText(BaseApplication.getInstance(), "必须先选择托运人才可以选择卸货地区！", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (mDataBinding.layoutEntrusted.tvValueRegion.getText().toString().equals("")) {
                        Toast.makeText(BaseApplication.getInstance(), "必须先选择地区才可以选择装货地区！", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                //获取托运人id
                BasePublicSearchFragment fragment = LoadingAndUnloadingFragment.newInstance(PublicSearchInteractorImpl.TYPE_LOADPLACES,
                        mOrder.getShipperId(), mDataBinding.layoutEntrusted.tvValueRegion.getText().toString(), null, mOrder.getAppInfoClientPlaces());
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_LOADPLACES));
            }
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
    public void showDetail(AppTruckOrder value, boolean isTemp) {
        mOrder = value;

        mDataBinding.flMain.setFocusable(true);

        // 判断状态是否已导入
        mIsExport = ObjectUtils.parseString(value.getStatus(),"0");

        initNumberInformation(value);
        initEntrusted(value);
        initIsLoadingOrUnLoading();
        initShipping(value);
        initLoadingAndUnLoading(value);

        // 如果是模版的，就直接返回
        if (isTemp)
            return;

        // 显示订单id
        mDataBinding.layoutInformation.llNumberInformation.setVisibility(View.VISIBLE);

        initCostinformation(value);
        initContainer(value);

        // 隐藏右上角菜单
        mToolbarCommonBinding.toolbar.getMenu().clear();
        if (ObjectUtils.parseString(mIsExport).equals("1")) {
            initIsExport();
        } else if (ObjectUtils.parseString(value.getStatus()).equals("0")) {
            // 显示取消预订单
            mDataBinding.btnCancel.setVisibility(View.VISIBLE);
            mDataBinding.btnComit.setText("重新提交");

            ((TextView) mToolbarCommonBinding.toolbar.findViewById(R.id.toolbar_title)).setText("修改预订单");
        }
    }

    /**
     * 单号信息
     */
    private void initNumberInformation(AppTruckOrder value) {
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
    private void initEntrusted(AppTruckOrder value) {
        //装卸 )委托信息
        if (value.getOtherService() == null || value.getOtherService().equals("0")) {
            //装货
            mDataBinding.layoutEntrusted.tvValueLoadingAndUnloading.setText(R.string.loading_goods);
        } else {
            //卸貨
            mDataBinding.layoutEntrusted.tvValueLoadingAndUnloading.setText(R.string.unloading_goods);
        }
        // 托运人 )委托信息
        mDataBinding.layoutEntrusted.tvValueShipper.setText(value.getShipper());
        mDataBinding.layoutEntrusted.tvValueShipper.setTag(value.getShipperId());
        // 货物名称
        mDataBinding.layoutEntrusted.tvValueGoodsName.setText(value.getGoodsName());
        // 货主姓名
        mDataBinding.layoutEntrusted.etConsigneeIdonsigneeId.setText(value.getOwner());
        // 港口
        mDataBinding.layoutEntrusted.tvValuePort.setText(value.getPort());
        //地区
        mDataBinding.layoutEntrusted.tvValueRegion.setText(value.getAddress());
    }

    /**
     * 船公司信息
     */
    private void initShipping(AppTruckOrder value) {
        //船公司
        mDataBinding.layoutShipping.tvValueShippingCompany.setText(value.getShipCompany());
        //运单号
        mDataBinding.layoutShipping.etPortDestination.setText(value.getmBlNo());
        //预到船期
        mDataBinding.layoutShipping.tvAdvanceShipmentDate.setText(value.getEtaPlan());
        mDataBinding.layoutShipping.tvAdvanceShipmentDateExport.setText(value.getEtaPlan());
    }

    /**
     * 装卸货信息
     */
    private void initLoadingAndUnLoading(AppTruckOrder value) {
        Gson gson = new Gson();
        ArrayList<AppInfoClientPlace> dockInfos = gson.fromJson(value.getDockInfos(), new TypeToken<List<AppInfoClientPlace>>() {
        }.getType());

        // 循环装卸货信息,只有一个装货或者卸货
        if (dockInfos != null) {
            ArrayList<AppPublicInfoWrapper> appPublicInfoWrappers = new ArrayList<>();
            if (dockInfos.size() > 0) {
                if (mIsExport.equals("0")) {
                    mDataBinding.layoutLoading.tvLoading.setText("添加");
                } else {
                    mDataBinding.layoutLoading.tvLoading.setText("");
                }
            }
            for (AppInfoClientPlace appInfoClientPlace : dockInfos) {
                AppPublicInfoWrapper appPublicInfoWrapper = new AppPublicInfoWrapper();
                appPublicInfoWrapper.setId(appInfoClientPlace.getId());
                appPublicInfoWrapper.setCheck(true);
                appPublicInfoWrapper.setFullName(appInfoClientPlace.getRegionid());
                appPublicInfoWrapper.setShortName(appInfoClientPlace.getAddress());
                appPublicInfoWrapper.setRemark1(appInfoClientPlace.getLinkman());
                appPublicInfoWrapper.setRemark2(appInfoClientPlace.getLinkmanMp());
                appPublicInfoWrapper.setRemark3(appInfoClientPlace.getLinkmanTel());
                appPublicInfoWrapper.setRemark4(appInfoClientPlace.getRemark());
                appPublicInfoWrappers.add(appPublicInfoWrapper);
            }
            //加载view
            initLoading(appPublicInfoWrappers);
        }

    }

    /**
     * 货柜信息
     */
    private void initContainer(AppTruckOrder value) {
        // )货柜信息
        if (!TextUtils.isEmpty(value.getSonos())){
            Gson gson = new Gson();
            mAppTruckSonos = gson.fromJson(value.getSonos(), new TypeToken<List<AppTruckSono>>() {
            }.getType());

            // )货柜信息
            containerAdapter.appendToList(mAppTruckSonos);
            containerAdapter.notifyDataSetChanged();
            showOrHideContainer();
        }
    }

    /**
     * 其他
     */
    private void initCostinformation(AppTruckOrder value) {
        // 装货时间 )其他
        if (value.getLoadDate() != null && value.getLoadDate().length() > 2) {
            String[] loadDates = value.getLoadDate().split(" ");
            mDataBinding.layoutCostinformation.tvLoadingDate.setText(loadDates[0]);
            mDataBinding.layoutCostinformation.tvLoadingTime.setText(loadDates[1].substring(0, loadDates[1].length() - 3));
        }
        //是否代收款
        mDataBinding.layoutCostinformation.cbValueIsFeeCollect.setChecked(value.getIsFeeCollect().equals("1"));
        // 预定费用
        mDataBinding.layoutCostinformation.etValueReservationFee.setText("¥" + value.getFee());
        // 备注
        mDataBinding.layoutCostinformation.etValueRemark.setText(value.getRemark());
        mDataBinding.layoutCostinformation.etValueRemark1.setText(value.getRemark1());
        mDataBinding.layoutCostinformation.etValueRemark2.setText(value.getRemark2());

    }



}
