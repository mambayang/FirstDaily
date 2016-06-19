package com.yrg.firstdaily.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

/**
 * Created by yrg on 2016/6/19.
 */
public class MyScrollView extends ScrollView {

    private int downX;
    private int downY;
    private int touchSlop;
    private OnScrollChangeListener scrollChangeListener;

    public MyScrollView(Context context) {
        super(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setScrollChangeListener(OnScrollChangeListener scrollChangeListener) {
        this.scrollChangeListener = scrollChangeListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollChangeListener != null) {
            scrollChangeListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollChangeListener != null && scrollY != 0) {
            scrollChangeListener.onScrollBottom(clampedY);
        }
    }

    public interface OnScrollChangeListener {
        void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy);

        void onScrollBottom(boolean isBottom);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                if (Math.abs(moveY - downY) > touchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
