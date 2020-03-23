package estyle.teabaike.test

import androidx.test.rule.ActivityTestRule
import estyle.teabaike.activity.GuideActivity
import estyle.teabaike.rule.RxJavaTestRule
import org.junit.Before
import org.junit.Rule

abstract class BaseTest {

    @Rule
    @JvmField
    val rxJavaRule = RxJavaTestRule()

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(GuideActivity::class.java)

    protected lateinit var activity: GuideActivity

    @Before
    fun before() {
        activity = activityRule.activity
        init()
    }

    abstract fun init()
}