package com.yaoguang.driver.phone.my.personal;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.driver.DriverDataSource;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentPersonalinformationBinding;
import com.yaoguang.driver.phone.my.authentication.drivinglicense.DrivingLicenseAuthenticationFragment;
import com.yaoguang.driver.phone.my.authentication.heavyvehicle.HeavyVehicleAuthenticationFragment;
import com.yaoguang.driver.phone.my.authentication.personal.RealNameAuthenticationFragment;
import com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment;
import com.yaoguang.driver.phone.my.bindphone.BindNewPhoneFragment;
import com.yaoguang.driver.phone.my.modify.email.ModifyMailFragment;
import com.yaoguang.driver.phone.my.modify.nickname.ModifyNickNameFragment;
import com.yaoguang.driver.phone.my.modify.qq.ModifyQQFragment;
import com.yaoguang.driver.phone.my.modify.sign.ModifySignFragment;
import com.yaoguang.driver.phone.my.my.adater.PersonalInformationAndFooterAdapter;
import com.yaoguang.driver.phone.my.personal.changebackground.ChangeBackgroundFragment;
import com.yaoguang.driver.phone.my.personal.event.UpdatePersonalEvent;
import com.yaoguang.appcommon.localselect.MyLocalSelectFragment;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.MyItem;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.adapter.BaseRecyclerHeaderAndFooterAdapter;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.utils.AppUtils;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.ProvincesCitysAreasSelectUtils;
import com.yaoguang.lib.common.RegexValidator;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.lib.common.imagepicker.ImagePickerUtils;
import com.yaoguang.lib.entity.ProvinceBeans;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.qinui.QiNiuManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.yaoguang.appcommon.common.finalrequest.DriverRequest.REQUESTCODE_PERSONAL_INFORMATION;
import static com.yaoguang.appcommon.common.view.EditTextFragment.VALUE;
import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.FLAG_UPDATE_AVATAR;
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
import static com.yaoguang.appcommon.localselect.MyLocalSelectFragment.SELECT_LOCAL_RESULT;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/02
 * 描    述：
 * 个人信息
 * <p>
 * update zhongjh
 * data 2018-04-03
 * <p>
 * =====================================
 */
public class PersonalInformationFragment extends BaseFragmentDataBind<FragmentPersonalinformationBinding> implements PersonalInformationContacts.View, BaseRecyclerHeaderAndFooterAdapter.OnRecyclerViewItemClickListener {

    PersonalInformationContacts.Presenter mPresenter;

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    DriverDataSource mDriverDataSource = new DriverDataSource();

    // 用户上传自定义图片
    private final static int FLAG_EMAIL = REQUESTCODE_PERSONAL_INFORMATION + 1;
    private final static int FLAG_SIGN = REQUESTCODE_PERSONAL_INFORMATION + 2;
    public static final int AVATAR = REQUESTCODE_PERSONAL_INFORMATION + 3;
    public static final int UPDATE = REQUESTCODE_PERSONAL_INFORMATION + 4;
    public static final int FLAG_NICK_NAME = REQUESTCODE_PERSONAL_INFORMATION + 5;
    public static final int FLAG_QQ = REQUESTCODE_PERSONAL_INFORMATION + 6;
    public static final int FLAG_HOMETOWN = REQUESTCODE_PERSONAL_INFORMATION + 7;
    public static final int FLAG_PERMANENT = REQUESTCODE_PERSONAL_INFORMATION + 8;
    public static final int EDIT_BACKGROUND_PHOTO = REQUESTCODE_PERSONAL_INFORMATION + 9;

    // 列表数据
    private List<MyItem> mData = new ArrayList<>();

    PersonalInformationAndFooterAdapter mPersonalInformationAdapter;
    ProvincesCitysAreasSelectUtils guanjiUtils;
    ProvincesCitysAreasSelectUtils cityUtils;
    ProvinceBeans guanjiBeans;
    ProvinceBeans cityBeans;

    private Drawable mLastDrawable;
    private LoginDriver mLoginDriver;
    private DialogHelper mDialogHelper;

    public static PersonalInformationFragment newInstance() {
        return new PersonalInformationFragment();
    }

