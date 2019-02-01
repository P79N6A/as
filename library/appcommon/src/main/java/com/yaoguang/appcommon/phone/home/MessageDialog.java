package com.yaoguang.appcommon.phone.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.common.SysMsg;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * 弹窗的窗口,分多个消息的窗口和单个消息的窗口
 * Created by zhongjh on 2017/7/12.
 */
public class MessageDialog extends Dialog {

    Context mContext;
    public ViewHolder viewHolder;
    private List<SysMsg> mSysMsg = new ArrayList<>();
    private int mPosition = 0;// 当前滑动的索引

    DialogListener dialogListener = null;


    public MessageDialog(Context context, List<SysMsg> sysMsg) {
        super(context, R.style.Dialog);
        mContext = context;
        mSysMsg.addAll(sysMsg);
    }

    /**
     * 接口
     */
    public interface DialogListener {
        void onLock(SysMsg sysMsg);

        void onLock();
    }

    public void setDialogListener(DialogListener listener) {
        this.dialogListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_message, null);
        viewHolder = new ViewHolder(layout);

        // 根据消息数量判断是打开当前一条还是直接跳到列表
        if (mSysMsg.size() > 1) {
            viewHolder.tvOK.setText("了解更多");
        } else {
            viewHolder.tvOK.setText("查看详情");
        }

        viewHolder.viewPager.setAdapter(new MyAdapter());
        if (viewHolder.viewPager.getAdapter().getCount() > 8) {
            viewHolder.indicator.setCount(8);
        }
        viewHolder.indicator.setViewPager(viewHolder.viewPager);

        viewHolder.imgClose.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                MessageDialog.this.dismiss();
            }
        });
        viewHolder.tvOK.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //关闭当前
                MessageDialog.this.dismiss();

                // 根据消息数量判断是打开当前一条还是直接跳到列表
                if (mSysMsg.size() > 1) {
                    dialogListener.onLock();
                } else {
                    dialogListener.onLock(mSysMsg.get(mPosition));
                }
            }
        });

        this.setContentView(layout);
    }

    /**
     * 自定义类实现PagerAdapter，填充显示数据
     */
    class MyAdapter extends PagerAdapter {

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);
            mPosition = position;
        }

        // 显示多少个页面
        @Override
        public int getCount() {
            return mSysMsg.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        // 初始化显示的条目对象
        @Override
        public View instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(getContext(), R.layout.item_message_dialog, null);
            TextView tvTitle = view.findViewById(R.id.tvTitle);
            TextView tvContent = view.findViewById(R.id.tvContent);
            tvTitle.setText(mSysMsg.get(position).getTitle());
            tvContent.setText(mSysMsg.get(position).getContent());
            container.addView(view);
            return view;
        }

        // 销毁条目对象
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }

    public static class ViewHolder {
        public View rootView;
        public Toolbar toolbar;
        public TextView tvTitle;
        public TextView tvContent;
        public LinearLayout log_in_layout;
        public ViewPager viewPager;
        public CircleIndicator indicator;
        public TextView tvOK;
        public ImageView imgClose;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.toolbar = rootView.findViewById(R.id.toolbar);
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.tvContent = rootView.findViewById(R.id.tvContent);
            this.tvOK = rootView.findViewById(R.id.tvOK);
            this.imgClose = rootView.findViewById(R.id.imgClose);
            this.log_in_layout = rootView.findViewById(R.id.log_in_layout);
            this.viewPager = rootView.findViewById(R.id.viewPager);
            this.indicator = rootView.findViewById(R.id.indicator);
        }

    }
}