package estyle.base.rxjava

import android.text.TextUtils
import estyle.base.entity.NetEntity
import estyle.base.exception.ErrorMessageException
import io.reactivex.functions.Consumer

class ErrorMessageConsumer<T : NetEntity> : Consumer<T> {
    override fun accept(it: T) {
        if (!TextUtils.equals(it.errorMessage, SUCCESS)) {
            throw ErrorMessageException(it.errorMessage!!)
        }
    }

    companion object {
        private const val SUCCESS = "success"
    }
}