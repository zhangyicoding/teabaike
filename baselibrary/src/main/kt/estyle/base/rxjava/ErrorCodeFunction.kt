package estyle.base.rxjava

import estyle.base.entity.HttpEntity
import estyle.base.exception.ErrorCodeException
import io.reactivex.functions.Function

class ErrorCodeFunction<R> : Function<HttpEntity<R>, R> {
    override fun apply(httpEntity: HttpEntity<R>): R {
        if (httpEntity.errorCode != HttpEntity.SUCCESS) {
            throw ErrorCodeException(httpEntity.errorMessage)
        }
        return httpEntity.data!!
    }
}