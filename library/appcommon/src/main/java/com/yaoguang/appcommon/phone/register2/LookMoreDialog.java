package com.yaoguang.appcommon.phone.register2;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

/**
 * .
 * 查看更多内容
 * Created by zhongjh on 2017/7/12.
 */
public class LookMoreDialog extends Dialog {

    Context mContext;
    String mContent;
    public ViewHolder viewHolder;


    public LookMoreDialog(Context context, String content) {
        super(context, R.style.Dialog);
        mContext = context;
        mContent = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_look_more, null);
        viewHolder = new ViewHolder(layout);
        viewHolder.imgClose.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                LookMoreDialog.this.dismiss();
            }
        });
        viewHolder.tvContent.setText(mContent);
        this.setContentView(layout);
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView imgClose;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            this.tvContent = (TextView) rootView.findViewById(R.id.tvContent);
            this.imgClose = (ImageView) rootView.findViewById(R.id.imgClose);
        }

    }
}