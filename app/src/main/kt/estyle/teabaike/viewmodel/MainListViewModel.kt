package estyle.teabaike.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import estyle.base.BaseViewModel
import estyle.teabaike.config.Url
import estyle.teabaike.datasource.MainDataSource
import estyle.teabaike.entity.HeadlineEntity
import estyle.teabaike.entity.MainEntity
import io.reactivex.Observable

class MainListViewModel(application: Application) : BaseViewModel(application) {

    private var page = 1

    val refreshList = MutableLiveData<List<MainEntity>>()
    val headline = MutableLiveData<List<HeadlineEntity>>()

    //    @Inject
    lateinit var dataSource: MainDataSource

    init {
        dataSource = MainDataSource()

//        InjectUtil.dataSourceComponent()
//            .inject(this)
    }

    /**
     * 刷新数据，头条频道刷新头条数据 + 列表数据
     */
    fun refresh(type: String): Observable<out Any> {
        val refreshListObservable = refreshList(type)
            .doOnNext { refreshList.value = it }
        var observable: Observable<out Any> = refreshListObservable

        // 头条
        if (TextUtils.equals(type, Url.TYPES[0])) {
            val headlineObservable = dataSource.loadHeadline()
                .doOnNext { headline.value = it }
            observable = Observable.mergeDelayError(refreshListObservable, headlineObservable)
        }
        return observable
    }

    /**
     * 加载更多列表
     */
    fun moreList(type: String) = dataSource.loadList(type, ++page)

    /**
     * 刷新列表
     */
    private fun refreshList(type: String): Observable<List<MainEntity>> {
        page = 1
        return dataSource.loadList(type, page)
    }
}