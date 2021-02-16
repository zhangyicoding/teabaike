package estyle.teabaike.test

import androidx.lifecycle.ViewModelProviders
import estyle.teabaike.datasource.database.DatabaseManager
import estyle.teabaike.datasource.database.dao.CollectionDao
import estyle.teabaike.entity.ContentEntity
import estyle.teabaike.viewmodel.ContentViewModel
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Test

class ContentActivityTest : BaseTest() {

    private lateinit var viewModel: ContentViewModel

    private lateinit var db: DatabaseManager
    private lateinit var dao: CollectionDao

    override fun init() {
        viewModel = ViewModelProviders.of(activity)
            .get(ContentViewModel::class.java)
    }

    @After
    fun after() {
//        db.close()
    }

    @Test
    fun refreshAndCollect() {
        var content: ContentEntity.DataEntity? = null

        viewModel.refresh(0)
            .subscribe {
                assertNotNull("content is null", it)
                content = it
                println("qqqq")
            }

        println("qqqq collect start")
        viewModel.collect(content!!)
            .subscribe({
                println("111" + it)
            }, {
                println("222error: " + it)
            })
    }
}
