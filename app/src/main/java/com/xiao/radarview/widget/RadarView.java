package com.xiao.radarview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.xiao.radarview.R;

/**
 * Created by xy on 2018/12/3.
 */

public class RadarView extends View {

    private Paint radarPaint, textPaint, linePaint, valuePaint;
    private int radarColor, textColor, lineColor, valueColor;
    private boolean isFullRadar;
    private float textSize = 0;
    private float radius;
    private int centerX;
    private int centerY;
    private int countCircle = 0;
    private int countAngle = 0;
    private double angle = 0;
    //起始角度
    private double beginAngle = 0;
    //数据集
    private double[] datas = {};
    //网格颜色（多颜色网格线时设置，非必须）
    private int[] colors = {};
    //标题集
    private String[] titles = {};

    private float maxValue = 0;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadarView, defStyleAttr, 0);
        countCircle = ta.getInteger(R.styleable.RadarView_count_circle, 0);
        countAngle = ta.getInteger(R.styleable.RadarView_count_angle, 0);
        angle = 2 * Math.PI / countAngle;
        maxValue = ta.getFloat(R.styleable.RadarView_max_value, 0);
        radarColor = ta.getColor(R.styleable.RadarView_radar_color, Color.GREEN);
        textColor = ta.getColor(R.styleable.RadarView_text_color, Color.BLACK);
        lineColor = ta.getColor(R.styleable.RadarView_line_color, 0xFF989898);
        valueColor = ta.getColor(R.styleable.RadarView_value_color, Color.BLACK);
        textSize = ta.getDimension(R.styleable.RadarView_text_size, 0);
        isFullRadar = ta.getBoolean(R.styleable.RadarView_is_full_radar, false);
        radarPaint = new Paint();
        textPaint = new Paint();
        linePaint = new Paint();
        valuePaint = new Paint();
        ta.recycle();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(h, w) / 2 * 0.7f;
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFullRadar) {
            radarPaint.setStyle(Paint.Style.FILL);
        } else {
            radarPaint.setStyle(Paint.Style.STROKE);
        }
        radarPaint.setColor(radarColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.STROKE);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(lineColor);
        valuePaint.setColor(valueColor);
        valuePaint.setStyle(Paint.Style.STROKE);
        valuePaint.setStrokeWidth(5);
        drawPolygon(canvas);
        drawText(canvas);
        drawLines(canvas);
        drawRegion(canvas);
    }

    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        float r = radius / (countCircle);
        for (int i = countCircle; i > 0; i--) {
            float curR = r * i;
            path.reset();
            for (int j = 0; j < countAngle; j++) {
                float x = (float) (centerX + curR * Math.cos(beginAngle + angle * j));
                float y = (float) (centerY + curR * Math.sin(beginAngle + angle * j));
                if (j == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
            if (i > 0 && i <= colors.length) {
                radarPaint.setColor(colors[i - 1]);
            }
            path.close();
            canvas.drawPath(path, radarPaint);
        }
    }

    private void drawText(Canvas canvas) {
        double curAngle = 0;
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent; //文字的高度
        for (int i = 0; i < titles.length; i++) {
            float dis = textPaint.measureText(titles[i]);//获取文本长度
            int textRedius = (int) Math.sqrt(dis * dis + fontHeight * fontHeight) / 2;
            curAngle = beginAngle + angle * i;
            float x = (float) (centerX + (textRedius + radius) * Math.cos(curAngle));
            float y = (float) (centerY + (textRedius + radius) * Math.sin(curAngle));
            float correctX = x - dis / 2;
            float correctY = y + fontHeight / 2;
            canvas.drawText(titles[i], correctX, correctY, textPaint);
        }

    }

    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < countAngle; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            float x = (float) (centerX + radius * Math.cos(beginAngle + angle * i));
            float y = (float) (centerY + radius * Math.sin(beginAngle + angle * i));
            path.lineTo(x, y);
            canvas.drawPath(path, linePaint);
        }
    }

    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        valuePaint.setAlpha(127);
        for (int i = 0; i < datas.length; i++) {
            double percent = datas[i] / maxValue;
            float x = (float) (centerX + radius * Math.cos(beginAngle + angle * i) * percent);
            float y = (float) (centerY + radius * Math.sin(beginAngle + angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, centerY);
            } else {
                path.lineTo(x, y);
            }
        }
        path.close();
        canvas.drawPath(path, valuePaint);
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public void setDatas(double[] datas) {
        this.datas = datas;
    }

    public void setBeginAngle(double beginAngle) {
        this.beginAngle = beginAngle;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }
}
