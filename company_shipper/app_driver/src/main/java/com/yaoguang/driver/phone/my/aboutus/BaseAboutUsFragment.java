package com.yaoguang.driver.phone.my.aboutus;

import android.graphics.Paint;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.bugly.beta.Beta;
import com.yaoguang.appcommon.html.HtmlFragment;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.appcommon.utils.AppUtils;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragment2;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.IntentUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.permissions.impl.PermissionsManager;
import com.yaoguang.driver.R;

/**
 * 帮助
 * 作者：zhongjh
 * 时间： 2017/8/8
 */
public abstract class BaseAboutUsFragment extends BaseFragment2 {

    private DialogHelper mDialogHelper;
    InitialView mInitialView;

    protected abstract void customInitialView(InitialView initialView);

    protected abstract void startAboutUsDialog();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_aboutus;
    }

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        mInitialView = new InitialView(view);
    }

    @Override
    public void init() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onDestroy() {
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        super.onDestroy();
    }

    protected final class InitialView {

        public ViewHolder viewHolder;

        public InitialView(View view) {
            viewHolder = new ViewHolder(view);

            initToolbarNav(viewHolder.toolbar, "关于货云集", -1, null);

            viewHolder.tvVer.setText("For Android " + "V" + AppUtils.getAppVersionName() + " " + getBeta());
            viewHolder.llMain.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
            viewHolder.tvOfficialWebsite.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            viewHolder.tvLog.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            viewHolder.llCheckUpdate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 使用腾讯的更新功能
                    Beta.checkUpgrade();
                }
            });

            viewHolder.llMobile.setOnClickListener(
                    new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            if (AppClickUtil.isDuplicateClick()) return;

                            if (PermissionsManager.getInstance().checkCallPhonePermissions(getActivity(), true)) {
                                if (!TextUtils.isEmpty(viewHolder.tvMobile.getText().toString())) {
                                    if (mDialogHelper == null)
                                        mDialogHelper = new DialogHelper(getActivity(), "拨打电话", "拨打号码：" + viewHolder.tvMobile.getText().toString(), "拨打", "取消", false, new CommonDialog.Listener() {
                                            @Override
                                            public void ok(String content) {
                                                getActivity().startActivity(IntentUtils.getCallIntent(viewHolder.tvMobile.getText().toString()));
                                            }

                                            @Override
                                            public void cancel() {

                                            }
                                        });
                                    mDialogHelper.show();
                                } else {
                                    Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "抱歉，没有此联系人电话", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "请开拨打电话权限", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
//            viewHolder.llOfficialWebsite.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Uri uri = Uri.parse(viewHolder.tvOfficialWebsite.getText().toString());
//                    startActivity(new Intent(Intent.ACTION_VIEW,uri));
//                }
//            });
            viewHolder.llOfficialWebsite.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    start(HtmlFragment.newInstance("关于货云集", viewHolder.tvOfficialWebsite.getText().toString()), SINGLETOP);
                }
            });
            viewHolder.tvLog.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (AppClickUtil.isDuplicateClick()) return;

                    startAboutUsDialog();
                }
            });
            customInitialView(this);

        }

    }

    protected abstract String getBeta();

    public static class ViewHolder {
        public View rootView;
        public ImageView imgReturn;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public ImageView imgApp;
        public TextView tvVer;
        public TextView tvLog;
        public TextView tvAppName;
        public TextView tvMobile;
        public LinearLayout llMobile;
        public LinearLayout llCheckUpdate;
        public TextView tvOfficialWebsite;
        public LinearLayout llOfficialWebsite;
        public LinearLayout llMain;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.imgReturn = (ImageView) rootView.findViewById(R.id.imgReturn);
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            this.imgApp = (ImageView) rootView.findViewById(R.id.imgApp);
            this.tvVer = (TextView) rootView.findViewById(R.id.tvVer);
            this.tvLog = (TextView) rootView.findViewById(R.id.tvLog);
            this.tvAppName = (TextView) rootView.findViewById(R.id.tvAppName);
            this.tvMobile = (TextView) rootView.findViewById(R.id.tvMobile);
            this.llMobile = (LinearLayout) rootView.findViewById(R.id.llMobile);
            this.llCheckUpdate = (LinearLayout) rootView.findViewById(R.id.llCheckUpdate);
            this.tvOfficialWebsite = (TextView) rootView.findViewById(R.id.tvOfficialWebsite);
            this.llOfficialWebsite = (LinearLayout) rootView.findViewById(R.id.llOfficialWebsite);
            this.llMain = (LinearLayout) rootView.findViewById(R.id.llMain);
        }

    }
}
