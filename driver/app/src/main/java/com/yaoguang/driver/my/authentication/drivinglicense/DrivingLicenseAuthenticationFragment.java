package com.yaoguang.driver.my.authentication.drivinglicense;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.appcommon.common.base.LookPhotoActivity;
import com.yaoguang.common.Glide.impl.GlideManager;
import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.common.appcommon.dialog.CommonDialog;
import com.yaoguang.common.appcommon.dialog.DialogHelper;
import com.yaoguang.common.appcommon.widget.date.DatePickerFragment;
import com.yaoguang.common.common.ConvertUtils;
import com.yaoguang.common.common.ObjectUtils;
import com.yaoguang.common.common.SpanUtils;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.common.UiUtils;
import com.yaoguang.common.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.common.common.imagepicker.ImagePickerUtils;
import com.yaoguang.common.qinui.QiNiuManager;
import com.yaoguang.driver.R;
import com.yaoguang.driver.base.DataBindingFragment;
import com.yaoguang.driver.databinding.FragmentDrivinglicenseBinding;
import com.yaoguang.driver.my.authentication.drivinglicense.adapter.QuasiDrivingTypeAdapter;
import com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.LoginResult;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;

import java.util.ArrayList;
import java.util.Arrays;

import static com.yaoguang.appcommon.common.finalrequest.DriverRequest.REQUESTCODE_DRIVING_LICENSE;
import static com.yaoguang.driver.my.authentication.result.AuthenticationResultFragment.FLAG_DRIVER_AUDIT;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/07
 * 描    述：
 * 驾驶证认证认证
 * =====================================
 */

public class DrivingLicenseAuthenticationFragment extends DataBindingFragment<DrivingLicenseAuthenticationPresenter, FragmentDrivinglicenseBinding> implements DrivingLicenseAuthenticationContacts.View {
    private final static int FRONT = REQUESTCODE_DRIVING_LICENSE + 1;
    private final static int BACKGROUND = REQUESTCODE_DRIVING_LICENSE + 2;

    private final static String LOGIN_RESULT = "loginResult";
    private final static String DATE_SUFFIX = " 00:00:00";
    private final static String BEAN = "bean";

    private String mIdNumberFront;
    private String mIdNumberBack;

    private SweetSheet mSweetSheet3;

    private UserDriverLicence driverLicence = new UserDriverLicence();
    private QuasiDrivingTypeAdapter mQuasiDrivingTypeAdapter;
    private DialogHelper mDialogHelper;

    public static DrivingLicenseAuthenticationFragment newInstance(LoginResult loginResult) {
        Bundle args = new Bundle();
        DrivingLicenseAuthenticationFragment fragment = new DrivingLicenseAuthenticationFragment();
        args.putSerializable(LOGIN_RESULT, loginResult);
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
        mToolbarCommonBinding.toolbarTitle.setText("驾驶证信息认证");
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());

