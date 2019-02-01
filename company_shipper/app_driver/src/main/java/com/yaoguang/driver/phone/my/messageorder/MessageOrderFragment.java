package com.yaoguang.driver.phone.my.messageorder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.appcommon.phone.my.my.event.MyEvent;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentOrderChildBinding;
import com.yaoguang.driver.phone.main.MainFragment;
import com.yaoguang.driver.phone.order.OrderFragment;
import com.yaoguang.driver.phone.order.detail.OrderDetailFragment;
import com.yaoguang.greendao.entity.driver.DriverOrderMsgWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.utils.AllSelectDelete;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.ObjectUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.REFRESH_UNREAD_COUNT;

/**
 * 业务消息
 * Created by zhongjh on 2018/3/18.
 */
public class MessageOrderFragment extends BaseFragmentListConditionDataBind<DriverOrderMsgWrapper, Integer, MessageOrderAdapter, FragmentOrderChildBinding> implements MessageOrderContact.View {

    protected MessageOrderContact.Presenter mPresenter;
    private AllSelectDelete mAllSelectDelete;
    private DialogHelper mDialogHelper;

    public static MessageOrderFragment newInstance() {
        return new MessageOrderFragment();
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        //列表初始化
        return new MessageOrderAdapter(getContext());
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public Integer getCondition(boolean isRegain) {
        return null;
    }

    @Override
    public void setConditionView(Integer condition) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_child;
    }

    @Override
    public void init() {
        mPresenter = new MessageOrderPresenter(this, getContext());

        mLayoutRecyclerviewBinding.loading.setEmptyImage(R.drawable.ic_xx_k);

        // 删除
        if (mDataBinding.llSelectDelete2 != null) {
            mDataBinding.llSelectDelete2.tvDelete.setVisibility(View.GONE);
        }

        mToolbarCommonBinding.getRoot().setVisibility(View.VISIBLE);
        mToolbarCommonBinding.toolbarTitle.setText(R.string.bussiness_message);

        mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();

        // 删除
        mDataBinding.llSelectDelete2.tvDelete.setVisibility(View.VISIBLE);
        mAllSelectDelete = new AllSelectDelete(mBaseLoadMoreRecyclerAdapter, mDataBinding.llSelectDelete2.tvSelect,
                mDataBinding.llSelectDelete2.tvDelete,
                mDataBinding.llSelectDelete2.llSelectDelete,
                mToolbarCommonBinding.toolbar, () -> {
            if (mAllSelectDelete.isAllSelect) {  // 全选删除
                mAllSelectDelete.selectDeleteIds.clear();
                mAllSelectDelete.selectDeletePos.clear();

                for (int i = 0; i < mBaseLoadMoreRecyclerAdapter.getList().size(); i++) {
                    DriverOrderMsgWrapper driverOrderMsgWrapper = mBaseLoadMoreRecyclerAdapter.getList().get(i);
                    mAllSelectDelete.selectDeleteIds.add(driverOrderMsgWrapper.getId());
                    mAllSelectDelete.selectDeletePos.add(i);
                }

            }
            // 部分删除 selectDeleteIds 和selectDeletePos 已经存在，不需要在处理，直接提交服务器
            if (mAllSelectDelete.selectDeleteIds.isEmpty()) {
                return;
            }

            if (mDialogHelper == null)
                mDialogHelper = new DialogHelper(getContext(), "提示",
                        "是否删除消息？", "是的", "我再想想", new CommonDialog.Listener() {
                    @Override
                    public void ok(String content) {
                        mPresenter.submitDeleteMessages(mAllSelectDelete.selectDeleteIds);
                    }

                    @Override
                    public void cancel() {

                    }
                });
            mDialogHelper.show();
        });

    }

    @Override
    public void initListener() {
        mBaseLoadMoreRecyclerAdapter.setOnRecyclerViewItemClickSelectListener((checkBox, item, position) -> {
            if (mAllSelectDelete.isEdit) {
                // 删除选择
                DriverOrderMsgWrapper driverOrderMsgWrapper = (DriverOrderMsgWrapper) item;
                mAllSelectDelete.selectItem(checkBox, driverOrderMsgWrapper.getId(), position);
            } else {
                // 查看
                DriverOrderMsgWrapper driverOrderMsgWrapper = (DriverOrderMsgWrapper) item;
                // 设置已读
                if (driverOrderMsgWrapper.getFlag().equals("0")) {
                    mPresenter.readBatch(driverOrderMsgWrapper.getId(), position);
                }

                start(OrderDetailFragment.newInstance(driverOrderMsgWrapper.getDriverOrderWrapper().getOrderId(), true, true));
            }
        });

        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());

        // 电话
        mBaseLoadMoreRecyclerAdapter.setCallPhone(driverOrderWrapper -> {
            String phone;
            if (!TextUtils.isEmpty(driverOrderWrapper.getMobile())) {
                phone = driverOrderWrapper.getPhone() + "," + driverOrderWrapper.getMobile();
            } else {
                phone = driverOrderWrapper.getPhone();
            }

            PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), phone.split(","));
        });
        // 列表跳转详情事件
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            if (AppClickUtil.isDuplicateClick()) return;

            DriverOrderMsgWrapper driverOrderMsgWrapper = (DriverOrderMsgWrapper) item;
            if (getParentFragment() instanceof OrderFragment && getParentFragment().getParentFragment() != null)
                ((MainFragment) getParentFragment().getParentFragment()).start(OrderDetailFragment.newInstance(driverOrderMsgWrapper.getDriverOrderWrapper().getOrderId(), true, true), SINGLETOP);
            else if (getParentFragment() instanceof MainFragment)
                ((MainFragment) getParentFragment()).start(OrderDetailFragment.newInstance(driverOrderMsgWrapper.getDriverOrderWrapper().getOrderId(), true, true), SINGLETOP);

            mPresenter.readBatch(driverOrderMsgWrapper.getDriverOrderWrapper().getId(), position);
        });

    }

    @Override
    public void recyclerViewShowEmpty(boolean isShowRecyclerView) {
        super.recyclerViewShowEmpty(isShowRecyclerView);
        if (mAllSelectDelete != null) {
            mAllSelectDelete.initMenu(null);
        }
    }

    @Override
    public void refreshAdapter(List<DriverOrderMsgWrapper> list, boolean isHas) {
        if (mAllSelectDelete != null) {
            mAllSelectDelete.initMenu(list);
        }
        super.refreshAdapter(list, isHas);
    }

    @Override
    public void refreshData() {
        super.refreshData();
        // 编辑状态不能刷新数据
        if (mAllSelectDelete.isEdit) {
            finishRefreshing();
        }
    }

    @Override
    public void deleteMessageSuccess(String msg) {
        showToast(msg);
        if (!mAllSelectDelete.isAllSelect) {
            // 如果不是全选，一个一个移除
            for (Integer selectPo : mAllSelectDelete.selectDeletePos) {
                try {
                    mBaseLoadMoreRecyclerAdapter.removeItem(selectPo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            // 如果是全选一次全清了
            mBaseLoadMoreRecyclerAdapter.getList().clear();
            mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();
        }

        // 通知刷新
        // 通知刷新
        EventBus.getDefault().postSticky(new MyEvent(ObjectUtils.parseString(REFRESH_UNREAD_COUNT)));

        mAllSelectDelete.selectDeleteIds.clear();
        mAllSelectDelete.selectDeletePos.clear();
        refreshData();
    }

    @Override
    public void setReadSuccess(int position) {
        try {
            mBaseLoadMoreRecyclerAdapter.getList().get(position).setFlag("1");
            mBaseLoadMoreRecyclerAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
