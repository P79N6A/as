package com.yaoguang.driver.my.authentication.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

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
import com.yaoguang.common.common.imagepicker.ImagePickerUtils;
import com.yaoguang.common.qinui.QiNiuManager;
import com.yaoguang.driver.R;
import com.yaoguang.driver.base.DataBindingFragment;
import com.yaoguang.driver.databinding.FragmentRealnameBinding;
import com.yaoguang.driver.main.MainFragment;
import com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.Driver;

import java.util.ArrayList;

import static com.yaoguang.appcommon.common.finalrequest.DriverRequest.REQUESTCODE_DRIVING_LICENSE;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_USER_INFO_AUDIT;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_USER_INFO_NO_PASS;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_USER_INFO_PASS;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/07
 * 描    述：
 * 实名认证认证
 * =====================================
 */

public class RealNameAuthenticationFragment extends DataBindingFragment<RealNameAuthenticationPresenter, FragmentRealnameBinding> implements RealNameAuthenticationContacts.View {
    private final static int FRONT = REQUESTCODE_DRIVING_LICENSE + 1;
    private final static int BACKGROUND = REQUESTCODE_DRIVING_LICENSE + 2;

    private final static String DATE_SUFFIX = " 00:00:00";
    private final static String BEAN = "bean";

    private SweetSheet mSweetSheet3;
    private DialogHelper mDialogHelper;

    private Driver driver = new Driver();

    private String mIdNumberFront;
    private String mIdNumberBack;

    public static RealNameAuthenticationFragment newInstance() {
        Bundle args = new Bundle();
        RealNameAuthenticationFragment fragment = new RealNameAuthenticationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        if (getContext() == null) return;
        mPresenter.setData(Injection.provideDriverRepository(getContext()));
        mPresenter.getData();
    }

    @Override
    protected void initView(View view) {
        mToolbarCommonBinding.toolbarTitle.setText("个人身份认证");
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());

        mDataBinding.tvSave.setText(new SpanUtils().append("先保存，下次再提交审核").setForegroundColor(UiUtils.getColor(R.color.orange500)).setUnderline().create());
        mDataBinding.tvAlert.setText(new SpanUtils().append("*").setForegroundColor(UiUtils.getColor(R.color.orange500)).append(" 若只先保存，身份证号码和姓名即可").create());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_realname;
    }

    @Override
    protected void initListener() {
        if (getContext() == null) return;

        mDataBinding.ivNumberFront.setOnClickListener(v -> openCamera(FRONT));
        mDataBinding.ivNumberBack.setOnClickListener(v -> openCamera(BACKGROUND));
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
            if (mDialogHelper != null) {
                mDialogHelper.hideDialog();
            }

            mDialogHelper = new DialogHelper();
            mDialogHelper.showConfirmDialog(getContext(), "提示", "请确保以上所填信息和身份证照片内容一致", "确定", "取消", new CommonDialog.Listener() {
                @Override
                public void ok() {
                    driver.setIdNumber(mDataBinding.etIdNumber.getText().toString());
                    driver.setDriverName(mDataBinding.etName.getText().toString());
                    driver.setIdAuthority(mDataBinding.etIssuingAuthority.getText().toString());
                    driver.setIdStartDate(mDataBinding.tvStartDate.getText().toString() + DATE_SUFFIX);
                    driver.setIdEndDate(mDataBinding.tvEndDate.getText().toString() + DATE_SUFFIX);
                    mPresenter.submit(driver);

                    mDialogHelper.hideDialog();
                }

                @Override
                public void cancel() {

                }
            });
        });
        mDataBinding.tvSave.setOnClickListener(v -> {
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

            ImagePickerUtils.startActivityForResult(getActivity(), RealNameAuthenticationFragment.this, false,
                    result, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
        });

        // 文件选择
        view.findViewById(R.id.tvAlbumSelect).setOnClickListener(v -> {
            hideSweetSheet();
            ImagePickerUtils.startActivityForResult(getActivity(), RealNameAuthenticationFragment.this, false,
                    result, false, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
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
        if (mSweetSheet3 != null) {
            mSweetSheet3.dismiss();
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
    public void succeed(LoginResult loginResult) {
        pop();
        start(AuthenticationResultFragment.newInstance(loginResult, FLAG_USER_INFO_AUDIT));
    }

    @Override
    public void onDestroyView() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        super.onDestroyView();
    }
}
