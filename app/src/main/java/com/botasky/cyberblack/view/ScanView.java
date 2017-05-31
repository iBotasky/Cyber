package com.botasky.cyberblack.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by botasky on 27/05/2017.
 */

public class ScanView extends View {

    private static final int MAIN_COLOR_ALPHA = Color.argb(60, 71, 166, 255);
    private static final int MAIN_COLOR = Color.parseColor("#47A7FF");
    private static final int CIRCLE3_COLOR = Color.parseColor("#2A5788");

    private Paint mDefaultPaint;
    private Paint mCircleLinPaint;
    private Paint mShadowPaint;
    private Paint mTextPaint;
    private RectF oval1, oval2, oval3;

    private float centerX;
    private float centerY;

    private int width;
    private int height;


    private int mCenterCircle3Radius;
    private int mCenterCircle2Radius;
    private int mCenterCircle1Radius;

    private int nameRoundWidth;
    private int nameRoundHight;
    private int nameRoundMargin;

    private int rota;


    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (rota == 360) {
                rota = 0;
            }
            rota += 2;
            postInvalidate();
            handler.postDelayed(runnable, 10);
        }
    };

    public ScanView(Context context) {
        super(context);
        init(context);
    }

    public ScanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mCenterCircle3Radius = dip2px(context, 12);
        mCenterCircle2Radius = dip2px(context, 8);
        mCenterCircle1Radius = dip2px(context, 7);

        nameRoundWidth = dip2px(context, 66);
        nameRoundHight = dip2px(context, 11);
        nameRoundMargin = dip2px(context, 12);

        mDefaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDefaultPaint.setStyle(Paint.Style.FILL);
        mDefaultPaint.setShader(null);

        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowPaint.setShader(new SweepGradient(0, 0, MAIN_COLOR_ALPHA, Color.TRANSPARENT));

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(dip2px(context, 16));
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mCircleLinPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleLinPaint.setStyle(Paint.Style.STROKE);
        mCircleLinPaint.setShader(new SweepGradient(0, 0, MAIN_COLOR, Color.TRANSPARENT));
        mCircleLinPaint.setStrokeWidth(dip2px(context, 1));

    }


    public void start() {
        handler.removeCallbacks(runnable);
        handler.post(runnable);
    }


    public void stop() {
        handler.removeCallbacks(runnable);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        centerX = width / 2;
        centerY = height / 2;

        float r = centerX * 0.8f / 4;
        oval1 = new RectF(-r * 2, -r * 2, r * 2, r * 2);
        oval2 = new RectF(-r * 3, -r * 3, r * 3, r * 3);
        oval3 = new RectF(-r * 4, -r * 4, r * 4, r * 4);
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(centerX, centerY);
        canvas.save();
        canvas.rotate(rota);
        drawViews(canvas);
        canvas.restore();
        drawMembers(canvas);
    }

    private void drawViews(Canvas canvas) {
        canvas.drawArc(oval3, 0, 330, false, mShadowPaint);
        mDefaultPaint.setColor(CIRCLE3_COLOR);
        canvas.drawCircle(0, 0, mCenterCircle3Radius, mDefaultPaint);
        mDefaultPaint.setColor(Color.WHITE);
        canvas.drawCircle(0, 0, mCenterCircle2Radius, mDefaultPaint);
        mDefaultPaint.setColor(MAIN_COLOR);
        canvas.drawCircle(0, 0, mCenterCircle1Radius, mDefaultPaint);


        canvas.drawArc(oval1, 0, 300, false, mCircleLinPaint);
        canvas.drawArc(oval2, 0, 300, false, mCircleLinPaint);


    }

    private void drawMembers(Canvas canvas) {
        String content = "Hello,world";

        Rect bounds = new Rect();
        mTextPaint.getTextBounds(content, 0, content.length(), bounds);
        RectF rectF = new RectF(bounds);
        RectF nameRect = new RectF(nameRoundMargin + rectF.left, nameRoundHight, nameRoundMargin * 2 + rectF.right, -nameRoundHight);
        canvas.drawRoundRect(nameRect, 100, 100, mDefaultPaint);
        mTextPaint.setColor(Color.WHITE);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        canvas.drawText(content, nameRect.centerX(), nameRect.centerY() - top / 2 - bottom / 2, mTextPaint);

    }

    private static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
