package com.ofirkp.sockettest

import android.content.Context
import android.view.MotionEvent
import android.text.method.Touch.onTouchEvent
import android.support.v4.view.ViewPager
import android.util.AttributeSet


class MyViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var pagingEnabled: Boolean = true

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (pagingEnabled) {
            super.onTouchEvent(event)
        } else false

    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (pagingEnabled) {
            super.onInterceptTouchEvent(event)
        } else false

    }

    fun setPagingEnabled(enabled: Boolean) {
        pagingEnabled = enabled
    }
}