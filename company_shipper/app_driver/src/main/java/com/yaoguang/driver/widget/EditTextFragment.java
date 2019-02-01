package com.yaoguang.driver.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.yaoguang.appcommon.BaseMainActivity;
import com.yaoguang.appcommon.common.base.BaseBackFragment;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.EmojiFilterUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.RegexValidator;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.driver.R;
import com.yaoguang.widget.edittext.ClearEditText;

import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ren.qinc.edit.PerformEdit;


/**
 * 可单行、多行编辑的文本框
 * Created by zhongjh on 2017/8/21.
 */
public abstract class EditTextFragment extends BaseBackFragment implements Toolbar.OnMenuItemClickListener {

    public static final String VALUE = "VALUE";

    String mOldValue = "";// 旧文本
    String mOldValueMobile = "";// 旧电话

    boolean isMobile = false;//是否固定电话

    boolean mIsActivity;
    protected String mCodeType;
    String mTitle;
    String mTvSubTitle;
    int mLeftDrawable;
    String mValue;
    String mHint;
    private String mAlert;
    boolean mIsMultiLine;
    boolean mIsClear;
    int mMaxLength;
    boolean mIsEmpty;
    String mRegexValidator;
    int mInputType;
    boolean mIsTwoConfirmation;


    protected ViewHolder mViewHolder;
    Observable<BaseResponse<String>> value;
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    PerformEdit mPerformEdit;
    BaseMainActivity.MyDispatchTouchEvent myDispatchTouchEvent;
    private DialogHelper mDialogHelper;

    protected abstract Observable<BaseResponse<String>> getObservable(String value);

    protected abstract BaseBackFragment getFragment();

