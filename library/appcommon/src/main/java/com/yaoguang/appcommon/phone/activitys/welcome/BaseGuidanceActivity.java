package com.yaoguang.appcommon.phone.activitys.welcome;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yaoguang.appcommon.R;

public abstract class BaseGuidanceActivity extends BaseGuidance {
    InitialView mInitialView;
    private int mPosition;
    private float x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance);
        mInitialView = new InitialView();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            x1 = ev.getX();
        }

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (x1 - ev.getX() > 50 && mPosition == 2) {
                toLoginActivity();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public class InitialView {

        private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };

        public InitialView() {
            initViewPager();
        }

        private void initViewPager() {
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
            viewPager.setAdapter(new GuidanceAdapter(getViewList()));
            viewPager.addOnPageChangeListener(onPageChangeListener);

        }
    }




    public LinearLayout getFullScreenView(Context context, int resid) {
        LinearLayout ll = new LinearLayout(context);
        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ImageView iv = new ImageView(context);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageResource(resid);

        ll.addView(iv);
        return ll;
    }
}
