package com.yaoguang.appcommon.phone.contact2.contactcompanydetail;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.appcommon.phone.contact2.contactcompanydetail.event.RefreshEvent;
import com.yaoguang.appcommon.phone.contact2.entity.RelevanceMessage;
import com.yaoguang.appcommon.databinding.FragmentCompanyDetail2Binding;
import com.yaoguang.datasource.common.ContactDataSource;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.common.UserOfficeWrapper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UnknownMessage;
import io.rong.message.TextMessage;

/**
 * 企业详情
 * Created by zhongjh on 2018/4/13.
 */
public abstract class BaseCompanyDetailFragment<B extends ViewDataBinding> extends BaseFragmentDataBind<FragmentCompanyDetail2Binding> {

    boolean isRefresh = false;
    ContactDataSource mContactDataSource = new ContactDataSource();
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    DialogHelper mDialogHelperCpbSubmit2;
    DialogHelper mDialogHelpervReply;

    public abstract void IWantToConnect(UserOfficeWrapper userOffice);

    @Override
    public int getLayoutId() {
        return R.layout.fragment_company_detail2;
    }

    @Override
    public void init() {
        //注册事件
        EventBus.getDefault().register(this);

        // 需要添加这个才能让回弹起效
        ((AppCompatActivity) _mActivity).setSupportActionBar(mDataBinding.toolbar);
        // 退出
        mDataBinding.imgReturn.setOnClickListener(v -> pop());

        initData();
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            UserOfficeWrapper userOffice = bundle.getParcelable("mUserOffice");
            if (userOffice != null) {
                mContactDataSource.getCompanyInfo(userOffice.getId())
                        .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                        .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                        .subscribe(new BaseObserver<BaseResponse<UserOfficeWrapper>>(mCompositeDisposable, BaseCompanyDetailFragment.this) {

                            @Override
                            public void onSuccess(BaseResponse<UserOfficeWrapper> response) {
                                initData(response.getResult());
                                initMessage(response.getResult());
                            }
                        });
            }
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (isRefresh) {
            initData();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent refreshEvent) {
        isRefresh = true;
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mCompositeDisposable.clear();
    }

    @Override
    public void initListener() {


    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    /**
     * 初始化个人信息
     *
     * @param userOffice 个人信息
     */
    private void initData(UserOfficeWrapper userOffice) {
        if (userOffice == null)
            return;

        // toolbar的头像
        GlideManager.getInstance().withRounded(getActivity(), GlideManager.getImageUrl(userOffice.getShopLogo(), false), mDataBinding.titleUcAvater, R.drawable.ic_qymrtx);
        // toolbar的名称
        mDataBinding.toolbarTitle.setText(userOffice.getName());

        Glide.with(getActivity())
                .load(GlideManager.getImageUrl(userOffice.getPhoto(), true))
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_jzsb)
                        .error(R.drawable.ic_jzsb))
                .into(mDataBinding.imgBanner);


        // 头像
        GlideManager.getInstance().withRounded(getActivity(), GlideManager.getImageUrl(userOffice.getShopLogo(), false), mDataBinding.ucAvater, R.drawable.ic_qymrtx);
        // 名称
        mDataBinding.layoutContent.tvName.setText(userOffice.getName());
        // 地址
        mDataBinding.layoutContent.tvLocal.setText(userOffice.getShopAddress());
        // 类型
        switch (userOffice.getApplyType()) {
            case 0:
                // 货代
                mDataBinding.layoutContent.tvType0.setVisibility(View.VISIBLE);
                mDataBinding.layoutContent.flType.setVisibility(View.VISIBLE);
                break;
            case 1:
                //  拖车
                mDataBinding.layoutContent.tvType1.setVisibility(View.VISIBLE);
                mDataBinding.layoutContent.flType.setVisibility(View.VISIBLE);
                break;
            case 2:
                // 货代拖车
                mDataBinding.layoutContent.tvType0.setVisibility(View.VISIBLE);
                mDataBinding.layoutContent.tvType2.setVisibility(View.VISIBLE);
                mDataBinding.layoutContent.flType.setVisibility(View.VISIBLE);
                break;
        }
        // 联系人
        mDataBinding.layoutContent.tvContactsName.setText(userOffice.getLinkMan());
        // 联系电话
        mDataBinding.layoutContent.tvContactsPhoto.setText(userOffice.getLinkPhone());
        mDataBinding.layoutContent.imgPhoto.setOnClickListener(v -> PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{mDataBinding.layoutContent.tvContactsPhoto.getText().toString()}));
        // 简介
        mDataBinding.layoutContent.tvRemark.setText(userOffice.getShopDetail());

