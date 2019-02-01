package com.yaoguang.company.my.loginconditionconfiguration.wifimanagement.nearwifimanagement.list.submissionuser;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentSubmissionUserBinding;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserRecentlyUsedWlanLog;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * UserRecentlyUsedWlanLog
 * Created by zhongjh on 2018/12/17.
 */

public class SubmissionUserFragment extends BaseFragmentDataBind<FragmentSubmissionUserBinding> {

    private List<UserRecentlyUsedWlanLog> mUserRecentlyUsedWlanLogs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mUserRecentlyUsedWlanLogs = args.getParcelableArrayList("userRecentlyUsedWlanLogs");
        }
        super.onCreate(savedInstanceState);
    }

    /**
     *
     * @param userRecentlyUsedWlanLogs  数据源
     */
    public static SubmissionUserFragment newInstance(ArrayList<UserRecentlyUsedWlanLog> userRecentlyUsedWlanLogs) {
        SubmissionUserFragment authorizedHistoryFragment = new SubmissionUserFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("userRecentlyUsedWlanLogs", userRecentlyUsedWlanLogs);
        authorizedHistoryFragment.setArguments(bundle);
        return authorizedHistoryFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_submission_user;
    }

    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "提交用户", -1, null);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        SubmissionUserAdapter submissionUserAdapter = new SubmissionUserAdapter();
        submissionUserAdapter.setList(mUserRecentlyUsedWlanLogs);
        RecyclerViewUtils.initPage(mDataBinding.rlView, submissionUserAdapter, null, getContext(), true);
    }

    @Override
    public void initListener() {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
