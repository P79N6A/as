package com.yaoguang.driver.order.map;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yaoguang.appcommon.common.base.BaseFragmentV2;
import com.yaoguang.appcommon.common.eventbus.HomeEvent;
import com.yaoguang.appcommon.common.eventbus.OrderFragmentEvent;
import com.yaoguang.appcommon.common.eventbus.OrderNodeRichTextEvent;
import com.yaoguang.appcommon.common.finalrequest.DriverRequest;
import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.common.appcommon.dialog.DialogHelper;
import com.yaoguang.common.appcommon.dialog.DialogUtil;
import com.yaoguang.common.appcommon.utils.PopupWindowUtils;
import com.yaoguang.common.appcommon.utils.ProgressDialogHelper;
import com.yaoguang.common.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.ConvertUtils;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.R;
import com.yaoguang.driver.order.adapter.MenuDianAdapter;
import com.yaoguang.driver.order.detail.OrderDetailFragment;
import com.yaoguang.driver.order.feedback.add.OrderFeedBackAddFragment;
import com.yaoguang.driver.order.submit.OrderNodeRichTextFragment;
import com.yaoguang.greendao.entity.DriverOrderNode;
import com.yaoguang.interactor.driver.order.DOrderNodeMapPresenterImpl;
import com.yaoguang.interfaces.driver.presenter.order.DOrderNodeMapPresenter;
import com.yaoguang.interfaces.driver.view.order.DOrderNodeMapView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.yaoguang.common.common.Constants.FLAG_REFRESH_PROGRESS;
import static com.yaoguang.driver.App.locationHistoryManager;
import static com.yaoguang.driver.order.OrderFragment.FLAG_TO_TAG;
import static com.yaoguang.driver.order.child.OrderChildFragment.UPLOAD_PROCESS;

/**
 * 节点的地图数据
 * Created by wly on 2017/4/28.
 */
public class OrderNodeMapFragment extends BaseFragmentV2 implements DOrderNodeMapView {
    private static final String NODESBEAN = "NodesBean";
    private static final String DETAIL_ID = "detail_id";
    public static final int TO_NEXT_NOTE = DriverRequest.ORDER_NODE_MAP_FRAGMENT;
    Unbinder unbinder;
    DOrderNodeMapPresenter mDOrderNodeMapPresenter = new DOrderNodeMapPresenterImpl(this);
    List<DriverOrderNode> driverOrderNodes;
    private InitialView mInitialView;
    private String detail_id;
    private boolean isEventBus; // 如果有节点更新，就发送通知
    private PopupWindowUtils mPopupWindowUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            driverOrderNodes = (List<DriverOrderNode>) getArguments().getSerializable(NODESBEAN);
            detail_id = getArguments().getString(DETAIL_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_ordernode_map, null);
        mInitialView = new InitialView(view);

        // 返回键监听
        UiUtils.listenerBackKey(view, () -> {
            if (getTopFragment() instanceof OrderNodeMapFragment) {
                pop();
            }
        });

        if (savedInstanceState != null) {
            driverOrderNodes = (List<DriverOrderNode>) savedInstanceState.getSerializable(NODESBEAN);
            detail_id = savedInstanceState.getString(DETAIL_ID);
        }

