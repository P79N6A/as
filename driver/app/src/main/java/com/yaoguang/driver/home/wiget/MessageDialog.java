package com.yaoguang.driver.home.wiget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.common.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.SysMsg;

/**
 * 弹窗的窗口
 * Created by zhongjh on 2017/7/12.
 */
public class MessageDialog extends Dialog {

    Context mContext;
    public ViewHolder viewHolder;
    public SysMsg mSysMsg;

    DialogListener dialogListener = null;


    public MessageDialog(Context context, SysMsg sysMsg) {
        super(context, R.style.Dialog);
        mContext = context;
        mSysMsg = sysMsg;
    }

    /**
     * 接口
     */
    public interface DialogListener {
        void onLock(SysMsg sysMsg);
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

        viewHolder.tvTitle.setText(mSysMsg.getTitle());
        viewHolder.tvContent.setText(mSysMsg.getContent());

        viewHolder.tvClose.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                MessageDialog.this.dismiss();
            }
        });
        viewHolder.tvLock.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //关闭当前
                MessageDialog.this.dismiss();
                //通知首页打开
                dialogListener.onLock(mSysMsg);
            }
        });

        this.setContentView(layout);
    }

    public static class ViewHolder {
        public View rootView;
        public Toolbar toolbar;
        public TextView tvTitle;
        public TextView tvContent;
        public TextView tvClose;
        public TextView tvLock;
        public LinearLayout log_in_layout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            this.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            this.tvContent = (TextView) rootView.findViewById(R.id.tvContent);
            this.tvClose = (TextView) rootView.findViewById(R.id.tvClose);
            this.tvLock = (TextView) rootView.findViewById(R.id.tvLock);
            this.log_in_layout = (LinearLayout) rootView.findViewById(R.id.log_in_layout);
        }

    }
}