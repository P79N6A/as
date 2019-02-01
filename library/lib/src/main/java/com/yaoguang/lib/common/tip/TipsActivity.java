package com.yaoguang.lib.common.tip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import com.yaoguang.lib.R;
import com.yaoguang.lib.entity.TipView;

import java.util.ArrayList;


/**
 * 蒙版activity
 * Created by zhongjh on 2017/11/20.
 */
public class TipsActivity extends Activity {

    RelativeLayout tipsRootview;

    ArrayList<TipView> tipViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消title
        setContentView(R.layout.activity_tips);
        tipsRootview = (RelativeLayout) findViewById(R.id.tips_rootview);
        Intent intent = getIntent();
        tipViews = intent.getParcelableArrayListExtra("tipViews");//获取对象
        initView();
    }

    private void initView() {
        TipsView tipsView = new TipsView(this);//将坐标传给自定义view
        tipsView.setTipViews(tipViews);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tipsRootview.addView(tipsView, layoutParams);

        tipsView.setOnKnowClickListener(new TipsView.OnKnowClickListener() {
            @Override
            public void onKnowClickListener() {
                finish();
                overridePendingTransition(0, 0); //取消动画效果
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(0, 0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
