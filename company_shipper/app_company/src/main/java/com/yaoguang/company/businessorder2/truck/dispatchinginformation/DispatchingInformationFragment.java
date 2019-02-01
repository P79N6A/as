package com.yaoguang.company.businessorder2.truck.dispatchinginformation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search2.SearchFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentTruckDispatchingInformationBinding;
import com.yaoguang.greendao.entity.TruckGoodsTruck;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 派车信息
 * Created by zhongjh on 2018/11/8.
 */
public class DispatchingInformationFragment extends BaseFragmentDataBind<FragmentTruckDispatchingInformationBinding> {

    public static final String TRUCK_GOODS_TRUCK = "truckGoodsTruck";// 存储数据的常量

    TruckGoodsTruck mTruckGoodsTruck = new TruckGoodsTruck();

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public static DispatchingInformationFragment newInstance(TruckGoodsTruck truckGoodsTruck) {
        DispatchingInformationFragment dispatchingInformationFragment = new DispatchingInformationFragment();
        Bundle bundle = new Bundle();
        if (truckGoodsTruck != null) {
            bundle.putParcelable(TRUCK_GOODS_TRUCK, truckGoodsTruck);
        }
        dispatchingInformationFragment.setArguments(bundle);
        return dispatchingInformationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mTruckGoodsTruck = args.getParcelable(TRUCK_GOODS_TRUCK);
        }
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_truck_dispatching_information;
    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "派车信息", -1, null);
        }

        if (mTruckGoodsTruck != null) {
            // 车牌号
            mDataBinding.tvValueLicensePlateNumber.setText(mTruckGoodsTruck.getPreCarriage());
            // 挂车车牌号
            mDataBinding.etTrailerLicensePlateNumber.setText(mTruckGoodsTruck.getTruckNo());
            // 司机
            mDataBinding.tvValueDriver.setText(mTruckGoodsTruck.getDriverId());
            // 身份证号
            mDataBinding.etIDNumber.setText(mTruckGoodsTruck.getDriverOther());
            // 手机
            mDataBinding.etMobile.setText(mTruckGoodsTruck.getDriverTel());
            // 拖车公司
            mDataBinding.tvValueCompany.setText(mTruckGoodsTruck.getPreTruck());
        }

    }

    @Override
    public void initListener() {
        // 确定
        mDataBinding.btnComit.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            if (mTruckGoodsTruck == null)
                mTruckGoodsTruck = new TruckGoodsTruck();
            // 车牌号
            mTruckGoodsTruck.setPreCarriage(mDataBinding.tvValueLicensePlateNumber.getText().toString());
            // 挂车车牌号
            mTruckGoodsTruck.setTruckNo(mDataBinding.etTrailerLicensePlateNumber.getText().toString());
            // 司机
            mTruckGoodsTruck.setDriverId(mDataBinding.tvValueDriver.getText().toString());
            // 身份证号
            mTruckGoodsTruck.setDriverOther(mDataBinding.etIDNumber.getText().toString());
            // 手机
            mTruckGoodsTruck.setDriverTel(mDataBinding.etMobile.getText().toString());
            // 拖车公司
            mTruckGoodsTruck.setPreTruck(mDataBinding.tvValueCompany.getText().toString());
            bundle.putParcelable(TRUCK_GOODS_TRUCK, mTruckGoodsTruck);
            setFragmentResult(RESULT_OK, bundle);
            pop();
        });
        // 拖车公司
        mDataBinding.rlCompany.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_TRUCK);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_TRUCK));
        });
        mDataBinding.rlLicensePlateNumber.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.SEARCH_INFO_TRUCKNOS);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.SEARCH_INFO_TRUCKNOS));
        });
        mDataBinding.rlDriver.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.SEARCH_INFO_HACKMANS);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.SEARCH_INFO_HACKMANS));
        });

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
                    break;
                case PublicSearchInteractorImpl.SEARCH_INFO_TRUCKNOS:
                    // 查找车牌号，填充数据
                    if (!TextUtils.isEmpty(data.getString("name"))) {
                        String[] trucknos = data.getString("name").split("\\| \\|",7);
                        // 挂车车牌号
                        mDataBinding.etTrailerLicensePlateNumber.setText(ObjectUtils.parseString(trucknos[5]));
                        // 拖车公司
                        mDataBinding.tvValueCompany.setText(ObjectUtils.parseString(trucknos[2]));
                        // 司机
                        mDataBinding.tvValueDriver.setText(ObjectUtils.parseString(trucknos[3]));
                        // 证件号码
                        mDataBinding.etIDNumber.setText(ObjectUtils.parseString(trucknos[6]));
                        // 手机
                        mDataBinding.etMobile.setText(ObjectUtils.parseString(trucknos[4]));
                        // 车牌号
                        mDataBinding.tvValueLicensePlateNumber.setText(data.getString("shortName"));
                    }
                    break;
                case PublicSearchInteractorImpl.SEARCH_INFO_HACKMANS:
                    // 选择司机
                    if (!TextUtils.isEmpty(data.getString("name"))) {
                        String[] trucknos = data.getString("name").split("\\| \\|");
                        // 身份证号
                        mDataBinding.etIDNumber.setText(ObjectUtils.parseString(trucknos[2]));
                        // 手机
                        mDataBinding.etMobile.setText(ObjectUtils.parseString(trucknos[1]));
                        // 拖车公司
                        mDataBinding.tvValueCompany.setText(ObjectUtils.parseString(trucknos[3]));
                        // 司机
                        mDataBinding.tvValueDriver.setText(data.getString("shortName"));
                    }
                    break;
            }
        }
    }

}
