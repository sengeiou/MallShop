package com.mike.baselib.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 拦截多点触控 和 横向滑动
 */

public class VerticalSwipeRefreshLayout extends SwipeRefreshLayout {
    private int scaleTouchSlop;
    private float preX;
    private int mode = 0;
    float oldDist;

    public VerticalSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scaleTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preX = ev.getX();
                mode = 1;
                break;
            case MotionEvent.ACTION_UP:
                mode = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                float instanceX = Math.abs(moveX - preX);
                Log.i("refresh...", "move: instanceX:" + instanceX + "=(moveX:" + moveX + " - preX:" + preX + ") , scaleTouchSlop:" + scaleTouchSlop);
                // 容差值大概是24，再加上60
                if (instanceX > scaleTouchSlop + 60) {
                    return false;
                }
                if (mode >= 2) {//多点触控
                    float newDist = spacing(ev);
                    if (newDist > oldDist + 1) {
                        oldDist = newDist;
                        return false;
                    }
                    if (newDist < oldDist - 1) {
                        oldDist = newDist;
                        return false;
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode -= 1;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if(mode>=2){
                    oldDist = spacing(ev);
                }
                mode += 1;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
}

