package com.yaoguang.appcommon.phone.my.companyinfo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.appcommon.R;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragment;
import com.yaoguang.appcommon.common.base.LookPhotoActivity;
import com.yaoguang.appcommon.common.finalrequest.CommpanyRequest;
import com.yaoguang.appcommon.common.utils.PhoneUtils;
import com.yaoguang.appcommon.common.view.EditTextFragment;
import com.yaoguang.appcommon.phone.my.companyinfo.modifybrief.ModifyBriefFragment;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.lib.common.displaymetrics.ProportionUtils;
import com.yaoguang.lib.common.imagepicker.ImagePickerUtils;
import com.yaoguang.lib.qinui.QiNiuManager;
import com.yaoguang.interactor.common.my.companyoinfo.CompanyInfoContact;
import com.yaoguang.interactor.common.my.companyoinfo.CompanyInfoPresenterImpl;

import java.util.ArrayList;


/**
 * 商户管理
 * Created by zhongjh on 2017/7/13.
 */
public abstract class BaseCompanyInfoFragment<T> extends BaseFragment implements CompanyInfoContact.CompanyInfoView<T> {

    public InitialView mInitialView;
    CompanyInfoContact.CompanyInfoPresenter mPresenter;


    public static final int REQUESTCODE_REMARK = 0;
    public static final int REQUESTCODE_PHOTO = 0;
    public static final int REQUESTCODE_LOG = 1;

    public BaseCompanyInfoFragment(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        this.setArguments(bundle);
    }

    /**
     * 用户类型
     */
    protected abstract String getCodeType();

