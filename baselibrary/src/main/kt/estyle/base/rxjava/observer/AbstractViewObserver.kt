package estyle.base.rxjava.observer

import android.view.View

abstract class AbstractViewObserver<T>(
    view: View?,
    errorText: String? = null
) :
    SnackbarObserver<T>(view, errorText) {

    override fun onNext(it: T) {
        gone()
        super.onNext(it)
    }

    override fun onError(e: Throwable) {
        gone()
        super.onError(e)
    }

    override fun onComplete() {
        gone()
        super.onComplete()
    }

    protected abstract fun gone()
}