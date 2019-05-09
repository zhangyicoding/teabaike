package estyle.teabaike.util

import android.app.Application

import com.squareup.leakcanary.LeakCanary

object LeakUtil {

    fun init(application: Application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            return
        }
        LeakCanary.install(application)
    }
}
