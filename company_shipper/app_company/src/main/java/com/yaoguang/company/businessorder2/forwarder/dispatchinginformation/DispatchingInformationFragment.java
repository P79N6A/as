package com.yaoguang.company.businessorder2.forwarder.dispatchinginformation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search2.SearchFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentForwarderDispatchingInformationBinding;
import com.yaoguang.datasource.company.CompanyBaseInfoDataSource;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightTruck;
import com.yaoguang.greendao.entity.company.InfoClientBookingconsignee;
import com.yaoguang.lib.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.lib.appcommon.widget.date.TimePickerFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 派车信息
 * Created by zhongjh on 2018/11/8.
 */
public class DispatchingInformationFragment extends BaseFragmentDataBind<FragmentForwarderDispatchingInformationBinding> {

    public static final String FREIGH_TTRUCK = "freightTruck";// 存储数据的常量

    FreightTruck mFreightTruck = new FreightTruck();
    int mType;

    SweetSheet sweetSheetPriority; // 优先级

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public static DispatchingInformationFragment newInstance(FreightTruck freightTruck, int type) {
        DispatchingInformationFragment dispatchingInformationFragment = new DispatchingInformationFragment();
        Bundle bundle = new Bundle();
        if (freightTruck != null) {
            bundle.putParcelable(FREIGH_TTRUCK, freightTruck);
        }
        bundle.putInt("type", type);
        dispatchingInformationFragment.setArguments(bundle);
        return dispatchingInformationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mType = args.getInt("type");
            mFreightTruck = args.getParcelable(FREIGH_TTRUCK);
        }
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_forwarder_dispatching_information;
    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            String title = "装货";
            if (mType == 1) {
                title = "卸货";
            }
            initToolbarNav(mToolbarCommonBinding.toolbar, title + "派车信息", -1, null);
        }

        if (mType == 0) {
            mDataBinding.tvBookingAgentTitle.setText("订舱委托人");
            mDataBinding.tvTimeTitle.setText("装货时间");
        } else {
            mDataBinding.tvBookingAgentTitle.setText("订舱收货人");
            mDataBinding.tvTimeTitle.setText("卸货时间");
        }

        if (mFreightTruck != null) {
            if (mType == 0) {
                mDataBinding.tvValuePriority.setText(mFreightTruck.getGoodsNameE());
                mDataBinding.tvValueInsuranceTime.setText(mFreightTruck.getLoadDate());
                mDataBinding.tvValueCompany.setText(mFreightTruck.getPreTruck());
                mDataBinding.tvValueBookingAgent.setText(mFreightTruck.getPreShipCompany());
            } else if (mType == 1) {
                mDataBinding.tvValuePriority.setText(mFreightTruck.getGoodsPriority());
                mDataBinding.tvValueInsuranceTime.setText(mFreightTruck.getReleasePlanDate());
                mDataBinding.tvValueCompany.setText(mFreightTruck.getDestTruck());
                mDataBinding.tvValueBookingAgent.setText(mFreightTruck.getDesShipCompany());
            }
        }

        sweetSheetPriority = new SweetSheet(mDataBinding.flMain);
        if (mType == 0) {
            sweetSheetPriority.setMenuList(R.menu.sheet_business_loading_priority);
        } else {
            sweetSheetPriority.setMenuList(R.menu.sheet_business_unloading_priority);
        }
        sweetSheetPriority.setTitle("优先级");
        RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
        sweetSheetPriority.setDelegate(recyclerViewDelegate);
        sweetSheetPriority.setBackgroundEffect(new DimEffect(4));
        sweetSheetPriority.setOnMenuItemClickListener((position, menuEntity1) -> {
            mDataBinding.tvValuePriority.setText(menuEntity1.title.toString());
            return true;
        });


    }

    @Override
    public void initListener() {
        // 确定
        mDataBinding.btnComit.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            if (mFreightTruck == null)
                mFreightTruck = new FreightTruck();
            if (mType == 0) {
                mFreightTruck.setGoodsNameE(mDataBinding.tvValuePriority.getText().toString());
                mFreightTruck.setLoadDate(mDataBinding.tvValueInsuranceTime.getText().toString());
                mFreightTruck.setPreTruck(mDataBinding.tvValueCompany.getText().toString());
                mFreightTruck.setPreShipCompany(mDataBinding.tvValueBookingAgent.getText().toString());
            } else if (mType == 1) {
                mFreightTruck.setGoodsPriority(mDataBinding.tvValuePriority.getText().toString());
                mFreightTruck.setReleasePlanDate(mDataBinding.tvValueInsuranceTime.getText().toString());
                mFreightTruck.setDestTruck(mDataBinding.tvValueCompany.getText().toString());
                mFreightTruck.setDesShipCompany(mDataBinding.tvValueBookingAgent.getText().toString());
            }
            bundle.putParcelable(FREIGH_TTRUCK, mFreightTruck);
            setFragmentResult(RESULT_OK, bundle);
            pop();
        });

        // 优先级
        mDataBinding.rlPriority.setOnClickListener(v -> sweetSheetPriority.show());

        // 时间
        mDataBinding.rlTime.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "rlTime");
            Bundle args = new Bundle();
            args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
            dateBeginPickerFragment.setArguments(args);
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("rlTime" + TimePickerFragment.TAGTIME)) {
                    mDataBinding.tvValueInsuranceTime.setText(data);
                }
            });
        });

        // 拖车公司
        mDataBinding.rlCompany.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_TRUCK);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_TRUCK));
        });

        // 订舱委托人
        if (mType == 0) {
            mDataBinding.rlBookingAgent.setOnClickListener(v -> {
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY));
            });
        } else {
            showSweetSheets(mDataBinding.rlBookingAgent.getId());
        }


    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_TRUCK:
                    // 拖车公司
                    mDataBinding.tvValueCompany.setText(data.getString("name"));
                    if (mType == 1) {
                        // 订舱收货人 根据拖车公司来
                        new CompanyBaseInfoDataSource().searchInfoClientBookingconsignees(data.getString("name"))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new BaseObserver<BaseResponse<List<InfoClientBookingconsignee>>>(mCompositeDisposable, this) {
                                    @Override
                                    public void onSuccess(BaseResponse<List<InfoClientBookingconsignee>> response) {
                                        if (response.getState().equals("200")) {
                                            if (response.getResult().size() <= 0){
                                                Toast.makeText(BaseApplication.getInstance(),"该拖车公司的订舱收货人没有数据",Toast.LENGTH_LONG).show();
                                            }
                                            List<String> strings = new ArrayList<>();
                                            for (InfoClientBookingconsignee infoClientBookingconsignee : response.getResult()) {
                                                strings.add(infoClientBookingconsignee.getCompanyname());
                                            }
                                            // 初始化下拉框的数据
                                            initSweetSheets(mDataBinding.rlBookingAgent.getId(), mDataBinding.flMain, "订舱收货人", strings, (position, menuEntity) -> {
                                                mDataBinding.tvValueBookingAgent.setText(menuEntity.title.toString());
                                                return false;
                                            });
                                        }
                                    }
                                });
                    }
                    break;
                case PublicSearchInteractorImpl.TYPE_SHIP_COMPANY:
                    // 订舱送仓：驳船公司
                    mDataBinding.tvValueBookingAgent.setText(data.getString("name"));
                    break;
            }
        }
    }

}
