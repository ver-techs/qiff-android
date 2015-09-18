package com.ver_techs.qiff_android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView
{
    float startY;

    public CustomScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e)
    {
        onTouchEvent(e);
        if (e.getAction() == MotionEvent.ACTION_DOWN) startY = e.getY();
        return (e.getAction() == MotionEvent.ACTION_MOVE) && (Math.abs(startY - e.getY()) > 50);
    }
}