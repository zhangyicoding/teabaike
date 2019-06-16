package estyle.teabaike.datasource

import estyle.base.rxjava.ErrorMessageConsumer
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.api.NetApi

class SearchDataSource {

    fun loadList(keyword: String, page: Int) =
        NetApi.searchService()
            .getSearch(keyword, page)
            .doOnNext(ErrorMessageConsumer())
            .map { it.data }
            .compose(SchedulersTransformer())
}
