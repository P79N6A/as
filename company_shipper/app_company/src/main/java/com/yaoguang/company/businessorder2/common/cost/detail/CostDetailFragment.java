package com.yaoguang.company.businessorder2.common.cost.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.yaoguang.appcommon.search2.SearchFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentBusinessCostDetailBinding;
import com.yaoguang.datasource.company.CompanyBaseInfoDataSource;
import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.greendao.entity.InfoContType;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.AccountFee;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.databinding.WidgetNumberTextviewBinding;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl.TYPE_FEE_TYPES;
import static com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER;

/**
 * 费用明细
 * Created by zhongjh on 2018/11/13.
 */
public class CostDetailFragment extends BaseFragmentDataBind<FragmentBusinessCostDetailBinding> implements Toolbar.OnMenuItemClickListener {

    CompanyOrderDataSource mCompanyOrderDataSource = new CompanyOrderDataSource();

    /**
     * 从列表传递过来的对象(用于进行修改或者查看)
     */
    public AccountFee mAccountFee;
    /**
     * 更新的索引
     */
    public int mPosition;
    private int mBillType;

    private String mNumberValue; // 数量

    DialogHelper mDialogHelper;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private CompanyBaseInfoDataSource mCompanyBaseInfoDataSource = new CompanyBaseInfoDataSource();

