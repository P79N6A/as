package com.yaoguang.driver.phone.my.authentication.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.yaoguang.appcommon.common.base.LookPhotoActivity;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentRealnameBinding;
import com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment;
import com.yaoguang.driver.phone.my.personal.event.UpdatePersonalEvent;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.lib.common.android.A2bigA;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.SpanUtils;
import com.yaoguang.lib.common.UiUtils;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.lib.common.imagepicker.ImagePickerUtils;
import com.yaoguang.lib.qinui.QiNiuManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static com.yaoguang.appcommon.common.finalrequest.DriverRequest.REQUESTCODE_DRIVING_LICENSE;
import static com.yaoguang.driver.phone.my.authentication.result.AuthenticationResultFragment.FLAG_USER_INFO_AUDIT;

/**
 * =====================================
 * 描    述：
 * 实名认证
 * Update zhongjh
 * Data 2018-03-15
 * =====================================
 */

public class RealNameAuthenticationFragment extends BaseFragmentDataBind<FragmentRealnameBinding> implements RealNameAuthenticationContacts.View {

    RealNameAuthenticationContacts.Presenter mPresenter;

    private final static int FRONT = REQUESTCODE_DRIVING_LICENSE + 1;
    private final static int BACKGROUND = REQUESTCODE_DRIVING_LICENSE + 2;

    private final static String DATE_SUFFIX = " 00:00:00";

    private DialogHelper mDialogHelper;

    private Driver driver = new Driver();

    private String mIdNumberFront;
    private String mIdNumberBack;

    private int mPhotoType;// 拍照类型

