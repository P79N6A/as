package com.yaoguang.lib.appcommon.widget.head;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.yaoguang.lib.R;

/**
 * 自制顶部
 * Created by zhongjh on 2018/3/26.
 */
public class Pull2Header extends RelativeLayout implements RefreshHeader {

    private RelativeLayout mainRider;
    private ImageView ivWheel1, ivWheel2;    //轮组图片组件
    private ImageView ivRider;  //骑手图片组件
    private ImageView ivSun, ivBack1, ivBack2;    //太阳、背景图片1、背景图片2
    private Animation wheelAnimation, sunAnimation;  //轮子、太阳动画
    private Animation backAnimation1, backAnimation2;    //两张背景图动画
    private TextView refreshTextView;


    public Pull2Header(Context context) {
        super(context);
        initView(context);
    }

    public Pull2Header(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public Pull2Header(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View rootView = View.inflate(getContext(), R.layout.headview2, null);
        ivRider = rootView.findViewById(R.id.iv_rider);
        ivSun = rootView.findViewById(R.id.ivsun);
        ivWheel1 = rootView.findViewById(R.id.wheel1);
        ivWheel2 = rootView.findViewById(R.id.wheel2);
        ivBack1 = rootView.findViewById(R.id.iv_back1);
        ivBack2 = rootView.findViewById(R.id.iv_back2);
        refreshTextView = rootView.findViewById(R.id.tvTitle);
        mainRider = rootView.findViewById(R.id.mainRider);

        //获取动画
        wheelAnimation = AnimationUtils.loadAnimation(context, R.anim.wheel);
        sunAnimation = AnimationUtils.loadAnimation(context, R.anim.sum);

        LinearInterpolator lir = new LinearInterpolator();
        wheelAnimation.setInterpolator(lir);
        sunAnimation.setInterpolator(lir);

        backAnimation1 = AnimationUtils.loadAnimation(context, R.anim.back1);
        backAnimation2 = AnimationUtils.loadAnimation(context, R.anim.back2);

        addView(rootView);

        startAnim();
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {
        // 更新中的动画
//        startAnim();
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                startAnim();
//                mHeaderText.setText("下拉开始刷新");
//                mArrowView.setVisibility(VISIBLE);//显示下拉箭头
//                mProgressView.setVisibility(GONE);//隐藏动画
//                mArrowView.animate().rotation(0);//还原箭头方向
                break;
            case Refreshing:
                startAnim();
//                mHeaderText.setText("正在刷新");
//                mProgressView.setVisibility(VISIBLE);//显示加载动画
//                mArrowView.setVisibility(GONE);//隐藏箭头
                break;
            case ReleaseToRefresh:
                startAnim();
//                mHeaderText.setText("释放立即刷新");
//                mArrowView.animate().rotation(180);//显示箭头改为朝上
                break;
        }
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        stopAnim();
//        mProgressDrawable.stop();//停止动画
//        if (success){
//            mHeaderText.setText("刷新完成");
//        } else {
//            mHeaderText.setText("刷新失败");
//        }
        slideview(mainRider,0,ivBack1.getWidth());
        return 500;//延迟500毫秒之后再弹回
    }

    /**
     * 开启动画
     */
    public void startAnim() {
        ivBack1.startAnimation(backAnimation1);
        ivBack2.startAnimation(backAnimation2);
        ivSun.startAnimation(sunAnimation);
        ivWheel1.startAnimation(wheelAnimation);
        ivWheel2.startAnimation(wheelAnimation);
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
        ivBack1.clearAnimation();
        ivBack2.clearAnimation();
        ivSun.clearAnimation();
        ivWheel1.clearAnimation();
        ivWheel2.clearAnimation();
    }

    public void slideview(View view, final float p1, final float p2) {
        TranslateAnimation animation = new TranslateAnimation(p1, p2, 0, 0);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(600);
        animation.setStartOffset(0);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                int left = view.getLeft() + (int) (p2 - p1);
//                int top = view.getTop();
//                int width = view.getWidth();
//                int height = view.getHeight();
                view.clearAnimation();
//                view.layout(left, top, left + width, top + height);
            }
        });
        view.startAnimation(animation);
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

}
