package com.yaoguang.driver.phone.my.authentication.result;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentAuthenticationResultBinding;
import com.yaoguang.driver.phone.my.authentication.drivinglicense.DrivingLicenseAuthenticationFragment;
import com.yaoguang.driver.phone.my.authentication.heavyvehicle.HeavyVehicleAuthenticationFragment;
import com.yaoguang.driver.phone.my.authentication.personal.RealNameAuthenticationFragment;
import com.yaoguang.driver.phone.my.authentication.result.adater.AuthenticationResultAdapter;
import com.yaoguang.greendao.entity.driver.LoginDriver;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.greendao.entity.driver.AuthenticationResultItem;
import com.yaoguang.greendao.entity.driver.UserDriverCar;
import com.yaoguang.greendao.entity.driver.UserDriverLicence;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.Gravity.LEFT;
import static com.yaoguang.appcommon.common.finalrequest.DriverRequest.AUTHENTICATION_RESULT_LICENSE;
import static com.yaoguang.driver.phone.my.authentication.heavyvehicle.HeavyVehicleAuthenticationFragment.MOTOR_TRACTOR;
import static com.yaoguang.driver.phone.my.authentication.heavyvehicle.HeavyVehicleAuthenticationFragment.SEMITRAILER;

/**
 * =====================================
 * 认证结果
 * update zhongjh
 * data 2018-03-27
 * =====================================
 */

public class AuthenticationResultFragment extends BaseFragmentDataBind<FragmentAuthenticationResultBinding> {

    // 身份证通过
    public final static int FLAG_USER_INFO_PASS = AUTHENTICATION_RESULT_LICENSE + 3;
    // 身份证审核中
    public final static int FLAG_USER_INFO_AUDIT = AUTHENTICATION_RESULT_LICENSE + 4;
    // 身份证不通过
    public final static int FLAG_USER_INFO_NO_PASS = AUTHENTICATION_RESULT_LICENSE + 5;

    // 驾驶证通过
    public final static int FLAG_DRIVER_LICENCE_PASS = AUTHENTICATION_RESULT_LICENSE + 6;
    // 驾驶证审核中
    public final static int FLAG_DRIVER_AUDIT = AUTHENTICATION_RESULT_LICENSE + 7;
    // 驾驶证不通过
    public final static int FLAG_DRIVER_NO_PASS = AUTHENTICATION_RESULT_LICENSE + 8;

    // 牵引车通过
    public final static int FLAG_MOTOR_TRACTOR_PASS = AUTHENTICATION_RESULT_LICENSE + 9;
    // 牵引车审核中
    public final static int FLAG_MOTOR_TRACTOR_AUDIT = AUTHENTICATION_RESULT_LICENSE + 10;
    // 牵引车不通过
    public final static int FLAG_MOTOR_TRACTOR_NO_PASS = AUTHENTICATION_RESULT_LICENSE + 11;

    // 半挂车通过
    public final static int FLAG_SEMITRAILER_PASS = AUTHENTICATION_RESULT_LICENSE + 12;
    // 半挂车审核中
    public final static int FLAG_SEMITRAILER_AUDIT = AUTHENTICATION_RESULT_LICENSE + 13;
    // 半挂车不通过
    public final static int FLAG_SEMITRAILER_NO_PASS = AUTHENTICATION_RESULT_LICENSE + 14;

    private final static String TYPE = "type";
    private final static String BEAN = "bean";

    private List<AuthenticationResultItem> mData = new ArrayList<>();
    private AuthenticationResultAdapter mAuthenticationResultAdapter;

    public static AuthenticationResultFragment newInstance(LoginDriver loginDriver, int type) {
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putParcelable(BEAN, loginDriver);
        AuthenticationResultFragment fragment = new AuthenticationResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_authentication_result;
    }

