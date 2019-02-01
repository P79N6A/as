package com.yaoguang.company.analysis.adapter;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.Utils;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.AppPriceAnalysisWrapper;

import java.util.List;

/**
 * 货代报价适配器
 * Created by zhongjh on 2017/6/13.
 */
public class PriceAnalysisAdapter extends BaseLoadMoreRecyclerAdapter<AppPriceAnalysisWrapper, RecyclerView.ViewHolder> {

    private SparseBooleanArray expandStartState = new SparseBooleanArray();
    private SparseBooleanArray expandOtherState = new SparseBooleanArray();
    private SparseBooleanArray expandEndState = new SparseBooleanArray();

    @Override
    public void appendToList(List<AppPriceAnalysisWrapper> list) {
        for (int i = 0; i < list.size(); i++) {
            expandStartState.append(i, false);
            expandOtherState.append(i, false);
            expandEndState.append(i, false);
        }
        super.appendToList(list);
    }

    public void clear() {
        expandStartState.clear();
        expandOtherState.clear();
        expandEndState.clear();
        super.clear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_analysis2, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);

        //点击，显示
        holder.llValueStartFeeAndLoadingTruckCompanyValue2.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (holder.erlValueStartFeeAndLoadingTruckCompanyValue2.getVisibility() == View.VISIBLE) {
                animClose(holder.erlValueStartFeeAndLoadingTruckCompanyValue2);
                createRotateAnimator(holder.imgValueStartFeeAndLoadingTruckCompanyValue2, 180f, 0f).start();
                expandStartState.put(position, false);
            } else {
                animOpen(holder.erlValueStartFeeAndLoadingTruckCompanyValue2);
                createRotateAnimator(holder.imgValueStartFeeAndLoadingTruckCompanyValue2, 0f, 180f).start();
                expandStartState.put(position, true);
            }
        });
        holder.llValueOtherFeeAndShipCompanyValue2.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (holder.erlllValueOtherFeeAndShipCompanyValue2.getVisibility() == View.VISIBLE) {
                animClose(holder.erlllValueOtherFeeAndShipCompanyValue2);
                createRotateAnimator(holder.imgValueOtherFeeAndShipCompanyValue2, 180f, 0f).start();
                expandOtherState.put(position, false);
            } else {
                animOpen(holder.erlllValueOtherFeeAndShipCompanyValue2);
                createRotateAnimator(holder.imgValueOtherFeeAndShipCompanyValue2, 0f, 180f).start();
                expandOtherState.put(position, true);
            }
        });
        holder.llValueEndFeeAndPortDeliveryAndFinalDestination.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();
            if (holder.erlValueEndFeeAndPortDeliveryAndFinalDestination.getVisibility() == View.VISIBLE) {
                animClose(holder.erlValueEndFeeAndPortDeliveryAndFinalDestination);
                createRotateAnimator(holder.imgValueEndFeeAndPortDeliveryAndFinalDestination, 180f, 0f).start();
                expandEndState.put(position, false);
            } else {
                animOpen(holder.erlValueEndFeeAndPortDeliveryAndFinalDestination);
                createRotateAnimator(holder.imgValueEndFeeAndPortDeliveryAndFinalDestination, 0f, 180f).start();
                expandEndState.put(position, true);
            }
        });

        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;


        itemViewHolder.tvcont.setText(ObjectUtils.parseString(getList().get(position).getCont()));
        //普通货
        itemViewHolder.tvContId.setText(ObjectUtils.parseString(getList().get(position).getPriceAnalysis().getGoodsType()));
        itemViewHolder.tvTotalFee.setText(ObjectUtils.parseString(ObjectUtils.formatNumber2(getList().get(position).getTotalFee(), 0)));
        //4种运货类型 1.起运地DockOfLoading，起运港PortLoading，目的港PortDelivery，目的地FinalDestination
        itemViewHolder.tvShipLine0.setText(getList().get(position).getPriceAnalysis().getDockOfLoading());
        itemViewHolder.tvShipLine1.setText(getList().get(position).getPriceAnalysis().getPortLoading());
        itemViewHolder.tvShipLine2.setText(getList().get(position).getPriceAnalysis().getPortDelivery());
        itemViewHolder.tvShipLine3.setText(getList().get(position).getPriceAnalysis().getFinalDestination());
        //始驳公司
        itemViewHolder.tvValueStartFeeAndLoadingTruckCompanyValue2.setText("¥" + ObjectUtils.parseString(ObjectUtils.formatNumber2(getList().get(position).getStartFee(), 0)) + " (" + getList().get(position).getPriceAnalysis().getLoadingTruckCompany() + ")");
        itemViewHolder.tvDockOfLoadingAndPortLoading0.setText(getList().get(position).getPriceAnalysis().getDockOfLoading());
        itemViewHolder.tvDockOfLoadingAndPortLoading1.setText(getList().get(position).getPriceAnalysis().getPortLoading());
        itemViewHolder.tvLoadingTruckCompanyValue.setText(getList().get(position).getPriceAnalysis().getLoadingTruckCompany());
        itemViewHolder.tvStartFeeValue.setText("¥" + ObjectUtils.formatNumber2(getList().get(position).getStartFee(), 0));
        //达驳公司
        itemViewHolder.tvValueEndFeeAndDestinationTruckCompanyValue2.setText("¥" + ObjectUtils.parseString(ObjectUtils.formatNumber2(getList().get(position).getEndFee(), 0)) + " (" + getList().get(position).getPriceAnalysis().getDestinationTruckCompany() + ")");
        itemViewHolder.tvPortDeliveryAndFinalDestination0.setText(getList().get(position).getPriceAnalysis().getPortDelivery());
        itemViewHolder.tvPortDeliveryAndFinalDestination1.setText(getList().get(position).getPriceAnalysis().getFinalDestination());
        itemViewHolder.tvDestinationTruckCompanyValue.setText(getList().get(position).getPriceAnalysis().getDestinationTruckCompany());
        itemViewHolder.tvEndFeeValue.setText("¥" + ObjectUtils.formatNumber2(getList().get(position).getEndFee(), 0));
        //船公司
        itemViewHolder.tvValueOtherFeeAndShipCompanyValue2.setText("¥" + ObjectUtils.parseString(ObjectUtils.formatNumber2(getList().get(position).getOtherFee(), 0)) + " (" + getList().get(position).getPriceAnalysis().getShipCompany() + ")");
        itemViewHolder.tvPortLoadingAndPortDelivery0.setText(getList().get(position).getPriceAnalysis().getPortLoading());
        itemViewHolder.tvPortLoadingAndPortDelivery1.setText(getList().get(position).getPriceAnalysis().getPortDelivery());
        itemViewHolder.tvShipCompanyValue.setText(getList().get(position).getPriceAnalysis().getShipCompany());
        itemViewHolder.tvAgentBookingCompanyValue.setText(getList().get(position).getPriceAnalysis().getAgentBookingCompany());
        itemViewHolder.tvOceanFreightPriceValue.setText("¥" + ObjectUtils.parseString(ObjectUtils.formatNumber2(getList().get(position).getPriceAnalysis().getOceanFreightPrice(), 0)));
        itemViewHolder.tvPackingPriceValue.setText("¥" + ObjectUtils.parseString(ObjectUtils.formatNumber2(getList().get(position).getPriceAnalysis().getPackingPrice(), 0)));
        itemViewHolder.tvOrderPriceValue.setText("¥" + ObjectUtils.parseString(ObjectUtils.formatNumber2(getList().get(position).getPriceAnalysis().getOrderPrice(), 0)));
        itemViewHolder.tvFuelsurPriceValue.setText("¥" + ObjectUtils.parseString(ObjectUtils.formatNumber2(getList().get(position).getPriceAnalysis().getFuelsurPrice(), 0)));
        itemViewHolder.tvDockPriceValue.setText("¥" + ObjectUtils.parseString(ObjectUtils.formatNumber2(getList().get(position).getPriceAnalysis().getDockPrice(), 0)));

        if (expandStartState.get(position)) {
            itemViewHolder.erlValueStartFeeAndLoadingTruckCompanyValue2.setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.erlValueStartFeeAndLoadingTruckCompanyValue2.setVisibility(View.GONE);
        }
        if (expandOtherState.get(position)) {
            itemViewHolder.erlllValueOtherFeeAndShipCompanyValue2.setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.erlllValueOtherFeeAndShipCompanyValue2.setVisibility(View.GONE);
        }
        if (expandEndState.get(position)) {
            itemViewHolder.erlValueEndFeeAndPortDeliveryAndFinalDestination.setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.erlValueEndFeeAndPortDeliveryAndFinalDestination.setVisibility(View.GONE);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    private void animOpen(final View view) {
        view.setVisibility(View.VISIBLE);
//        view.setAlpha(0);
//            ValueAnimator mOpenValueAnimator = createDropAnim(view,0, mHiddenViewMeasuredHeight);
//            mOpenValueAnimator.start();
    }

    private void animClose(final View view) {
//        int origHeight = view.getHeight();
//        view.setAlpha(1);
        view.setVisibility(View.GONE);
//            mCloseValueAnimator = createDropAnim(view, origHeight, 0);
//            mCloseValueAnimator.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//
//                }
//            });
//            mCloseValueAnimator.start();
    }

//        /**
//         * 使用动画的方式来改变高度解决visible不一闪而过出现
//         * @param view
//         * @param start 初始状态值
//         * @param end 结束状态值
//         * @return
//         */
//        private ValueAnimator createDropAnim(final  View view,int start,int end) {
//            final ValueAnimator va = ValueAnimator.ofInt(start, end);
//            va.addUpdateListener(animation -> {
//                int value = (int) animation.getAnimatedValue();//根据时间因子的变化系数进行设置高度
//                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//                layoutParams.height = value;
//
//                float alpha = ((float)value) / mHiddenViewMeasuredHeight;
//                view.setAlpha(alpha);
//
//                view.setLayoutParams(layoutParams);//设置高度
//            });
//            return  va;
//        }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tvcont;
        public TextView tvContId;
        public TextView tvTotalFee;
        public TextView tvShipLine0;
        public TextView tvShipLine1;
        public TextView tvShipLine2;
        public TextView tvShipLine3;
        public TextView tvValueStartFeeAndLoadingTruckCompanyValue2;
        public ImageView imgValueStartFeeAndLoadingTruckCompanyValue2;
        public LinearLayout llValueStartFeeAndLoadingTruckCompanyValue2;
        public TextView tvDockOfLoadingAndPortLoading0;
        public TextView tvDockOfLoadingAndPortLoading1;
        public TextView tvLoadingTruckCompanyValue;
        public TextView tvStartFeeValue;
        public LinearLayout erlValueStartFeeAndLoadingTruckCompanyValue2;
        public TextView tvValueEndFeeAndDestinationTruckCompanyValue2;
        public ImageView imgValueEndFeeAndPortDeliveryAndFinalDestination;
        public LinearLayout llValueEndFeeAndPortDeliveryAndFinalDestination;
        public TextView tvPortDeliveryAndFinalDestination0;
        public TextView tvPortDeliveryAndFinalDestination1;
        public TextView tvDestinationTruckCompanyValue;
        public TextView tvEndFeeValue;
        public LinearLayout erlValueEndFeeAndPortDeliveryAndFinalDestination;
        public TextView tvValueOtherFeeAndShipCompanyValue2;
        public ImageView imgValueOtherFeeAndShipCompanyValue2;
        public LinearLayout llValueOtherFeeAndShipCompanyValue2;
        public TextView tvPortLoadingAndPortDelivery0;
        public TextView tvPortLoadingAndPortDelivery1;
        public TextView tvShipCompanyValue;
        public TextView tvAgentBookingCompanyValue;
        public TextView tvOceanFreightPriceValue;
        public TextView tvPackingPriceValue;
        public TextView tvFuelsurPriceValue;
        public TextView tvOrderPriceValue;
        public TextView tvDockPriceValue;
        public LinearLayout erlllValueOtherFeeAndShipCompanyValue2;

        public ItemViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tvcont = rootView.findViewById(R.id.tvcont);
            this.tvContId = rootView.findViewById(R.id.tvContId);
            this.tvTotalFee = rootView.findViewById(R.id.tvTotalFee);
            this.tvShipLine0 = rootView.findViewById(R.id.tvShipLine0);
            this.tvShipLine1 = rootView.findViewById(R.id.tvShipLine1);
            this.tvShipLine2 = rootView.findViewById(R.id.tvShipLine2);
            this.tvShipLine3 = rootView.findViewById(R.id.tvShipLine3);
            this.tvValueStartFeeAndLoadingTruckCompanyValue2 = rootView.findViewById(R.id.tvValueStartFeeAndLoadingTruckCompanyValue2);
            this.imgValueStartFeeAndLoadingTruckCompanyValue2 = rootView.findViewById(R.id.imgValueStartFeeAndLoadingTruckCompanyValue2);
            this.llValueStartFeeAndLoadingTruckCompanyValue2 = rootView.findViewById(R.id.llValueStartFeeAndLoadingTruckCompanyValue2);
            this.tvDockOfLoadingAndPortLoading0 = rootView.findViewById(R.id.tvDockOfLoadingAndPortLoading0);
            this.tvDockOfLoadingAndPortLoading1 = rootView.findViewById(R.id.tvDockOfLoadingAndPortLoading1);
            this.tvLoadingTruckCompanyValue = rootView.findViewById(R.id.tvLoadingTruckCompanyValue);
            this.tvStartFeeValue = rootView.findViewById(R.id.tvStartFeeValue);
            this.erlValueStartFeeAndLoadingTruckCompanyValue2 = rootView.findViewById(R.id.erlValueStartFeeAndLoadingTruckCompanyValue2);
            this.tvValueEndFeeAndDestinationTruckCompanyValue2 = rootView.findViewById(R.id.tvValueEndFeeAndDestinationTruckCompanyValue2);
            this.imgValueEndFeeAndPortDeliveryAndFinalDestination = rootView.findViewById(R.id.imgValueEndFeeAndPortDeliveryAndFinalDestination);
            this.llValueEndFeeAndPortDeliveryAndFinalDestination = rootView.findViewById(R.id.llValueEndFeeAndPortDeliveryAndFinalDestination);
            this.tvPortDeliveryAndFinalDestination0 = rootView.findViewById(R.id.tvPortDeliveryAndFinalDestination0);
            this.tvPortDeliveryAndFinalDestination1 = rootView.findViewById(R.id.tvPortDeliveryAndFinalDestination1);
            this.tvDestinationTruckCompanyValue = rootView.findViewById(R.id.tvDestinationTruckCompanyValue);
            this.tvEndFeeValue = rootView.findViewById(R.id.tvEndFeeValue);
            this.erlValueEndFeeAndPortDeliveryAndFinalDestination = rootView.findViewById(R.id.erlValueEndFeeAndPortDeliveryAndFinalDestination);
            this.tvValueOtherFeeAndShipCompanyValue2 = rootView.findViewById(R.id.tvValueOtherFeeAndShipCompanyValue2);
            this.imgValueOtherFeeAndShipCompanyValue2 = rootView.findViewById(R.id.imgValueOtherFeeAndShipCompanyValue2);
            this.llValueOtherFeeAndShipCompanyValue2 = rootView.findViewById(R.id.llValueOtherFeeAndShipCompanyValue2);
            this.tvPortLoadingAndPortDelivery0 = rootView.findViewById(R.id.tvPortLoadingAndPortDelivery0);
            this.tvPortLoadingAndPortDelivery1 = rootView.findViewById(R.id.tvPortLoadingAndPortDelivery1);
            this.tvShipCompanyValue = rootView.findViewById(R.id.tvShipCompanyValue);
            this.tvAgentBookingCompanyValue = rootView.findViewById(R.id.tvAgentBookingCompanyValue);
            this.tvOceanFreightPriceValue = rootView.findViewById(R.id.tvOceanFreightPriceValue);
            this.tvPackingPriceValue = rootView.findViewById(R.id.tvPackingPriceValue);
            this.tvFuelsurPriceValue = rootView.findViewById(R.id.tvFuelsurPriceValue);
            this.tvOrderPriceValue = rootView.findViewById(R.id.tvOrderPriceValue);
            this.tvDockPriceValue = rootView.findViewById(R.id.tvDockPriceValue);
            this.erlllValueOtherFeeAndShipCompanyValue2 = rootView.findViewById(R.id.erlllValueOtherFeeAndShipCompanyValue2);
        }
    }
}
