package com.yaoguang.shipper.offer;

import android.os.Bundle;
import android.view.View;

import com.yaoguang.appcommon.publicsearch.PublicSearchInteractorImpl;
import com.yaoguang.appcommon.search.SearchFragment;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.shipper.PriceShipperReceivableRate;
import com.yaoguang.greendao.entity.shipper.PriceShipperReceivableRateCondition;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.databinding.FragmentOfferBinding;
import com.yaoguang.shipper.offer.adapter.OfferAdapter;
import com.yaoguang.shipper.offer.detail.OfferDetailFragment;

/**
 * 报价查询 - 主表
 * Created by zhongjh on 2018/8/29.
 */
public class OfferFragment extends BaseFragmentListConditionDataBind<PriceShipperReceivableRate, PriceShipperReceivableRateCondition, OfferAdapter, FragmentOfferBinding> implements OfferContact.View {

    OfferContact.Presenter mLogisticsPresenter = new OfferPresenter(this);
    PriceShipperReceivableRateCondition mPriceShipperReceivableRateCondition;

    /**
     * @param companyId 公司id
     * @param title     标题
     * @return
     */
    public static OfferFragment newInstance(String companyId, String title) {
        OfferFragment offerFragment = new OfferFragment();
        Bundle bundle = new Bundle();
        bundle.putString("companyId", companyId);
        bundle.putString("title", title);
        offerFragment.setArguments(bundle);
        return offerFragment;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new OfferAdapter();
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mLogisticsPresenter;
    }

    @Override
    public PriceShipperReceivableRateCondition getCondition(boolean isRegain) {
        if (isRegain) {
            if (mPriceShipperReceivableRateCondition == null)
                mPriceShipperReceivableRateCondition = new PriceShipperReceivableRateCondition();
            if (getArguments() != null) {
                mPriceShipperReceivableRateCondition.setCompanyId(getArguments().getString("companyId"));
            }
            mPriceShipperReceivableRateCondition.setClientId(DataStatic.getInstance().getUserOwner().getId());
            mPriceShipperReceivableRateCondition.setDockLoading(mDataBinding.tvValueDockOfLoading.getText().toString());
            mPriceShipperReceivableRateCondition.setDockDevilery(mDataBinding.tvValueFinalDestination.getText().toString());
        }
        return mPriceShipperReceivableRateCondition;
    }

    @Override
    public void setConditionView(PriceShipperReceivableRateCondition condition) {
        mDataBinding.tvValueDockOfLoading.setText(condition.getDockLoading());
        mDataBinding.tvValueFinalDestination.setText(condition.getDockDevilery());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_offer;
    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            if (getArguments() != null) {
                initToolbarNav(mToolbarCommonBinding.toolbar, getArguments().getString("title"), R.menu.shipschedule, this);
            }
        }
    }

    @Override
    public void initListener() {
        // 清空
        mDataBinding.btnEmpty.setOnClickListener(v -> {
            mDataBinding.tvValueDockOfLoading.setText("");
            mDataBinding.tvValueFinalDestination.setText("");
        });

        // 起运地
        mDataBinding.rlDockOfLoading.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                SearchFragment fragment = SearchFragment.newInstance( PublicSearchInteractorImpl.TYPE_DEPARTURE);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DEPARTURE));
            }
        });

        // 目的地
        mDataBinding.rlFinalDestination.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                SearchFragment fragment = SearchFragment.newInstance( PublicSearchInteractorImpl.TYPE_DESTINATION);
                startForResult(fragment, ObjectUtils.parseInt(PublicSearchInteractorImpl.TYPE_DESTINATION));
            }
        });

        // 点击某个
        mDataBinding.imgDockOfLoading.setOnClickListener(v -> mDataBinding.tvValueDockOfLoading.setText(""));
        mDataBinding.imgFinalDestination.setOnClickListener(v -> mDataBinding.tvValueFinalDestination.setText(""));

        // 点击详情
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> start(OfferDetailFragment.newInstance((PriceShipperReceivableRate) item)));
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PublicSearchInteractorImpl.TYPE_DEPARTURE:
                    mDataBinding.tvValueDockOfLoading.setText(data.getString("name"));
                    break;
                case PublicSearchInteractorImpl.TYPE_DESTINATION:
                    mDataBinding.tvValueFinalDestination.setText(data.getString("name"));
                    break;
            }
        }
    }

}
