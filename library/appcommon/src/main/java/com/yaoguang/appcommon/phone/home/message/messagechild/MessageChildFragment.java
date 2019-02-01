package com.yaoguang.appcommon.phone.home.message.messagechild;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yaoguang.appcommon.R;
import com.yaoguang.lib.base.BaseFragmentList;
import com.yaoguang.appcommon.phone.home.event.HomeMessageEvent;
import com.yaoguang.appcommon.phone.home.message.MessageDetailFragment;
import com.yaoguang.appcommon.phone.home.message.MessageFragment;
import com.yaoguang.appcommon.phone.home.message.adapter.MessageChildAdapter;
import com.yaoguang.appcommon.phone.home.message.eventbus.child.IsDeleteEvent;
import com.yaoguang.appcommon.phone.home.message.eventbus.child.IsEditEvent;
import com.yaoguang.appcommon.phone.home.message.eventbus.child.RefreshDataEvent;
import com.yaoguang.appcommon.phone.home.message.eventbus.main.UpdateMenuEvent;
import com.yaoguang.datasource.api.MessageApi;
import com.yaoguang.greendao.entity.common.SysMsg;
import com.yaoguang.lib.BuildConfig;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ezy.ui.layout.LoadingLayout;


/**
 * 消息子管理
 * Created by zhongjh on 2017/4/6.
 */
public class MessageChildFragment extends BaseFragmentList<SysMsg> implements MessageChildContact.MessageView {

    private static final String ARG_TYPE = "arg_type";
    private static final String ARG_CODETYPE = "arg_codeType";

    MessageChildContact.MessagePresenter mMessagePresenter;

    /**
     * 判断类型,0企业消息 1平台公告
     */
    private int mType;
    /**
     * 0代表司机 1代表物流 2代表货主
     */
    private String mCodeType;
    private ArrayList<String> mIds = new ArrayList<>();// 消息id的集合


    public MessageChildAdapter mMessageChildAdapter;

    InitialView mInitialView;


    /**
     * 实例化
     *
     * @param type 判断类型,0企业消息 1平台公告
     * @return 实例化
     */
    public static MessageChildFragment newInstance(int type, String codeType) {
        MessageChildFragment fragment = new MessageChildFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        args.putString(ARG_CODETYPE, codeType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注册成为订阅者
        EventBus.getDefault().register(this);
        Bundle args = getArguments();
        if (args != null) {
            mType = args.getInt(ARG_TYPE);
            mCodeType = args.getString(ARG_CODETYPE);
        }
        mMessagePresenter = new MessagePresenter(this, mType, mCodeType);
        setHasOptionsMenu(true);
    }

    @Override
    public BasePresenter getPresenter() {
        return mMessagePresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_child, container, false);
        mInitialView = new InitialView(view);
        mMessagePresenter.subscribe();
        mInitialView.viewHolder.refreshLayout.autoRefresh();
        return view;
    }

    @Override
    public void onDestroy() {
        // 解除注册
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
    }

    // 编辑通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isEditEvent(IsEditEvent isEditEvent) {
        // 重置取消全选
        mInitialView.viewHolder.tvSelect.setText(getResources().getString(R.string.message_select_all));

        // 设置列表显示单选框 并且重置单选框
        mMessageChildAdapter.clearAll();
        mMessageChildAdapter.setCheck(isEditEvent.getIsEdit());

        // 设置编辑模式，控制底部显示或者隐藏
        if (isEditEvent.getIsEdit()) {
            mInitialView.viewHolder.llDelete.setVisibility(View.VISIBLE);
            // 不允许刷新和加载更多
            mInitialView.viewHolder.refreshLayout.setEnableLoadMore(false);
            mInitialView.viewHolder.refreshLayout.setEnableRefresh(false);
            mMessageChildAdapter.setHasFooter(false);
        } else {
            mInitialView.viewHolder.llDelete.setVisibility(View.GONE);
            // 允许刷新和加载更多
            mInitialView.viewHolder.refreshLayout.setEnableLoadMore(true);
            mInitialView.viewHolder.refreshLayout.setEnableRefresh(true);
            mMessageChildAdapter.setHasFooter(super.mHasFooter);
        }

