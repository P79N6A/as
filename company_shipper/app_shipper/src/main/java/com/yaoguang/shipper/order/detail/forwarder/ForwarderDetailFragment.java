package com.yaoguang.shipper.order.detail.forwarder;

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
import com.yaoguang.appcommon.common.base.BaseBackFragment;
import com.yaoguang.appcommon.phone.order.adapter.LogBillTrackAdapter;
import com.yaoguang.greendao.entity.common.ViewForwardOrder;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AnimatorUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.FreightSono;
import com.yaoguang.appcommon.phone.order.detail.DetailContact;
import com.yaoguang.appcommon.phone.order.detail.DetailPresenterImpl;
import com.yaoguang.map.common.ToastUtil;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.order.common.OrderDetailNodeFragment;
import com.yaoguang.shipper.order.detail.forwarder.adapter.LoadingAdapter;
import com.yaoguang.shipper.order.detail.forwarder.adapter.UnLoadingAdapter;
import com.yaoguang.shipper.order.node.NodeFragment;
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
public class ForwarderDetailFragment extends BaseBackFragment implements DetailContact.DetailView<ViewForwardOrder> {

    /**
     * 订单id
     */
    public static final String BUNDLE_BILLID = "mBillId";
    /**
     * 关联公司id
     */
    public static final String BUNDLE_CLIENTID= "mClientId";

    public InitialView mInitialView;

    String mBillId;
    String mClientId; // 关联公司id

    protected DetailContact.DetailPresenter mPresenter;


    public static ForwarderDetailFragment newInstance(String billId,String clientId) {
        ForwarderDetailFragment forwarderDetailFragment = new ForwarderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_BILLID, billId);
        bundle.putString(BUNDLE_CLIENTID,clientId);
        forwarderDetailFragment.setArguments(bundle);
        return forwarderDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mBillId = args.getString(BUNDLE_BILLID);
            mClientId = args.getString(BUNDLE_CLIENTID);
            mPresenter = new DetailPresenterImpl(this, 0, mBillId,mClientId);
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
        mPresenter.subscribe();
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void initDataView(ViewForwardOrder data) {

        //订单信息
        mInitialView.tvLadingId.setText(data.getFreightBills().getLadingId());

        mInitialView.tvDockOfLoading.setText(data.getFreightBills().getDockOfLoading());
        mInitialView.tvFinalDestination.setText(data.getFreightBills().getFinalDestination());
        mInitialView.tvFreightCompany.setText(data.getFreightBills().getFreightCompany());
        mInitialView.tvModifyDate.setText(data.getFreightBills().getModifyDate());
        mInitialView.tvGoodsName.setText(data.getFreightBills().getGoodsName());
        mInitialView.tvCarriageItem.setText(data.getFreightBills().getCarriageItem());
        mInitialView.tvPrepaidCollect.setText(data.getFreightBills().getPrepaidCollect());
        if (data.getFreightInsurance() != null) {
            if (data.getFreightInsurance().getIsInsurance() == 0) {
                mInitialView.tvIsInsurance.setText("未购");
            } else {
                mInitialView.tvIsInsurance.setText("已购");
            }
        }
        mInitialView.tvLoadDate.setText(data.getFreightTruck().getLoadDate());
        mInitialView.tvMBLNO.setText(data.getFreightShip().getmBlNo());

        mInitialView.tvDetailCreated.setText(data.getFreightBills().getCreated() + " 制单"); // 制单日期

        //装货加载
        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity());
        LoadingAdapter loadingAdapter = new LoadingAdapter();
        RecyclerViewUtils.initPage(mInitialView.rvLoading, loadingAdapter, null, getContext(), false);
        loadingAdapter.appendToList(data.getFreightShipperList());
        loadingAdapter.notifyDataSetChanged();
        mInitialView.rvLoading.setLayoutManager(myLayoutManager);
        mInitialView.erlLoading.initLayout();

        //卸货加载
        MyLayoutManager myLayoutManager2 = new MyLayoutManager(getActivity());
        UnLoadingAdapter unloadingAdapter = new UnLoadingAdapter();
        RecyclerViewUtils.initPage(mInitialView.rvUnLoading, unloadingAdapter, null, getContext(), false);
        unloadingAdapter.appendToList(data.getFreightConsigneeList());
        unloadingAdapter.notifyDataSetChanged();
        mInitialView.rvUnLoading.setLayoutManager(myLayoutManager2);
        mInitialView.erlUnLoading.initLayout();

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
                    setSono(data.getFreightSonoList().get(ObjectUtils.parseInt(v.getTag())));
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

