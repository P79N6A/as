package com.yaoguang.driver.my.authentication.heavyvehicle;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.appcommon.common.base.LookPhotoActivity;
import com.yaoguang.common.Glide.impl.GlideManager;
import com.yaoguang.common.appcommon.dialog.CommonDialog;
import com.yaoguang.common.appcommon.dialog.DialogHelper;
import com.yaoguang.common.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.common.common.ConvertUtils;
import com.yaoguang.common.common.ObjectUtils;
import com.yaoguang.common.common.SpanUtils;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.common.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.common.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.common.common.imagepicker.ImagePickerUtils;
import com.yaoguang.common.qinui.QiNiuManager;
import com.yaoguang.driver.R;
import com.yaoguang.driver.base.DataBindingFragment;
import com.yaoguang.driver.databinding.FragmentHeavyVehicleBinding;
import com.yaoguang.driver.main.MainActivity;
import com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.driver.widget.skeyboardview.EditView;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.driver.UserDriverCar;

import java.util.ArrayList;
import java.util.List;

import static com.yaoguang.appcommon.common.finalrequest.DriverRequest.HEAVY_VEHICLE_AUTHENTICATION;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_MOTOR_TRACTOR_AUDIT;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_SEMITRAILER_AUDIT;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_USER_INFO_AUDIT;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/07
 * 描    述：
 * 重型半挂索引车认证
 * =====================================
 */

public class HeavyVehicleAuthenticationFragment extends DataBindingFragment<HeavyVehicleAuthenticationPresenter, FragmentHeavyVehicleBinding> implements HeavyVehicleAuthenticationContacts.View {
    private final static int FRONT = HEAVY_VEHICLE_AUTHENTICATION + 1;
    private final static int BACKGROUND = HEAVY_VEHICLE_AUTHENTICATION + 2;
    public final static int MOTOR_TRACTOR = 0;
    public final static int SEMITRAILER = 1;

    private final static String TYPE = "type";
    private final static String LOGIN_RESULT = "loginResult";
    private final static String DATE_SUFFIX = " 00:00:00";

    private SweetSheet mSweetSheet3;
    private DialogHelper mDialogHelper;

    private String mIdNumberFront;
    private String mIdNumberBack;

    private UserDriverCar driverCars = new UserDriverCar();
    //  车牌类型(0:牵引车 1:半挂车)
    private int mLicenceType;
    private int height = 0;
    private MainActivity.MyDispatchTouchEvent myDispatchTouchEvent;

    public static HeavyVehicleAuthenticationFragment newInstance(LoginResult loginResult, int type) {
        Bundle args = new Bundle();
        HeavyVehicleAuthenticationFragment fragment = new HeavyVehicleAuthenticationFragment();
        args.putSerializable(LOGIN_RESULT, loginResult);
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        if (getContext() == null) return;
        mPresenter.setData(Injection.provideDriverRepository(getContext()));
    }

    @Override
    protected void initView(View view) {
        if (getArguments() == null || getArguments().getSerializable(LOGIN_RESULT) == null) return;

        LoginResult loginResult = (LoginResult) getArguments().getSerializable(LOGIN_RESULT);
        if (loginResult == null) return;
        mLicenceType = getArguments().getInt(TYPE);
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

        driverCars = loginResult.getCarInfo().get(mLicenceType);
        mDataBinding.tvSave.setVisibility(View.GONE);
        mDataBinding.tvAlert.setVisibility(View.GONE);
        mDataBinding.tvSave.setText(new SpanUtils().append("先保存，下次再提交审核").setForegroundColor(UiUtils.getColor(R.color.orange500)).setUnderline().create());
        mDataBinding.tvAlert.setText(new SpanUtils().append("*").setForegroundColor(UiUtils.getColor(R.color.orange500)).append(" 若只先保存，身份证号码和姓名即可").create());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_heavy_vehicle;
    }

    @Override
    protected void initListener() {
        if (getContext() == null) return;

        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());
        mDataBinding.ivNumberFront.setOnClickListener(v -> openCamera(FRONT));
        mDataBinding.ivNumberBack.setOnClickListener(v -> openCamera(BACKGROUND));

        mDataBinding.etCarNumber.setOnKeyboardListener(new EditView.OnKeyboardListener() {
            @Override
            public void onHide(boolean isCompleted) {
                if (height > 0) {
                    mDataBinding.llCarNumber.scrollBy(0, -(height + ConvertUtils.dp2px(16)));
                }

                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).unregisterMyOnTouchListener(myDispatchTouchEvent);

                if (isCompleted) {
                    Log.i("", "你点击了完成按钮");
                }
            }

            @Override
            public void onShow() {
                mDataBinding.llCarNumber.post(new Runnable() {
                    @Override
                    public void run() {
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
                    }
                });


                //触摸事件
                myDispatchTouchEvent = new MainActivity.MyDispatchTouchEvent() {
                    @Override
                    public boolean dispatchTouchEvent(MotionEvent ev) {
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
                    }
                };
                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).registerMyOnTouchListener(myDispatchTouchEvent);
            }

            @Override
            public void onPress(int primaryCode) {

            }
        });