        mDOrderNodeMapPresenter.subscribe();
        refreshData();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(NODESBEAN, (Serializable) driverOrderNodes);
        outState.putString(DETAIL_ID, detail_id);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mPopupWindowUtils != null) {
            mPopupWindowUtils.onDestroyPopupView();
        }
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        mInitialView.mapFragment.onDestroyView();
        super.onDestroyView();
    }

    public static OrderNodeMapFragment newInstance(List<DriverOrderNode> driverOrderNodes, String id) {
        OrderNodeMapFragment fragment = new OrderNodeMapFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(NODESBEAN, (Serializable) driverOrderNodes);
        bundle.putString(DETAIL_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static void initAlertLogo(DriverOrderNode driverOrderNodes, TextView alertImage, Context context) {
        String parentName = driverOrderNodes.getParentName();
        if (parentName.startsWith("提")) {
            alertImage.setText("提");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                alertImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_ti_01));
            }
        }
        if (parentName.startsWith("还")) {
            alertImage.setText("还");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                alertImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_huan));
            }
        }
        if (parentName.startsWith("卸")) {
            alertImage.setText("卸");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                alertImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_xie));
            }
        }
        if (parentName.startsWith("装")) {
            alertImage.setText("装");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                alertImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_zhuang));
            }
        }
    }

    public static String handerBtnTile(String title) {
        int center = title.length() / 2;
        String start = title.substring(0, center);
        String end = title.substring(center, title.length());
        return start + "\n" + end;
    }

    /**
     * 更新数据
     *
     * @param driverOrderNodes 节点数据
     */
    @Override
    public void setDriverOrderNodes(List<DriverOrderNode> driverOrderNodes) {
        this.driverOrderNodes = driverOrderNodes;
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (resultCode == TO_NEXT_NOTE) {
            mDOrderNodeMapPresenter.getNodes(driverOrderNodes.get(0).getDriverOrderId());
            locationHistoryManager.checkUploadToServer(null);

            isEventBus = true;
        }
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public void setIsValid() {
//        mNodesBean.setIsValid(1);
    }

    @Override
    public void refreshData() {
        if (driverOrderNodes == null) return;
        // 如果有二个以上就显示更多
        int size = driverOrderNodes.size();
        if (size > 1) {
            mInitialView.tvShowMore.setVisibility(View.VISIBLE);
        } else {
            mInitialView.tvShowMore.setVisibility(View.GONE);
        }
        mInitialView.toolbarTitle.setTextSize(18);
        mInitialView.tvAddress.setText(getAddress(driverOrderNodes.get(0)));
        initAlertLogo(driverOrderNodes.get(0), mInitialView.alertImage, getContext());

        // 节点列表
        String[] titles = driverOrderNodes.get(0).getDriverOrderNodeList().get(0).getRemark().split(",");
        mInitialView.btnNodeTitle.setText(handerBtnTile(titles[1]));
        mInitialView.toolbarTitle.setText(titles[0]);

        mInitialView.menuDianAdapter.appendToList(driverOrderNodes);
        mInitialView.menuDianAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearOldData() {
        mInitialView.menuDianAdapter.getList().clear();
        driverOrderNodes.clear();
    }

    public static String getAddress(DriverOrderNode driverOrderNodes) {
        String add = driverOrderNodes.getAddress();
        if (TextUtils.isEmpty(add)) {
            return driverOrderNodes.getChildName();
        }
        return add;
    }

    @Override
    public void success(BaseResponse<String> value) {
        DialogHelper.showToast(getContext(), value.getResult());
        locationHistoryManager.checkUploadToServer(null);

        isEventBus = true;
    }

    @Override
    public void fail(String message) {
        DialogHelper.showToast(getContext(), message);
    }

    @Override
    public void startForResultOrderNodeRichTextFragment(String id) {
        startForResult(OrderNodeRichTextFragment.newInstance(id), TO_NEXT_NOTE);
    }

    /**
     * 事件订阅者自定义的接收方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OrderNodeRichTextEvent event) {

        if (event.isOK()) {
            //设置完成
//            mNodesBean.setIsValid(1)
            refreshData();
        }
    }

    @Override
    public void switchToNode(String switchToNodeId) {
        mDOrderNodeMapPresenter.switchToNode(driverOrderNodes, driverOrderNodes.get(0).getId(), switchToNodeId);
    }


    @Override
    public void pop() {
        if (isEventBus) {
//            HomeFragment homeFragment = (HomeFragment) getFragmentManager().findFragmentByTag("com.yaoguang.driver.home.HomeFragment");
//            if (homeFragment != null) {
//                homeFragment.startRefresh();
//
//
//            }
            EventBus.getDefault().post(new HomeEvent(FLAG_REFRESH_PROGRESS));       // 刷新首页进度
        }
        super.pop();
    }

    @Override
    public void switchToNodeSuccess() {
        mInitialView.rlHideMore.setVisibility(View.GONE);
        mInitialView.rlNodeDsc.setVisibility(View.VISIBLE);
    }

    /**
     * 描    述：订单完成
     * 作    者：韦理英
     * 时    间：
     */
    @Override
    public void orderFinish() {
        showToast("订单完成");
        isEventBus = true;
        setFragmentResult(UPLOAD_PROCESS, null);
        EventBus.getDefault().post(new OrderFragmentEvent(2, FLAG_TO_TAG));     // 跳转到已完成
        pop();
    }

    @Override
    public BasePresenter getPresenter() {
        return mDOrderNodeMapPresenter;
    }

    public final class InitialView implements Toolbar.OnMenuItemClickListener {
        @BindView(R.id.toolbar_left)
        ImageView toolbarLeft;
        @BindView(R.id.toolbar_title)
        TextView toolbarTitle;
        @BindView(R.id.toolbar_right)
        ImageView toolbarRight;
        @BindView(R.id.toolbar)
        Toolbar toolbar;
        @BindView(R.id.fragmentLayout)
        FrameLayout fragmentLayout;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvMobile)
        TextView tvMobile;
        @BindView(R.id.rlMobile)
        RelativeLayout rlMobile;
        @BindView(R.id.tvNavi)
        TextView tvNavi;
        @BindView(R.id.rlRoadPlan)
        RelativeLayout rlRoadPlan;
        @BindView(R.id.llBottom)
        LinearLayout llBottom;
        @BindView(R.id.alertImage)
        TextView alertImage;
        @BindView(R.id.btnNodeTitle)
        Button btnNodeTitle;
        @BindView(R.id.ll)
        LinearLayout ll;
        @BindView(R.id.tvShowMore)
        TextView tvShowMore;
        @BindView(R.id.rlNodeDsc)
        LinearLayout rlNodeDsc;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;
        @BindView(R.id.rlHideMore)
        RelativeLayout rlHideMore;
        ProgressDialogHelper progressDialogHelper;
        private MenuDianAdapter menuDianAdapter;
        private MapFragment mapFragment;

        InitialView(View view) {
            unbinder = ButterKnife.bind(this, view);

            progressDialogHelper = new ProgressDialogHelper(getContext());

            initView();
            initListener();
        }

        void initView() {
            mapFragment = MapFragment.newInstance(false);
            getChildFragmentManager().beginTransaction().add(R.id.fragmentLayout, mapFragment).show(mapFragment).commit();

            menuDianAdapter = new MenuDianAdapter(getContext());
            recyclerView.setAdapter(menuDianAdapter);

            toolbarTitle.setTextSize(ConvertUtils.dp2px(14));
            tvMobile.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{driverOrderNodes.get(0).getRemark()});
            });
            // 显示菜单
            toolbarLeft.setOnClickListener(v -> pop());
            toolbar.inflateMenu(R.menu.driver_liebiao);
            toolbar.setOnMenuItemClickListener(this);

            RecyclerViewUtils.initPage(recyclerView, menuDianAdapter, null, getContext(), true);
            rlHideMore.setVisibility(View.GONE);
        }

        void initListener() {
            //确认下一步
            btnNodeTitle.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick() || getActivity() == null) return;

                RxPermissions permissions = new RxPermissions(getActivity());
                permissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(aBoolean -> {
                            if (aBoolean) {
                                // 如果工作单号有2个，就要弹出对话框选择一个
                                int wordIdSize = driverOrderNodes.get(0).getDriverOrderNodeList().size();

                                if (wordIdSize > 1) {
                                    final String[] items = new String[]{driverOrderNodes.get(0).getDriverOrderNodeList().get(0).getCurrentOrder(), driverOrderNodes.get(0).getDriverOrderNodeList().get(1).getCurrentOrder()};
                                    DialogUtil.showSingleChoiceDialog(getContext(), items, "先处理哪个工作单号", (dialogInterface, i) -> {
                                        mDOrderNodeMapPresenter.setNodeNext(driverOrderNodes.get(0).getDriverOrderNodeList().get(i));
                                        dialogInterface.dismiss();
                                    });
                                } else {
                                    mDOrderNodeMapPresenter.setNodeNext(driverOrderNodes.get(0).getDriverOrderNodeList().get(0));
                                }
                            } else {
                                Toast.makeText(getActivity(), "需要打开手机定位权限", Toast.LENGTH_SHORT).show();
                            }
                        });
            });
            tvShowMore.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                rlNodeDsc.setVisibility(View.GONE);
                rlHideMore.setVisibility(View.VISIBLE);
            });
            rlHideMore.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                rlNodeDsc.setVisibility(View.VISIBLE);
                rlHideMore.setVisibility(View.GONE);
            });
            tvNavi.setOnClickListener(v -> {
                if (AppClickUtil.isDuplicateClick()) return;

                InitialView.this.autoNav(getAddress(driverOrderNodes.get(0)));
            });

            menuDianAdapter.setOnItemClickListener((itemView, item, position) -> {
                if (AppClickUtil.isDuplicateClick()) return;

                DriverOrderNode node = (DriverOrderNode) item;
                switchToNode(node.getId());
            });
            menuDianAdapter.setRoadPlan(new MenuDianAdapter.RoadPlan() {
                @Override
                public void result(String address) {    //  自动导航
                    if (AppClickUtil.isDuplicateClick()) return;

                    autoNav(address);
                }

                @Override
                public void callPhone(String phone) {   //  打电话
                    if (AppClickUtil.isDuplicateClick()) return;

                    PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{phone});
                }
            });
        }

        private void autoNav(String address) {
            start(MapSearchFragment.newInstance(address));
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (AppClickUtil.isDuplicateClick()) return false;

            View popupView = View.inflate(getContext(), R.layout.popupwindow1_showasdropdown, null);
            popupView.findViewById(R.id.tvOrderDetail).setOnClickListener(v2 -> {

                mPopupWindowUtils.onDestroyPopupView();
                start(OrderDetailFragment.newInstance(detail_id));
            });
            popupView.findViewById(R.id.tvFaultFeedback).setOnClickListener(v2 -> {

                mPopupWindowUtils.onDestroyPopupView();
                start(OrderFeedBackAddFragment.newInstance(
                        driverOrderNodes.get(0).getDriverOrderNodeList().get(0).getDriverOrderId(),
                        driverOrderNodes.get(0).getDriverOrderNodeList().get(0).getId(),
                        driverOrderNodes.get(0).getDriverOrderNodeList().get(0).getNodeName()));
            });
            mPopupWindowUtils = new PopupWindowUtils();
            mPopupWindowUtils.init(getActivity(), popupView);
            mPopupWindowUtils.showAsDropDown(toolbarRight, -70, 0);
            return false;
        }
    }
}
