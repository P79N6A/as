package com.yaoguang.lib.appcommon.widget.popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yaoguang.lib.R;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.utils.RecyclerViewUtils;

import java.util.List;

/**
 * 下拉框
 * Created by zhongjh on 2018/7/31.
 */
public class SpinnerPopWindow extends PopupWindow {

    private Context mContext;

    private RecyclerView mRLData;
    private View.OnClickListener mListener;
    private OnDismissListener mDismissListener;
    private List<String> mData;

    public SpinnerPopWindow(Context context, View.OnClickListener listener, OnDismissListener dismissListener, List<String> data) {
        super();
        this.mContext = context;
        this.mListener = listener;
        this.mDismissListener = dismissListener;
        this.mData = data;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_dropdown_list, null);
        setContentView(view);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        update();
        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mRLData = view.findViewById(R.id.rlData);
        setData();
        //当popupwindow消失时调用该方法
        setOnDismissListener(mDismissListener);
    }

    /**
     * 赋值数据源
     */
    private void setData() {
        SpinnerArrayAdapter spinnerArrayAdapter = new SpinnerArrayAdapter();
        spinnerArrayAdapter.appendToList(this.mData);
        RecyclerViewUtils.initPage(mRLData,spinnerArrayAdapter ,null,mContext,false);
        spinnerArrayAdapter.setOnItemClickListener((itemView, item, position) -> {
            // 点击事件
            mListener.onClick(itemView);
        });
    }

    /**
     * 底部弹出此框
     * @param parent 依附parent控件的下面
     */
    public void showSpPop(View parent) {
        if(!this.isShowing()){
            //所显示的与parent的宽度相等
            setWidth(parent.getWidth());
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            this.showAsDropDown(parent, 0, 0);
        }else {
            dismiss();
        }
    }



    /**
     * 设配器
     */
    class SpinnerArrayAdapter extends BaseLoadMoreRecyclerAdapter<String, RecyclerView.ViewHolder>  {

        @Override
        public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_dropdown_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(v -> {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, getItem(adapterPosition), adapterPosition);
                }
            });
            return holder;
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            ViewHolder holder = (ViewHolder) viewHolder;
            String myItem = getList().get(position);
            holder.text_view_spinner.setText(myItem);
        }


        private class ViewHolder extends RecyclerView.ViewHolder {
            public View rootView;
            public TextView text_view_spinner;

            public ViewHolder(View rootView) {
                super(rootView);
                this.rootView = rootView;
                this.text_view_spinner = rootView.findViewById(android.R.id.text1);
            }

        }
    }


}
