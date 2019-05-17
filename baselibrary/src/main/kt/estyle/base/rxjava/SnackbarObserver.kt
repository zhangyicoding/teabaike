package estyle.base.rxjava

import android.view.View
import com.google.android.material.snackbar.Snackbar
import estyle.base.R
import estyle.base.exception.ErrorMessageException

open class SnackbarObserver<T>(
    private val view: View?,
    private var errorText: String? = null
) : BaseObserver<T>(view?.context, errorText) {
    override fun onError(e: Throwable) {
        view ?: return
        if (e is ErrorMessageException) errorText = e.message
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