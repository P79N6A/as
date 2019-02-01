package com.yaoguang.company.businessorder2.forwarder.businessmain.business;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search2.SearchFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.businessmain.BaseBusinessMainFragment;
import com.yaoguang.company.businessorder2.common.businessmain.business.BaseBusinessOrderFragment;
import com.yaoguang.company.businessorder2.common.businessmain.business.event.BusinessFragmentResultEvent;
import com.yaoguang.company.businessorder2.common.loadingandunloading.list.LoadingAndUnloadingListFragment;
import com.yaoguang.company.businessorder2.forwarder.Insurance.InsuranceFragment;
import com.yaoguang.company.businessorder2.forwarder.businessmain.BusinessMainFragment;
import com.yaoguang.company.businessorder2.common.businessmain.dynamic.event.InitEvent;
import com.yaoguang.company.businessorder2.forwarder.container.list.ContainerListFragment;
import com.yaoguang.company.businessorder2.forwarder.dispatchinginformation.DispatchingInformationFragment;
import com.yaoguang.company.businessorder2.forwarder.shippinginformation.ShippingInformationFragment;
import com.yaoguang.greendao.entity.common.ViewForwardOrder;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightBills;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightConsignee;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightInsurance;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightShip;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightShipper;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightSono;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightTruck;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.LogBillsTrack;
import com.yaoguang.greendao.entity.company.AppViewForwardOrderWrapper;
import com.yaoguang.greendao.entity.company.UpdateBusinessOrderModel;
import com.yaoguang.greendao.entity.driver.LocationArea;
import com.yaoguang.greendao.entity.driver.Site;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.lib.appcommon.widget.date.TimePickerFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.map.common.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 新建货代工作单
 * Created by zhongjh on 2018/10/24.
 */
@SuppressLint("ValidFragment")
public class BusinessOrderFragment extends BaseBusinessOrderFragment<ViewForwardOrder, AppViewForwardOrderWrapper> {

    /**
     * @param type 类型，新增、编辑、拷贝（模板）
     */
    @SuppressLint("ValidFragment")
    public BusinessOrderFragment(int type, String otherService) {
        super(type, otherService);
    }

    @Override
    public void setViewOrder(ViewForwardOrder viewForwardOrder) {
        super.setViewOrder(viewForwardOrder);
    }

    @Override
    protected void cloneData() {
        if (mOrder != null) {
            // 判断除了编辑模式，其他模式的拖车的派车信息不应该拷贝过去
            if (mType != TYPE_MODEL_UPDATE) {
                mOrder.setFreightTruck(null);
            }
            Gson gson = new Gson();
            String json = gson.toJson(mOrder);
            mOrderOld = gson.fromJson(json, ViewForwardOrder.class);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mAppOrder = new AppViewForwardOrderWrapper();
        super.onCreate(savedInstanceState);
    }

    public static BusinessOrderFragment newInstance(int type, String otherService) {
        return new BusinessOrderFragment(type, otherService);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        if (mType == TYPE_MODEL_COPY_OR_TEMP) {
            // 传递前清掉货柜数据、动态数据
            if (mOrder.getFreightBills() != null)
                mOrder.getFreightBills().setId(null);
            mOrder.setFreightSonoList(new ArrayList<>());
            mOrder.setLogBillsTrack(new LogBillsTrack());
        }

        if (mOrder != null) {
            initData(mOrder);
        } else {
            // 现在实例化一个全新没有数据的，为了后面的添加界面不是null的对象而报错
            mOrder = new ViewForwardOrder();
            mOrderOld = new ViewForwardOrder();
        }

    }

    @Override
    public void init() {
        super.init();
        mPresenter = new BusinessOrderPresenter(this);
        // 业务日期，除了更新，业务日期都是当天
        if (mType != TYPE_MODEL_UPDATE) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date date = new Date(System.currentTimeMillis());
            mDataBinding.tvValueBusinessDate.setText(simpleDateFormat.format(date));
        }
    }

