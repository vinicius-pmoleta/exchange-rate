package com.exchangerate.features.home.presentation

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class NonScrollableViewPager(content: Context, attributes: AttributeSet) : ViewPager(content, attributes) {

    var isSwipeEnabled = false

    init {
        isSwipeEnabled = true
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return if (isSwipeEnabled) super.onTouchEvent(ev) else false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (isSwipeEnabled) super.onInterceptTouchEvent(ev) else false
    }

}