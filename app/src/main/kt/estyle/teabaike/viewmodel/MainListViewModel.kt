package estyle.teabaike.viewmodel

import android.app.Application
import android.text.TextUtils
import estyle.base.BaseViewModel
import estyle.base.rxjava.ErrorMessageConsumer
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.config.Url
import estyle.teabaike.datasource.net.MainService
import estyle.teabaike.entity.MainEntity
import estyle.teabaike.util.NetworkUtil
import io.reactivex.Observable

class MainListViewModel(application: Application) : BaseViewModel(application) {

    private var page = 1

//    @Inject
//    lateinit var dataSource: MainListDataSource

//    init {
//        InjectUtil.dataSourceComponent()
//            .inject(this)
//    }

    // 加载头条数据
    fun loadHeadline() = NetworkUtil.service(MainService::class.java)
        .getHeadline()
        .doOnNext(ErrorMessageConsumer())
        .map { it.data }
        .compose(SchedulersTransformer())

    // 刷新列表
    fun refreshList(type: String): Observable<List<MainEntity.DataEntity>> {
        page = 1
        return loadList(type, page)
    }

    // 加载更多列表
    fun moreList(type: String) = loadList(type, ++page)

    private fun loadList(type: String, page: Int): Observable<List<MainEntity.DataEntity>> {
        val service = NetworkUtil.service(MainService::class.java)
        val observable = if (TextUtils.equals(type, Url.TITLES[0])) {
            service.getHeadlineList(page)
        } else {
            service.getList(type, page)
        }
        return observable.doOnNext(ErrorMessageConsumer())
            .map { it.data }
            .compose(SchedulersTransformer())
    }
}