        // 我要关联
        // 判断是我要关联\取消关联\同意
        if (userOffice.getStatus() == 3) {
            mDataBinding.layoutContent.group.setVisibility(View.VISIBLE);
            mDataBinding.cpbSubmit.setText("同意");
            mDataBinding.cpbSubmit.setVisibility(View.VISIBLE);
            mDataBinding.cpbSubmit2.setVisibility(View.GONE);
            mDataBinding.cpbSubmit.setOnClickListener(v -> mContactDataSource.passAduit(userOffice.getContactId())
                    .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                    .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                    .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, BaseCompanyDetailFragment.this) {

                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            pop();
                            // 刷新我的关联
                            EventBus.getDefault().post(new com.yaoguang.appcommon.phone.contact2.event.RefreshEvent());
                        }
                    }));
        } else if (userOffice.getStatus() == 1) {
            mDataBinding.layoutContent.group.setVisibility(View.GONE);
            mDataBinding.cpbSubmit2.setText("取消关联");
            mDataBinding.cpbSubmit.setVisibility(View.GONE);
            mDataBinding.cpbSubmit2.setVisibility(View.VISIBLE);
            mDataBinding.cpbSubmit2.setOnClickListener(v -> {
                if (mDialogHelperCpbSubmit2 == null)
                    mDialogHelperCpbSubmit2 = new DialogHelper(getContext(), "提示", "取消关联后您将不能接收来自该企业的任何消息，请谨慎操作。", "确定", "取消", false, new CommonDialog.Listener() {
                        @Override
                        public void ok(String content) {
                            mContactDataSource.delete(userOffice.getContactId())
                                    .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                                    .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                                    .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, BaseCompanyDetailFragment.this) {

                                        @Override
                                        public void onSuccess(BaseResponse<String> response) {
                                            pop();
                                            // 刷新我的关联
                                            EventBus.getDefault().post(new com.yaoguang.appcommon.phone.contact2.event.RefreshEvent());
                                        }
                                    });
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                mDialogHelperCpbSubmit2.show();
            });
        } else if (userOffice.getStatus() == 2) {
            mDataBinding.layoutContent.group.setVisibility(View.GONE);
            // 我要关联
            mDataBinding.cpbSubmit.setText("我要关联");
            mDataBinding.cpbSubmit.setVisibility(View.VISIBLE);
            mDataBinding.cpbSubmit2.setVisibility(View.GONE);
            mDataBinding.cpbSubmit.setOnClickListener(v -> IWantToConnect(userOffice));
        }

        // 回复
        mDataBinding.layoutContent.tvReply.setOnClickListener(v -> {
            // 弹框
            if (mDialogHelpervReply == null)
                mDialogHelpervReply = new DialogHelper(getContext(), "回复", "", "", "确定", "取消", false, true, 20, new CommonDialog.Listener() {

                    @Override
                    public void ok(String content) {
                        TextMessage textMessage = new TextMessage(content);
                        Message message = Message.obtain(userOffice.getUserId(), Conversation.ConversationType.PRIVATE, textMessage);

/**
 * <p>发送消息。
 * 通过 {@link IRongCallback.ISendMessageCallback}
 * 中的方法回调发送的消息状态及消息体。</p>
 *
 * @param message     将要发送的消息体。
 * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
 *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
 *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
 * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
 * @param callback    发送消息的回调，参考 {@link IRongCallback.ISendMessageCallback}。
 */

                        String name = "";
                        if (BaseApplication.getAppType().equals(Constants.APP_DRIVER)) {
                            name = DataStatic.getInstance().getDriver().getDriverName();
                        } else if (BaseApplication.getAppType().equals(Constants.APP_COMPANY)) {
                            name = DataStatic.getInstance().getAppUserWrapper().getName();
                        } else if (BaseApplication.getAppType().equals(Constants.APP_SHIPPER)) {
                            name = DataStatic.getInstance().getUserOwner().getName();
                        }

                        RongIM.getInstance().sendMessage(message, name + "发起关联验证", null, new IRongCallback.ISendMessageCallback() {
                            @Override
                            public void onAttached(Message message) {
                                // 刷新
                            }

                            @Override
                            public void onSuccess(Message message) {
                                //消息通过网络发送成功的回调
                                initMessage(userOffice);
                            }

                            @Override
                            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                                //消息发送失败的回调
                            }
                        });
                    }

                    @Override
                    public void cancel() {

                    }
                });
            mDialogHelpervReply.show();
        });


    }

    /**
     * 初始化消息
     *
     * @param userOffice 个人信息
     */
    private void initMessage(UserOfficeWrapper userOffice) {
        RongIM.getInstance().getConversation(Conversation.ConversationType.PRIVATE, userOffice.getUserId(), new RongIMClient.ResultCallback<Conversation>() {
            @Override
            public void onSuccess(Conversation conversation) {
                if (conversation == null) {
                    mDataBinding.layoutContent.group.setVisibility(View.GONE);
                    return;
                }
                RongIM.getInstance().getLatestMessages(Conversation.ConversationType.PRIVATE, conversation.getTargetId(), 3, new RongIMClient.ResultCallback<List<Message>>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(List<Message> messages) {
                        if (messages.size() > 0 && userOffice.getStatus() != 1) {
                            mDataBinding.layoutContent.group.setVisibility(View.VISIBLE);
                        } else {
                            mDataBinding.layoutContent.group.setVisibility(View.GONE);
                        }
                        // 填充数据
                        int i = 0;
                        String id = "";
                        switch (BaseApplication.getAppType()) {
                            case Constants.APP_DRIVER:
                                id = DataStatic.getInstance().getDriver().getId();
                                break;
                            case Constants.APP_COMPANY:
                                id = DataStatic.getInstance().getAppUserWrapper().getId();
                                break;
                            case Constants.APP_SHIPPER:
                                id = DataStatic.getInstance().getUserOwner().getId();
                                break;
                        }
                        for (Message message : messages) {
                            String weiYu;
                            if (id.equals(message.getSenderUserId()))
                                weiYu = "我:";
                            else
                                weiYu = "对方:";

                            if (!(message.getContent() instanceof UnknownMessage) && message.getContent() != null) {
                                // 区分类型
                                String mes = null;
                                if (message.getContent() instanceof TextMessage) {
                                    TextMessage textMessage = ((TextMessage) message.getContent());
                                    mes = weiYu + textMessage.getContent();
                                } else if (message.getContent() instanceof RelevanceMessage) {
                                    RelevanceMessage relevanceMessage = ((RelevanceMessage) message.getContent());

                                    // 判断extra的txt是否有内容，如果没内容，则显示content
                                    if (relevanceMessage.getExtra() != null && !TextUtils.isEmpty(relevanceMessage.getExtra().getTxt())) {
                                        mes = weiYu + relevanceMessage.getExtra().getTxt();
                                    } else {
                                        mes = weiYu + relevanceMessage.getContent();
                                    }
                                }
                                switch (i) {
                                    case 0:
                                        mDataBinding.layoutContent.tvMessage1.setText(mes);
                                        break;
                                    case 1:
                                        mDataBinding.layoutContent.tvMessage2.setText(mes);
                                        break;
                                    case 2:
                                        mDataBinding.layoutContent.tvMessage3.setText(mes);
                                        break;
                                }
                            }
                            i++;
                        }

                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        mDataBinding.layoutContent.group.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                mDataBinding.layoutContent.group.setVisibility(View.GONE);
            }
        });
//        RongIM.getInstance().getHistoryMessages().getHistoryMessages(Conversation.ConversationType.PRIVATE,mUserOffice.getId(),);
    }

}

