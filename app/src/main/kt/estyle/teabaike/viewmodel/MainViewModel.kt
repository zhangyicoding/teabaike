package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.base.rxjava.ErrorMessageConsumer
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.net.MainService
import estyle.teabaike.util.NetworkUtil

class MainViewModel(application: Application) : BaseViewModel(application) {

//    @Inject
//    lateinit var dataSource: MainDataSource

//    init {
//        InjectUtil.dataSourceComponent()
//            .inject(this)
//    }

    fun checkVersion() = NetworkUtil.service(MainService::class.java)
        .getLatestVersion()
        .doOnNext(ErrorMessageConsumer())
        .map { it.data }
        .compose(SchedulersTransformer())
}