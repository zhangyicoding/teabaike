package estyle.teabaike.test

import androidx.lifecycle.ViewModelProviders
import estyle.teabaike.entity.ContentEntity
import estyle.teabaike.viewmodel.CollectionViewModel
import org.junit.Test

class CollectionActivityTest : BaseTest() {

    private lateinit var viewModel: CollectionViewModel

    override fun init() {
        viewModel = ViewModelProviders.of(activity)
            .get(CollectionViewModel::class.java)
    }

    @Test
    fun refreshAndDeleteAll() {
        println("cccccccccccccc11111")
        var items: List<ContentEntity.DataEntity>? = null
        viewModel.refresh()
            .flatMap {
                println("ccccc 222222 " + it)
                viewModel.deleteItems(it)
            }
            .subscribe {
                println("cccccc3333333 delete count: " + it)
            }
        println("cccccc4444444444 end")
    }
}