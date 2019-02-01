package com.yaoguang.appcommon.phone.contact2.contactaddmode;

import android.databinding.ViewDataBinding;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.databinding.FragmentContactAddModeBinding;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;

/**
 * 选择添加方式
 * Created by zhongjh on 2018/4/12.
 */

public abstract class BaseContactAddModeFragment<B extends ViewDataBinding> extends BaseFragmentDataBind<FragmentContactAddModeBinding>{



    @Override
    public int getLayoutId() {
        return R.layout.fragment_contact_add_mode;
    }

    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "添加", -1, null);
    }

    @Override
    public void initListener() {
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
