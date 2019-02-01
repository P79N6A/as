package com.yaoguang.company.order.forwarder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.yaoguang.appcommon.phone.order.adapter.LogBillTrackAdapter;
import com.yaoguang.appcommon.phone.order.detail.DetailContact;
import com.yaoguang.appcommon.phone.order.detail.DetailPresenterImpl;
import com.yaoguang.greendao.entity.common.ViewForwardOrder;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseFragment;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AnimatorUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.company.R;
import com.yaoguang.company.order.common.OrderDetailNodeFragment;
import com.yaoguang.company.order.forwarder.adapter.LoadingAdapter;
import com.yaoguang.company.order.forwarder.adapter.UnLoadingAdapter;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightSono;
import com.yaoguang.map.common.ToastUtil;
import com.yaoguang.lib.appcommon.widget.recyclerview.HorizontalDividerItemDecoration;
import com.yaoguang.widget.layoutmanager.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 货代明细
 * Created by zhongjh on 2017/6/21.
 */
public class ForwarderDetailFragment extends BaseFragment implements DetailContact.DetailView<ViewForwardOrder> {

    /**
     * 订单id
     */
    public static final String BUNDLE_BILLID = "mBillId";

    public InitialView mInitialView;

    String mBillId;
    String mDriverOrderId;
    String mOrderSn;

    protected DetailContact.DetailPresenter mPresenter;


