package com.yaoguang.driver.order.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.yaoguang.common.appcommon.dialog.CommonDialog;
import com.yaoguang.common.appcommon.dialog.DialogHelper;
import com.yaoguang.common.appcommon.utils.AllSelectDelete;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentOrderChildBinding;
import com.yaoguang.driver.order.adapter.MessageOrderChildAdapterWait;
import com.yaoguang.driver.order.child.OrderChildPresenter;
import com.yaoguang.driver.order.child.OrderChildFragment;
import com.yaoguang.driver.order.detail.OrderDetailFragment;
import com.yaoguang.greendao.entity.Order;

import java.util.List;

import static com.yaoguang.driver.my.my.MyFragment.REFRESH_UNREAD_COUNT;


/**
 * 订单查询的子fragment
 * 分为4种
 * Created by wly on 2017/4/13.
 */
public class MessageOrderChildFragment extends OrderChildFragment<Order, OrderChildPresenter, FragmentOrderChildBinding> {
    private AllSelectDelete mAllSelectDelete;
    private DialogHelper mDialogHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderChildAdapter = new MessageOrderChildAdapterWait(getContext());
    }

    /**
     * 实例化
     *
     * @param type 判断类型,0 "已接单", 1 "待接单", 2"已完成", 3"已关闭"
     * @return 实例化
     */
    public static MessageOrderChildFragment newInstance(int type) {
        MessageOrderChildFragment fragment = new MessageOrderChildFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void refreshUi() {
        toolbarCommonBinding.toolbar.setVisibility(View.VISIBLE);
        toolbarCommonBinding.toolbarTitle.setText(R.string.bussiness_message);

        orderChildAdapter.notifyDataSetChanged();

        // 删除
        mDataBinding.llSelectDelete2.tvDelete.setVisibility(View.VISIBLE);
        mAllSelectDelete = new AllSelectDelete(orderChildAdapter, mDataBinding.llSelectDelete2.tvSelect,
                mDataBinding.llSelectDelete2.tvDelete,
                mDataBinding.llSelectDelete2.llSelectDelete,
                toolbarCommonBinding.toolbar, () -> {
            if (mAllSelectDelete.isAllSelect) {  // 全选删除
                mAllSelectDelete.selectDeleteIds.clear();
                mAllSelectDelete.selectDeletePos.clear();

                for (int i = 0; i < orderChildAdapter.getList().size(); i++) {
                    Order order = orderChildAdapter.getList().get(i);
                    mAllSelectDelete.selectDeleteIds.add(order.getDriverOrderMsg().getId());
                    mAllSelectDelete.selectDeletePos.add(i);
                }

            } // 部分删除 selectDeleteIds 和selectDeletePos 已经存在，不需要在处理，直接提交服务器

            if (mAllSelectDelete.selectDeleteIds.isEmpty()) {
                return;
            }

            if (mDialogHelper != null) {
                mDialogHelper.hideDialog();
            }

            mDialogHelper = new DialogHelper();
            mDialogHelper.showConfirmDialog(getContext(), "提示",
                    "是否删除消息？", "是的", "我再想想", new CommonDialog.Listener() {
                        @Override
                        public void ok() {

                            mPresenter.submitDeleteMessages(mAllSelectDelete.selectDeleteIds);
                            mDialogHelper.hideDialog();
                        }

                        @Override
                        public void cancel() {

                        }
                    });
        });
        super.refreshUi();
    }


    /**
     * 业务消息 删除消息
     */
    public void initListener() {
        orderChildAdapter.setOnRecyclerViewItemClickSelectListener((checkBox, item, position) -> {
            if (mAllSelectDelete.isEdit) {  // 删除选择
                Order order = (Order) item;
                mAllSelectDelete.selectItem(checkBox, order.getDriverOrderMsg().getId(), position);
            } else {    // 查看
                Order order = (Order) item;
                if (order.getDriverOrderMsg().getFlag() == 0) {
                    mPresenter.readBatch(order.getDriverOrderMsg().getId(), position);
                }

                start(OrderDetailFragment.newInstance(order.getId()));
            }
        });

        toolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());

        super.initListener();
    }

    @Override
    public void recyclerViewShowEmpty() {
        if (mAllSelectDelete != null) {
            mAllSelectDelete.initMenu(null);
        }
        ((ImageView) getView().findViewById(R.id.ivEmpty)).setImageResource(R.drawable.ic_xx_k);
        super.recyclerViewShowEmpty();
    }

    @Override
    public void refreshAdapter(List<Order> list, boolean isHas) {
        if (mAllSelectDelete != null) {
            mAllSelectDelete.initMenu(list);
        }
        super.refreshAdapter(list, isHas);
    }

    public void refreshData() {
        // 编辑状态不能刷新数据
        if (!mAllSelectDelete.isEdit) {
            mPresenter.refreshData(mType, false);
        } else {
            finishRefreshing();
        }
    }

    protected void loadMoreData() {
        mPresenter.loadMoreData(mType, orderChildAdapter.getList().size(), false);
    }

    /**
     * 设置未读成功
     *
     * @param position 订单位置
     */
    @Override
    public void setReadSuccess(int position) {
        try {
            orderChildAdapter.getList().get(position).getDriverOrderMsg().setFlag(1);
            orderChildAdapter.notifyDataSetChanged();

            // 通知刷新
            setFragmentResult(REFRESH_UNREAD_COUNT, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 描    述：删除消息成功
     * 作    者：韦理英
     * 时    间：
     *
     * @param message [提示]
     */
    @Override
    public void deleteMessageSuccess(String message) {
        showToast(message);
        if (!mAllSelectDelete.isAllSelect) { // 如果不是全选，一个一个移除
            for (Integer selectPo : mAllSelectDelete.selectDeletePos) {
                try {
                    orderChildAdapter.removeItem(selectPo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {    // 如果是全选一次全清了
            orderChildAdapter.getList().clear();
            orderChildAdapter.notifyDataSetChanged();
        }

        // 通知刷新
        setFragmentResult(REFRESH_UNREAD_COUNT, null);

        mAllSelectDelete.selectDeleteIds.clear();
        mAllSelectDelete.selectDeletePos.clear();
        refreshData();
    }

    @Override
    public void orderClick(View itemView, Object item, int position) {
        Order order = (Order) item;
        mPresenter.readBatch(order.getId(), position);
        super.orderClick(itemView, item, position);
    }

    @Override
    public void onDestroyView() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
            mDialogHelper = null;
        }
        super.onDestroyView();
    }
}
