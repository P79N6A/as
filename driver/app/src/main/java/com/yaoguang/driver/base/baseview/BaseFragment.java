package com.yaoguang.driver.base.baseview;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yaoguang.common.R;
import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.common.appcommon.utils.ProgressDialogHelper;
import com.yaoguang.driver.util.InstanceUtil;

import java.lang.reflect.ParameterizedType;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * Created by wly on 2017/3/27.
 * 一个基类
 * 直接继承需要自行添加以下代码
 *
 @Nullable
 @Override
 public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
 View view = View.inflate(getContext(), getLayoutId(), null);
 initView(view);
 initListener();
 return view;
 }
 */
public abstract class BaseFragment<P extends BasePresenter> extends SupportFragment {
    protected P mPresenter;
    private ProgressDialogHelper progressDialogHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideKeyboard();
        progressDialogHelper = new ProgressDialogHelper(BaseFragment.this.getContext());
    }

    protected abstract void initView(View view);
    protected abstract int getLayoutId();
    protected abstract void initListener();


    /**
     * 暂停界面调用
     */
    @Override
    public void onPause() {
        hideDialog();
        super.onPause();
    }

    /**
     * 友好退出堆栈
     */
    @Override
    public void pop() {
        hideKeyboard();
        super.pop();
    }

    /**
     * 销毁界面
     */
    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
        super.onDestroyView();
    }

    /**
     * 初始化控制层,业务层
     */
    public void initPresenter() {
        if (this instanceof BaseView &&
                this.getClass().getGenericSuperclass() instanceof ParameterizedType &&
                ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {

            for (int i = 0; i < ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length; i++) {
                Class clazz = (Class) ((ParameterizedType) (this.getClass()
                        .getGenericSuperclass())).getActualTypeArguments()[i];
                if (clazz.getSimpleName().contains("Presenter")) {
                    try {
                        mPresenter = (P) clazz.newInstance();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (mPresenter != null) {
                mPresenter.setView(this);
            }
        }
    }

    /**
     * 初始化菜单
     *
     * @param toolbar   　菜单栏
     * @param title     　标题
     * @param flateMenu 　菜单
     * @param listener  　事件
     */
    protected void initToolbarNav(Toolbar toolbar, String title, int flateMenu, Toolbar.OnMenuItemClickListener listener) {
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(title);
        if (flateMenu != -1)
            toolbar.inflateMenu(flateMenu);
        if (listener != null)
            toolbar.setOnMenuItemClickListener(listener);
        toolbar.findViewById(R.id.imgReturn).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                pop();
            }
        });
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 显示短文本
     *
     * @param msg 　消息
     */
    public void showToast(String msg) {
        Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示等待框
     *
     * @param title 标题
     * @param msg   消息
     */
    public void showProgressDialog(String title, String msg) {
        if (progressDialogHelper != null)
            progressDialogHelper.showProgressDialog(title, msg);
    }

    /**
     * 显示等待框
     *
     * @param msg 消息
     */
    public void showProgressDialog(String msg) {
        if (progressDialogHelper != null)
            progressDialogHelper.showProgressDialog(null, msg);
    }

    /**
     * 显示等待框
     */
    public void showProgressDialog() {
        showProgressDialog(null, null);
    }

    /**
     * 隐藏等待框
     */
    public void hideDialog() {
        if (progressDialogHelper != null) progressDialogHelper.dismiss();
    }
}
