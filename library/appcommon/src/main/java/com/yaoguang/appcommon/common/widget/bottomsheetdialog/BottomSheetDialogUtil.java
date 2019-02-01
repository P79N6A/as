package com.yaoguang.appcommon.common.widget.bottomsheetdialog;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

/**
 * Created by zhongjh on 2017/6/16.
 */
public class BottomSheetDialogUtil {

    private OnItemClickListener onItemClickListener;
    public void show(Context context, Activity activity, List<String> list) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        //创建recyclerView
        RecyclerView recyclerView = new RecyclerView(activity);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list, context);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View item, int position) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(item, position);
                }
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(recyclerView);
        bottomSheetDialog.show();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(View item, int position);
    }


}
