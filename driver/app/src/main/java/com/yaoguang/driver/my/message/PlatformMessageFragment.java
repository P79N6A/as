package com.yaoguang.driver.my.message;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.yaoguang.appcommon.common.eventbus.HomeEvent;
import com.yaoguang.appcommon.common.eventbus.SysMessageEvent;
import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.common.BuildConfig;
import com.yaoguang.common.appcommon.dialog.CommonDialog;
import com.yaoguang.common.appcommon.dialog.DialogHelper;
import com.yaoguang.common.appcommon.utils.AllSelectDelete;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.ObjectUtils;
import com.yaoguang.driver.databinding.ToolbarCommonBinding;
import com.yaoguang.driver.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.base.baseview.BaseFragmentListV2;
import com.yaoguang.driver.databinding.FragmentMessageBinding;
import com.yaoguang.driver.databinding.ViewDeleteBinding;
import com.yaoguang.driver.my.message.adapter.MessageAdapter;
import com.yaoguang.driver.util.HtmlSwipeFragment;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.entity.MessageInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.yaoguang.common.common.Constants.FLAG_REFRESH_PAGE;
import static com.yaoguang.common.common.Constants.FLAG_UNREAD_NUM;
import static com.yaoguang.driver.my.my.MyFragment.REFRESH_UNREAD_COUNT;


/**
 * 平台公告
 * Created by 韦理英
 * on 2017/7/6.
 */

public class PlatformMessageFragment extends BaseFragmentListV2<MessageInfo, PlatformMessagePresenter, FragmentMessageBinding> implements PlatformMessageContacts.View {
    public static final String WEB_URL = BuildConfig.ENDPOINT + "page/msg/msg_detail.jsp?id=";
    public List<MessageInfo> data = new ArrayList<>();
    /**
     * 选择忽略消息的id
     */
    public HashSet<String> selectIgnoreIds = new HashSet<>();
    /**
     * 选择忽略消息的列表位置
     */
    public HashSet<Integer> selectIgnorePos = new HashSet<>();

    @Nullable
    public String noticeType = "0";

    public RecyclerView recyclerView;
    protected MessageAdapter mMessageAdapter;
    /**
     * 全选删除工具
     */
    protected AllSelectDelete mAllSelectDelete;
    /**
     * 对话框
     */
    private DialogHelper mDialogHelper;
    ToolbarCommonBinding mToolbarCommonBinding;
    ViewDeleteBinding mViewDelete;

    public static PlatformMessageFragment newInstance() {
        PlatformMessageFragment platformMessageFragment = new PlatformMessageFragment();
        Bundle bundle = new Bundle();
        platformMessageFragment.setArguments(bundle);
        return platformMessageFragment;
    }

