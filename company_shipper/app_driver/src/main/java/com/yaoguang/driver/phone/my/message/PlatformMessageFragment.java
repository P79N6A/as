package com.yaoguang.driver.phone.my.message;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.yaoguang.appcommon.common.eventbus.HomeEvent;
import com.yaoguang.appcommon.common.eventbus.SysMessageEvent;
import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.appcommon.phone.my.my.event.MyEvent;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentMessageBinding;
import com.yaoguang.driver.databinding.ViewDeleteBinding;
import com.yaoguang.driver.phone.my.message.adapter.MessageAdapter;
import com.yaoguang.driver.util.HtmlSwipeFragment;
import com.yaoguang.greendao.entity.common.SysMsg;
import com.yaoguang.lib.BuildConfig;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.utils.AllSelectDelete;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.ObjectUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.REFRESH_UNREAD_COUNT;
import static com.yaoguang.lib.common.constants.Constants.FLAG_REFRESH_PAGE;
import static com.yaoguang.lib.common.constants.Constants.FLAG_UNREAD_NUM;

/**
 * 平台公告
 * Created by 韦理英
 * on 2017/7/6.
 * <p>
 * update zhongjh
 * data 2018/3/19
 */
public class PlatformMessageFragment extends BaseFragmentListConditionDataBind<SysMsg, String, MessageAdapter, FragmentMessageBinding> implements PlatformMessageContacts.View {

    PlatformMessageContacts.Presenter mPresenter;


    public static final String WEB_URL = BuildConfig.ENDPOINT + "page/msg/msg_detail.jsp?id=";
    public List<SysMsg> data = new ArrayList<>();
    /**
     * 选择忽略消息的id
     */
    public HashSet<String> selectIgnoreIds = new HashSet<>();
    /**
     * 选择忽略消息的列表位置
     */
    public HashSet<Integer> selectIgnorePos = new HashSet<>();

    public RecyclerView recyclerView;
    /**
     * 全选删除工具
     */
    protected AllSelectDelete mAllSelectDelete;
    /**
     * 对话框
     */
    private DialogHelper mDialogHelperDelete;
    private DialogHelper mDialogHelperIgone;
    private DialogHelper mDialogHelperIgoneAll;

    ViewDeleteBinding mViewDelete;

    /**
     * 实例化
     *
     * @return this
     */
    public static PlatformMessageFragment newInstance() {
        PlatformMessageFragment platformMessageFragment = new PlatformMessageFragment();
        Bundle bundle = new Bundle();
        platformMessageFragment.setArguments(bundle);
        return platformMessageFragment;
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new MessageAdapter();
    }

    @Override
    public void init() {
        mPresenter = new PlatformMessagePresenter(this);

        mLayoutRecyclerviewBinding.loading.setEmptyImage(R.drawable.ic_xx_k);

        mViewDelete = DataBindingUtil.bind(mDataBinding.getRoot().findViewById(R.id.viewDelete));

        mToolbarCommonBinding.toolbar.setVisibility(View.VISIBLE);

        // 全选，反选设置
        mAllSelectDelete = new AllSelectDelete(mBaseLoadMoreRecyclerAdapter, mViewDelete.tvSelect, mViewDelete.tvDelete, mViewDelete.llSelectDelete, mToolbarCommonBinding.toolbar, () -> {
            if (mAllSelectDelete.isAllSelect) {
                // 全选删除
                mAllSelectDelete.selectDeleteIds.clear();
                mAllSelectDelete.selectDeletePos.clear();

                for (int i = 0; i < mBaseLoadMoreRecyclerAdapter.getList().size(); i++) {
                    SysMsg messageInfo = (SysMsg) mBaseLoadMoreRecyclerAdapter.getList().get(i);
                    mAllSelectDelete.selectDeleteIds.add(messageInfo.getId());
                    mAllSelectDelete.selectDeletePos.add(i);
                }


            }
            // 部分删除 selectDeleteIds 和selectDeletePos 已经存在，不需要在处理，直接提交服务器
            if (mAllSelectDelete.selectDeleteIds.isEmpty()) {
                return;
            }
            if (mDialogHelperDelete == null)
                mDialogHelperDelete = new DialogHelper(getContext(), "提示",
                        "是否删除消息？", "是的", "我再想想", new CommonDialog.Listener() {
                    @Override
                    public void ok(String content) {
                        // 删除
                        mPresenter.deleteMessage(ObjectUtils.subString(mAllSelectDelete.selectDeleteIds));
                    }

                    @Override
                    public void cancel() {

                    }
                });
            mDialogHelperDelete.show();
        });

        mToolbarCommonBinding.toolbarTitle.setText(R.string.platform_message);
    }

    @Override
    public void initListener() {
        // 返回
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());
        // 首页点击监听
        mBaseLoadMoreRecyclerAdapter.setOnRVItemClickListener(new MessageAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object item, int position) {
                if (AppClickUtil.isDuplicateClick()) return;

                // 查看
                SysMsg msg = (SysMsg) item;
                if (msg.getFlag().equals("0")) {
                    EventBus.getDefault().post(new HomeEvent(FLAG_UNREAD_NUM));
                    mPresenter.setMessageReadState(msg.getId(), position);
                }
                openMessageInfo(msg.getId());
            }

