package com.yaoguang.company.analysis;

import android.os.Bundle;

import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.analysis.adapter.PriceAnalysisAdapter;
import com.yaoguang.company.databinding.FragmentAnalysis2Binding;
import com.yaoguang.greendao.entity.AppPriceAnalysisWrapper;
import com.yaoguang.greendao.entity.PriceAnalysisCondition;
import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import java.util.List;

/**
 * 货代报价查询
 * Created by zhongjh on 2017/12/12.
 */
public class Analysis2Fragment extends BaseFragmentListConditionDataBind<AppPriceAnalysisWrapper, PriceAnalysisCondition, PriceAnalysisAdapter, FragmentAnalysis2Binding> implements AnalysisContact.View {

    AnalysisContact.Presenter mAnalysisPresenter = new AnalysisPresenter(this);
    PriceAnalysisCondition mPriceAnalysisCondition = new PriceAnalysisCondition();

    public static Analysis2Fragment newInstance() {
        return new Analysis2Fragment();
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mAnalysisPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_analysis2;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new PriceAnalysisAdapter();
    }


    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "货代报价查询", R.menu.shipschedule, Analysis2Fragment.this);
        }
        //绑定数据
        mDataBinding.setPriceAnalysisCondition(mPriceAnalysisCondition);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mAnalysisPresenter.subscribe();
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void initListener() {
        // 清空
        mDataBinding.btnEmpty.setOnClickListener(v -> {
            mDataBinding.tvValueDockOfLoading.setText("");
            mDataBinding.tvValuePlaceLoading.setText("");
            mDataBinding.tvValuePlaceDelivery.setText("");
            mDataBinding.tvValueFinalDestination.setText("");
            mDataBinding.tvValueContId.setText("");
        });
        mDataBinding.rlDockOfLoading.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance( PublicSearchInteractorImpl.TYPE_DEPARTURE);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DEPARTURE));
        });
        mDataBinding.rlPlaceLoading.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance( PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT));
        });
        mDataBinding.rlPlaceDelivery.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION));
        });
        mDataBinding.rlFinalDestination.setOnClickListener(v -> {
            SearchFragment fragment = SearchFragment.newInstance( PublicSearchInteractorImpl.TYPE_DESTINATION);
            startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DESTINATION));
        });
        mDataBinding.rlContId.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(android.view.View v) {
                showSweetSheets(mDataBinding.tvValueContId.getId());
            }
        });

        mDataBinding.imgDockOfLoading.setOnClickListener(v -> mDataBinding.tvValueDockOfLoading.setText(""));
        mDataBinding.imgPlaceLoading.setOnClickListener(v -> mDataBinding.tvValuePlaceLoading.setText(""));
        mDataBinding.imgPlaceDelivery.setOnClickListener(v -> mDataBinding.tvValuePlaceDelivery.setText(""));
        mDataBinding.imgContId.setOnClickListener(v -> mDataBinding.tvValueContId.setText(""));
        mDataBinding.imgFinalDestination.setOnClickListener(v -> mDataBinding.tvValueFinalDestination.setText(""));
    }

    @Override
    public void setSonos(List<String> values) {
        initSweetSheets(mDataBinding.tvValueContId.getId(), mDataBinding.flMain, "请选择柜型", values, (position, menuEntity) -> {
            //返回的分别是三个级别的选中位置
            mDataBinding.tvValueContId.setText(menuEntity.title);
            return true;
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_DEPARTURE:
                    mDataBinding.tvValueDockOfLoading.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_PORTOFSHIPMENT:
                    mDataBinding.tvValuePlaceLoading.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_PORTOFDESTINATION:
                    mDataBinding.tvValuePlaceDelivery.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_DESTINATION:
                    mDataBinding.tvValueFinalDestination.setText(data.getString("name"));
                    break;
            }
        }
    }

    @Override
    public PriceAnalysisCondition getCondition(boolean isRegain) {
        if (isRegain) {
            if (mDataBinding != null) {
                mPriceAnalysisCondition.setDockLoading(mDataBinding.tvValueDockOfLoading.getText().toString());
                mPriceAnalysisCondition.setPortLoading(mDataBinding.tvValuePlaceLoading.getText().toString());
                mPriceAnalysisCondition.setPortDelivery(mDataBinding.tvValuePlaceDelivery.getText().toString());
                mPriceAnalysisCondition.setFinalDestination(mDataBinding.tvValueFinalDestination.getText().toString());
                mPriceAnalysisCondition.setContId(mDataBinding.tvValueContId.getText().toString());
            }
        }
        return mPriceAnalysisCondition;
    }

    @Override
    public void setConditionView(PriceAnalysisCondition condition) {

    }

}
