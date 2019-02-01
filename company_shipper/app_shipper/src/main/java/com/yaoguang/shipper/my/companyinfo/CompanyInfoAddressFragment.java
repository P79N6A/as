package com.yaoguang.shipper.my.companyinfo;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.yaoguang.datasource.common.ProvinceBeansDataSource;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.entity.ProvinceBeans;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.datasource.shipper.OwnerUserDataSource;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.appcompanyshipper.phone.businessorder.forwarder.business.BaseBusinessOrderContact;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.databinding.FragmentCompanyInfoAddressBinding;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 地址管理
 * Created by zhongjh on 2017/12/19.
 */
public class CompanyInfoAddressFragment extends BaseFragmentDataBind<FragmentCompanyInfoAddressBinding> {

    private OwnerUserDataSource mOwnerUserDataSource = new OwnerUserDataSource();
    private ProvinceBeansDataSource mProvinceBeansDataSource = new ProvinceBeansDataSource();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private ProvinceBeans mProvinceBeans;
    private OptionsPickerView mPVOptions;
    private String provinces;
    private String citys;
    private String areas;

    /**
     * @param userOwner 传递的对象
     */
    public static CompanyInfoAddressFragment newInstance(UserOwner userOwner) {
        Bundle args = new Bundle();
        args.putParcelable("userOwner", userOwner);
        CompanyInfoAddressFragment fragment = new CompanyInfoAddressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_company_info_address;
    }

//    @Override
//    public void initDataBind(View view) {
//        mDataBinding = DataBindingUtil.bind(view);
//        super.initDataBind(view);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public void init() {
        mToolbarCommonBinding.toolbarTitle.setText("设置地址");
        mToolbarCommonBinding.imgReturn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                pop();
            }
        });

        UserOwner userOwner = getArguments().getParcelable("userOwner");
        mDataBinding.setUserOwner(userOwner);

        mProvinceBeansDataSource.analysisProvinceBeansJson(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProvinceBeans>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ProvinceBeans value) {
                        setProvinceBeans(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    @Override
    public void initListener() {
        // 地区选择
        mDataBinding.rlRegion.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mPVOptions.show();
            }
        });
        // 地区改变值
        mDataBinding.tvRegionValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // 详细地址改变值
        mDataBinding.tvAddressValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 提交
        mDataBinding.cpbSubmit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mDataBinding.getUserOwner().setProvince(provinces);
                mDataBinding.getUserOwner().setCity(citys);
                mDataBinding.getUserOwner().setDistrict(areas);
                mDataBinding.getUserOwner().setCompanyAddress(mDataBinding.tvAddressValue.getText().toString());
                mOwnerUserDataSource.updateInfo(mDataBinding.getUserOwner())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, CompanyInfoAddressFragment.this) {

                            @Override
                            public void onSuccess(BaseResponse<String> response) {
                                Bundle bundle = new Bundle();
                                setFragmentResult(RESULT_OK, bundle);
                                CompanyInfoAddressFragment.this.pop();
                                Toast.makeText(BaseApplication.getInstance(), "修改成功", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    /**
     * 赋值城市数据源
     *
     * @param value 数据
     */
    private void setProvinceBeans(ProvinceBeans value) {
        mProvinceBeans = value;
        mPVOptions = new OptionsPickerView.Builder(getContext(), (options1, options2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            String tx = mProvinceBeans.getProvinces().get(options1).getPickerViewText() +
                    mProvinceBeans.getCitys().get(options1).get(options2) +
                    mProvinceBeans.getAreas().get(options1).get(options2).get(options3);
            provinces = mProvinceBeans.getProvinces().get(options1).getPickerViewText();
            citys = mProvinceBeans.getCitys().get(options1).get(options2);
            areas = mProvinceBeans.getAreas().get(options1).get(options2).get(options3);
            mDataBinding.tvRegionValue.setText(tx);
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        mPVOptions.setPicker(mProvinceBeans.getProvinces(), mProvinceBeans.getCitys(), mProvinceBeans.getAreas());//三级选择器
    }

    /**
     * 检查按钮是否需要启用，判断值是否有改变
     */
    private void checkEnable() {
        if (mDataBinding.tvRegionValue.getText().toString().equals(mDataBinding.getUserOwner().getProvince() + mDataBinding.getUserOwner().getCity() + mDataBinding.getUserOwner().getDistrict()) && mDataBinding.tvAddressValue.getText().toString().equals(mDataBinding.getUserOwner().getCompanyAddress())) {
            mDataBinding.cpbSubmit.setEnabled(false);
        } else {
            mDataBinding.cpbSubmit.setEnabled(true);
        }
    }

}
