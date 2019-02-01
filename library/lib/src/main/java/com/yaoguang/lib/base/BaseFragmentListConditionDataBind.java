package com.yaoguang.lib.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;

import com.yaoguang.lib.R;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.databinding.LayoutRecyclerviewBinding;
import com.yaoguang.lib.databinding.ToolbarCommonBinding;

/**
 * 基类：列表条件专用
 * Created by zhongjh on 2017/12/13.
 */
public abstract class BaseFragmentListConditionDataBind<T, C, A extends BaseLoadMoreRecyclerAdapter, B extends ViewDataBinding> extends BaseFragmentListCondition<T, C, A> {


    public B mDataBinding;
    public ToolbarCommonBinding mToolbarCommonBinding;
    public LayoutRecyclerviewBinding mLayoutRecyclerviewBinding;

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.bind(view);

        if (mDataBinding.getRoot().findViewById(R.id.toolbarCommon) != null) {
            mToolbarCommonBinding = DataBindingUtil.bind(mDataBinding.getRoot().findViewById(R.id.toolbarCommon));
        }
        if (mDataBinding.getRoot().findViewById(R.id.layoutRecyclerview) != null) {
            mLayoutRecyclerviewBinding = DataBindingUtil.bind(mDataBinding.getRoot().findViewById(R.id.layoutRecyclerview));
        }


//        mToolbarCommonBinding = new ToolbarCommonBinding(view.findViewById(R.id.toolbar));
//        mRecyclerviewEmptyErrorBinding = new RecyclerviewEmptyErrorBinding(view.findViewById(R.id.layoutRecyclerviewEmptyError));
    }


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
//
//    public static class RecyclerviewEmptyErrorBinding {
//        public View rootView;
//        public ImageView ivEmpty;
//        public RelativeLayout rlEmpty;
//        public ImageView ivError;
//        public RelativeLayout rlError;
//
//        public RecyclerviewEmptyErrorBinding(View rootView) {
//            this.rootView = rootView;
//            this.ivEmpty = rootView.findViewById(R.id.ivEmpty);
//            this.rlEmpty = rootView.findViewById(R.id.rlEmpty);
//            this.ivError = rootView.findViewById(R.id.ivError);
//            this.rlError = rootView.findViewById(R.id.rlError);
//        }
//
//    }
}