        mInitialView.erlUnLoading.initLayout();
        mInitialView.erlLoading.initLayout();
        mInitialView.erlFreightSono.initLayout();
        mInitialView.erlTop.initLayout();
    }

    public void setSono(FreightSono freightSono) {
        mInitialView.tvValuePortDeliverySono.setText(freightSono.getContId());
        mInitialView.tvValueCarriageWaySono.setText(freightSono.getContNo());
        mInitialView.tvValueShipCompanySono.setText(freightSono.getSealNo());
        //装卸货动态
        mInitialView.tvLoadingDynamic.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                // 查询动态
                start(NodeFragment.newInstance("4",freightSono.getPcSonoId()));
//                if (!ObjectUtils.parseString(freightSono.getShipperDriverId()).equals("")) {
//
//                } else {
//                    ToastUtil.show(BaseApplication.getInstance().getApplicationContext(), "没有数据");
//                }
            }
        });
        mInitialView.tvUnLoadingDynamic.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                // 查询动态
                start(NodeFragment.newInstance("5",freightSono.getPcSonoId()));
//                if (!ObjectUtils.parseString(freightSono.getShipperDriverId()).equals("")) {
//                    start(OrderDetailNodeFragment.newInstance(freightSono.getConsigneeDriverId(), freightSono.getConsigneeTruckId(),"1",freightSono.getPcSonoId()));
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
        @BindView(R.id.tvDockOfLoading)
        TextView tvDockOfLoading;
        @BindView(R.id.tvFinalDestination)
        TextView tvFinalDestination;
        @BindView(R.id.tvFreightCompany)
        TextView tvFreightCompany;
        @BindView(R.id.tvModifyDate)
        TextView tvModifyDate;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvCarriageItem)
        TextView tvCarriageItem;
        @BindView(R.id.tvPrepaidCollect)
        TextView tvPrepaidCollect;
        @BindView(R.id.tvIsInsurance)
        TextView tvIsInsurance;
        @BindView(R.id.tvLoadDate)
        TextView tvLoadDate;
        @BindView(R.id.tvMBLNO)
        TextView tvMBLNO;
        @BindView(R.id.tlTop)
        TableLayout tlTop;
        @BindView(R.id.vTop)
        View vTop;
        @BindView(R.id.tvLogBillTrack)
        TextView tvLogBillTrack;
        @BindView(R.id.erlTop)
        ExpandableRelativeLayout erlTop;
        @BindView(R.id.imgLoading)
        ImageView imgLoading;
        @BindView(R.id.rlLoading)
        RelativeLayout rlLoading;
        @BindView(R.id.rvLoading)
        RecyclerView rvLoading;
        @BindView(R.id.erlLoading)
        ExpandableLinearLayout erlLoading;
        @BindView(R.id.imgUnLoading)
        ImageView imgUnLoading;
        @BindView(R.id.rlUnLoading)
        RelativeLayout rlUnLoading;
        @BindView(R.id.rvUnLoading)
        RecyclerView rvUnLoading;
        @BindView(R.id.erlUnLoading)
        ExpandableLinearLayout erlUnLoading;
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
        @BindView(R.id.tvLoadingDynamic)
        TextView tvLoadingDynamic;
        @BindView(R.id.tvUnLoadingDynamic)
        TextView tvUnLoadingDynamic;
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
            rlTop.setOnClickListener(v -> expandOrCollapse(erlTop));
            rlLoading.setOnClickListener(v -> expandOrCollapse(erlLoading));
            rlUnLoading.setOnClickListener(v -> expandOrCollapse(erlUnLoading));
            rlFreightSono.setOnClickListener(v -> expandOrCollapse(erlFreightSono));

            tvLogBillTrack.setOnClickListener(v -> {
                if (dlMain.isDrawerOpen(llAppLogBillTrackWrapper)) {
                    dlMain.closeDrawer(llAppLogBillTrackWrapper, true);
                } else {
                    dlMain.openDrawer(llAppLogBillTrackWrapper, true);
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
            erlUnLoading.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    AnimatorUtils.createRotateAnimator(imgUnLoading, 0f, 180f).start();
                }

                @Override
                public void onPreClose() {
                    AnimatorUtils.createRotateAnimator(imgUnLoading, 180f, 0f).start();
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
