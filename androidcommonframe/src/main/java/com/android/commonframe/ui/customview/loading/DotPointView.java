package com.android.commonframe.ui.customview.loading;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.android.commonframe.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jsion on 16/4/27.
 * <p>
 * you can use like this as a cuostom widget
 * <pre>in you XML</pre>
 * <pre>three attribute may be write</pre>
 * <pre>if you want to change the point size and seleteror and point margin </pre>
 */
public class DotPointView extends LinearLayout {
    private static final int POINT_DE_SIZE = 9;
    private static final float POINT_DE_MARGIN = 12;
    private List<View> dotList;
    private int mDotPintBackgroundRes;
    private int pointSize;
    private int pointMargin;

    public DotPointView(Context context) {
        this(context, null);
    }

    public DotPointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        dotList = new ArrayList<>();
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DotPointView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            if (R.styleable.DotPointView_point_view_selector_background == attr) {
                mDotPintBackgroundRes = typedArray.getResourceId(attr, 0);
            } else if (R.styleable.DotPointView_point_view_point_size == attr) {
                pointSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, POINT_DE_SIZE, metrics));
            } else if (R.styleable.DotPointView_point_view_point_margin == attr) {
                pointMargin = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, POINT_DE_MARGIN, metrics));
            }
        }
        typedArray.recycle();
        pointSize = pointSize == 0 ? (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, POINT_DE_SIZE, metrics) : pointSize;
        pointMargin = pointMargin == 0 ? (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, POINT_DE_MARGIN, metrics) : pointMargin;

    }

    /**
     * add point into point view
     *
     * @param dotPointCount point count;
     */
    public void addPoint2View(int dotPointCount) {
        removeAllViews();
        dotList.clear();
        for (int i = 0; i < dotPointCount; i++) {
            View dotPoint = generateDotPoint();
            dotList.add(dotPoint);
            addView(dotPoint);
        }
        // default first point select
        setDotPointSelecedByInDex(0);
    }

    /**
     * generate dot point
     */
    private View generateDotPoint() {
        View view = new View(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointSize, pointSize);
        params.rightMargin = pointMargin;
        view.setLayoutParams(params);
        view.setBackgroundResource(mDotPintBackgroundRes);
        return view;
    }

    /**
     * set all point selected false
     */
    private void resetPointViewState() {
        for (View pointView : dotList) {
            pointView.setSelected(false);
        }
    }

    /**
     * set point select
     *
     * @param index select index
     */
    public void setDotPointSelecedByInDex(int index) {
        resetPointViewState();
        dotList.get(index).setSelected(true);
    }

}
