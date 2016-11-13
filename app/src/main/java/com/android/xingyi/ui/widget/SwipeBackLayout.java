package com.android.xingyi.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.Scroller;

import com.android.commonframe.tools.MeizuUtil;
import com.android.commonframe.tools.ScreenUtil;
import com.android.xingyi.R;

import java.util.LinkedList;
import java.util.List;

public class SwipeBackLayout extends FrameLayout implements OnGestureListener, OnTouchListener {
    private final static float LEFT_SIDE_DISTANCE = 200;
    private static int Horizontal_MIN_DISTANCE = 180;
    private static float MIN_VELOCIT_X = 2500f;
    private static long FING_FINISH_GAP = 400;
    private static float MIN_ANGLE = 0.8f;
    private View mContentView;
    private int mTouchSlop;
    private int downX;
    private int downY;
    private int tempX;
    private Scroller mScroller;
    private int viewWidth;
    private boolean isEnableGesture = true;
    private boolean isSilding;
    private boolean isFinish;
    private boolean isFlingFinish;
    private boolean isNoScrollView = true;
    private boolean firstMove = false;
    private int finishX, screenWidth, screenHeight;
    private float distanceX, distanceY;
    private double angle;
    private long startTap, endTap;
    private Drawable mShadowDrawable;
    private Activity mActivity;
    private float alpha;
    private List<ViewPager> mViewPagers = new LinkedList<ViewPager>();
    private List<HorizontalScrollView> svs = new LinkedList<HorizontalScrollView>();
    @SuppressWarnings("deprecation")
    private List<Gallery> gallerys = new LinkedList<Gallery>();
    private GestureDetector gestureDetector;
    private View nightView = null;
    private boolean hasLoadNight;
    private Sliding currentMode = Sliding.LEFT_SIDE;
    private float disToucheX;

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);

        screenWidth = ScreenUtil.getScreenWidth(context);
        screenHeight = ScreenUtil.getScreenHeight(context);
        finishX = (int) (screenWidth / 3f);
        mShadowDrawable = getResources().getDrawable(R.mipmap.bg_shadow_left);
        this.setOnTouchListener(this);
    }

    public void setSlidingMode(Sliding mode) {
        this.currentMode = mode;
    }

    public void attachToActivity(Activity activity) {
        mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
        int background = a.getResourceId(0, 0);
        a.recycle();
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
        gestureDetector = new GestureDetector(activity, this);
        screenWidth -= 10;
    }


    private void setContentView(View decorChild) {
        mContentView = (View) decorChild.getParent();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isEnableGesture)
            return super.onInterceptTouchEvent(ev);
        isNoScrollView = true;
        ViewPager mViewPager = getTouchViewPager(mViewPagers, ev);
        if (mViewPager != null && mViewPager.getCurrentItem() != 0) {
            isNoScrollView = false;
            return super.onInterceptTouchEvent(ev);
        }
        HorizontalScrollView mScroview = getToutchHScrollView(svs, ev);
        if (mScroview != null && mScroview.getScrollX() != 0) {
            isNoScrollView = false;
            return super.onInterceptTouchEvent(ev);
        }
        Gallery mGallery = getTouchViewGallery(gallerys, ev);
        if (mGallery != null && mGallery.canScrollHorizontally(-1)) {
            isNoScrollView = false;
            return super.onInterceptTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                tempX = downX;
                downY = (int) ev.getRawY();
                if ((currentMode == Sliding.LEFT_SIDE && downX > LEFT_SIDE_DISTANCE) || currentMode == Sliding.NONE) {
                    return super.onInterceptTouchEvent(ev);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getRawX();
                angle = Math.atan2(Math.abs((int) ev.getRawY() - downY), moveX - downX);
                if (currentMode == Sliding.LEFT_SIDE) {
                    if (downX < LEFT_SIDE_DISTANCE && moveX - downX > mTouchSlop && angle < MIN_ANGLE) {
                        return true;
                    }
                } else if (currentMode == Sliding.ALL) {
                    if (moveX - downX > mTouchSlop && angle < MIN_ANGLE) {
                        return true;
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstMove = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (firstMove) {
                    downX = (int) event.getRawX();
                    firstMove = false;
                }
                int moveX = (int) event.getRawX();
                int deltaX = tempX - moveX;
                int diretionRight = moveX - downX;
                int scrollX = mContentView.getScrollX();
                tempX = moveX;
                angle = Math.atan2(Math.abs((int) event.getRawY() - downY), moveX - downX);
                if (diretionRight > mTouchSlop && angle < MIN_ANGLE) {
                    isSilding = true;
                }

                if (isSilding && scrollX <= 0) {
                    if (scrollX + deltaX >= 0) {
                        mContentView.scrollTo(0, 0);
                    } else {
                        mContentView.scrollBy(deltaX, 0);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isSilding = false;
                if ((isNoScrollView && isFlingFinish) || mContentView.getScrollX() <= -viewWidth / 2) {
                    isFinish = true;
                    scrollRight();
                } else {
                    scrollOrigin();
                    isFinish = false;
                }
                break;
        }

        return true;
    }

    private void getAllGallery(List<Gallery> gallerys, ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof Gallery) {
                gallerys.add((Gallery) child);
            } else if (child instanceof ViewGroup) {
                getAllGallery(gallerys, (ViewGroup) child);
            }
        }
    }

    private void getAlLViewPager(List<ViewPager> mViewPagers, ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewPager) {
                mViewPagers.add((ViewPager) child);
            } else if (child instanceof ViewGroup) {
                getAlLViewPager(mViewPagers, (ViewGroup) child);
            }
        }
    }

    private void getAlLHScrollView(List<HorizontalScrollView> mViewPagers, ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof HorizontalScrollView) {
                svs.add((HorizontalScrollView) child);
            } else if (child instanceof ViewGroup) {
                getAlLHScrollView(svs, (ViewGroup) child);
            }
        }
    }

    private Gallery getTouchViewGallery(List<Gallery> gallerys, MotionEvent ev) {
        if (gallerys == null || gallerys.size() == 0) {
            return null;
        }
        Rect mRect = new Rect();
        for (Gallery v : gallerys) {
            v.getGlobalVisibleRect(mRect);

            if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
                return v;
            }
        }
        return null;
    }

    private ViewPager getTouchViewPager(List<ViewPager> mViewPagers, MotionEvent ev) {
        if (mViewPagers == null || mViewPagers.size() == 0) {
            return null;
        }
        Rect mRect = new Rect();
        for (ViewPager v : mViewPagers) {
            v.getGlobalVisibleRect(mRect);

            if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
                return v;
            }
        }
        return null;
    }

    private HorizontalScrollView getToutchHScrollView(List<HorizontalScrollView> svs, MotionEvent ev) {
        if (svs == null || svs.size() == 0) {
            return null;
        }
        Rect mRect = new Rect();
        for (HorizontalScrollView v : svs) {
            v.getGlobalVisibleRect(mRect);
            if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
                return v;
            }
        }
        return null;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            viewWidth = this.getWidth();

            getAlLViewPager(mViewPagers, this);
            getAlLHScrollView(svs, this);
            getAllGallery(gallerys, this);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mShadowDrawable != null && mContentView != null) {
            int sleft = mContentView.getLeft() - screenWidth;
            int sright = sleft + screenWidth;
            int left = mContentView.getLeft() - mShadowDrawable.getIntrinsicWidth();
            int right = left + mShadowDrawable.getIntrinsicWidth();
            int top = mContentView.getTop();
            int bottom = mContentView.getBottom();
            int curX = Math.abs(mScroller.getCurrX());
            if (curX > screenWidth) {
                return;
            }
            if (MeizuUtil.isMeizuMx2OrHigher()) {
                top = 0;
            }
            mShadowDrawable.setBounds(left, top, right, bottom);
            mShadowDrawable.draw(canvas);
            float moveX = Math.abs(mContentView.getScrollX());
            alpha = 200 * (1 - moveX / (float) screenWidth);
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setAlpha((int) alpha);
            RectF rect = new RectF(sleft, top, sright, bottom);
            canvas.drawRect(rect, paint);
        }

    }

    private void scrollRight() {
        final int delta = (viewWidth + mContentView.getScrollX());
        float duration = Math.abs(delta);
        if (isFlingFinish) {
            duration /= 3;
        }
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta + 1, 0, (int) duration);
        postInvalidate();
    }

    private void scrollOrigin() {
        int delta = mContentView.getScrollX();
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta, 0, Math.abs(delta));
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mContentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();

            if (mScroller.isFinished() && isFinish) {
                mActivity.finish();
            }
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {

        startTap = System.currentTimeMillis();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        endTap = System.currentTimeMillis();
        long gap = endTap - startTap;
        isFlingFinish = false;
        distanceX = e2.getX() - e1.getX();
        distanceY = Math.abs(e2.getY() - e1.getY());
        angle = Math.atan2(distanceY, distanceX);
        if ((currentMode == Sliding.LEFT_SIDE && downX > LEFT_SIDE_DISTANCE) || currentMode == Sliding.NONE) {
        } else if (e1.getX() - e2.getX() > Horizontal_MIN_DISTANCE && Math.abs(velocityX) > MIN_VELOCIT_X) {
            //  towards the left
        } else if ((e2.getX() - e1.getX() > finishX || Math.abs(velocityX) > MIN_VELOCIT_X) && angle < 0.5) //  horizontal quickly slide
        //  finish current page
        {
            //  towards the right
            if (isNoScrollView && gap < FING_FINISH_GAP) {
                isFinish = true;
                isFlingFinish = true;
                scrollRight();
            }
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return isEnableGesture ? gestureDetector.onTouchEvent(event) : false;
    }

    public void setEnableGesture(boolean b) {
        isEnableGesture = b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (isEnableGesture) {
            gestureDetector.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public enum Sliding {
        LEFT_SIDE,
        NONE,
        ALL;
    }
}
