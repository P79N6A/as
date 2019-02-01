package com.yaoguang.lib.appcommon.widget.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.lcodecore.tkrefreshlayout.IBottomView;
import com.yaoguang.lib.R;

/**
 * Created by zhongjh on 2017/9/12.
 */

public class FooterView extends View implements IBottomView {
    public FooterView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        //添加一个底部view
        return LayoutInflater.from(getContext()).inflate(R.layout.item_foot, null, false);
    }

    @Override
    public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void startAnim(float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void onPullReleasing(float fraction, float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void reset() {

    }
}
