package com.yaoguang.appcommon.phone.register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.dd.CircularProgressButton;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.BaseSubmitProgressFragment;
import com.yaoguang.appcommon.common.base.LookPhotoActivity;
import com.yaoguang.appcommon.common.finalrequest.CommpanyRequest;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.appcommon.common.view.EditTextFragment;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseSubmitProgressView;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.appcommon.utils.TextViewUtils;
import com.yaoguang.lib.common.imagepicker.ImagePickerUtils;
import com.yaoguang.lib.entity.ProvinceBeans;
import com.yaoguang.lib.qinui.QiNiuManager;
import com.yaoguang.interactor.common.other.register.RegisterContact;
import com.yaoguang.interactor.common.other.register.RegisterPresenterImpl;

import java.util.ArrayList;


/**
 * 详情的注册
 * Created by zhongjh on 2017/7/4.
 */
public abstract class BaseRegisterDetailFragment<T> extends BaseSubmitProgressFragment implements RegisterContact.RegisterView<T>, RegisterContact.RegisterDetailView, BaseSubmitProgressView {


    ProvinceBeans mProvinceBeans;
    public InitialView mInitialView;
    RegisterContact.RegisterPresenter<T> mPresenter;
    BaseRegisterActivity.MyOnActivityResult mMyOnActivityResult;

    // 拍照类型
    int photoType;
    /***
     * 营业执照
     */
    final static int FLLICENSEPHOTO = 0;
    /***
     * 商户Logo
     */
    final static int FLSHOPLOGO = 1;
    /***
     * 商户照片
     */
    final static int FLSHOPPHOTO = 2;
    /***
     * 身份证正面
     */
    final static int FLIDPHOTO1 = 3;
    /***
     * 身份证反面
     */
    final static int FLIDPHOTO2 = 4;
    /***
     * 商户Logo
     */
    final static int FLSHIPPERSHOPLOGO = 5;
    /***
     * 商户照片
     */
    final static int FLSHIPPERSHOPPHOTO = 6;
    private DialogHelper mDialogHelper;

    /**
     * 用户类型
     */
    protected abstract String getCodeType();

    /**
     * 验证码
     */
    protected abstract String getCode();

    /**
     * 自定义ui
     *
     * @param initialView
     */
    protected abstract void customInitUI(InitialView initialView);

