package com.yaoguang.company.businessorder2.forwarder.container.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentBusinessForwarderContainerDetailBinding;
import com.yaoguang.datasource.company.CompanyBaseInfoDataSource;
import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightSono;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.InfoPackType;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.utils.TextViewUtils;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AoHaiUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/11/12.
 */
public class ContainerDetailFragment extends BaseFragmentDataBind<FragmentBusinessForwarderContainerDetailBinding> implements Toolbar.OnMenuItemClickListener {

    /**
     * 从列表传递过来的对象(用于进行修改或者查看)
     */
    public FreightSono mFreightSono;
    /**
     * 更新的索引
     */
    public int mPosition;
    /**
     * 0是可编辑，1是不可编辑
     */
    public int mType;

    DialogHelper mDialogHelper;
    DialogHelper mDialogHelperDelete;

    private CompanyBaseInfoDataSource mCompanyBaseInfoDataSource = new CompanyBaseInfoDataSource();
    private CompanyOrderDataSource mCompanyOrderDataSource = new CompanyOrderDataSource();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mFreightSono = args.getParcelable("freightSono");
            mPosition = args.getInt("position");
            mType = args.getInt("type");
        }
        super.onCreate(savedInstanceState);
    }

    public static ContainerDetailFragment newInstance(FreightSono freightSono, int position, int type) {
        ContainerDetailFragment containerDetailFragment = new ContainerDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("freightSono", freightSono);
        bundle.putInt("position", position);
        bundle.putInt("type", type);
        containerDetailFragment.setArguments(bundle);
        return containerDetailFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_forwarder_container_detail;
    }

    @Override
    public void init() {
        if (mFreightSono != null) {
            if (mType == 1) {
                initToolbarNav(mToolbarCommonBinding.toolbar, "货柜明细", -1, null);

                // 设置禁用
                initEnable();
            } else {
                initToolbarNav(mToolbarCommonBinding.toolbar, "货柜明细", R.menu.menu_container_detail, this);
            }
            initData();
        } else {
            initToolbarNav(mToolbarCommonBinding.toolbar, "货柜明细", -1, null);
        }
        getDataSource();

        // 初始化柜位要求下拉框的数据
        ContainerDetailFragment.this.initSweetSheets(mDataBinding.rlCabinetRequirements.getId(), mDataBinding.flMain, "柜型", R.menu.sheet_container_detail, (position, menuEntity) -> {
            mDataBinding.tvValueCabinetRequirements.setText(menuEntity.title.toString());
            return true;
        });

        //柜号只能输入数字和字母
        TextViewUtils.setAlphaNumeric(mDataBinding.etCabinetNumberValue);
        // 自动小写转大写
        mDataBinding.etCabinetNumberValue.setTransformationMethod(TextViewUtils.replacementTransformationMethod);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 初始化数据，柜型
        mDataBinding.tvValueCabinetType.setText(mFreightSono.getContId());
        // 柜号
        mDataBinding.etCabinetNumberValue.setText(mFreightSono.getContNo());
        if (mType == 1)
            mDataBinding.etCabinetNumberValue.setEnabled(false);
        // 封号
        mDataBinding.etTitleValue.setText(mFreightSono.getSealNo());
        if (mType == 1)
            mDataBinding.etTitleValue.setEnabled(false);
        // 套箱
        mDataBinding.cbTXValue.setChecked(mFreightSono.getTx() == 1);
        if (mType == 1)
            mDataBinding.cbTXValue.setEnabled(false);
        // 扣货
        mDataBinding.cbBuckleValue.setChecked(mFreightSono.getTruckGoodsOr() == 0);
        if (mType == 1)
            mDataBinding.cbBuckleValue.setEnabled(false);
        // 维修
        mDataBinding.cbRepairValue.setChecked(mFreightSono.getIsRepair() == 1);
        if (mType == 1)
            mDataBinding.cbRepairValue.setEnabled(false);
        // 车牌
        mDataBinding.etLicensePlateValue.setText(mFreightSono.getTruckId());
        if (mType == 1)
            mDataBinding.etLicensePlateValue.setEnabled(false);
        // 司机
        mDataBinding.etDriverValue.setText(mFreightSono.getHackman());
        if (mType == 1)
            mDataBinding.etDriverValue.setEnabled(false);
        // 身份证号码
        mDataBinding.etIDCardNoValue.setText(mFreightSono.getRemark5());
        if (mType == 1)
            mDataBinding.etIDCardNoValue.setEnabled(false);
        // 手机
        mDataBinding.etMobileValue.setText(mFreightSono.getHackmanTel());
        if (mType == 1)
            mDataBinding.etMobileValue.setEnabled(false);
        // 车牌2
        mDataBinding.etLicensePlateValue2.setText(mFreightSono.getDestTruckId());
        if (mType == 1)
            mDataBinding.etLicensePlateValue2.setEnabled(false);
        // 司机
        mDataBinding.etDriverValue2.setText(mFreightSono.getDestHackman());
        if (mType == 1)
            mDataBinding.etDriverValue2.setEnabled(false);
        // 身份证号码
        mDataBinding.etIDCardNoValue2.setText(mFreightSono.getDestRemark5());
        if (mType == 1)
            mDataBinding.etIDCardNoValue2.setEnabled(false);
        // 手机
        mDataBinding.etMobileValue2.setText(mFreightSono.getDestHackmanTel());
        if (mType == 1)
            mDataBinding.etMobileValue2.setEnabled(false);
        // 毛重
        mDataBinding.etGrossWeightValue.setText(ObjectUtils.parseString(mFreightSono.getShipperW()));
        if (mType == 1)
            mDataBinding.etGrossWeightValue.setEnabled(false);
        // 净重
        mDataBinding.etNetWeightValue.setText(ObjectUtils.parseString(mFreightSono.getNetWeight()));
        if (mType == 1)
            mDataBinding.etNetWeightValue.setEnabled(false);
        // 体积
        mDataBinding.etVolumeValue.setText(ObjectUtils.parseString(mFreightSono.getW1()));
        if (mType == 1)
            mDataBinding.etVolumeValue.setEnabled(false);
        // 货物价值
        mDataBinding.etValueOfGoodsValue.setText(ObjectUtils.parseString(mFreightSono.getGoodsValue()));
        if (mType == 1)
            mDataBinding.etValueOfGoodsValue.setEnabled(false);
        // 货名
        mDataBinding.etNameGoodsValue.setText(ObjectUtils.parseString(mFreightSono.getGoods()));
        if (mType == 1)
            mDataBinding.etNameGoodsValue.setEnabled(false);
        // 合同号
        mDataBinding.etContractNumberValue.setText(ObjectUtils.parseString(mFreightSono.getContractNo()));
        if (mType == 1)
            mDataBinding.etContractNumberValue.setEnabled(false);
        // 唛头
        mDataBinding.etMarkValue.setText(ObjectUtils.parseString(mFreightSono.getMarkNo()));
        if (mType == 1)
            mDataBinding.etMarkValue.setEnabled(false);
        // 数量
        mDataBinding.etNumberValue3.setText(ObjectUtils.parseString(mFreightSono.getQuantity()));
        if (mType == 1)
            mDataBinding.etNumberValue3.setEnabled(false);
        // 包装
        mDataBinding.tvValuePacking.setText(mFreightSono.getPack());
        // 货物型号
        mDataBinding.etModelOfGoodsValue.setText(mFreightSono.getGoodsSize());
        if (mType == 1)
            mDataBinding.etModelOfGoodsValue.setEnabled(false);
        // 件重
        mDataBinding.etNumberValue2.setText(ObjectUtils.parseString(mFreightSono.getWeight()));
        if (mType == 1)
            mDataBinding.etNumberValue2.setEnabled(false);
        // 货物位置
        mDataBinding.etLocationGoodsValue.setText(mFreightSono.getGoodsPosition());
        if (mType == 1)
            mDataBinding.etLocationGoodsValue.setEnabled(false);
        // 柜位要求
        mDataBinding.tvValueCabinetRequirements.setText(mFreightSono.getContposition());
    }

    /**
     * 设置禁用
     */
    private void initEnable() {
        mDataBinding.btnComit.setVisibility(View.GONE);
    }

    @Override
    public void initListener() {
        mDataBinding.btnComit.setOnClickListener(v -> {
            // 检查柜号是否有效
            if (!TextUtils.isEmpty(mDataBinding.etCabinetNumberValue.getText().toString()) && !AoHaiUtils.checkContNo(mDataBinding.etCabinetNumberValue.getText().toString())) {
                if (mDialogHelper == null)
                    mDialogHelper = new DialogHelper(getContext(), null, mDataBinding.etCabinetNumberValue.getText().toString() + " 是无效柜号,是否重新输入?", "是", "否", new CommonDialog.Listener() {
                        @Override
                        public void ok(String msg) {
                            mDialogHelper.hideDialog();
                            mDataBinding.etCabinetNumberValue.requestFocus();
                        }

                        @Override
                        public void cancel() {
                            mDialogHelper.hideDialog();
                            comfit();
                        }
                    });
                mDialogHelper.show();
            } else {
                comfit();
            }
        });

        mDataBinding.rlCabinetType.setOnClickListener(v -> {
            if (mType == 1)
                return;
            showSweetSheets(mDataBinding.rlCabinetType.getId());
        });

        mDataBinding.rlPacking.setOnClickListener(v -> {
            if (mType == 1)
                return;
            showSweetSheets(mDataBinding.rlPacking.getId());
        });

        mDataBinding.rlCabinetRequirements.setOnClickListener(v -> {
            if (mType == 1)
                return;
            showSweetSheets(mDataBinding.rlCabinetRequirements.getId());
        });
    }

    /**
     * 提交数据
     */
    private void comfit() {
        if (TextUtils.isEmpty(mDataBinding.tvValueCabinetType.getText().toString())) {
            Toast.makeText(BaseApplication.getInstance(), "柜型不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 返回到上一个界面
        if (mFreightSono == null) {
            mFreightSono = new FreightSono();
        }
        // 初始化数据，柜型
        mFreightSono.setContId(mDataBinding.tvValueCabinetType.getText().toString());
        // 柜号
        mFreightSono.setContNo(mDataBinding.etCabinetNumberValue.getText().toString());
        // 封号
        mFreightSono.setSealNo(mDataBinding.etTitleValue.getText().toString());
        // 套箱
        if (mDataBinding.cbTXValue.isChecked()) {
            mFreightSono.setTx(1);
        } else {
            mFreightSono.setTx(0);
        }
        // 扣货
        if (mDataBinding.cbBuckleValue.isChecked()) {
            mFreightSono.setTruckGoodsOr(0);
        } else {
            mFreightSono.setTruckGoodsOr(1);
        }
        // 维修
        if (mDataBinding.cbRepairValue.isChecked()) {
            mFreightSono.setIsRepair(1);
        } else {
            mFreightSono.setIsRepair(0);
        }
        // 车牌
        mFreightSono.setTruckId(mDataBinding.etLicensePlateValue.getText().toString());
        // 司机
        mFreightSono.setHackman(mDataBinding.etDriverValue.getText().toString());
        // 身份证号码
        mFreightSono.setRemark5(mDataBinding.etIDCardNoValue.getText().toString());
        // 手机
        mFreightSono.setHackmanTel(mDataBinding.etMobileValue.getText().toString());
        // 车牌2
        mFreightSono.setDestTruckId(mDataBinding.etLicensePlateValue2.getText().toString());
        // 司机
        mFreightSono.setDestHackman(mDataBinding.etDriverValue2.getText().toString());
        // 身份证号码
        mFreightSono.setDestRemark5(mDataBinding.etIDCardNoValue2.getText().toString());
        // 手机
        mFreightSono.setDestHackmanTel(mDataBinding.etMobileValue2.getText().toString());
        // 毛重
        mFreightSono.setShipperW(ObjectUtils.parseDouble(mDataBinding.etGrossWeightValue.getText().toString()));
        // 净重
        mFreightSono.setNetWeight(ObjectUtils.parseDouble(mDataBinding.etNetWeightValue.getText().toString()));
        // 体积
        mFreightSono.setW1(ObjectUtils.parseDouble(mDataBinding.etVolumeValue.getText().toString()));
        // 货物价值
        mFreightSono.setGoodsValue(ObjectUtils.parseDouble(mDataBinding.etValueOfGoodsValue.getText().toString()));
        // 货名
        mFreightSono.setGoods(mDataBinding.etNameGoodsValue.getText().toString());
        // 合同号
        mFreightSono.setContractNo(mDataBinding.etContractNumberValue.getText().toString());
        // 唛头
        mFreightSono.setMarkNo(mDataBinding.etMarkValue.getText().toString());
        // 数量
        mFreightSono.setQuantity(ObjectUtils.parseDouble(mDataBinding.etNumberValue3.getText().toString()));
        // 包装
        mFreightSono.setPack(mDataBinding.tvValuePacking.getText().toString());
        // 货物型号
        mFreightSono.setGoodsSize(mDataBinding.etModelOfGoodsValue.getText().toString());
        // 件重
        mFreightSono.setWeight(ObjectUtils.parseDouble(mDataBinding.etNumberValue2.getText().toString()));
        // 货物位置
        mFreightSono.setGoodsPosition(mDataBinding.etLocationGoodsValue.getText().toString());
        // 柜位要求
        mFreightSono.setContposition(mDataBinding.tvValueCabinetRequirements.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putParcelable("freightSono", mFreightSono);
        bundle.putInt("position", mPosition);
        setFragmentResult(RESULT_OK, bundle);
        pop();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_delete:
                if (mDialogHelperDelete == null)
                    mDialogHelperDelete = new DialogHelper(getContext(), "提示", "是否删除?", "是的", "我再想想", false, new CommonDialog.Listener() {
                        @Override
                        public void ok(String content) {
                            // 检查两个id是否为空
                            if (!TextUtils.isEmpty(mFreightSono.getId()) && !TextUtils.isEmpty(mFreightSono.getBillsId())) {
                                // 因为这个有数据，所以确定是编辑的
                                // 如果是货代
                                mCompanyOrderDataSource.forwardSonoDelete(mFreightSono.getId(), mFreightSono.getBillsId())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, ContainerDetailFragment.this) {

                                            @Override
                                            public void onSuccess(BaseResponse<String> response) {
                                                Bundle bundle = new Bundle();
                                                bundle.putParcelable("freightSono", null);
                                                bundle.putInt("position", mPosition);
                                                setFragmentResult(RESULT_OK, bundle);
                                                pop();
                                            }

                                            @Override
                                            public void onFail(BaseResponse<String> response) {
                                                super.onFail(response);
                                            }
                                        });
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("freightSono", null);
                                bundle.putInt("position", mPosition);
                                setFragmentResult(RESULT_OK, bundle);
                                pop();
                            }

                        }

                        @Override
                        public void cancel() {

                        }
                    });
                mDialogHelperDelete.show();
                break;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    /**
     * 获取数据
     */
    private void getDataSource() {
        mCompanyBaseInfoDataSource.getConts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<InfoContType>>>(mCompositeDisposable, this) {
                    @Override
                    public void onSuccess(BaseResponse<List<InfoContType>> infoContType) {
                        List<String> conts = new ArrayList<>(); // 柜型数据
                        if (infoContType != null && infoContType.getResult() != null) {
                            for (InfoContType item : infoContType.getResult()) {
                                conts.add(item.getContChina());
                            }
                            // 初始化下拉框的数据
                            ContainerDetailFragment.this.initSweetSheets(mDataBinding.rlCabinetType.getId(), mDataBinding.flMain, "柜型", conts, (position, menuEntity) -> {
                                mDataBinding.tvValueCabinetType.setText(menuEntity.title.toString());
                                return true;
                            });
                        }
                    }
                });

        mCompanyBaseInfoDataSource.packType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<InfoPackType>>>(mCompositeDisposable, this) {
                    @Override
                    public void onSuccess(BaseResponse<List<InfoPackType>> infoPackType) {
                        List<String> conts = new ArrayList<>(); // 柜型数据
                        if (infoPackType != null && infoPackType.getResult() != null) {
                            for (InfoPackType item : infoPackType.getResult()) {
                                conts.add(item.getTypeChniese());
                            }
                            // 初始化下拉框的数据
                            ContainerDetailFragment.this.initSweetSheets(mDataBinding.rlPacking.getId(), mDataBinding.flMain, "包装", conts, (position, menuEntity) -> {
                                mDataBinding.tvValuePacking.setText(menuEntity.title.toString());
                                return true;
                            });
                        }
                    }
                });
    }

}
