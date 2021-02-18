package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.teabaike.datasource.SearchDataSource
import estyle.teabaike.entity.MainEntity
import io.reactivex.Observable

class SearchViewModel(application: Application) : BaseViewModel(application) {

    private var page = 1

    //    @Inject
    lateinit var dataSource: SearchDataSource

    init {
        dataSource = SearchDataSource()

//        InjectUtil.dataSourceComponent()
//            .inject(this)
    }

    fun refresh(keyword: String): Observable<List<MainEntity>> {
        page = 1
        return dataSource.loadList(keyword, page)
    }

    fun loadMore(keyword: String) = dataSource.loadList(keyword, ++page)


}