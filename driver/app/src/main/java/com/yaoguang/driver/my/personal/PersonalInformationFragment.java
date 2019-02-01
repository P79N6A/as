package com.yaoguang.driver.my.personal;

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
import com.yaoguang.common.adapter.BaseRecyclerHeaderAndFooterAdapter;
import com.yaoguang.common.common.Constants;
import com.yaoguang.driver.widget.BlackEffect;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.common.Glide.impl.GlideManager;
import com.yaoguang.common.appcommon.dialog.DialogHelper;
import com.yaoguang.common.appcommon.utils.AppUtils;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.ConvertUtils;
import com.yaoguang.common.common.ObjectUtils;
import com.yaoguang.common.common.ProvincesCitysAreasSelectUtils;
import com.yaoguang.common.common.RegexValidator;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.common.common.imagepicker.ImagePickerUtils;
import com.yaoguang.common.entity.ProvinceBeans;
import com.yaoguang.common.qinui.QiNiuManager;
import com.yaoguang.driver.App;
import com.yaoguang.driver.R;
import com.yaoguang.driver.base.DataBindingFragment;
import com.yaoguang.driver.databinding.FragmentPersonalinformationBinding;
import com.yaoguang.driver.my.authentication.drivinglicense.DrivingLicenseAuthenticationFragment;
import com.yaoguang.driver.my.authentication.heavyvehicle.HeavyVehicleAuthenticationFragment;
import com.yaoguang.driver.my.authentication.personal.RealNameAuthenticationFragment;
import com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment;
import com.yaoguang.driver.my.bindphone.BindNewPhoneFragment;
import com.yaoguang.driver.my.changebackground.ChangeBackgroundFragment;
import com.yaoguang.driver.my.modify.email.ModifyMailFragment;
import com.yaoguang.driver.my.modify.nickname.ModifyNickNameFragment;
import com.yaoguang.driver.my.modify.qq.ModifyQQFragment;
import com.yaoguang.driver.my.modify.sign.ModifySignFragment;
import com.yaoguang.driver.my.my.MyFragment;
import com.yaoguang.driver.my.my.adater.PersonalInformationAndFooterAdapter;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.driver.widget.localselect.MyLocalSelectFragment;
import com.yaoguang.greendao.Injections;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.MyItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import static com.yaoguang.appcommon.common.finalrequest.DriverRequest.REQUESTCODE_PERSONAL_INFORMATION;
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
import static com.yaoguang.driver.my.my.MyFragment.FLAG_UPDATE_AVATAR;
import static com.yaoguang.driver.widget.localselect.MyLocalSelectFragment.SELECT_LOCAL_RESULT;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/02
 * 描    述：
 * 个人信息
 * =====================================
 */
public class PersonalInformationFragment extends DataBindingFragment<PersonalInformationPresenter, FragmentPersonalinformationBinding> implements PersonalInformationContacts.View, BaseRecyclerHeaderAndFooterAdapter.OnRecyclerViewItemClickListener {
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

    private SweetSheet mSweetSheet3;
    private Drawable mLastDrawable;
    private LoginResult mLoginResult;
    private DialogHelper mDialogHelper;

    private int mRandomNum = -1;

    public static PersonalInformationFragment newInstance() {
        return new PersonalInformationFragment();
    }


    @Override
    protected void initView(View view) {
        if (getActivity() == null || getContext() == null) return;
        mToolbarCommonBinding.toolbarTitle.setText("个人信息");

        mPersonalInformationAdapter = new PersonalInformationAndFooterAdapter();
        mDataBinding.recyclerView.setAdapter(mPersonalInformationAdapter);
        mDataBinding.recyclerView.setBackgroundResource(R.color.white);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mDataBinding.recyclerView.setHasFixedSize(true);
//        //添加分割线
//        mDataBinding.recyclerView.addItemDecoration(
//                new HorizontalDividerItemDecoration.Builder(getActivity())
//                        .showLastDivider()
//                        .color(ContextCompat.getColor(getActivity(), R.color.black_dividers_text))
//                        .sizeResId(R.dimen.divider)
//                        .build());
        mPersonalInformationAdapter.setOnItemClickListener(PersonalInformationFragment.this);
        View v = View.inflate(getContext(), R.layout.view_personalinfomation_head, null);
        mPersonalInformationAdapter.setHeaderView(v);
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        if (getContext() != null) {
            mPresenter.setData(Injection.provideDriverRepository(getContext()));
            mPresenter.subscribe();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personalinformation;
    }

    @Override
    protected void initListener() {
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());
        guanjiUtils = new ProvincesCitysAreasSelectUtils();
        guanjiUtils.init(beans -> guanjiBeans = beans);
        cityUtils = new ProvincesCitysAreasSelectUtils();
        cityUtils.init(beans -> cityBeans = beans);
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
        super.onDestroy();
    }

