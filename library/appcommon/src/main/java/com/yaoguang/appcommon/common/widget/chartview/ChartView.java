package com.yaoguang.appcommon.common.widget.chartview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.yaoguang.appcommon.R;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsUtils;

import java.util.ArrayList;


/**
 * 绘制天气折线图的View
 */
public class ChartView extends View {

    // 上下文
    private Context context;
    // 早上数据源
    private ArrayList<ChartItem> items;
    // 晚上数据源
    private ArrayList<ChartItem> items2;
    // 早上的x坐标数据源
    ArrayList<Integer> xlist;
    // 晚上的x坐标数据源
    ArrayList<Integer> xlist2;
    // 提示文字
    private String unit;
    // 格式化
    private String yFormat = "0.#°";

    int mSplit;
    // 顶部间距
    int mYMargintTop;
    int mMargint2;

    // 线条的油漆
    Paint mPaint;
    // 单位文字的油漆
    Paint mPaintUnit;

    /**
     * 赋值
     *
     * @param list     数据源
     * @param unitInfo 提示文字
     */
    public void setView(ArrayList<ChartItem> list, ArrayList<ChartItem> list2, String unitInfo) {
        this.items = list;
        this.items2 = list2;
        this.unit = unitInfo;
    }

    public ChartView(Context ct) {
        super(ct);
        this.context = ct;
        init();
    }

    public ChartView(Context ct, AttributeSet attrs) {
        super(ct, attrs);
        this.context = ct;
        init();
    }

    public ChartView(Context ct, AttributeSet attrs, int defStyle) {
        super(ct, attrs, defStyle);
        this.context = ct;
        init();
    }

    /**
     * 初始化工具
     */
    private void init() {
        // 初始化所有单位
        mSplit = DisplayMetricsUtils.dip2px(30);
        mYMargintTop = DisplayMetricsUtils.dip2px(60);
        mMargint2 = DisplayMetricsUtils.dip2px(25);

        // x坐标的数据源
        xlist = new ArrayList<>();
        xlist2 = new ArrayList<>();

        // 初始化 油漆
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(context.getResources().getColor(R.color.white));
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);

        // 初始化单位 的油漆
        mPaintUnit = new Paint();
        mPaintUnit.setTextSize(DisplayMetricsUtils.sp2px(12));
        mPaintUnit.setColor(context.getResources().getColor(R.color.white));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (items == null || items2 == null) {
            return;
        }

        int height = getHeight();
        int width = getWidth();
        // 左右间距
        int marginl = 0;
        int bheight = height - mYMargintTop - 2 * mSplit;

        // 画单位
//        canvas.drawText(unit, mSplit + Utils.dip2px(context, 30f), mMargint2 + mSplit * 2, mPaintUnit);

        // 画早上的X坐标值
        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(15);//设置字体大小
        for (int i = 0; i < items.size(); i++) {
            // 获取跨度，比如3条数据就平均3块
            int span = (width - 2 * marginl) / items.size();
            // 算出每个跨度的中间值，该值就是x了
            int x = marginl + span / 2 + span * i;
            xlist.add(x);
            drawText(items.get(i).getX(), x, mSplit, canvas);
        }
        // 画晚上的X坐标值
        for (int i = 0; i < items2.size(); i++) {
            // 获取跨度，比如3条数据就平均3块
            int span = (width - 2 * marginl) / items2.size();
            // 算出每个跨度的中间值，该值就是x了
            int x = marginl + span / 2 + span * i;
            xlist2.add(x);
        }

        // 获取最大值和最小值
        float max = Float.MIN_VALUE;
        float min = Float.MAX_VALUE;
        for (int i = 0; i < items.size(); i++) {
            float y = items.get(i).getY();
            if (y > max) {
                max = y;
            }
            if (y < min) {
                min = y;
            }
        }
        for (int i = 0; i < items2.size(); i++) {
            float y = items2.get(i).getY();
            if (y > max) {
                max = y;
            }
            if (y < min) {
                min = y;
            }
        }

