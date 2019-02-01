package com.yaoguang.driver.phone.order.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yaoguang.appcommon.common.eventbus.HomeEvent;
import com.yaoguang.appcommon.common.eventbus.OrderChildEvent;
import com.yaoguang.appcommon.common.finalrequest.DriverRequest;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentOrderChildBinding;
import com.yaoguang.driver.phone.main.MainFragment;
import com.yaoguang.driver.phone.order.OrderFragment;
import com.yaoguang.driver.phone.order.child.adapter.OrderChildAdapter;
import com.yaoguang.driver.phone.order.chooseaddress.PutOrderAddressFragment;
import com.yaoguang.driver.phone.order.detail.OrderDetailFragment;
import com.yaoguang.driver.phone.order.node.NodeFragment;
import com.yaoguang.driver.phone.order.nodeEdit.NodeEditFragment;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.driver.DriverOrderWrapper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.map.location.impl.LocationManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import static com.yaoguang.driver.phone.order.child.OrderChildPresenter.ACCEPT;
import static com.yaoguang.driver.phone.order.child.OrderChildPresenter.REFUSE;
import static com.yaoguang.driver.phone.order.child.OrderChildPresenter.WAIT;
import static com.yaoguang.lib.common.constants.Constants.FLAG_REFRESH_PAGE;
import static com.yaoguang.lib.common.constants.Constants.FLAG_REFRESH_PROGRESS;

/**
 * 订单
 * Created by zhongjh on 2018/3/16.
 */
public class OrderChildFragment extends BaseFragmentListConditionDataBind<DriverOrderWrapper, Integer, OrderChildAdapter, FragmentOrderChildBinding> implements OrderChildContacts.View {

    protected OrderChildContacts.Presenter mPresenter;
    public static final int ORDER_DETAIL_REFRESH = DriverRequest.ORDER_CHILD_FRAGMENT + 0x003;

    protected static final String ARG_TYPE = "arg_type";// 类型
    /**
     * 判断类型
     * 0 "已接单", 1 "待接单", 2"已完成", 3"已关闭"
     */
    protected int mType;

    DialogHelper dialogHelper;

    /**
     * 实例化
     *
     * @param type 判断类型,0 "已接单", 1 "待接单", 2"已完成", 3"已关闭"
     * @return 实例化
     */
    public static OrderChildFragment newInstance(int type) {
        OrderChildFragment fragment = new OrderChildFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Bundle args = getArguments();
        if (args != null) {
            mType = args.getInt(ARG_TYPE);
        }
        // 让Fragment中的onCreateOptionsMenu生效必须先调用setHasOptionsMenu方法
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 防止销毁
        outState.putInt(ARG_TYPE, mType);
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_child;
    }

    @Override
    public OrderChildAdapter getAdapter() {
        //列表初始化
        return new OrderChildAdapter(getContext(), mType);
    }

