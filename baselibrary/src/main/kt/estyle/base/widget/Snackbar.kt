package estyle.base.widget

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

object Snackbar {

    const val LENGTH_INDEFINITE = Snackbar.LENGTH_INDEFINITE
    const val LENGTH_LONG = Snackbar.LENGTH_LONG
    const val LENGTH_SHORT = Snackbar.LENGTH_SHORT

    var color: Int = 0

    fun make(view: View, @StringRes resId: Int, duration: Int): Snackbar {
        return make(view, view.resources.getText(resId), duration)
    }

    fun make(view: View, text: CharSequence, duration: Int): Snackbar =
        Snackbar.make(view, text, duration).apply {
            getView().setBackgroundColor(color)
        }
}