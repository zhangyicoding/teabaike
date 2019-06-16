package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.teabaike.datasource.SearchDataSource
import estyle.teabaike.entity.MainEntity
import estyle.teabaike.util.InjectUtil
import io.reactivex.Observable
import javax.inject.Inject

class SearchViewModel(application: Application) : BaseViewModel(application) {

    private var page = 1

    @Inject
    lateinit var dataSource: SearchDataSource

    init {
        InjectUtil.dataSourceComponent()
            .inject(this)
    }

    fun refresh(keyword: String): Observable<List<MainEntity.DataEntity>> {
        page = 1
        return dataSource.loadList(keyword, page)
    }

    fun load(keyword: String) = dataSource.loadList(keyword, page++)
}