package estyle.teabaike.datasource

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import estyle.base.rxjava.SchedulersTransformer
import estyle.teabaike.datasource.database.DatabaseManager
import estyle.teabaike.entity.ContentEntity
import io.reactivex.Observable

class CollectionDataSource {

    private var collectionListBuilder: RxPagedListBuilder<Int, ContentEntity>? = null

    /**
     * 刷新全部收藏
     */
    fun refresh(): Observable<PagedList<ContentEntity>> {
        if (collectionListBuilder == null) {
            collectionListBuilder = RxPagedListBuilder(
                DatabaseManager.INSTANCE.collectionDao()
                    .queryAll(),
                PagedList.Config.Builder()
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()
            )
        }
        return collectionListBuilder!!.buildObservable()
    }

    /**
     * 删除指定收藏
     */
    fun delete(items: List<ContentEntity>) =
        DatabaseManager.INSTANCE
            .collectionDao()
            .delete(items)
            .toObservable()
            .compose(SchedulersTransformer())
}