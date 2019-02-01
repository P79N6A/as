package com.yaoguang.appcommon.phone.my.aboutus;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

/**
 * .
 * 帮助更新内容
 * Created by zhongjh on 2017/7/12.
 */
public abstract class BaseAboutUsDialog extends Dialog {

    Context mContext;
    public ViewHolder viewHolder;

    protected abstract void customCreate();

    public BaseAboutUsDialog(Context context) {
        super(context, R.style.Dialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_aboutus, null);
        viewHolder = new ViewHolder(layout);
        viewHolder.tvClose.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                BaseAboutUsDialog.this.dismiss();
            }
        });
        customCreate();
        this.setContentView(layout);
    }


    public static class ViewHolder {
        public View rootView;
        public TextView toolbar_title;
        public Toolbar toolbar;
        public TextView tvContent;
        public TextView tvClose;
        public LinearLayout log_in_layout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            this.tvContent = (TextView) rootView.findViewById(R.id.tvContent);
            this.tvClose = (TextView) rootView.findViewById(R.id.tvClose);
            this.log_in_layout = (LinearLayout) rootView.findViewById(R.id.log_in_layout);
        }

    }
}