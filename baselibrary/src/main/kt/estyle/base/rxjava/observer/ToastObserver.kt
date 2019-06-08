package estyle.base.rxjava.observer

import estyle.base.R
import estyle.base.util.ToastUtil

open class ToastObserver<T>(errorText: String? = null) : AbstractErrorObserver<T>(errorText) {

    override fun error(e: Throwable, errorText: String?) {
        ToastUtil.showLong(errorText ?: ToastUtil.context.getString(R.string.request_fail))
    }
}