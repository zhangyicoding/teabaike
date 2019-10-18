package estyle.teabaike.test

import androidx.lifecycle.ViewModelProviders
import estyle.teabaike.config.Url
import estyle.teabaike.viewmodel.MainListViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class MainFragmentTest : BaseTest() {

    private lateinit var viewModel: MainListViewModel

    override fun init() {
        viewModel = ViewModelProviders.of(activity)
            .get(MainListViewModel::class.java)
    }

    @Test
    fun loadHeadline() {
        viewModel.loadHeadline()
            .subscribe { assertEquals(3, it.size) }
    }

    @Test
    fun moreList() {
        Url.TYPES.forEach { type ->
            viewModel.moreList(type)
                .subscribe { assertEquals(10, it.size) }
        }
    }
}