    @Override
    public void init() {

        mAuthenticationResultAdapter = new AuthenticationResultAdapter();
        RecyclerViewUtils.initPage(mDataBinding.recyclerView, mAuthenticationResultAdapter, null, getContext(), false);
        mDataBinding.recyclerView.setNestedScrollingEnabled(false);

        if (getArguments() == null)
            return;

        LoginDriver loginDriver = getArguments().getParcelable(BEAN);
        Driver userInfo;
        if (loginDriver == null || loginDriver.getUserInfo() == null) {
            return;
        }
        UserDriverLicence licenceInfo = loginDriver.getLicenceInfo();
        userInfo = loginDriver.getUserInfo();

        int type = getArguments().getInt(TYPE);


        switch (type) {
            case FLAG_USER_INFO_PASS:   // 身份认证通过
                mDataBinding.ivIcon.setImageResource(R.drawable.ic_jsz_yb);
                mDataBinding.tvTitle.setText("你的身份信息已经认证成功");
                mToolbarCommonBinding.toolbarTitle.setText("个人身份认证");
                // 设置列表
                mDataBinding.recyclerView.setVisibility(View.VISIBLE);
                mDataBinding.tvReturn.setOnClickListener(v -> pop());

                setAuthStatus(loginDriver.getUserInfo().getIdAuditStatus());

                // 设置数据
                AuthenticationResultItem nameItem = new AuthenticationResultItem();
                nameItem.setTitle("姓名");
                nameItem.setValue(userInfo.getDriverName());
                mData.add(nameItem);

                AuthenticationResultItem idNumberItem = new AuthenticationResultItem();
                idNumberItem.setTitle("证号");
                idNumberItem.setValue(userInfo.getIdNumber());
                mData.add(idNumberItem);

                mAuthenticationResultAdapter.clear();
                mAuthenticationResultAdapter.appendToList(mData);
                mAuthenticationResultAdapter.notifyDataSetChanged();
                break;

            case FLAG_USER_INFO_AUDIT:  //  身份认证审核中
                // 设置icon结果
                setAuthStatus(loginDriver.getUserInfo().getIdAuditStatus());

                mDataBinding.ivIcon.setImageResource(R.drawable.ic_jsz_yb);
                mToolbarCommonBinding.toolbarTitle.setText("个人身份认证");
                mDataBinding.tvTitle.setText("你的身份信息正在审核中");
                mDataBinding.dsc.setVisibility(View.VISIBLE);
                mDataBinding.dsc.setText("货云集将在你提交审核后的两个工作日内给予回复，请你耐心等待。");
                mDataBinding.tvReturn.setOnClickListener(v -> pop());
                break;

            case FLAG_USER_INFO_NO_PASS: // 身份证不通过
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = LEFT;
                params.setMargins(ConvertUtils.dp2px(30), ConvertUtils.dp2px(12), ConvertUtils.dp2px(30), 0);
                mDataBinding.dsc.setVisibility(View.VISIBLE);
                mDataBinding.dsc.setLayoutParams(params);

                mDataBinding.ivIcon.setImageResource(R.drawable.ic_jsz_yb);
                mToolbarCommonBinding.toolbarTitle.setText("个人身份认证");
                mDataBinding.tvTitle.setText("你的身份信息认证不通过");
                mDataBinding.dsc.setVisibility(View.VISIBLE);
//                mDataBinding.dsc.setText("原因：\n1.姓名与身份证号码不匹配；\n2.身份证国微面不清晰；");
                mDataBinding.dsc.setText(getRemark(loginDriver.getUserInfo().getRemark()));
                mDataBinding.tvReturn.setText("重新认证");
                mDataBinding.tvReturn.setOnClickListener((View v) -> {
//                    pop();
//                    start(RealNameAuthenticationFragment.newInstance());
                    startWithPop(RealNameAuthenticationFragment.newInstance(true));
                });

                setAuthStatus(loginDriver.getUserInfo().getIdAuditStatus());
                break;

            case FLAG_DRIVER_AUDIT:  //  驾驶证审核中
                mDataBinding.ivIcon.setImageResource(R.drawable.ic_jsz_yb);
                mDataBinding.tvReturn.setOnClickListener(v -> pop());
                mToolbarCommonBinding.toolbarTitle.setText("驾驶证信息认证");
                mDataBinding.tvTitle.setText("你的驾驶证信息正在审核中");
                mDataBinding.dsc.setVisibility(View.VISIBLE);
                mDataBinding.dsc.setText("货云集将在你提交审核后的两个工作日内给予回复，请你耐心等待。");

                setAuthStatus(loginDriver.getLicenceInfo().getAuditStatus());
                break;

            case FLAG_DRIVER_NO_PASS:  //  驾驶证不通过
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = LEFT;
                params.setMargins(ConvertUtils.dp2px(30), ConvertUtils.dp2px(12), ConvertUtils.dp2px(30), 0);
                mDataBinding.dsc.setVisibility(View.VISIBLE);
                mDataBinding.dsc.setLayoutParams(params);

                mDataBinding.ivIcon.setImageResource(R.drawable.ic_jsz_yb);
                mDataBinding.tvReturn.setText("重新认证");
                mDataBinding.tvReturn.setOnClickListener(v -> {
                    startWithPop(DrivingLicenseAuthenticationFragment.newInstance(loginDriver));
                });
                mToolbarCommonBinding.toolbarTitle.setText("驾驶证信息认证");
                mDataBinding.tvTitle.setText("你的驾驶证信息认证不通过");
//                mDataBinding.dsc.setText("原因：\n1.姓名与身份证号码不匹配；\n2.驾驶证副页不清晰；");
                mDataBinding.dsc.setText(getRemark(loginDriver.getLicenceInfo().getRemark()));

                setAuthStatus(loginDriver.getLicenceInfo().getAuditStatus());
                break;

            case FLAG_DRIVER_LICENCE_PASS:  //  驾驶证通过
                mDataBinding.ivIcon.setImageResource(R.drawable.ic_jsz_yb);
                mToolbarCommonBinding.toolbarTitle.setText("驾驶证信息认证");
                mDataBinding.tvTitle.setText("你的驾驶证信息已经认证成功");
                mDataBinding.recyclerView.setVisibility(View.VISIBLE);
                mDataBinding.tvReturn.setOnClickListener(v -> pop());

                setAuthStatus(loginDriver.getLicenceInfo().getAuditStatus());

                // 设置数据
                AuthenticationResultItem nameDriverLicItem = new AuthenticationResultItem();
                nameDriverLicItem.setTitle("姓名");
                nameDriverLicItem.setValue(licenceInfo.getName());
                mData.add(nameDriverLicItem);

                AuthenticationResultItem idNumberDriverLicItem = new AuthenticationResultItem();
                idNumberDriverLicItem.setTitle("证号");
                idNumberDriverLicItem.setValue(licenceInfo.getLicenceNumber());
                mData.add(idNumberDriverLicItem);

                AuthenticationResultItem quasiDriverLicItem = new AuthenticationResultItem();
                quasiDriverLicItem.setTitle("准架车型");
                quasiDriverLicItem.setValue(licenceInfo.getCarType());
                mData.add(quasiDriverLicItem);

                mAuthenticationResultAdapter.clear();
                mAuthenticationResultAdapter.appendToList(mData);
                mAuthenticationResultAdapter.notifyDataSetChanged();
                break;

            case FLAG_MOTOR_TRACTOR_AUDIT:  //  牵引车审核中
                heavyVehicleAudit(FLAG_MOTOR_TRACTOR_AUDIT, loginDriver);
                break;

            case FLAG_MOTOR_TRACTOR_NO_PASS:  //  牵引车不通过
                heavyVehicleNoPass(FLAG_MOTOR_TRACTOR_NO_PASS, loginDriver);
                break;

            case FLAG_MOTOR_TRACTOR_PASS:  //  牵引车通过
                heavyVehiclePass(FLAG_MOTOR_TRACTOR_PASS, loginDriver);
                break;

            case FLAG_SEMITRAILER_PASS:  //  半挂车通过
                heavyVehiclePass(FLAG_SEMITRAILER_PASS, loginDriver);
                break;

            case FLAG_SEMITRAILER_AUDIT:  //  半挂车审核中
                heavyVehicleAudit(FLAG_SEMITRAILER_AUDIT, loginDriver);
                break;

            case FLAG_SEMITRAILER_NO_PASS:  //  半挂车不通过
                heavyVehicleNoPass(FLAG_SEMITRAILER_NO_PASS, loginDriver);
                break;
        }
    }

