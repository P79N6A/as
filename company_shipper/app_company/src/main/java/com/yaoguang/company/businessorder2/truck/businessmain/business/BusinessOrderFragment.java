package com.yaoguang.company.businessorder2.truck.businessmain.business;

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
import com.yaoguang.company.businessorder2.truck.businessmain.BusinessMainFragment;
import com.yaoguang.company.businessorder2.truck.container.list.ContainerListFragment;
import com.yaoguang.company.businessorder2.common.businessmain.dynamic.event.InitEvent;
import com.yaoguang.company.businessorder2.truck.dispatchinginformation.DispatchingInformationFragment;
import com.yaoguang.greendao.entity.TruckGoodsTruck;
import com.yaoguang.greendao.entity.TruckSono;
import com.yaoguang.greendao.entity.common.TruckBills;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.LogBillsTrack;
import com.yaoguang.greendao.entity.company.RecordUpdate;
import com.yaoguang.greendao.entity.company.UpdateBusinessOrderModel;
import com.yaoguang.greendao.entity.driver.LocationArea;
import com.yaoguang.greendao.entity.driver.Site;
import com.yaoguang.greendao.entity.driver.TruckGoodsAddr;
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
public class BusinessOrderFragment extends BaseBusinessOrderFragment<TruckBills, RecordUpdate> {

    /**
     * @param type 类型，新增、编辑、拷贝（模板）
     */
    @SuppressLint("ValidFragment")
    public BusinessOrderFragment(int type, String otherService) {
        super(type, otherService);
    }

    @Override
    public void setViewOrder(TruckBills truckBills) {
        super.setViewOrder(truckBills);
    }

