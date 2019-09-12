package estyle.teabaike.viewmodel

import android.app.Application
import estyle.base.BaseViewModel
import estyle.base.rxjava.ErrorMessageConsumer
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.DbDataSource
import estyle.teabaike.datasource.NetDataSource
import estyle.teabaike.entity.ContentEntity

class ContentViewModel(application: Application) : BaseViewModel(application) {

//    @Inject
//    lateinit var dataSource: ContentDataSource

//    init {
//        InjectUtil.dataSourceComponent()
//            .inject(this)
//    }

    fun refresh(id: Long) = NetDataSource.contentService()
        .getContent(id)
        .doOnNext(ErrorMessageConsumer())
        .map { it.data }
        .compose(SchedulersTransformer())

    fun collect(content: ContentEntity.DataEntity) = DbDataSource.collectionDao()
        .insert(content)
        .toObservable()
        .compose(SchedulersTransformer())
}
