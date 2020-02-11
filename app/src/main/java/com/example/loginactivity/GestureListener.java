package com.example.loginactivity;


import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureListener extends GestureDetector.SimpleOnGestureListener
{

   public static String currentGestureDetected;

    // Override s all the callback methods of GestureDetector.SimpleOnGestureListener
    @Override
    public boolean onSingleTapUp(MotionEvent ev) {
        currentGestureDetected="single tap";

        return true;
    }
    @Override
    public void onShowPress(MotionEvent ev) {
        currentGestureDetected="show press";

    }
    @Override
    public void onLongPress(MotionEvent ev) {
        currentGestureDetected="long press";

    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        currentGestureDetected=e1.getPointerCount()+ "  "+e2.getPointerCount();

        return true;
    }
    @Override
    public boolean onDown(MotionEvent ev) {
        currentGestureDetected=" on Down";

        return true;
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        currentGestureDetected=e1.getPointerCount()+ "  "+e2.getPointerCount();
        return true;
    }
}


