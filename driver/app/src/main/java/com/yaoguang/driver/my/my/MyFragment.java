package com.yaoguang.driver.my.my;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.yaoguang.appcommon.common.eventbus.MyFragmentEvent;
import com.yaoguang.appcommon.common.finalrequest.DriverRequest;
import com.yaoguang.appcommon.contact.MyContactFragment;
import com.yaoguang.common.Glide.impl.GlideManager;
import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.common.appcommon.dialog.CommonDialog;
import com.yaoguang.common.appcommon.dialog.DialogHelper;
import com.yaoguang.common.common.AppClickUtil;

import com.yaoguang.common.common.Constants;
import com.yaoguang.common.common.ObjectUtils;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.driver.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.base.DataBindingFragment;
import com.yaoguang.driver.databinding.FragmentMyBinding;
import com.yaoguang.driver.main.MainFragment;
import com.yaoguang.driver.my.authentication.drivinglicense.DrivingLicenseAuthenticationFragment;
import com.yaoguang.driver.my.authentication.heavyvehicle.HeavyVehicleAuthenticationFragment;
import com.yaoguang.driver.my.authentication.personal.RealNameAuthenticationFragment;
import com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment;
import com.yaoguang.driver.my.changebackground.ChangeBackgroundFragment;
import com.yaoguang.driver.my.message.PlatformMessageFragment;
import com.yaoguang.driver.my.modify.password.ModifyPassFragment;
import com.yaoguang.driver.my.modify.sign.ModifySignFragment;
import com.yaoguang.driver.my.my.adater.MyAdapter;
import com.yaoguang.driver.my.myorder.MyOrderFragment;
import com.yaoguang.driver.my.personal.PersonalInformationFragment;
import com.yaoguang.driver.my.setting.SettingFragment;
import com.yaoguang.driver.order.message.MessageOrderChildFragment;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.Injections;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.MyItem;
import com.yaoguang.greendao.entity.UnreadNum;
import com.yaoguang.lib.annotation.aspect.BackKey;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.yaoguang.appcommon.common.finalrequest.DriverRequest.CHANGE_BACKGROUND_FRAGMENT;
import static com.yaoguang.appcommon.common.view.EditTextFragment.VALUE;
import static com.yaoguang.driver.my.authentication.heavyvehicle.HeavyVehicleAuthenticationFragment.MOTOR_TRACTOR;
import static com.yaoguang.driver.my.authentication.heavyvehicle.HeavyVehicleAuthenticationFragment.SEMITRAILER;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_DRIVER_AUDIT;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_DRIVER_LICENCE_PASS;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_DRIVER_NO_PASS;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_MOTOR_TRACTOR_AUDIT;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_MOTOR_TRACTOR_NO_PASS;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_MOTOR_TRACTOR_PASS;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_SEMITRAILER_AUDIT;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_SEMITRAILER_NO_PASS;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_SEMITRAILER_PASS;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_USER_INFO_AUDIT;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_USER_INFO_NO_PASS;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_USER_INFO_PASS;


/**
 * 我的tab页
 * Created by 韦理英
 * on 2017/4/24 0024.
 */
public class MyFragment extends DataBindingFragment<MyFragmentPresenter, FragmentMyBinding> implements MyFragmentContacts.View, BaseLoadMoreRecyclerAdapter.OnRecyclerViewItemClickListener {
    public final static int UPDATE = DriverRequest.REQUESTCODE_MY + 1;
    public final static int FLAG_SIGN = DriverRequest.REQUESTCODE_MY + 2;
    public final static int FLAG_AVATAR = DriverRequest.REQUESTCODE_MY + 3;
    public final static int FLAG_UPDATE_AVATAR = DriverRequest.REQUESTCODE_MY + 31;
    public static final int REFRESH_UNREAD_COUNT = DriverRequest.REQUESTCODE_MY + 4;
    public final static int EDIT_BACKGROUND_PHOTO = DriverRequest.REQUESTCODE_MY + 5;

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    /**
     * [我的]列表数据，可自定义
     */
    private List<MyItem> mData = new ArrayList<>();

    String[] names = new String[]{"我的订单", "我的关联", "业务消息", "平台公告", "设置"};

    int[] icons = new int[]{R.drawable.ic_dingdang_20, R.drawable.ic_guanlian_20,
            R.drawable.ic_qyxx_20, R.drawable.ic_ptgg_20, R.drawable.ic_set_grey};
    protected MyAdapter myAdapter;
    private LoginResult loginResult;
    private Drawable mLastDrawable;
    private DialogHelper mDialogHelper;

