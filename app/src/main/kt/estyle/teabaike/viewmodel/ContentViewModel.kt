package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.base.rxjava.ErrorMessageConsumer
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.TeaBaikeDatabase
import estyle.teabaike.datasource.net.ContentService
import estyle.teabaike.entity.ContentEntity
import estyle.teabaike.util.NetworkUtil

class ContentViewModel(application: Application) : BaseViewModel(application) {

//    @Inject
//    lateinit var dataSource: ContentDataSource

//    init {
//        InjectUtil.dataSourceComponent()
//            .inject(this)
//    }

    fun refresh(id: Long) = NetworkUtil.service(ContentService::class.java)
        .getContent(id)
        .doOnNext(ErrorMessageConsumer())
        .map { it.data }
        .compose(SchedulersTransformer())

    fun collect(content: ContentEntity.DataEntity) = TeaBaikeDatabase.INSTANCE
        .collectionDao()
        .insert(content)
        .toObservable()
        .compose(SchedulersTransformer())
}
