package com.yaoguang.appcommon.phone.register2.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.dd.CircularProgressButton;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.BaseSubmitProgressActivity;
import com.yaoguang.appcommon.common.base.LookPhotoActivity;
import com.yaoguang.appcommon.common.finalrequest.CommpanyRequest;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseSubmitProgressView;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.appcommon.utils.TextViewUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.lib.common.imagepicker.ImagePickerUtils;
import com.yaoguang.lib.entity.ProvinceBeans;
import com.yaoguang.lib.qinui.QiNiuManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 注册详情界面
 * Created by zhongjh on 2017/11/29.
 */
public abstract class BaseRegisterDetailActivity<T> extends BaseSubmitProgressActivity implements RegisterDetailContact.View<T>, BaseSubmitProgressView {

    public final static String DATA = "data";// 传递过来的数据源
    final static String VERIFICATION_CODE = "verificationCode";// 传递过来的验证码
    final static int FLLICENSEPHOTO = 0;// 营业执照
    final static int FLIDPHOTO1 = 1;// 身份证正面
    final static int FLIDPHOTO2 = 2;// 身份证反面
    final static int FLIDPHOTO3 = 3;// 手持身份证

    public InitialView mInitialView;
    RegisterDetailContact.Presenter<T> mPresenter;
    protected T model;

    private DialogHelper mDialogHelper;
    private DialogHelper mDialogHelperComfit;
    int mPhotoType; // 拍照类型
    int mStep = 1; // 当前步骤

    OptionsPickerView pvOptions; // 城市选择view
    ProvinceBeans mProvinceBeans; // 城市数据源
    public String provinces;// 省
    public String citys; // 市
    public String areas;// 区

    public String mLicensePhoto = "";// LicensePhoto
    public String mStrIdPhoto1 = "";// 身份证1
    public String mStrIdPhoto2 = "";// 身份证2
    public String mStrIdPhoto3 = ""; // 手持身份证

    // 是否计时中
    boolean runningThree;
    // 更新ui的Handler
    MyHandler myHandler = new MyHandler(this);
    //倒计时5秒
    CountDownTimer downTimer = new CountDownTimer(5 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            Message msg = new Message();
            msg.what = 1;
            Bundle bundle = new Bundle();
            bundle.putLong("L", l);
            msg.setData(bundle);
            myHandler.sendMessage(msg);
        }

