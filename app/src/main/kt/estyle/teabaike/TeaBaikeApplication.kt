package estyle.teabaike

import estyle.base.BaseApplication
import estyle.teabaike.datasource.DbDataSource
import estyle.teabaike.datasource.NetDataSource
import estyle.teabaike.widget.ViewManager

class TeaBaikeApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        NetDataSource.init(this)
        DbDataSource.init(this)
        ViewManager.init(this, R.color.colorAccent)
    }
}
