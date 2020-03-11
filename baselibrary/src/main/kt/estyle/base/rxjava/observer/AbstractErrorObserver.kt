package estyle.base.rxjava.observer

import estyle.base.exception.ErrorCodeException
import estyle.base.zhangyi.ZYLog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class AbstractErrorObserver<T>(private var errorText: String? = null) : Observer<T> {
    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(it: T) {
    }

    override fun onError(e: Throwable) {
        if (e is ErrorCodeException) errorText = e.message

        for ((i, s) in e.stackTrace.withIndex()) {
            ZYLog.e(s)
        }

        error(e, errorText)
    }

    override fun onComplete() {
    }

    abstract fun error(e: Throwable, errorText: String?)
}