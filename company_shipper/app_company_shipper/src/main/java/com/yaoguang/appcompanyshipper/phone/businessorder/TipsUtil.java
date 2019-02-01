package com.yaoguang.appcompanyshipper.phone.businessorder;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.yaoguang.appcompanyshipper.R;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.tip.TipUtils;
import com.yaoguang.lib.entity.TipBitmap;
import com.yaoguang.lib.entity.TipView;

import java.util.ArrayList;

/**
 * 提示公共工具类，适用于货代拖车业务
 * Created by zhongjh on 2017/11/24.
 */
public class TipsUtil {

    /**
     * @param activity 当前activity
     * @param rlView   列表0
     * @param guide    当前执行次数，到2次就执行了
     * @param adapter  适配器
     * @param victim   事件
     */
    public static void startTipsBusinessOrderList(Activity activity, View view, RecyclerView rlView, int guide, BaseLoadMoreRecyclerAdapter adapter, ViewTreeObserver.OnGlobalLayoutListener victim, String tag) {
        if (guide == 2 && adapter.getItemCount() > 0 && rlView != null && rlView.getViewTreeObserver() != null) {
            // 初始加载完事件，就加载
            boolean isShow = SPUtils.getInstance().getBoolean(TipsConstant.TIPSCONSTANT + tag);
            if (!isShow) {
                rlView.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(victim);
                rlView.smoothScrollToPosition(0);// 滚动到第一行
                //设置不能有任何动作
                view.setOnTouchListener((v, event) -> true);
                view.setTag("1");
//            rlView.// 禁止滚动
                // 必须有数据才可以
                new Handler().postDelayed(() -> {
                    if (rlView.getLayoutManager().findViewByPosition(0) != null) {
                        View lbtnStars = rlView.getLayoutManager().findViewByPosition(0).findViewById(R.id.lbtnStars);
                        View imgStatus = rlView.getLayoutManager().findViewByPosition(0).findViewById(R.id.imgStatus);

                        TipView tiplbtnStars = new TipView();
                        tiplbtnStars.setView(lbtnStars);
                        tiplbtnStars.setResourceId(R.drawable.ic_sc_yellow);
                        tiplbtnStars.setLeft(-10);

                        ArrayList<TipBitmap> tiplbtnStarsBitmaps = new ArrayList<>();
                        TipBitmap tipbtnStarsBitmap = new TipBitmap();
                        tipbtnStarsBitmap.setResourceId(R.drawable.ic_sc_wz);
                        tipbtnStarsBitmap.setGravity(Gravity.CENTER | Gravity.BOTTOM);
                        tiplbtnStarsBitmaps.add(tipbtnStarsBitmap);
                        tiplbtnStars.setTipBitmaps(tiplbtnStarsBitmaps);

                        TipView tipImgStatus = new TipView();
                        tipImgStatus.setView(imgStatus);
                        tipImgStatus.setResourceId(R.drawable.ic_wdr_green);
                        tipImgStatus.setLeft(-10);
                        tipImgStatus.setTop(-6);
                        ArrayList<TipBitmap> tipImgStatusBitmaps = new ArrayList<>();
                        TipBitmap tipImgStatusBitmap = new TipBitmap();
                        tipImgStatusBitmap.setResourceId(R.drawable.ic_wdr_wz);
                        tipImgStatusBitmap.setGravity(Gravity.CENTER | Gravity.BOTTOM);
                        tipImgStatusBitmaps.add(tipImgStatusBitmap);
                        tipImgStatus.setTipBitmaps(tipImgStatusBitmaps);

                        ArrayList<TipView> tipViews = new ArrayList<>();
                        tipViews.add(tiplbtnStars);
                        tipViews.add(tipImgStatus);
                        TipUtils.start(tipViews, activity);

                        view.setOnTouchListener((v, event) -> false);
                        view.setTag("0");
                        SPUtils.getInstance().put(TipsConstant.TIPSCONSTANT + tag, true);
                    }
                }, 500);
            } else {
                view.setOnTouchListener((v, event) -> false);
                view.setTag("0");
            }
        }
    }


}
