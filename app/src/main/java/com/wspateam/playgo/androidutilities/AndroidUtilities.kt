package com.wspateam.playgo.androidutilities

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

class AndroidUtilities
{
    companion object AndroidUtilities
    {
        fun hideKeyboard(view: View)
        {
            val visLocalRect = Rect()
            val visGlobalRect = Rect()

            view.getGlobalVisibleRect(visGlobalRect)
            view.getLocalVisibleRect(visLocalRect)

            if (visGlobalRect != visLocalRect)
            {
                val currentFocus = view.findFocus()
                val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

}