package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.teabaike.datasource.CollectionDataSource
import estyle.teabaike.entity.ContentEntity

class CollectionViewModel(application: Application) : BaseViewModel(application) {

    //    @Inject
    lateinit var dataSource: CollectionDataSource

    init {
        dataSource = CollectionDataSource()

//        InjectUtil.dataSourceComponent()
//            .inject(this)
    }

    /**
     * 刷新全部收藏
     */
    fun refresh() = dataSource.refresh()

    /**
     * 删除指定收藏
     */
    fun deleteItems(items: List<ContentEntity>) = dataSource.delete(items)
}