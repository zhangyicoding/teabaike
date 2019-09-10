package estyle.teabaike.test

import androidx.lifecycle.ViewModelProviders
import estyle.teabaike.viewmodel.FeedbackViewModel
import org.junit.Test

class FeedbackActivityTest : BaseTest() {

    private lateinit var viewModel: FeedbackViewModel

    override fun init() {
        viewModel = ViewModelProviders.of(activity)
            .get(FeedbackViewModel::class.java)
    }

    @Test
    fun checkVersion() {
        viewModel.feedback("标题", "内容")
            .subscribe {
                println(it)
            }
    }
}
