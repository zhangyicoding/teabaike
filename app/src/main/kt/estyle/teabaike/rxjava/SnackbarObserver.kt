package estyle.teabaike.rxjava

import android.view.View
import com.google.android.material.snackbar.Snackbar
import estyle.teabaike.R
import estyle.teabaike.zhangyi.ZYLog

open class SnackbarObserver<T>(
    private val view: View?,
    private val errorText: String? = null
) : BaseObserver<T>(view?.context, errorText) {
    override fun onError(e: Throwable) {
        view ?: return
        val snackbar = Snackbar.make(
            view,
            errorText ?: view.resources.getString(R.string.request_fail),
            Snackbar.LENGTH_LONG
        ).apply {
            view.setBackgroundResource(R.color.error)
        }
        snackbar.show()
        ZYLog.e("snackbar observer", "error: " + e.message)
    }
}