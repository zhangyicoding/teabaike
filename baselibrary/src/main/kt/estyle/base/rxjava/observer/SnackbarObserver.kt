package estyle.base.rxjava.observer

import android.view.View
import com.google.android.material.snackbar.Snackbar
import estyle.base.R

open class SnackbarObserver<T>(private val view: View?, errorText: String? = null) :
    AbstractErrorObserver<T>(errorText) {

    override fun error(e: Throwable, errorText: String?) {
        view ?: return
        val snackbar = Snackbar.make(
            view,
            errorText ?: view.resources.getString(R.string.request_fail),
            Snackbar.LENGTH_LONG
        ).apply {
            view.setBackgroundResource(R.color.error)
        }
        snackbar.show()
    }
}