    @Override
    public void initListener() {
        super.initListener();

        // 装货信息
        mDataBinding.flLoadingInformationAdd.setOnClickListener(v -> {
            if (TextUtils.isEmpty(ObjectUtils.parseString(mDataBinding.tvValueShipper.getTag()))) {
                ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请先选择托运人");
                return;
            }
            if (TextUtils.isEmpty(ObjectUtils.parseString(mDataBinding.tvValueDeparture.getText().toString()))) {
                ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请先选择起运地");
                return;
            }
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(LoadingAndUnloadingListFragment.newInstance(0, ObjectUtils.parseString(mDataBinding.tvValueShipper.getTag()), mDataBinding.tvValueDeparture.getText().toString(), mDataBinding.etConsigneeIdonsigneeId.getText().toString()), TYPE_LOADINGINFORMATION);
        });

        // 卸货信息
        mDataBinding.flUnloadingInformationAdd.setOnClickListener(v -> {
            if (TextUtils.isEmpty(ObjectUtils.parseString(mDataBinding.tvValueShipper.getTag()))) {
                ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请先选择托运人");
                return;
            }
            if (TextUtils.isEmpty(ObjectUtils.parseString(mDataBinding.tvValueDestination.getText().toString()))) {
                ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请先选择目的地");
                return;
            }
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(LoadingAndUnloadingListFragment.newInstance(1, ObjectUtils.parseString(mDataBinding.tvValueShipper.getTag()), mDataBinding.tvValueDestination.getText().toString(), mDataBinding.etConsigneeIdonsigneeId.getText().toString()), TYPE_UNLOADINGINFORMATION);
        });

        // 保险信息跳转
        mDataBinding.flBuyInsuranceAdd.setOnClickListener(v -> ((BusinessMainFragment) getParentFragment()).startForResult(InsuranceFragment.newInstance(mOrder.getFreightInsurance()), TYPE_INSURANCE));
        mDataBinding.tvInsuranceEdit.setOnClickListener(v -> ((BusinessMainFragment) getParentFragment()).startForResult(InsuranceFragment.newInstance(mOrder.getFreightInsurance()), TYPE_INSURANCE));

        // 船运更新信息
        mDataBinding.flWaybillNumberAdd.setOnClickListener(v -> ((BusinessMainFragment) getParentFragment()).startForResult(ShippingInformationFragment.newInstance(mOrder.getFreightShip()), TYPE_SHIP));
        mDataBinding.tvAgreen.setOnClickListener(v -> ((BusinessMainFragment) getParentFragment()).startForResult(ShippingInformationFragment.newInstance(mOrder.getFreightShip()), TYPE_SHIP));

        // 装货派车信息
        mDataBinding.flLoadingDispatchingInformationAdd.setOnClickListener(v -> ((BusinessMainFragment) getParentFragment()).startForResult(DispatchingInformationFragment.newInstance(mOrder.getFreightTruck(), 0), TYPE_DISPATCHING_LOADING));
        // 装货派车编辑
        mDataBinding.tvLoadingDispatchingEdit.setOnClickListener(v -> ((BusinessMainFragment) getParentFragment()).startForResult(DispatchingInformationFragment.newInstance(mOrder.getFreightTruck(), 0), TYPE_DISPATCHING_LOADING));

        // 卸货派车信息
        mDataBinding.flUnLoadingDispatchingInformationAdd.setOnClickListener(v -> ((BusinessMainFragment) getParentFragment()).startForResult(DispatchingInformationFragment.newInstance(mOrder.getFreightTruck(), 1), TYPE_DISPATCHING_UNLOADING));
        // 卸货派车编辑
        mDataBinding.tvDispatchingEdit.setOnClickListener(v -> ((BusinessMainFragment) getParentFragment()).startForResult(DispatchingInformationFragment.newInstance(mOrder.getFreightTruck(), 1), TYPE_DISPATCHING_UNLOADING));

        // 货柜（编辑版）
        mDataBinding.llClickContainer.setOnClickListener(v -> {
            ArrayList<FreightSono> freightSonos = new ArrayList<>();
            freightSonos.addAll(mOrder.getFreightSonoList());
            // 判断是否核柜
            if (mOrder != null && mOrder.getLogBillsTrack() != null && ObjectUtils.parseInt(mOrder.getLogBillsTrack().getIsComDate()) == 0) {
                ((BusinessMainFragment) getParentFragment()).startForResult(ContainerListFragment.newInstance(freightSonos, "1", 0), TYPE_SONO);
            } else {
                ((BusinessMainFragment) getParentFragment()).startForResult(ContainerListFragment.newInstance(freightSonos, "1", 1), TYPE_SONO);
            }
        });

        // region 委托信息

        // 托运人
        mDataBinding.rlShipper.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER111);
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER111));
        });

        // 起运地
        mDataBinding.rlDeparture.setOnClickListener(v -> {
            // 判断物流端或者货主端
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DEPARTURE222);
            } else {
                if (TextUtils.isEmpty(mDataBinding.tvValueShipper.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.tvValueShipper.getHint().toString());
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DEPARTURE222, mDataBinding.tvValueShipper.getTag().toString());
            }
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DEPARTURE222));
        });

        // 目的地
        mDataBinding.rlDestination.setOnClickListener(v -> {
            // 判断物流端或者货主端
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DESTINATION333);
            } else {
                if (TextUtils.isEmpty(mDataBinding.tvValueShipper.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请选择托运人");
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DESTINATION333, mDataBinding.tvValueShipper.getTag().toString());
            }
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DESTINATION333));
        });

        // 货物名称
        mDataBinding.rlGoodsName.setOnClickListener(v -> {
            // 判断物流端或者货主端
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_GOODSNAME);
            } else {
                if (TextUtils.isEmpty(mDataBinding.tvValueShipper.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请选择托运人");
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_GOODSNAME, mDataBinding.tvValueShipper.getTag().toString());
            }
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_GOODSNAME));
        });

        // 业务日期
        mDataBinding.rlBusinessDate.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "BusinessDate");
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("BusinessDate")) {
                    mDataBinding.tvValueBusinessDate.setText(data);
                }
            });
        });

        // 业务员
        mDataBinding.rlSalesman.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_EMPLOYEES);
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_EMPLOYEES));
        });

        // 操作条款属于sheet
        mDataBinding.rlOperationClause.setOnClickListener(v -> showSweetSheets(mDataBinding.rlOperationClause.getId()));

        // 货代公司
        mDataBinding.rlForwardingCompany.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_FORWARD);
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_FORWARD));
        });

        // 收款方式属于sheet
        mDataBinding.rlPaymentMethod.setOnClickListener(v -> showSweetSheets(mDataBinding.rlPaymentMethod.getId()));

        // endregion

        // 保险信息设置显示是否添加
        mDataBinding.cbBuyInsurance.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (ObjectUtils.parseString(mDataBinding.tvValueBLNO.getText()).equals("")) {
                    mDataBinding.flBuyInsuranceAdd.setVisibility(View.VISIBLE);
                } else {
                    mDataBinding.rlInsuranceDetail.setVisibility(View.VISIBLE);
                    mDataBinding.tabInsuranceDetail.setVisibility(View.VISIBLE);
                }
            } else {
                mDataBinding.rlInsuranceDetail.setVisibility(View.GONE);
                mDataBinding.tabInsuranceDetail.setVisibility(View.GONE);
                mDataBinding.flBuyInsuranceAdd.setVisibility(View.GONE);
            }
        });

        // region 船运信息

        // 船公司
        mDataBinding.rlShipCompany.setOnClickListener(v -> {
            // 判断物流端或者货主端，TYPE_PORTOFDESTINATION66跟TYPE_PORTOFDESTINATION不一样的，返回都一样是TYPE_PORTOFDESTINATION66
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY);
            } else {
                if (TextUtils.isEmpty(mDataBinding.tvValueShipper.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请选择托运人");
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY, mDataBinding.tvValueShipper.getTag().toString());
            }
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY));
        });

        // 预开船期
        mDataBinding.rlPreopeningDate.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "PreopeningDate");
            Bundle args = new Bundle();
            args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
            dateBeginPickerFragment.setArguments(args);
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("PreopeningDate" + TimePickerFragment.TAGTIME)) {
                    mDataBinding.tvValuePreopeningDate.setText(data);
                }
            });
        });

        // 干线船名
        mDataBinding.rlTrunkShipName.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_USER_INFO_SHIP);
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_USER_INFO_SHIP));
        });

        // 预抵船期
        mDataBinding.rlArrivalTimeDate.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "ArrivalTimeDate");
            Bundle args = new Bundle();
            args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
            dateBeginPickerFragment.setArguments(args);
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("ArrivalTimeDate" + TimePickerFragment.TAGTIME)) {
                    mDataBinding.tvValueArrivalTime.setText(data);
                }
            });
        });

        // 运输条款
        mDataBinding.rlTransportTerms.setOnClickListener(v -> showSweetSheets(mDataBinding.rlTransportTerms.getId()));

        // 起运港
        mDataBinding.rlPortOfDeparture.setOnClickListener(v -> {
            // 判断物流端或者货主端，TYPE_PORTOFSHIPMENT55跟TYPE_PORTOFSHIPMENT不一样的，返回都一样是TYPE_PORTOFSHIPMENT55
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT55);
            } else {
                if (TextUtils.isEmpty(mDataBinding.tvValueShipper.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请选择托运人");
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT, mDataBinding.tvValueShipper.getTag().toString());
            }
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT55));
        });

        // 目的港
        mDataBinding.rlPortOfDestination.setOnClickListener(v -> {
            // 判断物流端或者货主端，TYPE_PORTOFDESTINATION66跟TYPE_PORTOFDESTINATION不一样的，返回都一样是TYPE_PORTOFDESTINATION66
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION66);
            } else {
                if (TextUtils.isEmpty(mDataBinding.tvValueShipper.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请选择托运人");
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION, mDataBinding.tvValueShipper.getTag().toString());
            }
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION66));
        });

        // endregion 船运信息

        // 添加
        mDataBinding.btnComit.setOnClickListener(v -> {
            // 验证为空
            if (TextUtils.isEmpty(mDataBinding.tvValueShipper.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择托运人", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.tvValueDeparture.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择起运地", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.tvValueDestination.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择目的地", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.tvValueGoodsName.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择货名", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.tvValueBusinessDate.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择业务日期", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.tvValueSalesman.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择业务员", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.tvValueOperationClause.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择操作条款", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.tvValueTransportTerms.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择运输条款", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.tvValuePortOfDeparture.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择起运港", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.tvValuePortOfDestination.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择目的港", Toast.LENGTH_SHORT).show();
                return;
            }

            // 全部赋值
            loadEntrusted();
            loadInsurance();
            loadShipping();
            loadLoading();
            loadUnLoading();
            loadDispatching();
            loadUnDispatching();
            loadCont();
            if (mType == TYPE_MODEL_ADD || mType == TYPE_MODEL_COPY_OR_TEMP) {
                mPresenter.addOrder(mOrder, true);
            } else if (mType == TYPE_MODEL_UPDATE) {
                mAppOrder.setNewViewForwardOrder(mOrder);
                mAppOrder.setOldViewForwardOrder(mOrderOld);
                mPresenter.updateOrder(mAppOrder, true);
            }
        });

    }

    @Override
    public void searchPaymentMethod(List options1Items) {
        // sheet收款方式
        initSweetSheets(mDataBinding.rlPaymentMethod.getId(), mDataBinding.flMain, "收款方式", options1Items, (position, menuEntity) -> {
            mDataBinding.tvValuePaymentMethod.setText(menuEntity.title.toString());
            return true;
        });
    }

    @Override
    public void onBusinessFragmentResultEvent(BusinessFragmentResultEvent businessFragmentResultEvent) {
        super.onBusinessFragmentResultEvent(businessFragmentResultEvent);
        switch (businessFragmentResultEvent.getType()) {
            case TYPE_INSURANCE:
                // 保险信息
                mOrder.setFreightInsurance(businessFragmentResultEvent.getData().getParcelable(InsuranceFragment.INSURANCE));
                initInsurance(businessFragmentResultEvent.getData().getParcelable(InsuranceFragment.INSURANCE));
                break;
            case TYPE_SHIP:
                // 更多的船运信息
                mOrder.setFreightShip(businessFragmentResultEvent.getData().getParcelable(ShippingInformationFragment.FREIGHT_SHIP));
                initShipping(businessFragmentResultEvent.getData().getParcelable(ShippingInformationFragment.FREIGHT_SHIP));
                break;
            case TYPE_DISPATCHING_LOADING:
                mOrder.setFreightTruck(businessFragmentResultEvent.getData().getParcelable(DispatchingInformationFragment.FREIGH_TTRUCK));
                initDispatching(businessFragmentResultEvent.getData().getParcelable(DispatchingInformationFragment.FREIGH_TTRUCK));
                break;
            case TYPE_DISPATCHING_UNLOADING:
                mOrder.setFreightTruck(businessFragmentResultEvent.getData().getParcelable(DispatchingInformationFragment.FREIGH_TTRUCK));
                initUnDispatching(businessFragmentResultEvent.getData().getParcelable(DispatchingInformationFragment.FREIGH_TTRUCK));
                break;
            case TYPE_SONO:
                // 货柜
                List<FreightSono> freightSonos = businessFragmentResultEvent.getData().getParcelableArrayList("freightSonos");
                mOrder.setFreightSonoList(freightSonos);
                initContainer(freightSonos);
                break;
        }
    }

    @Override
    public void showUpdateFowardOrder(UpdateBusinessOrderModel result) {
        if (result.getShowDialog().equals("1")) {
            // 弹出对话框
            if (mDialogHelper == null)
                mDialogHelper = new DialogHelper(getContext(), "提示", result.getInfo(), new CommonDialog.Listener() {
                    @Override
                    public void ok(String msg) {
                        mDialogHelper.hideDialog();
                        if (mType == TYPE_MODEL_ADD || mType == TYPE_MODEL_COPY_OR_TEMP) {
                            mPresenter.addOrder(mOrder, false);
                        } else if (mType == TYPE_MODEL_UPDATE) {
                            mAppOrder.setNewViewForwardOrder(mOrder);
                            mAppOrder.setOldViewForwardOrder(mOrderOld);
                            mPresenter.updateOrder(mAppOrder, false);
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
            mDialogHelper.show();
        } else {
            if (mDialogHelperPop == null)
                mDialogHelperPop = new DialogHelper(getContext(), "提示", result.getInfo(), "继续编辑", "返回列表", new CommonDialog.Listener() {
                    @Override
                    public void ok(String msg) {
                        ((BaseBusinessMainFragment) getParentFragment()).pop();
                        // 跳转当前界面
                        mDialogHelperPop.hideDialog();
                        if (!TextUtils.isEmpty(result.getId()))
                            ((BaseBusinessMainFragment) getParentFragment()).start(BusinessMainFragment.newInstance(result.getId(), "0", mOtherService));
                        else
                            ((BaseBusinessMainFragment) getParentFragment()).start(BusinessMainFragment.newInstance(mOrder.getFreightBills().getId(), "0", mOtherService));
                    }

                    @Override
                    public void cancel() {
                        ((BaseBusinessMainFragment) getParentFragment()).pop();
                    }

                });
            mDialogHelperPop.show();
        }
    }

    @Override
    public void deleteLoadAddressData(String id, String billsId) {
        // 循环装货数据，删除
        Iterator<FreightShipper> iterator = mOrder.getFreightShipperList().iterator();
        while (iterator.hasNext()) {
            FreightShipper freightShipper = iterator.next();
            if (freightShipper.getId().equals(id) && freightShipper.getBillsId().equals(billsId)) {
                iterator.remove();
                return;
            }
        }
    }

    @Override
    public void deleteUnLoadAddressData(String id, String billsId) {
        // 循环卸货数据，删除
        Iterator<FreightConsignee> iterator = mOrder.getFreightConsigneeList().iterator();
        while (iterator.hasNext()) {
            FreightConsignee freightConsignee = iterator.next();
            if (freightConsignee.getId().equals(id) && freightConsignee.getBillsId().equals(billsId)) {
                iterator.remove();
                return;
            }
        }
    }

    /**
     * 全部初始化数据
     *
     * @param value 数据
     */
    protected void initData(ViewForwardOrder value) {
        mDataBinding.flMain.setFocusable(true);

        initEntrusted(value);
        initInsurance(value.getFreightInsurance());
        initShipping(value.getFreightShip());
        initLoading(value);
        initUnLoading(value);
        initDispatching(value.getFreightTruck());
        initUnDispatching(value.getFreightTruck());
        initContainer(value.getFreightSonoList());
        initOther(value);
        initLogBillsTrack(value);
    }

    /**
     * 装载委托信息
     */
    private void loadEntrusted() {
        if (mOrder.getFreightBills() == null) {
            mOrder.setFreightBills(new FreightBills());
        }
        // 托运人 )委托信息
        mOrder.getFreightBills().setShipper(mDataBinding.tvValueShipper.getText().toString());
        mOrder.getFreightBills().setShipperId(ObjectUtils.parseString(mDataBinding.tvValueShipper.getTag()));
        // 起运地
        mOrder.getFreightBills().setDockOfLoading(mDataBinding.tvValueDeparture.getText().toString());
        // 目的地
        mOrder.getFreightBills().setFinalDestination(mDataBinding.tvValueDestination.getText().toString());
        // 货物名称
        mOrder.getFreightBills().setGoodsName(mDataBinding.tvValueGoodsName.getText().toString());
        // 业务日期
        mOrder.getFreightBills().setModifyDate(mDataBinding.tvValueBusinessDate.getText().toString());
        // 货主
        mOrder.getFreightBills().setConsigneeId(mDataBinding.etConsigneeIdonsigneeId.getText().toString());
        // 业务员
        mOrder.getFreightBills().setEmployeeId(mDataBinding.tvValueSalesman.getText().toString());
        // 操作条款
        mOrder.getFreightBills().setCarriageItem(mDataBinding.tvValueOperationClause.getText().toString());
        // 货代公司
        mOrder.getFreightBills().setFreightCompany(mDataBinding.tvValueForwardingCompany.getText().toString());
        // 委托单号
        mOrder.getFreightBills().setConsolCode(mDataBinding.etEntrustmentSingleNumber.getText().toString());
        // 收款方式
        mOrder.getFreightBills().setPrepaidCollect(mDataBinding.tvValuePaymentMethod.getText().toString());
        // 毛重
        mOrder.getFreightBills().setGoodsGrossWeight(ObjectUtils.parseDouble(mDataBinding.etGrossWeight.getText().toString()));
    }

    /**
     * 装载保险信息
     */
    private void loadInsurance() {
        if (mOrder.getFreightInsurance() == null) {
            mOrder.setFreightInsurance(new FreightInsurance());
        }
        if (mDataBinding.cbBuyInsurance.isChecked()) {
            mOrder.getFreightInsurance().setIsInsurance(1);
            // 保险单号
            mOrder.getFreightInsurance().sethBlNo(ObjectUtils.EmptyIf(mDataBinding.tvValueBLNO.getText().toString(), "未知"));
            // 保险类型
            mOrder.getFreightInsurance().setInsurType(ObjectUtils.EmptyIf(mDataBinding.tvValueInsuranceType.getText().toString(), "未知"));
            // 投保时间
            mOrder.getFreightInsurance().setInstoreStatusDate(ObjectUtils.EmptyIf(mDataBinding.tvValueInsuranceDate.getText().toString(), "未知"));
            // 货值万元
            mOrder.getFreightInsurance().setInsurValue(ObjectUtils.EmptyIf(mDataBinding.tvValueInsuranceValue.getText().toString(), "未知"));
            // 费率
            mOrder.getFreightInsurance().setInsurRate(ObjectUtils.EmptyIf(mDataBinding.tvValueInsuranceValue.getText().toString(), "未知"));
            // 保险费
            mOrder.getFreightInsurance().setInsurMoney(ObjectUtils.EmptyIf(mDataBinding.tvValueInsuranceMoney.getText().toString(), "未知"));
            // 保险公司
            mOrder.getFreightInsurance().setAgency(ObjectUtils.EmptyIf(mDataBinding.tvValueInsuranceAgency.getText().toString(), "未知"));
        } else {
            mOrder.getFreightInsurance().setIsInsurance(0);
        }
    }

    /**
     * 装载船运信息
     */
    private void loadShipping() {
        if (mOrder.getFreightShip() == null) {
            mOrder.setFreightShip(new FreightShip());
        }
        // 运输条款
        mOrder.getFreightShip().setCarriageWay(mDataBinding.tvValueTransportTerms.getText().toString());
        // 起运港
        mOrder.getFreightShip().setPortLoading(mDataBinding.tvValuePortOfDeparture.getText().toString());
        // 目的港
        mOrder.getFreightShip().setPortDelivery(mDataBinding.tvValuePortOfDestination.getText().toString());
        // 船公司
        mOrder.getFreightShip().setShipCompany(mDataBinding.tvValueShipCompany.getText().toString());
        // 干线船名
        mOrder.getFreightShip().setVessel(mDataBinding.tvValueTrunkShipName.getText().toString());
        // 干线航次
        mOrder.getFreightShip().setVoyage(mDataBinding.etValueMainLineVoyage.getText().toString());
        // 预开船期
        mOrder.getFreightShip().setInVesselPlanTime(mDataBinding.tvValuePreopeningDate.getText().toString());
        // 预抵船期
        mOrder.getFreightShip().setEtaPlan2(mDataBinding.tvValueArrivalTime.getText().toString());

        // 支线船名
        mOrder.getFreightShip().setSecondVessel(ObjectUtils.EmptyIf(mDataBinding.tvValueShipName.getText().toString(), "未知"));
        // 支线航次
        mOrder.getFreightShip().setSecondVoyage(ObjectUtils.EmptyIf(mDataBinding.tvValueLineVoyage.getText().toString(), "未知"));
        // 支线中转期
        mOrder.getFreightShip().setSecondEtd(ObjectUtils.EmptyIf(mDataBinding.tvValueInsuranceTime.getText().toString(), "未知"));
        // 支线到期
        mOrder.getFreightShip().setSecondEta(ObjectUtils.EmptyIf(mDataBinding.tvValueBranchMaturity.getText().toString(), "未知"));
        // 三程船名
        mOrder.getFreightShip().setThirdVessel(ObjectUtils.EmptyIf(mDataBinding.tvValueThreeShipName.getText().toString(), "未知"));
        // 三程航次
        mOrder.getFreightShip().setThirdVoyage(ObjectUtils.EmptyIf(mDataBinding.tvValueThreeVoyage.getText().toString(), "未知"));
        // 三程开船期
        mOrder.getFreightShip().setThirdEtd(ObjectUtils.EmptyIf(mDataBinding.tvValueThreeWaySailingPeriod.getText().toString(), "未知"));
        // 驳船公司
        mOrder.getFreightShip().setFeederCompany(ObjectUtils.EmptyIf(mDataBinding.tvValueBargeCompany.getText().toString(), "未知"));
    }

    /**
     * 装载装货信息
     */
    private void loadLoading() {
        List<FreightShipper> freightShipperList = new ArrayList<>();
        for (int i = 0; i < mDataBinding.llLoading.getChildCount(); i++) {
            ViewHolderLoadingAndUnloading viewHolderLoadingAndUnloading = new ViewHolderLoadingAndUnloading(mDataBinding.llLoading.getChildAt(i));
            FreightShipper freightShipper = new FreightShipper();
            // 单位
            freightShipper.setConsignerUnit(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueUnitName.getText().toString(), "未知"));
            // 地址
            freightShipper.setJhr(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueAddress.getText().toString(), "未知"));
            // 联系人
            freightShipper.setCompanyLxr(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingContacts.getText().toString(), "未知"));
            // 手机
            freightShipper.setShipperMp(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingMobile.getText().toString(), "未知"));
            // 电话
            freightShipper.setShipperTel(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingPhone.getText().toString(), "未知"));
            // 传真
            freightShipper.setConsignerUnitFax(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingFax.getText().toString(), "未知"));
            // 说明
            freightShipper.setRemark3(ObjectUtils.parseString(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.getTag()));
            // 经纬度
            Site site = new Site();
            site.setLon(ObjectUtils.parseDouble(viewHolderLoadingAndUnloading.tvLon.getText().toString()));
            site.setLat(ObjectUtils.parseDouble(viewHolderLoadingAndUnloading.tvLat.getText().toString()));
            LocationArea locationArea = new LocationArea();
            locationArea.setSite(site);
            freightShipper.setLocationArea(locationArea);

            freightShipper.setId(viewHolderLoadingAndUnloading.tvId.getText().toString());
            freightShipper.setBillsId(viewHolderLoadingAndUnloading.tvBillsId.getText().toString());

            freightShipperList.add(freightShipper);
        }
        mOrder.setFreightShipperList(freightShipperList);
    }

    /**
     * 装载卸货信息
     */
    private void loadUnLoading() {
        List<FreightConsignee> freightConsigneeList = new ArrayList<>();
        for (int i = 0; i < mDataBinding.llUnLoading.getChildCount(); i++) {
            ViewHolderLoadingAndUnloading viewHolderLoadingAndUnloading = new ViewHolderLoadingAndUnloading(mDataBinding.llUnLoading.getChildAt(i));
            FreightConsignee freightConsignee = new FreightConsignee();

            // 单位
            freightConsignee.setConsigneeUnit(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueUnitName.getText().toString(), "未知"));
            // 地址
            freightConsignee.setConsigneePlace(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueAddress.getText().toString(), "未知"));
            // 联系人
            freightConsignee.setConsigneeLinkman(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingContacts.getText().toString(), "未知"));
            // 手机
            freightConsignee.setConsigneeMp(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingMobile.getText().toString(), "未知"));
            // 电话
            freightConsignee.setConsigneeTel(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingPhone.getText().toString(), "未知"));
            // 传真
            freightConsignee.setConsigneeUnitFax(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingFax.getText().toString(), "未知"));
            // 说明
            freightConsignee.setRemark9(ObjectUtils.parseString(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.getTag()));

            // 经纬度
            Site site = new Site();
            site.setLon(ObjectUtils.parseDouble(viewHolderLoadingAndUnloading.tvLon.getText().toString()));
            site.setLat(ObjectUtils.parseDouble(viewHolderLoadingAndUnloading.tvLat.getText().toString()));
            LocationArea locationArea = new LocationArea();
            locationArea.setSite(site);
            freightConsignee.setLocationArea(locationArea);

            freightConsignee.setId(viewHolderLoadingAndUnloading.tvId.getText().toString());
            freightConsignee.setBillsId(viewHolderLoadingAndUnloading.tvBillsId.getText().toString());

            freightConsigneeList.add(freightConsignee);
        }
        mOrder.setFreightConsigneeList(freightConsigneeList);
    }

    /**
     * 装载装货派车信息
     */
    private void loadDispatching() {
        if (mOrder.getFreightTruck() == null) {
            mOrder.setFreightTruck(new FreightTruck());
        }
        // 装货优先级
        mOrder.getFreightTruck().setGoodsNameE(ObjectUtils.EmptyIf(mDataBinding.tvValueDispatchingPriority.getText().toString(), "未知"));
        // 装货时间
        mOrder.getFreightTruck().setLoadDate(ObjectUtils.EmptyIf(mDataBinding.tvValueDispatchingLoadingTime.getText().toString(), "未知"));
        // 拖车公司
        mOrder.getFreightTruck().setPreTruck(ObjectUtils.EmptyIf(mDataBinding.tvValueDispatchingTowingCo.getText().toString(), "未知"));
        // 订舱委托人
        mOrder.getFreightTruck().setPreShipCompany(ObjectUtils.EmptyIf(mDataBinding.tvValueDispatchingBookingAgent.getText().toString(), "未知"));
    }

    /**
     * 装载卸货派车信息
     */
    private void loadUnDispatching() {
        if (mOrder.getFreightTruck() == null) {
            mOrder.setFreightTruck(new FreightTruck());
        }
        // 卸货优先级
        mOrder.getFreightTruck().setGoodsPriority(ObjectUtils.EmptyIf(mDataBinding.tvValueDispatchingUnPriority.getText().toString(), "未知"));
        // 计划送货时间
        mOrder.getFreightTruck().setReleasePlanDate(ObjectUtils.EmptyIf(mDataBinding.tvValueDispatchingUnLoadingTime.getText().toString(), "未知"));
        // 拖车公司
        mOrder.getFreightTruck().setDestTruck(ObjectUtils.EmptyIf(mDataBinding.tvValueDispatchingUnTowingCo.getText().toString(), "未知"));
        // 订舱收货人
        mOrder.getFreightTruck().setDesShipCompany(ObjectUtils.EmptyIf(mDataBinding.tvValueDispatchingUnBookingAgent.getText().toString(), "未知"));
    }

    /**
     * 装载货柜数据
     */
    private void loadCont() {
        if (mType == TYPE_MODEL_ADD || mType == TYPE_MODEL_COPY_OR_TEMP) {
            // 如果是添加的，就需要从控件上获取
            List<FreightSono> freightSonos = new ArrayList<>();
            for (int i = 0; i < mDataBinding.llContainer.getChildCount(); i++) {
                ViewHolderContainerCount viewHolderContainerCount = new ViewHolderContainerCount(mDataBinding.llContainer.getChildAt(i));
                for (int k = 1; k <= ObjectUtils.parseDouble(viewHolderContainerCount.tvValue.getText().toString()); k++) {
                    FreightSono freightSono = new FreightSono();
                    freightSono.setContId(viewHolderContainerCount.tvTitle.getText().toString());
                    freightSonos.add(freightSono);
                }
            }
            mOrder.setFreightSonoList(freightSonos);
        }
        // 如果是更新的，不需要从控件上获取

    }

    /**
     * 委托信息
     */
    @SuppressLint("SetTextI18n")
    protected void initEntrusted(ViewForwardOrder value) {
        // 托运人 )委托信息
        mDataBinding.tvValueShipper.setText(ObjectUtils.parseString(value.getFreightBills().getShipper(), ""));
        mDataBinding.tvValueShipper.setTag(value.getFreightBills().getShipperId());
        // 起运地
        mDataBinding.tvValueDeparture.setText(ObjectUtils.parseString(value.getFreightBills().getDockOfLoading(), ""));
        // 目的地
        mDataBinding.tvValueDestination.setText(ObjectUtils.parseString(value.getFreightBills().getFinalDestination(), ""));
        // 货物名称
        mDataBinding.tvValueGoodsName.setText(ObjectUtils.parseString(value.getFreightBills().getGoodsName(), ""));
        // 业务日期，除了更新，业务日期都是当天
        if (mType == TYPE_MODEL_UPDATE) {
            mDataBinding.tvValueBusinessDate.setText(ObjectUtils.parseString(value.getFreightBills().getModifyDate(), ""));
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date date = new Date(System.currentTimeMillis());
            mDataBinding.tvValueBusinessDate.setText(simpleDateFormat.format(date));
        }
        // 货主
        mDataBinding.etConsigneeIdonsigneeId.setText(ObjectUtils.parseString(value.getFreightBills().getConsigneeId(), ""));
        // 业务员
        mDataBinding.tvValueSalesman.setText(ObjectUtils.parseString(value.getFreightBills().getEmployeeId(), ""));
        // 操作条款
        mDataBinding.tvValueOperationClause.setText(ObjectUtils.parseString(value.getFreightBills().getCarriageItem(), ""));
        // 货代公司
        mDataBinding.tvValueForwardingCompany.setText(ObjectUtils.parseString(value.getFreightBills().getFreightCompany(), ""));
        // 委托单号
        mDataBinding.etEntrustmentSingleNumber.setText(ObjectUtils.parseString(value.getFreightBills().getConsolCode(), ""));
        // 收款方式
        mDataBinding.tvValuePaymentMethod.setText(ObjectUtils.parseString(value.getFreightBills().getPrepaidCollect(), ""));
        // 毛重
        mDataBinding.etGrossWeight.setText(ObjectUtils.parseString(value.getFreightBills().getGoodsGrossWeight(), ""));
    }

    /**
     * 保险信息
     */
    protected void initInsurance(FreightInsurance value) {
        if (value == null)
            return;

        if (ObjectUtils.parseString(value.getIsInsurance()).equals("1")) {
            mDataBinding.cbBuyInsurance.setChecked(true);

            if (ObjectUtils.parseString(value.getAgency()).equals("") &&
                    ObjectUtils.parseString(value.gethBlNo()).equals("") &&
                    ObjectUtils.parseString(value.getInsurType()).equals("") &&
                    ObjectUtils.parseString(value.getInstoreStatusDate()).equals("") &&
                    ObjectUtils.parseString(value.getInsurType()).equals("") &&
                    ObjectUtils.parseString(value.getInsurRate()).equals("") &&
                    ObjectUtils.parseString(value.getInsurMoney()).equals("")) {
                mDataBinding.flBuyInsuranceAdd.setVisibility(View.VISIBLE);
            } else {
                mDataBinding.flBuyInsuranceAdd.setVisibility(View.GONE);
                mDataBinding.rlInsuranceDetail.setVisibility(View.VISIBLE);
                mDataBinding.tabInsuranceDetail.setVisibility(View.VISIBLE);
            }
        } else {
            mDataBinding.cbBuyInsurance.setChecked(false);

            mDataBinding.rlInsuranceDetail.setVisibility(View.GONE);
            mDataBinding.tabInsuranceDetail.setVisibility(View.GONE);
            mDataBinding.flBuyInsuranceAdd.setVisibility(View.GONE);
        }

        // 保险单号
        mDataBinding.tvValueBLNO.setText(ObjectUtils.parseString(value.gethBlNo(), "未知"));
        // 保险类型
        mDataBinding.tvValueInsuranceType.setText(ObjectUtils.parseString(value.getInsurType(), "未知"));
        // 投保时间
        mDataBinding.tvValueInsuranceDate.setText(ObjectUtils.parseString(value.getInstoreStatusDate(), "未知"));
        // 货值万元
        mDataBinding.tvValueInsuranceValue.setText(ObjectUtils.parseString(value.getInsurValue(), "未知"));
        // 费率
        mDataBinding.tvValueInsuranceRate.setText(ObjectUtils.parseString(value.getInsurRate(), "未知"));
        // 保险费
        mDataBinding.tvValueInsuranceMoney.setText(ObjectUtils.parseString(value.getInsurMoney(), "未知"));
        // 保险公司
        mDataBinding.tvValueInsuranceAgency.setText(ObjectUtils.parseString(value.getAgency(), "未知"));
    }

    /**
     * 船运信息
     */
    protected void initShipping(FreightShip value) {
        // 运输条款
        mDataBinding.tvValueTransportTerms.setText(ObjectUtils.parseString(value.getCarriageWay()));
        // 起运港
        mDataBinding.tvValuePortOfDeparture.setText(ObjectUtils.parseString(value.getPortLoading()));
        // 目的港
        mDataBinding.tvValuePortOfDestination.setText(ObjectUtils.parseString(value.getPortDelivery()));
        // 船公司
        mDataBinding.tvValueShipCompany.setText(ObjectUtils.parseString(value.getShipCompany()));
        // 干线船名
        mDataBinding.tvValueTrunkShipName.setText(ObjectUtils.parseString(value.getVessel()));
        // 干线航次
        mDataBinding.etValueMainLineVoyage.setText(ObjectUtils.parseString(value.getVoyage()));
        // 预开船期
        mDataBinding.tvValuePreopeningDate.setText(ObjectUtils.parseString(value.getInVesselPlanTime()));
        // 预抵船期
        mDataBinding.tvValueArrivalTime.setText(ObjectUtils.parseString(value.getEtaPlan2()));
        // 运单号
        mDataBinding.etWaybillNumber.setText(ObjectUtils.parseString(value.getmBlNo()));

        // 隐藏和显示
        mDataBinding.flWaybillNumberAdd.setVisibility(View.GONE);
        mDataBinding.flWaybillNumberEdit1.setVisibility(View.VISIBLE);
        mDataBinding.tabWaybillNumberEdit1.setVisibility(View.VISIBLE);
        mDataBinding.flWaybillNumberEdit2.setVisibility(View.VISIBLE);
        mDataBinding.tabWaybillNumberEdit2.setVisibility(View.VISIBLE);
        // 支线船名
        mDataBinding.tvValueShipName.setText(ObjectUtils.parseString(value.getSecondVessel(), "未知"));
        // 支线航次
        mDataBinding.tvValueLineVoyage.setText(ObjectUtils.parseString(value.getSecondVoyage(), "未知"));
        // 支线中转期
        mDataBinding.tvValueInsuranceTime.setText(ObjectUtils.parseString(value.getSecondEtd(), "未知"));
        // 支线到期
        mDataBinding.tvValueBranchMaturity.setText(ObjectUtils.parseString(value.getSecondEta(), "未知"));
        // 三程船名
        mDataBinding.tvValueThreeShipName.setText(ObjectUtils.parseString(value.getThirdVessel(), "未知"));
        // 三程航次
        mDataBinding.tvValueThreeVoyage.setText(ObjectUtils.parseString(value.getThirdVoyage(), "未知"));
        // 三程开船期
        mDataBinding.tvValueThreeWaySailingPeriod.setText(ObjectUtils.parseString(value.getThirdEtd(), "未知"));
        // 驳船公司
        mDataBinding.tvValueBargeCompany.setText(ObjectUtils.parseString(value.getFeederCompany(), "未知"));
    }

    /**
     * 装货信息
     */
    protected void initLoading(ViewForwardOrder value) {
        // 循环装货信息
        if (value.getFreightShipperList() != null) {
            mDataBinding.flLoadingInformationAdd.setVisibility(View.VISIBLE);
            // 判断核柜的编辑功能
            if (mOrder != null && mOrder.getLogBillsTrack() != null && ObjectUtils.parseInt(mOrder.getLogBillsTrack().getIsComDate()) == 1) {
                mDataBinding.flLoadingInformationAdd.setVisibility(View.GONE);
            }
            for (FreightShipper freightShipper : value.getFreightShipperList()) {
                ViewHolderLoadingAndUnloading viewHolderLoadingAndUnloading = new ViewHolderLoadingAndUnloading(LayoutInflater.from(this.getContext()).inflate(R.layout.item_business_order_loading_and_unloading, mDataBinding.llLoading, false));
                // 判断核柜的编辑功能
                if (mOrder != null && mOrder.getLogBillsTrack() != null && ObjectUtils.parseInt(mOrder.getLogBillsTrack().getIsComDate()) == 1) {
                    viewHolderLoadingAndUnloading.rlLoadingAndUnloadingAction.setVisibility(View.GONE);
                }
                // 单位
                viewHolderLoadingAndUnloading.tvValueUnitName.setText(ObjectUtils.parseString(freightShipper.getConsignerUnit(), "未知"));
                // 地址
                viewHolderLoadingAndUnloading.tvValueAddress.setText(ObjectUtils.parseString(freightShipper.getJhr(), "未知"));
                // 联系人
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingContacts.setText(ObjectUtils.parseString(freightShipper.getCompanyLxr(), "未知"));
                // 手机
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingMobile.setText(ObjectUtils.parseString(freightShipper.getShipperMp(), "未知"));
                // 电话
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingPhone.setText(ObjectUtils.parseString(freightShipper.getShipperTel(), "未知"));
                // 传真
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingFax.setText(ObjectUtils.parseString(freightShipper.getConsignerUnitFax(), "未知"));
                // 说明
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.setText(ObjectUtils.parseString(freightShipper.getRemark3(), "未知"));
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.setTag(ObjectUtils.parseString(freightShipper.getRemark3()));

                // 经纬度
                if (freightShipper.getLocationArea() != null) {
                    if (freightShipper.getLocationArea().getSite() != null) {
                        viewHolderLoadingAndUnloading.tvLat.setText(ObjectUtils.parseString(freightShipper.getLocationArea().getSite().getLat()));
                        viewHolderLoadingAndUnloading.tvLon.setText(ObjectUtils.parseString(freightShipper.getLocationArea().getSite().getLon()));
                    }
                }

                // 多个id
                viewHolderLoadingAndUnloading.tvId.setText(ObjectUtils.parseString(freightShipper.getId()));
                viewHolderLoadingAndUnloading.tvBillsId.setText(ObjectUtils.parseString(freightShipper.getBillsId()));

                initLoadingAndUnLoading(0, viewHolderLoadingAndUnloading, mDataBinding.llLoading);

                mDataBinding.llLoading.addView(viewHolderLoadingAndUnloading.rootView);
            }
        }
    }

    /**
     * 卸货信息
     */
    protected void initUnLoading(ViewForwardOrder value) {
        // 循环装货信息
        if (value.getFreightConsigneeList() != null) {
            mDataBinding.flUnloadingInformationAdd.setVisibility(View.VISIBLE);
            // 判断核柜的编辑功能
            if (mOrder != null && mOrder.getLogBillsTrack() != null && ObjectUtils.parseInt(mOrder.getLogBillsTrack().getIsComDate()) == 1) {
                mDataBinding.flUnloadingInformationAdd.setVisibility(View.GONE);
            }
            for (FreightConsignee freightConsignee : value.getFreightConsigneeList()) {
                ViewHolderLoadingAndUnloading viewHolderLoadingAndUnloading = new ViewHolderLoadingAndUnloading(LayoutInflater.from(this.getContext()).inflate(R.layout.item_business_order_loading_and_unloading, mDataBinding.llLoading, false));
                // 判断核柜的编辑功能
                if (mOrder != null && mOrder.getLogBillsTrack() != null && ObjectUtils.parseInt(mOrder.getLogBillsTrack().getIsComDate()) == 1) {
                    viewHolderLoadingAndUnloading.rlLoadingAndUnloadingAction.setVisibility(View.GONE);
                }
                // 单位
                viewHolderLoadingAndUnloading.tvValueUnitName.setText(ObjectUtils.parseString(freightConsignee.getConsigneeUnit(), "未知"));
                // 地址
                viewHolderLoadingAndUnloading.tvValueAddress.setText(ObjectUtils.parseString(freightConsignee.getConsigneePlace(), "未知"));
                // 联系人
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingContacts.setText(ObjectUtils.parseString(freightConsignee.getConsigneeLinkman(), "未知"));
                // 手机
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingMobile.setText(ObjectUtils.parseString(freightConsignee.getConsigneeMp(), "未知"));
                // 电话
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingPhone.setText(ObjectUtils.parseString(freightConsignee.getConsigneeTel(), "未知"));
                // 传真
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingFax.setText(ObjectUtils.parseString(freightConsignee.getConsigneeUnitFax(), "未知"));
                // 说明
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.setText(ObjectUtils.parseString(freightConsignee.getRemark9(), "未知"));
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.setTag(ObjectUtils.parseString(freightConsignee.getRemark9()));

                // 经纬度
                if (freightConsignee.getLocationArea() != null) {
                    if (freightConsignee.getLocationArea().getSite() != null) {
                        viewHolderLoadingAndUnloading.tvLat.setText(ObjectUtils.parseString(freightConsignee.getLocationArea().getSite().getLat()));
                        viewHolderLoadingAndUnloading.tvLon.setText(ObjectUtils.parseString(freightConsignee.getLocationArea().getSite().getLon()));
                    }
                }

                // 多个id
                viewHolderLoadingAndUnloading.tvId.setText(ObjectUtils.parseString(freightConsignee.getId()));
                viewHolderLoadingAndUnloading.tvBillsId.setText(ObjectUtils.parseString(freightConsignee.getBillsId()));

                initLoadingAndUnLoading(1, viewHolderLoadingAndUnloading, mDataBinding.llUnLoading);

                mDataBinding.llUnLoading.addView(viewHolderLoadingAndUnloading.rootView);
            }
        }

    }

    /**
     * 装货派车信息(货代)
     */
    protected void initDispatching(FreightTruck freightTruck) {
        if (freightTruck != null) {
            mDataBinding.flLoadingDispatchingInformationAdd.setVisibility(View.GONE);
            mDataBinding.rlLoadingDispatchingInformation.setVisibility(View.VISIBLE);
            // 装货优先级
            mDataBinding.tvValueDispatchingPriority.setText(ObjectUtils.parseString(freightTruck.getGoodsNameE(), "未知"));
            // 装货时间
            mDataBinding.tvValueDispatchingLoadingTime.setText(ObjectUtils.parseString(freightTruck.getLoadDate(), "未知"));
            // 拖车公司
            mDataBinding.tvValueDispatchingTowingCo.setText(ObjectUtils.parseString(freightTruck.getPreTruck(), "未知"));
            // 订舱委托人
            mDataBinding.tvValueDispatchingBookingAgent.setText(ObjectUtils.parseString(freightTruck.getPreShipCompany(), "未知"));
        }
    }

    /**
     * 卸货派车信息(货代)
     */
    protected void initUnDispatching(FreightTruck freightTruck) {
        if (freightTruck != null) {
            mDataBinding.flUnLoadingDispatchingInformationAdd.setVisibility(View.GONE);
            mDataBinding.rlLoadingDispatchingUnInformation.setVisibility(View.VISIBLE);

            // 卸货优先级
            mDataBinding.tvValueDispatchingUnPriority.setText(ObjectUtils.parseString(freightTruck.getGoodsPriority(), "未知"));
            // 计划送货时间
            mDataBinding.tvValueDispatchingUnLoadingTime.setText(ObjectUtils.parseString(freightTruck.getReleasePlanDate(), "未知"));
            // 拖车公司
            mDataBinding.tvValueDispatchingUnTowingCo.setText(ObjectUtils.parseString(freightTruck.getDestTruck(), "未知"));
            // 订舱收货人
            mDataBinding.tvValueDispatchingUnBookingAgent.setText(ObjectUtils.parseString(freightTruck.getDesShipCompany(), "未知"));
        }
    }

    /**
     * 货柜信息
     */
    protected void initContainer(List<FreightSono> value) {
        mDataBinding.llContainer.removeAllViews();
        // 编辑的才隐藏
        if (mType == TYPE_MODEL_UPDATE) {
            mDataBinding.flContainerAdd.setVisibility(View.GONE);
        }

        // 合并货柜数据
        HashMap<String, Integer> hashMapSonos = new HashMap<>();
        // 循环装货信息整理数据
        for (FreightSono freightSono : value) {
            hashMapSonos.put(freightSono.getContId(), ObjectUtils.parseInt(hashMapSonos.get(freightSono.getContId())) + 1);
        }
        // 循环初始化view
        for (Map.Entry<String, Integer> entry : hashMapSonos.entrySet()) {
            ViewHolderContainer viewHolderContainer = new ViewHolderContainer(LayoutInflater.from(this.getContext()).inflate(R.layout.item_business_container, mDataBinding.llContainer, false));

            viewHolderContainer.tvValueCabinetType.setText(ObjectUtils.parseString(entry.getKey()));
            viewHolderContainer.tvValueNumber.setText(ObjectUtils.parseString(ObjectUtils.parseString(entry.getValue())));
            mDataBinding.llContainer.addView(viewHolderContainer.rootView);
        }
    }

    /**
     * 其他
     */
    protected void initOther(ViewForwardOrder value) {
        if (mType == TYPE_MODEL_UPDATE && value.getFreightOther() != null) {
            mDataBinding.llMainOther.setVisibility(View.VISIBLE);
            mDataBinding.tvJobNo.setText(ObjectUtils.parseString(value.getFreightBills().getLadingId()));
            mDataBinding.tvMakingTime.setText(ObjectUtils.parseString(value.getFreightOther().getCreated()));
        }
    }

    /**
     * 传递数据给dynamicFragment该窗体
     */
    protected void initLogBillsTrack(ViewForwardOrder value) {
        InitEvent initEvent = new InitEvent(value.getLogBillsTrack());
        initEvent.setBillType("0");
        EventBus.getDefault().post(initEvent);
    }


}
