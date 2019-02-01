package com.yaoguang.driver.phone.order.node;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.yaoguang.appcommon.databinding.FragmentOrderNode2PortBinding;
import com.yaoguang.appcommon.phone.node.BaseNodeFragment;
import com.yaoguang.driver.R;
import com.yaoguang.appcommon.phone.node.noderichtext.OrderNodeRichTextFragement;
import com.yaoguang.driver.phone.main.MainActivity;
import com.yaoguang.driver.phone.order.detail.OrderDetailFragment;
import com.yaoguang.driver.phone.order.feedback.OrderFeedBackListFragment;
import com.yaoguang.driver.phone.order.feedback.add.OrderFeedBackAddFragment;
import com.yaoguang.driver.phone.order.nodeEdit.NodeEditFragment;
import com.yaoguang.greendao.entity.driver.DriverOrderMergeNodeWrapper;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

/**
 * 当前任务进度
 * Created by zhongjh on 2019/1/8.
 */
public class NodeFragment extends BaseNodeFragment<FragmentOrderNode2PortBinding> {

    public static NodeFragment newInstance(String orderId) {
        NodeFragment nodeFragment = new NodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("orderId", orderId);
        nodeFragment.setArguments(bundle);
        return nodeFragment;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbarNav(mViewHolder.toolbar, "当前任务进度", R.menu.menu_node, this);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case OrderNodeRichTextFragement.REQUEST:
                    // 刷新
                    this.refreshDataAnimation();
                    break;
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_feedback:
                // 反馈记录
                startForResult(OrderFeedBackListFragment.newInstance(mOrderId, 0), -1);
                // 关闭重力感应
                sm.unregisterListener(listener);
                break;
            case R.id.action_order_detail:
                // 订单明细
                startForResult(OrderDetailFragment.newInstance(mOrderId, false, false), -1);
                // 关闭重力感应
                sm.unregisterListener(listener);
                break;
        }
        return super.onMenuItemClick(item);
    }

    @Override
    public void initListener() {
        super.initListener();
        // 问题反馈
        mViewHolder.tvProblemFeedback.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                startForResult(OrderFeedBackAddFragment.newInstance(
                        mOrderId,
                        "",
                        "故障反馈"), -1);
                // 关闭重力感应
                sm.unregisterListener(listener);
            }
        });

        // 调整路线
        mViewHolder.btnAdjustRoute.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                startForResult(NodeEditFragment.newInstance(mOrderId, mClientId), 2);
                // 关闭重力感应
                sm.unregisterListener(listener);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void refreshingFootView(int orderStatus, String clientId) {
        mOrderStatus = orderStatus;
        mClientId = clientId;
        // 状态为1就显示底部栏并且激活围栏，否则不显示也不激活
        if (orderStatus == 1) {
            // 判断是否有节点正在执行
            boolean isNode = false;
            // 获取正在执行的节点
            for (int i = 0; i < mBaseLoadMoreRecyclerAdapter.getList().size(); i++) {
                DriverOrderMergeNodeWrapper driverOrderMergeNodeWrapper = mBaseLoadMoreRecyclerAdapter.getList().get(i);
                if (driverOrderMergeNodeWrapper.getFinishStatus().equals("1") || driverOrderMergeNodeWrapper.getFinishStatus().equals("2")) {

                    // 列表滑到当前正在执行的节点
                    mViewHolder.rlView.scrollToPosition(i);

                    mDriverOrderMergeNodeWrapper = driverOrderMergeNodeWrapper;
                    isNode = true;
                    // 判断是到达还是离开,true 正在前往 false 正在离开
                    boolean isGetInto = false;
                    String getInfo = "";
                    String strGetInfo = "";
                    switch (driverOrderMergeNodeWrapper.getFinishStatus()) {
                        case "1":
                            isGetInto = true;
                            getInfo = "到达";
                            strGetInfo = "前往";
                            break;
                        case "2":
                            isGetInto = false;
                            getInfo = "离开";
                            strGetInfo = "离开";
                            break;
                    }

                    // 开启实时定位 // 如果是一个的，选择是否在围栏以内，如果在，就是您已进入XXX，否则是正在前往下一个地点
                    if (driverOrderMergeNodeWrapper.getNodes() != null && driverOrderMergeNodeWrapper.getNodes().size() > 0)
                        ((MainActivity) _mActivity).addGeoFence(driverOrderMergeNodeWrapper.getNodes(), isGetInto, mOrderId);

                    if (!driverOrderMergeNodeWrapper.getName().equals("") && mIsTTs) {
                        // 语音
                        ((MainActivity) _mActivity).mTtsManager.onGetNavigationText("正在" + strGetInfo + driverOrderMergeNodeWrapper.getName());
                    }
                    mIsTTs = true;

                    mViewHolder.tvTitle.setText("正在" + strGetInfo + " " + driverOrderMergeNodeWrapper.getName());
                    // 确认是提柜还柜还是到达离开门点
                    switch (driverOrderMergeNodeWrapper.getNodeType()) {
                        case "0":
                            mViewHolder.btnNext.setText("出车");
                            break;
                        case "1":
                            mViewHolder.btnNext.setText(getInfo + "提柜点");
                            break;
                        case "2":
                            mViewHolder.btnNext.setText(getInfo + "门点");
                            break;
                        case "3":
                            mViewHolder.btnNext.setText(getInfo + "还柜点");
                            break;
                        case "4":
                            mViewHolder.btnNext.setText("完成");
                            break;
                    }
                    break;
                }
            }
            if (!isNode) {
                // 隐藏底部
                if (mViewHolder.activity_cardview != null)
                    mViewHolder.activity_cardview.setVisibility(View.GONE);
                else
                    mViewHolder.activity_description_content.setVisibility(View.GONE);
            } else {
                // 显示底部
                if (mViewHolder.activity_cardview != null)
                    mViewHolder.activity_cardview.setVisibility(View.VISIBLE);
                else
                    mViewHolder.activity_description_content.setVisibility(View.VISIBLE);
            }
        } else {
            // 隐藏底部
            if (mViewHolder.activity_cardview != null)
                mViewHolder.activity_cardview.setVisibility(View.GONE);
            else
                mViewHolder.activity_description_content.setVisibility(View.GONE);
        }


    }

}
