package com.yaoguang.shipper.order.detail.trailerorder;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.yaoguang.lib.base.BaseFragment;
import com.yaoguang.appcommon.phone.order.adapter.LogBillTrackAdapter;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AnimatorUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.common.TruckBills;
import com.yaoguang.greendao.entity.TruckSono;
import com.yaoguang.appcommon.phone.order.detail.DetailContact;
import com.yaoguang.appcommon.phone.order.detail.DetailPresenterImpl;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.order.common.OrderDetailNodeFragment;
import com.yaoguang.shipper.order.detail.trailerorder.adapter.LoadingTrailerAdapter;
import com.yaoguang.shipper.order.node.NodeFragment;
import com.yaoguang.widget.layoutmanager.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 拖车订单
 * Created by zhongjh on 2017/8/7.
 */
public class TrailerDetailFragment extends BaseFragment implements DetailContact.DetailView<TruckBills> {

    /**
     * 订单id
     */
    public static final String BUNDLE_BILLID = "mBillId";
    /**
     * 关联公司id
     */
    public static final String BUNDLE_CLIENTID = "mClientId";

    public InitialView mInitialView;

    String mBillId; // 订单id
    String mClientId; // 关联公司id

    protected DetailContact.DetailPresenter mPresenter;


    public static TrailerDetailFragment newInstance(String billId, String clientId) {
        TrailerDetailFragment forwarderDetailFragment = new TrailerDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_BILLID, billId);
        bundle.putString(BUNDLE_CLIENTID, clientId);
        forwarderDetailFragment.setArguments(bundle);
        return forwarderDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mBillId = args.getString(BUNDLE_BILLID);
            mClientId = args.getString(BUNDLE_CLIENTID);
            mPresenter = new DetailPresenterImpl(this, 1, mBillId, mClientId);
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
        mPresenter.subscribe();
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void initDataView(TruckBills data) {
        //订单信息
        mInitialView.tvLadingId.setText(data.getOrderSn());
        mInitialView.tvDetailCreated.setText(data.getCreated() + " 制单");


        //判断装箱还是卸箱
        if (data.getOtherservice() == 0) {
            //装
            mInitialView.tvDepartureTitle.setText("起运地:");
            mInitialView.tvDeparture.setText(data.getDockOfLoading());
            mInitialView.tvDestinationTitle.setText("起运港:");
            mInitialView.tvDestination.setText(data.getPortLoading());
            mInitialView.tvPrepaidCollect.setText("装箱");
            mInitialView.tvLoadingDynamic.setText("装货动态");
            mInitialView.tvLoadingTitle.setText("装货信息");
            Drawable drawable = getResources().getDrawable(
                    R.drawable.ic_zhuanghuo_09);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            mInitialView.tvLoadingTitle.setCompoundDrawables(drawable, null, null, null);
        } else {
            //卸
            mInitialView.tvDepartureTitle.setText("目的地:");
            mInitialView.tvDeparture.setText(data.getFinalDestination());
            mInitialView.tvDestinationTitle.setText("目的港:");
            mInitialView.tvDestination.setText(data.getPortDelivery());
            mInitialView.tvPrepaidCollect.setText("卸箱");
            mInitialView.tvLoadingDynamic.setText("卸货动态");
            mInitialView.tvLoadingTitle.setText("卸货信息");
            Drawable drawable = getResources().getDrawable(
                    R.drawable.ic_xiehuo_09);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            mInitialView.tvLoadingTitle.setCompoundDrawables(drawable, null, null, null);
        }
        mInitialView.tvConsigneeId.setText(data.getClientName());
        mInitialView.tvEmployeeId.setText(data.getShipper());
        mInitialView.tvModifyDate.setText(data.getModifyDate());
        mInitialView.tvFreeStorage.setText(data.getGoodsName());
        mInitialView.tvServiceType.setText(data.getPrepaidCollect());
        mInitialView.tvOperationClause.setText(data.getConsolCode());

        //航次
        mInitialView.tvValueShipCompanyShip.setText(data.getVessel());
        mInitialView.tvValueVoyage.setText(data.getVoyage());
        mInitialView.tvValueMBLNO.setText(data.getmBlNo());

        //车牌号
        mInitialView.tvPreCarriage.setText(data.getTruckGoodsTruck().getPreCarriage());
        mInitialView.tvDriverId.setText(data.getTruckGoodsTruck().getDriverId());
        mInitialView.tvTruckNo.setText(data.getTruckGoodsTruck().getTruckNo());


        //装货/卸货 加载
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity());
        LoadingTrailerAdapter loadingTrailerAdapter = new LoadingTrailerAdapter(data.getTruckGoodsTruck().getDriverId());
        RecyclerViewUtils.initPage(mInitialView.rvLoading, loadingTrailerAdapter, null, getContext(), false);
        loadingTrailerAdapter.appendToList(data.getTruckGoodsAddrs());
        loadingTrailerAdapter.notifyDataSetChanged();
        mInitialView.rvLoading.setLayoutManager(myLayoutManager);
        mInitialView.erlLoading.initLayout();


        //柜号信息，默认取第一个
        ArrayList<String> stringSonos = new ArrayList<>();
        int sonosSize = data.getTruckSonos().size();
        if (sonosSize > 0) {
            for (int i = 0; i < sonosSize; i++) {
                int number = i + 1;
                stringSonos.add("柜号" + number);
            }
            setSono(data, data.getTruckSonos().get(0));
        }

