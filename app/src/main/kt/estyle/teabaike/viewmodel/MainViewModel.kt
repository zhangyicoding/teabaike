package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.teabaike.datasource.MainDataSource
import estyle.teabaike.util.InjectUtil
import javax.inject.Inject

class MainViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var dataSource: MainDataSource

    init {
        InjectUtil.dataSourceComponent()
            .inject(this)
    }

    fun checkVersion() = dataSource.checkVersion()
}