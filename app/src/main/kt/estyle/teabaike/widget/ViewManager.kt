package estyle.teabaike.widget

import android.content.Context
import androidx.annotation.ColorRes
import estyle.base.widget.Snackbar
import estyle.base.widget.SwipeRefreshLayout
import estyle.teabaike.R

object ViewManager {

    fun init(context: Context,@ColorRes themeColorResId: Int) {
        val themeColor = context.resources.getColor(themeColorResId)

        SwipeRefreshLayout.colors = themeColor

        Snackbar.color = themeColor

        DotsView.selectedColor = context.resources.getColor(R.color.white)
        DotsView.defaultColor = themeColor
    }
}