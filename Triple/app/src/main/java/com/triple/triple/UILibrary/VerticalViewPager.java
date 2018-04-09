package com.triple.triple.UILibrary;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.io.Serializable;

public class VerticalViewPager extends ViewPager implements Serializable {

    private static final String TAG = "DummyViewPager";
    private int baseScrollX;
    private int currentScrollState;

    public VerticalViewPager(Context context) {
        this(context, null);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(false, new DefaultTransformer());// vertical scroll trick
        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    baseScrollX = getScrollX();
                }
                currentScrollState = state;
            }
        });

    }

    public int getBaseScrollX() {
        return baseScrollX;
    }

    public void setBaseScrollX(int baseScrollX) {
        this.baseScrollX = baseScrollX;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (currentScrollState == ViewPager.SCROLL_STATE_IDLE) {
            baseScrollX = getScrollX();
        }
    }
}
