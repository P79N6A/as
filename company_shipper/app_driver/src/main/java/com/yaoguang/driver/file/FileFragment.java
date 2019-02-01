package com.yaoguang.driver.file;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yaoguang.driver.R;
import com.yaoguang.driver.databinding.FragmentFileBinding;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.file.FileUtils;

import java.io.File;

import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;

/**
 * 文件fragment
 * Created by zhongjh on 2018/5/18.
 */
public class FileFragment extends BaseFragmentDataBind<FragmentFileBinding> implements Toolbar.OnMenuItemClickListener{

    private String mFilePath;
    private static final int REQUEST_SHARE_FILE_CODE = 120;

    public static FileFragment newInstance(String file) {
        Bundle args = new Bundle();
        args.putString("file", file);
        FileFragment fileFragment = new FileFragment();
        fileFragment.setArguments(args);
        return fileFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_file;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDataBinding.mSuperFileView.onStopDisplay();
    }

    @Override
    public void init() {
        if (getArguments() != null)
            mFilePath = getArguments().getString("file");

        initToolbarNav(mToolbarCommonBinding.toolbar, "对账单文件", R.menu.menu_file, this);

        mDataBinding.mSuperFileView.setOnGetFilePathListener(mSuperFileView -> mDataBinding.mSuperFileView.displayFile(new File(mFilePath)));
        mDataBinding.mSuperFileView.show();
    }

    @Override
    public void initListener() {
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // 分享文件
        new Share2.Builder(_mActivity)
                .setContentType(ShareContentType.FILE)
                .setShareFileUri(FileUtils.getUriForFile(getContext(),new File(mFilePath)))
                .setTitle("分享文件")
                .setOnActivityResult(REQUEST_SHARE_FILE_CODE)
                .build()
                .shareBySystem();
        return false;
    }
}
