package com.yaoguang.driver.phone.my.my;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.yaoguang.appcommon.phone.my.my.event.MyEvent;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.driver.DriverDataSource;
import com.yaoguang.driver.phone.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentMy2Binding;
import com.yaoguang.driver.phone.main.MainFragment;
import com.yaoguang.driver.phone.my.authentication.drivinglicense.DrivingLicenseAuthenticationFragment;
import com.yaoguang.driver.phone.my.authentication.heavyvehicle.HeavyVehicleAuthenticationFragment;
import com.yaoguang.driver.phone.my.authentication.personal.RealNameAuthenticationFragment;
import com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment;
import com.yaoguang.driver.phone.my.contact.ContactFragment;
import com.yaoguang.driver.phone.my.message.PlatformMessageFragment;
import com.yaoguang.driver.phone.my.messageorder.MessageOrderFragment;
import com.yaoguang.driver.phone.my.modify.sign.ModifySignFragment;
import com.yaoguang.driver.phone.my.myorder2.MyOrderFragment;
import com.yaoguang.driver.phone.my.personal.PersonalInformationFragment;
import com.yaoguang.driver.phone.my.personal.changebackground.ChangeBackgroundFragment;
import com.yaoguang.driver.phone.my.setting.SettingFragment;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.UnreadNum;
import com.yaoguang.greendao.entity.driver.UserDriverCar;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

import static com.yaoguang.appcommon.common.view.EditTextFragment.VALUE;
import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.EDIT_BACKGROUND_PHOTO;
import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.FLAG_AVATAR;
import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.FLAG_SIGN;
import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.REFRESH_UNREAD_CONTACT_COUNT;
import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.REFRESH_UNREAD_COUNT;
import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.UPDATE;
import static com.yaoguang.driver.phone.my.authentication.heavyvehicle.HeavyVehicleAuthenticationFragment.MOTOR_TRACTOR;
import static com.yaoguang.driver.phone.my.authentication.heavyvehicle.HeavyVehicleAuthenticationFragment.SEMITRAILER;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_DRIVER_AUDIT;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_DRIVER_LICENCE_PASS;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_DRIVER_NO_PASS;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_MOTOR_TRACTOR_AUDIT;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_MOTOR_TRACTOR_NO_PASS;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_MOTOR_TRACTOR_PASS;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_SEMITRAILER_AUDIT;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_SEMITRAILER_NO_PASS;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_SEMITRAILER_PASS;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_USER_INFO_AUDIT;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_USER_INFO_NO_PASS;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_USER_INFO_PASS;


/**
 * 我的tab页
 * Created by 韦理英
 * on 2017/4/24 0024.
 */
public class MyFragment extends BaseFragmentDataBind<FragmentMy2Binding> implements MyFragmentContacts.View {

    MyFragmentContacts.Presenter mPresenter;
    CompositeDisposable mCompositeDisposable;
    DriverDataSource mDriverDataSource = new DriverDataSource();


    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    private Drawable mLastDrawable;
    private DialogHelper mDialogHelper;

    private int mRandomNum = -1;
    private int mScrollY = 0;

    @Override
    public void onDestroyView() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        mCompositeDisposable.clear();

        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    public void init() {

        EventBus.getDefault().register(this);

        mPresenter = new MyFragmentPresenter(this);
        mCompositeDisposable = new CompositeDisposable();

        mDataBinding.ivUserBackground.setFocusable(true);
        mDataBinding.ivUserBackground.setFocusableInTouchMode(true);
        mDataBinding.ivUserBackground.requestFocus();

        mDataBinding.buttonBarLayout.setAlpha(0);
        mDataBinding.toolbar.setBackgroundColor(0);
        mPresenter.subscribe();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my2;
    }

    @Override
    public void initListener() {

        // 我的订单
        mDataBinding.rlMyOrder.setOnClickListener(v -> {
            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).start(MyOrderFragment.newInstance());
            }
        });
        // 我的关联
        mDataBinding.rlMyConnection.setOnClickListener(v -> {
            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).start(ContactFragment.newInstance());
            }
        });
        // 修改密码
