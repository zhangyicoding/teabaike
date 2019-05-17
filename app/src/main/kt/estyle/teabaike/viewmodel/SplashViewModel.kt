package estyle.teabaike.viewmodel

import android.app.Application
import android.content.Context
import estyle.base.BaseViewModel
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.TeaBaikeApplication
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SplashViewModel(application: Application) : BaseViewModel(application) {

    fun delay(second: Long) =
        Observable.timer(second, TimeUnit.SECONDS)
            .map {
                getApplication<TeaBaikeApplication>()
                    .getSharedPreferences("config", Context.MODE_PRIVATE)
                    .getBoolean(IS_FIRST_LOGIN, true)
            }
            .compose(SchedulersTransformer())

    companion object {
        const val IS_FIRST_LOGIN = "is_first_login"
    }
}