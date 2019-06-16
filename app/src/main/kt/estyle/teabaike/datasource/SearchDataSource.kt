package estyle.teabaike.datasource

import androidx.paging.DataSource
import estyle.base.BasePageKeyedDataSource
import estyle.base.rxjava.ErrorMessageConsumer
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.api.NetApi
import estyle.teabaike.entity.MainEntity

class SearchDataSource {

    fun loadList(keyword: String, page: Int) =
        NetApi.searchService()
            .getSearch(keyword, page)
            .doOnNext(ErrorMessageConsumer())
            .map { it.data }
            .compose(SchedulersTransformer())
}