        @Override
        public void onFinish() {
            Message msg = new Message();
            msg.what = 0;
            Bundle bundle = new Bundle();
            msg.setData(bundle);
            myHandler.sendMessage(msg);
        }
    };

    /**
     * 客户类型
     */
    protected abstract String getCodeType();

    /**
     * 自定义ui
     *
     * @param initialView 改窗体的对象
     */
    protected abstract void customInitUI(InitialView initialView);

    /**
     * 初始化数据源
     *
     * @param t 实体
     */
    protected abstract void initData(T t);

    @Override
    public void initDataBind(int layoutResID) {

    }

    @Override
    protected void initView() {
        mPresenter = new RegisterDetailPresenter<>(this, getCodeType(), getBaseContext());
        mInitialView = new InitialView();
        model = getIntent().getParcelableExtra(DATA);
        initData(model);
        mPresenter.subscribe();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_detail;
    }

    @Override
    protected void initListener() {

    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == CommpanyRequest.REQUESTCODE_REGISTER + mPhotoType) {
                ArrayList<ImageItem> images = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String iconPath = images.get(0).path;
                switch (mPhotoType) {
                    case FLLICENSEPHOTO:
                        mInitialView.llLicensePhoto.setVisibility(View.GONE);
                        mInitialView.flLicensePhoto.setBackgroundResource(0);
                        mInitialView.imgLicensePhoto.setVisibility(View.VISIBLE);
                        mInitialView.imgLicensePhoto.setTag(R.id.tag_glide, iconPath);
                        GlideManager.getInstance().withNoneCache(getBaseContext(), iconPath, mInitialView.imgLicensePhoto);
                        break;
                    case FLIDPHOTO1:
                        mInitialView.llIdPhoto1.setVisibility(View.GONE);
                        mInitialView.flIdPhoto1.setBackgroundResource(0);
                        mInitialView.imgIdPhoto1.setVisibility(View.VISIBLE);
                        mInitialView.imgIdPhoto1.setTag(R.id.tag_glide, iconPath);
                        GlideManager.getInstance().withNoneCache(getBaseContext(), iconPath, mInitialView.imgIdPhoto1);
                        break;
                    case FLIDPHOTO2:
                        mInitialView.llIdPhoto2.setVisibility(View.GONE);
                        mInitialView.flIdPhoto2.setBackgroundResource(0);
                        mInitialView.imgIdPhoto2.setVisibility(View.VISIBLE);
                        mInitialView.imgIdPhoto2.setTag(R.id.tag_glide, iconPath);
                        GlideManager.getInstance().withNoneCache(getBaseContext(), iconPath, mInitialView.imgIdPhoto2);
                        break;
                    case FLIDPHOTO3:
                        mInitialView.llIdPhoto3.setVisibility(View.GONE);
                        mInitialView.flIdPhoto3.setBackgroundResource(0);
                        mInitialView.imgIdPhoto3.setVisibility(View.VISIBLE);
                        mInitialView.imgIdPhoto3.setTag(R.id.tag_glide, iconPath);
                        GlideManager.getInstance().withNoneCache(getBaseContext(), iconPath, mInitialView.imgIdPhoto3);
                        break;
                }
                wanTuUploadFile(iconPath);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        if (downTimer != null) {
            downTimer.cancel();
            downTimer = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressedSupport() {
        if (mInitialView.rlTipsLicense.getVisibility() == View.VISIBLE) {
            mInitialView.rlTipsLicense.setVisibility(View.GONE);
            return;
        }

        switch (mStep) {
            case 3:
                // 不能退
                break;
            case 2:
                setStep1();
                break;
            case 1:
                // 询问是否退出
                if (mDialogHelper == null)
                    mDialogHelper = new DialogHelper(BaseRegisterDetailActivity.this, "是否放弃认证", "未认证的账户将不能使用本系统服务", "是的", "我再想想", false, new CommonDialog.Listener() {
                        @Override
                        public void ok(String content) {
                            BaseRegisterDetailActivity.this.finish();
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                mDialogHelper.show();
                break;
        }
    }

    @Override
    public void setStep1() {
        mStep = 1;
        mInitialView.llStep1.setVisibility(View.VISIBLE);
        mInitialView.sllStep2.setVisibility(View.GONE);
        mInitialView.llStep3.setVisibility(View.GONE);
        // 显示返回键
        mInitialView.imgReturn.setVisibility(View.VISIBLE);

        // 顶部根据相应改变
        mInitialView.imgStep.setImageResource(R.drawable.ic_xz_lx01);
    }

    @Override
    public void setStep2() {
        // 隐藏自身，显示第二步
        mStep = 2;
        mInitialView.llStep1.setVisibility(View.GONE);
        mInitialView.sllStep2.setVisibility(View.VISIBLE);
        mInitialView.llStep3.setVisibility(View.GONE);
        // 显示返回键
        mInitialView.imgReturn.setVisibility(View.VISIBLE);

        // 顶部根据相应改变
        mInitialView.imgStep.setImageResource(R.drawable.ic_sc_zl02);
    }

    @Override
    public void setStep3() {
        // 第二步注册成功，跑到第三步
        mStep = 3;
        mInitialView.llStep1.setVisibility(View.GONE);
        mInitialView.sllStep2.setVisibility(View.GONE);
        mInitialView.llStep3.setVisibility(View.VISIBLE);
        //当可见时，就自动计时
        downTimer.start();
        // 并且隐藏返回键
        mInitialView.imgReturn.setVisibility(View.GONE);

        // 顶部根据相应改变
        mInitialView.imgStep.setImageResource(R.drawable.ic_tj_cg03);
    }


    @Override
    public void submitProgressSuccess(Bundle bundle) {
        setStep3();
    }

    @Override
    public void setProvinceBeans(ProvinceBeans value) {
        mProvinceBeans = value;
        pvOptions = new OptionsPickerView.Builder(BaseRegisterDetailActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = mProvinceBeans.getProvinces().get(options1).getPickerViewText() +
                        mProvinceBeans.getCitys().get(options1).get(options2) +
                        mProvinceBeans.getAreas().get(options1).get(options2).get(options3);
                provinces = mProvinceBeans.getProvinces().get(options1).getPickerViewText();
                citys = mProvinceBeans.getCitys().get(options1).get(options2);
                areas = mProvinceBeans.getAreas().get(options1).get(options2).get(options3);
                mInitialView.tvAddressValue.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(mProvinceBeans.getProvinces(), mProvinceBeans.getCitys(), mProvinceBeans.getAreas());//三级选择器
    }

    /**
     * 上传图片
     *
     * @param iconPath 图片地址
     */
    private void wanTuUploadFile(String iconPath) {
        QiNiuManager.getInstance().upload(BaseRegisterDetailActivity.this, BaseRegisterDetailActivity.this, iconPath, true, new QiNiuManager.ComeBack() {
            @Override
            public void result(boolean result, String url) {
                //更新
                switch (mPhotoType) {
                    case FLLICENSEPHOTO:
                        mLicensePhoto = url;
                        mInitialView.imgLicensePhoto.setTag(R.id.tag_glide, mLicensePhoto);
                        GlideManager.getInstance().withNoneCache(getBaseContext(), mLicensePhoto, mInitialView.imgLicensePhoto);
                        break;
                    case FLIDPHOTO1:
                        mStrIdPhoto1 = url;
                        mInitialView.imgIdPhoto1.setTag(R.id.tag_glide, mStrIdPhoto1);
                        GlideManager.getInstance().withNoneCache(getBaseContext(), mStrIdPhoto1, mInitialView.imgIdPhoto1);
                        break;
                    case FLIDPHOTO2:
                        mStrIdPhoto2 = url;
                        mInitialView.imgIdPhoto2.setTag(R.id.tag_glide, mStrIdPhoto2);
                        GlideManager.getInstance().withNoneCache(getBaseContext(), mStrIdPhoto2, mInitialView.imgIdPhoto2);
                        break;
                    case FLIDPHOTO3:
                        mStrIdPhoto3 = url;
                        mInitialView.imgIdPhoto3.setTag(R.id.tag_glide, mStrIdPhoto3);
                        GlideManager.getInstance().withNoneCache(getBaseContext(), mStrIdPhoto3, mInitialView.imgIdPhoto3);
                        break;
                }
                showToast("上传成功");
            }

            @Override
            public void progress(String speed, int progress) {

            }
        });
    }

    /**
     * 统一处理所有图片
     *
     * @param imageView 要呈现出来的图片
     */
    private void handlePhoto(final ImageView imageView) {
        // 设置剪辑模式
        boolean isWithCrop = false;
        // 宽度比例
        int minX = 1;
        // 高度比例
        int minY = 1;
        if (mPhotoType == FLLICENSEPHOTO) {
            mInitialView.imgTipsPhoto.setImageResource(R.drawable.ic_yyzz_fl);
            isWithCrop = false;
        } else if (mPhotoType == FLIDPHOTO1) {
            mInitialView.imgTipsPhoto.setImageResource(R.drawable.ic_sfz_fl01);
            isWithCrop = false;
        } else if (mPhotoType == FLIDPHOTO2) {
            mInitialView.imgTipsPhoto.setImageResource(R.drawable.ic_sfz_fl02);
            isWithCrop = false;
        } else if (mPhotoType == FLIDPHOTO3) {
            mInitialView.imgTipsPhoto.setImageResource(R.drawable.ic_sfz_fl03);
            isWithCrop = false;
        }
        final boolean finalIsWithCrop = isWithCrop;
        final int finalMinY = minY;
        final int finalMinX = minX;

        mInitialView.tvTake.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ImagePickerUtils.startActivityForResult(BaseRegisterDetailActivity.this, null, finalIsWithCrop, CommpanyRequest.REQUESTCODE_REGISTER + mPhotoType, true, DisplayMetricsSPUtils.getScreenWidth(getBaseContext()), finalMinY, finalMinX);
                mInitialView.rlTipsLicense.setVisibility(View.GONE);
            }
        });
        mInitialView.tvAlbum.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ImagePickerUtils.startActivityForResult(BaseRegisterDetailActivity.this, null, finalIsWithCrop, CommpanyRequest.REQUESTCODE_REGISTER + mPhotoType, false, DisplayMetricsSPUtils.getScreenWidth(getBaseContext()), finalMinY, finalMinX);
                mInitialView.rlTipsLicense.setVisibility(View.GONE);
            }
        });
        if (!TextUtils.isEmpty((String) imageView.getTag(R.id.tag_glide))) {
            mInitialView.tvLook.setOnClickListener(new NoDoubleClickListener() {

                @Override
                public void onNoDoubleClick(View v) {
                    LookPhotoActivity.newInstance(BaseRegisterDetailActivity.this, (String) imageView.getTag(R.id.tag_glide), true);
                    mInitialView.rlTipsLicense.setVisibility(View.GONE);
                }
            });
        } else {
            mInitialView.tvLook.setVisibility(View.GONE);
        }
        mInitialView.tvCancel.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                mInitialView.rlTipsLicense.setVisibility(View.GONE);
            }
        });

        InputMethodUtil.hideKeyboard(BaseRegisterDetailActivity.this);
        mInitialView.rlTipsLicense.setVisibility(View.VISIBLE);
    }

    static class MyHandler extends Handler {
        WeakReference<BaseRegisterDetailActivity> mBaseRegisterDetailActivity;

        MyHandler(BaseRegisterDetailActivity baseRegisterDetailActivity) {
            mBaseRegisterDetailActivity = new WeakReference<>(baseRegisterDetailActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseRegisterDetailActivity baseRegisterDetailActivity = mBaseRegisterDetailActivity.get();
            Long l = msg.getData().getLong("L");
            if (msg.what == 1) {
                baseRegisterDetailActivity.runningThree = true;
                baseRegisterDetailActivity.mInitialView.btnRegister.setText("回到登录页" + ((l / 1000) + "秒"));
            } else {
                baseRegisterDetailActivity.mInitialView.btnRegister.setText("回到登录页");
                baseRegisterDetailActivity.runningThree = false;
                baseRegisterDetailActivity.finish();
            }

        }
    }

    public class InitialView {

        public ImageView imgReturn;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public ImageView imgStep;
        public TextView tvStep1;
        public TextView tvCompanyForwarder;
        public TextView tvCompanyTrailer;
        public LinearLayout llCompany;
        public CheckBox cbShipperCompany;
        public CheckBox cbShipperUser;
        public LinearLayout llShipper;
        public CircularProgressButton cpbNext;
        public LinearLayout llStep1;
        public TextView tvStep2;
        public LinearLayout llLicensePhoto;
        public ImageView imgLicensePhoto;
        public FrameLayout flLicensePhoto;
        public LinearLayout llPhotoCompany;
        public LinearLayout llIdPhoto1;
        public ImageView imgIdPhoto1;
        public FrameLayout flIdPhoto1;
        public LinearLayout llIdPhoto2;
        public ImageView imgIdPhoto2;
        public FrameLayout flIdPhoto2;
        public LinearLayout llIdPhoto3;
        public ImageView imgIdPhoto3;
        public FrameLayout flIdPhoto3;
        public LinearLayout llPhotoShipper;
        public TextView tvCompanyCodeTitle;
        public EditText etCompanyCodeValue;
        public RelativeLayout rlCompanyCode;
        public TextView tvCompanyNameTitle;
        public EditText etValueDepartureTitle;
        public RelativeLayout rlCompanyName;
        public TextView tvAddressTitle;
        public TextView tvAddressValue;
        public RelativeLayout rlAddress;
        public TextView tvCompanyAddressTitle;
        public EditText etValueCompanyAddress;
        public RelativeLayout rlCompanyAddress;
        public LinearLayout llInformationCompany;
        public TextView tvShipperNameTitle;
        public EditText etShipperNameValue;
        public RelativeLayout rlShipperName;
        public TextView tvCodeTitle;
        public EditText etCodeValue;
        public RelativeLayout rlCode;
        public LinearLayout llInformationShipper;
        public TextView tvTitleName;
        public EditText etNameValue;
        public RelativeLayout rlName;
        public TextView tvTitleMobile;
        public EditText etValueMobile;
        public RelativeLayout rlMobile;
        public CircularProgressButton cpbRegister;
        public ScrollView sllStep2;
        public TextView title;
        public TextView context;
        public TextView btnRegister;
        public LinearLayout llStep3;
        public FrameLayout flMain;
        public RelativeLayout rlTipsLicense;
        public TextView tvLook;
        public TextView tvTake;
        public TextView tvAlbum;
        public TextView tvCancel;
        public ImageView imgTipsPhoto;

        public InitialView() {
            initUI();
            initListener();
        }

        private void initUI() {
            this.imgReturn = (ImageView) findViewById(R.id.imgReturn);
            this.toolbar_title = (TextView) findViewById(R.id.toolbar_title);
            this.toolbar = (Toolbar) findViewById(R.id.toolbar);
            this.imgStep = (ImageView) findViewById(R.id.imgStep);
            this.tvStep1 = (TextView) findViewById(R.id.tvStep1);
            this.tvCompanyForwarder = (TextView) findViewById(R.id.tvCompanyForwarder);
            this.tvCompanyTrailer = (TextView) findViewById(R.id.tvCompanyTrailer);
            this.llCompany = (LinearLayout) findViewById(R.id.llCompany);
            this.cbShipperCompany = (CheckBox) findViewById(R.id.cbShipperCompany);
            this.cbShipperUser = (CheckBox) findViewById(R.id.cbShipperUser);
            this.llShipper = (LinearLayout) findViewById(R.id.llShipper);
            this.cpbNext = (CircularProgressButton) findViewById(R.id.cpbNext);
            this.llStep1 = (LinearLayout) findViewById(R.id.llStep1);
            this.tvStep2 = (TextView) findViewById(R.id.tvStep2);
            this.llLicensePhoto = (LinearLayout) findViewById(R.id.llLicensePhoto);
            this.imgLicensePhoto = (ImageView) findViewById(R.id.imgLicensePhoto);
            this.flLicensePhoto = (FrameLayout) findViewById(R.id.flLicensePhoto);
            this.llPhotoCompany = (LinearLayout) findViewById(R.id.llPhotoCompany);
            this.llIdPhoto1 = (LinearLayout) findViewById(R.id.llIdPhoto1);
            this.imgIdPhoto1 = (ImageView) findViewById(R.id.imgIdPhoto1);
            this.flIdPhoto1 = (FrameLayout) findViewById(R.id.flIdPhoto1);
            this.llIdPhoto2 = (LinearLayout) findViewById(R.id.llIdPhoto2);
            this.imgIdPhoto2 = (ImageView) findViewById(R.id.imgIdPhoto2);
            this.flIdPhoto2 = (FrameLayout) findViewById(R.id.flIdPhoto2);
            this.llIdPhoto3 = (LinearLayout) findViewById(R.id.llIdPhoto3);
            this.imgIdPhoto3 = (ImageView) findViewById(R.id.imgIdPhoto3);
            this.flIdPhoto3 = (FrameLayout) findViewById(R.id.flIdPhoto3);
            this.llPhotoShipper = (LinearLayout) findViewById(R.id.llPhotoShipper);
            this.tvCompanyCodeTitle = (TextView) findViewById(R.id.tvCompanyCodeTitle);
            this.etCompanyCodeValue = (EditText) findViewById(R.id.etCompanyCodeValue);
            this.rlCompanyCode = (RelativeLayout) findViewById(R.id.rlCompanyCode);
            this.tvCompanyNameTitle = (TextView) findViewById(R.id.tvCompanyNameTitle);
            this.etValueDepartureTitle = (EditText) findViewById(R.id.etValueDepartureTitle);
            this.rlCompanyName = (RelativeLayout) findViewById(R.id.rlCompanyName);
            this.tvAddressTitle = (TextView) findViewById(R.id.tvAddressTitle);
            this.tvAddressValue = (TextView) findViewById(R.id.tvAddressValue);
            this.rlAddress = (RelativeLayout) findViewById(R.id.rlAddress);
            this.tvCompanyAddressTitle = (TextView) findViewById(R.id.tvCompanyAddressTitle);
            this.etValueCompanyAddress = (EditText) findViewById(R.id.etValueCompanyAddress);
            this.rlCompanyAddress = (RelativeLayout) findViewById(R.id.rlCompanyAddress);
            this.llInformationCompany = (LinearLayout) findViewById(R.id.llInformationCompany);
            this.tvShipperNameTitle = (TextView) findViewById(R.id.tvShipperNameTitle);
            this.etShipperNameValue = (EditText) findViewById(R.id.etShipperNameValue);
            this.rlShipperName = (RelativeLayout) findViewById(R.id.rlShipperName);
            this.tvCodeTitle = (TextView) findViewById(R.id.tvCodeTitle);
            this.etCodeValue = (EditText) findViewById(R.id.etCodeValue);
            this.rlCode = (RelativeLayout) findViewById(R.id.rlCode);
            this.llInformationShipper = (LinearLayout) findViewById(R.id.llInformationShipper);
            this.tvTitleName = (TextView) findViewById(R.id.tvTitleName);
            this.etNameValue = (EditText) findViewById(R.id.etNameValue);
            this.rlName = (RelativeLayout) findViewById(R.id.rlName);
            this.tvTitleMobile = (TextView) findViewById(R.id.tvTitleMobile);
            this.etValueMobile = (EditText) findViewById(R.id.etValueMobile);
            this.rlMobile = (RelativeLayout) findViewById(R.id.rlMobile);
            this.cpbRegister = (CircularProgressButton) findViewById(R.id.cpbRegister);
            this.sllStep2 = (ScrollView) findViewById(R.id.sllStep2);
            this.title = (TextView) findViewById(R.id.title);
            this.context = (TextView) findViewById(R.id.context);
            this.btnRegister = (TextView) findViewById(R.id.btnRegister);
            this.llStep3 = (LinearLayout) findViewById(R.id.llStep3);
            this.flMain = (FrameLayout) findViewById(R.id.flMain);
            this.rlTipsLicense = (RelativeLayout) findViewById(R.id.rlTipsLicense);
            this.tvLook = (TextView) findViewById(R.id.tvLook);
            this.tvTake = (TextView) findViewById(R.id.tvTake);
            this.tvAlbum = (TextView) findViewById(R.id.tvAlbum);
            this.tvCancel = (TextView) findViewById(R.id.tvCancel);
            this.imgTipsPhoto = (ImageView) findViewById(R.id.imgTipsPhoto);

            // 初始化
            setCpbSubmit(cpbRegister);

            customInitUI(InitialView.this);

            // 标题
            toolbar_title.setText("商户资料验证");

            //输入限制
            TextViewUtils.setPhone(etValueMobile);
            TextViewUtils.setAlphaNumeric(etCompanyCodeValue);
        }

        private void initListener() {
            imgReturn.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    onBackPressedSupport();
                }
            });

            // 设置点击就关闭
            rlTipsLicense.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    rlTipsLicense.setVisibility(View.GONE);
                }
            });

            // 选择地址
            rlAddress.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    //隐藏软键盘
                    hideKeyboard();
                    if (pvOptions != null) {
                        pvOptions.show();
                        hideKeyboard();
                    }
                }
            });
            // 提交数据 进行注册
            cpbRegister.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (getCodeType().equals(Constants.APP_COMPANY)) {
                        if (tvCompanyForwarder.getTag().equals("0") && tvCompanyTrailer.getTag().equals("0")) {
                            showToast("业务类型不能为空");
                            return;
                        }
                    }
                    // 判断基础类型
                    if (getCodeType().equals(Constants.APP_COMPANY) || (getCodeType().equals(Constants.APP_SHIPPER) && cbShipperCompany.isChecked())) {
                        if (TextUtils.isEmpty(etValueDepartureTitle.getText().toString().trim())) {
                            etValueDepartureTitle.requestFocus();
                            etValueDepartureTitle.setError("请输入商户名称");
                            return;
                        }
                        if (TextUtils.isEmpty(etCompanyCodeValue.getText().toString().trim())) {
                            etCompanyCodeValue.requestFocus();
                            etCompanyCodeValue.setError("请输入营业执照号码");
                            return;
                        }
                        if (TextUtils.isEmpty(tvAddressValue.getText().toString().trim())) {
                            showToast("请选择地区");
                            return;
                        }
                        if (TextUtils.isEmpty(etValueCompanyAddress.getText().toString().trim())) {
                            etValueCompanyAddress.requestFocus();
                            etValueCompanyAddress.setError("请输入详细地址");
                            return;
                        }
                    }
                    // 如果是货主端，并且选择个人的话
                    if (getCodeType().equals(Constants.APP_SHIPPER) && cbShipperUser.isChecked()) {
                        if (TextUtils.isEmpty(etShipperNameValue.getText().toString().trim())) {
                            etShipperNameValue.requestFocus();
                            etShipperNameValue.setError("请输入姓名");
                            return;
                        }
                        if (TextUtils.isEmpty(etCodeValue.getText().toString().trim())) {
                            etCodeValue.requestFocus();
                            etCodeValue.setError("请输入身份证号码");
                            return;
                        }
                    }


                    // 判断联系人，这是共同的
                    if (TextUtils.isEmpty(etNameValue.getText().toString().trim())) {
                        etNameValue.requestFocus();
                        etNameValue.setError("请输入联系人");
                        return;
                    }
                    if (TextUtils.isEmpty(etValueMobile.getText().toString().trim())) {
                        etValueMobile.requestFocus();
                        etValueMobile.setError("请输入手机/区号-座机号");
                        return;
                    }


                    //判断图片是否为空，2个类型
                    if (getCodeType().equals(Constants.APP_COMPANY) || (getCodeType().equals(Constants.APP_SHIPPER) && cbShipperCompany.isChecked())) {
                        if (TextUtils.isEmpty(mLicensePhoto)) {
                            showToast("营业执照不能为空");
                            return;
                        }
                    }
                    if (getCodeType().equals(Constants.APP_SHIPPER) && cbShipperUser.isChecked()) {
                        if (TextUtils.isEmpty(mStrIdPhoto1)) {
                            showToast("身份证正面不能为空");
                            return;
                        }
                        if (TextUtils.isEmpty(mStrIdPhoto2)) {
                            showToast("身份证反面不能为空");
                            return;
                        }
                        if (TextUtils.isEmpty(mStrIdPhoto3)) {
                            showToast("手持身份证不能为空");
                            return;
                        }
                    }

                    //提示
                    if (mDialogHelperComfit == null)
                        mDialogHelperComfit = new DialogHelper(BaseRegisterDetailActivity.this, "提示", "请确保以上资料正确无误", "是的", "取消", false, new CommonDialog.Listener() {
                            @Override
                            public void ok(String content) {
                                mPresenter.handleRegister(getModel(), getIntent().getStringExtra(VERIFICATION_CODE));
                            }

                            @Override
                            public void cancel() {

                            }
                        });
                    mDialogHelperComfit.show();

                }
            });

            // 如果选择企业的话
            cbShipperCompany.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (isChecked) {
                    // 显示和隐藏相关的图片层
                    llPhotoCompany.setVisibility(View.VISIBLE);
                    llPhotoShipper.setVisibility(View.GONE);

                    // 显示和隐藏相关的信息层
                    llInformationCompany.setVisibility(View.VISIBLE);
                    llInformationShipper.setVisibility(View.GONE);

                    cbShipperUser.setChecked(false);
                }
            });

            // 如果选择个人的话
            cbShipperUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        // 显示和隐藏相关的图片层
                        llPhotoCompany.setVisibility(View.GONE);
                        llPhotoShipper.setVisibility(View.VISIBLE);

                        // 显示和隐藏相关的信息层
                        llInformationCompany.setVisibility(View.GONE);
                        llInformationShipper.setVisibility(View.VISIBLE);

                        cbShipperCompany.setChecked(false);
                    }
                }
            });

            // 企业多选：货代业务，拖车业务
            tvCompanyForwarder.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (v.getTag().equals("1")) {
                        v.setTag("0");
                        v.setBackgroundResource(R.drawable.ic_hdyw_normal);
                    } else {
                        v.setTag("1");
                        v.setBackgroundResource(R.drawable.ic_hdyw_click);
                    }
                }
            });
            tvCompanyTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag().equals("1")) {
                        v.setTag("0");
                        v.setBackgroundResource(R.drawable.ic_hdyw_normal);
                    } else {
                        v.setTag("1");
                        v.setBackgroundResource(R.drawable.ic_hdyw_click);
                    }
                }
            });

            // 处理照片的
            flLicensePhoto.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    mPhotoType = FLLICENSEPHOTO;
                    handlePhoto(imgLicensePhoto);
                }
            });
            flIdPhoto1.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    mPhotoType = FLIDPHOTO1;
                    handlePhoto(imgIdPhoto1);
                }
            });
            flIdPhoto2.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    mPhotoType = FLIDPHOTO2;
                    handlePhoto(imgIdPhoto2);
                }
            });
            flIdPhoto3.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    mPhotoType = FLIDPHOTO3;
                    handlePhoto(imgIdPhoto3);
                }
            });

            // 第一个步骤选择下一步的话
            cpbNext.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    // 如果是企业的话，判断是否选择没
                    if (getCodeType().equals(Constants.APP_COMPANY)) {
                        // 判断是否选择了
                        if (tvCompanyForwarder.getTag().equals("0") && tvCompanyTrailer.getTag().equals("0")) {
                            Toast.makeText(BaseApplication.getInstance(), "至少选择一个业务类型", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    setStep2();
                }
            });

            // 第三部的注册完成按钮
            btnRegister.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    // 关闭当前界面
                    BaseRegisterDetailActivity.this.finish();
                }
            });

        }

    }

}
