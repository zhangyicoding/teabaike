package estyle.teabaike.datasource

import estyle.base.rxjava.ErrorCodeFunction
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.http.HttpManager
import estyle.teabaike.datasource.http.service.SearchService

class SearchDataSource {

    /**
     * 加载列表
     */
    fun loadList(keyword: String, page: Int) =
        HttpManager.service(SearchService::class.java)
            .getSearch(keyword, page)
            .map(ErrorCodeFunction())
            .compose(SchedulersTransformer())
}