package estyle.teabaike.rxjava

import estyle.teabaike.entity.BaseEntity
import estyle.teabaike.exception.ErrorCodeException
import io.reactivex.functions.Consumer

/**
 * Created by zhangyi7@360.cn on 2019/5/16
 * Description:
 */
class ErrorCodeInterceptor<T : BaseEntity> : Consumer<T> {
    override fun accept(it: T) {
        if (it.errorCode != 0) {
            throw ErrorCodeException(it.errorMessage!!)
        }
    }
}