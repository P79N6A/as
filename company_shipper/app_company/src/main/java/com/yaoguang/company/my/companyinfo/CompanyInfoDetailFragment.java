package com.yaoguang.company.my.companyinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.yaoguang.appcommon.common.base.LookPhotoActivity;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentCompanyInfoDetailBinding;
import com.yaoguang.greendao.entity.UserApply;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

/**
 * 商户认证信息
 * Created by zhongjh on 2017/12/15.
 */
public class CompanyInfoDetailFragment extends BaseFragmentDataBind<FragmentCompanyInfoDetailBinding> {

    /**
     * @param userApply 传递的对象
     */
    public static CompanyInfoDetailFragment newInstance(UserApply userApply) {
        Bundle args = new Bundle();
        args.putParcelable("userApply", userApply);
        CompanyInfoDetailFragment fragment = new CompanyInfoDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_company_info_detail;
    }


    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
//        mDataBinding = DataBindingUtil.bind(view);
        super.initDataBind(view, inflater);

        if (mToolbarCommonBinding != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "商户管理", -1, null);
        }
    }

    @Override
    public void init() {
        UserApply userApply = getArguments().getParcelable("userApply");
        mDataBinding.setUserApply(userApply);

    }

    @Override
    public void initListener() {
        //查看执照 作废，迁移到第二个详情界面
        mDataBinding.rlLockPhoto.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //查看营业执照
                LookPhotoActivity.newInstance(getActivity(), ObjectUtils.parseString(mDataBinding.rlLockPhoto.getTag()));
            }
        });
    }

    public BasePresenter getPresenter() {
        return null;
    }
}
