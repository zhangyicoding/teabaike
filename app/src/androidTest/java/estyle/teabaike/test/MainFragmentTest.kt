package estyle.teabaike.test

import android.text.TextUtils
import androidx.lifecycle.Observer
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
    fun refresh() {
        activity.runOnUiThread {
            Url.TYPES.forEach {
                viewModel.refreshMainList.observe(activity, Observer { list ->
                    assertEquals(10, list.size)
                })

                // TODO 需要把list和headline的请求继续抽离出来
                if (TextUtils.equals(it, Url.TYPES[0])) {
                    viewModel.refreshHeadlineList.observe(activity, Observer { list ->
                        assertEquals(3, list.size)
                    })
                }

                viewModel.refresh(it)
                    .subscribe()
            }
        }
    }

    @Test
    fun loadMore() {
        Url.TYPES.forEach { type ->
            viewModel.loadMore(type)
                .subscribe {
                    assertEquals(10, it.size)
                }
        }
    }
}
