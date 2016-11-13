package com.android.commonframe.ui.customview.loading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.android.commonframe.R;


/**
 * Created by jsion on 16/8/8.
 */

public class CircleView extends View {
    private static final float DE_VIEW_SIZE = 200.F;
    private static final float VIEW_PADDING_SACLE = 1 / 2.5f;
    private int circleWidth;
    private int circleCorner;
    private int viewBgColor;
    private int viewWidth;
    private int viewHeight;
    private Paint circlePaint;
    private int[] doughnutColors;
    private Paint bgPaint;
    private RectF rectBg;
    private float rotateDegree;
    private ValueAnimator valueAnimator;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleView, defStyleAttr, R.style.def_circleview);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            if (R.styleable.CircleView_circle_width == attr) {
                circleWidth = typedArray.getDimensionPixelOffset(attr, 0);
            } else if (R.styleable.CircleView_circle_corner == attr) {
                circleCorner = typedArray.getDimensionPixelOffset(attr, 0);
            } else if (R.styleable.CircleView_view_bg_color == attr) {
                viewBgColor = typedArray.getColor(attr, Color.BLACK);
            }
        }
        typedArray.recycle();
        init();
    }

    private void init() {
        circlePaint = createPaint(Color.BLACK, circleWidth, Paint.Style.STROKE);
        bgPaint = createPaint(viewBgColor, 0, Paint.Style.FILL);

        doughnutColors = new int[]{
                ContextCompat.getColor(getContext(), android.R.color.holo_green_light),
                ContextCompat.getColor(getContext(), android.R.color.holo_orange_light),
                ContextCompat.getColor(getContext(), android.R.color.holo_red_light),
                ContextCompat.getColor(getContext(), android.R.color.holo_purple)
        };
        valueAnimator = ValueAnimator.ofFloat(0, 1.F);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rotateDegree = 360 * Float.valueOf(valueAnimator.getAnimatedValue().toString());
                invalidate();
            }
        });
        valueAnimator.setDuration(800);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize;
        int heightSize;
        if (widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DE_VIEW_SIZE, getResources().getDisplayMetrics());
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DE_VIEW_SIZE, getResources().getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        rectBg = new RectF(0, 0, viewWidth, viewHeight);
        circlePaint.setShader(new SweepGradient(viewWidth, viewHeight, doughnutColors, null));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(rectBg, circleCorner, circleCorner, bgPaint);
        canvas.rotate(rotateDegree, viewWidth / 2, viewHeight / 2);
        canvas.drawCircle(viewWidth / 2, viewHeight / 2, viewHeight * (1 - VIEW_PADDING_SACLE) / 2, circlePaint);
    }

    private Paint createPaint(int color, int strokeWidth, Paint.Style style) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStyle(style);
        return paint;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setAnimation(AnimStatus.CANCEL);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setAnimation(AnimStatus.START);
    }

    public enum AnimStatus {
        START, END, CANCEL
    }

    private void setAnimation(AnimStatus animStatus) {
        if (valueAnimator == null) {
            return;
        }
        if (animStatus == AnimStatus.START) {
            valueAnimator.start();
        } else if (animStatus == AnimStatus.CANCEL) {
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.cancel();
        }
    }
}