        //显示多个柜号标题
        List<TextView> sonsIdViews = new ArrayList<>();
        for (int i = 0; i < stringSonos.size(); i++) {
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(TrailerDetailFragment.this.getContext()).inflate(R.layout.item_sono_id, null);
            moreView.setTag(i);
            TextView textView = (TextView) moreView.findViewById(R.id.tvTitle);
            sonsIdViews.add(textView);
            textView.setText(stringSonos.get(i));
            if (i == 0) {
                textView.setBackgroundResource(R.drawable.ic_guigaoyi);
                textView.setTextColor(getResources().getColor(R.color.white_text));
            }
            moreView.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    setSono(data, data.getTruckSonos().get(ObjectUtils.parseInt(v.getTag())));
                    for (TextView textViewItem : sonsIdViews) {
                        textViewItem.setBackgroundResource(R.drawable.ic_guihaoer);
                        textViewItem.setTextColor(getResources().getColor(R.color.black));
                    }
                    textView.setBackgroundResource(R.drawable.ic_guigaoyi);
                    textView.setTextColor(getResources().getColor(R.color.white_text));
                }
            });
            mInitialView.llSonoID.addView(moreView);
        }

        //货柜跟踪,禁止滑动
        mInitialView.dlMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        LogBillTrackAdapter logBillTrackAdapter = new LogBillTrackAdapter();
        RecyclerViewUtils.initPage(mInitialView.rvAppLogBillTrackWrapper, logBillTrackAdapter, null, getContext(), false);
        logBillTrackAdapter.appendToList(data.getAppLogBillTrackWrappers());
        logBillTrackAdapter.notifyDataSetChanged();

        mInitialView.erlFreightShip.initLayout();
        mInitialView.erlFreightSono.initLayout();
        mInitialView.erlLoading.initLayout();

    }

    public void setSono(TruckBills data, TruckSono truckSono) {
        mInitialView.tvValuePortDeliverySono.setText(truckSono.getContId());
        mInitialView.tvValueCarriageWaySono.setText(truckSono.getContNo());
        mInitialView.tvValueShipCompanySono.setText(truckSono.getSealNo());
        mInitialView.tvCarryPort.setText(truckSono.getCarryPort());
        mInitialView.tvGetPort.setText(truckSono.getGetPort());

        mInitialView.tvLoadingDynamic.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                // 查询动态
                start(NodeFragment.newInstance( ObjectUtils.parseString(data.getOtherservice()),truckSono.getPcSonoId()));
//                if (!ObjectUtils.parseString(freightSono.getShipperDriverId()).equals("")) {
//
//                } else {
//                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "没有数据");
//                }
            }
        });
    }

    @Override
    public void onDestroy() {
        mInitialView.unbinder.unbind();
        super.onDestroy();
    }

    public class InitialView {

        @BindView(R.id.imgReturn)
        ImageView imgReturn;
        @BindView(R.id.toolbar_title)
        TextView toolbarTitle;
        @BindView(R.id.toolbar)
        Toolbar toolbar;
        @BindView(R.id.tvLadingId)
        TextView tvLadingId;
        @BindView(R.id.tvDetailCreated)
        TextView tvDetailCreated;
        @BindView(R.id.rlTop)
        RelativeLayout rlTop;
        @BindView(R.id.tvDepartureTitle)
        TextView tvDepartureTitle;
        @BindView(R.id.tvDeparture)
        TextView tvDeparture;
        @BindView(R.id.tvDestinationTitle)
        TextView tvDestinationTitle;
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
        @BindView(R.id.tvServiceType)
        TextView tvServiceType;
        @BindView(R.id.tvOperationClause)
        TextView tvOperationClause;
        @BindView(R.id.tlTop)
        TableLayout tlTop;
        @BindView(R.id.vTop)
        View vTop;
        @BindView(R.id.tvLoadingDynamic)
        TextView tvLoadingDynamic;
        @BindView(R.id.tvLogBillTrack)
        TextView tvLogBillTrack;
        @BindView(R.id.erlTop)
        RelativeLayout erlTop;
        @BindView(R.id.imgFreightShip)
        ImageView imgFreightShip;
        @BindView(R.id.rlFreightShip)
        RelativeLayout rlFreightShip;
        @BindView(R.id.tvValueShipCompanyShip)
        TextView tvValueShipCompanyShip;
        @BindView(R.id.tvValueVoyage)
        TextView tvValueVoyage;
        @BindView(R.id.tvValueMBLNO)
        TextView tvValueMBLNO;
        @BindView(R.id.tlFreightShipShip)
        TableLayout tlFreightShipShip;
        @BindView(R.id.erlFreightShip)
        ExpandableRelativeLayout erlFreightShip;
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
        @BindView(R.id.tvTruckNo)
        TextView tvTruckNo;
        @BindView(R.id.erlLoading)
        ExpandableLinearLayout erlLoading;
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
        @BindView(R.id.tvValueCarriageWaySono)
        TextView tvValueCarriageWaySono;
        @BindView(R.id.tvValueShipCompanySono)
        TextView tvValueShipCompanySono;
        @BindView(R.id.tvCarryPort)
        TextView tvCarryPort;
        @BindView(R.id.tvGetPort)
        TextView tvGetPort;
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
            initToolbarNav(toolbar, "订单详情", -1, null);
        }

        void initListener() {
            rlFreightShip.setOnClickListener(v -> expandOrCollapse(erlFreightShip));
            rlLoading.setOnClickListener(v -> expandOrCollapse(erlLoading));
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
