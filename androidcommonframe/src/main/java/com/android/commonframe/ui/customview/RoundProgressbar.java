package com.android.commonframe.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.android.commonframe.R;


/**
 * 原型的progressbar
 * Created by feilong.guo on 16/11/11.
 */
public class RoundProgressbar extends View {
    private static final String TAG = "ATVersionUpdateProgressbar";
    private static final float DE_WIDTH = 200.F;
    private static final float DE_HEIGHT = 14.F;
    private int backgroundColor;
    private int progressColor;
    private int cornerSize;
    private Paint backgroundPaint;
    private Paint progressPaint;
    private DisplayMetrics metrics;
    private RectF backgroundRectF;
    private RectF progressRectF;
    private int progressWidth;
    private int width;
    private int height;
    private long total;
    private long currentProgress;

    public RoundProgressbar(Context context) {
        this(context, null);
    }

    public RoundProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundProgressbar, defStyleAttr, 0);
        metrics = getResources().getDisplayMetrics();

        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);

            if (attr == R.styleable.RoundProgressbar_version_update_background_color) {
                backgroundColor = typedArray.getColor(attr, Color.BLACK);
            } else if (attr == R.styleable.RoundProgressbar_version_update_progress_color) {
                progressColor = typedArray.getColor(attr, Color.BLACK);
            }
        }

        typedArray.recycle();
        init();
    }

    public interface OnProgressDoneListener {
        void progressDone();
    }

    private OnProgressDoneListener progressDoneListener;

    public void setProgressDoneListener(OnProgressDoneListener progressDoneListener) {
        this.progressDoneListener = progressDoneListener;
    }

    private void init() {
        backgroundPaint = initPaint(backgroundColor);
        progressPaint = initPaint(progressColor);

        backgroundRectF = new RectF();
        progressRectF = new RectF();
    }

    public Paint initPaint(int paintColor) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(paintColor);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize;
        int heightSize;

        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DE_WIDTH, metrics);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }

        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DE_HEIGHT, metrics);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        cornerSize = h / 2;
        backgroundRectF.set(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackgroundShape(canvas);
        drawProgressShape(canvas);
    }

    private void drawBackgroundShape(Canvas canvas) {
        backgroundRectF.set(0, 0, width, height);
        canvas.drawRoundRect(backgroundRectF, cornerSize, cornerSize, backgroundPaint);
    }

    private void drawProgressShape(Canvas canvas) {
        // NOTICE : MUST CHANG IT * 1.0 MAKE IT TO DOUBLE
        currentProgress = currentProgress > total ? total : currentProgress;
        progressWidth = currentProgress > total ? width : (int) ((currentProgress * 1.0 / total) * width);
        progressRectF.set(0, 0, progressWidth, height);
        canvas.drawRoundRect(progressRectF, cornerSize, cornerSize, progressPaint);
    }

    /**
     * invalidate UI
     *
     * @param total           total
     * @param currentProgress current
     */
    public void setProgress(long total, long currentProgress) {
        this.total = total;
        this.currentProgress = currentProgress;
        invalidate();

        if (progressDoneListener != null && currentProgress >= total && total != 0 && currentProgress != 0) {
            progressDoneListener.progressDone();
        }
    }
}
