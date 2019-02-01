package com.yaoguang.company.my.usermanagement.list;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.yaoguang.company.R;
import com.yaoguang.company.databinding.FragmentUserManagementBinding;
import com.yaoguang.company.my.usermanagement.list.adapter.UserManagementAdapter;
import com.yaoguang.company.my.usermanagement.mode.UserManagementModeFragment;
import com.yaoguang.greendao.entity.company.user.UserCondition;
import com.yaoguang.greendao.entity.company.user.ViewUser;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.base.BaseFragmentListConditionDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenterListCondition;

/**
 * Created by zhongjh on 2018/12/12.
 */
public class UserManagementFragment extends BaseFragmentListConditionDataBind<ViewUser, UserCondition, UserManagementAdapter, FragmentUserManagementBinding>
        implements UserManagementContract.View, Toolbar.OnMenuItemClickListener {

    private UserCondition mUserCondition = new UserCondition(); // 条件
    private UserManagementContract.Presenter mPresenter = new UserManagementPresenter(this);

    /**
     */
    public static UserManagementFragment newInstance() {
        return new UserManagementFragment();
    }

    @Override
    public BaseLoadMoreRecyclerAdapter getAdapter() {
        return new UserManagementAdapter(getContext());
    }

    @Override
    public BasePresenterListCondition getPresenterrConditionList() {
        return mPresenter;
    }

    @Override
    public UserCondition getCondition(boolean isRegain) {
        if (isRegain) {
            if (mDataBinding.cbNoUserAvailable.isChecked()) {
                mUserCondition.setUsable(0);
            } else if (mDataBinding.cbUserAvailable.isChecked()) {
                mUserCondition.setUsable(1);
            } else if (mDataBinding.cbUserAll.isChecked()) {
                mUserCondition.setUsable(-1);
            }
            if (mDataBinding.tvSpinner.getText().equals("登录名")){
                // 登录名
                mUserCondition.setLoginName(mDataBinding.etSpinnerValue.getText().toString());
            }else{
                // 姓名
                mUserCondition.setName(mDataBinding.etSpinnerValue.getText().toString());
            }
        }
        return mUserCondition;
    }

    @Override
    public void setConditionView(UserCondition condition) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_management;
    }

    @Override
    public void init() {
        if (mDataBinding.toolbarCommon != null) {
            initToolbarNav(mToolbarCommonBinding.toolbar, "用户管理", -1, null);
        }
        initSweetSheets(mDataBinding.llSpinner.getId(), mDataBinding.flMain, "选择类型", R.menu.menu_user_management, (position, menuEntity) -> {
            mDataBinding.tvSpinner.setText(menuEntity.title);
            return true;
        });
    }

    @Override
    public void initListener() {
        // region 筛选
        mDataBinding.llScreen.setOnClickListener(v -> {
            if (mDataBinding.flScreenContent.getVisibility() == View.GONE) {
                mDataBinding.imgScreen.setImageResource(R.drawable.ic_shouqi_yellow);
                // 显示
                YoYo.with(Techniques.FadeIn).duration(250).playOn(mDataBinding.flScreenContent);
                mDataBinding.flScreenContent.setVisibility(View.VISIBLE);
            } else {
                setGoneScreenContent(true);
            }
        });

        // 设置一些条件布局的体验优化
        mDataBinding.llScreenContent.setOnTouchListener((v, event) -> true);
        mDataBinding.flScreenContent.setOnTouchListener((v, event) -> {
            setGoneScreenContent(true);
            return true;
        });

        // 重置
        mDataBinding.btnEmpty.setOnClickListener(v -> mDataBinding.cbUserAll.setChecked(true));

        // 确定
        mDataBinding.btnComit.setOnClickListener(v -> {
            // 刷新
            refreshDataAnimation();
            // 隐藏
            setGoneScreenContent(false);
        });

        // 单选事件
        cbUserConditionOnCheckedChangeListener();

        // endregion 筛选

        // 姓名登录名
        mDataBinding.llSpinner.setOnClickListener(v -> showSweetSheets(mDataBinding.llSpinner.getId()));

        // 回车查询
        mDataBinding.etSpinnerValue.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //TODO回车键按下时要执行的操作
                refreshDataAnimation();
            }
            // 隐藏软键盘
            hideKeyboard();
            return false;
        });

        // 跳转编辑
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> startForResult(UserManagementModeFragment.newInstance(((ViewUser) item).getId()), 1));
    }

    /**
     * 船到情况 单选事件
     */
    private void cbUserConditionOnCheckedChangeListener() {
        mDataBinding.cbUserAll.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxUserCondition(buttonView));
        mDataBinding.cbUserAvailable.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxUserCondition(buttonView));
        mDataBinding.cbNoUserAvailable.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxUserCondition(buttonView));
    }

    /**
     * 设置单选（船到情况）
     *
     * @param buttonView 选中的控件
     */
    private void checkBoxUserCondition(CompoundButton buttonView) {
        mDataBinding.cbUserAll.setOnCheckedChangeListener(null);
        mDataBinding.cbUserAvailable.setOnCheckedChangeListener(null);
        mDataBinding.cbNoUserAvailable.setOnCheckedChangeListener(null);
        mDataBinding.cbUserAll.setChecked(false);
        mDataBinding.cbUserAvailable.setChecked(false);
        mDataBinding.cbNoUserAvailable.setChecked(false);
        buttonView.setChecked(true);
        cbUserConditionOnCheckedChangeListener();
    }

    /**
     * 隐藏布局
     *
     * @param isSynchro 是否需要让控件同步当前条件实体
     */
    private void setGoneScreenContent(boolean isSynchro) {
        // 隐藏
        mDataBinding.imgScreen.setImageResource(R.drawable.ic_zhankai_grey);
        // 隐藏
        YoYo.with(Techniques.FadeOut).duration(250).playOn(mDataBinding.flScreenContent);
        mDataBinding.flScreenContent.setVisibility(View.GONE);

        if (isSynchro)
            // 控件赋值当前实体
            switch (mUserCondition.getUsable()) {
                case 0:
                    mDataBinding.cbNoUserAvailable.setChecked(true);
                    break;
                case 1:
                    mDataBinding.cbUserAvailable.setChecked(true);
                    break;
                case -1:
                    mDataBinding.cbUserAll.setChecked(true);
                    break;
            }
    }
}