//        ((MainFragment) getParentFragment()).start(ModifyPassFragment.newInstance());
        // 业务消息
        mDataBinding.rlBusinessMessage.setOnClickListener(v -> {
            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).startForResult(MessageOrderFragment.newInstance(), REFRESH_UNREAD_COUNT);
            }
        });
        // 平台公告
        mDataBinding.rlPlatformBulletin.setOnClickListener(v -> {
            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).startForResult(PlatformMessageFragment.newInstance(), REFRESH_UNREAD_COUNT);
            }
        });
        // 设置
        mDataBinding.rlSetUp.setOnClickListener(v -> {
            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).start(SettingFragment.newInstance());
            }
        });

        // toolbar 渐变颜色
        mDataBinding.scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(60);
            private int color = ContextCompat.getColor(_mActivity, R.color.primary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    mDataBinding.toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                    mDataBinding.buttonBarLayout.setAlpha(1f * mScrollY / h);
                }
                lastScrollY = scrollY;
            }
        });

        // 头像
        mDataBinding.ivAvatar.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).startForResult(PersonalInformationFragment.newInstance(), UPDATE);
            }
        });
        // 打开背景图
        mDataBinding.ivUserBackground.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).startForResult(ChangeBackgroundFragment.newInstance(DataStatic.getInstance().getDriver().getBackgroundPicture()), UPDATE);
            }
        });
        // 背景切换
        mDataBinding.tvSwitchBackground.setOnClickListener(v -> randomAvatar());
        // 驾驶技能
        mDataBinding.tvDrivingLicense.setOnClickListener(v -> {
            if (getParentFragment() == null || getContext() == null) return;

            // 重新加载 LoginDriver
            mPresenter.openDrivingLicenseFragment();
        });
        // 索引车
        mDataBinding.llMotorTractor.setOnClickListener(v -> mPresenter.openMotorTractorOrSemiTrailerFragment(0));
        // 半挂车
        mDataBinding.llSemiTrailer.setOnClickListener(v -> mPresenter.openMotorTractorOrSemiTrailerFragment(1));
        // 个人简介
        mDataBinding.cvDsc.setOnClickListener(v -> {
            if (getParentFragment() == null || getContext() == null) return;

            String sign = DataStatic.getInstance().getDriver().getSign();
            ((MainFragment) getParentFragment()).startForResult(new ModifySignFragment()
                    .newInstance(Constants.APP_DRIVER, sign.startsWith("-") && sign.length() <= 1 ? "" : sign), FLAG_SIGN);
        });

    }

    /**
     * 检测是否已经申请个人申请
     * 如何没有通过个人申请认证
     * 需要先个人认证
     */
    private boolean checkPersonalCertification(LoginDriver loginDriver) {
        if (getContext() == null) return false;

        if (loginDriver.getUserInfo().getIdAuditStatus() == 2) {
            return true;
        }

        if (mDialogHelper == null)
            mDialogHelper = new DialogHelper(getContext(), "提示", "个人身份认证通过后，才能进行驾驶技能认证", "个人身份验证", "取消", new CommonDialog.Listener() {
                @Override
                public void ok(String content) {
                    if (getParentFragment() == null) return;

                    switch (loginDriver.getUserInfo().getIdAuditStatus()) {
                        case 0: // 未认证
                            ((MainFragment) getParentFragment()).startForResult(RealNameAuthenticationFragment.newInstance(true), UPDATE);
                            break;
                        case 1: // 待认证
                            ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginDriver, FLAG_USER_INFO_AUDIT), UPDATE);
                            break;
                        case 2: // 通过
                            ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginDriver, FLAG_USER_INFO_PASS), UPDATE);
                            break;
                        case 3: // 不通过
                            ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginDriver, FLAG_USER_INFO_NO_PASS), UPDATE);
                            break;
                    }

                }

                @Override
                public void cancel() {

                }
            });
        mDialogHelper.show();
        return false;
    }

    @Override
    public void showDriverInfo(Driver driver, List<UserDriverCar> carInfo, UserDriverLicence userDriverLicence) {
        // 名字
        mDataBinding.tvName.setText(ObjectUtils.parseString(driver.getNickName()));
        // 手机
        mDataBinding.tvTel.setText(driver.getMobile());
        //  头像
        GlideManager.getInstance().withRounded(getContext(), driver.getPhoto(), mDataBinding.ivAvatar, R.drawable.ic_jz_sb);
        // banner背景自定义
        GlideManager.getInstance().withCompress(getContext(), driver.getBackgroundPicture(), 768, 1024, -1, mDataBinding.ivUserBackground);
        // 个人简介
        mDataBinding.tvDsc.setText(ObjectUtils.parseString2(driver.getSign(), "可以写上您的行业经验..."));
        // 驾驶技能
        setAuditStatus(mDataBinding.tvDrivingSkills, userDriverLicence.getAuditStatus(), userDriverLicence.getCarType());
        // 索引车
        setAuditStatus(mDataBinding.tvIndexCarLicensePlate, Integer.parseInt(carInfo.get(0).getAuditStatus()), carInfo.get(0).getLicenceNumber());
        // 半挂车
        setAuditStatus(mDataBinding.tvSemiTrailer, Integer.parseInt(carInfo.get(1).getAuditStatus()), carInfo.get(1).getLicenceNumber());
        // 个人身份认证
        mDataBinding.tvIdAuditStatus.setImageResource(driver.getIdAuditStatus() != null && driver.getIdAuditStatus() == 2 ? R.drawable.ic_rz_sf : R.drawable.ic_wrz_sf);
    }

    /**
     * 认证状态(0:未认证 1:待认证 2:通过 3:不通过)
     *
     * @param tv          设置的view
     * @param auditStatus 状态
     * @param carNumber   车牌
     */
    public void setAuditStatus(TextView tv, int auditStatus, String carNumber) {
        String value = null;
        switch (auditStatus) {
            case 0:
                value = "去认证";
                tv.setBackgroundResource(R.drawable.stroke_gray_corners);
                tv.setTextColor(UiUtils.getColor(R.color.orange500));
                break;
            case 1:
                value = "审核中";
                tv.setBackgroundResource(R.drawable.stroke_gray_corners);
                tv.setTextColor(Color.parseColor("#0096ff"));
                break;
            case 2:
                value = carNumber;
                tv.setTextColor(Color.WHITE);
                tv.setBackgroundResource(R.drawable.orange_corners);
                break;
            case 3:
                value = "认证失败";
                tv.setBackgroundResource(R.drawable.stroke_gray_corners);
                tv.setTextColor(Color.parseColor("#f43434"));
                break;
        }
        tv.setText(value);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null || images.size() <= 0) {
                    return;
                }

                switch (requestCode) {
                    // 设置头像
                    case FLAG_AVATAR:
                        mPresenter.uploadAvatar(_mActivity, getContext(), images.get(0).path);
                        break;
                    case EDIT_BACKGROUND_PHOTO:
                        // 设置背景
                        String userBackgroundUrl = images.get(0).path;
                        mPresenter.uploadBackgroundUI(_mActivity, getContext(), userBackgroundUrl);
                        break;
                }
            }
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (mPresenter == null || AppClickUtil.isDuplicateClick()) return;

        if (requestCode == UPDATE) {
//            if (resultCode == FLAG_UPDATE_AVATAR) {
//            }
            mPresenter.showLocalDriverInfo();
        } else if (requestCode == FLAG_SIGN && data != null && data.getString(VALUE) != null) {

            Driver driver = DataStatic.getInstance().getDriver();
            driver.setSign(data.getString(VALUE));
            DataStatic.getInstance().setDriver(driver);

            mPresenter.showLocalDriverInfo();
        }
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Subscribe(sticky = true)
    public void MyEvent(MyEvent event) {
        if (isEvent) {
            if (event.getType().equals(ObjectUtils.parseString(REFRESH_UNREAD_COUNT))) {
                refreshUnReadCount();
            }
            if (event.getType().equals(ObjectUtils.parseString(REFRESH_UNREAD_CONTACT_COUNT))) {
                // 我的关联
                RongIM.getInstance().getUnreadCount(new RongIMClient.ResultCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        int count = RongIM.getInstance().getUnreadCount(Conversation.ConversationType.PRIVATE);
                        if (count > 0) {
                            mDataBinding.tvMyConnection.setText(ObjectUtils.parseString(count));
                            mDataBinding.flTitleMyConnection.setVisibility(View.VISIBLE);
                        } else {
                            mDataBinding.flTitleMyConnection.setVisibility(View.GONE);
                        }
                        // 赋值给父窗体
                        if (getParentFragment() != null) {
                            ((MainFragment) getParentFragment()).setContactCount(count);
                        }
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        mDataBinding.flTitleMyConnection.setVisibility(View.GONE);
                    }
                }, Conversation.ConversationType.PRIVATE);
            }
        }
        EventBus.getDefault().removeStickyEvent(event);
    }

    /**
     * 设置是否显示或隐藏消息红点
     *
     * @param result 消息数据
     */
    @Override
    public void setUnreadNum(UnreadNum result) {
        if (getParentFragment() == null) return;

        // 业务消息
        if (result.getOrderMsgNumber() > 0 && result.getOrderMsgNumber() < 100) {
            mDataBinding.tvBusinessMessage.setText(ObjectUtils.parseString(result.getOrderMsgNumber()));
            mDataBinding.flBusinessMessage.setVisibility(View.VISIBLE);
        } else if (result.getOrderMsgNumber() > 99) {
            mDataBinding.tvBusinessMessage.setText("...");
            mDataBinding.flBusinessMessage.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.tvBusinessMessage.setText(null);
            mDataBinding.flBusinessMessage.setVisibility(View.GONE);
        }

        // 平台公告
        if (result.getSysMsgNumber() > 0 && result.getSysMsgNumber() < 100) {
            mDataBinding.tvPlatformBulletin.setText(ObjectUtils.parseString(result.getSysMsgNumber()));
            mDataBinding.flPlatformBulletin.setVisibility(View.VISIBLE);
        } else if (result.getSysMsgNumber() > 99) {
            mDataBinding.tvPlatformBulletin.setText("...");
            mDataBinding.flPlatformBulletin.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.tvPlatformBulletin.setText(null);
            mDataBinding.flPlatformBulletin.setVisibility(View.GONE);
        }

        // 赋值给父窗体
        ((MainFragment) getParentFragment()).setMessageCount(result.getSysMsgNumber(), result.getOrderMsgNumber());
    }

    @Override
    public void openDrivingLicenseFragment(LoginDriver loginDriver) {

        if (!checkPersonalCertification(loginDriver)) return;

        Integer auditStatus = loginDriver.getLicenceInfo().getAuditStatus();
        switch (auditStatus) {
            case 0: // 未认证
                if (getParentFragment() != null) {
                    ((MainFragment) getParentFragment()).startForResult(DrivingLicenseAuthenticationFragment.newInstance(loginDriver), UPDATE);
                }
                break;
            case 1: // 待认证
                if (getParentFragment() != null) {
                    ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginDriver, FLAG_DRIVER_AUDIT), UPDATE);
                }
                break;
            case 2: // 通过
                if (getParentFragment() != null) {
                    ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginDriver, FLAG_DRIVER_LICENCE_PASS), UPDATE);
                }
                break;
            case 3: // 不通过
                if (getParentFragment() != null) {
                    ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginDriver, FLAG_DRIVER_NO_PASS), UPDATE);
                }
                break;
        }
    }

    @Override
    public void openMotorTractorFragment(LoginDriver loginDriver) {

        if (getParentFragment() == null || getContext() == null) return;

        if (loginDriver.getCarInfo().isEmpty()) return;

        switch (loginDriver.getCarInfo().get(0).getAuditStatus()) {
            case "0": // 未认证
                ((MainFragment) getParentFragment()).startForResult(HeavyVehicleAuthenticationFragment.newInstance(loginDriver, MOTOR_TRACTOR), UPDATE);
                break;
            case "1": // 待认证
                ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginDriver, FLAG_MOTOR_TRACTOR_AUDIT), UPDATE);
                break;
            case "2": // 通过
                ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginDriver, FLAG_MOTOR_TRACTOR_PASS), UPDATE);
                break;
            case "3": // 不通过
                ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginDriver, FLAG_MOTOR_TRACTOR_NO_PASS), UPDATE);
                break;
        }

    }

    @Override
    public void openSemiTrailerFragment(LoginDriver loginDriver) {
        if (getParentFragment() == null || getContext() == null) return;
        if (loginDriver.getCarInfo().size() < 1) return;
        switch (loginDriver.getCarInfo().get(1).getAuditStatus()) {
            case "0": // 未认证
                ((MainFragment) getParentFragment()).startForResult(HeavyVehicleAuthenticationFragment.newInstance(loginDriver, SEMITRAILER), UPDATE);
                break;
            case "1": // 待认证
                ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginDriver, FLAG_SEMITRAILER_AUDIT), UPDATE);
                break;
            case "2": // 通过
                ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginDriver, FLAG_SEMITRAILER_PASS), UPDATE);
                break;
            case "3": // 不通过
                ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginDriver, FLAG_SEMITRAILER_NO_PASS), UPDATE);
                break;
        }
    }

    /**
     * 随机头像
     */
    private void randomAvatar() {
        if (AppClickUtil.isDuplicateClick()) return;
        if (getContext() == null) return;

        int randomNum = new Random().nextInt(4) + 1;
        if (mRandomNum == randomNum) {
            randomAvatar();
            return;
        }
        mRandomNum = randomNum;

        // 设置随机图
        mLastDrawable = mDataBinding.ivUserBackground.getDrawable();

        // 转成网址
        String[] web = new String[]{"http://img.huoyunji.com/app_photo_backgroundPicture01_iOS.jpg",
                "http://img.huoyunji.com/app_photo_backgroundPicture02_iOS.jpg",
                "http://img.huoyunji.com/app_photo_backgroundPicture03_iOS.jpg",
                "http://img.huoyunji.com/app_photo_backgroundPicture04_iOS.jpg",
                "http://img.huoyunji.com/app_photo_backgroundPicture05_iOS.jpg"
        };

        Driver driver = DataStatic.getInstance().getDriver();
        driver.setBackgroundPicture(web[randomNum]);

        mDriverDataSource.update(driver)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, MyFragment.this) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        setFragmentResult(UPDATE, null);
                        DataStatic.getInstance().setDriver(driver);

                        // 设置新的背景
                        GlideManager.getInstance().withCompress(getContext(), web[mRandomNum], 768, 1024, R.drawable.ic_grbg00, mDataBinding.ivUserBackground);
                        YoYo.with(Techniques.FadeIn).playOn(mDataBinding.ivUserBackground);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mDataBinding.ivUserBackground.setImageDrawable(mLastDrawable);
                    }

                    @Override
                    public void onFail(BaseResponse<String> response) {
                        super.onFail(response);
                        mDataBinding.ivUserBackground.setImageDrawable(mLastDrawable);
                    }
                });
    }

    /**
     * 描述：刷新未读数
     */
    public void refreshUnReadCount() {
        mPresenter.getUnreadNum();
    }

    /**
     * 处理回退事件
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            App.getInstance().finishAllActivity();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(BaseApplication.getInstance(), R.string.keydownquitapp, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

}
