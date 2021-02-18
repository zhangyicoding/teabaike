package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.teabaike.datasource.MainDataSource

class MainViewModel(application: Application) : BaseViewModel(application) {

    //    @Inject
    lateinit var dataSource: MainDataSource

    init {
        dataSource = MainDataSource()

//        InjectUtil.dataSourceComponent()
//            .inject(this)
    }

    /**
     * 检查版本
     */
    fun checkVersion() = dataSource.checkVersion()
}