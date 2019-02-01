package com.yaoguang.lib.base;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.yaoguang.lib.base.interfaces.BaseView;
import com.yaoguang.lib.common.android.InputMethodUtil;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.lib.common.view.bar.ImmersionBar;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import io.reactivex.disposables.CompositeDisposable;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhongjh on 2017/3/27.
 * 一个基类
 */
public abstract class BaseFragment2 extends SupportFragment implements BaseView {

    // 一个等待框
    private ProgressDialogHelper progressDialogHelper;
    // 多个底部框的控件
    protected LinkedHashMap<Integer, SweetSheet> mSweetSheets = new LinkedHashMap<>();
    // android 4.4以上沉浸式以及bar的管理
    protected ImmersionBar mImmersionBar;

    protected View mVTop;
    protected TextView mTVMessage;
    protected TextView mTVOK;

    protected LayoutInflater mLayoutInflater; // 有些地方要用到这个

    protected boolean isEvent = false; // 用于防止刚开启fragment的时候，由于收到通知，启动两次refreshData，onEnterAnimationEnd动画结束后启动isEvent


    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    // 顶部的Sheet点击事件
    public interface OnTopSheets {
        void OnTopSheetsOKKey();
    }

    /**
     * 当前layout文件
     */
    public abstract int getLayoutId();

    /**
     * 加载数据绑定
     */
    public abstract void initDataBind(View view, LayoutInflater inflater);

    /**
     * 加载数据
     */
    public abstract void init();

    /**
     * 加载数据
     */
    public abstract void initListener();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideKeyboard();
        progressDialogHelper = new ProgressDialogHelper(BaseFragment2.this.getContext());
    }


    /**
     * 可以让子类选择是否使用
     *
     * @param savedInstanceState 缓存的数据
     */
    public void savedInstanceState(@Nullable Bundle savedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        savedInstanceState(savedInstanceState);
        mLayoutInflater = inflater;
        initDataBind(view, inflater);
        init();
        initListener();
        return view;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        isEvent = true;
        // 切换竖屏
        _mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void onRequestedOrientation(){

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
        if (getPresenter() != null)
            getPresenter().unSubscribe();
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
        if (mImmersionBar != null)
            mImmersionBar.destroy();

        unSubscribe();

        mCompositeDisposable.clear();

        super.onDestroy();
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
        InputMethodManager imm = (InputMethodManager) _mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(_mActivity.getWindow().getDecorView().getWindowToken(), 0);
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
        if (progressDialogHelper != null)
            progressDialogHelper.dismiss();
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
        // 长度不能超过5个
        int menuEntitiesSize;
        if (menuEntities.size() > 5){
            menuEntitiesSize = 5;
        }else{
            menuEntitiesSize = menuEntities.size();
        }
        if (TextUtils.isEmpty(title)) {
            int size = 50 + (menuEntitiesSize * 50);
            recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(size));
        } else {
            int size = 100 + (menuEntitiesSize * 50);
            recyclerViewDelegate.setContentHeight(DisplayMetricsUtils.dip2px(size));
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

    /**
     * 关闭sheets
     * @param key 索引
     */
    protected void dismissSweetSheets(int key){
        if (mSweetSheets.get(key) != null && !mSweetSheets.get(key).isShow()) {
            mSweetSheets.get(key).dismiss();
        }
    }


}
