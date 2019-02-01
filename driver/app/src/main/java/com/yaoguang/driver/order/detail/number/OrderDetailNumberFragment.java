package com.yaoguang.driver.order.detail.number;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.yaoguang.appcommon.common.base.BaseFragmentV2;
import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.driver.R;
import com.yaoguang.driver.order.adapter.OrderDetailFactoryAdapter;
import com.yaoguang.greendao.entity.DriverBillsVo;
import com.yaoguang.greendao.entity.DriverOrderDetailVo;
import com.yaoguang.greendao.entity.DriverSonoVo;
import com.yaoguang.interfaces.driver.view.order.DOrderDetailNumberView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 订单详情里面的 工作单号
 * Created by wly on 2017/4/24.
 */
public class OrderDetailNumberFragment extends BaseFragmentV2 implements DOrderDetailNumberView {

    private static final String ARG_DRIVERORDERADDRWRAPPER = "arg_driverOrderAddrWrapper";
    private static final String ARG_DRIVERORDERADDRWRAPPER_ALL = "arg_driverOrderAddrWrapper_all";

    InitialView mInitialView;

    private DriverBillsVo mDriverBillsVo;
    private DriverOrderDetailVo mOrderDetailResult;
    private OrderDetailFactoryAdapter mOrderDetailFactoryAdapter;

    /**
     * 实例化
     *
     * @return 实例化
     */
    public static OrderDetailNumberFragment newInstance(DriverBillsVo driverBillsVo, DriverOrderDetailVo orderDetailResult) {
        OrderDetailNumberFragment fragment = new OrderDetailNumberFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_DRIVERORDERADDRWRAPPER, driverBillsVo);
        args.putParcelable(ARG_DRIVERORDERADDRWRAPPER_ALL, orderDetailResult);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mDriverBillsVo = args.getParcelable(ARG_DRIVERORDERADDRWRAPPER);
            mOrderDetailResult = args.getParcelable(ARG_DRIVERORDERADDRWRAPPER_ALL);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_DRIVERORDERADDRWRAPPER, mDriverBillsVo);
        outState.putParcelable(ARG_DRIVERORDERADDRWRAPPER_ALL, mOrderDetailResult);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail_number, container, false);
        mInitialView = new InitialView(view);

        if (savedInstanceState != null) {
            mDriverBillsVo = savedInstanceState.getParcelable(ARG_DRIVERORDERADDRWRAPPER);
            mOrderDetailResult = savedInstanceState.getParcelable(ARG_DRIVERORDERADDRWRAPPER_ALL);
        }

        setData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mInitialView == null) return;
        mInitialView.unbinder.unbind();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void setData() {

        if (!mDriverBillsVo.getSonos().isEmpty()) {
            DriverSonoVo driverSonoVo = mDriverBillsVo.getSonos().get(0);

            // 柜号一
            if (!TextUtils.isEmpty(driverSonoVo.getContNo()))
                mInitialView.tvContNoFirst.setText(driverSonoVo.getContNo());
            // 封号一
            if (!TextUtils.isEmpty(driverSonoVo.getSealNo()))
                mInitialView.tvSealNoFirst.setText(driverSonoVo.getSealNo());

            mInitialView.tvCarryPort1.setText(driverSonoVo.getCarryPort());
            mInitialView.tvGetPort1.setText(driverSonoVo.getGetPort());

            if (mDriverBillsVo.getSonos().size() > 1) {
                DriverSonoVo driverSonoVoSecond = mDriverBillsVo.getSonos().get(1);

                // 柜号二
                if (!TextUtils.isEmpty(driverSonoVoSecond.getContNo())) {
                    mInitialView.tvContNoSecond.setText(driverSonoVoSecond.getContNo());
                }
                // 封号一
                if (!TextUtils.isEmpty(driverSonoVoSecond.getSealNo())) {
                    mInitialView.tvSealNoSecond.setText(driverSonoVoSecond.getSealNo());
                }

                mInitialView.tvCarryPort2.setText(driverSonoVoSecond.getCarryPort());
                mInitialView.tvGetPort2.setText(driverSonoVoSecond.getGetPort());
            } else {
                mInitialView.trContNoSecond.setVisibility(View.GONE);
                mInitialView.trContNoGetPostSecond.setVisibility(View.GONE);
            }
        }

        // 装拆箱类型(0:装箱 1：拆箱) 装货时间，拆箱时间
        if (mDriverBillsVo.getOtherService() == 0) {
            mInitialView.tvLoadDateKey.setText("装货时间: ");
        } else {
            mInitialView.tvLoadDateKey.setText("卸货时间: ");
        }
        mInitialView.tvLoadDate.setText(mDriverBillsVo.getLoadDate());
        mInitialView.tvGoodsType.setText(mOrderDetailResult.getOrderWrapper().getOrderType());
        mInitialView.tvWorkNo.setText(mDriverBillsVo.getTruckBillsId());
        mInitialView.tvShipCompany.setText(mOrderDetailResult.getOrderWrapper().getShipCompany());

        //列表赋值
        mOrderDetailFactoryAdapter.getList().clear();
        mOrderDetailFactoryAdapter.clear();
        mOrderDetailFactoryAdapter.appendToList(mDriverBillsVo.getGoodsAddrs());
        mOrderDetailFactoryAdapter.notifyDataSetChanged();
    }

    public void update(DriverBillsVo driverBillsVo, DriverOrderDetailVo mOrderDetailResult) {
        mDriverBillsVo = driverBillsVo;
        this.mOrderDetailResult = mOrderDetailResult;
        setData();
    }

    public class InitialView {
        @BindView(R.id.tvWorkNo)
        TextView tvWorkNo;
        @BindView(R.id.tvGoodsType)
        TextView tvGoodsType;
        @BindView(R.id.tvShipCompany)
        TextView tvShipCompany;
        @BindView(R.id.tvContNoFirst)
        TextView tvContNoFirst;
        @BindView(R.id.tvSealNoFirst)
        TextView tvSealNoFirst;
        @BindView(R.id.tvCarryPort1)
        TextView tvCarryPort1;
        @BindView(R.id.tvGetPort1)
        TextView tvGetPort1;
        @BindView(R.id.tvContNoSecond)
        TextView tvContNoSecond;
        @BindView(R.id.tvSealNoSecond)
        TextView tvSealNoSecond;
        @BindView(R.id.trContNoSecond)
        TableRow trContNoSecond;
        @BindView(R.id.trContNoGetPostSecond)
        TableRow trContNoGetPostSecond;
        @BindView(R.id.tvCarryPort2)
        TextView tvCarryPort2;
        @BindView(R.id.tvGetPort2)
        TextView tvGetPort2;
        @BindView(R.id.tvLoadDateKey)
        TextView tvLoadDateKey;
        @BindView(R.id.tvLoadDate)
        TextView tvLoadDate;
        @BindView(R.id.llRemark)
        LinearLayout llRemark;
        @BindView(R.id.rlFactory)
        RecyclerView rlFactory;
        Unbinder unbinder;

        InitialView(View view) {
            unbinder = ButterKnife.bind(this, view);
            initView();
            initListener();
        }

        void initView() {
            rlFactory.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rlFactory.setLayoutManager(layoutManager);


            mOrderDetailFactoryAdapter = new OrderDetailFactoryAdapter(mDriverBillsVo.getGoodsType());
            rlFactory.setAdapter(mOrderDetailFactoryAdapter);
            rlFactory.setNestedScrollingEnabled(false);
        }

        void initListener() {
            mOrderDetailFactoryAdapter.setCallPhone(phone -> PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{phone}));
        }

    }


}
