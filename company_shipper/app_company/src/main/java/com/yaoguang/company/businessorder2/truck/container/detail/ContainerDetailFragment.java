package com.yaoguang.company.businessorder2.truck.container.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentBusinessTruckContainerDetailBinding;
import com.yaoguang.datasource.company.CompanyBaseInfoDataSource;
import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.TruckSono;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.InfoPackType;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.utils.TextViewUtils;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AoHaiUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.constants.Constants;
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

public class ContainerDetailFragment extends BaseFragmentDataBind<FragmentBusinessTruckContainerDetailBinding> implements Toolbar.OnMenuItemClickListener {

    /**
     * 从列表传递过来的对象(用于进行修改或者查看)
     */
    public TruckSono mTruckSono;
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
            mTruckSono = args.getParcelable("truckSono");
            mPosition = args.getInt("position");
            mType = args.getInt("type");
        }
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
    }

    public static ContainerDetailFragment newInstance(TruckSono truckSono, int position, int type) {
        ContainerDetailFragment containerDetailFragment = new ContainerDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("truckSono", truckSono);
        bundle.putInt("position", position);
        bundle.putInt("type", type);
        containerDetailFragment.setArguments(bundle);
        return containerDetailFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_truck_container_detail;
    }

    @Override
    public void init() {
        if (mTruckSono != null) {
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
        mDataBinding.tvValueCabinetType.setText(mTruckSono.getContId());
        // 柜号
        mDataBinding.etCabinetNumberValue.setText(mTruckSono.getContNo());
        if (mType == 1)
            mDataBinding.etCabinetNumberValue.setEnabled(false);
        // 封号
        mDataBinding.etTitleValue.setText(mTruckSono.getSealNo());
        if (mType == 1)
            mDataBinding.etTitleValue.setEnabled(false);
        // 提柜码头
        mDataBinding.tvValueCupboardWharf.setText(mTruckSono.getCarryPort());
        // 还柜码头
        mDataBinding.tvValueCounterWharf.setText(mTruckSono.getGetPort());
        // 套箱
        mDataBinding.cbTXValue.setChecked(mTruckSono.getTx() == 1);
        if (mType == 1)
            mDataBinding.cbTXValue.setEnabled(false);
        // 扣货
        mDataBinding.cbBuckleValue.setChecked(mTruckSono.getTruckGoodsOr() == 0);
        if (mType == 1)
            mDataBinding.cbBuckleValue.setEnabled(false);
        // 维修
        mDataBinding.cbRepairValue.setChecked(mTruckSono.getIsRepair() == 1);
        if (mType == 1)
            mDataBinding.cbRepairValue.setEnabled(false);
        // 毛重
        mDataBinding.etGrossWeightValue.setText(ObjectUtils.parseString(mTruckSono.getShipperW()));
        if (mType == 1)
            mDataBinding.etGrossWeightValue.setEnabled(false);
        // 净重
        mDataBinding.etNetWeightValue.setText(ObjectUtils.parseString(mTruckSono.getNetWeight()));
        if (mType == 1)
            mDataBinding.etNetWeightValue.setEnabled(false);
        // 件数
        mDataBinding.etNumberValue3.setText(ObjectUtils.parseString(mTruckSono.getPiecs()));
        if (mType == 1)
            mDataBinding.etNumberValue3.setEnabled(false);
        // 包装
        mDataBinding.tvValuePacking.setText(mTruckSono.getPack());
        // 柜位要求
        mDataBinding.tvValueCabinetRequirements.setText(mTruckSono.getContposition());
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

        // 提柜码头
        // 判断物流端或者货主端
        mDataBinding.rlCupboardWharf.setOnClickListener(v -> {
            if (mType == 1)
                return;
            SearchFragment fragment = null;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFODOCK);
            }
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFODOCK));
        });
        // 还柜码头
        mDataBinding.rlCounterWharf.setOnClickListener(v -> {
            if (mType == 1)
                return;
            SearchFragment fragment = null;
            if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFODOCK);
            }
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFODOCK) + 1000);
        });
    }

    private void comfit() {
        if (TextUtils.isEmpty(mDataBinding.tvValueCabinetType.getText().toString())) {
            Toast.makeText(BaseApplication.getInstance(), "柜型不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 返回到上一个界面
        if (mTruckSono == null) {
            mTruckSono = new TruckSono();
        }
        // 初始化数据，柜型
        mTruckSono.setContId(mDataBinding.tvValueCabinetType.getText().toString());
        // 柜号
        mTruckSono.setContNo(mDataBinding.etCabinetNumberValue.getText().toString());
        // 封号
        mTruckSono.setSealNo(mDataBinding.etTitleValue.getText().toString());
        // 提柜码头
        mTruckSono.setCarryPort(mDataBinding.tvValueCupboardWharf.getText().toString());
        // 还柜码头
        mTruckSono.setGetPort(mDataBinding.tvValueCounterWharf.getText().toString());
        // 套箱
        if (mDataBinding.cbTXValue.isChecked()) {
            mTruckSono.setTx(1);
        } else {
            mTruckSono.setTx(0);
        }
        // 扣货
        if (mDataBinding.cbBuckleValue.isChecked()) {
            mTruckSono.setTruckGoodsOr(0);
        } else {
            mTruckSono.setTruckGoodsOr(1);
        }
        // 维修
        if (mDataBinding.cbRepairValue.isChecked()) {
            mTruckSono.setIsRepair(1);
        } else {
            mTruckSono.setIsRepair(0);
        }
        // 毛重
        mTruckSono.setShipperW(ObjectUtils.parseDouble(mDataBinding.etGrossWeightValue.getText().toString()));
        // 净重
        mTruckSono.setNetWeight(ObjectUtils.parseDouble(mDataBinding.etNetWeightValue.getText().toString()));
        // 件数
        mTruckSono.setPiecs(ObjectUtils.parseInt(mDataBinding.etNumberValue3.getText().toString()));
        // 包装
        mTruckSono.setPack(mDataBinding.tvValuePacking.getText().toString());
        // 柜位要求
        mTruckSono.setContposition(mDataBinding.tvValueCabinetRequirements.getText().toString());

        Bundle bundle = new Bundle();
        bundle.putParcelable("truckSono", mTruckSono);
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
                            if (!TextUtils.isEmpty(mTruckSono.getId()) && !TextUtils.isEmpty(mTruckSono.getBillsId())) {
                                // 因为这个有数据，所以确定是编辑的
                                // 如果是拖车
                                mCompanyOrderDataSource.truckSonoDelete(mTruckSono.getId(), mTruckSono.getBillsId())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, ContainerDetailFragment.this) {

                                            @Override
                                            public void onSuccess(BaseResponse<String> response) {
                                                Bundle bundle = new Bundle();
                                                bundle.putParcelable("truckSono", null);
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
                                bundle.putParcelable("truckSono", null);
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
                        List<String> conts = new ArrayList<>(); // 包装
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

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_INFODOCK:
                    mDataBinding.tvValueCupboardWharf.setText(data.getString("name"));
                    break;
                case (PublicSearchInteractorImpl.TYPE_INFODOCK + 1000):
                    mDataBinding.tvValueCounterWharf.setText(data.getString("name"));
                    break;
            }
        }
    }

}
