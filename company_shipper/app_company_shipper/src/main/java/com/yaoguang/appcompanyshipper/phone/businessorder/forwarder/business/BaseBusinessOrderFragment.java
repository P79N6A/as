package com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.business;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.publicsearch.addaddress.AddAddressFragment;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.appcompanyshipper.R;
import com.yaoguang.appcompanyshipper.databinding.FragmentBusinessOrderBinding;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.business.adapter.ContainerAdapter;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.list.event.BusinessOrderListEvent;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.Container;
import com.yaoguang.greendao.entity.company.AppInfoClientPlace;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.lib.appcommon.widget.date.TimePickerFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.lib.entity.ProvinceBeans;
import com.yaoguang.map.common.ToastUtil;
import com.yaoguang.widget.layoutmanager.MyLayoutManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务下单
 * Created by zhongjh on 2017/5/27.
 */
public abstract class BaseBusinessOrderFragment<T, B extends ViewDataBinding>
        extends BaseFragmentDataBind<FragmentBusinessOrderBinding>
        implements BaseBusinessOrderContact.View<T>, Toolbar.OnMenuItemClickListener {

    /**
     * 货主端：添加装货地址返回过来的标签
     */
    protected static final int REQUSET_APPINFOCLIENT_LOAD_ADD = 3333;
    /**
     * 货主端：添加卸货地址返回过来的标签
     */
    protected static final int REQUSET_APPINFOCLIENT_UNLOAD_ADD = 4444;
    /**
     * 应用模版后返回的数据
     */
    protected static final int REQUSET_MODEL_TEMP = 5555;
    /**
     * 从编辑地址返回过来的标签
     */
    protected static final int REQUSET_APPINFOCLIENT = 2;
    private int mType;//装货或者卸货，PublicSearchInteractorImpl.TYPE_LOADPLACES PublicSearchInteractorImpl.TYPE_UNLOADPLACES

    /**
     * 从列表传递过来的对象(用于进行修改或者查看)
     */
    public static final String BUNDLE_ID = "ID";
    public String mID;

    /**
     * 从模版列表传递过来的对象(用于复用模版),也是最终保存的对象
     */
    public static final String BUNDLE_ORDER = "order";
    public T mOrder;

    /**
     * 是否已经导入，1已经导入
     */
    protected String mIsExport = "0";

    protected BaseBusinessOrderContact.Presenter mPresenter;
    ProvinceBeans mProvinceBeans;

    protected ContainerAdapter containerAdapter;

    // 4个下拉框
    SweetSheet sweetSheet;
    SweetSheet sweetSheetOperationClause;
    SweetSheet sweetSheetTransportationClause;
    SweetSheet sweetSheetIsInsurance;

    //这是当前选择的柜型柜量
    TextView tvContainerTemp;
    //这是当前选择的柜型柜量的position
    int positionContainer;

    protected ArrayList<AppInfoClientPlace> mAppInfoClientPlaceLoadings = new ArrayList<>();//装货数据
    protected ArrayList<Integer> mAppInfoClientPlaceLoadingPositions = new ArrayList<>();//装货数据,记录索引id，避免删除后导致索引错乱
    protected ArrayList<AppInfoClientPlace> mAppInfoClientPlaceUnLoadings = new ArrayList<>();//卸货数据
    protected ArrayList<Integer> mAppInfoClientPlaceUnLoadingPositions = new ArrayList<>();//卸货数据,记录索引id，避免删除后导致索引错乱
    //修改地址的索引
    private int mAppInfoClientPlacePosition = -1;

    private DialogHelper mDialogHelperOrder;
    private DialogHelper mDialogHelperEdit;

    /**
     * 保存订单对象，进行下单
     */
    protected abstract void setOrder(String sonos);

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_order;
    }

    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "新建预订单", R.menu.menu_business_order, BaseBusinessOrderFragment.this);

        containerAdapter = new ContainerAdapter();
        RecyclerViewUtils.initPage(mDataBinding.layoutContainer.rvContainer, containerAdapter, null, getContext(), false);

        // 判断如果是物流端，就显示托运人，如果是货主端，就显示物流企业
        if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            // 显示托运人，隐藏物流企业
            mDataBinding.layoutEntrusted.rlShipper.setVisibility(View.VISIBLE);
            mDataBinding.layoutEntrusted.vShipper.setVisibility(View.VISIBLE);
            mDataBinding.layoutEntrusted.rlCompany.setVisibility(View.GONE);
            mDataBinding.layoutEntrusted.vCompany.setVisibility(View.GONE);
        } else {
            // 显示物流企业，隐藏托运人
            mDataBinding.layoutEntrusted.rlCompany.setVisibility(View.VISIBLE);
            mDataBinding.layoutEntrusted.vCompany.setVisibility(View.VISIBLE);
            mDataBinding.layoutEntrusted.rlShipper.setVisibility(View.GONE);
            mDataBinding.layoutEntrusted.vShipper.setVisibility(View.GONE);
        }
    }

    @Override
    public void initListener() {
        //返回事件
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> backPressedSupport());

        mDataBinding.layoutContainer.llAddTitle.setOnClickListener(v -> {
            //添加数据
            mPresenter.addContainer();
        });
        mDataBinding.layoutContainer.llContainerTitle.setOnClickListener(v -> {
            InputMethodUtil.hideKeyboard(getActivity());
            sweetSheet.show();
            positionContainer = -1;
            tvContainerTemp = mDataBinding.layoutContainer.tvCabinetType0;
        });
        //其他 - 时间
        mDataBinding.layoutCostinformation.tvLoadingDate.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "LoadingDate");
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("LoadingDate")) {
                    mDataBinding.layoutCostinformation.tvLoadingDate.setText(data);
                }
            });
        });
        mDataBinding.layoutCostinformation.tvLoadingTime.setOnClickListener(v -> {
            TimePickerFragment timePickerFragment = new TimePickerFragment();
            timePickerFragment.show(getFragmentManager(), "LoadingTime");
            timePickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("LoadingTime")) {
                    mDataBinding.layoutCostinformation.tvLoadingTime.setText(data);
                }
            });
        });
        //托运人
        mDataBinding.layoutEntrusted.rlShipper.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER111);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER111));
        });
        // 物流企业
        mDataBinding.layoutEntrusted.rlCompany.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY));
        });
        //起运地
        mDataBinding.layoutEntrusted.rlDeparture.setOnClickListener(v -> {
            // 判断物流端或者货主端
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DEPARTURE222);
            } else {
                if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueCompany.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueCompany.getHint().toString());
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DEPARTURE222, mDataBinding.layoutEntrusted.tvValueCompany.getTag().toString());
            }
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DEPARTURE222));
        });
        // 目的地
        mDataBinding.layoutEntrusted.rlDestination.setOnClickListener(v -> {
            // 判断物流端或者货主端
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DESTINATION333);
            } else {
                if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueCompany.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueCompany.getHint().toString());
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_DESTINATION333, mDataBinding.layoutEntrusted.tvValueCompany.getTag().toString());
            }
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DESTINATION333));
        });
        // 操作条款
        mDataBinding.layoutEntrusted.rlOperationClause.setOnClickListener(v -> {
            sweetSheetOperationClause.show();
        });
        // 起运港
        mDataBinding.layoutShipping.rlPortOfShipment.setOnClickListener(v -> {
            // 判断物流端或者货主端，TYPE_PORTOFSHIPMENT55跟TYPE_PORTOFSHIPMENT不一样的，返回都一样是TYPE_PORTOFSHIPMENT55
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT55);
            } else {
                if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueCompany.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueCompany.getHint().toString());
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT, mDataBinding.layoutEntrusted.tvValueCompany.getTag().toString());
            }
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT55));
        });
        // 目的港
        mDataBinding.layoutShipping.rlPortOfDestination.setOnClickListener(v -> {
            // 判断物流端或者货主端，TYPE_PORTOFDESTINATION66跟TYPE_PORTOFDESTINATION不一样的，返回都一样是TYPE_PORTOFDESTINATION66
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION66);
            } else {
                if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueCompany.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueCompany.getHint().toString());
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION, mDataBinding.layoutEntrusted.tvValueCompany.getTag().toString());
            }
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION66));
        });
        // 运输条款
        mDataBinding.layoutShipping.rlTransportationClause.setOnClickListener(v -> {
            sweetSheetTransportationClause.show();
        });
        // 货物名称
        mDataBinding.layoutEntrusted.rlGoodsName.setOnClickListener(v -> {
            // 判断物流端或者货主端
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_GOODSNAME);
            } else {
                if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueCompany.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueCompany.getHint().toString());
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_GOODSNAME, mDataBinding.layoutEntrusted.tvValueCompany.getTag().toString());
            }
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_GOODSNAME));
        });

        // 是否保险
        mDataBinding.layoutInsurance.rlIsInsurance.setOnClickListener(v -> sweetSheetIsInsurance.show());
        // 如果保险信息为“是”的话，显示保险相关信息，如果为“否”的话则隐藏
        mDataBinding.layoutInsurance.tvIsInsurance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("是")) {
                    // 显示保险相关信息
                    mDataBinding.layoutInsurance.VLineInsurMoney.setVisibility(android.view.View.VISIBLE);
                    mDataBinding.layoutInsurance.rlInsurMoney.setVisibility(android.view.View.VISIBLE);
                    mDataBinding.layoutInsurance.VLineInsurRate.setVisibility(android.view.View.VISIBLE);
                    mDataBinding.layoutInsurance.rlInsurRate.setVisibility(android.view.View.VISIBLE);
                    mDataBinding.layoutInsurance.VLineInsurValue.setVisibility(android.view.View.VISIBLE);
                    mDataBinding.layoutInsurance.rlInsurValue.setVisibility(android.view.View.VISIBLE);
                } else {
                    // 隐藏
                    mDataBinding.layoutInsurance.VLineInsurMoney.setVisibility(android.view.View.GONE);
                    mDataBinding.layoutInsurance.rlInsurMoney.setVisibility(android.view.View.GONE);
                    mDataBinding.layoutInsurance.VLineInsurRate.setVisibility(android.view.View.GONE);
                    mDataBinding.layoutInsurance.rlInsurRate.setVisibility(android.view.View.GONE);
                    mDataBinding.layoutInsurance.VLineInsurValue.setVisibility(android.view.View.GONE);
                    mDataBinding.layoutInsurance.rlInsurValue.setVisibility(android.view.View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        // 输入货值或者费率的时候，自动计算保险费
        mDataBinding.layoutInsurance.etValueInsurRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double insurRate = ObjectUtils.parseDouble(s.toString());
                double insurValue = ObjectUtils.parseDouble(mDataBinding.layoutInsurance.etValueInsurValue.getText().toString());
                // 相乘
                mDataBinding.layoutInsurance.etValueInsurMoney.setText(ObjectUtils.parseString(insurRate * insurValue));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDataBinding.layoutInsurance.etValueInsurValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double insurRate = ObjectUtils.parseDouble(mDataBinding.layoutInsurance.etValueInsurRate.getText().toString());
                double insurValue = ObjectUtils.parseDouble(s.toString());
                // 相乘
                mDataBinding.layoutInsurance.etValueInsurMoney.setText(ObjectUtils.parseString(insurRate * insurValue));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        containerAdapter.setOnRecyclerViewItemRLClickListener(new ContainerAdapter.OnRecyclerViewItemRLClickListener() {

            @Override
            public void onRlRemoveTitleClick(android.view.View itemView, Object item, int position) {
                //删除本身
                containerAdapter.removeItem(position);
                MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity());
                mDataBinding.layoutContainer.rvContainer.setLayoutManager(myLayoutManager);
            }

            @Override
            public void onRlContainerTitleClick(android.view.View itemView, TextView tvCabinetType0, Object item, int position) {
                tvContainerTemp = mDataBinding.layoutContainer.tvCabinetType0;
                positionContainer = position;
                InputMethodUtil.hideKeyboard(getActivity());
                sweetSheet.show();
            }
        });
        //添加
        mDataBinding.btnComit.setOnClickListener(v -> {
            InputMethodUtil.hideKeyboard(getActivity());
            addCompanyOrder();
        });

//            mDataBinding.layoutLoading.rlLoadingProvinceBean.setOnClickListener(v -> showPickerView());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mID = args.getString(BUNDLE_ID);
            mOrder = args.getParcelable(BUNDLE_ORDER);
        }
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mPresenter.subscribe();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER111:
                    // 托运人
                    mDataBinding.layoutEntrusted.tvValueShipper.setText(data.getString("name"));
                    mDataBinding.layoutEntrusted.tvValueShipper.setTag(data.getString("id"));
                    break;
                case PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY:
                    // 如果新的物流企业跟现在物流企业不一样的话，则其他有关物流企业的，重新选择
                    if (!data.getString("name").equals(mDataBinding.layoutEntrusted.tvValueCompany.getText())) {
                        mDataBinding.layoutEntrusted.tvValueCompany.setText(data.getString("name"));
                        mDataBinding.layoutEntrusted.tvValueCompany.setTag(data.getString("id"));
                        // 清空起运地
                        mDataBinding.layoutEntrusted.tvValueDepartureTitle.setText("");
                        // 清空目的地
                        mDataBinding.layoutEntrusted.tvValueDestination.setText("");
                        // 清空起运港
                        mDataBinding.layoutShipping.tvValuePortShipmentTitle.setText("");
                        // 清空目的港
                        mDataBinding.layoutShipping.tvValuePortDestinationTitle.setText("");
                        // 清空货物名称
                        mDataBinding.layoutEntrusted.tvValueGoodsName.setText("");
                    }
                    break;
                case PublicSearchInteractorImpl.TYPE_DEPARTURE222:
                    mDataBinding.layoutEntrusted.tvValueDepartureTitle.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_DESTINATION333:
                    mDataBinding.layoutEntrusted.tvValueDestination.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_OPERATIONCLAUSE:
                    mDataBinding.layoutEntrusted.tvValueOperationClause.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT55:
                    mDataBinding.layoutShipping.tvValuePortShipmentTitle.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION66:
                    mDataBinding.layoutShipping.tvValuePortDestinationTitle.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_TRANSPORTATIONCLAUSE:
                    mDataBinding.layoutShipping.tvValueTransportationClause.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_GOODSNAME:
                    mDataBinding.layoutEntrusted.tvValueGoodsName.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_LOADPLACES:
                    // 添加装货地址信息后返回
                    ArrayList<AppPublicInfoWrapper> appPublicInfoWrappers = data.getParcelableArrayList("appPublicInfoWrappers");
                    initLoading(appPublicInfoWrappers, PublicSearchInteractorImpl.TYPE_LOADPLACES);
                    break;
                case PublicSearchInteractorImpl.TYPE_UNLOADPLACES:
                    // 添加卸货地址信息后返回
                    ArrayList<AppPublicInfoWrapper> appPublicInfoWrappers2 = data.getParcelableArrayList("appPublicInfoWrappers");
                    initLoading(appPublicInfoWrappers2, PublicSearchInteractorImpl.TYPE_UNLOADPLACES);
                    break;
                case REQUSET_APPINFOCLIENT:
                    // 编辑地址信息后返回
                    AppInfoClientPlace appInfoClientPlace = data.getParcelable("appInfoClientPlace");
                    if (mType == PublicSearchInteractorImpl.TYPE_LOADPLACES) {
                        mAppInfoClientPlaceLoadings.set(mAppInfoClientPlacePosition, appInfoClientPlace);
                        mAppInfoClientPlaceLoadingPositions.set(mAppInfoClientPlacePosition, mAppInfoClientPlacePosition);
                    } else {
                        mAppInfoClientPlaceUnLoadings.set(mAppInfoClientPlacePosition, appInfoClientPlace);
                        mAppInfoClientPlaceUnLoadingPositions.set(mAppInfoClientPlacePosition, mAppInfoClientPlacePosition);
                    }
                    editInfoClientPlaceView(mAppInfoClientPlacePosition, mType);
                    break;
                case REQUSET_APPINFOCLIENT_LOAD_ADD:
                    AppInfoClientPlace appInfoClientPlaceLoadAdd = data.getParcelable("appInfoClientPlace");
                    if (appInfoClientPlaceLoadAdd != null) {
                        ArrayList<AppPublicInfoWrapper> appPublicInfoWrapperAdds = new ArrayList<>();
                        AppPublicInfoWrapper appPublicInfoWrapper = new AppPublicInfoWrapper();
                        appPublicInfoWrapper.setFullName(appInfoClientPlaceLoadAdd.getRegionid());
                        appPublicInfoWrapper.setShortName(appInfoClientPlaceLoadAdd.getAddress());
                        appPublicInfoWrapper.setRemark1(appInfoClientPlaceLoadAdd.getLinkman());
                        appPublicInfoWrapper.setRemark2(appInfoClientPlaceLoadAdd.getLinkmanMp());
                        appPublicInfoWrapper.setRemark3(appInfoClientPlaceLoadAdd.getLinkmanTel());
                        appPublicInfoWrapper.setRemark4(appInfoClientPlaceLoadAdd.getRemark());
                        appPublicInfoWrapperAdds.add(appPublicInfoWrapper);
                        initLoading(appPublicInfoWrapperAdds, PublicSearchInteractorImpl.TYPE_LOADPLACES);
                    }
                    break;
                case REQUSET_APPINFOCLIENT_UNLOAD_ADD:
                    AppInfoClientPlace appInfoClientPlaceUnladAdd = data.getParcelable("appInfoClientPlace");
                    if (appInfoClientPlaceUnladAdd != null) {
                        ArrayList<AppPublicInfoWrapper> appPublicInfoWrapperAdds = new ArrayList<>();
                        AppPublicInfoWrapper appPublicInfoWrapper = new AppPublicInfoWrapper();
                        appPublicInfoWrapper.setFullName(appInfoClientPlaceUnladAdd.getRegionid());
                        appPublicInfoWrapper.setShortName(appInfoClientPlaceUnladAdd.getAddress());
                        appPublicInfoWrapper.setRemark1(appInfoClientPlaceUnladAdd.getLinkman());
                        appPublicInfoWrapper.setRemark2(appInfoClientPlaceUnladAdd.getLinkmanMp());
                        appPublicInfoWrapper.setRemark3(appInfoClientPlaceUnladAdd.getLinkmanTel());
                        appPublicInfoWrapper.setRemark4(appInfoClientPlaceUnladAdd.getRemark());
                        appPublicInfoWrapperAdds.add(appPublicInfoWrapper);
                        initLoading(appPublicInfoWrapperAdds, PublicSearchInteractorImpl.TYPE_UNLOADPLACES);
                    }
                    break;
                case REQUSET_MODEL_TEMP:
                    mOrder = data.getParcelable(BUNDLE_ORDER);
                    // 获取模板信息之前，先删除当前信息
                    mAppInfoClientPlaceLoadings.clear();
                    mAppInfoClientPlaceUnLoadings.clear();
                    mAppInfoClientPlaceLoadingPositions.clear();
                    mAppInfoClientPlaceUnLoadingPositions.clear();
                    this.showDetail(mOrder, true);
                    break;
            }
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        backPressedSupport();
        return true;
    }

    /**
     * 返回之前提示是否需要确认返回，新增和编辑都是不同的提示
     */
    private void backPressedSupport() {
        if (mIsExport.equals("1")) {
            pop();
            return;
        }
        if (mDialogHelperOrder == null)
            mDialogHelperOrder = new DialogHelper(getContext(), "是否放弃下单?", "这会清空已填写的信息", new CommonDialog.Listener() {
                @Override
                public void ok(String msg) {
                    mDialogHelperOrder.hideDialog();
                    pop();
                }

                @Override
                public void cancel() {

                }
            });

        if (mID == null) {
            mDialogHelperOrder.show();
        } else {
            if (mDialogHelperEdit == null)
                mDialogHelperEdit = new DialogHelper(getContext(), "是否放弃修改?", "这将不保存您刚刚修改的信息", new CommonDialog.Listener() {
                    @Override
                    public void ok(String msg) {
                        mDialogHelperEdit.hideDialog();
                        pop();
                    }

                    @Override
                    public void cancel() {

                    }
                });
            mDialogHelperEdit.show();
        }
    }

    @Override
    public void addContainer(Container container) {
        containerAdapter.add(containerAdapter.getList().size(), container);
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity());
        mDataBinding.layoutContainer.rvContainer.setLayoutManager(myLayoutManager);
    }

    @Override
    public void addContainerSuccess(String message) {
        EventBus.getDefault().post(new BusinessOrderListEvent(true));
        pop();
        Toast.makeText(BaseApplication.getInstance().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void addContainerFail(String message) {
        Toast.makeText(BaseApplication.getInstance().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setProvinceBeans(ProvinceBeans value) {
        mProvinceBeans = value;
    }

    @Override
    public void setSonos(List<MenuEntity> valueLevel1) {
        //sheet
        SweetSheet sweetSheet = new SweetSheet(mDataBinding.flMain);
        sweetSheet.setMenuList(valueLevel1);
        sweetSheet.setTitle("柜型");
        RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
        recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(250));
        sweetSheet.setDelegate(recyclerViewDelegate);
        sweetSheet.setBackgroundEffect(new DimEffect(4));
        sweetSheet.setOnMenuItemClickListener((position, menuEntity1) -> {
            tvContainerTemp.setText(menuEntity1.title);
            if (positionContainer != -1)
                containerAdapter.getList().get(positionContainer).setTitle(menuEntity1.title.toString());
            return true;
        });
        this.sweetSheet = sweetSheet;
    }

    @Override
    public void setClause(List<MenuEntity> options1Items) {
        //sheet操作条款
        SweetSheet sweetSheet = new SweetSheet(mDataBinding.flMain);
        sweetSheet.setMenuList(options1Items);
        sweetSheet.setTitle("操作条款");
        RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
        sweetSheet.setDelegate(recyclerViewDelegate);
        sweetSheet.setBackgroundEffect(new DimEffect(4));
        sweetSheet.setOnMenuItemClickListener((position, menuEntity1) -> {
            //去掉括号
            String title = menuEntity1.title.toString().substring(menuEntity1.title.toString().indexOf("(") + 1, menuEntity1.title.toString().lastIndexOf(")"));
            mDataBinding.layoutEntrusted.tvValueOperationClause.setText(title);
            return true;
        });
        sweetSheetOperationClause = sweetSheet;

        //sheet运输条款
        SweetSheet sweetSheet2 = new SweetSheet(mDataBinding.flMain);
        sweetSheet2.setMenuList(options1Items);
        sweetSheet2.setTitle("运输条款");
        RecyclerViewDelegate recyclerViewDelegate2 = new RecyclerViewDelegate(true);
        sweetSheet2.setDelegate(recyclerViewDelegate2);
        sweetSheet2.setBackgroundEffect(new DimEffect(4));
        sweetSheet2.setOnMenuItemClickListener((position, menuEntity1) -> {
            String title = menuEntity1.title.toString().substring(menuEntity1.title.toString().indexOf("(") + 1, menuEntity1.title.toString().lastIndexOf(")"));
            mDataBinding.layoutShipping.tvValueTransportationClause.setText(title);
            return true;
        });
        sweetSheetTransportationClause = sweetSheet2;

        //是否保险
        SweetSheet sweetSheet3 = new SweetSheet(mDataBinding.flMain);
        sweetSheet3.setMenuList(R.menu.sheet_is_insurance);
        sweetSheet3.setTitle("是否保险");
        RecyclerViewDelegate recyclerViewDelegate3 = new RecyclerViewDelegate(true);
        recyclerViewDelegate3.setContentHeight(DisplayMetricsUtils.dip2px(200));
        sweetSheet3.setDelegate(recyclerViewDelegate3);
        sweetSheet3.setBackgroundEffect(new DimEffect(4));
        sweetSheet3.setOnMenuItemClickListener((position, menuEntity1) -> {
            mDataBinding.layoutInsurance.tvIsInsurance.setText(menuEntity1.title);
            return true;
        });
        sweetSheetIsInsurance = sweetSheet3;
    }

    @Override
    public void addCompanyOrder() {
        // 判断物流必须选择托运人，货主必须选择
        if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
            // 托运人
            if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueShipper.getText().toString())) {
                ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueShipper.getHint().toString());
                return;
            }
        } else {
            // 物流企业
            if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueCompany.getText().toString())) {
                ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueCompany.getHint().toString());
                return;
            }
        }

        if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueDepartureTitle.getText().toString())) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueDepartureTitle.getHint().toString());
            return;
        }
        if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueDestination.getText().toString())) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueDestination.getHint().toString());
            return;
        }
        //货物名称
        if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueGoodsName.getText().toString())) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueGoodsName.getHint().toString());
            return;
        }
        if (TextUtils.isEmpty(mDataBinding.layoutShipping.tvValuePortShipmentTitle.getText().toString())) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutShipping.tvValuePortShipmentTitle.getHint().toString());
            return;
        }
        if (TextUtils.isEmpty(mDataBinding.layoutShipping.tvValuePortDestinationTitle.getText().toString())) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutShipping.tvValuePortDestinationTitle.getHint().toString());
            return;
        }
        if (mAppInfoClientPlaceLoadings.size() <= 0) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请选择装货信息");
            return;
        }
        if (mAppInfoClientPlaceUnLoadings.size() <= 0) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请选择卸货信息");
            return;
        }
        //时间
        if (TextUtils.isEmpty(mDataBinding.layoutCostinformation.tvLoadingDate.getText().toString()) || TextUtils.isEmpty(mDataBinding.layoutCostinformation.tvLoadingTime.getText().toString())) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请选择装货时间");
            return;
        }
        //预定费用
        if (TextUtils.isEmpty(mDataBinding.layoutCostinformation.etValueReservationFee.getText().toString())) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutCostinformation.etValueReservationFee.getHint().toString());
            return;
        }

        //获取所有Sonos,如果有重复的，则提示,如果数量为0的，则不算进去
        String sonos = "";
        int count = ObjectUtils.parseInt(mDataBinding.layoutContainer.etContainer.getText().toString());
        if (count > 0)
            sonos = count + "*" + mDataBinding.layoutContainer.tvCabinetType0.getText().toString();
        //判断有没有数据
        if (containerAdapter.getList().size() > 0) {
            if (!sonos.equals(""))
                sonos = sonos + ",";
            //循环
            for (Container container : containerAdapter.getList()) {
                if (sonos.contains(container.getTitle())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "不能提交多组相同的柜型，请更正");
                    return;
                }
                if (container.getCount() > 0)
                    sonos += container.getCount() + "*" + container.getTitle() + ",";
            }
        }

        setOrder(sonos);

        mPresenter.addCompanyOrder(mOrder);
    }

    @Override
    public void initLoading(ArrayList<AppPublicInfoWrapper> appPublicInfoWrappers, int type) {
        if (type == PublicSearchInteractorImpl.TYPE_LOADPLACES) {
            //判断目前的数据，如果有一样的，就不添加。如果有不一样的，就添加。
            mAppInfoClientPlaceLoadings = addAppInfoClientPlace(mAppInfoClientPlaceLoadings, appPublicInfoWrappers, type);

            mDataBinding.layoutLoading.llLoading.removeAllViews();

            //显示多个地址
            for (int i = 0; i < mAppInfoClientPlaceLoadings.size(); i++) {
                addInfoClientPlaceView(i, type);
            }
            mAppInfoClientPlaceLoadingPositions.clear();
            for (int i = 0; i < mAppInfoClientPlaceLoadings.size(); i++) {
                mAppInfoClientPlaceLoadingPositions.add(i, i);
            }
        } else {
            //判断目前的数据，如果有一样的，就不添加。如果有不一样的，就添加。
            mAppInfoClientPlaceUnLoadings = addAppInfoClientPlace(mAppInfoClientPlaceUnLoadings, appPublicInfoWrappers, type);

            mDataBinding.layoutUnloading.llUnloading.removeAllViews();

            //显示多个地址
            for (int i = 0; i < mAppInfoClientPlaceUnLoadings.size(); i++) {
                addInfoClientPlaceView(i, type);
            }
            mAppInfoClientPlaceUnLoadingPositions.clear();
            for (int i = 0; i < mAppInfoClientPlaceUnLoadings.size(); i++) {
                mAppInfoClientPlaceUnLoadingPositions.add(i, i);
            }
        }
    }

    /**
     * @param appInfoClientPlaces   当前装卸货对象
     * @param appPublicInfoWrappers 添加地址返回来的对象
     * @param type                  0是装货 1是卸货
     */
    private ArrayList<AppInfoClientPlace> addAppInfoClientPlace(ArrayList<AppInfoClientPlace> appInfoClientPlaces, ArrayList<AppPublicInfoWrapper> appPublicInfoWrappers, int type) {
        if (appInfoClientPlaces != null) {
            if (appInfoClientPlaces.size() == 0) {
                for (AppPublicInfoWrapper newAppPublicInfoWrapper : appPublicInfoWrappers) {
                    //添加
                    AppInfoClientPlace newAppInfoClientPlace = new AppInfoClientPlace();
                    newAppInfoClientPlace.setRegionid(ObjectUtils.parseString(newAppPublicInfoWrapper.getFullName()));
                    newAppInfoClientPlace.setAddress(ObjectUtils.parseString(newAppPublicInfoWrapper.getShortName()));
                    newAppInfoClientPlace.setLinkman(newAppPublicInfoWrapper.getRemark1());
                    newAppInfoClientPlace.setLinkmanMp(newAppPublicInfoWrapper.getRemark2());
                    newAppInfoClientPlace.setLinkmanTel(newAppPublicInfoWrapper.getRemark3());
                    newAppInfoClientPlace.setRemark(newAppPublicInfoWrapper.getRemark4());
                    appInfoClientPlaces.add(newAppInfoClientPlace);
                }
            }
            for (AppPublicInfoWrapper newAppPublicInfoWrapper : appPublicInfoWrappers) {
                boolean isAdd = true;
                for (AppInfoClientPlace appInfoClientPlace : appInfoClientPlaces) {
                    if (ObjectUtils.equals(newAppPublicInfoWrapper.getFullName(), appInfoClientPlace.getRegionid()) &&
                            ObjectUtils.equals(newAppPublicInfoWrapper.getShortName(), appInfoClientPlace.getAddress()) &&
                            ObjectUtils.equals(newAppPublicInfoWrapper.getRemark1(), appInfoClientPlace.getLinkman()) &&
                            ObjectUtils.equals(newAppPublicInfoWrapper.getRemark2(), appInfoClientPlace.getLinkmanMp()) &&
                            ObjectUtils.equals(newAppPublicInfoWrapper.getRemark3(), appInfoClientPlace.getLinkmanTel()) &&
                            ObjectUtils.equals(newAppPublicInfoWrapper.getRemark4(), appInfoClientPlace.getRemark())
                            ) {
                        isAdd = false;
                        break;
                    }
                }
                if (isAdd) {
                    //添加
                    AppInfoClientPlace newAppInfoClientPlace = new AppInfoClientPlace();
                    newAppInfoClientPlace.setRegionid(ObjectUtils.parseString(newAppPublicInfoWrapper.getFullName()));
                    newAppInfoClientPlace.setAddress(ObjectUtils.parseString(newAppPublicInfoWrapper.getShortName()));
                    newAppInfoClientPlace.setLinkman(newAppPublicInfoWrapper.getRemark1());
                    newAppInfoClientPlace.setLinkmanMp(newAppPublicInfoWrapper.getRemark2());
                    newAppInfoClientPlace.setLinkmanTel(newAppPublicInfoWrapper.getRemark3());
                    newAppInfoClientPlace.setRemark(newAppPublicInfoWrapper.getRemark4());
                    appInfoClientPlaces.add(newAppInfoClientPlace);
                }
            }
        }
        return appInfoClientPlaces;
    }

    /**
     * 编辑地址view
     *
     * @param i 索引
     */
    private void editInfoClientPlaceView(int i, int type) {
        if (type == PublicSearchInteractorImpl.TYPE_LOADPLACES) {
            mDataBinding.layoutLoading.llLoading.removeViewAt(i);
        } else {
            mDataBinding.layoutUnloading.llUnloading.removeViewAt(i);
        }
        addInfoClientPlaceView(i, type);
    }

    /**
     * 添加地址view
     *
     * @param i    索引
     * @param type 装卸货类型
     */
    private void addInfoClientPlaceView(int i, int type) {
        ArrayList<AppInfoClientPlace> appInfoClientPlaces;
        if (type == PublicSearchInteractorImpl.TYPE_LOADPLACES) {
            appInfoClientPlaces = mAppInfoClientPlaceLoadings;
        } else {
            appInfoClientPlaces = mAppInfoClientPlaceUnLoadings;
        }

        LinearLayout moreView = (LinearLayout) LayoutInflater.from(BaseBusinessOrderFragment.this.getContext()).inflate(R.layout.item_business_loading_trailer, mDataBinding.layoutLoading.llLoading, false);

        TextView tvName = moreView.findViewById(R.id.tvName);
        TextView tvShortName = moreView.findViewById(R.id.tvShortName);
        ImageView imgDelete = moreView.findViewById(R.id.imgDelete);
        ImageView imgEdit = moreView.findViewById(R.id.imgEdit);
        TextView tvDelete = moreView.findViewById(R.id.tvDelete);
        TextView tvEdit = moreView.findViewById(R.id.tvEdit);
        LinearLayout llEditAndDelete = moreView.findViewById(R.id.llEditAndDelete);

        // 判断是否导入，未导入就显示
        if (mIsExport.equals("0")) {
            llEditAndDelete.setVisibility(View.VISIBLE);
        }

        // 添加索引标签
        imgDelete.setTag(i);
        imgEdit.setTag(i);
        tvDelete.setTag(i);
        tvEdit.setTag(i);
        ReadMoreTextView tvRemark = moreView.findViewById(R.id.tvRemark);
        if (TextUtils.isEmpty(appInfoClientPlaces.get(i).getRemark())) {
            tvRemark.setVisibility(android.view.View.GONE);
        } else {
            tvRemark.setVisibility(android.view.View.VISIBLE);
            tvRemark.setText(ObjectUtils.parseString(appInfoClientPlaces.get(i).getRemark()));
            tvRemark.setTrimCollapsedText("查看更多");
            tvRemark.setTrimExpandedText("隐藏");
        }

        tvName.setText(ObjectUtils.parseString(appInfoClientPlaces.get(i).getLinkman()) + " " + ObjectUtils.parseString(appInfoClientPlaces.get(i).getLinkmanMp()) + " " + ObjectUtils.parseString(appInfoClientPlaces.get(i).getLinkmanTel()));
        tvShortName.setText((ObjectUtils.parseString(appInfoClientPlaces.get(i).getRegionid()) + ObjectUtils.parseString(appInfoClientPlaces.get(i).getAddress())));


        // 编辑
        if (type == PublicSearchInteractorImpl.TYPE_LOADPLACES) {
            imgEdit.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(android.view.View v) {
                    // 循环
                    for (int i = 0; i < mAppInfoClientPlaceLoadingPositions.size(); i++) {
                        // 判断相等的索引
                        if (mAppInfoClientPlaceLoadingPositions.get(i) == ObjectUtils.parseInt(v.getTag())) {
                            startFragment(mAppInfoClientPlaceLoadings.get(i), i, PublicSearchInteractorImpl.TYPE_LOADPLACES);
                            break;
                        }
                    }
                }
            });
            tvEdit.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(android.view.View v) {
                    // 循环
                    for (int i = 0; i < mAppInfoClientPlaceLoadingPositions.size(); i++) {
                        // 判断相等的索引
                        if (mAppInfoClientPlaceLoadingPositions.get(i) == ObjectUtils.parseInt(v.getTag())) {
                            startFragment(mAppInfoClientPlaceLoadings.get(i), i, PublicSearchInteractorImpl.TYPE_LOADPLACES);
                            break;
                        }
                    }
                }
            });
        } else {
            imgEdit.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(android.view.View v) {
                    // 循环
                    for (int i = 0; i < mAppInfoClientPlaceUnLoadingPositions.size(); i++) {
                        // 判断相等的索引
                        if (mAppInfoClientPlaceUnLoadingPositions.get(i) == ObjectUtils.parseInt(v.getTag())) {
                            startFragment(mAppInfoClientPlaceLoadings.get(i), i, PublicSearchInteractorImpl.TYPE_UNLOADPLACES);
                            break;
                        }
                    }
                }
            });
            tvEdit.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(android.view.View v) {
                    // 循环
                    for (int i = 0; i < mAppInfoClientPlaceUnLoadingPositions.size(); i++) {
                        // 判断相等的索引
                        if (mAppInfoClientPlaceUnLoadingPositions.get(i) == ObjectUtils.parseInt(v.getTag())) {
                            startFragment(mAppInfoClientPlaceLoadings.get(i), i, PublicSearchInteractorImpl.TYPE_UNLOADPLACES);
                            break;
                        }
                    }
                }
            });
        }

        //删除
        if (type == PublicSearchInteractorImpl.TYPE_LOADPLACES) {
            imgDelete.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(android.view.View v) {
                    // 循环
                    for (int i = 0; i < mAppInfoClientPlaceLoadingPositions.size(); i++) {
                        // 判断相等的索引
                        if (mAppInfoClientPlaceLoadingPositions.get(i) == ObjectUtils.parseInt(v.getTag())) {
                            // 删除前判断有没有，因为两个控件的原因，可能导致触发两次
                            if (mDataBinding.layoutLoading.llLoading.getChildAt(i) != null) {
                                mDataBinding.layoutLoading.llLoading.removeViewAt(i);
                                mAppInfoClientPlaceLoadings.remove(i);
                                mAppInfoClientPlaceLoadingPositions.remove(i);
                            }
                            break;
                        }
                    }
                }
            });
            tvDelete.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(android.view.View v) {
                    // 循环
                    for (int i = 0; i < mAppInfoClientPlaceLoadingPositions.size(); i++) {
                        // 判断相等的索引
                        if (mAppInfoClientPlaceLoadingPositions.get(i) == ObjectUtils.parseInt(v.getTag())) {
                            // 删除前判断有没有，因为两个控件的原因，可能导致触发两次
                            if (mDataBinding.layoutLoading.llLoading.getChildAt(i) != null) {
                                mDataBinding.layoutLoading.llLoading.removeViewAt(i);
                                mAppInfoClientPlaceLoadings.remove(i);
                                mAppInfoClientPlaceLoadingPositions.remove(i);
                            }
                            break;
                        }
                    }
                }
            });
            mDataBinding.layoutLoading.llLoading.addView(moreView, i);
        } else {
            imgDelete.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(android.view.View v) {
                    // 循环
                    for (int i = 0; i < mAppInfoClientPlaceUnLoadingPositions.size(); i++) {
                        // 判断相等的索引
                        if (mAppInfoClientPlaceUnLoadingPositions.get(i) == ObjectUtils.parseInt(v.getTag())) {
                            // 删除前判断有没有，因为两个控件的原因，可能导致触发两次
                            if (mDataBinding.layoutUnloading.llUnloading.getChildAt(i) != null) {
                                mDataBinding.layoutUnloading.llUnloading.removeViewAt(i);
                                mAppInfoClientPlaceUnLoadings.remove(i);
                                mAppInfoClientPlaceUnLoadingPositions.remove(i);
                            }
                            break;
                        }
                    }
                }
            });
            tvDelete.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(android.view.View v) {
                    // 循环
                    for (int i = 0; i < mAppInfoClientPlaceUnLoadingPositions.size(); i++) {
                        // 判断相等的索引
                        if (mAppInfoClientPlaceUnLoadingPositions.get(i) == ObjectUtils.parseInt(v.getTag())) {
                            // 删除前判断有没有，因为两个控件的原因，可能导致触发两次
                            if (mDataBinding.layoutUnloading.llUnloading.getChildAt(i) != null) {
                                mDataBinding.layoutUnloading.llUnloading.removeViewAt(i);
                                mAppInfoClientPlaceUnLoadings.remove(i);
                                mAppInfoClientPlaceUnLoadingPositions.remove(i);
                            }
                            break;
                        }
                    }
                }
            });

            mDataBinding.layoutUnloading.llUnloading.addView(moreView, i);
        }

    }

    /**
     * 打开地址Fragment
     *
     * @param appInfoClientPlace 地址实体类
     * @param finalI             索引
     * @param type               装卸货类型
     */
    private void startFragment(AppInfoClientPlace appInfoClientPlace, int finalI, int type) {
        //打开前赋值，确保返回后是属于哪个类型
        mType = type;

        //赋值索引
        mAppInfoClientPlacePosition = finalI;
        AppPublicInfoWrapper appPublicInfoWrapper = new AppPublicInfoWrapper();
        appPublicInfoWrapper.setFullName(appInfoClientPlace.getRegionid());
        appPublicInfoWrapper.setShortName(appInfoClientPlace.getAddress());
        appPublicInfoWrapper.setRemark1(appInfoClientPlace.getLinkman());
        appPublicInfoWrapper.setRemark2(appInfoClientPlace.getLinkmanMp());
        appPublicInfoWrapper.setRemark3(appInfoClientPlace.getLinkmanTel());
        appPublicInfoWrapper.setRemark4(appInfoClientPlace.getRemark());

        if (type == PublicSearchInteractorImpl.TYPE_LOADPLACES) {
            startForResult(AddAddressFragment.newInstance(appPublicInfoWrapper, null, null, PublicSearchInteractorImpl.TYPE_LOADPLACES, true), REQUSET_APPINFOCLIENT);
        } else {
            startForResult(AddAddressFragment.newInstance(appPublicInfoWrapper, null, null, PublicSearchInteractorImpl.TYPE_UNLOADPLACES, true), REQUSET_APPINFOCLIENT);
        }
    }

    /**
     * 初始化已经导入的文本
     */
    protected void initIsExport() {
        ((TextView) mToolbarCommonBinding.toolbar.findViewById(R.id.toolbar_title)).setText("查看预订单");

        mDataBinding.layoutInformation.rlImportTime.setVisibility(View.VISIBLE);
        mDataBinding.btnComit.setVisibility(View.GONE);

        //托运人,1)委托信息 已导入设置所有东西只能查看
        mDataBinding.layoutEntrusted.rlShipper.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutEntrusted.tvValueShipper.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        // 物流企业
        mDataBinding.layoutEntrusted.rlCompany.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutEntrusted.tvValueCompany.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        //起运地
        mDataBinding.layoutEntrusted.rlDeparture.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutEntrusted.tvValueDepartureTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        //目的地
        mDataBinding.layoutEntrusted.rlDestination.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutEntrusted.tvValueDestination.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        //货物名称
        mDataBinding.layoutEntrusted.rlGoodsName.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutEntrusted.tvValueGoodsName.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        //操作条款
        mDataBinding.layoutEntrusted.rlOperationClause.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutEntrusted.tvValueOperationClause.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        //货主
        mDataBinding.layoutEntrusted.etConsigneeIdonsigneeId.setEnabled(false);
        mDataBinding.layoutEntrusted.etConsigneeIdonsigneeId.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));

        //目的港,2)船公司信息
        mDataBinding.layoutShipping.rlPortOfDestination.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutShipping.tvValuePortDestinationTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        //起运港
        mDataBinding.layoutShipping.rlPortOfShipment.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutShipping.tvValuePortShipmentTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        //运输条款
        mDataBinding.layoutShipping.rlTransportationClause.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutShipping.tvValueTransportationClause.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));

        //装货信息,3)
        mDataBinding.layoutLoading.rlLoading.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutLoading.tvLoading.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));

        //卸货信息,4)
        mDataBinding.layoutUnloading.rlUnloading.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutUnloading.tvUnLoading.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));

        //是否保险,5)保险信息
        mDataBinding.layoutInsurance.rlIsInsurance.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutInsurance.tvIsInsurance.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        mDataBinding.layoutInsurance.rlInsurMoney.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutInsurance.etValueInsurMoney.setEnabled(false);
        mDataBinding.layoutInsurance.rlInsurRate.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutInsurance.etValueInsurRate.setEnabled(false);
        mDataBinding.layoutInsurance.rlInsurValue.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutInsurance.etValueInsurValue.setEnabled(false);

        //数量,6)货柜信息
        //屏蔽列表点击事件
        containerAdapter.setEnable(false);
        containerAdapter.notifyDataSetChanged();
        //屏蔽文本框事件
        mDataBinding.layoutContainer.etContainer.setEnabled(false);
        //类型
        mDataBinding.layoutContainer.tvCabinetType0.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        //柜型选择框
        mDataBinding.layoutContainer.llContainerTitle.setOnTouchListener((v, event) -> true);
        //添加框
        mDataBinding.layoutContainer.llAddTitle.setOnTouchListener((v, event) -> true);

        //日月时分秒,7) 其他
        mDataBinding.layoutCostinformation.tvLoadingDate.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutCostinformation.tvLoadingDate.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        mDataBinding.layoutCostinformation.tvLoadingTime.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutCostinformation.tvLoadingTime.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        //预定费用
        mDataBinding.layoutCostinformation.etValueReservationFee.setEnabled(false);
        //备注
        mDataBinding.layoutCostinformation.etValueRemark.setEnabled(false);
        mDataBinding.layoutCostinformation.etValueRemark1.setEnabled(false);
        mDataBinding.layoutCostinformation.etValueRemark2.setEnabled(false);

        //单号信息,8)
        mDataBinding.layoutInformation.rlOrderSn.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutInformation.rlCreated.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutInformation.rlImportTime.setOnTouchListener((v, event) -> true);

        mDataBinding.layoutEntrusted.imgCompany.setVisibility(android.view.View.GONE);
        mDataBinding.layoutEntrusted.imgShipper.setVisibility(android.view.View.GONE);
        mDataBinding.layoutEntrusted.vDepartureTitle.setVisibility(android.view.View.GONE);
        mDataBinding.layoutEntrusted.vDestination.setVisibility(android.view.View.GONE);
        mDataBinding.layoutEntrusted.vGoodsName.setVisibility(android.view.View.GONE);
        mDataBinding.layoutEntrusted.vTitleOperationClause.setVisibility(android.view.View.GONE);
        mDataBinding.layoutShipping.vPortShipmentTitle.setVisibility(android.view.View.GONE);
        mDataBinding.layoutShipping.vPortDestinationTitle.setVisibility(android.view.View.GONE);
        mDataBinding.layoutShipping.vTitleTransportationClause.setVisibility(android.view.View.GONE);
        mDataBinding.layoutLoading.vLoading.setVisibility(android.view.View.GONE);
        mDataBinding.layoutUnloading.vUnLoading.setVisibility(android.view.View.GONE);
        mDataBinding.layoutInsurance.vIsInsurance.setVisibility(android.view.View.GONE);

        mDataBinding.layoutCostinformation.imgXDateTime.setVisibility(android.view.View.GONE);
        mDataBinding.layoutEntrusted.imgXDeparture.setVisibility(android.view.View.GONE);
        mDataBinding.layoutEntrusted.imgXDestination.setVisibility(android.view.View.GONE);
        mDataBinding.layoutLoading.imgXLoading.setVisibility(android.view.View.GONE);
        mDataBinding.layoutEntrusted.imgXGoodsName.setVisibility(android.view.View.GONE);
        mDataBinding.layoutShipping.imgXPortDestination.setVisibility(android.view.View.GONE);
        mDataBinding.layoutShipping.imgXPortShipment.setVisibility(android.view.View.GONE);
        mDataBinding.layoutCostinformation.imgXReservationFee.setVisibility(android.view.View.GONE);
        mDataBinding.layoutEntrusted.imgXShipper.setVisibility(android.view.View.GONE);
        mDataBinding.layoutUnloading.imgXUnLoading.setVisibility(android.view.View.GONE);
    }

}
