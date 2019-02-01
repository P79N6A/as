package com.yaoguang.appcommon.phone.order.orderdetailnode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.basemap.BaseHistoryMapFragment;
import com.yaoguang.appcommon.common.eventbus.OrderDetailNodeEvent;
import com.yaoguang.appcommon.phone.order.adapter.OrderDetailNodesAdapter;
import com.yaoguang.greendao.entity.LatLons;
import com.yaoguang.greendao.entity.NodesBean;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import ezy.ui.layout.LoadingLayout;


/**
 * 韦理英
 * 订单明细的节点
 * <p>
 * update zhongjh
 * data 2018-03-25
 */
public abstract class OrderDetailNodeFragment extends BaseHistoryMapFragment implements OrderDetailNodeContract.View {

    protected static final String DRIVER_ORDER = "driverOrderId";
    protected static final String ORDERSN = "orderSn";
    protected static final String LOADINGTYPE = "loadingType";
    protected static final String PCSONOID = "pcSonoId";

    InitialView mInitialView;
    OrderDetailNodesAdapter orderDetailNodesAdapter;

    private OrderDetailNodeContract.Presenter mPresenter = new OrderDetailNodePresenter(this);

    @NonNull
    private String driverOrderId;
    private String orderSn;
    private String loadingType; // 装货是0 卸货是1
    private String pcSonoId;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_detail_node;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        mInitialView = new InitialView(view);
    }

    @Override
    public void init() {
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mPresenter.activityData(driverOrderId, orderSn, loadingType, pcSonoId);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        driverOrderId = ObjectUtils.parseString(getArguments().get(DRIVER_ORDER));
        orderSn = ObjectUtils.parseString(getArguments().get(ORDERSN));
        loadingType = getArguments().getString(LOADINGTYPE, "");
        pcSonoId = ObjectUtils.parseString(getArguments().get(PCSONOID));
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setLocationButton(false);
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
    public void recyclerViewShowError(String strMessage) {
        mInitialView.loading.showError();
        mInitialView.map.setVisibility(View.GONE);
    }

    @Override
    public void recyclerViewShowEmpty() {
        orderDetailNodesAdapter.clear();
        orderDetailNodesAdapter.notifyDataSetChanged();

        mInitialView.loading.showEmpty();
        mInitialView.map.setVisibility(View.GONE);
    }

    public BasePresenter getPresenter() {
        return mPresenter;
    }

    /**
     * 事件订阅者自定义的接收方法
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OrderDetailNodeEvent event) {

        if (event.isRefresh()) {
            //设置刷新
            orderDetailNodesAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void showList(List<NodesBean> mDriverOrderNode) {
        mInitialView.map.setVisibility(View.VISIBLE);
        mInitialView.recyclerView.setVisibility(View.VISIBLE);
        mInitialView.loading.showContent();
        orderDetailNodesAdapter.appendToList(mDriverOrderNode);
        orderDetailNodesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLocalHistoryList(List<LatLons> latLonsBaseResponse) {
        setHistoryLatLngs(latLonsBaseResponse);
    }

    public class InitialView {
        public View rootView;
        public ImageView imgReturn;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public AppBarLayout app_bar;
        public TextureMapView map;
        public RecyclerView recyclerView;
        public LoadingLayout loading;

        InitialView(View view) {
            initView(view);
        }

        void initView(View rootView) {
            this.rootView = rootView;
            this.imgReturn = (ImageView) rootView.findViewById(R.id.imgReturn);
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            this.app_bar = (AppBarLayout) rootView.findViewById(R.id.appBar);
            this.map = (TextureMapView) rootView.findViewById(R.id.map);
            this.recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
            this.loading = (LoadingLayout) rootView.findViewById(R.id.loading);

            map.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            recyclerView.setBackgroundColor(UiUtils.getColor(R.color.white));

            if (loadingType.equals("0")) {
                toolbar_title.setText("装货动态");
            } else if (loadingType.equals("1")) {
                toolbar_title.setText("卸货动态");
            } else {
                toolbar_title.setText(R.string.zhaixiedongtai);
            }

            imgReturn.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    setFragmentResult(RESULT_OK, null);
                    pop();
                }
            });

            //列表初始化
            orderDetailNodesAdapter = new OrderDetailNodesAdapter();
            recyclerView.setAdapter(orderDetailNodesAdapter);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            orderDetailNodesAdapter.setOnItemClickListener((itemView, item, position) -> {
                if (AppClickUtil.isDuplicateClick()) return;

                NodesBean nodesBean = (NodesBean) item;
                startOrderFeedBackListFragment(nodesBean);
            });
            orderDetailNodesAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void initMapFinish() {

    }

    @Override
    public void onCameraChangeFinish(LatLng latLng) {

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result) {

    }


    protected abstract void startOrderFeedBackListFragment(NodesBean nodesBean);

}
