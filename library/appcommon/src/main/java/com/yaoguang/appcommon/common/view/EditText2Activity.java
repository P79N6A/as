package com.yaoguang.appcommon.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yaoguang.appcommon.BaseMainActivity;
import com.yaoguang.appcommon.R;
import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.EmojiFilterUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.widget.edittext.ClearEditText;

import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ren.qinc.edit.PerformEdit;

/**
 * 可单行、多行编辑的文本框
 * Created by zhongjh on 2017/8/22.
 */
public abstract class EditText2Activity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    public static final String VALUE = "VALUE";

    protected String mCodeType;
    String mTitle;
    int mLeftDrawable;
    String mValue;
    boolean mMultiLine;
    int mMaxLength;
    boolean mIsEmpty;
    String mRegexValidator;
    int mInputType;

    protected ViewHolder mViewHolder;
    Observable<BaseResponse<String>> value;
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    PerformEdit mPerformEdit;
    BaseMainActivity.MyDispatchTouchEvent myDispatchTouchEvent;

    protected abstract Observable<BaseResponse<String>> getObservable();


    /**
     * 如果不设置，则为null或者-1
     *
     * @param codeType       客户类型
     * @param title          标题
     * @param leftDrawable   左侧图标
     * @param value          文本内容
     * @param multiLine      是否多行
     * @param maxLength      限制最大长度
     * @param isEmpty        允许为空
     * @param regexValidator 验证码
     * @param inputType      键盘类型
     * @return
     */
    public void newInstance(Activity activity, Class classs, int requestCode, String codeType, String title, int leftDrawable, String value, boolean multiLine, int maxLength, boolean isEmpty, String regexValidator, int inputType) {
        Intent intent = new Intent();
        intent.setClass(activity, classs);
        intent.putExtra("codeType", codeType);
        intent.putExtra("title", title);
        intent.putExtra("leftDrawable", leftDrawable);
        intent.putExtra("value", value);
        intent.putExtra("multiLine", multiLine);
        intent.putExtra("isEmpty", isEmpty);
        intent.putExtra("maxLength", maxLength);
        intent.putExtra("regexValidator", regexValidator);
        intent.putExtra("inputType", inputType);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edittext);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mCodeType = bundle.getString("codeType");
            mTitle = bundle.getString("title");
            mLeftDrawable = bundle.getInt("leftDrawable");
            mValue = bundle.getString("value");
            mMultiLine = bundle.getBoolean("multiLine");
            mMaxLength = bundle.getInt("maxLength");
            mIsEmpty = bundle.getBoolean("isEmpty");
            mRegexValidator = bundle.getString("regexValidator");
            mInputType = bundle.getInt("inputType", -1);
        }

        onCreateView();
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

    public void onCreateView() {

        mViewHolder = new ViewHolder();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbarNav(toolbar, mTitle, R.menu.menu_edittext, EditText2Activity.this);

        //赋值文本
        mViewHolder.etValue.setText(mValue);

        //是否设置多行
        if (!mMultiLine) {
            mViewHolder.etValue.setSingleLine();
        }

        //当前文本长度
        mViewHolder.tvNumber.setText(ObjectUtils.parseString(mViewHolder.etValue.getText().toString().length()));
        //最大长度
        mViewHolder.tvNumberSum.setText(ObjectUtils.parseString(mMaxLength));
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

        initEditText();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    private void initEditText() {
        //过滤表情
        mViewHolder.etValue.setFilters(new InputFilter[]{new EmojiFilterUtils(), new InputFilter.LengthFilter(mMaxLength)});
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void initListener() {
        //提交事件
        mViewHolder.btnSubmit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //判断不能为空
                if (!mIsEmpty && TextUtils.isEmpty(mViewHolder.etValue.getText().toString())) {
                    Toast.makeText(BaseApplication.getInstance(), "不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                //判断格式
                if (!TextUtils.isEmpty(mRegexValidator) && !Pattern.matches(mRegexValidator, mViewHolder.etValue.getText().toString())) {
                    Toast.makeText(BaseApplication.getInstance(), "格式不正确", Toast.LENGTH_LONG).show();
                    return;
                }
                value = getObservable();
                if (value != null) {
                    value.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable, EditText2Activity.this) {

                                @Override
                                public void onSuccess(BaseResponse<String> response) {
                                    Intent intent = new Intent();
                                    intent.putExtra(VALUE, mViewHolder.etValue.getText().toString());
                                    EditText2Activity.this.setResult(RESULT_OK, intent);
                                    EditText2Activity.this.finish();
                                }

                            });
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(VALUE, mViewHolder.etValue.getText().toString());
                    EditText2Activity.this.setResult(RESULT_OK, intent);
                    EditText2Activity.this.finish();
                }
            }
        });
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
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public class ViewHolder {
        public View rootView;
        public AppCompatButton btnSubmit;
        public ImageView imgReturn;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public ClearEditText etValue;
        public TextView tvNumber;
        public TextView tvNumberSum;

        public ViewHolder() {
            this.btnSubmit = (AppCompatButton) findViewById(R.id.btnSubmit);
            this.imgReturn = (ImageView) findViewById(R.id.imgReturn);
            this.toolbar_title = (TextView) findViewById(R.id.toolbar_title);
            this.toolbar = (Toolbar) findViewById(R.id.toolbar);
            this.etValue = (ClearEditText) findViewById(R.id.etValue);
            this.tvNumber = (TextView) findViewById(R.id.tvNumber);
            this.tvNumberSum = (TextView) findViewById(R.id.tvNumberSum);
        }

    }

}
