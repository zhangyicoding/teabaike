package estyle.base.rxjava

import android.view.View
import com.google.android.material.snackbar.Snackbar
import estyle.base.R
import estyle.base.exception.ErrorMessageException
import estyle.base.zhangyi.ZYLog

open class SnackbarObserver<T>(
    private val view: View?,
    private var errorText: String? = null
) : BaseObserver<T>(errorText) {
    override fun onError(e: Throwable) {
        view ?: return
        if (e is ErrorMessageException) errorText = e.message
        ZYLog.e("snackbar observer error: $errorText")
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