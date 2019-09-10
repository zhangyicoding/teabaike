package estyle.teabaike.test

import androidx.test.rule.ActivityTestRule
import estyle.teabaike.rule.RxJavaTestRule
import estyle.teabaike.activity.WelcomeActivity
import org.junit.Before
import org.junit.Rule

abstract class BaseTest {

    @Rule
    @JvmField
    val rxJavaRule = RxJavaTestRule()

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(WelcomeActivity::class.java)

    protected lateinit var activity: WelcomeActivity

    @Before
    fun before() {
        activity = activityRule.activity
        init()
    }

    abstract fun init()
}