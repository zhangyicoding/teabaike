package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.base.rxjava.ErrorMessageConsumer
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.NetDataSource
import estyle.teabaike.entity.MainEntity
import io.reactivex.Observable

class SearchViewModel(application: Application) : BaseViewModel(application) {

    private var page = 1

//    @Inject
//    lateinit var dataSource: SearchDataSource

//    init {
//        InjectUtil.dataSourceComponent()
//            .inject(this)
//    }

    fun refresh(keyword: String): Observable<List<MainEntity.DataEntity>> {
        page = 1
        return loadList(keyword, page)
    }

    fun loadMore(keyword: String) = loadList(keyword, page++)

    private fun loadList(keyword: String, page: Int) = NetDataSource.searchService()
        .getSearch(keyword, page)
        .doOnNext(ErrorMessageConsumer())
        .map { it.data }
        .compose(SchedulersTransformer())
}