    @Override
    public void init() {
        mPresenter = new OrderChildPresenter(this);

        // 删除
        if (mDataBinding.llSelectDelete2 != null) {
            mDataBinding.llSelectDelete2.tvDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public void initListener() {
        // 列表跳转详情事件
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            if (AppClickUtil.isDuplicateClick()) return;

            DriverOrderWrapper driverOrderWrapper = (DriverOrderWrapper) item;
            if (getParentFragment() instanceof OrderFragment && getParentFragment().getParentFragment() != null)
                if (mType == WAIT || mType == ACCEPT)
                    ((MainFragment) getParentFragment().getParentFragment()).startForResult(OrderDetailFragment.newInstance(driverOrderWrapper.getOrderId(), true, false), ORDER_DETAIL_REFRESH);
                else
                    ((MainFragment) getParentFragment().getParentFragment()).start(OrderDetailFragment.newInstance(driverOrderWrapper.getOrderId(), true, false), SINGLETOP);
            else if (getParentFragment() instanceof MainFragment)
                if (mType == WAIT || mType == ACCEPT)
                    ((MainFragment) getParentFragment()).startForResult(OrderDetailFragment.newInstance(driverOrderWrapper.getOrderId(), true, false), ORDER_DETAIL_REFRESH);
                else
                    ((MainFragment) getParentFragment()).start(OrderDetailFragment.newInstance(driverOrderWrapper.getOrderId(), true, false), SINGLETOP);
        });

        //列表按钮事件
        mBaseLoadMoreRecyclerAdapter.setOnRecyclerViewItemClickListener(new OrderChildAdapter.OnRecyclerViewItemClickListener() {

            @Override
            public void onItemEditAddressClick(View itemView, DriverOrderWrapper item, int position) {
                if (getParentFragment() instanceof OrderFragment && getParentFragment().getParentFragment() != null)
                    ((MainFragment) getParentFragment().getParentFragment()).start(NodeEditFragment.newInstance(item.getOrderId(), item.getClientId()), SINGLETOP);
            }

            @Override
            public void onItemReceiptClick(View itemView, DriverOrderWrapper driverOrderWrapper, int position) {
                // 获取放单点，如果有就跳转，没有就接单
                mPresenter.getPutOrderData(driverOrderWrapper, position);
            }

            @Override
            public void onItemRefuseClick(View itemView, DriverOrderWrapper driverOrderWrapper, int position) {
                // 拒绝对话框
                // 弹框
                if (dialogHelper == null)
                    dialogHelper = new DialogHelper(getContext(), UiUtils.getString(R.string.order_reason), "", "请输入拒绝理由...", "是的", "我再想想", false, true, 50, new CommonDialog.Listener() {

                        @Override
                        public void ok(String content) {
                            mPresenter.handleUpdate(driverOrderWrapper.getOrderId(), REFUSE, driverOrderWrapper, position, content, null);
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                dialogHelper.show();
            }

            @Override
            public void onItemProgressUpdateClick(View itemView, DriverOrderWrapper driverOrderWrapper, int position) {
                //如果是进度更新的就直接跳转
                if (getParentFragment() instanceof OrderFragment && getParentFragment().getParentFragment() != null)
                    if (mType == WAIT || mType == ACCEPT)
                        ((MainFragment) getParentFragment().getParentFragment()).startForResult(NodeFragment.newInstance(driverOrderWrapper.getOrderId()), ORDER_DETAIL_REFRESH);
                    else
                        ((MainFragment) getParentFragment().getParentFragment()).startForResult(NodeFragment.newInstance(driverOrderWrapper.getOrderId()), -1);
                else if (getParentFragment() instanceof MainFragment)
                    if (mType == WAIT || mType == ACCEPT)
                        ((MainFragment) getParentFragment()).startForResult(NodeFragment.newInstance(driverOrderWrapper.getOrderId()), ORDER_DETAIL_REFRESH);
                    else
                        ((MainFragment) getParentFragment()).startForResult(NodeFragment.newInstance(driverOrderWrapper.getOrderId()), -1);
            }

            @Override
            public void onItemOutCarClick(View itemView, DriverOrderWrapper driverOrderWrapper, int position) {
                // 如果是现在出车就请求网络
                // (1) 获取当前地址
                LocationManager locationManager = new LocationManager();
                locationManager.init(BaseApplication.getInstance().getBaseContext());
                locationManager.setComeback(location -> {
                    locationManager.destroyLocation();
                    mPresenter.outCar(driverOrderWrapper.getOrderId(), driverOrderWrapper, location.getLat(), location.getLon(), location.getAddress(), position);
                });
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public Integer getCondition(boolean isRegain) {
        return mType;
    }

    @Override
    public void setConditionView(Integer condition) {

    }

    @Subscribe(sticky = true)
    public void OrderChildEvent(OrderChildEvent event) {
        switch (event.getType()) {
            case FLAG_REFRESH_PAGE:
                // 推送过来的时候，只刷新待接收的订单的订单
                if (mType == WAIT)
                    refreshData();
                break;
            case FLAG_REFRESH_PROGRESS:
                //指定刷新
                if (event.getOrderType() == mType) {
                    refreshData();
                }
                break;
        }
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    public void showSuccess(String strMessage, DriverOrderWrapper driverOrderWrapper, int position) {
        //重新读取此条数据
        mBaseLoadMoreRecyclerAdapter.updateItem(position, driverOrderWrapper);
        // 刷新首页进度
        EventBus.getDefault().post(new HomeEvent(FLAG_REFRESH_PROGRESS));
        // 进行跳转
        if (getParentFragment() instanceof OrderFragment && getParentFragment().getParentFragment() != null)
            if (mType == WAIT || mType == ACCEPT)
                ((MainFragment) getParentFragment().getParentFragment()).startForResult(NodeFragment.newInstance(driverOrderWrapper.getOrderId()), ORDER_DETAIL_REFRESH);
            else
                ((MainFragment) getParentFragment().getParentFragment()).startForResult(NodeFragment.newInstance(driverOrderWrapper.getOrderId()), -1);
        else if (getParentFragment() instanceof MainFragment)
            if (mType == WAIT || mType == ACCEPT)
                ((MainFragment) getParentFragment()).startForResult(NodeFragment.newInstance(driverOrderWrapper.getOrderId()), ORDER_DETAIL_REFRESH);
            else
                ((MainFragment) getParentFragment()).startForResult(NodeFragment.newInstance(driverOrderWrapper.getOrderId()), -1);

    }

    @Override
    public void showAcceptSuccess(String strMessage, DriverOrderWrapper driverOrderWrapper, int position,
                                  int fromFragment, int toFragment) {
        if (!TextUtils.isEmpty(strMessage)) {
            showToast(strMessage);
        }

        OrderFragment orderFragment = findFragment(OrderFragment.class);
        if (orderFragment == null) {
            orderFragment = (OrderFragment) getParentFragment();
        }

        if (orderFragment == null) {
            return;
        }
        // 刷新待确认和已确认的，并且跳转到已确认
        ((OrderChildFragment) orderFragment.mOrderAdapter.getFragment(fromFragment)).refreshData();
        orderFragment.mDataBinding.viewPager.setCurrentItem(toFragment);
        ((OrderChildFragment) orderFragment.mOrderAdapter.getFragment(toFragment)).refreshData();
    }

    @Override
    public void openPutOrderAddress(DriverOrderWrapper driverOrderWrapper, ArrayList<InfoPutorderPlace> infoPutorderPlaces, int position) {
        // 跳转到选择存放订单地址
        if (getParentFragment() instanceof OrderFragment && getParentFragment().getParentFragment() != null) {
            ((MainFragment) getParentFragment().getParentFragment()).start(PutOrderAddressFragment.newInstance(driverOrderWrapper, infoPutorderPlaces, position, mType));
        } else if (getParentFragment() != null && getParentFragment() instanceof MainFragment && getParentFragment().getParentFragment() != null) {
            ((MainFragment) getParentFragment()).start(PutOrderAddressFragment.newInstance(driverOrderWrapper, infoPutorderPlaces, position, mType));
        }
    }


}
