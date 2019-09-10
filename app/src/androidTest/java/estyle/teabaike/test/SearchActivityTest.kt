package estyle.teabaike.test

import androidx.lifecycle.ViewModelProviders
import estyle.teabaike.viewmodel.SearchViewModel
import org.junit.Assert.assertNotNull
import org.junit.Test

class SearchActivityTest : BaseTest() {

    private lateinit var viewModel: SearchViewModel

    private val keyword = "茶"

    override fun init() {
        viewModel = ViewModelProviders.of(activity)
            .get(SearchViewModel::class.java)
    }

    @Test
    fun refresh() {
        viewModel.refresh(keyword)
            .subscribe {
                it.forEach { data ->
                    assertNotNull("refresh search item id is null", data.id)
                }
            }
    }

    @Test
    fun loadMore() {
        viewModel.loadMore(keyword)
            .subscribe {
                it.forEach { data ->
                    assertNotNull("load search item id is null", data.id)
                }
            }
    }
}
