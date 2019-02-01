package com.yaoguang.company.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.appcommon.common.base.LookPhotoActivity;
import com.yaoguang.appcommon.common.finalrequest.CommpanyRequest;
import com.yaoguang.appcommon.common.view.EditTextFragment;
import com.yaoguang.appcommon.phone.my.presonalcenter.PersonalCenterContact;
import com.yaoguang.appcommon.phone.my.presonalcenter.PersonalCenterPresenterImpl;
import com.yaoguang.company.R;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.dialog.helper.DialogUtil;
import com.yaoguang.lib.appcommon.utils.CountDownButtonHelper;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragment2;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.common.imagepicker.ImagePickerUtils;
import com.yaoguang.lib.qinui.QiNiuManager;

import java.util.ArrayList;


/**
 * 个人中心
 * Created by zhongjh on 2017/7/4.
 * <p>
 * 物流：
 * 个人中心的手机：user.MOBILE
 * 个人中心的电话：user.PHONE
 * 物流商户的电话：userApply.MOBILE
 * <p>
 * 货主：
 * 个人中心的手机：userOwner.phone
 * 个人中心的电话：userOwner.TEL
 * 货主商户的电话：userOwner.MOBILE
 */
public abstract class BasePersonalCenterFragment<T> extends BaseFragment2 implements PersonalCenterContact.PersonalCenterView<T> {


    public static final int REQUESTCODE_EMAIL = 0;
    public static final int REQUESTCODE_QQ = 1;
    public static final int REQUESTCODE_SIGN = 2;
    public static final int REQUESTCODE_MOBILE = 3;
    public static final int REQUESTCODE_TOP = 4;
    public static final int REQUESTCODE_AVATAR = 5;


    public InitialView mInitialView;
    PersonalCenterContact.PersonalCenterPresenter<T> mPresenter;

    private CountDownButtonHelper mCountDownButtonHelper = new CountDownButtonHelper();

    protected TextView btnGetCode;
    private DialogHelper mDialogHelper;

    /**
     * 用户类型
     */
    protected abstract String getCodeType();

    /**
     * 初始化事件
     *
     * @param initialView 本身view
     */
    protected abstract void customInit(InitialView initialView);