    @Override
    public void initListener() {
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    private void heavyVehiclePass(int type, LoginDriver loginDriver) {
        UserDriverCar userDriverCar = loginDriver.getCarInfo().get(type == FLAG_MOTOR_TRACTOR_PASS ? 0 : 1);

        mDataBinding.ivIcon.setImageResource(R.drawable.ic_xsz_yb);
        mToolbarCommonBinding.toolbarTitle.setText(type == FLAG_MOTOR_TRACTOR_PASS ? "重型半挂牵引车认证" : "重型低平板半挂车认证");
        mDataBinding.tvTitle.setText("你的行驶证信息已经认证成功");
        mDataBinding.recyclerView.setVisibility(View.VISIBLE);
        mDataBinding.tvReturn.setOnClickListener(v -> pop());

        setAuthStatus(loginDriver.getLicenceInfo().getAuditStatus());

        // 设置数据
        AuthenticationResultItem licNumDriverCarItem = new AuthenticationResultItem();
        licNumDriverCarItem.setTitle("车牌号码");
        licNumDriverCarItem.setValue(userDriverCar.getLicenceNumber());
        mData.add(licNumDriverCarItem);

        AuthenticationResultItem nameDriverLicItem = new AuthenticationResultItem();
        nameDriverLicItem.setTitle("所有人");
        nameDriverLicItem.setValue(userDriverCar.getOwner());
        mData.add(nameDriverLicItem);

        AuthenticationResultItem quasiDriverLicItem = new AuthenticationResultItem();
        quasiDriverLicItem.setTitle("品牌型号");
        quasiDriverLicItem.setValue(userDriverCar.getBrandModel());
        mData.add(quasiDriverLicItem);

        AuthenticationResultItem curbWeightDriverLicItem = new AuthenticationResultItem();
        curbWeightDriverLicItem.setTitle("整备质量");
        curbWeightDriverLicItem.setValue(userDriverCar.getCurbWeight());
        mData.add(curbWeightDriverLicItem);

        AuthenticationResultItem loadTractionDriverLicItem = new AuthenticationResultItem();
        loadTractionDriverLicItem.setTitle(type == FLAG_MOTOR_TRACTOR_PASS ? "准牵引总量" : "核定载重总量");
        loadTractionDriverLicItem.setValue(userDriverCar.getLoadOrTraction());
        mData.add(loadTractionDriverLicItem);

        AuthenticationResultItem carLengthDriverLicItem = new AuthenticationResultItem();
        carLengthDriverLicItem.setTitle("车长");
        carLengthDriverLicItem.setValue(userDriverCar.getLength());
        mData.add(carLengthDriverLicItem);

        AuthenticationResultItem carWidthDriverLicItem = new AuthenticationResultItem();
        carWidthDriverLicItem.setTitle("车宽");
        carWidthDriverLicItem.setValue(userDriverCar.getWidth());
        mData.add(carWidthDriverLicItem);

        AuthenticationResultItem carHeightDriverLicItem = new AuthenticationResultItem();
        carHeightDriverLicItem.setTitle("车高");
        carHeightDriverLicItem.setValue(userDriverCar.getHeight());
        mData.add(carHeightDriverLicItem);

        mAuthenticationResultAdapter.clear();
        mAuthenticationResultAdapter.appendToList(mData);
        mAuthenticationResultAdapter.notifyDataSetChanged();


    }

    private void heavyVehicleNoPass(int type, LoginDriver loginDriver) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = LEFT;
        params.setMargins(ConvertUtils.dp2px(30), ConvertUtils.dp2px(12), ConvertUtils.dp2px(30), 0);
        mDataBinding.dsc.setVisibility(View.VISIBLE);
        mDataBinding.dsc.setLayoutParams(params);

        mDataBinding.ivIcon.setImageResource(R.drawable.ic_xsz_yb);
        mToolbarCommonBinding.toolbarTitle.setText(type == FLAG_MOTOR_TRACTOR_NO_PASS ? "重型半挂牵引车认证" : "重型低平板半挂车");
        mDataBinding.tvTitle.setText("你的行驶证信息认证不通过");
        mDataBinding.dsc.setVisibility(View.VISIBLE);
//        mDataBinding.dsc.setText("原因：\n1.姓名与身份证号码不匹配；\n2.身份证国微面不清晰；");
        mDataBinding.dsc.setText(getRemark(loginDriver.getCarInfo().get(type == FLAG_MOTOR_TRACTOR_NO_PASS ? 0 : 1).getRemark()));
        mDataBinding.tvReturn.setText("重新认证");
        mDataBinding.tvReturn.setOnClickListener(v -> {
            startWithPop(type == FLAG_MOTOR_TRACTOR_NO_PASS ?
                    HeavyVehicleAuthenticationFragment.newInstance(loginDriver, MOTOR_TRACTOR) :
                    HeavyVehicleAuthenticationFragment.newInstance(loginDriver, SEMITRAILER));
//            pop();
//            start(type == FLAG_MOTOR_TRACTOR_NO_PASS ?
//                    HeavyVehicleAuthenticationFragment.newInstance(loginDriver, MOTOR_TRACTOR) :
//                    HeavyVehicleAuthenticationFragment.newInstance(loginDriver, SEMITRAILER)
//            );
        });
        setAuthStatus(Integer.parseInt(loginDriver.getCarInfo().get(type == FLAG_MOTOR_TRACTOR_NO_PASS ? 0 : 1).getAuditStatus()));
    }