            @Override
            public void onItemIgnoreClick(View itemView, final Object item, final int position) {
                // 忽略
                if (mDialogHelperIgone == null)
                    mDialogHelperIgone = new DialogHelper(getContext(), "",
                            "是否忽略此条信息？", "是的", "我再想想", new CommonDialog.Listener() {
                        @Override
                        public void ok(String content) {
                            SysMsg msg = (SysMsg) item;

                            selectIgnoreIds.clear();
                            selectIgnorePos.clear();
                            selectIgnoreIds.add(msg.getId());
                            selectIgnorePos.add(position);
//                                ignore(mBaseLoadMoreRecyclerAdapter, selectIgnoreIds, selectIgnorePos);
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                mDialogHelperIgone.show();
            }

            /**
             * 打电话
             * @param phone 手机号
             */
            @Override
            public void callPhone(String phone) {
                PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{phone});
            }

        });

        //非首页点击监听
        mBaseLoadMoreRecyclerAdapter.setOnRecyclerViewItemClickSelectListener((checkBox, item, position) -> {
            if (mAllSelectDelete.isEdit) {
                // 删除选择
                SysMsg messageInfo = (SysMsg) item;
                mAllSelectDelete.selectItem(checkBox, messageInfo.getId(), position);
            } else {
                // 查看
                SysMsg msg = (SysMsg) item;
                if (msg.getFlag().equals("0")) {
                    EventBus.getDefault().post(new HomeEvent(FLAG_UNREAD_NUM));
                    mPresenter.setMessageReadState(msg.getId(), position);
                }
                openMessageInfo(msg.getId());
            }
        });
    }

    @Override
    public String getCondition(boolean isRegain) {
        return null;
    }

    @Override
    public void setConditionView(String condition) {

    }

//    public void ignore(MessageAdapter mMessageAdapter, HashSet<String> selectIgnoreIds, HashSet<Integer> selectIgnorePos) {
//
//    }

    @Override
    public void recyclerViewShowEmpty(boolean isShowRecyclerView) {
        if (mAllSelectDelete != null) {
            mAllSelectDelete.initMenu(null);
        }
        super.recyclerViewShowEmpty(isShowRecyclerView);
    }

    @Override
    public void refreshAdapter(List list, boolean isHas) {
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

    @Subscribe
    public void SysMessageEvent(SysMessageEvent event) {
        switch (event.getType()) {
            case "忽略所有":
                if (mBaseLoadMoreRecyclerAdapter.getList().isEmpty() || (mDialogHelperIgoneAll != null && mDialogHelperIgoneAll.isShow())) {
                    return;
                }
                if (mDialogHelperIgoneAll == null)
                    mDialogHelperIgoneAll = new DialogHelper(getContext(), "",
                            "是否需要忽略所有？", "是的", "我再想想", new CommonDialog.Listener() {
                        @Override
                        public void ok(String content) {
                            ignoreAll();
                        }

                        @Override
                        public void cancel() {
                        }
                    });
                mDialogHelperIgoneAll.show();
            case "下一页":
                loadMoreData();
                break;
            case FLAG_REFRESH_PAGE:
                refreshData();
                break;
            case "可见":
                recyclerView.setVisibility(View.VISIBLE);
                break;
            case "不可见":
                recyclerView.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 忽略所有
     */
    private void ignoreAll() {
        selectIgnoreIds.clear();
        selectIgnorePos.clear();
        for (int i = 0; i < mBaseLoadMoreRecyclerAdapter.getList().size(); i++) {
            SysMsg msg = (SysMsg) mBaseLoadMoreRecyclerAdapter.getList().get(i);
            selectIgnoreIds.add(msg.getId());
            selectIgnorePos.add(i);
        }
//        ignore((MessageAdapter) mBaseLoadMoreRecyclerAdapter, selectIgnoreIds, selectIgnorePos);
    }

    @Override
    public void refreshMessageRead(int position) {
        // 通知刷新
        EventBus.getDefault().postSticky(new MyEvent(ObjectUtils.parseString(REFRESH_UNREAD_COUNT)));

        SysMsg sysMsg = (SysMsg) mBaseLoadMoreRecyclerAdapter.getList().get(position);
        sysMsg.setFlag("1");
        mBaseLoadMoreRecyclerAdapter.notifyItemChanged(position);
    }


    @Override
    public void refreshMessageDelete() {
        // 通知刷新
        EventBus.getDefault().postSticky(new MyEvent(ObjectUtils.parseString(REFRESH_UNREAD_COUNT)));

        mAllSelectDelete.deleteMessageSuccess();
        refreshData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDialogHelperIgoneAll != null) {
            mDialogHelperIgoneAll.hideDialog();
        }
        if (mDialogHelperIgone != null) {
            mDialogHelperIgone.hideDialog();
        }
        if (mDialogHelperDelete != null) {
            mDialogHelperDelete.hideDialog();
        }
    }

    /**
     * 打开消息
     *
     * @param id 消息id
     */
    public void openMessageInfo(String id) {
        if (TextUtils.isEmpty(id)) return;
        start(HtmlSwipeFragment.newInstance("平台公告消息", WEB_URL + id));
    }

}