    public void openCamera() {
        // SweetSheet 控件,根据 rl 确认位置
        mSweetSheet3 = new SweetSheet(mDataBinding.flMain);
        //定义一个 CustomDelegate 的 Delegate ,并且设置它的出现动画.
        CustomDelegate customDelegate = new CustomDelegate(true,
                CustomDelegate.AnimationType.DuangLayoutAnimation);
        customDelegate.setContentHeight(ConvertUtils.dp2px(270));
        View view = View.inflate(getContext(), R.layout.view_dialog, null);
        view.findViewById(R.id.tvLook).setVisibility(View.GONE);
        view.findViewById(R.id.tvRandom).setVisibility(View.VISIBLE);
        ((TextView) view.findViewById(R.id.tvRandom)).setText("随机头像");

        //设置自定义视图.
        customDelegate.setCustomView(view);
        //设置代理类
        mSweetSheet3.setDelegate(customDelegate);
        mSweetSheet3.setBackgroundEffect(new BlackEffect(8));

        //因为使用了 CustomDelegate 所以mSweetSheet3中的 setMenuList和setOnMenuItemClickListener就没有效果了

        // 随机选择
        view.findViewById(R.id.tvRandom).setOnClickListener(v -> {
            if (AppClickUtil.isDuplicateClick()) return;

            showProgressDialog("正在更新头像");
            hideSweetSheet();
            randomAvatar();
        });

        // 取消
        view.findViewById(R.id.tvCancel).setOnClickListener(v -> mSweetSheet3.dismiss());

        // 拍照
        view.findViewById(R.id.tvCamera).setOnClickListener(v -> {
            showProgressDialog("正在更新头像");
            hideSweetSheet();
            getLastDrawable();

            ImagePickerUtils.startActivityForResult(getActivity(), PersonalInformationFragment.this, false,
                    AVATAR, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
        });

        // 文件选择
        view.findViewById(R.id.tvAlbumSelect).setOnClickListener(v -> {
            getLastDrawable();
            hideSweetSheet();
            ImagePickerUtils.startActivityForResult(getActivity(), PersonalInformationFragment.this, false,
                    AVATAR, false, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
        });
        mSweetSheet3.show();
    }

    private void randomAvatar() {
        if (getContext() == null) return;
        // 设置随机图
        getLastDrawable();
        String background = "ic_sjtx_0";
        int randomNum = new Random().nextInt(16) + 1;
        if (mRandomNum == randomNum) {
            randomAvatar();
            return;
        }
        mRandomNum = randomNum;

        String newAvatar;
        if (randomNum > 9) {
            newAvatar = background.substring(0, background.length() - 1) + randomNum;
        } else {
            newAvatar = background + randomNum;
        }
        ULog.i("new randomAvatar=" + newAvatar);
        // 预览
        int drawableId = getResources().getIdentifier(newAvatar, "drawable", AppUtils.getAppPackageName());
//        setAvatar(drawableId);

        // 转成网址
        String[] web = new String[]{"http://img.huoyunji.com/app_photo_driverLicencePhoto01_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto02_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto03_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto04_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto05_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto05_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto07_iOS.jpg",
                "http://img.huoyunji.com/app_photo_driverLicencePhoto07_iOS.jpg",
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

        LoginResult loginResult = Injection.provideDriverRepository(getContext()).getLoginResult();
        loginResult.getUserInfo().setPhoto(web[randomNum - 1]);
        Injection.provideDriverRepository(getContext()).update(loginResult.getUserInfo()).subscribe(value -> {
            if (value.getState().equals("200")) {
                Injection.provideDriverRepository(getContext()).saveOrUpdate(loginResult);

                // 设置新的背景
                setAvatar(drawableId);
                setFragmentResult(FLAG_UPDATE_AVATAR, null);
            } else {
                showToast("上传失败");
                setAvatar(mLastDrawable);
            }
            hideDialog();
        }, throwable -> {
            setAvatar(mLastDrawable);
            showToast("上传头像失败");
            hideDialog();
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
        QiNiuManager.getInstance().simpleUpload(getContext(), userBackgroundUrl, new QiNiuManager.ComeBack() {
            @Override
            public void result(boolean result, String url) {
                if (getContext() == null) return;

                LoginResult loginResult = Injection.provideDriverRepository(getContext()).getLoginResult();
                loginResult.getUserInfo().setPhoto(url);
                Injection.provideDriverRepository(getContext()).update(loginResult.getUserInfo()).subscribe(value -> {
                    if (value.getState().equals("200")) {
                        Injection.provideDriverRepository(getContext()).saveOrUpdate(loginResult);

                        // 设置新的背景
                        setAvatar(url);
                        setFragmentResult(FLAG_UPDATE_AVATAR, null);
                    } else {
                        showToast("上传失败");
                        setAvatar(mLastDrawable);
                    }
                    hideDialog();
                }, throwable -> {
                    setAvatar(mLastDrawable);
                    showToast("上传头像失败");
                    hideDialog();
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
            LoginResult loginResult = Injection.provideDriverRepository(getContext()).getLoginResult();
            if (requestCode == FLAG_EMAIL) { // 电子邮件
                loginResult.getUserInfo().setEmail(data.getString(VALUE));
            } else if (requestCode == FLAG_SIGN) {  // 签名
                loginResult.getUserInfo().setSign(data.getString(VALUE));
            } else if (requestCode == FLAG_NICK_NAME) {  // 昵称
                loginResult.getUserInfo().setNickName(data.getString(VALUE));
            } else if (requestCode == FLAG_QQ) {  // QQ
                loginResult.getUserInfo().setQq(data.getString(VALUE));
            }
            Injection.provideDriverRepository(getContext()).saveOrUpdate(loginResult);

            mPresenter.showLocalDriverInfo();
        }  else if (requestCode == FLAG_HOMETOWN && data != null && data.getString(SELECT_LOCAL_RESULT) != null) { // 家乡
            String[] local = data.getString(SELECT_LOCAL_RESULT, "").split(",");
            if (local.length > 2) {
                mPresenter.modifyNativePlace(local[0], local[1], local[2]);
            }
        }  else if (requestCode == FLAG_PERMANENT && data != null && data.getString(SELECT_LOCAL_RESULT) != null) { // 常住
            String[] local = data.getString(SELECT_LOCAL_RESULT, "").split(",");
            if (local.length > 1) {
                mPresenter.modifyCity(local[0], local[1]);
            }
        } if (requestCode == UPDATE) {
            mPresenter.subscribe();
        }
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public void showDriverInfo(LoginResult result) {
        mLoginResult = result;
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
        App.handler.postDelayed(() -> setAvatar(result.getUserInfo().getPhoto()), 200);

        hideDialog();
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
                LoginResult loginResult = Injection.provideDriverRepository(getContext()).getLoginResult();
                start(ChangeBackgroundFragment.newInstance(loginResult.getUserInfo().getBackgroundPicture()));
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
                // 身份证认证状态(0:未认证 1:待认证 2:通过 3:不通过)
//                mLoginResult.getUserInfo().setIdAuditStatus(3);
                switch (mLoginResult.getUserInfo().getIdAuditStatus()) {
                    case 0: // 未认证
                        startForResult(RealNameAuthenticationFragment.newInstance(), UPDATE);
                        break;
                    case 1: // 待认证
                        startForResult(AuthenticationResultFragment.newInstance(mLoginResult, FLAG_USER_INFO_AUDIT), UPDATE);
                        break;
                    case 2: // 通过
                        startForResult(AuthenticationResultFragment.newInstance(mLoginResult, FLAG_USER_INFO_PASS), UPDATE);
                        break;
                    case 3: // 不通过
                        startForResult(AuthenticationResultFragment.newInstance(mLoginResult, FLAG_USER_INFO_NO_PASS), UPDATE);
                        break;
                }
                break;
            case "驾驶技能认证":
                // 认证状态(0:未认证 1:待认证 2:通过 3:不通过)
//                mLoginResult.getLicenceInfo().setAuditStatus(3);
                if (!checkPersonalCertification()) return;

                switch (mLoginResult.getLicenceInfo().getAuditStatus()) {
                    case 0: // 未认证
                        startForResult(DrivingLicenseAuthenticationFragment.newInstance(mLoginResult), UPDATE);
                        break;
                    case 1: // 待认证
                        startForResult(AuthenticationResultFragment.newInstance(mLoginResult, FLAG_DRIVER_AUDIT), UPDATE);
                        break;
                    case 2: // 通过
                        startForResult(AuthenticationResultFragment.newInstance(mLoginResult, FLAG_DRIVER_LICENCE_PASS), UPDATE);
                        break;
                    case 3: // 不通过
                        startForResult(AuthenticationResultFragment.newInstance(mLoginResult, FLAG_DRIVER_NO_PASS), UPDATE);
                        break;
                }
                break;
            case "重型半挂牵引车":
                // 认证状态(0:未认证 1:待认证 2:通过 3:不通过)
                if (mLoginResult.getCarInfo().isEmpty()) return;
//                mLoginResult.getCarInfo().get(0).setAuditStatus("3");
                switch (mLoginResult.getCarInfo().get(0).getAuditStatus()) {
                    case "0": // 未认证
                        startForResult(HeavyVehicleAuthenticationFragment.newInstance(mLoginResult, MOTOR_TRACTOR), UPDATE);
                        break;
                    case "1": // 待认证
                        startForResult(AuthenticationResultFragment.newInstance(mLoginResult, FLAG_MOTOR_TRACTOR_AUDIT), UPDATE);
                        break;
                    case "2": // 通过
                        startForResult(AuthenticationResultFragment.newInstance(mLoginResult, FLAG_MOTOR_TRACTOR_PASS), UPDATE);
                        break;
                    case "3": // 不通过
                        startForResult(AuthenticationResultFragment.newInstance(mLoginResult, FLAG_MOTOR_TRACTOR_NO_PASS), UPDATE);
                        break;
                }
                break;
            case "重型低平板半挂车":
                // 认证状态(0:未认证 1:待认证 2:通过 3:不通过)
                if (mLoginResult.getCarInfo().size() < 1) return;
//                mLoginResult.getCarInfo().get(1).setAuditStatus("3");
                switch (mLoginResult.getCarInfo().get(1).getAuditStatus()) {
                    case "0": // 未认证
                        startForResult(HeavyVehicleAuthenticationFragment.newInstance(mLoginResult, SEMITRAILER), UPDATE);
                        break;
                    case "1": // 待认证
                        startForResult(AuthenticationResultFragment.newInstance(mLoginResult, FLAG_SEMITRAILER_AUDIT), UPDATE);
                        break;
                    case "2": // 通过
                        startForResult(AuthenticationResultFragment.newInstance(mLoginResult, FLAG_SEMITRAILER_PASS), UPDATE);
                        break;
                    case "3": // 不通过
                        startForResult(AuthenticationResultFragment.newInstance(mLoginResult, FLAG_SEMITRAILER_NO_PASS), UPDATE);
                        break;
                }
                break;

        }
    }

    private void hideSweetSheet() {
        if (mSweetSheet3 != null) {
            mSweetSheet3.dismiss();
        }
    }

    public void getLastDrawable() {
        View childAt = mDataBinding.recyclerView.getChildAt(0);
        mLastDrawable = ((ImageView) childAt.findViewById(R.id.ivAvatar)).getDrawable();
    }

    public void setAvatar(Object url) {
        if (getContext() == null) return;
        View childAt = mDataBinding.recyclerView.getChildAt(0);
        if (childAt == null || childAt.findViewById(R.id.ivAvatar) == null) return;

        if (url instanceof Integer) {
            ((ImageView) childAt.findViewById(R.id.ivAvatar)).setImageResource((Integer) url);
        } else if (url instanceof String) {
            GlideManager.getInstance().withRounded(getContext(), (String) url, childAt.findViewById(R.id.ivAvatar), R.drawable.ic_grtouxiang);
        }
        ULog.i("setAvatar="+ url);
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

        showToast("个人身份认证通过后，才能进行驾驶技能认证");
        return false;
    }
}
