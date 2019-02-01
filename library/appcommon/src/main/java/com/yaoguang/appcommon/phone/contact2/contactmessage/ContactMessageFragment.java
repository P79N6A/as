package com.yaoguang.appcommon.phone.contact2.contactmessage;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Toast;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.phone.contact2.contactcompanydetail.event.RefreshEvent;
import com.yaoguang.appcommon.phone.contact2.entity.Extra;
import com.yaoguang.appcommon.phone.contact2.entity.RelevanceMessage;
import com.yaoguang.appcommon.databinding.FragmentContactMessageBinding;
import com.yaoguang.datasource.common.ContactDataSource;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.greendao.entity.common.UserOfficeWrapper;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

/**
 * 身份验证
 * Created by zhongjh on 2018/4/17.
 */
public class ContactMessageFragment extends BaseFragmentDataBind<FragmentContactMessageBinding> implements Toolbar.OnMenuItemClickListener {

    UserOfficeWrapper mUserOffice;
    ContactDataSource mContactDataSource = new ContactDataSource();
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public static ContactMessageFragment newInstance(UserOfficeWrapper userOffice) {
        ContactMessageFragment contactMessageFragment = new ContactMessageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("mUserOffice", userOffice);
        contactMessageFragment.setArguments(bundle);
        return contactMessageFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_contact_message;
    }

    @Override
    public void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUserOffice = bundle.getParcelable("mUserOffice");
            if (mUserOffice != null) {
                GlideManager.getInstance().with(getActivity(), GlideManager.getImageUrl(mUserOffice.getShopLogo()), mDataBinding.ivPhoto);
                mDataBinding.tvName.setText(mUserOffice.getName());
            }
        }
        initToolbarNav(mToolbarCommonBinding.toolbar, "身份验证", R.menu.menu_send_out, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public void initListener() {
        mDataBinding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDataBinding.tvContent.setText(ObjectUtils.parseString(s.length()) + "/20");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {


            /* 生成 Message 对象。
 * "7127" 为目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
 * Conversation.ConversationType.PRIVATE 为私聊会话类型，根据需要，也可以传入其它会话类型，如群组，讨论组等。
 */
        Extra extra = new Extra();
        extra.setTxt(mDataBinding.etContent.getText().toString());

        String name = null;
        String id = null;
        String photo= null;
        switch (BaseApplication.getAppType()) {
            case Constants.APP_DRIVER:
                name = DataStatic.getInstance().getDriver().getDriverName();
                id = DataStatic.getInstance().getDriver().getId();
                photo = DataStatic.getInstance().getDriver().getPhoto();
                break;
            case Constants.APP_COMPANY:
                name = DataStatic.getInstance().getAppUserWrapper().getName();
                id = DataStatic.getInstance().getAppUserWrapper().getId();
                photo = DataStatic.getInstance().getAppUserWrapper().getPhoto();
                break;
            case Constants.APP_SHIPPER:
                name = DataStatic.getInstance().getUserOwner().getName();
                id = DataStatic.getInstance().getUserOwner().getId();
                photo = DataStatic.getInstance().getUserOwner().getPhoto();
                break;
        }


        extra.setDriverID(id);
        RelevanceMessage relevanceMessage = RelevanceMessage.obtain(name + "发起关联验证", extra);
        Uri uri = null;
        if (photo != null){
            uri = Uri.parse(photo);
        }

        UserInfo userInfo = new UserInfo(id, name, uri);
        relevanceMessage.setUserInfo(userInfo);


        Message message = Message.obtain(mUserOffice.getUserId(), Conversation.ConversationType.PRIVATE, relevanceMessage);


/**
 * <p>发送消息。
 * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
 * 中的方法回调发送的消息状态及消息体。</p>
 *
 * @param message     将要发送的消息体。
 * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
 *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
 *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
 * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
 * @param callback    发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
 */
        RongIM.getInstance().sendMessage(message, name + "发起关联验证", null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {

            }

            @Override
            public void onSuccess(Message message) {
                //消息通过网络发送成功的回调

                // 请求添加接口
                mContactDataSource.contact(mUserOffice.getId())
                        .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                        .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                        .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, ContactMessageFragment.this) {

                            @Override
                            public void onSuccess(BaseResponse<String> response) {
                                pop();
                                // 发送event刷新
                                EventBus.getDefault().post(new RefreshEvent());
                            }
                        });
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //消息发送失败的回调
                Toast.makeText(BaseApplication.getInstance(), "发送失败", Toast.LENGTH_SHORT).show();
            }
        });
        return false;
    }

}