    @Override
    protected void cloneData() {
        if (mOrder != null) {
            // 判断除了编辑模式，其他模式的拖车的派车信息不应该拷贝过去
            if (mType != TYPE_MODEL_UPDATE) {
                mOrder.setTruckGoodsTruck(null);
            } else if (mType == TYPE_MODEL_UPDATE) {
                // 如果是编辑模式，则设置装拆箱模式
                if (mOrder.getOtherservice().equals(0)) {
                    mOtherService = getString(R.string.loading_goods);
                } else {
                    mOtherService = getString(R.string.unloading_goods2);
                }
            }

            Gson gson = new Gson();
            String json = gson.toJson(mOrder);
            mOrderOld = gson.fromJson(json, TruckBills.class);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mAppOrder = new RecordUpdate();
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
            if (mOrder != null)
                mOrder.setId(null);
            mOrder.setTruckSonos(new ArrayList<>());
            mOrder.setLogBillsTrack(new LogBillsTrack());
        }

        if (mOrder != null) {
            initData(mOrder);
        } else {
            // 现在实例化一个全新没有数据的，为了后面的添加界面不是null的对象而报错
            mOrder = new TruckBills();
            mOrderOld = new TruckBills();
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void init() {
        super.init();
        mPresenter = new BusinessOrderPresenter(this);

        // 隐藏货代的委托信息
        mDataBinding.llMainEntrustmentForwarder.setVisibility(View.GONE);
        // 显示货代的委托信息
        mDataBinding.llMainEntrustmentTruck.setVisibility(View.VISIBLE);
        // 拖车没有保险信息，隐藏保险信息
        mDataBinding.llMainInsurance.setVisibility(View.GONE);
        // 隐藏货代的船运信息
        mDataBinding.llForwarderMainShipper.setVisibility(View.GONE);
        // 显示拖车的船运信息
        mDataBinding.llTruckMainShipper.setVisibility(View.VISIBLE);
        // 因为拖车只区分是装货还是拆箱，只有一个，所以派车信息隐藏一个
        mDataBinding.llMainUnLoadingDispatching.setVisibility(View.GONE);
        // 派车信息标题修改
        mDataBinding.tvLoadingDispatchingInformationAdd.setText("司机、车牌等");
        // 判断是装货还是拆箱
        mDataBinding.tvLayDaysTitleTruck.setText(mOtherService + "时间");
        if (mOtherService.equals(getString(R.string.loading_goods))) {
            // 装箱
            mDataBinding.tvEntrustmentState.setText(mOtherService);
            // 隐藏拆箱地址
            mDataBinding.llMainUnLoadingAddress.setVisibility(View.GONE);
            // 必填字段:起运地起运港
            mDataBinding.imgXDepartureTruck.setVisibility(View.VISIBLE);
            mDataBinding.imgXPortOfDepartureTruck.setVisibility(View.VISIBLE);
            mDataBinding.imgXPortOfDestinationTruck.setVisibility(View.GONE);
            mDataBinding.imgXDestinationTruck.setVisibility(View.GONE);
        } else if (mOtherService.equals(getString(R.string.unloading_goods2))) {
            // 拆箱
            mDataBinding.tvEntrustmentState.setText(mOtherService);
            // 隐藏装箱地址
            mDataBinding.llMainLoadingAddress.setVisibility(View.GONE);
            // 必填字段:起运港目的港
            mDataBinding.imgXDepartureTruck.setVisibility(View.GONE);
            mDataBinding.imgXPortOfDepartureTruck.setVisibility(View.GONE);
            mDataBinding.imgXPortOfDestinationTruck.setVisibility(View.VISIBLE);
            mDataBinding.imgXDestinationTruck.setVisibility(View.VISIBLE);
        }

        // 业务日期，除了更新，业务日期都是当天
        if (mType != TYPE_MODEL_UPDATE) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date date = new Date(System.currentTimeMillis());
            mDataBinding.tvValueBusinessDateTruck.setText(simpleDateFormat.format(date));
        }
    }

    @Override
    public void initListener() {
        super.initListener();

        // 装货信息
        mDataBinding.flLoadingInformationAdd.setOnClickListener(v -> {
            if (TextUtils.isEmpty(ObjectUtils.parseString(mDataBinding.tvValueShipperTruck.getTag()))) {
                ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请先选择托运人");
                return;
            }
            if (TextUtils.isEmpty(ObjectUtils.parseString(mDataBinding.tvValueDepartureTruck.getText().toString()))) {
                ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请先选择起运地");
                return;
            }
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(LoadingAndUnloadingListFragment.newInstance(0, ObjectUtils.parseString(mDataBinding.tvValueShipperTruck.getTag()), mDataBinding.tvValueDepartureTruck.getText().toString(), mDataBinding.etConsigneeIdonsigneeIdTruck.getText().toString()), TYPE_LOADINGINFORMATION);
        });

        // 卸货信息
        mDataBinding.flUnloadingInformationAdd.setOnClickListener(v -> {
            if (TextUtils.isEmpty(ObjectUtils.parseString(mDataBinding.tvValueShipperTruck.getTag()))) {
                ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请先选择托运人");
                return;
            }
            if (TextUtils.isEmpty(ObjectUtils.parseString(mDataBinding.tvValueDestinationTruck.getText().toString()))) {
                ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请先选择目的地");
                return;
            }
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(LoadingAndUnloadingListFragment.newInstance(1, ObjectUtils.parseString(mDataBinding.tvValueShipperTruck.getTag()), mDataBinding.tvValueDestinationTruck.getText().toString(), mDataBinding.etConsigneeIdonsigneeIdTruck.getText().toString()), TYPE_UNLOADINGINFORMATION);
        });

        // 装货派车信息
        mDataBinding.flLoadingDispatchingInformationAdd.setOnClickListener(v -> ((BaseBusinessMainFragment) getParentFragment()).startForResult(DispatchingInformationFragment.newInstance(mOrder.getTruckGoodsTruck()), TYPE_DISPATCHING_LOADING));
        // 装货派车编辑
        mDataBinding.tvLoadingDispatchingTrailerEdit.setOnClickListener(v -> ((BaseBusinessMainFragment) getParentFragment()).startForResult(DispatchingInformationFragment.newInstance(mOrder.getTruckGoodsTruck()), TYPE_DISPATCHING_LOADING));

        // 货柜（编辑版）
        mDataBinding.llClickContainer.setOnClickListener(v -> {
            // 判断是否核柜
            ArrayList<TruckSono> freightSonos = new ArrayList<>();
            freightSonos.addAll(mOrder.getTruckSonos());
            if (mOrder != null && mOrder.getLogBillsTrack() != null && ObjectUtils.parseInt(mOrder.getLogBillsTrack().getIsComDate()) == 0) {
                ((BaseBusinessMainFragment) getParentFragment()).startForResult(ContainerListFragment.newInstance(freightSonos, 0), TYPE_SONO);
            } else {
                ((BaseBusinessMainFragment) getParentFragment()).startForResult(ContainerListFragment.newInstance(freightSonos, 1), TYPE_SONO);
            }
        });

        // region 委托信息

        // 托运人
        mDataBinding.rlShipperTruck.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER111);
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER111 + 1000));
        });

        // 起运地
        mDataBinding.rlDepartureTruck.setOnClickListener(v -> {
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
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DEPARTURE222) + 1000);
        });

        // 起运港
        mDataBinding.rlPortOfDepartureTruck.setOnClickListener(v -> {
            // 判断物流端或者货主端
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT55);
            } else {
                if (TextUtils.isEmpty(mDataBinding.tvValueShipper.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.tvValueShipper.getHint().toString());
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT55, mDataBinding.tvValueShipper.getTag().toString());
            }
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT55) + 1000);
        });

        // 目的港
        mDataBinding.rlPortOfDestinationTruck.setOnClickListener(v -> {
            // 判断物流端或者货主端
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION66);
            } else {
                if (TextUtils.isEmpty(mDataBinding.tvValueShipper.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请选择托运人");
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION66, mDataBinding.tvValueShipper.getTag().toString());
            }
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION66) + 1000);
        });

        // 目的地
        mDataBinding.rlDestinationTruck.setOnClickListener(v -> {
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
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DESTINATION333) + 1000);
        });

        // 货物名称
        mDataBinding.rlGoodsNameTruck.setOnClickListener(v -> {
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
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_GOODSNAME) + 1000);
        });

        // 卸货时间
        mDataBinding.rlLayDaysTruck.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "tvValueLayDaysTruck");
            Bundle args = new Bundle();
            args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
            dateBeginPickerFragment.setArguments(args);
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("tvValueLayDaysTruck" + TimePickerFragment.TAGTIME)) {
                    mDataBinding.tvValueLayDaysTruck.setText(data);
                }
            });
        });

        // 业务日期
        mDataBinding.rlBusinessDateTruck.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "rlBusinessDateTruck");
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("rlBusinessDateTruck")) {
                    mDataBinding.tvValueBusinessDateTruck.setText(data);
                }
            });
        });

        // 收款方式属于sheet
        mDataBinding.rlPaymentMethodTruck.setOnClickListener(v -> showSweetSheets(mDataBinding.rlPaymentMethodTruck.getId()));

        // 业务员
        mDataBinding.rlSalesmanTruck.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_EMPLOYEES);
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_EMPLOYEES + 1000));
        });

        // 免滞日期
        mDataBinding.rlFreeDateTruck.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "rlFreeDateTruck");
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("rlFreeDateTruck")) {
                    mDataBinding.tvValueFreeDateTruck.setText(data);
                }
            });
        });
        // 免堆日期
        mDataBinding.rlFreeHeapDateTruck.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "rlFreeHeapDateTruck");
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("rlFreeHeapDateTruck")) {
                    mDataBinding.tvValueFreeHeapDateTruck.setText(data);
                }
            });
        });

        // endregion 委托信息

        // region 船运信息

        // 船公司（拖车）
        mDataBinding.rlShipCompanyTruck.setOnClickListener(v -> {
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
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY + 1000));
        });

        // 预开船期（拖车）
        mDataBinding.rlPreopeningDateTruck.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "PreopeningDateTruck");
            Bundle args = new Bundle();
            args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
            dateBeginPickerFragment.setArguments(args);
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("PreopeningDateTruck" + TimePickerFragment.TAGTIME)) {
                    mDataBinding.tvValuePreopeningDateTruck.setText(data);
                }
            });
        });

        // 预抵船期（拖车）
        mDataBinding.rlArrivalTimeDateTruck.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "ArrivalTimeDateTruck");
            Bundle args = new Bundle();
            args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
            dateBeginPickerFragment.setArguments(args);
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("ArrivalTimeDateTruck" + TimePickerFragment.TAGTIME)) {
                    mDataBinding.tvValueArrivalTimeTruck.setText(data);
                }
            });
        });

        // 干线船名(拖车)
        mDataBinding.rlTrunkShipNameTruck.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_USER_INFO_SHIP);
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_USER_INFO_SHIP + 1000));
        });

        // endregion 船运信息

        // 添加
        mDataBinding.btnComit.setOnClickListener(v -> {
            // 验证为空
            if (TextUtils.isEmpty(mDataBinding.tvValueShipperTruck.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择托运人", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mOtherService.equals(getString(R.string.loading_goods))) {
                if (TextUtils.isEmpty(mDataBinding.tvValueDepartureTruck.getText().toString())) {
                    Toast.makeText(BaseApplication.getInstance(), "请选择起运地", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mDataBinding.tvValuePortOfDepartureTruck.getText().toString())) {
                    Toast.makeText(BaseApplication.getInstance(), "请选择起运港", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else if (mOtherService.equals(getString(R.string.unloading_goods2))) {
                if (TextUtils.isEmpty(mDataBinding.tvValueDestinationTruck.getText().toString())) {
                    Toast.makeText(BaseApplication.getInstance(), "请选择目的地", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mDataBinding.tvValuePortOfDestinationTruck.getText().toString())) {
                    Toast.makeText(BaseApplication.getInstance(), "请选择目的港", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (TextUtils.isEmpty(mDataBinding.tvValueGoodsNameTruck.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择货名", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.tvValueLayDaysTruck.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择" + mDataBinding.tvLayDaysTitleTruck.getText().toString(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.tvValueBusinessDateTruck.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择业务日期", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.tvValueSalesmanTruck.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "请选择业务员", Toast.LENGTH_SHORT).show();
                return;
            }
            // 全部赋值
            loadEntrusted();
            loadShipping();
            if (mOtherService.equals(getString(R.string.loading_goods))) {
                loadLoading();
            } else {
                loadUnLoading();
            }
            loadDispatching();
            loadCont();
            if (mType == TYPE_MODEL_ADD || mType == TYPE_MODEL_COPY_OR_TEMP) {
                // 赋值装拆箱
                if (mOtherService.equals(getString(R.string.loading_goods))) {
                    mOrder.setOtherservice(0);
                } else if (mOtherService.equals(getString(R.string.unloading_goods2))) {
                    mOrder.setOtherservice(1);
                }
                mPresenter.addOrder(mOrder, true);

            } else if (mType == TYPE_MODEL_UPDATE) {
                mAppOrder.setNewRecord(mOrder);
                mAppOrder.setOldRecord(mOrderOld);
                mPresenter.updateOrder(mAppOrder, true);
            }
        });
    }

    @Override
    public void onBusinessFragmentResultEvent(BusinessFragmentResultEvent businessFragmentResultEvent) {
        super.onBusinessFragmentResultEvent(businessFragmentResultEvent);
        switch (businessFragmentResultEvent.getType()) {
            case TYPE_DISPATCHING_LOADING:
                // 派车信息
                mOrder.setTruckGoodsTruck(businessFragmentResultEvent.getData().getParcelable(DispatchingInformationFragment.TRUCK_GOODS_TRUCK));
                initLoadingOrUnLoadingDispatching(businessFragmentResultEvent.getData().getParcelable(DispatchingInformationFragment.TRUCK_GOODS_TRUCK));
                break;
            case TYPE_SONO:
                // 货柜
                List<TruckSono> truckSono = businessFragmentResultEvent.getData().getParcelableArrayList("truckSono");
                mOrder.setTruckSonos(truckSono);
                initContainer(truckSono);
                break;
        }
    }

    @Override
    public void searchPaymentMethod(List<String> options1Items) {
        // sheet收款方式
        initSweetSheets(mDataBinding.rlPaymentMethodTruck.getId(), mDataBinding.flMain, "收款方式", options1Items, (position, menuEntity) -> {
            mDataBinding.tvValuePaymentMethodTruck.setText(menuEntity.title.toString());
            return true;
        });
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
                            mAppOrder.setNewRecord(mOrder);
                            mAppOrder.setOldRecord(mOrderOld);
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
                            ((BaseBusinessMainFragment) getParentFragment()).start(BusinessMainFragment.newInstance(result.getId(), "1", mOtherService));
                        else
                            ((BaseBusinessMainFragment) getParentFragment()).start(BusinessMainFragment.newInstance(mOrder.getId(), "1", mOtherService));

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
        Iterator<TruckGoodsAddr> iterator = mOrder.getTruckGoodsAddrs().iterator();
        while (iterator.hasNext()) {
            TruckGoodsAddr truckGoodsAddr = iterator.next();
            if (truckGoodsAddr.getId().equals(id) && truckGoodsAddr.getBillsId().equals(billsId)) {
                iterator.remove();
                return;
            }
        }
    }

    @Override
    public void deleteUnLoadAddressData(String id, String billsId) {
        // 循环卸货数据，删除
        Iterator<TruckGoodsAddr> iterator = mOrder.getTruckGoodsAddrs().iterator();
        while (iterator.hasNext()) {
            TruckGoodsAddr truckGoodsAddr = iterator.next();
            if (truckGoodsAddr.getId().equals(id) && truckGoodsAddr.getBillsId().equals(billsId)) {
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
    protected void initData(TruckBills value) {
        mDataBinding.flMain.setFocusable(true);

        initEntrusted(value);
        initShipping(value);
        // 判断是装货还是卸货
        if (value.getOtherservice() == 0) {
            mDataBinding.llMainLoadingAddress.setVisibility(View.VISIBLE);
            mDataBinding.llMainUnLoadingAddress.setVisibility(View.GONE);
            initLoading(value);
        } else {
            mDataBinding.llMainLoadingAddress.setVisibility(View.GONE);
            mDataBinding.llMainUnLoadingAddress.setVisibility(View.VISIBLE);
            initUnLoading(value);
        }
        initLoadingOrUnLoadingDispatching(value.getTruckGoodsTruck());
        initContainer(value.getTruckSonos());
        initOther(value);
        initLogBillsTrack(value);
    }

    /**
     * 装载委托信息
     */
    private void loadEntrusted() {
        // 托运人 )委托信息
        mOrder.setShipper(mDataBinding.tvValueShipperTruck.getText().toString());
        mOrder.setShipperId(ObjectUtils.parseString(mDataBinding.tvValueShipperTruck.getTag()));
        // 起运地
        mOrder.setDockOfLoading(mDataBinding.tvValueDepartureTruck.getText().toString());
        // 起运港
        mOrder.setPortLoading(mDataBinding.tvValuePortOfDepartureTruck.getText().toString());
        // 目的港
        mOrder.setPortDelivery(mDataBinding.tvValuePortOfDestinationTruck.getText().toString());
        // 目的地
        mOrder.setFinalDestination(mDataBinding.tvValueDestinationTruck.getText().toString());
        // 货物名称
        mOrder.setGoodsName(mDataBinding.tvValueGoodsNameTruck.getText().toString());
        // 装货时间
        mOrder.setLoadDate(mDataBinding.tvValueLayDaysTruck.getText().toString());
        // 业务日期
        mOrder.setModifyDate(mDataBinding.tvValueBusinessDateTruck.getText().toString());
        // 货主
        mOrder.setConsigneeId(mDataBinding.etConsigneeIdonsigneeIdTruck.getText().toString());
        // 收款方式
        mOrder.setPrepaidCollect(mDataBinding.tvValuePaymentMethodTruck.getText().toString());
        // 业务员
        mOrder.setEmployeeId(mDataBinding.tvValueSalesmanTruck.getText().toString());
        // 免滞日期
        mOrder.setFreecontdate(mDataBinding.tvValueFreeDateTruck.getText().toString());
        // 免堆日期
        mOrder.setFreeStorage(mDataBinding.tvValueFreeHeapDateTruck.getText().toString());
    }

    /**
     * 装载船运信息
     */
    private void loadShipping() {
        // 船公司
        mOrder.setShipCompany(mDataBinding.tvValueShipCompanyTruck.getText().toString());
        // 干线船名
        mOrder.setVessel(mDataBinding.tvValueTrunkShipNameTruck.getText().toString());
        // 干线航次
        mOrder.setVoyage(mDataBinding.etValueMainLineVoyageTruck.getText().toString());
        // 预开船期
        mOrder.setInVesselPlanTime(mDataBinding.tvValuePreopeningDateTruck.getText().toString());
        // 预抵船期
        mOrder.setEtaPlan(mDataBinding.tvValueArrivalTimeTruck.getText().toString());
        // 运单号
        mOrder.setmBlNo(mDataBinding.etWaybillNumberTruck.getText().toString());
        // 委托单号
        mOrder.setConsolCode(mDataBinding.etEntrustmentSingleNumberTruck.getText().toString());
        // 提货单号
        mOrder.setThId(mDataBinding.etDeliverySingleNumberTruck.getText().toString());
    }

    /**
     * 装载装货信息
     */
    private void loadLoading() {
        List<TruckGoodsAddr> truckGoodsAddrList = new ArrayList<>();
        for (int i = 0; i < mDataBinding.llLoading.getChildCount(); i++) {
            ViewHolderLoadingAndUnloading viewHolderLoadingAndUnloading = new ViewHolderLoadingAndUnloading(mDataBinding.llLoading.getChildAt(i));
            TruckGoodsAddr truckGoodsAddr = new TruckGoodsAddr();
            // 单位
            truckGoodsAddr.setGoodsUnit(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueUnitName.getText().toString(), "未知"));
            // 地址
            truckGoodsAddr.setAddress(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueAddress.getText().toString(), "未知"));
            // 联系人
            truckGoodsAddr.setContacts(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingContacts.getText().toString(), "未知"));
            // 手机
            truckGoodsAddr.setMobile(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingMobile.getText().toString(), "未知"));
            // 电话
            truckGoodsAddr.setTel(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingPhone.getText().toString(), "未知"));
            // 传真
            truckGoodsAddr.setUnitFax(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingFax.getText().toString(), "未知"));
            // 说明
            truckGoodsAddr.setRemarks(ObjectUtils.parseString(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.getTag()));
            // 经纬度
            Site site = new Site();
            site.setLon(ObjectUtils.parseDouble(viewHolderLoadingAndUnloading.tvLon.getText().toString()));
            site.setLat(ObjectUtils.parseDouble(viewHolderLoadingAndUnloading.tvLat.getText().toString()));
            LocationArea locationArea = new LocationArea();
            locationArea.setSite(site);
            truckGoodsAddr.setLocationArea(locationArea);

            truckGoodsAddr.setId(viewHolderLoadingAndUnloading.tvId.getText().toString());
            truckGoodsAddr.setBillsId(viewHolderLoadingAndUnloading.tvBillsId.getText().toString());

            truckGoodsAddrList.add(truckGoodsAddr);
        }
        mOrder.setTruckGoodsAddrs(truckGoodsAddrList);
    }

    /**
     * 装载卸货信息
     */
    private void loadUnLoading() {
        List<TruckGoodsAddr> freightConsigneeList = new ArrayList<>();
        for (int i = 0; i < mDataBinding.llUnLoading.getChildCount(); i++) {
            ViewHolderLoadingAndUnloading viewHolderLoadingAndUnloading = new ViewHolderLoadingAndUnloading(mDataBinding.llUnLoading.getChildAt(i));
            TruckGoodsAddr truckGoodsAddr = new TruckGoodsAddr();
            // 单位
            truckGoodsAddr.setGoodsUnit(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueUnitName.getText().toString(), "未知"));
            // 地址
            truckGoodsAddr.setAddress(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueAddress.getText().toString(), "未知"));
            // 联系人
            truckGoodsAddr.setContacts(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingContacts.getText().toString(), "未知"));
            // 手机
            truckGoodsAddr.setMobile(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingMobile.getText().toString(), "未知"));
            // 电话
            truckGoodsAddr.setTel(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingPhone.getText().toString(), "未知"));
            // 传真
            truckGoodsAddr.setUnitFax(ObjectUtils.EmptyIf(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingFax.getText().toString(), "未知"));
            // 说明
            truckGoodsAddr.setRemarks(ObjectUtils.parseString(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.getTag()));
            // 经纬度
            Site site = new Site();
            site.setLon(ObjectUtils.parseDouble(viewHolderLoadingAndUnloading.tvLon.getText().toString()));
            site.setLat(ObjectUtils.parseDouble(viewHolderLoadingAndUnloading.tvLat.getText().toString()));
            LocationArea locationArea = new LocationArea();
            locationArea.setSite(site);
            truckGoodsAddr.setLocationArea(locationArea);

            truckGoodsAddr.setId(viewHolderLoadingAndUnloading.tvId.getText().toString());
            truckGoodsAddr.setBillsId(viewHolderLoadingAndUnloading.tvBillsId.getText().toString());

            freightConsigneeList.add(truckGoodsAddr);
        }
        mOrder.setTruckGoodsAddrs(freightConsigneeList);
    }

    /**
     * 装载装货派车信息(或者是拖车，共用一个)
     */
    private void loadDispatching() {
        if (mOrder.getTruckGoodsTruck() == null) {
            mOrder.setTruckGoodsTruck(new TruckGoodsTruck());
        }
        // 车牌号
        mOrder.getTruckGoodsTruck().setPreCarriage(ObjectUtils.EmptyIf(mDataBinding.tvValueDispatchingLicensePlateNumber.getText().toString(), "未知"));
        // 挂车车牌号
        mOrder.getTruckGoodsTruck().setTruckNo(ObjectUtils.EmptyIf(mDataBinding.tvValueDispatchingTrailerLicensePlateNumber.getText().toString(), "未知"));
        // 司机
        mOrder.getTruckGoodsTruck().setDriverId(ObjectUtils.EmptyIf(mDataBinding.tvValueDispatchingTrailerDriver.getText().toString(), "未知"));
        // 手机
        mOrder.getTruckGoodsTruck().setDriverTel(ObjectUtils.EmptyIf(mDataBinding.tvValueDispatchingTrailerMobile.getText().toString(), "未知"));
        // 身份证号
        mOrder.getTruckGoodsTruck().setDriverOther(ObjectUtils.EmptyIf(mDataBinding.tvValueDispatchingTrailerIDNumber.getText().toString(), "未知"));
        // 拖车公司
        mOrder.getTruckGoodsTruck().setPreTruck(ObjectUtils.EmptyIf(mDataBinding.tvValueDispatchingTrailerTowingCo.getText().toString(), "未知"));
    }


    /**
     * 装载货柜数据
     */
    private void loadCont() {
        if (mType == TYPE_MODEL_ADD || mType == TYPE_MODEL_COPY_OR_TEMP) {
            // 如果是添加的，就需要从控件上获取
            List<TruckSono> freightSonos = new ArrayList<>();
            for (int i = 0; i < mDataBinding.llContainer.getChildCount(); i++) {
                ViewHolderContainerCount viewHolderContainerCount = new ViewHolderContainerCount(mDataBinding.llContainer.getChildAt(i));
                for (int k = 1; k <= ObjectUtils.parseDouble(viewHolderContainerCount.tvValue.getText().toString()); k++) {
                    TruckSono freightSono = new TruckSono();
                    freightSono.setContId(viewHolderContainerCount.tvTitle.getText().toString());
                    freightSonos.add(freightSono);
                }
            }
            mOrder.setTruckSonos(freightSonos);
        }
        // 如果是更新的，不需要从控件上获取

    }

    /**
     * 委托信息
     */
    @SuppressLint("SetTextI18n")
    protected void initEntrusted(TruckBills value) {
        // 托运人 )委托信息
        mDataBinding.tvValueShipperTruck.setText(ObjectUtils.parseString(value.getShipper(), ""));
        mDataBinding.tvValueShipperTruck.setTag(value.getShipperId());
        // 起运地
        mDataBinding.tvValueDepartureTruck.setText(ObjectUtils.parseString(value.getDockOfLoading(), ""));
        // 起运港
        mDataBinding.tvValuePortOfDepartureTruck.setText(ObjectUtils.parseString(value.getPortLoading(), ""));
        // 目的港
        mDataBinding.tvValuePortOfDestinationTruck.setText(ObjectUtils.parseString(value.getPortDelivery(), ""));
        // 目的地
        mDataBinding.tvValueDestinationTruck.setText(ObjectUtils.parseString(value.getFinalDestination(), ""));
        // 货物名称
        mDataBinding.tvValueGoodsNameTruck.setText(ObjectUtils.parseString(value.getGoodsName(), ""));
        // 装货时间
        if (value.getOtherservice() == 0) {
            mDataBinding.tvLayDaysTitleTruck.setText(getString(R.string.loading_goods) + "时间");
        } else {
            mDataBinding.tvLayDaysTitleTruck.setText(getString(R.string.unloading_goods2) + "时间");
        }
        mDataBinding.tvValueLayDaysTruck.setText(ObjectUtils.parseString(value.getLoadDate(), ""));
        // 业务日期，除了更新，业务日期都是当天
        if (mType == TYPE_MODEL_UPDATE) {
            mDataBinding.tvValueBusinessDateTruck.setText(ObjectUtils.parseString(value.getModifyDate(), ""));
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date date = new Date(System.currentTimeMillis());
            mDataBinding.tvValueBusinessDateTruck.setText(simpleDateFormat.format(date));
        }
        // 货主
        mDataBinding.etConsigneeIdonsigneeIdTruck.setText(ObjectUtils.parseString(value.getConsigneeId(), ""));
        // 收款方式
        mDataBinding.tvValuePaymentMethodTruck.setText(ObjectUtils.parseString(value.getPrepaidCollect(), ""));
        // 业务员
        mDataBinding.tvValueSalesmanTruck.setText(ObjectUtils.parseString(value.getEmployeeId(), ""));
        // 免滞日期
        mDataBinding.tvValueFreeDateTruck.setText(ObjectUtils.parseString(value.getFreecontdate(), ""));
        // 免堆日期
        mDataBinding.tvValueFreeHeapDateTruck.setText(ObjectUtils.parseString(value.getFreeStorage(), ""));
    }

    /**
     * 船运信息
     */
    protected void initShipping(TruckBills value) {
        // 船公司
        mDataBinding.tvValueShipCompanyTruck.setText(ObjectUtils.parseString(value.getShipCompany(), ""));
        // 干线船名
        mDataBinding.tvValueTrunkShipNameTruck.setText(ObjectUtils.parseString(value.getVessel(), ""));
        // 干线航次
        mDataBinding.etValueMainLineVoyageTruck.setText(ObjectUtils.parseString(value.getVoyage(), ""));
        // 预开船期
        mDataBinding.tvValuePreopeningDateTruck.setText(ObjectUtils.parseString(value.getInVesselPlanTime(), ""));
        // 预抵船期
        mDataBinding.tvValueArrivalTimeTruck.setText(ObjectUtils.parseString(value.getEtaPlan(), ""));
        // 运单号
        mDataBinding.etWaybillNumberTruck.setText(ObjectUtils.parseString(value.getmBlNo(), ""));
        // 委托单号
        mDataBinding.etEntrustmentSingleNumberTruck.setText(ObjectUtils.parseString(value.getConsolCode(), ""));
        // 提货单号
        mDataBinding.etDeliverySingleNumberTruck.setText(ObjectUtils.parseString(value.getThId(), ""));
    }

    /**
     * 装货信息
     */
    protected void initLoading(TruckBills value) {
        // 循环装货信息
        if (value.getTruckGoodsAddrs() != null) {
            mDataBinding.flLoadingInformationAdd.setVisibility(View.VISIBLE);
            // 判断核柜的编辑功能
            if (mOrder != null && mOrder.getLogBillsTrack() != null && ObjectUtils.parseInt(mOrder.getLogBillsTrack().getIsComDate()) == 1) {
                mDataBinding.flLoadingInformationAdd.setVisibility(View.GONE);
            }

            // 循环装货信息
            for (TruckGoodsAddr freightShipper : value.getTruckGoodsAddrs()) {
                ViewHolderLoadingAndUnloading viewHolderLoadingAndUnloading = new ViewHolderLoadingAndUnloading(LayoutInflater.from(this.getContext()).inflate(R.layout.item_business_order_loading_and_unloading, mDataBinding.llLoading, false));
                // 判断核柜的编辑功能
                if (mOrder != null && mOrder.getLogBillsTrack() != null && ObjectUtils.parseInt(mOrder.getLogBillsTrack().getIsComDate()) == 1) {
                    viewHolderLoadingAndUnloading.rlLoadingAndUnloadingAction.setVisibility(View.GONE);
                }

                // 单位
                viewHolderLoadingAndUnloading.tvValueUnitName.setText(ObjectUtils.parseString(freightShipper.getGoodsUnit(), "未知"));
                // 地址
                viewHolderLoadingAndUnloading.tvValueAddress.setText(ObjectUtils.parseString(freightShipper.getAddress(), "未知"));
                // 联系人
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingContacts.setText(ObjectUtils.parseString(freightShipper.getContacts(), "未知"));
                // 手机
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingMobile.setText(ObjectUtils.parseString(freightShipper.getMobile(), "未知"));
                // 电话
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingPhone.setText(ObjectUtils.parseString(freightShipper.getTel(), "未知"));
                // 传真
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingFax.setText(ObjectUtils.parseString(freightShipper.getUnitFax(), "未知"));
                // 说明
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.setText(ObjectUtils.parseString(freightShipper.getRemarks(), "未知"));
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.setTag(ObjectUtils.parseString(freightShipper.getRemarks()));
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
    protected void initUnLoading(TruckBills value) {
        // 循环卸货信息
        if (value.getTruckGoodsAddrs() != null) {
            mDataBinding.flUnloadingInformationAdd.setVisibility(View.VISIBLE);
            // 判断核柜的编辑功能
            if (mOrder != null && mOrder.getLogBillsTrack() != null && ObjectUtils.parseInt(mOrder.getLogBillsTrack().getIsComDate()) == 1) {
                mDataBinding.flUnloadingInformationAdd.setVisibility(View.GONE);
            }

            // 循环装货信息
            for (TruckGoodsAddr freightConsignee : value.getTruckGoodsAddrs()) {
                ViewHolderLoadingAndUnloading viewHolderLoadingAndUnloading = new ViewHolderLoadingAndUnloading(LayoutInflater.from(this.getContext()).inflate(R.layout.item_business_order_loading_and_unloading, mDataBinding.llLoading, false));
                // 判断核柜的编辑功能
                if (mOrder != null && mOrder.getLogBillsTrack() != null && ObjectUtils.parseInt(mOrder.getLogBillsTrack().getIsComDate()) == 1) {
                    viewHolderLoadingAndUnloading.rlLoadingAndUnloadingAction.setVisibility(View.GONE);
                }
                // 单位
                viewHolderLoadingAndUnloading.tvValueUnitName.setText(ObjectUtils.parseString(freightConsignee.getGoodsUnit(), "未知"));
                // 地址
                viewHolderLoadingAndUnloading.tvValueAddress.setText(ObjectUtils.parseString(freightConsignee.getAddress(), "未知"));
                // 联系人
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingContacts.setText(ObjectUtils.parseString(freightConsignee.getContacts(), "未知"));
                // 手机
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingMobile.setText(ObjectUtils.parseString(freightConsignee.getMobile(), "未知"));
                // 电话
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingPhone.setText(ObjectUtils.parseString(freightConsignee.getTel(), "未知"));
                // 传真
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingFax.setText(ObjectUtils.parseString(freightConsignee.getUnitFax(), "未知"));
                // 说明
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.setText(ObjectUtils.parseString(freightConsignee.getRemarks(), "未知"));
                viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.setTag(ObjectUtils.parseString(freightConsignee.getRemarks()));

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
     * 装货或者卸货的派车信息
     */
    protected void initLoadingOrUnLoadingDispatching(TruckGoodsTruck truckGoodsTruck) {
        if (truckGoodsTruck != null) {
            // 判断核柜的编辑功能
            if (mOrder != null && mOrder.getLogBillsTrack() != null && ObjectUtils.parseInt(mOrder.getLogBillsTrack().getIsComDate()) == 1) {
                mDataBinding.tvLoadingDispatchingTrailerEdit.setVisibility(View.GONE);
            }

            // 隐藏添加，显示拖车的
            mDataBinding.flLoadingDispatchingInformationAdd.setVisibility(View.GONE);
            mDataBinding.rlLoadingDispatchingInformationTruck.setVisibility(View.VISIBLE);

            // 车牌号
            mDataBinding.tvValueDispatchingLicensePlateNumber.setText(ObjectUtils.parseString(truckGoodsTruck.getPreCarriage(), "未知"));
            // 挂车车牌号
            mDataBinding.tvValueDispatchingTrailerLicensePlateNumber.setText(ObjectUtils.parseString(truckGoodsTruck.getTruckNo(), "未知"));
            // 司机
            mDataBinding.tvValueDispatchingTrailerDriver.setText(ObjectUtils.parseString(truckGoodsTruck.getDriverId(), "未知"));
            // 手机
            mDataBinding.tvValueDispatchingTrailerMobile.setText(ObjectUtils.parseString(truckGoodsTruck.getDriverTel(), "未知"));
            // 身份证号
            mDataBinding.tvValueDispatchingTrailerIDNumber.setText(ObjectUtils.parseString(truckGoodsTruck.getDriverOther(), "未知"));
            // 拖车公司
            mDataBinding.tvValueDispatchingTrailerTowingCo.setText(ObjectUtils.parseString(truckGoodsTruck.getPreTruck(), "未知"));
        }
    }

    /**
     * 货柜信息
     */
    protected void initContainer(List<TruckSono> value) {
        mDataBinding.llContainer.removeAllViews();
        // 编辑的才隐藏
        if (mType == TYPE_MODEL_UPDATE) {
            mDataBinding.flContainerAdd.setVisibility(View.GONE);
        }

        // 合并货柜数据
        HashMap<String, Integer> hashMapSonos = new HashMap<>();
        // 循环装货信息整理数据
        for (TruckSono truckSono : value) {
            hashMapSonos.put(truckSono.getContId(), ObjectUtils.parseInt(hashMapSonos.get(truckSono.getContId())) + 1);
        }
        // 循环初始化view
        for (Map.Entry<String, Integer> entry : hashMapSonos.entrySet()) {
            ViewHolderContainer viewHolderContainer = new ViewHolderContainer(LayoutInflater.from(this.getContext()).inflate(R.layout.item_business_container, mDataBinding.llContainer, false));

            viewHolderContainer.tvValueCabinetType.setText(entry.getKey());
            viewHolderContainer.tvValueNumber.setText(ObjectUtils.parseString(entry.getValue()));
            mDataBinding.llContainer.addView(viewHolderContainer.rootView);
        }
    }

    /**
     * 其他
     */
    protected void initOther(TruckBills value) {
        if (mType == TYPE_MODEL_UPDATE) {
            mDataBinding.llMainOther.setVisibility(View.VISIBLE);
            mDataBinding.tvJobNo.setText(ObjectUtils.parseString(value.getOrderSn(), "未知"));
            mDataBinding.tvMakingTime.setText(ObjectUtils.parseString(value.getUpdated(), "未知"));
        }
    }

    /**
     * 传递数据给dynamicFragment该窗体
     */
    protected void initLogBillsTrack(TruckBills value) {
        InitEvent initEvent = new InitEvent(value.getLogBillsTrack());
        initEvent.setBillType("1");
        initEvent.setOtherService(mOtherService.equals(getString(R.string.loading_goods)) ? "0" : "1");
        EventBus.getDefault().post(initEvent);
    }


}
