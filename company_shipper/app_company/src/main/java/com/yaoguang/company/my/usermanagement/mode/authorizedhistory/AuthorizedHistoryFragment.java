package com.yaoguang.company.my.usermanagement.mode.authorizedhistory;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentAuthorizedHistoryBinding;
import com.yaoguang.datasource.company.MemberDataSource;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginAuthHistory;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/12/17.
 */

public class AuthorizedHistoryFragment extends BaseFragmentDataBind<FragmentAuthorizedHistoryBinding> {

    private String mUserId;
    private String mType;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    MemberDataSource mMemberDataSource = new MemberDataSource();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mType = args.getString("type");
            mUserId = args.getString("userId");
        }
        super.onCreate(savedInstanceState);
    }

    /**
     *
     * @param type  0:Web,1:物流 2:Boss
     * @return
     */
    public static AuthorizedHistoryFragment newInstance(String userId,String type) {
        AuthorizedHistoryFragment authorizedHistoryFragment = new AuthorizedHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("userId",userId);
        authorizedHistoryFragment.setArguments(bundle);
        return authorizedHistoryFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_authorized_history;
    }

    @Override
    public void init() {
        initToolbarNav(mToolbarCommonBinding.toolbar, "授权历史", -1, null);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mMemberDataSource.authHistory(mUserId,mType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<UserLoginAuthHistory>>>(mCompositeDisposable, AuthorizedHistoryFragment.this) {

                    @Override
                    public void onSuccess(BaseResponse<List<UserLoginAuthHistory>> response) {
                        AuthorizedHistoryAdapter authorizedHistoryAdapter = new AuthorizedHistoryAdapter();
                        authorizedHistoryAdapter.setList(response.getResult());
                        RecyclerViewUtils.initPage(mDataBinding.rlView, authorizedHistoryAdapter, null, getContext(), true);
                    }

                });
    }

    @Override
    public void initListener() {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
