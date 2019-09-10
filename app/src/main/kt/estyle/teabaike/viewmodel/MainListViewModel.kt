package estyle.teabaike.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import estyle.base.BaseViewModel
import estyle.base.rxjava.ErrorMessageConsumer
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.NetDataSource
import estyle.teabaike.config.Url
import estyle.teabaike.entity.HeadlineEntity
import estyle.teabaike.entity.MainEntity
import io.reactivex.Observable

class MainListViewModel(application: Application) : BaseViewModel(application) {

    val refreshHeadlineList by lazy { MutableLiveData<List<HeadlineEntity.DataEntity>>() }
    val refreshMainList by lazy { MutableLiveData<List<MainEntity.DataEntity>>() }

    private var page = 1

//    @Inject
//    lateinit var dataSource: MainListDataSource

//    init {
//        InjectUtil.dataSourceComponent()
//            .inject(this)
//    }

    fun refresh(type: String): Observable<MainListViewModel> {
        page = 1
        val observable = if (TextUtils.equals(type, Url.TYPES[0]))
            Observable.mergeDelayError(loadHeadline(), refreshList(type, page))
        else
            refreshList(type, page)

        return observable.map { this }
    }

    fun loadMore(type: String) = loadList(type, page++)

    private fun loadList(type: String, page: Int): Observable<List<MainEntity.DataEntity>> {
        val service = NetDataSource.mainService()
        val observable = if (TextUtils.equals(type, Url.TITLES[0])) {
            service.getHeadlineList(page)
        } else {
            service.getList(type, page)
        }
        return observable.doOnNext(ErrorMessageConsumer())
            .map { it.data }
            .compose(SchedulersTransformer())
    }

    private fun refreshList(type: String, page: Int) =
        loadList(type, page)
            .doOnNext { refreshMainList.value = it }

    private fun loadHeadline() = NetDataSource.mainService()
        .getHeadline()
        .doOnNext(ErrorMessageConsumer())
        .map { it.data }
        .compose(SchedulersTransformer())
        .doOnNext { refreshHeadlineList.value = it }
}