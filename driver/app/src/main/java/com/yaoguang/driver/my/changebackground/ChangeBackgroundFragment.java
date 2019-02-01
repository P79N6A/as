package com.yaoguang.driver.my.changebackground;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.common.Glide.impl.GlideManager;
import com.yaoguang.common.appcommon.utils.AppUtils;
import com.yaoguang.common.common.AppClickUtil;
import com.yaoguang.common.common.ConvertUtils;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.common.common.imagepicker.ImagePickerUtils;
import com.yaoguang.common.qinui.QiNiuManager;
import com.yaoguang.driver.R;
import com.yaoguang.driver.base.DataBindingFragment;
import com.yaoguang.driver.base.baseview.BasePresenter;
import com.yaoguang.driver.databinding.FragmentChangeBackgroundBinding;
import com.yaoguang.driver.my.my.MyFragment;
import com.yaoguang.driver.util.Injection;
import com.yaoguang.greendao.Injections;
import com.yaoguang.greendao.LoginResult;

import java.util.ArrayList;
import java.util.Random;

import static com.yaoguang.appcommon.common.finalrequest.DriverRequest.CHANGE_BACKGROUND_FRAGMENT;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/05
 * 描    述：
 * 修改背景
 * =====================================
 */

public class ChangeBackgroundFragment extends DataBindingFragment<BasePresenter, FragmentChangeBackgroundBinding> implements Toolbar.OnMenuItemClickListener {
    // 用户上传自定义图片
    public static final int EDIT_BACKGROUND_PHOTO = CHANGE_BACKGROUND_FRAGMENT + 2;

    private static final String IMAGE_URL = "image_url";
    private Drawable mLastDrawable;
    private SweetSheet mSweetSheet3;

    private int mRandomNum = -1;

