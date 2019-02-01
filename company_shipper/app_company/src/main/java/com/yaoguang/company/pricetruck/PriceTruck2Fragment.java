package com.yaoguang.company.pricetruck;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentPriceTruckBinding;
import com.yaoguang.company.pricetruck.adapter.PriceTruckAdapter;
import com.yaoguang.greendao.entity.AppPriceTruckWrapper;
import com.yaoguang.greendao.entity.PriceTruckCondition;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import java.util.List;

/**
 * 拖车报价查询 窗体
 * Created by zhongjh on 2017/12/28.
 */
public class PriceTruck2Fragment extends BaseFragmentListConditionDataBind<AppPriceTruckWrapper, PriceTruckCondition,PriceTruckAdapter, FragmentPriceTruckBinding> implements PriceTruckContract.View {

    PriceTruckContract.Presenter mPresenter = new PriceTruckPresenter(this);
    PriceTruckCondition mPriceTruckCondition = new PriceTruckCondition();

    public static PriceTruck2Fragment newInstance() {
        return new PriceTruck2Fragment();
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_price_truck;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new PriceTruckAdapter();
    }

    @Override
    public PriceTruckCondition getCondition(boolean isRegain) {
        if (isRegain) {
            if (mDataBinding != null) {
                mPriceTruckCondition.setPort(mDataBinding.tvValuePort.getText().toString());
                mPriceTruckCondition.setAddress(mDataBinding.tvValueAddress.getText().toString());
                mPriceTruckCondition.setShipper(mDataBinding.tvValueShipper.getText().toString());
                mPriceTruckCondition.setCont(mDataBinding.tvValueCont.getText().toString());
                if (mDataBinding.tvValueServiceType.getText().toString().equals(getResources().getString(R.string.loading_goods))) {
                    mPriceTruckCondition.setServiceType(0);
                } else if (mDataBinding.tvValueServiceType.getText().toString().equals(getResources().getString(R.string.unloading_goods))) {
                    mPriceTruckCondition.setServiceType(1);
                } else {
                    mPriceTruckCondition.setServiceType(null);
                }
            }
        }
        return mPriceTruckCondition;
    }

    @Override
    public void setConditionView(PriceTruckCondition condition) {

    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "拖车报价查询", R.menu.shipschedule, PriceTruck2Fragment.this);
        }
        //绑定数据
        mDataBinding.setPriceTruckCondition(mPriceTruckCondition);

        // 初始化装卸
        initSweetSheets(mDataBinding.tvValueServiceType.getId(), mDataBinding.flMain, "请选择装/卸", R.menu.sheet_service_type, (position, menuEntity) -> {
            //返回的分别是三个级别的选中位置
            mDataBinding.tvValueServiceType.setText(menuEntity.title);
            return true;
        });
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mPresenter.subscribe();
    }

    @Override
    public void initListener() {
        mDataBinding.btnEmpty.setOnClickListener(v -> {
            mDataBinding.tvValueServiceType.setText("");
            mDataBinding.tvValueCont.setText("");
            mDataBinding.tvValuePort.setText("");
            mDataBinding.tvValueAddress.setText("");
            mDataBinding.tvValueShipper.setText("");
        });
        // 选择装卸
        mDataBinding.rlServiceType.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showSweetSheets(mDataBinding.tvValueServiceType.getId());
            }
        });

        // 柜型柜量
        mDataBinding.rlCont.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showSweetSheets(mDataBinding.tvValueCont.getId());
            }
        });

        //码头,就是起运港或者目的港之类的
        mDataBinding.rlPort.setOnClickListener(v -> {
            //判断如果是装货，就起运港，如果是卸货，就目的港
            if (mDataBinding.tvValueServiceType.getText().equals("")) {
                Toast.makeText(BaseApplication.getInstance(), "必须先选择装卸类型才可以选择卸货地区！", Toast.LENGTH_LONG).show();
            } else if (mDataBinding.tvValueServiceType.getText().equals("装货")) {
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT));
            } else {
                SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION));
            }

        });

        //地点,就是起运地
        mDataBinding.rlAddress.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance( PublicSearchInteractorImpl.TYPE_DEPARTURE);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DEPARTURE));
        });
        //托运人
        mDataBinding.rlShipper.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER));
        });

        //清空
        mDataBinding.imgServiceType.setOnClickListener(v -> mDataBinding.tvValueServiceType.setText(""));
        mDataBinding.imgCont.setOnClickListener(v -> mDataBinding.tvValueCont.setText(""));
        mDataBinding.imgPort.setOnClickListener(v -> mDataBinding.tvValuePort.setText(""));
        mDataBinding.imgAddress.setOnClickListener(v -> mDataBinding.tvValueAddress.setText(""));
        mDataBinding.imgShipper.setOnClickListener(v -> mDataBinding.tvValueShipper.setText(""));
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT:
                    mDataBinding.tvValuePort.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION:
                    mDataBinding.tvValuePort.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_DEPARTURE:
                    mDataBinding.tvValueAddress.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_INFOCLIENTMANAGER:
                    mDataBinding.tvValueShipper.setText(data.getString("name"));
                    break;
            }
        }
    }


    @Override
    public void setSonos(List<String> values) {
        initSweetSheets(mDataBinding.tvValueCont.getId(), mDataBinding.flMain, "请选择柜型", values, (position, menuEntity) -> {
            //返回的分别是三个级别的选中位置
            mDataBinding.tvValueCont.setText(menuEntity.title);
            return true;
        });
    }


}
