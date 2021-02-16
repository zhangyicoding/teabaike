package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.base.rxjava.ErrorCodeFunction
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.http.service.MainService
import estyle.teabaike.entity.MainEntity
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
    fun loadHeadline() = HttpManager.service(MainService::class.java)
        .getHeadline()
        .map(ErrorCodeFunction())
        .compose(SchedulersTransformer())

    // 刷新列表
    fun refreshList(type: String): Observable<List<MainEntity>> {
        page = 1
        return loadList(type, page)
    }

    // 加载更多列表
    fun moreList(type: String) = loadList(type, ++page)

    private fun loadList(type: String, page: Int) =
        HttpManager.service(MainService::class.java)
            .getList(type, page)
            .map(ErrorCodeFunction())
            .compose(SchedulersTransformer())
}