package estyle.teabaike.viewmodel

import android.app.Application
import estyle.teabaike.datasource.ContentDataSource
import estyle.teabaike.entity.ContentEntity

class ContentViewModel(application: Application) : BaseViewModel(application) {

    private val dataSource by lazy { ContentDataSource() }

    fun refresh(id: Long) = dataSource.loadContent(id)

    fun collect(content: ContentEntity.DataEntity) = dataSource.collect(content)
}
