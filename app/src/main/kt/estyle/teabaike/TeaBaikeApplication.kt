package estyle.teabaike

import estyle.base.BaseApplication
import estyle.teabaike.api.DatabaseApi
import estyle.teabaike.api.NetApi
import estyle.teabaike.dagger.component.DaggerTeaBaikeComponent
import estyle.teabaike.dagger.component.TeaBaikeComponent
import estyle.teabaike.widget.ViewManager

class TeaBaikeApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        NetApi.init(this)
        DatabaseApi.init(this)
        ViewManager.init(this, R.color.colorAccent)
    }

    // TODO 接下来关联net和db的api
    private fun initDagger() {
        teaBaikeComponent = DaggerTeaBaikeComponent
            .builder()
//            .dataModule(DataModule(this))
            .build()
    }

    companion object {

        lateinit var teaBaikeComponent: TeaBaikeComponent
            private set
    }
}
