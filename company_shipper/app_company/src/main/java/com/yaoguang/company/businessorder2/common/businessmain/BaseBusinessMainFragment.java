package com.yaoguang.company.businessorder2.common.businessmain;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.yaoguang.company.R;
import com.yaoguang.company.businessorder2.common.businessmain.adapter.BaseBusinessMainAdapter;
import com.yaoguang.company.businessorder2.common.businessmain.business.event.BusinessFragmentResultEvent;
import com.yaoguang.company.businessorder2.forwarder.businessmain.business.BusinessOrderFragment;
import com.yaoguang.company.databinding.FragmentBusinessMainBinding;
import com.yaoguang.datasource.company.CompanyOrderDataSource;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhongjh on 2018/11/14.
 */
public abstract class BaseBusinessMainFragment<T> extends BaseFragmentDataBind<FragmentBusinessMainBinding> implements Toolbar.OnMenuItemClickListener {

    protected CompanyOrderDataSource mCompanyOrderDataSource = new CompanyOrderDataSource();
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected T t;
    /**
     * 从列表传递过来的对象(用于进行修改或者查看)
     */
    public static final String BUNDLE_ID = "ID";
    public String mID;
    private int mType;
    protected String mServiceType; // 0 货代 1 拖车
    protected String mOtherService; // 0 装箱，1拆箱

    public BaseBusinessMainAdapter mBaseBusinessMainAdapter;

    public abstract Observable<BaseResponse<T>> detail(String id);


    protected DialogHelper mDialogHelperPop;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mID = args.getString(BUNDLE_ID);
            mType = args.getInt("type");
            mServiceType = args.getString("serviceType");
            t = args.getParcelable("t");
            mOtherService = args.getString("otherService");
        }
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
    }

    /**
     * 通过列表传递id进去
     *
     * @param serviceType  0-货代，1-拖车
     * @param id           id
     * @param otherService 装箱 拆箱
     */
    public BaseBusinessMainFragment(String id, String serviceType, String otherService) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        if (TextUtils.isEmpty(id)) {
            bundle.putInt("type", BusinessOrderFragment.TYPE_MODEL_ADD);
        } else {
            bundle.putInt("type", BusinessOrderFragment.TYPE_MODEL_UPDATE);
        }
        bundle.putString("serviceType", serviceType);
        bundle.putString("otherService", otherService);
        this.setArguments(bundle);
    }

    /**
     * @param t            通过拷贝或者模板传递的ViewForwardOrder
     * @param serviceType  0-货代，1-拖车
     * @param otherService 装箱 拆箱
     */
    public BaseBusinessMainFragment(T t, String serviceType, String otherService) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("t", (Parcelable) t);
        bundle.putInt("type", BusinessOrderFragment.TYPE_MODEL_COPY_OR_TEMP);
        bundle.putString("serviceType", serviceType);
        bundle.putString("otherService", otherService);
        this.setArguments(bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_main;
    }

    @Override
    public void init() {
        if (mType == BusinessOrderFragment.TYPE_MODEL_UPDATE) {
            if (mServiceType.equals("0"))
                initToolbarNav(mToolbarCommonBinding.toolbar, "编辑货代工作单", R.menu.menu_business_main, this);
            else
                initToolbarNav(mToolbarCommonBinding.toolbar, "编辑拖车工作单", R.menu.menu_business_main, this);
            // 如果是编辑的，就有两个tab
            mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setText("基本信息"));
            mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setText("动态"));
            detail(mID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<T>>(mCompositeDisposable, this) {

                        @Override
                        public void onSuccess(BaseResponse<T> response) {
                            t = response.getResult();
                            mBaseBusinessMainAdapter = new BaseBusinessMainAdapter(getChildFragmentManager(), mType, mServiceType, mOtherService, response.getResult(), 2);
                            mDataBinding.viewPager.setAdapter(mBaseBusinessMainAdapter);
                            mDataBinding.viewPager.setOffscreenPageLimit(3);
                            mDataBinding.tabLayout.setupWithViewPager(mDataBinding.viewPager);
                        }

                    });
        } else if (mType == BusinessOrderFragment.TYPE_MODEL_ADD || mType == BusinessOrderFragment.TYPE_MODEL_COPY_OR_TEMP) {
            if (mServiceType.equals("0"))
                initToolbarNav(mToolbarCommonBinding.toolbar, "新建货代工作单", -1, null);
            else
                initToolbarNav(mToolbarCommonBinding.toolbar, "新建拖车工作单", -1, null);
            // 直接隐藏tab
            mDataBinding.flTab.setVisibility(View.GONE);
            // 如果是模板，直接赋值
            if (mType == BusinessOrderFragment.TYPE_MODEL_COPY_OR_TEMP)
                mBaseBusinessMainAdapter = new BaseBusinessMainAdapter(getChildFragmentManager(), mType, mServiceType, mOtherService, t, 1);
            else
                mBaseBusinessMainAdapter = new BaseBusinessMainAdapter(getChildFragmentManager(), mType, mServiceType, mOtherService, null, 1);
            mDataBinding.viewPager.setAdapter(mBaseBusinessMainAdapter);
            mDataBinding.viewPager.setOffscreenPageLimit(2);
        }
    }

    @Override
    public void initListener() {

        //返回事件
        mToolbarCommonBinding.imgReturn.setOnClickListener(v -> backPressedSupport());
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            EventBus.getDefault().post(new BusinessFragmentResultEvent(requestCode, data));
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        backPressedSupport();
        return true;
    }


    /**
     * 返回之前提示是否需要确认返回，新增和编辑都是不同的提示
     */
    private void backPressedSupport() {
        if (mDialogHelperPop == null)
            mDialogHelperPop = new DialogHelper(getContext(), "提示", "这将不保存工作单数据,确定退出?","确定","取消", new CommonDialog.Listener() {
                @Override
                public void ok(String msg) {
                    mDialogHelperPop.hideDialog();
                    pop();
                }

                @Override
                public void cancel() {

                }

            });
        mDialogHelperPop.show();
    }


}
