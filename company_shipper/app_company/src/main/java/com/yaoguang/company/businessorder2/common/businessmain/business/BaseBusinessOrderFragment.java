package com.yaoguang.company.businessorder2.common.businessmain.business;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.businessmain.BaseBusinessMainFragment;
import com.yaoguang.company.businessorder2.common.businessmain.business.event.BusinessFragmentResultEvent;
import com.yaoguang.company.businessorder2.common.loadingandunloading.model.LoadingAndUnloadingModelFragment;
import com.yaoguang.company.businessorder2.forwarder.businessmain.business.BusinessOrderFragment;
import com.yaoguang.company.databinding.FragmentBusinessOrder2Binding;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.company.InfoClientPlace;
import com.yaoguang.greendao.entity.driver.LocationArea;
import com.yaoguang.greendao.entity.driver.Site;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.bean.BaseResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongjh on 2018/11/15.
 */
@SuppressLint("ValidFragment")
public abstract class BaseBusinessOrderFragment<T, AT> extends BaseFragmentDataBind<FragmentBusinessOrder2Binding>
        implements BaseBusinessOrderContact.View {

    protected BaseBusinessOrderContact.Presenter mPresenter;

    protected int mType;
    protected String mOtherService;// 装箱（0） 拆箱（1） 标题文本而不是数字
    public static final int TYPE_MODEL_ADD = 500;// 添加类型的窗体
    public static final int TYPE_MODEL_UPDATE = 501;// 更新类型的窗体
    public static final int TYPE_MODEL_COPY_OR_TEMP = 502;// 拷贝或者模板的窗体


    /**
     * 保险信息
     */
    public static final int TYPE_INSURANCE = 400;
    /**
     * 装货信息
     */
    public static final int TYPE_LOADINGINFORMATION = 401;
    /**
     * 卸货信息
     */
    public static final int TYPE_UNLOADINGINFORMATION = 403;
    /**
     * 船运更多信息
     */
    public static final int TYPE_SHIP = 402;
    /**
     * 装卸货地址直接修改
     */
    public static final int TYPE_MODE = 404;
    /**
     * 装货派车信息
     */
    public static final int TYPE_DISPATCHING_LOADING = 405;
    /**
     * 卸货派车信息
     */
    public static final int TYPE_DISPATCHING_UNLOADING = 406;
    /**
     * 货柜信息
     */
    public static final int TYPE_SONO = 407;

    protected AT mAppOrder;
    protected T mOrder;// 这个要么从上个窗体（拷贝或者模板）传递过来，要么是通过列表的id读取出来
    protected T mOrderOld;

    protected DialogHelper mDialogHelper;
    protected DialogHelper mDialogHelperPop;

    // 缓存的装货信息数据，卸货信息数据
    protected SparseArray<View> mHashMapLoading = new SparseArray<>();
    protected int mKeyLoading;// 目前最多缓存的几个数据
    protected SparseArray<View> mHashMapUnLoading = new SparseArray<>();
    protected int mKeyUnLoading;// 目前最多缓存的几个数据
    protected int mKeyCurrentLoading = -1;// 点击编辑时候的索引
    protected int mKeyCurrentUnLoading = -1;// 点击编辑时候的索引

    // 货柜数据
    protected ArrayList<String> mContainerTypes = new ArrayList<>();

    protected View mLoadingOrUnLoadingView;
    protected ViewGroup mLoadingOrUnLoadingViewGroup;

    protected abstract void cloneData();

    /**
     * 赋值数据
     *
     * @param t 实体数据
     */
    public void setViewOrder(T t) {
        mOrder = t;
    }

    /**
     * @param type 类型，新增、编辑、拷贝（模板）
     */
    @SuppressLint("ValidFragment")
    public BaseBusinessOrderFragment(int type, String otherService) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("otherService", otherService);
        this.setArguments(bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mType = args.getInt("type");
            mOtherService = args.getString("otherService");
        }
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        cloneData();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        mPresenter.subscribe();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_order2;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        // 判断是否编辑的
        if (mType == TYPE_MODEL_UPDATE) {
            mDataBinding.llClickContainer.setVisibility(View.VISIBLE);
        } else {
            // 不是编辑的隐藏 货柜可点击详情
            mDataBinding.llClickContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mPresenter.unSubscribe();
    }

    @Override
    public void initListener() {
        // 货柜
        mDataBinding.flContainerAdd.setOnClickListener(v -> {
            // 弹框
            showSweetSheets(mDataBinding.flContainerAdd.getId());
        });
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    // + 1000 是属于拖车
    @Subscribe
    public void onBusinessFragmentResultEvent(BusinessFragmentResultEvent businessFragmentResultEvent) {
        switch (businessFragmentResultEvent.getType()) {
            case PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER111:
                // 托运人
                mDataBinding.tvValueShipper.setText(businessFragmentResultEvent.getData().getString("name"));
                mDataBinding.tvValueShipper.setTag(businessFragmentResultEvent.getData().getString("id"));
                break;
            case PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER111 + 1000:
                // 托运人
                mDataBinding.tvValueShipperTruck.setText(businessFragmentResultEvent.getData().getString("name"));
                mDataBinding.tvValueShipperTruck.setTag(businessFragmentResultEvent.getData().getString("id"));
                break;
            case PublicSearchInteractorImpl.TYPE_DEPARTURE222:
                // 起运地
                mDataBinding.tvValueDeparture.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_DEPARTURE222 + 1000:
                // 起运地
                mDataBinding.tvValueDepartureTruck.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_DESTINATION333:
                // 目的地
                mDataBinding.tvValueDestination.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_DESTINATION333 + 1000:
                // 目的地
                mDataBinding.tvValueDestinationTruck.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_GOODSNAME:
                // 货物名称
                mDataBinding.tvValueGoodsName.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_GOODSNAME + 1000:
                // 货物名称
                mDataBinding.tvValueGoodsNameTruck.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_EMPLOYEES:
                // 业务员
                mDataBinding.tvValueSalesman.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_EMPLOYEES + 1000:
                // 业务员
                mDataBinding.tvValueSalesmanTruck.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_FORWARD:
                // 货代公司
                mDataBinding.tvValueForwardingCompany.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT55:
                // 起运港
                mDataBinding.tvValuePortOfDeparture.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT55 + 1000:
                // 起运港
                mDataBinding.tvValuePortOfDepartureTruck.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION66:
                // 目的港
                mDataBinding.tvValuePortOfDestination.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION66 + 1000:
                // 目的港
                mDataBinding.tvValuePortOfDestinationTruck.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_SHIP_COMPANY:
                // 船公司
                mDataBinding.tvValueShipCompany.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_SHIP_COMPANY + 1000:
                // 船公司
                mDataBinding.tvValueShipCompanyTruck.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_USER_INFO_SHIP:
                // 干线船名
                mDataBinding.tvValueTrunkShipName.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case PublicSearchInteractorImpl.TYPE_USER_INFO_SHIP + 1000:
                // 干线船名
                mDataBinding.tvValueTrunkShipNameTruck.setText(businessFragmentResultEvent.getData().getString("name"));
                break;
            case TYPE_LOADINGINFORMATION:
                // 装货信息
                InfoClientPlace infoClientPlace = businessFragmentResultEvent.getData().getParcelable("infoClientPlace");
                addLoading(infoClientPlace, mDataBinding.llLoading, 0);
                break;
            case TYPE_UNLOADINGINFORMATION:
                // 卸货信息
                InfoClientPlace unInfoClientPlace = businessFragmentResultEvent.getData().getParcelable("infoClientPlace");
                addLoading(unInfoClientPlace, mDataBinding.llUnLoading, 1);
                break;
            case TYPE_MODE:
                // 编辑后的mode
                InfoClientPlace infoClientPlaceEdit = businessFragmentResultEvent.getData().getParcelable("infoClientPlace");
                if (mKeyCurrentLoading != -1) {
                    updateLoading(mHashMapLoading.get(mKeyCurrentLoading), infoClientPlaceEdit);
                }
                if (mKeyCurrentUnLoading != -1) {
                    updateLoading(mHashMapUnLoading.get(mKeyCurrentUnLoading), infoClientPlaceEdit);
                }
                break;
        }
    }

    @Override
    public void setClause(List<String> options1Items) {
        // sheet操作条款
        initSweetSheets(mDataBinding.rlOperationClause.getId(), mDataBinding.flMain, "操作条款", options1Items, (position, menuEntity) -> {
            // 去掉括号
            String title = menuEntity.title.toString().substring(0, menuEntity.title.toString().indexOf("("));
            mDataBinding.tvValueOperationClause.setText(title);
            return true;
        });

        // sheet运输条款
        initSweetSheets(mDataBinding.rlTransportTerms.getId(), mDataBinding.flMain, "运输条款", options1Items, (position, menuEntity) -> {
            // 去掉括号
            String title = menuEntity.title.toString().substring(0, menuEntity.title.toString().indexOf("("));
            mDataBinding.tvValueTransportTerms.setText(title);
            return true;
        });

    }

    @Override
    public void setSonos(BaseResponse<List<InfoContType>> infoContType) {
        List<String> conts = new ArrayList<>(); // 柜型数据
        if (infoContType != null && infoContType.getResult() != null) {
            for (InfoContType item : infoContType.getResult()) {
                conts.add(item.getContChina());
            }
            // 初始化下拉框的数据
            initSweetSheets(mDataBinding.flContainerAdd.getId(), mDataBinding.flMain, "柜型", conts, (position, menuEntity) -> {

                // 判断这些类是否存在，如果存在则不需要添加
                if (!mContainerTypes.contains(menuEntity.title.toString())) {
                    // 不存在就添加
                    mContainerTypes.add(menuEntity.title.toString());
                    // 添加
                    BusinessOrderFragment.ViewHolderContainerCount viewHolderContainerCount = new BusinessOrderFragment.ViewHolderContainerCount(LayoutInflater.from(this.getContext()).inflate(R.layout.item_business_ordre_container_count, mDataBinding.llContainer, false));
                    viewHolderContainerCount.tvTitle.setText(menuEntity.title);
                    // 加减的动作
                    viewHolderContainerCount.imgAddition.setOnClickListener(v -> viewHolderContainerCount.tvValue.setText(ObjectUtils.parseString(ObjectUtils.parseInt(viewHolderContainerCount.tvValue.getText().toString()) + 1)));
                    viewHolderContainerCount.imgSubtraction.setOnClickListener(v -> viewHolderContainerCount.tvValue.setText(ObjectUtils.parseString(ObjectUtils.parseInt(viewHolderContainerCount.tvValue.getText().toString()) - 1)));
                    viewHolderContainerCount.imgDelete.setOnClickListener(v -> {
                        mDataBinding.llContainer.removeView(viewHolderContainerCount.rootView);
                        for (int i = 0; i < mContainerTypes.size(); i++) {
                            if (mContainerTypes.get(i).equals(viewHolderContainerCount.tvTitle.getText().toString())) {
                                mContainerTypes.remove(i);
                            }
                        }
                    });
                    mDataBinding.llContainer.addView(viewHolderContainerCount.rootView);
                } else {
                    Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "已经存在相同柜型", Toast.LENGTH_LONG).show();
                }

                return true;
            });
        }
    }

    /**
     * 通过添加进来的
     *
     * @param infoClientPlace 装卸货地址
     * @param viewGroup       父界面
     * @param type            0 装货 1卸货
     */
    @SuppressLint("SetTextI18n")
    protected void addLoading(InfoClientPlace infoClientPlace, ViewGroup viewGroup, int type) {
        ViewHolderLoadingAndUnloading viewHolderLoadingAndUnloading = new ViewHolderLoadingAndUnloading(LayoutInflater.from(this.getContext()).inflate(R.layout.item_business_order_loading_and_unloading, mDataBinding.llLoading, false));
        // 单位
        viewHolderLoadingAndUnloading.tvValueUnitName.setText(infoClientPlace.getConsigneeCompany());
        // 地址
        viewHolderLoadingAndUnloading.tvValueAddress.setText(infoClientPlace.getRegionid() + infoClientPlace.getAddr());
        // 联系人
        viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingContacts.setText(infoClientPlace.getLinkman());
        // 手机
        viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingMobile.setText(infoClientPlace.getLinkmanMp());
        // 电话
        viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingPhone.setText(infoClientPlace.getLinkmanTel());
        // 传真
        viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingFax.setText(infoClientPlace.getConsigneeCompanyFax());
        // 说明
        viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.setText(infoClientPlace.getZxRemark());
        viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.setTag(ObjectUtils.parseString(infoClientPlace.getZxRemark()));
        // 经纬度
        if (infoClientPlace.getLocationArea() != null) {
            if (infoClientPlace.getLocationArea().getSite() != null) {
                viewHolderLoadingAndUnloading.tvLat.setText(ObjectUtils.parseString(infoClientPlace.getLocationArea().getSite().getLat()));
                viewHolderLoadingAndUnloading.tvLon.setText(ObjectUtils.parseString(infoClientPlace.getLocationArea().getSite().getLon()));
            }
        }

        initLoadingAndUnLoading(type, viewHolderLoadingAndUnloading, viewGroup);

        viewGroup.addView(viewHolderLoadingAndUnloading.rootView);
    }

    /**
     * 更新数据
     *
     * @param view            view
     * @param infoClientPlace 实体
     */
    protected void updateLoading(View view, InfoClientPlace infoClientPlace) {
        ViewHolderLoadingAndUnloading viewHolderLoadingAndUnloading = new ViewHolderLoadingAndUnloading(view);
        // 单位
        viewHolderLoadingAndUnloading.tvValueUnitName.setText(infoClientPlace.getConsigneeCompany());
        // 地址
        viewHolderLoadingAndUnloading.tvValueAddress.setText(infoClientPlace.getRegionid() + infoClientPlace.getAddr());
        // 联系人
        viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingContacts.setText(infoClientPlace.getLinkman());
        // 手机
        viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingMobile.setText(infoClientPlace.getLinkmanMp());
        // 电话
        viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingPhone.setText(infoClientPlace.getLinkmanTel());
        // 传真
        viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingFax.setText(infoClientPlace.getConsigneeCompanyFax());
        // 说明
        viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.setText(infoClientPlace.getZxRemark());
        viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.setTag(ObjectUtils.parseString(infoClientPlace.getZxRemark()));
        // 经纬度
        if (infoClientPlace.getLocationArea() != null) {
            if (infoClientPlace.getLocationArea().getSite() != null) {
                viewHolderLoadingAndUnloading.tvLat.setText(ObjectUtils.parseString(infoClientPlace.getLocationArea().getSite().getLat()));
                viewHolderLoadingAndUnloading.tvLon.setText(ObjectUtils.parseString(infoClientPlace.getLocationArea().getSite().getLon()));
            }
        }
    }

    /**
     * 初始化其他东西：例如缓存当前数据，添加事件
     *
     * @param type                          0 装货 1卸货
     * @param viewHolderLoadingAndUnloading view实体
     * @param viewGroup                     父界面
     */
    protected void initLoadingAndUnLoading(int type, ViewHolderLoadingAndUnloading viewHolderLoadingAndUnloading, ViewGroup viewGroup) {
        if (type == 0) {
            mKeyLoading = mKeyLoading + 1;
            mHashMapLoading.put(mKeyLoading, viewHolderLoadingAndUnloading.rootView);
            checkLoadingView();
            viewHolderLoadingAndUnloading.tvLoadingAndUnloadingDelete.setTag(mKeyLoading);
            viewHolderLoadingAndUnloading.tvLoadingAndUnloadingEdit.setTag(mKeyLoading);
            // 事件
            viewHolderLoadingAndUnloading.tvLoadingAndUnloadingDelete.setOnClickListener(v -> {
                mLoadingOrUnLoadingView = v;
                mLoadingOrUnLoadingViewGroup = viewGroup;
                if (!TextUtils.isEmpty(viewHolderLoadingAndUnloading.tvId.getText().toString()) && !TextUtils.isEmpty(viewHolderLoadingAndUnloading.tvBillsId.getText().toString())) {
                    mPresenter.deleteLoadAddress(viewHolderLoadingAndUnloading.tvId.getText().toString(), viewHolderLoadingAndUnloading.tvBillsId.getText().toString());
                } else {
                    deleteLoadAddress();
                }
            });
        } else {
            mKeyUnLoading = mKeyUnLoading + 1;
            mHashMapUnLoading.put(mKeyUnLoading, viewHolderLoadingAndUnloading.rootView);
            checkUnLoadingView();
            viewHolderLoadingAndUnloading.tvLoadingAndUnloadingDelete.setTag(mKeyUnLoading);
            viewHolderLoadingAndUnloading.tvLoadingAndUnloadingEdit.setTag(mKeyUnLoading);
            // 事件
            viewHolderLoadingAndUnloading.tvLoadingAndUnloadingDelete.setOnClickListener(v -> {
                mLoadingOrUnLoadingView = v;
                mLoadingOrUnLoadingViewGroup = viewGroup;
                if (!TextUtils.isEmpty(viewHolderLoadingAndUnloading.tvId.getText().toString()) && !TextUtils.isEmpty(viewHolderLoadingAndUnloading.tvBillsId.getText().toString())) {
                    mPresenter.deleteUnLoadAddress(viewHolderLoadingAndUnloading.tvId.getText().toString(), viewHolderLoadingAndUnloading.tvBillsId.getText().toString());
                } else {
                    deleteUnLoadAddress();
                }
            });
        }

        viewHolderLoadingAndUnloading.tvLoadingAndUnloadingEdit.setOnClickListener(v -> {
            if (type == 0) {
                mKeyCurrentLoading = ObjectUtils.parseInt(v.getTag());
                mKeyCurrentUnLoading = -1;
            } else {
                mKeyCurrentUnLoading = ObjectUtils.parseInt(v.getTag());
                mKeyCurrentLoading = -1;
            }
            InfoClientPlace infoClientPlace = new InfoClientPlace();
            // 单位名称
            infoClientPlace.setConsigneeCompany(viewHolderLoadingAndUnloading.tvValueUnitName.getText().toString());
            // 地区 和 详细地址
            infoClientPlace.setAddr(viewHolderLoadingAndUnloading.tvValueAddress.getText().toString());
            // 联系人
            infoClientPlace.setLinkman(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingContacts.getText().toString());
            // 手机
            infoClientPlace.setLinkmanMp(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingMobile.getText().toString());
            // 电话
            infoClientPlace.setLinkmanTel(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingPhone.getText().toString());
            // 传真
            infoClientPlace.setConsigneeCompanyFax(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingFax.getText().toString());
            // 说明
            infoClientPlace.setZxRemark(ObjectUtils.parseString(viewHolderLoadingAndUnloading.tvValueLoadingAndUnloadingRemark.getTag()));
            // 经纬度
            LocationArea locationArea = new LocationArea();
            Site site = new Site();
            site.setLon(ObjectUtils.parseDouble(viewHolderLoadingAndUnloading.tvLon.getText().toString()));
            site.setLat(ObjectUtils.parseDouble(viewHolderLoadingAndUnloading.tvLat.getText().toString()));
            locationArea.setSite(site);
            infoClientPlace.setLocationArea(locationArea);
            ((BaseBusinessMainFragment) getParentFragment()).startForResult(LoadingAndUnloadingModelFragment.newInstance("", infoClientPlace, "", type, null, null), TYPE_MODE);
        });

    }

    @Override
    public void deleteLoadAddress() {
        mLoadingOrUnLoadingViewGroup.removeView(mHashMapLoading.get(ObjectUtils.parseInt(mLoadingOrUnLoadingView.getTag())));
        mHashMapLoading.remove(ObjectUtils.parseInt(mLoadingOrUnLoadingView.getTag()));
        checkLoadingView();
    }

    @Override
    public void deleteUnLoadAddress() {
        mLoadingOrUnLoadingViewGroup.removeView(mHashMapUnLoading.get(ObjectUtils.parseInt(mLoadingOrUnLoadingView.getTag())));
        mHashMapUnLoading.remove(ObjectUtils.parseInt(mLoadingOrUnLoadingView.getTag()));
        checkLoadingView();
    }

    /**
     * 检查view是否一个都没有，进行相应的view显示隐藏
     */
    protected void checkLoadingView() {
    }

    /**
     * 检查view是否一个都没有，进行相应的view显示隐藏
     */
    protected void checkUnLoadingView() {
    }

    public static class ViewHolderContainer {
        public View rootView;
        public TextView tvValueCabinetType;
        public TextView tvValueNumber;

        public ViewHolderContainer(View rootView) {
            this.rootView = rootView;
            this.tvValueCabinetType = rootView.findViewById(R.id.tvValueCabinetType);
            this.tvValueNumber = rootView.findViewById(R.id.tvValueNumber);
        }

    }

    public static class ViewHolderLoadingAndUnloading {
        public View rootView;
        public TextView tvValueUnitName;
        public TextView tvValueAddress;
        public TextView tvValueLoadingAndUnloadingContacts;
        public TextView tvValueLoadingAndUnloadingMobile;
        public TextView tvValueLoadingAndUnloadingPhone;
        public TextView tvValueLoadingAndUnloadingFax;
        public ReadMoreTextView tvValueLoadingAndUnloadingRemark;
        public TableLayout tabLoadingAndUnloadingDetail;
        public TextView tvLoadingAndUnloadingDelete;
        public TextView tvLoadingAndUnloadingEdit;
        public RelativeLayout rlLoadingAndUnloadingAction;
        public TextView tvLat;
        public TextView tvLon;
        public TextView tvId;
        public TextView tvBillsId;

        public ViewHolderLoadingAndUnloading(View rootView) {
            this.rootView = rootView;
            this.tvValueUnitName = rootView.findViewById(R.id.tvValueUnitName);
            this.tvValueAddress = rootView.findViewById(R.id.tvValueAddress);
            this.tvValueLoadingAndUnloadingContacts = rootView.findViewById(R.id.tvValueLoadingAndUnloadingContacts);
            this.tvValueLoadingAndUnloadingMobile = rootView.findViewById(R.id.tvValueLoadingAndUnloadingMobile);
            this.tvValueLoadingAndUnloadingPhone = rootView.findViewById(R.id.tvValueLoadingAndUnloadingPhone);
            this.tvValueLoadingAndUnloadingFax = rootView.findViewById(R.id.tvValueLoadingAndUnloadingFax);
            this.tvValueLoadingAndUnloadingRemark = rootView.findViewById(R.id.tvValueLoadingAndUnloadingRemark);
            this.tvValueLoadingAndUnloadingRemark.setTrimCollapsedText("查看更多");
            this.tvValueLoadingAndUnloadingRemark.setTrimExpandedText("隐藏");
            this.tabLoadingAndUnloadingDetail = rootView.findViewById(R.id.tabLoadingAndUnloadingDetail);
            this.tvLoadingAndUnloadingDelete = rootView.findViewById(R.id.tvLoadingAndUnloadingDelete);
            this.tvLoadingAndUnloadingEdit = rootView.findViewById(R.id.tvLoadingAndUnloadingEdit);
            this.rlLoadingAndUnloadingAction = rootView.findViewById(R.id.rlLoadingAndUnloadingAction);
            this.tvLat = rootView.findViewById(R.id.tvLat);
            this.tvLon = rootView.findViewById(R.id.tvLon);
            this.tvId = rootView.findViewById(R.id.tvId);
            this.tvBillsId = rootView.findViewById(R.id.tvBillsId);
        }

    }

    public static class ViewHolderContainerCount {
        public View rootView;
        public TextView tvTitle;
        public ImageView imgMain;
        public Button imgSubtraction;
        public Button imgAddition;
        public EditText tvValue;
        public ImageView imgDelete;

        public ViewHolderContainerCount(View rootView) {
            this.rootView = rootView;
            this.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            this.imgMain = (ImageView) rootView.findViewById(R.id.imgMain);
            this.imgSubtraction = (Button) rootView.findViewById(R.id.imgSubtraction);
            this.imgAddition = (Button) rootView.findViewById(R.id.imgAddition);
            this.tvValue = (EditText) rootView.findViewById(R.id.tvValue);
            this.imgDelete = (ImageView) rootView.findViewById(R.id.imgDelete);
        }

    }

}
