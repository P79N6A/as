package com.yaoguang.appcommon.phone.contact2.contactnew;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.phone.contact2.contactnew.adapter.ContactNewAdapter;
import com.yaoguang.appcommon.databinding.FragmentContactNewBinding;
import com.yaoguang.appcommon.phone.contact2.contactnew.event.ContactNewRefreshEvent;
import com.yaoguang.appcommon.phone.my.my.event.MyEvent;
import com.yaoguang.greendao.entity.common.DriverFollowCompany;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;
import com.yaoguang.lib.common.ObjectUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.REFRESH_UNREAD_CONTACT_COUNT;

/**
 * 新的关联
 * Created by zhongjh on 2018/4/19.
 */
public abstract class BaseContactNewFragment<B extends ViewDataBinding> extends BaseFragmentListConditionDataBind<DriverFollowCompany, String, ContactNewAdapter, FragmentContactNewBinding> implements ContactNewContract.View, Toolbar.OnMenuItemClickListener {

    DialogHelper mDialogHelper;

    ContactNewContract.Presenter mPresenter = new ContactNewPresenter(this);

    @Override
    public int getLayoutId() {
        return R.layout.fragment_contact_new;
    }

    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "新关联", R.menu.menu_contact, BaseContactNewFragment.this);
        EventBus.getDefault().register(this);

        // 清除所有关联消息
        // 清除这个用户的未读消息
        RongIM.getInstance().clearConversations(Conversation.ConversationType.PRIVATE);
        // 通知刷新首页的红点
        EventBus.getDefault().postSticky(new MyEvent(ObjectUtils.parseString(REFRESH_UNREAD_CONTACT_COUNT)));
        // 刷新首页

    }


    @Override
    public void initListener() {
        // 长按
        mBaseLoadMoreRecyclerAdapter.setOnItemLongClickListener((itemView, item, position) -> {
            if (mDialogHelper == null)
                mDialogHelper = new DialogHelper(getContext(), "提示", "删除", "确定", "取消", false, new CommonDialog.Listener() {
                    @Override
                    public void ok(String content) {
                        DriverFollowCompany driverContactCompany = ((DriverFollowCompany) (item));
                        mPresenter.ignoreAduit(driverContactCompany.getId());
                    }

                    @Override
                    public void cancel() {

                    }
                });
            mDialogHelper.show();
        });
        // 同意
        mBaseLoadMoreRecyclerAdapter.setOnAgreenItemClickListener((itemView, item, position) -> {
            DriverFollowCompany driverContactCompany = ((DriverFollowCompany) (item));
            mPresenter.passAduit(driverContactCompany.getId());
        });
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new ContactNewAdapter(BaseContactNewFragment.this);
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public String getCondition(boolean isRegain) {
        return "";
    }

    @Override
    public void setConditionView(String condition) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 接收消息推送
     *
     * @param event
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onContactNewRefreshEvent(ContactNewRefreshEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        if (isEvent)
            refreshData();
    }


}
