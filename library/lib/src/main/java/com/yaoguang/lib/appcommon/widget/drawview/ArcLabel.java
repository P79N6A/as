package com.yaoguang.lib.appcommon.widget.drawview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.yaoguang.lib.R;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;

/**
 * 这是一个圆弧标签
 * Created by zhongjh on 2017/11/6.
 */
public class ArcLabel extends View {

    private Paint mPaint;
    private RectF mRectF;
    private Paint mPaintText;
    private Paint.FontMetrics mFm;
    private int mColorPrimary;
    private Context mContext;


    /**
     * 圆的宽度
     */
    private int mCircleWidth = 1;

    public ArcLabel(Context context) {
        this(context, null);
        mPaint = new Paint();
        mRectF = new RectF();
        mPaintText = new Paint();
        TypedArray array = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.colorPrimary});
        mColorPrimary = array.getColor(0, 0);
        mContext = context;
        array.recycle();
    }

    public ArcLabel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mPaint = new Paint();
        mRectF = new RectF();
        mPaintText = new Paint();
        TypedArray array = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.colorPrimary});
        mColorPrimary = array.getColor(0, 0);
        mContext = context;
        array.recycle();
    }

    public ArcLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mRectF = new RectF();
        mPaintText = new Paint();
        TypedArray array = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.colorPrimary});
        mColorPrimary = array.getColor(0, 0);
        mContext = context;
        array.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mRectF = new RectF(0, 0,
                getWidth(), getHeight());

        //以分辨率为720*1080准，计算宽高比值
        float ratioWidth = (float) DisplayMetricsSPUtils.getScreenWidth(mContext) / 720;
        float ratioHeight = (float)  DisplayMetricsSPUtils.getScreenHeight(mContext)  / 1080;
        float ratioMetrics = Math.min(ratioWidth, ratioHeight);
        int textSize = Math.round(18 * ratioMetrics);

        mPaintText.setTextSize(textSize);
        mPaintText.setColor(ContextCompat.getColor(getContext(), R.color.white));
        mFm = mPaintText.getFontMetrics();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAntiAlias(true);//取消锯齿
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setColor(mColorPrimary);

        // 画圆弧
        canvas.drawArc(mRectF, 20, 140, false, mPaint);
        // 画编辑字
//        mPaintText.setTextAlign(Paint.Align.CENTER);
//        Paint.FontMetrics fontMetrics = mPaintText.getFontMetrics();
//        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
//        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
//        int baseLineY = (int) (mRectF.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
        canvas.drawText("编辑", (float) (mRectF.centerX() * 0.5), (float) (getWidth() * 0.9), mPaintText);
    }
}
