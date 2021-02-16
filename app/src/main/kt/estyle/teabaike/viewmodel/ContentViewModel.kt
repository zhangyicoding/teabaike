package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.base.rxjava.ErrorCodeFunction
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.database.DatabaseManager
import estyle.teabaike.datasource.http.HttpManager
import estyle.teabaike.datasource.http.service.ContentService
import estyle.teabaike.entity.ContentEntity

class ContentViewModel(application: Application) : BaseViewModel(application) {

//    @Inject
//    lateinit var dataSource: ContentDataSource

//    init {
//        InjectUtil.dataSourceComponent()
//            .inject(this)
//    }

    fun refresh(id: Long) = HttpManager.service(ContentService::class.java)
        .getContent(id)
        .map(ErrorCodeFunction())
        .compose(SchedulersTransformer())

    fun collect(content: ContentEntity) = DatabaseManager.INSTANCE
        .collectionDao()
        .insert(content)
        .toObservable()
        .compose(SchedulersTransformer())
}
