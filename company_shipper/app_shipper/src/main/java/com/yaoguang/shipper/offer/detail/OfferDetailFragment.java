package com.yaoguang.shipper.offer.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yaoguang.greendao.entity.shipper.PriceShipperReceivableRate;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.databinding.FragmentOfferDetailBinding;

import java.util.Map;

/**
 * Created by zhongjh on 2018/9/18.
 */

public class OfferDetailFragment extends BaseFragmentDataBind<FragmentOfferDetailBinding> {

    PriceShipperReceivableRate mPriceShipperReceivableRate;

    public static OfferDetailFragment newInstance(PriceShipperReceivableRate priceShipperReceivableRate) {
        OfferDetailFragment offerDetailFragment = new OfferDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("priceShipperReceivableRate", priceShipperReceivableRate);
        offerDetailFragment.setArguments(bundle);
        return offerDetailFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_offer_detail;
    }

    @Override
    public void init() {
        // 获取数据
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPriceShipperReceivableRate = bundle.getParcelable("priceShipperReceivableRate");
            if (mPriceShipperReceivableRate != null) {
                mDataBinding.tvConsigneeId.setText(mPriceShipperReceivableRate.getContId());
                mDataBinding.tvGoodsNameAndDR.setText(mPriceShipperReceivableRate.getGoodsName() + "      │      " + mPriceShipperReceivableRate.getCarriageItem());
                mDataBinding.tvMoney.setText(ObjectUtils.decimalFormat(mPriceShipperReceivableRate.getMoney(), "#,##0.00"));
                // 生成其他费用
                for (Map<String, Double> map : mPriceShipperReceivableRate.getFeeDetail()) {
                    // 循环map
                    for (Map.Entry<String, Double> entry : map.entrySet()) {
                        View view = View.inflate(getContext(), R.layout.layout_offer_detail_item, null);
                        ViewHolder viewHolder = new ViewHolder(view);
                        viewHolder.tvTitle.setText(entry.getKey());
                        viewHolder.tvValue.setText(ObjectUtils.decimalFormat(entry.getValue().toString(), "#,##0.00"));
                        mDataBinding.llMoney.addView(view);
                    }
                }
            }
        }

        initToolbarNav(mToolbarCommonBinding.toolbar, "费用明细", -1, null);
    }

    @Override
    public void initListener() {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tvTitle;
        public TextView tvValue;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.tvValue = rootView.findViewById(R.id.tvValue);
        }

    }
}