        // 最大跟最小的差距为0，则设置默认值6给它们
        float span = max - min;
        if (span == 0) {
            span = 6.0f;
        }
        // 添加差距，这样即使span为6,最大的也+1，最小的也-1。他们相差就2点了
        max = max + span / 6.0f;
        min = min - span / 6.0f;

        // 获取点集合
        Point[] mPoints = getPoints(items, xlist, max, min, bheight, mYMargintTop);
        Point[] mPoints2 = getPoints(items2, xlist2, max, min, bheight, mYMargintTop);

        // 画线
        mPaint.setColor(context.getResources().getColor(R.color.morning_color));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);
        drawLine(mPoints, canvas, mPaint);
        mPaint.setColor(context.getResources().getColor(R.color.night_color));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);
        drawLine(mPoints2, canvas, mPaint);

        // 画点
        mPaint.setColor(context.getResources().getColor(R.color.morning_color));
        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < mPoints.length; i++) {
            canvas.drawCircle(mPoints[i].x, mPoints[i].y, 12, mPaint);
            @SuppressLint("DrawAllocation")
            String yText = new java.text.DecimalFormat(yFormat).format(items.get(i).getY());
            drawText(yText, mPoints[i].x,
                    mPoints[i].y - DisplayMetricsUtils.dip2px(12), canvas);
        }
        mPaint.setColor(context.getResources().getColor(R.color.night_color));
        for (int i = 0; i < mPoints2.length; i++) {
            canvas.drawCircle(mPoints2[i].x, mPoints2[i].y, 12, mPaint);
            @SuppressLint("DrawAllocation")
            String yText = new java.text.DecimalFormat(yFormat).format(items2.get(i).getY());
            drawText(yText, mPoints2[i].x,
                    mPoints2[i].y - DisplayMetricsUtils.dip2px(12), canvas);
        }
    }

    /**
     * @param items items数据源
     * @param xlist 横轴坐标
     * @param max   最大的max
     * @param min   最小的min
     * @param h     仅仅该折现图的高度
     * @param top   顶部间距
     * @return Point
     */
    private Point[] getPoints(ArrayList<ChartItem> items, ArrayList<Integer> xlist, float max, float min,
                              int h, int top) {
        // 实例化point
        Point[] points = new Point[items.size()];
        // 获取y轴位置,这个逻辑还是很好理解的
        for (int i = 0; i < items.size(); i++) {
            // （1）items.get(i).getY() - min 首先是y的实际天气度数数据 - 最小的y轴数据获取他们的差距作为分子
            double molecule = items.get(i).getY() - min;
            // （2）max - min 获取y轴的范围作为分母
            double denominator = max - min;
            // （3）然后是h具体高度*(1)(2)步骤组成的分子分母获取 具体的 y轴差异 数据
            int yDifference = (int) (h * (molecule / denominator));
            //  （4）最后 h+顶部间距 - y轴差异 就获得y轴数据了
            int y = top + h - yDifference;
            points[i] = new Point(xlist.get(i), y);
        }
        return points;
    }

    private void drawLine(Point[] ps, Canvas canvas, Paint paint) {
        Point startp;
        Point endp;
        for (int i = 0; i < ps.length - 1; i++) {
            startp = ps[i];
            endp = ps[i + 1];
            canvas.drawLine(startp.x, startp.y, endp.x, endp.y, paint);
        }
    }

    private void drawText(String text, int x, int y, Canvas canvas) {
        Paint p = new Paint();
        p.setAlpha(context.getResources().getColor(R.color.white));
        p.setTextSize(DisplayMetricsUtils.sp2px(14));
        p.setTextAlign(Paint.Align.CENTER);
        p.setColor(Color.WHITE);
        canvas.drawText(text, x, y, p);
    }

    public ArrayList<ChartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ChartItem> items) {
        this.items = items;
    }

    public String getyFormat() {
        return yFormat;
    }

    public void setyFormat(String yFormat) {
        this.yFormat = yFormat;
    }

}
