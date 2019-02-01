package com.yaoguang.lib.appcommon.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/10/9 0009.
 */

public class ProcessCircleImageView extends android.support.v7.widget.AppCompatImageView {
    private boolean isShowProcess;  // 是否显示进度条
    private Paint mPaint;           // 画笔
    private Context mContext;
    public static final int ROUND_WIDTH = 80;
    public static final int STROKE_WIDTH = 3;
    private int mFontSize;
    private int mRoundWidth;
    private int mStrokeWidth;
    private boolean mShowProgress;
    private int mProgress;
    private float mTextY;
    private int mCenterX;
    private int mCenterY;
    private int mRadius;
    private RectF mOval;
    private int maxProgress = 100;

    public ProcessCircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }
    private void init() {
        float scale = mContext.getResources().getDisplayMetrics().density;

        mRoundWidth = (int) (ROUND_WIDTH * scale);
        mStrokeWidth = (int) (STROKE_WIDTH * scale);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mFontSize);

        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;
        mRadius = mRoundWidth / 2;

        mTextY = mCenterY + mFontSize * 11.0f / 28;

        mOval = new RectF(mCenterX - mRadius, mCenterY - mRadius, mCenterX
                + mRadius, mCenterY + mRadius);
    }

    public void onDraw(Canvas canvas) {
        if (mShowProgress) {
            if (mCenterX == 0 || mCenterY == 0) {
                init();
            }
            // 画最外层的大圆环
            mPaint.setColor(Color.DKGRAY);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mStrokeWidth);
            canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);

            // 画进度百分比
//            mPaint.setStrokeWidth(0);
//            mPaint.setColor(Color.WHITE);
//            mPaint.setTypeface(Typeface.MONOSPACE);
//            mPaint.setTextAlign(Paint.Align.CENTER);
//            String progressStr = mProgress + "%";
//            canvas.drawText(progressStr, mCenterX, mTextY, mPaint);

            // 画圆环的进度
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setColor(Color.WHITE);
            canvas.drawArc(mOval, 0, 360 * mProgress / maxProgress, false, mPaint);
        } else {
            super.onDraw(canvas);
        }
    }

    public void startProgress() {
        mShowProgress = true;
        setProgress(0);
    }

    public void setProgress(int progress) {
        if (mShowProgress) {
            mProgress = progress;
            invalidate();
        }
    }

    public void closeProgress() {
        mShowProgress = false;
    }

    public void setShowProgress(boolean mShowProgress) {
        this.mShowProgress = mShowProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }
}
