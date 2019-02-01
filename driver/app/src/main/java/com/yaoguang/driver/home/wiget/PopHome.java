package com.yaoguang.driver.home.wiget;

import android.app.Activity;
import android.widget.Toast;

import com.yaoguang.appcommon.html.HtmlFragment;
import com.yaoguang.common.BuildConfig;
import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.net.Api;
import com.yaoguang.common.net.bean.BaseResponse;
import com.yaoguang.driver.home.HomeFragment;
import com.yaoguang.driver.main.MainFragment;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.entity.SysMsg;
import com.yaoguang.netmodule.api.SMSApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 文件名： 消息弹窗
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/9/1 0001.
 * 版    权：
 */
public class PopHome {
    static List<SysMsg> sysMsgs = new ArrayList<>();
    static MessageDialog mDialog;//为了确保每次只有一个弹窗

    /**
     * 初始化弹窗
     */
    public static void initDialog(final HomeFragment homeFragment, final Activity activity) {
        if (mDialog == null || !mDialog.isShowing()) {

            String platformType = "1";
            String id = Injection.provideDriverRepository(BaseApplication.getInstance()).getDriver().getId();
            Api.getInstance().retrofit.create(SMSApi.class).getPopUpMsg(id, platformType)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse<List<SysMsg>>>() {

                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(BaseResponse<List<SysMsg>> value) {
                            if (value.getState().equals("200")) {
                                //缓存列表
                                sysMsgs = value.getResult();
                                if (sysMsgs.size() > 0) {
                                    //显示弹窗
                                    mDialog = new MessageDialog(activity, sysMsgs.get(0));
                                    mDialog.setDialogListener(new MessageDialog.DialogListener() {
                                        @Override
                                        public void onLock(SysMsg sysMsg) {
                                            ((MainFragment) homeFragment.getParentFragment()).start(HtmlFragment.newInstance("消息详情", BuildConfig.ENDPOINT + "page/msg/msg_detail.jsp?id=" + sysMsg.getId()));
                                        }
                                    });
                                    mDialog.show();
                                    //设置已弹窗
                                    updatePopUp(activity, sysMsgs.get(0).getId());
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(activity, "当前网络不可用，请检查网络连接", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    /**
     * 设置成已弹窗
     */
    private static void updatePopUp(final Activity activity, String msgId) {
        String platformType = "1";
        String id = Injection.provideDriverRepository(BaseApplication.getInstance()).getDriver().getId();
        Api.getInstance().retrofit.create(SMSApi.class).updatePopUp(id, msgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<String>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseResponse<String> value) {
                        if (value.getState().equals("200")) {
                            sysMsgs.remove(0);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(activity, "当前网络不可用，请检查网络连接", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


}
