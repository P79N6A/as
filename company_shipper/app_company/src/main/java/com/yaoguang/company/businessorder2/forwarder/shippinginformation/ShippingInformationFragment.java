package com.yaoguang.company.businessorder2.forwarder.shippinginformation;

import android.os.Bundle;
import android.text.TextUtils;

import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search2.SearchFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentShippingInformationBinding;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightShip;
import com.yaoguang.lib.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.lib.appcommon.widget.date.TimePickerFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.map.common.ToastUtil;

/**
 * 船运信息
 * Created by zhongjh on 2018/10/30.
 */
public class ShippingInformationFragment extends BaseFragmentDataBind<FragmentShippingInformationBinding> {

    public static final int TYPE_SHIP_COMPANY10 = PublicSearchInteractorImpl.TYPE_SHIP_COMPANY * 10;
    public static final int TYPE_SHIP_COMPANY100 = PublicSearchInteractorImpl.TYPE_SHIP_COMPANY * 100;
    public static final String FREIGHT_SHIP = "freightShip";// 存储数据的常量
    public String id;   // id
    FreightShip freightShip;

    public static ShippingInformationFragment newInstance(FreightShip freightShip) {
        ShippingInformationFragment shippingInformationFragment = new ShippingInformationFragment();
        Bundle bundle = new Bundle();
        if (freightShip != null) {
            bundle.putParcelable(FREIGHT_SHIP, freightShip);
        }
        shippingInformationFragment.setArguments(bundle);
        return shippingInformationFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shipping_information;
    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "更多船运信息", -1, null);
        }

        if (getArguments() != null) {
            freightShip = getArguments().getParcelable(FREIGHT_SHIP);
            if (freightShip != null) {
                mDataBinding.tvValueShipName.setText(freightShip.getSecondVessel());
                mDataBinding.etLineVoyage.setText(freightShip.getSecondVoyage());
                mDataBinding.tvValueInsuranceTime.setText(freightShip.getSecondEtd());
                mDataBinding.tvValueBranchMaturity.setText(freightShip.getSecondEta());
                mDataBinding.tvValueThreeShipName.setText(freightShip.getThirdVessel());
                mDataBinding.etThreeVoyage.setText(freightShip.getThirdVoyage());
                mDataBinding.tvValueThreeWaySailingPeriod.setText(freightShip.getThirdEtd());
                mDataBinding.tvValueBargeCompany.setText(freightShip.getFeederCompany());
            }
        }
    }

    @Override
    public void initListener() {
        // 确定
        mDataBinding.btnComit.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            if (freightShip == null)
                freightShip = new FreightShip();
            freightShip.setSecondVessel(mDataBinding.tvValueShipName.getText().toString());
            freightShip.setSecondVoyage(mDataBinding.etLineVoyage.getText().toString());
            freightShip.setSecondEtd(mDataBinding.tvValueInsuranceTime.getText().toString());
            freightShip.setSecondEta(mDataBinding.tvValueBranchMaturity.getText().toString());
            freightShip.setThirdVessel(mDataBinding.tvValueThreeShipName.getText().toString());
            freightShip.setThirdVoyage(mDataBinding.etThreeVoyage.getText().toString());
            freightShip.setThirdEtd(mDataBinding.tvValueThreeWaySailingPeriod.getText().toString());
            freightShip.setFeederCompany(mDataBinding.tvValueBargeCompany.getText().toString());
            bundle.putParcelable(FREIGHT_SHIP, freightShip);
            setFragmentResult(RESULT_OK, bundle);
            pop();
        });

        // 支线船名
        mDataBinding.rlShipName.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_USER_INFO_SHIP);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_USER_INFO_SHIP));
        });

        // 支线中转站
        mDataBinding.rlInsuranceTime.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "rlInsuranceTime");
            Bundle args = new Bundle();
            args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
            dateBeginPickerFragment.setArguments(args);
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("rlInsuranceTime" + TimePickerFragment.TAGTIME)) {
                    mDataBinding.tvValueInsuranceTime.setText(data);
                }
            });
        });

        // 支线到期
        mDataBinding.rlBranchMaturity.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "rlBranchMaturity");
            Bundle args = new Bundle();
            args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
            dateBeginPickerFragment.setArguments(args);
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("rlBranchMaturity" + TimePickerFragment.TAGTIME)) {
                    mDataBinding.tvValueBranchMaturity.setText(data);
                }
            });
        });

        // 三程船名
        mDataBinding.rlThreeShipName.setOnClickListener(v -> {
            // 判断物流端或者货主端
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY);
            } else {
                if (TextUtils.isEmpty(id)) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "没有选择货代公司");
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY, id);
            }
            startForResult(fragment, TYPE_SHIP_COMPANY10);
        });

        // 三程开船期
        mDataBinding.rlThreeWaySailingPeriod.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "rlThreeVoyage");
            Bundle args = new Bundle();
            args.putBoolean(DatePickerFragment.ISSHOWTIME, true);
            dateBeginPickerFragment.setArguments(args);
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("rlThreeVoyage" + TimePickerFragment.TAGTIME)) {
                    mDataBinding.tvValueThreeWaySailingPeriod.setText(data);
                }
            });
        });

        // 驳船公司
        mDataBinding.rlBargeCompany.setOnClickListener(v -> {
            // 判断物流端或者货主端
            SearchFragment fragment;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY);
            } else {
                if (TextUtils.isEmpty(id)) {
                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "没有选择货代公司");
                    return;
                }
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_SHIP_COMPANY, id);
            }
            startForResult(fragment,TYPE_SHIP_COMPANY100);
        });

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_USER_INFO_SHIP:
                    // 支线船名
                    mDataBinding.tvValueShipName.setText(data.getString("name"));
                    break;
                case TYPE_SHIP_COMPANY10:
                    // 三程船名
                    mDataBinding.tvValueThreeShipName.setText(data.getString("name"));
                    break;
                case TYPE_SHIP_COMPANY100:
                    // 驳船公司
                    mDataBinding.tvValueBargeCompany.setText(data.getString("name"));
                    break;
            }
        }
    }

}
