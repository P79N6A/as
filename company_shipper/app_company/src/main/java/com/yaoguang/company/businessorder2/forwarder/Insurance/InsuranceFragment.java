package com.yaoguang.company.businessorder2.forwarder.Insurance;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search2.SearchFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentInsuranceBinding;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightInsurance;
import com.yaoguang.lib.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * 保险信息
 * Created by zhongjh on 2018/10/29.
 */
public class InsuranceFragment extends BaseFragmentDataBind<FragmentInsuranceBinding> {

    public static final String INSURANCE = "Insurance";// 存储数据的常量
    FreightInsurance mFreightInsurance;

    public static InsuranceFragment newInstance(FreightInsurance freightInsurance) {
        InsuranceFragment insuranceFragment = new InsuranceFragment();
        Bundle bundle = new Bundle();
        if (freightInsurance != null) {
            bundle.putParcelable(INSURANCE, freightInsurance);
        }
        insuranceFragment.setArguments(bundle);
        return insuranceFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_insurance;
    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "保险明细", -1, null);
        }

        initSweetSheets(mDataBinding.rlInsuranceType.getId(), mDataBinding.flMain, "保险类型", R.menu.sheet_business_insurance, new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity) {
                mDataBinding.tvValueInsuranceType.setText(menuEntity.title.toString());
                return false;
            }
        });

        if (getArguments() != null) {
            mFreightInsurance = getArguments().getParcelable(INSURANCE);
            if (mFreightInsurance != null) {
                mDataBinding.tvValueInsuranceCompany.setText(mFreightInsurance.getAgency());
                mDataBinding.etConsigneeIdonsigneeId.setText(mFreightInsurance.gethBlNo());
                mDataBinding.tvValueInsuranceType.setText(mFreightInsurance.getInsurType());
                mDataBinding.tvValueInsuranceTime.setText(mFreightInsurance.getInstoreStatusDate());
                mDataBinding.etThousands.setText(mFreightInsurance.getInsurValue());
                mDataBinding.etRate.setText(mFreightInsurance.getInsurRate());
                mDataBinding.etInsurancePremium.setText(mFreightInsurance.getInsurMoney());
            }
        }

    }

    @Override
    public void initListener() {
        // 保险公司
        mDataBinding.rlInsuranceCompany.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INSURER_COMPANY);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INSURER_COMPANY));
        });

        // 保险类型
        mDataBinding.rlInsuranceType.setOnClickListener(v -> showSweetSheets(mDataBinding.rlInsuranceType.getId()));

        // 投保时间
        mDataBinding.rlInsuranceTime.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.show(getFragmentManager(), "InsuranceTime");
            dateBeginPickerFragment.setComeBack((data, tag) -> {
                if (tag.equals("InsuranceTime")) {
                    mDataBinding.tvValueInsuranceTime.setText(data);
                }
            });
        });

        // 自动计算
        mDataBinding.etThousands.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mDataBinding.etInsurancePremium.setText(String.format("%.2f",ObjectUtils.parseDouble(mDataBinding.etThousands.getText().toString()) * ObjectUtils.parseDouble(mDataBinding.etRate.getText().toString())));
            }
        });
        mDataBinding.etRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mDataBinding.etInsurancePremium.setText(String.format("%.2f",ObjectUtils.parseDouble(mDataBinding.etThousands.getText().toString()) * ObjectUtils.parseDouble(mDataBinding.etRate.getText().toString())));
            }
        });

        // 确定
        mDataBinding.btnComit.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            if (mFreightInsurance == null)
                mFreightInsurance = new FreightInsurance();
            mFreightInsurance.setAgency(mDataBinding.tvValueInsuranceCompany.getText().toString());
            mFreightInsurance.sethBlNo(mDataBinding.etConsigneeIdonsigneeId.getText().toString());
            mFreightInsurance.setInsurType(mDataBinding.tvValueInsuranceType.getText().toString());
            mFreightInsurance.setInstoreStatusDate(mDataBinding.tvValueInsuranceTime.getText().toString());
            mFreightInsurance.setInsurValue(mDataBinding.etThousands.getText().toString());
            mFreightInsurance.setInsurRate(mDataBinding.etRate.getText().toString());
            mFreightInsurance.setInsurMoney(mDataBinding.etInsurancePremium.getText().toString());
            mFreightInsurance.setIsInsurance(1);
            bundle.putParcelable(INSURANCE, mFreightInsurance);
            setFragmentResult(RESULT_OK, bundle);
            pop();
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
                case PublicSearchInteractorImpl.TYPE_INSURER_COMPANY:
                    // 保险公司
                    mDataBinding.tvValueInsuranceCompany.setText(data.getString("name"));
                    break;
            }
        }
    }


}