        mMessageChildAdapter.notifyDataSetChanged();

    }

    // 获取选择的
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isDeleteEvent(IsDeleteEvent isDeleteEvent) {
        if (isDeleteEvent.getType() == mType) {
            getSelect();
        }
    }

    // 刷新为已读
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshDataEvent(RefreshDataEvent refreshDataEvent) {
        if (ObjectUtils.parseInt(refreshDataEvent.getType()) == mType) {
            SysMsg sysMsg = mMessageChildAdapter.getList().get(ObjectUtils.parseInt(refreshDataEvent.getPosition()));
            // 判断本身如果未读，就进行刷新
            if (!sysMsg.getFlag().equals("1")) {
                sysMsg.setFlag("1");
                mMessageChildAdapter.updateItem(ObjectUtils.parseInt(refreshDataEvent.getPosition()), sysMsg);
            }
        }
    }

    private void getSelect() {
        mIds.clear();
        boolean isDelete = false;
        //循环选择的,获取相应的id
        for (int i = 0; i <= mMessageChildAdapter.checks.size() - 1; i++) {
            if (mMessageChildAdapter.checks.get(i)) {
                mIds.add(mMessageChildAdapter.getItem(i).getId());
                isDelete = true;
            }
        }

        if (mIds.size() == mMessageChildAdapter.getList().size()) {
            //判断选择的如果跟当前数据一样多，就设置 取消全选
            mInitialView.viewHolder.tvSelect.setText(getResources().getString(R.string.message_select_cancel_all));
        } else {
            //否则就是 全选
            mInitialView.viewHolder.tvSelect.setText(getResources().getString(R.string.message_select_all));
        }

        //判断如果有选择的，删除就可用
        if (isDelete) {
            mInitialView.viewHolder.tvDelete.setTextColor(getResources().getColor(R.color.orange500));
        } else {
            mInitialView.viewHolder.tvDelete.setTextColor(getResources().getColor(R.color.text_cccccc));
        }
    }

    @Override
    public void refreshData() {
        mMessagePresenter.refreshData();
        mIds.clear();
        mMessageChildAdapter.checks.clear();

        // 信息刷新的同时，也通知首页的信息刷新
        EventBus.getDefault().post(new HomeMessageEvent(true, false));

        // 判断当前如果是取消全选，那么删除完后，肯定取消全选就变成全选了
        if (mInitialView.viewHolder.tvSelect.getText().toString().equals(getResources().getString(R.string.message_select_cancel_all))) {
            mInitialView.viewHolder.tvSelect.setText(getResources().getString(R.string.message_select_all));
        }

    }

    @Override
    public void setAdapter(List<SysMsg> list, boolean isHas) {
        super.setAdapter(list, isHas);
        // 通知MessageFragment更新是否显示编辑menu
        EventBus.getDefault().post(new UpdateMenuEvent(mMessageChildAdapter.getList().size() > 0, mType));
    }

    @Override
    public void recyclerViewShowError(String strMessage) {
        super.recyclerViewShowError(strMessage);
        // 通知MessageFragment更新是否显示编辑menu
        EventBus.getDefault().post(new UpdateMenuEvent(mMessageChildAdapter.getList().size() > 0, mType));
    }

    @Override
    public void recyclerViewShowEmpty() {
        super.recyclerViewShowEmpty();
        // 通知MessageFragment更新是否显示编辑menu
        EventBus.getDefault().post(new UpdateMenuEvent(mMessageChildAdapter.getList().size() > 0, mType));
    }

    @Override
    public void loadMoreData() {
        mMessagePresenter.loadMoreData(mMessageChildAdapter.getList().size());
    }

    @Override
    public void alreadyRead(int position) {

    }

    public class InitialView {

        public ViewHolder viewHolder;

        InitialView(View view) {
            viewHolder = new ViewHolder(view);
            initView();
            MessageChildFragment.this.setRecyclerview(viewHolder.rlView, viewHolder.refreshLayout, viewHolder.loading, mMessageChildAdapter);
            initListener();
        }

        void initView() {
            // 列表初始化
            mMessageChildAdapter = new MessageChildAdapter(mType);
            viewHolder.rlView.setAdapter(mMessageChildAdapter);
            RecyclerViewUtils.initPage5(viewHolder.rlView, mMessageChildAdapter, null, getContext(), true);

            if (mType == 0) {
                viewHolder.tvDelete.setText("删除企业消息");
            } else {
                viewHolder.tvDelete.setText("删除平台公告");
            }

            viewHolder.loading.setEmptyImage(R.drawable.ic_xx_k);
        }

        void initListener() {
            MessageChildFragment.this.initRecyclerviewListener();
            // 删除
            viewHolder.tvDelete.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (mIds.size() > 0) {
                        //提交删除，支持批量
                        StringBuilder ids = new StringBuilder();
                        for (String s : mIds) {
                            ids.append(s);
                            ids.append(",");
                        }
                        mMessagePresenter.deleteBatch(mCodeType, ids.toString());
                    }
                }
            });
            // 全选和取消全选
            viewHolder.tvSelect.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (viewHolder.tvSelect.getText().toString().equals(getResources().getString(R.string.message_select_all))) {
                        // 点击全选
                        mMessageChildAdapter.selectAll();
                        mMessageChildAdapter.notifyDataSetChanged();
                        viewHolder.tvSelect.setText(getResources().getString(R.string.message_select_cancel_all));
                        mInitialView.viewHolder.tvDelete.setTextColor(getResources().getColor(R.color.orange500));

                        // 循环选择的,获取相应的id
                        for (int i = 0; i <= mMessageChildAdapter.checks.size() - 1; i++) {
                            if (mMessageChildAdapter.checks.get(i)) {
                                mIds.add(mMessageChildAdapter.getItem(i).getId());
                            }
                        }

                    } else if (viewHolder.tvSelect.getText().toString().equals(getResources().getString(R.string.message_select_cancel_all))) {
                        // 点击取消全选
                        mMessageChildAdapter.clearAll();
                        mMessageChildAdapter.notifyDataSetChanged();
                        viewHolder.tvSelect.setText(getResources().getString(R.string.message_select_all));
                        mInitialView.viewHolder.tvDelete.setTextColor(getResources().getColor(R.color.text_cccccc));

                        mIds.clear();
                    }
                }
            });

            mMessageChildAdapter.setOnItemClickListener(new BaseLoadMoreRecyclerAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View itemView, Object item, int position) {
                    // 打开详情，设置已读
                    SysMsg sysMsg = (SysMsg) item;
//                    mMessagePresenter.updateBath(mCodeType, sysMsg.getId(), position);
                    MessageFragment messageFragment = (MessageFragment) MessageChildFragment.this.getParentFragment();
                    messageFragment.startForResult(MessageDetailFragment.newInstance("消息详情", BuildConfig.ENDPOINT + MessageApi.MESSAGEDETAIL + sysMsg.getId(), ObjectUtils.parseString(mType), ObjectUtils.parseString(mCodeType), sysMsg.getId(), ObjectUtils.parseString(position)), MessageFragment.REQUEST_HTMLFRAGMENT);
                }
            });
        }
    }

    public static class ViewHolder {

        public View rootView;
        public RecyclerView rlView;
        public LoadingLayout loading;
        public SmartRefreshLayout refreshLayout;
        public TextView tvDelete;
        public TextView tvSelect;
        public LinearLayout llDelete;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.rlView = (RecyclerView) rootView.findViewById(R.id.rlView);
            this.loading = (LoadingLayout) rootView.findViewById(R.id.loading);
            this.refreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refreshLayout);
            this.tvDelete = (TextView) rootView.findViewById(R.id.tvDelete);
            this.tvSelect = (TextView) rootView.findViewById(R.id.tvSelect);
            this.llDelete = (LinearLayout) rootView.findViewById(R.id.llDelete);
        }

    }
}
