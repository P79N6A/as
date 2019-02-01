package com.yaoguang.appcommon.phone.home.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaoguang.appcommon.phone.home.event.HomeMessageEvent;
import com.yaoguang.appcommon.html.HtmlFragment;
import com.yaoguang.datasource.common.SysMsgDataSource;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 消息详情
 * Created by zhongjh on 2017/11/27.
 */
public class MessageDetailFragment extends HtmlFragment {

    /**
     * 判断类型,0企业消息 1平台公告
     */
    private String mType;
    /**
     * 0代表司机 1代表物流 2代表货主
     */
    private String mCodeType;
    private String mId;
    private String mPosition;
    private boolean mIsRefresh;

    private SysMsgDataSource mSysMsgDataSource = new SysMsgDataSource();
    private CompositeDisposable mCompositeDisposable;

    /**
     * @param title    "船位查询"
     * @param url      "http://weixin.shipxy.com/shipxy/map/?sid=972325687EAC4B2C909F74CF9811A8B4"
     * @param type     消息类型
     * @param codeType 用户类型
     * @param id       编号
     * @param position 索引
     */
    public static MessageDetailFragment newInstance(String title, String url, String type, String codeType, String id, String position) {
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("url", url);
        args.putString("type", type);
        args.putString("codeType", codeType);
        args.putString("id", id);
        args.putString("position", position);
        MessageDetailFragment fragment = new MessageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getString("type");
            mCodeType = bundle.getString("codeType");
            mId = bundle.getString("id");
            mPosition = bundle.getString("position");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mCompositeDisposable = new CompositeDisposable();
        // 刷新首页的信息
        EventBus.getDefault().post(new HomeMessageEvent(true, false));
        // 设置成已读
        mSysMsgDataSource.readBatch(mId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        mIsRefresh = true;
                    }

                });
        return view;
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        if (mIsRefresh) {
            Bundle bundle = new Bundle();
            bundle.putString("codeType", mCodeType);
            bundle.putString("id", mId);
            bundle.putString("position", mPosition);
            bundle.putString("type", mType);
            setFragmentResult(RESULT_OK, bundle);
        }
        super.onDestroyView();
    }
}
