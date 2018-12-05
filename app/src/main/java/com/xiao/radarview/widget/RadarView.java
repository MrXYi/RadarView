package com.xiao.radarview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xy on 2018/12/3.
 */

public class RadarView extends View {

    private Paint radarPaint, textPaint, linePaint, valuePaint;
    private float radius;
    private int centerX;
    private int centerY;
    private int countCircle = 4;
    private int countAngle = 11;
    private double angle = (2 * Math.PI) / 11;
    //    private double beginAngle = Math.PI / 14;
    private double beginAngle = 0;
    private double[] data = {2, 4, 2, 3, 3, 4, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    private int[] colors = {0xFF575757, 0xFF888888, 0xFFAEAEAE, 0xFFCECECE};
    private String[] titles = {"助攻", "物理", "魔法", "防御", "金钱", "击杀", "生存",
            "测试测测", "测试测测", "测试测测", "测试测测", "测试测测", "测试测测", "测试测测", "测试测测", "测试测测"};

    private float maxValue = 4;

    public RadarView(Context context) {
        super(context);
        init();
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        radarPaint = new Paint();
        radarPaint.setStyle(Paint.Style.FILL);
        radarPaint.setColor(Color.GREEN);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(48);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(0xFF989898);

        valuePaint = new Paint();
        valuePaint.setColor(Color.BLACK);
        valuePaint.setStyle(Paint.Style.STROKE);
        valuePaint.setStrokeWidth(5);
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
            radarPaint.setColor(colors[i - 1]);
            path.close();
            canvas.drawPath(path, radarPaint);
        }
    }

    private void drawText(Canvas canvas) {
        double curAngle = 0;
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent; //文字的高度
        //修正标题
        for (int i = 0; i < countAngle; i++) {
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
        for (int i = 0; i < countAngle; i++) {
            double percent = data[i] / maxValue;
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

}
