package estyle.teabaike.rxjava

import android.content.Context
import android.widget.Toast
import estyle.teabaike.R
import estyle.teabaike.exception.ErrorMessageException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.*

open class BaseObserver<T>(
    private val context: Context?,
    private var errorText: String? = null
) : Observer<T> {
    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(it: T) {
    }

    override fun onError(e: Throwable) {
        val applicationContext = context?.applicationContext ?: return
        if (e is ErrorMessageException) errorText = e.message
        Toast.makeText(
            applicationContext,
            errorText ?: applicationContext.getString(R.string.request_fail),
            Toast.LENGTH_LONG
        )
            .show()
    }

    override fun onComplete() {
    }
}