        mDataBinding.tvSave.setVisibility(View.GONE);
        mDataBinding.tvAlert.setVisibility(View.GONE);
        mDataBinding.tvSave.setText(new SpanUtils().append("先保存，下次再提交审核").setForegroundColor(UiUtils.getColor(R.color.orange500)).setUnderline().create());
        mDataBinding.tvAlert.setText(new SpanUtils().append("*").setForegroundColor(UiUtils.getColor(R.color.orange500)).append(" 若只先保存，身份证号码和姓名即可").create());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_drivinglicense;
    }

    @Override
    protected void initListener() {
        if (getArguments() == null || getArguments().getSerializable(LOGIN_RESULT) == null) return;
        if (getContext() == null) return;

        LoginResult loginResult = (LoginResult) getArguments().getSerializable(LOGIN_RESULT);
        if (loginResult == null) return;

        // 准驾车型
        mDataBinding.llQuasiDrivingType.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // SweetSheet 控件,根据 rl 确认位置
                mSweetSheet3 = new SweetSheet(mDataBinding.flMain);
                //定义一个 CustomDelegate 的 Delegate ,并且设置它的出现动画.
                CustomDelegate customDelegate = new CustomDelegate(true,
                        CustomDelegate.AnimationType.DuangLayoutAnimation);
                customDelegate.setContentHeight(ConvertUtils.dp2px(305));

                View view = View.inflate(getContext(), R.layout.view_quasi_driving_type, null);
                TextView tvOK = view.findViewById(R.id.tvOK);
                TextView tvClose = view.findViewById(R.id.tvClose);
                RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

                if (mQuasiDrivingTypeAdapter == null) {
                    mQuasiDrivingTypeAdapter = new QuasiDrivingTypeAdapter();
                    String[] data = new String[]{"A1", "A2", "A3", "B1", "B2", "C1", "C2", "C3", "C4", "D", "E", "F", "M", "N", "P"};

                    mQuasiDrivingTypeAdapter.getList().clear();
                    mQuasiDrivingTypeAdapter.appendToList(Arrays.asList(data));
                    mQuasiDrivingTypeAdapter.notifyDataSetChanged();
                }
                recyclerView.setAdapter(mQuasiDrivingTypeAdapter);
                recyclerView.setHasFixedSize(true);
                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
                recyclerView.setLayoutManager(layoutManager);


                mQuasiDrivingTypeAdapter.setOnItemClickListener(new BaseLoadMoreRecyclerAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, Object item, int position) {
                    }
                });

                //设置自定义视图.
                customDelegate.setCustomView(view);
                //设置代理类
                mSweetSheet3.setDelegate(customDelegate);

                // 确认选择
                tvOK.setOnClickListener(v1 -> {

                    String result = "";
                    Object[] array = mQuasiDrivingTypeAdapter.getqDTypeHashSet().toArray();
                    for (int i = 0; i < array.length; i++) {
                        Object o = array[i];
                        ULog.i("select value="+o.toString());
                        result += o.toString();
                        if (i != array.length - 1) {
                            result += " ";
                        }
                    }
                    mDataBinding.tvQuasiDrivingType.setText(result.equals("") ? "可多选" : result);
                    mSweetSheet3.dismiss();
                });
                tvClose.setOnClickListener(v1 -> mSweetSheet3.dismiss());

                mSweetSheet3.show();
            }
        });

        mDataBinding.ivNumberFront.setOnClickListener(v -> openCamera(FRONT));
        mDataBinding.ivNumberBack.setOnClickListener(v -> openCamera(BACKGROUND));
        mDataBinding.btnSubmit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(driverLicence.getLicencePhoto1())) {
                showToast("请选择行驶证正面 ");
                return;
            }
            if (TextUtils.isEmpty(driverLicence.getLicencePhoto2())) {
                showToast("请选择行驶证副页 ");
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.etIdNumber.getText().toString())) {
                showToast("请输入证号");
                return;
            }
            if (TextUtils.isEmpty(mDataBinding.etName.getText().toString())) {
                showToast("请输入姓名");
                return;
            }
            if (mDataBinding.tvToLicenseDate.getText().toString().equals("未选择")) {
                showToast("请选择初次领证日期");
                return;
            }
            if (mDataBinding.tvQuasiDrivingType.getText().toString().equals("可多选")) {
                showToast("请选择准架车型");
                return;
            }
            if (mDataBinding.tvStartDate.getText().toString().equals("起始日期")) {
                showToast("请选择证件起始日期 ");
                return;
            }
            if (mDataBinding.tvEndDate.getText().toString().equals("截止日期")) {
                showToast("请选择证件截止日期 ");
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

                    driverLicence.setId(loginResult.getLicenceInfo().getId());
                    driverLicence.setLicenceNumber(mDataBinding.etIdNumber.getText().toString());
                    driverLicence.setName(mDataBinding.etName.getText().toString());
                    driverLicence.setLicenseDate(mDataBinding.tvToLicenseDate.getText().toString() + DATE_SUFFIX);
                    driverLicence.setCarType(mDataBinding.tvQuasiDrivingType.getText().toString());
                    driverLicence.setStartDate(mDataBinding.tvStartDate.getText().toString() + DATE_SUFFIX);
                    driverLicence.setEndDate(mDataBinding.tvEndDate.getText().toString() + DATE_SUFFIX);
                    mPresenter.submit(driverLicence);

                    mDialogHelper.hideDialog();
                }

                @Override
                public void cancel() {

                }
            });


        });
        mDataBinding.tvSave.setOnClickListener(v -> {
        });

        mDataBinding.tvToLicenseDate.setOnClickListener(v -> {
            DatePickerFragment dateBeginPickerFragment = new DatePickerFragment();
            dateBeginPickerFragment.setComeBack((data, tag) -> mDataBinding.tvToLicenseDate.setText(data));
            dateBeginPickerFragment.show(getFragmentManager(), "");
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

            ImagePickerUtils.startActivityForResult(getActivity(), DrivingLicenseAuthenticationFragment.this, false,
                    result, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
        });

        // 文件选择
        view.findViewById(R.id.tvAlbumSelect).setOnClickListener(v -> {
            hideSweetSheet();
            ImagePickerUtils.startActivityForResult(getActivity(), DrivingLicenseAuthenticationFragment.this, false,
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
                if (getContext() == null) return;

                ImageView iv;
                if (requestCode == FRONT) {
                    iv = mDataBinding.ivNumberFront;
                    mIdNumberFront = url;
                    driverLicence.setLicencePhoto1(url);
                } else {
                    iv = mDataBinding.ivNumberBack;
                    mIdNumberBack = url;
                    driverLicence.setLicencePhoto2(url);
                }

                // 设置新的背景
                GlideManager.getInstance().with(getContext(),userBackgroundUrl,iv);
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
    public void refreshData(UserDriverLicence result) {
        driverLicence = result;

        mDataBinding.etIdNumber.setText(ObjectUtils.parseString2(result.getLicenceNumber(), ""));
        mDataBinding.etName.setText(ObjectUtils.parseString2(result.getName(), ""));
        mDataBinding.tvToLicenseDate.setText(ObjectUtils.parseString2(result.getLicenseDate(), ""));
        mDataBinding.tvQuasiDrivingType.setText(ObjectUtils.parseString2(result.getCarType(), ""));

        if (!TextUtils.isEmpty(result.getStartDate())) {
            mDataBinding.tvStartDate.setText(ObjectUtils.parseString2(result.getStartDate().replace(DATE_SUFFIX, ""), ""));
        }
        if (!TextUtils.isEmpty(result.getEndDate())) {
            mDataBinding.tvEndDate.setText(ObjectUtils.parseString2(result.getEndDate().replace(DATE_SUFFIX, ""), ""));
        }

        if (!TextUtils.isEmpty(result.getLicencePhoto1())) {
            GlideManager.getInstance()
                    .withSquare(getContext(),
                            result.getLicencePhoto1(), mDataBinding.ivNumberFront,
                            R.drawable.ic_ic_tpjzz_b,
                            R.drawable.ic_jzsb_01,
                            true);
        }

        if (!TextUtils.isEmpty(result.getLicencePhoto2())) {
            GlideManager.getInstance()
                    .withSquare(getContext(),
                            result.getLicencePhoto2(), mDataBinding.ivNumberBack,
                            R.drawable.ic_ic_tpjzz_b,
                            R.drawable.ic_jzsb_01,
                            true);
        }
    }

    @Override
    public void succeed(LoginResult loginResult) {
        pop();
        start(AuthenticationResultFragment.newInstance(loginResult, FLAG_DRIVER_AUDIT));
    }

    @Override
    public void onDestroyView() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        super.onDestroyView();
    }
}
