package com.yaoguang.lib.common.tip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.yaoguang.lib.R;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;
import com.yaoguang.lib.entity.TipBitmap;
import com.yaoguang.lib.entity.TipView;

import java.util.ArrayList;

/**
 * 指引的FrameLayout
 * Created by zhongjh on 2017/11/20.
 */
public class TipsView extends FrameLayout {

    private Context mContext;
    ArrayList<TipView> mTipView; // 所有提示模型
    Paint mPaintImage; //背景图片

    // 确认的位置
    Bitmap bitmapKnow;
    float left;
    float top;
    // 确认事件
    private OnKnowClickListener onKnowClickListener;

    public void setOnKnowClickListener(OnKnowClickListener onKnowClickListener) {
        this.onKnowClickListener = onKnowClickListener;
    }

    public interface OnKnowClickListener {
        void onKnowClickListener();
    }

    public TipsView(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public TipsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public TipsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        setBackgroundColor(Color.parseColor("#7f000000"));//半透明底色
        mPaintImage = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setTipViews(ArrayList<TipView> tipViews) {
        this.mTipView = tipViews;
        // 循环处理
        for (TipView tipView : tipViews) {
            // 设置图片资源
            tipView.setBitmap(BitmapFactory.decodeResource(mContext.getResources(), tipView.getResourceId()));

            for (TipBitmap tipBitmap : tipView.getTipBitmaps()) {
                tipBitmap.setBitmap(BitmapFactory.decodeResource(mContext.getResources(), tipBitmap.getResourceId()));
                switch (tipBitmap.getGravity()) {
                    case Gravity.CENTER | Gravity.BOTTOM:
                        // 如果是提示的图片是居中的
                        tipBitmap.setLeft((DisplayMetricsUtils.getScreenWidth(getContext()) - tipBitmap.getBitmap().getWidth()) * 0.5f);
                        tipBitmap.setTop(tipView.getTop() + tipView.getBitmap().getHeight());
                        break;


                }
            }
        }

        //确认的图片
        bitmapKnow = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_know);
        left = (DisplayMetricsUtils.getScreenWidth(getContext()) - bitmapKnow.getWidth()) * 0.5f;
        top = DisplayMetricsUtils.getScreenHeight(getContext()) - bitmapKnow.getHeight() - DisplayMetricsUtils.dip2px(100);

        invalidate(); //重新绘画
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTipView != null) {
            // 循环画图
            for (TipView tipView : mTipView) {
                canvas.drawBitmap(tipView.getBitmap(), tipView.getLeft(), tipView.getTop(), mPaintImage);
                for (TipBitmap tipBitmap : tipView.getTipBitmaps()) {
                    canvas.drawBitmap(tipBitmap.getBitmap(), tipBitmap.getLeft(), tipBitmap.getTop(), mPaintImage);
                }
            }
        }

        // 画一个确认按钮
        canvas.drawBitmap(bitmapKnow, left, top, mPaintImage);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Check if the x and y position of the touch is inside the bitmap
                if (x > left && x < left + bitmapKnow.getWidth() && y > top && y < top + bitmapKnow.getHeight()) {
                    // 关闭
                    if (onKnowClickListener != null) {
                        onKnowClickListener.onKnowClickListener();
                    }
                }
                return true;
        }
        return false;
    }


}