    @NonNull
    private String getRemark(String remark) {
        return "原因：\n\n" + remark;
    }

    private void heavyVehicleAudit(int type, LoginDriver loginDriver) {
        mDataBinding.ivIcon.setImageResource(R.drawable.ic_xsz_yb);
        mDataBinding.tvReturn.setOnClickListener(v -> pop());
        mToolbarCommonBinding.toolbarTitle.setText(type == FLAG_MOTOR_TRACTOR_AUDIT ? "重型半挂牵引车认证" : "重型低平板半挂车");
        mDataBinding.tvTitle.setText("你的行驶证信息正在审核中");
        mDataBinding.dsc.setVisibility(View.VISIBLE);
        mDataBinding.dsc.setText("货云集将在你提交审核后的两个工作日内给予回复，请你耐心等待。");

        setAuthStatus(Integer.parseInt(loginDriver.getCarInfo().get(type == FLAG_MOTOR_TRACTOR_AUDIT ? 0 : 1).getAuditStatus()));
    }

    /**
     * 认证状态(1:待认证 2:通过 3:不通过)
     */
    private void setAuthStatus(int auditStatus) {
        switch (auditStatus) {
            case 1:
                mDataBinding.ivResult.setImageResource(R.drawable.ic_js_shz);
                break;
            case 2:
                mDataBinding.ivResult.setImageResource(R.drawable.ic_js_yrz);
                break;
            case 3: //  3:不通过
                mDataBinding.ivResult.setImageResource(R.drawable.ic_js_btg);
                break;
        }
    }


}
