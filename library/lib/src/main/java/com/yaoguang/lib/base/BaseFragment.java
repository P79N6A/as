package com.yaoguang.lib.base;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.lib.R;
import com.yaoguang.lib.appcommon.utils.ProgressDialogHelper;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.lib.common.view.bar.ImmersionBar;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * Created by zhongjh on 2017/3/27.
 * 一个基类
 */
public abstract class BaseFragment extends SupportFragment {


    private ProgressDialogHelper progressDialogHelper; // 一个等待框
    protected LinkedHashMap<Integer, SweetSheet> mSweetSheets = new LinkedHashMap<>();// 多个底部框的控件

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideKeyboard();

        progressDialogHelper = new ProgressDialogHelper(BaseFragment.this.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            // 计算toolbar高度，倾入式后高度变化
            View titleBar = view.findViewById(R.id.toolbar);
            if (titleBar != null)
                ImmersionBar.setTitleBar(getActivity(), titleBar);
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        // 先关闭底部所有框
        Set<Integer> set = mSweetSheets.keySet();
        for (int key : set) {
            if (mSweetSheets.get(key) != null && mSweetSheets.get(key).isShow()) {
                mSweetSheets.get(key).dismiss();
                return true;
            }
        }
        return super.onBackPressedSupport();
    }

    /**
     * 取消订阅
     */
    public void unSubscribe() {
        if (getPresenter() != null) {
            getPresenter().unSubscribe();
        }
    }

    /**
     * 注入 presenter,执行通用操作
     */
    public abstract BasePresenter getPresenter();

    /**
     * 暂停界面调用
     */
    @Override
    public void onPause() {
        hideProgressDialog();
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
    public void onDestroy() {
        super.onDestroy();
        unSubscribe();
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
        Toast.makeText(BaseApplication.getInstance().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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
    public void hideProgressDialog() {
        if (progressDialogHelper != null) progressDialogHelper.dismiss();
    }

    /**
     * 使用 showSweetSheets打开
     *
     * @param key            键
     * @param flView         父子view
     * @param title          标题
     * @param values         数据源
     * @param onItemListener 事件
     */
    protected SweetSheet initSweetSheets(int key, FrameLayout flView, String title, List<String> values, SweetSheet.OnMenuItemClickListener onItemListener) {
        return initSweetSheets(key, flView, title, values, -1, onItemListener);
    }

    /**
     * 使用 showSweetSheets打开
     *
     * @param key            键
     * @param flView         父子view
     * @param title          标题
     * @param menuRes        文件id数据源
     * @param onItemListener 事件
     */
    protected SweetSheet initSweetSheets(int key, FrameLayout flView, String title, int menuRes, SweetSheet.OnMenuItemClickListener onItemListener) {
        return initSweetSheets(key, flView, title, null, menuRes, onItemListener);
    }

    /**
     * 使用 showSweetSheets打开
     *
     * @param key            键
     * @param flView         父子view
     * @param title          标题
     * @param values         数据源
     * @param menuRes        文件id数据源
     * @param onItemListener 事件
     */
    private SweetSheet initSweetSheets(int key, FrameLayout flView, String title, List<String> values, int menuRes, SweetSheet.OnMenuItemClickListener onItemListener) {
        // 判断是否存在，如果存在就不新建了
        SweetSheet sweetSheet = mSweetSheets.get(key);
        if (sweetSheet != null)
            return sweetSheet;
        // 数据源
        List<MenuEntity> menuEntities = new ArrayList<>();
        // 初始化弹框
        sweetSheet = new SweetSheet(flView);
        if (menuRes != -1) {
            // 将资源id赋值
            Menu menu = new PopupMenu(flView.getContext(), null).getMenu();
            new MenuInflater(flView.getContext()).inflate(menuRes, menu);
            menuEntities = SweetSheet.getMenuEntityFormMenuRes(menu);
            sweetSheet.setMenuList(menuEntities);
        } else {
            if (values != null) {
                for (String item : values) {
                    MenuEntity menuEntity = new MenuEntity();
                    menuEntity.title = item;
                    menuEntities.add(menuEntity);
                }
            }
            sweetSheet.setMenuList(menuEntities);
        }
        sweetSheet.setTitle(title);
        RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate(true);
        switch (menuEntities.size()) {
            case 1:
                recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(150));
                break;
            case 2:
                recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(200));
                break;
            case 3:
                recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(250));
                break;
        }

        sweetSheet.setDelegate(recyclerViewDelegate);
        sweetSheet.setBackgroundEffect(new DimEffect(4));
        sweetSheet.setOnMenuItemClickListener(onItemListener);
        mSweetSheets.put(key, sweetSheet);
        return sweetSheet;
    }


    /**
     * 展现
     *
     * @param key 索引
     */
    protected void showSweetSheets(int key) {
        if (mSweetSheets.get(key) != null && !mSweetSheets.get(key).isShow()) {
            InputMethodUtil.hideKeyboard(getActivity());
            mSweetSheets.get(key).show();
        }
    }
}