    WidgetNumberTextviewBinding mWidgetNumberTextviewBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mAccountFee = args.getParcelable("accountFee");
            mPosition = args.getInt("position");
            mBillType = ObjectUtils.parseInt(args.getString("billType"));
            mNumberValue = args.getString("numberValue");
        }
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
    }

    public static CostDetailFragment newInstance(AccountFee accountFee, int position, String billType, String numberValue) {
        CostDetailFragment costDetailFragment = new CostDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("accountFee", accountFee);
        bundle.putInt("position", position);
        bundle.putString("billType", billType);
        bundle.putString("numberValue", numberValue);
        costDetailFragment.setArguments(bundle);
        return costDetailFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_cost_detail;
    }

    @Override
    public void init() {
        mWidgetNumberTextviewBinding = DataBindingUtil.bind(mDataBinding.getRoot().findViewById(R.id.includeWidgetNumberTextView));
        if (mAccountFee != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "编辑费用", R.menu.menu_cost_detail, this);
            initData();
        } else {
            initToolbarNav(mToolbarCommonBinding.toolbar, "新增费用", -1, null);
            // 数量
            mWidgetNumberTextviewBinding.tvValue.setText(mNumberValue);
        }
        getDataSource();

        initSweetSheets(mDataBinding.rlType.getId(), mDataBinding.flMain, "类型", R.menu.sheet_cost_detail, (position, menuEntity) -> {
            mDataBinding.tvValueType.setText(menuEntity.title.toString());
            return true;
        });

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
                            initSweetSheets(mDataBinding.rlUnit.getId(), mDataBinding.flMain, "柜型", conts, (position, menuEntity) -> {
                                mDataBinding.tvValueUnit.setText(menuEntity.title.toString());
                                return true;
                            });
                        }
                    }
                });

    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 应收/应付
        mDataBinding.tvValueType.setText(mAccountFee.getAccoAttr() == 0 ? "应收" : "应付");
        // 核算对象
        mDataBinding.tvValueAccounting.setText(ObjectUtils.parseString(mAccountFee.getShipper()));
        // 费用项目
        mDataBinding.tvValueProject.setText(ObjectUtils.parseString(mAccountFee.getFeeId()));
        // 计量单位
        mDataBinding.tvValueUnit.setText(ObjectUtils.parseString(mAccountFee.getUnit()));
        // 数量
        mWidgetNumberTextviewBinding.tvValue.setText(ObjectUtils.parseString(mAccountFee.getQuantity()));
        // 单价(元)
        mDataBinding.etUnitPriceValue.setText(ObjectUtils.parseString(mAccountFee.getPrice()));
        // 总金额(元)
        mDataBinding.etTotalAmountValue.setText(ObjectUtils.parseString(mAccountFee.getTotalup()));
    }

    @Override
    public void initListener() {
        // 应收应付
        mDataBinding.rlType.setOnClickListener(v -> showSweetSheets(mDataBinding.rlType.getId()));
        // 费用核算对象
        mDataBinding.rlAccounting.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(TYPE_INFOCLIENTMANAGER);
            startForResult(fragment, ObjectUtils.parseInt(TYPE_INFOCLIENTMANAGER));
        });
        // 费用项目
        mDataBinding.rlProject.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(TYPE_FEE_TYPES);
            startForResult(fragment, ObjectUtils.parseInt(TYPE_FEE_TYPES));
        });
        // 计量单位
        mDataBinding.rlUnit.setOnClickListener(v -> showSweetSheets(mDataBinding.rlUnit.getId()));

        // 自动计算金额
        mWidgetNumberTextviewBinding.tvValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mDataBinding.etTotalAmountValue.setText(ObjectUtils.parseString(ObjectUtils.parseDouble(mWidgetNumberTextviewBinding.tvValue.getText().toString()) * ObjectUtils.parseDouble(mDataBinding.etUnitPriceValue.getText().toString())));
            }
        });
        mDataBinding.etUnitPriceValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mDataBinding.etTotalAmountValue.setText(ObjectUtils.parseString(ObjectUtils.parseDouble(mWidgetNumberTextviewBinding.tvValue.getText().toString()) * ObjectUtils.parseDouble(mDataBinding.etUnitPriceValue.getText().toString())));
            }
        });

        // 加减的动作
        mWidgetNumberTextviewBinding.imgAddition.setOnClickListener(v -> mWidgetNumberTextviewBinding.tvValue.setText(ObjectUtils.parseString(ObjectUtils.parseInt(mWidgetNumberTextviewBinding.tvValue.getText().toString()) + 1)));
        mWidgetNumberTextviewBinding.imgSubtraction.setOnClickListener(v -> mWidgetNumberTextviewBinding.tvValue.setText(ObjectUtils.parseString(ObjectUtils.parseInt(mWidgetNumberTextviewBinding.tvValue.getText().toString()) - 1)));

        mDataBinding.btnComit.setOnClickListener(v -> {
            // 非空判断
            if (TextUtils.isEmpty(mDataBinding.tvValueAccounting.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), mDataBinding.tvAccountingTitle.getText().toString() + "不能为空", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(mDataBinding.tvValueProject.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), mDataBinding.tvProjectTitle.getText().toString() + "不能为空", Toast.LENGTH_SHORT).show();
            }

            // 返回到上一个界面
            if (mAccountFee == null) {
                mAccountFee = new AccountFee();
            }
            // 应收/应付
            if (mDataBinding.tvValueType.getText().toString().equals("应收")) {
                mAccountFee.setAccoAttr(0);
            } else {
                mAccountFee.setAccoAttr(1);
            }
            // 核算对象
            mAccountFee.setShipper(mDataBinding.tvValueAccounting.getText().toString());
            // 费用项目
            mAccountFee.setFeeId(mDataBinding.tvValueProject.getText().toString());
            // 计量单位
            mAccountFee.setUnit(mDataBinding.tvValueUnit.getText().toString());
            // 数量
            mAccountFee.setQuantity(ObjectUtils.parseDouble(mWidgetNumberTextviewBinding.tvValue.getText().toString()));
            // 单价(元)
            mAccountFee.setPrice(ObjectUtils.parseDouble(mDataBinding.etUnitPriceValue.getText().toString()));
            // 总金额(元)
            mAccountFee.setTotalup(ObjectUtils.parseDouble(mDataBinding.etTotalAmountValue.getText().toString()));
            // 类型
            mAccountFee.setBillType(mBillType);
            Bundle bundle = new Bundle();
            bundle.putParcelable("accountFee", mAccountFee);
            bundle.putInt("position", mPosition);
            setFragmentResult(RESULT_OK, bundle);
            pop();
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_delete:
                if (mDialogHelper == null)
                    mDialogHelper = new DialogHelper(getContext(), "提示", "是否删除?", "是的", "我再想想", false, new CommonDialog.Listener() {
                        @Override
                        public void ok(String content) {
                            mCompanyOrderDataSource.feeDelete(mAccountFee.getId(), mAccountFee.getBillsId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, CostDetailFragment.this) {

                                        @Override
                                        public void onSuccess(BaseResponse<String> response) {
                                            Bundle bundle = new Bundle();
                                            bundle.putParcelable("freightSono", null);
                                            bundle.putInt("position", mPosition);
                                            setFragmentResult(RESULT_OK, bundle);
                                            pop();
                                        }

                                    });
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                mDialogHelper.show();
                break;
        }
        return false;
    }

    /**
     * 获取数据
     */
    private void getDataSource() {


    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TYPE_INFOCLIENTMANAGER:
                    mDataBinding.tvValueAccounting.setText(data.getString("name"));
                    break;
                case TYPE_FEE_TYPES:
                    mDataBinding.tvValueProject.setText(data.getString("name"));
                    break;
            }
        }
    }

}
