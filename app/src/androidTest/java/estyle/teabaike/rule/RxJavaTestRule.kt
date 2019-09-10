package estyle.teabaike.rule

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

class RxJavaTestRule : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {

            override fun evaluate() {
                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                RxAndroidPlugins.reset()
                RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
                base.evaluate()
            }
        }
    }
}