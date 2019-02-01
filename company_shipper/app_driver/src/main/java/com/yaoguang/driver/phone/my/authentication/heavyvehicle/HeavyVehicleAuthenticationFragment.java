package com.yaoguang.driver.phone.my.authentication.heavyvehicle;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.yaoguang.appcommon.common.base.LookPhotoActivity;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentHeavyVehicleBinding;
import com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment;
import com.yaoguang.driver.phone.my.personal.event.UpdatePersonalEvent;
import com.yaoguang.driver.phone.my.authentication.heavyvehicle.skeyboardview.EditView;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.greendao.entity.driver.UserDriverCar;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ConvertUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.SpanUtils;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.lib.common.imagepicker.ImagePickerUtils;
import com.yaoguang.lib.qinui.QiNiuManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.yaoguang.appcommon.common.finalrequest.DriverRequest.HEAVY_VEHICLE_AUTHENTICATION;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_MOTOR_TRACTOR_AUDIT;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_SEMITRAILER_AUDIT;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/07
 * 描    述：
 * 重型半挂牵引车认证
 * <p>
 * update zhongjh
 * data 2018-03-30
 * <p>
 * =====================================
 */

public class HeavyVehicleAuthenticationFragment extends BaseFragmentDataBind<FragmentHeavyVehicleBinding> implements HeavyVehicleAuthenticationContacts.View {

    HeavyVehicleAuthenticationContacts.Presenter mPresenter;

    private final static int FRONT = HEAVY_VEHICLE_AUTHENTICATION + 1;
    private final static int BACKGROUND = HEAVY_VEHICLE_AUTHENTICATION + 2;
    public final static int MOTOR_TRACTOR = 0;
    public final static int SEMITRAILER = 1;

    private final static String TYPE = "type";
    private final static String LOGIN_RESULT = "loginResult";
    private final static String DATE_SUFFIX = " 00:00:00";

    private DialogHelper mDialogHelper;

    private String mIdNumberFront;
    private String mIdNumberBack;

    private UserDriverCar driverCars = new UserDriverCar();
    //  车牌类型(0:牵引车 1:半挂车)
    private int mLicenceType;
    private int height = 0;
    private MyDispatchTouchEvent myDispatchTouchEvent;

    private int mPhotoType;// 拍照类型

    //触摸事件
    public interface MyDispatchTouchEvent {
        boolean dispatchTouchEvent(MotionEvent ev);
    }

    private ArrayList<MyDispatchTouchEvent> dispatchTouchEvents = new ArrayList<>();

    public void registerMyOnTouchListener(MyDispatchTouchEvent myDispatchTouchEvent) {
        dispatchTouchEvents.add(myDispatchTouchEvent);
    }

    public void unregisterMyOnTouchListener(MyDispatchTouchEvent myDispatchTouchEvent) {
        dispatchTouchEvents.remove(myDispatchTouchEvent);
    }

