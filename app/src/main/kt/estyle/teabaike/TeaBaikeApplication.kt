package estyle.teabaike

import estyle.base.BaseApplication
import estyle.teabaike.datasource.database.DatabaseManager
import estyle.teabaike.datasource.http.HttpManager
import estyle.teabaike.widget.ViewManager

class TeaBaikeApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        HttpManager.init(this)
        DatabaseManager.init(this)
        ViewManager.init(this, R.color.colorAccent)
    }
}
