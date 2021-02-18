package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.teabaike.datasource.MainDataSource
import estyle.teabaike.entity.MainEntity
import io.reactivex.Observable

class MainListViewModel(application: Application) : BaseViewModel(application) {

    private var page = 1

    //    @Inject
    lateinit var dataSource: MainDataSource

    init {
        dataSource = MainDataSource()

//        InjectUtil.dataSourceComponent()
//            .inject(this)
    }

    /**
     * 加载头条数据
     */
    fun loadHeadline() = dataSource.loadHeadline()

    /**
     * 刷新列表
     */
    fun refreshList(type: String): Observable<List<MainEntity>> {
        page = 1
        return dataSource.loadList(type, page)
    }

    /**
     * 加载更多列表
     */
    fun moreList(type: String) = dataSource.loadList(type, ++page)
}