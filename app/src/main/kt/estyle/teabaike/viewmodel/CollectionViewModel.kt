package estyle.teabaike.viewmodel

import android.app.Application
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import estyle.base.BaseViewModel
import estyle.teabaike.datasource.CollectionDataSource
import estyle.teabaike.entity.ContentEntity
import estyle.teabaike.util.InjectUtil
import io.reactivex.Observable
import javax.inject.Inject

class CollectionViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var dataSource: CollectionDataSource

    init {
        InjectUtil.dataSourceComponent()
            .inject(this)
    }

    private var collectionListBuilder: RxPagedListBuilder<Int, ContentEntity.DataEntity>? = null

    fun refresh(): Observable<PagedList<ContentEntity.DataEntity>> {
        if (collectionListBuilder == null) {
            collectionListBuilder = RxPagedListBuilder(
                dataSource.queryAll(),
                PagedList.Config.Builder()
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()
            )
        }
        return collectionListBuilder!!.buildObservable()
    }

    fun deleteItems(items: List<ContentEntity.DataEntity>) = dataSource.delete(items)
}