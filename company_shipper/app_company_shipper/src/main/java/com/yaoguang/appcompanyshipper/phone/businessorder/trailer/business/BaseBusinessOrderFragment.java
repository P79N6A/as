package com.yaoguang.appcompanyshipper.phone.businessorder.trailer.business;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.google.gson.Gson;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.publicsearch.addaddress.AddAddressFragment;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.appcompanyshipper.R;
import com.yaoguang.appcompanyshipper.databinding.FragmentBusinessOrderTrailerBinding;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.BusinessOrderTrailerSonsFragment;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.list.event.BusinessOrderListEvent;
import com.yaoguang.appcompanyshipper.phone.businessorder.trailer.business.adapter.ContainerAdapter;
import com.yaoguang.greendao.entity.AppPublicInfoWrapper;
import com.yaoguang.greendao.entity.AppTruckSono;
import com.yaoguang.greendao.entity.company.AppInfoClientPlace;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.appcommon.utils.TextViewUtils;
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
        extends BaseFragmentDataBind<FragmentBusinessOrderTrailerBinding>
        implements BaseBusinessOrderContact.View<T>, Toolbar.OnMenuItemClickListener {

    private DialogHelper mDialogHelperOrder;
    private DialogHelper mDialogHelperEdit;

    /**
     * 货主端：添加装货地址返回过来的标签
     */
    protected static final int REQUSET_APPINFOCLIENT_LOAD_ADD = 3333;
    /**
     * 应用模版后返回的数据
     */
    protected static final int REQUSET_MODEL_TEMP = 5555;
    private static final int REQUSET_SONS = 1;
    private static final int REQUSET_APPINFOCLIENT = 2;
    private static final int REQUSET_TEMP = 3;

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

    protected ContainerAdapter containerAdapter;

    ProvinceBeans mProvinceBeans;

    //装货或者卸货数据
    protected ArrayList<AppInfoClientPlace> mAppInfoClientPlaceLoadings = new ArrayList<>();
    protected ArrayList<Integer> mAppInfoClientPlaceLoadingPositions = new ArrayList<>();//装货数据,记录索引id，避免删除后导致索引错乱
    //修改地址的索引
    private int mAppInfoClientPlacePosition = -1;
    //货柜数据
    protected ArrayList<AppTruckSono> mAppTruckSonos = new ArrayList<>();
    //货柜的选项
    ArrayList<MenuEntity> mSonosMenuEntity = new ArrayList<>();

    /**
     * 保存订单对象，进行下单
     */
    protected abstract void setOrder(String sonos);

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_order_trailer;
    }

    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "新建预订单", R.menu.menu_business_order, BaseBusinessOrderFragment.this);

        containerAdapter = new ContainerAdapter();
        RecyclerViewUtils.initPage(mDataBinding.layoutContainer.rvContainer, containerAdapter, null, getContext(), false);

        //封号只能输入数字和字母
        TextViewUtils.setAlphaNumeric(mDataBinding.layoutShipping.etPortDestination);

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

        //装卸选择,委托信息 1)
        mDataBinding.layoutEntrusted.rlLoadingAndUnloading.setOnClickListener(v -> {
            SweetSheet sweetSheet = new SweetSheet(mDataBinding.flMain);
            sweetSheet.setMenuList(R.menu.sheet_loading_unloading_business);
            sweetSheet.setTitle("装/卸");
            RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
            recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(200));
            sweetSheet.setDelegate(recyclerViewDelegate);
            sweetSheet.setBackgroundEffect(new DimEffect(4));
            sweetSheet.setOnMenuItemClickListener((position, menuEntity1) -> {
                mDataBinding.layoutEntrusted.tvValueLoadingAndUnloading.setText(menuEntity1.title);
                initIsLoadingOrUnLoading();
                return true;
            });
            InputMethodUtil.hideKeyboard(getActivity());
            sweetSheet.show();
        });
        //托运人
        mDataBinding.layoutEntrusted.rlShipper.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER111);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER111));
            }
        });

        // 物流企业
        mDataBinding.layoutEntrusted.rlCompany.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY));
        });

        //货物名称
        mDataBinding.layoutEntrusted.rlGoodsName.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
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


            }
        });

        // 港口 - 起运港
        mDataBinding.layoutEntrusted.rlPort.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
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
            }
        });

        //地区 - 起运地
        mDataBinding.layoutEntrusted.rlRegion.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
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
            }
        });

        //船公司,航次信息 2）
        mDataBinding.layoutShipping.rlShippingCompany.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                // 判断物流端或者货主端
                SearchFragment fragment;
                if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                    fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY);
                } else {
                    if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueCompany.getText())) {
                        ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueCompany.getHint().toString());
                        return;
                    }
                    fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY, mDataBinding.layoutEntrusted.tvValueCompany.getTag().toString());
                }
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY));
            }
        });

        //预到船期
        mDataBinding.layoutShipping.tvAdvanceShipmentDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
                dateBeginPickerFragment.show(getFragmentManager(), "AdvanceShipmentDate");
                Bundle args = new Bundle();
                args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
                dateBeginPickerFragment.setArguments(args);
                dateBeginPickerFragment.setComeBack((data, tag) -> {
                    if (tag.equals("AdvanceShipmentDate" + TimePickerFragment.TAGTIME)) {
                        mDataBinding.layoutShipping.tvAdvanceShipmentDate.setText(data + ":00");
                    }
                });
            }
        });
        mDataBinding.layoutShipping.imgAdvanceShipmentDateF.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
                dateBeginPickerFragment.show(getFragmentManager(), "AdvanceShipmentDate");
                Bundle args = new Bundle();
                args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
                dateBeginPickerFragment.setArguments(args);
                dateBeginPickerFragment.setComeBack((data, tag) -> {
                    if (tag.equals("AdvanceShipmentDate" + TimePickerFragment.TAGTIME)) {
                        mDataBinding.layoutShipping.tvAdvanceShipmentDate.setText(data + ":00");
                    }
                });
            }
        });

        mDataBinding.layoutShipping.imgAdvanceShipmentDateClear.setOnClickListener(v -> mDataBinding.layoutShipping.tvAdvanceShipmentDate.setText(""));

        // 货柜，添加货柜
        mDataBinding.layoutContainer.rlAddContainer.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueCompany.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueCompany.getHint().toString());
                    return;
                }
                startForResult(BusinessOrderTrailerSonsFragment.newInstance(mAppTruckSonos, mSonosMenuEntity, BusinessOrderTrailerSonsFragment.ADD, -1, mDataBinding.layoutEntrusted.tvValueCompany.getTag().toString()), REQUSET_SONS);
            }
        });

        // 其他 - 时间
        mDataBinding.layoutCostinformation.tvLoadingDate.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
                dateBeginPickerFragment.show(getFragmentManager(), "LoadingDate");
                dateBeginPickerFragment.setComeBack((data, tag) -> {
                    if (tag.equals("LoadingDate")) {
                        mDataBinding.layoutCostinformation.tvLoadingDate.setText(data);
                    }
                });
            }
        });
        mDataBinding.layoutCostinformation.tvLoadingTime.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getFragmentManager(), "LoadingTime");
                timePickerFragment.setComeBack((data, tag) -> {
                    if (tag.equals("LoadingTime")) {
                        mDataBinding.layoutCostinformation.tvLoadingTime.setText(data);
                    }
                });
            }
        });

        // 货柜点击事件
        containerAdapter.setOnRecyclerViewItemRLClickListener(new ContainerAdapter.OnRecyclerViewItemRLClickListener() {

            @Override
            public void onRlRemoveClick(View itemView, AppTruckSono item, int position) {
                removewContainer(position);
            }

            @Override
            public void onRlAddClick(View itemView, AppTruckSono item, int position) {
                if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueCompany.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueCompany.getHint().toString());
                    return;
                }
                startForResult(BusinessOrderTrailerSonsFragment.newInstance(mAppTruckSonos, mSonosMenuEntity, BusinessOrderTrailerSonsFragment.ADD, position, mDataBinding.layoutEntrusted.tvValueCompany.getTag().toString()), REQUSET_SONS);
            }

            @Override
            public void onEditAddClick(View itemView, AppTruckSono item, int position) {
                if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueCompany.getText())) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueCompany.getHint().toString());
                    return;
                }
                startForResult(BusinessOrderTrailerSonsFragment.newInstance(mAppTruckSonos, mSonosMenuEntity, BusinessOrderTrailerSonsFragment.EDIT, position, mDataBinding.layoutEntrusted.tvValueCompany.getTag().toString()), REQUSET_SONS);
            }

        });

        // 添加
        mDataBinding.btnComit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                InputMethodUtil.hideKeyboard(getActivity());
                addCompanyOrder();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mID = args.getString(BUNDLE_ID);
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
        mPresenter.subscribe();
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_CONTACT_COMPANY:
                    // 如果新的物流企业跟现在物流企业不一样的话，则其他有关物流企业的，重新选择
                    if (!data.getString("name").equals(mDataBinding.layoutEntrusted.tvValueCompany.getText())) {
                        mDataBinding.layoutEntrusted.tvValueCompany.setText(data.getString("name"));
                        mDataBinding.layoutEntrusted.tvValueCompany.setTag(data.getString("id"));
                    }
                    // 清空货物名称
                    mDataBinding.layoutEntrusted.tvValueGoodsName.setText("");
                    // 清空起运港
                    mDataBinding.layoutEntrusted.tvValuePort.setText("");
                    // 清空起运地
                    mDataBinding.layoutEntrusted.tvValueRegion.setText("");
                    // 清空船公司
                    mDataBinding.layoutShipping.tvValueShippingCompany.setText("");

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
                        initLoading(appPublicInfoWrapperAdds);
                    }
                    break;
                case REQUSET_TEMP:
                    //获取模版数据
                    mOrder = data.getParcelable(BUNDLE_ORDER);
                    showDetail(mOrder, true);
                    break;
                case REQUSET_APPINFOCLIENT:
                    //获取地址信息
                    AppInfoClientPlace appInfoClientPlace = data.getParcelable("appInfoClientPlace");
                    mAppInfoClientPlaceLoadings.set(mAppInfoClientPlacePosition, appInfoClientPlace);
                    mAppInfoClientPlaceLoadingPositions.set(mAppInfoClientPlacePosition, mAppInfoClientPlacePosition);
                    editInfoClientPlaceView(mAppInfoClientPlacePosition);
                    break;
                case REQUSET_SONS:
                    //获取货柜数据
                    AppTruckSono appTruckSono = data.getParcelable("appTruckSono");
                    //判断编辑还是添加
                    if (data.getInt("type") == BusinessOrderTrailerSonsFragment.ADD) {
                        addContainer(appTruckSono);
                    } else if (data.getInt("type") == BusinessOrderTrailerSonsFragment.EDIT) {
                        editContainer(appTruckSono, data.getInt("position", -1));
                    }
                    break;
                case PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER111:
                    mDataBinding.layoutEntrusted.tvValueShipper.setText(data.getString("name"));
                    mDataBinding.layoutEntrusted.tvValueShipper.setTag(data.getString("id"));
                    break;
                case PublicSearchInteractorImpl.TYPE_GOODSNAME:
                    mDataBinding.layoutEntrusted.tvValueGoodsName.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_LOADPLACES:
                    ArrayList<AppPublicInfoWrapper> appPublicInfoWrappers = data.getParcelableArrayList("appPublicInfoWrappers");
                    initLoading(appPublicInfoWrappers);
                    break;
                case PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT55:
                    //港口
                    mDataBinding.layoutEntrusted.tvValuePort.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_DEPARTURE222:
                    //地区
                    mDataBinding.layoutEntrusted.tvValueRegion.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_SHIP_COMPANY:
                    //船公司
                    mDataBinding.layoutShipping.tvValueShippingCompany.setText(data.getString("name"));
                    break;
                case REQUSET_MODEL_TEMP:
                    mOrder = data.getParcelable(BUNDLE_ORDER);
                    // 获取模板信息之前，先删除当前信息
                    mAppInfoClientPlaceLoadings.clear();
                    mAppInfoClientPlaceLoadingPositions.clear();
                    mAppTruckSonos.clear();
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
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 添加柜型柜量
     *
     * @param appTruckSono 柜型实体类
     */
    private void addContainer(AppTruckSono appTruckSono) {
        containerAdapter.add(containerAdapter.getList().size(), appTruckSono);
        showOrHideContainer();
        mAppTruckSonos.clear();
        for (int i = 0; i <= containerAdapter.getList().size() - 1; i++) {
            mAppTruckSonos.add(containerAdapter.getList().get(i));
        }
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity());
        mDataBinding.layoutContainer.rvContainer.setLayoutManager(myLayoutManager);
    }

    /**
     * 编辑柜型柜量
     *
     * @param appTruckSono 柜型实体类
     * @param position     索引
     */
    private void editContainer(AppTruckSono appTruckSono, int position) {
        containerAdapter.removeItem(position);
        containerAdapter.add(position, appTruckSono);
        mAppTruckSonos.clear();
        for (int i = 0; i <= containerAdapter.getList().size() - 1; i++) {
            mAppTruckSonos.add(containerAdapter.getList().get(i));
        }
    }

    /**
     * 删除
     *
     * @param position 索引
     */
    private void removewContainer(int position) {
        containerAdapter.removeItem(position);
        showOrHideContainer();
        mAppTruckSonos.clear();
        for (int i = 0; i <= containerAdapter.getList().size() - 1; i++) {
            mAppTruckSonos.add(containerAdapter.getList().get(i));
        }
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
        //这个柜型可以传递到下一个窗体添加
        mSonosMenuEntity = (ArrayList<MenuEntity>) valueLevel1;
    }

    @Override
    public void setClause(List<MenuEntity> options1Items) {

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

        // 货物名称
        if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueGoodsName.getText().toString())) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueGoodsName.getHint().toString());
            return;
        }
        //港口
        if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValuePort.getText().toString())) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValuePort.getHint().toString());
            return;
        }
        //地区
        if (TextUtils.isEmpty(mDataBinding.layoutEntrusted.tvValueRegion.getText().toString())) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutEntrusted.tvValueRegion.getHint().toString());
            return;
        }

        // 2.）船公司
        if (TextUtils.isEmpty(mDataBinding.layoutShipping.tvValueShippingCompany.getText().toString())) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), mDataBinding.layoutShipping.tvValueShippingCompany.getHint().toString());
            return;
        }
        if (mDataBinding.layoutEntrusted.tvValueLoadingAndUnloading.getText().equals(getResources().getString(R.string.loading_goods)) && mAppInfoClientPlaceLoadings.size() <= 0) {
            ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "请选择装货信息");
            return;
        }
        if (mDataBinding.layoutEntrusted.tvValueLoadingAndUnloading.getText().equals(getResources().getString(R.string.unloading_goods)) && mAppInfoClientPlaceLoadings.size() <= 0) {
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

        // 货柜信息
        ArrayList<AppTruckSono> appTruckSonos = new ArrayList<>();
        for (AppTruckSono appTruckSono : containerAdapter.getList()) {
            appTruckSonos.add(appTruckSono);
        }
        Gson gson = new Gson();
        setOrder(gson.toJson(appTruckSonos));

        mPresenter.editTruckOrder(mOrder);
    }

    /**
     * 初始化装卸货的信息
     *
     * @param appPublicInfoWrappers 返回来的装卸货信息
     */
    public void initLoading(ArrayList<AppPublicInfoWrapper> appPublicInfoWrappers) {
        //判断目前的数据，如果有一样的，就不添加。如果有不一样的，就添加。
        mAppInfoClientPlaceLoadings = addAppInfoClientPlace(mAppInfoClientPlaceLoadings, appPublicInfoWrappers);

        mDataBinding.layoutLoading.llLoading.removeAllViews();

        //显示多个地址
        for (int i = 0; i < mAppInfoClientPlaceLoadings.size(); i++) {
            addInfoClientPlaceView(i);
        }
        mAppInfoClientPlaceLoadingPositions.clear();
        for (int i = 0; i < mAppInfoClientPlaceLoadings.size(); i++) {
            mAppInfoClientPlaceLoadingPositions.add(i, i);
        }
    }

    /**
     * @param appInfoClientPlaces   当前装卸货对象
     * @param appPublicInfoWrappers 添加地址返回来的对象
     */
    private ArrayList<AppInfoClientPlace> addAppInfoClientPlace(ArrayList<AppInfoClientPlace> appInfoClientPlaces, ArrayList<AppPublicInfoWrapper> appPublicInfoWrappers) {
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
    private void editInfoClientPlaceView(int i) {
        mDataBinding.layoutLoading.llLoading.removeViewAt(i);
        addInfoClientPlaceView(i);
    }

    /**
     * 添加地址view
     *
     * @param i 索引
     */
    private void addInfoClientPlaceView(int i) {
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
        if (TextUtils.isEmpty(mAppInfoClientPlaceLoadings.get(i).getRemark())) {
            tvRemark.setVisibility(View.GONE);
        } else {
            tvRemark.setVisibility(View.VISIBLE);
            tvRemark.setText(ObjectUtils.parseString(mAppInfoClientPlaceLoadings.get(i).getRemark()));
            tvRemark.setTrimCollapsedText("查看更多");
            tvRemark.setTrimExpandedText("隐藏");
        }

        tvName.setText(ObjectUtils.parseString(mAppInfoClientPlaceLoadings.get(i).getLinkman()) + " " + ObjectUtils.parseString(mAppInfoClientPlaceLoadings.get(i).getLinkmanMp()) + " " + ObjectUtils.parseString(mAppInfoClientPlaceLoadings.get(i).getLinkmanTel()));
        tvShortName.setText((ObjectUtils.parseString(mAppInfoClientPlaceLoadings.get(i).getRegionid()) + ObjectUtils.parseString(mAppInfoClientPlaceLoadings.get(i).getAddress())));

        //赋值编辑删除的动作
        final int finalI = i;

        //编辑
        imgEdit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                // 循环
                for (int i = 0; i < mAppInfoClientPlaceLoadingPositions.size(); i++) {
                    // 判断相等的索引
                    if (mAppInfoClientPlaceLoadingPositions.get(i) == ObjectUtils.parseInt(v.getTag())) {
                        startFragment(mAppInfoClientPlaceLoadings.get(i), i);
                        break;
                    }
                }
            }
        });
        tvEdit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                // 循环
                for (int i = 0; i < mAppInfoClientPlaceLoadingPositions.size(); i++) {
                    // 判断相等的索引
                    if (mAppInfoClientPlaceLoadingPositions.get(i) == ObjectUtils.parseInt(v.getTag())) {
                        startFragment(mAppInfoClientPlaceLoadings.get(i), i);
                        break;
                    }
                }
            }
        });

        //删除
        imgDelete.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
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
            public void onNoDoubleClick(View v) {
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
    }

    /**
     * 打开地址Fragment
     *
     * @param appInfoClientPlace 地址实体类
     * @param finalI             索引
     */
    private void startFragment(AppInfoClientPlace appInfoClientPlace, int finalI) {
        //赋值索引
        mAppInfoClientPlacePosition = finalI;
        AppPublicInfoWrapper appPublicInfoWrapper = new AppPublicInfoWrapper();
        appPublicInfoWrapper.setFullName(appInfoClientPlace.getRegionid());
        appPublicInfoWrapper.setShortName(appInfoClientPlace.getAddress());
        appPublicInfoWrapper.setRemark1(appInfoClientPlace.getLinkman());
        appPublicInfoWrapper.setRemark2(appInfoClientPlace.getLinkmanMp());
        appPublicInfoWrapper.setRemark3(appInfoClientPlace.getLinkmanTel());
        appPublicInfoWrapper.setRemark4(appInfoClientPlace.getRemark());
        if (mDataBinding.layoutEntrusted.tvValueLoadingAndUnloading.getText().equals(getResources().getString(R.string.loading_goods))) {
            startForResult(AddAddressFragment.newInstance(appPublicInfoWrapper, null, null, PublicSearchInteractorImpl.TYPE_LOADPLACES, true), REQUSET_APPINFOCLIENT);
        } else {
            startForResult(AddAddressFragment.newInstance(appPublicInfoWrapper, null, null, PublicSearchInteractorImpl.TYPE_UNLOADPLACES, true), REQUSET_APPINFOCLIENT);
        }
    }

    /**
     * 初始化是装货还是卸货
     */
    protected void initIsLoadingOrUnLoading() {
        //判断是装货，还是卸货
        if (mDataBinding.layoutEntrusted.tvValueLoadingAndUnloading.getText().equals(getResources().getString(R.string.loading_goods))) {
            mDataBinding.layoutLoading.tvTitleLoading.setText("装货信息");
            mDataBinding.layoutCostinformation.tvLoadingDateTitle.setText("装货时间");
            mDataBinding.layoutLoading.imgLoading.setImageResource(R.drawable.ic_zhuanghuo);
            mDataBinding.layoutCostinformation.rlIsFeeCollect.setVisibility(View.GONE);
            mDataBinding.layoutCostinformation.vIsFeeCollect.setVisibility(View.GONE);
        } else {
            mDataBinding.layoutLoading.tvTitleLoading.setText("卸货信息");
            mDataBinding.layoutCostinformation.tvLoadingDateTitle.setText("卸货时间");
            mDataBinding.layoutLoading.imgLoading.setImageResource(R.drawable.ic_xiehuo);
            mDataBinding.layoutCostinformation.rlIsFeeCollect.setVisibility(View.VISIBLE);
            mDataBinding.layoutCostinformation.vIsFeeCollect.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 判断是否显示还是隐藏货柜
     */
    protected void showOrHideContainer() {
        if (containerAdapter.getList().size() > 0) {
            //隐藏添加货柜的栏目
            mDataBinding.layoutContainer.rlAddContainer.setVisibility(View.GONE);
            mDataBinding.layoutContainer.vAddContainer.setVisibility(View.GONE);
        } else {
            //显示
            mDataBinding.layoutContainer.rlAddContainer.setVisibility(View.VISIBLE);
            mDataBinding.layoutContainer.vAddContainer.setVisibility(View.VISIBLE);
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
        //装卸货
        mDataBinding.layoutEntrusted.vLoadingAndUnloading.setVisibility(View.GONE);
        //货物名称
        mDataBinding.layoutEntrusted.rlGoodsName.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutEntrusted.tvValueGoodsName.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        mDataBinding.layoutEntrusted.vGoodsName.setVisibility(View.GONE);
        //货主
        mDataBinding.layoutEntrusted.etConsigneeIdonsigneeId.setEnabled(false);
        mDataBinding.layoutEntrusted.etConsigneeIdonsigneeId.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        //港口
        mDataBinding.layoutEntrusted.rlPort.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutEntrusted.tvValuePort.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        mDataBinding.layoutEntrusted.vTitlePort.setVisibility(View.GONE);
        //起运地
        mDataBinding.layoutEntrusted.rlRegion.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutEntrusted.tvValueRegion.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));

        //目的港,2)航次信息
        mDataBinding.layoutShipping.rlShippingCompany.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutShipping.tvValueShippingCompany.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        //运单号
        mDataBinding.layoutShipping.rlWaybillNumber.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutShipping.etPortDestination.setEnabled(false);
        mDataBinding.layoutShipping.etPortDestination.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        //预到船期
        mDataBinding.layoutShipping.rlAdvanceShipmentDate.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutShipping.tvAdvanceShipmentDate.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));
        mDataBinding.layoutShipping.imgAdvanceShipmentDateClear.setVisibility(View.GONE);
        mDataBinding.layoutShipping.vVAdvanceShipmentDate.setVisibility(View.GONE);
        mDataBinding.layoutShipping.imgAdvanceShipmentDateF.setVisibility(View.GONE);
        mDataBinding.layoutShipping.tvAdvanceShipmentDate.setVisibility(View.GONE);
        mDataBinding.layoutShipping.tvAdvanceShipmentDateExport.setVisibility(View.VISIBLE);

        //装/卸货信息,3)
        mDataBinding.layoutLoading.rlLoading.setOnTouchListener((v, event) -> true);
        mDataBinding.layoutLoading.tvLoading.setTextColor(ContextCompat.getColor(getContext(), R.color.text_hint));


        //数量,6)货柜信息
        containerAdapter.setEnable(false);
        containerAdapter.notifyDataSetChanged();

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

        mDataBinding.layoutCostinformation.imgXDateTime.setVisibility(View.GONE);
        mDataBinding.layoutLoading.imgXLoading.setVisibility(View.GONE);
        mDataBinding.layoutEntrusted.imgXGoodsName.setVisibility(View.GONE);
        mDataBinding.layoutCostinformation.imgXReservationFee.setVisibility(View.GONE);
        mDataBinding.layoutEntrusted.imgXShipper.setVisibility(View.GONE);
        mDataBinding.layoutContainer.imgXContainer.setVisibility(View.GONE);
        mDataBinding.layoutShipping.imgXShippingCompany.setVisibility(View.GONE);
        mDataBinding.layoutEntrusted.imgXPort.setVisibility(View.GONE);
        mDataBinding.layoutEntrusted.imgXRegion.setVisibility(View.GONE);
    }

}