    /**
     * 自定义事件
     *
     * @param viewHolder 控件集合
     */
    protected abstract void initListener(ViewHolder viewHolder);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_info, container, false);
        mInitialView = new InitialView(view);
        mPresenter = new CompanyInfoPresenterImpl<>(this, getCodeType());
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String value = data.getString(EditTextFragment.VALUE);
            switch (requestCode) {
                case REQUESTCODE_REMARK:
                    mPresenter.modifyRemark(value);
                    break;
            }
            mPresenter.initData();
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                switch (requestCode) {
                    case CommpanyRequest.REQUESTCODE_COMPANYINFO + REQUESTCODE_PHOTO:
                        //顶部图片
                        ArrayList<ImageItem> images = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                        if (images.size() <= 0) {
                            Toast.makeText(BaseApplication.getInstance().getApplicationContext(),"没有数据", Toast.LENGTH_SHORT).show();
                        } else {
                            QiNiuManager.getInstance().upload(getActivity(), getContext(), images.get(0).path, true, new QiNiuManager.ComeBack() {
                                @Override
                                public void result(boolean result, String url) {
                                    if (result) {
                                        //更新
                                        mPresenter.modifyPhoto(url);
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
                    case CommpanyRequest.REQUESTCODE_COMPANYINFO + REQUESTCODE_LOG:
                        //LOGO图片
                        ArrayList<ImageItem> images2 = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                        if (images2.size() <= 0) {
                            Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "没有数据", Toast.LENGTH_SHORT).show();
                        } else {
                            QiNiuManager.getInstance().upload(getActivity(), getContext(), images2.get(0).path, true, new QiNiuManager.ComeBack() {
                                @Override
                                public void result(boolean result, String url) {
                                    if (result) {
                                        //更新
                                        mPresenter.modifyLog(url);
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
                Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class InitialView {

        public ViewHolder viewHolder;

        //上传图片重试次数
        int againNum = 0;

        public final View rootView;

        public InitialView(View rootView) {
            this.rootView = rootView;
            viewHolder = new ViewHolder(rootView);
            initUI();
            initListener();
        }

        private void initUI() {

            initToolbarNav(viewHolder.toolbar, getArguments().getString("title"), -1, null);

            //动态设置convenientBanner高度
            //获取屏幕的宽度
            int screenWidth = DisplayMetricsSPUtils.getScreenWidth(getContext());
            //计算BGABanner的应有高度
            int viewHeight = ProportionUtils.getHeight(160, 360, screenWidth);
            //设置BGABanner的宽高属性
            ViewGroup.LayoutParams banner_params = viewHolder.imgPhoto.getLayoutParams();
            banner_params.width = screenWidth;
            banner_params.height = viewHeight;
            viewHolder.imgPhoto.setLayoutParams(banner_params);
            ViewGroup.LayoutParams banner_params2 = viewHolder.rlPhoto.getLayoutParams();
            banner_params2.width = screenWidth;
            banner_params2.height = viewHeight;

            viewHolder.rlPhoto.setLayoutParams(banner_params2);
        }

        public float getRawSize(int unit, float value) {
            Resources res = getResources();
            return TypedValue.applyDimension(unit, value, res.getDisplayMetrics());
        }

        private void initListener() {
            BaseCompanyInfoFragment.this.initListener(viewHolder);
            //打电话
            viewHolder.imgCall.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    PhoneUtils.nodeCallPhone(getActivity(), getFragmentManager(), new String[]{viewHolder.tvValuePhone.getText().toString()});
                }
            });
            //商户照片
            viewHolder.imgPhoto.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    SweetSheet sweetSheet = new SweetSheet(viewHolder.flMain);
                    sweetSheet.setMenuList(R.menu.sheet_head_portrait_phone);
                    sweetSheet.setTitle("请选择商户照片");
                    RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
                    recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(250));
                    sweetSheet.setDelegate(recyclerViewDelegate);
                    sweetSheet.setBackgroundEffect(new DimEffect(4));
                    sweetSheet.setOnMenuItemClickListener((position, menuEntity) -> {
                        //设置裁剪参数
                        if (position == 0) {
                            //拍照
                            ImagePickerUtils.startActivityForResult(getActivity(), BaseCompanyInfoFragment.this, true, CommpanyRequest.REQUESTCODE_COMPANYINFO + REQUESTCODE_PHOTO, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), 160, 360);
                        } else if (position == 1) {
                            //选择
                            ImagePickerUtils.startActivityForResult(getActivity(), BaseCompanyInfoFragment.this, true, CommpanyRequest.REQUESTCODE_COMPANYINFO + REQUESTCODE_PHOTO, false, DisplayMetricsSPUtils.getScreenWidth(getContext()), 160, 360);
                        } else if (position == 2) {
                            LookPhotoActivity.newInstance(getActivity(), ObjectUtils.parseString(viewHolder.imgPhoto.getTag(R.id.tag_glide)));
                        }
                        return true;
                    });
                    sweetSheet.show();
                }
            });
            //商户Logo
            viewHolder.imgLog.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    SweetSheet sweetSheet = new SweetSheet(viewHolder.flMain);
                    sweetSheet.setMenuList(R.menu.sheet_head_portrait_phone);
                    sweetSheet.setTitle("请选择商户Logo");
                    RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
                    recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(250));
                    sweetSheet.setDelegate(recyclerViewDelegate);
                    sweetSheet.setBackgroundEffect(new DimEffect(4));
                    sweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
                        @Override
                        public boolean onItemClick(int position, MenuEntity menuEntity) {
                            if (position == 0) {
                                //拍照
                                ImagePickerUtils.startActivityForResult(getActivity(), BaseCompanyInfoFragment.this, true, CommpanyRequest.REQUESTCODE_COMPANYINFO + REQUESTCODE_LOG, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
                            } else if (position == 1) {
                                //选择
                                ImagePickerUtils.startActivityForResult(getActivity(), BaseCompanyInfoFragment.this, true, CommpanyRequest.REQUESTCODE_COMPANYINFO + REQUESTCODE_LOG, false, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
                            } else if (position == 2) {
                                LookPhotoActivity.newInstance(getActivity(), ObjectUtils.parseString(viewHolder.imgLog.getTag(R.id.tag_glide)));
                            }
                            return true;
                        }
                    });
                    InputMethodUtil.hideKeyboard(getActivity());
                    sweetSheet.show();
                }
            });
