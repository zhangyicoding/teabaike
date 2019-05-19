package estyle.teabaike

import estyle.base.BaseApplication
import estyle.teabaike.api.DatabaseApi
import estyle.teabaike.api.NetApi
import estyle.teabaike.widget.ViewManager

class TeaBaikeApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        NetApi.init(this)
        DatabaseApi.init(this)
        ViewManager.init(this, R.color.colorAccent)
    }
}
