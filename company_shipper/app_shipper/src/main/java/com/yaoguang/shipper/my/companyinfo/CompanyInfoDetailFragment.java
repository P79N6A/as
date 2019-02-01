package com.yaoguang.shipper.my.companyinfo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.appcommon.common.base.LookPhotoActivity;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.databinding.FragmentCompanyInfoDetailBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 商户认证信息
 * Created by zhongjh on 2017/12/15.
 */
public class CompanyInfoDetailFragment extends BaseFragmentDataBind<FragmentCompanyInfoDetailBinding> {

    /**
     * @param userOwner 传递的对象
     */
    public static CompanyInfoDetailFragment newInstance(UserOwner userOwner) {
        Bundle args = new Bundle();
        args.putParcelable("userOwner", userOwner);
        CompanyInfoDetailFragment fragment = new CompanyInfoDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_company_info_detail;
    }

    @Override
    public void init() {
        mToolbarCommonBinding.toolbarTitle.setText("商户管理");
        mToolbarCommonBinding.imgReturn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                pop();
            }
        });

        UserOwner userOwner = getArguments().getParcelable("userOwner");
        mDataBinding.setUserOwner(userOwner);
    }

    @Override
    public void initListener() {
        // 查看执照 作废，迁移到第二个详情界面
        mDataBinding.rlLockPhoto.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                // 查看如果是多张，就弹出选择框，如果是单张，就直接查看照片
                if (mDataBinding.rlLockPhoto.getTag().toString().contains(",") && !TextUtils.isEmpty(mDataBinding.rlLockPhoto.getTag().toString())) {
                    final String[] codePhoto = mDataBinding.rlLockPhoto.getTag().toString().split(",");
                    List<MenuEntity> menuEntities = new ArrayList<>();
                    MenuEntity menuEntity = new MenuEntity();
                    menuEntity.title = "正面身份证";
                    menuEntities.add(menuEntity);
                    MenuEntity menuEntity2 = new MenuEntity();
                    menuEntity2.title = "反面身份证";
                    menuEntities.add(menuEntity2);
                    MenuEntity menuEntity3 = new MenuEntity();
                    menuEntity3.title = "手持身份证";
                    menuEntities.add(menuEntity3);
                    SweetSheet sweetSheet = new SweetSheet(mDataBinding.flMain);
                    sweetSheet.setMenuList(menuEntities);
                    sweetSheet.setTitle("请选择身份证");
                    RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
                    recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(250));
                    sweetSheet.setDelegate(recyclerViewDelegate);
                    sweetSheet.setBackgroundEffect(new DimEffect(4));
                    sweetSheet.setOnMenuItemClickListener((position, menuEntity1) -> {
                        if (position == 0) {
                            LookPhotoActivity.newInstance(getActivity(), codePhoto[0]);
                        } else if (position == 1) {
                            LookPhotoActivity.newInstance(getActivity(), codePhoto[1]);
                        }
                        return true;
                    });
                    sweetSheet.show();
                } else {
                    LookPhotoActivity.newInstance(getActivity(), ObjectUtils.parseString(mDataBinding.rlLockPhoto.getTag()));
                }
            }
        });
    }

    public BasePresenter getPresenter() {
        return null;
    }
}
