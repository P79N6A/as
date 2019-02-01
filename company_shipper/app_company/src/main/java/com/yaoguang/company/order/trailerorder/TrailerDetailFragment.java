package com.yaoguang.company.order.trailerorder;

import android.graphics.drawable.Drawable;
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
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseFragment;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AnimatorUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.company.R;
import com.yaoguang.company.order.common.OrderDetailNodeFragment;
import com.yaoguang.company.order.trailerorder.adapter.LoadingTrailerAdapter;
import com.yaoguang.greendao.entity.common.TruckBills;
import com.yaoguang.greendao.entity.TruckSono;
import com.yaoguang.lib.appcommon.widget.recyclerview.HorizontalDividerItemDecoration;
import com.yaoguang.widget.layoutmanager.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 拖车明细
 * Created by zhongjh on 2017/6/21.
 */
public class TrailerDetailFragment extends BaseFragment implements DetailContact.DetailView<TruckBills> {

    /**
     * 订单id
     */
    public static final String BUNDLE_BILLID = "mBillId";

    public InitialView mInitialView;

    String mBillId;

    protected DetailContact.DetailPresenter mPresenter;


    public static TrailerDetailFragment newInstance(String billId) {
        TrailerDetailFragment forwarderDetailFragment = new TrailerDetailFragment();
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
            mPresenter = new DetailPresenterImpl(this, 1, mBillId,null);
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
        View view = inflater.inflate(R.layout.fragment_order_trailer_detail, container, false);
        mInitialView = new InitialView(view);
        return view;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mPresenter.subscribe();
    }

