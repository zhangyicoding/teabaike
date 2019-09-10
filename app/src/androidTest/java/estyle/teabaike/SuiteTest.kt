package estyle.teabaike

import estyle.teabaike.test.*
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityTest::class,
    MainFragmentTest::class,
    SearchActivityTest::class,
    ContentActivityTest::class,
    CollectionActivityTest::class,
    FeedbackActivityTest::class
)
class SuiteTest