    /**
     * 点击事件
     *
     * @param initialView 本身view
     */
    protected abstract void customInitListener(InitialView initialView);

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        mInitialView = new InitialView(view);
    }

    @Override
    public void init() {
        mPresenter = new PersonalCenterPresenterImpl<>(this, getCodeType());
        mPresenter.subscribe();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                switch (requestCode) {
                    case CommpanyRequest.REQUESTCODE_PERSONAL + REQUESTCODE_TOP:
                        //个人中心顶部图片
                        ArrayList<ImageItem> images = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                        if (images.size() <= 0) {
                            Toast.makeText(BaseApplication.getInstance(), "没有数据", Toast.LENGTH_SHORT).show();
                        } else {
                            QiNiuManager.getInstance().upload(getActivity(), getContext(), images.get(0).path, true, new QiNiuManager.ComeBack() {
                                @Override
                                public void result(boolean result, String url) {
                                    if (result) {
                                        //更新
                                        mPresenter.modifyBackgroundPicture(url);
                                        mInitialView.imgTop.setTag(R.id.tag_glide, url);

                                        Glide.with(BasePersonalCenterFragment.this)
                                                .load(url)
                                                .apply(new RequestOptions()
                                                        .centerCrop()
                                                        .placeholder(R.drawable.ic_grbg)
                                                        .error(R.drawable.ic_grbg))
                                                .into(mInitialView.imgTop);
                                    } else {
                                        showToast("上传失败");
                                    }
                                }

                                @Override
                                public void progress(String speed, int progress) {

                                }
                            });
                        }
                        break;
                    case CommpanyRequest.REQUESTCODE_PERSONAL + REQUESTCODE_AVATAR:
                        //个人中心头像图片
                        ArrayList<ImageItem> images2 = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                        if (images2.size() <= 0) {
                            Toast.makeText(BaseApplication.getInstance(), "没有数据", Toast.LENGTH_SHORT).show();
                        } else {
                            QiNiuManager.getInstance().upload(getActivity(), getContext(), images2.get(0).path, true, new QiNiuManager.ComeBack() {
                                @Override
                                public void result(boolean result, String url) {
                                    if (result) {
                                        //更新
                                        mPresenter.modifyPhoto(url);
                                        mInitialView.ivAvatar.setTag(R.id.tag_glide, url);
                                        GlideManager.getInstance().withRounded(BasePersonalCenterFragment.this, url, mInitialView.ivAvatar);
                                    } else {
                                        showToast("上传失败");
                                    }
                                }

                                @Override
                                public void progress(String speed, int progress) {

                                }
                            });
                        }
                        break;
                }

            } else {
                Toast.makeText(BaseApplication.getInstance(), "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String value = data.getString(EditTextFragment.VALUE);
            switch (requestCode) {
                case REQUESTCODE_MOBILE:
                    mPresenter.modifyMobile(value);
                    break;
                case REQUESTCODE_EMAIL:
                    mPresenter.modifyEmail(value);
                    break;
                case REQUESTCODE_QQ:
                    mPresenter.modifyQQ(value);
                    break;
                case REQUESTCODE_SIGN:
                    mPresenter.modifySign(value);
                    break;
            }
            mPresenter.initData();
        }
    }

    @Override
    public void cancleDialog(View v) {
        DialogUtil.hideDialog();
    }

    @Override
    public void onDestroy() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        mPresenter.unSubscribe();
        super.onDestroy();
    }

    @Override
    public void startCountDown() {
        mCountDownButtonHelper.init(btnGetCode, getString(R.string.get_code), 90, 1);
        mCountDownButtonHelper.start();
    }

    @Override
    public void stopCountDown() {
        mCountDownButtonHelper.stop();
    }


    public class InitialView {

        public View rootView;
        public ImageView imgTop;
        public ImageView ivAvatar;
        public ImageView ivEditPhoto;
        public TextView tvName;
        public TextView tvTel;
        public TextView tvMobileTitle;
        public TextView tvValueMobile;
        public RelativeLayout rlMobile;
        public TextView tvPassWordTitle;
        public TextView etValuePassWord;
        public RelativeLayout rlPassWord;
        public TextView tvPhoneTitle;
        public TextView tvValuePhone;
        public RelativeLayout rlPhone;
        public TextView tvEmailTitle;
        public TextView tvValueEmail;
        public RelativeLayout rlEmail;
        public TextView tvQQTitle;
        public TextView tvValueQQ;
        public RelativeLayout rlQQ;
        public TextView tvAutographTitle;
        public TextView tvValueAutograph;
        public LinearLayout llAutograph;
        public TextView tvMerchantManagementTitle;
        public RelativeLayout rlMerchantManagement;
        public TextView tvLoginConfigurationTitle;
        public RelativeLayout rlLoginConfiguration;
        public TextView tvUserManagementTitle;
        public RelativeLayout rlUserManagement;
        public ImageView imgReturn;
        public TextView textView;
        public ImageView imgSetting;
        public RelativeLayout toolbar;
        public FrameLayout flMain;

        public InitialView(View rootView) {
            this.rootView = rootView;
            initUI();
            initListener();
        }

        private void initUI() {
            this.rootView = rootView;
            this.imgTop = (ImageView) rootView.findViewById(R.id.imgTop);
            this.ivAvatar = (ImageView) rootView.findViewById(R.id.ivAvatar);
            this.ivEditPhoto = (ImageView) rootView.findViewById(R.id.ivEditPhoto);
            this.tvName = (TextView) rootView.findViewById(R.id.tvName);
            this.tvTel = (TextView) rootView.findViewById(R.id.tvTel);
            this.tvMobileTitle = (TextView) rootView.findViewById(R.id.tvMobileTitle);
            this.tvValueMobile = (TextView) rootView.findViewById(R.id.tvValueMobile);
            this.rlMobile = (RelativeLayout) rootView.findViewById(R.id.rlMobile);
            this.tvPassWordTitle = (TextView) rootView.findViewById(R.id.tvPassWordTitle);
            this.etValuePassWord = (TextView) rootView.findViewById(R.id.etValuePassWord);
            this.rlPassWord = (RelativeLayout) rootView.findViewById(R.id.rlPassWord);
            this.tvPhoneTitle = (TextView) rootView.findViewById(R.id.tvPhoneTitle);
            this.tvValuePhone = (TextView) rootView.findViewById(R.id.tvValuePhone);
            this.rlPhone = (RelativeLayout) rootView.findViewById(R.id.rlPhone);
            this.tvEmailTitle = (TextView) rootView.findViewById(R.id.tvEmailTitle);
            this.tvValueEmail = (TextView) rootView.findViewById(R.id.tvValueEmail);
            this.rlEmail = (RelativeLayout) rootView.findViewById(R.id.rlEmail);
            this.tvQQTitle = (TextView) rootView.findViewById(R.id.tvQQTitle);
            this.tvValueQQ = (TextView) rootView.findViewById(R.id.tvValueQQ);
            this.rlQQ = (RelativeLayout) rootView.findViewById(R.id.rlQQ);
            this.tvAutographTitle = (TextView) rootView.findViewById(R.id.tvAutographTitle);
            this.tvValueAutograph = (TextView) rootView.findViewById(R.id.tvValueAutograph);
            this.llAutograph = (LinearLayout) rootView.findViewById(R.id.llAutograph);
            this.tvMerchantManagementTitle = (TextView) rootView.findViewById(R.id.tvMerchantManagementTitle);
            this.rlMerchantManagement = (RelativeLayout) rootView.findViewById(R.id.rlMerchantManagement);
            this.tvLoginConfigurationTitle = (TextView) rootView.findViewById(R.id.tvLoginConfigurationTitle);
            this.rlLoginConfiguration = (RelativeLayout) rootView.findViewById(R.id.rlLoginConfiguration);
            this.tvUserManagementTitle = (TextView) rootView.findViewById(R.id.tvUserManagementTitle);
            this.rlUserManagement = (RelativeLayout) rootView.findViewById(R.id.rlUserManagement);
            this.imgReturn = (ImageView) rootView.findViewById(R.id.imgReturn);
            this.textView = (TextView) rootView.findViewById(R.id.textView);
            this.imgSetting = (ImageView) rootView.findViewById(R.id.imgSetting);
            this.toolbar = (RelativeLayout) rootView.findViewById(R.id.toolbar);
            this.flMain = (FrameLayout) rootView.findViewById(R.id.flMain);

            customInit(this);
        }

        private void initListener() {

            this.imgReturn.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    BasePersonalCenterFragment.this.pop();
                }
            });

            //添加头像
            this.ivAvatar.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    SweetSheet sweetSheet = new SweetSheet(flMain);
                    sweetSheet.setMenuList(R.menu.sheet_head_portrait_phone);
                    sweetSheet.setTitle("请选择一种操作");
                    RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
                    recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(250));
                    sweetSheet.setDelegate(recyclerViewDelegate);
                    sweetSheet.setBackgroundEffect(new DimEffect(4));
                    sweetSheet.setOnMenuItemClickListener((position, menuEntity) -> {
                        //设置裁剪参数
                        if (position == 0) {
                            ImagePickerUtils.startActivityForResult(getActivity(), BasePersonalCenterFragment.this, true, CommpanyRequest.REQUESTCODE_PERSONAL + REQUESTCODE_AVATAR, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
                        } else if (position == 1) {
                            ImagePickerUtils.startActivityForResult(getActivity(), BasePersonalCenterFragment.this, true, CommpanyRequest.REQUESTCODE_PERSONAL + REQUESTCODE_AVATAR, false, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
                        } else if (position == 2) {
                            LookPhotoActivity.newInstance(getActivity(), ObjectUtils.parseString(ivAvatar.getTag(R.id.tag_glide)));
                        }
                        return true;
                    });
                    InputMethodUtil.hideKeyboard(getActivity());
                    sweetSheet.show();
                }
            });

            //顶部图片
            imgTop.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    SweetSheet sweetSheet = new SweetSheet(flMain);
                    sweetSheet.setMenuList(R.menu.sheet_head_portrait_phone);
                    sweetSheet.setTitle("请选择一种操作");
                    RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
                    recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(250));
                    sweetSheet.setDelegate(recyclerViewDelegate);
                    sweetSheet.setBackgroundEffect(new DimEffect(4));
                    sweetSheet.setOnMenuItemClickListener((position, menuEntity) -> {
                        //设置裁剪参数
                        if (position == 0) {
                            ImagePickerUtils.startActivityForResult(getActivity(), BasePersonalCenterFragment.this, true, CommpanyRequest.REQUESTCODE_PERSONAL + REQUESTCODE_TOP, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), 285, 540);
                        } else if (position == 1) {
                            //选择
                            ImagePickerUtils.startActivityForResult(getActivity(), BasePersonalCenterFragment.this, true, CommpanyRequest.REQUESTCODE_PERSONAL + REQUESTCODE_TOP, false, DisplayMetricsSPUtils.getScreenWidth(getContext()), 285, 540);
                        } else if (position == 2) {
                            LookPhotoActivity.newInstance(getActivity(), ObjectUtils.parseString(imgTop.getTag(R.id.tag_glide)));
                        }
                        return true;
                    });
                    InputMethodUtil.hideKeyboard(getActivity());
                    sweetSheet.show();
                }
            });

            customInitListener(this);

        }

    }


}