    @Override
    protected void initView(View view) {
        mToolbarCommonBinding = DataBindingUtil.bind(view.findViewById(R.id.toolbarCommon));
        mViewDelete = DataBindingUtil.bind(view.findViewById(R.id.viewDelete));

        mMessageAdapter = new MessageAdapter();
        setHomePageSwipeRv(view);
        customSetting();

        mToolbarCommonBinding.toolbarTitle.setText(R.string.platform_message);
        mDataBinding.tvNoMessage.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initListener() {
        // 返回
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());
        // 首页点击监听
        mMessageAdapter.setOnRVItemClickListener(new MessageAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object item, int position) {
                if (AppClickUtil.isDuplicateClick()) return;

                // 查看
                MessageInfo msg = (MessageInfo) item;
                if (msg.getFlag() == 0) {
                    EventBus.getDefault().post(new HomeEvent(FLAG_UNREAD_NUM));
                    mPresenter.setMessageReadState(msg.getId(), position);
                }
                openMessageInfo(msg.getId());
            }

            @Override
            public void onItemIgnoreClick(View itemView, final Object item, final int position) {
                // 忽略
                if (mDialogHelper != null) {
                    mDialogHelper.hideDialog();
                }

                mDialogHelper = new DialogHelper();
                mDialogHelper.showConfirmDialog(getContext(), "",
                        "是否忽略此条信息？", "是的", "我再想想", new CommonDialog.Listener() {
                            @Override
                            public void ok() {
                                MessageInfo msg = (MessageInfo) item;

                                selectIgnoreIds.clear();
                                selectIgnorePos.clear();
                                selectIgnoreIds.add(msg.getId());
                                selectIgnorePos.add(position);
                                ignore(mMessageAdapter, selectIgnoreIds, selectIgnorePos);

                                mDialogHelper.hideDialog();
                            }

                            @Override
                            public void cancel() {

                            }
                        });
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

        // 非首页点击监听
        mMessageAdapter.setOnRecyclerViewItemClickSelectListener((checkBox, item, position) -> {
            if (mAllSelectDelete.isEdit) {
                // 删除选择
                MessageInfo messageInfo = (MessageInfo) item;
                mAllSelectDelete.selectItem(checkBox, messageInfo.getId(), position);
            } else {
                // 查看
                MessageInfo msg = (MessageInfo) item;
                if (msg.getFlag() == 0) {
                    EventBus.getDefault().post(new HomeEvent(FLAG_UNREAD_NUM));
                    mPresenter.setMessageReadState(msg.getId(), position);
                }
                openMessageInfo(msg.getId());
            }
        });

    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        if (getContext() == null) return;
        mPresenter.setData(Injection.provideMessageRepository(getContext()));
    }

    public void customSetting() {
        mToolbarCommonBinding.toolbar.setVisibility(View.VISIBLE);

        // 全选，反选设置
        mAllSelectDelete = new AllSelectDelete(mMessageAdapter, mViewDelete.tvSelect, mViewDelete.tvDelete, mViewDelete.llSelectDelete, mToolbarCommonBinding.toolbar, () -> {
            if (mAllSelectDelete.isAllSelect) {
                // 全选删除
                mAllSelectDelete.selectDeleteIds.clear();
                mAllSelectDelete.selectDeletePos.clear();

                for (int i = 0; i < mMessageAdapter.getList().size(); i++) {
                    MessageInfo messageInfo = (MessageInfo) mMessageAdapter.getList().get(i);
                    mAllSelectDelete.selectDeleteIds.add(messageInfo.getId());
                    mAllSelectDelete.selectDeletePos.add(i);
                }


            }  // 部分删除 selectDeleteIds 和selectDeletePos 已经存在，不需要在处理，直接提交服务器



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
                            // 删除
                            mPresenter.deleteMessage(ObjectUtils.subString(mAllSelectDelete.selectDeleteIds));
                            mDialogHelper.hideDialog();
                        }

                        @Override
                        public void cancel() {

                        }
                    });
        });
    }

    public void ignore(MessageAdapter mMessageAdapter, HashSet<String> selectIgnoreIds, HashSet<Integer> selectIgnorePos) {

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
    public void refreshAdapter(List list, boolean isHas) {
        if (mAllSelectDelete != null) {
            mAllSelectDelete.initMenu(list);
        }
        super.refreshAdapter(list, isHas);
    }

    @Override
    public void refreshData() {
        // 编辑状态不能刷新数据
        if (!mAllSelectDelete.isEdit) {
            App.handler.postDelayed(() -> mPresenter.homePage(noticeType), 300);
        } else {
            finishRefreshing();
        }
    }


    @Subscribe
    public void SysMessageEvent(SysMessageEvent event) {
        switch (event.getType()) {
            case "忽略所有":
                if (mMessageAdapter.getList().isEmpty() || (mDialogHelper != null && mDialogHelper.isShow())) {
                    return;
                }
                if (mDialogHelper != null) {
                    mDialogHelper.hideDialog();
                }
                mDialogHelper = new DialogHelper();
                mDialogHelper.showConfirmDialog(getContext(), "",
                        "是否需要忽略所有？", "是的", "我再想想", new CommonDialog.Listener() {
                            @Override
                            public void ok() {
                                ignoreAll();
                                mDialogHelper.hideDialog();
                            }

                            @Override
                            public void cancel() {
                            }
                        });
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
        for (int i = 0; i < mMessageAdapter.getList().size(); i++) {
            MessageInfo msg = (MessageInfo) mMessageAdapter.getList().get(i);
            selectIgnoreIds.add(msg.getId());
            selectIgnorePos.add(i);
        }
        ignore(mMessageAdapter, selectIgnoreIds, selectIgnorePos);
    }

    /**
     * 设置列表已读
     *
     * @param position 位置
     */
    @Override
    public void refreshMessageRead(int position) {
        // 通知刷新
        setFragmentResult(REFRESH_UNREAD_COUNT, null);

        MessageInfo messageInfo = (MessageInfo) mMessageAdapter.getList().get(position);
        messageInfo.setFlag(1);
        mMessageAdapter.notifyItemChanged(position);
    }

    /**
     * 删除列表消息
     */
    @Override
    public void refreshMessageDelete() {
        // 通知刷新
        setFragmentResult(REFRESH_UNREAD_COUNT, null);

        mAllSelectDelete.deleteMessageSuccess();
        refreshData();
    }

    /**
     * 设置列表
     *
     * @param view view
     */
    public void setHomePageSwipeRv(View view) {
        View view_hasSwipe = View.inflate(getContext(), R.layout.view_swipe_recyclerview, null);
        recyclerView = view_hasSwipe.findViewById(R.id.recyclerView);
        mDataBinding.contentMain.addView(view_hasSwipe);
        initSwipeRecyclerView(view, mMessageAdapter);
    }

    /**
     * 加载更多
     */
    @Override
    public void loadMoreData() {
        mPresenter.nextPage(noticeType, mMessageAdapter.getList().size());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
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
