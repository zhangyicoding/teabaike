package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.base.rxjava.ErrorCodeFunction
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.http.service.MainService

class MainViewModel(application: Application) : BaseViewModel(application) {

//    @Inject
//    lateinit var dataSource: MainDataSource

//    init {
//        InjectUtil.dataSourceComponent()
//            .inject(this)
//    }

    fun checkVersion() = HttpManager.service(MainService::class.java)
        .getLatestVersion()
        .map(ErrorCodeFunction())
        .compose(SchedulersTransformer())
}