package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.teabaike.dagger.component.DaggerDataSourceComponent
import estyle.teabaike.dagger.module.DataSourceModule
import estyle.teabaike.datasource.ContentDataSource
import estyle.teabaike.entity.ContentEntity
import javax.inject.Inject

class ContentViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var dataSource: ContentDataSource

    init {
        DaggerDataSourceComponent.builder()
            .dataSourceModule(DataSourceModule())
            .build()
            .inject(this)
    }

    fun refresh(id: Long) = dataSource.loadContent(id)

    fun collect(content: ContentEntity.DataEntity) = dataSource.collect(content)
}
