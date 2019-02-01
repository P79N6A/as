package com.yaoguang.lib.appcommon.widget.head;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.yaoguang.lib.R;

/**
 * Created by zhongjh on 2017/8/29.
 */
public class PullHead extends FrameLayout implements IHeaderView {

    private boolean isInit = false;//初始化
    private RelativeLayout mainRider;
    private ImageView ivWheel1, ivWheel2;    //轮组图片组件
    private ImageView ivRider;  //骑手图片组件
    private ImageView ivSun, ivBack1, ivBack2;    //太阳、背景图片1、背景图片2
    private Animation wheelAnimation, sunAnimation;  //轮子、太阳动画
    private Animation backAnimation1, backAnimation2;    //两张背景图动画
    private TextView refreshTextView;

    public PullHead(Context context) {
        super(context);
        init(context);
    }

    public PullHead(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public PullHead(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View rootView = View.inflate(getContext(), R.layout.headview, null);
        ivRider = (ImageView) rootView.findViewById(R.id.iv_rider);
        ivSun = (ImageView) rootView.findViewById(R.id.ivsun);
        ivWheel1 = (ImageView) rootView.findViewById(R.id.wheel1);
        ivWheel2 = (ImageView) rootView.findViewById(R.id.wheel2);
        ivBack1 = (ImageView) rootView.findViewById(R.id.iv_back1);
        ivBack2 = (ImageView) rootView.findViewById(R.id.iv_back2);
        refreshTextView = (TextView) rootView.findViewById(R.id.tvTitle);
        mainRider = (RelativeLayout) rootView.findViewById(R.id.mainRider);

        //获取动画
        wheelAnimation = AnimationUtils.loadAnimation(context, R.anim.wheel);
        sunAnimation = AnimationUtils.loadAnimation(context, R.anim.sum);

        LinearInterpolator lir = new LinearInterpolator();
        wheelAnimation.setInterpolator(lir);
        sunAnimation.setInterpolator(lir);

        backAnimation1 = AnimationUtils.loadAnimation(context, R.anim.back1);
        backAnimation2 = AnimationUtils.loadAnimation(context, R.anim.back2);

        addView(rootView);

//        startAnim();
    }

    @Override
    public View getView() {
        return this;
    }

    private String pullDownStr = "下拉可以刷新";
    private String releaseRefreshStr = "松开立即刷新";
    private String refreshingStr = "正在刷新";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //一边绘图，一边变形
    }


    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1f) refreshTextView.setText(pullDownStr);
        if (fraction >= 0.78f) {
            startAnim();
            refreshTextView.setText(releaseRefreshStr);
        }
        if (fraction > 0.7f) {
            //显示
            if (mainRider.getVisibility() != View.VISIBLE) {
                mainRider.setVisibility(View.VISIBLE);
            }
        } else {
            if (mainRider.getVisibility() != View.INVISIBLE) {
                mainRider.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1f) refreshTextView.setText(pullDownStr);
        if (fraction >= 0.78f) {
            startAnim();
            refreshTextView.setText(releaseRefreshStr);
        }
        if (fraction > 0.7f) {
            //显示
            if (mainRider.getVisibility() != View.VISIBLE) {
                mainRider.setVisibility(View.VISIBLE);
            }
        } else {
            if (mainRider.getVisibility() != View.INVISIBLE) {
                mainRider.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        refreshTextView.setText(refreshingStr);
        stopAnim();
        mainRider.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        animEndListener.onAnimEnd();
    }

    @Override
    public void reset() {
        refreshTextView.setText(pullDownStr);
    }

    /**
     * 开启动画
     */
    public void startAnim() {
        if (!isInit) {
            isInit = true;
            ivBack1.startAnimation(backAnimation1);
            ivBack2.startAnimation(backAnimation2);
            ivSun.startAnimation(sunAnimation);
            ivWheel1.startAnimation(wheelAnimation);
            ivWheel2.startAnimation(wheelAnimation);
        }
    }

    /**
     * 关闭动画
     */
    public void stopAnim() {
        backAnimation1.cancel();
        backAnimation2.cancel();
        sunAnimation.cancel();
        wheelAnimation.cancel();
        wheelAnimation.cancel();
//        ivBack1.clearAnimation();
//        ivBack2.clearAnimation();
//        ivSun.clearAnimation();
//        ivWheel1.clearAnimation();
//        ivWheel2.clearAnimation();
    }


}
