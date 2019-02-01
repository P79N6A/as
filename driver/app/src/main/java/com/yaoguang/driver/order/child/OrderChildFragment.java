package com.yaoguang.driver.order.child;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yaoguang.appcommon.common.eventbus.HomeEvent;
import com.yaoguang.appcommon.common.eventbus.MainActivityResult;
import com.yaoguang.appcommon.common.eventbus.OrderChildEvent;
import com.yaoguang.appcommon.common.eventbus.OrderNodeMapEvent;
import com.yaoguang.appcommon.common.finalrequest.DriverRequest;
import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.appcommon.common.widget.InputCommonDialog;
import com.yaoguang.common.appcommon.dialog.DialogUtil;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.driver.databinding.ToolbarCommonBinding;
import com.yaoguang.driver.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.base.baseview.BaseFragmentListV2;
import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.databinding.FragmentOrderChildBinding;
import com.yaoguang.driver.main.MainFragment;
import com.yaoguang.driver.order.OrderFragment;
import com.yaoguang.driver.order.adapter.OrderChildAdapter;
import com.yaoguang.driver.order.adapter.OrderChildAdapterClosed;
import com.yaoguang.driver.order.adapter.OrderChildAdapterComplete;
import com.yaoguang.driver.order.adapter.OrderChildAdapterReceived;
import com.yaoguang.driver.order.adapter.OrderChildAdapterWait;
import com.yaoguang.driver.order.chooseaddress.PutOrderAddressFragment;
import com.yaoguang.driver.order.detail.OrderDetailFragment;
import com.yaoguang.driver.order.map.OrderNodeMapFragment;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.greendao.entity.InfoPutorderPlace;
import com.yaoguang.greendao.entity.Order;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.yaoguang.common.common.Constants.FLAG_REFRESH_PAGE;
import static com.yaoguang.common.common.Constants.FLAG_REFRESH_PROGRESS;
import static com.yaoguang.driver.order.child.OrderChildPresenter.ACCEPT;
import static com.yaoguang.driver.order.child.OrderChildPresenter.FINISH;
import static com.yaoguang.driver.order.child.OrderChildPresenter.REFUSE;
import static com.yaoguang.driver.order.child.OrderChildPresenter.WAIT;
import static com.yaoguang.driver.order.chooseaddress.PutOrderAddressFragment.ORDER;
import static com.yaoguang.driver.order.chooseaddress.PutOrderAddressFragment.PLACE_ID;
import static com.yaoguang.driver.order.chooseaddress.PutOrderAddressFragment.POSITION;


/**
 * 订单查询的子fragment
 * 分为4种
 * Created by wly on 2017/4/13.
 */
