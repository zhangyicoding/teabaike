package estyle.base.rxjava

import android.content.Context
import android.widget.Toast
import estyle.base.R
import estyle.base.exception.ErrorMessageException
import estyle.base.util.ToastUtil
import estyle.base.zhangyi.ZYLog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

open class BaseObserver<T>(private var errorText: String? = null) : Observer<T> {
    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(it: T) {
    }

    override fun onError(e: Throwable) {
        if (e is ErrorMessageException) errorText = e.message
        ZYLog.e("base observer error: $errorText")
        ToastUtil.showLong(errorText ?: ToastUtil.context.getString(R.string.request_fail))
    }

    override fun onComplete() {
    }
}