    public static ChangeBackgroundFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, url);
        ChangeBackgroundFragment fragment = new ChangeBackgroundFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void openCamera() {

        // SweetSheet 控件,根据 rl 确认位置
        mSweetSheet3 = new SweetSheet(mDataBinding.flMain);
        //定义一个 CustomDelegate 的 Delegate ,并且设置它的出现动画.
        CustomDelegate customDelegate = new CustomDelegate(true,
                CustomDelegate.AnimationType.DuangLayoutAnimation);
        customDelegate.setContentHeight(ConvertUtils.dp2px(280));
        View view = View.inflate(getContext(), R.layout.view_dialog, null);
        View tvRandom = view.findViewById(R.id.tvRandom);
        tvRandom.setVisibility(View.VISIBLE);
        ((TextView) tvRandom).setText("随机背景");

        //设置自定义视图.
        customDelegate.setCustomView(view);
        //设置代理类
        mSweetSheet3.setDelegate(customDelegate);


        //因为使用了 CustomDelegate 所以mSweetSheet3中的 setMenuList和setOnMenuItemClickListener就没有效果了

        // 随机选择
        view.findViewById(R.id.tvRandom).setOnClickListener(v -> {
            showProgressDialog("正在更新背景");
            hideSweetSheet();
            randomBackground();
        });

        // 取消
        view.findViewById(R.id.tvCancel).setOnClickListener(v -> mSweetSheet3.dismiss());

        // 拍照
        view.findViewById(R.id.tvCamera).setOnClickListener(v -> {
            showProgressDialog("正在更新背景");
            hideSweetSheet();
            mLastDrawable = mDataBinding.photoView.getDrawable();

            ImagePickerUtils.startActivityForResult(getActivity(), ChangeBackgroundFragment.this, false,
                    EDIT_BACKGROUND_PHOTO, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
        });

        // 文件选择
        view.findViewById(R.id.tvAlbumSelect).setOnClickListener(v -> {
            mLastDrawable = mDataBinding.photoView.getDrawable();
            hideSweetSheet();
            ImagePickerUtils.startActivityForResult(getActivity(), ChangeBackgroundFragment.this, false,
                    EDIT_BACKGROUND_PHOTO, false, DisplayMetricsSPUtils.getScreenWidth(getContext()), 1, 1);
        });

        view.findViewById(R.id.tvLook).setVisibility(View.GONE);
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

                switch (requestCode) {
                    case EDIT_BACKGROUND_PHOTO:
                        // 设置背景
                        String userBackgroundUrl = images.get(0).path;

                        ULog.i("onActivityResult");
                        uploadBackground(userBackgroundUrl);
                        break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadBackground(String userBackgroundUrl) {
        ULog.i("uploadBackground");
        QiNiuManager.getInstance().simpleUpload(getContext(), userBackgroundUrl, new QiNiuManager.ComeBack() {
            @Override
            public void result(boolean result, String url) {
                if (getContext() == null || AppClickUtil.isDuplicateClick()) return;

                ULog.i("setBackgroundPicture");
                LoginResult loginResult = Injection.provideDriverRepository(getContext()).getLoginResult();
                loginResult.getUserInfo().setBackgroundPicture(url);
                Injection.provideDriverRepository(getContext()).update(loginResult.getUserInfo()).subscribe(value -> {
                    if (value.getState().equals("200")) {
                        setFragmentResult(MyFragment.UPDATE, null);
                        Injection.provideDriverRepository(getContext()).saveOrUpdate(loginResult);

                        // 设置新的背景
                        GlideManager.getInstance()
                                .withSquare(getContext(),
                                        url, mDataBinding.photoView,
                                        R.drawable.ic_ic_tpjzz_b,
                                        R.drawable.ic_jzsb_01,
                                        true);
                    } else {
                        showToast("上传失败");
                        mDataBinding.photoView.setImageDrawable(mLastDrawable);
                    }
                    hideDialog();
                }, throwable -> {
                    mDataBinding.photoView.setImageDrawable(mLastDrawable);
                    showToast("上传头像失败");
                    hideDialog();
                });
            }

            @Override
            public void progress(String speed, int progress) {

            }
        });
    }

    private void randomBackground() {
        if (getContext() == null) return;

        // 设置随机图
        int randomNum = new Random().nextInt(4) + 1;
        if (mRandomNum == randomNum) {
            randomBackground();
            return;
        }
        mRandomNum = randomNum;


        mLastDrawable = mDataBinding.photoView.getDrawable();
        String background = "ic_grbg0";
        String newBackground = background + randomNum;
        ULog.i("new background=" + newBackground);
        int drawableId = getResources().getIdentifier(newBackground, "drawable", AppUtils.getAppPackageName());
        mDataBinding.photoView.setImageResource(drawableId);

        // 转成网址
        String[] web = new String[]{"http://img.huoyunji.com/app_photo_backgroundPicture01_iOS.jpg",
                "http://img.huoyunji.com/app_photo_backgroundPicture02_iOS.jpg",
                "http://img.huoyunji.com/app_photo_backgroundPicture03_iOS.jpg",
                "http://img.huoyunji.com/app_photo_backgroundPicture04_iOS.jpg",
                "http://img.huoyunji.com/app_photo_backgroundPicture05_iOS.jpg"
        };

        LoginResult loginResult = Injection.provideDriverRepository(getContext()).getLoginResult();
        loginResult.getUserInfo().setBackgroundPicture(web[randomNum]);
        Injection.provideDriverRepository(getContext()).update(loginResult.getUserInfo()).subscribe(value -> {
            if (value.getState().equals("200")) {
                setFragmentResult(MyFragment.UPDATE, null);
                Injection.provideDriverRepository(getContext()).saveOrUpdate(loginResult);

                // 设置新的背景
                mDataBinding.photoView.setImageResource(drawableId);
            } else {
                showToast("上传失败");
                mDataBinding.photoView.setImageDrawable(mLastDrawable);
            }
            hideDialog();
        }, throwable -> {
            mDataBinding.photoView.setImageDrawable(mLastDrawable);
            showToast("上传背景失败");
            hideDialog();
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_background;
    }

    @Override
    protected void initView(View view) {
        if (getArguments() == null || getArguments().getString(IMAGE_URL) == null) {
            showToast("没有图片");
            return;
        }
        // toolbar标题
        mToolbarCommonBinding.toolbarTitle.setText("背景图片");
        mToolbarCommonBinding.toolbar.inflateMenu(R.menu.driver_three_dian);
        mToolbarCommonBinding.toolbar.setOnMenuItemClickListener(this);

        // 显示图片
        GlideManager.getInstance().withSquare(getContext(), getArguments().getString(IMAGE_URL), mDataBinding.photoView, R.drawable.ic_ic_tpjzz_b, R.drawable.ic_jzsb_01, true);

        openCamera();
    }

    @Override
    protected void initListener() {
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());
        mDataBinding.photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        mDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }



    private void hideSweetSheet() {
        if (mSweetSheet3 != null) {
            mSweetSheet3.dismiss();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (AppClickUtil.isDuplicateClick()) return false;
        openCamera();
        return false;
    }
}
