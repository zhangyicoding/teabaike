package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.base.rxjava.ErrorCodeFunction
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.net.SearchService
import estyle.teabaike.entity.MainEntity
import estyle.teabaike.util.NetworkUtil
import io.reactivex.Observable

class SearchViewModel(application: Application) : BaseViewModel(application) {

    private var page = 1

//    @Inject
//    lateinit var dataSource: SearchDataSource

//    init {
//        InjectUtil.dataSourceComponent()
//            .inject(this)
//    }

    fun refresh(keyword: String): Observable<List<MainEntity>> {
        page = 1
        return loadList(keyword, page)
    }

    fun loadMore(keyword: String) = loadList(keyword, page++)

    private fun loadList(keyword: String, page: Int) = NetworkUtil.service(SearchService::class.java)
        .getSearch(keyword, page)
        .map(ErrorCodeFunction())
        .compose(SchedulersTransformer())
}