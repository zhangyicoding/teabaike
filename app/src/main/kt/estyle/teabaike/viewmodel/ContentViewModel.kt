package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.teabaike.datasource.ContentDataSource
import estyle.teabaike.entity.ContentEntity

class ContentViewModel(application: Application) : BaseViewModel(application) {

    //    @Inject
    lateinit var dataSource: ContentDataSource

    init {
        dataSource = ContentDataSource()

//        InjectUtil.dataSourceComponent()
//            .inject(this)
    }

    /**
     * 刷新
     */
    fun refresh(id: Long) = dataSource.refresh(id)

    /**
     * 收藏
     */
    fun collect(content: ContentEntity) = dataSource.collect(content)
}
