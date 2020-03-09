package estyle.teabaike

import estyle.base.BaseApplication
import estyle.teabaike.config.Url.BASE_URL
import estyle.teabaike.util.NetworkUtil
import estyle.teabaike.widget.ViewManager

class TeaBaikeApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        NetworkUtil.init(this, BASE_URL)
        TeaBaikeDatabase.init(this)
        ViewManager.init(this, R.color.colorAccent)
    }
}
