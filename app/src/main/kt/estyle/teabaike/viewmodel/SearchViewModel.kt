package estyle.teabaike.viewmodel

import android.app.Application
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import estyle.base.BaseViewModel
import estyle.teabaike.datasource.SearchListDataSource
import estyle.teabaike.entity.MainEntity
import io.reactivex.Observable

class SearchViewModel(application: Application) : BaseViewModel(application) {

    private var searchListBuilder: RxPagedListBuilder<Int, MainEntity.DataEntity>? = null

    fun refresh(keyword: String): Observable<PagedList<MainEntity.DataEntity>> {
        if (searchListBuilder == null) {
            searchListBuilder = RxPagedListBuilder(
                SearchListDataSource.Factory(keyword),
                PagedList.Config.Builder()
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()
            )
        }
        return searchListBuilder!!.buildObservable()
    }

}