//        mDataBinding.etCarNumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                SelectCarNoDialogFragment dialogFragmengt = new SelectCarNoDialogFragment();
////                dialogFragmengt.show(getFragmentManager(), "NavSetting");
//            }
//        });
        mDataBinding.etCarNumber.setEditView(mDataBinding.llKeyboard, mDataBinding.keyboardView, false);
//        mDataBinding.etCarNumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SelectCarNoDialogFragment dialogFragmengt = new SelectCarNoDialogFragment();
//                dialogFragmengt.show(getFragmentManager(), "NavSetting");
//            }
//        });

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
                showToast("请输入准牵引总量");
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
            if (mDialogHelper != null) {
                mDialogHelper.hideDialog();
            }

            mDialogHelper = new DialogHelper();
            mDialogHelper.showConfirmDialog(getContext(), "提示", "请确保以上所填信息和身份证照片内容一致", "确定", "取消", new CommonDialog.Listener() {
                @Override
                public void ok() {

                    driverCars.setLicenceType(String.valueOf(mLicenceType));
                    driverCars.setLicenceNumber(mDataBinding.etCarNumber.getText().toString());
                    driverCars.setOwner(mDataBinding.etName.getText().toString());
                    driverCars.setBrandModel(mDataBinding.etBrandModel.getText().toString());
                    driverCars.setRegisterDate(mDataBinding.tvRegistrationDate.getText().toString() + DATE_SUFFIX);
                    driverCars.setCurbWeight(mDataBinding.etCurbWeight.getText().toString());
                    driverCars.setLoadOrTraction(mDataBinding.etQuasitractionTotal.getText().toString());
                    driverCars.setLength(mDataBinding.etVehicleLength.getText().toString());
                    driverCars.setWidth(mDataBinding.etVehicleWidth.getText().toString());
                    driverCars.setHeight(mDataBinding.etVehicleHeight.getText().toString());

                    mPresenter.submit(driverCars, mLicenceType);

                    mDialogHelper.hideDialog();
                }

                @Override
                public void cancel() {

                }
            });

        });

    }

    public void openCamera(int result) {
        // SweetSheet 控件,根据 rl 确认位置
        mSweetSheet3 = new SweetSheet(mDataBinding.flMain);
        //定义一个 CustomDelegate 的 Delegate ,并且设置它的出现动画.
        CustomDelegate customDelegate = new CustomDelegate(true,
                CustomDelegate.AnimationType.DuangLayoutAnimation);
        customDelegate.setContentHeight(ConvertUtils.dp2px(270));
        View view = View.inflate(getContext(), R.layout.view_dialog, null);
        view.findViewById(R.id.tvRandom).setVisibility(View.GONE);

        //设置自定义视图.
        customDelegate.setCustomView(view);
        //设置代理类
        mSweetSheet3.setDelegate(customDelegate);


        //因为使用了 CustomDelegate 所以mSweetSheet3中的 setMenuList和setOnMenuItemClickListener就没有效果了

        // 取消
        view.findViewById(R.id.tvCancel).setOnClickListener(v -> mSweetSheet3.dismiss());

        // 拍照
        view.findViewById(R.id.tvCamera).setOnClickListener(v -> {
            showProgressDialog("正在更新头像");
            hideSweetSheet();

            ImagePickerUtils.startActivityForResult(getActivity(), HeavyVehicleAuthenticationFragment.this, false,
                    result, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
        });

        // 查看
        view.findViewById(R.id.tvLook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tag = result == FRONT ? mIdNumberFront : mIdNumberBack ;
                if (tag == null || !tag.startsWith("http")) {
                    showToast("没有照片");
                    mSweetSheet3.dismiss();
                    return;
                }
                LookPhotoActivity.newInstance(getActivity(), tag);
                mSweetSheet3.dismiss();
            }
        });

        // 文件选择
        view.findViewById(R.id.tvAlbumSelect).setOnClickListener(v -> {
            hideSweetSheet();
            ImagePickerUtils.startActivityForResult(getActivity(), HeavyVehicleAuthenticationFragment.this, false,
                    result, false, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
        });
        mSweetSheet3.show();
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
        QiNiuManager.getInstance().simpleUpload(getContext(), userBackgroundUrl, new QiNiuManager.ComeBack() {
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

                GlideManager.getInstance().with(getContext(), userBackgroundUrl, iv);
//                GlideManager.getInstance()
//                        .withSquare(getContext(),
//                                url, iv,
//                                R.drawable.ic_ic_tpjzz_b,
//                                R.drawable.ic_jzsb_01,
//                                true);
            }

            @Override
            public void progress(String speed, int progress) {

            }
        });
    }

    private void hideSweetSheet() {
        if (mSweetSheet3 != null) {
            mSweetSheet3.dismiss();
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
    public void succeed(LoginResult loginResult, int licenceType) {
        pop();
        start(AuthenticationResultFragment.newInstance(loginResult, licenceType == MOTOR_TRACTOR ? FLAG_MOTOR_TRACTOR_AUDIT : FLAG_SEMITRAILER_AUDIT));
    }

    @Override
    public void onDestroyView() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }

        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).unregisterMyOnTouchListener(myDispatchTouchEvent);
        super.onDestroyView();
    }
}
