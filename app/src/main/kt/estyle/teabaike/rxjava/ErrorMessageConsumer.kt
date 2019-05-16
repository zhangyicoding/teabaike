package estyle.teabaike.rxjava

import android.text.TextUtils
import estyle.teabaike.entity.BaseEntity
import estyle.teabaike.exception.ErrorMessageException
import io.reactivex.functions.Consumer

class ErrorMessageConsumer<T : BaseEntity> : Consumer<T> {
    override fun accept(it: T) {
        if (!TextUtils.equals(it.errorMessage, SUCCESS)) {
            throw ErrorMessageException(it.errorMessage!!)
        }
    }

    companion object {
        private const val SUCCESS = "success"
    }
}