    @Override
    public void initDataView(TruckBills data) {

        if (data.getOtherservice() == 0) {
            mInitialView.tvLoadingDynamic.setText("装货动态");
            mInitialView.tvLoadingTitle.setText("装货信息");
            Drawable drawable = getResources().getDrawable(
                    R.drawable.ic_zhuanghuo_10);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            mInitialView.tvLoadingTitle.setCompoundDrawables(drawable, null, null, null);
        } else {
            mInitialView.tvLoadingDynamic.setText("卸货动态");
            mInitialView.tvLoadingTitle.setText("卸货信息");
            Drawable drawable = getResources().getDrawable(
                    R.drawable.ic_xiehuo_10);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            mInitialView.tvLoadingTitle.setCompoundDrawables(drawable, null, null, null);
        }

        //订单信息
        mInitialView.tvLadingId.setText(data.getOrderSn());
        mInitialView.tvDetailCreated.setText(data.getCreated() + " 制单");
        mInitialView.tvPeople.setText(data.getShipper());
        mInitialView.tvGoodsName.setText(data.getGoodsName());
        mInitialView.tvDeparture.setText(data.getDockOfLoading());
        mInitialView.tvDestination.setText(data.getFinalDestination());
        mInitialView.tvPortLoading.setText(data.getPortLoading());
        mInitialView.tvPortDelivery.setText(data.getPortDelivery());
        mInitialView.tvPrepaidCollect.setText(data.getPrepaidCollect());
        mInitialView.tvFreeStorage.setText(data.getFreeStorage());
        if (data.getIsForesee() != null) {
            if (data.getIsForesee() == 0) {
                mInitialView.tvIsForesee.setText("否");
            } else {
                mInitialView.tvIsForesee.setText("是");
            }
        }

        mInitialView.tvConsigneeId.setText(data.getConsigneeId());
        mInitialView.tvEmployeeId.setText(data.getEmployeeId());
        mInitialView.tvModifyDate.setText(data.getModifyDate());
        mInitialView.tvServiceType.setText(data.getServiceTypeZn());
        mInitialView.tvFreecontdate.setText(data.getFreecontdate()); // 免滞日期
        mInitialView.tvContQty.setText(data.getTruckOther().getContQty()); // 柜型柜量


        //装/卸 货加载
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity());
        LoadingTrailerAdapter loadingTrailerAdapter = new LoadingTrailerAdapter();
        mInitialView.rvLoading.setAdapter(loadingTrailerAdapter);
        mInitialView.rvLoading.setHasFixedSize(true);
        mInitialView.rvLoading.setLayoutManager(myLayoutManager);
        mInitialView.rvLoading.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext())
                        .showLastDivider()
                        .color(ContextCompat.getColor(getContext(), R.color.black_dividers_text))
                        .sizeResId(R.dimen.divider)
                        .build());


        loadingTrailerAdapter.appendToList(data.getTruckGoodsAddrs());
        loadingTrailerAdapter.notifyDataSetChanged();
        mInitialView.rvLoading.setLayoutManager(myLayoutManager);
        mInitialView.erlLoading.initLayout();

        //车牌号
        mInitialView.tvPreCarriage.setText(data.getTruckGoodsTruck().getPreCarriage());
        mInitialView.tvDriverId.setText(data.getTruckGoodsTruck().getDriverId());
        mInitialView.tvDriverTel.setText(data.getTruckGoodsTruck().getDriverTel());
        mInitialView.tvPreTruck.setText(data.getTruckGoodsTruck().getPreTruck());
        mInitialView.tvTruckNo.setText(data.getTruckGoodsTruck().getTruckNo());

        //船公司
        mInitialView.tvValueShipCompanyShip.setText(data.getShipCompany());
        mInitialView.tvValueVessel.setText(data.getVessel());
        mInitialView.tvValueVoyage.setText(data.getVoyage());
        mInitialView.tvValueMBLNO.setText(data.getmBlNo());
        mInitialView.tvValueConsolCode.setText(data.getConsolCode());
        mInitialView.tvValueThID.setText(data.getThId());
        mInitialView.tvValueInVesselPlanTime.setText(data.getInVesselPlanTime());
        mInitialView.tvValueEtaPlan.setText(data.getEtaPlan());

        //柜号信息，默认取第一个
        ArrayList<String> stringSonos = new ArrayList<>();
        int sonosSize = data.getTruckSonos().size();
        if (sonosSize > 0) {
            for (int i = 0; i < sonosSize; i++) {
                int number = i + 1;
                stringSonos.add("柜号" + number);
            }
            setSono(data,data.getTruckSonos().get(0));
        }

        //显示多个柜号标题
        List<TextView> sonsIdViews = new ArrayList<>();
        for (int i = 0; i < stringSonos.size(); i++) {
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(TrailerDetailFragment.this.getContext()).inflate(R.layout.item_sono_id, null);
            TextView textView = (TextView) moreView.findViewById(R.id.tvTitle);
            sonsIdViews.add(textView);
            textView.setText(stringSonos.get(i));
            if (i == 0) {
                textView.setBackgroundResource(R.drawable.ic_guigaoyi);
                textView.setTextColor(getResources().getColor(R.color.white_text));
            }
            int finalI = i;
            moreView.setOnClickListener(v -> {
                setSono(data,data.getTruckSonos().get(finalI));
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

        mInitialView.erlTop.initLayout();
        mInitialView.erlFreightShip.initLayout();
        mInitialView.erlLoading.initLayout();
        mInitialView.erlFreightSono.initLayout();
    }

    public void setSono(TruckBills data,TruckSono truckSono) {
        mInitialView.tvValuePortDeliverySono.setText(truckSono.getContId());
        mInitialView.tvValueCarriageWaySono.setText(truckSono.getContNo());
        mInitialView.tvValueShipCompanySono.setText(truckSono.getSealNo());
        if (truckSono.getTx() == 0) {
            GlideManager.getInstance().with(TrailerDetailFragment.this.getContext(), R.drawable.ic_weigouxuan, mInitialView.imgTX);
        } else {
            GlideManager.getInstance().with(TrailerDetailFragment.this.getContext(), R.drawable.ic_gouxuan, mInitialView.imgTX);
        }
        if (truckSono.getIsRepair() == 0) {
            GlideManager.getInstance().with(TrailerDetailFragment.this.getContext(), R.drawable.ic_weigouxuan, mInitialView.imgIsRepair);
        } else {
            GlideManager.getInstance().with(TrailerDetailFragment.this.getContext(), R.drawable.ic_gouxuan, mInitialView.imgIsRepair);
        }
        mInitialView.tvCarryPort.setText(truckSono.getCarryPort());
        mInitialView.tvGetPort.setText(truckSono.getGetPort());
        mInitialView.tvPickupDate.setText(truckSono.getPickupDate());

        mInitialView.tvShipperW.setText(ObjectUtils.parseString(truckSono.getShipperW()));
        mInitialView.tvNetWeight.setText(ObjectUtils.parseString(truckSono.getNetWeight()));
        mInitialView.tvW1.setText(ObjectUtils.parseString(truckSono.getW1()));
        mInitialView.tvPack.setText(truckSono.getPack());
        mInitialView.tvPiecs.setText(ObjectUtils.parseString(truckSono.getPiecs()));
//        mInitialView.tvGoodsValue.setText(ObjectUtils.parseString(truckSono.getGoodsValue()));
        mInitialView.tvRemark1.setText(truckSono.getRemark1());
        mInitialView.tvDamage.setText(truckSono.getDamage());
        mInitialView.tvGoodsSize.setText(truckSono.getGoodsSize());

        mInitialView.tvLoadingDynamic.setOnClickListener(v -> start(OrderDetailNodeFragment.newInstance(data.getDriverOrderId(), data.getTruckSonos().get(0).getPcSonoId(), ObjectUtils.parseString(data.getOtherservice()),truckSono.getPcSonoId()), SINGLETOP));
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
        @BindView(R.id.tvPortLoading)
        TextView tvPortLoading;
        @BindView(R.id.tvPortDelivery)
        TextView tvPortDelivery;
        @BindView(R.id.tvDestination)
        TextView tvDestination;
        @BindView(R.id.tvConsigneeId)
        TextView tvConsigneeId;
        @BindView(R.id.tvEmployeeId)
        TextView tvEmployeeId;
        @BindView(R.id.tvPrepaidCollect)
        TextView tvPrepaidCollect;
        @BindView(R.id.tvModifyDate)
        TextView tvModifyDate;
        @BindView(R.id.tvFreeStorage)
        TextView tvFreeStorage;
        @BindView(R.id.tvFreecontdate)
        TextView tvFreecontdate;
        @BindView(R.id.tvIsForesee)
        TextView tvIsForesee;
        @BindView(R.id.tvServiceType)
        TextView tvServiceType;
        @BindView(R.id.tvContQty)
        TextView tvContQty;
        @BindView(R.id.tlTop)
        TableLayout tlTop;
        @BindView(R.id.vTop)
        View vTop;
        @BindView(R.id.tvLoadingDynamic)
        TextView tvLoadingDynamic;
        @BindView(R.id.tvDetailCreated)
        TextView tvDetailCreated;
        @BindView(R.id.erlTop)
        ExpandableRelativeLayout erlTop;
        @BindView(R.id.tvLoadingTitle)
        TextView tvLoadingTitle;
        @BindView(R.id.imgLoading)
        ImageView imgLoading;
        @BindView(R.id.rlLoading)
        RelativeLayout rlLoading;
        @BindView(R.id.rvLoading)
        RecyclerView rvLoading;
        @BindView(R.id.tvPreCarriage)
        TextView tvPreCarriage;
        @BindView(R.id.tvDriverId)
        TextView tvDriverId;
        @BindView(R.id.tvDriverTel)
        TextView tvDriverTel;
        @BindView(R.id.tvPreTruck)
        TextView tvPreTruck;
        @BindView(R.id.tvTruckNo)
        TextView tvTruckNo;
        @BindView(R.id.erlLoading)
        ExpandableLinearLayout erlLoading;
        @BindView(R.id.imgFreightShip)
        ImageView imgFreightShip;
        @BindView(R.id.rlFreightShip)
        RelativeLayout rlFreightShip;
        @BindView(R.id.tvValueShipCompanyShip)
        TextView tvValueShipCompanyShip;
        @BindView(R.id.tvValueVessel)
        TextView tvValueVessel;
        @BindView(R.id.tvValueVoyage)
        TextView tvValueVoyage;
        @BindView(R.id.tvValueMBLNO)
        TextView tvValueMBLNO;
        @BindView(R.id.tvValueConsolCode)
        TextView tvValueConsolCode;
        @BindView(R.id.tvValueThID)
        TextView tvValueThID;
        @BindView(R.id.tvValueInVesselPlanTime)
        TextView tvValueInVesselPlanTime;
        @BindView(R.id.tvValueEtaPlan)
        TextView tvValueEtaPlan;
        @BindView(R.id.tlFreightShipShip)
        TableLayout tlFreightShipShip;
        @BindView(R.id.erlFreightShip)
        ExpandableRelativeLayout erlFreightShip;
        @BindView(R.id.imgFreightSono)
        ImageView imgFreightSono;
        @BindView(R.id.rlFreightSono)
        RelativeLayout rlFreightSono;
        @BindView(R.id.llSonoID)
        LinearLayout llSonoID;
        @BindView(R.id.hslSonoID)
        HorizontalScrollView hslSonoID;
        @BindView(R.id.tvValuePortDeliverySono)
        TextView tvValuePortDeliverySono;
        @BindView(R.id.tvPickupDate)
        TextView tvPickupDate;
        @BindView(R.id.tvValueCarriageWaySono)
        TextView tvValueCarriageWaySono;
        @BindView(R.id.tvValueShipCompanySono)
        TextView tvValueShipCompanySono;
        @BindView(R.id.imgTX)
        ImageView imgTX;
        @BindView(R.id.imgIsRepair)
        ImageView imgIsRepair;
        @BindView(R.id.tvCarryPort)
        TextView tvCarryPort;
        @BindView(R.id.tvGetPort)
        TextView tvGetPort;
        @BindView(R.id.tvShipperW)
        TextView tvShipperW;
        @BindView(R.id.tvNetWeight)
        TextView tvNetWeight;
        @BindView(R.id.tvW1)
        TextView tvW1;
        @BindView(R.id.tvPack)
        TextView tvPack;
        @BindView(R.id.tvPiecs)
        TextView tvPiecs;
        @BindView(R.id.tvGoodsSize)
        TextView tvGoodsSize;
        @BindView(R.id.tvDamage)
        TextView tvDamage;
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
            initToolbarNav(toolbar, "拖车订单详情", -1, null);
        }

        void initListener() {
            rlTop.setOnClickListener(v -> expandOrCollapse(erlTop));
            rlLoading.setOnClickListener(v -> expandOrCollapse(erlLoading));
            rlFreightShip.setOnClickListener(v -> expandOrCollapse(erlFreightShip));
            rlFreightSono.setOnClickListener(v -> expandOrCollapse(erlFreightSono));

            tvLogBillTrack.setOnClickListener(v -> {
                if (dlMain.isDrawerOpen(llAppLogBillTrackWrapper)) {
                    dlMain.closeDrawer(llAppLogBillTrackWrapper, true);
                } else {
                    dlMain.openDrawer(llAppLogBillTrackWrapper, true);
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
            erlLoading.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    AnimatorUtils.createRotateAnimator(imgLoading, 0f, 180f).start();
                }

                @Override
                public void onPreClose() {
                    AnimatorUtils.createRotateAnimator(imgLoading, 180f, 0f).start();
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