    public static ForwarderDetailFragment newInstance(String billId) {
        ForwarderDetailFragment forwarderDetailFragment = new ForwarderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_BILLID, billId);
        forwarderDetailFragment.setArguments(bundle);
        return forwarderDetailFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mBillId = args.getString(BUNDLE_BILLID);
            mPresenter = new DetailPresenterImpl(this, 0, mBillId,null);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_forwarder_detail, container, false);
        mInitialView = new InitialView(view);
        return view;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mPresenter.subscribe();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initDataView(ViewForwardOrder data) {
        //订单信息
        mInitialView.tvLadingId.setText(data.getFreightBills().getLadingId());
        mInitialView.tvDetailCreated.setText(data.getFreightBills().getCreated() + " 制单");
        mInitialView.tvPeople.setText(data.getFreightBills().getShipper());
        mInitialView.tvGoodsName.setText(data.getFreightBills().getGoodsName());
        mInitialView.tvDeparture.setText(data.getFreightBills().getDockOfLoading());
        mInitialView.tvDestination.setText(data.getFreightBills().getFinalDestination());
        mInitialView.tvConQty.setText(data.getConQty());
        mInitialView.tvConsigneeId.setText(data.getFreightBills().getConsigneeId());
        mInitialView.tvEmployeeId.setText(data.getFreightBills().getEmployeeId());
        mInitialView.tvModifyDate.setText(data.getFreightBills().getModifyDate());
        mInitialView.tvBusinessType.setText(data.getFreightBills().getPrepaidCollect());
        mInitialView.tvCarriageItem.setText(data.getFreightBills().getCarriageItem());
        mInitialView.tvServiceType.setText(data.getFreightBills().getServicetype());
        mInitialView.tvOperationClause.setText(data.getFreightBills().getConsolCode());
        mInitialView.tvFreightCompany.setText(data.getFreightBills().getFreightCompany());
        // 毛重
        mInitialView.tvGoodsGrossWeight.setText(ObjectUtils.formatNumber2(data.getFreightBills().getGoodsGrossWeight(), 0));

        //装货加载
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity());
        LoadingAdapter loadingAdapter = new LoadingAdapter();
        mInitialView.rvLoading.setAdapter(loadingAdapter);
        mInitialView.rvLoading.setHasFixedSize(true);
        mInitialView.rvLoading.setLayoutManager(myLayoutManager);
        mInitialView.rvLoading.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext())
                        .showLastDivider()
                        .color(ContextCompat.getColor(getContext(), R.color.black_dividers_text))
                        .sizeResId(R.dimen.divider)
                        .build());
        loadingAdapter.appendToList(data.getFreightShipperList());
        loadingAdapter.notifyDataSetChanged();
        mInitialView.erlLoading.initLayout();

        //卸货加载
        MyLayoutManager myLayoutManager2 = new MyLayoutManager(getActivity());
        UnLoadingAdapter unloadingAdapter = new UnLoadingAdapter();
        mInitialView.rvUnLoading.setAdapter(unloadingAdapter);
        mInitialView.rvUnLoading.setHasFixedSize(true);
        mInitialView.rvUnLoading.setLayoutManager(myLayoutManager2);
        mInitialView.rvUnLoading.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext())
                        .showLastDivider()
                        .color(ContextCompat.getColor(getContext(), R.color.black_dividers_text))
                        .sizeResId(R.dimen.divider)
                        .build());
        unloadingAdapter.appendToList(data.getFreightConsigneeList());
        unloadingAdapter.notifyDataSetChanged();
        mInitialView.erlUnLoading.initLayout();

        //派车信息 - 装货
        mInitialView.tvValueGoodsNameE.setText(data.getFreightTruck().getGoodsNameE());
        mInitialView.tvValueLoadDate.setText(data.getFreightTruck().getLoadDate());
        mInitialView.tvValuePreTruck.setText(data.getFreightTruck().getPreTruck());
        mInitialView.tvValuePreShipCompany.setText(data.getFreightTruck().getPreShipCompany());
        if (data.getLogBillsTrack() != null)
            mInitialView.tvCreatedFreightTruck.setText(data.getLogBillsTrack().getTruckPlanTime() + " 派单");

        //派车信息 - 送货
        mInitialView.tvValueGoodsPriority.setText(data.getFreightTruck().getGoodsPriority());
        mInitialView.tvValueReleasePlanDate.setText(data.getFreightTruck().getReleasePlanDate());
        mInitialView.tvValueDestTruck.setText(data.getFreightTruck().getDestTruck());
        mInitialView.tvValueDesShipCompany.setText(data.getFreightTruck().getDesShipCompany());
        if (data.getLogBillsTrack() != null)
            mInitialView.tvCreated2.setText(data.getLogBillsTrack().getDestTruckPlanTime() + " 派单");

        //船公司信息
        mInitialView.tvValuePortLoadingShip.setText(data.getFreightShip().getPortLoading());
        mInitialView.tvValuePortDeliveryShip.setText(data.getFreightShip().getPortDelivery());
        mInitialView.tvValueCarriageWayShip.setText(data.getFreightShip().getCarriageWay());
        mInitialView.tvValueShipCompanyShip.setText(data.getFreightShip().getShipCompany());
        mInitialView.tvValueVessel.setText(data.getFreightShip().getVessel());
        mInitialView.tvValueVoyage.setText(data.getFreightShip().getVoyage());
        mInitialView.tvValueInVesselPlanTime.setText(data.getFreightShip().getInVesselPlanTime());
        mInitialView.tvValueEtaPlan2.setText(data.getFreightShip().getEtaPlan2());
        mInitialView.tvValueMBLNO.setText(data.getFreightShip().getmBlNo());
        if (data.getFreightSonoList() != null && data.getFreightSonoList().size() > 0)
            mInitialView.tvCreatedShip.setText(data.getFreightSonoList().get(0).getSonoDate() + " 订舱");
        mInitialView.tvValueSecondVessel.setText(data.getFreightShip().getSecondVessel());
        mInitialView.tvValueSecondVoyage.setText(data.getFreightShip().getSecondVoyage());
        mInitialView.tvValueSecondEtd.setText(data.getFreightShip().getSecondEtd());
        mInitialView.tvValueSecondEta.setText(data.getFreightShip().getSecondEta());
        if (data.getFreightShip().getTrangeOr() == 0) {
            GlideManager.getInstance().with(ForwarderDetailFragment.this.getContext(), R.drawable.ic_weigouxuan, mInitialView.imgTrangeOr);
        } else {
            GlideManager.getInstance().with(ForwarderDetailFragment.this.getContext(), R.drawable.ic_gouxuan, mInitialView.imgTrangeOr);
        }
        mInitialView.tvValueThirdVessel.setText(data.getFreightShip().getThirdVessel());
        mInitialView.tvValueThirdVoyage.setText(data.getFreightShip().getThirdVoyage());
        mInitialView.tvValueThirdEtd.setText(data.getFreightShip().getThirdEtd());
        mInitialView.tvValueFeederCompany.setText(data.getFreightShip().getFeederCompany());

        //保险信息

        if (data.getFreightInsurance().getIsInsurance() == 1) {
            mInitialView.tvFreightInsuranceTitle.setText("保险信息（已购买）");
            mInitialView.tbFreightInsurance.setVisibility(View.VISIBLE);
            mInitialView.tvFreightInsurance.setVisibility(View.INVISIBLE);
        } else {
            mInitialView.tvFreightInsuranceTitle.setText("保险信息（未购买）");
            mInitialView.tbFreightInsurance.setVisibility(View.INVISIBLE);
            mInitialView.tvFreightInsurance.setVisibility(View.VISIBLE);
        }

        mInitialView.tvValueInsurType.setText(data.getFreightInsurance().getInsurType());
        mInitialView.tvValueInsurValue.setText("¥" + ObjectUtils.formatNumber2(data.getFreightInsurance().getInsurValue(), 0));
        mInitialView.tvValueInsurRate.setText(ObjectUtils.parseString(data.getFreightInsurance().getInsurRate()));
        mInitialView.tvValueInsurMoney.setText("¥" + ObjectUtils.formatNumber2(data.getFreightInsurance().getInsurMoney(), 0));
        mInitialView.tvValueHBLNO.setText(data.getFreightInsurance().gethBlNo());
        mInitialView.tvValueAgency.setText(data.getFreightInsurance().getAgency());
        mInitialView.tvValueInstoreStatusDate.setText(data.getFreightInsurance().getInstoreStatusDate());

        //柜号信息，默认取第一个
        ArrayList<String> stringSonos = new ArrayList<>();
        if (data.getFreightSonoList() != null) {
            int sonosSize = data.getFreightSonoList().size();
            if (sonosSize > 0) {
                for (int i = 0; i < sonosSize; i++) {
                    int number = i + 1;
                    stringSonos.add("柜号" + number);
                }
                setSono(data.getFreightSonoList().get(0));
            }
        }

        //显示多个柜号标题
        List<TextView> sonsIdViews = new ArrayList<>();
        for (int i = 0; i < stringSonos.size(); i++) {
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(ForwarderDetailFragment.this.getContext()).inflate(R.layout.item_sono_id, null);
            TextView textView = (TextView) moreView.findViewById(R.id.tvTitle);
            sonsIdViews.add(textView);
            textView.setText(stringSonos.get(i));
            if (i == 0) {
                textView.setBackgroundResource(R.drawable.ic_guigaoyi);
                textView.setTextColor(getResources().getColor(R.color.white_text));
            }
            int finalI = i;
            moreView.setOnClickListener(v -> {
                setSono(data.getFreightSonoList().get(finalI));
                for (TextView textViewItem : sonsIdViews) {
                    textViewItem.setBackgroundResource(R.drawable.ic_guihaoer);
                    textViewItem.setTextColor(getResources().getColor(R.color.black));
                }
                textView.setBackgroundResource(R.drawable.ic_guigaoyi);
                textView.setTextColor(getResources().getColor(R.color.white_text));
            });
            mInitialView.llSonoID.addView(moreView);
        }


        //货柜跟踪,禁止滑动
        mInitialView.dlMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        LogBillTrackAdapter logBillTrackAdapter = new LogBillTrackAdapter();
        RecyclerViewUtils.initPage(mInitialView.rvAppLogBillTrackWrapper, logBillTrackAdapter, null, getContext(), false);
        logBillTrackAdapter.appendToList(data.getAppLogBillTrackWrappers());
        logBillTrackAdapter.notifyDataSetChanged();

        mInitialView.erlUnLoading.initLayout();
        mInitialView.erlTop.initLayout();
        mInitialView.erlLoading.initLayout();
        mInitialView.erlFreightTruck.initLayout();
        mInitialView.erlFreightSono.initLayout();
        mInitialView.erlFreightShip.initLayout();
        mInitialView.erlFreightInsurance.initLayout();
    }

    public void setSono(FreightSono freightSono) {
        mInitialView.tvValueSOID.setText(freightSono.getSoId());
        mInitialView.tvValuePortDeliverySono.setText(freightSono.getContId());
        mInitialView.tvValueCarriageWaySono.setText(freightSono.getContNo());
        mInitialView.tvValueShipCompanySono.setText(freightSono.getSealNo());
        if (freightSono.getTx() == 0) {
            GlideManager.getInstance().with(ForwarderDetailFragment.this.getContext(), R.drawable.ic_weigouxuan, mInitialView.imgTX);
        } else {
            GlideManager.getInstance().with(ForwarderDetailFragment.this.getContext(), R.drawable.ic_gouxuan, mInitialView.imgTX);
        }
        if (freightSono.getTruckGoodsOr() == 1) {
            GlideManager.getInstance().with(ForwarderDetailFragment.this.getContext(), R.drawable.ic_weigouxuan, mInitialView.imgTruckGoodsOr);
        } else {
            GlideManager.getInstance().with(ForwarderDetailFragment.this.getContext(), R.drawable.ic_gouxuan, mInitialView.imgTruckGoodsOr);
        }
        if (freightSono.getIsRepair() == 0) {
            GlideManager.getInstance().with(ForwarderDetailFragment.this.getContext(), R.drawable.ic_weigouxuan, mInitialView.imgIsRepair);
        } else {
            GlideManager.getInstance().with(ForwarderDetailFragment.this.getContext(), R.drawable.ic_gouxuan, mInitialView.imgIsRepair);
        }
        mInitialView.tvTruckId.setText(freightSono.getTruckId());
        mInitialView.tvHackmanTel.setText(freightSono.getHackmanTel());
        mInitialView.tvHackman.setText(freightSono.getHackman());
        mInitialView.tvRemark5.setText(freightSono.getRemark5());
        mInitialView.tvDestTruckId.setText(freightSono.getDestTruckId());
        mInitialView.tvDestHackmanTel.setText(freightSono.getDestHackmanTel());
        mInitialView.tvDestHackman.setText(freightSono.getDestHackman());
        mInitialView.tvDestRemark5.setText(freightSono.getDestRemark5());
        mInitialView.tvShipperW.setText(ObjectUtils.parseString(freightSono.getShipperW()));
        mInitialView.tvNetWeight.setText(ObjectUtils.parseString(freightSono.getNetWeight()));
        mInitialView.tvW1.setText(ObjectUtils.parseString(freightSono.getW1()));
        mInitialView.tvPack.setText(freightSono.getPack());
        mInitialView.tvGoodsValue.setText(ObjectUtils.parseString(freightSono.getGoodsValue()));
        mInitialView.tvGoods.setText(freightSono.getGoods());
        mInitialView.tvGoodsSize.setText(freightSono.getGoodsSize());
        mInitialView.tvQuantity.setText(ObjectUtils.parseString(freightSono.getQuantity()));
        mInitialView.tvGoodsPosition.setText(freightSono.getGoodsPosition());
        mInitialView.tvContposition.setText(freightSono.getContposition());
        mInitialView.tvRemark1.setText(freightSono.getRemark1());

        //装卸货动态
        mInitialView.tvLoadingDynamic.setOnClickListener(v -> {
            if (!ObjectUtils.parseString(freightSono.getShipperDriverId()).equals("")) {
                start(OrderDetailNodeFragment.newInstance(freightSono.getShipperDriverId(), freightSono.getPcSonoId(), "0",freightSono.getPcSonoId()), SINGLETOP);
            } else {
                ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "没有数据");
            }
        });
        mInitialView.tvUnLoadingDynamic.setOnClickListener(v -> {

            if (!ObjectUtils.parseString(freightSono.getShipperDriverId()).equals("")) {
                start(OrderDetailNodeFragment.newInstance(freightSono.getConsigneeDriverId(), freightSono.getPcSonoId(), "1",freightSono.getPcSonoId()), SINGLETOP);
            } else {
                ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "没有数据");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mInitialView.unbinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public class InitialView {

        @BindView(R.id.toolbar_title)
        TextView toolbarTitle;
        @BindView(R.id.toolbar)
        Toolbar toolbar;
        @BindView(R.id.tvLadingId)
        TextView tvLadingId;
        @BindView(R.id.tvLogBillTrack)
        TextView tvLogBillTrack;
        @BindView(R.id.rlTop)
        RelativeLayout rlTop;
        @BindView(R.id.tvPeople)
        TextView tvPeople;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvDeparture)
        TextView tvDeparture;
        @BindView(R.id.tvDestination)
        TextView tvDestination;
        @BindView(R.id.tvConQty)
        TextView tvConQty;
        @BindView(R.id.tvConsigneeId)
        TextView tvConsigneeId;
        @BindView(R.id.tvEmployeeId)
        TextView tvEmployeeId;
        @BindView(R.id.tvModifyDate)
        TextView tvModifyDate;
        @BindView(R.id.tvBusinessType)
        TextView tvBusinessType;
        @BindView(R.id.tvCarriageItem)
        TextView tvCarriageItem;
        @BindView(R.id.tvServiceType)
        TextView tvServiceType;
        @BindView(R.id.tvOperationClause)
        TextView tvOperationClause;
        @BindView(R.id.tvFreightCompany)
        TextView tvFreightCompany;
        @BindView(R.id.tvGoodsGrossWeight)
        TextView tvGoodsGrossWeight;
        @BindView(R.id.tlTop)
        TableLayout tlTop;
        @BindView(R.id.vTop)
        View vTop;
        @BindView(R.id.tvDetailCreated)
        TextView tvDetailCreated;
        @BindView(R.id.erlTop)
        ExpandableRelativeLayout erlTop;
        @BindView(R.id.imgDetailLoading)
        ImageView imgDetailLoading;
        @BindView(R.id.rlLoading)
        RelativeLayout rlLoading;
        @BindView(R.id.rvLoading)
        RecyclerView rvLoading;
        @BindView(R.id.erlLoading)
        ExpandableLinearLayout erlLoading;
        @BindView(R.id.imgDetailUnLoading)
        ImageView imgDetailUnLoading;
        @BindView(R.id.rlUnLoading)
        RelativeLayout rlUnLoading;
        @BindView(R.id.rvUnLoading)
        RecyclerView rvUnLoading;
        @BindView(R.id.erlUnLoading)
        ExpandableLinearLayout erlUnLoading;
        @BindView(R.id.imgDetailFreightTruck)
        ImageView imgDetailFreightTruck;
        @BindView(R.id.rlFreightTruck)
        RelativeLayout rlFreightTruck;
        @BindView(R.id.vTitleGoodsNameE)
        View vTitleGoodsNameE;
        @BindView(R.id.tvTitleGoodsNameE)
        TextView tvTitleGoodsNameE;
        @BindView(R.id.tvValueGoodsNameE)
        TextView tvValueGoodsNameE;
        @BindView(R.id.tvTitleLoadDate)
        TextView tvTitleLoadDate;
        @BindView(R.id.tvValueLoadDate)
        TextView tvValueLoadDate;
        @BindView(R.id.tvTitlePreTruck)
        TextView tvTitlePreTruck;
        @BindView(R.id.tvValuePreTruck)
        TextView tvValuePreTruck;
        @BindView(R.id.tvTitlePreShipCompany)
        TextView tvTitlePreShipCompany;
        @BindView(R.id.tvValuePreShipCompany)
        TextView tvValuePreShipCompany;
        @BindView(R.id.tvCreatedFreightTruck)
        TextView tvCreatedFreightTruck;
        @BindView(R.id.vTitlePreShipCompanyFreightTruck)
        View vTitlePreShipCompanyFreightTruck;
        @BindView(R.id.tvTitleGoodsPriority)
        TextView tvTitleGoodsPriority;
        @BindView(R.id.tvValueGoodsPriority)
        TextView tvValueGoodsPriority;
        @BindView(R.id.tvTitleReleasePlanDate)
        TextView tvTitleReleasePlanDate;
        @BindView(R.id.tvValueReleasePlanDate)
        TextView tvValueReleasePlanDate;
        @BindView(R.id.tvTitleDestTruck)
        TextView tvTitleDestTruck;
        @BindView(R.id.tvValueDestTruck)
        TextView tvValueDestTruck;
        @BindView(R.id.tvTitleDesShipCompany)
        TextView tvTitleDesShipCompany;
        @BindView(R.id.tvValueDesShipCompany)
        TextView tvValueDesShipCompany;
        @BindView(R.id.tvCreated2)
        TextView tvCreated2;
        @BindView(R.id.erlFreightTruck)
        ExpandableRelativeLayout erlFreightTruck;
        @BindView(R.id.imgFreightShip)
        ImageView imgFreightShip;
        @BindView(R.id.rlFreightShip)
        RelativeLayout rlFreightShip;
        @BindView(R.id.tvValuePortLoadingShip)
        TextView tvValuePortLoadingShip;
        @BindView(R.id.tvValuePortDeliveryShip)
        TextView tvValuePortDeliveryShip;
        @BindView(R.id.tvValueCarriageWayShip)
        TextView tvValueCarriageWayShip;
        @BindView(R.id.tvValueShipCompanyShip)
        TextView tvValueShipCompanyShip;
        @BindView(R.id.tvValueVessel)
        TextView tvValueVessel;
        @BindView(R.id.tvValueVoyage)
        TextView tvValueVoyage;
        @BindView(R.id.tlFreightShipShip)
        TableLayout tlFreightShipShip;
        @BindView(R.id.tvTitleInVesselPlanTime)
        TextView tvTitleInVesselPlanTime;
        @BindView(R.id.tvValueInVesselPlanTime)
        TextView tvValueInVesselPlanTime;
        @BindView(R.id.tvTitleEtaPlan2)
        TextView tvTitleEtaPlan2;
        @BindView(R.id.tvValueEtaPlan2)
        TextView tvValueEtaPlan2;
        @BindView(R.id.tvTitleMBLNO)
        TextView tvTitleMBLNO;
        @BindView(R.id.tvValueMBLNO)
        TextView tvValueMBLNO;
        @BindView(R.id.tvCreatedShip)
        TextView tvCreatedShip;
        @BindView(R.id.vTitlePreShipCompanyShip)
        View vTitlePreShipCompanyShip;
        @BindView(R.id.tvValueSecondVessel)
        TextView tvValueSecondVessel;
        @BindView(R.id.tvValueSecondVoyage)
        TextView tvValueSecondVoyage;
        @BindView(R.id.tvValueSecondEtd)
        TextView tvValueSecondEtd;
        @BindView(R.id.tvValueSecondEta)
        TextView tvValueSecondEta;
        @BindView(R.id.imgTrangeOr)
        ImageView imgTrangeOr;
        @BindView(R.id.tvValueThirdVessel)
        TextView tvValueThirdVessel;
        @BindView(R.id.tvValueThirdVoyage)
        TextView tvValueThirdVoyage;
        @BindView(R.id.tvValueThirdEtd)
        TextView tvValueThirdEtd;
        @BindView(R.id.tvValueFeederCompany)
        TextView tvValueFeederCompany;
        @BindView(R.id.erlFreightShip)
        ExpandableRelativeLayout erlFreightShip;
        @BindView(R.id.tvFreightInsuranceTitle)
        TextView tvFreightInsuranceTitle;
        @BindView(R.id.imgFreightInsurance)
        ImageView imgFreightInsurance;
        @BindView(R.id.rlFreightInsurance)
        RelativeLayout rlFreightInsurance;
        @BindView(R.id.tvFreightInsurance)
        TextView tvFreightInsurance;
        @BindView(R.id.tvValueInsurType)
        TextView tvValueInsurType;
        @BindView(R.id.tvValueInsurValue)
        TextView tvValueInsurValue;
        @BindView(R.id.tvValueInsurRate)
        TextView tvValueInsurRate;
        @BindView(R.id.tvValueInsurMoney)
        TextView tvValueInsurMoney;
        @BindView(R.id.tvValueHBLNO)
        TextView tvValueHBLNO;
        @BindView(R.id.tvValueAgency)
        TextView tvValueAgency;
        @BindView(R.id.tvValueInstoreStatusDate)
        TextView tvValueInstoreStatusDate;
        @BindView(R.id.tbFreightInsurance)
        TableLayout tbFreightInsurance;
        @BindView(R.id.erlFreightInsurance)
        ExpandableRelativeLayout erlFreightInsurance;
        @BindView(R.id.imgFreightSono)
        ImageView imgFreightSono;
        @BindView(R.id.rlFreightSono)
        RelativeLayout rlFreightSono;
        @BindView(R.id.llSonoID)
        LinearLayout llSonoID;
        @BindView(R.id.hslSonoID)
        HorizontalScrollView hslSonoID;
        @BindView(R.id.tvValueSOID)
        TextView tvValueSOID;
        @BindView(R.id.tvValuePortDeliverySono)
        TextView tvValuePortDeliverySono;
        @BindView(R.id.tvValueCarriageWaySono)
        TextView tvValueCarriageWaySono;
        @BindView(R.id.tvValueShipCompanySono)
        TextView tvValueShipCompanySono;
        @BindView(R.id.imgTX)
        ImageView imgTX;
        @BindView(R.id.imgTruckGoodsOr)
        ImageView imgTruckGoodsOr;
        @BindView(R.id.imgIsRepair)
        ImageView imgIsRepair;
        @BindView(R.id.tvLoadingDynamic)
        TextView tvLoadingDynamic;
        @BindView(R.id.tvTruckId)
        TextView tvTruckId;
        @BindView(R.id.tvHackmanTel)
        TextView tvHackmanTel;
        @BindView(R.id.tvHackman)
        TextView tvHackman;
        @BindView(R.id.tvRemark5)
        TextView tvRemark5;
        @BindView(R.id.tvUnLoadingDynamic)
        TextView tvUnLoadingDynamic;
        @BindView(R.id.tvDestTruckId)
        TextView tvDestTruckId;
        @BindView(R.id.tvDestHackmanTel)
        TextView tvDestHackmanTel;
        @BindView(R.id.tvDestHackman)
        TextView tvDestHackman;
        @BindView(R.id.tvDestRemark5)
        TextView tvDestRemark5;
        @BindView(R.id.tvShipperW)
        TextView tvShipperW;
        @BindView(R.id.tvNetWeight)
        TextView tvNetWeight;
        @BindView(R.id.tvW1)
        TextView tvW1;
        @BindView(R.id.tvPack)
        TextView tvPack;
        @BindView(R.id.tvGoods)
        TextView tvGoods;
        @BindView(R.id.tvGoodsValue)
        TextView tvGoodsValue;
        @BindView(R.id.tvQuantity)
        TextView tvQuantity;
        @BindView(R.id.tvGoodsPosition)
        TextView tvGoodsPosition;
        @BindView(R.id.tvGoodsSize)
        TextView tvGoodsSize;
        @BindView(R.id.tvContposition)
        TextView tvContposition;
        @BindView(R.id.tvRemark1)
        TextView tvRemark1;
        @BindView(R.id.tlFreightShipSono)
        TableLayout tlFreightShipSono;
        @BindView(R.id.erlFreightSono)
        ExpandableRelativeLayout erlFreightSono;
        @BindView(R.id.rvAppLogBillTrackWrapper)
        RecyclerView rvAppLogBillTrackWrapper;
        @BindView(R.id.llAppLogBillTrackWrapper)
        LinearLayout llAppLogBillTrackWrapper;
        @BindView(R.id.dlMain)
        DrawerLayout dlMain;
        Unbinder unbinder;


        InitialView(View view) {
            unbinder = ButterKnife.bind(this, view);
            initView();
            initListener();
        }

        void initView() {
            initToolbarNav(toolbar,"货代订单详情",-1,null);
        }

        void initListener() {

            rlLoading.setOnClickListener(v -> expandOrCollapse(erlLoading));
            rlUnLoading.setOnClickListener(v -> expandOrCollapse(erlUnLoading));
            rlFreightTruck.setOnClickListener(v -> expandOrCollapse(erlFreightTruck));
            rlFreightShip.setOnClickListener(v -> expandOrCollapse(erlFreightShip));
            rlFreightInsurance.setOnClickListener(v -> expandOrCollapse(erlFreightInsurance));
            rlFreightSono.setOnClickListener(v -> expandOrCollapse(erlFreightSono));

            tvLogBillTrack.setOnClickListener(v -> {
                if (dlMain.isDrawerOpen(llAppLogBillTrackWrapper)) {
                    dlMain.closeDrawer(llAppLogBillTrackWrapper, true);
                } else {
                    dlMain.openDrawer(llAppLogBillTrackWrapper, true);
                }
            });

            erlLoading.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    AnimatorUtils.createRotateAnimator(imgDetailLoading, 0f, 180f).start();
                }

                @Override
                public void onPreClose() {
                    AnimatorUtils.createRotateAnimator(imgDetailLoading, 180f, 0f).start();
                }
            });
            erlUnLoading.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    AnimatorUtils.createRotateAnimator(imgDetailUnLoading, 0f, 180f).start();
                }

                @Override
                public void onPreClose() {
                    AnimatorUtils.createRotateAnimator(imgDetailUnLoading, 180f, 0f).start();
                }
            });
            erlFreightTruck.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    AnimatorUtils.createRotateAnimator(imgDetailFreightTruck, 0f, 180f).start();
                }

                @Override
                public void onPreClose() {
                    AnimatorUtils.createRotateAnimator(imgDetailFreightTruck, 180f, 0f).start();
                }
            });
            erlFreightShip.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    AnimatorUtils.createRotateAnimator(imgFreightShip, 0f, 180f).start();
                }

                @Override
                public void onPreClose() {
                    AnimatorUtils.createRotateAnimator(imgFreightShip, 180f, 0f).start();
                }
            });
            erlFreightInsurance.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    AnimatorUtils.createRotateAnimator(imgFreightInsurance, 0f, 180f).start();
                }

                @Override
                public void onPreClose() {
                    AnimatorUtils.createRotateAnimator(imgFreightInsurance, 180f, 0f).start();
                }
            });
            erlFreightSono.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    AnimatorUtils.createRotateAnimator(imgFreightSono, 0f, 180f).start();
                }

                @Override
                public void onPreClose() {
                    AnimatorUtils.createRotateAnimator(imgFreightSono, 180f, 0f).start();
                }
            });


        }

        void expandOrCollapse(ExpandableRelativeLayout expandableRelativeLayout) {
            expandableRelativeLayout.toggle();
        }

        void expandOrCollapse(ExpandableLinearLayout expandableLinearLayout) {
            expandableLinearLayout.toggle();
        }

    }


}