public class OrderChildFragment<T, P extends BasePresenter, B extends ViewDataBinding> extends BaseFragmentListV2<Order, OrderChildPresenter, FragmentOrderChildBinding> implements OrderChildContacts.View {
    public static final int UPLOAD_PROCESS = DriverRequest.ORDER_CHILD_FRAGMENT + 0x001;
    public static final int ORDER_ACCEPT = DriverRequest.ORDER_CHILD_FRAGMENT + 0x002;
    public static final int ORDER_DETAIL_REFRESH = DriverRequest.ORDER_CHILD_FRAGMENT + 0x003;
    protected static final String ARG_TYPE = "arg_type";
    public View viewList;
    public ToolbarCommonBinding toolbarCommonBinding;
    /**
     * 判断类型
     * 0 "已接单", 1 "待接单", 2"已完成", 3"已关闭"
     */
    protected int mType;
    protected OrderChildAdapter orderChildAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mType = args.getInt(ARG_TYPE);
        }
        setHasOptionsMenu(true);
        ULog.i("OrderChildFragment onCreate");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_TYPE, mType);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mType = savedInstanceState.getInt(ARG_TYPE);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        mPresenter.setMessageRepository(Injection.provideMessageRepository(getContext()));
        mPresenter.setData(Injection.provideOrderRepository(getContext()));
    }

    @Override
    protected void initView(View view) {
        toolbarCommonBinding = DataBindingUtil.bind(view.findViewById(R.id.toolbarCommon));
        viewList = getListView();
        setSwipeRv();

        //列表初始化
        switch (mType) {
            case ACCEPT:
                orderChildAdapter = new OrderChildAdapterReceived(getContext());
                break;
            case WAIT:
                orderChildAdapter = new OrderChildAdapterWait(getContext());
                break;
            case FINISH:
                orderChildAdapter = new OrderChildAdapterComplete(getContext());
                break;
            case REFUSE:
                orderChildAdapter = new OrderChildAdapterClosed(getContext());
                break;
        }
        // 添加列表，这样写是方便扩展
        mDataBinding.contentMain.addView(viewList);

        App.handler.postDelayed(() -> {
            initSwipeRecyclerView(view, orderChildAdapter);
            refreshUi();
        }, 200);

        // 删除
        if (mDataBinding.llSelectDelete2 != null) {
            mDataBinding.llSelectDelete2.tvDelete.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_child;
    }

    @Override
    protected void initListener() {
        orderChildAdapter.setCallPhone(order -> {
            String phone;
            if (!TextUtils.isEmpty(order.getMobile())) {
                phone = order.getPhone() + "," + order.getMobile();
            } else {
                phone = order.getPhone();
            }

            PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), phone.split(","));
        });
        // 列表跳转详情事件
        orderChildAdapter.setOnItemClickListener((itemView, item, position) -> {
            if (AppClickUtil.isDuplicateClick()) return;

            Order order = (Order) item;
            if (getParentFragment() instanceof OrderFragment && getParentFragment().getParentFragment() != null)
                if (mType == WAIT || mType == ACCEPT)
                    ((MainFragment) getParentFragment().getParentFragment()).startBrotherFourResultFragment(OrderDetailFragment.newInstance(order.getId()), ORDER_DETAIL_REFRESH);
                else ((MainFragment) getParentFragment().getParentFragment()).startBrotherFragment(OrderDetailFragment.newInstance(order.getId()));
            else if (getParentFragment() != null)
                if (mType == WAIT || mType == ACCEPT)
                    ((MainFragment) getParentFragment()).startBrotherFourResultFragment(OrderDetailFragment.newInstance(order.getId()), ORDER_DETAIL_REFRESH);
                else ((MainFragment) getParentFragment()).startBrotherFragment(OrderDetailFragment.newInstance(order.getId()));

            orderClick(itemView, item, position);
        });

        //列表按钮事件
        switch (mType) {
            case WAIT:
                OrderChildAdapterWait orderChildAdapterWait = (OrderChildAdapterWait) orderChildAdapter;
                orderChildAdapterWait.setOnRecyclerViewItemClickListener(new OrderChildAdapterWait.OnRecyclerViewItemClickListener() {

                    /**
                     * 接受
                     * @param itemView view
                     * @param item 数据
                     * @param position 位置
                     */
                    @Override
                    public void onItemBtnAcceptClick(View itemView, Object item, int position) {
                        if (AppClickUtil.isDuplicateClick()) return;
                        Order order = (Order) item;

                        // 获取放单点，如果有就跳转，没有就接单
                        mPresenter.getPutOrderData(order, position);

                        //Order order = (Order) item;
                        //mPresenter.handleUpdate(order.getOrderId(), ACCEPT, order, position, "");
                    }

                    /**
                     * 拒绝
                     * @param itemView view
                     * @param item 数据
                     * @param position 位置
                     */
                    @Override
                    public void onItemBtnRefuseClick(View itemView, final Object item, final int position) {
                        if (AppClickUtil.isDuplicateClick()) return;

                        final Order order = (Order) item;

                        // 拒绝对话框

                        InputCommonDialog dialog = new InputCommonDialog(getContext());
                        dialog.setTitle(UiUtils.getString(R.string.order_reason));
                        dialog.setHit(UiUtils.getString(R.string.please_enter_reason));
                        dialog.setToast(UiUtils.getString(R.string.please_enter_reason_hit));
                        dialog.setMaxLength(50);
                        dialog.setComeBack(value -> mPresenter.handleUpdate(order.getOrderId(), REFUSE, order, position, value, null));
                        dialog.show();
                    }
                });
                break;
            case ACCEPT:
                OrderChildAdapterReceived orderChildAdapterReceived = (OrderChildAdapterReceived) orderChildAdapter;
                orderChildAdapterReceived.setOnItemBtnRegisterClickListener((itemView, item, position) -> {
                    if (AppClickUtil.isDuplicateClick()) return;

                    Order order = (Order) item;

                    if (order.getVehicleFlag() == 1) {
                        //如果是进度更新的就直接跳转
                        if (getParentFragment() instanceof OrderFragment) {
                            mPresenter.getNodes(order.getOrderId(), order.getId());
                        }
                    } else {
                        //如果是现在出车就请求网络
                        mPresenter.outCar(order.getOrderId(), order, position);
                    }
                });
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        DialogUtil.hideDialog();
        super.onDestroy();
    }

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


    @Subscribe
    public void OrderEvent(OrderChildEvent orderChildEvent) {
        switch (orderChildEvent.getType()) {
            case FLAG_REFRESH_PAGE:
                //设置刷新
                if (mType == WAIT)
                    refreshData();
                break;
        }
    }

    /**
     * 下拉刷新初始化
     */
    protected void setSwipeRv() {
        ((ImageView) viewList.findViewById(R.id.ivEmpty)).setImageResource(R.drawable.ic_dd_k);
    }


    /**
     * 描述：获取view
     */

    public View getListView() {
        return View.inflate(getContext(), R.layout.view_swipe_recyclerview, null);
    }

    /**
     * 给子类刷新UI
     */
    public void refreshUi() {
        setOpenLoadMore(true);
    }

    /**
     * 事件订阅者自定义的接收方法
     */
    @Subscribe
    public void onMessageEvent(OrderNodeMapEvent event) {
        if (event.isRefresh()) {
            //设置刷新
            if (mType == ACCEPT || mType == FINISH)
                refreshData();
        }
    }


    /**
     * 更新状态成功
     *
     * @param strMessage 成功信息
     * @param order      后期更新ui
     * @param position   更新的索引
     */
    @Override
    public void showSuccess(String strMessage, Order order, int position) {
        showToast(strMessage);
        //重新读取此条数据
        orderChildAdapter.updateItem(position, order);

        EventBus.getDefault().post(new HomeEvent(FLAG_REFRESH_PROGRESS));       // 刷新首页进度
    }


    /**
     * 接单成功
     *
     * @param strMessage   成功信息
     * @param order        后期更新ui
     * @param position     删除的索引
     * @param fromFragment
     * @param toFragment
     */
    @Override
    public void showAcceptSuccess(String strMessage, Order order, int position, int fromFragment, int toFragment) {
        if (!TextUtils.isEmpty(strMessage)) {
            showToast(strMessage);
        }
        //删除
        if (position != -1) {
            orderChildAdapter.removeItem(position);
        }
        // 如果空了，就显示空图标
        if (orderChildAdapter.getList().isEmpty()) {
            recyclerViewShowEmpty();
        }
        //往已接单那里添加最新一个
//        ((OrderChildFragment) ((OrderFragment) getParentFragment()).mOrderAdapter.getFragment(1)).orderChildAdapter.add(0, order);
//        ((OrderFragment) getParentFragment()).viewPager.setCurrentItem(1);
        OrderFragment orderFragment = findFragment(OrderFragment.class);
        if (orderFragment == null) {
            orderFragment = (OrderFragment) getParentFragment();
        }

        if (orderFragment == null) {
            return;
        }

        ((OrderChildFragment) orderFragment.mOrderAdapter.getFragment(fromFragment)).refreshData();
        orderFragment.viewPager.setCurrentItem(toFragment);
        ((OrderChildFragment) orderFragment.mOrderAdapter.getFragment(toFragment)).refreshData();
    }


    /**
     * 拒绝成功
     *
     * @param strMessage 成功信息
     * @param order      后期更新ui
     * @param position   删除的索引
     */
    @Override
    public void showRefuseSuccess(String strMessage, Order order, int position) {
        showToast(strMessage);
        //删除
        orderChildAdapter.removeItem(position);
        // 如果空了，就显示空图标
        if (orderChildAdapter.getList().isEmpty()) {
            recyclerViewShowEmpty();
        }
        //往已拒绝那里添加最新一个
        if (getParentFragment() != null) {
            ((OrderChildFragment) ((OrderFragment) getParentFragment()).mOrderAdapter.getFragment(REFUSE)).orderChildAdapter.add(0, order);
            ((OrderFragment) getParentFragment()).viewPager.setCurrentItem(REFUSE);
        }
    }


    /**
     * 打开地图节点界面
     *
     * @param value
     * @param id
     */
    @Override
    public void startOrderNodeMapFragment(List<DriverOrderNode> value, String id) {
        //如果最后一个都是完成的
        if (getParentFragment() instanceof OrderFragment && getParentFragment().getParentFragment() != null) {
            ((MainFragment) getParentFragment().getParentFragment()).startBrotherFourResultFragment(OrderNodeMapFragment.newInstance(value, id), UPLOAD_PROCESS);
        }
    }


    /**
     * 删除消息，仅首页使用
     */
    @Override
    public void deleteMessageSuccess(String message) {

    }

    @Override
    public void setReadSuccess(int position) {

    }

    /**
     * 打开放单地点
     *
     * @param order
     * @param infoPutorderPlaces
     * @param position
     */
    @Override
    public void openPutOrderAddress(Order order, ArrayList<InfoPutorderPlace> infoPutorderPlaces, int position) {
        // 跳转到选择存放订单地址
        if (getParentFragment() instanceof OrderFragment && getParentFragment().getParentFragment() != null) {
            ((MainFragment) getParentFragment().getParentFragment()).startBrotherFourResultFragment(PutOrderAddressFragment.newInstance(order, infoPutorderPlaces, position), ORDER_ACCEPT);
        } else if (getParentFragment() != null && getParentFragment().getParentFragment() != null){
            ((MainFragment) getParentFragment()).startBrotherFourResultFragment(PutOrderAddressFragment.newInstance(order, infoPutorderPlaces, position), ORDER_ACCEPT);
        }
    }


    @Subscribe(sticky = true)
    public void mainActivityResult(MainActivityResult result) {
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (requestCode == UPLOAD_PROCESS) {
            refreshData();
        } else if (resultCode == ORDER_DETAIL_REFRESH) {
            showAcceptSuccess("", null, -1, 0, 1);
        } else if (requestCode == ORDER_ACCEPT && data != null && data.getParcelable(ORDER) != null) {
            // 接受订单
            Order order = data.getParcelable(ORDER);
            if (order != null) {
                int position = data.getInt(POSITION);
                String placeId = data.getString(PLACE_ID);
                mPresenter.handleUpdate(order.getOrderId(), ACCEPT, order, position, "", placeId);
            }
        }
        super.onFragmentResult(requestCode, resultCode, data);
    }

    public void refreshData() {
        mPresenter.refreshData(mType);
    }

    protected void loadMoreData() {
        mPresenter.loadMoreData(mType, orderChildAdapter.getList().size());
    }

    /**
     * 点击订单
     *
     * @param itemView view
     * @param item     数据bean
     * @param position 位置
     */
    public void orderClick(View itemView, Object item, int position) {

    }
}