    public static HeavyVehicleAuthenticationFragment newInstance(LoginDriver loginDriver, int type) {
        Bundle args = new Bundle();
        HeavyVehicleAuthenticationFragment fragment = new HeavyVehicleAuthenticationFragment();
        args.putParcelable(LOGIN_RESULT, loginDriver);
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init() {
        mPresenter = new HeavyVehicleAuthenticationPresenter(this);

        if (getArguments() != null && getArguments().getParcelable(LOGIN_RESULT) != null) {
            LoginDriver loginDriver = getArguments().getParcelable(LOGIN_RESULT);
            mLicenceType = getArguments().getInt(TYPE);
            if (loginDriver != null) {
                driverCars = loginDriver.getCarInfo().get(mLicenceType);
            }
        }

        switch (mLicenceType) {
            case MOTOR_TRACTOR:
                mToolbarCommonBinding.toolbarTitle.setText("重型半挂牵引车认证");
                mDataBinding.tvQuasitractionTotalKey.setText("准牵引总量");

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, ConvertUtils.dp2px(16), 0);
                params.weight = 1;

                mDataBinding.etCarNumber.setLayoutParams(params);
                break;
            case SEMITRAILER:
                mToolbarCommonBinding.toolbarTitle.setText("重型低平板半挂车认证");
                mDataBinding.tvQuasitractionTotalKey.setText("核定载重总量");
                mDataBinding.tvCarNumberGua.setVisibility(View.VISIBLE);
                break;
        }
        mDataBinding.tvSave.setVisibility(View.GONE);
        mDataBinding.tvAlert.setVisibility(View.GONE);
        mDataBinding.tvSave.setText(new SpanUtils().append("先保存，下次再提交审核").setForegroundColor(UiUtils.getColor(R.color.orange500)).setUnderline().create());
        mDataBinding.tvAlert.setText(new SpanUtils().append("*").setForegroundColor(UiUtils.getColor(R.color.orange500)).append(" 若只先保存，身份证号码和姓名即可").create());

        // 创建选择菜单
        initSweetSheets(0, mDataBinding.flMain, null, R.menu.sheet_head_portrait_phone3, (position, menuEntity) -> {
            switch (position) {
                case 0:
                    // 拍照
                    ImagePickerUtils.startActivityForResult(getActivity(), HeavyVehicleAuthenticationFragment.this, false,
                            mPhotoType, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
                    hideSweetSheet();
                    break;
                case 1:
                    // 选择
                    ImagePickerUtils.startActivityForResult(getActivity(), HeavyVehicleAuthenticationFragment.this, false,
                            mPhotoType, false, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
                    hideSweetSheet();
                    break;
                case 2:
                    // 查看
                    String tag = mPhotoType == FRONT ? mIdNumberFront : mIdNumberBack;
                    if (tag == null || !tag.startsWith("http")) {
                        showToast("没有照片");
                    } else {
                        LookPhotoActivity.newInstance(getActivity(), tag);
                    }
                    hideSweetSheet();
                    break;
            }
            return false;
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_heavy_vehicle;
    }

    @Override
    public void initListener() {
//        mDataBinding.flMain.dispatchTouchEvent(new MotionEvent());
//        mDataBinding.flMain.setOnTouchListener((v, event) -> {
//            if (mDataBinding.etCarNumber.isShow())
//                mDataBinding.etCarNumber.hide(true);
//            return false;
//        });

        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());
        mDataBinding.ivNumberFront.setOnClickListener(v -> {
            mPhotoType = FRONT;
            showSweetSheets(0);
        });
        mDataBinding.ivNumberBack.setOnClickListener(v -> {
            mPhotoType = BACKGROUND;
            showSweetSheets(0);
        });

        // 自定义软键盘
        mDataBinding.etCarNumber.setEditView(mDataBinding.llKeyboard, mDataBinding.keyboardView, false);
        mDataBinding.etCarNumber.setOnKeyboardListener(new EditView.OnKeyboardListener() {

            @Override
            public void onHide(boolean isCompleted) {
                if (height > 0) {
                    mDataBinding.llCarNumber.scrollBy(0, -(height + ConvertUtils.dp2px(16)));
                }

                InputMethodUtil.hideKeyboard(_mActivity);

                if (isCompleted) {
                    Log.i("", "你点击了完成按钮");
                }
            }

            @Override
            public void onShow() {
                mDataBinding.llCarNumber.post(() -> {
                    //pos[0]: X，pos[1]: Y
                    int[] pos = new int[2];
                    //获取编辑框在整个屏幕中的坐标
                    mDataBinding.etCarNumber.getLocationOnScreen(pos);
                    //编辑框的Bottom坐标和键盘Top坐标的差
                    height = (pos[1] + mDataBinding.etCarNumber.getHeight())
                            - (DisplayMetricsUtils.getScreenHeight(getActivity()) - mDataBinding.keyboardView.getHeight());
                    if (height > 0) {
                        //编辑框和键盘之间预留出16dp的距离
                        mDataBinding.llCarNumber.scrollBy(0, height + ConvertUtils.dp2px(16));
                    }
                });


                //触摸事件
                myDispatchTouchEvent = ev -> {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）

                        // 输入框
                        View v = mDataBinding.etCarNumber;
                        int[] l = {0, 0};
                        v.getLocationInWindow(l);
                        int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                                + v.getWidth();
                        boolean isEditView = ev.getX() > left && ev.getX() < right
                                && ev.getY() > top && ev.getY() < bottom;

                        // 键盘
                        View keyboard = mDataBinding.keyboardView;
                        int[] key = {0, 0};
                        keyboard.getLocationInWindow(key);
                        int keyLeft = key[0], keyTop = key[1], keyBottom = keyTop + keyboard.getHeight(), keyRight = keyLeft
                                + keyboard.getWidth();
                        boolean keyIsInput = ev.getX() > keyLeft && ev.getX() < keyRight
                                && ev.getY() > keyTop && ev.getY() < keyBottom;

                        if (isEditView || keyIsInput) {
                            // 点击EditText的事件，忽略它。
                            return false;
                        } else {
                            mDataBinding.etCarNumber.hide(true);
                            return true;
                        }
                        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
                    }
                    return false;
                };

                registerMyOnTouchListener(myDispatchTouchEvent);
            }

            @Override
            public void onPress(int primaryCode) {

            }
        });

        // 自定义软键盘点击别的地方的时候，自动缩小
        mDataBinding.flMain.addView(mDataBinding.llCarNumber);
        mDataBinding.flMain.addView(mDataBinding.llKeyboard);
        mDataBinding.flMain.setTouchView(() -> {
            if (mDataBinding.etCarNumber.isShow()) {
                mDataBinding.etCarNumber.hide(true);
            }
        });

        mDataBinding.tvRegistrationDate.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.setComeBack((data, tag) -> mDataBinding.tvRegistrationDate.setText(data));
            dateBeginPickerFragment.show(getFragmentManager(), "");
        });

        mDataBinding.btnSubmit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(driverCars.getLicencePhoto1())) {
                showToast("请选择行驶证正页 ");
                return;
            }
            if (TextUtils.isEmpty(driverCars.getLicencePhoto2())) {
                showToast("请选择行驶证副页 ");
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.etCarNumber.getText().toString())) {
                showToast("请输入车牌号码");
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.etName.getText().toString())) {
                showToast("请输入所有人");
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.etBrandModel.getText().toString())) {
                showToast("请输入品牌型号");
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.tvRegistrationDate.getText().toString())) {
                showToast("请输入注册日期");
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.etCurbWeight.getText().toString())) {
                showToast("请输入整备质量");
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.etQuasitractionTotal.getText().toString())) {

                switch (mLicenceType) {
                    case MOTOR_TRACTOR:
                        showToast("请输入准牵引总量");
                        break;
                    case SEMITRAILER:
                        showToast("请输入核定载重总量");
                        break;
                }

                return;
            }
            if (TextUtils.isEmpty(mDataBinding.etVehicleLength.getText().toString())) {
                showToast("请输入车长");
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.etVehicleWidth.getText().toString())) {
                showToast("请输入车宽");
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.etVehicleHeight.getText().toString())) {
                showToast("请输入车高");
                return;
            }

            // 确认框
            if (mDialogHelper == null)
                mDialogHelper = new DialogHelper(getContext(), "提示", "请确保以上所填信息和身份证照片内容一致", "确定", "取消", new CommonDialog.Listener() {
                    @Override
                    public void ok(String content) {

                        driverCars.setLicenceType(String.valueOf(mLicenceType));
                        driverCars.setLicenceNumber(mDataBinding.etCarNumber.getText().toString() + "挂");
                        driverCars.setOwner(mDataBinding.etName.getText().toString());
                        driverCars.setBrandModel(mDataBinding.etBrandModel.getText().toString());
                        driverCars.setRegisterDate(mDataBinding.tvRegistrationDate.getText().toString() + DATE_SUFFIX);
                        driverCars.setCurbWeight(mDataBinding.etCurbWeight.getText().toString());
                        driverCars.setLoadOrTraction(mDataBinding.etQuasitractionTotal.getText().toString());
                        driverCars.setLength(mDataBinding.etVehicleLength.getText().toString());
                        driverCars.setWidth(mDataBinding.etVehicleWidth.getText().toString());
                        driverCars.setHeight(mDataBinding.etVehicleHeight.getText().toString());

                        mPresenter.submit(driverCars, mLicenceType);
                    }

                    @Override
                    public void cancel() {

                    }
                });
            mDialogHelper.show();

        });

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public boolean onBackPressedSupport() {
        if (mDataBinding.etCarNumber.isShow()) {
            mDataBinding.etCarNumber.hide(true);
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null || images.size() <= 0) {
                    return;
                }
                String url;
                switch (requestCode) {
                    case FRONT:
                        // 设置背景
                        url = images.get(0).path;
                        uploadQiNiu(url, FRONT);
                        break;
                    case BACKGROUND:
                        // 设置背景
                        url = images.get(0).path;
                        uploadQiNiu(url, BACKGROUND);
                        break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadQiNiu(String userBackgroundUrl, int requestCode) {
        QiNiuManager.getInstance().upload(getActivity(), getContext(), userBackgroundUrl, true, new QiNiuManager.ComeBack() {
            @Override
            public void result(boolean result, String url) {
                if (getContext() == null) return;

                ImageView iv;
                if (requestCode == FRONT) {
                    iv = mDataBinding.ivNumberFront;
                    driverCars.setLicencePhoto1(url);
                    mIdNumberFront = url;
                } else {
                    iv = mDataBinding.ivNumberBack;
                    mIdNumberBack = url;
                    driverCars.setLicencePhoto2(url);
                }

                // 设置新的背景

                GlideManager.getInstance().with(getContext(), url, iv);
            }

            @Override
            public void progress(String speed, int progress) {

            }
        });
    }

    private void hideSweetSheet() {
        if (mSweetSheets.get(0) != null) {
            mSweetSheets.get(0).dismiss();
        }
    }

    @Override
    public void refreshData(List<UserDriverCar> list) {
        driverCars = list.get(MOTOR_TRACTOR == mLicenceType ? 0 : 1);

        mDataBinding.etCarNumber.setText(ObjectUtils.parseString2(driverCars.getLicenceNumber(), ""));
        mDataBinding.etName.setText(ObjectUtils.parseString2(driverCars.getLicenceNumber(), ""));
        mDataBinding.etBrandModel.setText(ObjectUtils.parseString2(driverCars.getBrandModel(), ""));
        mDataBinding.tvRegistrationDate.setText(ObjectUtils.parseString2(driverCars.getRegisterDate(), ""));
        mDataBinding.etCurbWeight.setText(ObjectUtils.parseString2(driverCars.getCurbWeight(), ""));
        mDataBinding.etQuasitractionTotal.setText(ObjectUtils.parseString2(driverCars.getLoadOrTraction(), ""));
        mDataBinding.etVehicleLength.setText(ObjectUtils.parseString2(driverCars.getLength(), ""));
        mDataBinding.etVehicleWidth.setText(ObjectUtils.parseString2(driverCars.getWidth(), ""));
        mDataBinding.etVehicleHeight.setText(ObjectUtils.parseString2(driverCars.getHeight(), ""));

        if (!TextUtils.isEmpty(driverCars.getLicencePhoto1())) {
            GlideManager.getInstance()
                    .withSquare(getContext(),
                            driverCars.getLicencePhoto1(), mDataBinding.ivNumberFront,
                            R.drawable.ic_ic_tpjzz_b,
                            R.drawable.ic_jzsb_01,
                            true);
        }

        if (!TextUtils.isEmpty(driverCars.getLicencePhoto2())) {
            GlideManager.getInstance()
                    .withSquare(getContext(),
                            driverCars.getLicencePhoto2(), mDataBinding.ivNumberBack,
                            R.drawable.ic_ic_tpjzz_b,
                            R.drawable.ic_jzsb_01,
                            true);
        }
    }

    @Override
    public void succeed(LoginDriver loginDriver, int licenceType) {
        startWithPop(AuthenticationResultFragment.newInstance(loginDriver, licenceType == MOTOR_TRACTOR ? FLAG_MOTOR_TRACTOR_AUDIT : FLAG_SEMITRAILER_AUDIT));
        // 认证通过后，刷新
        EventBus.getDefault().post(new UpdatePersonalEvent());
    }

    @Override
    public void onDestroyView() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }

        unregisterMyOnTouchListener(myDispatchTouchEvent);

        super.onDestroyView();
    }
}