    @Override
    public void init() {

        EventBus.getDefault().register(this);

        mPresenter = new PersonalInformationPresenter(this, getContext());

        mToolbarCommonBinding.toolbarTitle.setText("个人信息");

        mPersonalInformationAdapter = new PersonalInformationAndFooterAdapter();
        mDataBinding.recyclerView.setAdapter(mPersonalInformationAdapter);
        mDataBinding.recyclerView.setBackgroundResource(R.color.white);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mDataBinding.recyclerView.setHasFixedSize(true);

        mPersonalInformationAdapter.setOnItemClickListener(PersonalInformationFragment.this);
        View v = View.inflate(getContext(), R.layout.view_personalinfomation_head, null);
        mPersonalInformationAdapter.setHeaderView(v);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mPresenter.showRemoteDriverInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personalinformation;
    }

    @Override
    public void initListener() {
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());
        guanjiUtils = new ProvincesCitysAreasSelectUtils();
        guanjiUtils.init(beans -> guanjiBeans = beans);
        cityUtils = new ProvincesCitysAreasSelectUtils();
        cityUtils.init(beans -> cityBeans = beans);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.clear();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void openCamera() {
        initSweetSheets(0, mDataBinding.flMain, null, R.menu.sheet_picture_random, (position, menuEntity) -> {
            switch (position) {
                case 0:
                    // 随机头像
                    showProgressDialog("正在更新头像");
                    randomAvatar();
                    break;
                case 1:
                    // 拍照
                    getLastDrawable();
                    ImagePickerUtils.startActivityForResult(getActivity(), PersonalInformationFragment.this, true,
                            AVATAR, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
                    break;
                case 2:
                    // 从相册中选择
                    getLastDrawable();
                    ImagePickerUtils.startActivityForResult(getActivity(), PersonalInformationFragment.this, true,
                            AVATAR, false, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
                    break;
            }
            return true;
        });
        showSweetSheets(0);
//        // SweetSheet 控件,根据 rl 确认位置
//        mSweetSheet3 = new SweetSheet(mDataBinding.flMain);
//        //定义一个 CustomDelegate 的 Delegate ,并且设置它的出现动画.
//        CustomDelegate customDelegate = new CustomDelegate(true,
//                CustomDelegate.AnimationType.DuangLayoutAnimation);
//        customDelegate.setContentHeight(ConvertUtils.dp2px(270));
//        View view = View.inflate(getContext(), R.layout.view_dialog, null);
//        view.findViewById(R.id.tvLook).setVisibility(View.GONE);
//        view.findViewById(R.id.tvRandom).setVisibility(View.VISIBLE);
//        ((TextView) view.findViewById(R.id.tvRandom)).setText("随机头像");
//
//        //设置自定义视图.
//        customDelegate.setCustomView(view);
//        //设置代理类
//        mSweetSheet3.setDelegate(customDelegate);
//        mSweetSheet3.setBackgroundEffect(new BlackEffect(8));
//
//        //因为使用了 CustomDelegate 所以mSweetSheet3中的 setMenuList和setOnMenuItemClickListener就没有效果了
//
//        // 随机选择
//        view.findViewById(R.id.tvRandom).setOnClickListener(v -> {
//            if (AppClickUtil.isDuplicateClick()) return;
//
//            showProgressDialog("正在更新头像");
//            hideSweetSheet();
//            randomAvatar();
//        });
//
//        // 取消
//        view.findViewById(R.id.tvCancel).setOnClickListener(v -> mSweetSheet3.dismiss());
//
//        // 拍照
//        view.findViewById(R.id.tvCamera).setOnClickListener(v -> {
//            showProgressDialog("正在更新头像");
//            hideSweetSheet();
//            getLastDrawable();
//
//            ImagePickerUtils.startActivityForResult(getActivity(), PersonalInformationFragment.this, false,
//                    AVATAR, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
//        });
//
//        // 文件选择
//        view.findViewById(R.id.tvAlbumSelect).setOnClickListener(v -> {
//            getLastDrawable();
//            hideSweetSheet();
//            ImagePickerUtils.startActivityForResult(getActivity(), PersonalInformationFragment.this, false,
//                    AVATAR, false, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
//        });
//        mSweetSheet3.show();
    }

    /**
     * 随机头像
     */
    private void randomAvatar() {
        // 设置随机图
        getLastDrawable();

        // 转成网址
        String[] web = new String[]{"http://img.huoyunji.com/app_photo_driverLicencePhoto01_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto02_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto03_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto04_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto05_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto06_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto07_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto08_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto09_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto10_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto11_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto12_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto13_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto14_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto15_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto16_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto17_iOS.jpg"
        };

        String background = "ic_sjtx_0";// 资源的固定名
        Driver driver = DataStatic.getInstance().getDriver();
        String photo = null; // driver的photo是个url
        String newAvatar = null;// 最终头像

        while (photo == null || driver.getPhoto().equals(photo)) {

            // 随即值
            int randomNum = 1 + (int) (Math.random() * web.length);

            // 赋值图片名称，如果超过9，就是两位数
            if (randomNum > 9) {
                newAvatar = background.substring(0, background.length() - 1) + randomNum;
            } else {
                newAvatar = background + randomNum;
            }

            // 判断是否重复
            photo = web[randomNum - 1];
        }

        // 预览
        int drawableId = getResources().getIdentifier(newAvatar, "drawable", AppUtils.getAppPackageName());
        driver.setPhoto(photo);

        // 提交服务器更新
        mDriverDataSource.update(driver)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        // 赋值新的对象
                        DataStatic.getInstance().setDriver(driver);

                        // 设置新的背景
                        setAvatar(drawableId);
                        setFragmentResult(FLAG_UPDATE_AVATAR, null);
                        hideProgressDialog();
                    }

                    @Override
                    public void onFail(BaseResponse<String> response) {
                        showToast("上传失败");
                        setAvatar(mLastDrawable);
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        setAvatar(mLastDrawable);
                        showToast("上传头像失败");
                        hideProgressDialog();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null || images.size() <= 0) {
                    return;
                }

                switch (requestCode) {
                    case AVATAR:
                        // 设置背景
                        String userBackgroundUrl = images.get(0).path;

                        uploadAvatar(userBackgroundUrl);
                        break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadAvatar(String userBackgroundUrl) {
        QiNiuManager.getInstance().upload(getActivity(),getContext(), userBackgroundUrl, true,new QiNiuManager.ComeBack() {
            @Override
            public void result(boolean result, String url) {
                if (getContext() == null) return;

                Driver driver = DataStatic.getInstance().getDriver();
                driver.setPhoto(url);
                mDriverDataSource.update(driver)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, PersonalInformationFragment.this) {
                            @Override
                            public void onSuccess(BaseResponse<String> response) {
                                DataStatic.getInstance().setDriver(driver);

                                // 设置新的背景
                                setAvatar(url);
                                setFragmentResult(FLAG_UPDATE_AVATAR, null);
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                setAvatar(mLastDrawable);
                            }

                            @Override
                            public void onFail(BaseResponse<String> response) {
                                super.onFail(response);
                                setAvatar(mLastDrawable);
                            }
                        });
            }

            @Override
            public void progress(String speed, int progress) {

            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (data != null && data.getString(VALUE) != null) {
            Driver driver = DataStatic.getInstance().getDriver();
            if (requestCode == FLAG_EMAIL) { // 电子邮件
                driver.setEmail(data.getString(VALUE));
            } else if (requestCode == FLAG_SIGN) {  // 签名
                driver.setSign(data.getString(VALUE));
            } else if (requestCode == FLAG_NICK_NAME) {  // 昵称
                driver.setNickName(data.getString(VALUE));
            } else if (requestCode == FLAG_QQ) {  // QQ
                driver.setQq(data.getString(VALUE));
            }
            DataStatic.getInstance().setDriver(driver);

            mPresenter.showLocalDriverInfo();
        } else if (requestCode == FLAG_HOMETOWN && data != null && data.getString(SELECT_LOCAL_RESULT) != null) { // 家乡
            String[] local = data.getString(SELECT_LOCAL_RESULT, "").split(",");
            if (local.length > 2) {
                mPresenter.modifyNativePlace(local[0], local[1], local[2]);
            }
        } else if (requestCode == FLAG_PERMANENT && data != null && data.getString(SELECT_LOCAL_RESULT) != null) { // 常住
            String[] local = data.getString(SELECT_LOCAL_RESULT, "").split(",");
            if (local.length > 1) {
                mPresenter.modifyCity(local[0], local[1]);
            }
        }
        if (requestCode == UPDATE) {
            mPresenter.showLocalDriverInfo();
        }
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Subscribe
    public void updatePersonalEvent(UpdatePersonalEvent event) {
        mPresenter.showRemoteDriverInfo();
    }

    @Override
    public void showDriverInfo(LoginDriver result) {
        mLoginDriver = result;
        mData.clear();

//        // banner基本信息
//        MyItem banner = new MyItem();
//        banner.setTitle("基本信息");
//        banner.setType(1);
//        mData.add(banner);
//
//        // 头像
//        MyItem avatar = new MyItem();
//        avatar.setTitle("头像");
//        avatar.setValue(ObjectUtils.parseString(driver.getPhoto()));
//        mData.add(avatar);

        // 背景图片
        MyItem background = new MyItem();
        background.setTitle("背景图片");
        background.setValue(ObjectUtils.parseString2(result.getUserInfo().getBackgroundPicture(), ""));
        mData.add(0, background);

        // 昵称
        MyItem nickname = new MyItem();
        nickname.setTitle("昵称");
        nickname.setValue(ObjectUtils.parseString2(result.getUserInfo().getNickName(), "未填写"));
        mData.add(1, nickname);

        // 更换手机号码
        MyItem mobile = new MyItem();
        mobile.setTitle("更换手机号码");
        mobile.setValue(ObjectUtils.parseString2(result.getUserInfo().getMobile(), "未填写"));
        mData.add(2, mobile);

        // 个人身份认 证
        MyItem idNumber = new MyItem();
        idNumber.setTitle("个人身份认证");
        idNumber.setValue(result.getUserInfo().getIdAuditStatusStr());
        mData.add(3, idNumber);

        // 驾驶技能认证
        MyItem drivingSkillCertification = new MyItem();
        drivingSkillCertification.setTitle("驾驶技能认证");
        drivingSkillCertification.setValue(result.getLicenceInfo().getAuditStatusStr());
        mData.add(4, drivingSkillCertification);

        // banner我的车辆
        MyItem myVehicle = new MyItem();
        myVehicle.setTitle("我的车辆");
        myVehicle.setType(1);
        mData.add(5, myVehicle);

        // 重型半挂牵引车
        MyItem heavyDutySemiTrailer = new MyItem();
        heavyDutySemiTrailer.setTitle("重型半挂牵引车");
        heavyDutySemiTrailer.setValue(result.getCarInfo().get(0).getAuditStatusStr());
        mData.add(6, heavyDutySemiTrailer);

        // 重型低平板半挂车
        MyItem bottomPlateSemiTrailer = new MyItem();
        bottomPlateSemiTrailer.setTitle("重型低平板半挂车");
        bottomPlateSemiTrailer.setValue(result.getCarInfo().get(1).getAuditStatusStr());
        mData.add(7, bottomPlateSemiTrailer);

        // banner其它信息
        MyItem otherInformation = new MyItem();
        otherInformation.setType(1);
        otherInformation.setTitle("其它信息");
        mData.add(8, otherInformation);

        // 电子邮箱
        MyItem email = new MyItem();
        email.setTitle("电子邮箱");
        email.setValue(ObjectUtils.parseString2(result.getUserInfo().getEmail(), "未填写"));
        mData.add(9, email);

        // QQ
        MyItem qq = new MyItem();
        qq.setTitle("QQ");
        qq.setValue(ObjectUtils.parseString2(result.getUserInfo().getQq(), "未填写"));
        mData.add(10, qq);

        // 常住
        MyItem permanent = new MyItem();
        permanent.setTitle("常住");
        String value = TextUtils.isEmpty(result.getUserInfo().getPlaceProvince()) ? "未选择" :
                ObjectUtils.parseString(result.getUserInfo().getPlaceProvince()) + " "
                        + ObjectUtils.parseString(result.getUserInfo().getPlaceCity());
        permanent.setValue(value);
        mData.add(11, permanent);

        // 家乡
        MyItem nativePlace = new MyItem();
        nativePlace.setTitle("家乡");
        String value2 = TextUtils.isEmpty(result.getUserInfo().getNativePlaceProvince()) ? "未选择" :
                ObjectUtils.parseString(result.getUserInfo().getNativePlaceProvince()) + " "
                        + ObjectUtils.parseString(result.getUserInfo().getNativePlaceCity()) + " "
                        + ObjectUtils.parseString(result.getUserInfo().getNativePlaceDistrict());
        nativePlace.setValue(value2);
        mData.add(12, nativePlace);

        // 个人简介
        MyItem dsc = new MyItem();
        dsc.setTitle("个人简介");
        dsc.setValue(ObjectUtils.parseString2(result.getUserInfo().getSign(), ""));
        mData.add(13, dsc);

        mPersonalInformationAdapter.getList().clear();
        mPersonalInformationAdapter.appendToList(mData);
        mPersonalInformationAdapter.notifyDataSetChanged();

        // 更新头像
        setAvatar(result.getUserInfo().getPhoto());

        hideProgressDialog();
    }

    @Override
    public void openRealNameAuthenticationFragment(LoginDriver result) {
        mLoginDriver = result;
        // 身份证认证状态(0:未认证 1:待认证 2:通过 3:不通过)
        switch (mLoginDriver.getUserInfo().getIdAuditStatus()) {
            case 0: // 未认证
                startForResult(RealNameAuthenticationFragment.newInstance(true), UPDATE);
                break;
            case 1: // 待认证
                startForResult(AuthenticationResultFragment.newInstance(mLoginDriver, FLAG_USER_INFO_AUDIT), UPDATE);
                break;
            case 2: // 通过
                startForResult(AuthenticationResultFragment.newInstance(mLoginDriver, FLAG_USER_INFO_PASS), UPDATE);
                break;
            case 3: // 不通过
                startForResult(AuthenticationResultFragment.newInstance(mLoginDriver, FLAG_USER_INFO_NO_PASS), UPDATE);
                break;
        }
    }

    @Override
    public void openDrivingLicenseAuthenticationFragment(LoginDriver result) {
        mLoginDriver = result;
        if (!checkPersonalCertification()) return;

        switch (mLoginDriver.getLicenceInfo().getAuditStatus()) {
            case 0: // 未认证
                startForResult(DrivingLicenseAuthenticationFragment.newInstance(mLoginDriver), UPDATE);
                break;
            case 1: // 待认证
                startForResult(AuthenticationResultFragment.newInstance(mLoginDriver, FLAG_DRIVER_AUDIT), UPDATE);
                break;
            case 2: // 通过
                startForResult(AuthenticationResultFragment.newInstance(mLoginDriver, FLAG_DRIVER_LICENCE_PASS), UPDATE);
                break;
            case 3: // 不通过
                startForResult(AuthenticationResultFragment.newInstance(mLoginDriver, FLAG_DRIVER_NO_PASS), UPDATE);
                break;
        }
    }

    @Override
    public void openHeavyVehicleAuthenticationFragment(LoginDriver result) {
        mLoginDriver = result;
        if (mLoginDriver.getCarInfo().isEmpty()) return;
        switch (mLoginDriver.getCarInfo().get(0).getAuditStatus()) {
            case "0": // 未认证
                startForResult(HeavyVehicleAuthenticationFragment.newInstance(mLoginDriver, MOTOR_TRACTOR), UPDATE);
                break;
            case "1": // 待认证
                startForResult(AuthenticationResultFragment.newInstance(mLoginDriver, FLAG_MOTOR_TRACTOR_AUDIT), UPDATE);
                break;
            case "2": // 通过
                startForResult(AuthenticationResultFragment.newInstance(mLoginDriver, FLAG_MOTOR_TRACTOR_PASS), UPDATE);
                break;
            case "3": // 不通过
                startForResult(AuthenticationResultFragment.newInstance(mLoginDriver, FLAG_MOTOR_TRACTOR_NO_PASS), UPDATE);
                break;
        }
    }

    @Override
    public void openHeavyVehicleAuthenticationFragment1(LoginDriver result) {
        mLoginDriver = result;
        if (mLoginDriver.getCarInfo().size() < 1) return;
        switch (mLoginDriver.getCarInfo().get(1).getAuditStatus()) {
            case "0": // 未认证
                startForResult(HeavyVehicleAuthenticationFragment.newInstance(mLoginDriver, SEMITRAILER), UPDATE);
                break;
            case "1": // 待认证
                startForResult(AuthenticationResultFragment.newInstance(mLoginDriver, FLAG_SEMITRAILER_AUDIT), UPDATE);
                break;
            case "2": // 通过
                startForResult(AuthenticationResultFragment.newInstance(mLoginDriver, FLAG_SEMITRAILER_PASS), UPDATE);
                break;
            case "3": // 不通过
                startForResult(AuthenticationResultFragment.newInstance(mLoginDriver, FLAG_SEMITRAILER_NO_PASS), UPDATE);
                break;
        }
    }

    @Override
    public void onItemClick(View itemView, Object item) {
        if (AppClickUtil.isDuplicateClick()) return;

        String value = ((TextView) itemView.findViewById(R.id.tvName)).getText().toString();
        switch (value) {
            case "头像":
                openCamera();
                break;
            case "背景图片":
                start(ChangeBackgroundFragment.newInstance(DataStatic.getInstance().getDriver().getBackgroundPicture()));
                break;
            case "更换手机号码":
                start(BindNewPhoneFragment.newInstance());
                break;
            case "昵称":
                String _value;
                MyItem myItem = mData.get(1);
                // 如果无昵称，或是"-"，传空过去
                if (myItem.getValue().startsWith("-") && myItem.getValue().length() <= 1) {
                    _value = "";
                } else {
                    _value = myItem.getValue();
                }
                startForResult(new ModifyNickNameFragment().newInstance(Constants.APP_DRIVER, _value), FLAG_NICK_NAME);
                break;
            case "家乡":
                startForResult(MyLocalSelectFragment.newInstance(true), FLAG_HOMETOWN);
//                guanjiUtils.initView(getContext(), (options1, options2, options3, v) -> {
//                    String provinces1 = guanjiBeans.getProvinces().get(options1).getPickerViewText();
//                    String city = guanjiBeans.getCitys().get(options1).get(options2);
//                    mPresenter.modifyNativePlace(provinces1, city);
//                });
//                guanjiUtils.showProvinceCity(guanjiBeans);
                break;
            case "常住":
                startForResult(MyLocalSelectFragment.newInstance(false), FLAG_PERMANENT);
//                cityUtils.initView(getContext(), (options1, options2, options3, v) -> {
//                    String provinces12 = cityBeans.getProvinces().get(options1).getPickerViewText();
//                    String city = cityBeans.getCitys().get(options1).get(options2);
//                    mPresenter.modifyCity(provinces12, city);
//                });
//                cityUtils.showProvinceCity(cityBeans);
                break;
            case "电子邮箱":
                myItem = mData.get(3);
                // 如果不是邮箱，传空过去
                if (!Pattern.matches(RegexValidator.REGEX_EMAIL, myItem.getValue())) {
                    _value = "";
                } else {
                    _value = myItem.getValue();
                }
                startForResult(new ModifyMailFragment().newInstance(Constants.APP_DRIVER, _value), FLAG_EMAIL);
                break;
            case "QQ":
                myItem = mData.get(10);
                // 如果不是邮箱，传空过去
                if (!Pattern.matches(RegexValidator.REGEX_EMAIL, myItem.getValue())) {
                    _value = "";
                } else {
                    _value = myItem.getValue();
                }
                startForResult(new ModifyQQFragment().newInstance(Constants.APP_DRIVER, _value), FLAG_QQ);
                break;
            case "个人简介":
                // 如果无备注，或是"-"，传空过去
                myItem = mData.get(13);
                if (myItem.getValue().startsWith("-") && myItem.getValue().length() <= 1) {
                    _value = "";
                } else {
                    _value = myItem.getValue();
                }
                startForResult(new ModifySignFragment().newInstance(Constants.APP_DRIVER, _value), FLAG_SIGN);
                break;
            case "个人身份认证":
                mPresenter.openDriverFragment(0);
                break;
            case "驾驶技能认证":
                // 认证状态(0:未认证 1:待认证 2:通过 3:不通过)
                mPresenter.openDriverFragment(1);
                break;
            case "重型半挂牵引车":
                // 认证状态(0:未认证 1:待认证 2:通过 3:不通过)
                mPresenter.openDriverFragment(2);
                break;
            case "重型低平板半挂车":
                // 认证状态(0:未认证 1:待认证 2:通过 3:不通过)
                mPresenter.openDriverFragment(3);
                break;

        }
    }

    public void getLastDrawable() {
        View childAt = mDataBinding.recyclerView.getChildAt(0);
        mLastDrawable = ((ImageView) childAt.findViewById(R.id.ivAvatar)).getDrawable();
    }

    /**
     * 赋值头像
     *
     * @param url
     */
    public void setAvatar(Object url) {
        if (getContext() == null) return;
        View childAt = mDataBinding.recyclerView.getChildAt(0);
        if (childAt == null || childAt.findViewById(R.id.ivAvatar) == null) return;

        if (url instanceof Integer) {
            // 如果是资源
            ((ImageView) childAt.findViewById(R.id.ivAvatar)).setImageResource((Integer) url);
        } else if (url instanceof String) {
            // 如果是url
            GlideManager.getInstance().withRounded(getContext(), (String) url, childAt.findViewById(R.id.ivAvatar), R.drawable.ic_grtouxiang);
        }
        ULog.i("setAvatar=" + url);
    }


    /**
     * 检测是否已经申请个人申请
     * 如何没有通过个人申请认证
     * 需要先个人认证
     */
    public boolean checkPersonalCertification() {
        if (getContext() == null) return false;


        if (DataStatic.getInstance().getDriver().getIdAuditStatus() == 2) {
            return true;
        }

        showToast("个人身份认证通过后，才能进行驾驶技能认证");
        return false;
    }
}
