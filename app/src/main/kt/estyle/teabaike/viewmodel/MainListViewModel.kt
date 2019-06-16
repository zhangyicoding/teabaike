package estyle.teabaike.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import estyle.base.BaseViewModel
import estyle.teabaike.config.Url
import estyle.teabaike.datasource.MainDataSource
import estyle.teabaike.datasource.MainListDataSource
import estyle.teabaike.entity.HeadlineEntity
import estyle.teabaike.entity.MainEntity
import estyle.teabaike.util.InjectUtil
import io.reactivex.Observable
import javax.inject.Inject

class MainListViewModel(application: Application) : BaseViewModel(application) {

    val refreshHeadlineList by lazy { MutableLiveData<List<HeadlineEntity.DataEntity>>() }
    val refreshMainList by lazy { MutableLiveData<List<MainEntity.DataEntity>>() }

    private var page = 1

    @Inject
    lateinit var dataSource: MainListDataSource

    init {
        InjectUtil.dataSourceComponent()
            .inject(this)
    }

    fun refresh(type: String): Observable<MainListViewModel> {
        page = 1
        val observable = if (TextUtils.equals(type, Url.TYPES[0]))
            Observable.mergeDelayError(loadHeadline(), refreshList(type, page))
        else
            refreshList(type, page)

        return observable.map { this }
    }

    fun load(type: String) = loadList(type, page++)

    private fun loadList(type: String, page: Int) = dataSource.loadList(type, page)

    private fun refreshList(type: String, page: Int) =
        loadList(type, page)
            .doOnNext { refreshMainList.value = it }

    private fun loadHeadline() =
        dataSource.loadHeadline()
            .doOnNext { refreshHeadlineList.value = it }
}