    private int mRandomNum = -1;
    private String mLastAvatar;
    private boolean isAvatarAnim;

    @Override
    public void onDestroyView() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        super.onDestroyView();
    }

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        if (getContext() == null) return;
        mPresenter.setData(Injection.provideMessageRepository(getContext()));
        mPresenter.setDriverRepository(Injection.provideDriverRepository(getContext()));
        mPresenter.subscribe();
    }

    @Override
    protected void initView(View view) {
        if (getActivity() == null) return;

        mDataBinding.imgReturn.setVisibility(View.GONE);
        mDataBinding.toolbar.setBackgroundResource(0);
        mDataBinding.ivBackground.setBackgroundColor(Color.argb(0, 240, 132, 25));
        mDataBinding.toolbarTitle.setText(getString(R.string.my));
        mDataBinding.toolbarTitle.setShadowLayer(1, 0, 1, Color.BLACK);

        // 组装实体
        for (int i = 0; i < names.length; i++) {
            MyItem myItem = new MyItem();
            myItem.setTitle(names[i]);
            myItem.setIcon(icons[i]);
            mData.add(myItem);
        }

        myAdapter = new MyAdapter();
        mDataBinding.recyclerView.setAdapter(myAdapter);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mDataBinding.recyclerView.setHasFixedSize(true);

        myAdapter.getList().clear();
        myAdapter.appendToList(mData);
        myAdapter.notifyDataSetChanged();
        myAdapter.setOnItemClickListener(MyFragment.this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initListener() {
        // 头像
        mDataBinding.ivAvatar.setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            if (getParentFragment() != null) {
                ((MainFragment) getParentFragment()).startBrotherFourResultFragment(PersonalInformationFragment.newInstance(), UPDATE);
            }
        });
        // 背景
        mDataBinding.ivUserBackground.setOnClickListener(v -> {
//            if (AppClickUtil.isDuplicateClick()) return;
//
//            if (getParentFragment() != null && loginResult != null) {
//                ((MainFragment) getParentFragment()).startBrotherFourResultFragment(ChangeBackgroundFragment.newInstance(loginResult.getUserInfo().getBackgroundPicture()), UPDATE);
//            }
        });
        // 背景切换
        mDataBinding.tvSwitchBackground.setOnClickListener(v -> randomAvatar());
        // 驾驶技能
        mDataBinding.tvDrivingLicense.setOnClickListener(v -> {
            if (getParentFragment() == null || getContext() == null) return;

            if (!checkPersonalCertification()) return;

            LoginResult loginResult = Injection.provideDriverRepository(getContext()).getLoginResult();
            Integer auditStatus = loginResult.getLicenceInfo().getAuditStatus();
            switch (auditStatus) {
                case 0: // 未认证
                    ((MainFragment) getParentFragment()).startBrotherFourResultFragment(DrivingLicenseAuthenticationFragment.newInstance(loginResult), UPDATE);
                    break;
                case 1: // 待认证
                    ((MainFragment) getParentFragment()).startBrotherFourResultFragment(AuthenticationResultFragment.newInstance(loginResult, FLAG_DRIVER_AUDIT), UPDATE);
                    break;
                case 2: // 通过
                    ((MainFragment) getParentFragment()).startBrotherFourResultFragment(AuthenticationResultFragment.newInstance(loginResult, FLAG_DRIVER_LICENCE_PASS), UPDATE);
                    break;
                case 3: // 不通过
                    ((MainFragment) getParentFragment()).startBrotherFourResultFragment(AuthenticationResultFragment.newInstance(loginResult, FLAG_DRIVER_NO_PASS), UPDATE);
                    break;
            }
        });
        // 索引车
        mDataBinding.llMotorTractor.setOnClickListener(v -> {
            if (getParentFragment() == null || getContext() == null) return;

            LoginResult loginResult = Injection.provideDriverRepository(getContext()).getLoginResult();
            if (loginResult.getCarInfo().isEmpty()) return;
//                mLoginResult.getCarInfo().get(0).setAuditStatus("3");
            switch (loginResult.getCarInfo().get(0).getAuditStatus()) {
                case "0": // 未认证
                    ((MainFragment) getParentFragment()).startBrotherFourResultFragment(HeavyVehicleAuthenticationFragment.newInstance(loginResult, MOTOR_TRACTOR), UPDATE);
                    break;
                case "1": // 待认证
                    ((MainFragment) getParentFragment()).startBrotherFourResultFragment(AuthenticationResultFragment.newInstance(loginResult, FLAG_MOTOR_TRACTOR_AUDIT), UPDATE);
                    break;
                case "2": // 通过
                    ((MainFragment) getParentFragment()).startBrotherFourResultFragment(AuthenticationResultFragment.newInstance(loginResult, FLAG_MOTOR_TRACTOR_PASS), UPDATE);
                    break;
                case "3": // 不通过
                    ((MainFragment) getParentFragment()).startBrotherFourResultFragment(AuthenticationResultFragment.newInstance(loginResult, FLAG_MOTOR_TRACTOR_NO_PASS), UPDATE);
                    break;
            }
        });
        // 半挂车
        mDataBinding.llSemiTrailer.setOnClickListener(v -> {
            if (getParentFragment() == null || getContext() == null) return;

            LoginResult loginResult = Injection.provideDriverRepository(getContext()).getLoginResult();
            if (loginResult.getCarInfo().size() < 1) return;
//                mLoginResult.getCarInfo().get(1).setAuditStatus("3");
            switch (loginResult.getCarInfo().get(1).getAuditStatus()) {
                case "0": // 未认证
                    ((MainFragment) getParentFragment()).startBrotherFourResultFragment(HeavyVehicleAuthenticationFragment.newInstance(loginResult, SEMITRAILER), UPDATE);
                    break;
                case "1": // 待认证
                    ((MainFragment) getParentFragment()).startBrotherFourResultFragment(AuthenticationResultFragment.newInstance(loginResult, FLAG_SEMITRAILER_AUDIT), UPDATE);
                    break;
                case "2": // 通过
                    ((MainFragment) getParentFragment()).startBrotherFourResultFragment(AuthenticationResultFragment.newInstance(loginResult, FLAG_SEMITRAILER_PASS), UPDATE);
                    break;
                case "3": // 不通过
                    ((MainFragment) getParentFragment()).startBrotherFourResultFragment(AuthenticationResultFragment.newInstance(loginResult, FLAG_SEMITRAILER_NO_PASS), UPDATE);
                    break;
            }
        });
        // 个人简介
        mDataBinding.cvDsc.setOnClickListener(v -> {
            if (getParentFragment() == null || getContext() == null) return;

            String sign = Injection.provideDriverRepository(getContext()).getDriver().getSign();
            ((MainFragment) getParentFragment()).startBrotherFourResultFragment(new ModifySignFragment()
                    .newInstance(Constants.APP_DRIVER, sign.startsWith("-") && sign.length() <= 1 ? "" : sign), FLAG_SIGN);
        });
//        mDataBinding.nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                ULog.i("scrollX=" + scrollX + "scrollY=" + scrollY + "oldScrollX=" + oldScrollX + "oldScrollY=" + oldScrollY);
//
//                if (scrollY == 0) {
//                    mDataBinding.ivBackground.setBackgroundColor(Color.argb(0, 240, 132, 25));
//                }
//
//                if (scrollY > 70) {
//                    mDataBinding.ivBackground.setBackgroundColor(Color.argb(255, 240, 132, 25));
//                    YoYo.with(Techniques.FadeIn).playOn(mDataBinding.ivBackground);
//                }
////                mDataBinding.toolbar.setBackgroundColor(Color.argb(value, 240, 132, 25));
//            }
//        });
    }

    /**
     * 检测是否已经申请个人申请
     * 如何没有通过个人申请认证
     * 需要先个人认证
     */
    public boolean checkPersonalCertification() {
        if (getContext() == null) return false;

        LoginResult loginResult = Injection.provideDriverRepository(getContext()).getLoginResult();
        if (loginResult.getUserInfo().getIdAuditStatus() == 2) {
            return true;
        }

        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }

        mDialogHelper = new DialogHelper();
        mDialogHelper.showConfirmDialog(getContext(), "提示", "个人身份认证通过后，才能进行驾驶技能认证", "个人身份验证", "取消", new CommonDialog.Listener() {
            @Override
            public void ok() {
                if (getParentFragment() == null) return;

                switch (loginResult.getUserInfo().getIdAuditStatus()) {
                    case 0: // 未认证
                        ((MainFragment) getParentFragment()).startForResult(RealNameAuthenticationFragment.newInstance(), UPDATE);
                        break;
                    case 1: // 待认证
                        ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginResult, FLAG_USER_INFO_AUDIT), UPDATE);
                        break;
                    case 2: // 通过
                        ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginResult, FLAG_USER_INFO_PASS), UPDATE);
                        break;
                    case 3: // 不通过
                        ((MainFragment) getParentFragment()).startForResult(AuthenticationResultFragment.newInstance(loginResult, FLAG_USER_INFO_NO_PASS), UPDATE);
                        break;
                }

                if (mDialogHelper != null) {
                    mDialogHelper.hideDialog();
                }
            }

            @Override
            public void cancel() {

            }
        });
        return false;
    }


    @Subscribe
    public void myEvent(MyFragmentEvent event) {
    }

    @Override
    public void showDriverInfo(LoginResult result) {
        loginResult = result;

        // 名字
        mDataBinding.tvName.setText(ObjectUtils.parseString(result.getUserInfo().getNickName()));
        // 手机
        mDataBinding.tvTel.setText(result.getUserInfo().getMobile());
        //  头像
        GlideManager.getInstance().withRounded(getContext(), result.getUserInfo().getPhoto(), mDataBinding.ivAvatar, R.drawable.ic_grtouxiang);
        if (isAvatarAnim) {
            YoYo.with(Techniques.FadeIn).playOn(mDataBinding.ivAvatar);
            isAvatarAnim = false;
        }
//        }
        // banner背景自定义
        GlideManager.getInstance().withCompress(getContext(), result.getUserInfo().getBackgroundPicture(), 768, 1024, R.drawable.ic_grbg00, mDataBinding.ivUserBackground);
        // 个人简介
        mDataBinding.tvDsc.setText(ObjectUtils.parseString2(result.getUserInfo().getSign(), "可以写上您的行业经验..."));
        // 驾驶技能
        setAuditStatus(mDataBinding.tvDrivingSkills, result.getLicenceInfo().getAuditStatus(), result.getLicenceInfo().getLicenceNumber());
        // 索引车
        setAuditStatus(mDataBinding.tvIndexCarLicensePlate, Integer.parseInt(result.getCarInfo().get(0).getAuditStatus()), result.getCarInfo().get(0).getLicenceNumber());
        // 半挂车
        setAuditStatus(mDataBinding.tvSemiTrailer, Integer.parseInt(result.getCarInfo().get(1).getAuditStatus()), result.getCarInfo().get(1).getLicenceNumber());
        // 个人身份认证
        mDataBinding.tvIdAuditStatus.setImageResource(result.getUserInfo().getIdAuditStatus() == 2 ? R.drawable.ic_rz_sf : R.drawable.ic_wrz_sf);
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
                        mPresenter.uploadAvatar(images.get(0).path, getContext());
                        break;
                    case EDIT_BACKGROUND_PHOTO:
                        // 设置背景
                        String userBackgroundUrl = images.get(0).path;
                        mPresenter.uploadBackgroundUI(userBackgroundUrl, getContext());
                        break;
                }
            }
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (mPresenter == null || AppClickUtil.isDuplicateClick()) return;

        if (resultCode == REFRESH_UNREAD_COUNT) {
            mPresenter.getUnreadNum();
        } else if (requestCode == UPDATE) {
            if (resultCode == FLAG_UPDATE_AVATAR) {
                isAvatarAnim = true;
            }
            mPresenter.showLocalDriverInfo();
        } else if (requestCode == FLAG_SIGN && data != null && data.getString(VALUE) != null) {
            LoginResult loginResult = Injection.provideDriverRepository(getContext()).getLoginResult();
            loginResult.getUserInfo().setSign(data.getString(VALUE));
            Injection.provideDriverRepository(getContext()).saveOrUpdate(loginResult);

            mPresenter.showLocalDriverInfo();
        }
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(View itemView, Object item, int position) {
        if (AppClickUtil.isDuplicateClick() || getParentFragment() == null) return;

        String value = ((TextView) itemView.findViewById(R.id.tvName)).getText().toString();
        switch (value) {
            case "我的订单":
                ((MainFragment) getParentFragment()).startBrotherFragment(MyOrderFragment.newInstance());
                break;
            case "我的关联":
                ((MainFragment) getParentFragment()).startBrotherFragment(MyContactFragment.newInstance("0"));
                break;
            case "修改密码":
                ((MainFragment) getParentFragment()).startBrotherFragment(ModifyPassFragment.newInstance());
                break;
            case "业务消息":
                ((MainFragment) getParentFragment()).startBrotherFourResultFragment(MessageOrderChildFragment.newInstance(4), REFRESH_UNREAD_COUNT);
                break;
            case "平台公告":
                ((MainFragment) getParentFragment()).startBrotherFourResultFragment(PlatformMessageFragment.newInstance(), REFRESH_UNREAD_COUNT);
                break;
            case "个人证件":
//                ((MainFragment) getParentFragment()).startBrotherFragment(IdNumberFragment.newInstance());
                break;
            case "车辆证件":
//                ((MainFragment) getParentFragment()).startBrotherFragment(CarIdNumberFragment.newInstance());
                break;
            case "个人资料":
                ((MainFragment) getParentFragment()).startBrotherFragment(PersonalInformationFragment.newInstance());
                break;
            case "设置":
                ((MainFragment) getParentFragment()).startBrotherFragment(SettingFragment.newInstance());
                break;
        }
    }

    /**
     * 设置是否显示或隐藏消息红点
     *
     * @param result 消息数据
     */
    @Override
    public void setUnreadNum(UnreadNum result) {
        if (getParentFragment() == null) return;
//        click.setOrderMsgNumber(123);
//        click.setSysMsgNumber(33);

        // 业务消息
        if (result.getOrderMsgNumber() > 0 && result.getOrderMsgNumber() < 100) {
            MyItem myItem = mData.get(2);
            myItem.setValue(result.getOrderMsgNumber() + "");
            mData.set(2, myItem);
        } else if (result.getOrderMsgNumber() > 99) {
            mData.get(2).setValue("...");
        } else {
            mData.get(2).setValue(null);
        }

        // 平台公告
        if (result.getSysMsgNumber() > 0 && result.getSysMsgNumber() < 100) {
            MyItem myItem = mData.get(3);
            myItem.setValue(result.getSysMsgNumber() + "");
            mData.set(3, myItem);
        } else if (result.getSysMsgNumber() > 99) {
            mData.get(3).setValue("...");
        } else {
            mData.get(3).setValue(null);
        }

        myAdapter.getList().clear();
        myAdapter.appendToList(mData);
        myAdapter.notifyDataSetChanged();

        if (result.getSysMsgNumber() > 0 || result.getOrderMsgNumber() > 0) {
            ((MainFragment) getParentFragment()).showUnreadNumber();
        } else {
            ((MainFragment) getParentFragment()).hideUnreadNumber();
        }
    }

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
//        String background = "ic_grbg0";

