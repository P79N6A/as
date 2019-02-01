package com.yaoguang.driver.phone.my.personal.changebackground;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.datasource.driver.DriverDataSource;
import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentChangeBackgroundBinding;
import com.yaoguang.driver.phone.my.my.MyFragment;
import com.yaoguang.greendao.entity.Driver;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.appcommon.utils.AppUtils;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.lib.common.imagepicker.ImagePickerUtils;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.qinui.QiNiuManager;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.yaoguang.appcommon.common.finalrequest.DriverRequest.CHANGE_BACKGROUND_FRAGMENT;
import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.UPDATE;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/02/05
 * 描    述：
 * 修改背景
 * <p>
 * update zhongjh
 * data 2018/03/15
 * =====================================
 */

public class ChangeBackgroundFragment extends BaseFragmentDataBind<FragmentChangeBackgroundBinding> implements Toolbar.OnMenuItemClickListener {

    CompositeDisposable mCompositeDisposable;

    // 用户上传自定义图片
    public static final int EDIT_BACKGROUND_PHOTO = CHANGE_BACKGROUND_FRAGMENT + 2;

    private static final String IMAGE_URL = "image_url";
    private Drawable mLastDrawable;
//    private SweetSheet mSweetSheet3;

    private int mRandomNum = -1;

    DriverDataSource mDriverDataSource = new DriverDataSource();

    public static ChangeBackgroundFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, url);
        ChangeBackgroundFragment fragment = new ChangeBackgroundFragment();
        fragment.setArguments(args);
        return fragment;
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
        QiNiuManager.getInstance().upload(getActivity(), getContext(), userBackgroundUrl, true, new QiNiuManager.ComeBack() {
            @Override
            public void result(boolean result, String url) {
                if (getContext() == null || AppClickUtil.isDuplicateClick()) return;

                ULog.i("setBackgroundPicture");
                Driver driver = DataStatic.getInstance().getDriver();
                driver.setBackgroundPicture(url);

                mDriverDataSource.update(driver)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {
                            @Override
                            public void onSuccess(BaseResponse<String> response) {
                                setFragmentResult(UPDATE, null);
                                DataStatic.getInstance().setDriver(driver);

                                // 设置新的背景
                                GlideManager.getInstance()
                                        .withSquare(getContext(),
                                                url, mDataBinding.photoView,
                                                R.drawable.ic_ic_tpjzz_b,
                                                R.drawable.ic_jzsb_01,
                                                true);
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mDataBinding.photoView.setImageDrawable(mLastDrawable);
                            }

                            @Override
                            public void onFail(BaseResponse<String> response) {
                                super.onFail(response);
                                mDataBinding.photoView.setImageDrawable(mLastDrawable);
                            }
                        });
            }

            @Override
            public void progress(String speed, int progress) {

            }
        });
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.clear();
        super.onDestroy();
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

        Driver driver = DataStatic.getInstance().getDriver();
        driver.setBackgroundPicture(web[randomNum]);
        mDriverDataSource.update(driver)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, ChangeBackgroundFragment.this) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        setFragmentResult(UPDATE, null);
                        DataStatic.getInstance().setDriver(driver);

                        // 设置新的背景
                        mDataBinding.photoView.setImageResource(drawableId);

                        hideSweetSheet();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mDataBinding.photoView.setImageDrawable(mLastDrawable);


                        hideSweetSheet();
                    }

                    @Override
                    public void onFail(BaseResponse<String> response) {
                        super.onFail(response);
                        mDataBinding.photoView.setImageDrawable(mLastDrawable);


                        hideSweetSheet();
                    }
                });

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_change_background;
    }

    @Override
    public void init() {

        mCompositeDisposable = new CompositeDisposable();

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

        initSweetSheets(0, mDataBinding.flMain, null, R.menu.change_background_menu, (position, menuEntity) -> {
            switch (position) {
                case 0:
                    randomBackground();
                    break;
                case 1:
                    hideSweetSheet();
                    mLastDrawable = mDataBinding.photoView.getDrawable();
                    ImagePickerUtils.startActivityForResult(getActivity(), ChangeBackgroundFragment.this, true,
                            EDIT_BACKGROUND_PHOTO, true, DisplayMetricsSPUtils.getScreenWidth(getContext()), 190, 360);
                    break;
                case 2:
                    mLastDrawable = mDataBinding.photoView.getDrawable();
                    hideSweetSheet();
                    ImagePickerUtils.startActivityForResult(getActivity(), ChangeBackgroundFragment.this, true,
                            EDIT_BACKGROUND_PHOTO, false, DisplayMetricsSPUtils.getScreenWidth(getContext()), 190, 360);
                    break;
            }
            return false;
        });

    }

    @Override
    public void initListener() {
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> pop());
        mDataBinding.photoView.setOnClickListener(v -> showSweetSheets(0));
        mDataBinding.getRoot().setOnClickListener(v -> showSweetSheets(0));
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }


    private void hideSweetSheet() {
        if (mSweetSheets.get(0) != null) {
            mSweetSheets.get(0).dismiss();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (AppClickUtil.isDuplicateClick()) return false;
        showSweetSheets(0);
        return false;
    }
}
