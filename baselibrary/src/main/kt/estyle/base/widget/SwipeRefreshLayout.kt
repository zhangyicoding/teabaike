package estyle.base.widget

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class SwipeRefreshLayout(
    context: Context,
    attrs: AttributeSet?
) : SwipeRefreshLayout(context, attrs) {

    init {
        setColorSchemeColors(colors)
    }

    companion object {
        var colors: Int = 0
    }
}