//        String newBackground = background + randomNum;
//        ULog.i("new background=" + newBackground);
//        int drawableId = getResources().getIdentifier(newBackground, "drawable", AppUtils.getAppPackageName());
//        mDataBinding.ivUserBackground.setImageResource(drawableId);

        // 转成网址
        String[] web = new String[]{"http://img.huoyunji.com/app_photo_backgroundPicture01_iOS.jpg",
                "http://img.huoyunji.com/app_photo_backgroundPicture02_iOS.jpg",
                "http://img.huoyunji.com/app_photo_backgroundPicture03_iOS.jpg",
                "http://img.huoyunji.com/app_photo_backgroundPicture04_iOS.jpg",
                "http://img.huoyunji.com/app_photo_backgroundPicture05_iOS.jpg"
        };

        LoginResult loginResult = Injection.provideDriverRepository(getContext()).getLoginResult();
        loginResult.getUserInfo().setBackgroundPicture(web[randomNum]);
        Injection.provideDriverRepository(getContext()).update(loginResult.getUserInfo()).subscribe(value -> {
            if (value.getState().equals("200")) {
                setFragmentResult(MyFragment.UPDATE, null);
                Injection.provideDriverRepository(getContext()).saveOrUpdate(loginResult);

                // 设置新的背景
                GlideManager.getInstance().withCompress(getContext(), web[mRandomNum], 768, 1024, R.drawable.ic_grbg00, mDataBinding.ivUserBackground);
                YoYo.with(Techniques.FadeIn).playOn(mDataBinding.ivUserBackground);
            } else {
                showToast("切换头像失败");
                mDataBinding.ivUserBackground.setImageDrawable(mLastDrawable);
            }
            hideDialog();
        }, throwable -> {
            mDataBinding.ivUserBackground.setImageDrawable(mLastDrawable);
            showToast("切换头像失败");
            hideDialog();
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
    @BackKey
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            App.getInstance().finishAllActivity();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(App.getInstance(), R.string.keydownquitapp, Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
