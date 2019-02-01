package com.yaoguang.lib.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;

import com.yaoguang.lib.R;
import com.yaoguang.lib.databinding.ToolbarCommonBinding;

/**
 * 基类：一般的用法
 * Created by zhongjh on 2017/12/15.
 */
public abstract class BaseFragmentDataBind<B extends ViewDataBinding> extends BaseFragment2 {

    public B mDataBinding;
    public ToolbarCommonBinding mToolbarCommonBinding;

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.bind(view);
        // 如果DataBind又出现羊癫病了就启动这个
//        mToolbarCommonBinding = new ToolbarCommonBinding(view.findViewById(R.id.toolbar));
        if (mDataBinding.getRoot().findViewById(R.id.toolbarCommon) != null) {
            mToolbarCommonBinding = DataBindingUtil.bind(mDataBinding.getRoot().findViewById(R.id.toolbarCommon));
        }
    }

    // 如果DataBind又出现羊癫病了就启动这个
//    public static class ToolbarCommonBinding {
//        public View rootView;
//        public ImageView imgReturn;
//        public TextView toolbar_title;
//        public Toolbar toolbar;
//
//        public ToolbarCommonBinding(View rootView) {
//            this.rootView = rootView;
//            this.imgReturn = rootView.findViewById(R.id.imgReturn);
//            this.toolbar_title = rootView.findViewById(R.id.toolbar_title);
//            this.toolbar = rootView.findViewById(R.id.toolbar);
//        }
//
//    }
}