    /**
     * 固定电话的版本
     * 如果不设置，则为null或者-1
     *
     * @param isActivity 是否activity
     * @param codeType   客户类型
     * @param title      标题
     * @param value      文本内容
     */
    public BaseBackFragment newInstance(boolean isActivity, String codeType, String title, String value) {
        Bundle args = new Bundle();
        args.putBoolean("isActivity", isActivity);
        args.putBoolean("isMobile", true);
        args.putString("codeType", codeType);
        args.putString("title", title);
        args.putString("value", value);
        BaseBackFragment fragment = getFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 如果不设置，则为null或者-1
     *
     * @param isActivity        是否activity
     * @param codeType          客户类型
     * @param title             标题
     * @param tvSubTitle        子标题
     * @param leftDrawable      左侧图标
     * @param value             文本内容
     * @param alert             提示
     * @param hint              输入提示
     * @param isMultiLine       是否多行
     * @param isClear           是否需要删除
     * @param maxLength         限制最大长度
     * @param isEmpty           允许为空
     * @param regexValidator    验证码
     * @param inputType         键盘类型
     * @param isTwoConfirmation 是否需要二次确认           @return
     */
    public BaseBackFragment newInstance(boolean isActivity, String codeType, String title, String tvSubTitle, int leftDrawable, String value, String alert, String hint, boolean isMultiLine, boolean isClear, int maxLength, boolean isEmpty, String regexValidator, int inputType, boolean isTwoConfirmation) {
        Bundle args = new Bundle();
        args.putBoolean("isActivity", isActivity);
        args.putString("codeType", codeType);
        args.putString("title", title);
        args.putString("title", title);
        args.putString("tvSubTitle", tvSubTitle);
        args.putInt("leftDrawable", leftDrawable);
        args.putString("value", value);
        args.putString("hint", hint);
        args.putString("alert", alert);
        args.putBoolean("isMultiLine", isMultiLine);
        args.putBoolean("isClear", isClear);
        args.putBoolean("isEmpty", isEmpty);
        args.putInt("maxLength", maxLength);
        args.putString("regexValidator", regexValidator);
        args.putInt("inputType", inputType);
        args.putBoolean("isTwoConfirmation", isTwoConfirmation);
        BaseBackFragment fragment = getFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mIsActivity = bundle.getBoolean("isActivity");
            isMobile = bundle.getBoolean("isMobile");
            mCodeType = bundle.getString("codeType");
            mTitle = bundle.getString("title");
            mTvSubTitle = bundle.getString("tvSubTitle");
            mLeftDrawable = bundle.getInt("leftDrawable", -1);
            mValue = bundle.getString("value");
            mHint = bundle.getString("hint");
            mAlert = bundle.getString("alert");
            mIsMultiLine = bundle.getBoolean("isMultiLine");
            mIsClear = bundle.getBoolean("isClear");
            mMaxLength = bundle.getInt("maxLength");
            mIsEmpty = bundle.getBoolean("isEmpty");
            mRegexValidator = bundle.getString("regexValidator");
            mInputType = bundle.getInt("inputType", -1);
            mIsTwoConfirmation = bundle.getBoolean("isTwoConfirmation");
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_chexiao) {
            mPerformEdit.undo();
        } else if (i == R.id.action_chongzuo) {
            mPerformEdit.redo();
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edittext, container, false);

        mViewHolder = new ViewHolder(view);


        mViewHolder.cpbSubmit.setEnabled(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        int menu = -1;//菜单
        Toolbar.OnMenuItemClickListener listener = null;//点击事件
        if (isMobile) {
            //隐藏文本
            mViewHolder.etValue.setVisibility(View.GONE);
            mViewHolder.llNumber.setVisibility(View.GONE);
            mViewHolder.llMobile.setVisibility(View.VISIBLE);
            //拆开2个
            String[] values = mValue.split("-");
            if (values.length > 1) {
                mViewHolder.etMobile1.setText(values[0]);
                mViewHolder.etMobile2.setText(values[1]);
                mOldValueMobile = mViewHolder.etMobile1.getText().toString() + "-" + mViewHolder.etMobile2.getText().toString();
            }
        } else {
            //隐藏电话
            mViewHolder.etValue.setVisibility(View.VISIBLE);
            mViewHolder.llNumber.setVisibility(View.VISIBLE);
            mViewHolder.llMobile.setVisibility(View.GONE);

            menu = R.menu.menu_edittext;
            listener = EditTextFragment.this;

            //赋值文本
            mViewHolder.etValue.setText(mValue);
            mOldValue = mViewHolder.etValue.getText().toString();

            //是否设置多行
            if (!mIsMultiLine) {
                mViewHolder.etValue.setSingleLine();
            }

            //是否需要删除按钮
            mViewHolder.etValue.setEnableClear(mIsClear);
            if (mMaxLength > 0) {
                //显示长度出来
                mViewHolder.llNumber.setVisibility(View.VISIBLE);
                //当前文本长度
                mViewHolder.tvNumber.setText(ObjectUtils.parseString(mViewHolder.etValue.getText().toString().length()));
                //最大长度
                mViewHolder.tvNumberSum.setText(ObjectUtils.parseString(mMaxLength));
            } else {
                //隐藏长度
                mViewHolder.llNumber.setVisibility(View.GONE);
            }
            if (mInputType != -1)
                mViewHolder.etValue.setInputType(mInputType);

            //左侧显示的图片
            if (mLeftDrawable != -1) {
                Drawable drawable = getResources().getDrawable(mLeftDrawable);
                drawable.setBounds(10, 0, drawable.getMinimumWidth() + 10, drawable.getMinimumHeight());
                mViewHolder.etValue.setCompoundDrawables(drawable, null, null, null);
            }

            //创建PerformEdit，一定要传入不为空的EditText
            mPerformEdit = new PerformEdit(mViewHolder.etValue);
            mPerformEdit.setDefaultText(mViewHolder.etValue.getText());
        }

        initToolbarNav(toolbar, mTitle, menu, listener);

        initEditText();
        initListener();

        //  设置hint
        if (mHint != null) {
            mViewHolder.etValue.setHint(mHint);
        }

        //  设置子标题
        if (mTvSubTitle != null) {
            mViewHolder.tvSubTitle.setText(mTvSubTitle);
            mViewHolder.tvSubTitle.setVisibility(View.VISIBLE);
        }

        //  设置备注
        if (mAlert != null) {
            mViewHolder.tvAlert.setText(mAlert);
            mViewHolder.tvAlert.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        if (getActivity() instanceof BaseMainActivity)
            ((BaseMainActivity) getActivity()).unregisterMyOnTouchListener(myDispatchTouchEvent);
        mCompositeDisposable.clear();
        if (mDialogHelper != null) {
            mDialogHelper.hideDialog();
        }
        super.onDestroy();
    }

    @Override
    public boolean onBackPressedSupport() {
        breackPop();
        return true;
    }

    private void initEditText() {
        //过滤表情
        if (mMaxLength < 1) {
            mViewHolder.etValue.setFilters(new InputFilter[]{new EmojiFilterUtils()});
        } else {
            mViewHolder.etValue.setFilters(new InputFilter[]{new EmojiFilterUtils(), new InputFilter.LengthFilter(mMaxLength)});
        }
        //记录字数
        mViewHolder.etValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewHolder.tvNumber.setText(ObjectUtils.parseString(s.length()));
            }
        });
    }

    private void initListener() {
        //返回事件
        mViewHolder.imgReturn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                breackPop();
            }
        });
        //触摸事件
        myDispatchTouchEvent = new BaseMainActivity.MyDispatchTouchEvent() {
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                if (ev.getAction() == MotionEvent.ACTION_DOWN) {

                    // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
                    View v = getActivity().getCurrentFocus();
                    if (isShouldHideInput(v, ev)) {
                        hideSoftInput(v.getWindowToken());
                    }
                }
                return false;
            }
        };
        if (getActivity() instanceof BaseMainActivity)
            ((BaseMainActivity) getActivity()).registerMyOnTouchListener(myDispatchTouchEvent);

        //提交事件
        mViewHolder.cpbSubmit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (isMobile) {
                    submitMobile();
                } else {
                    submitText();
                }
            }
        });
    }

    /**
     * 提交手机
     */
    private void submitMobile() {
        String mobile = mViewHolder.etMobile1.getText().toString() + "-" + mViewHolder.etMobile2.getText().toString();
        if (mOldValueMobile.equals(mobile)) {
            pop();
            return;
        }

        if (TextUtils.isEmpty(mViewHolder.etMobile1.getText().toString()) && TextUtils.isEmpty(mViewHolder.etMobile2.getText().toString())) {
            submitPresenter("");
        } else {
            if (RegexValidator.isPhone(mobile)) {
                submitPresenter(mobile);
            } else {
                Toast.makeText(BaseApplication.getInstance(), "电话格式错误", Toast.LENGTH_LONG).show();
            }
        }

    }

    /**
     * 提交文本
     */
    private void submitText() {
        if (mOldValue.equals(mViewHolder.etValue.getText().toString())) {
            pop();
            return;
        }

        // 判断不能为空
        if (!mIsEmpty && TextUtils.isEmpty(mViewHolder.etValue.getText().toString())) {
            Toast.makeText(BaseApplication.getInstance(), "不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        // 如果允许为空，则不需要判断格式
        if (mIsEmpty && !TextUtils.isEmpty(mViewHolder.etValue.getText().toString())) {
            if (!TextUtils.isEmpty(mRegexValidator) && !Pattern.matches(mRegexValidator, mViewHolder.etValue.getText().toString())) {
                Toast.makeText(BaseApplication.getInstance(), "格式不正确", Toast.LENGTH_LONG).show();
                return;
            }
        }
        submitPresenter(mViewHolder.etValue.getText().toString());
    }

    private void submitPresenter(final String tvValue) {
        value = getObservable(tvValue);
        if (value != null) {
            value.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, EditTextFragment.this) {

                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            Toast.makeText(BaseApplication.getInstance(), "保存成功", Toast.LENGTH_LONG).show();
                            if (mIsActivity) {
                                Intent intent = new Intent();
                                intent.putExtra(VALUE, tvValue);
                                getActivity().setResult(RESULT_OK, intent);
                                getActivity().finish();
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString(VALUE, tvValue);
                                setFragmentResult(RESULT_OK, bundle);
                                pop();
                            }
                        }

                    });
        } else {
            if (mIsActivity) {
                Intent intent = new Intent();
                intent.putExtra(VALUE, tvValue);
                getActivity().setResult(RESULT_OK, intent);
                getActivity().finish();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString(VALUE, tvValue);
                setFragmentResult(RESULT_OK, bundle);
                pop();
            }
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 返回前的提示
     */
    private void breackPop() {
        if (mIsTwoConfirmation && !mValue.equals(mViewHolder.etValue.toString())) {
            if (mDialogHelper == null)
                mDialogHelper = new DialogHelper(getContext(), "是否放弃编辑?", "这将不保存您刚编辑的内容", "是的", "我再想想", false, new CommonDialog.Listener() {
                    @Override
                    public void ok(String content) {
                        if (mIsActivity) {
                            getActivity().finish();
                        } else {
                            pop();
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
            mDialogHelper.show();
        } else {
            if (mIsActivity) {
                getActivity().finish();
            } else {
                pop();
            }
        }
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView imgReturn;
        public TextView toolbar_title;
        public TextView tvSubTitle;
        public TextView tvAlert;
        public Toolbar toolbar;
        public EditText etMobile1;
        public EditText etMobile2;
        public LinearLayout llMobile;
        public ClearEditText etValue;
        public TextView tvNumber;
        public TextView tvNumberSum;
        public LinearLayout llNumber;
        public com.dd.CircularProgressButton cpbSubmit;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.imgReturn = (ImageView) rootView.findViewById(R.id.imgReturn);
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.tvSubTitle = (TextView) rootView.findViewById(R.id.tvSubTitle);
            this.toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            this.etMobile1 = (EditText) rootView.findViewById(R.id.etMobile1);
            this.etMobile2 = (EditText) rootView.findViewById(R.id.etMobile2);
            this.llMobile = (LinearLayout) rootView.findViewById(R.id.llMobile);
            this.etValue = (ClearEditText) rootView.findViewById(R.id.etValue);
            this.tvAlert = (TextView) rootView.findViewById(R.id.tvAlert);
            this.tvNumber = (TextView) rootView.findViewById(R.id.tvNumber);
            this.tvNumberSum = (TextView) rootView.findViewById(R.id.tvNumberSum);
            this.llNumber = (LinearLayout) rootView.findViewById(R.id.llNumber);
            this.cpbSubmit = (CircularProgressButton) rootView.findViewById(R.id.cpbSubmit);
        }

    }
}