    /**
     * @param isload 是否加载
     * @return 返回本身
     */
    public static RealNameAuthenticationFragment newInstance(boolean isload) {
        Bundle args = new Bundle();
        args.putBoolean("isload", isload);
        RealNameAuthenticationFragment fragment = new RealNameAuthenticationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init() {

        mPresenter = new RealNameAuthenticationPresenter(this);

        mToolbarCommonBinding.toolbarTitle.setText("个人身份认证");
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());

        mDataBinding.tvSave.setText(new SpanUtils().append("先保存，下次再提交审核").setForegroundColor(UiUtils.getColor(R.color.orange500)).setUnderline().create());
        mDataBinding.tvAlert.setText(new SpanUtils().append("*").setForegroundColor(UiUtils.getColor(R.color.orange500)).append(" 若只先保存，身份证号码和姓名即可").create());

        // 获取是否需要加载
        if (getArguments() != null && getArguments().getBoolean("isload", false)) {
            mPresenter.getData();
        }

        // 创建选择菜单
        initSweetSheets(0, mDataBinding.flMain, null, R.menu.sheet_head_portrait_phone3, (position, menuEntity) -> {
            switch (position) {
                case 0:
                    // 拍照
                    ImagePickerUtils.startActivityForResult(getActivity(), RealNameAuthenticationFragment.this, false,
                            mPhotoType, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
                    hideSweetSheet();
                    break;
                case 1:
                    // 选择
                    ImagePickerUtils.startActivityForResult(getActivity(), RealNameAuthenticationFragment.this, false,
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

        // 大小写转换
        mDataBinding.etIdNumber.setTransformationMethod(new A2bigA());

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_realname;
    }


    @Override
    public void initListener() {
        if (getContext() == null) return;

        mDataBinding.ivNumberFront.setOnClickListener(v -> {
            mPhotoType = FRONT;
            showSweetSheets(0);
        });
        mDataBinding.ivNumberBack.setOnClickListener(v -> {
            mPhotoType = BACKGROUND;
            showSweetSheets(0);
        });
        mDataBinding.btnSubmit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(driver.getIdPhoto1())) {
                showToast("请选择身份证人像面 ");
                return;
            }

            if (TextUtils.isEmpty(driver.getIdPhoto2())) {
                showToast("请选择身份证国徽面 ");
                return;
            }

            if (mDataBinding.tvStartDate.getText().toString().equals("生效日期")) {
                showToast("请选择证件开始时间 ");
                return;
            }

            if (mDataBinding.tvEndDate.getText().toString().equals("失效日期")) {
                showToast("请选择证件失效时间 ");
                return;
            }

            if (TextUtils.isEmpty(mDataBinding.etIdNumber.getText().toString())) {
                showToast("请输入身份证号码");
                return;
            }

            if (TextUtils.isEmpty(mDataBinding.etName.getText().toString())) {
                showToast("请输入名字");
                return;
            }

            if (TextUtils.isEmpty(mDataBinding.etIssuingAuthority.getText().toString())) {
                showToast("请输入签发机关");
                return;
            }

            // 确认框
            if (mDialogHelper == null)
                mDialogHelper = new DialogHelper(getContext(), "提示", "请确保以上所填信息和身份证照片内容一致", "确定", "取消", new CommonDialog.Listener() {
                    @Override
                    public void ok(String content) {
                        driver.setIdNumber(mDataBinding.etIdNumber.getText().toString());
                        driver.setDriverName(mDataBinding.etName.getText().toString());
                        driver.setIdAuthority(mDataBinding.etIssuingAuthority.getText().toString());
                        driver.setIdStartDate(mDataBinding.tvStartDate.getText().toString() + DATE_SUFFIX);
                        driver.setIdEndDate(mDataBinding.tvEndDate.getText().toString() + DATE_SUFFIX);
                        mPresenter.submit(driver);
                    }

                    @Override
                    public void cancel() {

                    }
                });
            mDialogHelper.show();
        });
        mDataBinding.tvSave.setOnClickListener(v -> {

            if (TextUtils.isEmpty(mDataBinding.etIdNumber.getText().toString())) {
                showToast("请输入身份证号码");
                return;
            }

            if (TextUtils.isEmpty(mDataBinding.etName.getText().toString())) {
                showToast("请输入名字");
                return;
            }

            driver.setIdNumber(mDataBinding.etIdNumber.getText().toString());
            driver.setDriverName(mDataBinding.etName.getText().toString());
            driver.setIdAuthority(mDataBinding.etIssuingAuthority.getText().toString());

            if (!mDataBinding.tvStartDate.getText().toString().equals("生效日期"))
                driver.setIdStartDate(mDataBinding.tvStartDate.getText().toString() + DATE_SUFFIX);

            if (!mDataBinding.tvEndDate.getText().toString().equals("失效日期"))
                driver.setIdEndDate(mDataBinding.tvEndDate.getText().toString() + DATE_SUFFIX);

            mPresenter.save(driver);
        });

        mDataBinding.tvStartDate.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.setComeBack((data, tag) -> mDataBinding.tvStartDate.setText(data));
            dateBeginPickerFragment.show(getFragmentManager(), "");
        });
        mDataBinding.tvEndDate.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.setComeBack((data, tag) -> mDataBinding.tvEndDate.setText(data));
            dateBeginPickerFragment.show(getFragmentManager(), "");
        });
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
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

    @Override
    public void presenterPop() {
        pop();
    }

    private void uploadQiNiu(String userBackgroundUrl, int requestCode) {
        QiNiuManager.getInstance().upload(getActivity(), getContext(), userBackgroundUrl, true, new QiNiuManager.ComeBack() {
            @Override
            public void result(boolean result, String url) {
                if (getContext() == null) {
                    return;
                }

                ImageView iv;
                if (requestCode == FRONT) {
                    iv = mDataBinding.ivNumberFront;
                    mIdNumberFront = url;
                    driver.setIdPhoto1(url);
                } else {
                    iv = mDataBinding.ivNumberBack;
                    mIdNumberBack = url;
                    driver.setIdPhoto2(url);
                }

                // 设置新的背景
                GlideManager.getInstance()
                        .withSquare(getContext(),
                                url, iv,
                                R.drawable.ic_ic_tpjzz_b,
                                R.drawable.ic_jzsb_01,
                                true);
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
    public void refreshData(Driver result) {
        driver = result;

        mDataBinding.etIdNumber.setText(ObjectUtils.parseString2(result.getIdNumber(), ""));
        mDataBinding.etName.setText(ObjectUtils.parseString2(result.getDriverName(), ""));
        mDataBinding.etIssuingAuthority.setText(ObjectUtils.parseString2(result.getIdAuthority(), ""));

        if (!TextUtils.isEmpty(result.getIdStartDate())) {
            mDataBinding.tvStartDate.setText(ObjectUtils.parseString2(result.getIdStartDate().replace(DATE_SUFFIX, ""), ""));
        }
        if (!TextUtils.isEmpty(result.getIdEndDate())) {
            mDataBinding.tvEndDate.setText(ObjectUtils.parseString2(result.getIdEndDate().replace(DATE_SUFFIX, ""), ""));
        }

        if (!TextUtils.isEmpty(result.getIdPhoto1())) {
            GlideManager.getInstance()
                    .withSquare(getContext(),
                            result.getIdPhoto1(), mDataBinding.ivNumberFront,
                            R.drawable.ic_ic_tpjzz_b,
                            R.drawable.ic_jzsb_01,
                            true);
        }

        if (!TextUtils.isEmpty(result.getIdPhoto2())) {
            GlideManager.getInstance()
                    .withSquare(getContext(),
                            result.getIdPhoto2(), mDataBinding.ivNumberBack,
                            R.drawable.ic_ic_tpjzz_b,
                            R.drawable.ic_jzsb_01,
                            true);
        }
    }

    @Override
    public void succeed(LoginDriver loginDriver) {
        startWithPop(AuthenticationResultFragment.newInstance(loginDriver, FLAG_USER_INFO_AUDIT));
        // 认证通过后，刷新
        EventBus.getDefault().post(new UpdatePersonalEvent());
    }

    @Override
    public void onDestroyView() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        super.onDestroyView();
    }
}