//            //查看执照 作废，迁移到第二个详情界面
//            viewHolder.rlCodePhoto.setOnClickListener(new NoDoubleClickListener() {
//                @Override
//                public void onNoDoubleClick(View v) {
//                    //查看如果是多张，就弹出选择框，如果是单张，就直接查看照片
//                    if (viewHolder.rlCodePhoto.getTag().toString().contains(",") && !TextUtils.isEmpty(viewHolder.rlCodePhoto.getTag().toString())) {
//                        final String[] codePhoto = viewHolder.rlCodePhoto.getTag().toString().split(",");
//                        List<MenuEntity> menuEntities = new ArrayList<>();
//                        MenuEntity menuEntity = new MenuEntity();
//                        menuEntity.title = "正面身份证";
//                        menuEntities.add(menuEntity);
//                        MenuEntity menuEntity2 = new MenuEntity();
//                        menuEntity2.title = "反面身份证";
//                        menuEntities.add(menuEntity2);
//                        SweetSheet sweetSheet = new SweetSheet(viewHolder.flMain);
//                        sweetSheet.setMenuList(menuEntities);
//                        sweetSheet.setTitle("请选择身份证");
//                        RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
//                        recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(200));
//                        sweetSheet.setDelegate(recyclerViewDelegate);
//                        sweetSheet.setBackgroundEffect(new DimEffect(4));
//                        sweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onItemClick(int position, MenuEntity menuEntity) {
//                                if (position == 0) {
//                                    LookPhotoActivity.newInstance(getActivity(), codePhoto[0]);
//                                } else if (position == 1) {
//                                    LookPhotoActivity.newInstance(getActivity(), codePhoto[1]);
//                                }
//                                return true;
//                            }
//                        });
//                        sweetSheet.show();
//                    } else {
//                        LookPhotoActivity.newInstance(getActivity(), ObjectUtils.parseString(viewHolder.rlCodePhoto.getTag()));
//                    }
//                }
//            });
            //商户简介
            viewHolder.llRemark.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    startForResult(new ModifyBriefFragment().newInstance(viewHolder.tvValueRemark.getText().toString()), BaseCompanyInfoFragment.REQUESTCODE_REMARK);
                }
            });
        }
    }

    public static class ViewHolder {
        public View rootView;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public ImageView imgPhoto;
        public RelativeLayout rlPhoto;
        public ImageView imgLog;
        public TextView tvCompanyName;
        public TextView btnType0;
        public TextView btnType1;
        public TextView btnTypeShipper0;
        public TextView btnTypeShipper1;
        public TextView tvAddressTitle;
        public TextView tvValueAddress;
        public RelativeLayout rlAddress;
        public TextView tvUserTitle;
        public TextView tvValueUser;
        public RelativeLayout rlUser;
        public TextView tvPhoneTitle;
        public TextView tvValuePhone;
        public ImageView imgCall;
        public RelativeLayout rlMobile;
        public TextView tvCodePhotoTitle;
        public RelativeLayout rlCodePhoto;
        public TextView tvRemarkTitle;
        public TextView tvValueRemark;
        public LinearLayout llRemark;
        public FrameLayout flMain;
        public ImageView imgAddress;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            this.imgPhoto = (ImageView) rootView.findViewById(R.id.imgPhoto);
            this.rlPhoto = (RelativeLayout) rootView.findViewById(R.id.rlPhoto);
            this.imgLog = (ImageView) rootView.findViewById(R.id.imgLog);
            this.tvCompanyName = (TextView) rootView.findViewById(R.id.tvCompanyName);
            this.btnType0 = (TextView) rootView.findViewById(R.id.btnType0);
            this.btnType1 = (TextView) rootView.findViewById(R.id.btnType1);
            this.btnTypeShipper0 = (TextView) rootView.findViewById(R.id.btnTypeShipper0);
            this.btnTypeShipper1 = (TextView) rootView.findViewById(R.id.btnTypeShipper1);
            this.tvAddressTitle = (TextView) rootView.findViewById(R.id.tvAddressTitle);
            this.tvValueAddress = (TextView) rootView.findViewById(R.id.tvValueAddress);
            this.rlAddress = (RelativeLayout) rootView.findViewById(R.id.rlAddress);
            this.tvUserTitle = (TextView) rootView.findViewById(R.id.tvUserTitle);
            this.tvValueUser = (TextView) rootView.findViewById(R.id.tvValueUser);
            this.rlUser = (RelativeLayout) rootView.findViewById(R.id.rlUser);
            this.tvPhoneTitle = (TextView) rootView.findViewById(R.id.tvPhoneTitle);
            this.tvValuePhone = (TextView) rootView.findViewById(R.id.tvValuePhone);
            this.imgCall = (ImageView) rootView.findViewById(R.id.imgCall);
            this.rlMobile = (RelativeLayout) rootView.findViewById(R.id.rlMobile);
            this.tvCodePhotoTitle = (TextView) rootView.findViewById(R.id.tvCodePhotoTitle);
            this.rlCodePhoto = (RelativeLayout) rootView.findViewById(R.id.rlCodePhoto);
            this.tvRemarkTitle = (TextView) rootView.findViewById(R.id.tvRemarkTitle);
            this.tvValueRemark = (TextView) rootView.findViewById(R.id.tvValueRemark);
            this.llRemark = (LinearLayout) rootView.findViewById(R.id.llRemark);
            this.flMain = (FrameLayout) rootView.findViewById(R.id.flMain);
            this.imgAddress = (ImageView) rootView.findViewById(R.id.imgAddress);
        }

    }
}
