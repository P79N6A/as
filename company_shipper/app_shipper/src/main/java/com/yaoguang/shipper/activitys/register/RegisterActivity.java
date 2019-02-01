package com.yaoguang.shipper.activitys.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.appcommon.phone.register.BaseRegisterActivity;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.greendao.entity.UserOwner;
import com.yaoguang.shipper.R;
import com.yaoguang.shipper.activitys.MainActivity;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhongjh
 * on 2017/6/8 0008.
 */
public class RegisterActivity extends BaseRegisterActivity<UserOwner> {

    private static final String FLAG_CACHE = "Register_Cache";
    UserOwner userOwner = new UserOwner();
    private DialogHelper mDialogHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SupportFragment firstFragment = findFragment(RegisterDoorwayFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = RegisterDoorwayFragment.newInstance();
            mFragments[SECOND] = RegisterDetailFragment.newInstance();
            mFragments[THIRD] = RegisterEndFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.findFragmentByTag()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(RegisterDetailFragment.class);
            mFragments[THIRD] = findFragment(RegisterEndFragment.class);
        }
    }

    public static void newInstance(Activity activity) {
        //(A启动B A不动 B从下面滑入) 第一个参数是进入activity的动画，第二个参数是当前activity退出时的动画。
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.up_in, R.anim.up_out);
    }

    @Override
    protected SupportFragment getRegisterOneFragment() {
        return new RegisterDoorwayFragment();
    }

    @Override
    public void toMainActivity() {
        MainActivity.newInstance(this, null);
    }

    @Override
    public void toLoginActivity() {
        this.finish();
    }

    @Override
    public UserOwner getModel() {
        return userOwner;
    }

    @Override
    public void saveCache() {
        String model = new Gson().toJson(getModel());
        SPUtils.getInstance().put(FLAG_CACHE, model);
    }

    @Override
    public UserOwner getCache() {
        String cache = SPUtils.getInstance().getString(FLAG_CACHE);
        if (cache != null) {
            return new Gson().fromJson(cache, UserOwner.class);
        }
        return null;
    }

    @Override
    public void initUICustom(InitialView initialView) {
        initialView.tvTitle.setText("商户注册");
    }


    @Override
    public void nextPage() {
        hideKeyboard();
        if (node == 1) {
            node = node + 1;
            showHideFragment(mFragments[THIRD], mFragments[SECOND]);
        } else if (node == 0) {
            node = node + 1;
            showHideFragment(mFragments[SECOND], mFragments[FIRST]);
        }
        mInitialView.registerNode.setVisibility(View.VISIBLE);
        if (node == 1) {
            mInitialView.registerNode.setImageResource(R.drawable.ic_txzl_01);
        }
        if (node == 2) {
            mInitialView.registerNode.setImageResource(R.drawable.ic_zcwc_01);
        }

    }

    @Override
    protected void onDestroy() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        super.onDestroy();
    }

    @Override
    public void onBack() {
        hideKeyboard();
        if (node == 2) {
            onBackPressed();
        } else if (node == 1) {
            node = node - 1;
            mInitialView.registerNode.setVisibility(View.GONE);
            showHideFragment(mFragments[FIRST], mFragments[SECOND
                    ]);
        } else if (node == 0) {
            if (mDialogHelper == null)
                mDialogHelper = new DialogHelper(this, "是否放弃注册", "这会清空已上传的资料", "确认", "取消", new CommonDialog.Listener() {

                    @Override
                    public void ok(String msg) {
                        mDialogHelper.hideDialog();
                        onBackPressed();
                    }

                    @Override
                    public void cancel() {

                    }

                });
            mDialogHelper.show();
        }
    }

}