    /**
     * 保存进实体，用于注册用的实体
     *
     * @param initialView 当前ui类
     */
    protected abstract void saveModel(InitialView initialView);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_register_detail, container, false);
        customSetting();
        mInitialView = new InitialView(view);
        setCpbSubmit(mInitialView.viewHolder.cpbRegister);
        mPresenter = new RegisterPresenterImpl<>(this, this, getCodeType(), getContext());
        mPresenter.initDetail();
        return view;
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onDestroy() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        ((BaseRegisterActivity) getActivity()).unregisterMyOnActivityResult(mMyOnActivityResult);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == CommpanyRequest.REQUESTCODE_REGISTER + photoType) {
                ArrayList<ImageItem> images = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String iconPath = images.get(0).path;
                switch (photoType) {
                    case FLLICENSEPHOTO:
                        mInitialView.viewHolder.llLicensePhoto.setVisibility(View.GONE);
                        mInitialView.viewHolder.imgLicensePhoto.setVisibility(View.VISIBLE);
                        mInitialView.viewHolder.imgLicensePhoto.setTag(R.id.tag_glide, iconPath);
                        GlideManager.getInstance().withNoneCache(this.getContext(), iconPath, mInitialView.viewHolder.imgLicensePhoto);
                        break;
                    case FLSHOPPHOTO:
                        mInitialView.viewHolder.llShopPhoto.setVisibility(View.GONE);
                        mInitialView.viewHolder.imgShopPhoto.setVisibility(View.VISIBLE);
                        mInitialView.viewHolder.imgShopPhotoClear.setVisibility(View.VISIBLE);
                        mInitialView.viewHolder.imgShopPhoto.setTag(R.id.tag_glide, iconPath);
                        GlideManager.getInstance().withNoneCache(this.getContext(), iconPath, mInitialView.viewHolder.imgShopPhoto);
                        break;
                    case FLSHOPLOGO:
                        mInitialView.viewHolder.llShopLogo.setVisibility(View.GONE);
                        mInitialView.viewHolder.imgShopLogo.setVisibility(View.VISIBLE);
                        mInitialView.viewHolder.imgShopLogoClear.setVisibility(View.VISIBLE);
                        mInitialView.viewHolder.imgShopLogo.setTag(R.id.tag_glide, iconPath);
                        GlideManager.getInstance().withNoneCache(this.getContext(), iconPath, mInitialView.viewHolder.imgShopLogo);
                        break;
                    case FLIDPHOTO1:
                        mInitialView.viewHolder.llIdPhoto1.setVisibility(View.GONE);
                        mInitialView.viewHolder.imgIdPhoto1.setVisibility(View.VISIBLE);
                        mInitialView.viewHolder.imgIdPhoto1.setTag(R.id.tag_glide, iconPath);
                        GlideManager.getInstance().withNoneCache(this.getContext(), iconPath, mInitialView.viewHolder.imgIdPhoto1);
                        break;
                    case FLIDPHOTO2:
                        mInitialView.viewHolder.llIdPhoto2.setVisibility(View.GONE);
                        mInitialView.viewHolder.imgIdPhoto2.setVisibility(View.VISIBLE);
                        mInitialView.viewHolder.imgIdPhoto2.setTag(R.id.tag_glide, iconPath);
                        GlideManager.getInstance().withNoneCache(this.getContext(), iconPath, mInitialView.viewHolder.imgIdPhoto2);
                        break;
                    case FLSHIPPERSHOPLOGO:
                        mInitialView.viewHolder.llShipperShopLogo.setVisibility(View.GONE);
                        mInitialView.viewHolder.imgShipperShopLogo.setVisibility(View.VISIBLE);
                        mInitialView.viewHolder.imgShipperShopLogoClear.setVisibility(View.VISIBLE);
                        mInitialView.viewHolder.imgShipperShopLogo.setTag(R.id.tag_glide, iconPath);
                        GlideManager.getInstance().withNoneCache(this.getContext(), iconPath, mInitialView.viewHolder.imgShipperShopLogo);
                        break;
                    case FLSHIPPERSHOPPHOTO:
                        mInitialView.viewHolder.llShipperShopPhoto.setVisibility(View.GONE);
                        mInitialView.viewHolder.imgShipperShopPhoto.setVisibility(View.VISIBLE);
                        mInitialView.viewHolder.imgShipperShopPhotoClear.setVisibility(View.VISIBLE);
                        mInitialView.viewHolder.imgShipperShopPhoto.setTag(R.id.tag_glide, iconPath);
                        GlideManager.getInstance().withNoneCache(this.getContext(), iconPath, mInitialView.viewHolder.imgShipperShopPhoto);
                        break;
                }
                wanTuUploadFile(iconPath);
            }
        }
    }

    private void wanTuUploadFile(String iconPath) {
        QiNiuManager.getInstance().upload(getActivity(), getContext(), iconPath, true, new QiNiuManager.ComeBack() {
            @Override
            public void result(boolean result, String url) {
                if (result) {
                    //更新
                    switch (photoType) {
                        case FLLICENSEPHOTO:
                            mInitialView.licensePhoto = url;
                            break;
                        case FLSHOPPHOTO:
                            mInitialView.shopPhoto = url;
                            break;
                        case FLSHOPLOGO:
                            mInitialView.shopLogo = url;
                            break;
                        case FLIDPHOTO1:
                            mInitialView.strIdPhoto1 = url;
                            break;
                        case FLIDPHOTO2:
                            mInitialView.strIdPhoto2 = url;
                            break;
                        case FLSHIPPERSHOPLOGO:
                            mInitialView.shipperShopLogo = url;
                            break;
                        case FLSHIPPERSHOPPHOTO:
                            mInitialView.shipperShopPhoto = url;
                            break;
                    }
                    showToast("上传成功");
                } else {
                    showToast("上传失败");
                }
            }

            @Override
            public void progress(String speed, int progress) {

            }
        });
    }

    @Override
    public void setProvinceBeans(ProvinceBeans value) {
        mProvinceBeans = value;
        mInitialView.pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = mProvinceBeans.getProvinces().get(options1).getPickerViewText() +
                        mProvinceBeans.getCitys().get(options1).get(options2) +
                        mProvinceBeans.getAreas().get(options1).get(options2).get(options3);
                mInitialView.provinces = mProvinceBeans.getProvinces().get(options1).getPickerViewText();
                mInitialView.citys = mProvinceBeans.getCitys().get(options1).get(options2);
                mInitialView.areas = mProvinceBeans.getAreas().get(options1).get(options2).get(options3);
                mInitialView.viewHolder.tvAddressValue.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        mInitialView.pvOptions.setPicker(mProvinceBeans.getProvinces(), mProvinceBeans.getCitys(), mProvinceBeans.getAreas());//三级选择器
    }

    public class InitialView {

        public ViewHolder viewHolder;

        //Logo图片
        public String shopLogo = "";
        //大图
        public String shopPhoto = "";
        //LicensePhoto
        public String licensePhoto = "";

        //身份证1
        public String strIdPhoto1 = "";
        //身份证2
        public String strIdPhoto2 = "";
        //Logo图片
        public String shipperShopLogo = "";
        //大图
        public String shipperShopPhoto = "";

        public final View view;

        //省
        public String provinces;
        //市
        public String citys;
        //区
        public String areas;

        OptionsPickerView pvOptions;

        public InitialView(View view) {
            this.view = view;
            viewHolder = new ViewHolder(view);
            initUI();
            initListener();
        }

        private void initUI() {

            viewHolder.llCompanyPhoto.setVisibility(View.VISIBLE);
            viewHolder.llShipperPhoto1.setVisibility(View.GONE);
            viewHolder.llShipperPhoto2.setVisibility(View.GONE);
            //企业的话，修改成营业执照号码
            viewHolder.tvCompanyCodeTitle.setText("营业执照号码");
            viewHolder.etCompanyCodeValue.setHint("请输入营业执照号码");


            customInitUI(InitialView.this);

            //输入限制
            TextViewUtils.setPhone(viewHolder.etValueMobile);
            TextViewUtils.setAlphaNumeric(viewHolder.etCompanyCodeValue);

        }

        private void initListener() {
            //返回事件
            mMyOnActivityResult = new BaseRegisterActivity.MyOnActivityResult() {
                @Override
                public boolean onActivityResult(Intent data) {
                    mInitialView.viewHolder.tvShopDetailTitleValue.setText(data.getStringExtra(EditTextFragment.VALUE));
                    return false;
                }
            };
            ((BaseRegisterActivity) getActivity()).registerMyOnActivityResult(mMyOnActivityResult);

            viewHolder.rlAddress.setOnClickListener(new NoDoubleClickListener() {
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
            viewHolder.cpbRegister.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (getCodeType().equals(Constants.APP_COMPANY))
                        if (!viewHolder.cbApplyType0.isChecked() && !viewHolder.cbApplyType1.isChecked()) {
                            showToast("业务类型不能为空");
                            return;
                        }
                    if (TextUtils.isEmpty(viewHolder.etValueDepartureTitle.getText().toString().trim())) {
                        viewHolder.etValueDepartureTitle.requestFocus();
                        viewHolder.etValueDepartureTitle.setError("请输入商户名称");
                        return;
                    }
                    if (TextUtils.isEmpty(viewHolder.tvAddressValue.getText().toString().trim())) {
                        showToast("请选择地区");
                        return;
                    }
                    if (TextUtils.isEmpty(viewHolder.etValueCompanyAddress.getText().toString().trim())) {
                        showToast("请输入详细地址");
                        return;
                    }
                    if (TextUtils.isEmpty(viewHolder.etNameValue.getText().toString().trim())) {
                        showToast("请输入联系人");
                        return;
                    }
                    if (TextUtils.isEmpty(viewHolder.etValueMobile.getText().toString().trim())) {
                        viewHolder.etValueMobile.requestFocus();
                        viewHolder.etValueMobile.setError("请输入手机/区号-座机号");
                        return;
                    }
                    if (TextUtils.isEmpty(viewHolder.etCompanyCodeValue.getText().toString().trim())) {
                        viewHolder.etCompanyCodeValue.requestFocus();
                        viewHolder.etCompanyCodeValue.setError("请输入营业执照号码");
                        return;
                    }

                    //判断图片是否为空，2个类型
                    if (viewHolder.rbApplyStatus0.isChecked() && getCodeType().equals(Constants.APP_COMPANY)) {
                        if (licensePhoto == null || licensePhoto.equals("")) {
                            showToast("营业执照不能为空");
                            return;
                        }
                    }
                    if (viewHolder.rbApplyStatus1.isChecked() && getCodeType().equals(Constants.APP_SHIPPER)) {
                        if (strIdPhoto1 == null || strIdPhoto1.equals("")) {
                            showToast("身份证正面不能为空");
                            return;
                        }
                        if (strIdPhoto2 == null || strIdPhoto2.equals("")) {
                            showToast("身份证反面不能为空");
                            return;
                        }
                    }

                    //提示
                    if (mDialogHelper == null)
                        mDialogHelper = new DialogHelper(getContext(), "是否提交并完成注册", "请确保以上资料是否正确", "是的", "我再看看", false, new CommonDialog.Listener() {
                            @Override
                            public void ok(String content) {
                                saveModel(InitialView.this);
                                mPresenter.handleRegister(getModel(), getCode());
                            }

                            @Override
                            public void cancel() {

                            }
                        });
                    mDialogHelper.show();

                }
            });
            viewHolder.rbApplyStatus0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        viewHolder.llCompanyPhoto.setVisibility(View.VISIBLE);
                        viewHolder.llShipperPhoto1.setVisibility(View.GONE);
                        viewHolder.llShipperPhoto2.setVisibility(View.GONE);
                        //企业的话，修改成营业执照号码
                        viewHolder.tvCompanyCodeTitle.setText("营业执照号码");
                        viewHolder.etCompanyCodeValue.setHint("请输入营业执照号码");
                        //商户名称处修改成企业名称
                        viewHolder.etValueDepartureTitle.setHint("企业名称");
                    }
                }
            });
            viewHolder.rbApplyStatus1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        viewHolder.llCompanyPhoto.setVisibility(View.GONE);
                        viewHolder.llShipperPhoto1.setVisibility(View.VISIBLE);
                        viewHolder.llShipperPhoto2.setVisibility(View.VISIBLE);
                        //个人的话，修改成身份证号码
                        viewHolder.tvCompanyCodeTitle.setText("身份证号码");
                        viewHolder.etCompanyCodeValue.setHint("请输入身份证号码");
                        //商户名称处修改成姓名
                        viewHolder.etValueDepartureTitle.setHint("姓名");
                    }
                }
            });

            // 处理照片的
            viewHolder.flLicensePhoto.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    photoType = FLLICENSEPHOTO;
                    handlePhoto(InitialView.this, viewHolder, viewHolder.imgLicensePhoto);
                }
            });
            viewHolder.flShopPhoto.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    photoType = FLSHOPPHOTO;
                    handlePhoto(InitialView.this, viewHolder, viewHolder.imgShopPhoto);
                }
            });
            viewHolder.flShopLogo.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    photoType = FLSHOPLOGO;
                    handlePhoto(InitialView.this, viewHolder, viewHolder.imgShopLogo);
                }
            });
            viewHolder.flIdPhoto1.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    photoType = FLIDPHOTO1;
                    handlePhoto(InitialView.this, viewHolder, viewHolder.imgIdPhoto1);
                }
            });
            viewHolder.flIdPhoto2.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    photoType = FLIDPHOTO2;
                    handlePhoto(InitialView.this, viewHolder, viewHolder.imgIdPhoto2);
                }
            });
            viewHolder.flShipperShopLogo.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    photoType = FLSHIPPERSHOPLOGO;
                    handlePhoto(InitialView.this, viewHolder, viewHolder.imgShipperShopLogo);
                }
            });
            viewHolder.flShipperShopPhoto.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    photoType = FLSHIPPERSHOPPHOTO;
                    handlePhoto(InitialView.this, viewHolder, viewHolder.imgShipperShopPhoto);
                }
            });

            // 图片清除事件
            viewHolder.imgShopLogoClear.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    viewHolder.imgShopLogo.setImageResource(0);
                    viewHolder.imgShopLogo.setVisibility(View.GONE);
                    viewHolder.imgShopLogoClear.setVisibility(View.GONE);
                    viewHolder.llShopLogo.setVisibility(View.VISIBLE);
                }
            });
            viewHolder.imgShopPhotoClear.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    viewHolder.imgShopPhoto.setImageResource(0);
                    viewHolder.imgShopPhoto.setVisibility(View.GONE);
                    viewHolder.imgShopPhotoClear.setVisibility(View.GONE);
                    viewHolder.llShopPhoto.setVisibility(View.VISIBLE);
                }
            });
            viewHolder.imgShipperShopLogoClear.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    viewHolder.imgShipperShopLogo.setImageResource(0);
                    viewHolder.imgShipperShopLogo.setVisibility(View.GONE);
                    viewHolder.imgShipperShopLogoClear.setVisibility(View.GONE);
                    viewHolder.llShipperShopLogo.setVisibility(View.VISIBLE);
                }
            });
            viewHolder.imgShipperShopPhotoClear.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    viewHolder.imgShipperShopPhoto.setImageResource(0);
                    viewHolder.imgShipperShopPhoto.setVisibility(View.GONE);
                    viewHolder.imgShipperShopPhotoClear.setVisibility(View.GONE);
                    viewHolder.llShipperShopPhoto.setVisibility(View.VISIBLE);
                }
            });
        }


    }

    private void handlePhoto(InitialView initialView, ViewHolder viewHolder, final ImageView imageView) {
        // 设置剪辑模式
        boolean isWithCrop = false;
        // 宽度比例
        int minX = 1;
        // 高度比例
        int minY = 1;
        if (photoType == FLLICENSEPHOTO) {
            isWithCrop = false;
        } else if (photoType == FLSHOPPHOTO) {
            isWithCrop = true;
            minX = 360;
            minY = 160;
        } else if (photoType == FLSHOPLOGO) {
            isWithCrop = true;
            minX = 100;
            minY = 100;
        } else if (photoType == FLIDPHOTO1) {
            isWithCrop = false;
        } else if (photoType == FLIDPHOTO2) {
            isWithCrop = false;
        } else if (photoType == FLSHIPPERSHOPLOGO) {
            isWithCrop = true;
            minX = 100;
            minY = 100;
        } else if (photoType == FLSHIPPERSHOPPHOTO) {
            isWithCrop = true;
            minX = 360;
            minY = 160;
        }
        SweetSheet sweetSheet = new SweetSheet(viewHolder.flMain);
        sweetSheet.setMenuList(R.menu.sheet_head_portrait_phone);
        sweetSheet.setTitle("请选择图片");
        RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
        recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(250));
        sweetSheet.setDelegate(recyclerViewDelegate);
        sweetSheet.setBackgroundEffect(new DimEffect(4));
        final boolean finalIsWithCrop = isWithCrop;
        final int finalMinY = minY;
        final int finalMinX = minX;
        sweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity) {
                if (position == 0) {
                    ImagePickerUtils.startActivityForResult(getActivity(), BaseRegisterDetailFragment.this, finalIsWithCrop, CommpanyRequest.REQUESTCODE_REGISTER + photoType, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), finalMinY, finalMinX);
                } else if (position == 1) {
                    ImagePickerUtils.startActivityForResult(getActivity(), BaseRegisterDetailFragment.this, finalIsWithCrop, CommpanyRequest.REQUESTCODE_REGISTER + photoType, false, DisplayMetricsSPUtils.getScreenWidth(getContext()), finalMinY, finalMinX);
                } else if (position == 2) {
                    if (!TextUtils.isEmpty((String) imageView.getTag(R.id.tag_glide))) {
                        LookPhotoActivity.newInstance(getActivity(), (String) imageView.getTag(R.id.tag_glide), true);
                    } else {
                        Toast.makeText(BaseApplication.getInstance(), "请先上传图片", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
        InputMethodUtil.hideKeyboard(getActivity());
        sweetSheet.show();
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tvApplyTypeTitle;
        public CheckBox cbApplyType0;
        public CheckBox cbApplyType1;
        public RelativeLayout rlCommpanyApplyType;
        public TextView tvApplyStatusTitle;
        public RadioButton rbApplyStatus0;
        public RadioButton rbApplyStatus1;
        public RadioGroup rg;
        public RelativeLayout rlShipperApplyStatus;
        public TextView tvCompanyNameTitle;
        public EditText etValueDepartureTitle;
        public RelativeLayout rlCompanyName;
        public TextView tvAddressTitle;
        public TextView tvAddressValue;
        public RelativeLayout rlAddress;
        public TextView tvCompanyAddressTitle;
        public EditText etValueCompanyAddress;
        public RelativeLayout rlCompanyAddress;
        public TextView tvTitleName;
        public EditText etNameValue;
        public RelativeLayout rlName;
        public TextView tvTitleMobile;
        public EditText etValueMobile;
        public RelativeLayout rlMobile;
        public TextView tvShopDetailTitle;
        public TextView tvShopDetailTitleValue;
        public RelativeLayout rlShopDetail;
        public TextView tvCompanyCodeTitle;
        public EditText etCompanyCodeValue;
        public RelativeLayout rlCompanyCode;
        public ImageView imgLicensePhotoAdd;
        public LinearLayout llLicensePhoto;
        public ImageView imgLicensePhoto;
        public FrameLayout flLicensePhoto;
        public ImageView imgShopLogoAdd;
        public LinearLayout llShopLogo;
        public ImageView imgShopLogo;
        public ImageView imgShopLogoClear;
        public FrameLayout flShopLogo;
        public ImageView imgShopPhotoAdd;
        public LinearLayout llShopPhoto;
        public ImageView imgShopPhoto;
        public ImageView imgShopPhotoClear;
        public FrameLayout flShopPhoto;
        public LinearLayout llCompanyPhoto;
        public ImageView imgIdPhoto1Add;
        public LinearLayout llIdPhoto1;
        public ImageView imgIdPhoto1;
        public FrameLayout flIdPhoto1;
        public ImageView imgIdPhoto2Add;
        public LinearLayout llIdPhoto2;
        public ImageView imgIdPhoto2;
        public FrameLayout flIdPhoto2;
        public ImageView imgShipperShopLogoAdd;
        public LinearLayout llShipperShopLogo;
        public ImageView imgShipperShopLogo;
        public ImageView imgShipperShopLogoClear;
        public FrameLayout flShipperShopLogo;
        public LinearLayout llShipperPhoto1;
        public ImageView imgShipperShopPhotoAdd;
        public LinearLayout llShipperShopPhoto;
        public ImageView imgShipperShopPhoto;
        public ImageView imgShipperShopPhotoClear;
        public FrameLayout flShipperShopPhoto;
        public LinearLayout llShipperPhoto2;
        public CircularProgressButton cpbRegister;
        public FrameLayout flMain;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvApplyTypeTitle = (TextView) rootView.findViewById(R.id.tvApplyTypeTitle);
            this.cbApplyType0 = (CheckBox) rootView.findViewById(R.id.cbApplyType0);
            this.cbApplyType1 = (CheckBox) rootView.findViewById(R.id.cbApplyType1);
            this.rlCommpanyApplyType = (RelativeLayout) rootView.findViewById(R.id.rlCommpanyApplyType);
            this.tvApplyStatusTitle = (TextView) rootView.findViewById(R.id.tvApplyStatusTitle);
            this.rbApplyStatus0 = (RadioButton) rootView.findViewById(R.id.rbApplyStatus0);
            this.rbApplyStatus1 = (RadioButton) rootView.findViewById(R.id.rbApplyStatus1);
            this.rg = (RadioGroup) rootView.findViewById(R.id.rg);
            this.rlShipperApplyStatus = (RelativeLayout) rootView.findViewById(R.id.rlShipperApplyStatus);
            this.tvCompanyNameTitle = (TextView) rootView.findViewById(R.id.tvCompanyNameTitle);
            this.etValueDepartureTitle = (EditText) rootView.findViewById(R.id.etValueDepartureTitle);
            this.rlCompanyName = (RelativeLayout) rootView.findViewById(R.id.rlCompanyName);
            this.tvAddressTitle = (TextView) rootView.findViewById(R.id.tvAddressTitle);
            this.tvAddressValue = (TextView) rootView.findViewById(R.id.tvAddressValue);
            this.rlAddress = (RelativeLayout) rootView.findViewById(R.id.rlAddress);
            this.tvCompanyAddressTitle = (TextView) rootView.findViewById(R.id.tvCompanyAddressTitle);
            this.etValueCompanyAddress = (EditText) rootView.findViewById(R.id.etValueCompanyAddress);
            this.rlCompanyAddress = (RelativeLayout) rootView.findViewById(R.id.rlCompanyAddress);
            this.tvTitleName = (TextView) rootView.findViewById(R.id.tvTitleName);
            this.etNameValue = (EditText) rootView.findViewById(R.id.etNameValue);
            this.rlName = (RelativeLayout) rootView.findViewById(R.id.rlName);
            this.tvTitleMobile = (TextView) rootView.findViewById(R.id.tvTitleMobile);
            this.etValueMobile = (EditText) rootView.findViewById(R.id.etValueMobile);
            this.rlMobile = (RelativeLayout) rootView.findViewById(R.id.rlMobile);
            this.tvShopDetailTitle = (TextView) rootView.findViewById(R.id.tvShopDetailTitle);
            this.tvShopDetailTitleValue = (TextView) rootView.findViewById(R.id.tvShopDetailTitleValue);
            this.rlShopDetail = (RelativeLayout) rootView.findViewById(R.id.rlShopDetail);
            this.tvCompanyCodeTitle = (TextView) rootView.findViewById(R.id.tvCompanyCodeTitle);
            this.etCompanyCodeValue = (EditText) rootView.findViewById(R.id.etCompanyCodeValue);
            this.rlCompanyCode = (RelativeLayout) rootView.findViewById(R.id.rlCompanyCode);
            this.imgLicensePhotoAdd = (ImageView) rootView.findViewById(R.id.imgLicensePhotoAdd);
            this.llLicensePhoto = (LinearLayout) rootView.findViewById(R.id.llLicensePhoto);
            this.imgLicensePhoto = (ImageView) rootView.findViewById(R.id.imgLicensePhoto);
            this.flLicensePhoto = (FrameLayout) rootView.findViewById(R.id.flLicensePhoto);
            this.imgShopLogoAdd = (ImageView) rootView.findViewById(R.id.imgShopLogoAdd);
            this.llShopLogo = (LinearLayout) rootView.findViewById(R.id.llShopLogo);
            this.imgShopLogo = (ImageView) rootView.findViewById(R.id.imgShopLogo);
            this.imgShopLogoClear = (ImageView) rootView.findViewById(R.id.imgShopLogoClear);
            this.flShopLogo = (FrameLayout) rootView.findViewById(R.id.flShopLogo);
            this.imgShopPhotoAdd = (ImageView) rootView.findViewById(R.id.imgShopPhotoAdd);
            this.llShopPhoto = (LinearLayout) rootView.findViewById(R.id.llShopPhoto);
            this.imgShopPhoto = (ImageView) rootView.findViewById(R.id.imgShopPhoto);
            this.imgShopPhotoClear = (ImageView) rootView.findViewById(R.id.imgShopPhotoClear);
            this.flShopPhoto = (FrameLayout) rootView.findViewById(R.id.flShopPhoto);
            this.llCompanyPhoto = (LinearLayout) rootView.findViewById(R.id.llCompanyPhoto);
            this.imgIdPhoto1Add = (ImageView) rootView.findViewById(R.id.imgIdPhoto1Add);
            this.llIdPhoto1 = (LinearLayout) rootView.findViewById(R.id.llIdPhoto1);
            this.imgIdPhoto1 = (ImageView) rootView.findViewById(R.id.imgIdPhoto1);
            this.flIdPhoto1 = (FrameLayout) rootView.findViewById(R.id.flIdPhoto1);
            this.imgIdPhoto2Add = (ImageView) rootView.findViewById(R.id.imgIdPhoto2Add);
            this.llIdPhoto2 = (LinearLayout) rootView.findViewById(R.id.llIdPhoto2);
            this.imgIdPhoto2 = (ImageView) rootView.findViewById(R.id.imgIdPhoto2);
            this.flIdPhoto2 = (FrameLayout) rootView.findViewById(R.id.flIdPhoto2);
            this.imgShipperShopLogoAdd = (ImageView) rootView.findViewById(R.id.imgShipperShopLogoAdd);
            this.llShipperShopLogo = (LinearLayout) rootView.findViewById(R.id.llShipperShopLogo);
            this.imgShipperShopLogo = (ImageView) rootView.findViewById(R.id.imgShipperShopLogo);
            this.imgShipperShopLogoClear = (ImageView) rootView.findViewById(R.id.imgShipperShopLogoClear);
            this.flShipperShopLogo = (FrameLayout) rootView.findViewById(R.id.flShipperShopLogo);
            this.llShipperPhoto1 = (LinearLayout) rootView.findViewById(R.id.llShipperPhoto1);
            this.imgShipperShopPhotoAdd = (ImageView) rootView.findViewById(R.id.imgShipperShopPhotoAdd);
            this.llShipperShopPhoto = (LinearLayout) rootView.findViewById(R.id.llShipperShopPhoto);
            this.imgShipperShopPhoto = (ImageView) rootView.findViewById(R.id.imgShipperShopPhoto);
            this.imgShipperShopPhotoClear = (ImageView) rootView.findViewById(R.id.imgShipperShopPhotoClear);
            this.flShipperShopPhoto = (FrameLayout) rootView.findViewById(R.id.flShipperShopPhoto);
            this.llShipperPhoto2 = (LinearLayout) rootView.findViewById(R.id.llShipperPhoto2);
            this.cpbRegister = (CircularProgressButton) rootView.findViewById(R.id.cpbRegister);
            this.flMain = (FrameLayout) rootView.findViewById(R.id.flMain);
        }

    }
}
