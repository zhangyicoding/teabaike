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
import io.reactivex.Observable

class MainViewModel(application: Application) : BaseViewModel(application) {

    val headerList by lazy { MutableLiveData<List<HeadlineEntity.DataEntity>>() }
    val mainList by lazy { MutableLiveData<PagedList<MainEntity.DataEntity>>() }

    private var mainListBuilder: RxPagedListBuilder<Int, MainEntity.DataEntity>? = null

    private val dataSource by lazy { MainDataSource() }

    fun refresh(type: String): Observable<MainViewModel> {
        val observable = if (TextUtils.equals(type, Url.TYPES[0]))
            Observable.mergeDelayError(loadData(type), loadHead())
        else
            loadData(type)

        return observable.map { this }
    }

    // 加载数据
    private fun loadData(type: String): Observable<PagedList<MainEntity.DataEntity>> {
        if (mainListBuilder == null) {
            mainListBuilder = RxPagedListBuilder(
                MainListDataSource.Factory(type),
                PagedList.Config.Builder()
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()
            )
        }
        return mainListBuilder!!.buildObservable()
            .doOnNext { mainList.value = it }
    }

    // 加载头数据
    private fun loadHead() =
        dataSource.loadHeadline()
            .doOnNext { headerList.value = it }

}
