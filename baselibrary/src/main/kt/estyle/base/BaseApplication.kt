package estyle.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import estyle.base.util.LeakUtil

open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        LeakUtil.init(this)
    }
}