package com.wspateam.playgo.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton

class CustomButton(context: Context, attrs: AttributeSet?) : AppCompatButton(context, attrs)
{
    private val toggledSize = 0.9f
    private val animTimeUp = 100L
    private val animTimeDown = 30L
    private val normalSize = 1f

    init
    {
        this.setOnTouchListener { v, event ->
            when(event.actionMasked)
            {
                MotionEvent.ACTION_DOWN ->
                {
                    v.animate().scaleX(toggledSize).scaleY(toggledSize).setDuration(animTimeDown).start()
                    false
                }
                MotionEvent.ACTION_UP ->
                {
                    v.animate().scaleX(normalSize).scaleY(normalSize).setDuration(animTimeUp).start()
                    false
                }
            }
            false
        }
    }
}